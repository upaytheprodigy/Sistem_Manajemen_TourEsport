package com.myteam.tournament.app;

import com.myteam.tournament.factory.MatchType;
import com.myteam.tournament.manager.ScheduleManager;
import com.myteam.tournament.manager.StandingManager;
import com.myteam.tournament.manager.MatchManager;
import com.myteam.tournament.manager.TeamManager;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.model.Team;
import com.myteam.tournament.strategy.KnockoutStrategy;
import com.myteam.tournament.strategy.RoundRobinStrategy;
import com.myteam.tournament.storage.JsonStorage;
import com.myteam.tournament.storage.LogService;
import com.myteam.tournament.tournament.TournamentSession;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Simple Swing GUI front-end for the tournament manager.
 * It delegates all business logic to TournamentSession and managers.
 */
public class MainGUI extends JFrame {

    private final DefaultListModel<String> sessionsModel = new DefaultListModel<>();
    private final JList<String> sessionsList = new JList<>(sessionsModel);

    private TournamentSession current = null;
    private final JsonStorage storage = new JsonStorage();

    // center panels
    private final DefaultListModel<String> teamsModel = new DefaultListModel<>();
    private final JList<String> teamsList = new JList<>(teamsModel);

    private final DefaultListModel<String> matchesModel = new DefaultListModel<>();
    private final JList<String> matchesList = new JList<>(matchesModel);

    private final DefaultListModel<String> standingsModel = new DefaultListModel<>();
    private final JList<String> standingsList = new JList<>(standingsModel);

