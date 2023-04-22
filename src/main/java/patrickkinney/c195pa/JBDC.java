package patrickkinney.c195pa;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**Abstract Class to handle connection to database.
 *
 */
public abstract class JBDC {

    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static final String password = "Passw0rd!"; // Password
    public static Connection connection;  // Connection Interface

    /**Opens connection to SQL database.
     *
     */
    public static void openConnection() {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        } catch (Exception e) {
            //System.out.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**Returns connection status.
     *
     * @return
     */
    public static Connection getConnection() {
        return connection;
    }

    /**Closes connection to SQL database.
     *
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }


}
