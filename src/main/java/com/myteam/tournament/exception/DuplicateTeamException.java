package com.myteam.tournament.exception;

public class DuplicateTeamException extends RuntimeException {
    public DuplicateTeamException(String name) 
    { super("Nama tim sudah digunakan: " + name); }
}
