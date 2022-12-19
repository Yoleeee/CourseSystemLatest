package com.coursesys.coursesystem23.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Folder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @ManyToOne
    private Course parentCourse;
    @OneToMany(mappedBy = "parentFolder")
    @OrderBy("id ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Folder> subFolders;
    @ManyToOne
    private Folder parentFolder;
    @OneToMany(mappedBy = "folder", cascade = {CascadeType.ALL})
    @OrderBy("id ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<File> folderFiles;
    @ManyToMany(mappedBy = "myFolders")
    @OrderBy("id ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> editors;

    public Folder() {
    }

    public Folder(int id, String title, Course parentCourse, List<Folder> subFolders, Folder parentFolder, List<File> folderFiles, List<User> editors) {
        this.id = id;
        this.title = title;
        this.parentCourse = parentCourse;
        this.subFolders = subFolders;
        this.parentFolder = parentFolder;
        this.folderFiles = folderFiles;
        this.editors = editors;
    }

    public Folder(String title, Course parentCourse, Folder parentFolder, List<User> editors) {
        this.title = title;
        this.parentCourse = parentCourse;
        this.subFolders = new ArrayList<>();
        this.folderFiles = new ArrayList<>();
        this.parentFolder = parentFolder;
        this.editors = editors;
    }

    public Folder(String title, Course parentCourse, List<User> editors) {
        this.title = title;
        this.parentCourse = parentCourse;
        this.subFolders = new ArrayList<>();
        this.folderFiles = new ArrayList<>();
        this.editors = editors;
    }

    public Folder(String title, Folder parentFolder, List<User> editors) {
        this.title = title;
        this.parentFolder = parentFolder;
        this.subFolders = new ArrayList<>();
        this.folderFiles = new ArrayList<>();
        this.editors = editors;
    }

    public Folder(String title, Course parentCourse, List<Folder> subFolders, Folder parentFolder, List<User> editors) {
        this.title = title;
        this.parentCourse = parentCourse;
        this.subFolders = subFolders;
        this.parentFolder = parentFolder;
        this.folderFiles = new ArrayList<>();
        this.editors = editors;
    }

    public Folder(String title, Course parentCourse, List<Folder> subFolders, List<File> folderFiles, List<User> editors) {
        this.title = title;
        this.parentCourse = parentCourse;
        this.subFolders = subFolders;
        this.folderFiles = new ArrayList<>();
        this.editors = editors;
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

    public Course getParentCourse() {
        return parentCourse;
    }

    public void setParentCourse(Course parentCourse) {
        this.parentCourse = parentCourse;
    }

    public List<Folder> getSubFolders() {
        return subFolders;
    }

    public void setSubFolders(List<Folder> subFolders) {
        this.subFolders = subFolders;
    }

    public Folder getParentFolder() {
        return parentFolder;
    }

    public void setParentFolder(Folder parentFolder) {
        this.parentFolder = parentFolder;
    }

    public List<File> getFolderFiles() {
        return folderFiles;
    }

    public void setFolderFiles(List<File> folderFiles) {
        this.folderFiles = folderFiles;
    }

    public List<User> getEditors() {
        return editors;
    }

    public void setEditors(List<User> editors) {
        this.editors = editors;
    }
}
