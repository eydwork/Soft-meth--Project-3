package clinic.controller;

import clinic.model.mainclasses.Appointment;
import clinic.model.util.List;
import clinic.model.util.Sort;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/*
*Controller for List of Appointments including the Date, Time, and Provider
*
*@authors Erika Dong, Emily Wong
*/

public class AppointmentByDateTimeProviderController extends ClinicManagerController{

    @FXML
    private TextArea outputTextArea;

    public void displayAppointmentsButton(ActionEvent actionEvent) {
        outputTextArea.setText("Appointments: " + getAppointmentsByDTP());
    }
}
