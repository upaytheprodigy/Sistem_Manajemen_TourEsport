package com.myteam.tournament.strategy;

import com.myteam.tournament.factory.MatchFactory;
import com.myteam.tournament.factory.MatchType;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Round-robin: each pair of teams plays once.
 */
public final class RoundRobinStrategy implements MatchFormatStrategy {

    private final MatchType matchType;

    public RoundRobinStrategy(MatchType matchType) {
        this.matchType = Objects.requireNonNull(matchType);
    }

    @Override
    public List<Match> generateMatches(List<Team> teams) {
        if (teams == null) return Collections.emptyList();
        List<Match> matches = new ArrayList<>();
        int n = teams.size();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                matches.add(MatchFactory.createMatch(matchType, teams.get(i), teams.get(j)));
            }
        }
        return matches;
    }
}
