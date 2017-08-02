package im.qingtui.platform.hibernate.datamng.impl;

import im.qingtui.platform.hibernate.dao.TemplateFactory;
import im.qingtui.platform.hibernate.datamng.BaseEntityMng;
import im.qingtui.platform.hibernate.model.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("unchecked")
@Service("baseEntityMng")
@Transactional
public class BaseEntityMngImpl implements BaseEntityMng {

    protected HibernateTemplate getHibernateTemplate(String dbId) {
        return TemplateFactory.getHibernateTemplate(dbId);
    }

    protected Session getSession(String dbId) {
        return getHibernateTemplate(dbId).getSessionFactory().getCurrentSession();
    }

    @Override
    public Serializable saveObj(Entity r) throws Exception {
        return saveObj(null, r);
    }

    @Override
    public Serializable saveObj(String dbId, Entity r) throws Exception {
        return this.getHibernateTemplate(dbId).save(r);
    }

    @Override
    public void updateObj(Entity r) throws Exception {
        updateObj(null, r);
    }

    @Override
    public void updateObj(String dbId, Entity r) throws Exception {
        this.getHibernateTemplate(dbId).update(r);
    }

    @Override
    public void saveOrUpdateObj(Entity r) throws Exception {
        saveOrUpdateObj(null, r);
    }

    @Override
    public void saveOrUpdateObj(String dbId, Entity r) throws Exception {
        this.getHibernateTemplate(dbId).saveOrUpdate(r);
    }

    @Override
    public <T extends Entity> List<T> getAllObj(String className) {
        return getAllObj(null, className);
    }

    @Override
    public <T extends Entity> List<T> getAllObj(String dbId, String className) {
        Query q = this.getSession(dbId).createQuery("from " + className);
        return q.list();
    }

    @Override
    public <T extends Entity> List<T> getAllObj(Class<T> classType) {
        return getAllObj(classType.getSimpleName());
    }

    @Override
    public <T extends Entity> List<T> getAllObj(String dbId, Class<T> classType) {
        return getAllObj(dbId, classType.getSimpleName());
    }

    @Override
    public Long getAllObjCount(String className) {
        return getAllObjCount(null, className);
    }

    @Override
    public Long getAllObjCount(String dbId, String className) {
        String sql = "select count(*) from " + className;
        Query query = this.getSession(dbId).createQuery(sql);
        @SuppressWarnings("rawtypes")
        List l = query.list();
        return (Long) l.get(0);
    }

    @Override
    public <T extends Entity> Long getAllObjCount(Class<T> classType) {
        return getAllObjCount(null, classType.getSimpleName());
    }

    @Override
    public <T extends Entity> Long getAllObjCount(String dbId, Class<T> classType) {
        return getAllObjCount(dbId, classType.getSimpleName());
    }

    @Override
    public <T extends Entity> List<T> queryhql(String hql, List<Object> param) throws Exception {
        return queryhql(null, hql, param);
    }

    @Override
    public <T extends Entity> List<T> queryhql(String dbId, String hql, List<Object> param) throws Exception {
        Session session = getSession(dbId);
        Query hqlquery = session.createQuery(hql);
        if (param != null) {
            for (int i = 0; i < param.size(); i++) {
                hqlquery.setParameter(i, param.get(i));
            }
        }
        return (List<T>) hqlquery.list();
    }

    @Override
    public <T extends Entity> List<T> querySql(String sql, List<Object> param, Class<T> classType)
        throws Exception {
        return querySql(null, sql, param, classType);
    }

    @Override
    public <T extends Entity> List<T> querySql(String dbId, String sql, List<Object> param, Class<T> classType)
        throws Exception {
        Session session = getSession(dbId);
        SQLQuery sqlquery = session.createSQLQuery(sql);
        if (param != null) {
            for (int i = 0; i < param.size(); i++) {
                sqlquery.setParameter(i, param.get(i));
            }
        }
        List<T> list = null;
        list = sqlquery.setResultTransformer(Transformers.aliasToBean(classType)).list();
        return list;
    }

    @Override
    public List<Map<String, Object>> querySql(String sql, List<Object> param) throws Exception {
        return querySql(null, sql, param);
    }

    @Override
    public List<Map<String, Object>> querySql(String dbId, String sql, List<Object> param) throws Exception {
        Session session = getSession(dbId);
        Query sqlquery = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        if (param != null) {
            for (int i = 0; i < param.size(); i++) {
                sqlquery.setParameter(i, param.get(i));
            }
        }
        List<Map<String, Object>> list = sqlquery.list();
        return list;
    }

    @Override
    public List<Map<String, Object>> querySqlToLowerCase(String sql, List<Object> param) throws Exception {
        return querySqlToLowerCase(null, sql, param);
    }

    @Override
    public List<Map<String, Object>> querySqlToLowerCase(String dbId, String sql, List<Object> param)
        throws Exception {
        List<Map<String, Object>> list = querySql(dbId, sql, param);
        return toLowerCase(list);
    }

    @Override
    public Map<String, Object> querySqlSol(String sql, List<Object> param) throws Exception {
        return querySqlSol(null, sql, param);
    }

    @Override
    public Map<String, Object> querySqlSol(String dbId, String sql, List<Object> param) throws Exception {
        Session session = getSession(dbId);
        Query sqlquery = session.createSQLQuery(sql);
        if (param != null) {
            for (int i = 0; i < param.size(); i++) {
                sqlquery.setParameter(i, param.get(i));
            }
        }
        return (Map<String, Object>) sqlquery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
    }

