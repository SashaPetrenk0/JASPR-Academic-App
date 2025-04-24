package org.jaspr.hr.demo;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.fxml.FXML;

public class ClassroomCreationController {

    @FXML
    private Label classroomCreate;

    @FXML
    private Button returnToPrevious;

    private void returnToAdmin() throws IOException{
        Stage stage = (Stage) returnToPrevious.getScene().getWindow();
        SceneChanger.changeScene(stage, "admin-dashboard-view.fxml");
    }
}
