package com.myteam.tournament.facade;
import com.myteam.tournament.manager.*;
import com.myteam.tournament.model.*;
import com.myteam.tournament.strategy.ScheduleStrategy;
import com.myteam.tournament.factory.MatchType; 

import java.util.List;

public class TournamentFacade {
    private final TeamManager teamManager;
    private final MatchManager matchManager;
    private final ScheduleManager scheduleManager;
    private final StandingManager standingManager;

    public TournamentFacade(TeamManager tm, MatchManager mm, ScheduleManager sm, StandingManager srm){
        this.teamManager = tm; this.matchManager = mm; this.scheduleManager = sm; this.standingManager = srm;
    }

    public void addTeam(String name){ teamManager.addTeam(name); }
    public List<Team> getTeams(){ return teamManager.getAllTeams(); }
    public List<Match> getAllMatches(){ return matchManager.getAllMatches(); }

    public void generateSchedule(ScheduleStrategy strategy, MatchType type){
        var list = strategy.generate(teamManager.getAllTeams(), matchManager, type);
        matchManager.setMatches(list);
    }

    public List<Standing> getStandings(){
        return standingManager.computeStandings(matchManager.getAllMatches());
    }

    public void inputResult(String id, int a, int b) {
        matchManager.updateMatchResult(id, a, b);
    }
}
