package Service;

import Model.Dto.StockUpdate;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface StockUpdateService {
    void addStockUpdate(StockUpdate stockUpdate) throws SQLException;

    void updateStockUpdate(StockUpdate stockUpdate) throws SQLException;


    ObservableList<StockUpdate> getAllStockUpdates() throws SQLException;
}
