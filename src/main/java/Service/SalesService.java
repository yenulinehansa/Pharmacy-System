package Service;

import Model.Dto.CartItems;
import Model.Dto.Sales;
import Model.Dto.SalesDetails;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDate;

public interface SalesService {
    String generateid() throws SQLException;


    ObservableList<SalesDetails> getSalesDetails() throws SQLException;

    ObservableList<SalesDetails> searchsales(LocalDate date) throws SQLException;

    Boolean save(Sales sales) throws SQLException;

    Boolean saveSalesDetails(SalesDetails salesDetail) throws SQLException;

    int getsalescount() throws SQLException;

    void placefullOrder(String orderid, String patientid, Double totalDiscount, double finalAmount, ObservableList<CartItems> cartItems) throws SQLException;
}
