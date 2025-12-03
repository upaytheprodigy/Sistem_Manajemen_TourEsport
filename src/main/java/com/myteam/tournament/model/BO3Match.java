package com.myteam.tournament.model;

public class BO3Match extends Match {
    public BO3Match(Team a, Team b) { super(a,b); }
    @Override public void play() { /* optional simulate best-of-3 logic */ }
}
