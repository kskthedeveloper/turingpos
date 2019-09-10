package com.turing.dao.user;

import com.turing.model.User;

import java.util.List;

public interface UserDao {
    int insert(User user);
    void update(User user);
    void delete(User user);
    User get(User user);
    List<User> getAll();

    User getByNameAndPassword(String username, String password);

    boolean isValidUser(String username, String password);
}
