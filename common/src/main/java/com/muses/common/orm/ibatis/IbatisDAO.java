package com.muses.common.orm.ibatis;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.util.ReflectionUtils;

import com.ibatis.sqlmap.client.SqlMapClient;

import com.muses.common.orm.page.PageReq;
import com.muses.common.orm.page.PageRes;
import com.muses.common.orm.annotations.Table;
import com.muses.common.orm.common.Constants;
import com.muses.common.orm.common.OrmContext;

/**
 * @author Jervis
 *
 * @param <Entity>
 * @param <Criteria>
 */
@SuppressWarnings({ "unchecked", "deprecation" })
public abstract class IbatisDAO<Entity, Criteria> extends SqlMapClientDaoSupport {
    protected static Map<Class<?>, Table> tableCache = new HashMap<Class<?>, Table>();
    @Autowired
    protected OrmContext ormContext;

    @PostConstruct
    public void postConstruct() {
        SqlMapClient sqlMapClient = (SqlMapClient) ormContext.getSessions().get(this.getSchema());
        super.setSqlMapClient(sqlMapClient);
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
        return getEntityTable().name();
    }

    protected String getSchema() {
        return getEntityTable().schema();
    }

    public int countByCriteria(Criteria criteria) {
        Integer count = (Integer) this.getSqlMapClientTemplate()
                .queryForObject(getEntityTable().name() + ".countByCriteria", criteria);
        return count;
    }

    public int deleteByCriteria(Criteria criteria) {
        int count = this.getSqlMapClientTemplate().delete(this.getNamespace() + ".deleteByCriteria", criteria);
        return count;
    }

    public int deleteByPrimaryKey(Entity entity) {
        int count = this.getSqlMapClientTemplate().delete(this.getNamespace() + ".deleteByPrimaryKey", entity);
        return count;
    }

    public void insert(Entity entity) {
        this.getSqlMapClientTemplate().insert(this.getNamespace() + ".insert", entity);
    }

    public void insertSelective(Entity entity) {
        this.getSqlMapClientTemplate().insert(this.getNamespace() + ".insertSelective", entity);
    }

    public List<Entity> selectByCriteria(Criteria criteria) {
        List<Entity> data = this.getSqlMapClientTemplate().queryForList(this.getNamespace() + ".selectByCriteria",
                criteria);
        return data;
    }

    public PageRes<Entity> selectByCriteriaWithPageReq(Criteria criteria, PageReq pageReq) {
        PageRes<Entity> pageRes = new PageRes<Entity>(pageReq);
        int totalCount = countByCriteria(criteria);
        pageRes.setTotalCount(totalCount);
        if (totalCount > 0) {
            List<Entity> list = this.selectByCriteriaWithRowbounds(criteria, pageRes.getFirstResult(),
                    pageRes.getPageSize());
            pageRes.setData(list);
        }
        return pageRes;
    }

    public List<Entity> selectByCriteriaWithRowbounds(Criteria criteria, int skipResults, int maxResults) {
        List<Entity> data = this.getSqlMapClientTemplate().queryForList(this.getNamespace() + ".selectByCriteria",
                criteria, skipResults, maxResults);
        return data;
    }

    public List<Entity> selectByCriteriaWithBLOBs(Criteria criteria) {
        assertBlobsSupported();
        List<Entity> data = this.getSqlMapClientTemplate()
                .queryForList(this.getNamespace() + ".selectByCriteriaWithBLOBs", criteria);
        return data;
    }

    public PageRes<Entity> selectByCriteriaWithBLOBsWithPageReq(Criteria criteria, PageReq pageReq) {
        assertBlobsSupported();
        PageRes<Entity> pageRes = new PageRes<Entity>(pageReq);
        int totalCount = countByCriteria(criteria);
        pageRes.setTotalCount(totalCount);
        if (totalCount > 0) {
            List<Entity> list = this.selectByCriteriaWithBLOBsWithRowbounds(criteria, pageRes.getFirstResult(),
                    pageRes.getPageSize());
            pageRes.setData(list);
        }
        return pageRes;
    }

    public List<Entity> selectByCriteriaWithBLOBsWithRowbounds(Criteria criteria, int skipResults, int maxResults) {
        assertBlobsSupported();
        List<Entity> data = this.getSqlMapClientTemplate()
                .queryForList(this.getNamespace() + ".selectByCriteriaWithBLOBs", criteria, skipResults, maxResults);
        return data;
    }

    public Entity selectByPrimaryKey(Entity entity) {
        Entity record = (Entity) this.getSqlMapClientTemplate()
                .queryForObject(this.getNamespace() + ".selectByPrimaryKey", entity);
        return record;
    }

