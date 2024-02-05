package com.example.schedulerapp;

public class MyAssignment {
    private String title;
    private String dueDate;
    private String associatedClass;

    public MyAssignment(String title, String dueDate, String associatedClass) {
        this.title = title;
        this.dueDate = dueDate;
        this.associatedClass = associatedClass;
    }

    // Getter method for title
    public String getTitle() {
        return title;
    }

    // Setter method for title
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter method for due date
    public String getDueDate() {
        return dueDate;
    }

    // Setter method for due date
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    // Getter method for associated class
    public String getAssociatedClass() {
        return associatedClass;
    }

    // Setter method for associated class
    public void setAssociatedClass(String associatedClass) {
        this.associatedClass = associatedClass;
    }

    @Override
    public String toString() {
        return "MyAssignment{" +
                "title='" + title + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", associatedClass='" + associatedClass + '\'' +
                '}';
    }
}

