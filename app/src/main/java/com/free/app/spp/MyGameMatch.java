package com.free.app.spp;

class MyGameMatch {
    private String teamName1, teamName2;
    private String time;
    private String place;
    private String score1, score2;

    MyGameMatch(String teamName1, String teamName2, String time, String place, String score1, String score2) {
        this.teamName1 = teamName1;
        this.teamName2 = teamName2;
        this.time = time;
        this.place = place;
        this.score1 = score1;
        this.score2 = score2;
    }

    String getTeamName1() {
        return teamName1;
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
