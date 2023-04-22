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

import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**Controller class for login page.
 *
 */
public class loginController {
    @FXML
    private TextField enterPassword;

    @FXML
    private TextField enterUsername;

    @FXML
    private Button mainExitButton;

    @FXML
    private Button mainLoginButton;

    @FXML
    private Label mainTitle;

    @FXML
    private Label passLabel;

    @FXML
    private Label userLocationLabel;

    @FXML
    private Label usernameLabel;

        String user;
        String userLang = String.valueOf(Locale.getDefault().getLanguage());


    /**Initialize page. Sets resource bundle.
     *
     */
        public void initialize () {

                String userLocation = String.valueOf(ZoneId.systemDefault());
                this.userLocationLabel.setText(userLocation);
                ResourceBundle rb = ResourceBundle.getBundle("lang", Locale.getDefault());

                    mainTitle.setText(rb.getString("mainTitle"));
                    usernameLabel.setText(rb.getString("username"));
                    enterUsername.setPromptText(rb.getString("enterUsername"));
                    passLabel.setText(rb.getString("password"));
                    enterPassword.setPromptText(rb.getString("enterPassword"));
                    mainLoginButton.setText(rb.getString("login"));
                    mainExitButton.setText(rb.getString("exit"));


        }

        @FXML
        void onActionEnterPassword(ActionEvent event) {

        }

        @FXML
        void onActionEnterUserName(ActionEvent event) {

        }

    /**Exits application upon user confirmation.
     *
     * @param event
     */
        @FXML
        void onActionExitMainForm(ActionEvent event) {
            ResourceBundle rb = ResourceBundle.getBundle("lang", Locale.getDefault());

                if(landingPageController.confirmDialogBox(rb.getString("confirmation"), rb.getString("exit?"))){
                        JBDC.closeConnection();
                        System.exit(0);
                }

        }

    /**Logs user into application.
     * Checks and validates username and password with database.
     * Creates login attempt file for tracking.
     *
     * @param event
     * @throws IOException
     */
        @FXML
        void onActionLogin(ActionEvent event) throws IOException {

            ResourceBundle rb = ResourceBundle.getBundle("lang", Locale.getDefault());
            ObservableList<Users> userList = DBQueries.getAllUsers();
            //get entered text, check against User object, either grant or reject
            String enteredUsername = enterUsername.getText();
            String enteredPassword = enterPassword.getText();
            boolean userMatch = false;
            boolean passMatch = true;
            boolean loginState = false;
            boolean loginAttempt = false;
            String loginText = "FAILED";
            ZonedDateTime today = ZonedDateTime.now();


            if(enteredUsername.isEmpty()){
                    landingPageController.errorDialogBox(rb.getString("loginError"), rb.getString("enterUser"));
            }
            else if (enteredPassword.isEmpty()){
                    landingPageController.errorDialogBox(rb.getString("loginError"), rb.getString("enterPass"));
                }
            else {


                    for (Users user : userList) {
                            loginAttempt = true;
                            if (user.getUserName().equals(enteredUsername)) {
                                    userMatch = true;
                                    passMatch = false;
                                    loginAttempt = true;
                                    if (user.getUserPassword().equals(enteredPassword)) {
                                            passMatch = true;
                                            loginAppCheck(user);
                                            loginState = true;
                                            loginAttempt = true;
                                            loginText = "SUCCESS";

                                        }
                                }
                    }

                    if(!userMatch){
                            landingPageController.errorDialogBox(rb.getString("loginError"), rb.getString("incorrectUser"));
                    }
                    if(!passMatch){
                            landingPageController.errorDialogBox(rb.getString("loginError"), rb.getString("incorrectPass"));
                    }
            }


            String logger = String.format("Login Attempt: %s, by %s, on %s \n", loginText, enteredUsername, today);
            FileWriter loginLog = new FileWriter("login_activity.txt", true);
            loginLog.write(logger);
            loginLog.close();


            if(loginState){
                    Stage stage;
                    Parent parent;

                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("landingPage.fxml")));
                    stage.setScene(new Scene(parent));
                    stage.show();
            }
        }

    /**Checks for user appointment within 15mins of login.
     *
     * @param user
     */
        private void loginAppCheck (Users user){
            ResourceBundle rb = ResourceBundle.getBundle("lang", Locale.getDefault());
            ObservableList<Appointment> appList = DBQueries.getAllAppointments();

            boolean hasApp = false;

                for (Appointment app: appList) {
                        if (app.getUserId() == user.getUserId()) {

                                LocalDateTime start = app.getStart();
                                LocalDateTime current = LocalDateTime.now();


                                long minutes = current.until(start, ChronoUnit.MINUTES);

                                if (minutes <= 15 && minutes > 0) {

                                        hasApp = true;

                                        int foundId = app.getId();
                                        LocalDate foundDate = start.toLocalDate();
                                        LocalTime foundTime = start.toLocalTime();

                                        landingPageController.warningDialogBox(rb.getString("warning"), rb.getString("appSoon") + foundId + rb.getString("date") + foundDate + rb.getString("time") + foundTime);
                                }
                        }
                }

                if(hasApp == false){
                    landingPageController.confirmDialogBox(rb.getString("confirmation"), rb.getString("noAppSoon"));
                }
        }




}

