package models;

public class Service {
    private String serviceID;
    private String vehicleID;
    private String employeeID;
    private String name;
    private String description;
    private double serviceCharge;
    private String date;
    private String time;


    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Service(String serviceID, String vehicleID, String employeeID, String name, String description, double serviceCharge, String date, String time) {
        this.serviceID = serviceID;
        this.vehicleID = vehicleID;
        this.employeeID = employeeID;
        this.name = name;
        this.description = description;
        this.serviceCharge = serviceCharge;
        this.date = date;
        this.time = time;
    }

    public Service() {
    }

    @Override
    public String toString() {
        return "Service{" +
                "serviceID='" + serviceID + '\'' +
                ", vehicleID='" + vehicleID + '\'' +
                ", employeeID='" + employeeID + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", serviceCharge=" + serviceCharge +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
