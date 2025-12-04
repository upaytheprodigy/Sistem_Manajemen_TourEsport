package com.myteam.tournament.manager;

import com.myteam.tournament.exception.MatchNotFoundException;
import com.myteam.tournament.factory.MatchFactory;
import com.myteam.tournament.factory.MatchType;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;
import com.myteam.tournament.util.Repository;

import java.util.List;

/**
 * Manages match creation, retrieval, and result reporting.
 */
public class MatchManager {

    private final Repository<Match, String> repository;

    public MatchManager(Repository<Match, String> repository) {
        this.repository = repository;
    }

    public Match createMatch(Team a, Team b, MatchType type) {
        Match m = MatchFactory.createMatch(type, a, b);
        repository.add(m);
        return m;
    }

    public Match getMatch(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new MatchNotFoundException(id));
    }

    public List<Match> getAllMatches() {
        return repository.findAll();
    }

    public void reportResult(String matchId, int scoreA, int scoreB) {
        Match m = getMatch(matchId);
        m.reportResult(scoreA, scoreB);
    }
}
