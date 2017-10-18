package im.qingtui.platform.mybatis.entity;

/**
 * Created by sunny on 16/7/26.
 */
public class Entity {

    private long createtime;
    private long updatetime;

    public long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(long updatetime) {
        this.updatetime = updatetime;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }
}
