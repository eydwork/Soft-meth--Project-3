package clinic.model.mainclasses;
import clinic.model.util.Date;
/*
 *This class sets up an appointment with the patient's desired *date, timeslot, profile, provider. It tests to see if an *appointment is the same as a prexisting appointment, as well as *comparing appointments to reorder them. It also formats the *appointments as strings.
 *@author Erika Dong, Emily Wong
 */

public class Appointment implements Comparable<Appointment> {
    protected Date date;
    protected Timeslot timeslot;
    protected Patient patient;
    protected Provider provider;

    /**
     * Constructs a new Appointment with the specified date, timeslot, patient, and provider.
     */
    public Appointment(Date date, Timeslot timeslot, Patient patient, Provider provider) {
        this.date = date;
        this.timeslot = timeslot;
        this.patient = patient;
        this.provider= provider;
    }

    public Date getDate() {
        return date;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public Patient getPatient() {
        return patient;
    }

    public Provider getProvider() {
        return provider;
    }



    /**
     * Checks if this appointment is equal to another object.
     * Two appointments are considered equal if they have the same date, timeslot, patient, and provider.
    */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) // Telling us if the object is the same as the appointment
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Appointment other = (Appointment) obj;
        return this.date.equals(other.date) &&
                this.timeslot.equals(other.timeslot) &&
                this.patient.equals(other.patient) &&
                this.provider.equals(other.provider);
    }

    /**
     * Returns a string representation of the appointment.
     * The string includes details about the date, timeslot, patient, and provider.
     */

    @Override
    public String toString() {

        String string = String.format("%s %s %s %s %s", //Appointment date, timeslot, patient first name, last name, and date of birth
                date.toString(),
                timeslot.toString(),
                patient.getProfile().getFname(),
                patient.getProfile().getLname(),
                patient.getProfile().getDob().toString()
        );

        string += String.format(" [%s %s %s, %s]",//Provider first name, last name, date of birth, location, specialty, and NPI
                provider.getProfile().getFname(),
                provider.getProfile().getLname(),
                provider.getProfile().getDob().toString(),
                provider.getLocation().toString()
        );

        if (provider instanceof Doctor) { //If the provider is a doctor, add the specialty and NPI
            string += String.format(" [%s, %s]",
                    ((Doctor) provider).getSpecialty().toString(),
                    ((Doctor) provider).getNpi()
            );
        }
        else if (this instanceof Imaging) { // If the appointment is an imaging appointment, add the rate per visit and room type
            string += String.format(" [rate: %s][%s]",
                    ((Technician) provider).getRatePerVisit(),
                    ((Imaging) this).getRoomType().toString()
            );
        }

        return string;
    }


    /**
     * Compares this appointment to another appointment based on date, timeslot, and provider.
     */

    @Override
    public int compareTo(Appointment other) {
        int dateCompare = this.date.compareTo(other.date);

        int timeslotCompare = this.timeslot.compareTo(other.timeslot);
        if (timeslotCompare != 0) return timeslotCompare;

        return this.provider.compareTo(other.provider); // ???
    }
}