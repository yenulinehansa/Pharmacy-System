package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashBoardController implements Initializable {

    @FXML private Label lblTotDrugs;
    @FXML private Label lblTotSales;
    @FXML private Label lblOutOfStock;
    @FXML private Label lblExpiredItems;



    private void loadDashboardData() {
        // Load actual data from services
        lblTotDrugs.setText("125");
        lblTotSales.setText("1,245");
        lblOutOfStock.setText("8");
        lblExpiredItems.setText("3");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDashboardData();
    }
}