package clinic.controller;

import clinic.model.mainclasses.Doctor;
import clinic.model.util.Radiology;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.format.DateTimeFormatter;

public class ScheduleController extends ClinicManagerController {

    public TextField patientFirstName;
    public TextField patientLastName;
    public DatePicker appointmentDate;
    public DatePicker patientDOB;
    public Button scheduleAppointmentButton;
    public ToggleGroup typeOfAppointment;
    public Button cancelAppointmentButton;
    public Button clearAllButton;
    public TextArea outputTextArea;

    @FXML
    private ComboBox<String> timeslotComboBox;
    @FXML
    private ComboBox<String> providerComboBox;
    @FXML
    private ComboBox<String> imagingComboBox;
    @FXML
    private RadioButton officeRadioButton;
    @FXML
    private RadioButton imagingRadioButton;


    @FXML
    public void initialize() {

        providerComboBox.setDisable(true);
        imagingComboBox.setDisable(true);

        // Add listeners to the radio buttons to enable/disable combo boxes
        officeRadioButton.setOnAction(event -> updateComboBoxAvailability());
        imagingRadioButton.setOnAction(event -> updateComboBoxAvailability());

        // Initial state based on default radio button selection
        updateComboBoxAvailability();

        populateTimeslots();
        populateProviders();
        populateImagingServices();
    }

    private void updateComboBoxAvailability() {
        if (officeRadioButton.isSelected()) {
            providerComboBox.setDisable(false);
            imagingComboBox.setDisable(true);
        } else if (imagingRadioButton.isSelected()) {
            providerComboBox.setDisable(true);
            imagingComboBox.setDisable(false);
        }
    }

    // Populate ComboBox with predefined timeslots
    private void populateTimeslots() {
        timeslotComboBox.setItems(FXCollections.observableArrayList(
                "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM",
                "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM"
        ));
    }

    // Populate ComboBox with provider list
    private void populateProviders() {
        providerComboBox.setItems(FXCollections.observableArrayList(
                "ANDREW PATEL FAMILY (01)", "RACHEL LIM PEDIATRICIAN (23)", "MONICA ZIMNES FAMILY (11)",
                "JOHN HARPER FAMILY (32)", "TOM KAUR ALLERGIST (54)", "ERIC TAYLOR PEDIATRICIAN (91)", "BEN RAMESH ALLERGIST (39)",
                "JUSTIN CERAVOLO PEDIATRICIAN (09)", "GARY JOHNSON FAMILY (85)", "BEN JERRY FAMILY (77)"
        ));
    }

