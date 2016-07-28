package mapper;

import entity.UserEntity;

import java.util.List;

/**
 * Created by sunny on 16/7/26.
 */
public interface UserMapper {
    void saveUser(UserEntity user);

    List<UserEntity> getUserList();
}
