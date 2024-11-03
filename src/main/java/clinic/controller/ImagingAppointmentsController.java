package clinic.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

/*
*Controller for List of Appointments Patient First Name, Patient Last Name,
*Appointment Location, Appointment Date, Appointment Time, Technician First Name,
*Technician Last Name, and Imaging Type
*
*@authors Erika Dong, Emily Wong
*/

public class ImagingAppointmentsController extends ClinicManagerController{
    @FXML
    private TextArea outputTextArea;

    public void displayAppointmentsButton(ActionEvent actionEvent) {
        outputTextArea.setText(displayImagingAppointments());
    }
}

