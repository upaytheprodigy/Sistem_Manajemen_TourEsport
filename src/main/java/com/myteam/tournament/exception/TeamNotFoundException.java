package com.myteam.tournament.exception;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(String id) { super("Team not found: " + id); }
}
