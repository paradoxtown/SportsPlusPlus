package com.free.app.spp;

import java.io.Serializable;

class player implements Serializable {
    private String ChiName;
    private String pos;
    private String weight;
    private String contract;
    private String nationality;
    private String school;
    private String number;
    private String salary;
    private String team;
    private String birth;
    private String EngName;
    private String height;
    private String draft;
//    private String portrait;
//    private PlayerData season_regular_data;
//    private PlayerData career_regular_data;
//    private PlayerData career_playoffs_data;
    player(String cn,String p,String w,String con,String nat,String sh,String num,String sal,String t,String bir,String en,String hei,String d)
    {
        this.ChiName = cn;
        this.pos = p;this.weight = w;this.contract = con;this.nationality = nat;this.school = sh;this.number = num;
        this.salary = sal;this.team  = t;this.birth = bir;this.height = hei;this.draft =d;
        if(this.ChiName == null) this.ChiName = this.EngName;
//        this.portrait = por;this.season_regular_data = srd;this.career_regular_data = crd;
//        this.career_playoffs_data = cpd;
    }
    String getChiName(){return this.ChiName;}
    String getPos() { return this.pos; }
    String getWeight()
    {
        return this.weight;
    }
    String getContract()
    {
        return this.contract;
    }
    String getNationality()
    {
        return this.nationality;
    }
    String getSchool()
    {
        return this.school;
    }
    String getNumber(){return this.number;}
    String getSalary()
    {
        return this.salary;
    }
    String getTeam()
    {
        return this.team;
    }
    String getBirth()
    {
        return this.birth;
    }
    String getEngName(){return this.EngName;}
    String getHeight()
    {
        return this.height;
    }
    String getDraft()
    {
        return this.draft;
    }
//    String getPortrait()
//    {
//        return this.portrait;
//    }
//
//    PlayerData getCareer_playoffs_data() {
//        return career_playoffs_data;
//    }
//
//    PlayerData getCareer_regular_data() {
//        return career_regular_data;
//    }
//
//    PlayerData getSeason_regular_data() {
//        return season_regular_data;
//    }
}
