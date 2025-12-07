package com.myteam.tournament.model;
public class MatchResult {
    private final int scoreA;
    private final int scoreB;
    public MatchResult(int scoreA, int scoreB) { this.scoreA = scoreA; this.scoreB = scoreB; }
    public int getScoreA() { return scoreA; }
    public int getScoreB() { return scoreB; }
}
