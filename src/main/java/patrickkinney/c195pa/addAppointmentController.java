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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.TimeZone;

/** Controller class for adding appointments.
 *
 */
public class addAppointmentController {

    @FXML
    private Button addAppCancelButton;

    @FXML
    private ComboBox<String> addAppContactCombo;

    @FXML
    private ComboBox<Integer> addAppCustIDCombo;


    @FXML
    private TextArea addAppDescripTextArea;


    @FXML
    private TextField addAppIDTextfield;


    @FXML
    private TextField addAppLocationTextfield;

    @FXML
    private Button addAppSaveButton;


    @FXML
    private TextField addAppTitleTextfield;

    @FXML
    private TextField addAppTypeTextfield;

    @FXML
    private ComboBox<Integer> addAppUserIDCombo;


    @FXML
    private DatePicker endDate;

    @FXML
    private ComboBox<String> endHour;

    @FXML
    private ComboBox<String> endMin;

    @FXML
    private DatePicker startDate;

    @FXML
    private ComboBox<String> startHour;

    @FXML
    private ComboBox<String> startMin;

    /** Initialize the addAppointmentController.
     * Populates combo-boxes.
     */
    public void initialize() {
        addAppContactCombo.getItems().addAll(DBQueries.contactList());
        addAppUserIDCombo.getItems().addAll(DBQueries.userList());
        addAppCustIDCombo.getItems().addAll(DBQueries.custIDList());

        ObservableList<String> hours = FXCollections.observableArrayList();
        ObservableList<String> mins = FXCollections.observableArrayList();

        for (int i = 0; i < 24; i++) {
            String s = String.format("%02d", i);
            hours.add(s);
        }
        for (int i = 0; i < 60; i++) {
            String s = String.format("%02d", i);
            mins.add(s);
        }
        startHour.setItems(hours);
        startMin.setItems(mins);
        endHour.setItems(hours);
        endMin.setItems(mins);

    }

    /** Returns the user to landing page.
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

    /** Saves appointment information.
     * Creates a new appointment object from user input.
     * Completes input validation and error handling.
     * Converts local input time to UTC for storage in database.
     *
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void onActionSaveAppointment(ActionEvent event) throws SQLException, IOException {

        boolean hasError = true;
        boolean hasOverlap = true;

        int id = autoGenID();
        String title = addAppTitleTextfield.getText();
        String description = addAppDescripTextArea.getText();
        String location = addAppLocationTextfield.getText();
        String type = addAppTypeTextfield.getText();
        LocalDate sD = startDate.getValue();
        String sH = startHour.getValue();
        String sM = startMin.getValue();
        LocalDate eD = endDate.getValue();
        String eH = endHour.getValue();
        String eM = endMin.getValue();

        if (sH == null || sM == null || sD == null) {
            landingPageController.errorDialogBox("Input Error", "Select appointment start time and date.");
        } else if (eH == null || eM == null || eD == null) {
            landingPageController.errorDialogBox("Input Error", "Select appointment end time and date.");
        } else if (addAppContactCombo.getValue() == null || addAppCustIDCombo.getValue() == null || addAppUserIDCombo.getValue() == null) {
            landingPageController.errorDialogBox("Input Error", "Contact, Customer, and User must be selected from dropdowns.");
        } else {

            String contactName = addAppContactCombo.getValue();
            int contactId = DBQueries.determineContactId(contactName);
            int custId = addAppCustIDCombo.getValue();
            int userId = addAppUserIDCombo.getValue();

            //formats entered start/end date/time as a singe stream for each start/end
            String start = String.format(sH + ":" + sM);
            String end = String.format(eH + ":" + eM);
            String sDate = sD.toString();
            String eDate = eD.toString();
            String sDateTime = String.format(sDate + " " + start);
            String eDateTime = String.format(eDate + " " + end);

            //converts string start/end to localDateTime objects
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime enteredStartDateTime = LocalDateTime.parse(sDateTime, dtf);
            LocalDateTime enteredEndDateTime = LocalDateTime.parse(eDateTime, dtf);


            //gets the zoneId of the user
            ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());

            //following sections convert localDateTime to ZonedDateTime objects, first to UTC then to Eastern
            //this is only used to check the start/end times to see if they are in business hours

            //Coverts zoned local start time to UTC time (appStart)
            ZonedDateTime localStart = enteredStartDateTime.atZone(localZoneId);
            ZonedDateTime UTCstart = localStart.withZoneSameInstant(ZoneId.of("UTC"));
            LocalDateTime appStart = UTCstart.toLocalDateTime();

            //Converts zoned local end time to UTC time (appEnd)
            ZonedDateTime localEnd = enteredEndDateTime.atZone(localZoneId);
            ZonedDateTime UTCend = localEnd.withZoneSameInstant(ZoneId.of("UTC"));
            LocalDateTime appEnd = UTCend.toLocalDateTime();

            //Converts UTC start time to Eastern
            ZonedDateTime appStartUTC = appStart.atZone(ZoneId.of("UTC"));
            ZonedDateTime appStartEast = appStartUTC.withZoneSameInstant(ZoneId.of("America/New_York"));
            LocalDateTime appStartEastLocal = appStartEast.toLocalDateTime();

            //Converts UTC end time to Eastern
            ZonedDateTime appEndUTC = appEnd.atZone(ZoneId.of("UTC"));
            ZonedDateTime appEndEast = appEndUTC.withZoneSameInstant(ZoneId.of("America/New_York"));
            LocalDateTime appEndEastLocal = appEndEast.toLocalDateTime();

            //
            LocalTime appStartTime = appStartEastLocal.toLocalTime();
            LocalTime appEndTime = appEndEastLocal.toLocalTime();
            LocalDate appDate = appStartEastLocal.toLocalDate();


            //Gets day of week in Eastern time
            int dayNum = appDate.getDayOfWeek().getValue();

            //checks for blanks
            if(title.isBlank() || description.isBlank() || location.isBlank() || type.isBlank()){
                hasError = true;
                landingPageController.errorDialogBox("Input Error", "Must have inputs for title, description, location, and type.");
            }
            //Checks if app start/end are within eastern hours
            else if (enteredStartDateTime.isEqual(enteredEndDateTime)){
                hasError = true;
                landingPageController.errorDialogBox("Input Error", "Start and end times must be different.");
            }
            else if (appStartTime.getHour() < 8 || appEndTime.getHour() > 22 || dayNum >= 6) {
                hasError = true;
                landingPageController.errorDialogBox("Input Error", "Appointment is scheduled outside of business hours. Appointment must be between 8am-10pm, M-F, Eastern.");
            }
            //checks to see if dropdowns are selected
            else if (contactName == null || custId == 0 || userId == 0) {
                hasError = true;
                landingPageController.errorDialogBox("Input Error", "Contact, Customer, and User must be selected from dropdowns.");
            } else {
                hasError = false;
            }



            /* UPDATED FOR SUBMISSION 2
             * Had an error where a customer with no exiting appointments bypassed error handling.
             * Fixed with if statement to check for null appointment list.
             */

