package clinic.model.mainclasses;
import clinic.model.util.Date;

/* This class is the superclass of Provider and Patient which stores Profile information. It is also used to compare Person objects.
@author Erika Dong, Emily Wong
*/

public class Person implements Comparable<Person> { // superclass of Provider and Patient
    protected Profile profile;

    public Person(Profile profile){
        this.profile = profile;
    } //constructor


    public String getFname() {
        return profile.getFname();
    }
    public String getLname() {
        return profile.getLname();
    }
    public Date getDob() {
        return profile.getDob();
    }


    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    //Used to compare person with other existing persons.
    @Override
    public int compareTo(Person o) {
        return 0;
    }



}
