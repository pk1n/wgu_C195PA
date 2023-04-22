module C195PA {
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.controls;

    opens patrickkinney.c195pa to javafx.fxml;
    exports patrickkinney.c195pa;
}