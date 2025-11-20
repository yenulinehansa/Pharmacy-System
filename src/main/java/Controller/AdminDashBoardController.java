package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminDashBoardController {

    @FXML private Label lblTotDrugs;
    @FXML private Label lblTotSales;
    @FXML private Label lblOutOfStock;
    @FXML private Label lblExpiredItems;

    public void initialize() {

        loadDashboardData();
    }

    private void loadDashboardData() {
        // Load actual data from services
        lblTotDrugs.setText("125");
        lblTotSales.setText("1,245");
        lblOutOfStock.setText("8");
        lblExpiredItems.setText("3");
    }
}