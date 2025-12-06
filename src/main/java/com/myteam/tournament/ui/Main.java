package com.myteam.tournament.ui;

import com.myteam.tournament.util.Repository;
import com.myteam.tournament.manager.*;
import com.myteam.tournament.facade.TournamentFacade;
import com.myteam.tournament.model.*;
import com.myteam.tournament.strategy.RoundRobinStrategy;
import com.myteam.tournament.strategy.KnockoutStrategy;
import com.myteam.tournament.factory.MatchType;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class Main extends Application {

    private TournamentFacade facade;
    private ListView<String> lvTeams, lvMatches, lvStandings;
    private ListView<String> lvTournaments;

    @Override
    public void start(Stage stage) {
        // init repositories & managers & facade
        TeamManager tm = new TeamManager(new Repository<>(Team::getId));
        MatchManager mm = new MatchManager(new Repository<>(Match::getId));
        StandingManager sm = new StandingManager(new Repository<>(Standing::getTeamId));
        ScheduleManager sch = new ScheduleManager(new RoundRobinStrategy());
        facade = new TournamentFacade(tm, mm, sch, sm);

        // top-level layout (4 columns)
        HBox root = new HBox(10);
        root.setPadding(new Insets(12));
        root.setPrefHeight(600);

        // Column 1: tournaments
        VBox col1 = new VBox(8);
        Label l1 = new Label("Tournaments");
        l1.setFont(Font.font(16));
        lvTournaments = new ListView<>();
        lvTournaments.getItems().add("Default Tournament");
        Button btnCreateT = new Button("Create Tournament"); btnCreateT.setOnAction(e->{
            TextInputDialog d = new TextInputDialog();
            d.setTitle("Create Tournament"); d.setHeaderText("Name?");
            d.showAndWait().ifPresent(name -> lvTournaments.getItems().add(name));
        });
        col1.getChildren().addAll(l1, lvTournaments, btnCreateT);
        col1.setPrefWidth(220);

        // Column 2: teams
        VBox col2 = new VBox(8);
        Label l2 = new Label("Teams");
        l2.setFont(Font.font(16));
        lvTeams = new ListView<>();
        Button addTeam = new Button("Add Team");
        addTeam.setOnAction(e -> {
            TextInputDialog d = new TextInputDialog();
            d.setTitle("Add Team"); d.setHeaderText("Team name:");
            d.showAndWait().ifPresent(name -> {
                facade.addTeam(name);
                refreshTeams();
            });
        });
        col2.getChildren().addAll(l2, lvTeams, addTeam);
        col2.setPrefWidth(300);

        // Column 3: matches
        VBox col3 = new VBox(8);
        Label l3 = new Label("Matches");
        l3.setFont(Font.font(16));
        lvMatches = new ListView<>();
        Button genRR = new Button("Generate RoundRobin (BO1)");
        genRR.setOnAction(e -> {
            facade.generateSchedule(new RoundRobinStrategy(), MatchType.BO1);
            refreshMatches();
        });
        Button genKO = new Button("Generate Knockout (BO3)");
        genKO.setOnAction(e -> {
            facade.generateSchedule(new KnockoutStrategy(), MatchType.BO3);
            refreshMatches();
        });
        Button inputRes = new Button("Input Result");
        inputRes.setOnAction(e -> {
            String sel = lvMatches.getSelectionModel().getSelectedItem();
            if (sel == null){ alert("Select a match first"); return; }
            // simple parsing: items are rendered as toDisplayString which contains team names, we'll find match by toDisplayString
            List<Match> matches = facade.getAllMatches();
            Match found = matches.stream().filter(m -> m.toDisplayString().equals(sel)).findFirst().orElse(null);
            if (found==null) { alert("Can't map selected match"); return; }
            TextInputDialog aDialog = new TextInputDialog("0"); aDialog.setHeaderText("Score for " + found.getTeamA().getName());
            aDialog.showAndWait().ifPresent(sa -> {
                TextInputDialog bDialog = new TextInputDialog("0"); bDialog.setHeaderText("Score for " + found.getTeamB().getName());
                bDialog.showAndWait().ifPresent(sb -> {
                    try {
                        int A = Integer.parseInt(sa.trim());
                        int B = Integer.parseInt(sb.trim());
                        facade.reportResult(found.getId(), A, B);
                        refreshMatches();
                        refreshStandings();
                        alert("Result saved.");
                    } catch(NumberFormatException ex){ alert("Invalid number"); }
                });
            });
        });

        col3.getChildren().addAll(l3, lvMatches, genRR, genKO, inputRes);
        col3.setPrefWidth(380);

        // Column 4: standings
        VBox col4 = new VBox(8);
        Label l4 = new Label("Standings");
        l4.setFont(Font.font(16));
        lvStandings = new ListView<>();
        Button compute = new Button("Compute Standings");
        compute.setOnAction(e -> refreshStandings());
        col4.getChildren().addAll(l4, lvStandings, compute);
        col4.setPrefWidth(320);

        root.getChildren().addAll(col1, col2, col3, col4);

        // Styles: apply simple color palette
        root.setStyle("-fx-background-color: linear-gradient(#1B211A, #2B5A5A);");
        Scene scene = new Scene(root, 1200, 640);
        stage.setTitle("Esport Tournament Manager - GUI");
        stage.setScene(scene);
        stage.show();

        // seed some demo data
        facade.addTeam("Ian Team");
        facade.addTeam("Upay Team");
        facade.addTeam("Brata Team");
        facade.addTeam("Nesta Team");
        refreshTeams();
    }

    private void refreshTeams(){
        lvTeams.getItems().clear();
        facade.getTeams().forEach(t -> lvTeams.getItems().add(t.getName()));
    }

    private void refreshMatches(){
        lvMatches.getItems().clear();
        facade.getAllMatches().forEach(m -> lvMatches.getItems().add(m.toDisplayString()));
    }

    private void refreshStandings(){
        // initialize standings repo first
        List<Team> teams = facade.getTeams();
        // We need access to standingManager internal repo — in this simplified design, recompute by creating a new StandingManager and computing
        // But facade doesn't expose standingManager compute; to keep encapsulation, the facade could provide a helper.
        // For now: compute inside by building a new StandingManager temporarily
        // (Better: extend facade — but keep simple)
        // We'll compute standings directly here:
        // Build temporary repo closure:
        var repo = new com.myteam.tournament.util.Repository<com.myteam.tournament.model.Standing>(s -> s.getTeamId());
        var stman = new com.myteam.tournament.manager.StandingManager(repo);
        stman.initialize(teams);
        stman.computeStandings(facade.getAllMatches())
                .forEach(s -> lvStandings.getItems().add(s.toString()));
    }

    private void alert(String msg){
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.showAndWait();
    }

    public static void main(String[] args){ launch(); }
}
