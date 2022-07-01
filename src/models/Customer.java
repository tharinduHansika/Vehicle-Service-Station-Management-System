package models;

public class Customer {
    private String NIC;
    private String name;
    private String address;
    private String telephone;

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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Customer(String NIC, String name, String address, String telephone) {
        this.NIC = NIC;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
    }

    public Customer() {
    }

    @Override
    public String toString() {
        return "Customer{" +
                "NIC='" + NIC + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
