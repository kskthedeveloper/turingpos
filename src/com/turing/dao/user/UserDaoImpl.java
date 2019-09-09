package com.turing.dao.user;

import com.turing.dao.Dao;
import com.turing.model.User;
import com.turing.model.UserType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends Dao implements UserDao {

    @Override
    public void insert(User user) {
        try {
            PreparedStatement st = this.connection.prepareStatement("INSERT INTO USER(name,usertype,password ) VALUES (?,?,?)");

            st.setString(1, user.getName());
            st.setString(2, user.getUserType().getType());
            st.setString(3, user.getPassword());
            st.executeUpdate();
            st.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        try {
            PreparedStatement st = this.connection.prepareStatement("UPDATE user SET name=?, usertype=? WHERE id=?");
            st.setString(1, user.getName());
            st.setString(2, user.getUserType().getType());
            st.setInt(3, user.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User user) {
        try {
            Statement st =  connection.createStatement();
            st.executeUpdate("DELETE FROM user WHERE id=" + user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // to implement
    @Override
    public User get(User user) {
        User user1 = null;
        try {
            Statement st = this.connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM user WHERE id = " + user.getId());

            if(resultSet.next()) {
                user1 = User.parseUser(resultSet);
            }

            return user1;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<User>();
        try {
            Statement st = this.connection.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * from user");

            while (resultSet.next()) {
                users.add(User.parseUser(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public boolean isValidUser(String username, String password) {
        try {
            PreparedStatement st = this.connection.prepareStatement("SELECT * FROM user WHERE name=? AND password=?");
            st.setString(1, username);
            st.setString(2, password);

            ResultSet resultSet = st.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // UserDaoImpl Testing
    public static void main(String[] args) {
        UserDao userDao = new UserDaoImpl();
//        testInsertUser(userDao);
        testGetAllUser(userDao);

//        userDao.isValidUser("User5", "user5");
    }

    private static void testInsertUser(UserDao userDao) {
        User user = new User("User5", "user5", UserType.CASHIER);
        userDao.insert(user);
    }

    private static void testGetAllUser(UserDao userDao) {
        for(User user: userDao.getAll()) {
            System.out.println(user);
        }
    }

    private static void testGetUser(UserDao userDao) {
        User user = new User(1, "User1", "user2",UserType.ADMIN);
        System.out.println(userDao.get(user));
    }

    private static void testUpdateUser(UserDao userDao) {
        User user = new User(1, "User 2", "user2", UserType.CASHIER);
        userDao.update(user);
    }

    private static void testDeleteUser(UserDao userDao) {
        User user = new User(1, "User 2", "user2",UserType.CASHIER);
        userDao.delete(user);
    }


}
