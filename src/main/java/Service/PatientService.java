package Service;

import Model.Dto.Patients;

import java.sql.SQLException;
import java.util.List;

public interface PatientService {
    List<Patients> getAllPatients() throws SQLException;
    boolean updatePatient(Patients patient) throws SQLException;
    boolean deletePatient(String patientId) throws SQLException;
    Patients findPatientById(String patientId) throws SQLException;
}