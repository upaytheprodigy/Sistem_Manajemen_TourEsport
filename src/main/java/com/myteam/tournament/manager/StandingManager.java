package com.myteam.tournament.manager;

import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Standing;
import com.myteam.tournament.model.Team;
import com.myteam.tournament.util.Repository;
import java.util.*;
import java.util.stream.Collectors;

public class StandingManager {

    private final Repository<Standing> repo;
    public StandingManager(Repository<Standing> repo){ 
        this.repo = repo; 
    }

    public void initialize(List<Team> teams){
        repo.clear();
        teams.forEach(t -> repo.add(new Standing(t.getId(), t.getName())));
    }

    public List<Standing> computeStandings(List<Match> matches){
        repo.findAll().forEach(Standing::reset);
        for (Match m : matches){
            var r = m.getResult();
            if (r.isEmpty()) continue;
            Standing a = repo.findById(m.getTeamA().getId()).orElseThrow();
            Standing b = repo.findById(m.getTeamB().getId()).orElseThrow();
            var res = r.get();
            if (res.getScoreA() > res.getScoreB()){ a.addWin(); b.addLoss();}
            else if (res.getScoreA() < res.getScoreB()){ b.addWin(); a.addLoss();}
            else { a.addDraw(); b.addDraw(); }
        }
        return repo.findAll().stream()
            .sorted(Comparator.comparingInt(Standing::getPoints).reversed())
            .collect(Collectors.toList());
    }
}
