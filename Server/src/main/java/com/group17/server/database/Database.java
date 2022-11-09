package com.group17.server.database;

import java.sql.*;

import static com.group17.server.database.DatabaseCredentials.PASSWORD;
import static com.group17.server.database.DatabaseCredentials.USER;
import static com.group17.server.database.Queries.*;

public class Database {
    static final String URL = "jdbc:postgresql://bronto.ewi.utwente.nl/";
    static final String SCHEMA = "?currentSchema=safe_home";
    private static final Connection connection = connectToDB();

    /**
     * Returns a new database connection. Uses predefined url, user and password
     */
    public static Connection connectToDB() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection ret = DriverManager.getConnection(URL + USER + SCHEMA, USER, PASSWORD);

            // Make sure we have the tables ready

            try (PreparedStatement statement = ret.prepareStatement(CREATE)) {
                statement.executeUpdate();
            }

            System.out.println("Database connection succeeded!");

            return ret;
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            return null;
        }
    }

    public static int update(String query, String... params) throws SQLException {
        assert connection != null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setString(i + 1, params[i]);
            }
            return preparedStatement.executeUpdate();
        }
    }

    public static void update(String query, byte[][] bytesParams, String[] stringParams) throws SQLException {
        assert connection != null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            int index = 0;
            for (int i = 0; i < bytesParams.length; i++) {
                preparedStatement.setBytes(i + 1, bytesParams[i]);
                index = i + 1;
            }
            for (int i = 0; i < stringParams.length; i++) {
                preparedStatement.setString(i + (index + 1), stringParams[i]);
            }
            preparedStatement.executeUpdate();
        }
    }

    public static void update(String query, boolean alert, String rpi_id) throws SQLException {
        assert connection != null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBoolean(1,alert);
            preparedStatement.setString(2,rpi_id);
            preparedStatement.executeUpdate();
        }
    }

    public static String getString(String query, String... params) throws SQLException {
        assert connection != null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setString(i + 1, params[i]);
            }
            ResultSet fin = preparedStatement.executeQuery();
            if (fin.next()) {
                return fin.getString(1);
            } else {
                return null;
            }
        }
    }
    public static boolean getBoolean(String query, String... params) throws SQLException {
        assert connection != null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setString(i + 1, params[i]);
            }
            ResultSet fin = preparedStatement.executeQuery();
            if (fin.next()) {
                return fin.getBoolean(1);
            } else {
                return false;
            }
        }
    }

    public static ResultSet getResultSet(String query, String... params) throws SQLException {
        assert connection != null;
        PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setString(i + 1, params[i]);
            }
        return preparedStatement.executeQuery();
    }
}


