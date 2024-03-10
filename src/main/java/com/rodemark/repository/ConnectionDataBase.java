package com.rodemark.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDataBase {
    public static Connection getConnection() throws SQLException {

        String url = "jdbc:postgresql://localhost:5432/currency_db";
        String username = "postgres";
        String password = "postgres";

        return DriverManager.getConnection(url, username, password);
    }
}
