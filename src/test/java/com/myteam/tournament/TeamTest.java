package com.myteam.tournament;

import com.myteam.tournament.model.Team;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TeamTest {

    @Test
    void testTeamName() {
        Team t = new Team("Alpha");
        assertEquals("Alpha", t.getName());

        t.setName("Beta");
        assertEquals("Beta", t.getName());
    }
}
