package com.myteam.tournament.app.controller;

import com.myteam.tournament.app.util.SceneLoader;
import com.myteam.tournament.facade.TournamentFacade;
import com.myteam.tournament.model.Match;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class MatchListController {

    @FXML private ListView<Match> listMatches;

    private TournamentFacade facade;

    public void setFacade(TournamentFacade facade) {
        this.facade = facade;
        refresh();
    }

    private void refresh() {
        listMatches.getItems().setAll(facade.getAllMatches());
    }

    @FXML
    private void onInputScore() {
        Match selected = listMatches.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        SceneLoader.load("InputScore.fxml", "Input Skor", c -> {
            ((InputScoreController)c).setData(facade, selected);
        });
    }

    @FXML
    private void onBack() {
        listMatches.getScene().getWindow().hide();
    }
}
