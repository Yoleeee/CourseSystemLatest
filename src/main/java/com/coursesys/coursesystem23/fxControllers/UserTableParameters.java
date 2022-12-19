package com.coursesys.coursesystem23.fxControllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserTableParameters {

    private SimpleIntegerProperty userId = new SimpleIntegerProperty();
    private SimpleStringProperty login = new SimpleStringProperty();
    private SimpleStringProperty password = new SimpleStringProperty();
    private SimpleStringProperty dateCreated = new SimpleStringProperty();
    private SimpleStringProperty dateModified = new SimpleStringProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty surname = new SimpleStringProperty();
    private SimpleStringProperty email = new SimpleStringProperty();
    private SimpleStringProperty company = new SimpleStringProperty();
    private SimpleStringProperty phone = new SimpleStringProperty();
    private SimpleStringProperty representative = new SimpleStringProperty();
    private SimpleStringProperty address = new SimpleStringProperty();

    public UserTableParameters() {
    }

    public UserTableParameters(SimpleIntegerProperty userId, SimpleStringProperty login, SimpleStringProperty password, SimpleStringProperty dateCreated, SimpleStringProperty dateModified, SimpleStringProperty name, SimpleStringProperty surname, SimpleStringProperty email, SimpleStringProperty company, SimpleStringProperty phone, SimpleStringProperty representative, SimpleStringProperty address) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.company = company;
        this.phone = phone;
        this.representative = representative;
        this.address = address;
    }

    public int getUserId() {
        return userId.get();
    }

    public SimpleIntegerProperty userIdProperty() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public String getLogin() {
        return login.get();
    }

    public SimpleStringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getDateCreated() {
        return dateCreated.get();
    }

    public SimpleStringProperty dateCreatedProperty() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated.set(dateCreated);
    }

    public String getDateModified() {
        return dateModified.get();
    }

    public SimpleStringProperty dateModifiedProperty() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified.set(dateModified);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getCompany() {
        return company.get();
    }

    public SimpleStringProperty companyProperty() {
        return company;
    }

    public void setCompany(String company) {
        this.company.set(company);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getRepresentative() {
        return representative.get();
    }

    public SimpleStringProperty representativeProperty() {
        return representative;
    }

    public void setRepresentative(String representative) {
        this.representative.set(representative);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }
}
