package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import models.Payment;
import util.CrudUtil;
import view.tm.EmployeeTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class ViewPaymentFormController {

    public Label lblTime;
    public Label lblDate;
    public TableView tblPayment;
    public TableColumn colPaymentID;
    public TableColumn colVehicleID;
    public TableColumn colCustomerID;
    public TableColumn colAmount;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colServiceID;

    public void initialize() {
        colPaymentID.setCellValueFactory(new PropertyValueFactory<>("paymentID"));
        colServiceID.setCellValueFactory(new PropertyValueFactory<>("serviceID"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        try {
            loadAllPayment();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadDateAndTime();
    }

    private void loadAllPayment() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Payment");
        ObservableList<Payment> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new Payment(
                            result.getString("paymentID"),
                            result.getString("serviceID"),
                            result.getDouble("amount"),
                            result.getString("date"),
                            result.getString("time")
                    )
            );

            tblPayment.setItems(obList);
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
}
