package com.turing.dao.user;

import com.turing.model.User;

import java.util.List;

public interface UserDao {
    void insert(User user);
    void update(User user);
    void delete(User user);
    User get(User user);
    List<User> getAll();
    boolean isValidUser(String username, String password);
}
