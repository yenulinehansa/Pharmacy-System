package Service;

import Model.Dto.Suppliers;
import java.sql.SQLException;
import java.util.List;

public interface SupplierService {
    void addSupplier(Suppliers suppliers) throws SQLException;
    void updateSupplier(Suppliers suppliers) throws SQLException;
    void deleteSupplier(int id) throws SQLException;
    Suppliers getSupplierById(int id) throws SQLException;
    List<Suppliers> getAllSuppliers() throws SQLException;
    List<Suppliers> searchSuppliers(String keyword) throws SQLException;
    String generateNextSupplierId() throws SQLException;

    List<String> getAllSupplierIds() throws SQLException;
}