package com.free.app.spp;

import java.io.Serializable;

public class PlayerData implements Serializable {
    private String ThreePoint;
    private String assist;
    private String percent;
    private String session;
    private String turnover;
    private String points;
    private String shoot;
    private String steal;
    private String time;
    private String foul;
    private String block;
    private String rebound;
    private String penalty;

    public PlayerData(String tp, String as, String per, String ss, String to, String p, String s, String st, String t, String f, String bl, String rb, String pe) {
        this.ThreePoint = tp;
        this.assist = as;
        this.percent = per;
        this.session = ss;
        this.turnover = to;
        this.points = p;
        this.shoot = s;
        this.steal = st;
        this.time = t;
        this.foul = f;
        this.block = bl;
        this.rebound = rb;
        this.penalty = pe;
    }

    public String getAssist() {
        return assist;
    }

    public String getBlock() {
        return block;
    }

    public String getFoul() {
        return foul;
    }

    public String getPercent() {
        return percent;
    }

    public String getPenalty() {
        return penalty;
    }

    public String getPoints() {
        return points;
    }

    public String getSession() {
        return session;
    }

    public String getRebound() {
        return rebound;
    }

    public String getShoot() {
        return shoot;
    }

    public String getSteal() {
        return steal;
    }

    public String getThreePoint() {
        return ThreePoint;
    }

    public String getTime() {
        return time;
    }

    public String getTurnover() {
        return turnover;
    }
}
