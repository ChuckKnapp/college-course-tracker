package com.bomb.c196.Model;

public class Mentor {
    private int id;
    private String name;
    private String phone;
    private String email;
    private int courseId;

    public Mentor() {}

    public Mentor(String name, String phone, String email, int courseId) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.courseId = courseId;
    }

    public Mentor(int id, String name, String phone, String email, int courseId) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.courseId = courseId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
}
