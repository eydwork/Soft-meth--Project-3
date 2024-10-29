package mainclasses;

/* This class is a subclass of Person which keeps track of patients' visits (past appointments) by adding and removing visits. Additionally, it keeps track of the charge based off the visits, and it checks to see if patients are the same.
@author Erika Dong, Emily Wong
*/

public class Patient extends Person  { //subclass of Person
    private Visit visits; //a linked list of visits (completed appt.)

    public Patient(Profile profile){ //constructor
        super(profile);
        this.visits = null; //this says that the visit list will be empty
    }

//adding a visit
    public void addVisit(Appointment appointment) {
        Visit newVisit = new Visit(appointment); // Create a new visit
        if (visits == null) {
            visits = newVisit;
        } else {
            Visit current = visits;
            while (current.getNext() != null) {
                current = current.getNext(); // Traverse to the end of the list
            }
            current.setNext(newVisit); // Add new visit at the end
        }
    }

//removing a visit
    public int removeVisit(Appointment appointment) {
        if (visits == null) {
            return 1;
        }

        if (visits.getAppointment().equals(appointment)) {
            visits = visits.getNext();
            return 2;
        }

        Visit current = visits;
        Visit previous = null;
        while (current != null && !current.getAppointment().equals(appointment)) {
            previous = current;
            current = current.getNext();
        }

        if (current == null) {
            return 3;
        } else {
            previous.setNext(current.getNext());
            return 0;
        }
    }

//getting the visits
    public Visit getVisits(){
        return visits;
    }

    public void setVisits(Visit visits) {
        this.visits = visits;
    }

//getting the charge
    public double charge() {
        double total = 0;
        Visit current = visits;
        while (current != null) {
            total += current.getCharge();
            current = current.getNext();
        }
        return total;
    }


    //turning the patient into a string
    @Override
    public String toString() {
        return profile.toString();
    }



    //checking if two patients are equal
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Patient other = (Patient) obj;
        return this.profile.equals(other.profile);
    }
}
