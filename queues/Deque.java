/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

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
        return null;
    }

    // remove and return the item from the back
    public Item removeLast() {
        return null;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return null;
    }

    private void display() {
        Node currentNode = this.first;
        StdOut.println(numberOfElements);
        for (int i = 0; i < numberOfElements; i++) {
            StdOut.print(currentNode.item + " ");
            currentNode = currentNode.next;
        }
        StdOut.println();
    }


    // unit testing (required)
    public static void main(String[] args) {
        // unit test creating a deque and the adding functions
        Deque<Integer> integersDeque = new Deque<>();
        integersDeque.addFirst(1);
        integersDeque.addFirst(2);
        integersDeque.addFirst(3);
        integersDeque.display();
        integersDeque.addLast(4);
        integersDeque.addLast(5);
        integersDeque.addLast(6);
        integersDeque.display();

    }
}
