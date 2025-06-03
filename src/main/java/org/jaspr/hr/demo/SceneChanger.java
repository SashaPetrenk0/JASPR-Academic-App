package org.jaspr.hr.demo;
import javafx.scene.Parent;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Class to handle changing between fxml windows
 */
public class SceneChanger {

    //TODO: Change these to our final styled dimensions
    public static final double WIDTH = 800;
    public static final double HEIGHT = 600;

    /**
     * Method used to change scenes
     * @param stage
     * @param fxmlFileName the name of the file that should be displayed
     */
    public static void changeScene(Stage stage, String fxmlFileName){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFileName));
            Scene scene = new Scene(fxmlLoader.load(), WIDTH, HEIGHT);
            stage.setScene(scene);
        }
        catch (IOException e) {
            e.printStackTrace();

        }
    }




}
