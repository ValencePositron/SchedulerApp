package com.example.schedulerapp;

public class MyClass {
    private String name;
    private String time;
    private String location;

    public MyClass(String name, String time, String location) {
        this.name = name;
        this.time = time;
        this.location = location;
    }

    // Getter for className
    public String getClassName() {
        return name;
    }

    // Setter for className
    public void setClassName(String className) {
        this.name = className;
    }

    // Getter for classTime
    public String getClassTime() {
        return time;
    }

    // Setter for classTime
    public void setClassTime(String classTime) {
        this.time = classTime;
    }

    // Getter for classLocation
    public String getClassLocation() {
        return location;
    }

    // Setter for classLocation
    public void setClassLocation(String classLocation) {
        this.location = classLocation;
    }
}
