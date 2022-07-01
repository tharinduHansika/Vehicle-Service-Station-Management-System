package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController {
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    public AnchorPane loginFormContext;

    public void loginOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM password WHERE userName=?",txtUserName.getText());
        try {
            if (resultSet.next()){
                String type=resultSet.getString(1);
                String userName=resultSet.getString(2);
                String password=resultSet.getString(3);

                String un=txtUserName.getText();
                String pw=txtPassword.getText();

                if(type.equals("Owner")){
                    if(un.equals(userName) | pw.equals(password)){
                        Stage stage = (Stage) loginFormContext.getScene().getWindow();
                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/OwnerForm.fxml"))));
                    }else {
                        new Alert(Alert.AlertType.WARNING,"Invalid Password!").show();
                    }
                }

                else if(type.equals("Cashier")){
                    if(un.equals(userName) | pw.equals(password)){
                        Stage stage = (Stage) loginFormContext.getScene().getWindow();
                        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/CashierForm.fxml"))));
                    }else {
                        new Alert(Alert.AlertType.WARNING,"Invalid Password!").show();
                    }
                }

            }else {
                new Alert(Alert.AlertType.WARNING,"Error!").show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
