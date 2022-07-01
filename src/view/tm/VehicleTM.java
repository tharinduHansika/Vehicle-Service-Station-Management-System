package view.tm;

import javafx.scene.control.Button;

public class VehicleTM {
    private String vehicleNo;
    private String customerID;
    private String type;
    private String brand;
    private Integer mileage;
    private String fuelType;
    private String transmission;
    private Button btn;

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public VehicleTM(String vehicleNo, String customerID, String type, String brand, Integer mileage, String fuelType, String transmission, Button btn) {
        this.vehicleNo = vehicleNo;
        this.customerID = customerID;
        this.type = type;
        this.brand = brand;
        this.mileage = mileage;
        this.fuelType = fuelType;
        this.transmission = transmission;
        this.btn = btn;
    }

    public VehicleTM() {
    }

    @Override
    public String toString() {
        return "VehicleTM{" +
                "vehicleNo='" + vehicleNo + '\'' +
                ", customerID='" + customerID + '\'' +
                ", type='" + type + '\'' +
                ", brand='" + brand + '\'' +
                ", mileage=" + mileage +
                ", fuelType='" + fuelType + '\'' +
                ", transmission='" + transmission + '\'' +
                ", btn=" + btn +
                '}';
    }
}
