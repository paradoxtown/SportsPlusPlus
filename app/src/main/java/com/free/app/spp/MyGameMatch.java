package com.free.app.spp;

import org.json.JSONObject;

class MyGameMatch {
    private String teamName1, teamName2;
    private String time;
    private String date;
    private String place;
    private String score1, score2;
    private JSONObject js;

    MyGameMatch(String teamName1, String teamName2, String date, String time, String place, String score1, String score2, JSONObject jsonArray) {
        this.teamName1 = teamName1;
        this.teamName2 = teamName2;
        this.time = time;
        this.place = place;
        this.date = date;
        this.score1 = score1;
        this.score2 = score2;
        this.js = jsonArray;
    }
    String getTeamName1() {
        return teamName1;
    }

    JSONObject getJs() {
        return js;
    }

    String getTeamName2() {
        return teamName2;
    }

    String getTime() {
        return time;
    }

    String getScore1() {
        return score1;
    }

    String getScore2() {
        return score2;
    }

    String getPlace() {
        return place;
    }
}
