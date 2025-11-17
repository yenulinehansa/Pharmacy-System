package Service;

import Model.Dto.Drugs;

import java.sql.SQLException;
import java.util.List;

public interface DrugService {
    void addDrug(Drugs drugs) throws SQLException;
    void updateDrug(Drugs drugs) throws SQLException;
    void deleteDrug(String id) throws SQLException;
    Drugs getDrugById(String id) throws SQLException;
    List<Drugs> getAllDrugs() throws SQLException;
    List<Drugs> searchDrugs(String keyword) throws SQLException;
    String generateNextDrugId() throws SQLException;
}