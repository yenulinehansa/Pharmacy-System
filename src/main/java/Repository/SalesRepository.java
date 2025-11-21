package Repository;

import Model.Dto.Sales;
import Model.Dto.SalesDetails;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDate;

public interface SalesRepository {


    String generatelastID() throws SQLException;

    ObservableList<SalesDetails> getsalesdetails() throws SQLException;

    ObservableList<SalesDetails> searchsales(LocalDate date) throws SQLException;

    void save(Sales sales) throws SQLException;

    void insert(SalesDetails salesDetail) throws SQLException;
}
