package com.myteam.tournament.manager;

import com.myteam.tournament.exception.MatchNotFoundException;
import com.myteam.tournament.factory.MatchFactory;
import com.myteam.tournament.factory.MatchType;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;
import com.myteam.tournament.util.Repository;

import java.util.List;

public class MatchManager {

    private final Repository<Match> repo;

    public MatchManager(Repository<Match> repo) { this.repo = repo; }

    public Match createMatch(Team a, Team b, MatchType type) {
        Match m = MatchFactory.createMatch(type, a, b);
        repo.add(m);
        return m;
    }

    public Match getMatch(String id) { return repo.findById(id).orElseThrow(() -> new MatchNotFoundException(id)); }

    public List<Match> getAllMatches() { return repo.findAll(); }

    public void reportResult(String matchId, int sA, int sB) { getMatch(matchId).reportResult(sA, sB); }

    public void setAllMatches(List<Match> list) { repo.overrideAll(list); }
}
