package Service;

import Model.Dto.Drugs;
import Model.Dto.Suppliers;
import Model.Entity.DrugsEntity;
import Repository.DrugRepository;
import Repository.DrugRepositoryImpl;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DrugServiceImpl implements DrugService {
    DrugRepository drugRepository = new DrugRepositoryImpl();

    @Override
    public void addDrug(Drugs drugs) throws SQLException {
        DrugsEntity drugsEntity = new DrugsEntity(
                drugs.getId(),
                drugs.getName(),
                drugs.getBrand(),
                drugs.getUnitprice(),
                drugs.getStock_qty(),
                drugs.getExpDate()
        );
        drugRepository.save(drugsEntity);
    }

    @Override
    public void updateDrug(Drugs drugs) throws SQLException {
        DrugsEntity drugsEntity = new DrugsEntity(
                drugs.getId(),
                drugs.getName(),
                drugs.getBrand(),
                drugs.getUnitprice(),
                drugs.getStock_qty(),
                drugs.getExpDate()
        );
        drugRepository.update(drugsEntity);
    }

    @Override
    public void deleteDrug(String id) throws SQLException {
        drugRepository.delete(id);
    }

    @Override
    public Drugs getDrugById(String id) throws SQLException {
        DrugsEntity entity = drugRepository.findById(id);
        return convertToDto(entity);
    }

    @Override
    public List<Drugs> getAllDrugs() throws SQLException {
        List<DrugsEntity> entities = drugRepository.findAll();
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Drugs> searchDrugs(String keyword) throws SQLException {
        List<DrugsEntity> entities = drugRepository.search(keyword);
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public String generateNextDrugId() throws SQLException {
        String lastId = drugRepository.getLastDrugId();

        if (lastId == null) {
            // If no drugs exist, start with D001
            return "D001";
        }

        try {
            // Extract the numeric part and increment
            String prefix = "D";
            String numericPart = lastId.substring(1); // Remove the "D" prefix
            int number = Integer.parseInt(numericPart);
            number++; // Increment the number

            // Format back to 3-digit string with leading zeros
            return String.format("%s%03d", prefix, number);
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            // If there's any issue with the ID format, start from D001
            return "D001";
        }
    }

    @Override
    public List<String> getAllDrugIds() throws SQLException {
        List<String> ids=drugRepository.getIds();
        return ids;
    }

    @Override
    public int loaddrugscount() throws SQLException {
        int drugscount=drugRepository.loaddrugscount();
        return  drugscount;
    }

    @Override
    public Boolean quantityupdate(String drugid, int quantity) throws SQLException {
        return drugRepository.quantityupdate(drugid,quantity);
    }

    @Override
    public int getExpiredDrugsCount() throws SQLException {
        List<Drugs> drugs = getAllDrugs();
        int count = 0;

        LocalDate today = LocalDate.now();

        for (Drugs drug : drugs) {
            if (drug.getExpDate().isBefore(today)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int getOutOfStockCount() throws SQLException {
        List<Drugs> drugs = getAllDrugs();
        int count = 0;

        for (Drugs drug : drugs) {
            if (drug.getStock_qty() == 0) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void updateDrugStock(String drugId, int quantityToAdd) throws SQLException {
        drugRepository.updatedrugstock(drugId,quantityToAdd);

    }

    @Override
    public List<Drugs> getLowStockDrugs(int threshold) throws SQLException {
        List<Drugs> drugsList=drugRepository.getLowstockDrugs(threshold);
        return drugsList;
    }

    private Drugs convertToDto(DrugsEntity entity) {
        if (entity == null) return null;
        return new Drugs(
                entity.getId(),
                entity.getName(),
                entity.getBrand(),
                entity.getUnitprice(),
                entity.getStock_qty(),
                entity.getExpDate()
        );
    }

}