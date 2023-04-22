package patrickkinney.c195pa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

/**Class used to store most of the methods that interact with the SQL Database.
 *
 */
public class DBQueries {
    /**Gets all customers from the database.
     *
     * @return
     */
    public static ObservableList<Customer> getAllCustomers() {
        ObservableList<Customer> custlist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from customers";
            PreparedStatement ps = JBDC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postal = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");

                String sqlDiv = String.format("SELECT * from first_level_divisions WHERE Division_ID = %s", divisionId);
                PreparedStatement ps2 = JBDC.getConnection().prepareStatement(sqlDiv);
                ResultSet rs2 = ps2.executeQuery();

                while (rs2.next()) {
                    String state = rs2.getString("Division");
                    int countryId = rs2.getInt("Country_ID");

                    String sqlCountry = String.format("SELECT * from countries WHERE Country_ID = %s", countryId);
                    PreparedStatement ps3 = JBDC.getConnection().prepareStatement(sqlCountry);
                    ResultSet rs3 = ps3.executeQuery();

                    while (rs3.next()) {
                        String country = rs3.getString("Country");

                        Customer c = new Customer(id, name, address, postal, phone, divisionId, country, state);
                        custlist.add(c);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return custlist;
    }

    /**Gets all appointments from the database.
     *
     * @return
     */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> applist = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from appointments";
            PreparedStatement ps = JBDC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int custID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");


                /* UPDATED FOR SUBMISSION #2
                 * Originally, code pulled times from MySQL, assumed they were UTC, and converted to local.
                 * Now, code pulls directly as MySQL automatically converts to local timezone.
                 *
                 */

                /* REMOVED FOR SUBMISSION #2
                ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
                ZonedDateTime UTCstart = start.atZone(ZoneId.of("UTC"));
                ZonedDateTime localStart = UTCstart.withZoneSameInstant(localZoneId);
                LocalDateTime appStart = localStart.toLocalDateTime();
                ZonedDateTime UTCend = end.atZone(ZoneId.of("UTC"));
                ZonedDateTime localEnd = UTCend.withZoneSameInstant(localZoneId);
                LocalDateTime appEnd = localEnd.toLocalDateTime();
                 */


                String sqlDiv = String.format("SELECT * from contacts WHERE Contact_ID = %s", contactID);
                PreparedStatement ps2 = JBDC.getConnection().prepareStatement(sqlDiv);
                ResultSet rs2 = ps2.executeQuery();

                while (rs2.next()) {
                    String name = rs2.getString("Contact_Name");

                    Appointment a = new Appointment(id, title, description, location, type, start, end, createDate, createBy, lastUpdate, lastUpdatedBy, custID, userID, contactID, name);
                    applist.add(a);
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return applist;
    }

    /** Gets Appointments for a specific customer.
     *
     * @param custId Customer ID
     * @return
     */
    public static ObservableList<Appointment> getUserAppointments(int custId) {
        ObservableList<Appointment> userAppointments = FXCollections.observableArrayList();

        for (Appointment app : getAllAppointments()) {
            if (custId == app.getCustomerId()) {
                userAppointments.add(app);
            }
        }
        return userAppointments;
    }


    /* ************************ NO LONGER USED, REPLACED WITH LAMBDA **********************************


    public static int appCountsTotal (int custId, int month) throws SQLException {
        int total = 0;

        String sql = String.format("SELECT * from appointments WHERE Customer_ID = %d AND MONTH(Start) = %d", custId, month);
        PreparedStatement ps = JBDC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            total++;
        }
        return total;
    }
    */

    /**Calculated total appointments by location.
     *
     * @param location
     * @return total count of appointments.
     * @throws SQLException
     */
    public static int allLocationsTotal(String location) throws SQLException {
        int total = 0;

        String sql = String.format("SELECT * from appointments WHERE Location = '%s'", location);
        PreparedStatement ps = JBDC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            total++;
        }
        return total;
    }

    /**Calculates total appointments by type.
     *
     * @param type
     * @return total count of appointments.
     * @throws SQLException
     */
    public static int allTypesTotal(String type) throws SQLException {
        int total = 0;

        String sql = String.format("SELECT * from appointments WHERE Type = '%s'", type);
        PreparedStatement ps = JBDC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            total++;
        }
        return total;
    }

    /**Gets all users from database. Stores in user object.
     *
     * @return
     */
    public static ObservableList<Users> getAllUsers() {
        ObservableList<Users> programUsers = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from users";
            PreparedStatement ps = JBDC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("User_ID");
                String name = rs.getString("User_Name");
                String password = rs.getString("Password");

                Users u = new Users(id, name, password);
                programUsers.add(u);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return programUsers;
    }

    /**Gets all countries from database. Stores in country object.
     *
     * @return
     */
    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from countries";
            PreparedStatement ps = JBDC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Country_ID");
                String name = rs.getString("Country");

