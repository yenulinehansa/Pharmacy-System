package Repository;

import DB.DBConnection;
import Model.Dto.Drugs;
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
        return null;
    }

    @Override
    public List<String> getIds() throws SQLException {
        List<String> ids=new ArrayList<>();
        Connection connection=DBConnection.getInstance().getConnection();
        String SQL = "SELECT id FROM drugs ORDER BY id ";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String id=resultSet.getString("id");
            ids.add(id.trim());

        }


        return ids;
    }

    @Override
    public int loaddrugscount() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "SELECT * FROM Drugs ";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<DrugsEntity> drugsList = new ArrayList<>();
        while (resultSet.next()) {
            drugsList.add(extractDrugsEntity(resultSet));

        }
        return drugsList.size();
    }

    @Override
    public Boolean quantityupdate(String drugid, int quantity) throws SQLException {
        Connection connection=DBConnection.getInstance().getConnection();
        String SQL="UPDATE drugs SET stock_qty=stock_qty-? WHERE id=?";
        PreparedStatement preparedStatement=connection.prepareStatement(SQL);
        preparedStatement.setObject(1,quantity);
        preparedStatement.setObject(2,drugid);
        return preparedStatement.executeUpdate()>0;
    }

    @Override
    public void updatedrugstock(String drugId, int quantityToAdd) throws SQLException {
        Connection connection=DBConnection.getInstance().getConnection();
        String sql = "UPDATE drugs SET stock_qty = stock_qty + ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1,quantityToAdd);
        preparedStatement.setObject(2,drugId);
        preparedStatement.executeUpdate();

    }

    @Override
    public List<Drugs> getLowstockDrugs(int threshold) throws SQLException {
        List<Drugs> drugsList = new ArrayList<>();
        Connection connection=DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM drugs WHERE stock_qty <= ?";
        PreparedStatement  preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1,threshold);

        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            Drugs drug = new Drugs();
            drug.setId(resultSet.getString("id"));
            drug.setName(resultSet.getString("name"));
            drug.setBrand(resultSet.getString("brand"));
            drug.setUnitprice(resultSet.getDouble("unitprice"));
            drug.setStock_qty(resultSet.getInt("stock_qty"));
            drug.setExpDate(resultSet.getDate("expDate").toLocalDate());
            drugsList.add(drug);
        }
        return drugsList;
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