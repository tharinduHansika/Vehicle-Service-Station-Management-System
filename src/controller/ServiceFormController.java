package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import util.ValidationUtil;
import view.tm.ServiceTM;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ServiceFormController {
    public JFXComboBox<String> cmbVehicleNo;
    public JFXTextField txtType;
    public JFXTextField txtMileage;
    public JFXTextField txtTransmission;
    public JFXTextField txtVehicleBrand;
    public JFXTextField txtFuelType;
    public TableView tblServiceDetails;
    public TableColumn colSparePartID;
    public TableColumn colName;
    public TableColumn colBrand;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQty;
    public TableColumn colTotal;
    public TableColumn colOperation;
    public JFXComboBox<String> cmbEmployeeID;
    public JFXTextField txtEmployeeName;
    public JFXTextField txtPosition;
    public JFXComboBox<String> cmbSparePartID;
    public JFXTextField txtSparePartName;
    public JFXTextField txtSparePartDescription;
    public JFXTextField txtSparePartBrand;
    public JFXTextField txtQtyOnHand;
    public JFXButton btnAddToCart;
    public JFXButton btnPlaceOrder;
    public JFXTextField txtServiceCharge;
    public Label txtTotal;
    public JFXTextField txtServiceDescription;
    public JFXComboBox<String> cmbServiceName;
    public JFXTextField txtQty;
    public JFXTextField txtUnitPrice;
    public JFXButton btnRemoveItem;
    public JFXButton openNewVehicleOnAction;
    public Label lblDate;
    public Label lblTime;
    public JFXButton btnMakePayment;
    public AnchorPane context;

    private String ServiceID;

    LinkedHashMap<JFXTextField, Pattern> map2 = new LinkedHashMap<>();

    public void initialize(){
        ObservableList serviceNameObList= FXCollections.observableArrayList();
        serviceNameObList.add("Full Servicing & Lubricating");
        serviceNameObList.add("Oil Filter Changing");
        serviceNameObList.add("Fuel Filter & Fuel Pump Changing");
        serviceNameObList.add("Transmission Oil & Filter Changing");
        serviceNameObList.add("Fluid Top-Up & Changing");
        serviceNameObList.add("Body Painting");
        serviceNameObList.add("Quick Body Wash");
        serviceNameObList.add("Mechanical Service");
        serviceNameObList.add("3M Cut & Polish");
        serviceNameObList.add("3M Interior Cleaning");
        serviceNameObList.add("Engine Tune-Up");
        serviceNameObList.add("AC Repairing");
        cmbServiceName.setItems(serviceNameObList);


        colSparePartID.setCellValueFactory(new PropertyValueFactory<>("sparePartID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colOperation.setCellValueFactory(new PropertyValueFactory<>("btn"));


        cmbVehicleNo.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    setVehicleDetails(newValue);
                });

        cmbEmployeeID.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    setEmployeeDetails(newValue);
                });

        cmbSparePartID.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    setSparePartDetails(newValue);
                });

        setVehicleNo();
        setSparePartID();
        setEmployeeID();
        setServiceID();

        loadDateAndTime();

        Pattern descriptionPattern = Pattern.compile("^[A-z ]{1,30}$");
        Pattern qtyPattern = Pattern.compile("^[0-9]{1,3}$");

        map2.put(txtServiceDescription,descriptionPattern);
        map2.put(txtQty,qtyPattern);
    }

    private void setServiceID() {
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

    private void setEmployeeID() {
        try {
            ObservableList<String> eIDObList = FXCollections.observableArrayList(
                    ServiceDetailsCrudController.getEmployeeID()
            );
            cmbEmployeeID.setItems(eIDObList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setVehicleNo() {
        try {
            ObservableList<String> vIDObList = FXCollections.observableArrayList(
                    ServiceDetailsCrudController.getVehicleNo()
            );
            cmbVehicleNo.setItems(vIDObList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setSparePartID() {
        try {
            ObservableList<String> cIdObList = FXCollections.observableArrayList(
                    ServiceDetailsCrudController.getSparePartID()
            );
            cmbSparePartID.setItems(cIdObList);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setSparePartDetails(String newValue) {
        try {
            SparePart s = ServiceDetailsCrudController.getSparePart(newValue);
            if (s != null) {
                txtSparePartName.setText(s.getName());
                txtSparePartBrand.setText(s.getBrand());
                txtSparePartDescription.setText(s.getDescription());
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

    private void setEmployeeDetails(String newValue) {
        Employee e = null;
        try {
            e = ServiceDetailsCrudController.getEmployeeIDDetails(newValue);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }
        if (e != null) {
                txtEmployeeName.setText(e.getName());
                txtPosition.setText(e.getPosition());
            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }
    }

    private void setVehicleDetails(String newValue) {
        try {
            Vehicle v = ServiceDetailsCrudController.getVehicleNoDetails(newValue);
            if (v != null) {
                txtType.setText(v.getType());
                txtVehicleBrand.setText(v.getBrand());
                txtMileage.setText(String.valueOf(v.getMileage()));
                txtFuelType.setText(v.getFuelType());
                txtTransmission.setText(v.getTransmission());
            } else {
                new Alert(Alert.AlertType.WARNING, "Empty Result").show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    ObservableList<ServiceTM> tmList = FXCollections.observableArrayList();

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double totalCost = unitPrice * qty;

        ServiceTM isExists=isExists(cmbSparePartID.getValue());

        if(isExists!=null){
            for (ServiceTM temp:tmList
                 ) {
                if(temp.equals(isExists)){
                    temp.setQty(temp.getQty()+qty);
                    temp.setTotal(temp.getTotal()+totalCost);
                }
            }
        }else{
            Button btn = new Button("Delete");

            ServiceTM tm = new ServiceTM(
                    cmbSparePartID.getValue(),
                    txtSparePartName.getText(),
                    txtVehicleBrand.getText(),
                    txtSparePartDescription.getText(),
                    unitPrice,
                    qty,
                    totalCost,
                    btn
            );

            btn.setOnAction(e->{
                tmList.remove(tm);
                calculateTotal();
                /*or (ServiceTM tempTM:tmList
                     ) {
                    if(tempTM.getSparePartID().equals(tm.getSparePartID())){
                        tmList.remove(tempTM);
                        calculateTotal();
                    }*/

            });

            tmList.add(tm);
            tblServiceDetails.setItems(tmList);
        }
        tblServiceDetails.refresh();
        calculateTotal();
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String serviceId=nextServiceID();
        String paymentId=nextPaymentID();

        Service service=new Service(serviceId,cmbVehicleNo.getValue(),cmbEmployeeID.getValue(),cmbServiceName.getValue(),
                txtServiceDescription.getText(),Double.parseDouble(txtServiceCharge.getText()),lblDate.getText(),lblTime.getText());

        Payment payment=new Payment(paymentId,serviceId,Double.parseDouble(txtTotal.getText()),lblDate.getText(),lblTime.getText());

        ArrayList<ServiceDetails> details=new ArrayList<>();
        for (ServiceTM tm:tmList
             ) {
            details.add(new ServiceDetails(serviceId,tm.getSparePartID(),tm.getQty(),tm.getUnitPrice()));
        }

        Connection connection= null;
        HashMap paymentMap = new HashMap();
        paymentMap.put("FinalTotal",Double.parseDouble(txtTotal.getText()));
        paymentMap.put("VehicleNumber",cmbVehicleNo.getValue());
        paymentMap.put("ServiceID","S004");
        paymentMap.put("EmployeeID",cmbEmployeeID.getValue());
        paymentMap.put("ServiceType",cmbServiceName.getValue());
        paymentMap.put("Mileage",Integer.parseInt(txtMileage.getText()));
        paymentMap.put("Date",lblDate.getText());
        paymentMap.put("Time",lblTime.getText());

        try{
            connection= DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isServiceSaved=new ServiceCrudController().saveService(service);
            if(isServiceSaved){
                System.out.println("saved at service");
                    boolean isPaymentSaved=new ServiceCrudController().savePayment(payment);
                    if(isPaymentSaved){
                        System.out.println("saved at payment");
                        boolean isDetailsSaved=new ServiceCrudController().saveServiceDetail(details);
                        if(isDetailsSaved){
                            System.out.println("saved at serviceDetails");
                            connection.commit();
                            new Alert(Alert.AlertType.CONFIRMATION,"Saved!").show();
                            ObservableList<ServiceTM> serviceTMS = tblServiceDetails.getItems();
                            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("../view/Reports/payment_bill.jasper"));
                            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,paymentMap,new JRBeanCollectionDataSource(serviceTMS));
                            JasperViewer.viewReport(jasperPrint,false);
                        }else{
                            connection.rollback();
                            new Alert(Alert.AlertType.ERROR,"Error").show();
                        }
                    }else{
                        connection.rollback();
                        new Alert(Alert.AlertType.ERROR,"Error").show();
                    }
            }else{
                connection.rollback();
                new Alert(Alert.AlertType.ERROR,"Error!").show();
            }
        }catch(SQLException | ClassNotFoundException e){
            System.out.println(e);
        } catch (JRException e) {
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }

    }

    private String nextPaymentID() throws SQLException, ClassNotFoundException {
        String x=ServiceCrudController.getServiceID();
        //System.out.println(x);
        String[] arrOfStr = x.split("S", 2);

        String num="0";
        int digits=0;
        for (String a : arrOfStr){
            //System.out.println(a);
            num=a;
        }

        digits=Integer.parseInt(num);
        int implent=digits+1;
        //System.out.println("c"+implent);
        System.out.println(String.format("P"+"%0" + 3 + "d",(digits+1)));
        return (String.format("P"+"%0" + 3 + "d",(digits+1)));
    }

    private String nextServiceID() throws SQLException, ClassNotFoundException {
        String x=ServiceCrudController.getServiceID();
        //System.out.println(x);
        String[] arrOfStr = x.split("S", 2);

        String num="0";
        int digits=0;
        for (String a : arrOfStr){
            //System.out.println(a);
            num=a;
        }

        digits=Integer.parseInt(num);
        int implent=digits+1;
        //System.out.println("c"+implent);
        System.out.println(String.format("C"+"%0" + 3 + "d",(digits+1)));
        return (String.format("S"+"%0" + 3 + "d",(digits+1)));
    }

    public void btnRemoveActionOnAction(ActionEvent actionEvent) {
    }

    public void openNewVehicleOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/VehicleForm.fxml"))));
        stage.show();
    }

    private ServiceTM isExists(String sparePartID){
        for (ServiceTM tm:tmList) {
            if(tm.getSparePartID().equals(sparePartID)){
                return tm;
            }
        }
        return null;
    }
    
    private void calculateTotal(){
        double total=0;
        double serviceCharge=0;
        serviceCharge=Double.parseDouble(txtServiceCharge.getText());
        total=total+serviceCharge;
        for (ServiceTM tm:tmList
             ) {
            total+=tm.getTotal();
        }
        txtTotal.setText(String.valueOf(total));
    }

    public void textFields_Key_Released(KeyEvent keyEvent) {
        ValidationUtil.validate(map2,btnPlaceOrder);
//        TextField = error
//        boolean // validation ok

        //if the enter key pressed
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response =  ValidationUtil.validate(map2,btnPlaceOrder);;
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

    public void btnMakePaymentOnAction(ActionEvent actionEvent) throws IOException, JRException {
        /*HashMap paymentMap = new HashMap();
        paymentMap.put("FinalTotal",Double.parseDouble(txtTotal.getText()));
        paymentMap.put("VehicleNumber",cmbVehicleNo.getValue());
        paymentMap.put("ServiceID","S003");
        paymentMap.put("EmployeeID",cmbEmployeeID.getValue());
        paymentMap.put("ServiceType",cmbServiceName.getValue());
        paymentMap.put("Mileage",Integer.parseInt(txtMileage.getText()));
        paymentMap.put("Date",lblDate.getText());
        paymentMap.put("Time",lblTime.getText());

        ObservableList<ServiceTM> serviceTMS = tblServiceDetails.getItems();
        JasperReport jasperReport = null;
        try {
            jasperReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("view/Reports/payment_bill.jasper"));
        } catch (JRException e) {
            e.printStackTrace();
        }
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,paymentMap,new JRBeanCollectionDataSource(serviceTMS));*/
    }

    public void serviceChargeOnAction(ActionEvent actionEvent) {
        calculateTotal();
    }
}
