package com.myteam.tournament.manager;

import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;
import com.myteam.tournament.factory.MatchFactory;
import com.myteam.tournament.factory.MatchType;
import com.myteam.tournament.util.Repository;
import java.util.List;


public class MatchManager {
    private final Repository<Match> repo;
    public MatchManager(Repository<Match> repo){ 
        this.repo = repo; 
    }

    public Match createMatch(Team a, Team b, MatchType type){
        Match m = MatchFactory.create(type, a, b);
        repo.add(m);
        return m;
    }
    
    public List<Match> getAllMatches(){ return repo.findAll(); }
    
    public Match findById(String id){ 
        return repo.findById(id).orElse(null);
    }
}
