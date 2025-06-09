package org.jaspr.hr.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Entry point of the AcademIQ JavaFX application.
 * This class sets up the primary stage and loads the initial FXML view.
 */
public class HelloApplication extends Application {
    /** Title of the application window */
    public static final String TITLE = "AcademIQ";

    /** Default width and height of the application window */
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    /**
     * Called when the JavaFX application starts.
     * Loads the initial FXML layout and sets the scene for the stage.
     * @param stage the primary stage provided by the JavaFX runtime
     * @throws IOException if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
        stage.setTitle(TITLE);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method to launch the JavaFX application.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}