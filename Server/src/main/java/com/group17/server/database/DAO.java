package com.group17.server.database;

import com.group17.JSONObjects.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.group17.server.SecurityFunctions.hashSaltFromPassword;
import static com.group17.server.SecurityFunctions.passwordsEqual;
import static com.group17.server.database.Queries.*;

public class DAO {


    public static UserInfo getUserInfo(String email) throws SQLException {
        String result = Database.getString(GET_USER_INFO, email);
        assert result != null;
        return new UserInfo(result);
    }

    public static void setUserInfo(String email, UserInfo userInfo) throws SQLException {
        if (userInfo.getPassword() == null) {
            Database.update(SET_USER_INFO, userInfo.getFull_name(), userInfo.getUsername(), email);
        } else {
            String[] hashWithSalt = hashSaltFromPassword(userInfo.getPassword());
            assert hashWithSalt != null;
            Database.update(SET_USER_INFO_PASSWORD, hashWithSalt[0], hashWithSalt[1], userInfo.getFull_name(), userInfo.getUsername(), email);
        }
    }

    public static void addUser(String email, String[] hashWithSalt, String rfid) throws SQLException {
        //Add a new user with email = email and username = email
        Database.update(INSERT_USER, hashWithSalt[0], hashWithSalt[1], email, email, rfid);
    }

    public static HashedUserCredentials getUser(String userName) throws SQLException {
        ResultSet resultSet = Database.getResultSet(GET_USER, userName);
        if (resultSet.next()) {
            String password = resultSet.getString("hashed_password");
            String salt = resultSet.getString("salt");
            String email = resultSet.getString("email");
            return new HashedUserCredentials(email, password, salt);
        } else {
            return null;
        }
    }

    public static HashedUserCredentials getRpiPasswordSalt(String rpi_id) throws SQLException {
        ResultSet resultSet = Database.getResultSet(GET_RPI_PASSWORD_SALT, rpi_id);
        if (resultSet.next()) {
            String password = resultSet.getString("hashed_password");
            String salt = resultSet.getString("salt");
            return new HashedUserCredentials(password, salt);
        } else {
            return null;
        }
    }

    public static List<Username_AtHomeStatus> getAtHomeList(String rpi_id) throws SQLException {
        ResultSet resultSet = Database.getResultSet(GET_AT_HOME_TAGS, rpi_id);
        List<Username_AtHomeStatus> RfidAthomeList = new ArrayList<>();
        while (resultSet.next()) {
            String username = resultSet.getString("username");
            boolean online = resultSet.getBoolean("at_home");
            Username_AtHomeStatus rfidStatus = new Username_AtHomeStatus(username, online);
            RfidAthomeList.add(rfidStatus);
        }
        return RfidAthomeList;

    }

    public static boolean registerUser(String userName, String passwordPlain, String rfid) throws SQLException {
        // Check if a user is in a database
        HashedUserCredentials user = getUser(userName);
        if (user != null) {
            return false;

        } else {
            String[] hashWithSalt = hashSaltFromPassword(passwordPlain);
            // Update database
            addUser(userName, hashWithSalt, rfid);
            return true;
        }
    }

    public static boolean registerRpi(String rpi_id, String passwordPlain) throws SQLException {
        HashedUserCredentials rpi = getRpiPasswordSalt(rpi_id);
        if (rpi != null) {
            return false;

        } else {
            String[] hashWithSalt = hashSaltFromPassword(passwordPlain);
            // Update database
            Database.update(INSERT_RPI, hashWithSalt[0], hashWithSalt[1], rpi_id);
            return true;
        }

    }

    public static boolean checkUser(String userName, String plainPassword) throws SQLException {
        try {
            // Get JSON object with user credentials for given userName
            HashedUserCredentials credentials = getUser(userName);
            // Get hexadecimal representation of a salt and a password
            String saltHexStr = credentials.getSalt();
            String hashPswrdHexStr = credentials.getHashed_password();
            return passwordsEqual(plainPassword, saltHexStr, hashPswrdHexStr);
        } catch (NullPointerException nullUser) {
            return false;
        }

    }

