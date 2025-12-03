package com.myteam.tournament.factory;

import com.myteam.tournament.model.*;

public final class MatchFactory {
    private MatchFactory() {}
    public static Match createMatch(MatchType type, Team a, Team b) {
        return switch (type) {
            case BO3 -> new BO3Match(a,b);
            case BO1 -> new BO1Match(a,b);
        };
    }
}
