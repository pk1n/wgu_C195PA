package patrickkinney.c195pa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** Main Class. This runs the application. */

public class Main extends Application {

    /** Main method.
     * @param args
     */
    public static void main (String[] args) {
        JBDC.openConnection();
        launch(args);
        JBDC.closeConnection();
    }

    /** The start method that loads the login form.
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 400);
        stage.setTitle("Login Screen");
        stage.setScene(scene);
        stage.show();
    }

}