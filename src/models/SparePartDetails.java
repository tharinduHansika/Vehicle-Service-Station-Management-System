package models;

public class SparePartDetails {
    private String sparePartsID;
    private String supplierID;
    private Integer qty;
    private double unitPrice;
    private String date;
    private String time;

    public String getSparePartsID() {
        return sparePartsID;
    }

    public void setSparePartsID(String sparePartsID) {
        this.sparePartsID = sparePartsID;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
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

    public SparePartDetails(String sparePartsID, String supplierID, Integer qty, double unitPrice, String date, String time) {
        this.sparePartsID = sparePartsID;
        this.supplierID = supplierID;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.date = date;
        this.time = time;
    }

    public SparePartDetails() {
    }

    @Override
    public String toString() {
        return "SparePartDetails{" +
                "sparePartsID='" + sparePartsID + '\'' +
                ", supplierID='" + supplierID + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
