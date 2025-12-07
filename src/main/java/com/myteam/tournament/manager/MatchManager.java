package com.myteam.tournament.manager;
import com.myteam.tournament.model.*;
import com.myteam.tournament.util.Repository;
import com.myteam.tournament.factory.MatchFactory;
import com.myteam.tournament.factory.MatchType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<Match> generateMatches(List<Team> teams, MatchType type) {
        List<Match> matches = new ArrayList<>();

        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                matches.add(
                    MatchFactory.createMatch(type, teams.get(i), teams.get(j))
                );
            }
        }

        return matches;
    }

    public void updateMatchResult(String id, int a, int b) {
        Optional<Match> match = repo.findById(id);
        if (match.isPresent()) {
            Match m = match.get();
            m.setScoreA(a);
            m.setScoreB(b);
            repo.update(m);
        }
    }
}
