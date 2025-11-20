package Controller;

import Service.PatientService;
import Service.PatientServiceImpl;
import Model.Dto.Patients;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PatientViewController implements Initializable {

    @FXML
    private Button btndelete;

    @FXML
    private Button btnupdate;

    @FXML
    private TableColumn<Patients, String> colName;

    @FXML
    private TableColumn<Patients, String> colTelNo;

    @FXML
    private TableColumn<Patients, String> colid;

    @FXML
    private TableView<Patients> tblPatients;

    @FXML
    private TextField txtTelNo;

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtname;

    private final PatientService patientService = new PatientServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableColumns();
        try {
            loadAllPatients();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setupTableSelectionListener();
    }

    private void setupTableColumns() {
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTelNo.setCellValueFactory(new PropertyValueFactory<>("telNo"));
    }

    private void loadAllPatients() throws SQLException {
        tblPatients.getItems().clear();
        tblPatients.getItems().addAll(patientService.getAllPatients());
    }

    private void setupTableSelectionListener() {
        tblPatients.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        setPatientDataToFields(newValue);
                    }
                }
        );
    }

    private void setPatientDataToFields(Patients patient) {
        txtid.setText(patient.getId());
        txtname.setText(patient.getName());
        txtTelNo.setText(patient.getTelNo());
    }

    @FXML
    void ondelete(ActionEvent event) throws SQLException {
        Patients selectedPatient = tblPatients.getSelectionModel().getSelectedItem();

        if (selectedPatient == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a patient to delete.");
            return;
        }

        boolean deleted = patientService.deletePatient(selectedPatient.getId());
        if (deleted) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Patient deleted successfully!");
            clearFields();
            loadAllPatients();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete patient.");
        }
    }

    @FXML
    void onupdate(ActionEvent event) throws SQLException {
        String id = txtid.getText();
        String name = txtname.getText();
        String telNo = txtTelNo.getText();

        if (id.isEmpty() || name.isEmpty() || telNo.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill all fields.");
            return;
        }

        Patients patient = new Patients(id, name, telNo);
        boolean updated = patientService.updatePatient(patient);

        if (updated) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Patient updated successfully!");
            clearFields();
            loadAllPatients();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update patient.");
        }
    }

    private void clearFields() {
        txtid.clear();
        txtname.clear();
        txtTelNo.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}