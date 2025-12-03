package com.myteam.tournament.model;

import java.util.Random;

/**
 * Best-of-3: first to 2 wins.
 * For simplicity, play three BO1 simulations and decide by wins.
 */
public final class BO3Match extends Match {

    public BO3Match(Team teamA, Team teamB) {
        super(teamA, teamB);
    }

    @Override
    public void play() {
        int winsA = 0;
        int winsB = 0;
        Random rnd = new Random();
        for (int i = 0; i < 3 && winsA < 2 && winsB < 2; i++) {
            int sA = rnd.nextInt(5);
            int sB = rnd.nextInt(5);
            if (sA > sB) winsA++;
            else if (sB > sA) winsB++;
            // draws count as no wins; loop continues
        }
        // convert wins to final score representation (e.g., wins)
        this.reportResult(winsA, winsB);
    }
}
