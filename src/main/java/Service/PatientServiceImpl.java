package Service;

import Repository.PatientRepository;
import Repository.PatientRepositoryImpl;
import Model.Dto.Patients;
import Model.Entity.PatientsEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository = new PatientRepositoryImpl();

    @Override
    public List<Patients> getAllPatients() throws SQLException {
        List<PatientsEntity> entities = patientRepository.getAllPatients();
        return entities.stream()
                .map(entity -> new Patients(
                        entity.getId(),
                        entity.getName(),
                        entity.getTelNo()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public boolean updatePatient(Patients patient) throws SQLException {
        PatientsEntity entity = new PatientsEntity(
                patient.getId(),
                patient.getName(),
                patient.getTelNo()
        );
        return patientRepository.updatePatient(entity);
    }

    @Override
    public boolean deletePatient(String patientId) throws SQLException {
        return patientRepository.deletePatient(patientId);
    }

    @Override
    public Patients findPatientById(String patientId) throws SQLException {
        PatientsEntity entity = patientRepository.findPatientById(patientId);
        if (entity != null) {
            return new Patients(
                    entity.getId(),
                    entity.getName(),
                    entity.getTelNo()
            );
        }
        return null;
    }
}