package clinic.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.format.DateTimeFormatter;

public class RescheduleController extends ClinicManagerController{

    public TextField patientFirstName;
    public TextField patientLastName;
    public DatePicker appointmentDate;
    public DatePicker patientDOB;
    public Button rescheduleAppointmentButton;
    public Button clearAllButton;
    public TextArea outputTextArea;

    @FXML
    private ComboBox<String> originalTimeslotComboBox;
    @FXML
    private ComboBox<String> newTimeslotComboBox;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    @FXML
    public void initialize() {
        populateTimeslots();
    }

    private void populateTimeslots() {
        originalTimeslotComboBox.setItems(FXCollections.observableArrayList(
                "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM",
                "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM"
        ));
        newTimeslotComboBox.setItems(FXCollections.observableArrayList(
                "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM",
                "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM"
        ));
    }

    public void rescheduleAppointmentButton(ActionEvent actionEvent) {
        // Initialize the parts array to store input values
        String[] parts = new String[7];

        // Collect original appointment date, timeslot, and patient information
        parts[1] = appointmentDate.getValue() != null ? appointmentDate.getValue().format(dateFormatter) : "";
        parts[2] = getTimeslotCase(originalTimeslotComboBox.getValue());
        parts[3] = patientFirstName.getText().trim();
        parts[4] = patientLastName.getText().trim();
        parts[5] = patientDOB.getValue() != null ? patientDOB.getValue().format(dateFormatter) : "";

        // Collect new timeslot for rescheduling
        parts[6] = newTimeslotComboBox.getValue() != null ? getTimeslotCase(newTimeslotComboBox.getValue()) : "";

        // Validate all fields for rescheduling
        if (!fieldsAreValidForRescheduling(parts)) {
            outputTextArea.setText("Please fill out all required fields to reschedule an appointment.");
            return;
        }

        // Call handleRescheduleAppointment with the collected inputs
        handleRescheduleAppointment(parts);
    }

    // Helper method to validate rescheduling fields
    private boolean fieldsAreValidForRescheduling(String[] parts) {
        return !parts[1].isEmpty() && !parts[2].isEmpty() && !parts[3].isEmpty() &&
                !parts[4].isEmpty() && !parts[5].isEmpty() && !parts[6].isEmpty();
    }

    // Convert timeslot from ComboBox to corresponding case number
    private String getTimeslotCase(String time) {
        return switch (time) {
            case "9:00 AM" -> "1";
            case "9:30 AM" -> "2";
            case "10:00 AM" -> "3";
            case "10:30 AM" -> "4";
            case "11:00 AM" -> "5";
            case "11:30 AM" -> "6";
            case "2:00 PM" -> "7";
            case "2:30 PM" -> "8";
            case "3:00 PM" -> "9";
            case "3:30 PM" -> "10";
            case "4:00 PM" -> "11";
            case "4:30 PM" -> "12";
            default -> ""; // Return empty if no matching time is found
        };
    }

    // Clear all input fields
    public void clearAllButton(ActionEvent actionEvent) {
        patientFirstName.clear();
        patientLastName.clear();
        appointmentDate.setValue(null);
        patientDOB.setValue(null);
        originalTimeslotComboBox.getSelectionModel().clearSelection();
        newTimeslotComboBox.getSelectionModel().clearSelection();
        outputTextArea.clear();
    }
}
