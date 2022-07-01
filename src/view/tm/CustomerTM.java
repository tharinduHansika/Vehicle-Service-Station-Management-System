package view.tm;

import javafx.scene.control.Button;

public class CustomerTM {
    private String NIC;
    private String Name;
    private String Address;
    private String Telephone;
    private Button btn;

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public CustomerTM(String NIC, String name, String address, String telephone, Button btn) {
        this.NIC = NIC;
        Name = name;
        Address = address;
        Telephone = telephone;
        this.btn = btn;
    }

    public CustomerTM() {
    }

    @Override
    public String toString() {
        return "CustomerTM{" +
                "NIC='" + NIC + '\'' +
                ", Name='" + Name + '\'' +
                ", Address='" + Address + '\'' +
                ", Telephone='" + Telephone + '\'' +
                ", btn=" + btn +
                '}';
    }
}
