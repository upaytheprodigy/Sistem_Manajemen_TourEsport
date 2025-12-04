package com.myteam.tournament.model;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Abstract Match. Concrete matches implement play() and possibly best-of logic.
 * Keep fields protected enough for subclass access if needed.
 */
public abstract class Match {
    private final String id;
    protected final Team teamA;
    protected final Team teamB;
    protected Result result; // null until reported/played

    protected Match(Team teamA, Team teamB) {
        this.id = UUID.randomUUID().toString();
        this.teamA = Objects.requireNonNull(teamA, "teamA required");
        this.teamB = Objects.requireNonNull(teamB, "teamB required");
    }

    public String getId() { return id; }
    public Team getTeamA() { return teamA; }
    public Team getTeamB() { return teamB; }
    public Optional<Result> getResult() { return Optional.ofNullable(result); }

    /**
     * Concrete subclasses either simulate or accept result input.
     * Implementation should set `result` field.
     */
    public abstract void play(); // can simulate or leave empty if using manual reporting

    /**
     * Allow external reporting (e.g., CLI) to set result explicitly.
     */
    public void reportResult(int scoreA, int scoreB) {
        this.result = new Result(scoreA, scoreB);
    }

    public Optional<Team> getWinner() {
        if (result == null) return Optional.empty();
        return result.getWinner(teamA, teamB);
    }

    @Override
    public String toString() {
        return "Match{" + "id='" + id + '\'' + ", teamA=" + teamA.getName() + ", teamB=" + teamB.getName() + ", result=" + result + '}';
    }
}
