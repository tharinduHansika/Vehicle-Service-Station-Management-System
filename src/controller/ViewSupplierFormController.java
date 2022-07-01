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
import models.Supplier;
import util.CrudUtil;
import view.tm.SupplierTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class ViewSupplierFormController {
    public TableView tblSupplier;
    public TableColumn colSupplierID;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colAccountNo;
    public TableColumn colTelephoneNo;
    public TableColumn colEmail;
    public Label lblDate;
    public Label lblTime;

    public void initialize(){
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colAccountNo.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        colTelephoneNo.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        try {
            loadAllSupplier();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        loadDateAndTime();
    }

    private void loadAllSupplier() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Supplier");
        ObservableList<Supplier> obList = FXCollections.observableArrayList();

        while (result.next()) {
            Button btn = new Button("Delete");
            obList.add(
                    new Supplier(
                            result.getString("supplierID"),
                            result.getString("name"),
                            result.getString("address"),
                            result.getInt("accountNumber"),
                            result.getString("telephone"),
                            result.getString("email")
                    )
            );

            tblSupplier.setItems(obList);
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
