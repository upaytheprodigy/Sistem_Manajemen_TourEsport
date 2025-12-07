package com.myteam.tournament.manager;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.factory.MatchType;
import com.myteam.tournament.strategy.ScheduleStrategy;
import java.util.List;

public class ScheduleManager {
    private ScheduleStrategy strategy;
    public ScheduleManager(ScheduleStrategy strategy){ this.strategy = strategy; }
    public void setStrategy(ScheduleStrategy s){ this.strategy = s; }
    public List<Match> generate(List<com.myteam.tournament.model.Team> teams, MatchManager mm, MatchType type){
        return strategy.generate(teams, mm, type);
    }
}
