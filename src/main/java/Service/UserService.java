package Service;

import Model.Dto.Users;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    void addUser(Users users) throws SQLException;
    void updateUser(Users users) throws SQLException;
    void deleteUser(String id) throws SQLException;
    Users getUserById(String id) throws SQLException;
    Users getUserByUsername(String username) throws SQLException;
    List<Users> getAllUsers() throws SQLException;
    List<Users> searchUsers(String keyword) throws SQLException;
    String generateNextUserId() throws SQLException;
    boolean validateUser(String username, String password) throws SQLException;

    Users authenticateUser(String username, String password, String role) throws SQLException;


    boolean userExistsWithRole(String username, String role) throws SQLException;
}