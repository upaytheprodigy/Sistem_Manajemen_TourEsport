package com.myteam.tournament.facade;

import com.myteam.tournament.manager.*;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;
import com.myteam.tournament.strategy.ScheduleStrategy;
import com.myteam.tournament.factory.MatchType;
import java.util.List;

public class TournamentFacade {
    
    private final TeamManager teamManager;
    private final MatchManager matchManager;
    private final ScheduleManager scheduleManager;
    private final StandingManager standingManager;

    public TournamentFacade(TeamManager tm, MatchManager mm, ScheduleManager sm, StandingManager st){
        this.teamManager = tm; 
        this.matchManager = mm; 
        this.scheduleManager = sm; 
        this.standingManager = st;
    }

    public void addTeam(String name){ 
        teamManager.addTeam(name); 
    }

    public List<Team> getTeams(){ return teamManager.getAllTeams(); }

    public List<Match> generateSchedule(ScheduleStrategy strat, MatchType type){
        scheduleManager.setStrategy(strat);
        return scheduleManager.generateSchedule(getTeams(), matchManager, type);
    }

    public List<Match> getAllMatches(){ return matchManager.getAllMatches(); }

    public void reportResult(String matchId, int a, int b){
        Match m = matchManager.findById(matchId);
        if (m != null) m.reportResult(a,b);
    }
}
