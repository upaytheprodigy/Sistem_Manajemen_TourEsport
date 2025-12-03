package com.myteam.tournament.strategy;

import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;

import java.util.List;

/**
 * Strategy interface for generating list of matches from a list of teams.
 */
public interface MatchFormatStrategy {
    /**
     * Generate matches according to format (e.g., round robin, knockout).
     * Implementations should not modify input list.
     */
    List<Match> generateMatches(List<Team> teams);
}
