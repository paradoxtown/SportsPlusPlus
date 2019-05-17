package com.free.app.spp;

import java.io.Serializable;
import java.util.List;

class MyGame implements Serializable {
    private String gameName;
    private String date;
    private String intro;
    private String id;
    private List<String> admins;

    MyGame(String gameName,String d,String description,String id,List<String>admins) {
        this.gameName = gameName;this.intro = description;
        this.admins = admins;date = d;this.id = id;
    }

    public String getGameName() {
        return gameName;
    }

    public List<String> getAdmins() {
        return admins;
    }

    public String getDate() {
        return date;
    }

    public String getIntro() {
        return intro;
    }

    public String getId() {
        return id;
    }
}
