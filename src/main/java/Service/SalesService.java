package Service;

import Model.Dto.Sales;
import Model.Dto.SalesDetails;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDate;

public interface SalesService {
    String generateid() throws SQLException;


    ObservableList<SalesDetails> getSalesDetails() throws SQLException;

    ObservableList<SalesDetails> searchsales(LocalDate date) throws SQLException;

    void save(Sales sales) throws SQLException;

    void saveSalesDetails(SalesDetails salesDetail) throws SQLException;
}
