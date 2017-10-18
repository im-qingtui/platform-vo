package im.qingtui.platform.hibernate.dao;

import java.io.Serializable;
import java.util.List;

/**
 * 基于Hibernate的数据访问接口，里面包含最常用也最原子性的实体对象操作，所有业务层的数据访问接口都可以继承该类的实现类BaseDaoImpl
 * 没有提供dbId的方法则通过获取defaultDB执行，前提是设置全局的defaultDB。
 *
 * @param <T> 被操作的实体类
 * @param <ID> 被操作的实体类的ID
 * @version 2014-3-3
 */
public interface BaseDao<T, ID extends Serializable> {

    /**
     * 保存实体至默认DB
     *
     * @param entity 要操作的实体
     */
    public void save(T entity);

    /**
     * 保存实体至dbId对应的数据库
     *
     * @param dbId 要操作的数据库Id
     * @param entity 要操作的实体
     */
    public void save(String dbId, T entity);

    /**
     * 删除默认DB的实体
     *
     * @param entity 要操作的实体
     */
    public void delete(T entity);

    /**
     * 删除指定DB的实体
     *
     * @param dbId 要操作的数据库Id
     * @param entity 要操作的实体
     */
    public void delete(String dbId, T entity);

    /**
     * 根据实体类和id删除默认DB的实体
     *
     * @param entityClass 要操作的实体类 要操作的实体类
     * @param id 实体ID
     */
    public void deleteById(Class<T> entityClass, ID id);

    /**
     * 根据实体类和id删除指定DB的实体
     *
     * @param dbId 要操作的数据库Id
     * @param entityClass 要操作的实体类
     * @param id 实体ID
     */
    public void deleteById(String dbId, Class<T> entityClass, ID id);

    /**
     * 更新默认DB的实体
     *
     * @param entity 要操作的实体
     */
    public void update(T entity);

    /**
     * 更新指定DB的实体
     *
     * @param dbId 要操作的数据库Id
     * @param entity 要操作的实体
     */
    public void update(String dbId, T entity);

    /**
     * 根据实体类和id获取默认DB的实体
     *
     * @param entityClass 要操作的实体类
     * @param id 实体ID
     * @return 实体对象
     */
    public T getById(Class<T> entityClass, ID id);

    /**
     * 根据实体类和id获取指定DB的实体
     *
     * @param dbId 要操作的数据库Id
     * @param entityClass 要操作的实体类
     * @param id 实体ID
     * @return 实体对象
     */
    public T getById(String dbId, Class<T> entityClass, ID id);

    /**
     * 根据实体类获取默认DB的所有实体
     *
     * @param entityClass 要操作的实体类
     * @return 实体对象列表
     */
    public List<T> getAll(Class<T> entityClass);

    /**
     * 根据实体类获取指定DB的所有实体
     *
     * @param dbId 要操作的数据库Id
     * @param entityClass 要操作的实体类
     * @return 实体对象列表
     */
    public List<T> getAll(String dbId, Class<T> entityClass);
}