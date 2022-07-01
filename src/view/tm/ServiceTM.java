package view.tm;

import javafx.scene.control.Button;

public class ServiceTM {
    private String sparePartID;
    private String name;
    private String brand;
    private String description;
    private double unitPrice;
    private Integer qty;
    private double total;
    private Button btn;

    public String getSparePartID() {
        return sparePartID;
    }

    public void setSparePartID(String sparePartID) {
        this.sparePartID = sparePartID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public ServiceTM(String sparePartID, String name, String brand, String description, double unitPrice, Integer qty, double total) {
        this.sparePartID = sparePartID;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.total = total;
    }

    public ServiceTM(String sparePartID, String name, String brand, String description, double unitPrice, Integer qty, double total, Button btn) {
        this.sparePartID = sparePartID;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.total = total;
        this.btn = btn;
    }

    public ServiceTM() {
    }

    @Override
    public String toString() {
        return "ServiceTM{" +
                "sparePartID='" + sparePartID + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                ", total=" + total +
                ", btn=" + btn +
                '}';
    }
}
