package controller;

import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class DashboardFormController {
    public AnchorPane context;
    public Label lblTime;
    public Label lblDate;

    public void initialize(){
        loadDateAndTime();
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

    public void incomeReportOnAction(ActionEvent actionEvent) {
    }

    public void MonthlyIncomeReportOnAction(ActionEvent actionEvent) {
        try {
            JasperReport compileReport=(JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/Reports/Monthly Report.jasper"));
            Connection connection = null;
            try {
                connection = DBConnection.getInstance().getConnection();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            JasperPrint jasperPrint=JasperFillManager.fillReport(compileReport,null,connection);
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void TodayIncomeReportOnAction(ActionEvent actionEvent) {
        try {
            JasperReport compileReport =(JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/Reports/Today Report.jasper"));
            Connection connection = null;
            try {
                connection = DBConnection.getInstance().getConnection();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            JasperPrint jasperPrint=JasperFillManager.fillReport(compileReport,null,connection);
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void userOnAction(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("../view/userForm.fxml"));
        Stage stage=new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public void quickServiceOnAction(ActionEvent actionEvent) {
        context.getChildren().clear();
        Parent parent= null;
        try {
            parent = FXMLLoader.load(getClass().getResource("../view/CustomerForm.fxml"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        context.getChildren().add(parent);
    }
}
