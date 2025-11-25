package Controller;

import Service.DrugService;
import Service.Impl.DrugServiceImpl;
import Service.SalesService;
import Service.Impl.SalesServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminDashBoardController implements Initializable {
    DrugService drugService=new DrugServiceImpl();
    SalesService salesService=new SalesServiceImpl();

    @FXML private Label lblTotDrugs;
    @FXML private Label lblTotSales;
    @FXML private Label lblOutOfStock;
    @FXML private Label lblExpiredItems;



    private void loadDashboardData() throws SQLException {
//        // Load actual data from services
//        lblTotDrugs.setText("125");
//        lblTotSales.setText("1,245");
//        lblOutOfStock.setText("8");
//        lblExpiredItems.setText("3");
        loaddrugs();
        loadsales();
        loadstock();
        loadexpitems();
    }

    private void loadexpitems() {
        try {
            int expiredCount = drugService.getExpiredDrugsCount();
            lblExpiredItems.setText(String.valueOf(expiredCount));
        } catch (SQLException e) {
            lblExpiredItems.setText("0");
            e.printStackTrace();
        }

    }

    private void loadstock() {
        try {
            int outOfStockCount = drugService.getOutOfStockCount();
            lblOutOfStock.setText(String.valueOf(outOfStockCount));
        } catch (SQLException e) {
            lblOutOfStock.setText("0");
            e.printStackTrace();
        }
    }

    private void loadsales() throws SQLException {
        int salescount=salesService.getsalescount();
        lblTotSales.setText(String.valueOf(salescount));
    }

    private void loaddrugs() throws SQLException {
        int drugscount=drugService.loaddrugscount();
        lblTotDrugs.setText(String.valueOf((drugscount)));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadDashboardData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}