    public int updateByCriteria(Entity entity, Criteria criteria) {
        UpdateByCriteriaParms<Entity, Criteria> parms = new UpdateByCriteriaParms<Entity, Criteria>(entity, criteria);
        int rows = this.getSqlMapClientTemplate().update(this.getNamespace() + ".updateByCriteria", parms);
        return rows;
    }

    public int updateByCriteriaSelective(Entity entity, Criteria criteria) {
        UpdateByCriteriaParms<Entity, Criteria> parms = new UpdateByCriteriaParms<Entity, Criteria>(entity, criteria);
        int count = this.getSqlMapClientTemplate().update(this.getNamespace() + ".updateByCriteriaSelective", parms);
        return count;
    }

    public int updateByCriteriaWithBLOBs(Entity entity, Criteria criteria) {
        assertBlobsSupported();
        UpdateByCriteriaParms<Entity, Criteria> parms = new UpdateByCriteriaParms<Entity, Criteria>(entity, criteria);
        int count = this.getSqlMapClientTemplate().update(this.getNamespace() + ".updateByCriteriaWithBLOBs", parms);
        return count;
    }

    public int updateByPrimaryKey(Entity entity) {
        int count = this.getSqlMapClientTemplate().update(this.getNamespace() + ".updateByPrimaryKey", entity);
        return count;
    }

    public int updateByPrimaryKeySelective(Entity entity) {
        int count = this.getSqlMapClientTemplate().update(this.getNamespace() + ".updateByPrimaryKeySelective", entity);
        return count;
    }

    public int updateByPrimaryKeyWithBLOBs(Entity entity) {
        assertBlobsSupported();
        int count = this.getSqlMapClientTemplate().update(this.getNamespace() + ".updateByPrimaryKeyWithBLOBs", entity);
        return count;
    }

    private Table getEntityTable() {
        Table table = tableCache.get(this.getClass());
        if (table != null) {
            return table;
        }
        Map<TypeVariable, Type> typeVariableMap = GenericTypeResolver.getTypeVariableMap(this.getClass());
        for (TypeVariable typeVariable : typeVariableMap.keySet()) {
            if (Constants.TYPE_VARIABLES_ENTITY.equals(typeVariable.getName())) {
                TypeDescriptor srcTypeDescriptor = TypeDescriptor.valueOf((Class<?>) typeVariableMap.get(typeVariable));
                table = srcTypeDescriptor.getType().getAnnotation(Table.class);
                break;
            }
        }
        tableCache.put(this.getClass(), table);
        return table;
    }

    private void assertBlobsSupported() {
        if (!isBlobsSupported()) {
            throw new UnsupportedOperationException("DAO不支持Blobs操作，如需要请覆盖isBlobsSupported()方法！");
        }
    }

    private static class CriteriaClazz {
        private static final String ORED_CRITERIA_FIELD = "orderByClause";
        private static final String ORED_CRITERIA = "oredCriteria";
        private static final String DISTINCT = "distinct";
        public final Field oredCriteriaField;
        public final Field orderByClauseField;
        public final Field distinctField;

        public CriteriaClazz(Class criteria) {
            this.orderByClauseField = ReflectionUtils.findField(criteria, ORED_CRITERIA_FIELD);
            this.oredCriteriaField = ReflectionUtils.findField(criteria, ORED_CRITERIA);
            this.distinctField = ReflectionUtils.findField(criteria, DISTINCT);
            this.orderByClauseField.setAccessible(true);
            this.oredCriteriaField.setAccessible(true);
            this.distinctField.setAccessible(true);
        }
    }

    @SuppressWarnings("unused")
    private static class UpdateByCriteriaParms<Entity, Criteria> {
        private static Map<Class, CriteriaClazz> criteriaClazzCache = new HashMap<Class, CriteriaClazz>();
        private final Entity entity;
        private final Criteria criteria;
        private final CriteriaClazz criteriaClazz;

        public UpdateByCriteriaParms(Entity entity, Criteria criteria) {
            this.entity = entity;
            this.criteria = criteria;
            // 加快访问速度，criteria不做Null判断，有错误立刻抛出。
            CriteriaClazz criteriaClazz0 = criteriaClazzCache.get(criteria.getClass());
            if (criteriaClazz0 != null) {
                this.criteriaClazz = criteriaClazz0;
            } else {
                this.criteriaClazz = new CriteriaClazz(criteria.getClass());
                criteriaClazzCache.put(criteria.getClass(), this.criteriaClazz);
            }
        }

        /** 必须为getRecord */
        public Entity getRecord() {
            return entity;
        }

        public String getOrderByClause() {
            return (String) ReflectionUtils.getField(criteriaClazz.orderByClauseField, criteria);
        }

        public List getOredCriteria() {
            return (List) ReflectionUtils.getField(criteriaClazz.oredCriteriaField, criteria);
        }

        public boolean isDistinct() {
            return (Boolean) ReflectionUtils.getField(criteriaClazz.distinctField, criteria);
        }

    }
}
