package com.myteam.tournament.model;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public abstract class Match {
    private final String id;
    protected final Team teamA;
    protected final Team teamB;
    protected Result result; // null until reported

    protected Match(Team a, Team b) {
        this.id = UUID.randomUUID().toString();
        this.teamA = Objects.requireNonNull(a);
        this.teamB = Objects.requireNonNull(b);
    }

    public String getId() { return id; }
    public Team getTeamA() { return teamA; }
    public Team getTeamB() { return teamB; }
    public Optional<Result> getResult() { return Optional.ofNullable(result); }

    public void reportResult(int scoreA, int scoreB) { this.result = new Result(scoreA, scoreB); }
    public Optional<Team> getWinner() {
        if (result == null) return Optional.empty();
        return result.getWinner(teamA, teamB);
    }
    public boolean isFinished() { return result != null; }

    public abstract void play(); // optional simulation for subclasses

    @Override public String toString() {
        return id + ": " + teamA.getName() + " vs " + teamB.getName() + " -> " + (result==null? "-" : result.toString());
    }
}
