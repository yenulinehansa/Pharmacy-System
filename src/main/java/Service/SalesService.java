package Service;

import Model.Dto.SalesDetails;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface SalesService {
    String generateid() throws SQLException;


    ObservableList<SalesDetails> getSalesDetails() throws SQLException;

    ObservableList<SalesDetails> searchsales(LocalDate date) throws SQLException;
}
