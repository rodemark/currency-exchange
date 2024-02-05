package com.rodemark.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDataBase {
    public static Connection connect(){
        String jdbcUrl = "jdbc:postgresql://localhost:5432/currency_db";
        String username = "postgres";
        String password = "postgres";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("Успешное подключение к базе данных PostgreSQL!");
            return connection;
        } catch (SQLException e) {
            System.err.println("Ошибка подключения к базе данных PostgreSQL.");
            e.printStackTrace();
            return null;
        }
    }
}
