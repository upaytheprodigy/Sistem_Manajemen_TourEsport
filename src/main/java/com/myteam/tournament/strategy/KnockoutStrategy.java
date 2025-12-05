package com.myteam.tournament.strategy;

import com.myteam.tournament.manager.MatchManager;
import com.myteam.tournament.factory.MatchType;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates first-round knockout pairing (1v2, 3v4 ...). Not auto-resolve next rounds.
 */
public class KnockoutStrategy implements ScheduleStrategy {

    @Override
    public List<Match> generate(List<Team> teams, MatchManager mm, MatchType type) {
        List<Match> res = new ArrayList<>();
        List<Team> copy = new ArrayList<>(teams);
        // if odd, last one gets bye (not implemented: just skip)
        for (int i = 0; i + 1 < copy.size(); i += 2) {
            res.add(mm.createMatch(copy.get(i), copy.get(i + 1), type));
        }
        return res;
    }
}
