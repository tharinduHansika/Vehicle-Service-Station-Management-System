package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Customer;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.CustomerTM;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class CustomerFormController {
    public JFXTextField txtID;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtTelephone;
    public TableColumn colNIC;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colTelephone;
    public TableColumn colOption;
    public TableView tblCustomer;
    public JFXButton btnSave;
    public Label lblDate;
    public Label lblTime;
    public JFXButton btnNext;
    public AnchorPane context;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();

    public void initialize(){
        colNIC.setCellValueFactory(new PropertyValueFactory("NIC"));
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colTelephone.setCellValueFactory(new PropertyValueFactory("telephone"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        try {
            loadAllCustomers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        loadDateAndTime();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                setData((CustomerTM)newValue);
            }
        });

        Pattern idPattern = Pattern.compile("^(C)[0-9]{3}$");
        Pattern namePattern = Pattern.compile("^[A-z ]{3,30}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,30}$");
        Pattern telephonePattern = Pattern.compile("^(0)[0-9]{2}(-)[0-9]{7}$");

        map.put(txtID,idPattern);
        map.put(txtName,namePattern);
        map.put(txtAddress,addressPattern);
        map.put(txtTelephone,telephonePattern);
    }

    private void setData(CustomerTM tm) {
        txtID.setText(tm.getNIC());
        txtName.setText(tm.getName());
        txtAddress.setText(tm.getAddress());
        txtTelephone.setText(tm.getTelephone());
    }

    private void loadAllCustomers() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Customer");
        ObservableList<CustomerTM> obList = FXCollections.observableArrayList();

        while (result.next()) {
            Button btn = new Button("Delete");
            obList.add(
                    new CustomerTM(
                            result.getString("NIC"),
                            result.getString("name"),
                            result.getString("address"),
                            result.getString("telephone"),
                            btn
                    )
            );

            tblCustomer.setItems(obList);

            btn.setOnAction((e) -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get().equals(ButtonType.YES)) {
                    try {
                        CrudUtil.execute("DELETE FROM customer WHERE NIC=?", txtID.getText());
                        loadAllCustomers();
                        btnSave.setText("Add+");
                        txtClear();
                        tblCustomer.refresh();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                }
                tblCustomer.refresh();
            });
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        tblCustomer.refresh();
        Customer c=new Customer(txtID.getText(),txtName.getText(),txtAddress.getText(),txtTelephone.getText());

        try{
            if (btnSave.getText().equals("Save")) {
                if (CrudUtil.execute("INSERT INTO customer VALUES(?,?,?,?)", c.getNIC(), c.getName(), c.getAddress(),
                        c.getTelephone())) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();
                    loadAllCustomers();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Error!").show();
                }
            }else {
                if (CrudUtil.execute("UPDATE customer SET name=?,address=?,telephone=? WHERE NIC=?",
                        c.getName(),c.getAddress(),c.getTelephone(),c.getNIC())){
                    new Alert(Alert.AlertType.CONFIRMATION,"Updated!").show();
                    tblCustomer.refresh();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Error!").show();
                }
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void txtClear() {
        txtID.clear();
        txtName.clear();
        txtAddress.clear();
        txtTelephone.clear();
    }

    public void NICSearchOnAction(ActionEvent actionEvent) {
        try {
            Search();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadDateAndTime() {
        /*set Date*/
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        /*set Time*/
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour() + ":" +
                    currentTime.getMinute() + ":" +
                    currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void Search() throws SQLException, ClassNotFoundException {
        /*ResultSet resultSet = CrudUtil.execute("SELECT * FROM customer WHERE NIC=?",txtID.getText());
        try {
            if (resultSet.next()){
                txtName.setText(resultSet.getString(2));
                txtAddress.setText(resultSet.getString(3));
                txtTelephone.setText(resultSet.getString(4));

            }else {
                new Alert(Alert.AlertType.WARNING,"Empty Result!").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map,btnSave);
//        TextField = error
//        boolean // validation ok

        //if the enter key pressed
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response =  ValidationUtil.validate(map,btnSave);;
            //if the response is a text field
            //that means there is a error
            if (response instanceof JFXTextField) {
                JFXTextField textField = (JFXTextField) response;
                textField.requestFocus();// if there is a error just focus it
            } else if (response instanceof Boolean) {
                System.out.println("Work");
                //btnSaveOnAction();
            }
        }
    }

    public void btnNextOnAction(ActionEvent actionEvent) throws IOException {
        context.getChildren().clear();
        Parent parent= null;
        try {
            parent = FXMLLoader.load(getClass().getResource("../view/VehicleForm.fxml"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        context.getChildren().add(parent);
    }
}
