package patrickkinney.c195pa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

/**Controller class for the main landing page view.
 *
 */
public class landingPageController {

    @FXML
    private Button appAddButton;

    @FXML
    private TableColumn<Appointment, Integer> appContactCol;

    @FXML
    private TableColumn<Appointment, Integer> appCustIDCol;

    @FXML
    private Button appDeleteButton;

    @FXML
    private TableColumn<Appointment, String> appDescriptionCol;

    @FXML
    private Button appEditButton;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appEndCol;

    @FXML
    private TableColumn<Appointment, Integer> appIdCol;

    @FXML
    private TableColumn<Appointment, String> appLocationCol;

    @FXML
    private ToggleGroup appSelectionTG;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appStartCol;

    @FXML
    private TableColumn<Appointment, String> appTitleCol;

    @FXML
    private TableColumn<Appointment, String> appTypeCol;

    @FXML
    private TableColumn<Appointment, Integer> appUserIDCol;

    @FXML
    private RadioButton appViewAllRadio;

    @FXML
    private RadioButton appViewMonthRadio;

    @FXML
    private RadioButton appViewWeekRadio;

    @FXML
    private Button custAddButton;

    @FXML
    private TableColumn<Customer, String> custAddrCol;

    @FXML
    private Button custDeleteButton;

    @FXML
    private TableColumn<Customer, String> custDivCol;

    @FXML
    private TableColumn<Customer, String> custCountryCol;

    @FXML
    private Button custEditButton;

    @FXML
    private TableColumn<Customer, Integer> custIdCol;

    @FXML
    private TableColumn<Customer, String> custNameCol;

    @FXML
    private TableColumn<Customer, String> custPhoneNumCol;

    @FXML
    private TableColumn<Customer, String> custZipCol;

    @FXML
    private Button mainExitButton;

    @FXML
    private Label mainTitle;

    @FXML
    private TableView<Customer> custTable;

    @FXML
    private TableView<Appointment> appTable;

    @FXML
    private Button reportButton;


    ObservableList<Customer> custList = DBQueries.getAllCustomers();
    ObservableList<Appointment> appList = DBQueries.getAllAppointments();
    ObservableList<Users> userList = DBQueries.getAllUsers();

    /**
     * Initialize controller tables.
     */
    public void initialize() {

        custIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        custAddrCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        custZipCol.setCellValueFactory(new PropertyValueFactory<>("postal"));
        custPhoneNumCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        custCountryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        custDivCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        custTable.setItems(custList);

        appIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appContactCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        appCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appUserIDCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        appTable.setItems(appList);

    }

    /**
     * Takes user to Add Application page.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAddApp(ActionEvent event) throws IOException {

        if (landingPageController.confirmDialogBox("Confirmation", "Are you sure you want to add an appointment?")) {

            Stage stage;
            Parent parent;

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addAppointment.fxml")));
            stage.setScene(new Scene(parent));
            stage.show();
        }

    }

    /**
     * Takes user to Add Customer page.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAddCust(ActionEvent event) throws IOException {

        if (landingPageController.confirmDialogBox("Confirmation", "Are you sure you want to add a customer?")) {


            Stage stage;
            Parent parent;

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("addCustomer.fxml")));
            stage.setScene(new Scene(parent));
            stage.show();
        }
    }

    /**
     * Deletes selected Appointment.
     *
     * @param event
     * @throws SQLException
     */
    @FXML
    void onActionDeleteApp(ActionEvent event) throws SQLException {


        Appointment selectedAppointment = appTable.getSelectionModel().getSelectedItem();
        int selectedAppId = selectedAppointment.getId();
        String appType = selectedAppointment.getType();

        if (landingPageController.confirmDialogBox("Confirmation", "Are you sure you want to delete this Appointment? ID: " + selectedAppId + " Type:" + appType)) {

            String sql = String.format("DELETE FROM appointments WHERE Appointment_ID = '%d'", selectedAppId);
            PreparedStatement ps = JBDC.getConnection().prepareStatement(sql);
            ps.executeUpdate();

            appTable.setItems(DBQueries.getAllAppointments());
        }
    }

