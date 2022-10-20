package com.group17.JSONObjects;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TagID {
    String tagID;
    public TagID(String tagID){
        this.tagID = tagID;
    }

    public String getTagID() {
        return tagID;
    }
}
