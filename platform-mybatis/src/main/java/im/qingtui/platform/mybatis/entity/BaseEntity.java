package im.qingtui.platform.mybatis.entity;

import lombok.Data;

/**
 * @author bowen
 */
@Data
public class BaseEntity {

    private long id;
    private long createTime;
    private long modefyTime;

    public BaseEntity() {
        long currentTime = System.currentTimeMillis();
        createTime = currentTime;
        modefyTime = currentTime;
    }

}
