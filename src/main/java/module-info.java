module com.example.project3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project3 to javafx.fxml, javafx.graphics;
    exports com.example.project3;
}