package patrickkinney.c195pa;

import javafx.event.ActionEvent;
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

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

/**Controller class for modifying customers.
 *
 */
public class modCustomerController {

    @FXML
    private TextField modCustAddTextfield;

    @FXML
    private Button modCustCancelButton;

    @FXML
    private ComboBox<String> modCustCountryCombo;

    @FXML
    private ComboBox<String> modCustDivisionCombo;

    @FXML
    private TextField modCustIDTextfield;

    @FXML
    private TextField modCustNameTextfield;

    @FXML
    private TextField modCustPhoneTextfield;

    @FXML
    private TextField modCustPostalTextfield;

    @FXML
    private Button modCustSaveButton;

    static Customer recievedCust;


    /**Initializes the modify customer form.
     *
     */
    public void initialize() {
        modCustIDTextfield.setText(String.valueOf(recievedCust.getId()));
        modCustNameTextfield.setText(String.valueOf(recievedCust.getName()));
        modCustAddTextfield.setText(String.valueOf(recievedCust.getAddress()));
        modCustPostalTextfield.setText(String.valueOf(recievedCust.getPostal()));
        modCustPhoneTextfield.setText(String.valueOf(recievedCust.getPhone()));
        modCustCountryCombo.setValue(recievedCust.getCountry());
        modCustDivisionCombo.setValue(recievedCust.getState());
        modCustCountryCombo.getItems().addAll(DBQueries.countryList());
        modCustDivisionCombo.getItems().addAll(DBQueries.divisionList());

    }

    /**This method populates the division combo-box with values from the associated country.
     *
     * @param event
     */
    @FXML
    void onActionModCountryCombo(ActionEvent event) {

        modCustDivisionCombo.setDisable(false);
        modCustDivisionCombo.setValue(recievedCust.getState());
        String country = modCustCountryCombo.getValue();
        modCustDivisionCombo.setItems(DBQueries.getDivisionList(country));


    }

    /**This method takes the user back to the main landing form upon confirmation.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionCancelModCust(ActionEvent event) throws IOException {
        if (landingPageController.confirmDialogBox("Confirmation", "Are you sure you want to exit?")) {
            Stage stage;
            Parent parent;

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("landingPage.fxml")));
            stage.setScene(new Scene(parent));
            stage.show();
        }
    }

    /**This method saves updated customer information.
     *
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void onActionSaveModCust(ActionEvent event) throws SQLException, IOException {

        try {

            boolean hasError = false;

            int id = Integer.parseInt(modCustIDTextfield.getText());
            String name = modCustNameTextfield.getText();
            String address = modCustAddTextfield.getText();
            String postal = modCustPostalTextfield.getText();
            String phone = modCustPhoneTextfield.getText();
            String country = modCustCountryCombo.getValue();
            String state = modCustDivisionCombo.getValue();
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

                String sql = String.format("UPDATE customers SET Customer_Name = '%s', Address = '%s', Postal_Code = '%s', Phone = '%s', Last_Update = NOW(), Last_Updated_By = '%s', Division_ID = '%d' WHERE Customer_ID = '%d'", name, address, postal, phone, "lastUpdatedBy", divId, id);

                PreparedStatement ps = JBDC.getConnection().prepareStatement(sql);
                ps.executeUpdate();

                Stage stage;
                Parent parent;

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("landingPage.fxml")));
                stage.setScene(new Scene(parent));
                stage.show();
            }
        }
        catch (Exception e){
            landingPageController.errorDialogBox("Input Error", "Input Error. Check inputs.");
        }

    }

    /**This method is used to send the selected customer from the landing page to the modify customer page.
     *
     * @param cust
     */
    public static void sentCust(Customer cust) {
        recievedCust = cust;

    }

}
