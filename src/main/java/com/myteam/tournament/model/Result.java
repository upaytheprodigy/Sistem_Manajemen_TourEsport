package com.myteam.tournament.model;

public class Result {

    private final int scoreA;
    private final int scoreB;

    public Result(int a,int b){ 
        this.scoreA=a; 
        this.scoreB=b;
    }
    
    public int getScoreA(){return scoreA;} 
    public int getScoreB(){return scoreB;}
}
