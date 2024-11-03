package clinic.controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class PatientBillStatement {
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty dob;
    private final SimpleDoubleProperty billAmount;

    public PatientBillStatement(String firstName, String lastName, String dob, double billAmount) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.dob = new SimpleStringProperty(dob);
        this.billAmount = new SimpleDoubleProperty(billAmount);
    }

    public String getFirstName() { return firstName.get(); }
    public String getLastName() { return lastName.get(); }
    public String getDob() { return dob.get(); }
    public double getBillAmount() { return billAmount.get(); }
}
