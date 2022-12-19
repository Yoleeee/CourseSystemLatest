package com.coursesys.coursesystem23.fxControllers;

import com.coursesys.coursesystem23.Start;
import com.coursesys.coursesystem23.hibControllers.CourseHibController;
import com.coursesys.coursesystem23.hibControllers.UserHibController;
import com.coursesys.coursesystem23.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.List;

public class MyAddedCourses {
    public Menu menuUsers;
    public ListView myCoursesList;
    public TreeView courseFolders;
    public Label coursesL;
    public MenuItem enrolledCoursesMenu;
    private int userId;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseSys");
    CourseHibController courseHibController = new CourseHibController(entityManagerFactory);
    UserHibController userHibController = new UserHibController(entityManagerFactory);

    public void setMyAddedCourseData(int id) {
        this.userId =id;
        if (userHibController.getUserById(userId).getUserType() != null) {
            User user = userHibController.getUserById(userId);
            if (!user.getClass().equals(Person.class) || user.getUserType().equals(UserType.ADMIN))
                enrolledCoursesMenu.setVisible(false);
            if (user.getUserType().equals(UserType.ADMIN)) {
                menuUsers.setVisible(true);
            }
        }
        fillTables();
    }

    private void fillTables() {
        Person person = userHibController.getPersonById(userId);
        myCoursesList.getItems().clear();
        List<Course> myEnrolledCourses = person.getMyEnrolledCourses();
        for (Course c: myEnrolledCourses) {
            myCoursesList.getItems().add(c);
        }
    }

    public void openMainWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-coursesys-window.fxml"));
        Parent root = fxmlLoader.load();

        MainCoursesysWindow mainProjectsWindow = fxmlLoader.getController();
        mainProjectsWindow.setMainCourseData(userId);

        Scene scene = new Scene(root);
        Stage stage = (Stage) coursesL.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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
        Stage stage = (Stage) coursesL.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void openMyAddedCourses(ActionEvent actionEvent) {

    }

    public void openMyModeratedCourses(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("my-moderated-courses-window.fxml"));
        Parent root = fxmlLoader.load();

        MyModeratedCoursesWindow myModeratedCoursesWindow = fxmlLoader.getController();
        myModeratedCoursesWindow.setMyModeratedCourseData(userId);

        Scene scene = new Scene(root);
        Stage stage = (Stage) coursesL.getScene().getWindow();
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

    public void showFolders(MouseEvent mouseEvent) {
        Course course = (Course) myCoursesList.getSelectionModel().getSelectedItem();
        courseFolders.setRoot(new TreeItem<String>("Course folders:"));
        course.getCourseFolders().forEach(folder -> addTreeItem(folder,courseFolders.getRoot()));
    }

    private void addTreeItem(Folder folder, TreeItem parentFolder){
        TreeItem<Folder> treeItem = new TreeItem<>(folder);
        parentFolder.getChildren().add(treeItem);
        folder.getSubFolders().forEach(sub->addTreeItem(sub,treeItem));
    }

    public void leaveCourse(ActionEvent actionEvent) {
        Person person = userHibController.getPersonById(userId);
        Course course = (Course) myCoursesList.getSelectionModel().getSelectedItem();
        courseHibController.removeStudent(person.getId(), course.getId());
        fillTables();
    }

    public void openFolder(MouseEvent mouseEvent) throws IOException {

        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2) {
                TreeItem<Folder> folder = (TreeItem<Folder>) courseFolders.getSelectionModel().getSelectedItem();

                FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("opened-folder.fxml"));
                Parent root = fxmlLoader.load();


                OpenedFolder openedFolder = fxmlLoader.getController();
                openedFolder.setOpenedFoldersInfo(folder.getValue().getId());
                openedFolder.editButton.setVisible(false);
                openedFolder.deleteB.setVisible(false);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle(folder.getValue().getTitle());
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            }
        }
    }
}
