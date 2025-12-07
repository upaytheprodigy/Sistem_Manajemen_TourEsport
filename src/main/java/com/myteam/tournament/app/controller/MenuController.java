package com.myteam.tournament.app.controller;

import com.myteam.tournament.app.util.SceneLoader;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MenuController {

    @FXML private ImageView bgImage;
    @FXML private ImageView btnCreate;
    @FXML private ImageView btnLoad;
    @FXML private ImageView btnExit;

    @FXML
    public void initialize() {
        bgImage.setImage(load("3.png"));
        btnCreate.setImage(load("1.png"));
        btnCreate.setPickOnBounds(true);
        btnCreate.setOnMouseClicked(e -> onCreate());
        btnCreate.setOnMousePressed(null); 

        btnLoad.setImage(load("2.png"));
        btnExit.setImage(load("exit.png"));


        btnLoad.setPickOnBounds(true);
        btnExit.setPickOnBounds(true);
    }

    private Image load(String file) {
        return new Image(getClass().getResource("/assets/" + file).toExternalForm());
    }

    @FXML
    private void onCreate() {
        SceneLoader.load("TournamentView.fxml", "Tournament", null);
    }

    @FXML
    private void onLoad() {
        SceneLoader.load("TournamentView.fxml", "Tournament", null);
    }

    @FXML
    private void onExit() {
        ((Stage) bgImage.getScene().getWindow()).close();
    }
}
