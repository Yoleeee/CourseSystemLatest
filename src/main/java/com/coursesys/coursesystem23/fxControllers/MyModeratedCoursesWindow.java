package com.coursesys.coursesystem23.fxControllers;

import com.coursesys.coursesystem23.Start;
import com.coursesys.coursesystem23.hibControllers.CourseHibController;
import com.coursesys.coursesystem23.hibControllers.FolderHibController;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.coursesys.coursesystem23.utils.JavaFXUtils.alertMessage;

public class MyModeratedCoursesWindow {
    public ListView coursesList;
    public TreeView courseFolders;
    public Button addFolderB;
    public Button edtiCourseInfoButton;
    public Button deleteFolderB;
    public Button addFileB;
    public Button deleteCourseB;
    public Button deleteStudentB;
    public Label titleL;
    public TextField titleF;
    public Label descriptionL;
    public TextArea descriptionF;
    public Label satrtDateL;
    public Label endDateL;
    public DatePicker startDateDP;
    public DatePicker endDateDP;
    public Button saveInfoB;
    public Button addModeratorB;
    public ListView usersList;
    public Label usersToModL;
    public TextField moderatorsLoginF;
    public Button saveModerB;
    public Button deleteModeratorB;
    public Label newFoldersNameL;
    public TextField newFoldersNameF;
    public ComboBox foldersComboBox;
    public Button saveNewFolderB;
    public Menu menuUsers;
    public MenuItem enrolledCoursesMenu;

    private int userId;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseSys");
    CourseHibController courseHibController = new CourseHibController(entityManagerFactory);
    UserHibController userHibController = new UserHibController(entityManagerFactory);
    FolderHibController folderHibController = new FolderHibController(entityManagerFactory);

    public void setMyModeratedCourseData(int id) {
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
        System.out.println(userId);
        User user = userHibController.getUserById(userId);
        coursesList.getItems().clear();
        List<Course> myModeratedCourses = user.getMyModeratedCourses();
        for (Course c: myModeratedCourses) {
            coursesList.getItems().add(c);
        }
    }

    public void openMainWindow(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("main-coursesys-window.fxml"));
        Parent root = fxmlLoader.load();

        MainCoursesysWindow mainProjectsWindow = fxmlLoader.getController();
        mainProjectsWindow.setMainCourseData(userId);

        Scene scene = new Scene(root);
        Stage stage = (Stage) titleF.getScene().getWindow();
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
        Stage stage = (Stage) titleF.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void openMyAddedCourses(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("my-added-course-windows.fxml"));
        Parent root = fxmlLoader.load();

        MyAddedCourses myAddedCourses = fxmlLoader.getController();
        myAddedCourses.setMyAddedCourseData(userId);

        Scene scene = new Scene(root);
        Stage stage = (Stage) titleF.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void openMyModeratedCourses(ActionEvent actionEvent) {
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
        loadFolders();
    }

    public void addFolder(ActionEvent actionEvent) {

        Course course = (Course) coursesList.getSelectionModel().getSelectedItem();
        courseFolders.setVisible(false);
        newFoldersNameL.setVisible(true);
        newFoldersNameF.setVisible(true);
        foldersComboBox.setVisible(true);
        saveNewFolderB.setVisible(true);
        addFileB.setVisible(false);
        addFolderB.setVisible(false);
        deleteFolderB.setVisible(false);
        foldersComboBox.getItems().clear();
        List<Folder> folders = new ArrayList<>(folderHibController.getCourseFolders(course.getId()));
        folders.removeIf(f -> f.getParentFolder() != null);
        foldersComboBox.getItems().addAll(folders);
    }

