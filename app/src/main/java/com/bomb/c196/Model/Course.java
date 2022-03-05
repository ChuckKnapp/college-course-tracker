package com.bomb.c196.Model;

import java.util.Date;

public class Course {
    private int id;
    private String title;
    private String startDate;
    private String anticipatedEndDate;
    private String status;
    private int termId;
    private int assessmentId;
    private int noteId;
    private int mentorId;

    public Course() {}

    public Course(String title, String startDate, String anticipatedEndDate, String status, int termId) {
        this.title = title;
        this.startDate = startDate;
        this.anticipatedEndDate = anticipatedEndDate;
        this.status = status;
        this.termId = termId;
    }

    public Course(String title, String startDate, String anticipatedEndDate, String status, int termId, int assessmentId, int noteId, int mentorId) {
        this.title = title;
        this.startDate = startDate;
        this.anticipatedEndDate = anticipatedEndDate;
        this.status = status;
        this.termId = termId;
        this.assessmentId = assessmentId;
        this.noteId = noteId;
        this.mentorId = mentorId;
    }

    public Course(int id, String title, String startDate, String anticipatedEndDate, String status, int termId) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.anticipatedEndDate = anticipatedEndDate;
        this.status = status;
        this.termId = termId;
    }

    public Course(int id, String title, String startDate, String anticipatedEndDate, String status, int termId, int assessmentId, int noteId, int mentorId) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.anticipatedEndDate = anticipatedEndDate;
        this.status = status;
        this.termId = termId;
        this.assessmentId = assessmentId;
        this.noteId = noteId;
        this.mentorId = mentorId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public String getAnticipatedEndDate() { return anticipatedEndDate; }
    public void setAnticipatedEndDate(String anticipatedEndDate) { this.anticipatedEndDate = anticipatedEndDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getTermId() { return termId; }
    public void setTermId(int termId) { this.termId = termId; }
    public int getAssessmentId() { return assessmentId; }
    public void setAssessmentId(int assessmentId) { this.assessmentId = assessmentId; }
    public int getNoteId() { return noteId; }
    public void setNoteId(int noteId) { this.noteId = noteId; }
    public int getMentorId() { return mentorId; }
    public void setMentorId(int mentorId) { this.mentorId = mentorId; }
}