    @Override
    public int querySqlUD(String sql, List<Object> param) throws Exception {
        return querySqlUD(null, sql, param);
    }

    @Override
    public int querySqlUD(String dbId, String sql, List<Object> param) throws Exception {
        Session session = getSession(dbId);
        SQLQuery sqlquery = session.createSQLQuery(sql);
        if (param != null) {
            for (int i = 0; i < param.size(); i++) {
                sqlquery.setParameter(i, param.get(i));
            }
        }
        return sqlquery.executeUpdate();
    }

    /**
     * 将map的键转换小写
     */
    protected List<Map<String, Object>> toLowerCase(List<Map<String, Object>> list) {
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        Map<String, Object> _map;
        for (Map<String, Object> map : list) {
            _map = new HashMap<String, Object>();
            for (String key : map.keySet()) {
                _map.put(key.toString().toLowerCase(), map.get(key));
            }
            results.add(_map);
        }
        return results;
    }

    @Override
    public void deleteObj(Entity r) throws Exception {
        deleteObj(null, r);

    }

    @Override
    public void deleteObj(String dbId, Entity r) throws Exception {
        this.getHibernateTemplate(dbId).delete(r);
    }

    @Override
    public <T extends Entity> List<T> getPagedObj(Class<T> classType, int firstRow, int pageSize) {
        return getPagedObj(null, classType, firstRow, pageSize);
    }

    @Override
    public <T extends Entity> List<T> getPagedObj(String dbId, Class<T> classType, int firstRow, int pageSize) {
        Query q = null;
        q = this.getSession(dbId).createQuery("from " + classType.getSimpleName());
        if (pageSize != -1) {
            q.setFirstResult(firstRow);
            q.setMaxResults(pageSize);
        }
        return q.list();
    }

    @Override
    public Long getAllObjCountByCondition(String className, String condition) {
        return getAllObjCountByCondition(null, className, condition);
    }

    @Override
    public Long getAllObjCountByCondition(String dbId, String className, String condition) {
        String conditionStr = condition == null ? "" : (" as obj where 1=1 and " + condition);
        String queryString = "select count(*) from " + className + conditionStr;
        Query query = this.getSession(dbId).createQuery(queryString);
        @SuppressWarnings("rawtypes")
        List l = query.list();
        long r = (Long) l.get(0);
        return r;
    }

    @Override
    public <T extends Entity> Long getAllObjCountByCondition(Class<T> classType, String condition) {
        return getAllObjCountByCondition(classType.getSimpleName(), condition);
    }

    @Override
    public <T extends Entity> Long getAllObjCountByCondition(String dbId, Class<T> classType, String condition) {
        return getAllObjCountByCondition(dbId, classType.getSimpleName(), condition);
    }

    @Override
    public <T extends Entity> T getSingleObjByHql(String hql, List<Object> param) throws Exception {
        return getSingleObjByHql(null, hql, param);
    }

    @Override
    public <T extends Entity> T getSingleObjByHql(String dbId, String hql, List<Object> param) throws Exception {
        List<T> list = queryhql(dbId, hql, param);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public <T extends Entity> List<T> getPagedObjByCondition(Class<T> classType, int firstRow, int pageSize,
        String condition) throws Exception {
        return getPagedObjByCondition(null, classType, firstRow, pageSize, condition);
    }

    @Override
    public <T extends Entity> List<T> getPagedObjByCondition(String dbId, Class<T> classType, int firstRow,
        int pageSize, String condition) throws Exception {
        Query q = null;
        if (condition == null || condition.trim().equals("")) {
            q = this.getSession(dbId).createQuery("from " + classType.getSimpleName());
        } else {
            q =
                this.getSession(dbId).createQuery(
                    "from " + classType.getSimpleName() + " as obj where 1=1 and " + parseCondition(condition));
        }

        if (pageSize != -1) {
            q.setFirstResult(firstRow);
            q.setMaxResults(pageSize);
        }
        return q.list();
    }

    @Override
    public <T extends Entity> List<T> getPagedObjOrdered(String dbId, String className, String condition,
        int firstRow, int pageSize, String order) {
        Query q = null;
        if (condition == null || condition.trim().equals("")) {
            if (order == null || order.trim().equals("")) {
                q = this.getSession(dbId).createQuery("from " + className + " as obj");
            } else {
                q = this.getSession(dbId).createQuery("from " + className + " as obj " + order);
            }
        } else {
            if (order == null || order.trim().equals("")) {
                q = this.getSession(dbId).createQuery("from " + className + " as obj where 1=1 and " + condition);
            } else {
                q =
                    this.getSession(dbId).createQuery(
                        "from " + className + " as obj where 1=1 and " + condition + " " + order);
            }
        }

        if (pageSize != -1) {
            q.setFirstResult(firstRow);
            q.setMaxResults(pageSize);
        }
        return q.list();
    }

    @Override
    public List<Map<String, Object>> queryForSqlInDBlink(String dbId, String sql, String dbLinkName) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Session session = getHibernateTemplate(dbId).getSessionFactory().getCurrentSession();
        list = session.createSQLQuery(sql).list();
        session.createSQLQuery("Alter session close database link " + dbLinkName.toUpperCase());
        return list;
    }

    protected String parseCondition(String condition) {
        if (condition == null) {
            return null;
        }
        if (condition.trim().startsWith("and ")) {
            return condition.replaceFirst("\\s*and\\s", " ");
        } else {
            return condition;
        }
    }

}
