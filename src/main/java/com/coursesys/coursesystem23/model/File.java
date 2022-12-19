package com.coursesys.coursesystem23.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String location;
    private LocalDate dateCreated;
    private LocalDate dateModified;
    @ManyToOne
    private Folder folder;

    public File() {
    }

    public File(int id, String name, String location, LocalDate dateCreated, LocalDate dateModified, Folder folder) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.folder = folder;
    }

    public File(String name, String location, Folder folder) {
        this.name = name;
        this.location = location;
        this.folder = folder;
        this.dateCreated = LocalDate.now();
        this.dateModified = LocalDate.now();
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }
}
