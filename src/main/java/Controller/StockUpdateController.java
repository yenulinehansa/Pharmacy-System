package Controller;

import Model.Dto.StockUpdate;
import Service.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class StockUpdateController implements Initializable {

    StockUpdateService stockUpdateService = new StockUpdateServiceImpl();
    ObservableList<StockUpdate> stockUpdateList = FXCollections.observableArrayList();
    SupplierService supplierService = new SupplierServiceImpl();
    DrugService drugService=new DrugServiceImpl();

    @FXML
    private Button btnRestock;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<StockUpdate, String> colDrugid;

    @FXML
    private TableColumn<StockUpdate, String> colSupplierid;

    @FXML
    private TableColumn<StockUpdate, Double> colbuyingprice;

    @FXML
    private TableColumn<StockUpdate, LocalDate> coldate;

    @FXML
    private TableColumn<StockUpdate, Integer> colqty;

    @FXML
    private ComboBox<String> comboDrugs;

    @FXML
    private ComboBox<String> comboSuppliers;

    @FXML
    private TableView<StockUpdate> tblStockupdate;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQty;

    public StockUpdateController() throws SQLException {
    }

    @FXML
    void onRestock(ActionEvent event) throws SQLException {
        try {
            if (validateInput()) {
                StockUpdate stockUpdate = new StockUpdate(
                        comboSuppliers.getValue(),
                        comboDrugs.getValue(),
                        Integer.parseInt(txtQty.getText()),
                        Double.parseDouble(txtPrice.getText()),
                        LocalDate.now()
                );

                stockUpdateService.addStockUpdate(stockUpdate);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Stock restocked successfully!");
                clearFields();
                loadAllStockUpdates();
                loadComboBoxData();
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid quantity and price.");
        }
    }

    @FXML
    void onUpdate(ActionEvent event) throws SQLException {
        StockUpdate selectedStockUpdate = tblStockupdate.getSelectionModel().getSelectedItem();
        if (selectedStockUpdate != null) {
            try {
                if (validateInputForUpdate()) {
                    StockUpdate stockUpdate = new StockUpdate(
                            comboSuppliers.getValue(),
                            comboDrugs.getValue(),
                            Integer.parseInt(txtQty.getText()),
                            Double.parseDouble(txtPrice.getText()),
                            selectedStockUpdate.getDate() // Keep original date for updates
                    );

                    stockUpdateService.updateStockUpdate(stockUpdate);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Stock update record modified successfully!");
                    clearFields();
                    loadAllStockUpdates();
                    loadComboBoxData();
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid quantity and price.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Selection Required", "Please select a stock record to update.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTableColumns();
        try {
            loadAllStockUpdates();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            loadComboBoxData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setupTableSelectionListener();
    }

    private void setupTableColumns() {
        colSupplierid.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colDrugid.setCellValueFactory(new PropertyValueFactory<>("drugId"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colbuyingprice.setCellValueFactory(new PropertyValueFactory<>("buying_price"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void loadAllStockUpdates() throws SQLException {
        stockUpdateList.clear();
        stockUpdateList=stockUpdateService.getAllStockUpdates();
        tblStockupdate.setItems(stockUpdateList);
    }

    private void loadComboBoxData() throws SQLException {
        // Load suppliers and drugs into comboboxes
        comboSuppliers.getItems().clear();

        List<String> supplierids= supplierService.getAllSupplierIds();
        comboSuppliers.getItems().addAll(supplierids);


        comboDrugs.getItems().clear();
        List<String> drugIds=drugService.getAllDrugIds();
        comboDrugs.getItems().addAll(drugIds);
    }

    private void setupTableSelectionListener() {
        tblStockupdate.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        populateFields(newValue);
                    }
                });
    }

    private void populateFields(StockUpdate stockUpdate) {
        comboSuppliers.setValue(stockUpdate.getSupplierId());
        comboDrugs.setValue(stockUpdate.getDrugId());
        txtQty.setText(String.valueOf(stockUpdate.getQuantity()));
        txtPrice.setText(String.valueOf(stockUpdate.getBuying_price()));

        // Disable comboboxes when updating to maintain data integrity
        comboSuppliers.setDisable(true);
        comboDrugs.setDisable(true);
    }

    private void clearFields() {
        comboSuppliers.setValue(null);
        comboDrugs.setValue(null);
        txtQty.clear();
        txtPrice.clear();
        tblStockupdate.getSelectionModel().clearSelection();

        // Enable comboboxes for new entries
        comboSuppliers.setDisable(false);
        comboDrugs.setDisable(false);
    }

    private boolean validateInput() {
        if (comboSuppliers.getValue() == null ||
                comboDrugs.getValue() == null ||
                txtQty.getText().trim().isEmpty() ||
                txtPrice.getText().trim().isEmpty()) {

            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill all fields.");
            return false;
        }

        try {
            int quantity = Integer.parseInt(txtQty.getText());
            double price = Double.parseDouble(txtPrice.getText());

            if (quantity <= 0) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Quantity must be greater than zero.");
                return false;
            }

            if (price <= 0) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Price must be greater than zero.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter valid numeric values for quantity and price.");
            return false;
        }

        return true;
    }

    private boolean validateInputForUpdate() {
        // For update, we don't validate comboboxes as they're disabled
        if (txtQty.getText().trim().isEmpty() ||
                txtPrice.getText().trim().isEmpty()) {

            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill all fields.");
            return false;
        }

        try {
            int quantity = Integer.parseInt(txtQty.getText());
            double price = Double.parseDouble(txtPrice.getText());

            if (quantity <= 0) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Quantity must be greater than zero.");
                return false;
            }

            if (price <= 0) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Price must be greater than zero.");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter valid numeric values for quantity and price.");
            return false;
        }

        return true;
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}