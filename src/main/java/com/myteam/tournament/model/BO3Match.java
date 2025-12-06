package com.myteam.tournament.model;

public class BO3Match extends Match {
    public BO3Match(Team a, Team b){ super(a,b); }
    @Override public void reportResult(int a,int b){ super.reportResult(a,b); }
}
