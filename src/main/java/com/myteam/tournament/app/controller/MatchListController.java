package com.myteam.tournament.app.controller;

import com.myteam.tournament.facade.TournamentFacade;
import com.myteam.tournament.model.Match;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class MatchListController {

    @FXML private ListView<String> listMatches;

    private TournamentFacade facade;

    public void setFacade(TournamentFacade facade) {
        this.facade = facade;
        refresh();
    }

    private void refresh() {
        listMatches.getItems().clear();
        for (Match m : facade.getAllMatches()) {
            listMatches.getItems().add(m.toString());
        }
    }
}
