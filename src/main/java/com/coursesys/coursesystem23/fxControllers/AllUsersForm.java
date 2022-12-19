package com.coursesys.coursesystem23.fxControllers;

import com.coursesys.coursesystem23.hibControllers.UserHibController;
import com.coursesys.coursesystem23.model.Company;
import com.coursesys.coursesystem23.model.Person;
import com.coursesys.coursesystem23.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

public class AllUsersForm implements Initializable {


    public TableView usersTable;
    public TableColumn<UserTableParameters, Integer> idCol;
    public TableColumn<UserTableParameters, String> loginCol;
    public TableColumn<UserTableParameters, String> passwordCol;
    public TableColumn<UserTableParameters, String> createdCol;
    public TableColumn<UserTableParameters, String> modifiedCol;
    public TableColumn<UserTableParameters, String> nameCol;
    public TableColumn<UserTableParameters, String> surnameCol;
    public TableColumn<UserTableParameters, String> emailCol;
    public TableColumn<UserTableParameters, String> companyCol;
    public TableColumn<UserTableParameters, String> phoneCol;
    public TableColumn<UserTableParameters, String> repCol;
    public TableColumn<UserTableParameters, String> addressCol;
    public TableColumn<UserTableParameters, Void> dummyField;

    private ObservableList<UserTableParameters> data = FXCollections.observableArrayList();

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CourseSys");
    UserHibController userHibController = new UserHibController(entityManagerFactory);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usersTable.setEditable(true);
        idCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        createdCol.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        modifiedCol.setCellValueFactory(new PropertyValueFactory<>("dateModified"));
        loginCol.setCellValueFactory(new PropertyValueFactory<>("login"));
        loginCol.setCellFactory(TextFieldTableCell.forTableColumn());
        loginCol.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setLogin(t.getNewValue());
                    User user = userHibController.getUserById(t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId());
                    user.setLogin(t.getTableView().getItems().get(t.getTablePosition().getRow()).getLogin());
                    userHibController.edit(user);
                }
        );
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        passwordCol.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordCol.setOnEditCommit(
                t -> {
                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setPassword(t.getNewValue());
                    User user = userHibController.getUserById(t.getTableView().getItems().get(t.getTablePosition().getRow()).getUserId());
                    user.setPassword(t.getTableView().getItems().get(t.getTablePosition().getRow()).getPassword());
                    userHibController.edit(user);
                }
        );
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        surnameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        companyCol.setCellValueFactory(new PropertyValueFactory<>("company"));
        companyCol.setCellFactory(TextFieldTableCell.forTableColumn());
        repCol.setCellValueFactory(new PropertyValueFactory<>("representative"));
        repCol.setCellFactory(TextFieldTableCell.forTableColumn());
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressCol.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phoneCol.setCellFactory(TextFieldTableCell.forTableColumn());

        Callback<TableColumn<UserTableParameters, Void>, TableCell<UserTableParameters, Void>> cellFactory = new Callback<TableColumn<UserTableParameters, Void>, TableCell<UserTableParameters, Void>>() {
            @Override
            public TableCell<UserTableParameters, Void> call(final TableColumn<UserTableParameters, Void> param) {
                final TableCell<UserTableParameters, Void> cell = new TableCell<UserTableParameters, Void>() {

                    private final Button button = new Button("Delete");

                    {
                        button.setOnAction((ActionEvent event) -> {
                            UserTableParameters data = getTableView().getItems().get(getIndex());
                            userHibController.remove(data.getUserId());
                            try {
                                loadUsers();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(button);
                        }
                    }
                };
                return cell;
            }
        };

        dummyField.setCellFactory(cellFactory);

        try {
            loadUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadUsers() throws SQLException {
        usersTable.setEditable(true);
        usersTable.getItems().clear();
        List<Person> persons = userHibController.getAllPersons();
        for (Person p : persons) {
            UserTableParameters userTableParameters = new UserTableParameters();
            userTableParameters.setUserId(p.getId());
            userTableParameters.setLogin(p.getLogin());
            userTableParameters.setPassword(p.getPassword());
            userTableParameters.setDateCreated(p.getDateCreated().toString());
            userTableParameters.setDateModified(p.getDateModified().toString());
            userTableParameters.setName(p.getName());
            userTableParameters.setSurname(p.getSurname());
            userTableParameters.setEmail(p.getEmail());
            data.add(userTableParameters);
        }

        List<Company> companies = userHibController.getAllCompanies();
        for (Company c : companies) {
            UserTableParameters userTableParameters = new UserTableParameters();
            userTableParameters.setUserId(c.getId());
            userTableParameters.setLogin(c.getLogin());
            userTableParameters.setPassword(c.getPassword());
            userTableParameters.setDateCreated(c.getDateCreated().toString());
            userTableParameters.setDateModified(c.getDateModified().toString());
            userTableParameters.setAddress(c.getAddress());
            userTableParameters.setRepresentative(c.getCompanyRep());
            userTableParameters.setPhone(c.getPhoneNum());
            userTableParameters.setCompany(c.getName());
            data.add(userTableParameters);
        }

        usersTable.setItems(data);
    }
}
