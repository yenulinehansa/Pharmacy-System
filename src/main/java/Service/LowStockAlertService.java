package Service;


import Model.Dto.Drugs;
import Model.Dto.Suppliers;
import Service.Impl.DrugServiceImpl;
import Service.Impl.SupplierServiceImpl;


import java.sql.SQLException;
import java.util.List;

public class LowStockAlertService {
    private DrugService drugService = new DrugServiceImpl();
    private SupplierService supplierService = new SupplierServiceImpl();

    public void checkLowStockAndNotifySuppliers() throws SQLException {
        List<Drugs> lowStockDrugs = drugService.getLowStockDrugs(10);

        for (Drugs drug : lowStockDrugs) {
            List<Suppliers> relevantSuppliers = supplierService.getSuppliersForDrug(drug.getId());

            for (Suppliers supplier : relevantSuppliers) {
                sendLowStockAlert(supplier, drug);
            }
        }
    }

    private void sendLowStockAlert(Suppliers supplier, Drugs drug) throws SQLException {
        createSystemAlert(supplier, drug);
    }



    private void createSystemAlert(Suppliers supplier, Drugs drug) throws SQLException {
        supplierService.createLowStockAlert(supplier.getId(), drug.getId(), drug.getStock_qty());
    }
}