package com.myteam.tournament.model;

public class Standing {
    private final String teamId;
    private final String teamName;
    private int wins, losses, draws, points;

    public Standing(String id, String name){
        this.teamId=id; this.teamName=name; reset();
    }

    public void addWin(){ 
        wins++; 
        points+=3; 
    }

    public void addLoss(){ losses++; }

    public void addDraw(){ 
        draws++; 
        points+=1; 
    }

    public String getTeamId(){return teamId;}
    public String getTeamName(){return teamName;}
    public int getPoints(){return points;}
    public void reset(){ wins=losses=draws=points=0; }

    @Override public String toString(){ 
        return teamName + " - " + points + " pts (W"+wins+" D"+draws+" L"+losses+")"; 
    }
}
