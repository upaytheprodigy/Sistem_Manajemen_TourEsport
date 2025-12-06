package com.myteam.tournament.strategy;

import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;
import com.myteam.tournament.manager.MatchManager;
import com.myteam.tournament.factory.MatchType;
import java.util.ArrayList;
import java.util.List;

public class KnockoutStrategy implements ScheduleStrategy {
    @Override
    public List<Match> generate(List<Team> teams, MatchManager mm, MatchType type){
        List<Match> out = new ArrayList<>();
        for (int i=0;i+1<teams.size(); i+=2){
            out.add(mm.createMatch(teams.get(i), teams.get(i+1), type));
        }
        return out;
    }
}
