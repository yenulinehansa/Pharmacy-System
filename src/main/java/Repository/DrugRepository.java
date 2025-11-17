package Repository;

import Model.Entity.DrugsEntity;

import java.sql.SQLException;
import java.util.List;

public interface DrugRepository {
    void save(DrugsEntity drugsEntity) throws SQLException;
    void update(DrugsEntity drugsEntity) throws SQLException;
    void delete(String id) throws SQLException;
    DrugsEntity findById(String id) throws SQLException;
    List<DrugsEntity> findAll() throws SQLException;
    List<DrugsEntity> search(String keyword) throws SQLException;
    String getLastDrugId() throws SQLException;
}