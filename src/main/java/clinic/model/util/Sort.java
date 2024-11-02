package clinic.model.util;

import clinic.model.mainclasses.Appointment;
/* This class sorts appointments by its different parameters, and it the different kinds of appointments (office and imaging appointments).
@author Erika Dong, Emily Wong
*/
public class Sort {

    // Sort appointments by date, time, and provider name (PA command)
    public static void appointmentByDateAndProvider(List<Appointment> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                Appointment a1 = list.get(j);
                Appointment a2 = list.get(j + 1);

                if (a1.getDate().compareTo(a2.getDate()) > 0 ||
                        (a1.getDate().compareTo(a2.getDate()) == 0 &&
                                a1.getTimeslot().compareTo(a2.getTimeslot()) > 0) ||
                        (a1.getDate().compareTo(a2.getDate()) == 0 &&
                                a1.getTimeslot().compareTo(a2.getTimeslot()) == 0 &&
                                a1.getProvider().getProfile().getLname().compareTo(a2.getProvider().getProfile().getLname()) > 0)) {
                    // Swap the appointments
                    list.set(j, a2);
                    list.set(j + 1, a1);
                }
            }
        }
    }

    // Sort appointments by patient (PP command)
    public static void appointmentByPatient(List<Appointment> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                Appointment a1 = list.get(j);
                Appointment a2 = list.get(j + 1);

                if (a1.getPatient().getProfile().getLname().compareTo(a2.getPatient().getProfile().getLname()) > 0 ||
                        (a1.getPatient().getProfile().getLname().compareTo(a2.getPatient().getProfile().getLname()) == 0 &&
                                a1.getPatient().getProfile().getFname().compareTo(a2.getPatient().getProfile().getFname()) > 0) ||
                        (a1.getPatient().getProfile().getLname().compareTo(a2.getPatient().getProfile().getLname()) == 0 &&
                                a1.getPatient().getProfile().getFname().compareTo(a2.getPatient().getProfile().getFname()) == 0 &&
                                a1.getPatient().getProfile().getDob().compareTo(a2.getPatient().getProfile().getDob()) > 0) ||
                        (a1.getPatient().getProfile().getLname().compareTo(a2.getPatient().getProfile().getLname()) == 0 &&
                                a1.getPatient().getProfile().getFname().compareTo(a2.getPatient().getProfile().getFname()) == 0 &&
                                a1.getPatient().getProfile().getDob().compareTo(a2.getPatient().getProfile().getDob()) == 0 &&
                                a1.getDate().compareTo(a2.getDate()) > 0)) {
                    // Swap the appointments
                    list.set(j, a2);
                    list.set(j + 1, a1);
                }
            }
        }
    }

    // Sort appointments by county, then appointment date and time (PL, PO, PI commands)
    public static void appointmentByCountyDateTime(List<Appointment> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                Appointment a1 = list.get(j);
                Appointment a2 = list.get(j + 1);

                String county1 = a1.getProvider().getLocation().getCounty();
                String county2 = a2.getProvider().getLocation().getCounty();

                // Compare by county
                if (county1.compareTo(county2) > 0) {
                    // Swap the appointments
                    list.set(j, a2);
                    list.set(j + 1, a1);
                }
                // If counties are the same, compare by date
                else if (county1.compareTo(county2) == 0) {
                    if (a1.getDate().compareTo(a2.getDate()) > 0) {
                        // Swap the appointments
                        list.set(j, a2);
                        list.set(j + 1, a1);
                    }
                    // If dates are the same, compare by time
                    else if (a1.getDate().compareTo(a2.getDate()) == 0) {
                        if (a1.getTimeslot().compareTo(a2.getTimeslot()) > 0) {
                            // Swap the appointments
                            list.set(j, a2);
                            list.set(j + 1, a1);
                        }
                    }
                    // No need for further comparison for order, the list will keep the insertion order naturally.
                }
            }
        }
    }
}
