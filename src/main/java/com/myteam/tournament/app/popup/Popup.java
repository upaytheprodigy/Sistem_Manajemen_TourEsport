package com.myteam.tournament.app.popup;

import javafx.scene.control.Alert;

public class Popup {

    public static void info(String msg) {
        alert(Alert.AlertType.INFORMATION, msg);
    }

    public static void error(String msg) {
        alert(Alert.AlertType.ERROR, msg);
    }

    private static void alert(Alert.AlertType type, String msg) {
        Alert a = new Alert(type);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.show();
    }
}
