package com.myteam.tournament.app;

import com.myteam.tournament.factory.MatchType;
import com.myteam.tournament.manager.*;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;
import com.myteam.tournament.model.Standing;
import com.myteam.tournament.strategy.KnockoutStrategy;
import com.myteam.tournament.strategy.RoundRobinStrategy;
import com.myteam.tournament.storage.JsonStorage;
import com.myteam.tournament.storage.LogService;
import com.myteam.tournament.tournament.TournamentSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * CLI entrypoint.
 */
public class MainCLI {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Map<String, TournamentSession> sessions = new LinkedHashMap<>();
    private static TournamentSession current = null;
    private static final JsonStorage storage = new JsonStorage();

    public static void main(String[] args) {
        ensureBaseFolder();
        loopMainMenu();
    }

    private static void ensureBaseFolder() {
        try { Files.createDirectories(Path.of("tournament-data")); } catch (IOException ignored) {}
    }

    private static void loopMainMenu() {
        while (true) {
            System.out.println("\n=== TOURNAMENT MANAGER (MULTI SESSION) ===");
            System.out.println("1. Buat Tournament Baru");
            System.out.println("2. Pilih Tournament");
            System.out.println("3. Hapus Tournament");
            System.out.println("4. List Tournament");
            System.out.println("0. Exit");
            System.out.print("Pilihan: ");
            switch (scanner.nextLine().trim()) {
                case "1" -> createTournament();
                case "2" -> pickTournament();
                case "3" -> deleteTournament();
                case "4" -> listTournaments();
                case "0" -> System.exit(0);
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void createTournament() {
        System.out.print("Nama tournament: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) { System.out.println("Nama tidak boleh kosong."); return; }
        if (sessions.containsKey(name)) { System.out.println("Nama sudah ada."); return; }

        ScheduleManager scheduleManager = new ScheduleManager(new RoundRobinStrategy());
        TournamentSession s = new TournamentSession(name, scheduleManager);
        sessions.put(name, s);
        current = s;
        LogService.write(name, "Tournament created");
        System.out.println("Tournament '" + name + "' dibuat dan dipilih aktif.");
        loopTournamentMenu();
    }

    private static void pickTournament() {
        if (sessions.isEmpty()) { System.out.println("Belum ada tournament."); return; }
        System.out.println("Pilih tournament:");
        List<String> keys = new ArrayList<>(sessions.keySet());
        for (int i=0;i<keys.size();i++) System.out.printf("%d. %s%n", i+1, keys.get(i));
        System.out.print("Pilihan: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (idx < 0 || idx >= keys.size()) { System.out.println("Pilihan invalid"); return; }
            current = sessions.get(keys.get(idx));
            System.out.println("Selected: " + current.getName());
            loopTournamentMenu();
        } catch (NumberFormatException e) { System.out.println("Input angka yang benar."); }
    }

    private static void deleteTournament() {
        System.out.print("Nama tournament hapus: ");
        String name = scanner.nextLine().trim();
        if (!sessions.containsKey(name)) { System.out.println("Tidak ditemukan."); return; }
        sessions.remove(name);
        try { Files.walk(Path.of("tournament-data", name)).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(java.io.File::delete);
        } catch (IOException ignored) {}
        LogService.write(name, "Tournament deleted");
        System.out.println("Dihapus.");
    }

    private static void listTournaments() { sessions.keySet().forEach(n -> System.out.println(" - " + n)); }

    private static void loopTournamentMenu() {
        if (current == null) return;
        while (true) {
            System.out.println("\n=== ESPORT: " + current.getName() + " ===");
            System.out.println("1. Tambah Tim");
            System.out.println("2. Lihat Tim");
            System.out.println("3. Generate RoundRobin (BO1)");
            System.out.println("4. Generate Knockout (BO3)");
            System.out.println("5. Lihat Match");
            System.out.println("6. Input Hasil Match");
            System.out.println("7. Lihat Klasemen");
            System.out.println("8. Save Tournament");
            System.out.println("9. Load Tournament");
            System.out.println("0. Kembali");
            System.out.print("Pilihan: ");
            String opt = scanner.nextLine().trim();
            try {
                switch (opt) {
                    case "1" -> addTeam();
                    case "2" -> listTeams();
                    case "3" -> generateSchedule(true);
                    case "4" -> generateSchedule(false);
                    case "5" -> listMatches();
                    case "6" -> reportResult();
                    case "7" -> showStandings();
                    case "8" -> saveCurrent();
                    case "9" -> loadCurrent();
                    case "0" -> { current = null; return; }
                    default -> System.out.println("Pilihan invalid.");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private static void addTeam() {
        System.out.print("Nama tim: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) { System.out.println("Kosong."); return; }
        try {
            current.getTeamManager().addTeam(name);
            current.getStandingManager().initialize(current.getTeamManager().getAllTeams());
            LogService.write(current.getName(), "Added team: " + name);
            System.out.println("Tim ditambahkan.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listTeams() {
        var teams = current.getTeamManager().getAllTeams();
        if (teams.isEmpty()) { System.out.println("Belum ada tim."); return; }
        teams.forEach(t -> System.out.println(t.getId() + " - " + t.getName()));
    }

    private static void generateSchedule(boolean roundRobin) {
        if (roundRobin) {
            current.getScheduleManager().setStrategy(new com.myteam.tournament.strategy.RoundRobinStrategy());
            var matches = current.getScheduleManager().generateSchedule(current.getTeamManager().getAllTeams(), current.getMatchManager(), MatchType.BO1);
            System.out.println("RoundRobin dibuat: " + matches.size());
            LogService.write(current.getName(), "Generated RoundRobin: " + matches.size());
        } else {
            current.getScheduleManager().setStrategy(new com.myteam.tournament.strategy.KnockoutStrategy());
            var matches = current.getScheduleManager().generateSchedule(current.getTeamManager().getAllTeams(), current.getMatchManager(), MatchType.BO3);
            System.out.println("Knockout first round dibuat: " + matches.size());
            LogService.write(current.getName(), "Generated Knockout: " + matches.size());
        }
    }

    private static void listMatches() {
        var matches = current.getMatchManager().getAllMatches();
        if (matches.isEmpty()) { System.out.println("Belum ada match."); return; }
        matches.forEach(m -> System.out.println(m.getId() + " : " + m.getTeamA().getName() + " vs " + m.getTeamB().getName() +
                (m.getResult().isPresent() ? " -> " + m.getResult().get() : " -> -")));
    }

    private static void reportResult() {
        List<Match> matches = current.getMatchManager().getAllMatches();

        // Filter match yang belum selesai
        List<Match> unfinished = matches.stream()
                .filter(m -> m.getResult().isEmpty())
                .toList();

        if (unfinished.isEmpty()) {
            System.out.println("Semua match sudah memiliki hasil.");
            return;
        }

        System.out.println("\nPilih match yang ingin diinput hasilnya:");
        for (int i = 0; i < unfinished.size(); i++) {
            Match m = unfinished.get(i);
            System.out.printf(
                    "%d) %s vs %s%n",
                    i + 1,
                    m.getTeamA().getName(),
                    m.getTeamB().getName()
            );
        }

        System.out.print("Pilih nomor: ");
        int idx = Integer.parseInt(scanner.nextLine()) - 1;

        if (idx < 0 || idx >= unfinished.size()) {
            System.out.println("Pilihan tidak valid.");
            return;
        }

        Match m = unfinished.get(idx);

        System.out.println("Input skor:");
        System.out.print(m.getTeamA().getName() + ": ");
        int scoreA = Integer.parseInt(scanner.nextLine());

        System.out.print(m.getTeamB().getName() + ": ");
        int scoreB = Integer.parseInt(scanner.nextLine());

        current.getMatchManager().reportResult(m.getId(), scoreA, scoreB);
        System.out.println("Hasil berhasil disimpan!");
    }

    private static void showStandings() {
        List<Standing> standings = current.getStandingManager().computeStandings(current.getMatchManager().getAllMatches());
        if (standings == null || standings.isEmpty()) { System.out.println("Belum ada klasemen."); return; }
        System.out.println("=== KLASSEMEN ===");
        int i=1;
        for (Standing s : standings) {
            System.out.printf("%d. %s - %d pts (W:%d D:%d L:%d)%n", i++, s.getTeamName(), s.getPoints(), s.getWins(), s.getDraws(), s.getLosses());
        }
    }

    private static void saveCurrent() {
        String base = Path.of("tournament-data", current.getName()).toString();
        try {
            storage.save(base + "/teams.json", current.exportTeams());
            storage.save(base + "/matches.json", current.exportMatches());
            LogService.write(current.getName(), "Saved tournament");
            System.out.println("Saved.");
        } catch (IOException e) { System.out.println("Gagal simpan: " + e.getMessage()); }
    }

    private static void loadCurrent() {
        String base = Path.of("tournament-data", current.getName()).toString();
        try {
            var teams = storage.loadArray(base + "/teams.json", com.myteam.tournament.model.Team[].class);
            var matches = storage.loadArray(base + "/matches.json", com.myteam.tournament.model.Match[].class);
            current.importTeams(teams == null ? List.of() : List.of(teams));
            current.importMatches(matches == null ? List.of() : List.of(matches));
            current.getStandingManager().initialize(current.getTeamManager().getAllTeams());
            LogService.write(current.getName(), "Loaded tournament");
            System.out.println("Loaded.");
        } catch (IOException e) { System.out.println("Gagal muat: " + e.getMessage()); }
    }
}
