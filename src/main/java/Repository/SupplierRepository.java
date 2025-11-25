package Repository;

import Model.Dto.Suppliers;
import Model.Entity.SuppliersEntity;
import java.sql.SQLException;
import java.util.List;

public interface SupplierRepository {
    void save(SuppliersEntity suppliersEntity) throws SQLException;
    void update(SuppliersEntity suppliersEntity) throws SQLException;
    void delete(int id) throws SQLException;
    SuppliersEntity findById(int id) throws SQLException;
    List<SuppliersEntity> findAll() throws SQLException;
    List<SuppliersEntity> search(String keyword) throws SQLException;
    String getLastSupplierId() throws SQLException;

    List<String> getSuppliersIds() throws SQLException;

    List<Suppliers> getsupplierfordrug(String drugId) throws SQLException;

    void adddrugsupplierrelationship(String drugId, String supplierId) throws SQLException;

    void createlowstockalert(String supplierId, String drugId, int currentStock) throws SQLException;
}