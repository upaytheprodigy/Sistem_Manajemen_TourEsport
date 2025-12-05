package com.myteam.tournament.facade;

import com.myteam.tournament.factory.MatchType;
import com.myteam.tournament.manager.*;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;
import com.myteam.tournament.model.Standing;
import com.myteam.tournament.strategy.ScheduleStrategy;

import java.util.List;

/**
 * Thin façade for programmatic use (not required for CLI but kept for structure).
 */
public class TournamentFacade {

    private final TeamManager teamManager;
    private final MatchManager matchManager;
    private final ScheduleManager scheduleManager;
    private final StandingManager standingManager;

    public TournamentFacade(TeamManager tm, MatchManager mm, ScheduleManager sm, StandingManager smg) {
        this.teamManager = tm; this.matchManager = mm; this.scheduleManager = sm; this.standingManager = smg;
    }

    public void addTeam(String name) { teamManager.addTeam(name); }
    public List<Team> getTeams() { return teamManager.getAllTeams(); }

    public List<Match> generateSchedule(ScheduleStrategy strat, MatchType type) {
        scheduleManager.setStrategy(strat);
        return scheduleManager.generateSchedule(getTeams(), matchManager, type);
    }

    public List<Match> getAllMatches() { return matchManager.getAllMatches(); }
    public void reportResult(String matchId, int a, int b) { matchManager.reportResult(matchId, a, b); }
    public List<Standing> getStandings() { return standingManager.computeStandings(getAllMatches()); }
}
