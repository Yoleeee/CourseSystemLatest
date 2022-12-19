package com.coursesys.coursesystem23.fxControllers;

import com.coursesys.coursesystem23.Start;
import com.coursesys.coursesystem23.hibControllers.UserHibController;
import com.coursesys.coursesystem23.model.Company;
import com.coursesys.coursesystem23.model.Person;
import com.coursesys.coursesystem23.model.User;
import com.coursesys.coursesystem23.model.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MyAccount implements Initializable {
    public TextField firstF;
    public TextField secondF;
    public Label studentSurnameL;
    public Label studentNameL;
    public Text idF;
    public TextField thirdF;
    public Label studentEmailL;
    public TextField fourthF;
    public Button editButton;
    public Button saveButton;
    public Button cancelButton;
    public Label CompanyNameL;
    public Label companyRepL;
    public Label companyAddressL;

    private int userId;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseSys");
    UserHibController userHibController = new UserHibController(entityManagerFactory);

    public void setAccountInfo(int id) {
        userId = id;
        loadInfo();
    }

    public void editInformation(ActionEvent actionEvent) throws IOException {

        firstF.setEditable(true);
        secondF.setEditable(true);
        thirdF.setEditable(true);
        fourthF.setEditable(true);
        editButton.setVisible(false);
        saveButton.setVisible(true);
    }

    public void saveInformation(ActionEvent actionEvent) {

        User user = userHibController.getUserById(userId);
        if (user.getClass().equals(Person.class)) {
            Person person = userHibController.getPersonById(userId);
            person.setName(firstF.getText());
            person.setSurname(secondF.getText());
            person.setEmail(thirdF.getText());
            person.setPhoneNum(fourthF.getText());
            person.setDateModified(LocalDate.now());
            userHibController.edit(person);
        } else {
            Company company = userHibController.getCompanyById(userId);
            company.setName(firstF.getText());
            company.setCompanyRep(secondF.getText());
            company.setAddress(thirdF.getText());
            company.setPhoneNum(fourthF.getText());
            company.setDateModified(LocalDate.now());
            userHibController.edit(company);
        }
        firstF.setEditable(false);
        secondF.setEditable(false);
        thirdF.setEditable(false);
        fourthF.setEditable(false);
        editButton.setVisible(true);
        saveButton.setVisible(false);
    }

    public void cancleEditing(ActionEvent actionEvent) throws IOException {

    }

    private void loadInfo() {
        User user = userHibController.getUserById(userId);
        idF.setText(Integer.toString(userId));
        if (user.getClass().equals(Person.class)) {
            studentSurnameL.setVisible(true);
            studentNameL.setVisible(true);
            studentEmailL.setVisible(true);
            CompanyNameL.setVisible(false);
            companyRepL.setVisible(false);
            companyAddressL.setVisible(false);
            Person person = userHibController.getPersonById(userId);
            firstF.setText(person.getName());
            secondF.setText(person.getSurname());
            thirdF.setText(person.getEmail());
            fourthF.setText(person.getPhoneNum());
        } else {
            studentSurnameL.setVisible(false);
            studentNameL.setVisible(false);
            studentEmailL.setVisible(false);
            CompanyNameL.setVisible(true);
            companyRepL.setVisible(true);
            companyAddressL.setVisible(true);
            Company company = userHibController.getCompanyById(userId);
            firstF.setText(company.getName());
            secondF.setText(company.getCompanyRep());
            thirdF.setText(company.getAddress());
            fourthF.setText(company.getPhoneNum());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
