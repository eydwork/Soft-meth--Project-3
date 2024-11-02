package clinic.model.util;
import java.util.Iterator;
/*
 *This class can store any data type but is used to stores the appointments in a list. The list can add *and remove and is expanded by 4 when full. It can be searched *through to find a specific appointment. It can also be sorted *by patient, location, and date and timeslot; additionally, it *can print by patient, location, and appointment.
 *@author Erika Dong, Emily Wong
 */
public class List<E> implements Iterable<E> { //general list class that can be used for any type of object, however will mainly be used for appointments

    private E[] objects;
    private int size; //number of objects in the array
    private int origCapacity;

    public List() {
        objects = (E[]) new Object[origCapacity]; //original size of the list
        size = 0;
    }
    public boolean isEmpty() {
        return size == 0;
    }

    private void grow() { // this function allows the list to increase by 4 when it is full, allowing more appointments to be filled.
        E[] newObject = (E[]) new Object[objects.length + 4]; //makes size bigger
        //copies the original array to the now bigger one
        if (size >= 0) System.arraycopy(objects, 0, newObject, 0, size);

        objects = newObject; //now replaces the original one
    }

    private int find(E e){    //this method goes through the list checking if it is equal to the one you are searching for. This is useful for canceling appointments and such.
        for (int i = 0; i < size; i++) {
            if (objects[i].equals(e)) {
                return i; // if it is found
            }
        }
        return -1; // if it is not found
    }

    public boolean contains(E e) {
        return find(e) != -1; // If find() returns -1, the appointment isn't found
    }

    public void add(E e) {  // this method adds the new appointment and uses the grow() function if it is full
        if (contains(e)) {

            return; // Prevent adding duplicate appointments
        }
        if (size == objects.length) {
            grow(); // Grow the array if it's full
        }
        objects[size++] = e; // Add the new appointment and increase size

    }
    public void remove(E e) {
        int index = find(e); // Find the index of the appointment
        if (index == -1) {

            return; // Appointment does not exist
        }

        // Shift the remaining appointments to fill the gap
        for (int i = index; i < size - 1; i++) {
            objects[i] = objects[i + 1];
        }
        size--; // Reduce the size of the array
        objects[size] = null; // Clean up the last entry
    }


    public int size() {
        return size;
    }

    // Get the appointment at a specific index
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range.");
        }
        return objects[index];  // Return the appointment at the index
    }

    // Set the appointment at a specific index
    public void set(int index, E e) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of range.");
        }
        objects[index] = e;  // Replace the appointment at the index
    }

    // Return the index of the appointment or -1 if not found
    public int indexOf(E e) {
        return find(e);  // Use the find method to locate the index
    }

    // Create an iterator for iterating through the list of appointments
    @Override
    public Iterator<E> iterator() {
        return (Iterator<E>) new ListIterator();
    }

    // Inner class implementing Iterator for appointments
    private class ListIterator implements Iterator<E> {
        private int currentIndex = 0;

        // Check if there is a next element
        @Override
        public boolean hasNext() {
            return currentIndex < size; // Ensure there are more elements in the list
        }

        // Return the next element in the list or null if no more elements
        @Override
        public E next() {
            if (!hasNext()) {
                return null;  // Return null if no more elements
            }
            return objects[currentIndex++];  // Return the next element and increment the index
        }
    }
}
