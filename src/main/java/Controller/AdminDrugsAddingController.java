package Controller;

import Model.Dto.Drugs;
import Service.DrugService;
import Service.Impl.DrugServiceImpl;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminDrugsAddingController implements Initializable {

    DrugService drugService = new DrugServiceImpl();
    ObservableList<Drugs> drugsList = FXCollections.observableArrayList();
    @FXML
    private Button btnView;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnBilling;
    @FXML
    private JFXButton btnDashboard;
    @FXML
    private Button btnDelete;
    @FXML
    private JFXButton btnDrugs;
    @FXML
    private JFXButton btnPatients;
    @FXML
    private JFXButton btnReports;
    @FXML
    private JFXButton btnSales;
    @FXML
    private JFXButton btnStockUpdate;
    @FXML
    private JFXButton btnSuppliers;
    @FXML
    private Button btnUpdate;
    @FXML
    private JFXButton btnUsers;
    @FXML
    private TableColumn<Drugs, String> colBrand;
    @FXML
    private TableColumn<Drugs, String> colExpDate;
    @FXML
    private TableColumn<Drugs, String> colId;
    @FXML
    private TableColumn<Drugs, String> colName;
    @FXML
    private TableColumn<Drugs, Integer> colStockQty;
    @FXML
    private TableColumn<Drugs, Double> colUnitPrice;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label lblUser;
    @FXML
    private TableView<Drugs> tblDrugs;
    @FXML
    private TextField txtBrand;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtSearch;
    @FXML
    private TextField txtStockQty;
    @FXML
    private TextField txtUnitPrice;

    @FXML
    void OnAdd(ActionEvent event) throws SQLException {
        try {
            if (validateInput()) {
                Drugs drugs = new Drugs(
                        txtId.getText(),
                        txtName.getText(),
                        txtBrand.getText(),
                        Double.parseDouble(txtUnitPrice.getText()),
                        Integer.parseInt(txtStockQty.getText()),
                        datePicker.getValue()
                );
                drugService.addDrug(drugs);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Drug added successfully!");
                clearFields();
                loadAllDrugs();
                generateNextId(); // Generate next ID after adding
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid numbers for unit price and stock quantity.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add drug: " + e.getMessage());
        }
    }

    @FXML
    void OnDelete(ActionEvent event) {
        Drugs selectedDrug = tblDrugs.getSelectionModel().getSelectedItem();
        if (selectedDrug != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Delete Drug");
            confirmation.setContentText("Are you sure you want to delete drug: " + selectedDrug.getName() + "?");

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    drugService.deleteDrug(selectedDrug.getId());
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Drug deleted successfully!");
                    clearFields();
                    loadAllDrugs();
                    generateNextId(); // Generate next ID after deletion
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to delete drug: " + e.getMessage());
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Selection Required", "Please select a drug to delete.");
        }
    }

    @FXML
    void OnIdSelected(ActionEvent event) {

    }

    @FXML
    void OnSearch(ActionEvent event) {
        String keyword = txtSearch.getText().trim();
        if (!keyword.isEmpty()) {
            try {
                drugsList.clear();
                drugsList.addAll(drugService.searchDrugs(keyword));
                tblDrugs.setItems(drugsList);
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Search Error", "Failed to search drugs: " + e.getMessage());
            }
        } else {
            loadAllDrugs();
        }
    }

    @FXML
    void OnUpdate(ActionEvent event) {
        try {
            if (validateInputForUpdate()) {
                Drugs drugs = new Drugs(
                        txtId.getText(),
                        txtName.getText(),
                        txtBrand.getText(),
                        Double.parseDouble(txtUnitPrice.getText()),
                        Integer.parseInt(txtStockQty.getText()),
                        datePicker.getValue()
                );
                drugService.updateDrug(drugs);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Drug updated successfully!");
                clearFields();
                loadAllDrugs();
                generateNextId(); // Generate next ID after update
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid numbers for unit price and stock quantity.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update drug: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTableColumns();
        loadAllDrugs();
        setupTableSelectionListener();
        generateNextId(); // Generate initial ID when form loads
    }

    private void setupTableColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitprice"));
        colStockQty.setCellValueFactory(new PropertyValueFactory<>("stock_qty"));
        colExpDate.setCellValueFactory(new PropertyValueFactory<>("expDate"));
    }

    private void loadAllDrugs() {
        try {
            drugsList.clear();
            drugsList.addAll(drugService.getAllDrugs());
            tblDrugs.setItems(drugsList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load drugs: " + e.getMessage());
        }
    }

    private void setupTableSelectionListener() {
        tblDrugs.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        populateFields(newValue);
                    }
                });
    }

    private void populateFields(Drugs drug) {
        txtId.setText(drug.getId());
        txtName.setText(drug.getName());
        txtBrand.setText(drug.getBrand());
        txtUnitPrice.setText(String.valueOf(drug.getUnitprice()));
        txtStockQty.setText(String.valueOf(drug.getStock_qty()));
        datePicker.setValue(drug.getExpDate());

        // Disable ID field when updating (to prevent changing ID)
        txtId.setDisable(true);
    }

    private void clearFields() {
        txtId.clear();
        txtName.clear();
        txtBrand.clear();
        txtUnitPrice.clear();
        txtStockQty.clear();
        datePicker.setValue(null);
        txtSearch.clear();
        tblDrugs.getSelectionModel().clearSelection();

        // Enable ID field for new entries
        txtId.setDisable(false);

        // Generate new ID after clearing fields
        generateNextId();
    }

    private void generateNextId() {
        try {
            String nextId = drugService.generateNextDrugId();
            txtId.setText(nextId);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "ID Generation Error", "Failed to generate next drug ID: " + e.getMessage());
            txtId.setText("D001"); // Fallback ID
        }
    }

    private boolean validateInput() {
        if (txtId.getText().trim().isEmpty() ||
                txtName.getText().trim().isEmpty() ||
                txtBrand.getText().trim().isEmpty() ||
                txtUnitPrice.getText().trim().isEmpty() ||
                txtStockQty.getText().trim().isEmpty() ||
                datePicker.getValue() == null) {

            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill all fields.");
            return false;
        }

        try {
            Double.parseDouble(txtUnitPrice.getText());
            Integer.parseInt(txtStockQty.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid numbers for unit price and stock quantity.");
            return false;
        }

        return true;
    }

    private boolean validateInputForUpdate() {
        // For update, we don't validate ID as it's disabled
        if (txtName.getText().trim().isEmpty() ||
                txtBrand.getText().trim().isEmpty() ||
                txtUnitPrice.getText().trim().isEmpty() ||
                txtStockQty.getText().trim().isEmpty() ||
                datePicker.getValue() == null) {

            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill all fields.");
            return false;
        }

        try {
            Double.parseDouble(txtUnitPrice.getText());
            Integer.parseInt(txtStockQty.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid numbers for unit price and stock quantity.");
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

    public void Onview(ActionEvent actionEvent) {
        loadAllDrugs();

    }
}