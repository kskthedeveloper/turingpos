package com.turing.dao;

import com.turing.connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class Dao {
    protected Connection connection;

    public Dao() {
        try {
            connection = ConnectionFactory.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
