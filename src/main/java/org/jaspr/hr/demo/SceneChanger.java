package org.jaspr.hr.demo;
import javafx.scene.Parent;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneChanger {

    //TODO: Change these to our final styled dimensions

    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;

    public static void changeScene(Stage stage, String fxmlFileName){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFileName));
            Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
            stage.setScene(scene);
        }
        catch (IOException e) {
            e.printStackTrace();
            // You can show an error dialog or handle the error gracefully
        }
    }




}
