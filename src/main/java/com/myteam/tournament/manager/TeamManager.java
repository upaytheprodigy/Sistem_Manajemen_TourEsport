package com.myteam.tournament.manager;
import com.myteam.tournament.model.Team;
import com.myteam.tournament.util.Repository;
import java.util.List;
public class TeamManager {
    private final Repository<Team> repo;
    public TeamManager(Repository<Team> repo){ this.repo = repo; }
    public void addTeam(String name){ repo.add(new Team(name)); }
    public List<Team> getAllTeams(){ return repo.findAll(); }
}
