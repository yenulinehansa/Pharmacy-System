package Repository;

import DB.DBConnection;
import Model.Dto.Sales;
import Model.Dto.SalesDetails;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SalesRepositoryImpl implements SalesRepository{

    @Override
    public String generatelastID() throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "SELECT Orderid FROM Sales ORDER BY Orderid DESC LIMIT 1";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("Orderid");
        }
        return null;
    }

    @Override
    public ObservableList<SalesDetails> getsalesdetails() throws SQLException {
        ObservableList<SalesDetails> salesDetails = FXCollections.observableArrayList();
        Connection connection=DBConnection.getInstance().getConnection();
        String SQL = "SELECT * FROM Salesdetails";
        PreparedStatement preparedStatement=connection.prepareStatement(SQL);
        ResultSet resultSet=preparedStatement.executeQuery();
        while (resultSet.next()) {
            salesDetails.add(new SalesDetails(
                    resultSet.getString("orderId"),
                    resultSet.getString("patientId"),
                    resultSet.getString("drugid"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("discount"),
                    resultSet.getDouble("total"),
                    resultSet.getDate("orderDate").toLocalDate()

            ));


        }
        return salesDetails;
    }

    @Override
    public ObservableList<SalesDetails> searchsales(LocalDate date) throws SQLException {
        ObservableList<SalesDetails> salesDetails = FXCollections.observableArrayList();
        Connection connection=DBConnection.getInstance().getConnection();
        String SQL = "SELECT * FROM Salesdetails WHERE orderDate=?";
        PreparedStatement preparedStatement=connection.prepareStatement(SQL);
        preparedStatement.setObject(1,date);
        ResultSet resultSet=preparedStatement.executeQuery();
        while (resultSet.next()) {
            salesDetails.add(new SalesDetails(
                    resultSet.getString("orderId"),
                    resultSet.getString("patientId"),
                    resultSet.getString("drugid"),
                    resultSet.getInt("quantity"),
                    resultSet.getDouble("discount"),
                    resultSet.getDouble("total"),
                    resultSet.getDate("orderDate").toLocalDate()

            ));


        }
        return salesDetails;
    }

    @Override
    public void save(Sales sales) throws SQLException {
        Connection connection=DBConnection.getInstance().getConnection();
        String SQL="INSERT INTO Sales VALUES (?,?,?,?)";
        PreparedStatement  preparedStatement=connection.prepareStatement(SQL);
        preparedStatement.setObject(1,sales.getOrderid());
        preparedStatement.setObject(2,sales.getPatientid());
        preparedStatement.setObject(3,sales.getTotaldiscount());
        preparedStatement.setObject(4,sales.getFinaltotal());
        preparedStatement.executeUpdate();
    }

    @Override
    public void insert(SalesDetails salesDetail) throws SQLException {
        Connection connection=DBConnection.getInstance().getConnection();
        String SQL="INSERT INTO Salesdetails VALUES (?,?,?,?,?,?,?)";
        PreparedStatement  preparedStatement=connection.prepareStatement(SQL);
        preparedStatement.setObject(1,salesDetail.getOrderId());
        preparedStatement.setObject(2,salesDetail.getPatientId());
        preparedStatement.setObject(3,salesDetail.getDrugid());
        preparedStatement.setObject(4,salesDetail.getQuantity());
        preparedStatement.setObject(5,salesDetail.getDiscount());
        preparedStatement.setObject(6,salesDetail.getTotal());
        preparedStatement.setObject(7,salesDetail.getOrderDate());
        preparedStatement.executeUpdate();
    }
}
