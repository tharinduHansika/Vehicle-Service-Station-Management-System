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
import models.Service;
import util.CrudUtil;
import view.tm.EmployeeTM;
import view.tm.ServiceTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class ViewServiceFormController {
    public TableView tblViewService;
    public TableColumn colServiceID;
    public TableColumn colVehicleID;
    public TableColumn colEmployeeID;
    public TableColumn colDescription;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colName;
    public Label lblTime;
    public Label lblDate;
    public TableColumn colServiceCharge;

    public void initialize() {
        colServiceID.setCellValueFactory(new PropertyValueFactory<>("serviceID"));
        colVehicleID.setCellValueFactory(new PropertyValueFactory<>("vehicleID"));
        colEmployeeID.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colServiceCharge.setCellValueFactory(new PropertyValueFactory<>("serviceCharge"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        try {
            loadAllService();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        loadDateAndTime();
    }

    private void loadAllService() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Service");
        ObservableList<Service> obList = FXCollections.observableArrayList();

        while (result.next()) {
            obList.add(
                    new Service(
                            result.getString("serviceID"),
                            result.getString("vehicleID"),
                            result.getString("employeeID"),
                            result.getString("name"),
                            result.getString("description"),
                            result.getDouble("serviceCharge"),
                            result.getString("date"),
                            result.getString("time")
                    )
            );

            tblViewService.setItems(obList);
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
