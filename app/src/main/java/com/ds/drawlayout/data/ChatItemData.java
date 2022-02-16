package com.ds.drawlayout.data;

public class ChatItemData {

    String name;
    String message;
    String time;
    String profileUrl;

    public ChatItemData(String name, String message, String time, String pofileUrl) {
        this.name = name;
        this.message = message;
        this.time = time;
        this.profileUrl = pofileUrl;
    }

    public ChatItemData() {
    }

    //Getter & Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPofileUrl() {
        return profileUrl;
    }

    public void setPofileUrl(String pofileUrl) {
        this.profileUrl = pofileUrl;
    }
}