package com.myteam.tournament.model;

import com.myteam.tournament.factory.MatchType;

public class BO3Match extends Match {
    public BO3Match(Team a, Team b){ super(a, b, "id"); }
    @Override public void reportResult(int a, int b){ super.reportResult(a,b); }
}
