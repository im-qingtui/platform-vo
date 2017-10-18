package im.qingtui.platform.hibernate.datamng;

import im.qingtui.platform.hibernate.model.Entity;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 一般通用接口<br>
 * 此接口用于一般性质的持久化接口，接口使用范围包括Entity所有子类<br>
 */
public interface BaseEntityMng {

    /**
     * 保存Obj 返回的值不确定，因为存在联合主键
     *
     * @return Obj主键，id值
     */
    public Serializable saveObj(Entity r) throws Exception;

    /**
     * 保存Obj 返回的值不确定，因为存在联合主键
     *
     * @return Obj主键，id值
     */
    public Serializable saveObj(String dbId, Entity r) throws Exception;

    /**
     * 更新Obj <br>
     * 只能对持久状态的对象进行存储 <br>
     * 如果对离线状态对象进行存储会引发错误<br>
     * 该方法可以有效的 <span style="color:red">防止空值覆盖</span>。
     */
    public void updateObj(Entity r) throws Exception;

    /**
     * 更新Obj <br>
     * 只能对持久状态的对象进行存储 <br>
     * 如果对离线状态对象进行存储会引发错误<br>
     * 该方法可以有效的 <span style="color:red">防止空值覆盖</span>。
     */
    public void updateObj(String dbId, Entity r) throws Exception;

    /**
     * 更新Obj<br>
     * 离线对象可以调用此方法进行存储或者更新<br>
     * 在持久化时首先检查主键是否存在于数据库中<br>
     * 如不存在则新增一条记录存储<br>
     * 如存在则更新这条记录，严格的字段更新，会导致空值覆盖掉数据库原有的值
     */
    public void saveOrUpdateObj(Entity r) throws Exception;

    /**
     * 更新Obj<br>
     * 离线对象可以调用此方法进行存储或者更新<br>
     * 在持久化时首先检查主键是否存在于数据库中<br>
     * 如不存在则新增一条记录存储<br>
     * 如存在则更新这条记录，严格的字段更新，会导致空值覆盖掉数据库原有的值
     */
    public void saveOrUpdateObj(String dbId, Entity r) throws Exception;

    /**
     * 删除对象
     */
    public void deleteObj(Entity r) throws Exception;

    /**
     * 删除对象
     */
    public void deleteObj(String dbId, Entity r) throws Exception;

    /**
     * 用默认数据库<br>
     * 获取className中所有Obj列表
     *
     * @param className 模型类名
     * @return 返回值包含后代实例
     */
    public <T extends Entity> List<T> getAllObj(String className);

    /**
     * 获取className中所有Obj列表
     *
     * @param className 模型类名
     * @return 返回值包含后代实例
     */
    public <T extends Entity> List<T> getAllObj(String dbId, String className);

    /**
     * 用默认数据库<br>
     * 获取className中所有Obj列表
     *
     * @param classType 模型class
     * @return 返回值包含后代实例
     */
    public <T extends Entity> List<T> getAllObj(Class<T> classType);

    /**
     * 获取className中所有Obj列表
     *
     * @param classType 模型class
     * @return 返回值包含后代实例
     */
    public <T extends Entity> List<T> getAllObj(String dbId, Class<T> classType);

    /**
     * 用默认数据库<br>
     * 获取总共记录条数
     */
    public Long getAllObjCount(String className);

    /**
     * 获取总共记录条数
     */
    public Long getAllObjCount(String dbId, String className);

    /**
     * 用默认数据库<br>
     * 获取总共记录条数
     */
    public <T extends Entity> Long getAllObjCount(Class<T> classType);

    /**
     * 获取总共记录条数
     */
    public <T extends Entity> Long getAllObjCount(String dbId, Class<T> classType);

