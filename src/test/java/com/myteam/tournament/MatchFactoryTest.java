package com.myteam.tournament;

import com.myteam.tournament.factory.MatchFactory;
import com.myteam.tournament.factory.MatchType;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatchFactoryTest {

    @Test
    void createBO1Match() {
        Team a = new Team("A");
        Team b = new Team("B");

        Match m = MatchFactory.createMatch(MatchType.BO1, a, b);

        assertNotNull(m);
        assertEquals(a, m.getTeamA());
        assertEquals(b, m.getTeamB());
        assertTrue(m.getClass().getSimpleName().contains("BO1"));
    }

    @Test
    void createBO3Match() {
        Team a = new Team("A");
        Team b = new Team("B");

        Match m = MatchFactory.createMatch(MatchType.BO3, a, b);

        assertNotNull(m);
        assertEquals(a, m.getTeamA());
        assertEquals(b, m.getTeamB());
        assertTrue(m.getClass().getSimpleName().contains("BO3"));
    }
}
