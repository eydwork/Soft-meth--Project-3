package clinic.model;

import clinic.model.mainclasses.*;
import clinic.model.util.*;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Scanner;

/**
 * The ClinicManager class is responsible for managing the clinic's operations,
 * including loading providers from a file, displaying providers, and processing commands.
 */
public class ClinicManager {
    private List<Provider> providersList; // List to store providers
    private CircularLinkedList<Technician> technicianRotation; // Circular list to store technicians
    private List<Appointment> appointmentsList; // List to store appointments
    private MedicalRecord medicalRecord; // Medical record to store patient visits
    private int appointmentSize = 0;  // To track the number of appointments

    Scanner scanner = new Scanner(System.in); // Scanner to read user input

    /**
     * Constructor to initialize the Clinic Manager.
     */
    public ClinicManager() {
        providersList = new List<>();
        technicianRotation = new CircularLinkedList<>();
        appointmentsList = new List<>();
        medicalRecord = new MedicalRecord();

    }

    /**
     * Runs the clinic manager.
     * Loads the providers from the text file, displays them, and processes user commands.
     */
    public void run() {

        // Load providers from the file
        loadProvidersFromFile("src/providers.txt");

        // Display loaded providers
        displayProvidersList(); // This method will handle printing the loaded providers

        // Display rotation list for technicians
        displayTechnicianRotation(); // This method will handle printing the rotation list
        System.out.println();

        System.out.println("Clinic Manager is running...");

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equals("Q")) {
                System.out.println("Clinic Manager terminated");
                break;
            }

