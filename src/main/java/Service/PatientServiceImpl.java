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

    @Override
    public String generatenewId() throws SQLException {
        String lastId=patientRepository.generatelastid();
        if (lastId == null) {

            return "P001";
        }

        try {
            // Extract the numeric part and increment
            String prefix = "P";
            String numericPart = lastId.substring(1);
            int number = Integer.parseInt(numericPart);
            number++; // Increment the number


            return String.format("%s%03d", prefix, number);
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {

            return "P001";
        }
    }

    @Override
    public void save(Patients patient) throws SQLException {
        PatientsEntity patientsentity = new PatientsEntity(
                patient.getId(),
                patient.getName(),
                patient.getTelNo()
        );
        patientRepository.insert(patientsentity);
    }

    @Override
    public Patients findpatientByTelno(String telno) throws SQLException {
        PatientsEntity patientsEntity=patientRepository.findPatientByTelno(telno);
        return new Patients(
                patientsEntity.getId(),
                patientsEntity.getName(),
                patientsEntity.getTelNo()
        );
    }
}