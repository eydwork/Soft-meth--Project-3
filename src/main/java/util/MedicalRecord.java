package util;

import clinic.mainclasses.Patient;

/*
 *This class stores patients, and it can be expanded by 4 to *store more patients when the array is full. It can also be used *to search, add, and print patients.
 *@author Erika Dong, Emily Wong
 */
public class MedicalRecord { //class to store patients and able to get total billing statements
    private Patient[] patients;
    private int size; //number of patient objects in the array
    private static final int origCapacity = 4;

    public MedicalRecord() {
        patients = new Patient[origCapacity];  // makes the array start with capacity 4
        size = 0;
    }

    private void grow() { //this method increases the array
        Patient[] newPatients = new Patient[patients.length + 4]; //makes size bigger by 4
        for (int i = 0; i < size; i++) { //copies original array to new bigger array now
            newPatients[i] = patients[i];
        }
        patients = newPatients; //replaces old one with new one
    }

    public int find(Patient patient) { //this method uses the equal method to search for the patient
        for (int i = 0; i < size; i++) {
            if (patients[i].equals(patient)) {
                return i; // Return the index where the patient is found
            }
        }
        return -1; // Patient not found
    }

    public Patient findPatient(Patient patient) {
        int index = find(patient);
        return index == -1 ? null : patients[index];
    }

    private boolean contains(Patient patient) {
        return find(patient) != -1; // If the patient is found, return true
    }

    public void add(Patient patient) { //method to add a new patient
        if (contains(patient)) {  //uses the contains function to see if the patient is already in the record
            return;
        }

        if (size == patients.length) {
            grow(); // Expands the array if it's full
        }

        patients[size++] = patient; // Add the patient and increase size
    }

    private void sortPatientsByProfile() {
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                if (patients[j].compareTo(patients[j + 1]) > 0) {
                    // Swap patients[j] and patients[j + 1]
                    Patient temp = patients[j];
                    patients[j] = patients[j + 1];
                    patients[j + 1] = temp;
                }
            }
        }
    }

    // This method calculates and prints the billing statements for all patients
    public void printBillingStatements() {

        sortPatientsByProfile();

        System.out.println("** Billing statement ordered by patient **");
        for (int i = 0; i < size; i++) {
            Patient patient = patients[i];
            System.out.printf("(%d) %s %s [amount due: $%.2f]%n",
                    i + 1,
                    patient.getProfile().getFname() + " " + patient.getProfile().getLname(),
                    patient.getProfile().getDob(),
                    patient.charge()// Calculate total amount due for each patient
            );
        }
    }


    public void printRecords() { //prints all the patients
        for (int i = 0; i < size; i++) {
            System.out.println(patients[i]);
        }
    }
}