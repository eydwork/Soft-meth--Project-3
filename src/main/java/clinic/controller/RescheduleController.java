package clinic.controller;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RescheduleController extends ClinicController {

    public TextField patientFirstName;
    public TextField patientLastName;
    public DatePicker appointmentDate;
    public DatePicker patientDOB;
    public Button rescheduleAppointmentButton;
    public Button clearAllButton;
    public ComboBox originalTimeslotComboBox;
    public ComboBox newTimeslotComboBox;
    private Stage stage;
    private Scene scene;
    private Parent root;


    public void rescheduleAppointmentButton(ActionEvent actionEvent) {
        // Collect input values from form fields
        String[] parts = new String[7];

        // Retrieve and trim data from text fields and date pickers
        parts[1] = appointmentDate.getValue() != null ? appointmentDate.getValue().toString() : "";
        parts[2] = (String) originalTimeslotComboBox.getValue();
        parts[3] = patientFirstName.getText().trim();
        parts[4] = patientLastName.getText().trim();
        parts[5] = patientDOB.getValue() != null ? patientDOB.getValue().toString() : "";
        parts[6] = (String) newTimeslotComboBox.getValue();

        // Validate inputs to ensure they are all provided
        if (fieldsAreValidForRescheduling(parts)) {
            handleRescheduleAppointment(parts);
        } else {
            System.out.println("Please fill out all required fields for rescheduling an appointment.");
        }
    }

    // Helper method to validate rescheduling fields
    private boolean fieldsAreValidForRescheduling(String[] parts) {
        return !parts[1].isEmpty() && !parts[2].isEmpty() && !parts[3].isEmpty() &&
                !parts[4].isEmpty() && !parts[5].isEmpty() && !parts[6].isEmpty();
    }

    // Clear all input fields
    public void clearAllButton(ActionEvent actionEvent) {
        patientFirstName.clear();
        patientLastName.clear();
        appointmentDate.setValue(null);
        patientDOB.setValue(null);
        originalTimeslotComboBox.getSelectionModel().clearSelection();
        newTimeslotComboBox.getSelectionModel().clearSelection();
        System.out.println("All fields have been cleared.");
    }
}
