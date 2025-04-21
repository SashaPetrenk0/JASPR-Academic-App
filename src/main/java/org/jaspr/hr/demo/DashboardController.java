package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class DashboardController {
    @FXML
    private VBox vBox;

    @FXML
    public void initialize() {
        // Set background color to light blue
        vBox.setStyle("-fx-background-color: lightblue;");
    }
}
