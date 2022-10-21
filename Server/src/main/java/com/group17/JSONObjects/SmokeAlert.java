package com.group17.JSONObjects;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SmokeAlert {
    int alert;
    public SmokeAlert(int alert){
        this.alert = alert;
    }
    public SmokeAlert(){

    }

    public int getAlert() {
        return alert;
    }

    public void setAlert(int alert) {
        this.alert = alert;
    }
}