    /**
     * <span style="color:green">使用默认数据库</span><br>
     * 执行hql <span style="color:red">查询</span>
     *
     * @param hql hql语句<br> e.g: from User
     * @param param 参数list
     * @return 后代实例 List
     */
    public <T extends Entity> List<T> queryhql(String hql, List<Object> param) throws Exception;

    /**
     * 执行hql <span style="color:red">查询</span>
     *
     * @param dbId 数据库名称
     * @param hql hql语句 e.g: from User
     * @param param 参数list
     * @return 后代实例 List
     */
    public <T extends Entity> List<T> queryhql(String dbId, String hql, List<Object> param) throws Exception;

    /**
     * <span style="color:green">使用默认数据库</span><br>
     * 执行sql <span style="color:red">查询</span>
     *
     * @param sql sql语句
     * @param param sql语句参数<br> e.g : select * from ctp_user
     * @param classType 模型class
     * @return 后代实例 List
     */
    public <T extends Entity> List<T> querySql(String sql, List<Object> param, Class<T> classType)
        throws Exception;

    /**
     * 执行sql语句 <span style="color:red">查询</span>
     *
     * @param dbId 数据源
     * @param sql sql语句
     * @param param sql语句参数 e.g : select * from ctp_user
     * @param classType 模型class
     * @return 后代实例 List
     */
    public <T extends Entity> List<T> querySql(String dbId, String sql, List<Object> param, Class<T> classType)
        throws Exception;

    /**
     * <span style="color:green">使用默认数据库</span><br>
     * 执行sql语句 <span style="color:red">查询</span>，返回Map List对象.<br>
     * <span style="color:red">注意 : </span>如果有查询的字段名字一样的需要给字段指定别名。<br>
     * 此方法一般用于查询跨多表的字段，无需另外写pojo类存数据。
     *
     * @param sql e.g : select * from ctp_user
     */
    public List<Map<String, Object>> querySql(String sql, List<Object> param) throws Exception;

    /**
     * 执行sql语句 <span style="color:red">查询</span>，返回Map List对象.<br>
     * <span style="color:red">注意 : </span>如果有查询的字段名字一样的需要给字段指定别名。<br>
     * 此方法一般用于查询跨多表的字段，无需另外写pojo类存数据。
     *
     * @param sql e.g : select * from ctp_user
     */
    public List<Map<String, Object>> querySql(String dbId, String sql, List<Object> param) throws Exception;

    /**
     * <span style="color:green">使用默认数据库</span><br>
     * 执行sql语句 <span style="color:red">查询</span>，返回Map List对象.<br>
     * <span style="color:red">注意 : </span>如果有查询的字段名字一样的需要给字段指定别名。<br>
     * 返回map的键值转换为小写,屏蔽了oracle数据库差异问题<br>
     * 此方法一般用于查询跨多表的字段，无需另外写pojo类存数据。
     *
     * @param sql e.g : select * from ctp_user
     */
    public List<Map<String, Object>> querySqlToLowerCase(String sql, List<Object> param) throws Exception;

    /**
     * 执行sql语句 <span style="color:red">查询</span>，返回Map List对象.<br>
     * <span style="color:red">注意 : </span>如果有查询的字段名字一样的需要给字段指定别名。<br>
     * 返回map的键值转换为小写,屏蔽了oracle数据库差异问题<br>
     * 此方法一般用于查询跨多表的字段，无需另外写pojo类存数据。
     *
     * @param sql e.g : select * from ctp_user
     */
    public List<Map<String, Object>> querySqlToLowerCase(String dbId, String sql, List<Object> param)
        throws Exception;

    /**
     * <span style="color:green">使用默认数据库</span><br>
     * 执行sql语句 <span style="color:red">查询</span>，返回Map对象.只返回一条记录,uniqueResult<br>
     * <span style="color:red">注意 : </span>如果有查询的字段名字一样的需要给字段指定别名。<br>
     * 如果查询出的结果不唯一，抛出错误<span
     * style="color:red">org.hibernate.NonUniqueResultException</span><br>
     * 此方法一般用于查询跨多表的字段，无需另外写pojo类存数据。
     *
     * @param sql e.g : select * from ctp_user
     */
    public Map<String, Object> querySqlSol(String sql, List<Object> param) throws Exception;

