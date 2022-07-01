package models;

public class Vehicle {
    private String vehicleNo;
    private String customerID;
    private String type;
    private String brand;
    private int mileage;
    private String fuelType;
    private String transmission;


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

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
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

    public Vehicle(String vehicleNo, String customerID, String type, String brand, int mileage, String fuelType, String transmission) {
        this.vehicleNo = vehicleNo;
        this.customerID = customerID;
        this.type = type;
        this.brand = brand;
        this.mileage = mileage;
        this.fuelType = fuelType;
        this.transmission = transmission;
    }

    public Vehicle(String vehicleNo, String type, String brand, int mileage, String fuelType, String transmission) {
        this.vehicleNo = vehicleNo;
        this.type = type;
        this.brand = brand;
        this.mileage = mileage;
        this.fuelType = fuelType;
        this.transmission = transmission;
    }

    public Vehicle(String type, String brand, int mileage, String fuelType, String transmission) {
        this.type = type;
        this.brand = brand;
        this.mileage = mileage;
        this.fuelType = fuelType;
        this.transmission = transmission;
    }

    public Vehicle() {
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleNo='" + vehicleNo + '\'' +
                ", customerID='" + customerID + '\'' +
                ", type='" + type + '\'' +
                ", brand='" + brand + '\'' +
                ", mileage=" + mileage +
                ", fuelType='" + fuelType + '\'' +
                ", transmission='" + transmission + '\'' +
                '}';
    }
}
