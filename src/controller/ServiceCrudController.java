package controller;

import models.*;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceCrudController {

    public boolean saveService(Service service) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Service VALUES(?,?,?,?,?,?,?,?)",
                service.getServiceID(), service.getVehicleID(), service.getEmployeeID(), service.getName(), service.getDescription(),
                service.getServiceCharge(),service.getDate(),service.getTime());
    }

    public boolean savePayment(Payment payment) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Payment VALUES(?,?,?,?,?)",
                payment.getPaymentID(),payment.getServiceID(),payment.getAmount(),payment.getDate(),payment.getTime());
    }

    public boolean saveServiceDetail(ArrayList<ServiceDetails> details) throws SQLException, ClassNotFoundException {
        for (ServiceDetails det:details
             ) {
            boolean isDetailSaved = CrudUtil.execute("INSERT INTO ServiceDetails VALUES(?,?,?,?)", det.getServiceID(), det.getSparePartID(), det.getQty(), det.getUnitPrice());
            if (isDetailSaved) {
                if(!updateQty(det.getSparePartID(), det.getQty())){
                    return false;
                }
            }else{
                return false;
            }
        }
        return true;
    }

    private boolean updateQty(String sparePartID, Integer qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE SparePart SET qtyOnHand=qtyOnHand-? WHERE sparePartID=?",qty,sparePartID);
    }

    public static String getServiceID() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT serviceID FROM Service ORDER BY serviceID DESC LIMIT 1");
        if (set.next()){
            return set.getString(1);
            /*-----D001-> D002----*/
        }else{
            return "D001";
        }
    }
}
