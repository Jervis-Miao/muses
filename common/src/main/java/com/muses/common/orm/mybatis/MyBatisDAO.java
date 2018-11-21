package com.muses.common.orm.mybatis;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.convert.TypeDescriptor;

import com.muses.common.orm.annotations.Table;
import com.muses.common.orm.common.Constants;
import com.muses.common.orm.common.OrmContext;
import com.muses.common.orm.page.PageReq;
import com.muses.common.orm.page.PageRes;

public abstract class MyBatisDAO<Entity, Criteria> extends SqlSessionDaoSupport {
	protected static Map<Class<?>, Class<?>>	tableCache	= new HashMap<>();
	@Autowired
	protected OrmContext						ormContext;

	@PostConstruct
	public void postConstruct() {
		SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) ormContext.getSessions().get(this.getSchema());
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	/**
	 * 默认不支持BLOBs操作，如需要则需明确关闭
	 *
	 * @return false
	 */
	protected boolean isBlobsSupported() {
		return false;
	}

	protected String getNamespace() {
		return getEntityClazz().getCanonicalName();
	}

	protected String getSchema() {
		Table table = getEntityClazz().getAnnotation(Table.class);
		return table.schema();
	}

	public int countByCriteria(Criteria criteria) {
		Integer count = (Integer) this.getSqlSession().selectOne(this.getNamespace() + ".countByCriteria", criteria);
		return count;
	}

	public int deleteByCriteria(Criteria criteria) {
		int count = this.getSqlSession().delete(this.getNamespace() + ".deleteByCriteria", criteria);
		return count;
	}

	public int deleteByPrimaryKey(Entity entity) {
		int count = this.getSqlSession().delete(this.getNamespace() + ".deleteByPrimaryKey", entity);
		return count;
	}

	public int insert(Entity entity) {
		return this.getSqlSession().insert(this.getNamespace() + ".insert", entity);
	}

	public int insertSelective(Entity entity) {
		return this.getSqlSession().insert(this.getNamespace() + ".insertSelective", entity);
	}

	public List<Entity> selectByCriteria(Criteria criteria) {
		return this.getSqlSession().selectList(this.getNamespace() + ".selectByCriteria", criteria);
	}

	public PageRes<Entity> selectByCriteriaWithPageReq(Criteria criteria, PageReq pageReq) {
		PageRes<Entity> pageResDTO = new PageRes<Entity>(pageReq.getPageIndex(), pageReq.getPageSize());
		int count = this.countByCriteria(criteria);
		pageResDTO.setTotalCount(count);
		if (count > 0) {
			RowBounds rowBounds = new RowBounds(pageResDTO.getFirstResult(), pageResDTO.getPageSize());
			List<Entity> data = this.selectByCriteriaWithRowbounds(criteria, rowBounds);
			pageResDTO.setData(data);
		}
		return pageResDTO;
	}

	public List<Entity> selectByCriteriaWithRowbounds(Criteria criteria, RowBounds rowBounds) {
		List<Entity> data = this.getSqlSession().selectList(this.getNamespace() + ".selectByCriteriaWithRowbounds",
				criteria, rowBounds);
		return data;
	}

	public List<Entity> selectByCriteriaWithBLOBs(Criteria criteria) {
		assertBlobsSupported();
		return this.getSqlSession().selectList(this.getNamespace() + ".selectByCriteriaWithBLOBs", criteria);
	}

	public PageRes<Entity> selectByCriteriaWithBLOBsWithPageReq(Criteria criteria, PageReq pageReq) {
		assertBlobsSupported();
		PageRes<Entity> pageRes = new PageRes<Entity>(pageReq);
		int count = this.countByCriteria(criteria);
		pageRes.setTotalCount(count);
		if (count > 0) {
			RowBounds rowBounds = new RowBounds(pageRes.getFirstResult(), pageRes.getPageSize());
			List<Entity> data = this.selectByCriteriaWithBLOBsWithRowbounds(criteria, rowBounds);
			pageRes.setData(data);
		}
		return pageRes;
	}

	public List<Entity> selectByCriteriaWithBLOBsWithRowbounds(Criteria criteria, RowBounds rowBounds) {
		assertBlobsSupported();
		List<Entity> data = this.getSqlSession().selectList(
				this.getNamespace() + ".selectByCriteriaWithBLOBsWithRowbounds", criteria, rowBounds);
		return data;
	}

	public Entity selectByPrimaryKey(Entity entity) {
		return this.getSqlSession().selectOne(this.getNamespace() + ".selectByPrimaryKey", entity);
	}

	public int updateByCriteria(Entity entity, Criteria criteria) {
		HashMap map = new HashMap();
		map.put("record", entity);
		map.put("example", criteria);
		return this.getSqlSession().update(this.getNamespace() + ".updateByCriteria", map);
	}

	public int updateByCriteriaSelective(Entity entity, Criteria criteria) {
		HashMap map = new HashMap();
		map.put("record", entity);
		map.put("example", criteria);
		return this.getSqlSession().update(this.getNamespace() + ".updateByCriteriaSelective", map);
	}

	public int updateByCriteriaWithBLOBs(Entity entity, Criteria criteria) {
		assertBlobsSupported();
		HashMap map = new HashMap();
		map.put("record", entity);
		map.put("example", criteria);
		return this.getSqlSession().update(this.getNamespace() + ".updateByCriteriaWithBLOBs", map);
	}

	public int updateByPrimaryKey(Entity entity) {
		return this.getSqlSession().update(this.getNamespace() + ".updateByPrimaryKey", entity);
	}

	public int updateByPrimaryKeySelective(Entity entity) {
		return this.getSqlSession().update(this.getNamespace() + ".updateByPrimaryKeySelective", entity);
	}

	public int updateByPrimaryKeyWithBLOBs(Entity entity) {
		assertBlobsSupported();
		return this.getSqlSession().update(this.getNamespace() + ".updateByPrimaryKeyWithBLOBs", entity);
	}

	private void assertBlobsSupported() {
		if (!isBlobsSupported()) {
			throw new UnsupportedOperationException("DAO不支持Blobs操作，如需要请覆盖isBlobsSupported()方法！");
		}
	}

	private Class<?> getEntityClazz() {
		Class clazz = tableCache.get(this.getClass());
		if (clazz != null) {
			return clazz;
		}
		Map<TypeVariable, Type> typeVariableMap = GenericTypeResolver.getTypeVariableMap(this.getClass());
		for (TypeVariable typeVariable : typeVariableMap.keySet()) {
			if (Constants.TYPE_VARIABLES_ENTITY.equals(typeVariable.getName())) {
				TypeDescriptor srcTypeDescriptor = TypeDescriptor.valueOf((Class<?>) typeVariableMap.get(typeVariable));
				clazz = srcTypeDescriptor.getType();
				break;
			}
		}
		tableCache.put(this.getClass(), clazz);
		return clazz;
	}
}
