package com.turing.service.user;

import com.turing.dao.user.UserDao;
import com.turing.dao.user.UserDaoImpl;
import com.turing.model.User;
import com.turing.model.UserType;

import java.util.List;

public class UserServiceImpl implements UserService{
    UserDao userDao;
    User user;

    public static UserServiceImpl userService;

    private UserServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    @Override
    public boolean loginUser(String name, String password) {
        return this.userDao.isValidUser(name, password);
    }

    @Override
    public User getUser(String name, String password) {
        return this.userDao.getByNameAndPassword(name, password);
    }

    @Override
    public void registerUser(String name, String password, UserType userType) {

    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    public static UserService getUserService() {
        if(userService == null) {
            userService = new UserServiceImpl();
        }
        return userService;
    }
}
