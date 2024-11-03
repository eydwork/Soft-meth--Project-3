package clinic.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

/*
*Controller for List of Appointments including the County, Date, and Time
*
*@authors Erika Dong, Emily Wong
*/

public class AppointmentByLocationController extends ClinicManagerController{
    @FXML
    private TextArea outputTextArea;

    public void displayAppointmentsButton(ActionEvent actionEvent) {
        outputTextArea.setText("Appointments: " + getAppointmentsByLoc());
    }

}
