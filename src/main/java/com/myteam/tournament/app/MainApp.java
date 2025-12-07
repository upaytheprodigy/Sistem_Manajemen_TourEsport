package com.myteam.tournament.app;

import com.myteam.tournament.app.util.SceneLoader;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        SceneLoader.init(stage);
        SceneLoader.load("MenuView.fxml", "Konmik League - Menu", c -> {});
    }

    public static void main(String[] args) {
        launch();
    }
}
