package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CashierFormController {
    public AnchorPane context;
    public AnchorPane cashierFormContext;
    public JFXButton btnCustomer;
    public JFXButton btnVehicle;
    public JFXButton btnService;
    public JFXButton btnBooking;
    public JFXButton btnSparePartDetails;
    public JFXButton btnViewEmployee;
    public JFXButton btnViewSupplier;
    public JFXButton btnSparePart;
    public JFXButton btnViewService;

    public void initialize() throws IOException {
        setDefaultColour();
        btnCustomer.setStyle("-fx-background-color: #30336b");
        context.getChildren().clear();
        Parent parent= FXMLLoader.load(getClass().getResource("../view/CustomerForm.fxml"));
        context.getChildren().add(parent);
    }

    private void setDefaultColour() {
        btnCustomer.setStyle("-fx-background-color:  #130f40");
        btnVehicle.setStyle("-fx-background-color: #130f40");
        btnService.setStyle("-fx-background-color:  #130f40");
        btnBooking.setStyle("-fx-background-color:  #130f40");
        btnSparePart.setStyle("-fx-background-color:  #130f40");
        btnSparePartDetails.setStyle("-fx-background-color:  #130f40");
        btnViewEmployee.setStyle("-fx-background-color:  #130f40");
        btnViewSupplier.setStyle("-fx-background-color:  #130f40");
        btnViewService.setStyle("-fx-background-color:  #130f40");
    }

    public void customerOnAction(ActionEvent actionEvent) throws IOException {
        setDefaultColour();
        btnCustomer.setStyle("-fx-background-color: #30336b");
        context.getChildren().clear();
        Parent parent= FXMLLoader.load(getClass().getResource("../view/CustomerForm.fxml"));
        context.getChildren().add(parent);
    }

    public void vehicleOnAction(ActionEvent actionEvent) throws IOException {
        setDefaultColour();
        btnVehicle.setStyle("-fx-background-color: #30336b");
        context.getChildren().clear();
        Parent parent=FXMLLoader.load(getClass().getResource("../view/VehicleForm.fxml"));
        context.getChildren().add(parent);
    }

    public void serviceOnAction(ActionEvent actionEvent) throws IOException {
        setDefaultColour();
        btnService.setStyle("-fx-background-color: #30336b");
        context.getChildren().clear();
        Parent parent=FXMLLoader.load(getClass().getResource("../view/ServiceForm.fxml"));
        context.getChildren().add(parent);
    }

    public void sparePartOnAction(ActionEvent actionEvent) throws IOException {
        setDefaultColour();
        btnSparePart.setStyle("-fx-background-color: #30336b");
        context.getChildren().clear();
        Parent parent=FXMLLoader.load(getClass().getResource("../view/SparePartForm.fxml"));
        context.getChildren().add(parent);
    }

    public void SparePartsDetailsOnAction(ActionEvent actionEvent) throws IOException {
        setDefaultColour();
        btnSparePartDetails.setStyle("-fx-background-color: #30336b");
        context.getChildren().clear();
        Parent parent=FXMLLoader.load(getClass().getResource("../view/SparePartDetailsForm.fxml"));
        context.getChildren().add(parent);
    }

    public void ViewEmployeeOnAction(ActionEvent actionEvent) throws IOException {
        setDefaultColour();
        btnViewEmployee.setStyle("-fx-background-color: #30336b");
        context.getChildren().clear();
        Parent parent=FXMLLoader.load(getClass().getResource("../view/ViewEmployeeForm.fxml"));
        context.getChildren().add(parent);
    }

    public void ViewSupplierOnAction(ActionEvent actionEvent) throws IOException {
        setDefaultColour();
        btnViewSupplier.setStyle("-fx-background-color: #30336b");
        context.getChildren().clear();
        Parent parent=FXMLLoader.load(getClass().getResource("../view/ViewSupplierForm.fxml"));
        context.getChildren().add(parent);
    }

    public void bookingOnAction(ActionEvent actionEvent) throws IOException {
        setDefaultColour();
        btnBooking.setStyle("-fx-background-color: #30336b");
        context.getChildren().clear();
        Parent parent=FXMLLoader.load(getClass().getResource("../view/BookingForm.fxml"));
        context.getChildren().add(parent);
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) cashierFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
    }

    public void ViewServiceOnAction(ActionEvent actionEvent) throws IOException {
        setDefaultColour();
        btnViewService.setStyle("-fx-background-color: #30336b");
        context.getChildren().clear();
        Parent parent=FXMLLoader.load(getClass().getResource("../view/ViewServiceForm.fxml"));
        context.getChildren().add(parent);
    }
}