    public MainGUI() {
        super("Esport Tournament Manager - GUI");
        ensureBaseFolder();
        initUi();
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitSafely();
            }
        });
    }

    private void ensureBaseFolder() {
        try { Files.createDirectories(Path.of("tournament-data")); } catch (IOException ignored) {}
    }

    private void initUi() {
        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(10,10,10,10));
        setContentPane(root);

        // left: sessions list + controls
        JPanel left = new JPanel(new BorderLayout(5,5));
        left.setPreferredSize(new Dimension(240, 0));
        left.add(new JLabel("Tournaments"), BorderLayout.NORTH);
        sessionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        left.add(new JScrollPane(sessionsList), BorderLayout.CENTER);

        JPanel leftButtons = new JPanel(new GridLayout(0,1,5,5));
        JButton createBtn = new JButton("Create Tournament");
        JButton pickBtn = new JButton("Select Tournament");
        JButton deleteBtn = new JButton("Delete Tournament");
        JButton refreshBtn = new JButton("Refresh");
        leftButtons.add(createBtn);
        leftButtons.add(pickBtn);
        leftButtons.add(deleteBtn);
        leftButtons.add(refreshBtn);
        left.add(leftButtons, BorderLayout.SOUTH);

        // center: teams / matches / standings
        JPanel center = new JPanel(new GridLayout(1,3,10,10));
        // teams panel
        JPanel pTeams = new JPanel(new BorderLayout(5,5));
        pTeams.add(new JLabel("Teams"), BorderLayout.NORTH);
        pTeams.add(new JScrollPane(teamsList), BorderLayout.CENTER);
        JPanel teamsBtns = new JPanel(new GridLayout(0,1,5,5));
        JButton addTeamBtn = new JButton("Add Team");
        JButton listTeamsBtn = new JButton("Refresh Teams");
        teamsBtns.add(addTeamBtn);
        teamsBtns.add(listTeamsBtn);
        pTeams.add(teamsBtns, BorderLayout.SOUTH);

        // matches panel
        JPanel pMatches = new JPanel(new BorderLayout(5,5));
        pMatches.add(new JLabel("Matches"), BorderLayout.NORTH);
        pMatches.add(new JScrollPane(matchesList), BorderLayout.CENTER);
        JPanel matchesBtns = new JPanel(new GridLayout(0,1,5,5));
        JButton genRRBtn = new JButton("Generate RoundRobin (BO1)");
        JButton genKOBtn = new JButton("Generate Knockout (BO3)");
        JButton reportBtn = new JButton("Input Result");
        JButton listMatchesBtn = new JButton("Refresh Matches");
        matchesBtns.add(genRRBtn);
        matchesBtns.add(genKOBtn);
        matchesBtns.add(listMatchesBtn);
        matchesBtns.add(reportBtn);
        pMatches.add(matchesBtns, BorderLayout.SOUTH);

        // standings panel
        JPanel pStand = new JPanel(new BorderLayout(5,5));
        pStand.add(new JLabel("Standings"), BorderLayout.NORTH);
        pStand.add(new JScrollPane(standingsList), BorderLayout.CENTER);
        JPanel standBtns = new JPanel(new GridLayout(0,1,5,5));
        JButton showStandBtn = new JButton("Compute Standings");
        JButton refreshStandBtn = new JButton("Refresh Standings");
        standBtns.add(showStandBtn);
        standBtns.add(refreshStandBtn);
        pStand.add(standBtns, BorderLayout.SOUTH);

        center.add(pTeams);
        center.add(pMatches);
        center.add(pStand);

        // bottom: save/load
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton saveBtn = new JButton("Save Tournament");
        JButton loadBtn = new JButton("Load Tournament");
        JButton exitBtn = new JButton("Exit (close)");
        bottom.add(saveBtn);
        bottom.add(loadBtn);
        bottom.add(exitBtn);

        root.add(left, BorderLayout.WEST);
        root.add(center, BorderLayout.CENTER);
        root.add(bottom, BorderLayout.SOUTH);

        // actions
        refreshBtn.addActionListener(e -> refreshSessionList());
        createBtn.addActionListener(e -> onCreateTournament());
        pickBtn.addActionListener(e -> onPickTournament());
        deleteBtn.addActionListener(e -> onDeleteTournament());

        addTeamBtn.addActionListener(e -> onAddTeam());
        listTeamsBtn.addActionListener(e -> refreshTeams());

        genRRBtn.addActionListener(e -> generateSchedule(true));
        genKOBtn.addActionListener(e -> generateSchedule(false));
        listMatchesBtn.addActionListener(e -> refreshMatches());
        reportBtn.addActionListener(e -> onReportResult());

        showStandBtn.addActionListener(e -> computeStandings());
        refreshStandBtn.addActionListener(e -> refreshStandings());

        saveBtn.addActionListener(e -> onSave());
        loadBtn.addActionListener(e -> onLoad());
        exitBtn.addActionListener(e -> exitSafely());

        // initial load
        refreshSessionList();
    }

    private void refreshSessionList() {
        sessionsModel.clear();
        try {
            Files.list(Path.of("tournament-data"))
                .filter(Files::isDirectory)
                .map(Path::getFileName)
                .map(Path::toString)
                .sorted()
                .forEach(sessionsModel::addElement);
        } catch (IOException ignored) {}
    }

    private void onCreateTournament() {
        String name = JOptionPane.showInputDialog(this, "Nama tournament:");
        if (name == null) return;
        name = name.trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong");
            return;
        }
        // create new session
        ScheduleManager scheduleManager = new ScheduleManager(new RoundRobinStrategy());
        TournamentSession s = new TournamentSession(name, scheduleManager);
        current = s;
        // persist empty folder (just create folder)
        try { Files.createDirectories(Path.of("tournament-data", name)); } catch (IOException ignored) {}
        LogService.write(name, "Tournament created (GUI)");
        refreshSessionList();
        sessionsList.setSelectedValue(name, true);
        JOptionPane.showMessageDialog(this, "Tournament '" + name + "' dibuat dan dipilih.");
        refreshAllViews();
    }

    private void onPickTournament() {
        String sel = sessionsList.getSelectedValue();
        if (sel == null) { JOptionPane.showMessageDialog(this, "Pilih tournament dari kiri."); return; }
        // attempt to load session from disk if exists
        current = new TournamentSession(sel, new ScheduleManager(new RoundRobinStrategy()));
        // try load existing data
        try {
            String base = Path.of("tournament-data", sel).toString();
            var teams = storage.loadArray(base + "/teams.json", Team[].class);
            var matches = storage.loadArray(base + "/matches.json", Match[].class);
            current.importTeams(teams == null ? java.util.List.of() : java.util.Arrays.asList(teams));
            current.importMatches(matches == null ? java.util.List.of() : java.util.Arrays.asList(matches));
        } catch (Exception ex) {
            // ignore but notify
            System.err.println("Load: " + ex.getMessage());
        }
        LogService.write(sel, "Tournament selected (GUI)");
        refreshAllViews();
    }

    private void onDeleteTournament() {
        String sel = sessionsList.getSelectedValue();
        if (sel == null) { JOptionPane.showMessageDialog(this, "Pilih tournament dahulu."); return; }
        int confirm = JOptionPane.showConfirmDialog(this, "Hapus tournament '" + sel + "'? (folder akan dihapus)");
        if (confirm != JOptionPane.YES_OPTION) return;
        try {
            Files.walk(Path.of("tournament-data", sel))
                .sorted((a,b)->b.compareTo(a))
                .map(Path::toFile)
                .forEach(java.io.File::delete);
        } catch (IOException ignored) {}
        LogService.write(sel, "Tournament deleted (GUI)");
        if (current != null && current.getName().equals(sel)) current = null;
        refreshSessionList();
        refreshAllViews();
    }

    private void onAddTeam() {
        if (current == null) { JOptionPane.showMessageDialog(this, "Pilih tournament dulu."); return; }
        String name = JOptionPane.showInputDialog(this, "Nama tim:");
        if (name == null) return;
        name = name.trim();
        if (name.isEmpty()) { JOptionPane.showMessageDialog(this, "Nama kosong."); return; }
        try {
            current.getTeamManager().addTeam(name);
            LogService.write(current.getName(), "Added team (GUI): " + name);
            refreshTeams();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal: " + ex.getMessage());
        }
    }

    private void refreshTeams() {
        teamsModel.clear();
        if (current == null) return;
        current.getTeamManager().getAllTeams().forEach(t -> teamsModel.addElement(t.getId() + " - " + t.getName()));
    }

    private void generateSchedule(boolean roundRobin) {
        if (current == null) { JOptionPane.showMessageDialog(this, "Pilih tournament dulu."); return; }
        try {
            if (roundRobin) {
                current.getScheduleManager().setStrategy(new RoundRobinStrategy());
                var matches = current.getScheduleManager().generateSchedule(current.getTeamManager().getAllTeams(), current.getMatchManager(), MatchType.BO1);
                JOptionPane.showMessageDialog(this, "RoundRobin dibuat: " + matches.size() + " match");
                LogService.write(current.getName(), "Generated RoundRobin: " + matches.size() + " matches (GUI)");
            } else {
                current.getScheduleManager().setStrategy(new KnockoutStrategy());
                var matches = current.getScheduleManager().generateSchedule(current.getTeamManager().getAllTeams(), current.getMatchManager(), MatchType.BO3);
                JOptionPane.showMessageDialog(this, "Knockout dibuat: " + matches.size() + " match");
                LogService.write(current.getName(), "Generated Knockout first round: " + matches.size() + " matches (GUI)");
            }
            refreshMatches();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal generate: " + ex.getMessage());
        }
    }

    private void refreshMatches() {
        matchesModel.clear();
        if (current == null) return;
        current.getMatchManager().getAllMatches()
                .forEach(m -> matchesModel.addElement(m.getId() + ": " + sid(m.getTeamA()) + " vs " + sid(m.getTeamB()) + " -> " + m.getResult().map(Object::toString).orElse("-")));
    }

    private String sid(Team t) { return t.getName(); }

    private void onReportResult() {
        if (current == null) { JOptionPane.showMessageDialog(this, "Pilih tournament dulu."); return; }
        String sel = matchesList.getSelectedValue();
        if (sel == null) { JOptionPane.showMessageDialog(this, "Pilih match dari daftar."); return; }
        // parse id from list item (id: teamA vs teamB ...)
        String id = sel.split(":")[0].trim();
        String sA = JOptionPane.showInputDialog(this, "Skor untuk Team A (angka):");
        if (sA == null) return;
        String sB = JOptionPane.showInputDialog(this, "Skor untuk Team B (angka):");
        if (sB == null) return;
        try {
            int a = Integer.parseInt(sA.trim());
            int b = Integer.parseInt(sB.trim());
            current.getMatchManager().reportResult(id, a, b);
            LogService.write(current.getName(), "Reported result for " + id + ": " + a + "-" + b + " (GUI)");
            JOptionPane.showMessageDialog(this, "Hasil disimpan.");
            refreshMatches();
            refreshStandings();
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Masukkan angka yang valid.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan hasil: " + ex.getMessage());
        }
    }

    private void computeStandings() {
        if (current == null) { JOptionPane.showMessageDialog(this, "Pilih tournament dulu."); return; }
        try {
            var list = current.getStandingManager().computeStandings(current.getMatchManager().getAllMatches());
            // list may be List<Standing> — we show teamName + points if available
            standingsModel.clear();
            list.forEach(s -> standingsModel.addElement(s.getTeamName() + " - " + s.getPoints() + " pts"));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Gagal compute standings: " + ex.getMessage());
        }
    }

    private void refreshStandings() {
        // try to compute; if not possible show current standings
        try { computeStandings(); } catch (Exception ignored) {}
    }

    private void onSave() {
        if (current == null) { JOptionPane.showMessageDialog(this, "Pilih tournament dulu."); return; }
        try {
            String base = Path.of("tournament-data", current.getName()).toString();
            Files.createDirectories(Path.of(base));
            storage.save(base + "/teams.json", current.exportTeams());
            storage.save(base + "/matches.json", current.exportMatches());
            LogService.write(current.getName(), "Saved tournament (GUI)");
            JOptionPane.showMessageDialog(this, "Saved.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan: " + e.getMessage());
        }
    }

    private void onLoad() {
        String sel = sessionsList.getSelectedValue();
        if (sel == null) { JOptionPane.showMessageDialog(this, "Pilih tournament pada list di kiri lalu tekan Load."); return; }
        current = new TournamentSession(sel, new ScheduleManager(new RoundRobinStrategy()));
        try {
            String base = Path.of("tournament-data", sel).toString();
            Team[] teams = storage.loadArray(base + "/teams.json", Team[].class);
            Match[] matches = storage.loadArray(base + "/matches.json", Match[].class);
            current.importTeams(teams == null ? java.util.List.of() : java.util.Arrays.asList(teams));
            current.importMatches(matches == null ? java.util.List.of() : java.util.Arrays.asList(matches));
            LogService.write(sel, "Loaded tournament (GUI)");
            JOptionPane.showMessageDialog(this, "Loaded.");
            refreshAllViews();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Gagal load: " + e.getMessage());
        }
    }

    private void refreshAllViews() {
        refreshTeams();
        refreshMatches();
        refreshStandings();
    }

    private void exitSafely() {
        int confirm = JOptionPane.showConfirmDialog(this, "Exit aplikasi? Pastikan telah menyimpan tournament jika perlu.");
        if (confirm != JOptionPane.YES_OPTION) return;
        // optional: auto-save current session
        if (current != null) {
            try {
                String base = Path.of("tournament-data", current.getName()).toString();
                Files.createDirectories(Path.of(base));
                storage.save(base + "/teams.json", current.exportTeams());
                storage.save(base + "/matches.json", current.exportMatches());
                LogService.write(current.getName(), "Auto-saved on exit (GUI)");
            } catch (IOException ignored) {}
        }
        dispose();
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGUI g = new MainGUI();
            g.setVisible(true);
        });
    }
}

