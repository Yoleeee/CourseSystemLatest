package com.coursesys.coursesystem23.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
public abstract class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String login;
    private String password;
    private LocalDate dateCreated;
    private LocalDate dateModified;
    @Enumerated(EnumType.ORDINAL)
    private UserType userType;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_moderated_courses")
    private List<Course> myModeratedCourses;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_folders")
    private List<Folder> myFolders;

    public User() {
    }

    public User(int id, String login, String password, LocalDate dateCreated, LocalDate dateModified, UserType userType, List<Course> myModeratedCourses, List<Folder> myFolders) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.userType = userType;
        this.myModeratedCourses = myModeratedCourses;
        this.myFolders = myFolders;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.dateCreated = LocalDate.now();
        this.dateModified = LocalDate.now();
    }

    public User(String login, String password, UserType userType) {
        this.login = login;
        this.password = password;
        this.userType = userType;
        this.dateCreated = LocalDate.now();
        this.dateModified = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getDateModified() {
        return dateModified;
    }

    public void setDateModified(LocalDate dateModified) {
        this.dateModified = dateModified;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public List<Course> getMyModeratedCourses() {
        return myModeratedCourses;
    }

    public void setMyModeratedCourses(List<Course> myModeratedCourses) {
        this.myModeratedCourses = myModeratedCourses;
    }

    public List<Folder> getMyFolders() {
        return myFolders;
    }

    public void setMyFolders(List<Folder> myFolders) {
        this.myFolders = myFolders;
    }
}