    public static boolean checkOnline(String rfid, String rpi_id) throws SQLException {
        int switched_status = Database.update(SWITCH_AT_HOME, rfid);
        ResultSet resultSet = Database.getResultSet(GET_AT_HOME_TAG, rfid, rpi_id);
        resultSet.next();
        String name = resultSet.getString("username");
        boolean k = resultSet.getBoolean("at_home");
        if (k) {
            DAO.addLog("User " + name + " has entered the house.", rpi_id);
        } else {
            DAO.addLog("User " + name + " has left the house.", rpi_id);
        }
        return switched_status > 0;
    }

    public static void smokeAlert(boolean alert, String rpi_id) throws SQLException {
        Database.update(SET_SMOKE_ALERT, alert, rpi_id);
    }

    public static void micAlert(boolean alert, String rpi_id) throws SQLException {
        Database.update(SET_MIC_ALERT, alert, rpi_id);
    }

    public static void flameAlert(boolean alert, String rpi_id) throws SQLException {
        Database.update(SET_FLAME_ALERT, alert, rpi_id);
    }

    public static void addLog(String log, String rpi_id) throws SQLException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String date = dtf.format(now);
        Database.update(INSERT_LOG, rpi_id, date, log);
    }

    public static void addImage(String rpiId, String name, String encoding) throws SQLException {
        Database.update(INSERT_IMAGE, rpiId, name, encoding);
    }

    public static void addTemp(String temp, String humidity, String rpi_id) throws SQLException {
        Database.update(SET_TEMPERATURE, temp, humidity, rpi_id);
    }

    public static int getSmoke(String rpi_id) throws SQLException {
        return (Database.getBoolean(GET_SMOKE_ALERT, rpi_id)) ? 1 : 0;
    }

    public static int getMic(String rpi_id) throws SQLException {
        return (Database.getBoolean(GET_MIC_ALERT, rpi_id)) ? 1 : 0;
    }


    public static int getFlame(String rpi_id) throws SQLException {
        return (Database.getBoolean(GET_FLAME_ALERT, rpi_id)) ? 1 : 0;
    }

    public static Temp getTemp(String rpi_id) throws SQLException {
        ResultSet resultSet = Database.getResultSet(GET_TEMPERATURE_HUMIDITY, rpi_id);
        resultSet.next();
        String temp = resultSet.getString("temperature");

        String humidity = resultSet.getString("humidity");
        return new Temp(temp, humidity);
    }

    public static List<Image> getImages(String rpi_id) throws SQLException {
        ResultSet resultSet = Database.getResultSet(GET_IMAGES, rpi_id);
        List<Image> images = new ArrayList<>();

        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String encode = resultSet.getString("encoding");
            Image image = new Image(name, encode);
            images.add(image);
        }
        return images;
    }

    public static String getImage(String rpi_id) throws SQLException {
        ResultSet resultSet = Database.getResultSet(GET_IMAGE, rpi_id);
        resultSet.next();
        String encode = resultSet.getString("encoding");
        return encode;
    }

    public static List<Log> getLogs(String rpi_id) throws SQLException {
        ResultSet resultSet = Database.getResultSet(GET_LOGS, rpi_id);
        List<Log> logs = new ArrayList<>();
        while (resultSet.next()) {
            String date = resultSet.getString("date");
            String logstr = resultSet.getString("log");
            Log log = new Log(date, logstr);
            logs.add(log);
        }
        return logs;
    }

    public static boolean arePeopleInside(String rpi_id) throws SQLException {
        ResultSet resultSet = Database.getResultSet(COUNT_AT_HOME, rpi_id);
        resultSet.next();
        int i = resultSet.getInt(1);
        return i <= 0;
    }

    public static void stopPir(String rpi_id) throws SQLException {
        Database.update(SET_PIR_OFF, rpi_id);
    }

    public static void startPir(String rpi_id) throws SQLException {
        Database.update(SET_PIR_ON, rpi_id);
    }

    public static boolean getPir(String rpi_id) throws SQLException {
        return Database.getBoolean(GET_PIR_STATUS, rpi_id);
    }

    public static String[] getRpi_hashedPassword_salt(String rpi_id) throws SQLException {
        HashedUserCredentials credentials = getRpiPasswordSalt(rpi_id);
        // Get hexadecimal representation of a salt and a password

        return new String[]{credentials.getHashed_password(), credentials.getSalt()};

    }

    public static String getRpiFromEmail(String email) throws SQLException {
        return Database.getString(GET_RPI_FROM_EMAIL,email);
    }
}
