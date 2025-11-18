package Service;

import Model.Dto.Suppliers;
import Model.Entity.SuppliersEntity;
import Repository.SupplierRepository;
import Repository.SupplierRepositoryImpl;

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