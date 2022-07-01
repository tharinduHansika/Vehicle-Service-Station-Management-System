package view.tm;

import javafx.scene.control.Button;

public class SupplierTM {
    private String supplierID;
    private String name;
    private String address;
    private Integer accountNumber;
    private String telephone;
    private String email;
    private Button btn;

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public SupplierTM(String supplierID, String name, String address, Integer accountNumber, String telephone, String email, Button btn) {
        this.supplierID = supplierID;
        this.name = name;
        this.address = address;
        this.accountNumber = accountNumber;
        this.telephone = telephone;
        this.email = email;
        this.btn = btn;
    }

    public SupplierTM() {
    }

    @Override
    public String toString() {
        return "SupplierTM{" +
                "supplierID='" + supplierID + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", accountNumber=" + accountNumber +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", btn=" + btn +
                '}';
    }
}
