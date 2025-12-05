package com.myteam.tournament.strategy;

import com.myteam.tournament.manager.MatchManager;
import com.myteam.tournament.factory.MatchType;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;

import java.util.ArrayList;
import java.util.List;

public class RoundRobinStrategy implements ScheduleStrategy {

    @Override
    public List<Match> generate(List<Team> teams, MatchManager matchManager, MatchType type) {
        List<Match> result = new ArrayList<>();
        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                result.add(matchManager.createMatch(teams.get(i), teams.get(j), type));
            }
        }
        return result;
    }
}
