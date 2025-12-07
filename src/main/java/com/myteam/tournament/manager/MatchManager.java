package com.myteam.tournament.manager;
import com.myteam.tournament.model.*;
import com.myteam.tournament.util.Repository;
import com.myteam.tournament.factory.MatchType;
import java.util.ArrayList;
import java.util.List;

public class MatchManager {
    private final Repository<Match> repo;
    public MatchManager(Repository<Match> repo){ this.repo = repo; }
    public Match createMatch(Team a, Team b, MatchType type){
        Match m = new Match(a,b,type);
        repo.add(m);
        return m;
    }
    public List<Match> getAllMatches(){ return repo.findAll(); }
    public void setMatches(List<Match> list){
        repo.overrideAll(list);
    }
}
