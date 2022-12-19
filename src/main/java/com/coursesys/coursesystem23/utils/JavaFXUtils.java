package com.coursesys.coursesystem23.utils;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

public class JavaFXUtils {
        public static void alertMessage(String s) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Alert");
            alert.setContentText(s);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
        }
}
