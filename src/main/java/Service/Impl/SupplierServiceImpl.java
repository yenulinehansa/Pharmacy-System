package Service.Impl;

import Model.Dto.Suppliers;
import Model.Entity.SuppliersEntity;
import Repository.SupplierRepository;
import Repository.Impl.SupplierRepositoryImpl;
import Service.SupplierService;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class SupplierServiceImpl implements SupplierService {
    SupplierRepository supplierRepository = new SupplierRepositoryImpl();

    @Override
    public void addSupplier(Suppliers suppliers) throws SQLException {
        SuppliersEntity suppliersEntity = new SuppliersEntity(
                suppliers.getId(),
                suppliers.getName(),
                suppliers.getTelNo(),
                suppliers.getEmail(),
                suppliers.getCompany(),
                suppliers.getRegDate()
        );
        supplierRepository.save(suppliersEntity);
    }

    @Override
    public void updateSupplier(Suppliers suppliers) throws SQLException {
        SuppliersEntity suppliersEntity = new SuppliersEntity(
                suppliers.getId(),
                suppliers.getName(),
                suppliers.getTelNo(),
                suppliers.getEmail(),
                suppliers.getCompany(),
                suppliers.getRegDate()
        );
        supplierRepository.update(suppliersEntity);
    }

    @Override
    public void deleteSupplier(int id) throws SQLException {
        supplierRepository.delete(id);
    }

    @Override
    public Suppliers getSupplierById(int id) throws SQLException {
        SuppliersEntity entity = supplierRepository.findById(id);
        return convertToDto(entity);
    }

    @Override
    public List<Suppliers> getAllSuppliers() throws SQLException {
        List<SuppliersEntity> entities = supplierRepository.findAll();
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Suppliers> searchSuppliers(String keyword) throws SQLException {
        List<SuppliersEntity> entities = supplierRepository.search(keyword);
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public String generateNextSupplierId() throws SQLException {
        String lastId = supplierRepository.getLastSupplierId();
//        return(lastId + 1); // Auto-increment for next ID
        int num=Integer.parseInt(lastId.substring(1))+1;
        String newId = String.format("S%03d", num);
        return newId;
    }

    @Override
    public List<String> getAllSupplierIds() throws SQLException {
        List<String> ids=supplierRepository.getSuppliersIds();
        return ids;
    }

    @Override
    public List<Suppliers> getSuppliersForDrug(String drugId) throws SQLException {
        List<Suppliers> suppliers=supplierRepository.getsupplierfordrug(drugId);
        return  suppliers;

    }

    @Override
    public void addDrugSupplierRelationship(String drugId, String supplierId) throws SQLException {
        supplierRepository.adddrugsupplierrelationship(drugId,supplierId);

    }

    @Override
    public void createLowStockAlert(String supplierId, String drugId, int currentStock) throws SQLException {
        supplierRepository.createlowstockalert(supplierId,drugId,currentStock);

    }
    private Suppliers convertToDto(SuppliersEntity entity) {
        if (entity == null) return null;
        return new Suppliers(
                entity.getId(),
                entity.getName(),
                entity.getTelNo(),
                entity.getEmail(),
                entity.getCompany(),
                entity.getRegDate()
        );
    }
}