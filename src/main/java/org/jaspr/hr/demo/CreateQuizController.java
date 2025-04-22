package org.jaspr.hr.demo;

import javafx.fxml.FXML;

public class CreateQuizController {






    @FXML
    private void onAdd() {
        // Default values for a new contact
        final String DEFAULT_TITLE = "New Quiz";
        final String DEFAULT_DESCRIPTION = "This is what your quiz is about";
        final String DEFAULT_TOPIC = "Chemistry";
        final int DEFAULT_LENGTH= 1;
        final int DEFAULT_AUTHOR = 1;

        Quiz newQuiz = new Quiz(DEFAULT_TITLE, DEFAULT_DESCRIPTION, DEFAULT_TOPIC, DEFAULT_LENGTH, DEFAULT_AUTHOR);

        // Add the new contact to the database
//        contactDAO.addContact(newContact);
//        syncContacts();
//        // Select the new contact in the list view
//        // and focus the first name text field
//        selectContact(newContact);
//        firstNameTextField.requestFocus();
    }




}
