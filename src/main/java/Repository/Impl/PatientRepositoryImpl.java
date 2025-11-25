package Repository.Impl;

import Model.Entity.PatientsEntity;
import DB.DBConnection;
import Repository.PatientRepository;

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

    @Override
    public String generatelastid() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "SELECT id FROM Patients ORDER BY id DESC LIMIT 1";
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("id");
        }
        return null;
    }

    @Override
    public void insert(PatientsEntity patientsentity) throws SQLException {
        Connection connection=DBConnection.getInstance().getConnection();
        String SQL="INSERT INTO patients VALUES (?,?,?)";
        PreparedStatement preparedStatement=connection.prepareStatement(SQL);
        preparedStatement.setString(1, patientsentity.getId());
        preparedStatement.setString(2, patientsentity.getName());
        preparedStatement.setString(3, patientsentity.getTelNo());
        preparedStatement.executeUpdate();


    }

    @Override
    public PatientsEntity findPatientByTelno(String telno) throws SQLException {
        String sql = "SELECT * FROM patients WHERE telNo = ?";


        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql) ;

        statement.setString(1, telno);

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