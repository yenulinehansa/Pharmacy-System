package Controller;

import Model.Dto.Patients;
import Service.PatientService;
import Service.PatientServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class StaffPatientController implements Initializable {
    PatientService patientService=new PatientServiceImpl();
    ObservableList<Patients> patientslist= FXCollections.observableArrayList();

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TableColumn<?, ?> coltelno;

    @FXML
    private TableView<Patients> tblpatients;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        coltelno.setCellValueFactory(new PropertyValueFactory<>("telNo"));

        try {
           patientslist.clear();
           patientslist.addAll(patientService.getAllPatients());
           tblpatients.setItems(patientslist);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
