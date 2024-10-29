package mainclasses;

import clinic.util.Date;
import clinic.util.Radiology;
/* This class is a subclass of Appointment that inclues the date, timeslot, patient, technician, and radiology room. The class is used for the patient to recieve the type of imaging they need.
@author Erika Dong, Emily Wong
*/

public class Imaging extends Appointment { // subclass of Appointment
    private Radiology room;

    // Constructs a new Imaging with the specified date, timeslot, patient, provider, and room.
    public Imaging(Date date, Timeslot timeslot, Patient patient, Technician provider, Radiology room) {
        super(date, timeslot, patient, provider);
        this.room = room;
    }


    public Radiology getRoomType() {
        return room;
    }

    public void setRoom(Radiology room) {
        this.room = room;
    }
}
