package CS284;
import java.util.ArrayList;

/**
 * Class representing an Indexed Double Linked List (IDLList) with fast accessing.
 * @author Ayush Misra
 * @course CS 284 E, Data Structures
 * @pledge "I pledge my honor that I have abided by the Stevens Honor System."
 * @param <E> the generic type of elements in the list
 */
public class IDLList<E> {

    /**
     * Node class that contains data fields for stored data, next node, and previous node.
     * 
     * @param <E> the generic type of data in the node
     */
    private class Node<E> {
        private E data;
        private Node<E> next;
        private Node<E> prev;

        /**
         * Creates a node with no next or previous pointers.
         * 
         * @param data the data to be stored in the node
         */
        public Node(E data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }

        /**
         * Creates a node with next and previous pointers.
         * 
         * @param data the data to be stored in the node
         * @param prev the previous node in the list
         * @param next the next node in the list
         */
        public Node(E data, Node<E> prev, Node<E> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    // Data Fields
    private Node<E> head;
    private Node<E> tail;
    private int size;
    private ArrayList<Node<E>> indices;

    // Constructors

    /**
     * Creates an empty Indexed Double Linked List (IDLList).
     */
    public IDLList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.indices = new ArrayList<>();
    }

    // Methods

    /**
     * Adds elem at the head of the IDLList.
     * 
     * @param elem the data to be added
     * @return always true (for purposes of this assignment)
     */
    public boolean add(E elem) {
        if (size == 0) {
            head = new Node(elem);
            tail = head;
            indices.add(head);
            size++;
            return true;
        } else {
            Node<E> newNode = new Node(elem);
            newNode.next = head;
            newNode.prev = null;
            head.prev = newNode;
            head = newNode;
            indices.add(0, newNode);
            size++;
            return true;
        }
    }

    /**
     * Adds elem at position index. Uses the index array for fast access.
     * 
     * @param index the location at which to add the data
     * @param elem  the data to be added
     * @return always true (for purposes of this assignment)
     */
    public boolean add(int index, E elem) {
        if (index == 0) {
            add(elem);
            return true;
        } else if (index == size){
            append(elem);
            return true;
        } else {
            Node<E> prevNode = indices.get(index - 1);
            Node<E> nextNode = indices.get(index + 1);
            Node<E> newNode = new Node(elem);
            prevNode.next = newNode;
            newNode.next = nextNode;
            nextNode.prev = newNode;
            newNode.prev = prevNode;
            indices.add(index, newNode);
            size++;
            return true;
        }
    }

    /**
     * Adds elem as the new last element of the list (as the tail).
     * 
     * @param elem the data to be added
     * @return always true (for purposes of this assignment)
     */
    public boolean append(E elem) {
        if (size == 0) {
            add(elem);
            return true;
        } else {
            Node<E> newNode = new Node(elem, tail, null);
            tail.next = newNode;
            tail = newNode;
            indices.add(size, newNode);
            size++;
            return true;
        }
    }

    /**
     * Returns the object at position index from the head.
     * 
     * @param index the location to search
     * @return the data of the node located at that index
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("The index must be in bounds of the linked list.");
        } else {
            Node<E> newNode = indices.get(index);
            return newNode.data;
        }
    }

    /**
     * Returns the object at the head.
     * 
     * @return the data of head
     */
    public E getHead() {
        return head.data;
    }

    /**
     * Returns the object at the tail.
     * 
     * @return the data of tail
     */
    public E getLast() {
        return tail.data;
    }

    /**
     * Returns the size of the IDLList.
     * 
     * @return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Removes and returns the element at the head.
     * 
     * @return the data of head
     */
    public E remove() {
        if (head == null) {
            throw new IllegalStateException("This list is already empty.");
        } else if (head == tail) {
            Node<E> oldHead = head;
            head = null;
            tail = null;
            size--;
            indices.remove(0);
            return oldHead.data;
        } else {
            Node<E> oldHead = head;
            head = head.next;
            head.prev = null;
            size--;
            indices.remove(0);
            return oldHead.data;
        }   
    }

    /**
     * Removes and returns the element at the tail,
     * 
     * @return the data of tail
     */
    public E removeLast() {
        if (tail == null) {
            throw new IllegalStateException("This list is already empty.");
        } else if (head == tail) {
            return remove();
        } else {
            Node<E> oldTail = tail;
            tail = tail.prev;
            tail.next = null;
            size--;
            indices.remove(size - 1);
            return oldTail.data;
        }
    }

    /**
     * Removes and returns the element at the given index.
     * 
     * @param index the index at which removal occurs
     * @return the data of the node located at that index
     */
    public E removeAt(int index) {
        if (index == 0) {
            return remove();
        } else if (index == size){
            return removeLast();
        } else {
            Node<E> removedNode = indices.get(index);
            Node<E> prevNode = indices.get(index - 1);
            Node<E> nextNode = indices.get(index + 1);
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
            indices.remove(index);
            size--;
            return removedNode.data;
        }
    }

    /**
     * Removes the first occurrence of the element. Returns a boolean indicating if
     * that element was found.
     * 
     * @param elem the data to be removed 
     * @return boolean representing if the data was located
     */
    public boolean remove(E elem) {
        if (size == 0) {
            return false;
        } else if (elem.equals(head.data)) {
            remove();
            return true;
        } else if (elem.equals(tail.data)) {
            removeLast();
            return true;
        } else {
            for (int i = 0; i < size - 1; i++) {
                if (elem.equals(indices.get(i).data)) {
                    removeAt(i);
                    return true;
                } 
            }
        }
        return false;   
    }

    /**
     * Presents a string representation of the list.
     * 
     * @return the string representation of the list
     */
    public String toString() {
        Node<E> current = head;
        StringBuilder listAsString = new StringBuilder();
        while (current != null) {
            listAsString.append(current.data).append(",");
            current = current.next;
        }
        return listAsString.toString();
    }
}
