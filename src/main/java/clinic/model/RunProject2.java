package clinic.model;

import clinic.model.mainclasses.Appointment;
import clinic.model.util.List;

/* This class runs the entire project
@author Erika Dong, Emily Wong

*/

public class RunProject2 {
    public static void main(String[] args) {
        // Create a new List for appointments
        List<Appointment> appointmentList = new List<>();

        // Pass the appointment list to the ClinicManager constructor
        ClinicManager clinicManager = new ClinicManager();

        // Run the clinic manager
        clinicManager.run();
    }
}

