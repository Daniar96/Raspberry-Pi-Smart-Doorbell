package com.group17.server.database;

public class Queries {

    static final String CREATE_SCHEMA = "CREATE SCHEMA IF NOT EXISTS safe_home; SET search_path = safe_home;\n";
    static final String CREATE_USERS = "CREATE TABLE IF NOT EXISTS users (email VARCHAR PRIMARY KEY, username VARCHAR NOT NULL, full_name VARCHAR," +
            " hashed_password VARCHAR NOT NULL, salt  VARCHAR NOT NULL, rfid VARCHAR UNIQUE);";
    static final String CREATE_RPI = "CREATE TABLE IF NOT EXISTS rpi (rpi_id VARCHAR PRIMARY KEY, hashed_password VARCHAR NOT NULL, salt  VARCHAR NOT NULL," +
            "is_smoke_alert boolean, is_mic_alert boolean, is_flame_alert boolean, pir_is_on boolean, temperature VARCHAR, humidity VARCHAR);";
    static final String CREATE_TAGS = "CREATE TABLE IF NOT EXISTS allowed_tags (rpi_id VARCHAR, rfid VARCHAR," +
            " at_home boolean, PRIMARY KEY(rpi_id, rfid), FOREIGN KEY (rpi_id) REFERENCES rpi(rpi_id)," +
            " FOREIGN KEY (rfid) REFERENCES users(rfid));";
    static final String CREATE_IMAGES = "CREATE TABLE IF NOT EXISTS images (rpi_id VARCHAR, name TEXT, encoding TEXT," +
            "PRIMARY KEY(rpi_id, name), FOREIGN KEY(rpi_id) REFERENCES rpi(rpi_id));";
    static final String CREATE_LOGS = "CREATE TABLE IF NOT EXISTS logs (rpi_id VARCHAR, date TEXT, log TEXT," +
            "PRIMARY KEY(rpi_id, date), FOREIGN KEY(rpi_id) REFERENCES rpi(rpi_id));";
    static final String CREATE = CREATE_SCHEMA + CREATE_USERS + CREATE_RPI + CREATE_TAGS + CREATE_IMAGES + CREATE_LOGS;


    static final String GET_USER = "SELECT u.email, u.hashed_password, u.salt FROM safe_home.users u WHERE u.email = ?;";
    static final String GET_RPI_PASSWORD_SALT = "SELECT hashed_password, salt FROM rpi WHERE rpi_id = ?;";
    static final String GET_USER_INFO = "SELECT to_jsonb(data) FROM (SELECT u.username, u.full_name FROM safe_home.users u WHERE u.email = ?) as data;";
    static final String GET_AT_HOME_TAGS = "SELECT u.username, a.at_home FROM allowed_tags a, users u WHERE a.rpi_id = ? AND u.rfid = a.rfid;";
    static final String GET_AT_HOME_TAG = "SELECT u.username, a.at_home FROM allowed_tags a, users u WHERE a.rpi_id = ? AND u.rfid = a.rfid AND a.rfid = ?;";
    static final String GET_IMAGES = "SELECT name, encoding FROM images WHERE rpi_id = ? ORDER BY name DESC;";
    static final String GET_SMOKE_ALERT = "SELECT is_smoke_alert FROM rpi WHERE rpi_id = ?;";
    static final String GET_FLAME_ALERT = "SELECT is_flame_alert FROM rpi WHERE rpi_id = ?;";
    static final String GET_MIC_ALERT = "SELECT is_mic_alert FROM rpi WHERE rpi_id = ?;";
    static final String GET_PIR_STATUS = "SELECT pir_is_on FROM rpi WHERE rpi_id = ?;";
    static final String GET_TEMPERATURE_HUMIDITY = "SELECT temperature, humidity FROM rpi WHERE rpi_id = ?;";
    static final String GET_LOGS = "SELECT date, log FROM logs WHERE rpi_id = ? ORDER BY date DESC;";
    static final String GET_RPI_FROM_EMAIL = "SELECT r.rpi_id FROM rpi r, users u, allowed_tags a WHERE u.email = ? AND u.rfid = a.rfid AND a.rpi_id = r.rpi_id;";
    static final String COUNT_AT_HOME = "SELECT COUNT (*) FROM allowed_tags WHERE is_online = TRUE AND rpi_id = ?";

    static final String INSERT_USER = "INSERT INTO users (hashed_password, salt, email, username, rfid) VALUES (?, ?, ?, ?, ?);";
    static final String INSERT_RPI = "INSERT INTO rpi (hashed_password, salt, rpi_id," +
            " is_smoke_alert,is_mic_alert, is_flame_alert, pir_is_on) VALUES (?, ?, ?, FALSE, FALSE, FALSE, FALSE);";
    static final String INSERT_IMAGE = "INSERT INTO images (rpi_id, name, encoding) VALUES (?, ?, ?);";
    static final String INSERT_LOG = "INSERT INTO logs (rpi_id, date, log) VALUES (?, ?, ?);";
    static final String SET_USER_INFO_PASSWORD = "UPDATE users SET hashed_password = ?, salt = ?, full_name = ?, username = ?  WHERE email = ?;";
    static final String SET_USER_INFO = "UPDATE users SET full_name = ?, username = ?  WHERE email = ?;";
    static final String SWITCH_AT_HOME = "UPDATE allowed_tags SET at_home = NOT at_home WHERE rfid=?;";
    static final String SET_SMOKE_ALERT = "UPDATE rpi SET is_smoke_alert=? WHERE rpi_id = ?;";
    static final String SET_MIC_ALERT = "UPDATE rpi SET is_mic_alert=? WHERE rpi_id = ?;";
    static final String SET_FLAME_ALERT = "UPDATE rpi SET is_flame_alert=? WHERE rpi_id = ?;";
    static final String SET_TEMPERATURE = "UPDATE rpi SET temperature=?, humidity=? WHERE rpi_id = ?";
    static final String SET_PIR_ON = "UPDATE rpi SET pir_is_on = TRUE WHERE rpi_id = ?;";
    static final String SET_PIR_OFF = "UPDATE rpi SET pir_is_on = FALSE WHERE rpi_id = ?;";


}
