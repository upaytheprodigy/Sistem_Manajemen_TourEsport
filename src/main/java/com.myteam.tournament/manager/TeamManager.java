package com.myteam.tournament.manager;

import com.myteam.tournament.exception.TeamNotFoundException;
import com.myteam.tournament.model.Team;
import com.myteam.tournament.util.Repository;

import java.util.List;

/**
 * Handles CRUD operations for teams 
 * using a clean abstraction layer over Repository.
 */
public class TeamManager {

    private final Repository<Team, String> repository;

    public TeamManager(Repository<Team, String> repository) {
        this.repository = repository;
    }

    public void addTeam(String name) {
        Team team = new Team(name);
        repository.add(team);
    }

    public Team getTeam(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new TeamNotFoundException(id));
    }

    public List<Team> getAllTeams() {
        return repository.findAll();
    }
}
