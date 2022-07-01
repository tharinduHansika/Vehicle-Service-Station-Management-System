package models;

public class Payment {
    private String paymentID;
    private String serviceID;
    private double amount;
    private String date;
    private String time;


    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
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

    public Payment(String paymentID, String serviceID, double amount, String date, String time) {
        this.paymentID = paymentID;
        this.serviceID = serviceID;
        this.amount = amount;
        this.date = date;
        this.time = time;
    }

    public Payment() {
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentID='" + paymentID + '\'' +
                ", serviceID='" + serviceID + '\'' +
                ", amount=" + amount +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
