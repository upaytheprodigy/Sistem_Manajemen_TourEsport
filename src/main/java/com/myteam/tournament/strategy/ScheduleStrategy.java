package com.myteam.tournament.strategy;
import com.myteam.tournament.manager.MatchManager;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;
import com.myteam.tournament.factory.MatchType;
import java.util.List;
public interface ScheduleStrategy {
    List<Match> generate(List<Team> teams, MatchManager matchManager, MatchType type);
}
