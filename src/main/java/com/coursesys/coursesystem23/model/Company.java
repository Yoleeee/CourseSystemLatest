package com.coursesys.coursesystem23.model;

import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Company extends User implements Serializable {

    private String name;
    private String companyRep;
    private String address;
    private String phoneNum;

    public Company() {
    }


    public Company(String name, String companyRep, String address, String phoneNum) {
        this.name = name;
        this.companyRep = companyRep;
        this.address = address;
        this.phoneNum = phoneNum;
    }

    public Company(int id, String login, String password, LocalDate dateCreated, LocalDate dateModified, UserType userType, List<Course> myModeratedCourses, List<Folder> myFolders, String name, String companyRep, String address, String phoneNum) {
        super(id, login, password, dateCreated, dateModified, userType, myModeratedCourses, myFolders);
        this.name = name;
        this.companyRep = companyRep;
        this.address = address;
        this.phoneNum = phoneNum;
        this.setUserType(UserType.MODERATOR);
    }

    public Company(String login, String password, String name, String companyRep, String address, String phoneNum) {
        super(login, password);
        this.name = name;
        this.companyRep = companyRep;
        this.address = address;
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyRep() {
        return companyRep;
    }

    public void setCompanyRep(String companyRep) {
        this.companyRep = companyRep;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
