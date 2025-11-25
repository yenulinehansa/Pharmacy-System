package Repository;

import Model.Entity.UsersEntity;

import java.sql.SQLException;
import java.util.List;

public interface UserRepository {
    void save(UsersEntity usersEntity) throws SQLException;
    void update(UsersEntity usersEntity) throws SQLException;
    void delete(String id) throws SQLException;
    UsersEntity findById(String id) throws SQLException;
    List<UsersEntity> findAll() throws SQLException;
    List<UsersEntity> search(String keyword) throws SQLException;
    String getLastUserId() throws SQLException;
    UsersEntity findByUsername(String username) throws SQLException;

    UsersEntity findByUsernameAndRole(String username, String role) throws SQLException;


    UsersEntity authenticate(String username, String password, String role) throws SQLException;
}