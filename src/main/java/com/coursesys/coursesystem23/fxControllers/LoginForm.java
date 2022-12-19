package com.coursesys.coursesystem23.fxControllers;

import com.coursesys.coursesystem23.Start;
import com.coursesys.coursesystem23.hibControllers.UserHibController;
import com.coursesys.coursesystem23.model.Person;
import com.coursesys.coursesystem23.model.User;
import com.coursesys.coursesystem23.model.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.coursesys.coursesystem23.utils.JavaFXUtils.alertMessage;



public class LoginForm implements Initializable {


    public TextField loginF;
    public PasswordField passwordF;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseSys");
    UserHibController userHibController = new UserHibController(entityManagerFactory);

    public void validateAndLoadCourses(ActionEvent actionEvent) throws IOException {

        if (!userHibController.getAllUsers().isEmpty()) {
            boolean haveAdmin = false;
            List<User> users = new ArrayList<>(userHibController.getAllUsers());
            for (User u: users) {
                if (u.getUserType().equals(UserType.ADMIN)) {
                    haveAdmin = true;
                    break;
                }
            }
            if (!haveAdmin) {
                Person person = new Person("admin", "admin", "admin", "admin", "admin", "admin");
                person.setUserType(UserType.ADMIN);
                userHibController.create(person);
            }

        } else if (userHibController.getAllUsers().isEmpty()) {
            Person person = new Person("admin", "admin", "admin", "admin", "admin", "admin");
            person.setUserType(UserType.ADMIN);
            userHibController.create(person);
            return;
        }

        User user = userHibController.getUserByLoginData(loginF.getText(), passwordF.getText());

        if (user != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-coursesys-window.fxml"));
            Parent root = fxmlLoader.load();

            MainCoursesysWindow mainProjectsWindow = fxmlLoader.getController();
            mainProjectsWindow.setMainCourseData(user.getId());

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Course system");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } else {
            alertMessage("No such user");
        }

    }

    public void openSignUpForm(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("sign-up-form.fxml"));
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
