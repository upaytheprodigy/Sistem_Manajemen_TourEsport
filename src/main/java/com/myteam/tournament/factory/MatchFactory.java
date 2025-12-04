package com.myteam.tournament.factory;

import com.myteam.tournament.model.BO1Match;
import com.myteam.tournament.model.BO3Match;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;
import java.util.Objects;

/**
 * Simple factory for creating Match instances.
 * Centralizes creation logic and hides concrete classes.
 */
public final class MatchFactory {

    private MatchFactory() { /* utility */ }

    public static Match createMatch(MatchType type, Team a, Team b) {
        Objects.requireNonNull(type, "type required");
        Objects.requireNonNull(a, "team a required");
        Objects.requireNonNull(b, "team b required");

        return switch (type) {
            case BO3 -> new BO3Match(a, b);
            case BO1 -> new BO1Match(a, b);
        };
    }
}
