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
import models.Booking;
import util.CrudUtil;
import view.tm.BookingTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class ViewBookingFormController {
    public TableView tblBooking;
    public TableColumn colBookingID;
    public TableColumn colVehicleNo;
    public TableColumn colCustomerID;
    public TableColumn colServiceType;
    public TableColumn colDate;
    public TableColumn colTime;
    public Label lblTime;
    public Label lblDate;

    public void initialize(){
        colBookingID.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        colVehicleNo.setCellValueFactory(new PropertyValueFactory<>("vehicleID"));
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        colServiceType.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        try {
            loadAllBooking();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        loadDateAndTime();
    }

    private void loadAllBooking() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Booking");
        ObservableList<Booking> obList = FXCollections.observableArrayList();

        while (result.next()) {
            Button btn = new Button("Delete");
            obList.add(
                    new Booking(
                            result.getString("bookingID"),
                            result.getString("vehicleID"),
                            result.getString("customerID"),
                            result.getString("serviceType"),
                            result.getString("date"),
                            result.getString("time")
                    )
            );

            tblBooking.setItems(obList);
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
