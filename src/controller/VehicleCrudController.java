package controller;

import models.Customer;
import models.Vehicle;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleCrudController {
    public static Vehicle getVehicle(String vehicleNo) throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT * FROM Vehicle WHERE vehicleNo=?", vehicleNo);
        if (result.next()){
            return new Vehicle(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getInt(5),
                    result.getString(6),
                    result.getString(7)
            );
        }
        return null;
    }
}
