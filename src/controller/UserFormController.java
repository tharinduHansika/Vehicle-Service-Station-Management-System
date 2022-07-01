package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Customer;
import models.User;
import util.CrudUtil;
import view.tm.CustomerTM;
import view.tm.UserTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserFormController {
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    public TableView tblUser;
    public TableColumn colType;
    public TableColumn colUserName;
    public TableColumn colPassword;
    public TableColumn colOption;
    public JFXComboBox<String> cmbType;
    public JFXButton btnSave;

    public void initialize(){
        ObservableList observableList= FXCollections.observableArrayList();
        observableList.add("Owner");
        observableList.add("Cashier");
        cmbType.setItems(observableList);

        colType.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        try {
            loadAllUser();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        tblUser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                setData((UserTM)newValue);
            }
        });
    }

    private void loadAllUser() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Password");
        ObservableList<UserTM> obList = FXCollections.observableArrayList();

        while (result.next()) {
            Button btn = new Button("Delete");
            obList.add(
                    new UserTM(
                            result.getString("accountType"),
                            result.getString("userName"),
                            result.getString("password"),
                            btn
                    )
            );

            tblUser.setItems(obList);

            btn.setOnAction((e) -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get().equals(ButtonType.YES)) {
                    try {
                        CrudUtil.execute("DELETE FROM Password WHERE userName=?", txtUserName.getText());
                        loadAllUser();
                        btnSave.setText("Add+");
                        txtClear();
                        tblUser.refresh();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                }
                tblUser.refresh();
            });
        }
    }

    private void setData(UserTM tm) {
        try {
            cmbType.setValue(tm.getAccountType());
            txtUserName.setText(tm.getUserName());
            txtPassword.setText(tm.getPassword());
        } catch (NullPointerException e) {

        }
    }

    private void txtClear() {
        cmbType.setSelectionModel(null);
        txtUserName.clear();
        txtPassword.clear();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        tblUser.refresh();
        User u=new User(cmbType.getValue(),txtUserName.getText(),txtPassword.getText());

        try{
            if (btnSave.getText().equals("Save")) {
                if (CrudUtil.execute("INSERT INTO password VALUES(?,?,?)", u.getAccountType(), u.getUserName(), u.getPassword())) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();
                    loadAllUser();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Error!").show();
                }
            }else {
                if (CrudUtil.execute("UPDATE password SET password=? WHERE userName=?",
                        u.getPassword(),u.getUserName())){
                    new Alert(Alert.AlertType.CONFIRMATION,"Updated!").show();
                    tblUser.refresh();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Error!").show();
                }
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
