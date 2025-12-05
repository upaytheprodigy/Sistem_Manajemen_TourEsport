package com.myteam.tournament.model;

import java.util.Optional;

public class Result {
    private final int scoreA;
    private final int scoreB;

    public Result(int scoreA, int scoreB) {
        this.scoreA = scoreA;
        this.scoreB = scoreB;
    }

    public int getScoreA() { return scoreA; }
    public int getScoreB() { return scoreB; }

    public Optional<Team> getWinner(Team a, Team b) {
        if (scoreA > scoreB) return Optional.of(a);
        if (scoreB > scoreA) return Optional.of(b);
        return Optional.empty();
    }

    @Override public String toString() { return scoreA + "-" + scoreB; }
}
