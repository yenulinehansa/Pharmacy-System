package Repository;

import DB.DBConnection;
import Model.Dto.StockUpdate;
import Model.Entity.StockUpdateEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StockUpdateRepositoryImpl implements StockUpdateRepository {


    @Override
    public void add(StockUpdate stockUpdate) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO stockupdate VALUES(?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, stockUpdate.getSupplierId());
        preparedStatement.setObject(2, stockUpdate.getDrugId());
        preparedStatement.setObject(3, stockUpdate.getQuantity());
        preparedStatement.setObject(4, stockUpdate.getBuying_price());
        preparedStatement.setObject(5, stockUpdate.getDate());
        preparedStatement.executeUpdate();

    }

    @Override
    public void update(StockUpdate stockUpdate) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "UPDATE stockupdate SET qty=?,buyingPrice=? WHERE supplierId=? AND drugId=? ";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, stockUpdate.getQuantity());
        preparedStatement.setObject(2, stockUpdate.getBuying_price());

        preparedStatement.setObject(3, stockUpdate.getSupplierId());
        preparedStatement.setObject(4, stockUpdate.getDrugId());
        preparedStatement.executeUpdate();


    }

    @Override
    public ObservableList<StockUpdate> Allstockupdates() throws SQLException {
        ObservableList<StockUpdate> stockList = FXCollections.observableArrayList();

        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM stockupdate";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            StockUpdate stockUpdate = new StockUpdate(
                    resultSet.getString("supplierId"),
                    resultSet.getString("drugId"),
                    resultSet.getInt("qty"),
                    resultSet.getDouble("buyingPrice"),
                    resultSet.getDate("date").toLocalDate()
            );
            stockList.add(stockUpdate);
        }
        return stockList;


    }
}

