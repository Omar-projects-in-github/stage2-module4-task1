package com.mjc.stage2.impl;

import com.mjc.stage2.ConnectionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class H2ConnectionFactory implements ConnectionFactory {

    @Override
    public Connection createConnection() {
        Properties properties = loadProperties();

        String jdbcDriver = properties.getProperty("jdbc_driver");
        String databaseUrl = properties.getProperty("db_url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");

        try {
            Class.forName(jdbcDriver);
            return DriverManager.getConnection(databaseUrl, user, password);
        } catch (ClassNotFoundException | SQLException exception) {
            throw new RuntimeException("Error was occurred while connecting to the database", exception);
        }
    }

    private Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("h2database.properties")) {
            if (inputStream == null)
                throw new RuntimeException("Properties file not found in classpath");
            properties.load(inputStream);
        } catch (IOException exception) {
            throw new RuntimeException("Error while loading the properties file", exception);
        }
        return properties;
    }

    public static void main(String[] args) {
        H2ConnectionFactory factory = new H2ConnectionFactory();
        try (Connection connection = factory.createConnection()) {
            System.out.println("Successfully connected");
        } catch (Exception e) {
            System.out.println("Failed to connect: " + e.getMessage());
        }
    }
}