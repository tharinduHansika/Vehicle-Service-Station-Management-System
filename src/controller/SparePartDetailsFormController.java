package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import models.*;
import util.CrudUtil;
import view.tm.BookingTM;
import view.tm.CustomerTM;
import view.tm.SparePartDetailsTM;
import view.tm.SparePartTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.Optional;

public class SparePartDetailsFormController {
    public JFXComboBox<String> cmbSparePartID;
    public JFXComboBox<String> cmbSupplierID;
    public JFXTextField txtSupplierName;
    public JFXTextField txtAddress;
    public JFXTextField txtTelephone;
    public JFXTextField txtEmail;
    public JFXTextField txtAccountNumber;
    public JFXTextField txtSparePartName;
    public JFXTextField txtBrand;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQty;
    public JFXTextField txtDescription;
    public TableView tblSupplierDetails;
    public TableColumn colSupplierID;
    public TableColumn colSparePartID;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colOperation;
    public JFXButton btnSave;
    public Label lblDate;
    public Label lblTime;
    public JFXTextField txtQtyOnHand;
    public TableView tblSparePartsDetails;

    public void initialize(){
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("sparePartsID"));
        colSparePartID.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colOperation.setCellValueFactory(new PropertyValueFactory<>("btn"));

        try {
            loadAllSparePartDetails();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbSparePartID.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    setSparePartDetails(newValue);
                });

        cmbSupplierID.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    setSupplierDetails(newValue);
                });

        tblSparePartsDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                setData((SparePartDetailsTM)newValue);
            }
        });

        setSparePartID();
        setSupplierID();
        LoadDateAndTime();
    }

    private void setData(SparePartDetailsTM newValue) {
        cmbSparePartID.setValue(newValue.getSparePartsID());
        cmbSupplierID.setValue(newValue.getSupplierID());
        txtQty.setText(String.valueOf(newValue.getQty()));
    }

    private void LoadDateAndTime() {
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

    private void setSupplierDetails(String newValue) {
        try {
            Supplier su = SparePartDetailsCrudController.getSupplierDetails(newValue);
            if (su != null) {
                txtSupplierName.setText(su.getName());
                txtAddress.setText(su.getAddress());
                txtAccountNumber.setText(String.valueOf(su.getAccountNumber()));
                txtTelephone.setText(su.getTelephone());
                txtEmail.setText(su.getEmail());
            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setSparePartDetails(String newValue) {
        try {
            SparePart s = SparePartDetailsCrudController.getSparePartDetails(newValue);
            if (s != null) {
                txtSparePartName.setText(s.getName());
                txtBrand.setText(s.getBrand());
                txtDescription.setText(s.getDescription());
                txtQtyOnHand.setText(String.valueOf(s.getQtyOnHand()));
                txtUnitPrice.setText(String.valueOf(s.getUnitPrice()));
            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setSupplierID() {
        try {
            ObservableList<String> suIDObList = FXCollections.observableArrayList(
                    SparePartDetailsCrudController.getSupplierID()
            );
            cmbSupplierID.setItems(suIDObList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setSparePartID() {
        try {
            ObservableList<String> spIDObList = FXCollections.observableArrayList(
                    SparePartDetailsCrudController.getSparePartID()
            );
            cmbSparePartID.setItems(spIDObList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllSparePartDetails() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM sparepartdetails");
        ObservableList<SparePartDetailsTM> obList = FXCollections.observableArrayList();

        while (result.next()) {
            Button btn = new Button("Delete");
            obList.add(
                    new SparePartDetailsTM(
                            result.getString("sparePartsID"),
                            result.getString("supplierID"),
                            result.getInt("qty"),
                            result.getDouble("unitPrice"),
                            result.getString("date"),
                            result.getString("time"),
                            btn
                    )
            );

            tblSparePartsDetails.setItems(obList);

            btn.setOnAction((e) -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure?", ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get().equals(ButtonType.YES)) {
                    try {
                        CrudUtil.execute("DELETE FROM sparepartdetails WHERE sparePartID=?", cmbSparePartID.getValue());
                        loadAllSparePartDetails();
                        btnSave.setText("Add+");
                        txtClear();
                        tblSupplierDetails.refresh();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                }
                tblSupplierDetails.refresh();
            });
        }

    }

    private void txtClear() {
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {

        SparePartDetails spd=new SparePartDetails(cmbSparePartID.getValue(), cmbSupplierID.getValue(), Integer.parseInt(txtQty.getText()), Double.parseDouble(txtUnitPrice.getText()) ,lblDate.getText(),lblTime.getText());

        try{
            if (btnSave.getText().equals("Save")) {
                if (CrudUtil.execute("INSERT INTO SparePartDetails VALUES(?,?,?,?,?,?)", spd.getSparePartsID(), spd.getSupplierID(), spd.getQty(),
                        spd.getUnitPrice(), spd.getDate(), spd.getTime())) {

                    SparePartDetailsCrudController.updateQTY(spd.getSparePartsID(),spd.getQty());

                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();
                    loadAllSparePartDetails();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Error!").show();
                }
            }else {
                if (CrudUtil.execute("UPDATE SparePartDetails SET SupplierID=?,Qty=?,unitPrice=? date=? time=? WHERE sparePartID=?",
                        spd.getSupplierID(), spd.getQty(),
                        spd.getUnitPrice(), spd.getDate(), spd.getTime(), spd.getSparePartsID())){
                    new Alert(Alert.AlertType.CONFIRMATION,"Updated!").show();
                    tblSparePartsDetails.refresh();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Error!").show();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
