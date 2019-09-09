package com.turing.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    int id;
    String name;
    String password;
    UserType userType;

    public User() {}

    public User(int id) {
        this.id = id;
    }

    public User(String name, String password, UserType userType) {
        this.name = name;
        this.userType = userType;
        this.password = password;
    }

    public User(int id, String name, String password, UserType userType) {
        this.id = id;
        this.name = name;
        this.userType = userType;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static User parseUser(ResultSet rt) throws SQLException {
        User user = new User();

        user.setId(rt.getInt("id"));
        user.setName(rt.getString("name"));
        user.setPassword(rt.getString("password"));
//        System.out.println(UserType.fromString(rt.getString("usertype")));
        user.setUserType(UserType.fromString(rt.getString("usertype")));

        return user;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                '}';
    }
}
