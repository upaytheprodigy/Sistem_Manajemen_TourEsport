package com.myteam.tournament.model;
import java.util.UUID;
public class Team {
    private final String id;
    private String name;
    public Team(String name) { this.id = UUID.randomUUID().toString(); this.name = name; }
    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    @Override public String toString() { return name; }
}