                Country c = new Country(id, name);
                allCountries.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allCountries;
    }

    /**Gets all divisions. Stores in division object.
     *
     * @return
     */
    public static ObservableList<Division> getAllDivisions() {
        ObservableList<Division> allDivisions = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from first_level_divisions";
            PreparedStatement ps = JBDC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Division_ID");
                String name = rs.getString("Division");
                int countryId = rs.getInt(("Country_ID"));

                Division d = new Division(id, name, countryId);
                allDivisions.add(d);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allDivisions;
    }

    /**Stores all contacts. Stores in contact object.
     *
     * @return
     */
    public static ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * from contacts";
            PreparedStatement ps = JBDC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contact c = new Contact(id, name, email);
                allContacts.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allContacts;
    }

    /**Creates a String list of country names.
     *
     * @return String list of country names.
     */
    public static ObservableList<String> countryList() {
        ObservableList<String> allCountries = FXCollections.observableArrayList();


        for (Country country : getAllCountries()) {
            allCountries.add(country.getCountry());
        }

        return allCountries;
    }

    /**Creates a String list of division names.
     *
     * @return String list of division names.
     */
    public static ObservableList<String> divisionList() {
        ObservableList<String> allDivisions = FXCollections.observableArrayList();


        for (Division division : getAllDivisions()) {
            allDivisions.add(division.getDivision());
        }

        return allDivisions;
    }

    /**Creates a String list of contact names.
     *
     * @return String list of contact names.
     */
    public static ObservableList<String> contactList() {
        ObservableList<String> allContacts = FXCollections.observableArrayList();


        for (Contact contact : getAllContacts()) {
            allContacts.add(contact.getContactName());
        }

        return allContacts;
    }

    /**Creates an Integer list of user IDs.
     *
     * @return Integer list of user IDs.
     */
    public static ObservableList<Integer> userList() {
        ObservableList<Integer> allUsers = FXCollections.observableArrayList();


        for (Users users : getAllUsers()) {
            allUsers.add(users.getUserId());
        }

        return allUsers;
    }

    /**Creates an Integer list of customer IDs.
     *
     * @return Integer list of customer IDs.
     */
    public static ObservableList<Integer> custIDList() {
        ObservableList<Integer> allCustIDs = FXCollections.observableArrayList();


        for (Customer customer : getAllCustomers()) {
            allCustIDs.add(customer.getId());
        }

        return allCustIDs;
    }


    public static ObservableList<Integer> contactIDList() {
        ObservableList<Integer> allContactIDs = FXCollections.observableArrayList();


        for (Contact contact : getAllContacts()) {
            allContactIDs.add(contact.getContactId());
        }

        return allContactIDs;
    }




    /**Creates a String list of appointment types.
     *
     * @return String list of appointment types.
     */
    public static ObservableList<String> appTypeList() {
        ObservableList<String> allAppTypes = FXCollections.observableArrayList();


        for (Appointment app : getAllAppointments()) {
            allAppTypes.add(app.getType());
        }

        return allAppTypes;
    }

    /**Creates a String list of appointment locations.
     *
     * @return String list of appointment locations.
     */
    public static ObservableList<String> appLocationList() {
        ObservableList<String> allAppLocations = FXCollections.observableArrayList();


        for (Appointment app : getAllAppointments()) {
            allAppLocations.add(app.getLocation());
        }

        return allAppLocations;
    }

    /**Creates a String list of Division Names by country.
     *
     * @param countryName
     * @return String list of division names by country.
     */
    public static ObservableList<String> getDivisionList(String countryName) {
        ObservableList<Country> allCountries = getAllCountries();
        ObservableList<String> allDivisions = FXCollections.observableArrayList();
        int countryID = 0;

        try {
            for (Country country : allCountries) {

                if (country.getCountry().equals(countryName)) {
                    int foundId = country.getCountryId();
                    countryID = foundId;
                }
            }
            String sql = "SELECT * from first_level_divisions WHERE Country_ID = " + countryID;
            PreparedStatement ps = JBDC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("Division");

                allDivisions.add(name);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allDivisions;
    }

    /**Determines Division ID from Division Name.
     *
     * @param stateName Division Name.
     * @return Integer Division ID.
     */
    public static int determineDivId(String stateName) {

        int foundDivId = 0;

        try {

            String sql = String.format("SELECT * from first_level_divisions WHERE Division = '%s'", stateName);
            PreparedStatement ps = JBDC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                foundDivId = rs.getInt("Division_ID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return foundDivId;
    }

    /**Determines Contact IDs from Name.
     *
     * @param contactName
     * @return Integer Contact ID.
     */
    public static int determineContactId(String contactName) {

        int foundContactId = 0;

        try {

            String sql = String.format("SELECT * from contacts WHERE Contact_Name = '%s'", contactName);
            PreparedStatement ps = JBDC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                foundContactId = rs.getInt("Contact_ID");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return foundContactId;
    }


}
