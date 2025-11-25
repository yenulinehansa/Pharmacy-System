package Controller;

import Service.UserService;
import Service.Impl.UserServiceImpl;
import Model.Dto.Users;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class FrontPageController {

    @FXML
    private JFXRadioButton EmployeeRadio;

    @FXML
    private JFXRadioButton ManagerRadio;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private Label lblForgotPassword;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUsername;

    private ToggleGroup roleToggleGroup;
    private UserService userService;

    @FXML
    public void initialize() {
        userService = new UserServiceImpl();
        setupRoleToggleGroup();
        setupEventHandlers();


        ManagerRadio.setText("I'm Admin");
        EmployeeRadio.setText("I'm Staff");
    }

    private void setupRoleToggleGroup() {
        roleToggleGroup = new ToggleGroup();
        EmployeeRadio.setToggleGroup(roleToggleGroup);
        ManagerRadio.setToggleGroup(roleToggleGroup);


        ManagerRadio.setSelected(true);
    }

    private void setupEventHandlers() {
        btnLogin.setOnAction(event -> handleLogin());

        lblForgotPassword.setOnMouseClicked(event -> {
            showAlert("Info", "Please contact administrator to reset your password.");
        });
    }

    @FXML
    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();


        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both username and password.");
            return;
        }

        if (roleToggleGroup.getSelectedToggle() == null) {
            showAlert("Error", "Please select a role.");
            return;
        }


        String role = ManagerRadio.isSelected() ? "admin" : "staff";

        try {

            boolean userExists = userService.userExistsWithRole(username, role);

            if (!userExists) {
                showAlert("Error", "No " + role + " found with this username.");
                return;
            }


            Users authenticatedUser = userService.authenticateUser(username, password, role);

            if (authenticatedUser != null) {

                showAlert("Success", "Welcome " + authenticatedUser.getName() + "!");
                openMainWindow(authenticatedUser);
            } else {
                showAlert("Error", "Invalid password. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Database connection error: " + e.getMessage());
        }
    }

    private void openMainWindow(Users user) {
        try {
            Stage currentStage = (Stage) btnLogin.getScene().getWindow();
            Stage mainStage = new Stage();
            Parent root;

            if ("Admin".equals(user.getRole())) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainAdminLayout.fxml"));
                root = loader.load();


                MainAdminController adminController = loader.getController();
                adminController.setUser(user);

                mainStage.setTitle("Pharmacy Management System - Admin");
            } else {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainStaffLayout.fxml"));
                root = loader.load();


                MainStaffController staffController = loader.getController();
                staffController.setUser(user);

                mainStage.setTitle("Pharmacy Management System - Staff");
            }

            Scene scene = new Scene(root);
            mainStage.setScene(scene);
            mainStage.setMaximized(true);
            mainStage.show();


            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load main window: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}