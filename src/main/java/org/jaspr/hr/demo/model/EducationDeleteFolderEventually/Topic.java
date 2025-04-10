package org.jaspr.hr.demo.model.EducationDeleteFolderEventually;

public class Topic {
    private int id;
    private String title;

    // Constructor
    public Topic(int id, String title) {
        this.id = id;
        this.title = title;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // toString() for easy representation
    @Override
    public String toString() {
        return "Topic{id=" + id + ", title='" + title + "'}";
    }
}

