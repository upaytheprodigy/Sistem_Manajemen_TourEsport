package com.myteam.tournament.manager;

import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Standing;
import com.myteam.tournament.model.Team;
import com.myteam.tournament.util.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StandingManager {

    private final Repository<Standing> repo;

    public StandingManager(Repository<Standing> repo) { this.repo = repo; }

    public void initialize(List<Team> teams) {
        repo.clear();
        teams.forEach(t -> repo.add(new Standing(t.getId(), t.getName())));
    }

    public List<Standing> computeStandings(List<Match> matches) {
        repo.findAll().forEach(Standing::reset);

        for (Match m : matches) {
            var opt = m.getResult();
            if (opt.isEmpty()) continue;

            String idA = m.getTeamA().getId();
            String idB = m.getTeamB().getId();
            Standing a = repo.findById(idA).orElseThrow();
            Standing b = repo.findById(idB).orElseThrow();

            var r = opt.get();
            if (r.getScoreA() > r.getScoreB()) { a.addWin(); b.addLoss(); }
            else if (r.getScoreA() < r.getScoreB()) { b.addWin(); a.addLoss(); }
            else { a.addDraw(); b.addDraw(); }
        }

        return repo.findAll().stream()
                .sorted(Comparator.comparingInt(Standing::getPoints).reversed())
                .collect(Collectors.toList());
    }

    public List<Standing> getCurrentStandings() { return repo.findAll(); }
    public void setAllStandings(List<Standing> list) { repo.overrideAll(list); }
}
