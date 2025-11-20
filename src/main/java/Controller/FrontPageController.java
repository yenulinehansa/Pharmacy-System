package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

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

    @FXML
    private void initialize() {
        // Set up login button action
        btnLogin.setOnAction(event -> handleLogin());

        // Set up forgot password action
        lblForgotPassword.setOnMouseClicked(event -> {
            showAlert("Forgot Password", "Please contact system administrator.");
        });
    }

    private void handleLogin() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        // Basic validation
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Validation Error", "Please enter both username and password.");
            return;
        }

        if (!ManagerRadio.isSelected() && !EmployeeRadio.isSelected()) {
            showAlert("Validation Error", "Please select user type.");
            return;
        }

        // Simple authentication (replace with your actual authentication)
        if (authenticate(username, password)) {
            try {
                // Load main admin layout
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MainAdminLayout.fxml"));
                Parent root = loader.load();

                // Set user information if needed
                MainAdminController mainController = loader.getController();
                mainController.setUser(username);

                // Switch to main admin scene
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("New Pharmacy - Admin Dashboard");
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to load application: " + e.getMessage());
            }
        } else {
            showAlert("Login Failed", "Invalid username or password.");
        }
    }

    private boolean authenticate(String username, String password) {
        // Replace with your actual authentication logic
        // This is a simple example
        return "admin".equals(username) && "admin".equals(password);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}