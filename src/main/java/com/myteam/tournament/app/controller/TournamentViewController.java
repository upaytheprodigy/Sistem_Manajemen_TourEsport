package com.myteam.tournament.app.controller;

import com.myteam.tournament.app.util.SceneLoader;
import com.myteam.tournament.facade.TournamentFacade;
import com.myteam.tournament.manager.*;
import com.myteam.tournament.model.*;
import com.myteam.tournament.strategy.*;
import com.myteam.tournament.factory.MatchType;
import com.myteam.tournament.util.Repository;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TournamentViewController {

    @FXML private ListView<Team> listTeams;
    @FXML private ListView<Match> listMatches;
    @FXML private ListView<Standing> listStandings;
    @FXML private ListView<String> listTournaments;

    @FXML private Button btnCreateTournament;
    @FXML private Button btnAddTeam;
    @FXML private Button btnRoundRobin;
    @FXML private Button btnKnockout;
    @FXML private Button btnInputResult;
    @FXML private Button btnCompute;

    private TournamentFacade facade;

    @FXML
    public void initialize() {

        // semua repo
        var teamRepo = new Repository<Team>(Team::getId);
        var matchRepo = new Repository<Match>(Match::getId);
        var standingRepo = new Repository<Standing>(Standing::getTeamId);

        TeamManager tm = new TeamManager(teamRepo);
        MatchManager mm = new MatchManager(matchRepo);
        ScheduleManager sm = new ScheduleManager(new RoundRobinStrategy());
        StandingManager srm = new StandingManager();

        facade = new TournamentFacade(tm, mm, sm, srm);

        // load UI images
        setButtonImage(btnAddTeam, "/assets/5.png");
        setButtonImage(btnRoundRobin, "/assets/10.png");
        setButtonImage(btnKnockout, "/assets/12.png");
        setButtonImage(btnInputResult, "/assets/7.png");
        setButtonImage(btnCompute, "/assets/8.png");

        refreshAll();

        // tombol
        btnAddTeam.setOnAction(e ->
                SceneLoader.load("AddTeam.fxml", "Tambah Team", c -> {
                    ((AddTeamController) c).setFacade(facade);
                })
        );

        btnRoundRobin.setOnAction(e -> {
            facade.generateSchedule(new RoundRobinStrategy(), MatchType.BO1);
            refreshMatches();
        });

        btnKnockout.setOnAction(e -> {
            facade.generateSchedule(new KnockoutStrategy(), MatchType.BO3);
            refreshMatches();
        });

        btnInputResult.setOnAction(e ->
                SceneLoader.load("MatchListView.fxml", "Input Skor", c -> {
                    ((MatchListController) c).setFacade(facade);
                })
        );

        btnCompute.setOnAction(e -> {
            facade.getStandings();
            refreshStandings();
        });
    }

    private void setButtonImage(Button btn, String path) {
        Image img = new Image(getClass().getResource(path).toExternalForm());
        ImageView view = new ImageView(img);
        view.setFitWidth(200);
        view.setFitHeight(60);
        btn.setGraphic(view);
        btn.setText(null);
    }

    private void refreshAll() {
        refreshTeams();
        refreshMatches();
        refreshStandings();
    }

    private void refreshTeams() {
        listTeams.getItems().setAll(facade.getTeams());
    }

    private void refreshMatches() {
        listMatches.getItems().setAll(facade.getAllMatches());
    }

    private void refreshStandings() {
        listStandings.getItems().setAll(facade.getStandings());
    }
}
