package cn.muses.common.orm.mybatis.easylist.paginator.dialect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.muses.common.orm.mybatis.easylist.paginator.domain.Order;
import cn.muses.common.orm.mybatis.easylist.paginator.domain.PageBounds;
import cn.muses.common.orm.mybatis.easylist.paginator.domain.WhereCriteria;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.StringUtils;

/**
 * 类似hibernate的Dialect,但只精简出分页部分
 *
 * @author badqiu
 * @author miemiedev
 * @author Jervis
 */
public class Dialect {

	public static final String			WHERE_CLAUSE_PLACEHOLDER	= "_WHERE_CLAUSE_PLACEHOLDER";

	protected MappedStatement			mappedStatement;
	protected PageBounds pageBounds;
	protected Object					parameterObject;
	protected BoundSql					boundSql;
	protected List<ParameterMapping>	parameterMappings;
	protected List<ParameterMapping>	whereTempMappings;
	protected Map<String, Object>		pageParameters				= new HashMap<String, Object>();
	protected int						nowParamIndex				= 0;

	private String						pageSQL;
	private String						countSQL;
	private List<ParameterMapping>		countParameterMappings;
	private Map<String, Object>			countParameterObject;

	public Dialect(MappedStatement mappedStatement, Object parameterObject, PageBounds pageBounds) {
		this.mappedStatement = mappedStatement;
		this.parameterObject = parameterObject;
		this.pageBounds = pageBounds;

		init();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void init() {
		boundSql = mappedStatement.getBoundSql(parameterObject);
		parameterMappings = new ArrayList<ParameterMapping>(boundSql.getParameterMappings());
		if (parameterObject instanceof Map) {
			pageParameters.putAll((Map) parameterObject);
		} else {
			for (ParameterMapping parameterMapping : parameterMappings) {
				pageParameters.put(parameterMapping.getProperty(), parameterObject);
			}
		}

		StringBuffer bufferSql = new StringBuffer(boundSql.getSql().trim());
		if (bufferSql.lastIndexOf(";") == bufferSql.length() - 1) {
			bufferSql.deleteCharAt(bufferSql.length() - 1);
		}
		String sql = bufferSql.toString();

		// 拼装WHERE 条件
		pageSQL = imbedWhereClause(sql);
		// 求总数的Sql和参数应该是添加分页语句之前的sql与参数，否则最大数量就是分页数量
		// 所以在拼装完Where条件后即处理Count需要的资源
		processCountResources();
		// 拼装OrderBy条件
		pageSQL = imbedOrderClause(pageSQL, pageBounds);

		if (pageBounds.getOffset() != RowBounds.NO_ROW_OFFSET || pageBounds.getLimit() != RowBounds.NO_ROW_LIMIT) {
			pageSQL = getLimitString(pageSQL, "__offset", pageBounds.getOffset(), "__limit", pageBounds.getLimit());
		}
	}

	public List<ParameterMapping> getParameterMappings() {
		return parameterMappings;
	}

	public Object getParameterObject() {
		return pageParameters;
	}

	public String getPageSQL() {
		return pageSQL;
	}

	protected void setPageParameter(String name, Object value, Class<?> type) {
		ParameterMapping parameterMapping = new ParameterMapping.Builder(mappedStatement.getConfiguration(), name, type)
				.build();
		parameterMappings.add(parameterMapping);
		pageParameters.put(name, value);
	}

	protected void setWhereParameter(String name, Object value, Class<?> type) {
		ParameterMapping parameterMapping = new ParameterMapping.Builder(mappedStatement.getConfiguration(), name, type)
				.build();
		whereTempMappings.add(parameterMapping);
		pageParameters.put(name, value);
	}

	public String getCountSQL() {
		return countSQL;
	}

	/**
	 * @return the countParameterMappings
	 */
	public List<ParameterMapping> getCountParameterMappings() {
		return countParameterMappings;
	}

	/**
	 * @return the countParameterObject
	 */
	public Map<String, Object> getCountParameterObject() {
		return countParameterObject;
	}

	/**
	 * 将sql变成分页sql语句
	 */
	protected String getLimitString(String sql, String offsetName, int offset, String limitName, int limit) {
		throw new UnsupportedOperationException("paged queries not supported");
	}

	/**
	 * 将sql转换为总记录数SQL
	 *
	 * @param sql SQL语句
	 * @return 总记录数的sql
	 */
	protected String getCountString(String sql) {
		return "select count(1) from (" + sql + ") tmp_count";
	}

	/**
	 * 根据PageBounds的WhereClause植入Where条件<br/>
	 * 最终条件语句放置的位置参照 {@link #imbedStrWhereClause(String, String)}
	 *
	 * @param sql
	 * @return
	 */
	protected String imbedWhereClause(String sql) {
		if (!pageBounds.hasWhereClause()) {
			return sql;
		}

		whereTempMappings = new LinkedList<ParameterMapping>();

		WhereCriteria whereClause = pageBounds.getWhereClause();
		List<WhereCriteria.Criteria> oredCriteria = whereClause.getOredCriteria();

		StringBuilder sb = new StringBuilder();
		for (WhereCriteria.Criteria criteria : oredCriteria) {
			if (criteria.isValid()) {
				sb.append("(");
				for (WhereCriteria.Criterion criterion : criteria.getCriteria()) {
					if (criterion.isNoValue()) {
						sb.append(" and ").append(criterion.getCondition());
					}
					if (criterion.isSingleValue()) {
						sb.append(" and ").append(criterion.getCondition()).append("?");
						String whereParamName = getWhereParamName();
						setWhereParameter(whereParamName, criterion.getValue(), criterion.getValue().getClass());
					}
					if (criterion.isBetweenValue()) {
						sb.append(" and ").append(criterion.getCondition()).append("?").append(" and ").append("?");
						String whereParamName = getWhereParamName();
						String secondWhereParamName = getWhereParamName();
						setWhereParameter(whereParamName, criterion.getValue(), criterion.getValue().getClass());
						setWhereParameter(secondWhereParamName, criterion.getSecondValue(), criterion.getSecondValue()
								.getClass());
					}
					if (criterion.isListValue()) {
						if (!(criterion.getValue() instanceof Iterable)) {
							throw new IllegalArgumentException("criterion.value 不可遍历！");
						}
						sb.append(" and ").append(criterion.getCondition()).append(" ( ");
						for (Object obj : (Iterable<?>) criterion.getValue()) {
							String whereParamName = getWhereParamName();
							sb.append(" ? ").append(",");
							setWhereParameter(whereParamName, obj, obj.getClass());
						}
						if (sb.charAt(sb.length() - 1) == ',') {
							sb.deleteCharAt(sb.length() - 1);
						}
						sb.append(")");
					}
				}
				int firstIndex = sb.indexOf("and");
				sb.delete(firstIndex, firstIndex + 3);
				sb.append(")");
			}
			sb.append(" or ");
		}
		sb.delete(sb.lastIndexOf("or "), sb.length());
		if (sb.length() > 2) {
			return imbedStrWhereClause(sql, sb.toString());
		}
		return sql;
	}

	/**
	 * 根据当前上下文处理Count查询需要的资源
	 */
	protected void processCountResources() {
		countSQL = getCountString(pageSQL);
		countParameterMappings = new ArrayList<ParameterMapping>(parameterMappings);
		countParameterObject = new HashMap<String, Object>(pageParameters);
	}

	/**
	 * 在原sql中简单置入OrderBy条件<br/>
	 *
	 * @param sql
	 * @param pageBounds
	 * @return
	 */
	protected String imbedOrderClause(String sql, PageBounds pageBounds) {
		if (!pageBounds.hasOrderByClause()) {
			return sql;
		}
		StringBuilder sqlBuilder = new StringBuilder("select * from (").append(sql).append(") temp_order order by ");
		if (StringUtils.hasText(pageBounds.getOrderByClause())) {
			return sqlBuilder.append(pageBounds.getOrderByClause()).toString();
		}
		for (Order order : pageBounds.getOrders()) {
			if (order != null) {
				sqlBuilder.append(order.toString()).append(", ");
			}

		}
		sqlBuilder.delete(sqlBuilder.length() - 2, sqlBuilder.length());
		return sqlBuilder.toString();
	}

	/**
	 * 生成不重复的Where条件参数名
	 *
	 * @return
	 */
	private String getWhereParamName() {
		return "__Where_Param_" + (++nowParamIndex);
	}

	/**
	 * 在原sql中简单置入where条件<br/>
	 * 原则如下：
	 * <ul>
	 * <li>如果 sql中使用 WHERE_CLAUSE_PLACEHOLDER 占位符 则直接替换</li>
	 * <li>如果存在where，则直接替换第一个</li>
	 * <li>没使用占位符且不存在where的，直接添加在最后</li>
	 * </ul>
	 *
	 * @param sql 原始sql
	 * @param clause 不含“where”的搜索条件
	 * @return
	 */
	private String imbedStrWhereClause(String sql, String clause) {
		boolean containsWhere = sql.matches("[\\s\\S]*(?i)where[\\s\\S]*");
		String whereStr = " where ";
		if (!containsWhere) {
			clause = whereStr.concat(clause);
		}
		if (sql.contains(WHERE_CLAUSE_PLACEHOLDER)) {
			// 把Where参数插入到指定位置
			int beforeNum = getBeforeNum(sql, WHERE_CLAUSE_PLACEHOLDER, "?");
			parameterMappings.addAll(beforeNum, whereTempMappings);
			// 如果 sql中使用 WHERE_CLAUSE_PLACEHOLDER 占位符 则直接替换
			return sql.replace(WHERE_CLAUSE_PLACEHOLDER, clause);
		}
		if (containsWhere) {
			// 如果存在where，则直接替换第一个
			String resultSql = sql.replaceFirst("(?i)where", whereStr.concat(clause).concat(" and "));
			// 把Where参数插入到指定位置
			int beforeNum = getBeforeNum(sql, whereStr, "?");
			parameterMappings.addAll(beforeNum, whereTempMappings);
			return resultSql;
		}

		parameterMappings.addAll(whereTempMappings);
		// 没使用占位符且不存在where的，直接添加在最后
		return sql.concat(clause);
	}

	/**
	 * 获取oStr 中，在idStr前有多少个tStr
	 *
	 * @param oStr
	 * @param id
	 * @param tStr
	 * @return
	 */
	private int getBeforeNum(String oStr, String id, String tStr) {
		int index = oStr.indexOf(id);
		if (index < 0) {
			return 0;
		}
		String substring = oStr.substring(0, index);
		if (substring.indexOf(tStr) < 0) {
			return 0;
		}
		return substring.split(tStr).length - 1;
	}

}
