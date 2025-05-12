package org.jaspr.hr.demo;

import java.util.List;

public class Quiz {
    private int id;
    private String title;
    private String description;
    private String topic;
    private int numOfQuestions;
    private int author;
    private List<Question> questions;



    public Quiz(String title, String description, String topic, int numOfQuestions, int author) {
        this.title = title;
        this.description = description;
        this.topic = topic;
        this.numOfQuestions = numOfQuestions;
        this.author = author;
        //  this.questions = questions;
    }

    public Quiz(int id, String title, String description, String topic, int numOfQuestions, int author){
        this.id = id;
        this.title = title;
        this.description = description;
        this.topic = topic;
        this.numOfQuestions = numOfQuestions;
        this.author = author;
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
    private void setID(int id){
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

    public String getQuizDetails(){
        return title + " " + topic +  " " + description + " " + numOfQuestions;
    }
}
