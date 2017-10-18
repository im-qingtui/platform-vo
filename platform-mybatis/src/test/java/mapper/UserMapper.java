package mapper;

import entity.User;

import java.util.List;

/**
 * Created by sunny on 16/7/26.
 */
public interface UserMapper {

    void saveUser(User user);

    List<User> getUserList();
}