    public void saveNewFolder(ActionEvent actionEvent) {
        Course course = (Course) coursesList.getSelectionModel().getSelectedItem();
        TreeItem<Folder> folderTreeItem = (TreeItem<Folder>) courseFolders.getSelectionModel().getSelectedItem();
        int courseID = course.getId();
        if (!folderHibController.getCourseFolders(courseID).isEmpty() && foldersComboBox.getSelectionModel().getSelectedItem()!= null) {
            Course course1 = courseHibController.getCourseById(courseID);
            Folder parentFolder = folderHibController.getFolderById(((Folder) foldersComboBox.getSelectionModel().getSelectedItem()).getId());
            for(Folder f: parentFolder.getSubFolders()) {
                if (f.getTitle().equals(newFoldersNameF.getText())) {
                    alertMessage("Folder with this name already exists");
                    return;
                }
            }
            Folder folder = new Folder(newFoldersNameF.getText(), course1, parentFolder, course1.getCourseModerator());
            course1.getCourseFolders().add(folder);
            courseHibController.edit(course1);
        } else if(courseID != 0){
            Course course1 = courseHibController.getCourseById(courseID);
            ArrayList<Folder> folders = new ArrayList<>(course1.getCourseFolders());
            folders.removeIf(f -> f.getParentFolder() != null);
            for (Folder f: folders) {
                if (f.getTitle().equals(newFoldersNameF.getText())) {
                    alertMessage("Folder with this name already exists");
                   return;
                }
            }
            Folder folder = new Folder(newFoldersNameF.getText(), course1, course1.getCourseModerator());
            course1.getCourseFolders().add(folder);
            courseHibController.edit(course1);
        }
        courseFolders.setVisible(true);
        newFoldersNameL.setVisible(false);
        newFoldersNameF.setVisible(false);
        foldersComboBox.setVisible(false);
        saveNewFolderB.setVisible(false);
        addFileB.setVisible(true);
        addFolderB.setVisible(true);
        deleteFolderB.setVisible(true);
        loadFolders();
        fillTables();
    }

    public void editCourse(ActionEvent actionEvent) {

        titleF.setEditable(true);
        descriptionF.setEditable(true);
        endDateDP.setEditable(true);
        startDateDP.setEditable(true);
        edtiCourseInfoButton.setVisible(false);
        saveInfoB.setVisible(true);
    }

    public void loadCourseFolders(ActionEvent actionEvent) {

        courseFolders.setVisible(true);
        addFolderB.setVisible(true);
        edtiCourseInfoButton.setVisible(false);
        deleteFolderB.setVisible(true);
        addFileB.setVisible(true);
        deleteCourseB.setVisible(false);
        deleteStudentB.setVisible(false);
        titleL.setVisible(false);
        titleF.setVisible(false);
        descriptionL.setVisible(false);
        descriptionF.setVisible(false);
        satrtDateL.setVisible(false);
        endDateL.setVisible(false);
        startDateDP.setVisible(false);
        endDateDP.setVisible(false);
        saveInfoB.setVisible(false);
        addModeratorB.setVisible(false);
        usersList.setVisible(false);
        usersToModL.setVisible(false);
        moderatorsLoginF.setVisible(false);
        saveModerB.setVisible(false);
        deleteModeratorB.setVisible(false);
        newFoldersNameL.setVisible(false);
        newFoldersNameF.setVisible(false);
        foldersComboBox.setVisible(false);
        saveNewFolderB.setVisible(false);
        loadFolders();
    }

    private void loadFolders() {
        foldersComboBox.getItems().clear();
        Course course = (Course) coursesList.getSelectionModel().getSelectedItem();
        courseFolders.setRoot(new TreeItem<>("Course folders:"));
        for (Folder f: course.getCourseFolders()) {
                if (f.getParentFolder() == null) {
                    addTreeItem(f, courseFolders.getRoot());
                }
        }
    }

    private void addTreeItem(Folder folder, TreeItem parentFolder){
        TreeItem<Folder> treeItem = new TreeItem<>(folder);
        parentFolder.getChildren().add(treeItem);
        if (!folder.getSubFolders().isEmpty()) {
            for (Folder f: folder.getSubFolders()) {
                addTreeItem(f, treeItem);
            }
        }
    }

