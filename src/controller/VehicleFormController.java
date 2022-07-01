package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import models.Vehicle;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.CustomerTM;
import view.tm.VehicleTM;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class VehicleFormController {
    public JFXComboBox<String> cmbType;
    public JFXComboBox<String> cmbFuelType;
    public JFXComboBox<String> cmbTransmission;
    public JFXComboBox txtNIC;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtTelephone;
    public JFXTextField txtVehicleNo;
    public JFXTextField txtMileage;
    public JFXTextField txtBrand;
    public TableView tblVehicle;
    public TableColumn colVehicleNo;
    public TableColumn colCustomerID;
    public TableColumn colType;
    public TableColumn colBrand;
    public TableColumn colMileage;
    public TableColumn colFuelType;
    public TableColumn colTransmission;
    public TableColumn colOption;
    public JFXComboBox <String>cmbNIC;
    public JFXButton btnSave;
    public Label lblDate;
    public Label lblTime;
    public JFXButton btnNext;
    public AnchorPane context;

    LinkedHashMap<JFXTextField, Pattern> map1 = new LinkedHashMap<>();

    public void initialize(){
        ObservableList<String> typeList= FXCollections.observableArrayList();
        typeList.add("Car");
        typeList.add("Van");
        typeList.add("Bus");
        typeList.add("Cab");
        typeList.add("Lorry");
        typeList.add("Truck");
        typeList.add("Other");
        cmbType.setItems(typeList);

        ObservableList<String> fuelTypeList=FXCollections.observableArrayList();
        fuelTypeList.add("Petrol");
        fuelTypeList.add("Diesel");
        fuelTypeList.add("Hybrid");
        fuelTypeList.add("Electric");
        fuelTypeList.add("Other");
        cmbFuelType.setItems(fuelTypeList);

        ObservableList<String> transmissionList=FXCollections.observableArrayList();
        transmissionList.add("Manual");
        transmissionList.add("Automatic");
        transmissionList.add("Tiptronic");
        transmissionList.add("Other");
        cmbTransmission.setItems(transmissionList);

        colVehicleNo.setCellValueFactory(new PropertyValueFactory<>("vehicleNo"));
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colMileage.setCellValueFactory(new PropertyValueFactory<>("mileage"));
        colFuelType.setCellValueFactory(new PropertyValueFactory<>("fuelType"));
        colTransmission.setCellValueFactory(new PropertyValueFactory<>("transmission"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        try {
            loadAllVehicle();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        setCustomerID();
        loadDateAndTime();

        cmbNIC.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setVehicleDetails(newValue);});

        tblVehicle.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                setData((VehicleTM)newValue);
            }
        });

        Pattern vehicleNoPattern = Pattern.compile("^[A-Z]{2,3}(-)[0-9]{4}$");
        Pattern brandPattern = Pattern.compile("^[A-z ]{2,10}$");
        Pattern MileagePattern = Pattern.compile("^[0-9]{1,6}$");

        map1.put(txtVehicleNo,vehicleNoPattern);
        map1.put(txtBrand,brandPattern);
        map1.put(txtMileage,MileagePattern);
    }

    private void setVehicleDetails(String newValue) {
        try {
            Customer c = CustomerCrudController.getCustomer(newValue);
            if (c != null) {
                txtName.setText(c.getName());
                txtAddress.setText(c.getAddress());
                txtTelephone.setText(String.valueOf(c.getTelephone()));
            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void setCustomerID() {
        try {
            ObservableList<String> cIdObList = FXCollections.observableArrayList(
                    CustomerCrudController.getCustomerID()
            );
            cmbNIC.setItems(cIdObList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setData(VehicleTM tm) {
        txtVehicleNo.setText(tm.getVehicleNo());
        cmbNIC.setValue(tm.getCustomerID());
        cmbType.setValue(tm.getType());
        txtBrand.setText(tm.getBrand());
        txtMileage.setText(String.valueOf(tm.getMileage()));
        cmbFuelType.setValue(tm.getFuelType());
        cmbTransmission.setValue(tm.getTransmission());
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

    private void loadAllVehicle() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM vehicle");
        ObservableList<VehicleTM> obList = FXCollections.observableArrayList();

        while (result.next()) {
            Button btn = new Button("Delete");
            obList.add(
                    new VehicleTM(
                            result.getString("vehicleNo"),
                            result.getString("customerID"),
                            result.getString("type"),
                            result.getString("brand"),
                            result.getInt("mileage"),
                            result.getString("fuelType"),
                            result.getString("transmission"),
                            btn
                    )
            );

            tblVehicle.setItems(obList);

            btn.setOnAction((e) -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get().equals(ButtonType.YES)) {
                    try {
                        CrudUtil.execute("DELETE FROM Vehicle WHERE vehicleNo=?", txtVehicleNo.getText());
                        loadAllVehicle();
                        btnSave.setText("Add+");
                        txtClear();
                        tblVehicle.refresh();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                }
                tblVehicle.refresh();
            });
        }
    }

    private void txtClear() {
        txtVehicleNo.clear();
        cmbNIC.setSelectionModel(null);
        txtName.clear();
        txtAddress.clear();
        txtTelephone.clear();
        cmbType.setSelectionModel(null);
        txtBrand.clear();
        txtMileage.clear();
        cmbFuelType.setSelectionModel(null);
        cmbTransmission.setSelectionModel(null);
    }

    public void txtNICOnAction(ActionEvent actionEvent) {
        /*try {
            Search();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    private void Search() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM vehicle WHERE vehicleNo=?",txtVehicleNo.getText());
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
        }
    }


    public void btnSaveOnAction(ActionEvent actionEvent) {
        tblVehicle.refresh();
        Vehicle v=new Vehicle(txtVehicleNo.getText(),cmbNIC.getValue(),cmbType.getValue(),txtBrand.getText(),Integer.parseInt(txtMileage.getText()),cmbFuelType.getValue(),cmbTransmission.getValue());

        try{
            if (btnSave.getText().equals("Save")) {
                if (CrudUtil.execute("INSERT INTO vehicle VALUES(?,?,?,?,?,?,?)", v.getVehicleNo(), v.getCustomerID(), v.getType(),v.getBrand(),v.getMileage(),v.getFuelType(),
                        v.getTransmission())) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();
                    loadAllVehicle();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Error!").show();
                }
            }else {
                if (CrudUtil.execute("UPDATE customer SET customerID=?, type=?, brand=?, mileage=?, fuelType=?, transmission=? WHERE vehicleNo=?",
                        v.getCustomerID(), v.getType(),v.getBrand(),v.getMileage(),v.getFuelType(),
                        v.getTransmission(),v.getVehicleNo())){
                    new Alert(Alert.AlertType.CONFIRMATION,"Updated!").show();
                    tblVehicle.refresh();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Error!").show();
                }
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map1,btnSave);
//        TextField = error
//        boolean // validation ok

        //if the enter key pressed
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response =  ValidationUtil.validate(map1,btnSave);;
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
            parent = FXMLLoader.load(getClass().getResource("../view/ServiceForm.fxml"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        context.getChildren().add(parent);
    }
}

