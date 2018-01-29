package entity;

import im.qingtui.platform.mybatis.entity.BaseEntity;
import lombok.Data;

/**
 * Created by sunny on 16/7/26.
 */
@Data
public class User extends BaseEntity {

    private String name;
    private String password;

}
