package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import models.Customer;
import models.SparePart;
import util.CrudUtil;
import util.ValidationUtil;
import view.tm.CustomerTM;
import view.tm.SparePartTM;
import view.tm.SupplierTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.regex.Pattern;

public class SparePartFormController {
    public JFXTextField txtSparePartID;
    public JFXTextField txtName;
    public JFXTextField txtBrand;
    public JFXTextField txtDescription;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtUnitPrice;
    public JFXButton btnSave;
    public TableView tblSparePart;
    public TableColumn colSparePartID;
    public TableColumn colName;
    public TableColumn colBrand;
    public TableColumn colDescription;
    public TableColumn colQtyOnHand;
    public TableColumn colUnitPrice;
    public TableColumn colOption;
    public Label lblDate;
    public Label lblTime;

    LinkedHashMap<JFXTextField, Pattern> map3 = new LinkedHashMap<>();

    public void initialize(){
        colSparePartID.setCellValueFactory(new PropertyValueFactory("sparePartID"));
        colName.setCellValueFactory(new PropertyValueFactory("name"));
        colBrand.setCellValueFactory(new PropertyValueFactory("brand"));
        colDescription.setCellValueFactory(new PropertyValueFactory("description"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        try {
            loadAllSparePart();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        loadDateAndTime();

        Pattern idPattern = Pattern.compile("^(SP)[0-9]{3}$");
        Pattern namePattern = Pattern.compile("^[A-z0-9 ./]{3,30}$");
        Pattern brandPattern = Pattern.compile("^[A-z0-9 ,/]{2,10}$");
        Pattern descriptionPattern = Pattern.compile("^[A-z0-9 .()/]{3,30}$");
        Pattern qtyOnHandPattern = Pattern.compile("^[0-9]{1,3}$");
        Pattern unitPricePattern = Pattern.compile("^[1-9][0-9]*(.[0-9]{2})?$");

        map3.put(txtSparePartID,idPattern);
        map3.put(txtName,namePattern);
        map3.put(txtBrand,brandPattern);
        map3.put(txtDescription,descriptionPattern);
        map3.put(txtQtyOnHand,qtyOnHandPattern);
        map3.put(txtUnitPrice,unitPricePattern);

        tblSparePart.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                setData((SparePartTM)newValue);
            }
        });

    }

    private void setData(SparePartTM newValue) {
        txtSparePartID.setText(newValue.getSparePartID());
        txtName.setText(newValue.getName());
        txtBrand.setText(newValue.getBrand());
        txtDescription.setText(newValue.getDescription());
        txtQtyOnHand.setText(String.valueOf(newValue.getQtyOnHand()));
        txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
    }

    private void loadAllSparePart() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM SparePart");
        ObservableList<SparePartTM> obList = FXCollections.observableArrayList();

        while (result.next()) {
            Button btn = new Button("Delete");
            obList.add(
                    new SparePartTM(
                            result.getString("sparePartID"),
                            result.getString("name"),
                            result.getString("brand"),
                            result.getString("description"),
                            result.getInt("qtyOnHand"),
                            result.getDouble("UnitPrice"),
                            btn
                    )
            );

            tblSparePart.setItems(obList);

            btn.setOnAction((e) -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get().equals(ButtonType.YES)) {
                    try {
                        CrudUtil.execute("DELETE FROM sparePart WHERE sparePartID=?", txtSparePartID.getText());
                        loadAllSparePart();
                        btnSave.setText("Add+");
                        txtClear();
                        tblSparePart.refresh();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                }
                tblSparePart.refresh();
            });
        }
    }

    private void txtClear() {
        txtSparePartID.clear();
        txtName.clear();
        txtBrand.clear();
        txtDescription.clear();
        txtQtyOnHand.clear();
        txtUnitPrice.clear();
    }

    public void sparePartIDOnAction(ActionEvent actionEvent) {
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

    public void btnSaveOnAction(ActionEvent actionEvent) {
        tblSparePart.refresh();
        SparePart s=new SparePart(txtSparePartID.getText(),txtName.getText(),txtBrand.getText(),txtDescription.getText(),
                Integer.parseInt(txtQtyOnHand.getText()), Double.parseDouble(txtUnitPrice.getText()));

        try{
            if (btnSave.getText().equals("Save")) {
                if (CrudUtil.execute("INSERT INTO sparePart VALUES(?,?,?,?,?,?)", s.getSparePartID(), s.getName(), s.getBrand(),
                        s.getDescription(), s.getQtyOnHand(), s.getUnitPrice())) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();
                    loadAllSparePart();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Error!").show();
                }
            }else {
                if (CrudUtil.execute("UPDATE sparePart SET name=?,brand=?,description=?, qtyOnHand=?, unitPrice=?  WHERE sparePartID=?",
                        s.getName(), s.getBrand(),
                        s.getDescription(), s.getQtyOnHand(), s.getUnitPrice(),s.getSparePartID())){
                    new Alert(Alert.AlertType.CONFIRMATION,"Updated!").show();
                    tblSparePart.refresh();
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
        ValidationUtil.validate(map3,btnSave);
//        TextField = error
//        boolean // validation ok

        //if the enter key pressed
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response =  ValidationUtil.validate(map3,btnSave);;
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
