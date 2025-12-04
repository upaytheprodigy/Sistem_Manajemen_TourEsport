package com.myteam.tournament.strategy;

import com.myteam.tournament.factory.MatchFactory;
import com.myteam.tournament.factory.MatchType;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 * Simple single-elimination pairing:
 * - If odd number, last team gets a bye to next round (represented as no direct match now).
 * This implementation generates only first round pairings.
 *
 * Note for integration: building full bracket rounds can be added later.
 */
public final class KnockoutStrategy implements MatchFormatStrategy {

    private final MatchType matchType;

    public KnockoutStrategy(MatchType matchType) {
        this.matchType = Objects.requireNonNull(matchType);
    }

    @Override
    public List<Match> generateMatches(List<Team> teams) {
        if (teams == null || teams.isEmpty()) return Collections.emptyList();

        // copy and shuffle for fairness (integration might want deterministic order)
        List<Team> pool = new ArrayList<>(teams);
        Collections.shuffle(pool);

        Queue<Team> queue = new LinkedList<>(pool);
        List<Match> matches = new ArrayList<>();

        while (queue.size() >= 2) {
            Team a = queue.poll();
            Team b = queue.poll();
            matches.add(MatchFactory.createMatch(matchType, a, b));
        }

        // if one team remains, it has a bye (no match created). Integration can handle bye.
        return matches;
    }
}
