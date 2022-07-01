package view.tm;

import javafx.scene.control.Button;

public class SparePartTM {
    private String sparePartID;
    private String name;
    private String brand;
    private String description;
    private Integer qtyOnHand;
    private double unitPrice;
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

    public Integer getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(Integer qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public SparePartTM(String sparePartID, String name, String brand, String description, Integer qtyOnHand, double unitPrice, Button btn) {
        this.sparePartID = sparePartID;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
        this.btn = btn;
    }

    public SparePartTM() {
    }

    @Override
    public String toString() {
        return "SparePartTM{" +
                "sparePartID='" + sparePartID + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", description='" + description + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                ", unitPrice=" + unitPrice +
                ", btn=" + btn +
                '}';
    }
}
