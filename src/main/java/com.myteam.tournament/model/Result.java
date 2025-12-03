package com.myteam.tournament.model;

import java.util.Optional;

/**
 * Simple result object for a match.
 */
public final class Result {
    private final int scoreA;
    private final int scoreB;

    public Result(int scoreA, int scoreB) {
        this.scoreA = scoreA;
        this.scoreB = scoreB;
    }

    public int getScoreA() { return scoreA; }
    public int getScoreB() { return scoreB; }

    public Optional<Team> getWinner(Team teamA, Team teamB) {
        if (scoreA > scoreB) return Optional.ofNullable(teamA);
        if (scoreB > scoreA) return Optional.ofNullable(teamB);
        return Optional.empty(); // draw
    }

    public boolean isDraw() { return scoreA == scoreB; }

    @Override
    public String toString() {
        return "Result{" + scoreA + ":" + scoreB + '}';
    }
}
