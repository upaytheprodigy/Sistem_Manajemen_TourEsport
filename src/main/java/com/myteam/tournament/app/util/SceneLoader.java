package com.myteam.tournament.app.util;

import java.util.function.Consumer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SceneLoader {

    private static Stage primary;

    public static void init(Stage stage) {
        primary = stage;
    }

    public static void load(String fxml, String title, Consumer<Object> controllerConfig) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource("/fxml/" + fxml));
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controllerConfig != null) controllerConfig.accept(controller);

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> void openDialog(String fxml, String title, Consumer<T> consumer) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneLoader.class.getResource("/fxml/" + fxml));
            Stage s = new Stage();
            s.setScene(new Scene(loader.load()));
            s.setTitle(title);

            if (consumer != null) consumer.accept(loader.getController());

            s.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeWindow(ImageView btnAdd) {
        Stage stage = (Stage) btnAdd.getScene().getWindow();
        stage.close();
    }
}
