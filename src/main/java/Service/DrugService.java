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


    List<String> getAllDrugIds() throws SQLException;

    int loaddrugscount() throws SQLException;

    Boolean quantityupdate(String drugid, int quantity) throws SQLException;

    int getExpiredDrugsCount() throws SQLException;

    int getOutOfStockCount() throws SQLException;

    void updateDrugStock(String value, int i) throws SQLException;

    List<Drugs> getLowStockDrugs(int i) throws SQLException;
}