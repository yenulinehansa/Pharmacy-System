package Controller;

import Model.Dto.*;
import Service.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;


import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class BillingController implements Initializable {

    SalesService salesService=new SalesServiceImpl();
    PatientService patientService=new PatientServiceImpl();
    ObservableList<Drugs> drugsList= FXCollections.observableArrayList();
    DrugService drugService=new DrugServiceImpl();
    ObservableList<CartItems> cartItems= FXCollections.observableArrayList();

    @FXML
    private Button btnremove;

    @FXML
    private Button btnview;


    @FXML
    private Button btnnewidgenerator;

    @FXML
    private Button btnpayment;

    @FXML
    private Button btnregister;

    @FXML
    private TableColumn<?, ?> colavailability;

    @FXML
    private TableColumn<?, ?> colbrand;

    @FXML
    private TableColumn<?, ?> coldiscount;

    @FXML
    private TableColumn<?, ?> coldrugid;

    @FXML
    private TableColumn<?, ?> colid;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TableColumn<?, ?> colpatientid;

    @FXML
    private TableColumn<?, ?> colqty;

    @FXML
    private TableColumn<?, ?> coltotal;

    @FXML
    private TableColumn<?, ?> colunitprice;

    @FXML
    private AnchorPane finalBillpane;

    @FXML
    private Label lblDiscount;

    @FXML
    private Label lblFinalAmount;

    @FXML
    private Label lblFinaltotal;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblbalance;

    @FXML
    private TableView<CartItems> tblcart;

    @FXML
    private TableView<Drugs> tbldrugs;

    @FXML
    private TextField txtReceivedAmount;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtname;

    @FXML
    private TextField txttelno;

    @FXML
    void onenter(ActionEvent event) throws SQLException {
        String telno=txttelno.getText();
        Patients patients=patientService.findpatientByTelno(telno);
        txtid.setText(patients.getId());
        txtname.setText(patients.getName());

    }
    @FXML
    void onidgenerator(ActionEvent event) throws SQLException {
        generatePatientid();
    }

    @FXML
    void onpayment(ActionEvent event) throws SQLException {
        if (cartItems.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Empty Cart", "Please add items to cart before proceeding to payment.");
            return;
        }

        try {
            double receivedAmount = Double.parseDouble(txtReceivedAmount.getText());
            double finalAmount = Double.parseDouble(lblFinalAmount.getText().replace("Rs. ", ""));

            if (receivedAmount < finalAmount) {
                showAlert(Alert.AlertType.ERROR, "Insufficient Amount",
                        "Received amount is less than final amount.");
                return;
            }

            double balance = receivedAmount - finalAmount;
            lblbalance.setText(String.format("Rs. %.2f", balance));


            SaveToDatabase();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Amount", "Please enter a valid received amount.");
        }
    }

    private void SaveToDatabase() throws SQLException {
        String orderid=lblOrderId.getText();
        String patientid=txtid.getText();
        Double totalDiscount=Double.parseDouble(lblDiscount.getText().replace("Rs. ", ""));
        Double finaltotal=Double.parseDouble(lblTotal.getText().replace("Rs. ", ""));
        Sales sales=new Sales(orderid,patientid,totalDiscount,finaltotal);
        salesService.save(sales);


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

            salesService.saveSalesDetails(salesDetail);
        }
    }

    @FXML
    void onregister(ActionEvent event) throws SQLException {
        Patients patient=new Patients(
                txtid.getText(),
                txtname.getText(),
                txttelno.getText()
        );
        patientService.save(patient);
    }

    @FXML
    void onsearch(ActionEvent event) {
        String keyword = txtSearch.getText().trim();
        if (!keyword.isEmpty()) {
            try {
                drugsList.clear();
                drugsList.addAll(drugService.searchDrugs(keyword));
                tbldrugs.setItems(drugsList);
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Search Error", "Failed to search drugs: " + e.getMessage());
            }
        } else {
            loadAllDrugs();
        }
    }

    private void loadAllDrugs() {
        try {
            drugsList.addAll(drugService.getAllDrugs());
            tbldrugs.setItems(drugsList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            generateOrderid();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colbrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colunitprice.setCellValueFactory(new PropertyValueFactory<>("unitprice"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("stock_qty"));
        colavailability.setCellValueFactory(new PropertyValueFactory<>("expDate"));

        loadAllDrugs();

        colpatientid.setCellValueFactory(new PropertyValueFactory<>("Patientid"));
        coldrugid.setCellValueFactory(new PropertyValueFactory<>("Drugid"));
        coltotal.setCellValueFactory(new PropertyValueFactory<>("totalprice"));
        coldiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));

        setupTableSelectionListener();

    }
    public void generateOrderid() throws SQLException {
        String orderID=salesService.generateid();
        lblOrderId.setText(orderID);
    }
    public void generatePatientid() throws SQLException {
        String patID=patientService.generatenewId();
        txtid.setText(patID);
    }
    @FXML
    void onview(ActionEvent event) {
        loadAllDrugs();
    }
    private void setupTableSelectionListener() {
        tbldrugs.setRowFactory(tv -> {
            TableRow<Drugs> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty())) {
                    Drugs selectedDrug = row.getItem();
                    showQuantityDialog(selectedDrug);
                }
            });
            return row;
        });
    }

    private void showQuantityDialog(Drugs selectedDrug) {
        // Check if patient is selected
        String patientId = txtid.getText();
        if (patientId == null || patientId.trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Patient Required", "Please select or register a patient first.");
            return;
        }

        // Check if drug is already in cart
        for (CartItems item : cartItems) {
            if (item.getDrugid().equals(selectedDrug.getId())) {
                showAlert(Alert.AlertType.INFORMATION, "Item Exists", "This drug is already in the cart.");
                return;
            }
        }

        // Create custom dialog
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Add to Cart");
        dialog.setHeaderText("Add " + selectedDrug.getName() + " to Cart");

        // Set up buttons
        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        // Create input fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField quantityField = new TextField("1");
        TextField discountField = new TextField("0");

        grid.add(new Label("Quantity:"), 0, 0);
        grid.add(quantityField, 1, 0);
        grid.add(new Label("Discount :"), 0, 1);
        grid.add(discountField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        // Convert result to pair
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                return new Pair<>(quantityField.getText(), discountField.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(inputs -> {
            try {
                String quantityStr = inputs.getKey();
                String discountStr = inputs.getValue();

                int quantity = Integer.parseInt(quantityStr);
                double discount = Double.parseDouble(discountStr);

                // Validate quantity (existing validations)
                if (quantity <= 0) {
                    showAlert(Alert.AlertType.ERROR, "Invalid Quantity", "Quantity must be greater than 0.");
                    return;
                }

                if (quantity > selectedDrug.getStock_qty()) {
                    showAlert(Alert.AlertType.ERROR, "Insufficient Stock",
                            "Available stock: " + selectedDrug.getStock_qty());
                    return;
                }



                // Calculate prices
                double totalPriceBeforeDiscount = selectedDrug.getUnitprice() * quantity;
                double discountAmount = discount;
                double finalPrice = totalPriceBeforeDiscount - discountAmount;

                // Create cart item
                CartItems cartItem = new CartItems(patientId, selectedDrug.getId(),
                        finalPrice, discountAmount,quantity);

                // Add to cart
                cartItems.add(cartItem);
                tblcart.setItems(cartItems);
                updateCartTotals();

                showAlert(Alert.AlertType.INFORMATION, "Success",
                        "Added " + quantity + " of " + selectedDrug.getName() +
                                " to cart with " + discount + "discount.");

            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input",
                        "Please enter valid numbers for quantity and discount.");
            }
        });
    }

    private void updateCartTotals() {
        double total = 0.0;
        double totalDiscount = 0.0;

        for (CartItems item : cartItems) {
            total += item.getTotalprice();
            totalDiscount += item.getDiscount();
        }

        double finalAmount = total - totalDiscount;

        lblTotal.setText(String.format("Rs. %.2f", total));
        lblDiscount.setText(String.format("Rs. %.2f", totalDiscount));
        lblFinalAmount.setText(String.format("Rs. %.2f", finalAmount));
        lblFinaltotal.setText(String.format("Rs. %.2f", finalAmount));
    }
    @FXML
    void onRemoveFromCart(ActionEvent event) {
        CartItems selectedItem = tblcart.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            cartItems.remove(selectedItem);
            updateCartTotals();
            showAlert(Alert.AlertType.INFORMATION, "Removed", "Item removed from cart.");
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an item to remove from cart.");
        }
    }
}