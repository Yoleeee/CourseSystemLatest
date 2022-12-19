package com.coursesys.coursesystem23.fxControllers;

import com.coursesys.coursesystem23.hibControllers.CourseHibController;
import com.coursesys.coursesystem23.hibControllers.FolderHibController;
import com.coursesys.coursesystem23.hibControllers.UserHibController;
import com.coursesys.coursesystem23.model.Course;
import com.coursesys.coursesystem23.model.File;
import com.coursesys.coursesystem23.model.Folder;
import com.coursesys.coursesystem23.model.Person;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.lang.reflect.Field;
import java.util.List;

import static com.coursesys.coursesystem23.utils.JavaFXUtils.alertMessage;

public class OpenedFolder {

    public ListView listFiles;
    public Button deleteB;
    public Button editButton;
    public TextField fileNameF;
    public TextField fileLocationF;
    public Text fileFOlderT;
    public Button saveB;

    private int foldersId;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseSys");
    FolderHibController folderHibController = new FolderHibController(entityManagerFactory);

    public void setOpenedFoldersInfo(int id) {

        this.foldersId = id;
        fillTable(foldersId);
    }

    public void deleteFile(ActionEvent actionEvent) {

        if(!listFiles.getSelectionModel().isEmpty()) {
            int fileId = ((File) listFiles.getSelectionModel().getSelectedItem()).getId();
            folderHibController.removeFile(fileId);
            fillTable(foldersId);
            fileNameF.clear();
            fileLocationF.clear();
            fileFOlderT.setText("");
        }
    }

    public void editFile(ActionEvent actionEvent) {
        if(!listFiles.getSelectionModel().isEmpty()){
            deleteB.setVisible(false);
            editButton.setVisible(false);
            saveB.setVisible(true);
            fileNameF.setEditable(true);
            fileLocationF.setEditable(true);
        }
    }

    public void saveFile(ActionEvent actionEvent) {
        deleteB.setVisible(true);
        editButton.setVisible(true);
        saveB.setVisible(false);
        File file = new File(fileNameF.getText(), fileLocationF.getText(), folderHibController.getFolderById(foldersId));
        folderHibController.editFile(file);
        fileNameF.setEditable(false);
        fileLocationF.setEditable(false);
    }



    private void fillTable(int foldersId) {

        Folder folder = folderHibController.getFolderById(foldersId);
        listFiles.getItems().clear();
        List<File> files = folder.getFolderFiles();
        for (File f: files) {
            listFiles.getItems().add(f);
        }
    }

    public void showInfoAboutFile(MouseEvent mouseEvent)
    {
        if (!listFiles.getSelectionModel().isEmpty()) {
            loadFileInfo();
        }
    }

    private void loadFileInfo() {
        File file = (File) listFiles.getSelectionModel().getSelectedItem();
        fileNameF.setText(file.getName());
        fileLocationF.setText(file.getLocation());
        fileFOlderT.setText(file.getFolder().toString());
    }
}
