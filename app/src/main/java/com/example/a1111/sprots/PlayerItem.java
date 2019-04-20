package com.example.a1111.sprots;

import com.bin.david.form.annotation.SmartTable;

@SmartTable(name = "Player Data")
class PlayerItem {
    private String name, pos;
    private String time;
    private String shots, threePoints, penaltyShots;
    private String frontRebs, backRebs, rebs;
    private String assists, fouls, steals, faults, blocks, score;
    private String plusMinus;

    PlayerItem(String name, String pos, String time, String shots, String threePoints, String penaltyShots,
               String frontRebs, String backRebs, String rebs, String assists, String fouls, String steals, String faults,
               String blocks, String score, String plusMinus) {
        this.name = name;
        this.pos = pos;
        this.time = time;
        this.shots = shots;
        this.threePoints = threePoints;
        this.penaltyShots = penaltyShots;
        this.frontRebs = frontRebs;
        this.backRebs = backRebs;
        this.rebs = rebs;
        this.assists = assists;
        this.fouls = fouls;
        this.steals = steals;
        this.faults = faults;
        this.blocks = blocks;
        this.score = score;
        this.plusMinus = plusMinus;
    }

}
