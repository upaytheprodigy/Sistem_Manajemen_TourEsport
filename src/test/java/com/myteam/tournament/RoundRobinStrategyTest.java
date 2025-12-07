package com.myteam.tournament;

import com.myteam.tournament.factory.MatchType;
import com.myteam.tournament.manager.MatchManager;
import com.myteam.tournament.model.Team;
import com.myteam.tournament.strategy.RoundRobinStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoundRobinStrategyTest {

    @Test
    public void testGenerate() {
        RoundRobinStrategy strat = new RoundRobinStrategy();
        MatchManager matchManager = new MatchManager(null);

        List<Team> teams = List.of(
                new Team("A"),
                new Team("B"),
                new Team("C")
        );

        var matches = strat.generate(teams, matchManager, MatchType.BO1);

        // Round robin menghasilkan n(n-1)/2 = 3 pertandingan
        assertEquals(3, matches.size());
    }
}
