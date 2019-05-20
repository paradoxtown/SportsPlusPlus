package com.free.app.spp;

import java.io.Serializable;
import java.util.List;

class MyGame implements Serializable {
    private String gameName;
    private String date;
    private String intro;
    private String id;
    private boolean is_liked;
    private boolean is_administrator;

    MyGame(String gameName,String d,String description,String id) {
        this.gameName = gameName;this.intro = description;
        date = d;this.id = id;
        this.is_liked = false;
        this.is_administrator = false;
    }

    String getGameName() {
        return gameName;
    }


    public String getDate() {
        return date;
    }

    String getIntro() {
        return intro;
    }

    public String getId() {
        return id;
    }

    boolean getIs_administrator() {
        return is_administrator;
    }

    boolean getIs_liked() {
        return is_liked;
    }

    void setIs_administrator(boolean is_administrator) {
        this.is_administrator = is_administrator;
    }

    void setIs_liked(boolean is_liked) {
        this.is_liked = is_liked;
    }
}