    public void loadStudentsList(ActionEvent actionEvent) {
        courseFolders.setVisible(false);
        addFolderB.setVisible(false);
        edtiCourseInfoButton.setVisible(false);
        deleteFolderB.setVisible(false);
        addFileB.setVisible(false);
        deleteCourseB.setVisible(false);
        deleteStudentB.setVisible(true);
        titleL.setVisible(false);
        titleF.setVisible(false);
        descriptionL.setVisible(false);
        descriptionF.setVisible(false);
        satrtDateL.setVisible(false);
        endDateL.setVisible(false);
        startDateDP.setVisible(false);
        endDateDP.setVisible(false);
        saveInfoB.setVisible(false);
        addModeratorB.setVisible(false);
        usersList.setVisible(true);
        usersToModL.setVisible(false);
        moderatorsLoginF.setVisible(false);
        saveModerB.setVisible(false);
        deleteModeratorB.setVisible(false);
        newFoldersNameL.setVisible(false);
        newFoldersNameF.setVisible(false);
        foldersComboBox.setVisible(false);
        saveNewFolderB.setVisible(false);
        loadStudents();
    }

    private void loadStudents() {
        usersList.getItems().clear();
        Course course = (Course) coursesList.getSelectionModel().getSelectedItem();
        List<Person> students = course.getStudents();
        System.out.println(students);
        for (Person p: students)
            usersList.getItems().add(p);
    }

    public void showCourseInfo(ActionEvent actionEvent) {
        courseFolders.setVisible(false);
        addFolderB.setVisible(false);
        edtiCourseInfoButton.setVisible(true);
        deleteFolderB.setVisible(false);
        addFileB.setVisible(false);
        deleteCourseB.setVisible(true);
        deleteStudentB.setVisible(false);
        titleL.setVisible(true);
        titleF.setVisible(true);
        descriptionL.setVisible(true);
        descriptionF.setVisible(true);
        satrtDateL.setVisible(true);
        endDateL.setVisible(true);
        startDateDP.setVisible(true);
        endDateDP.setVisible(true);
        saveInfoB.setVisible(false);
        addModeratorB.setVisible(false);
        usersList.setVisible(false);
        usersToModL.setVisible(false);
        moderatorsLoginF.setVisible(false);
        saveModerB.setVisible(false);
        deleteModeratorB.setVisible(false);
        newFoldersNameL.setVisible(false);
        newFoldersNameF.setVisible(false);
        foldersComboBox.setVisible(false);
        saveNewFolderB.setVisible(false);
        Course course = (Course) coursesList.getSelectionModel().getSelectedItem();
        titleF.setText(course.getTitle());
        descriptionF.setText(course.getDescription());
        endDateDP.setValue(course.getEndDate());
        startDateDP.setValue(course.getStartDate());
    }

    public void deleteFolder(ActionEvent actionEvent) {
        TreeItem<Folder> folder = (TreeItem<Folder>) courseFolders.getSelectionModel().getSelectedItem();
        int foldersId = folder.getValue().getId();
        removeFolder(foldersId);
        folderHibController.remove(foldersId);
        loadFolders();
        fillTables();
    }

    public void removeFolder(int foldersId) {
        if(!folderHibController.getFolderById(foldersId).getSubFolders().isEmpty()){
            for (Folder f: folderHibController.getFolderById(foldersId).getSubFolders()) {
                if (!f.getSubFolders().isEmpty()) {
                    removeFolder(f.getId());
                } else {
                    folderHibController.remove(f.getId());
                }
            }
        } else {
            folderHibController.remove(foldersId);
        }
    }

