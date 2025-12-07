package com.myteam.tournament.strategy;
import com.myteam.tournament.factory.*;
import com.myteam.tournament.manager.MatchManager;
import com.myteam.tournament.model.*;
import java.util.ArrayList;
import java.util.List;
public class KnockoutStrategy implements ScheduleStrategy {
    @Override
    public List<Match> generate(List<Team> teams, MatchManager matchManager, MatchType type) {
        // Simple bracket: pair sequential
        List<Match> out = new ArrayList<>();
        for(int i=0;i+1<teams.size();i+=2){
            out.add(matchManager.createMatch(teams.get(i), teams.get(i+1), type));
        }
        return out;
    }
}
