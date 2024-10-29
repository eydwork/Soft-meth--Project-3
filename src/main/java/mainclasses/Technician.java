package mainclasses;
import clinic.util.Location;

/* This class is a subclass of Person which contains profile, location, and the rate per visit.
@author Erika Dong, Emily Wong
*/
public class Technician extends Provider { // subclass of Provider
    private int ratePerVisit; //Technician's have a rate per visit

    public Technician(Profile profile, Location location, int ratePerVisit) { //constructor
        super(profile, location);
        this.ratePerVisit = ratePerVisit;
    }

    public void setRatePerVisit(int ratePerVisit) {
        this.ratePerVisit = ratePerVisit;
    }
    public int getRatePerVisit() {
        return ratePerVisit;
    }


    @Override
    public String toString() {
        return "[" + super.getProfile().getFname() + " " + super.getProfile().getLname() + " " + super.getProfile().getDob() + ", " + super.getLocation().getCity() + ", " + super.getLocation().getCounty() + " " +
                super.getLocation().getZip() + "][rate: $" + ratePerVisit +".00]";
    }

    @Override
    public int rate() {
        return ratePerVisit;
    }
}

