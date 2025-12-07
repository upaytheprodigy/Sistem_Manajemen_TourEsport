package com.myteam.tournament.app.controller;

import com.myteam.tournament.facade.TournamentFacade;
import com.myteam.tournament.model.Standing;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.*;

public class StandingViewController {

    @FXML private TableView<Standing> tblStanding;
    @FXML private TableColumn<Standing, String> colTeam;
    @FXML private TableColumn<Standing, Integer> colWin;
    @FXML private TableColumn<Standing, Integer> colLose;

    private TournamentFacade facade;

    public void setFacade(TournamentFacade facade) {
        this.facade = facade;
        refreshTable();
    }

    private void refreshTable() {
        colTeam.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTeamName()));
        colWin.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getWins()).asObject());
        colLose.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getLosses()).asObject());

        ObservableList<Standing> items =
                FXCollections.observableArrayList(facade.getStandings());

        tblStanding.setItems(items);
    }

    @FXML
    private void onBack() {
        tblStanding.getScene().getWindow().hide();
    }
}
