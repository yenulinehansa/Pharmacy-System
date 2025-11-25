package Repository.Impl;

import DB.DBConnection;
import Model.Entity.UsersEntity;
import Repository.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public void save(UsersEntity usersEntity) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "INSERT INTO Users VALUES(?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setObject(1, usersEntity.getId());
        preparedStatement.setObject(2, usersEntity.getName());
        preparedStatement.setObject(3, usersEntity.getTelNo());
        preparedStatement.setObject(4, usersEntity.getEmail());
        preparedStatement.setObject(5, usersEntity.getRole());
        preparedStatement.setObject(6, usersEntity.getUsername());
        preparedStatement.setObject(7, usersEntity.getPassword());
        preparedStatement.setObject(8, usersEntity.getSalary());
        preparedStatement.setObject(9, usersEntity.getRegDate());
        preparedStatement.executeUpdate();
    }

    @Override
    public void update(UsersEntity usersEntity) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "UPDATE Users SET name=?, telNo=?, email=?, role=?, username=?, password=?, salary=?, regDate=? WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setObject(1, usersEntity.getName());
        preparedStatement.setObject(2, usersEntity.getTelNo());
        preparedStatement.setObject(3, usersEntity.getEmail());
        preparedStatement.setObject(4, usersEntity.getRole());
        preparedStatement.setObject(5, usersEntity.getUsername());
        preparedStatement.setObject(6, usersEntity.getPassword());
        preparedStatement.setObject(7, usersEntity.getSalary());
        preparedStatement.setObject(8, usersEntity.getRegDate());
        preparedStatement.setObject(9, usersEntity.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "DELETE FROM Users WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setObject(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public UsersEntity findById(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "SELECT * FROM Users WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setObject(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return extractUsersEntity(resultSet);
        }
        return null;
    }

    @Override
    public UsersEntity findByUsername(String username) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "SELECT * FROM Users WHERE username=?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setObject(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return extractUsersEntity(resultSet);
        }
        return null;
    }

    @Override
    public List<UsersEntity> findAll() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "SELECT * FROM Users ORDER BY id";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<UsersEntity> usersList = new ArrayList<>();
        while (resultSet.next()) {
            usersList.add(extractUsersEntity(resultSet));
        }
        return usersList;
    }

    @Override
    public List<UsersEntity> search(String keyword) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "SELECT * FROM Users WHERE id LIKE ? OR name LIKE ? OR username LIKE ? OR email LIKE ? OR role LIKE ? ORDER BY id";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        String searchPattern = "%" + keyword + "%";
        preparedStatement.setString(1, searchPattern);
        preparedStatement.setString(2, searchPattern);
        preparedStatement.setString(3, searchPattern);
        preparedStatement.setString(4, searchPattern);
        preparedStatement.setString(5, searchPattern);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<UsersEntity> usersList = new ArrayList<>();
        while (resultSet.next()) {
            usersList.add(extractUsersEntity(resultSet));
        }
        return usersList;
    }

    @Override
    public String getLastUserId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "SELECT id FROM Users ORDER BY id DESC LIMIT 1";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString("id");
        }
        return null; // No users in database
    }

    private UsersEntity extractUsersEntity(ResultSet resultSet) throws SQLException {
        return new UsersEntity(
                resultSet.getString("id"),
                resultSet.getString("name"),
                resultSet.getString("telNo"),
                resultSet.getString("email"),
                resultSet.getString("role"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getDouble("salary"),
                resultSet.getDate("regDate").toLocalDate()
        );
    }
    @Override
    public UsersEntity findByUsernameAndRole(String username, String role) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "SELECT * FROM Users WHERE username=? AND role=?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setObject(1, username);
        preparedStatement.setObject(2, role);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return extractUsersEntity(resultSet);
        }
        return null;
    }

    @Override
    public UsersEntity authenticate(String username, String password, String role) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "SELECT * FROM Users WHERE username=? AND password=? AND role=?";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setObject(1, username);
        preparedStatement.setObject(2, password);
        preparedStatement.setObject(3, role);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return extractUsersEntity(resultSet);
        }
        return null;
    }
}