package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    private Button Cancel;

    @FXML
    private TextArea TandC;

    @FXML
    public void initialize() {
        TandC.setText("""
jamie says something!!!""");
    }
    @FXML
    private CheckBox agreeCheckBox;
    @FXML
    private Button nextButton;

    private final IUserDAO userDAO = new SqliteUserDAO();

    @FXML
    public void initialize() {
        if (!userDAO.hasAnyRegisteredUsers()) {
            loginButton.setDisable(true);   // Keep it disabled
        } else {
            loginButton.setDisable(false);  // Enable login
        }
    }

    @FXML
    protected void onAgreeCheckBoxClick() {
        boolean accepted = agreeCheckBox.isSelected();
        nextButton.setDisable(!accepted);
    }
    @FXML
    protected void onNextButtonClick() throws IOException {
        Stage stage = (Stage) nextButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    protected void onCancelButtonClick() {
        Stage stage = (Stage) nextButton.getScene().getWindow();
        stage.close();
    }


}
