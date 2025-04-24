package org.jaspr.hr.demo;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {

    @FXML
    private Button ClassRoomCreation;

    @FXML
    private Button AssignButton;

    @FXML
    protected void onCreateClassroomClick() throws IOException {
        Stage stage = (Stage) ClassRoomCreation.getScene().getWindow();
        SceneChanger.changeScene(stage, "classroom-creation-view.fxml");
    }

    @FXML
    protected void onAssignUsersClick() throws IOException{
        Stage stage = (Stage) AssignButton.getScene().getWindow();
        SceneChanger.changeScene(stage, "assignToClassroom-view.fxml");
    }
}
