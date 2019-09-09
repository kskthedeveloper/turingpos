package com.turing.service.user;

import com.turing.model.UserType;

public interface UserService {
    boolean loginUser(String name, String password);
    void registerUser(String name, String password, UserType userType);
}
