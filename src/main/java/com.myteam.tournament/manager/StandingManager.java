package com.myteam.tournament.manager;

import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;

import java.util.*;

/**
 * Calculates standings based on match results.
 * Clean, simple, and deterministic.
 */
public class StandingManager {

    public Map<Team, Integer> computeStandings(List<Match> matches) {
        Map<Team, Integer> points = new HashMap<>();

        for (Match m : matches) {
            points.putIfAbsent(m.getTeamA(), 0);
            points.putIfAbsent(m.getTeamB(), 0);

            m.getWinner().ifPresent(winner ->
                    points.put(winner, points.get(winner) + 3)
            );
        }
        return points;
    }
}
