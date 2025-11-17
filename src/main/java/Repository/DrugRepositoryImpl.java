package Repository;

import DB.DBConnection;
import Model.Entity.DrugsEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DrugRepositoryImpl implements DrugRepository {

    @Override
    public void save(DrugsEntity drugsEntity) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "INSERT INTO Drugs VALUES(?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setObject(1, drugsEntity.getId());
        preparedStatement.setObject(2, drugsEntity.getName());
        preparedStatement.setObject(3, drugsEntity.getBrand());
        preparedStatement.setObject(4, drugsEntity.getUnitprice());
        preparedStatement.setObject(5, drugsEntity.getStock_qty());
        preparedStatement.setObject(6, drugsEntity.getExpDate());
        preparedStatement.executeUpdate();
    }

    @Override
    public void update(DrugsEntity drugsEntity) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "UPDATE Drugs SET name=?, brand=?, unitprice=?, stock_qty=?, expDate=? WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setObject(1, drugsEntity.getName());
        preparedStatement.setObject(2, drugsEntity.getBrand());
        preparedStatement.setObject(3, drugsEntity.getUnitprice());
        preparedStatement.setObject(4, drugsEntity.getStock_qty());
        preparedStatement.setObject(5, drugsEntity.getExpDate());
        preparedStatement.setObject(6, drugsEntity.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "DELETE FROM Drugs WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setObject(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public DrugsEntity findById(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "SELECT * FROM Drugs WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setObject(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return extractDrugsEntity(resultSet);
        }
        return null;
    }

    @Override
    public List<DrugsEntity> findAll() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "SELECT * FROM Drugs ORDER BY id";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<DrugsEntity> drugsList = new ArrayList<>();
        while (resultSet.next()) {
            drugsList.add(extractDrugsEntity(resultSet));
        }
        return drugsList;
    }

    @Override
    public List<DrugsEntity> search(String keyword) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "SELECT * FROM Drugs WHERE id LIKE ? OR name LIKE ? OR brand LIKE ? ORDER BY id";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        String searchPattern = "%" + keyword + "%";
        preparedStatement.setString(1, searchPattern);
        preparedStatement.setString(2, searchPattern);
        preparedStatement.setString(3, searchPattern);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<DrugsEntity> drugsList = new ArrayList<>();
        while (resultSet.next()) {
            drugsList.add(extractDrugsEntity(resultSet));
        }
        return drugsList;
    }

    @Override
    public String getLastDrugId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "SELECT id FROM Drugs ORDER BY id DESC LIMIT 1";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("id");
        }
        return null; // No drugs in database
    }

    private DrugsEntity extractDrugsEntity(ResultSet resultSet) throws SQLException {
        return new DrugsEntity(
                resultSet.getString("id"),
                resultSet.getString("name"),
                resultSet.getString("brand"),
                resultSet.getDouble("unitprice"),
                resultSet.getInt("stock_qty"),
                resultSet.getDate("expDate").toLocalDate()
        );
    }
}