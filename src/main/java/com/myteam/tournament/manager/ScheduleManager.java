package com.myteam.tournament.manager;

import com.myteam.tournament.factory.MatchType;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;
import com.myteam.tournament.strategy.MatchFormatStrategy;

import java.util.List;

/**
 * Generates schedules using strategy pattern (RoundRobin / Knockout).
 */
public class ScheduleManager {

    private MatchFormatStrategy strategy;

    public ScheduleManager(MatchFormatStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(MatchFormatStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Match> generateSchedule(List<Team> teams, MatchManager manager, MatchType type) {
        List<Match> matches = strategy.generateMatches(teams);
        for (Match m : matches) {
            manager.createMatch(m.getTeamA(), m.getTeamB(), type);
        }
        return matches;
    }
}
