package com.myteam.tournament.model;
import java.util.Optional;
import java.util.UUID;

import com.myteam.tournament.factory.MatchType;

public class Match {
    protected final String id;
    private final Team teamA;
    private final Team teamB;
    protected final MatchType type;
    private MatchResult result; // nullable
    public Match(Team a, Team b, MatchType type2) {
        this.id = UUID.randomUUID().toString();
        this.teamA = a; this.teamB = b; this.type = type2;
    }
    public Match(Team a, Team b, String string) {
        this.id = UUID.randomUUID().toString();
        this.teamA = a;
        this.teamB = b;
        // Attempt to parse MatchType from the string, or assign a default if parsing fails
        MatchType parsedType;
        try {
            parsedType = MatchType.valueOf(string.toUpperCase());
        } catch (Exception e) {
            parsedType = null; // or assign a default MatchType if desired
        }
        this.type = parsedType;
    }
    public String getId() { return id; }
    public Team getTeamA() { return teamA; }
    public Team getTeamB() { return teamB; }
    public MatchType getType() { return type; }
    public Optional<MatchResult> getResult() { return Optional.ofNullable(result); }
    public void setResult(MatchResult r) { this.result = r; }
    public String getWinner() {
        if (result == null) return null;
        if (result.getScoreA() > result.getScoreB()) return teamA.getId();
        if (result.getScoreB() > result.getScoreA()) return teamB.getId();
        return null; // draw
    }
    public String getLoser() {
        if (result == null) return null;
        if (result.getScoreA() > result.getScoreB()) return teamB.getId();
        if (result.getScoreB() > result.getScoreA()) return teamA.getId();
        return null;
    }
    public String toDisplayString() {
        String s = teamA.getName() + " vs " + teamB.getName() + " (" + type + ")";
        if (result != null) s += " - " + result.getScoreA() + ":" + result.getScoreB();
        return s;
    }
    @Override public String toString() { return toDisplayString(); }
    public void reportResult(int a, int b) {
        this.result = new MatchResult(a, b);
    }
    
    public void setScoreB(int b) {
        if (this.result == null) {
            this.result = new MatchResult(0, b);
        } else {
            this.result = new MatchResult(this.result.getScoreA(), b);
        }
    }
    public void setScoreA(int a) {
        if (this.result == null) {
            this.result = new MatchResult(a, 0);
        } else {
            this.result = new MatchResult(a, this.result.getScoreB());
        }
    }
}
