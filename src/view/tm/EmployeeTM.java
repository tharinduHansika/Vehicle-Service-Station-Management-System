package view.tm;

import javafx.scene.control.Button;

public class EmployeeTM {
    private String NIC;
    private String name;
    private String address;
    private String position;
    private double salary;
    private String telephone;
    private Button btn;

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }

    public EmployeeTM(String NIC, String name, String address, String position, double salary, String telephone, Button btn) {
        this.NIC = NIC;
        this.name = name;
        this.address = address;
        this.position = position;
        this.salary = salary;
        this.telephone = telephone;
        this.btn = btn;
    }

    public EmployeeTM() {
    }

    @Override
    public String toString() {
        return "EmployeeTm{" +
                "NIC='" + NIC + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", telephone='" + telephone + '\'' +
                ", btn=" + btn +
                '}';
    }
}
