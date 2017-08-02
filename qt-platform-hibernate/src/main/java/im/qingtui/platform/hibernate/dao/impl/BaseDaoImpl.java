package im.qingtui.platform.hibernate.dao.impl;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class BaseDaoImpl<T, ID extends Serializable> implements im.qingtui.platform.hibernate.dao.BaseDao<T, ID> {

    public HibernateTemplate getHibernateTemplate(String dbId) {
        return im.qingtui.platform.hibernate.dao.TemplateFactory.getHibernateTemplate(dbId);
    }

    public Session getSession(String dbId) {
        return getHibernateTemplate(dbId).getSessionFactory().getCurrentSession();
    }

    @Override
    public void save(T entity) {
        save(null, entity);
    }

    @Override
    public void delete(T entity) {
        delete(null, entity);
    }

    @Override
    public void deleteById(Class<T> entityClass, ID id) {
        deleteById(null, entityClass, id);
    }

    @Override
    public void update(T entity) {
        update(null, entity);
    }

    @Override
    public T getById(Class<T> entityClass, ID id) {
        return getById(null, entityClass, id);
    }

    @Override
    public List<T> getAll(Class<T> entityClass) {
        return getAll(null, entityClass);
    }

    @Override
    public void delete(String dbId, T entity) {
        this.getHibernateTemplate(dbId).delete(entity);
    }

    @Override
    public void deleteById(String dbId, Class<T> entityClass, ID id) {
        this.getHibernateTemplate(dbId).delete(getById(entityClass, id));
    }

    @Override
    public T getById(String dbId, Class<T> entityClass, ID id) {
        return (T) this.getHibernateTemplate(dbId).get(entityClass, id);
    }

    @Override
    public List<T> getAll(String dbId, Class<T> entityClass) {
        return this.getHibernateTemplate(dbId).loadAll(entityClass);
    }

    @Override
    public void save(String dbId, T entity) {
        this.getHibernateTemplate(dbId).save(entity);
    }

    @Override
    public void update(String dbId, T entity) {
        this.getHibernateTemplate(dbId).update(entity);
    }
}