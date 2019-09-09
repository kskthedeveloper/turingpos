package com.turing.service.user;

import com.turing.dao.user.UserDao;
import com.turing.model.UserType;

public class UserServiceImpl implements UserService{
    UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean loginUser(String name, String password) {
        return this.userDao.isValidUser(name, password);
    }

    @Override
    public void registerUser(String name, String password, UserType userType) {

    }
}
