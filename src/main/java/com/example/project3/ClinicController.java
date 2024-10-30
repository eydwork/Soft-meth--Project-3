package com.example.project3;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ClinicController {

    @FXML
    private void handleButtonClick() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Appointment");
        alert.setHeaderText(null);
        alert.setContentText("Appointment booked successfully!");
        alert.showAndWait();
    }
}
