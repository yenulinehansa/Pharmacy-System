package Repository;

import Model.Entity.PatientsEntity;

import java.sql.SQLException;
import java.util.List;

public interface PatientRepository {
    List<PatientsEntity> getAllPatients() throws SQLException;
    boolean updatePatient(PatientsEntity patient) throws SQLException;
    boolean deletePatient(String patientId) throws SQLException;
    PatientsEntity findPatientById(String patientId) throws SQLException;
}