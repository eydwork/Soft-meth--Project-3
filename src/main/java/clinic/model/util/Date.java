package clinic.model.util;
import java.time.LocalDate;

/*
* This class sets up dates and formats them as such: XX/XX/XXXX.
*  It has a functions to determine if dates are valid and if a given date is a leap year.
* It also checks to see if dates are the same, and it sorts the dates chronologically.
* @author Erika Dong, Emily Wong
*/
public class Date implements Comparable<Date> {

    private int year;
    private int month;
    private int day;
    private LocalDate localDate;
    LocalDate today = LocalDate.now();

    public Date(int month, int day, int year) {
        this.month = month;
        this.day = day;
        this.year = year;
        this.localDate=LocalDate.of(year,month,day);
    }
    public int getYear(){
        return year;
    }
    public int getMonth(){
        return month;
    }
    public int getDay(){
        return day;
    }


    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;
    int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public Date(String dateStr) {
        String[] str = dateStr.split("/");
        this.month = Integer.parseInt(str[0]);
        this.day = Integer.parseInt(str[1]);
        this.year = Integer.parseInt(str[2]);
    }

    // Check to see if the year is a leap year
    public boolean isLeapYear(int year){
        if( (year % QUADRENNIAL == 0 && year % CENTENNIAL != 0) || year % QUATERCENTENNIAL == 0) {
            return true;
        }
        else {
            return false;
        }
    }
    public boolean isValid() {
        daysInMonth[1] = isLeapYear(year) ? 29 : 28;
        if (year < 0 || month < 1 || month > 12 || day < 1 || day > daysInMonth[month - 1]) {
            return false; // year must be positive, month between 1-12, day at least 1, and the day
                          // cannot greater than how many days are in the respective month
        }
        else return true;
    }


    // testing if two dates are equal to one another
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Date other = (Date) obj;
        return this.year == other.year && this.month == other.month && this.day == other.day;
    }


    //Putting the dates in MM/DD/YYYY format
    @Override
    public String toString() {
        return String.format("%02d/%02d/%d", this.month, this.day, this.year);
    }


    //Comparing dates with one another to sort them. First by year, then month and finally day
    @Override
    public int compareTo(Date other) {

        if (this.year != other.year) {
            return this.year - other.year;
        }

        if (this.month != other.month) {
            return this.month - other.month;
        }

        return this.day - other.day;
    }

}
