package mainclasses;

/*
This class provides the possible time slots that are available for scheduling appointments. It can also compare timeslots with one another and see if timeslots are equal.
@author Erika Dong, Emily Wong
*/

public class Timeslot implements Comparable<Timeslot> { //Timeslots for appointments
    private int hour;
    private int minute;

    // Constructor to initialize the timeslot with hour and minute
    public Timeslot(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    // Getters for hour and minute
    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    // Method to display the time in a human-readable format (e.g., 9:00 AM or 2:30 PM)
    public String getFormattedTime() {
        String period = (hour < 12) ? "AM" : "PM";
        int displayHour = (hour % 12 == 0) ? 12 : (hour % 12); // Handle 12-hour format
        return String.format("%02d:%02d %s", displayHour, minute, period);
    }



    // toString method for printing timeslot
    @Override
    public String toString() {
        return getFormattedTime();
    }

    // Compare method for comparing two timeslots based on the hour and minute
    @Override
    public int compareTo(Timeslot other) {
        if (this.hour != other.hour) {
            return this.hour - other.hour; // Compare hours
        }
        return this.minute - other.minute; // Compare minutes if hours are the same
    }

    // Equals method to check if two timeslots are the same
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Timeslot timeslot = (Timeslot) obj;
        return hour == timeslot.hour && minute == timeslot.minute;
    }

    // HashCode method for hashing based on hour and minute
    @Override
    public int hashCode() {
        int result = hour;
        result = 31 * result + minute;
        return result;
    }

    // Static method to create and return the 12 timeslots as per the project requirement
    public static Timeslot fromNumber(int timeslotNum) {
        switch (timeslotNum) {
            case 1: return new Timeslot(9, 0);   // 9:00 AM
            case 2: return new Timeslot(9, 30);  // 9:30 AM
            case 3: return new Timeslot(10, 0);  // 10:00 AM
            case 4: return new Timeslot(10, 30); // 10:30 AM
            case 5: return new Timeslot(11, 0);  // 11:00 AM
            case 6: return new Timeslot(11, 30); // 11:30 AM
            case 7: return new Timeslot(14, 0);  // 2:00 PM
            case 8: return new Timeslot(14, 30); // 2:30 PM
            case 9: return new Timeslot(15, 0);  // 3:00 PM
            case 10: return new Timeslot(15, 30); // 3:30 PM
            case 11: return new Timeslot(16, 0); // 4:00 PM
            case 12: return new Timeslot(16, 30); // 4:30 PM
            default: return null;
        }
    }


}
