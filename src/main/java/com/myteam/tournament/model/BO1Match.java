package com.myteam.tournament.model;
public class BO1Match extends Match {
    public BO1Match(Team a, Team b){ super(a,b, "BO1"); }
    @Override public void reportResult(int a, int b){ super.reportResult(a,b); }
}
