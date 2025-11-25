package Service;

import Model.Dto.StockUpdate;
import Model.Entity.StockUpdateEntity;
import Repository.StockUpdateRepository;
import Repository.StockUpdateRepositoryImpl;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.stream.Collectors;

public class StockUpdateServiceImpl implements StockUpdateService {
    StockUpdateRepository stockUpdateRepository=new StockUpdateRepositoryImpl();



    @Override
    public void addStockUpdate(StockUpdate stockUpdate) throws SQLException {
        stockUpdateRepository.add(stockUpdate);
    }

    @Override
    public void updateStockUpdate(StockUpdate stockUpdate) throws SQLException {
        stockUpdateRepository.update(stockUpdate);
    }

    @Override
    public ObservableList<StockUpdate> getAllStockUpdates() throws SQLException {
        ObservableList<StockUpdate> stockUpdates = stockUpdateRepository.Allstockupdates();
        return stockUpdates;


    }


}
