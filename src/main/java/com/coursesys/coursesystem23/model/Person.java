package com.coursesys.coursesystem23.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Person extends User implements Serializable {
    private String name;
    private String surname;
    private String email;
    private String phoneNum;
    @ManyToMany(mappedBy = "students", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Course> myEnrolledCourses;

    public Person() {
    }

    public Person(String name, String surname, String email, String phoneNum, List<Course> myEnrolledCourses) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNum = phoneNum;
        this.myEnrolledCourses = myEnrolledCourses;
    }

    public Person(int id, String login, String password, LocalDate dateCreated, LocalDate dateModified, UserType userType, List<Course> myModeratedCourses, List<Folder> myFolders, String name, String surname, String email, String phoneNum, List<Course> myEnrolledCourses) {
        super(id, login, password, dateCreated, dateModified, userType, myModeratedCourses, myFolders);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNum = phoneNum;
        this.myEnrolledCourses = myEnrolledCourses;
    }

    public Person(String login, String password, String name, String surname, String email, String phoneNum) {
        super(login, password);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNum = phoneNum;
    }

    public Person(String login, String password, UserType userType, String name, String surname, String email, String phoneNum) {
        super(login, password, userType);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return this.getLogin();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public List<Course> getMyEnrolledCourses() {
        return myEnrolledCourses;
    }

    public void setMyEnrolledCourses(List<Course> myEnrolledCourses) {
        this.myEnrolledCourses = myEnrolledCourses;
    }
}

