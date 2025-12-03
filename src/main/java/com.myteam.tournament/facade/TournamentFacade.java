package com.myteam.tournament.facade;

import com.myteam.tournament.factory.MatchType;
import com.myteam.tournament.manager.*;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;
import com.myteam.tournament.strategy.MatchFormatStrategy;

import java.util.List;
import java.util.Map;

/**
 * Facade simplifying the entire tournament operation.
 */
public class TournamentFacade {

    private final TeamManager teamManager;
    private final MatchManager matchManager;
    private final ScheduleManager scheduleManager;
    private final StandingManager standingManager;

    public TournamentFacade(
            TeamManager teamManager,
            MatchManager matchManager,
            ScheduleManager scheduleManager,
            StandingManager standingManager) {

        this.teamManager = teamManager;
        this.matchManager = matchManager;
        this.scheduleManager = scheduleManager;
        this.standingManager = standingManager;
    }

    public void addTeam(String name) {
        teamManager.addTeam(name);
    }

    public List<Team> getTeams() {
        return teamManager.getAllTeams();
    }

    public List<Match> generateSchedule(MatchFormatStrategy strategy, MatchType type) {
        scheduleManager.setStrategy(strategy);
        return scheduleManager.generateSchedule(getTeams(), matchManager, type);
    }

    public List<Match> getAllMatches() {
        return matchManager.getAllMatches();
    }

    public void reportResult(String matchId, int scoreA, int scoreB) {
        matchManager.reportResult(matchId, scoreA, scoreB);
    }

    public Map<Team, Integer> getStandings() {
        return standingManager.computeStandings(getAllMatches());
    }
}
