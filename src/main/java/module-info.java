module com.example.project3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens clinic to javafx.fxml, javafx.graphics;
    exports clinic;
}