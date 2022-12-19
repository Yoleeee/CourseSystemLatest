package com.coursesys.coursesystem23.fxControllers;

import com.coursesys.coursesystem23.Start;
import com.coursesys.coursesystem23.hibControllers.CourseHibController;
import com.coursesys.coursesystem23.hibControllers.UserHibController;
import com.coursesys.coursesystem23.model.Course;
import com.coursesys.coursesystem23.model.User;
import com.coursesys.coursesystem23.model.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.coursesys.coursesystem23.utils.JavaFXUtils.alertMessage;

public class NewCourseForm {


    public TextField titleF;
    public TextArea descriptionF;
    public DatePicker startDateF;
    public DatePicker endDateF;
    private int userId;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseSys");
    CourseHibController courseHibController = new CourseHibController(entityManagerFactory);
    UserHibController userHibController = new UserHibController(entityManagerFactory);

    public void setNewCourseData(int userId) {
        this.userId = userId;
    }

    public void createCourse(ActionEvent actionEvent) throws IOException {
        User user = userHibController.getUserById(userId);
        List<Course> courses = new ArrayList<>(courseHibController.getAllCourses());
        for (Course c: courses) {
            if (c.getTitle().equals(titleF.getText())) {
                alertMessage("Course with title like this already exists");
                return;
            }
        }
        Course course = new Course(titleF.getText(),descriptionF.getText(),startDateF.getValue(),endDateF.getValue());
        courseHibController.createCourse(course);
        course.getCourseModerator().add(user);
        courseHibController.edit(course);
        for (User u: userHibController.getAllUsers()) {
            if(u.getUserType() != null && u.getUserType().equals(UserType.ADMIN) && u != user) {
                course.getCourseModerator().add(u);
            }
        }
        courseHibController.edit(course);
        for (User u: userHibController.getAllUsers()) {
            if(u.getUserType() != null && u.getUserType().equals(UserType.ADMIN) && u != user) {
                u.getMyModeratedCourses().add(course);
                userHibController.edit(u);
            }
        }


        user.getMyModeratedCourses().add(course);
        userHibController.edit(user);
        returnToMain();
    }

    public void returnToMain(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("my-moderated-courses-window.fxml"));

        Parent root = fxmlLoader.load();

        MyModeratedCoursesWindow myModeratedCoursesWindow = fxmlLoader.getController();
        myModeratedCoursesWindow.setMyModeratedCourseData(userId);

        Scene scene = new Scene(root);
        Stage stage = (Stage)titleF.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void returnToMain() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-coursesys-window.fxml"));

        Parent root = fxmlLoader.load();

        MainCoursesysWindow mainProjectsWindow = fxmlLoader.getController();
        mainProjectsWindow.setMainCourseData(userId);

        Scene scene = new Scene(root);
        Stage stage = (Stage)titleF.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
