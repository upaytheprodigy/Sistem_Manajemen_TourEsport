package com.myteam.tournament.model;
public class Standing {
    private final String teamId;
    private final String teamName;
    private int wins;
    private int losses;
    private int draws;
    private int points;
    public Standing(String teamId, String teamName) {
        this.teamId = teamId; this.teamName = teamName;
    }
    public String getTeamId() { return teamId; }
    public String getTeamName() { return teamName; }
    public int getWins() { return wins; }
    public int getLosses() { return losses; }
    public int getDraws() { return draws; }
    public int getPoints() { return points; }
    public void addWin(){ wins++; points += 3; }
    public void addLoss(){ losses++; }
    public void addDraw(){ draws++; points += 1; }
    public void reset(){ wins=losses=draws=points=0; }
    @Override public String toString(){
        return teamName + " Pts:" + points + " W:" + wins + " D:" + draws + " L:" + losses;
    }
}
