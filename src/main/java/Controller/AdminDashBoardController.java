package Controller;

import Service.DrugService;
import Service.DrugServiceImpl;
import Service.SalesService;
import Service.SalesServiceImpl;
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

    }

    private void loadstock() {
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