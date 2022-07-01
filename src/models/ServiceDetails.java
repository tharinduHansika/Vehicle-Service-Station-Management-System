package models;

public class ServiceDetails {
    private String serviceID;
    private String sparePartID;
    private Integer qty;
    private double unitPrice;

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getSparePartID() {
        return sparePartID;
    }

    public void setSparePartID(String sparePartID) {
        this.sparePartID = sparePartID;
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

    public ServiceDetails(String serviceID, String sparePartID, Integer qty, double unitPrice) {
        this.serviceID = serviceID;
        this.sparePartID = sparePartID;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    public ServiceDetails() {
    }

    @Override
    public String toString() {
        return "ServiceDetails{" +
                "serviceID='" + serviceID + '\'' +
                ", sparePartID='" + sparePartID + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
