package com.coursesys.coursesystem23.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    private String title;
    private String description;
    private LocalDate dateCreated;
    private LocalDate dateModified;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToMany(mappedBy = "myModeratedCourses")
    @OrderBy("id ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> courseModerator;
    @ManyToMany
    @JoinTable(name = "course_students")
    @OrderBy("id ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Person> students;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "course_folders")
    @OrderBy("id ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Folder> courseFolders;

    public Course() {
    }

    public Course(int id, String title, String description, LocalDate dateCreated, LocalDate dateModified, LocalDate startDate, LocalDate endDate, List<User> courseModerator, List<Person> students, List<Folder> courseFolders) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseModerator = courseModerator;
        this.students = students;
        this.courseFolders = courseFolders;
    }

    public Course(String title, String description, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dateModified = LocalDate.now();
        this.dateCreated = LocalDate.now();
        this.courseModerator = new ArrayList<>();
        this.courseFolders = new ArrayList<>();
    }

    @Override
    public String toString() {
        return this.title;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<User> getCourseModerator() {
        return courseModerator;
    }

    public void setCourseModerator(List<User> courseModerator) {
        this.courseModerator = courseModerator;
    }

    public List<Person> getStudents() {
        return students;
    }

    public void setStudents(List<Person> students) {
        this.students = students;
    }

    public List<Folder> getCourseFolders() {
        return courseFolders;
    }

    public void setCourseFolders(List<Folder> courseFolders) {
        this.courseFolders = courseFolders;
    }
}
