package Service.Impl;

import Model.Dto.StockUpdate;
import Repository.StockUpdateRepository;
import Repository.Impl.StockUpdateRepositoryImpl;
import Service.StockUpdateService;
import javafx.collections.ObservableList;

import java.sql.SQLException;

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
