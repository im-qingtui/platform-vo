package im.qingtui.platform.hibernate.datamng.impl;

import im.qingtui.platform.hibernate.datamng.BaseDataMng;
import im.qingtui.platform.hibernate.model.Entity;
import im.qingtui.platform.hibernate.model.Root;
import im.qingtui.platform.utils.UuidUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
@Transactional
@Service("baseDataMng")
public class BaseDataMngImpl extends BaseEntityMngImpl implements BaseDataMng {

    @Override
    public <T extends Root> T getObj(String id, String className) {
        return (T) this.getObjByCondition(null, className, "obj.id='" + id + "'");
    }

    @Override
    public <T extends Root> T getObj(String dbId, String id, String className) {
        return (T) this.getObjByCondition(dbId, className, "obj.id='" + id + "'");
    }

    @Override
    public String saveObj(Root r) throws Exception {
        return saveObj(null, r);
    }

    @Override
    public String saveObj(String dbId, Root r) throws Exception {
        if (r.getId() == null)
            r.setId(UuidUtils.getUUID());
        long now = System.currentTimeMillis();
        r.setCreateTime(now);
        r.setUpdateTime(now);
        return (String) this.getHibernateTemplate(dbId).save(r);
    }

    @Override
    public void updateObj(Root r) throws Exception {
        updateObj(null, r);
    }

    @Override
    public void updateObj(String dbId, Root r) throws Exception {
        r.setUpdateTime(System.currentTimeMillis());
        this.getHibernateTemplate(dbId).update(r);
    }

    @Override
    public void deleteObj(String id, String className) throws Exception {
        deleteObj(null, id, className);
    }

    @Override
    public void deleteObj(String dbId, String id, String className) throws Exception {
        Set<String> set = new HashSet<String>();
        deleteObj(dbId, id, className, set, true);
    }

    private void deleteObj(String dbId, String id, String className, Set<String> set, Boolean editConstrain) {
        Root r = this.getObj(dbId, id, className);
        if (r == null) {
            return;
        }
        this.getHibernateTemplate(dbId).delete(r);
    }

    @Override
    public <T extends Root> List<T> getPagedObj(String dbId, String className, String condition, int firstRow,
                                                int pageSize) {
        Query q = null;
        if (condition == null || condition.trim().equals("")) {
            q = this.getSession(dbId).createQuery("from " + className + " as obj order by obj.createTime desc");
        } else
            q = this.getSession(dbId).createQuery(
                    "from " + className + " as obj where 1=1 and " + condition + " order by obj.createTime desc");

        if (pageSize != -1) {
            q.setFirstResult(firstRow);
            q.setMaxResults(pageSize);
        }
        return q.list();
    }

    @Override
    public <T extends Root> T getObjByCondition(String className, String condition) {
        return (T) getObjByCondition(null, className, condition);
    }

    @Override
    public <T extends Root> List<T> getObjListByCondition(String dbId, String className, String condition) {
        String queryString = "";
        if (condition != null && !condition.trim().equals("")) {
            queryString = "from " + className + " as obj where 1=1 and " + condition + " order by obj.createTime desc";
        } else {
            queryString = "from " + className + " as obj " + " order by obj.createTime desc";
        }

        Query q = this.getSession(dbId).createQuery(queryString);
        return q.list();
    }

