package com.myteam.tournament.app;

import com.myteam.tournament.exception.TournamentException;
import com.myteam.tournament.facade.TournamentFacade;
import com.myteam.tournament.factory.MatchType;
import com.myteam.tournament.manager.*;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;
import com.myteam.tournament.strategy.KnockoutStrategy;
import com.myteam.tournament.strategy.MatchFormatStrategy;
import com.myteam.tournament.strategy.RoundRobinStrategy;
import com.myteam.tournament.util.Repository;

import java.util.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // Repositories
        Repository<Team, String> teamRepo = new Repository<>(Team::getId);
        Repository<Match, String> matchRepo = new Repository<>(Match::getId);

        // Managers
        TeamManager teamManager = new TeamManager(teamRepo);
        MatchManager matchManager = new MatchManager(matchRepo);
        ScheduleManager scheduleManager = new ScheduleManager(new RoundRobinStrategy(MatchType.BO1));
        StandingManager standingManager = new StandingManager();

        TournamentFacade facade = new TournamentFacade(
                teamManager,
                matchManager,
                scheduleManager,
                standingManager
        );

        while (true) {
            System.out.println("\n=== ESPORT TOURNAMENT MENU ===");
            System.out.println("1. Tambah Tim");
            System.out.println("2. Lihat Semua Tim");
            System.out.println("3. Generate Jadwal Round Robin");
            System.out.println("4. Generate Jadwal Knockout");
            System.out.println("5. Lihat Semua Match");
            System.out.println("6. Input Hasil Match");
            System.out.println("7. Lihat Klasemen");
            System.out.println("0. Keluar");
            System.out.print("Pilihan: ");

            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "1" -> addTeam(facade);
                    case "2" -> listTeams(facade);
                    case "3" -> generateSchedule(facade, true);
                    case "4" -> generateSchedule(facade, false);
                    case "5" -> listMatches(facade);
                    case "6" -> reportResult(facade);
                    case "7" -> showStandings(facade);
                    case "0" -> System.exit(0);
                    default -> System.out.println("Pilihan tidak valid.");
                }
            } catch (TournamentException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private static void addTeam(TournamentFacade facade) {
        System.out.print("Nama tim: ");
        String name = scanner.nextLine();
        facade.addTeam(name);
        System.out.println("Tim berhasil ditambahkan!");
    }

    private static void listTeams(TournamentFacade facade) {
        List<Team> teams = facade.getTeams();
        if (teams.isEmpty()) {
            System.out.println("Belum ada tim.");
            return;
        }
        System.out.println("Daftar Tim:");
        teams.forEach(t -> System.out.println(t.getId() + " - " + t.getName()));
    }

    private static void generateSchedule(TournamentFacade facade, boolean isRoundRobin) {
        MatchFormatStrategy strategy =
                isRoundRobin
                        ? new RoundRobinStrategy(MatchType.BO1)
                        : new KnockoutStrategy(MatchType.BO3);

        List<Match> matches = facade.generateSchedule(strategy,
                isRoundRobin ? MatchType.BO1 : MatchType.BO3);

        System.out.println("Jadwal berhasil dibuat!");
        matches.forEach(m ->
                System.out.println(m.getId() + ": " + m.getTeamA().getName() + " vs " + m.getTeamB().getName())
        );
    }

    private static void listMatches(TournamentFacade facade) {
        List<Match> matches = facade.getAllMatches();
        if (matches.isEmpty()) {
            System.out.println("Belum ada match.");
            return;
        }
        for (Match m : matches) {
            System.out.println(m);
        }
    }

    private static void reportResult(TournamentFacade facade) {
        System.out.print("ID Match: ");
        String id = scanner.nextLine();
        System.out.print("Skor A: ");
        int a = Integer.parseInt(scanner.nextLine());
        System.out.print("Skor B: ");
        int b = Integer.parseInt(scanner.nextLine());
        facade.reportResult(id, a, b);
        System.out.println("Hasil berhasil disimpan!");
    }

    private static void showStandings(TournamentFacade facade) {
        Map<Team, Integer> standings = facade.getStandings();
        if (standings.isEmpty()) {
            System.out.println("Belum ada klasemen.");
            return;
        }
        standings.forEach((t, p) ->
                System.out.println(t.getName() + " - " + p + " pts"));
    }
}
