package clinic.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class ClinicController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private MenuItem homeTab;

    @FXML
    private MenuItem scheduleTab;

    @FXML
    private MenuItem rescheduleTab;

    @FXML
    private MenuItem appointmentByLocationTab;

    @FXML
    private MenuItem appointmentsByDateTimeProviderTab;

    @FXML
    private MenuItem officeVisitAppointments;

    @FXML
    private MenuItem imagingAppointmentsTab;

    @FXML
    private MenuItem statementsTab;

    @FXML
    private void loadPage(String fxmlPath, MenuItem menuItem) {
        try {
            FXMLLoader sceneLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent newRoot = sceneLoader.load();
            Scene scene = new Scene(newRoot);
            Stage stage = (Stage) menuItem.getParentPopup().getOwnerWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void goToHomePage() {
        loadPage("/clinic/clinic-view.fxml", homeTab);
    }

    @FXML
    public void goToSchedulePage() {
        loadPage("/clinic/schedule-view.fxml", scheduleTab);
    }

    @FXML
    public void goToReschedulePage() {
        loadPage("/clinic/reschedule-view.fxml", rescheduleTab);
    }

    @FXML
    public void goToAppointmentByLocationPage() {
        loadPage("/clinic/apt-loc-view.fxml", appointmentByLocationTab);
    }

    @FXML
    public void goToAppointmentByDateTimeProviderPage() {
        loadPage("/clinic/apt-dtp-view.fxml", appointmentsByDateTimeProviderTab);
    }

    @FXML
    public void goToOfficeAppointmentPage() {
        loadPage("/clinic/office-view.fxml", officeVisitAppointments);
    }

    @FXML
    public void goToImagingAppointmentPage() {
        loadPage("/clinic/imaging-view.fxml", imagingAppointmentsTab);
    }

    @FXML
    public void goToStatementsPage() {
        loadPage("/clinic/statements-view.fxml", statementsTab);
    }


    public void listByLocation(ActionEvent actionEvent) {
    }

    public void listByDateTimeProvider(ActionEvent actionEvent) {
    }

    public void officeVisitsList(ActionEvent actionEvent) {
    }

    public void imagingServiceList(ActionEvent actionEvent) {
    }

    public void patientBillStatement(ActionEvent actionEvent) {
    }

    public void providerCreditStatement(ActionEvent actionEvent) {
    }
}
