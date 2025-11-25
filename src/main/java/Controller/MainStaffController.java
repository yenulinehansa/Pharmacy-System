package Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainStaffController implements Initializable {

    @FXML
    private Button btnBilling;

    @FXML
    private JFXButton btnDashboard;

    @FXML
    private JFXButton btnDrugs;

    @FXML
    private JFXButton btnPatients;

    @FXML
    private JFXButton btnSales;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private Label lblUser;

    @FXML
    void loadBilling() {
        loadFXML("/View/Billing.fxml");

    }

    @FXML
    void loadDashboard() {
        loadFXML("/View/StaffDashboard.fxml");

    }

    @FXML
    void loadDrugs() {
        loadFXML("/View/StaffDrugs.fxml");

    }

    @FXML
    void loadPatients() {
        loadFXML("/View/StaffPatients.fxml");

    }

    @FXML
    void loadSales() {
        loadFXML("/View/Sales.fxml");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDashboard();
    }
    private void loadFXML(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Clear previous content and add new content
            contentPane.getChildren().clear();
            contentPane.getChildren().add(root);

            // Set anchors to make content fill the entire area
            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load: " + e.getMessage());
        }
    }
    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
