package Repository;

import DB.DBConnection;
import Model.Dto.Suppliers;
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

    @Override
    public List<String> getSuppliersIds() throws SQLException {
        List<String> ids=new ArrayList<>();
        Connection connection=DBConnection.getInstance().getConnection();
        String SQL = "SELECT id FROM Suppliers ORDER BY id ";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String id=resultSet.getString("id");
            ids.add(id.trim());

        }


        return ids;
    }

    @Override
    public List<Suppliers> getsupplierfordrug(String drugId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT s.* FROM suppliers s " +
                "JOIN drug_supplier ds ON s.id = ds.supplier_id " +
                "WHERE ds.drug_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, drugId); // Use setString instead of setObject

        ResultSet resultSet = preparedStatement.executeQuery();
        List<Suppliers> suppliersList = new ArrayList<>();

        while (resultSet.next()) {
            Suppliers supplier = new Suppliers(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("telNo"),
                    resultSet.getString("email"),
                    resultSet.getString("company"),
                    resultSet.getDate("regDate").toLocalDate()
            );
            suppliersList.add(supplier);
        }



        return suppliersList;
    }

    @Override
    public void adddrugsupplierrelationship(String drugId, String supplierId) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO drug_supplier (drug_id, supplier_id) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE supplier_id = supplier_id";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, drugId);
            preparedStatement.setString(2, supplierId);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Drug-Supplier relationship established: Drug=" + drugId + ", Supplier=" + supplierId);
            }
        } catch (SQLException e) {
            System.err.println("Error establishing drug-supplier relationship: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void createlowstockalert(String supplierId, String drugId, int currentStock) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO low_stock_alerts (supplier_id, drug_id, current_stock, alert_date, status) " +
                "VALUES (?, ?, ?, CURDATE(), 'PENDING')";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, supplierId);
            preparedStatement.setString(2, drugId);
            preparedStatement.setInt(3, currentStock);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Low stock alert created for Supplier=" + supplierId +
                        ", Drug=" + drugId + ", Current Stock=" + currentStock);
            }
        } catch (SQLException e) {
            System.err.println("Error creating low stock alert: " + e.getMessage());
            throw e;
        }
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