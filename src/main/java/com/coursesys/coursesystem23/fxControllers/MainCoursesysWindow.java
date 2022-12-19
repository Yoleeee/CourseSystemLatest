package com.coursesys.coursesystem23.fxControllers;

import com.coursesys.coursesystem23.Start;
import com.coursesys.coursesystem23.hibControllers.CourseHibController;
import com.coursesys.coursesystem23.hibControllers.UserHibController;
import com.coursesys.coursesystem23.model.Course;
import com.coursesys.coursesystem23.model.Person;
import com.coursesys.coursesystem23.model.User;
import com.coursesys.coursesystem23.model.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.coursesys.coursesystem23.utils.JavaFXUtils.alertMessage;

public class MainCoursesysWindow implements Initializable {


    public Menu menuUsers;
    public ListView coursesList;
    public Text courseDescriptionF;
    public MenuItem enrolledCoursesMenu;
    public Button addB;
    private int userId;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseSys");
    CourseHibController courseHibController = new CourseHibController(entityManagerFactory);
    UserHibController userHibController = new UserHibController(entityManagerFactory);

    public void setMainCourseData(int id) {
        this.userId = id;
        if (userHibController.getUserById(userId).getUserType() != null) {
            User user = userHibController.getUserById(userId);
            if (!user.getClass().equals(Person.class) || user.getUserType().equals(UserType.ADMIN)) {
                enrolledCoursesMenu.setVisible(false);
                addB.setVisible(false);
            }


            if (user.getUserType().equals(UserType.ADMIN)) {
                menuUsers.setVisible(true);
            }
        }
        fillTables();
    }

    private void fillTables() {
        coursesList.getItems().clear();
        List<Course> allCourses = courseHibController.getAllCourses();
        for (Course c : allCourses) {
            coursesList.getItems().add(c);
        }
        System.out.println(userHibController.getUserById(userId).getUserType());
    }

    public void openMainWindow(ActionEvent actionEvent) {

    }

    public void openMyAccount(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("my-account.fxml"));
        Parent root = fxmlLoader.load();

        MyAccount myAccount = fxmlLoader.getController();
        myAccount.setAccountInfo(userId);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("My Account");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void newCourseForm(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("new-course-form.fxml"));

        Parent root = fxmlLoader.load();

        NewCourseForm newCourseForm = fxmlLoader.getController();
        newCourseForm.setNewCourseData(userId);

        Scene scene = new Scene(root);
        Stage stage = (Stage) courseDescriptionF.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void openMyAddedCourses(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("my-added-course-windows.fxml"));
        Parent root = fxmlLoader.load();

        MyAddedCourses myAddedCourses = fxmlLoader.getController();
        myAddedCourses.setMyAddedCourseData(userId);

        Scene scene = new Scene(root);
        Stage stage = (Stage) courseDescriptionF.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void openMyModeratedCourses(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("my-moderated-courses-window.fxml"));
        Parent root = fxmlLoader.load();

        MyModeratedCoursesWindow myModeratedCoursesWindow = fxmlLoader.getController();
        myModeratedCoursesWindow.setMyModeratedCourseData(userId);

        Scene scene = new Scene(root);
        Stage stage = (Stage) courseDescriptionF.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void openListOfUsers(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("all-users-form.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Users");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void showDescription(MouseEvent mouseEvent) {
        Course course = (Course) coursesList.getSelectionModel().getSelectedItem();
        courseDescriptionF.setText(course.getDescription());
    }

    public void addToMyCourses(ActionEvent actionEvent) {
        Course course = (Course) coursesList.getSelectionModel().getSelectedItem();
        Person person = userHibController.getPersonById(userId);
        for(User u: course.getCourseModerator()) {
            if (u.getId() == userId) {
                alertMessage("You are already moderator of this course");
                return;
            }
        }
        for(User u: course.getStudents()) {
            if (u.getId() == userId) {
                alertMessage("You are already student of this course");
                return;
            }
        }
        person.getMyEnrolledCourses().add(course);
        course.getStudents().add(person);
        courseHibController.edit(course);
        fillTables();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
