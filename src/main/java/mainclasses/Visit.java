package mainclasses;


public class Visit { // class to represent a visit
    private Appointment appointment; //a reference to an appointment object
    private Visit next; //a ref. to the next appointment object in the list

    public Visit(Appointment appointment) { //constructor
        this.appointment = appointment;
        this.next = null;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public Visit getNext() {
        return next;
    }

    public void setNext(Visit next) {
        this.next = next;
    }


    // Get the charge for the visit
    public double getCharge() {
        if (appointment.getProvider() instanceof Doctor) {
            return ((Doctor) appointment.getProvider()).getSpecialty().getCost();
        } else if (appointment.getProvider() instanceof Technician) {
            return ((Technician) appointment.getProvider()).getRatePerVisit();
        }
        return 0;
    }

}

