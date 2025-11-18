package Controller;

import Model.Dto.Suppliers;
import Service.SupplierService;
import Service.SupplierServiceImpl;
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

public class AdminSupplierAddingController implements Initializable {

    SupplierService supplierService = new SupplierServiceImpl();
    ObservableList<Suppliers> suppliersList = FXCollections.observableArrayList();
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
    private TableColumn<Suppliers, String> colCompany;
    @FXML
    private TableColumn<Suppliers, String> colEmail;
    @FXML
    private TableColumn<Suppliers, Integer> colId;
    @FXML
    private TableColumn<Suppliers, String> colName;
    @FXML
    private TableColumn<Suppliers, String> colRegDate;
    @FXML
    private TableColumn<Suppliers, String> colTelNo;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label lblUser;
    @FXML
    private TableView<Suppliers> tblSuppliers;
    @FXML
    private TextField txtCompany;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtSearch;
    @FXML
    private TextField txtTelNo;

    @FXML
    void OnAdd(ActionEvent event) throws SQLException {
        try {
            if (validateInput()) {
                Suppliers supplier = new Suppliers(
                        txtId.getText(),
                        txtName.getText(),
                        txtTelNo.getText(),
                        txtEmail.getText(),
                        txtCompany.getText(),
                        datePicker.getValue()
                );
                supplierService.addSupplier(supplier);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Supplier added successfully!");
                clearFields();
                loadAllSuppliers();
                generateNextId();
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid ID.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add supplier: " + e.getMessage());
        }
    }

    @FXML
    void OnDelete(ActionEvent event) {
        Suppliers selectedSupplier = tblSuppliers.getSelectionModel().getSelectedItem();
        if (selectedSupplier != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Delete Supplier");
            confirmation.setContentText("Are you sure you want to delete supplier: " + selectedSupplier.getName() + "?");

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    supplierService.deleteSupplier(Integer.parseInt(selectedSupplier.getId()));
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Supplier deleted successfully!");
                    clearFields();
                    loadAllSuppliers();
                    generateNextId();
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to delete supplier: " + e.getMessage());
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Selection Required", "Please select a supplier to delete.");
        }
    }

    @FXML
    void OnIdSelected(ActionEvent event) {
        // This can be used for auto-completion or validation
    }

    @FXML
    void OnSearch(ActionEvent event) {
        String keyword = txtSearch.getText().trim();
        if (!keyword.isEmpty()) {
            try {
                suppliersList.clear();
                suppliersList.addAll(supplierService.searchSuppliers(keyword));
                tblSuppliers.setItems(suppliersList);
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Search Error", "Failed to search suppliers: " + e.getMessage());
            }
        } else {
            loadAllSuppliers();
        }
    }

    @FXML
    void OnUpdate(ActionEvent event) {
        try {
            if (validateInputForUpdate()) {
                Suppliers supplier = new Suppliers(
                        txtId.getText(),
                        txtName.getText(),
                        txtTelNo.getText(),
                        txtEmail.getText(),
                        txtCompany.getText(),
                        datePicker.getValue()
                );
                supplierService.updateSupplier(supplier);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Supplier updated successfully!");
                clearFields();
                loadAllSuppliers();
                generateNextId();
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid ID.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update supplier: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTableColumns();
        loadAllSuppliers();
        setupTableSelectionListener();
        generateNextId();
    }

    private void setupTableColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTelNo.setCellValueFactory(new PropertyValueFactory<>("telNo"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colCompany.setCellValueFactory(new PropertyValueFactory<>("company"));
        colRegDate.setCellValueFactory(new PropertyValueFactory<>("regDate"));
    }

    private void loadAllSuppliers() {
        try {
            suppliersList.clear();
            suppliersList.addAll(supplierService.getAllSuppliers());
            tblSuppliers.setItems(suppliersList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load suppliers: " + e.getMessage());
        }
    }

    private void setupTableSelectionListener() {
        tblSuppliers.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        populateFields(newValue);
                    }
                });
    }

    private void populateFields(Suppliers supplier) {
        txtId.setText(String.valueOf(supplier.getId()));
        txtName.setText(supplier.getName());
        txtTelNo.setText(supplier.getTelNo());
        txtEmail.setText(supplier.getEmail());
        txtCompany.setText(supplier.getCompany());
        datePicker.setValue(supplier.getRegDate());

        // Disable ID field when updating (to prevent changing ID)
        txtId.setDisable(true);
    }

    private void clearFields() {
        txtId.clear();
        txtName.clear();
        txtTelNo.clear();
        txtEmail.clear();
        txtCompany.clear();
        datePicker.setValue(null);
        txtSearch.clear();
        tblSuppliers.getSelectionModel().clearSelection();

        // Enable ID field for new entries
        txtId.setDisable(false);

        // Generate new ID after clearing fields
        generateNextId();
    }

    private void generateNextId() {
        try {
            String nextId = supplierService.generateNextSupplierId();
            txtId.setText(String.valueOf(nextId));
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "ID Generation Error", "Failed to generate next supplier ID: " + e.getMessage());
            txtId.setText("1"); // Fallback ID
        }
    }

    private boolean validateInput() {
        if (txtId.getText().trim().isEmpty() ||
                txtName.getText().trim().isEmpty() ||
                txtTelNo.getText().trim().isEmpty() ||
                txtEmail.getText().trim().isEmpty() ||
                txtCompany.getText().trim().isEmpty() ||
                datePicker.getValue() == null) {

            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill all fields.");
            return false;
        }

        // Email validation (basic)
        if (!txtEmail.getText().contains("@")) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter a valid email address.");
            return false;
        }

        return true;
    }

    private boolean validateInputForUpdate() {
        // For update, we don't validate ID as it's disabled
        if (txtName.getText().trim().isEmpty() ||
                txtTelNo.getText().trim().isEmpty() ||
                txtEmail.getText().trim().isEmpty() ||
                txtCompany.getText().trim().isEmpty() ||
                datePicker.getValue() == null) {

            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill all fields.");
            return false;
        }

        // Email validation (basic)
        if (!txtEmail.getText().contains("@")) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter a valid email address.");
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
        loadAllSuppliers();
    }
}