package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;





import java.io.IOException;
import java.util.List;

public class CreateQuizController {
    private final SqliteQuizDAO quizDAO = new SqliteQuizDAO();



    User user = UserSession.getInstance().getCurrentUser();
    String role = UserSession.getInstance().getRole();

    @FXML
    private Button createQuiz;
    @FXML
    private Label successMessage;

    @FXML
    private TextField titleField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField topicField;
    @FXML
    private TextField lengthField;
    @FXML
    private Button returnToPrevious;



    @FXML
    private void onCreateQuiz() {

        String title = titleField.getText();
        String desc = descriptionField.getText();
        String topic = topicField.getText();
        int length = Integer.parseInt(lengthField.getText().trim());
        int author = 0;
        AIGenQuestions.genQuestions("Write 2 short response questions about chemical reactions");
        //TODO: Need to extract the data from the ollama response and add it to the questions?
        Question[] questions = new Question[] {
                new Question("text",
                        new String[] { "a", "b", "c", "d" }, 2),
                new Question("Which planet is known as the Red Planet?",
                        new String[] { "Earth", "Mars", "Jupiter", "Saturn" }, 1),

        };



        if ("Teacher".equals(role) && user instanceof Teacher){
            Teacher teacher = (Teacher) user;
            author = teacher.getTeacherID();
        }

        Quiz newQuiz = new Quiz(title, desc, topic, length, author, List.of(questions));
        quizDAO.addQuiz(newQuiz);
        successMessage.setText("Quiz " + title + " created successfully! Yay :)");
        successMessage.setVisible(true);
        createQuiz.setDisable(true);
        //TODO: create error handling for if from is not complete



    }

    @FXML private void returnToPage() throws IOException {
        Stage stage = (Stage) returnToPrevious.getScene().getWindow();
        SceneChanger.changeScene(stage, "temp-home-view.fxml");

    }









}
