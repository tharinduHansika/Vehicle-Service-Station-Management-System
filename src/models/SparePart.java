package models;

public class SparePart {
    private String sparePartID;
    private String name;
    private String brand;
    private String description;
    private int qtyOnHand;
    private double unitPrice;

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

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public SparePart(String sparePartID, String name, String brand, String description, int qtyOnHand, double unitPrice) {
        this.sparePartID = sparePartID;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.qtyOnHand = qtyOnHand;
        this.unitPrice = unitPrice;
    }

    public SparePart() {
    }

    @Override
    public String toString() {
        return "SparePart{" +
                "sparePartID='" + sparePartID + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", description='" + description + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
