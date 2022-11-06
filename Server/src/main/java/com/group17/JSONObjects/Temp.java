package com.group17.JSONObjects;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Temp {
    String temp;
    String humidity;

    public Temp(String temp, String humidity){
        this.temp = temp;
        this.humidity = humidity;
    }

    public Temp(){

    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getTemp() {
        return temp;
    }
}
