package Controller;

import Model.Dto.Users;
import Service.UserService;
import Service.Impl.UserServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserAddingController implements Initializable {

    UserService userService = new UserServiceImpl();
    ObservableList<Users> usersList = FXCollections.observableArrayList();

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnView;
    @FXML
    private ComboBox<String> cmbRole;
    @FXML
    private TableColumn<Users, String> colEmail;
    @FXML
    private TableColumn<Users, String> colId;
    @FXML
    private TableColumn<Users, String> colName;
    @FXML
    private TableColumn<Users, String> colPassword;
    @FXML
    private TableColumn<Users, String> colRegDate;
    @FXML
    private TableColumn<Users, String> colRole;
    @FXML
    private TableColumn<Users, Double> colSalary;
    @FXML
    private TableColumn<Users, String> colTelno;
    @FXML
    private TableColumn<Users, String> colUsername;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableView<Users> tblUsers;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtSalary;
    @FXML
    private TextField txtSearch;
    @FXML
    private TextField txtTelNo;
    @FXML
    private TextField txtUsername;

    @FXML
    void OnAdd(ActionEvent event) throws SQLException {
        try {
            if (validateInput()) {
                Users users = new Users(
                        txtId.getText(),
                        txtName.getText(),
                        txtTelNo.getText(),
                        txtEmail.getText(),
                        cmbRole.getValue(),
                        txtUsername.getText(),
                        txtPassword.getText(),
                        Double.parseDouble(txtSalary.getText()),
                        datePicker.getValue()
                );
                userService.addUser(users);
                showAlert(Alert.AlertType.INFORMATION, "Success", "User added successfully!");
                clearFields();
                loadAllUsers();
                generateNextId();
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid number for salary.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add user: " + e.getMessage());
        }
    }

    @FXML
    void OnDelete(ActionEvent event) {
        Users selectedUser = tblUsers.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Delete User");
            confirmation.setContentText("Are you sure you want to delete user: " + selectedUser.getName() + "?");

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    userService.deleteUser(selectedUser.getId());
                    showAlert(Alert.AlertType.INFORMATION, "Success", "User deleted successfully!");
                    clearFields();
                    loadAllUsers();
                    generateNextId();
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to delete user: " + e.getMessage());
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Selection Required", "Please select a user to delete.");
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
                usersList.clear();
                usersList.addAll(userService.searchUsers(keyword));
                tblUsers.setItems(usersList);
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Search Error", "Failed to search users: " + e.getMessage());
            }
        } else {
            loadAllUsers();
        }
    }

    @FXML
    void OnUpdate(ActionEvent event) {
        try {
            if (validateInputForUpdate()) {
                Users users = new Users(
                        txtId.getText(),
                        txtName.getText(),
                        txtTelNo.getText(),
                        txtEmail.getText(),
                        cmbRole.getValue(),
                        txtUsername.getText(),
                        txtPassword.getText(),
                        Double.parseDouble(txtSalary.getText()),
                        datePicker.getValue()
                );
                userService.updateUser(users);
                showAlert(Alert.AlertType.INFORMATION, "Success", "User updated successfully!");
                clearFields();
                loadAllUsers();
                generateNextId();
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid number for salary.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update user: " + e.getMessage());
        }
    }

    @FXML
    void Onview(ActionEvent event) {
        loadAllUsers();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTableColumns();
        setupRoleComboBox();
        loadAllUsers();
        setupTableSelectionListener();
        generateNextId();
    }

    private void setupTableColumns() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTelno.setCellValueFactory(new PropertyValueFactory<>("telNo"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colRegDate.setCellValueFactory(new PropertyValueFactory<>("regDate"));
    }

    private void setupRoleComboBox() {
        cmbRole.getItems().addAll("Admin", "Staff");
    }

    private void loadAllUsers() {
        try {
            usersList.clear();
            usersList.addAll(userService.getAllUsers());
            tblUsers.setItems(usersList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load users: " + e.getMessage());
        }
    }

    private void setupTableSelectionListener() {
        tblUsers.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        populateFields(newValue);
                    }
                });
    }

    private void populateFields(Users user) {
        txtId.setText(user.getId());
        txtName.setText(user.getName());
        txtTelNo.setText(user.getTelNo());
        txtEmail.setText(user.getEmail());
        cmbRole.setValue(user.getRole());
        txtUsername.setText(user.getUsername());
        txtPassword.setText(user.getPassword());
        txtSalary.setText(String.valueOf(user.getSalary()));
        datePicker.setValue(user.getRegDate());

        // Disable ID field when updating
        txtId.setDisable(true);
    }

    private void clearFields() {
        txtId.clear();
        txtName.clear();
        txtTelNo.clear();
        txtEmail.clear();
        cmbRole.setValue(null);
        txtUsername.clear();
        txtPassword.clear();
        txtSalary.clear();
        datePicker.setValue(null);
        txtSearch.clear();
        tblUsers.getSelectionModel().clearSelection();

        // Enable ID field for new entries
        txtId.setDisable(false);

        // Generate new ID after clearing fields
        generateNextId();
    }

    private void generateNextId() {
        try {
            String nextId = userService.generateNextUserId();
            txtId.setText(nextId);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "ID Generation Error", "Failed to generate next user ID: " + e.getMessage());
            txtId.setText("U001"); // Fallback ID
        }
    }

    private boolean validateInput() {
        if (txtId.getText().trim().isEmpty() ||
                txtName.getText().trim().isEmpty() ||
                txtTelNo.getText().trim().isEmpty() ||
                txtEmail.getText().trim().isEmpty() ||
                cmbRole.getValue() == null ||
                txtUsername.getText().trim().isEmpty() ||
                txtPassword.getText().trim().isEmpty() ||
                txtSalary.getText().trim().isEmpty() ||
                datePicker.getValue() == null) {

            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill all fields.");
            return false;
        }

        try {
            Double.parseDouble(txtSalary.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid number for salary.");
            return false;
        }

        return true;
    }

    private boolean validateInputForUpdate() {
        // For update, we don't validate ID as it's disabled
        if (txtName.getText().trim().isEmpty() ||
                txtTelNo.getText().trim().isEmpty() ||
                txtEmail.getText().trim().isEmpty() ||
                cmbRole.getValue() == null ||
                txtUsername.getText().trim().isEmpty() ||
                txtPassword.getText().trim().isEmpty() ||
                txtSalary.getText().trim().isEmpty() ||
                datePicker.getValue() == null) {

            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill all fields.");
            return false;
        }

        try {
            Double.parseDouble(txtSalary.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid number for salary.");
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