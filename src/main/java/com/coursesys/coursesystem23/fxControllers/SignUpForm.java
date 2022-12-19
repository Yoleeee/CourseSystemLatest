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
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.coursesys.coursesystem23.utils.JavaFXUtils.alertMessage;

public class SignUpForm implements Initializable {
    public TextField loginF;
    public PasswordField passwordF;
    public RadioButton studentRadio;
    public ToggleGroup userType;
    public RadioButton companyRadio;
    public Label studentNameL;
    public Label studentSurnameL;
    public Label studentPhoneL;
    public Label studentEmailL;
    public Label companyNameL;
    public Label companyRepL;
    public Label companyAddressL;
    public Label companyPhoneL;
    public TextField firstF;
    public TextField secondF;
    public TextField thirdF;
    public TextField fourthF;


    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseSys");
    UserHibController userHibController = new UserHibController(entityManagerFactory);

    public void enableFields(ActionEvent actionEvent) {
        if(studentRadio.isSelected()){
            studentNameL.setVisible(true);
            studentSurnameL.setVisible(true);
            studentPhoneL.setVisible(true);
            studentEmailL.setVisible(true);
            companyNameL.setVisible(false);
            companyRepL.setVisible(false);
            companyPhoneL.setVisible(false);
            companyAddressL.setVisible(false);
        }else{
            studentNameL.setVisible(false);
            studentSurnameL.setVisible(false);
            studentPhoneL.setVisible(false);
            studentEmailL.setVisible(false);
            companyNameL.setVisible(true);
            companyRepL.setVisible(true);
            companyPhoneL.setVisible(true);
            companyAddressL.setVisible(true);
        }
    }

    public void createUser(ActionEvent actionEvent) throws IOException {
        if (!userHibController.getAllUsers().isEmpty()) {
            List<User> users = new ArrayList<>(userHibController.getAllUsers());
            for (User u: users) {
                if (u.getLogin().equals(loginF.getText())) {
                    alertMessage("User with this login already exists");
                    return;
                }
            }
        }

        if (studentRadio.isSelected()) {
            Person person = new Person(loginF.getText(), passwordF.getText(), firstF.getText(), secondF.getText(), fourthF.getText(), thirdF.getText());
            person.setUserType(UserType.STUDENT);
            userHibController.create(person);
        } else {
            Company company = new Company(loginF.getText(), passwordF.getText(), firstF.getText(), secondF.getText(), thirdF.getText(), fourthF.getText());
            company.setUserType(UserType.STUDENT);
            userHibController.create(company);
        }
        returnToPrev();
    }

    public void closeForm(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("login-form.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)loginF.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void returnToPrev() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("login-form.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)loginF.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
