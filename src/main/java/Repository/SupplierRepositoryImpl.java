package Repository;

import DB.DBConnection;
import Model.Entity.SuppliersEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepositoryImpl implements SupplierRepository {

    @Override
    public void save(SuppliersEntity suppliersEntity) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "INSERT INTO Suppliers (id,name, telNo, email, company, regDate) VALUES(?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setObject(1, suppliersEntity.getId());
        preparedStatement.setObject(2, suppliersEntity.getName());
        preparedStatement.setObject(3, suppliersEntity.getTelNo());
        preparedStatement.setObject(4, suppliersEntity.getEmail());
        preparedStatement.setObject(5, suppliersEntity.getCompany());
        preparedStatement.setObject(6, suppliersEntity.getRegDate());
        preparedStatement.executeUpdate();
    }

    @Override
    public void update(SuppliersEntity suppliersEntity) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "UPDATE Suppliers SET name=?, telNo=?, email=?, company=?, regDate=? WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setObject(1, suppliersEntity.getName());
        preparedStatement.setObject(2, suppliersEntity.getTelNo());
        preparedStatement.setObject(3, suppliersEntity.getEmail());
        preparedStatement.setObject(4, suppliersEntity.getCompany());
        preparedStatement.setObject(5, suppliersEntity.getRegDate());
        preparedStatement.setObject(6, suppliersEntity.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "DELETE FROM Suppliers WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setObject(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public SuppliersEntity findById(int id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "SELECT * FROM Suppliers WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setObject(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return extractSuppliersEntity(resultSet);
        }
        return null;
    }

    @Override
    public List<SuppliersEntity> findAll() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "SELECT * FROM Suppliers ORDER BY id";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<SuppliersEntity> suppliersList = new ArrayList<>();
        while (resultSet.next()) {
            suppliersList.add(extractSuppliersEntity(resultSet));
        }
        return suppliersList;
    }

    @Override
    public List<SuppliersEntity> search(String keyword) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "SELECT * FROM Suppliers WHERE id LIKE ? OR name LIKE ? OR telNo LIKE ? OR email LIKE ? OR company LIKE ? OR regDate LIKE ? ORDER BY id";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        String searchPattern = "%" + keyword + "%";
        preparedStatement.setString(1, searchPattern);
        preparedStatement.setString(2, searchPattern);
        preparedStatement.setString(3, searchPattern);
        preparedStatement.setString(4, searchPattern);
        preparedStatement.setString(5, searchPattern);
        preparedStatement.setString(6, searchPattern);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<SuppliersEntity> suppliersList = new ArrayList<>();
        while (resultSet.next()) {
            suppliersList.add(extractSuppliersEntity(resultSet));
        }
        return suppliersList;
    }

    @Override
    public String getLastSupplierId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "SELECT id FROM Suppliers ORDER BY id DESC LIMIT 1";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("id");
        }
        return null; // No suppliers in database
    }

    private SuppliersEntity extractSuppliersEntity(ResultSet resultSet) throws SQLException {
        return new SuppliersEntity(
                resultSet.getString("id"),
                resultSet.getString("name"),
                resultSet.getString("telNo"),
                resultSet.getString("email"),
                resultSet.getString("company"),
                resultSet.getDate("regDate").toLocalDate()
        );
    }
}