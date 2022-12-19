package com.coursesys.coursesystem23;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class Start extends Application {

    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("login-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Course system");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
