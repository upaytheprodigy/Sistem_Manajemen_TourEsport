package com.myteam.tournament.model;

import java.util.Random;

/**
 * Simple BO1 match — either simulated play() or external report.
 */
public final class BO1Match extends Match {

    public BO1Match(Team teamA, Team teamB) {
        super(teamA, teamB);
    }

    /**
     * Simple deterministic/random simulation for demo/testing.
     * In real integration, you might prefer to not use simulation and accept reportResult().
     */
    @Override
    public void play() {
        // small simulation using random; deterministic alternatives possible in tests by injecting rng
        Random rnd = new Random();
        int scoreA = rnd.nextInt(5); // 0..4
        int scoreB = rnd.nextInt(5);
        this.reportResult(scoreA, scoreB);
    }
}
