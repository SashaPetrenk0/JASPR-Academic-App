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



    public static void passSceneData(Stage stage, String fxmlFileName) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFileName));
            Parent root = fxmlLoader.load();

            // If teacher is provided, pass it to the controller (must have setTeacher)
            Object controller = fxmlLoader.getController();

            //if (teacher != null) {
               // System.out.println("teacher not null");
                if( controller instanceof TeacherDashboardController){
                    System.out.println("has controller");
                    // Check if the controller is of the correct type and pass the teacher
                    ((TeacherDashboardController) controller).setTeacher();
                }


           // }

            Scene scene = new Scene(root, WIDTH, HEIGHT);
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
            // Optional: show user-friendly error
        }
    }
}
