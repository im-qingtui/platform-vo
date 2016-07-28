package entity;

import im.qingtui.platform.mybatis.entity.Entity;

/**
 * Created by sunny on 16/7/26.
 */
public class UserEntity extends Entity {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
