package Repository;

import Model.Dto.StockUpdate;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface StockUpdateRepository {
    void add(StockUpdate stockUpdate) throws SQLException;

    void update(StockUpdate stockUpdate) throws SQLException;


    ObservableList<StockUpdate> Allstockupdates() throws SQLException;
}
