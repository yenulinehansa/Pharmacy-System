package Repository;

import Model.Entity.PatientsEntity;
import DB.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientRepositoryImpl implements PatientRepository {

    @Override
    public List<PatientsEntity> getAllPatients() throws SQLException {
        List<PatientsEntity> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";


        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            PatientsEntity patient = new PatientsEntity(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("telNo")
            );
            patients.add(patient);
        }


        return patients;
    }


    @Override
    public boolean updatePatient(PatientsEntity patient) throws SQLException {
        String sql = "UPDATE patients SET name = ?, telNo = ? WHERE id = ?";


             Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, patient.getName());
            statement.setString(2, patient.getTelNo());
            statement.setString(3, patient.getId());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;


    }

    @Override
    public boolean deletePatient(String patientId) throws SQLException {
        String sql = "DELETE FROM patients WHERE id = ?";


        Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, patientId);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;


    }

    @Override
    public PatientsEntity findPatientById(String patientId) throws SQLException {
        String sql = "SELECT * FROM patients WHERE id = ?";


        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql) ;

            statement.setString(1, patientId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new PatientsEntity(
                            resultSet.getString("id"),
                            resultSet.getString("name"),
                            resultSet.getString("telNo")
                    );
                }

            }
        return null;





}
}