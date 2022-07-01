package view.tm;

import javafx.scene.control.Button;

public class BookingTM {
    private String bookingID;
    private String vehicleID;
    private String customerID;
    private String serviceType;
    private String date;
    private String time;
    private Button btn;

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
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

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public BookingTM(String bookingID, String vehicleID, String customerID, String serviceType, String date, String time, Button btn) {
        this.bookingID = bookingID;
        this.vehicleID = vehicleID;
        this.customerID = customerID;
        this.serviceType = serviceType;
        this.date = date;
        this.time = time;
        this.btn = btn;
    }

    public BookingTM() {
    }

    @Override
    public String toString() {
        return "BookingTM{" +
                "bookingID='" + bookingID + '\'' +
                ", vehicleID='" + vehicleID + '\'' +
                ", customerID='" + customerID + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", btn=" + btn +
                '}';
    }
}
