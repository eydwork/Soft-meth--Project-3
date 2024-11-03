package clinic.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;


import javafx.scene.control.TextArea;


public class OfficeAppointmentsController extends ClinicManagerController{

    @FXML
    private TextArea outputTextArea;

    public void displayAppointmentsButton(ActionEvent actionEvent) {
        outputTextArea.setText(displayOfficeAppointmentsList());
    }

}

