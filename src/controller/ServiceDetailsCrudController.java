package controller;

import models.*;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceDetailsCrudController {
    public static ArrayList<String> getSparePartID() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT sparePartID FROM sparePart");
        ArrayList<String> ids= new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }

    public static SparePart getSparePart(String id) throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT * FROM SparePart WHERE sparePartID=?", id);
        if (result.next()){
            return new SparePart(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getInt(5),
                    result.getDouble(6)
            );
        }
        return null;
    }

    public static ArrayList<String> getVehicleNo() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT vehicleNo FROM vehicle");
        ArrayList<String> ids= new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }

    public static Vehicle getVehicleNoDetails(String ID) throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT type,brand,mileage,fuelType,transmission FROM Vehicle WHERE vehicleNo=?", ID);
        if (result.next()){
            return new Vehicle(
                    result.getString(1),
                    result.getString(2),
                    result.getInt(3),
                    result.getString(4),
                    result.getString(5)
            );
        }
        return null;
    }

    public static ArrayList<String> getEmployeeID() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT NIC FROM Employee");
        ArrayList<String> ids= new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }

    public static Employee getEmployeeIDDetails(String ID) throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT * FROM Employee WHERE NIC=?", ID);
        if (result.next()){
            return new Employee(
                    result.getString(1),
                    result.getString(2),
                    result.getString(4)
            );
        }
        return null;
    }

    /*public boolean saveService(Service service, ArrayList<ServiceDetails> details){

    }*/
}
