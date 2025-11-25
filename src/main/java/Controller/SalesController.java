package Controller;

import Model.Dto.SalesDetails;
import Service.SalesService;
import Service.Impl.SalesServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SalesController implements Initializable {
    SalesService salesService=new SalesServiceImpl();
    ObservableList<SalesDetails> salesDetails = FXCollections.observableArrayList();

    @FXML
    private Button btnView;

    @FXML
    private TableColumn<?, ?> colDiscount;

    @FXML
    private TableColumn<?, ?> colDrugid;

    @FXML
    private TableColumn<?, ?> colOrderDate;

    @FXML
    private TableColumn<?, ?> colOrderid;

    @FXML
    private TableColumn<?, ?> colPatientid;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private DatePicker datepicker;

    @FXML
    private TableView<SalesDetails> tblSales;

    @FXML
    void onDateSelect(ActionEvent event) throws SQLException {
        LocalDate date = datepicker.getValue();
        if (date != null) {
            salesDetails = salesService.searchsales(date);
            tblSales.setItems(salesDetails);
        }
    }

    @FXML
    void onview(ActionEvent event) throws SQLException {
        loadsalesDetails();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colOrderid.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colPatientid.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colDrugid.setCellValueFactory(new PropertyValueFactory<>("drugid"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        try {
            loadsalesDetails();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            loadsalesDetails();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadsalesDetails() throws SQLException {
        salesDetails=salesService.getSalesDetails();
        tblSales.setItems(salesDetails);
    }


}