            if(DBQueries.getUserAppointments(custId).isEmpty()){
                //If the appointment list is empty, customer has no existing appointments
                //Only need to check if entered times are appropriate business hours
                hasOverlap = false;
            }


            for (Appointment app : DBQueries.getUserAppointments(custId)) {
                //Looping through each appointment object to check
                //Gets start/end times from appointment objects (local time)
                LocalDateTime searchAppStart = app.getStart();
                LocalDateTime searchAppEnd = app.getEnd();

                if(enteredStartDateTime.isEqual(searchAppStart) && app.getId() != id || enteredEndDateTime.isEqual(searchAppEnd) && app.getId() != id){
                    hasOverlap = true;
                    landingPageController.errorDialogBox("Input Error", "Appointment start time overlaps other appointments for this customer.");
                }
                else if (enteredStartDateTime.isAfter(searchAppStart) && enteredStartDateTime.isBefore(searchAppEnd) && app.getId() != id) {
                    hasOverlap = true;
                    landingPageController.errorDialogBox("Input Error", "Appointment start time overlaps other appointments for this customer.");
                }
                else if (enteredEndDateTime.isAfter(searchAppStart) && enteredEndDateTime.isBefore(searchAppEnd) && app.getId() != id || enteredEndDateTime.isEqual(searchAppStart) && app.getId() != id) {
                    hasOverlap = true;
                    landingPageController.errorDialogBox("Input Error", "Appointment end time overlaps other appointments for this customer.");
                }
                else if (enteredStartDateTime.isBefore(searchAppEnd) && enteredEndDateTime.isAfter(searchAppEnd) && app.getId() != id) {
                    hasOverlap = true;
                    landingPageController.errorDialogBox("Input Error", "Appointment start and end time overlaps other appointments for this customer.");
                }
                else if (enteredStartDateTime.isEqual(searchAppEnd) && app.getId() != id || enteredEndDateTime.isEqual(searchAppStart) && app.getId() != id) {
                    hasOverlap = true;
                    landingPageController.errorDialogBox("Input Error", "Appointment start and end times cannot overlap.");
                }
                else{
                    hasOverlap = false;
                }
            }

            /* UPDATED FOR SUBMISSION #2
             * Originally, code changed user inputted time & dates to UTC time for entry into DB.
             * MySQL assumes this is local timezone and converts to UTC.
             * Essentially, I was converting to UTC twice. Once in code, then once from MySQL.
             * Changed from "appStart" and "appEnd" to "enteredAppStart" and "enteredAppEnd"
             *
             */

            if (!hasError && !hasOverlap) {

                if(landingPageController.confirmDialogBox("Confirmation", "Do you want to save your changes?")){

                    //Update#3 - added Timestamp.valueOf to enteredStartDateTime and enteredEndDateTime. Previously the code inserted the localDateTime objects into the SQL.
                    String sql = String.format("INSERT INTO appointments VALUES(%d, '%s', '%s', '%s', '%s', '%s', '%s', NOW(), '%s', NOW(), '%s', %d, %d, %d)", id, title, description, location, type, Timestamp.valueOf(enteredStartDateTime), Timestamp.valueOf(enteredEndDateTime), userId, userId, custId, userId, contactId);

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
        }

    }

    /** Generates sequential IDs.
     *
     * @return
     */
    public static int autoGenID() {
        int appID = 1;
        for (int i = 0; i < DBQueries.getAllAppointments().size(); i++) {
            appID++;
        }
        return appID;
    }


}
