package com.myteam.tournament.app.controller;

import com.myteam.tournament.facade.TournamentFacade;
import com.myteam.tournament.app.util.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class AddTeamController {

    @FXML private TextField txtNamaTeam;
    @FXML private ImageView btnAdd;

    private TournamentFacade facade;

    public void setFacade(TournamentFacade facade) {
        this.facade = facade;
    }

    @FXML
    private void onAdd() {
        if (txtNamaTeam.getText().isBlank()) return;
        facade.addTeam(txtNamaTeam.getText());
        SceneLoader.closeWindow(btnAdd);
    }
}
