package Controller;

import Model.Dto.Users;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainStaffController implements Initializable {

    @FXML
    private Button btnlogin;

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


    private Users currentUser;

    public void setUser(Users user) {
        this.currentUser = user;
        if (user != null && lblUser != null) {
            lblUser.setText(" Cashier " + user.getName());
        }
    }

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


            contentPane.getChildren().clear();
            contentPane.getChildren().add(root);


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
    public void onBacktologin() {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/View/FrontPage.fxml")); // Example path

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();


            Stage currentStage = (Stage) btnlogin.getScene().getWindow();
            currentStage.close();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load login page: " + e.getMessage());
        }
    }
}
