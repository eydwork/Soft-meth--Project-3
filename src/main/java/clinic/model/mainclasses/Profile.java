package clinic.model.mainclasses;

import clinic.model.util.Date;

/*
 *This class stores the patient's first name, last name, and *their date of birth.
 * The profile is formatted as first name, *last name, date of birth format.
 * It also compares profiles to *see if they have the same first name, last name, or date of *birth.
 * It sorts the profiles as well.
 *@author Erika Dong, Emily Wong
 */
public class Profile implements Comparable<Profile>{
    private String fname;
    private String lname;
    private Date dob;

    public Profile(String fname, String lname, Date dob){ //constructor
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    public String getFname(){
        return fname;
    }
    public String getLname(){
        return lname;
    }
    public Date getDob(){
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }



    //Used to compare profile with other existing profiles.
    // They are equal with one another if their first name, last name and date of birth are the same.

    @Override
    public boolean equals(Object obj){
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        Profile other = (Profile) obj;
        return fname.equalsIgnoreCase(other.fname) &&
                lname.equalsIgnoreCase(other.lname) &&
                dob.equals(other.dob);
    }

    // putting it in first name, last name, date of birth format
    @Override
    public String toString() {
        return fname + " " + lname + " " + dob.toString();
    }


    //Used for sorting and ordering
    @Override
    public int compareTo(Profile other) {
        int lnameCompare = this.lname.compareToIgnoreCase(other.lname);
        if (lnameCompare != 0) {
            return lnameCompare;
        }

        int fnameCompare = this.fname.compareToIgnoreCase(other.fname);
        if (fnameCompare != 0) {
            return fnameCompare;
        }

        return this.dob.compareTo(other.dob);
    }

}


