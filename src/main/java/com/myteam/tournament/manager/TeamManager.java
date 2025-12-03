package com.myteam.tournament.manager;

import com.myteam.tournament.exception.DuplicateTeamException;
import com.myteam.tournament.exception.TeamNotFoundException;
import com.myteam.tournament.model.Team;
import com.myteam.tournament.util.Repository;

import java.util.List;

public class TeamManager {

    private final Repository<Team> repo;

    public TeamManager(Repository<Team> repo) { this.repo = repo; }

    public void addTeam(String name) {
        boolean exists = repo.findAll().stream().anyMatch(t -> t.getName().equalsIgnoreCase(name));
        if (exists) throw new DuplicateTeamException(name);
        repo.add(new Team(name));
    }

    public Team getTeam(String id) {
        return repo.findById(id).orElseThrow(() -> new TeamNotFoundException(id));
    }

    public List<Team> getAllTeams() { return repo.findAll(); }

    public void setAllTeams(List<Team> teams) { repo.overrideAll(teams); }
}
