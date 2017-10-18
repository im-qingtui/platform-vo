package im.qingtui.platform.hibernate.datamng;

import im.qingtui.platform.hibernate.model.Root;
import java.util.List;

/**
 * 该接口适用于继承自Root的对象持久化操作<br>
 */
public interface BaseDataMng extends BaseEntityMng {

    /**
     * 保存Obj
     *
     * @return Obj主键，id值
     */
    public String saveObj(Root r) throws Exception;

    /**
     * 保存Obj
     *
     * @return Obj主键，id值
     */
    public String saveObj(String dbId, Root r) throws Exception;

    /**
     * 更新Obj<br>
     * 离线对象可以调用此方法进行存储或者更新<br>
     * 在持久化时首先检查主键是否存在于数据库中<br>
     * 如不存在则新增一条记录存储<br>
     * 如存在则更新这条记录，严格的字段更新，会导致空值覆盖掉数据库原有的值
     */
    public void saveOrUpdateObj(Root r) throws Exception;

    /**
     * 更新Obj<br>
     * 离线对象可以调用此方法进行存储或者更新<br>
     * 在持久化时首先检查主键是否存在于数据库中<br>
     * 如不存在则新增一条记录存储<br>
     * 如存在则更新这条记录，严格的字段更新，会导致空值覆盖掉数据库原有的值
     */
    public void saveOrUpdateObj(String dbId, Root r) throws Exception;

    /**
     * 更新Obj <br>
     * 只能对持久状态的对象进行存储 <br>
     * 如果对离线状态对象进行存储会引发错误<br>
     * 该方法可以有效的 <span style="color:red">防止空值覆盖</span>。
     */
    public void updateObj(Root r) throws Exception;

    /**
     * 更新Obj <br>
     * 只能对持久状态的对象进行存储 <br>
     * 如果对离线状态对象进行存储会引发错误<br>
     * 该方法可以有效的 <span style="color:red">防止空值覆盖</span>。
     */
    public void updateObj(String dbId, Root r) throws Exception;

    /**
     * 通过ID获取Obj
     */
    public <T extends Root> T getObj(String id, String className);

    /**
     * 通过ID获取Obj
     */
    public <T extends Root> T getObj(String dbId, String id, String className);

    /**
     * 用默认数据源<br>
     * 获取Root及其子类对象实例从数据库中
     */
    public <T extends Root> T getObj(String id, Class<T> classType);

    /**
     * 获取Root及其子类对象实例从数据库中
     */
    public <T extends Root> T getObj(String dbId, String id, Class<T> classType);

    /**
     * 用默认数据源<br>
     * 删除Obj
     */
    public void deleteObj(String id, String className) throws Exception;

    /**
     * 删除Obj
     */
    public void deleteObj(String dbId, String id, String className) throws Exception;

    /**
     * 用默认数据源<br>
     * 删除Obj
     */
    public <T extends Root> void deleteObj(String id, Class<T> classType) throws Exception;

    /**
     * 删除Obj
     */
    public <T extends Root> void deleteObj(String dbId, String id, Class<T> classType) throws Exception;

    /**
     * 用默认数据库<br>
     * 按照condition获取一个Obj
     *
     * @param className 模型类名
     * @param condition 对象名位obj,过滤条件 例如：obj.attr=1 and obj.name='abc'
     * @return 返回值包含后代实例
     */
    public <T extends Root> T getObjByCondition(String className, String condition);

    /**
     * 按照condition获取一个Obj
     *
     * @param className 模型类名
     * @param condition 对象名位obj,过滤条件 例如：obj.attr=1 and obj.name='abc'
     * @return 返回值包含后代实例
     */
    public <T extends Root> T getObjByCondition(String dbId, String className, String condition);

    /**
     * 用默认数据库<br>
     * 按照condition获取一个Obj
     *
     * @param classType 模型class <br> ex Root.class
     * @param condition 对象名位obj,过滤条件 例如：obj.attr=1 and obj.name='abc'
     * @return 返回值包含后代实例
     */
    public <T extends Root> T getObjByCondition(Class<T> classType, String condition);

    /**
     * 按照condition获取一个Obj
     *
     * @param classType 模型class <br> ex Root.class
     * @param condition 对象名位obj,过滤条件 例如：obj.attr=1 and obj.name='abc'
     * @return 返回值包含后代实例
     */
    public <T extends Root> T getObjByCondition(String dbId, Class<T> classType, String condition);

    /**
     * 用默认数据库<br>
     * 按照condition获取Obj列表
     *
     * @param className 模型类名
     * @param condition 对象名位obj,过滤条件 例如：obj.attr=1 and obj.name='abc'
     * @return 返回值包含后代实例
     */
    public <T extends Root> List<T> getObjListByCondition(String className, String condition);