    /**
     * 执行sql语句 <span style="color:red">查询</span>，返回Map对象.只返回一条记录,uniqueResult<br>
     * <span style="color:red">注意 : </span>如果有查询的字段名字一样的需要给字段指定别名。<br>
     * 如果查询出的结果不唯一，抛出错误<span
     * style="color:red">org.hibernate.NonUniqueResultException</span><br>
     * 此方法一般用于查询跨多表的字段，无需另外写pojo类存数据。
     *
     * @param sql e.g : select * from ctp_user
     */
    public Map<String, Object> querySqlSol(String dbId, String sql, List<Object> param) throws Exception;

    /**
     * <span style="color:green">使用默认数据库</span><br>
     * 执行sql语句更新或者删除<br>
     * querySqlUpdate or Delete
     *
     * @param sql update ctp_user set name=? where id=? <br> delete ctp_user where id=?
     * @param param sql 参数的值
     * @return 操作所影响的行数
     */
    public int querySqlUD(String sql, List<Object> param) throws Exception;

    /**
     * 执行sql语句更新或者删除<br>
     * querySqlUpdate or Delete
     *
     * @param sql update ctp_user set name=? where id=? <br> delete ctp_user where id=?
     * @return 操作所影响的行数
     */
    public int querySqlUD(String dbId, String sql, List<Object> param) throws Exception;

    /**
     * 分页查询
     */
    public <T extends Entity> List<T> getPagedObj(Class<T> classType, int firstRow, int pageSize);

    /**
     * 分页查询
     */
    public <T extends Entity> List<T> getPagedObj(String dbId, Class<T> classType, int firstRow, int pageSize);

    /**
     * 分页查询 加条件
     *
     * from classType.getSimpleName() as obj where 1=1 condition
     */
    public <T extends Entity> List<T> getPagedObjByCondition(Class<T> classType, int firstRow, int pageSize,
        String condition) throws Exception;

    /**
     * 分页查询 加条件 from classType.getSimpleName() as obj where 1=1 condition
     */
    public <T extends Entity> List<T> getPagedObjByCondition(String dbId, Class<T> classType, int firstRow,
        int pageSize, String condition) throws Exception;

    /**
     * 用默认数据库<br>
     * 获取总共记录条数加上条件
     *
     * @param classType Class T
     */
    public <T extends Entity> Long getAllObjCountByCondition(Class<T> classType, String condition);

    /**
     * 用默认数据库<br>
     * 获取总共记录条数加上条件
     *
     * @param classType Class T
     */
    public <T extends Entity> Long getAllObjCountByCondition(String dbId, Class<T> classType, String condition);

    /**
     * 用默认数据库<br>
     * 获取总共记录条数加上条件
     */
    public Long getAllObjCountByCondition(String className, String condition);

    /**
     * 获取总共记录条数加上条件
     */
    public Long getAllObjCountByCondition(String dbId, String className, String condition);

    /**
     * 通过hql获取单条数据 如果数据为多条，返回第一条记录
     */
    public <T extends Entity> T getSingleObjByHql(String hql, List<Object> param) throws Exception;

    /**
     * 通过hql获取单条数据 如果数据为多条，返回第一条记录
     */
    public <T extends Entity> T getSingleObjByHql(String dbId, String hql, List<Object> param) throws Exception;

    /**
     * 获取一定条件下和经过排序的分页对象
     */
    public <T extends Entity> List<T> getPagedObjOrdered(String dbId, String className, String condition,
        int firstRow, int pageSize, String order);

    List<Map<String, Object>> queryForSqlInDBlink(String dbId, String sql, String dbLinkName);

}
