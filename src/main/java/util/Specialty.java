package util;
/*
*This class provides the cost of each specialty.
* @author Erika Dong, Emily Wong
 */
public enum Specialty { //specialties for doctors with costs
    FAMILY("Family", 250),
    PEDIATRICIAN("Pediatrician", 300),
    ALLERGIST("Allergist", 350);

    private final String specialtyName;
    private final int cost;

    // Combined constructor for both cost and specialty name
    Specialty(String specialtyName, int cost) {
        this.specialtyName = specialtyName;
        this.cost = cost;
    }

    // Getter for specialty name
    public String getSpecialtyName() {
        return specialtyName;
    }

    // Getter for cost
    public int getCost() {
        return cost;
    }
}


