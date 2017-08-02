package service.Impl;

import entity.UserEntity;
import java.util.List;
import mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.UserService;

/**
 * Created by sunny on 16/7/26.
 */
//@Transactional
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public void saveUser(UserEntity user) {
        userMapper.saveUser(user);
    }

    public List<UserEntity> getUserList() {
        return userMapper.getUserList();
    }

}