            processCommand(input);
        }
    }

    /**
     * Load providers from a text file using Scanner
     * @param providersFile The file path to load providers from
     */
    private void loadProvidersFromFile(String providersFile) {
        try (Scanner fileScanner = new Scanner(new File(providersFile))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                parseProviderLine(line); // Call method to parse and process each line
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    /**
     * Parse each line of the provider's file and checks if they are doctors or technicians.
     * @param line The line to parse
     */
    private void parseProviderLine(String line) {
        String[] details = line.split("\\s+");

        // Check if it's a doctor and verify it has exactly 7 details
        if (details[0].equals("D")) {
            if (details.length != 7) {
                System.out.println("Invalid doctor format. A doctor's entry must have exactly 7 details.");
                return;
            }
            addDoctorProvider(details);
        }
        // Check if it's a technician and verify it has exactly 6 details
        else if (details[0].equals("T")) {
            if (details.length != 6) {
                System.out.println("Invalid technician format. A technician's entry must have exactly 6 details.");
                return;
            }
            addTechnicianProvider(details);
        }
        // If the first element isn't 'D' or 'T', print an error message
        else {
            System.out.println("Invalid provider type. It must start with 'D' for Doctor or 'T' for Technician.");
        }
    }

    /**
     * Add a doctor provider if it has D in the first index of the array.
     * @param details The details of the doctor provider
     */
    private void addDoctorProvider(String[] details) {
        // Format: D, FIRSTNAME, LASTNAME, DOB, LOCATION, SPECIALTY, NPI
        String firstName = details[1];
        String lastName = details[2];
        String dob = details[3];
        String location = details[4].toUpperCase();
        String specialty = details[5].toUpperCase();
        String npi = details[6];

        Profile profile = new Profile(firstName, lastName, new Date(dob));
        Location loc = Location.valueOf(location);
        Specialty spec = Specialty.valueOf(specialty);

        Doctor doctor = new Doctor(profile, loc, spec, npi);
        providersList.add(doctor);
    }

    /**
     * Add a technician provider if it has T in the first index of the array.
     * @param details The details of the technician provider
     */
    private void addTechnicianProvider(String[] details) {
        // Format: T, FIRSTNAME, LASTNAME, DOB, LOCATION, RATE
        String firstName = details[1];
        String lastName = details[2];
        String dob = details[3];
        String location = details[4];
        String rate = details[5];

        Profile profile = new Profile(firstName, lastName, new Date(dob));
        Location loc = Location.valueOf(location.toUpperCase());

        Technician technician = new Technician(profile, loc, Integer.parseInt(rate));
        technicianRotation.addFirst(technician);
        providersList.add(technician);
    }

    /**
     * Sort the providers list by last name.
     */
    private void sortProvidersListByLastName() {
        int n = providersList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Provider p1 = providersList.get(j);
                Provider p2 = providersList.get(j + 1);
                if (p1.getProfile().getLname().compareToIgnoreCase(p2.getProfile().getLname()) > 0) {
                    // Swap p1 and p2
                    providersList.set(j, p2);
                    providersList.set(j + 1, p1);
                }
            }
        }
    }

    /**
     * Display the list of loaded providers.
     */
    private void displayProvidersList() {
        System.out.println("Providers loaded to the list.");

        sortProvidersListByLastName();

        for (Provider provider : providersList) {
            System.out.println(provider.toString());  // Use overridden toString()
        }

        System.out.println();
    }

    /**
     * Display the rotation list for the technicians.
     */
    private void displayTechnicianRotation() {
        System.out.print("Rotation list for the technicians.");
        System.out.println();
        int size = technicianRotation.size();

        // Loop through the technicians and print them in order
        for (int i = 0; i < size; i++) {
            Technician technician = technicianRotation.getNext();

            // Print the technician's information in the specified format
            System.out.print(technician.getProfile().getFname() + " " +
                    technician.getProfile().getLname() + " (" +
                    technician.getLocation().getCity() + ")");

            // Print " --> " after each technician except the last one
            if (i < size - 1) {
                System.out.print(" --> ");
            }
        }

        System.out.println();  // End with a new line after the rotation list
    }

    /*
     * COMMANDS
     */

    /**
     * Process the user input command.
     * @param input The user input command
     */
    private void processCommand(String input) {
        String[] parts = input.split(",");
        String command = parts[0].trim();  // Extract command from input

        switch (command) {
            case "D": // Scheduling a doctor appointment
                if (hasValidTokens(parts, 7)) {
                    System.out.println("Missing data tokens.");
                    return;
                }
                handleDoctorAppointment(parts); break;
            case "T": // Scheduling a technician appointment
                if (hasValidTokens(parts, 7)) {
                    System.out.println("Missing data tokens.");
                    return;
                }
                handleTechnicianAppointment(parts); break;
            case "C": // Canceling an appointment
                if (hasValidTokens(parts, 6)) {
                    System.out.println("Missing data tokens.");
                    return;
                }
                handleCancelAppointment(parts); break;
            case "R": // Rescheduling an appointment
                if (hasValidTokens(parts, 7)) {
                    System.out.println("Missing data tokens.");
                    return;
                }
                handleRescheduleAppointment(parts); break;
            case "PS": // Prints billing statements (sorted by expected credit)
                if (appointmentsList.isEmpty()) System.out.println("Schedule calendar is empty.");
                else medicalRecord.printBillingStatements();
                appointmentsList = new List<Appointment>();
                break;

            case "PA": // Sort appointments by date, time, provider's name
                if (appointmentsList.isEmpty()) System.out.println("Schedule calendar is empty.");
                else {
                    Sort.appointmentByDateAndProvider(appointmentsList);
                    printAppointments();
                }
                break;
            case "PP": // Sort appointments by patient (last name, first name, DOB, appointment date)
                if (appointmentsList.isEmpty()) System.out.println("Schedule calendar is empty.");
                else {
                    Sort.appointmentByPatient(appointmentsList);
                    printAppointments();
                }
                break;
            case "PL": // Sort appointments by county, date, and time
                if (appointmentsList.isEmpty()) System.out.println("Schedule calendar is empty.");
                else {
                    Sort.appointmentByCountyDateTime(appointmentsList);
                    printAppointments();
                }
                break;
            case "PO": // Display office appointments
                if (appointmentsList.isEmpty()) System.out.println("Schedule calendar is empty.");
                else {
                    displayOfficeAppointments();
                }
                break;
            case "PI": // Display imaging appointments
                if (appointmentsList.isEmpty()) System.out.println("Schedule calendar is empty.");
                else {
                    displayImagingAppointments();
                }
                break;
            case "PC": // Display providers credit statements
                 if (appointmentsList.isEmpty()) {
                System.out.println("Schedule calendar is empty.");
                } else {
                printCreditStatement();
                }
                break;
            case "Q": // Quits the scheduler
                System.out.println("Scheduler terminated.");
                scanner.close();
                break;
            default:
                System.out.println("Invalid command!");
        }
    }

    /**
     * Prints the billing statements for all appointments.
     * Each statement includes the appointment date, timeslot, patient name, and due amount.
     */
    private void printBillingStatements() {
        for (Appointment appointment : appointmentsList) {
            String str = String.format("(%d) %s %s %s [due: $%.2f]",
                    appointmentsList.indexOf(appointment) + 1,
                    appointment.getDate().toString(),
                    appointment.getTimeslot().toString(),
                    appointment.getPatient().getProfile().getFname() + " " + appointment.getPatient().getProfile().getLname(),
                    appointment.getProvider().rate()  // Assuming a getRatePerVisit() exists for both Technician and Doctor
            );
            System.out.println(str);  // You can customize the format
        }
    }
    /**
     * Prints the credit statements for all providers.
     * Each statement includes the provider's name, DOB, and total billing amount.
     */
    private void printCreditStatement() {
        for (int i = 0; i < providersList.size(); i++) {
            Provider provider = providersList.get(i);
            String str = String.format("(%d) %s %s %s [credit amount: $%.2f]",
                    i + 1,
                    provider.getProfile().getFname(),
                    provider.getProfile().getLname(),
                    provider.getProfile().getDob(),
                    getTotalBilling(appointmentsList, provider)
            );
            System.out.println(str);
        }
    }


    /**
     * Calculates the total billing amount for a given provider.
     *
     * @param appointmentList The list of appointments.
     * @param provider The provider for whom the total billing is calculated.
     * @return The total billing amount.
     */
    private double getTotalBilling(List<Appointment> appointmentList, Provider provider) {
        double totalBilling = 0;
        for (Appointment appointment : appointmentList) {
            if (appointment.getProvider().equals(provider)) {
                totalBilling += appointment.getProvider().rate();  // Add cost per visit to total billing
            }
        }
        return totalBilling;
    }

    /**
     * Displays the list of office appointments (appointments with doctors).
     * The appointments are sorted by county, date, and time.
     */
    private void displayOfficeAppointments() {
        List<Appointment> officeAppointments = new List<>();

        // Filter for office appointments (appointments with doctors)
        for (int i = 0; i < appointmentsList.size(); i++) {
            Appointment appointment = appointmentsList.get(i);
            if (appointment.getProvider() instanceof Doctor) {
                officeAppointments.add(appointment);
            }
        }

        // Sort office appointments by county, date, and time
        Sort.appointmentByCountyDateTime(officeAppointments);

        // Print the sorted office appointments
        for (int i = 0; i < officeAppointments.size(); i++) {
            System.out.println(officeAppointments.get(i).toString());
        }
    }

    /**
     * Checks if the input command has the expected number of tokens.
     *
     * @param parts The input command split into parts.
     * @param expectedTokens The expected number of tokens.
     * @return True if the number of tokens is valid, false otherwise.
     */
    private boolean hasValidTokens(String[] parts, int expectedTokens) {
        return parts.length != expectedTokens;
    }

    /**
     * Displays the list of imaging appointments (appointments with technicians).
     * The appointments are sorted by county, date, and time.
     */
    private void displayImagingAppointments() {
        List<Appointment> imagingAppointments = new List<>();

        // Filter for imaging appointments (appointments with technicians)
        for (int i = 0; i < appointmentsList.size(); i++) {
            Appointment appointment = appointmentsList.get(i);
            if (appointment.getProvider() instanceof Technician) {
                imagingAppointments.add(appointment);
            }
        }

        // Sort imaging appointments by county, date, and time
        Sort.appointmentByCountyDateTime(imagingAppointments);

        // Print the sorted imaging appointments
        for (int i = 0; i < imagingAppointments.size(); i++) {
            Appointment appointment = imagingAppointments.get(i);
            Technician technician = (Technician) appointment.getProvider();

            // Customize the output to show the county, date, and time
            System.out.println(appointment.getDate() + " " +
                    appointment.getTimeslot().getFormattedTime() + " " +
                    appointment.getPatient().getProfile().getFname() + " " +
                    appointment.getPatient().getProfile().getLname() + " " +
                    appointment.getPatient().getProfile().getDob() + " [" +
                    technician.getProfile().getFname() + " " +
                    technician.getProfile().getLname() + ", " +
                    technician.getProfile().getDob() + ", " +
                    technician.getLocation().getCity() + ", " +
                    technician.getLocation().getCounty() + ", " +
                    technician.getLocation().getZip() + "][rate: $" +
                    technician.getRatePerVisit() + ".00]" + " [" +
                    ((Imaging) appointment).getRoomType() + "]");
        }
    }



    /**
     * Handle the Doctor appointment scheduling (placeholder, needs implementation).
     *
     * @param parts The command parts for scheduling a doctor appointment.
     */
    private void handleDoctorAppointment(String[] parts) {
        if (parts.length < 7) {
            System.out.println("Missing data tokens.");
            return;
        }

        // Extract details from the command
        String dateStr = parts[1].trim();
        String timeslotStr = parts[2].trim();
        String firstName = parts[3].trim();
        String lastName = parts[4].trim();
        String dobStr = parts[5].trim();
        String npi = parts[6].trim();

        Date appointmentDate = new Date(dateStr);// Validate and create the appointment date
        if (!validateDate(appointmentDate)) return;

        Date dob = new Date(dobStr);// Validate and create the patient's date of birth
        if (!validateDOB(dob)) return;

        if (!isValidTimeslot(timeslotStr)) {
            System.out.println(timeslotStr + " is not a valid time slot.");
            return;
        }
        Timeslot timeslot = Timeslot.fromNumber(Integer.parseInt(timeslotStr));

        Doctor doctor = findDoctorByNPI(npi);
        // Find the doctor by NPI
        if (doctor == null) {
            System.out.println(npi + " - provider doesn't exist");
            return;
        }

        // Create a profile for the patient
        Profile patientProfile = new Profile(firstName, lastName, dob);
        Patient patient = new Patient(patientProfile);

        // Check if the patient already has an appointment at the same date and timeslot
        if (isPatientBooked(patient, appointmentDate, timeslot)) {
            System.out.println (patient.getProfile().getFname() + " " +
                    patient.getProfile().getLname() + " " + patient.getProfile().getDob() + " has an existing appointment at the same time slot.");
            return;
        }

        // Check if the doctor is available at the given date and timeslot
        if (isProviderBooked(doctor, appointmentDate, timeslot)) {
            System.out.println("[" + doctor.getProfile().getFname() + " " + doctor.getProfile().getLname() + " " + doctor.getProfile().getDob() + ", " +
                    doctor.getLocation().getCity() + ", " + doctor.getLocation().getCounty() + " " + doctor.getLocation().getZip() + "][" +
                    doctor.getSpecialty().getSpecialtyName() + ", #" + doctor.getNpi() + "] is not available at slot " + timeslotStr);
            return;
        }

        // Schedule the appointment
        Appointment newAppointment = new Appointment(appointmentDate, timeslot, patient, doctor);
        appointmentsList.add(newAppointment);

        medicalRecord.add(patient);
        patient.addVisit(newAppointment);

        System.out.println(appointmentDate + " " + timeslot.getFormattedTime() + " " +
                patient.getProfile().getFname() + " " + patient.getProfile().getLname() + " " + patient.getProfile().getDob() + " [" +
                doctor.getProfile().getFname() + " " + doctor.getProfile().getLname() + " " + doctor.getProfile().getDob() + ", " +
                doctor.getLocation().getCity() + ", " + doctor.getLocation().getCounty() + " " +
                doctor.getLocation().getZip() + "][" + doctor.getSpecialty().getSpecialtyName() + ", #" + doctor.getNpi() + "] booked.");
    }

    private void handleTechnicianAppointment(String[] parts) {
        if (parts.length < 7) {
            System.out.println("Missing data tokens. Usage: T,date,timeslot,firstName,lastName,DOB,roomType");
            return;
        }

        String dateStr = parts[1].trim();
        String timeslotStr = parts[2].trim();
        String firstName = parts[3].trim();
        String lastName = parts[4].trim();
        String dobStr = parts[5].trim();
        String roomTypeStr = parts[6].trim();

        Date appointmentDate = new Date(dateStr);
        if (!validateDate(appointmentDate)) {
            return;
        }

        Date dob = new Date(dobStr);
        if (!validateDOB(dob)) {
            return;
        }

        if (!isValidTimeslot(timeslotStr)) {
            System.out.println(timeslotStr + " is not a valid time slot.");
            return;
        }
        Timeslot timeslot = Timeslot.fromNumber(Integer.parseInt(timeslotStr));

        // Validate the imaging service directly by attempting to match it with the Radiology enum
        Radiology imagingService;
        try {
            imagingService = Radiology.valueOf(roomTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(roomTypeStr + " - imaging service not provided");
            return;
        }

        Profile patientProfile = new Profile(firstName, lastName, dob);
        Patient patient = new Patient(patientProfile);

        // Step 1: Check if the patient already has an appointment at this time slot
        if (isPatientBooked(patient, appointmentDate, timeslot)) {
            System.out.println(firstName + " " + lastName + " " + dobStr + " has an existing appointment at the same time slot.");
            return;
        }

        Technician startTechnician = technicianRotation.getNext(); // Start with the current technician
        Technician currentTechnician = startTechnician;
        Technician availableTechnician = null;

        // Check if rooms are available at specific timeslots and locations
        boolean roomAvailableInBridgewater = true;
        boolean roomAvailableInPiscataway = true;

        // Check for any existing bookings at each location and timeslot
        for (Appointment appointment : appointmentsList) {
            if (appointment instanceof Imaging) {
                Imaging imagingAppointment = (Imaging) appointment;
                if (imagingAppointment.getRoomType() == imagingService && imagingAppointment.getTimeslot().equals(timeslot)) {
                    if (imagingAppointment.getProvider().getLocation() == Location.BRIDGEWATER) {
                        roomAvailableInBridgewater = false; // Room is booked in Bridgewater at this timeslot
                    } else if (imagingAppointment.getProvider().getLocation() == Location.PISCATAWAY) {
                        roomAvailableInPiscataway = false; // Room is booked in Piscataway at this timeslot
                    }
                }
            }
        }

        boolean isFirstIteration = true;

        // Rotate through technicians until an available one with an available room is found
        do {
            // Skip technicians if no rooms are available at their location
            if ((currentTechnician.getLocation() == Location.BRIDGEWATER && !roomAvailableInBridgewater) ||
                    (currentTechnician.getLocation() == Location.PISCATAWAY && !roomAvailableInPiscataway)) {
                currentTechnician = technicianRotation.getNext();
                continue;
            }
            // Check if the technician is available at the given timeslot
            if (!isProviderBooked(currentTechnician, appointmentDate, timeslot)) {
                availableTechnician = currentTechnician;
                break; // Break once we find an available technician and room
            }
            // Move to the next technician in the circular list
            currentTechnician = technicianRotation.getNext();
        } while (technicianRotation.getCurrent() != startTechnician); // Continue until we loop back to the start

        // If no available technician or room was found, print a message
        if (availableTechnician == null) {
            System.out.println("Cannot find an available technician at all locations for " + imagingService + " at slot " + timeslotStr);
            return;
        }

        // Step 2: Schedule the appointment
        Imaging newAppointment = new Imaging(appointmentDate, timeslot, patient, availableTechnician, imagingService);
        appointmentsList.add(newAppointment);

        medicalRecord.add(patient);
        patient.addVisit(newAppointment);

        // Print appointment details
        System.out.println(appointmentDate + " " + timeslot.getFormattedTime() + " " +
                patient.getProfile().getFname() + " " + patient.getProfile().getLname() + " " + patient.getProfile().getDob() + " [" +
                availableTechnician.getProfile().getFname() + " " + availableTechnician.getProfile().getLname() + " " +
                availableTechnician.getProfile().getDob() + ", " + availableTechnician.getLocation().getCity() + ", " +
                availableTechnician.getLocation().getCounty() + " " + availableTechnician.getLocation().getZip() + "][rate: $" +
                availableTechnician.getRatePerVisit() + ".00][" + roomTypeStr + "] booked.");
    }



    private void handleCancelAppointment(String[] parts) {
        if (parts.length < 5) {
            System.out.println("Missing data tokens. Usage: C,date,timeslot,firstName,lastName");
            return;
        }

        String dateStr = parts[1].trim();
        String timeslotStr = parts[2].trim();
        String firstName = parts[3].trim();
        String lastName = parts[4].trim();
        String dobStr = parts[5].trim();


        Date appointmentDate = new Date(dateStr);

        if (!isValidTimeslot(timeslotStr)){
            System.out.println(timeslotStr + " is not a valid time slot.");
        }
        Timeslot timeslot = Timeslot.fromNumber(Integer.parseInt(timeslotStr));

        Appointment toCancel = findAppointmentByPatient(firstName, lastName, appointmentDate, timeslot);
        if (toCancel == null) {
            String str = String.format("%s %s %s %s %s - appointment does not exist.",
                    dateStr, timeslot, firstName, lastName, dobStr);
            System.out.println(str);
            return;
        }

        Patient patient = toCancel.getPatient();
        patient.removeVisit(toCancel);

        appointmentsList.remove(toCancel);
        System.out.println(appointmentDate + " " + timeslot + " " + firstName + " " + lastName + " " + dobStr + " - appointment has been canceled.");
    }

    private void handleRescheduleAppointment(String[] parts) {
        if (parts.length != 7) { // Ensure the correct number of inputs
            System.out.println("Invalid command format. Usage: R,originalDate,originalTimeslot,firstName,lastName,dob,newTimeslot");
            return;
        }

        // Extract the original and new appointment details
        String originalDateStr = parts[1].trim();
        String originalTimeslotStr = parts[2].trim();
        String firstName = parts[3].trim();
        String lastName = parts[4].trim();
        String dobStr = parts[5].trim();
        String newTimeslotStr = parts[6].trim();

        // Create objects for the original appointment
        Date originalDate = new Date(originalDateStr);
        Timeslot originalTimeslot = Timeslot.fromNumber(Integer.parseInt(originalTimeslotStr));

        // Validate the original timeslot
        if (originalTimeslot == null) {
            System.out.println(originalTimeslotStr + " is not a valid timeslot.");
            return;
        }

        // Validate the original appointment date
        if (!validateDate(originalDate)) {
            return;
        }

        // Find the original appointment by patient and timeslot
        Appointment originalAppointment = findAppointmentByPatientAndTimeslot(firstName, lastName, originalDate, originalTimeslot);
        if (originalAppointment == null) {
            String str = String.format("%s %s %s %s %s does not exist.",
                    originalDateStr, originalTimeslot, firstName, lastName, dobStr);
            System.out.println(str);
            return;
        }

        // Create objects for the new appointment
        Timeslot newTimeslot = Timeslot.fromNumber(Integer.parseInt(newTimeslotStr));

        // Validate the new timeslot
        if (newTimeslot == null) {
            System.out.println(newTimeslotStr + " is not a valid timeslot.");
            return;
        }

        // Check if the patient already has an appointment at the new timeslot and date
        if (isPatientBooked(originalAppointment.getPatient(), originalDate, newTimeslot)) {
            System.out.println(firstName + " " + lastName + " " + dobStr + " has an existing appointment at " + originalDateStr + " " + newTimeslot.getFormattedTime());
            return;
        }

        // Check if the provider is available at the new timeslot and date
        Provider provider = originalAppointment.getProvider();
        if (isProviderBooked(provider, originalDate, newTimeslot)) {  // Check if the provider is already booked
            System.out.println("[" + provider.getProfile().getFname() + " " + provider.getProfile().getLname() + "] is not available at the new timeslot.");
            return;
        }

        // Remove the old appointment and schedule the new one
        appointmentsList.remove(originalAppointment); // Remove original appointment
        Appointment newAppointment = new Appointment(originalDate, newTimeslot, originalAppointment.getPatient(), provider);
        appointmentsList.add(newAppointment); // Add the new appointment

        // Update patient visit history
        Patient patient = originalAppointment.getPatient();
        patient.removeVisit(originalAppointment);
        patient.addVisit(newAppointment);

        // Confirmation message
        System.out.println("Rescheduled to " + " " + originalDateStr + " " + newTimeslot + " " + firstName + " " + lastName + " " + dobStr + " [" +
                provider.getProfile().getFname() + " " + provider.getProfile().getLname() + " " + provider.getProfile().getDob() + ", " +
                provider.getLocation().getCity() + ", " + provider.getLocation().getCounty() + " " + provider.getLocation().getZip() + "]["+ ((Doctor) provider).getSpecialty() + ", #" + ((Doctor) provider).getNpi() + "]");
    }






    private boolean isRoomAvailable(Location location, Radiology roomType, Date appointmentDate, Timeslot timeslot) {
        // Iterate over the list of appointments to check if the room is already booked
        for (Appointment appointment : appointmentsList) {
            // Ensure the appointment is for the same location, room type, date, and timeslot
            if (appointment instanceof Imaging) {
                Imaging imagingAppointment = (Imaging) appointment;

                // Check if the appointment location, room type, date, and timeslot match the current request
                if (imagingAppointment.getProvider().getLocation().equals(location) &&
                        imagingAppointment.getRoomType() == roomType &&
                        imagingAppointment.getDate().equals(appointmentDate) &&
                        imagingAppointment.getTimeslot().equals(timeslot)) {
                    return false; // Room is already booked at this location for the given timeslot
                }
            }
        }
        return true; // Room is available if no conflict is found
    }


    private boolean validateDate(Date appointmentDate) {
        if (!appointmentDate.isValid()) {
            System.out.println("Appointment date: " + appointmentDate + " is not a valid calendar date.");
            return false;
        }

        Calendar today = Calendar.getInstance();
        Calendar apptDate = Calendar.getInstance();
        apptDate.set(appointmentDate.getYear(), appointmentDate.getMonth() - 1, appointmentDate.getDay());

        Calendar sixMonthsFromNow = (Calendar) today.clone();
        sixMonthsFromNow.add(Calendar.MONTH, 6);

        if (apptDate.after(sixMonthsFromNow)) {
            System.out.println("Appointment date: " + appointmentDate + " is not within six months.");
            return false;
        }

        if (apptDate.before(today) || apptDate.equals(today)) {
            System.out.println("Appointment date: " + appointmentDate + " is today or a date before today.");
            return false;
        }

        int dayOfWeek = apptDate.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            System.out.println("Appointment date: " + appointmentDate + " is Saturday or Sunday.");
            return false;
        }

        return true;
    }

    private boolean validateDOB(Date dob) {
        if (!dob.isValid()) {
            System.out.println("Patient dob: " + dob + " is not a valid calendar date.");
            return false;
        }

        Calendar today = Calendar.getInstance();
        Calendar dobDate = Calendar.getInstance();
        dobDate.set(dob.getYear(), dob.getMonth() - 1, dob.getDay());

        if (dobDate.after(today) || dobDate.equals(today)) {
            System.out.println("Patient dob: " + dob + " is today or a date after today.");
            return false;
        }

        return true;
    }

    private boolean isValidTimeslot(String timeslotStr) {
        try {
            int timeslotNumber = Integer.parseInt(timeslotStr); // Parse the input as an integer
            Timeslot timeslot = Timeslot.fromNumber(timeslotNumber); // Validate against predefined timeslots
            return timeslot != null; // Return true if the timeslot exists, otherwise false
        } catch (NumberFormatException e) {
            return false; // If the input is not a valid integer, return false
        }
    }

    private Doctor findDoctorByNPI(String npi) {
        for (Provider provider : providersList) {
            if (provider instanceof Doctor doctor) {
                if (doctor.getNpi().equals(npi)) {
                    return doctor;
                }
            }
        }
        return null; // Return null if no matching doctor is found
    }

    private boolean isPatientBooked(Patient patient, Date appointmentDate, Timeslot timeslot) {
        // Iterate over the list of appointments
        for (Appointment appointment : appointmentsList) {
            // Check if the same patient has a booking on the same date and timeslot
            if (appointment.getPatient().equals(patient) &&
                    appointment.getDate().equals(appointmentDate) &&
                    appointment.getTimeslot().equals(timeslot)) {
                return true; // Patient is already booked at this timeslot and date
            }
        }
        return false; // Patient is not booked
    }

    private boolean isProviderBooked(Provider provider, Date appointmentDate, Timeslot timeslot) {
        // Iterate over all existing appointments to check if the provider is already booked
        for (Appointment appointment : appointmentsList) {
            if (appointment.getProvider().equals(provider) &&
                    appointment.getDate().equals(appointmentDate) &&
                    appointment.getTimeslot().equals(timeslot)) {
                // If the provider already has an appointment at this date and timeslot, return true
                return true;
            }
        }
        // If no conflicting appointment is found, return false (provider is available)
        return false;
    }

    private Appointment findAppointmentByPatient(String firstName, String lastName, Date appointmentDate, Timeslot timeslot) {
        // Iterate over the list of appointments
        for (Appointment appointment : appointmentsList) {
            Profile patientProfile = appointment.getPatient().getProfile();

            // Check if the appointment matches the provided patient details, date, and timeslot
            if (patientProfile.getFname().equalsIgnoreCase(firstName) &&
                    patientProfile.getLname().equalsIgnoreCase(lastName) &&
                    appointment.getDate().equals(appointmentDate) &&
                    appointment.getTimeslot().equals(timeslot)) {
                // If a match is found, return the appointment
                return appointment;
            }
        }
        // Return null if no matching appointment is found
        return null;
    }

    private void printAppointments() {
        for (Appointment appointment : appointmentsList) {
            System.out.println(appointment.toString());  // You can customize the format
        }
    }
    private void printProviders() {
        for (Provider provider : providersList) {
            System.out.println(provider.toString());  // You can customize the format
        }
    }
    private Appointment findAppointmentByPatientAndTimeslot(String firstName, String lastName, Date date, Timeslot timeslot) {
        for (Appointment appointment : appointmentsList) {
            Profile patientProfile = appointment.getPatient().getProfile();
            if (patientProfile.getFname().equalsIgnoreCase(firstName) &&
                    patientProfile.getLname().equalsIgnoreCase(lastName) &&
                    appointment.getDate().equals(date) &&
                    appointment.getTimeslot().equals(timeslot)) {
                return appointment;
            }
        }
        return null; // Return null if no matching appointment is found
    }

    /**
     * Main method to start the ClinicManager.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        ClinicManager clinicManager = new ClinicManager(); // Pass it to the constructor
        clinicManager.run(); // Start the ClinicManager
    }
}
