package org.jaspr.hr.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public static final String TITLE = "App Dashboard"; // Updated title
    public static final int WIDTH = 1315;  // Updated to match your dashboard size
    public static final int HEIGHT = 890;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard.fxml")); // <-- load dashboard.fxml
        Scene scene = new Scene(fxmlLoader.load(), 1315, 800);
        stage.setTitle(TITLE);
        stage.sizeToScene(); // This will resize the window to match your layout size
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
