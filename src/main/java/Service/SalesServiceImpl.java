package Service;

import Repository.SalesRepository;
import Repository.SalesRepositoryImpl;

import java.sql.SQLException;

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
}
