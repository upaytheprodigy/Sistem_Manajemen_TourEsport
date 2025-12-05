package com.myteam.tournament;

import com.myteam.tournament.factory.MatchType;
import com.myteam.tournament.manager.MatchManager;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;
import com.myteam.tournament.strategy.RoundRobinStrategy;
import com.myteam.tournament.util.Repository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoundRobinStrategyTest {

    @Test
    void roundRobinProducesNChoose2Matches() {

        int n = 4;
        List<Team> teams = IntStream.range(0, n)
                .mapToObj(i -> new Team("Team" + i))
                .toList();

        // Tambahkan repo untuk matchManager
        Repository<Match> repo = new Repository<>(Match::getId);

        // MatchManager membutuhkan repo
        MatchManager manager = new MatchManager(repo);

        // RoundRobinStrategy TIDAK punya constructor dengan MatchType
        RoundRobinStrategy strat = new RoundRobinStrategy();

        List<Match> matches = strat.generate(teams, manager, MatchType.BO1);

        assertEquals(n * (n - 1) / 2, matches.size());
    }
}
