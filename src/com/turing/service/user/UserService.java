package com.turing.service.user;

import com.turing.model.User;
import com.turing.model.UserType;

import java.util.List;

public interface UserService {
    boolean loginUser(String name, String password);

    User getUser(String name, String password);

    void registerUser(String name, String password, UserType userType);

    List<User> getAll();

    User getUser();

    void setUser(User user);
}
