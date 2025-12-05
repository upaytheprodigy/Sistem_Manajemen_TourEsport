package com.myteam.tournament;

import com.myteam.tournament.manager.StandingManager;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Standing;
import com.myteam.tournament.model.Team;
import com.myteam.tournament.util.Repository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StandingManagerTest {

    @Test
    void computeStandingsWorks() {
        Repository<Standing> repo = new Repository<>(Standing::getTeamId);
        StandingManager sm = new StandingManager(repo);

        Team a = new Team("A");
        Team b = new Team("B");

        sm.initialize(List.of(a, b));

        Match m = new Match(a, b) {
            @Override public void play() {}
        };
        m.reportResult(3, 1);

        List<Standing> standings = sm.computeStandings(List.of(m));

        assertEquals(2, standings.size());
        assertEquals("A", standings.get(0).getTeamName());
        assertEquals(3, standings.get(0).getPoints());  // menang
        assertEquals(0, standings.get(1).getPoints());  // kalah
    }
}
