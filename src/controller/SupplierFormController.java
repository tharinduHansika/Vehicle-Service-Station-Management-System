package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import models.Employee;
import models.Supplier;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.CustomerTM;
import view.tm.EmployeeTM;
import view.tm.SupplierTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class SupplierFormController {

    public JFXTextField txtSupplierID;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtEmail;
    public JFXTextField txtTelephone;
    public JFXTextField txtAccountNo;
    public TableView tblSupplier;
    public TableColumn colSupplierID;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colTelephone;
    public TableColumn colEmail;
    public TableColumn colAccountNo;
    public TableColumn colOption;
    public JFXButton btnSave;
    public Label lblTime;
    public Label lblDate;

    LinkedHashMap<JFXTextField, Pattern> map6 = new LinkedHashMap<>();

    public void initialize(){
        colSupplierID.setCellValueFactory(new PropertyValueFactory("supplierID"));
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory("address"));
        colAccountNo.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        colTelephone.setCellValueFactory(new PropertyValueFactory("telephone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        try {
            loadAllSupplier();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        loadDateAndTime();

        Pattern NICPattern = Pattern.compile("^(SUP)[0-9]{3}$");
        Pattern NamePattern = Pattern.compile("^[A-z ]{3,30}$");
        Pattern AddressPattern = Pattern.compile("^[A-z0-9 ,/]{4,30}$");
        Pattern AccountNoPattern = Pattern.compile("^[A-z0-9 ,/]{4,20}$");
        Pattern TelephonePattern = Pattern.compile("^(0)[0-9]{2}(-)[0-9]{7}$");
        Pattern EmailPattern = Pattern.compile("^[A-z0-9 .]{4,30}(@)[A-z ]{4,10}(.com)$");

        map6.put(txtSupplierID,NICPattern);
        map6.put(txtName,NamePattern);
        map6.put(txtAddress,AddressPattern);
        map6.put(txtAccountNo,AccountNoPattern);
        map6.put(txtTelephone,TelephonePattern);
        map6.put(txtEmail,EmailPattern);

        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                setData((SupplierTM)newValue);
            }
        });
    }

    private void setData(SupplierTM newValue) {
        txtSupplierID.setText(newValue.getSupplierID());
        txtName.setText(newValue.getName());
        txtAddress.setText(newValue.getAddress());
        txtAccountNo.setText(String.valueOf(newValue.getAccountNumber()));
        txtTelephone.setText(newValue.getTelephone());
        txtEmail.setText(newValue.getEmail());
    }

    private void loadAllSupplier() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Supplier");
        ObservableList<SupplierTM> obList = FXCollections.observableArrayList();

        while (result.next()) {
            Button btn = new Button("Delete");
            obList.add(
                    new SupplierTM(
                            result.getString("supplierID"),
                            result.getString("name"),
                            result.getString("address"),
                            result.getInt("accountNumber"),
                            result.getString("telephone"),
                            result.getString("email"),
                            btn
                    )
            );

            tblSupplier.setItems(obList);

            btn.setOnAction((e) -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get().equals(ButtonType.YES)) {
                    try {
                        CrudUtil.execute("DELETE FROM supplier WHERE supplierID=?", txtSupplierID.getText());
                        loadAllSupplier();
                        btnSave.setText("Add+");
                        txtClear();
                        tblSupplier.refresh();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                }
                tblSupplier.refresh();
            });
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

    private void txtClear() {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        tblSupplier.refresh();
        Supplier s=new Supplier(txtSupplierID.getText(),txtName.getText(),txtAddress.getText(),Integer.parseInt(txtAccountNo.getText()),txtTelephone.getText(),txtEmail.getText());

        try{
            if (btnSave.getText().equals("Save")) {
                if (CrudUtil.execute("INSERT INTO Supplier VALUES(?,?,?,?,?,?)", s.getSupplierID(), s.getName(), s.getAddress(), s.getAccountNumber(), s.getTelephone(),
                        s.getEmail())) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();
                    loadAllSupplier();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Error!").show();
                }
            }else {
                if (CrudUtil.execute("UPDATE Supplier SET name=?,address=?,accountNumber=? telephone=? email=?  WHERE SupplierID=?",
                        s.getName(), s.getAddress(), s.getAccountNumber(), s.getTelephone(),
                        s.getEmail(), s.getSupplierID())){
                    new Alert(Alert.AlertType.CONFIRMATION,"Updated!").show();
                    tblSupplier.refresh();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Error!").show();
                }
            }
        } catch (ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
            new Alert(Alert.AlertType.ERROR, exception.getMessage()).show();
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map6,btnSave);
//        TextField = error
//        boolean // validation ok

        //if the enter key pressed
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response =  ValidationUtil.validate(map6,btnSave);;
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
}
