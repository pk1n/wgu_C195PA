package patrickkinney.c195pa;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Controller class for adding customers.
 *
 * LAMBDA EXPRESSION USED.
 * The lambda expression is used in the save handler to generate user-id's inline as opposed to using distinct and separate method.
 * Using the inline lambda expression allows the programmer to see all the logic and input where it will be used.
 *
 * Method saves a new customer object based on user input. Input validation and error handling are completed.
 */
public class addCustomerController {

    @FXML
    private ComboBox<String> addCustCountryCombo;

    @FXML
    private ComboBox<String> addCustDivisionCombo;

    @FXML
    private TextField addCustAddTextfield;

    @FXML
    private TextField addCustPhoneTextfield;

    @FXML
    private TextField addCustNameTextfield;

    @FXML
    private TextField addCustPostalTextfield;

    /**
     * Initialize the add customer page.
     * Populates combo-boxes for user selection.
     */
    public void initialize() {

        addCustCountryCombo.getItems().addAll(DBQueries.countryList());


    }

    /**Country combo-box logic.
     *
     * Form requires user to select country. Then first-level-divisions will be populated in a seperate combo box for the user to select.
     * The first-level-divsion combo-box is only populated with divisions associated with that country.
     *
     * @param event
     */
    @FXML
    void onActionCountryCombo(ActionEvent event) {

        addCustDivisionCombo.setDisable(false);
        String country = addCustCountryCombo.getValue();
        addCustDivisionCombo.setItems(DBQueries.getDivisionList(country));

    }

    /**Returns user to the landing page.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {

        if (landingPageController.confirmDialogBox("Confirmation", "Are you sure you want to exit?")) {
            Stage stage;
            Parent parent;

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("landingPage.fxml")));
            stage.setScene(new Scene(parent));
            stage.show();
        }
    }

    /**LAMBDA EXPRESSION USED.
     * The lambda expression is used to generate user-id's inline as opposed to using distinct and separate method.
     *Using the inline lambda expression allows the programmer to see all the logic and input where it will be used.
     *
     *Method saves a new customer object based on user input. Input validation and error handling are completed.
     *
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws SQLException, IOException {

        try {
            boolean hasError = false;

            autoGenId genId = () -> {
                int size = DBQueries.getAllCustomers().size() + 1;
                return size;
            };

            int id = genId.newId();
            //int id = autoGenIDs(); *********** replaced with lambda
            String name = addCustNameTextfield.getText();
            String address = addCustAddTextfield.getText();
            String postal = addCustPostalTextfield.getText();
            String phone = addCustPhoneTextfield.getText();
            String country = addCustCountryCombo.getValue();
            String state = addCustDivisionCombo.getValue();
            int divId = DBQueries.determineDivId(state);


            if (name.isBlank()) {
                landingPageController.errorDialogBox("Input Error", "Input Error. Must enter Customer name.");
                hasError = true;
            }
            if (address.isBlank()) {
                landingPageController.errorDialogBox("Input Error", "Input Error. Must enter Customer address.");
                hasError = true;
            }
            if (postal.isBlank()) {
                landingPageController.errorDialogBox("Input Error", "Input Error. Must enter Customer postal code.");
                hasError = true;
            }
            if (phone.isBlank()) {
                landingPageController.errorDialogBox("Input Error", "Input Error. Must enter Customer phone code.");
                hasError = true;
            }
            if (country == null) {
                landingPageController.errorDialogBox("Input Error", "Input Error. Must select Customer country.");
                hasError = true;
            }
            if (state == null) {
                landingPageController.errorDialogBox("Input Error", "Input Error. Must select Customer division.");
                hasError = true;
            }
            if (!hasError) {
                String sql = String.format("INSERT INTO customers VALUES(%d, '%s', '%s', '%s', '%s', NOW(), '%s', NOW(), '%s', %d)", id, name, address, postal, phone, "createdBy", "lastUpdatedBy", divId);

                PreparedStatement ps = JBDC.getConnection().prepareStatement(sql);
                ps.executeUpdate();

                Stage stage;
                Parent parent;

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("landingPage.fxml")));
                stage.setScene(new Scene(parent));
                stage.show();
            }
        } catch(Exception e){
            landingPageController.errorDialogBox("Input Error", "Input Error. Check inputs.");
        }



    }
}

/* ********************************** REPLACED WITH LAMBDA ***************************************
    public static int autoGenIDs() {
        int custID = 1;
        for (int i = 0; i < DBQueries.getAllCustomers().size(); i++){
            custID++;
        }
        return custID;
    }

 */



