package com.group17.JSONObjects;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MicAlert {
    int alert;

    public MicAlert(int alert){
        this.alert = alert;
    }

    public MicAlert(){
    }

    public void setAlert(int alert) {
        this.alert = alert;
    }

    public int getAlert() {
        return alert;
    }
}
