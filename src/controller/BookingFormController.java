package controller;

import com.jfoenix.controls.*;
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
import models.Booking;
import models.Customer;
import models.Vehicle;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.BookingTM;
import view.tm.CustomerTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class BookingFormController {
    public JFXTextField txtDate;
    public JFXTextField txtTime;
    public JFXComboBox <String>cmbServiceType;
    public JFXComboBox <String>cmbNIC;
    public JFXComboBox <String>cmbVehicleNo;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtTelephoneNo;
    public JFXTextField txtType;
    public JFXTextField txtBrand;
    public JFXTextField txtMileage;
    public JFXTextField txtTransmission;
    public TableView tblBooking;
    public TableColumn colBookingID;
    public TableColumn colVehicleID;
    public TableColumn colCustomerNIC;
    public TableColumn colServiceType;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colOperation;
    public JFXTextField txtBooking;
    public Label lblDate;
    public Label lblTime;
    public JFXDatePicker datePicker;
    public TableColumn colCustomerID;
    public JFXButton btnSave;
    public JFXTextField timePicker;
    public JFXTextField txtCustomerID;
    public JFXTimePicker timePickerID;

    LinkedHashMap<JFXTextField, Pattern> map4 = new LinkedHashMap<>();


    public void initialize(){
        ObservableList<String> serviceType= FXCollections.observableArrayList();
        serviceType.add("Full Servicing & Lubricating");
        serviceType.add("Oil Filter Changing");
        serviceType.add("Fuel Filter & Fuel Pump Changing");
        serviceType.add("Transmission Oil & Filter Changing");
        serviceType.add("Fluid Top-Up & Changing");
        serviceType.add("Body Painting");
        serviceType.add("Quick Body Wash");
        serviceType.add("Mechanical Service");
        serviceType.add("3M Cut & Polish");
        serviceType.add("3M Interior Cleaning");
        serviceType.add("Engine Tune-Up");
        serviceType.add("AC Repairing");
        cmbServiceType.setItems(serviceType);

        colBookingID.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        colVehicleID.setCellValueFactory(new PropertyValueFactory<>("vehicleID"));
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colServiceType.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colOperation.setCellValueFactory(new PropertyValueFactory<>("btn"));

        loadDateAndTime();
        try {
            loadAllBooking();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        setVehicleID();

        Pattern vehicleNoPattern = Pattern.compile("^(B)[0-9]{3}$");
        map4.put(txtBooking,vehicleNoPattern);

        cmbVehicleNo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setVehicleDetails(newValue);});

        tblBooking.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                setData((BookingTM)newValue);
            }
        });
    }

    private void setVehicleDetails(String newValue) {
        try {
            Vehicle v = VehicleCrudController.getVehicle(newValue);
            if (v != null) {
                txtBrand.setText(v.getBrand());
                txtMileage.setText(String.valueOf(v.getMileage()));
                txtTransmission.setText(String.valueOf(v.getTransmission()));
            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setData(BookingTM newValue) {
        txtBooking.setText(newValue.getBookingID());
        cmbVehicleNo.setValue(newValue.getVehicleID());
        txtCustomerID.setText(newValue.getCustomerID());
        txtType.setText(newValue.getServiceType());
        datePicker.setValue(LocalDate.parse(newValue.getDate()));
        timePickerID.setValue(LocalTime.parse(newValue.getTime()));
    }

    private void setVehicleID() {
        try {
            ObservableList<String> vIDObList = FXCollections.observableArrayList(
                    BookingCrudController.getVehicleID()
            );
            cmbVehicleNo.setItems(vIDObList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllBooking() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Booking");
        ObservableList<BookingTM> obList = FXCollections.observableArrayList();

        while (result.next()) {
            Button btn = new Button("Delete");
            obList.add(
                    new BookingTM(
                            result.getString("bookingID"),
                            result.getString("vehicleID"),
                            result.getString("customerID"),
                            result.getString("serviceType"),
                            result.getString("date"),
                            result.getString("time"),
                            btn
                    )
            );

            tblBooking.setItems(obList);

            btn.setOnAction((e) -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get().equals(ButtonType.YES)) {
                    try {
                        CrudUtil.execute("DELETE FROM Booking WHERE BookingID=?", txtBooking.getText());
                        loadAllBooking();
                        btnSave.setText("Add+");
                        txtClear();
                        tblBooking.refresh();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                }
                tblBooking.refresh();
            });
        }

    }

    private void txtClear() {
        txtBooking.clear();
        cmbServiceType.setSelectionModel(null);
        cmbVehicleNo.setSelectionModel(null);
        txtType.clear();
        txtBrand.clear();
        txtMileage.clear();
        txtTransmission.clear();
        txtName.clear();
        txtAddress.clear();
        txtTelephoneNo.clear();
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

    public void datePickerOnAction(ActionEvent actionEvent) {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        tblBooking.refresh();
        Booking b=new Booking(txtBooking.getText(), cmbVehicleNo.getValue(),txtCustomerID.getText(),cmbServiceType.getValue(),datePicker.getValue().toString(),timePickerID.getValue().toString());

        try{
            if (btnSave.getText().equals("Save")) {
                if (CrudUtil.execute("INSERT INTO booking VALUES(?,?,?,?,?,?)", b.getBookingID(), b.getVehicleID(), b.getCustomerID(),b.getServiceType(),b.getDate(),
                        b.getTime())) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();
                    loadAllBooking();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Error!").show();
                }
            }else {
                if (CrudUtil.execute("UPDATE Booking SET vehicleID=?, customerID=?, serviceType=?, date=?, time=? WHERE bookingID=?",
                        b.getVehicleID(), b.getCustomerID(),b.getServiceType(),b.getDate(),
                        b.getTime(),b.getBookingID())){
                    new Alert(Alert.AlertType.CONFIRMATION,"Updated!").show();
                    tblBooking.refresh();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Error!").show();
                }
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void NICOnAction(ActionEvent actionEvent) {
        try {
            NICSearch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void NICSearch() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM customer WHERE NIC=?",cmbNIC.getValue());
        try {
            if (resultSet.next()){
                txtName.setText(resultSet.getString(2));
                txtAddress.setText(resultSet.getString(3));
                txtTelephoneNo.setText(resultSet.getString(4));

            }else {
                new Alert(Alert.AlertType.WARNING,"Empty Result!").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void vehicleNoOnAction(ActionEvent actionEvent) {
        try {
            vehicleNoSearch();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void vehicleNoSearch() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Vehicle WHERE vehicleNo=?",cmbVehicleNo.getValue());
        try {
            if (resultSet.next()){
                txtCustomerID.setText(resultSet.getString(2));
                txtType.setText(resultSet.getString(3));
                txtBrand.setText(resultSet.getString(4));
                txtMileage.setText(resultSet.getString(5));
                txtTransmission.setText(resultSet.getString(6));

            }else {
                new Alert(Alert.AlertType.WARNING,"Empty Result!").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map4,btnSave);
//        TextField = error
//        boolean // validation ok

        //if the enter key pressed
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response =  ValidationUtil.validate(map4,btnSave);;
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
