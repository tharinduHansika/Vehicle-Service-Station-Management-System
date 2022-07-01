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
import models.Customer;
import models.Employee;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.CustomerTM;
import view.tm.EmployeeTM;
import view.tm.SparePartDetailsTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class EmployeeFormController {
    public TableView tblEmployee;
    public TableColumn colNIC;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContactNo;
    public TableColumn colPosition;
    public TableColumn colSalary;
    public TableColumn colOption;
    public JFXTextField txtName;
    public JFXTextField txtNIC;
    public JFXTextField txtAddress;
    public JFXTextField txtContactNo;
    public JFXTextField txtSalary;
    public JFXTextField txtPosition;
    public TableColumn colTelephone;
    public JFXButton btnSave;
    public Label lblTime;
    public Label lblDate;

    LinkedHashMap<JFXTextField, Pattern> map5 = new LinkedHashMap<>();

    public void initialize(){
        colNIC.setCellValueFactory(new PropertyValueFactory<>("NIC"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        try {
            loadAllEmployee();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        loadDateAndTime();

        Pattern NICPattern = Pattern.compile("^(E)[0-9]{3}$");
        Pattern NamePattern = Pattern.compile("^[A-z ]{3,30}$");
        Pattern AddressPattern = Pattern.compile("^[A-z0-9 ,/]{4,30}$");
        Pattern PositionPattern = Pattern.compile("^[A-z0-9 ,/]{4,20}$");
        Pattern SalaryPattern = Pattern.compile("^[1-9][0-9]*(.[0-9]{2})?$");
        Pattern ContactNoPattern = Pattern.compile("^(0)[0-9]{2}(-)[0-9]{7}$");

        map5.put(txtNIC,NICPattern);
        map5.put(txtName,NamePattern);
        map5.put(txtAddress,AddressPattern);
        map5.put(txtPosition,PositionPattern);
        map5.put(txtSalary,SalaryPattern);
        map5.put(txtContactNo,ContactNoPattern);

        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                setData((EmployeeTM)newValue);
            }
        });
    }

    private void setData(EmployeeTM newValue) {
        txtNIC.setText(newValue.getNIC());
        txtName.setText(newValue.getName());
        txtAddress.setText(newValue.getAddress());
        txtPosition.setText(newValue.getPosition());
        txtSalary.setText(String.valueOf(newValue.getSalary()));
        txtContactNo.setText(newValue.getTelephone());

    }

    private void loadAllEmployee() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Employee");
        ObservableList<EmployeeTM> obList = FXCollections.observableArrayList();

        while (result.next()) {
            Button btn = new Button("Delete");
            obList.add(
                    new EmployeeTM(
                            result.getString("NIC"),
                            result.getString("name"),
                            result.getString("address"),
                            result.getString("position"),
                            result.getDouble("salary"),
                            result.getString("telephone"),
                            btn
                    )
            );

            tblEmployee.setItems(obList);

            btn.setOnAction((e) -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get().equals(ButtonType.YES)) {
                    try {
                        CrudUtil.execute("DELETE FROM Employee WHERE NIC=?", txtNIC.getText());
                        loadAllEmployee();
                        btnSave.setText("Add+");
                        txtClear();
                        tblEmployee.refresh();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                }
                tblEmployee.refresh();
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

    public void txtNameOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        tblEmployee.refresh();
        Employee e=new Employee(txtNIC.getText(),txtName.getText(),txtAddress.getText(),txtPosition.getText(),Double.parseDouble(txtSalary.getText()),txtContactNo.getText());

        try{
            if (btnSave.getText().equals("Save")) {
                if (CrudUtil.execute("INSERT INTO Employee VALUES(?,?,?,?,?,?)", e.getNIC(), e.getName(), e.getAddress(), e.getPosition(), e.getSalary(),
                        e.getTelephone())) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();
                    loadAllEmployee();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Error!").show();
                }
            }else {
                if (CrudUtil.execute("UPDATE Employee SET name=?,address=?,position=? salary=? telephone=?  WHERE NIC=?",
                        e.getName(), e.getAddress(), e.getPosition(), e.getSalary(),
                        e.getTelephone(), e.getNIC())){
                    new Alert(Alert.AlertType.CONFIRMATION,"Updated!").show();
                    tblEmployee.refresh();
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
        ValidationUtil.validate(map5,btnSave);
//        TextField = error
//        boolean // validation ok

        //if the enter key pressed
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response =  ValidationUtil.validate(map5,btnSave);;
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
