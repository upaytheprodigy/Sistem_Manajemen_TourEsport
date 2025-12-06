package com.myteam.tournament.model;

import java.util.Optional;
import java.util.UUID;
import java.util.Objects;

public abstract class Match {
    private final String id;
    protected final Team teamA;
    protected final Team teamB;
    protected Result result;

    protected Match(Team a, Team b) {
        this.id = UUID.randomUUID().toString();
        this.teamA = Objects.requireNonNull(a); this.teamB = Objects.requireNonNull(b);
    }

    public String getId(){ return id; }
    public Team getTeamA(){ return teamA; }
    public Team getTeamB(){ return teamB; }
    public Optional<Result> getResult(){ return Optional.ofNullable(result); }
   
    public void reportResult(int a,int b){ 
        this.result = new Result(a,b); 
    }
    
    public String toDisplayString(){
        String r = result==null ? "-" : (result.getScoreA()+"-"+result.getScoreB());
        return teamA.getName() + " vs " + teamB.getName() + " -> " + r;
    }
}
