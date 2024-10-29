package mainclasses;

import clinic.util.Location;
import clinic.util.Specialty;
/*
*This class creates doctor objects that includes the doctor's profile, location, specialty, and NPI. It also creates a string representation of doctor, and it has the rate that the doctor charges.
@author Erika Dong, Emily Wong
*/

public class Doctor extends Provider { // subclass of Provider
    private Specialty specialty;
    private String npi;

    public Doctor(Profile profile, Location location, Specialty specialty, String npi) { //constructor
        super(profile, location);
        this.specialty = specialty;
        this.npi = npi;
    }


    public Specialty getSpecialty() {
        return specialty;
    }

    public String getNpi() {
        return npi;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public void setNpi(String npi) {
        this.npi = npi;
    }


    /**
    * Creates string representation of doctor
    */
    @Override
    public String toString() {
        return "[" + super.getProfile().getFname() + " " + super.getProfile().getLname() + " " + super.getProfile().getDob() + ", " + super.getLocation().getCity() + ", " + super.getLocation().getCounty() + " " +
                super.getLocation().getZip() + "] [" + specialty.getSpecialtyName() + ", #" + npi + "]";
    }


    @Override
    public int rate() {
        return specialty.getCost();
    }
}
