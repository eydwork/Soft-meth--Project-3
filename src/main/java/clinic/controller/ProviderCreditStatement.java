package clinic.controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class ProviderCreditStatement {
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty dob;
    private final SimpleDoubleProperty creditAmount;

    public ProviderCreditStatement(String firstName, String lastName, String dob, double creditAmount) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.dob = new SimpleStringProperty(dob);
        this.creditAmount = new SimpleDoubleProperty(creditAmount);
    }

    public String getFirstName() { return firstName.get(); }
    public String getLastName() { return lastName.get(); }
    public String getDob() { return dob.get(); }
    public double getCreditAmount() { return creditAmount.get(); }
}
