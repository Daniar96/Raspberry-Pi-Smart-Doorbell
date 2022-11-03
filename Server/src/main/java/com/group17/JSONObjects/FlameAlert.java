package com.group17.JSONObjects;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FlameAlert {
    int alert;

    public FlameAlert(int alert){
        this.alert = alert;
    }

    public FlameAlert(){

    }

    public void setAlert(int alert) {
        this.alert = alert;
    }

    public int getAlert() {
        return alert;
    }
}
