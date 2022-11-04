package com.group17.JSONObjects;

public class UserInfo {
    private String full_name;
    private String username;
    private String password;


    public UserInfo() {
    }
    public UserInfo(String jsonString) throws NullPointerException {
        if (jsonString.equals("{}")){
            throw new NullPointerException("There is no user info");
        }
        String[] jsonArray = jsonString.split("\\\": \"|\\\", \"|\\\"}|\\{\"");
        for (int i = 1; i < jsonArray.length; i++) {
            switch (jsonArray[i]) {
                case "username":
                    this.username = jsonArray[i + 1];
                    break;
                case "full_name":
                    this.full_name = jsonArray[i + 1].substring(3);
                    break;
                default:
                    break;
            }
        }
        this.password = "";
    }
    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
