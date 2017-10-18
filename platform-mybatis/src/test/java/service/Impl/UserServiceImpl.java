package service.Impl;

import entity.User;
import mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.UserService;

import java.util.List;

/**
 * Created by sunny on 16/7/26.
 */
//@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public void saveUser(User user) {
        userMapper.saveUser(user);
    }

    public List<User> getUserList() {
        return userMapper.getUserList();
    }

}
