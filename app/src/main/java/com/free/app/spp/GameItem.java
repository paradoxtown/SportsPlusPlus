package com.free.app.spp;

class GameItem {
    private String team_name;
    private String sec1, sec2, sec3, sec4, extra1, extra2, extra3, extra4, total;
    GameItem(String team_name, String sec1, String sec2, String sec3, String sec4,
             String extra1, String extra2, String extra3, String extra4, String total) {
        this.team_name = team_name;
        this.sec1 = sec1;
        this.sec2 = sec2;
        this.sec3 = sec3;
        this.sec4 = sec4;
        this.extra1 = extra1;
        this.extra2 = extra2;
        this.extra3 = extra3;
        this.extra4 = extra4;
        this.total = total;
    }
}
