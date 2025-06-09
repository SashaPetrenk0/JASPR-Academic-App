package org.jaspr.hr.demo;

/**
 * Represents a multiple-choice question used in the quiz
 * Includes options A-D and one correct answer.
 */
public class Question {
    int id;
    private  String question;
    private  String optionA;
    private  String optionB;
    private  String optionC;
    private  String optionD;
    private  String correctAnswer;

    /**
     * Constructs a new question without an ID (e.g., before storing in DB).
     * @param question the question text
     * @param optionA option A text
     * @param optionB option B text
     * @param optionC option C text
     * @param optionD option D text
     * @param correctAnswer the correct answer (same as one of the options)
     */
    public Question(String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        this.question = question;
        this.optionA =  optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
    }

    /**
     * Constructs a new question with an ID (e.g., loaded from DB).
     * @param id unique identifier of the questions
     * @param question the question text
     * @param optionA option A text
     * @param optionB option B text
     * @param optionC option C text
     * @param optionD option D text
     * @param correctAnswer the correct answer (same as one of the options)
     */
    public Question(int id, String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        this.id = id;
        this.question = question;
        this.optionA =  optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
    }

    /**
     * Return unique identifier of question from database
     * @return integer ID
     */
    public int getId() {
        return id;
    }

    /**
     * Set the unique identifier of the question
     * @param id ID of question to be set
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * @return the question text
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Sets the question text.
     * @param question new question text
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return option A
     */
    public String getOptionA() {
        return optionA;
    }

    /**
     * @param optionA new option A
     */
    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    /**
     * @return option B
     */
    public String getOptionB() {
        return optionB;
    }

    /**
     * @param optionB new option B
     */
    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    /**
     * @return option C
     */
    public String getOptionC() {
        return optionC;
    }

    /**
     * @param optionC new option C
     */
    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    /**
     * @return option D
     */
    public String getOptionD() {
        return optionD;
    }

    /**
     * @param optionD new option D
     */
    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    /**
     * @return the correct answer (must match one of the options)
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    /**
     * Sets the correct answer.
     * @param correctAnswer the correct answer text
     */
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
