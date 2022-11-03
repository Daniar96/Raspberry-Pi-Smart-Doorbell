package com.group17.server;

import com.group17.JSONObjects.HashedUserCredentials;
import com.group17.JSONObjects.Image;
import com.group17.JSONObjects.UserCredentials;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.group17.server.SecurityFunctions.hashSaltFromPassword;
import static com.group17.server.SecurityFunctions.passwordsEqual;

public class Database {
    private static final String URL = "jdbc:postgresql://bronto.ewi.utwente.nl/";
    private static final String USER = "dab_pcsdb20211a_57";
    private static final String PASSWORD = "JybJIj/xE116RuOZ";
    private static final String SCHEMA = "?currentSchema=safe_home";
    private static final Connection connection = connectToDB();
    private static final String GET_USER = "SELECT to_jsonb(data) FROM (SELECT u.email, u.hashed_password, u.salt FROM safe_home.users u WHERE u.email = ?) as data;";
    private static final String GET_USER_LIST = "SELECT u.email FROM safe_home.users u;";
    private static final String INSERT_USER = "INSERT INTO users (email, is_online, hashed_password, salt) " + "VALUES (?,?, ?, ?) ;";

    private static final String SET_RFID = "UPDATE users SET rfid = ? WHERE email = ?;";
    private static final String CREATE = "CREATE SCHEMA IF NOT EXISTS safe_home; SET search_path = safe_home;\n"
            + "CREATE TABLE IF NOT EXISTS users (pid SERIAL PRIMARY KEY,"
            + "email TEXT NOT NULL UNIQUE, is_online boolean, hashed_password VARCHAR NOT NULL, salt  VARCHAR, rfid VARCHAR);";


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

    public static void setRFID(String rfid, String user) throws SQLException {
        connectToDB();
        PreparedStatement pr = connection.prepareStatement(SET_RFID);
        pr.setString(1, rfid);
        pr.setString(2, user);
        pr.executeUpdate();
    }

    /**
     * Registers a new user in a database
     *
     * @param email    - user's email
     * @param password - user's password
     * @param salt     - random salt
     * @throws SQLException - SQL error
     */
    public static void updateUser(String email, byte[] password, byte[] salt) throws SQLException {
        connectToDB();
        PreparedStatement pr = connection.prepareStatement(INSERT_USER);
        pr.setString(1, email);
        pr.setBoolean(2, true);
        pr.setBytes(3, password);
        pr.setBytes(4, salt);
        pr.executeUpdate();

    }

    /**
     * Gets a user from a database
     *
     * @param userName - username of a searched user
     * @return - Json object in String with username, hashed password and salt
     * @throws SQLException - sql error
     */
    public static String getUser(String userName) throws SQLException {
        connectToDB();
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

    //TODO Bad code
    public static String getUserList() throws SQLException {
        connectToDB();
        try (PreparedStatement pr = connection.prepareStatement(GET_USER_LIST)) {
            ResultSet fin = pr.executeQuery();
            StringBuilder res = new StringBuilder();
            while (fin.next()) {
                res.append(fin.getString(1)).append(", ");
            }
            res.setLength(res.length() - 2);
            return res.toString();
        }
    }

    public static void insertImage(String name, String encode) {
        connectToDB();
        try (PreparedStatement pr = connection.prepareStatement("INSERT INTO images VALUES (?, ?)")){
            pr.setString(1, name);
            pr.setString(2, encode);
            pr.executeUpdate();
        }catch (SQLException e){

        }
    }

    public static List<Image> getImages(){
        connectToDB();
        List<Image> images = new ArrayList<>();
        try (ResultSet resultSet = connection.createStatement().executeQuery("SELECT name, encode FROM images ORDER BY name DESC")){
            while (resultSet.next()){
                String name = resultSet.getString("name");
                String encode = resultSet.getString("encode");
                Image image = new Image(name, encode);
                images.add(image);
                if (images.size() >= 10){
                    return images;
                }
            }
            return images;
        }catch (SQLException e){
            return null;
        }
    }

    public static List<UserCredentials> getUsersList() {
        connectToDB();
        List <UserCredentials> users = new ArrayList<>();
        try (ResultSet resultSet = connection.createStatement().executeQuery("Select email, is_online FROM users")) {
            while (resultSet.next()) {
                String email = resultSet.getString("email");
                boolean online = resultSet.getBoolean("is_online");
                UserCredentials user = new UserCredentials(email, online);
                users.add(user);
            }
            return users;
        }catch (SQLException e){
            return null;
        }
    }

    public static boolean checkUser(String userName, String plainPassword) throws SQLException {
        connectToDB();
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

    public static boolean checkOnline(String id) {
        connectToDB();
        try(PreparedStatement pr = connection.prepareStatement("UPDATE users SET is_online=NOT is_online WHERE rfid=?")){
            pr.setString(1, id);
            int res = pr.executeUpdate();
            return res > 0;
        } catch (SQLException e) {
            System.out.println("a");
        }
        return false;
    }

    public static void smokeAlert(boolean alert) {
        connectToDB();
        try(PreparedStatement pr = connection.prepareStatement("UPDATE smoke SET alert=?")) {
            pr.setBoolean(1, alert);
            pr.executeUpdate();
        } catch (SQLException e) {

        }
    }

    public static int getSmoke() {
        connectToDB();
        try(ResultSet resultSet = connection.createStatement().executeQuery("SELECT alert FROM smoke")){
            resultSet.next();
            boolean k = resultSet.getBoolean("alert");
            if (k)return 1; else return 0;
        }catch (SQLException e){
            return 0;
        }
    }

    public static void micAlert(boolean alert) {
        connectToDB();
        try(PreparedStatement pr = connection.prepareStatement("UPDATE mic SET alert=?")) {
            pr.setBoolean(1, alert);
            pr.executeUpdate();
        } catch (SQLException e) {

        }
    }

    public static int getMic() {
        connectToDB();
        try(ResultSet resultSet = connection.createStatement().executeQuery("SELECT alert FROM mic")){
            resultSet.next();
            boolean k = resultSet.getBoolean("alert");
            if (k)return 1; else return 0;
        }catch (SQLException e){
            return 0;
        }
    }

    public static void flameAlert(boolean alert) {
        connectToDB();
        try(PreparedStatement pr = connection.prepareStatement("UPDATE flame SET alert=?")) {
            pr.setBoolean(1, alert);
            pr.executeUpdate();
        } catch (SQLException e) {

        }
    }

    public static int getFlame() {
        connectToDB();
        try(ResultSet resultSet = connection.createStatement().executeQuery("SELECT alert FROM flame")){
            resultSet.next();
            boolean k = resultSet.getBoolean("alert");
            if (k)return 1; else return 0;
        }catch (SQLException e){
            return 0;
        }
    }

    public static boolean registerUser(String userName, String passwordString) throws SQLException {
        connectToDB();
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


