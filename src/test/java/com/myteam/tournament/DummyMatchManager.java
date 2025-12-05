package com.myteam.tournament;

import com.myteam.tournament.factory.MatchFactory;
import com.myteam.tournament.factory.MatchType;
import com.myteam.tournament.manager.MatchManager;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;

// Dummy MatchManager for test (tidak butuh Repository)
public class DummyMatchManager extends MatchManager {

    public DummyMatchManager() {
        super(null); // repo tidak dipakai
    }

    @Override
    public Match createMatch(Team a, Team b, MatchType type) {
        return MatchFactory.createMatch(type, a, b);
    }
}
