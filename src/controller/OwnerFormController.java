package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class OwnerFormController {
    public AnchorPane context;
    public AnchorPane ownerFormContext;
    public JFXButton btnDashboard;
    public JFXButton btnEmployee;
    public JFXButton btnSupplier;
    public JFXButton btnSpareParts;
    public JFXButton btnViewCustomer;
    public JFXButton btnViewBooking;
    public JFXButton btnViewPayment;
    public JFXButton btnViewServices;

    public void initialize() throws IOException {
        setDefaultColour();
        btnDashboard.setStyle("-fx-background-color: #30336b");
        context.getChildren().clear();
        Parent parent=FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"));
        context.getChildren().add(parent);
    }

    private void setDefaultColour() {
        btnDashboard.setStyle("-fx-background-color:  #130f40");
        btnEmployee.setStyle("-fx-background-color:  #130f40");
        btnSupplier.setStyle("-fx-background-color:  #130f40");
        btnSpareParts.setStyle("-fx-background-color:  #130f40");
        btnViewCustomer.setStyle("-fx-background-color:  #130f40");
        btnViewBooking.setStyle("-fx-background-color:  #130f40");
        btnViewPayment.setStyle("-fx-background-color:  #130f40");
        btnViewServices.setStyle("-fx-background-color:  #130f40");
    }

    public void employeeOnAction(ActionEvent actionEvent) throws IOException {
        setDefaultColour();
        btnEmployee.setStyle("-fx-background-color: #30336b");
        context.getChildren().clear();
        Parent parent= FXMLLoader.load(getClass().getResource("../view/EmployeeForm.fxml"));
        context.getChildren().add(parent);
    }

    public void supplierOnAction(ActionEvent actionEvent) throws IOException {
        setDefaultColour();
        btnSupplier.setStyle("-fx-background-color: #30336b");
        context.getChildren().clear();
        Parent parent= FXMLLoader.load(getClass().getResource("../view/SupplierForm.fxml"));
        context.getChildren().add(parent);
    }

    public void sparePartOnAction(ActionEvent actionEvent) throws IOException {
        setDefaultColour();
        btnSpareParts.setStyle("-fx-background-color: #30336b");
        context.getChildren().clear();
        Parent parent =FXMLLoader.load(getClass().getResource("../view/SparePartForm.fxml"));
        context.getChildren().add(parent);
    }

    public void viewCustomerOnAction(ActionEvent actionEvent) throws IOException {
        setDefaultColour();
        btnViewCustomer.setStyle("-fx-background-color: #30336b");
        context.getChildren().clear();
        Parent parent=FXMLLoader.load(getClass().getResource("../view/ViewCustomerForm.fxml"));
        context.getChildren().add(parent);
    }

    public void viewBookingOnAction(ActionEvent actionEvent) throws IOException {
        setDefaultColour();
        btnViewBooking.setStyle("-fx-background-color: #30336b");
        context.getChildren().clear();
        Parent parent=FXMLLoader.load(getClass().getResource("../view/ViewBookingForm.fxml"));
        context.getChildren().add(parent);
    }

    public void viewPaymentOnAction(ActionEvent actionEvent) throws IOException {
        setDefaultColour();
        btnViewPayment.setStyle("-fx-background-color: #30336b");
        context.getChildren().clear();
        Parent parent=FXMLLoader.load(getClass().getResource("../view/ViewPaymentForm.fxml"));
        context.getChildren().add(parent);
    }

    public void viewServiceOnAction(ActionEvent actionEvent) throws IOException {
        setDefaultColour();
        btnViewServices.setStyle("-fx-background-color: #30336b");
        context.getChildren().clear();
        Parent parent=FXMLLoader.load(getClass().getResource("../view/ViewServiceForm.fxml"));
        context.getChildren().add(parent);
    }

    public void backOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ownerFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"))));
    }

    public void dashboardOnAction(ActionEvent actionEvent) throws IOException {
        setDefaultColour();
        btnDashboard.setStyle("-fx-background-color: #30336b");
        context.getChildren().clear();
        Parent parent=FXMLLoader.load(getClass().getResource("../view/DashboardForm.fxml"));
        context.getChildren().add(parent);
    }
}