    /**
     * 按照condition获取Obj列表
     *
     * @param className 模型类名
     * @param condition 对象名位obj,过滤条件 例如：obj.attr=1 and obj.name='abc'
     * @return 返回值包含后代实例
     */
    public <T extends Root> List<T> getObjListByCondition(String dbId, String className, String condition);

    /**
     * 用默认数据库<br>
     * 按照condition获取Obj列表
     *
     * @param classType 模型class
     * @param condition 对象名位obj,过滤条件 例如：obj.attr=1 and obj.name='abc'
     * @return 返回值包含后代实例
     */
    public <T extends Root> List<T> getObjListByCondition(Class<T> classType, String condition);

    /**
     * 按照condition获取Obj列表
     *
     * @param classType 模型class
     * @param condition 对象名位obj,过滤条件 例如：obj.attr=1 and obj.name='abc'
     * @return 返回值包含后代实例
     */
    public <T extends Root> List<T> getObjListByCondition(String dbId, Class<T> classType, String condition);

    /**
     * 用默认数据库<br>
     * 获取所有的Obj
     *
     * @param className 模型类名
     * @param firstRow 返回第一行位置
     * @param condition 对象名位obj,过滤条件 例如：obj.attr=1 and obj.name='abc'
     * @return 返回值不包含后代实例
     */
    public <T extends Root> List<T> getPagedObj(String className, String condition, int firstRow, int pageSize);

    /**
     * 获取所有的Obj
     *
     * @param className 模型类名
     * @param firstRow 返回第一行位置
     * @param condition 对象名位obj,过滤条件 例如：obj.attr=1 and obj.name='abc'
     * @return 返回值不包含后代实例
     */
    public <T extends Root> List<T> getPagedObj(String dbId, String className, String condition, int firstRow,
        int pageSize);

    /**
     * 用默认数据库<br>
     * 获取所有的Obj
     *
     * @param classType 模型class
     * @param firstRow 返回第一行位置
     * @param condition 对象名位obj,过滤条件 例如：obj.attr=1 and obj.name='abc'
     * @return 返回值不包含后代实例
     */
    public <T extends Root> List<T> getPagedObj(Class<T> classType, String condition, int firstRow, int pageSize);

    /**
     * 获取所有的Obj
     *
     * @param classType 模型class
     * @param firstRow 返回第一行位置
     * @param condition 对象名位obj,过滤条件 例如：obj.attr=1 and obj.name='abc'
     * @return 返回值不包含后代实例
     */
    public <T extends Root> List<T> getPagedObj(String dbId, Class<T> classType, String condition, int firstRow,
        int pageSize);

    /**
     * 使用默认数据库<br>
     * 获取所有的Obj
     *
     * @param className 模型类名
     * @param firstRow 返回第一行位置
     * @param condition 对象名位obj,过滤条件 例如：obj.attr=1 and obj.name='abc'
     * @return 返回值不包含后代实例
     */
    public <T extends Root> List<T> getPagedObjOrdered(String className, String condition, int firstRow, int pageSize,
        String order);


    /**
     * 使用默认数据库<br>
     * 获取所有的Obj
     *
     * @param classType 模型class
     * @param firstRow 返回第一行位置
     * @param condition 对象名位obj,过滤条件 例如：obj.attr=1 and obj.name='abc'
     * @return 返回值不包含后代实例
     */
    public <T extends Root> List<T> getPagedObjOrdered(Class<T> classType, String condition, int firstRow,
        int pageSize, String order);

    /**
     * 获取所有的Obj
     *
     * @param classType 模型class
     * @param firstRow 返回第一行位置
     * @param condition 对象名位obj,过滤条件 例如：obj.attr=1 and obj.name='abc'
     * @return 返回值不包含后代实例
     */
    public <T extends Root> List<T> getPagedObjOrdered(String dbId, Class<T> classType, String condition, int firstRow,
        int pageSize, String order);

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
     * 获取className中所有指定name属性的Obj列表
     *
     * @param classType 模型Class
     * @param name name属性的值
     * @return 返回值包含后代实例
     */
    public <T extends Root> T getObjByName(String name, Class<T> classType);

    /**
     * 获取className中所有指定name属性的Obj列表
     *
     * @param classType 模型Class
     * @param name 模型名字属性的值
     * @return 返回值包含后代实例
     */

    public <T extends Root> T getObjByName(String dbId, String name, Class<T> classType);

    /**
     * <span style="color:green">使用默认数据库</span><br>
     * 通过对象name属性获取对象的id
     *
     * @param objName name属性
     * @param className 对象类名
     */
    public String getObjIdByName(String objName, Class<? extends Root> className) throws Exception;

    /**
     * 通过对象name属性获取对象的id
     *
     * @param objName name属性
     * @param className 对象类名
     */
    public String getObjIdByName(String dbId, String objName, Class<? extends Root> className) throws Exception;
}