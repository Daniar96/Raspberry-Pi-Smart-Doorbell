package com.group17.JSONObjects;

import static com.group17.server.SecurityFunctions.getRandomString;

public class Username_Token {

    String username;
    String token;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return new String(token);
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Username_Token(String username) {
        this.username = username;
        this.token = getRandomString();
    }

    public Username_Token(String username, String token) {
        this.username = username;
        this.token = token;
    }


    public void resetToken() {
        this.token = getRandomString();
    }

}

