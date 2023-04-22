package patrickkinney.c195pa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**Controller class for the reports view.
 * LAMBDA EXPRESSION USED.
 * The LAMBDA expression is used to calculate the total appointment types by month for a specific customer in the fill summary method.
 * The LAMBDA expression takes in a customer ID, desired month (int), and a total count variable (int).
 * Using the inline lambda expression allows the programmer to see all the logic and input where it will be used.
 * This is especially useful for one-off functions, like this report total calculation.
 */
public class reportsController {

    @FXML
    private TableColumn<Appointment, Integer> appCustIDCol;

    @FXML
    private TableColumn<Appointment, String> appDescriptionCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appEndCol;

    @FXML
    private TableColumn<Appointment, Integer> appIdCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> appStartCol;

    @FXML
    private TableColumn<Appointment, String> appTitleCol;

    @FXML
    private TableColumn<Appointment, String> appTypeCol;

    @FXML
    private TableColumn<Months, Integer> april;

    @FXML
    private TableColumn<Months, Integer> aug;

    @FXML
    private ComboBox<Integer> custCombo;

    @FXML
    private TableView<Appointment> custTable;

    @FXML
    private TableColumn<Months, Integer> dec;

    @FXML
    private TableColumn<Types, Integer> durationCol;

    @FXML
    private TableColumn<Months, Integer> feb;

    @FXML
    private TableColumn<Months, Integer> janCol;

    @FXML
    private TableColumn<Months, Integer> july;

    @FXML
    private TableColumn<Months, Integer> june;

    @FXML
    private Button mainExitButton;

    @FXML
    private Label mainTitle;

    @FXML
    private TableColumn<Months, Integer> mar;

    @FXML
    private TableColumn<Months, Integer> may;

    @FXML
    private TableView<Months> monthTable;

    @FXML
    private TableColumn<Months, Integer> nov;

    @FXML
    private TableColumn<Months, Integer> oct;

    @FXML
    private TableColumn<?, ?> qtyCol;

    @FXML
    private TableColumn<Months, Integer> sept;

    @FXML
    private TableView<Types> locationTable;

    @FXML
    private TableColumn<Months, Integer> typeCol;

    @FXML
    private TableView<Types> typeTable;
    @FXML
    private TableColumn<Months, Integer> custCol;
    @FXML
    private TableColumn<Types, String> locationCol;

    ObservableList<Appointment> appList = DBQueries.getAllAppointments();
    ObservableList<Appointment> appListSelected = FXCollections.observableArrayList();
    ObservableList<Integer> custIds = DBQueries.custIDList();
    ObservableList<Months> countMonths = FXCollections.observableArrayList();
    ObservableList<Types> countTypes = FXCollections.observableArrayList();

    ObservableList<String> appTypes = DBQueries.appTypeList();
    ObservableList<String> appLocations = DBQueries.appLocationList();
    ObservableList<Types> locationTypes = FXCollections.observableArrayList();

    /**
     * Initialize the Reports Controller.
     *
     * @throws SQLException
     */
    public void initialize() throws SQLException {
        custCombo.getItems().addAll(DBQueries.contactIDList());

        appIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        appCustIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        custTable.setItems(appList);

        janCol.setCellValueFactory(new PropertyValueFactory<>("jan"));
        feb.setCellValueFactory(new PropertyValueFactory<>("feb"));
        mar.setCellValueFactory(new PropertyValueFactory<>("march"));
        april.setCellValueFactory(new PropertyValueFactory<>("april"));
        may.setCellValueFactory(new PropertyValueFactory<>("may"));
        june.setCellValueFactory(new PropertyValueFactory<>("june"));
        july.setCellValueFactory(new PropertyValueFactory<>("july"));
        aug.setCellValueFactory(new PropertyValueFactory<>("aug"));
        sept.setCellValueFactory(new PropertyValueFactory<>("sep"));
        oct.setCellValueFactory(new PropertyValueFactory<>("oct"));
        nov.setCellValueFactory(new PropertyValueFactory<>("nov"));
        dec.setCellValueFactory(new PropertyValueFactory<>("dec"));
        custCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        durationCol.setCellValueFactory(new PropertyValueFactory<>("qty"));


        fillSummary();

    }

    /**
     * Filters Contact Summary table by user selected contact
     *
     * @param event
     */
    @FXML
    void onActionCustCombo(ActionEvent event) {
        int contactId = custCombo.getValue();
        appListSelected.clear();

        for (Appointment app : appList) {
            if (contactId == app.getContactId()) {
                appListSelected.add(app);
            }
        }
        custTable.setItems(appListSelected);
    }

    /**
     * LAMBDA EXPRESSION USED.
     * The LAMBDA expression is used to calculate the total appointment types by month for a specific customer.
     * The LAMBDA expression takes in a customer ID, desired month (int), and a total count variable (int).
     * Using the inline lambda expression allows the programmer to see all the logic and input where it will be used.
     * This is especially useful for one-off functions, like this report total calculation.
     * <p>
     * This method is used to fill the lists used in tableviews for this report.
     *
     * @throws SQLException
     */
    private void fillSummary() throws SQLException {

        for (int id : custIds) {

            int jan = 0, feb = 0, mar = 0, april = 0, may = 0, june = 0, july = 0, aug = 0, sep = 0, oct = 0, nov = 0, dec = 0;
            int[] months = {jan, feb, mar, april, may, june, july, aug, sep, oct, nov, dec};

            for (int i = 1; i < 13; i++) {


                appTotalCount counted = (custId, month, total) -> {
                    String sql = String.format("SELECT * from appointments WHERE Customer_ID = %d AND MONTH(Start) = %d", custId, month);
                    PreparedStatement ps = JBDC.getConnection().prepareStatement(sql);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        total++;
                    }
                    return total;
                };

                //int count = DBQueries.appCountsTotal(id, i);  *************replaced with lambda
                int count = counted.calcTotal(id, i, 0);
                months[i - 1] = count;
            }
            Months gotMonths = new Months(months[0], months[1], months[2], months[3], months[4], months[5], months[6], months[7], months[8], months[9], months[10], months[11], id);
            countMonths.add(gotMonths);

        }
        monthTable.setItems(countMonths);


        Set<String> typeSet = new HashSet<>(appTypes);
        ObservableList<String> uniqueTypes = FXCollections.observableArrayList();
        uniqueTypes.addAll(typeSet);

        for (String type : uniqueTypes) {
            int count2 = DBQueries.allTypesTotal(type);

            Types types = new Types(type, count2, "placeholder", 0);
            countTypes.add(types);

        }
        typeTable.setItems(countTypes);

        Set<String> locationSet = new HashSet<>(appLocations);
        ObservableList<String> uniqueLocations = FXCollections.observableArrayList();
        uniqueLocations.addAll(locationSet);

        for (String location : uniqueLocations) {
            int count3 = DBQueries.allLocationsTotal(location);
            Types locType = new Types("placeholder", 0, location, count3);
            locationTypes.add(locType);

        }
        locationTable.setItems(locationTypes);
    }



    /*
           for(String location : appLocations){
            int count3 = DBQueries.allLocationsTotal(location);
            Types locType = new Types("placeholder", 0, location, count3);
            locationTypes.add(locType);
        }
        locationTable.setItems(locationTypes);
     */

    /**
     * This method takes the user back to the main landing page when the return button is pressed.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionExitLanding(ActionEvent event) throws IOException {
        Stage stage;
        Parent parent;

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("landingPage.fxml")));
        stage.setScene(new Scene(parent));
        stage.show();


    }


}
