package com.myteam.tournament.model;

public class Standing {
    private final String teamId;
    private final String teamName;
    private int wins, losses, draws, points;

    public Standing(String teamId, String teamName) {
        this.teamId = teamId; this.teamName = teamName;
        reset();
    }

    public void addWin(){ wins++; points += 3; }
    public void addLoss(){ losses++; }
    public void addDraw(){ draws++; points += 1; }
    public void reset(){ wins=losses=draws=points=0; }

    public String getTeamId(){ return teamId; }
    public String getTeamName(){ return teamName; }
    public int getWins(){ return wins; }
    public int getLosses(){ return losses; }
    public int getDraws(){ return draws; }
    public int getPoints(){ return points; }
}
