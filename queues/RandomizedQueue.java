/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 4;

    private Item[] array;
    private int numberOfItems;
    // private int lastElementIndex;

    // construct an empty randomized queue
    public RandomizedQueue() {
        numberOfItems = 0;
        array = (Item[]) new Object[INIT_CAPACITY];
        // lastElementIndex = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        if (numberOfItems == 0) {
            return true;
        }
        return false;
    }

    // return the number of items on the randomized queue
    public int size() {
        return numberOfItems;
    }

    // add the item
    public void enqueue(Item item) {
        // add an item at the beginning of the queue
        if (item == null) {
            throw new IllegalArgumentException("Cannot add a null item to the queue");
        }

        if (array.length == numberOfItems + 1) {
            resize();
        }
        array[numberOfItems] = item;
        numberOfItems++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Cannot return sample of empty queue");
        Item itemToRemove;
        int randomIndex = StdRandom.uniformInt(numberOfItems);
        itemToRemove = array[randomIndex];
        for (int i = randomIndex; i < numberOfItems - 1; i++) {
            array[i] = array[i + 1];
        }
        StdOut.println("Last elem index: " + numberOfItems);
        array[--numberOfItems] = null;
        // numberOfItems--;
        return itemToRemove;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("Cannot return sample of empty queue");
        return array[StdRandom.uniformInt(numberOfItems)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayItterator();
    }

    private class ArrayItterator implements Iterator<Item> {
        private int i = 0;

        public boolean hasNext() {
            return i < numberOfItems;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException(
                    "Next method called when the are no more items to return");
            Item item = array[i];
            i++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove operation not supported");
        }

    }

    private void resize() {
        Item[] newArray = (Item[]) new Object[2 * array.length];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        numberOfItems = array.length - 1;
        array = newArray;
    }

    private void display() {
        StdOut.println("Length of array: " + array.length);
        StdOut.println("Last element index: " + numberOfItems);
        for (int i = 0; i < array.length && array[i] != null; i++) {
            StdOut.print(array[i] + " ");
        }
        StdOut.println("\n");
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        randomizedQueue.enqueue(1);
        randomizedQueue.enqueue(2);
        randomizedQueue.enqueue(3);
        randomizedQueue.enqueue(4);
        randomizedQueue.enqueue(5);
        randomizedQueue.display();
        int removedItem = randomizedQueue.dequeue();
        StdOut.println("Removed item: " + removedItem);
        randomizedQueue.display();
        StdOut.println("Random item: " + randomizedQueue.sample());

        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
    }
}
