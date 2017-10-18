package service;

import entity.User;

import java.util.List;

/**
 * Created by sunny on 16/7/26.
 */
public interface UserService {

    public void saveUser(User user);

    public List<User> getUserList();
}