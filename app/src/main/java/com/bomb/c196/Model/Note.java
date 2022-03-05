package com.bomb.c196.Model;

public class Note {
    private int id;
    private String note;
    private int courseId;

    public Note() {}

    public Note(String note, int courseId) {
        this.note = note;
        this.courseId = courseId;
    }

    public Note(int id, String note, int courseId) {
        this.id = id;
        this.note = note;
        this.courseId = courseId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
}
