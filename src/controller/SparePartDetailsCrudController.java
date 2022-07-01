package controller;

import models.SparePart;
import models.Supplier;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SparePartDetailsCrudController {
    public static ArrayList<String> getSparePartID() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT sparePartID FROM sparePart");
        ArrayList<String> ids= new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }

    public static SparePart getSparePartDetails(String id) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM SparePart WHERE sparePartID=?", id);
        if (result.next()) {
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

    public static ArrayList<String> getSupplierID() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT supplierID FROM supplier");
        ArrayList<String> ids= new ArrayList<>();
        while (result.next()){
            ids.add(result.getString(1));
        }
        return ids;
    }

    public static Supplier getSupplierDetails(String id) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM Supplier WHERE supplierID=?", id);
        if (result.next()) {
            return new Supplier(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getInt(4),
                    result.getString(5),
                    result.getString(6)
            );
        }
        return null;
    }

    public static boolean updateQTY(String sparePartsID, Integer qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE SparePart SET qtyOnHand=qtyOnHand+? WHERE sparePartID=?",qty,sparePartsID);
    }
}