    public void addFIle(ActionEvent actionEvent) throws IOException {

        if( !((Course)coursesList.getSelectionModel().getSelectedItem()).getCourseFolders().isEmpty()) {
            FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("new-file.fxml"));

            Parent root = fxmlLoader.load();

            NewFile newFile = fxmlLoader.getController();
            newFile.setNefFileInfo( ((Course) coursesList.getSelectionModel().getSelectedItem()).getId());

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Create New File");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } else {
            alertMessage("This course dont have folders to place file");
        }
    }

    public void deleteCourse(ActionEvent actionEvent) {

        int courseId = ((Course) coursesList.getSelectionModel().getSelectedItem()).getId();
        courseHibController.remove(courseId);
        alertMessage("Curse removed");
        fillTables();
    }

    public void deleteStudentFromCourse(ActionEvent actionEvent) {

        Person person = (Person) usersList.getSelectionModel().getSelectedItem();
        Course course = (Course) coursesList.getSelectionModel().getSelectedItem();
        courseHibController.removeStudent(person.getId(), course.getId());
        loadStudents();
    }

    public void loadModeratorsList(ActionEvent actionEvent) {
        courseFolders.setVisible(false);
        addFolderB.setVisible(false);
        edtiCourseInfoButton.setVisible(false);
        deleteFolderB.setVisible(false);
        addFileB.setVisible(false);
        deleteCourseB.setVisible(false);
        deleteStudentB.setVisible(false);
        titleL.setVisible(false);
        titleF.setVisible(false);
        descriptionL.setVisible(false);
        descriptionF.setVisible(false);
        satrtDateL.setVisible(false);
        endDateL.setVisible(false);
        startDateDP.setVisible(false);
        endDateDP.setVisible(false);
        saveInfoB.setVisible(false);
        addModeratorB.setVisible(true);
        usersList.setVisible(true);
        usersToModL.setVisible(false);
        moderatorsLoginF.setVisible(false);
        saveModerB.setVisible(false);
        deleteModeratorB.setVisible(true);
        newFoldersNameL.setVisible(false);
        newFoldersNameF.setVisible(false);
        foldersComboBox.setVisible(false);
        saveNewFolderB.setVisible(false);
        loadModerators();
    }

    private void loadModerators() {
        usersList.getItems().clear();
        Course course = (Course) coursesList.getSelectionModel().getSelectedItem();
        List<User> moderators = course.getCourseModerator();
        for (User u: moderators)
            usersList.getItems().add(u);
    }

    public void saveCourseInfo(ActionEvent actionEvent) {

        Course course = (Course) coursesList.getSelectionModel().getSelectedItem();
        course.setTitle(titleF.getText());
        course.setDescription(descriptionF.getText());
        course.setStartDate(startDateDP.getValue());
        course.setEndDate(endDateDP.getValue());
        course.setDateModified(LocalDate.now());
        courseHibController.edit(course);
        titleF.setEditable(false);
        descriptionF.setEditable(false);
        endDateDP.setEditable(false);
        startDateDP.setEditable(false);
        edtiCourseInfoButton.setVisible(true);
        saveInfoB.setVisible(false);
    }

    public void addModeratorToCourse(ActionEvent actionEvent) {
        addModeratorB.setVisible(false);
        saveModerB.setVisible(true);
        usersList.setVisible(false);
        moderatorsLoginF.setVisible(true);
        usersToModL.setVisible(true);
    }

    public void addModeratorByLogin(ActionEvent actionEvent) {

        addModeratorB.setVisible(true);
        saveModerB.setVisible(false);
        usersList.setVisible(true);
        moderatorsLoginF.setVisible(false);
        usersToModL.setVisible(false);
        User user = userHibController.getUserByLogin(moderatorsLoginF.getText());
        Course course = (Course) coursesList.getSelectionModel().getSelectedItem();
        if (user != null) {
            course.getCourseModerator().add(user);
            user.getMyModeratedCourses().add(course);
            courseHibController.edit(course);
            userHibController.edit(user);
        }
        loadModerators();
    }

    public void deleteModeratorFromCourse(ActionEvent actionEvent) {

        User user = (User) usersList.getSelectionModel().getSelectedItem();
        if (user.getUserType().equals(UserType.ADMIN)) {
            alertMessage("You cannot delete admin");
            return;
        }
        if (user.getId() == userId) {
            alertMessage("You cannot delete yourself form course");
            return;
        }
        Course course = (Course) coursesList.getSelectionModel().getSelectedItem();
        courseHibController.removeModerator(user.getId(), course.getId());
        loadModerators();
    }

    public void openFolder(MouseEvent mouseEvent) throws IOException {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 2) {
                    TreeItem<Folder> folder = (TreeItem<Folder>) courseFolders.getSelectionModel().getSelectedItem();

                    FXMLLoader fxmlLoader = new FXMLLoader(Start.class.getResource("opened-folder.fxml"));
                    Parent root = fxmlLoader.load();


                    OpenedFolder openedFolder = fxmlLoader.getController();
                    openedFolder.setOpenedFoldersInfo(folder.getValue().getId());

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
