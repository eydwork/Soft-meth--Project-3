package clinic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ClinicController {


    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    private MenuItem switchSceneToClinicView;

    @FXML
    private void switchSceneToClinicView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clinic/clinic-view.fxml"));
            Parent newRoot = loader.load();

            Stage stage = (Stage) switchSceneToClinicView.getParentPopup().getOwnerWindow();

            stage.setScene(new Scene(newRoot));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private MenuItem switchSceneToScheduleCancelAppointmentView;

    @FXML
    public void switchSceneToScheduleCancelAppointmentView(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clinic/scheduleCancelAppointment-view.fxml"));
            Parent newRoot = loader.load();

            Stage stage = (Stage) switchSceneToScheduleCancelAppointmentView.getParentPopup().getOwnerWindow();

            stage.setScene(new Scene(newRoot));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private MenuItem switchSceneToRescheduleAppointmentView;

    @FXML
    public void switchSceneToRescheduleAppointmentView(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/clinic/rescheduleAppointment-view.fxml"));
            Parent newRoot = loader.load();

            Stage stage = (Stage) switchSceneToScheduleCancelAppointmentView.getParentPopup().getOwnerWindow();

            stage.setScene(new Scene(newRoot));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sortByLocation(ActionEvent actionEvent) {
    }

    public void sortByDateTimeProvider(ActionEvent actionEvent) {
    }

    public void sortByOfficeVisit(ActionEvent actionEvent) {
    }

    public void sortByImaging(ActionEvent actionEvent) {
    }

    public void patientStatement(ActionEvent actionEvent) {
    }

    public void providerStatement(ActionEvent actionEvent) {
    }
}
