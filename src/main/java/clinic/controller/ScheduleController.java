package clinic.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ScheduleController extends ClinicController {

    public TextField patientFirstName;
    public TextField patientLastName;
    public DatePicker appointmentDate;
    public DatePicker patientDOB;
    public Button scheduleAppointmentButton;
    public ToggleGroup typeOfAppointment;
    public Button cancelAppointmentButton;
    public Button clearAllButton;


    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private DatePicker dobPicker;
    @FXML
    private DatePicker appointmentDatePicker;
    @FXML
    private ComboBox<String> timeslotComboBox;
    @FXML
    private ComboBox<String> providerComboBox;
    @FXML
    private RadioButton officeRadioButton;
    @FXML
    private RadioButton imagingRadioButton;
    @FXML
    private Button scheduleButton;

    @FXML
    public void initialize() {
        populateTimeslots();
        populateProviders();

        // Listen to the type of appointment selection to enable/disable provider ComboBox
        typeOfAppointment.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            providerComboBox.setDisable(newValue == imagingRadioButton);
        });

        // Set initial state of provider ComboBox based on the default selection
        providerComboBox.setDisable(!officeRadioButton.isSelected());
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
                "ANDREW PATEL (01)", "RACHEL LIM (23)", "MONICA ZIMNES (11)",
                "JOHN HARPER (32)", "TOM KAUR (54)", "ERIC TAYLOR (91)", "BEN RAMESH (39)", "JUSTIN CERAVOLO (09)",
                "GARY JOHNSON (85)", "BEN JERRY (77)"
        ));
    }

    // Handle scheduling based on appointment type
    public void scheduleAppointmentButton(ActionEvent actionEvent) {
        // Collect input values from form fields
        String[] parts = new String[7];
        parts[1] = appointmentDatePicker.getValue() != null ? appointmentDatePicker.getValue().toString() : "";
        parts[2] = timeslotComboBox.getValue();
        parts[3] = firstNameField.getText().trim();
        parts[4] = lastNameField.getText().trim();
        parts[5] = dobPicker.getValue() != null ? dobPicker.getValue().toString() : "";

        // Validate inputs based on the selected appointment type
        if (officeRadioButton.isSelected()) {
            parts[6] = providerComboBox.getValue() != null ? extractNPI(providerComboBox.getValue()) : "";
            if (fieldsAreValidForOffice(parts)) {
                handleDoctorAppointment(parts);
            } else {
                System.out.println("Please fill out all required fields for an office appointment.");
            }
        } else if (imagingRadioButton.isSelected()) {
            if (fieldsAreValidForImaging(parts)) {
                handleTechnicianAppointment(parts);
            } else {
                System.out.println("Please fill out all required fields for an imaging appointment.");
            }
        } else {
            System.out.println("Please select an appointment type.");
        }
    }

    public void cancelAppointmentButton(ActionEvent actionEvent) {
        // Collect input values from form fields for cancellation
        String[] parts = new String[7];
        parts[1] = appointmentDatePicker.getValue() != null ? appointmentDatePicker.getValue().toString() : "";
        parts[2] = timeslotComboBox.getValue();
        parts[3] = firstNameField.getText().trim();
        parts[4] = lastNameField.getText().trim();
        parts[5] = dobPicker.getValue() != null ? dobPicker.getValue().toString() : "";

        // Validate inputs based on the selected appointment type
        if (officeRadioButton.isSelected()) {
            parts[6] = providerComboBox.getValue() != null ? extractNPI(providerComboBox.getValue()) : "";
            if (fieldsAreValidForOffice(parts)) {
                handleCancelAppointment(parts); // Calls handleCancelAppointment for office appointment
            } else {
                System.out.println("Please fill out all required fields for an office appointment cancellation.");
            }
        } else if (imagingRadioButton.isSelected()) {
            if (fieldsAreValidForImaging(parts)) {
                handleCancelAppointment(parts); // Calls handleCancelAppointment for imaging appointment
            } else {
                System.out.println("Please fill out all required fields for an imaging appointment cancellation.");
            }
        } else {
            System.out.println("Please select an appointment type.");
        }
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
        firstNameField.clear();
        lastNameField.clear();
        patientFirstName.clear();
        patientLastName.clear();

        // Reset date pickers
        appointmentDatePicker.setValue(null);
        dobPicker.setValue(null);

        // Reset combo boxes
        timeslotComboBox.getSelectionModel().clearSelection();
        providerComboBox.getSelectionModel().clearSelection();

        // Reset toggle group (radio buttons)
        typeOfAppointment.selectToggle(null);

    }
}