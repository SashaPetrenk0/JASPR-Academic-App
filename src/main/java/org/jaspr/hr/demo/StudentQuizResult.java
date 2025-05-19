package org.jaspr.hr.demo;

import javafx.beans.property.SimpleStringProperty;

public class StudentQuizResult {
    private final SimpleStringProperty studentName;
    private final SimpleStringProperty scoreText;
    private final SimpleStringProperty percentageText;

    public StudentQuizResult(String studentName, int score, int total) {
        this.studentName = new SimpleStringProperty(studentName);
        this.scoreText = new SimpleStringProperty(score + "/" + total);
        this.percentageText = new SimpleStringProperty((int)((score * 100.0) / total) + "%");
    }

    public String getStudentName() {
        return studentName.get();
    }

    public String getScoreText() {
        return scoreText.get();
    }

    public String getPercentageText() {
        return percentageText.get();
    }
}
