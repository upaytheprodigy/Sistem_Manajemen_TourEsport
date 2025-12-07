package com.myteam.tournament.app.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

public class SceneLoader {

    private static Stage mainStage;

    public static void init(Stage stage) {
        mainStage = stage;
    }

    public static void load(String fxml, String title, java.util.function.Consumer<Object> controllerSetup) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource("/fxml/" + fxml));
            Parent root = loader.load();

            if (controllerSetup != null)
                controllerSetup.accept(loader.getController());

            Scene scene = new Scene(root);
            mainStage.setTitle(title);
            mainStage.setScene(scene); 
            mainStage.show();          

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeWindow(Node n) {
        ((Stage) n.getScene().getWindow()).close();
    }
}
