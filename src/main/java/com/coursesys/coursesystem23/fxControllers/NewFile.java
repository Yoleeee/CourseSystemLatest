package com.coursesys.coursesystem23.fxControllers;

import com.coursesys.coursesystem23.hibControllers.CourseHibController;
import com.coursesys.coursesystem23.hibControllers.FolderHibController;
import com.coursesys.coursesystem23.hibControllers.UserHibController;
import com.coursesys.coursesystem23.model.Course;
import com.coursesys.coursesystem23.model.File;
import com.coursesys.coursesystem23.model.Folder;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

import static com.coursesys.coursesystem23.utils.JavaFXUtils.alertMessage;

public class NewFile {
    public TextField fileNameF;
    public TextField fileLocationF;
    public ComboBox fileComboBox;

    private int courseId;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseSys");
    FolderHibController folderHibController = new FolderHibController(entityManagerFactory);

    public void setNefFileInfo(int id) {

        this.courseId = id;
        fileComboBox.getItems().addAll(folderHibController.getCourseFolders(courseId));
    }

    public void createFile(ActionEvent actionEvent) {

        List<File> files = new ArrayList<>(folderHibController.getAllFiles());
        for (File f: files) {
            if (f.getFolder().getId() == ((Folder) fileComboBox.getSelectionModel().getSelectedItem()).getId() && f.getName().equals(fileNameF.getText())) {
                alertMessage("FIle with this name already exists in folder");
                return;
            }
        }
        if (fileComboBox.getSelectionModel().getSelectedItem() != null) {
            File file = new File(fileNameF.getText(), fileLocationF.getText(), (Folder) fileComboBox.getSelectionModel().getSelectedItem());
            folderHibController.createFile(file);
        }
        else {
            alertMessage("Please select folder");
            return;
        }

        alertMessage("File successfully added to Folder:" + fileComboBox.getSelectionModel().getSelectedItem());

    }
}
