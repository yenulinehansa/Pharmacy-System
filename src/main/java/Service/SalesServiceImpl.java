package Service;

import Model.Dto.Patients;
import Model.Dto.SalesDetails;
import Model.Entity.PatientsEntity;
import Repository.SalesRepository;
import Repository.SalesRepositoryImpl;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class SalesServiceImpl implements SalesService{
    SalesRepository salesRepository = new SalesRepositoryImpl();

    @Override
    public String generateid() throws SQLException {
        String lastId=salesRepository.generatelastID();
        if (lastId == null) {

            return "O001";
        }

        try {
            // Extract the numeric part and increment
            String prefix = "O";
            String numericPart = lastId.substring(1);
            int number = Integer.parseInt(numericPart);
            number++; // Increment the number


            return String.format("%s%03d", prefix, number);
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {

            return "O001";
        }

    }

    @Override
    public ObservableList<SalesDetails> getSalesDetails() throws SQLException {
        ObservableList<SalesDetails> salesDetails = salesRepository.getsalesdetails();
        return salesDetails;

    }

    @Override
    public ObservableList<SalesDetails> searchsales(LocalDate date) throws SQLException {
        ObservableList<SalesDetails> salesDetails=salesRepository.searchsales(date);
        return salesDetails;
    }


}
