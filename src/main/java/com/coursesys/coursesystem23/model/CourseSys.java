package com.coursesys.coursesystem23.model;

import java.util.ArrayList;

public class CourseSys {

    private int id;
    private String version;
    private ArrayList<User> courseUsers;
    private ArrayList<Course> courses;

    public CourseSys() {
    }

    public CourseSys(int id, String version, ArrayList<User> courseUsers, ArrayList<Course> courses) {
        this.id = id;
        this.version = version;
        this.courseUsers = courseUsers;
        this.courses = courses;
    }

    public CourseSys(int id, ArrayList<User> courseUsers, ArrayList<Course> courses) {
        this.id = id;
        this.courseUsers = courseUsers;
        this.courses = courses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ArrayList<User> getCourseUsers() {
        return courseUsers;
    }

    public void setCourseUsers(ArrayList<User> courseUsers) {
        this.courseUsers = courseUsers;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
}
