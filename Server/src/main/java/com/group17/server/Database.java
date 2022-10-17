package com.group17.server;

import com.group17.JSONObjects.HashedUserCredentials;

import java.sql.*;

import static com.group17.server.SecurityFunctions.hashSaltFromPassword;
import static com.group17.server.SecurityFunctions.passwordsEqual;

public class Database {
    private static final String URL = "jdbc:postgresql://bronto.ewi.utwente.nl/";
    private static final String USER = "dab_pcsdb20211a_57";
    private static final String PASSWORD = "JybJIj/xE116RuOZ";
    private static final String SCHEMA = "?currentSchema=home_helper";
    private static final Connection connection = connectToDB();
    private static final String GET_USER = "SELECT to_jsonb(data) FROM (SELECT u.email, u.hashed_password, u.salt FROM home_helper.users u WHERE u.email = ?) as data;";
    private static final String INSERT_USER = "INSERT INTO users (email, hashed_password, salt) " + "VALUES (?, ?, ?) ;";
    private static final String CREATE = "CREATE SCHEMA IF NOT EXISTS safe_home; SET search_path = home_helper;\n"
            + "CREATE TABLE IF NOT EXISTS users (tid SERIAL PRIMARY KEY,"
            + "email TEXT NOT NULL UNIQUE, hashed_password VARCHAR NOT NULL, salt  VARCHAR);";


    /**
     * Returns a new database connection. Uses predefined url, user and password
     */
    public static Connection connectToDB() {
        try {
//            Class.forName("org.postgresql.Driver");
            Connection ret = DriverManager.getConnection(URL + USER + SCHEMA, USER, PASSWORD);

            // Make sure we have the tables ready
            try (PreparedStatement statement = ret.prepareStatement(CREATE)) {
                statement.executeUpdate();
            }

            System.out.println("Database connection succeeded!");

            return ret;
        } catch (SQLException e) {
            System.err.println("Failed to connect to database: " + e.getMessage());
            return null;
        }
    }

    /**
     * Registers a new user in a database
     * @param email - user's email
     * @param password - user's password
     * @param salt - random salt
     * @throws SQLException - SQL error
     */
    public static void updateUser(String email, byte[] password, byte[] salt) throws SQLException {
        try (PreparedStatement pr = connection.prepareStatement(INSERT_USER)) {
            pr.setString(1, email);
            pr.setBytes(2, password);
            pr.setBytes(3, salt);
            pr.executeUpdate();
        }
    }

    /**
     * Gets a user from a database
     * @param userName - username of a searched user
     * @return - Json object in String with username, hashed password and salt
     * @throws SQLException - sql error
     */
    public static String getUser(String userName) throws SQLException {
        try (PreparedStatement pr = connection.prepareStatement(GET_USER)) {
            pr.setString(1, userName);
            ResultSet fin = pr.executeQuery();
            if (fin.next()) {
                return fin.getString(1);
            } else {
                return "{}";
            }
        }
    }
    public static boolean checkUser(String userName, String plainPassword) throws SQLException {
        try {

            // Get JSON object with user credentials for given userName
            String credentialsString = getUser(userName);
            HashedUserCredentials credentials = new HashedUserCredentials(credentialsString);
            // Get hexadecimal representation of a salt and a password
            String saltHexStr = ((String) credentials.getSalt());
            String hashPswrdHexStr = ((String) credentials.getHashed_password());
            return passwordsEqual(plainPassword, saltHexStr, hashPswrdHexStr);
        } catch (NullPointerException nullUser) {
            System.out.println("NullUser");
            return false;
        }

    }

    public static boolean registerUser(String userName, String passwordString) throws SQLException {
        // Check if a user is in a database
        String user = getUser(userName);
        if (!user.equals("{}")) {
            return false;

        } else {
            byte[][] hashWithSalt = hashSaltFromPassword(passwordString);
            // Update database
            updateUser(userName, hashWithSalt[0], hashWithSalt[1]);
            return true;
        }
    }
}


