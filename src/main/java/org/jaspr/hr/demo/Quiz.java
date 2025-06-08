package org.jaspr.hr.demo;

/**
 * A simple model class representing a quiz with an id, title, description,topic,number of questions, author and array of questions
 */

public class Quiz {
    private int id;
    private String title;
    private String description;
    private String topic;
    private int numOfQuestions;
    private int author;
    private Question[] questions;

    /**
     * Constructs a new Quiz with the specified title, description,topic,number of questions, author.
     *
     * @param title          the title of the quiz
     * @param description    the description of the quiz (used as a prompt for AI)
     * @param topic          topic of quiz
     * @param numOfQuestions number of questions in the quiz (also used to prompt AI)
     * @param author         id of the user who made the quiz
     */

    public Quiz(String title, String description, String topic, int numOfQuestions, int author) {
        this.title = title;
        this.description = description;
        this.topic = topic;
        this.numOfQuestions = numOfQuestions;
        this.author = author;

    }

    /**
     * Constructs a new Quiz with the specified id, title, description,topic,number of questions, author.
     *
     * @param id             the id of the quiz
     * @param title          the title of the quiz
     * @param description    the description of the quiz (used as a prompt for AI)
     * @param topic          topic of quiz
     * @param numOfQuestions number of questions in the quiz (also used to prompt AI)
     * @param author         id of the user who made the quiz
     */

    public Quiz(int id, String title, String description, String topic, int numOfQuestions, int author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.topic = topic;
        this.numOfQuestions = numOfQuestions;
        this.author = author;
    }

    /**
     * Returns the array of questions in the quiz.
     *
     * @return an array of {@link Question} objects
     */
    public Question[] getQuestions() {
        return questions;
    }

    /**
     * Sets the array of questions for this quiz.
     *
     * @param questions an array of {@link Question} objects
     */
    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }

    /**
     * Sets the author ID of the quiz.
     *
     * @param author the user ID of the author
     */
    public void setAuthor(int author) {
        this.author = author;
    }

    /**
     * Gets the author ID of the quiz.
     *
     * @return the user ID of the author
     */
    public int getAuthor() {
        return author;
    }

    /**
     * Gets the unique ID of this quiz.
     *
     * @return the quiz ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique ID for this quiz.
     *
     * @param id the quiz ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the description of the quiz.
     *
     * @return the description text
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the title of the quiz.
     *
     * @return the quiz title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the topic of the quiz.
     *
     * @return the quiz topic
     */
    public String getTopic() {
        return topic;
    }

    /**
     * Gets the number of questions in the quiz.
     *
     * @return the number of questions
     */
    public int getNumOfQuestions() {
        return numOfQuestions;
    }

    /**
     * Sets the description for the quiz.
     *
     * @param description a text description of the quiz
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the number of questions in the quiz.
     *
     * @param numOfQuestions the number of questions
     */
    public void setNumOfQuestions(int numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
    }

    /**
     * Sets the title of the quiz.
     *
     * @param title the title text
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the topic of the quiz.
     *
     * @param topic the topic text
     */
    public void setTopic(String topic) {
        this.topic = topic;
    }
}