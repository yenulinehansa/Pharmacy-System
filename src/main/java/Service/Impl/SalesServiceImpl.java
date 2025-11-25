package Service.Impl;

import DB.DBConnection;
import Model.Dto.CartItems;
import Model.Dto.Sales;
import Model.Dto.SalesDetails;
import Repository.SalesRepository;
import Repository.Impl.SalesRepositoryImpl;
import Service.DrugService;
import Service.SalesService;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class SalesServiceImpl implements SalesService {
    SalesRepository salesRepository = new SalesRepositoryImpl();
    DrugService drugService=new DrugServiceImpl();

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

    @Override
    public Boolean save(Sales sales) throws SQLException {
       Boolean isadded=salesRepository.save(sales);

        return isadded;
    }

    @Override
    public Boolean saveSalesDetails(SalesDetails salesDetail) throws SQLException {
        Boolean isadded=salesRepository.insert(salesDetail);
        return isadded;
    }

    @Override
    public int getsalescount() throws SQLException {
        int salescount=salesRepository.getsalescount();
        return salescount;

    }

    @Override
    public void placefullOrder(String orderid, String patientid, Double totalDiscount, double finalAmount, ObservableList<CartItems> cartItems) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

    try{    if (cartItems == null || cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        Sales sales = new Sales(orderid, patientid, totalDiscount, finalAmount);
        connection.setAutoCommit(false);

        Boolean isSalesAdded = save(sales);

        if (isSalesAdded == null || !isSalesAdded) {
            connection.rollback();
            throw new RuntimeException("Failed to save order");
        }
        for (CartItems cartItem : cartItems) {
            SalesDetails salesDetail = new SalesDetails(
                    orderid,
                    patientid,
                    cartItem.getDrugid(),
                    cartItem.getQuantity(),
                    cartItem.getDiscount(),
                    cartItem.getTotalprice(),
                    LocalDate.now()
            );

            Boolean issalesdetailsadded=saveSalesDetails(salesDetail);

            if (issalesdetailsadded == null || !issalesdetailsadded) {
                connection.rollback();
                throw new RuntimeException("Failed to save sales detail for order: " + orderid);
            }
            Boolean isQtyUpdated=drugService.quantityupdate(salesDetail.getDrugid(),salesDetail.getQuantity());
            if (isQtyUpdated == null || !isQtyUpdated) {
                connection.rollback();
                throw new RuntimeException("Failed to update quantity for drug: " +cartItem.getDrugid() );
            }





        }
        connection.commit();
        System.out.println("Order placed successfully");
    } catch (Exception e) {
        connection.rollback();
        System.out.println("Error placing order: " + e.getMessage());
        throw new RuntimeException("Order placement failed: " + e.getMessage(), e);
    } finally {
        connection.setAutoCommit(true);
    }


        }



}
