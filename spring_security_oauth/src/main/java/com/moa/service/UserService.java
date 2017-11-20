package com.moa.service;

import com.moa.model.User;

import java.util.List;

/**
 * Created by Administrator on 2017/11/14.
 */
public interface UserService {

    User findById(long id);

    User findByName(String name);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUserById(long id);

    List<User> findAllUsers();

    void deleteAllUsers();

    public boolean isUserExist(User user);

}
