package com.bomb.c196.Model;

import java.util.Date;

public class Assessment {
    private int id;
    private String title;
    private String type;
    private String dueDate;
    private String goalDate;
    private int courseId;
    private boolean dueAlertChecked = false;
    private boolean goalAlertChecked = false;

    public Assessment() {}

    public Assessment(String title, String type, String dueDate, String goalDate, int courseId) {
        this.title = title;
        this.type = type;
        this.dueDate = dueDate;
        this.goalDate = goalDate;
        this.courseId = courseId;
    }

    public Assessment(int id, String title, String type, String dueDate, String goalDate, int courseId) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.dueDate = dueDate;
        this.goalDate = goalDate;
        this.courseId = courseId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
    public String getGoalDate() { return goalDate; }
    public void setGoalDate(String goalDate) { this.goalDate = goalDate; }
    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
    public boolean isDueAlertChecked() { return dueAlertChecked; }
    public void setDueAlertChecked(boolean dueAlertChecked) { this.dueAlertChecked = dueAlertChecked; }
    public boolean isGoalAlertChecked() { return goalAlertChecked; }
    public void setGoalAlertChecked(boolean goalAlertChecked) { this.goalAlertChecked = goalAlertChecked; }
}