    /**
     * Deletes Selected Customer.
     * Contains logic to check for associated appointments first.
     * Confirms user choice and deletes both customer and associated appointments as applicable.
     *
     * @param event
     * @throws SQLException
     */
    @FXML
    void onActionDeleteCust(ActionEvent event) throws SQLException {


        if (custTable.getSelectionModel().getSelectedItem() == null) {
            landingPageController.errorDialogBox("Selection Error.", "Select a customer to delete.");
        } else {

            if (landingPageController.confirmDialogBox("Confirmation", "Are you sure you want to delete this customer?")) {

                Customer deleteCust = custTable.getSelectionModel().getSelectedItem();
                int deleteId = deleteCust.getId();
                int appId;

                for (Appointment app : DBQueries.getAllAppointments()) {
                    if (app.getCustomerId() == deleteId) {
                        appId = app.getCustomerId();
                        if (landingPageController.confirmDialogBox("Confirmation", "Selected customer has existing appointments. Delete customer and appointments?")) {

                            String sql = String.format("DELETE FROM Appointments WHERE Appointment_ID = '%d'", appId);
                            PreparedStatement ps = JBDC.getConnection().prepareStatement(sql);
                            ps.executeUpdate();

                            String sql2 = String.format("DELETE FROM Customers WHERE Customer_ID = '%d'", deleteId);
                            PreparedStatement ps2 = JBDC.getConnection().prepareStatement(sql2);
                            ps2.executeUpdate();
                        }

                    } else {
                        String sql3 = String.format("DELETE FROM Customers WHERE Customer_ID = '%d'", deleteId);
                        PreparedStatement ps3 = JBDC.getConnection().prepareStatement(sql3);
                        ps3.executeUpdate();

                    }
                }
                if (deleteId >= DBQueries.getAllCustomers().size()) {

                    for (Customer c : DBQueries.getAllCustomers()) {

                        if (c.getId() > deleteId) {

                            int foundId = c.getId();
                            int updateId = foundId - 1;
                            String custName = c.getName();


                            String sql4 = String.format("UPDATE customers SET Customer_ID = %d WHERE Customer_Name = '%s'", updateId, custName);
                            PreparedStatement ps4 = JBDC.getConnection().prepareStatement(sql4);
                            ps4.executeUpdate();
                        }

                    }
                }

            }
            custTable.setItems(DBQueries.getAllCustomers());
            appTable.setItems(DBQueries.getAllAppointments());
        }

    }

    /**
     * Takes user to modify appointment page. Uses user selection to prepopulate modify form.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionEditApp(ActionEvent event) throws IOException {

        if (appTable.getSelectionModel().getSelectedItem() == null) {
            landingPageController.errorDialogBox("Selection Error.", "Select an appointment to modify.");
        } else {
            if (landingPageController.confirmDialogBox("Confirmation", "Are you sure you want to edit this appointment?")) {
                Appointment appointment = appTable.getSelectionModel().getSelectedItem();
                modAppointmentController.sentApp(appointment);

                Stage stage;
                Parent parent;

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("modAppointment.fxml")));
                stage.setScene(new Scene(parent));
                stage.show();
            }
        }
    }

    /**
     * Takes user to modify customer page. Uses user selection to prepopulate modify form.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionEditCust(ActionEvent event) throws IOException {

        if (custTable.getSelectionModel().getSelectedItem() == null) {
            landingPageController.errorDialogBox("Selection Error.", "Select a customer to modify.");
        } else {
            if (landingPageController.confirmDialogBox("Confirmation", "Are you sure you want to edit this customer?")) {
                Customer customer = custTable.getSelectionModel().getSelectedItem();
                modCustomerController.sentCust(customer);

                Stage stage;
                Parent parent;

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("modifyCustomer.fxml")));
                stage.setScene(new Scene(parent));
                stage.show();
            }
        }
    }

    /**
     * Exists application. Confirms with user.
     *
     * @param event
     */
    @FXML
    void onActionExitLanding(ActionEvent event) {
        if (landingPageController.confirmDialogBox("Confirmation", "Are you sure you want to exit?")) {
            JBDC.closeConnection();
            System.exit(0);
        }

    }

    /**
     * Takes user to reports page.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionReportPage(ActionEvent event) throws IOException {
        Stage stage;
        Parent parent;

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("reportsPage.fxml")));
        stage.setScene(new Scene(parent));
        stage.show();

    }

    /**
     * Sets appointment table to show all.
     *
     * @param event
     */
    @FXML
    void onActionViewAllApp(ActionEvent event) {
        appTable.setItems(appList);
    }

    /**
     * Sets appointment table to show appointments in current month.
     *
     * @param event
     */
    @FXML
    void onActionViewMonthApp(ActionEvent event) {

        ObservableList<Appointment> appListMonth = FXCollections.observableArrayList();

        for (Appointment app : appList) {
            LocalDateTime start = app.getStart();
            int startMonth = start.getMonthValue();
            LocalDateTime current = LocalDateTime.now();
            int currentMonth = current.getMonthValue();

            if (startMonth == currentMonth) {
                appListMonth.add(app);
            }
        }
        appTable.setItems(appListMonth);

    }

    /**
     * Sets appointment table to show appointments in current week.
     *
     * @param event
     */
    @FXML
    void onActionViewWeekApp(ActionEvent event) {
        ObservableList<Appointment> appListWeek = FXCollections.observableArrayList();

        for (Appointment app : appList) {

            LocalDate start = LocalDate.from(app.getStart());
            LocalDate current = LocalDate.from(LocalDateTime.now());
            LocalDate currentWeek = current.plusWeeks(1);

            if (!current.isAfter(start) && start.isBefore(currentWeek)) {
                appListWeek.add(app);
            }
        }
        appTable.setItems(appListWeek);


    }


    /**
     * Method for confirmation dialog box.
     *
     * @param title String confirmation title
     * @param text  String confirmation text
     * @return
     */
    static boolean confirmDialogBox(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText("Confirmation");
        alert.setContentText(text);
        Optional<ButtonType> result = alert.showAndWait();

        return result.get() == ButtonType.OK;
    }

    /**
     * Method for error dialog box.
     *
     * @param title String error title
     * @param text  String error text
     */
    static void errorDialogBox(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.showAndWait();
    }

    /**
     * Method for warning dialog box.
     *
     * @param title String warning title
     * @param text  String warning text
     */
    static void warningDialogBox(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.showAndWait();
    }


}
