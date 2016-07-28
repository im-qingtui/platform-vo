package service;

import entity.UserEntity;

import java.util.List;

/**
 * Created by sunny on 16/7/26.
 */
public interface UserService {

    public void saveUser(UserEntity user);

    public List<UserEntity> getUserList();
}