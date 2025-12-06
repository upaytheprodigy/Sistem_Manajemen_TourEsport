package com.myteam.tournament.manager;

import com.myteam.tournament.strategy.ScheduleStrategy;
import com.myteam.tournament.factory.MatchType;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;
import java.util.List;

public class ScheduleManager {

    private ScheduleStrategy strategy;

    public ScheduleManager(ScheduleStrategy strat){ 
        this.strategy = strat; 
    }

    public void setStrategy(ScheduleStrategy strat){
        this.strategy = strat; 
    }
    
    public List<Match> generateSchedule(List<Team> teams, MatchManager mm, MatchType type){
        return strategy.generate(teams, mm, type);
    }
}
