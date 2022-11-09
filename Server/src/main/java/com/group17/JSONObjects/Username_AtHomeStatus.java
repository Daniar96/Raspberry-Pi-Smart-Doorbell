package com.group17.JSONObjects;

public class Username_AtHomeStatus {
    private String username;
    private boolean at_home;

    public Username_AtHomeStatus() {
    }

    public Username_AtHomeStatus(String username, boolean at_home) {
        this.username = username;
        this.at_home = at_home;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getAthome() {
        return at_home;
    }

    public void setAthome(boolean at_home) {
        this.at_home = at_home;
    }
}
