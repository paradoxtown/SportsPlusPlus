package com.example.a1111.sprots;

public class Match {
    private String host_name;
    private String guest_name;
    private String host_points;
    public String guest_points;
    private String host_image;
    private String guest_image;
    private String id;

    public Match(String hn, String gn,String hp,String gp,String hi,String gi,String id) {
        this.host_name = hn;
        this.guest_name = gn;
        this.host_points = hp;
        this.guest_points = gp;
        this.host_image = hi;
        this.guest_image = gi;
        this.id = id;
    }

    public String getHost_points() {
        return host_points;
    }

    public String getGuest_points() {
        return guest_points;
    }

    public String getGuest_name() {
        return guest_name;
    }

    public String getHost_name()
    {
        return host_name;
    }

    public String getGuest_image() {
        return guest_image;
    }

    public String getHost_image() {
        return host_image;
    }
    public String getId(){
        return id;
    }

}


class MatchDate {
    private String date;
    MatchDate(String date) {
        this.date = date;
    }
    String getDate() {
        return date;
    }
    public void setDate(String aName) {
        this.date = aName;
    }
}

