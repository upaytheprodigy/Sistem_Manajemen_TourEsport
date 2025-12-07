package com.myteam.tournament.app.controller;

import com.myteam.tournament.facade.TournamentFacade;
import com.myteam.tournament.model.Match;
import com.myteam.tournament.app.util.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class InputScoreController {

    @FXML private Label lblMatch;
    @FXML private TextField txtScoreA;
    @FXML private TextField txtScoreB;
    @FXML private Button btnSubmit;

    private TournamentFacade facade;
    private Match match;

    public void setData(TournamentFacade facade, Match match) {
        this.facade = facade;
        this.match = match;

        lblMatch.setText(match.getTeamA() + " vs " + match.getTeamB());
    }

    @FXML
    private void onSubmit() {
        try {
            int a = Integer.parseInt(txtScoreA.getText());
            int b = Integer.parseInt(txtScoreB.getText());

            facade.inputResult(match.getId(), a, b);

            SceneLoader.closeWindow(btnSubmit);
        } catch (Exception ex) {
            System.out.println("Invalid score input");
        }
    }

    @FXML
    private void onBack() {
        SceneLoader.closeWindow(btnSubmit);
    }
}

