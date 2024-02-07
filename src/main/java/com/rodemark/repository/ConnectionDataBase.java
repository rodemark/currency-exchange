package com.rodemark.repository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionDataBase {
    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get("src/main/resources/DataBase/database.properties"))){
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String password = props.getProperty("jdbc.password");

        return DriverManager.getConnection(url, username, password);
    }
}
