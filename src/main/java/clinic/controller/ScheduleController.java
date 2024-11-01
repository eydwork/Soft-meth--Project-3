package clinic.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ScheduleController extends ClinicController {

    public TextField patientFirstName;
    public TextField patientLastName;
    public DatePicker appointmentDate;
    public DatePicker patientDOB;
    public MenuBar menuBar;
    public Menu menuAppointment;
    public Menu appointmentList;
    public MenuItem listByLocation;
    public MenuItem listByDateTimeProvider;
    public MenuItem officeVisitsList;
    public MenuItem imagingServiceList;
    public Menu statements;
    public MenuItem patientBillStatement;
    public MenuItem providerCreditStatement;
    public Button scheduleAppointmentButton;
    public ToggleGroup typeOfAppointment;
    public Button cancelAppointmentButton;
    private Stage stage;
    private Scene scene;
    private Parent root;

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

        typeOfAppointment.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == officeRadioButton) {
                providerComboBox.setDisable(false); // Enable the ComboBox for office appointments
            } else if (newValue == imagingRadioButton) {
                providerComboBox.setDisable(true); // Disable the ComboBox for imaging appointments
            }
        });

        // Initialize the ComboBox state based on the default selection
        providerComboBox.setDisable(!officeRadioButton.isSelected());

    }

    // Method to populate the ComboBox with predefined timeslots
    private void populateTimeslots() {
        // Define the list of timeslots
        timeslotComboBox.setItems(FXCollections.observableArrayList(
                "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM",
                "2:00 PM", "2:30 PM", "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM"
        ));
    }

    private void populateProviders() {

        providerComboBox.setItems(FXCollections.observableArrayList(
                "ANDREW PATEL (01)", "RACHEL LIM (23)", "MONICA ZIMNES (11)",
                "JOHN HARPER (32)", "TOM KAUR (54)", "ERIC TAYLOR (91)", "BEN RAMESH (39)", "JUSTIN CERAVOLO (09)",
                "GARY JOHNSON (85)", "BEN JERRY (77)"
        ));
    }




    public void scheduleAppointmentButton(ActionEvent actionEvent) {
    }

    public void cancelAppointmentButton(ActionEvent actionEvent) {
    }
}