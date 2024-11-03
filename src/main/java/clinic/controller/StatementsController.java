package clinic.controller;

import clinic.model.mainclasses.Patient;
import clinic.model.mainclasses.Provider;
import clinic.model.mainclasses.Appointment;
import clinic.model.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class StatementsController extends ClinicManagerController {
    public Button displayProviderStatement;
    public Button displayPatientStatement;

    @FXML
    private TableColumn<ProviderCreditStatement, String> providerFirstName;
    @FXML
    private TableColumn<ProviderCreditStatement, String> providerLastName;
    @FXML
    private TableColumn<ProviderCreditStatement, String> providerDOB;
    @FXML
    private TableColumn<ProviderCreditStatement, Double> totalCredit;
    @FXML
    private TableColumn<PatientBillStatement, String> patientFirstName;
    @FXML
    private TableColumn<PatientBillStatement, String> patientLastName;
    @FXML
    private TableColumn<PatientBillStatement, String> patientDOB;
    @FXML
    private TableColumn<PatientBillStatement, Double> totalBill;

    @FXML
    private TableView<ProviderCreditStatement> providerTable;
    @FXML
    private TableView<PatientBillStatement> patientTable;

    // ObservableList to hold provider data with calculated credit statements
    private final ObservableList<ProviderCreditStatement> providerData = FXCollections.observableArrayList();
    private final ObservableList<PatientBillStatement> patientData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up the table columns to use ProviderCreditStatement properties
        providerFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        providerLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        providerDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        totalCredit.setCellValueFactory(new PropertyValueFactory<>("creditAmount"));

        patientFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        patientLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        patientDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        totalBill.setCellValueFactory(new PropertyValueFactory<>("billAmount"));

        // Assign the data to the TableView
        providerTable.setItems(providerData);
        patientTable.setItems(patientData);
    }

    public void displayProviderStatement(ActionEvent actionEvent) {
        loadProviderCreditStatements(); // Load and display provider statements
    }


    private void loadProviderCreditStatements() {
        for (Provider provider : getProvidersList()) {
            double totalCreditAmount = getTotalBilling(getAppointmentsList(), provider); // Calculate total billing
            ProviderCreditStatement statement = new ProviderCreditStatement(
                    provider.getProfile().getFname(),
                    provider.getProfile().getLname(),
                    provider.getProfile().getDob().toString(),
                    totalCreditAmount
            );
            providerData.add(statement);
        }
        providerData.clear();  // Clear existing data
    }

    public void displayPatientStatement(ActionEvent actionEvent) {
        loadPatientBillingStatements(); // Load and display patient billing statements
    }

    private void loadPatientBillingStatements() {
        System.out.println(getAppointmentsList().size());
        for (Appointment apt : getAppointmentsList()) {
            Patient patient = apt.getPatient();
            if (patient == null) {
                continue;
            }
            // Assuming getPatientsList() gives the list of all patients
            double totalBillAmount = calculatePatientTotalBill(getAppointmentsList(), patient); // Calculate total bill
            PatientBillStatement statement = new PatientBillStatement(
                    patient.getProfile().getFname(),
                    patient.getProfile().getLname(),
                    patient.getProfile().getDob().toString(),
                    totalBillAmount
            );

            patientData.add(statement);
        }

        patientData.clear();  // Clear existing data
    }
    private double calculatePatientTotalBill(List<Appointment> appointmentList, Patient patient) {
        double totalBill = 0;
        for (Appointment appointment : appointmentList) {
            if (appointment.getPatient().equals(patient)) {
                totalBill += appointment.getProvider().rate();  // Add cost per visit to total bill
            }
        }
        return totalBill;
    }

}