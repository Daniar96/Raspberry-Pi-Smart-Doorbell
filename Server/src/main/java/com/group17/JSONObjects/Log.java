package com.group17.JSONObjects;

public class Log {
    private String date;
    private String log;

    public Log(String date, String log){
        this.date = date;
        this.log = log;
    }

    public Log(){

    }

    public String getDate() {
        return date;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