    // Populate ComboBox with imaging technician list
    private void populateImagingServices() {
        imagingComboBox.setItems(FXCollections.observableArrayList(
                "CATSCAN", "ULTRASOUND", "XRAY"
        ));
    }

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    // Handle scheduling based on appointment type
    public void scheduleAppointmentButton(ActionEvent actionEvent) {

        if (officeRadioButton.isSelected()) {
            // Collect input values from form fields
            String[] parts = new String[7];
            parts[1] = appointmentDate.getValue() != null ? appointmentDate.getValue().format(dateFormatter) : "";
            parts[2] = getTimeslotCase(timeslotComboBox.getValue()); // Convert time to corresponding timeslot case
            parts[3] = patientFirstName.getText().trim();
            parts[4] = patientLastName.getText().trim();
            parts[5] = patientDOB.getValue() != null ? patientDOB.getValue().format(dateFormatter) : "";
            parts[6] = providerComboBox.getValue() != null ? extractNPI(providerComboBox.getValue()) : "";

            // Extract NPI from providerComboBox
            String selectedProvider = providerComboBox.getValue() != null ? providerComboBox.getValue().toString() : "";
            String npi = extractNPI(selectedProvider);

            // Find the doctor by NPI
            Doctor selectedDoctor = findDoctorByNPI(npi);

            if (selectedDoctor != null) {
                // Use selectedDoctor information
                parts[6] = npi;  // Use NPI in parts for further processing if needed

                // Validate fields for office appointment
                if (fieldsAreValidForOffice(parts)) {
                    handleDoctorAppointment(parts);
                } else {
                    outputTextArea.setText("Please fill out all required fields for an office appointment.");
                }
            } else {
                outputTextArea.setText("Selected provider not found. Please select a valid provider.");
            }
        }
        else if (imagingRadioButton.isSelected()) {
            // Collect input values from form fields
            String[] parts = new String[7];
            parts[1] = appointmentDate.getValue() != null ? appointmentDate.getValue().format(dateFormatter) : "";
            parts[2] = getTimeslotCase(timeslotComboBox.getValue()); // Convert time to corresponding timeslot case
            parts[3] = patientFirstName.getText().trim();
            parts[4] = patientLastName.getText().trim();
            parts[5] = patientDOB.getValue() != null ? patientDOB.getValue().format(dateFormatter) : "";
            parts[6] = imagingComboBox.getValue() != null ? imagingComboBox.getValue().toString() : "";

            // Extract and validate imaging service from imagingComboBox
            String roomTypeStr = imagingComboBox.getValue() != null ? imagingComboBox.getValue().toString() : "";
            Radiology imagingService;
            try {
                imagingService = Radiology.valueOf(roomTypeStr.toUpperCase());
            } catch (IllegalArgumentException e) {
                outputTextArea.setText(roomTypeStr + " - imaging service not provided");
                return;
            }

            // Place the valid room type in parts array for further processing
            parts[6] = imagingService.name(); // Use the validated imaging service name

            // Validate fields for imaging appointment
            if (fieldsAreValidForImaging(parts)) {
                handleTechnicianAppointment(parts);

            } else {
                outputTextArea.setText("Please fill out all required fields for an imaging appointment.");
            }
        }
    }


    public void cancelAppointmentButton(ActionEvent actionEvent) {
        // Collect input values from form fields for cancellation
        String[] parts = new String[7];
        parts[1] = appointmentDate.getValue() != null ? appointmentDate.getValue().format(dateFormatter) : "";
        parts[2] = getTimeslotCase(timeslotComboBox.getValue());
        parts[3] = patientFirstName.getText().trim();
        parts[4] = patientLastName.getText().trim();
        parts[5] = patientDOB.getValue() != null ? patientDOB.getValue().format(dateFormatter) : "";

        // Validate inputs to ensure they are all provided
        if (fieldsAreValidForCancellation(parts)) {
            handleCancelAppointment(parts);
        } else {
            outputTextArea.setText("Please fill out all required fields for canceling an appointment.");
        }
    }

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

    private boolean fieldsAreValidForCancellation(String[] parts) {
        return !parts[1].isEmpty() && !parts[2].isEmpty() && !parts[3].isEmpty() &&
                !parts[4].isEmpty() && !parts[5].isEmpty();
    }

    // Helper to validate office appointment fields
    private boolean fieldsAreValidForOffice(String[] parts) {
        return !parts[1].isEmpty() && !parts[2].isEmpty() && !parts[3].isEmpty() &&
                !parts[4].isEmpty() && !parts[5].isEmpty() && !parts[6].isEmpty();
    }

    // Helper to validate imaging appointment fields
    private boolean fieldsAreValidForImaging(String[] parts) {
        return !parts[1].isEmpty() && !parts[2].isEmpty() && !parts[3].isEmpty() &&
                !parts[4].isEmpty() && !parts[5].isEmpty();
    }

    // Extract NPI number from provider's name
    private String extractNPI(String provider) {
        int start = provider.indexOf('(');
        int end = provider.indexOf(')');
        return (start != -1 && end != -1) ? provider.substring(start + 1, end) : "";
    }

    public void clearAllButton(ActionEvent actionEvent) {
        patientFirstName.clear();
        patientLastName.clear();
        patientFirstName.clear();
        patientLastName.clear();

        // Reset date pickers
        appointmentDate.setValue(null);
        patientDOB.setValue(null);

        // Reset combo boxes
        timeslotComboBox.getSelectionModel().clearSelection();
        providerComboBox.getSelectionModel().clearSelection();

        // Reset toggle group (radio buttons)
        typeOfAppointment.selectToggle(null);

        outputTextArea.clear();

    }
}