package com.group17.JSONObjects;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TagID {
    private String tagID;
    public TagID(String tagID){
        this.tagID = tagID;
    }

    public TagID(){

    }

    public String getTagID() {
        return tagID;
    }

    public void setTagID(String tagID) {
        this.tagID = tagID;
    }

}
