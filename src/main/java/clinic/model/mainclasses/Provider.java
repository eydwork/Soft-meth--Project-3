package clinic.model.mainclasses;

import clinic.model.util.Location;

/* This class is a subclass of Person which stores the location of the provider. It also contains an abstract method, rate, which is used to calculate Doctor and Technicians' rates.
@author Erika Dong, Emily Wong
*/

public abstract class Provider extends Person { // subclass of Person and superclass of Doctor and Technician
    private Location location; // location of provider

    public abstract int rate(); // abstract method to be implemented by subclasses

    public Provider(Profile profile, Location location) { //constructor
        super(profile);
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    //Used to compare provider with other existing providers.
    public int compareTo(Provider provider) {
        return this.profile.getLname().compareTo(provider.getProfile().getLname());
    }
}





