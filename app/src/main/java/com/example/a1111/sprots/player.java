package com.example.a1111.sprots;

import java.io.Serializable;

public class player implements Serializable {
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
    public player(String cn,String p,String w,String con,String nat,String sh,String num,String sal,String t,String bir,String en,String hei,String d)
    {
        this.ChiName = cn;
        this.pos = p;this.weight = w;this.contract = con;this.nationality = nat;this.school = sh;this.number = num;
        this.salary = sal;this.team  = t;this.birth = bir;this.height = hei;this.draft =d;
        if(this.ChiName == null)this.ChiName = this.EngName;
//        this.portrait = por;this.season_regular_data = srd;this.career_regular_data = crd;
//        this.career_playoffs_data = cpd;
    }
    public String getChiName(){return this.ChiName;}
    public String getPos() { return this.pos; }
    public String getWeight()
    {
        return this.weight;
    }
    public String getContract()
    {
        return this.contract;
    }
    public String getNationality()
    {
        return this.nationality;
    }
    public String getSchool()
    {
        return this.school;
    }
    public String getNumber(){return this.number;}
    public String getSalary()
    {
        return this.salary;
    }
    public String getTeam()
    {
        return this.team;
    }
    public String getBirth()
    {
        return this.birth;
    }
    public String getEngName(){return this.EngName;}
    public String getHeight()
    {
        return this.height;
    }
    public String getDraft()
    {
        return this.draft;
    }
//    public String getPortrait()
//    {
//        return this.portrait;
//    }
//
//    public PlayerData getCareer_playoffs_data() {
//        return career_playoffs_data;
//    }
//
//    public PlayerData getCareer_regular_data() {
//        return career_regular_data;
//    }
//
//    public PlayerData getSeason_regular_data() {
//        return season_regular_data;
//    }
}
