package com.free.app.spp;

import java.util.List;

class MyGame {
    private String gameName;
    private String description;
    private List<String> admins;

    MyGame(String gameName) {
        this.gameName = gameName;
    }

    String getGameName() {
        return gameName;
    }
}
