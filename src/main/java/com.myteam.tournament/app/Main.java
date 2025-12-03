package com.myteam.tournament.app;

import com.myteam.tournament.factory.MatchFactory;
import com.myteam.tournament.factory.MatchType;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;
import com.myteam.tournament.strategy.MatchFormatStrategy;
import com.myteam.tournament.strategy.RoundRobinStrategy;
import com.myteam.tournament.strategy.KnockoutStrategy;
import com.myteam.tournament.util.Repository;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("=== DEMO: CORE ENGINE ANGGOTA A ===");
        System.out.println();

        // ------------------------------
        // 1. Membuat repository team
        // ------------------------------
        Repository<Team, String> teamRepo = new Repository<>(Team::getId);

        Team t1 = new Team("Evos");
        Team t2 = new Team("RRQ");
        Team t3 = new Team("Alter Ego");
        Team t4 = new Team("Bigetron");

        teamRepo.add(t1);
        teamRepo.add(t2);
        teamRepo.add(t3);
        teamRepo.add(t4);

        System.out.println("Daftar Tim:");
        teamRepo.findAll().forEach(team ->
                System.out.println(" - " + team.getName()));
        System.out.println();

        // ------------------------------
        // 2. Round Robin Strategy
        // ------------------------------
        System.out.println("=== ROUND ROBIN (BO1) ===");
        MatchFormatStrategy rrStrategy = new RoundRobinStrategy(MatchType.BO1);
        List<Match> rrMatches = rrStrategy.generateMatches(teamRepo.findAll());

        for (Match m : rrMatches) {
            System.out.println(m.getTeamA().getName() + " vs " + m.getTeamB().getName());
        }
        System.out.println();

        // ------------------------------
        // 3. Knockout Strategy
        // ------------------------------
        System.out.println("=== KNOCKOUT (BO3) ===");
        MatchFormatStrategy koStrategy = new KnockoutStrategy(MatchType.BO3);
        List<Match> koMatches = koStrategy.generateMatches(teamRepo.findAll());

        for (Match m : koMatches) {
            System.out.println(m.getTeamA().getName() + " vs " + m.getTeamB().getName());
        }
        System.out.println();

        // ------------------------------
        // 4. Testing MatchFactory & BO1 play
        // ------------------------------
        System.out.println("=== TEST BO1 MATCH SIMULATION ===");
        Match bo1 = MatchFactory.createMatch(MatchType.BO1, t1, t2);
        bo1.play(); // simulation
        System.out.println(bo1);
        System.out.println();

        // ------------------------------
        // 5. Testing BO3 match
        // ------------------------------
        System.out.println("=== TEST BO3 MATCH SIMULATION ===");
        Match bo3 = MatchFactory.createMatch(MatchType.BO3, t3, t4);
        bo3.play();
        System.out.println(bo3);
        System.out.println();

        System.out.println("=== END OF DEMO ===");
    }
}
