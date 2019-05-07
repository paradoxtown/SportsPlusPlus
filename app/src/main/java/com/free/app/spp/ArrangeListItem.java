package com.free.app.spp;

import java.io.Serializable;

class ArrangeListItem implements Serializable {
    private String teamA,teamB,pos,time;
    ArrangeListItem(String a,String b,String p,String t){
        teamA = a;
        teamB = b;
        pos = p;
        time = t;
    }

    String getPos() {
        return pos;
    }

    String getTeamA() {
        return teamA;
    }

    String getTeamB() {
        return teamB;
    }

    String getTime() {
        return time;
    }
}
