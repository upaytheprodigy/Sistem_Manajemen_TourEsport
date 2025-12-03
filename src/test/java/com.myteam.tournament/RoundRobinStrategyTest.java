package com.myteam.tournament;

import com.myteam.tournament.factory.MatchType;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;
import com.myteam.tournament.strategy.RoundRobinStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoundRobinStrategyTest {

    @Test
    void roundRobinProducesNChoose2Matches() {
        int n = 4;
        List<Team> teams = IntStream.range(0, n)
            .mapToObj(i -> new Team("Team" + i)).toList();

        RoundRobinStrategy strat = new RoundRobinStrategy(MatchType.BO1);
        List<Match> matches = strat.generateMatches(teams);
        assertEquals(n * (n - 1) / 2, matches.size());
    }
}
