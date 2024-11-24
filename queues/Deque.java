/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

// deque implementation using a linked list
public class Deque<Item> implements Iterable<Item> {
    private int numberOfElements;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node next;
    }

    // construct an empty deque
    public Deque() {
        this.first = null;
        this.last = null;
        this.numberOfElements = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        if (numberOfElements == 0) {
            return true;
        }
        return false;
    }

    // return the number of items on the deque
    public int size() {
        return numberOfElements;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException(
                "Item to be added is a null reference.");

        Node newNode = new Node();
        newNode.item = item;
        Node oldFirst = first;
        first = newNode;
        if (numberOfElements == 0) {
            last = first;
        }
        newNode.next = oldFirst;
        numberOfElements++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException(
                "Item to be added is a null reference.");

        Node newNode = new Node();
        newNode.item = item;
        Node oldLast = last;
        last = newNode;
        if (numberOfElements == 0) {
            first = last;
        }
        newNode.next = null;
        if (oldLast != null) {
            oldLast.next = last;
        }
        numberOfElements++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove first element, deque is empty");
        }

        Node oldFirst = first;
        Item oldFirstItem = oldFirst.item;
        first = first.next;
        oldFirst = null;
        numberOfElements--;
        return oldFirstItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        Node oldLast = last;
        Item oldLastItem = oldLast.item;
        Node currentNode = first;
        while (currentNode.next != last) {
            currentNode = currentNode.next;
        }
        last = currentNode;
        last.next = null;
        oldLast = null;
        numberOfElements--;
        return oldLastItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Deque.DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported");
        }
    }

    private void display() {
        Node currentNode = this.first;
        StdOut.println(numberOfElements);
        for (int i = 0; i < numberOfElements; i++) {
            StdOut.print(currentNode.item + " ");
            currentNode = currentNode.next;
        }
        StdOut.println("\n");
    }


    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> integersDeque = new Deque<>();
        integersDeque.addFirst(1);
        integersDeque.addFirst(2);
        integersDeque.addFirst(3);
        // integersDeque.display();
        integersDeque.addLast(4);
        integersDeque.addLast(5);
        integersDeque.addLast(6);
        integersDeque.display();
        integersDeque.removeFirst();
        integersDeque.removeLast();
        integersDeque.display();

        StdOut.println("Testing iterator:");
        for (int i : integersDeque) {
            StdOut.println(i);
        }

    }
}
