package com.myteam.tournament.tournament;

import com.myteam.tournament.manager.*;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Standing;
import com.myteam.tournament.model.Team;
import com.myteam.tournament.util.Repository;

import java.util.List;
import java.util.UUID;

public final class TournamentSession {

    private final String id;
    private final String name;

    private final Repository<Team> teamRepo = new Repository<>(Team::getId);
    private final Repository<Match> matchRepo = new Repository<>(Match::getId);
    private final Repository<Standing> standingRepo = new Repository<>(Standing::getTeamId);

    private final TeamManager teamManager;
    private final MatchManager matchManager;
    private final ScheduleManager scheduleManager;
    private final StandingManager standingManager;

    public TournamentSession(String name, ScheduleManager schedule) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.teamManager = new TeamManager(teamRepo);
        this.matchManager = new MatchManager(matchRepo);
        this.scheduleManager = schedule;
        this.standingManager = new StandingManager(standingRepo);
    }

    public String getName() { return name; }
    public TeamManager getTeamManager() { return teamManager; }
    public MatchManager getMatchManager() { return matchManager; }
    public ScheduleManager getScheduleManager() { return scheduleManager; }
    public StandingManager getStandingManager() { return standingManager; }

    public List<Team> exportTeams() { return teamRepo.findAll(); }
    public List<Match> exportMatches() { return matchRepo.findAll(); }

    public void importTeams(List<Team> teams) { teamRepo.overrideAll(teams); }
    public void importMatches(List<Match> matches) { matchRepo.overrideAll(matches); }
}
