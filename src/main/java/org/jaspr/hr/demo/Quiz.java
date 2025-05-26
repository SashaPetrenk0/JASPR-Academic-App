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
     * @param title the title of the quiz
     * @param description the description of the quiz (used as a prompt for AI)
     * @param topic topic of quiz
     * @param numOfQuestions number of questions in the quiz (also used to prompt AI)
     * @param author id of the user who made the quiz
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
     * @param id the id of the quiz
     * @param title the title of the quiz
     * @param description the description of the quiz (used as a prompt for AI)
     * @param topic topic of quiz
     * @param numOfQuestions number of questions in the quiz (also used to prompt AI)
     * @param author id of the user who made the quiz
     */

    public Quiz(int id, String title, String description, String topic, int numOfQuestions, int author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.topic = topic;
        this.numOfQuestions = numOfQuestions;
        this.author = author;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public int getAuthor() {
        return author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getTopic() {
        return topic;
    }

    public int getNumOfQuestions() {
        return numOfQuestions;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNumOfQuestions(int numOfQuestions) {
        this.numOfQuestions = numOfQuestions;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

}
