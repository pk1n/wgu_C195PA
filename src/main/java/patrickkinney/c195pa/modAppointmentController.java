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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.TimeZone;


/**Controller class for modifying appointments.
 *
 */
public class modAppointmentController {

    @FXML
    private DatePicker endDate;

    @FXML
    private ComboBox<String> endHour;

    @FXML
    private ComboBox<String> endMin;

    @FXML
    private Button modAppCancelButton;

    @FXML
    private ComboBox<String> modAppContactCombo;

    @FXML
    private ComboBox<Integer> modAppCustIDCombo;

    @FXML
    private TextArea modAppDescripTextArea;

    @FXML
    private TextField modAppIDTextfield;

    @FXML
    private TextField modAppLocationTextfield;

    @FXML
    private Button modAppSaveButton;

    @FXML
    private TextField modAppTitleTextfield;

    @FXML
    private TextField modAppTypeTextfield;

    @FXML
    private ComboBox<Integer> modAppUserIDCombo;

    @FXML
    private DatePicker startDate;

    @FXML
    private ComboBox<String> startHour;

    @FXML
    private ComboBox<String> startMin;


    static Appointment recievedApp;

    /**Initializes the modify appointment controller.
     *
     * Fills form field with the selected appointment. Auto-populates combo-box choices.
     *
     */
    public void initialize(){
        modAppContactCombo.getItems().addAll(DBQueries.contactList());
        modAppUserIDCombo.getItems().addAll(DBQueries.userList());
        modAppCustIDCombo.getItems().addAll(DBQueries.custIDList());


        modAppIDTextfield.setText(String.valueOf(recievedApp.getId()));
        modAppTitleTextfield.setText(String.valueOf(recievedApp.getTitle()));
        modAppDescripTextArea.setText(String.valueOf(recievedApp.getDescription()));
        modAppLocationTextfield.setText(String.valueOf(recievedApp.getLocation()));
        modAppTypeTextfield.setText(String.valueOf(recievedApp.getType()));

        startDate.setValue(recievedApp.getStart().toLocalDate());
        startHour.setValue(String.format("%02d", recievedApp.getStart().getHour()));
        startMin.setValue(String.format("%02d", recievedApp.getStart().getMinute()));

        endDate.setValue(recievedApp.getEnd().toLocalDate());
        endHour.setValue(String.format("%02d", recievedApp.getEnd().getHour()));
        endMin.setValue(String.format("%02d", recievedApp.getEnd().getMinute()));

        modAppContactCombo.setValue(recievedApp.getContactName());
        modAppCustIDCombo.setValue(recievedApp.getCustomerId());
        modAppUserIDCombo.setValue(recievedApp.getUserId());

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

    /**Takes the user back to the main landing page upon user confirmation.
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

    /**Saves modified customer to database.
     *
     * Input validation and error handling. Converts local time to UTC/Eastern for storage and validation.
     *
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void onActionSaveAppointment(ActionEvent event) throws SQLException, IOException {

        boolean hasError = true;
        boolean hasOverlap = true;

        int id = Integer.parseInt(modAppIDTextfield.getText());
        String title = modAppTitleTextfield.getText();
        String description = modAppDescripTextArea.getText();
        String location = modAppLocationTextfield.getText();
        String type = modAppTypeTextfield.getText();
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
        } else if (modAppContactCombo.getValue() == null || modAppCustIDCombo.getValue() == null || modAppUserIDCombo.getValue() == null) {
            landingPageController.errorDialogBox("Input Error", "Contact, Customer, and User must be selected from dropdowns.");
        } else {

            String contactName = modAppContactCombo.getValue();
            int contactId = DBQueries.determineContactId(contactName);
            int custId = modAppCustIDCombo.getValue();
            int userId = modAppUserIDCombo.getValue();


            String start = String.format(sH + ":" + sM);
            String end = String.format(eH + ":" + eM);
            String sDate = sD.toString();
            String eDate = eD.toString();
            String sDateTime = String.format(sDate + " " + start);
            String eDateTime = String.format(eDate + " " + end);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime enteredStartDateTime = LocalDateTime.parse(sDateTime, dtf);
            LocalDateTime enteredEndDateTime = LocalDateTime.parse(eDateTime, dtf);


            ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());

            ZonedDateTime localStart = enteredStartDateTime.atZone(localZoneId);
            ZonedDateTime UTCstart = localStart.withZoneSameInstant(ZoneId.of("UTC"));
            LocalDateTime appStart = UTCstart.toLocalDateTime();

            ZonedDateTime localEnd = enteredEndDateTime.atZone(localZoneId);
            ZonedDateTime UTCend = localEnd.withZoneSameInstant(ZoneId.of("UTC"));
            LocalDateTime appEnd = UTCend.toLocalDateTime();

            ZonedDateTime appStartUTC = appStart.atZone(ZoneId.of("UTC"));
            ZonedDateTime appStartEast = appStartUTC.withZoneSameInstant(ZoneId.of("America/New_York"));
            LocalDateTime appStartEastLocal = appStartEast.toLocalDateTime();

            ZonedDateTime appEndUTC = appEnd.atZone(ZoneId.of("UTC"));
            ZonedDateTime appEndEast = appEndUTC.withZoneSameInstant(ZoneId.of("America/New_York"));
            LocalDateTime appEndEastLocal = appEndEast.toLocalDateTime();

            LocalTime appStartTime = appStartEastLocal.toLocalTime();
            LocalTime appEndTime = appEndEastLocal.toLocalTime();
            LocalDate appDate = appStartEastLocal.toLocalDate();

            int dayNum = appDate.getDayOfWeek().getValue();

            if (title.isBlank() || description.isBlank() || location.isBlank() || type.isBlank()) {
                hasError = true;
                landingPageController.errorDialogBox("Input Error", "Must have inputs for title, description, location, and type.");
            }
            else if (appStartTime.getHour() < 8 || appEndTime.getHour() > 22 || dayNum >= 6) {
                hasError = true;
                landingPageController.errorDialogBox("Input Error", "Appointment is scheduled outside of business hours. Appointment must be between 8am-10pm, M-F, Eastern.");
            } else if (contactName == null || custId == 0 || userId == 0) {
                hasError = true;
                landingPageController.errorDialogBox("Input Error", "Contact, Customer, and User must be selected from dropdowns.");
            } else {
                hasError = false;
            }


            for (Appointment app : DBQueries.getUserAppointments(custId)) {
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
                else if (enteredStartDateTime.isEqual(enteredEndDateTime)) {
                   hasOverlap = true;
                   landingPageController.errorDialogBox("Input Error", "Appointment start and end times must be different.");
               } else{
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
                    String sql = String.format("UPDATE appointments SET Title = '%s', Description = '%s', Location = '%s', Type = '%s', Start = '%s', END = '%s', Last_Update = NOW(), Customer_ID = '%d', User_ID = '%d', Contact_ID = '%d' WHERE Appointment_ID = '%d'", title, description, location, type, enteredStartDateTime, enteredEndDateTime, custId, userId, contactId, id);
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


    /**Populates an appointment object with the user selected item from the main landing page.
     *
     * @param app
     */

    public static void sentApp (Appointment app){
        recievedApp = app;
    }

}