    @Override
    public <T extends Entity> List<T> getPagedObjOrdered(String dbId, String className, String condition, int firstRow,
                                                         int pageSize, String order) {
        Query q = null;
        if (condition == null || condition.trim().equals("")) {
            if (order == null || order.trim().equals("")) {
                q = this.getSession(dbId).createQuery("from " + className + " as obj order by obj.createTime desc");
            } else {
                q = this.getSession(dbId).createQuery("from " + className + " as obj " + order);
            }
        } else {
            if (order == null || order.trim().equals("")) {
                q = this.getSession(dbId).createQuery(
                        "from " + className + " as obj where 1=1 and " + condition + " order by obj.createTime desc");
            } else {
                q = this.getSession(dbId).createQuery(
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
    public <T extends Root> T getObjByCondition(String dbId, String className, String condition) {
        String queryString = "";
        if (condition != null && !condition.trim().equals("")) {
            queryString = "from " + className + " as obj where 1=1 and (" + condition + ")"
                    + " order by obj.createTime desc";
        } else {
            queryString = "from " + className + " as obj where 1=1 " + " order by obj.createTime desc";
        }
        Session s = this.getSession(dbId);
        Query q = s.createQuery(queryString);
        List<Root> list = q.list();
        if (list != null && list.size() > 0) {
            return (T) list.get(0);
        }
        return null;
    }

    @Override
    public <T extends Root> List<T> getObjListByCondition(String className, String condition) {
        return getObjListByCondition(null, className, condition);
    }

    @Override
    public <T extends Root> List<T> getPagedObj(String className, String condition, int firstRow, int pageSize) {
        return getPagedObj(null, className, condition, firstRow, pageSize);
    }

    @Override
    public <T extends Root> List<T> getPagedObjOrdered(String className, String condition, int firstRow, int pageSize,
                                                       String order) {
        return getPagedObjOrdered(null, className, condition, firstRow, pageSize, order);
    }

    @Override
    public Long getAllObjCount(String className) {
        return getAllObjCount(null, className);
    }

    @Override
    public Long getAllObjCount(String dbId, String className) {
        return getAllObjCountByCondition(dbId, className, null);
    }

    @Override
    public <T extends Root> T getObjByName(String name, Class<T> classType) {
        Session session = getSession(null);
        List<Root> list = (List<Root>) session.createCriteria(classType).add(Restrictions.eq("name", name)).list();
        if (list != null && list.size() > 0) {
            return (T) list.get(0);
        }
        return null;
    }

    @Override
    public <T extends Root> T getObjByName(String dbId, String name, Class<T> classType) {
        Session session = getSession(dbId);
        List<Root> list = (List<Root>) session.createCriteria(classType).add(Restrictions.eq("name", name)).list();
        if (list != null && list.size() > 0) {
            return (T) list.get(0);
        }
        return null;
    }

    @Override
    public void saveOrUpdateObj(Root r) throws Exception {
        saveOrUpdateObj(null, r);
    }

    @Override
    public void saveOrUpdateObj(String dbId, Root r) throws Exception {
        long now = System.currentTimeMillis();
        if (r.getId() == null) {
            r.setId(UuidUtils.getUUID());
            r.setCreateTime(now);
        }
        r.setUpdateTime(now);
        this.getHibernateTemplate(dbId).saveOrUpdate(r);
    }

    @Override
    public String getObjIdByName(String objName, Class<? extends Root> className) throws Exception {
        return getObjIdByName(null, objName, className);
    }

    @Override
    public String getObjIdByName(String dbId, String objName, Class<? extends Root> className) throws Exception {
        Session session = getSession(dbId);
        List<Root> list = (List<Root>) session.createCriteria(className).add(Restrictions.eq("name", objName)).list();
        if (list != null && list.size() > 0) {
            return list.get(0).getId();
        }
        return null;
    }

    @Override
    public <T extends Root> T getObj(String id, Class<T> classType) {
        return getObj(id, classType.getSimpleName());
    }

    @Override
    public <T extends Root> T getObj(String dbId, String id, Class<T> classType) {
        return getObj(dbId, id, classType.getSimpleName());
    }

    @Override
    public <T extends Root> void deleteObj(String id, Class<T> classType) throws Exception {
        deleteObj(id, classType.getSimpleName());
    }

    @Override
    public <T extends Root> void deleteObj(String dbId, String id, Class<T> classType) throws Exception {
        deleteObj(dbId, id, classType.getSimpleName());
    }

    @Override
    public <T extends Root> T getObjByCondition(Class<T> classType, String condition) {
        return getObjByCondition(classType.getSimpleName(), condition);
    }

    @Override
    public <T extends Root> T getObjByCondition(String dbId, Class<T> classType, String condition) {
        return getObjByCondition(dbId, classType.getSimpleName(), condition);
    }

    @Override
    public <T extends Root> List<T> getObjListByCondition(Class<T> classType, String condition) {
        return getObjListByCondition(classType.getSimpleName(), condition);
    }

    @Override
    public <T extends Root> List<T> getObjListByCondition(String dbId, Class<T> classType, String condition) {
        return getObjListByCondition(dbId, classType.getSimpleName(), condition);
    }

    @Override
    public <T extends Root> List<T> getPagedObj(Class<T> classType, String condition, int firstRow, int pageSize) {
        return getPagedObj(classType.getSimpleName(), condition, firstRow, pageSize);
    }

    @Override
    public <T extends Root> List<T> getPagedObj(String dbId, Class<T> classType, String condition, int firstRow,
                                                int pageSize) {
        return getPagedObj(dbId, classType.getSimpleName(), condition, firstRow, pageSize);
    }

    @Override
    public <T extends Root> List<T> getPagedObjOrdered(Class<T> classType, String condition, int firstRow,
                                                       int pageSize, String order) {
        return getPagedObjOrdered(classType.getSimpleName(), condition, firstRow, pageSize, order);
    }

    @Override
    public <T extends Root> List<T> getPagedObjOrdered(String dbId, Class<T> classType, String condition, int firstRow,
                                                       int pageSize, String order) {
        return getPagedObjOrdered(dbId, classType.getSimpleName(), condition, firstRow, pageSize, order);
    }

}