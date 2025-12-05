package com.myteam.tournament.exception;

public class MatchNotFoundException extends RuntimeException {
    public MatchNotFoundException(String id) { super("Match not found: " + id); }
}
