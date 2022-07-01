package controller;

import models.Customer;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookingCrudController {
    public static ArrayList<String> getCustomerID() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT NIC FROM Customer");
        ArrayList<String> ids= new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }

    public static ArrayList<String> getVehicleID() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT vehicleNo FROM Vehicle");
        ArrayList<String> ids= new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }

    public static Customer getCustomer(String NIC) throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT * FROM Customer WHERE NIC=?", NIC);
        if (result.next()){
            return new Customer(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            );
        }
        return null;
    }

    public static Customer getV(String NIC) throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT * FROM Customer WHERE NIC=?", NIC);
        if (result.next()){
            return new Customer(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            );
        }
        return null;
    }
}
