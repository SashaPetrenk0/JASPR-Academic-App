package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CreateQuizController {
    private final SqliteQuizDAO quizDAO = new SqliteQuizDAO();


    User user = UserSession.getInstance().getCurrentUser();
    String role = UserSession.getInstance().getRole();

    @FXML
    private TextField titleField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField topicField;
    @FXML
    private TextField lengthField;

    @FXML
    private void onCreateQuiz() {
        // Default values for a new contact
        final String DEFAULT_TITLE = "New Quiz";
        final String DEFAULT_DESCRIPTION = "This is what your quiz is about";
        final String DEFAULT_TOPIC = "Chemistry";
        final int DEFAULT_LENGTH= 1;
        final int DEFAULT_AUTHOR = 0;

        String title = titleField.getText();
        String desc = descriptionField.getText();
        String topic = topicField.getText();
        int length = Integer.parseInt(lengthField.getText().trim());
        int author = 0;



        if ("Teacher".equals(role) && user instanceof Teacher){
            Teacher teacher = (Teacher) user;
            author = teacher.getTeacherID();
        }

        Quiz newQuiz = new Quiz(title, desc, topic, length, author);
        quizDAO.addQuiz(newQuiz);


    }




}
