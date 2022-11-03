package com.group17.JSONObjects;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Image {
    private String name;
    private String encode;

    public Image (String name, String encode){
        this.name = name;
        this.encode = encode;
    }

    public Image (){

    }

    public String getEncode() {
        return encode;
    }

    public String getName() {
        return name;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public void setName(String name) {
        this.name = name;
    }

}
