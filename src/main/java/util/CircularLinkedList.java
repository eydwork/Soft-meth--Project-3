package util;
/* This class is used to imitate the rotation of technicians
@author Erika Dong, Emily Wong
*/
public class CircularLinkedList<E> {
    private Node<E> current; // Only keep track of the current technician in the circular list
    private int size; // To track the size of the list

    // Constructor: Creates an empty list
    public CircularLinkedList() {
        current = null;
        size = 0; // Initialize size to 0
    }

    // Check if the circular list is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Get the size of the circular list
    public int size() {
        return size;
    }

    // Add an element to the circular list
    public void add(E element) {
        Node<E> newNode = new Node<>(element);
        if (current == null) {
            // The first node points to itself
            newNode.next = newNode;
            current = newNode; // Set current to the new node
        } else {
            // Insert the new node into the circle
            Node<E> temp = current;
            while (temp.next != current) {
                temp = temp.next;
            }
            temp.next = newNode; // Point the last node to the new node
            newNode.next = current; // The new node points back to the current node
        }
        size++; // Increment the size of the list
    }

    public E getCurrent() {
        if (current == null) {
            throw new IllegalStateException("The list is empty.");
        }
        return current.element;
    }

    // Move to the next element in the circular list and return it
    public E getNext() {
        if (current == null) {
            throw new IllegalStateException("The list is empty.");
        }
        E element = current.element;
        current = current.next; // Move to the next element in the circle
        return element;
    }

    public void addFirst(E technician) {
        Node<E> newNode = new Node<>(technician);
        if (current == null) {
            newNode.next = newNode;
            current = newNode;
        } else {
            Node<E> temp = current;
            while (temp.next != current) {
                temp = temp.next;
            }
            temp.next = newNode;
            newNode.next = current;
            current = newNode;
        }
        size++;
    }

    public void setCurrentToHead() {
        // The "head" is simply the first node inserted, so resetting the `current` pointer to itself
        if (current != null) {
            // Find the original head by traversing the list to ensure we get back to the first node
            Node<E> temp = current;
            while (temp.next != current) {
                temp = temp.next;
            }
            current = temp.next; // Set current to the head
        }
    }

    public void remove() {
        if (current == null) {
            throw new IllegalStateException("The list is empty.");
        }
        Node<E> temp = current;
        while (temp.next != current) {
            temp = temp.next;
        }
        if (temp == current) {
            current = null;
        } else {
            temp.next = current.next;
            current = current.next;
        }
        size--;
    }

    // Inner Node class
    private static class Node<E> {
        E element;
        Node<E> next;

        Node(E element) {
            this.element = element;
        }
    }
}