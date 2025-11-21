package Repository;

import DB.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
