package com.myteam.tournament.app.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import com.myteam.tournament.facade.TournamentFacade;
import com.myteam.tournament.model.Standing; 

public class StandingViewController {

    @FXML private TableView<Standing> tblStanding;

    private final TournamentFacade facade = new TournamentFacade(null, null, null, null);

    @FXML
    public void initialize() {
        tblStanding.setItems(FXCollections.observableArrayList(facade.getStandings()));

    }
}
