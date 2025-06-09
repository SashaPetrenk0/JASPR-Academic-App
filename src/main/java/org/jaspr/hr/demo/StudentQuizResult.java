package org.jaspr.hr.demo;

import javafx.beans.property.SimpleStringProperty;

/**
 * Model class used to create new object type for student results so that results may be shown in graphs easily in a robust way.
 */
public class StudentQuizResult {
    private final SimpleStringProperty studentName;
    private final SimpleStringProperty scoreText;
    private final SimpleStringProperty percentageText;

    private final int score;
    private final int total;

    /**
     * Setting student result object
     * @param studentName name of the student as string
     * @param score integer grade, number of correct answers
     * @param total integer, number of questions in the quiz
     */
    public StudentQuizResult(String studentName, int score, int total) {
        this.studentName = new SimpleStringProperty(studentName);
        this.score = score;
        this.total = total;
        this.scoreText = new SimpleStringProperty(score + "/" + total);
        //calculate percentage, set it to string variable to be displayed as text later
        this.percentageText = new SimpleStringProperty((int)((score * 100.0) / total) + "%");
    }


    /**
     * @return the student name
     */
    public String getStudentName() {
        return studentName.get();
    }

    /**
     * @return string showing student score, eg. 10/20
     */
    public String getScoreText() {
        return scoreText.get();
    }

    /**
     * @return string showing the percentage score, eg. 80%
     */
    public String getPercentageText() {
        return percentageText.get();
    }

    /**
     * @return number of correct answers as integer
     */
    public int getScore() {
        return score;
    }

    /**
     * @return total number of questions in quiz as integer
     */
    public int getTotal() {
        return total;
    }

    /**
     * @return the percentage of correct answers as an integer
     */
    public double getPercentage() {
        return (score * 100.0) / total;
    }
}
