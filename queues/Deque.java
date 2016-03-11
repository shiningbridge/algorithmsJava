package se.zcambridge.algs4.queues;


import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
//import edu.princeton.cs.algs4.LinkedQueue.Node;

/**
 * MyDeque. 
 * @author kangqiao
 *
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {
    private int l; 	// size
    private Node first;
    private Node last;

    private class Node {
        private Node pre;
        private Node next;
        public Item it;
        public Node() {
            pre = null;
            next = null;
            it = null;
        }
    }

    public Deque() {                          // construct an empty deque
        first = null;
        last = null;
        l = 0;
    }
    public boolean isEmpty() {                 // is the deque empty?
        return l == 0;
    }
    public int size() {                       // return the number of items on the deque
        return l;
    }
    public void addFirst(Item item) {          // add the item to the front
        if (item == null) throw new NullPointerException("null item is not accepted");
        Node n1 = new Node();
        n1.it = item;
        if (isEmpty()) {
            first = n1;
            last = n1;
        } else {
            n1.next = first;
            first.pre = n1;
            first = n1;
        }
        l++;
    }
    public void addLast(Item item) {           // add the item to the end
        if (item == null) throw new NullPointerException("null item is not accepted");
        Node n1 = new Node();
        n1.it = item;
        if (isEmpty()) {
            last = n1;
            first = n1;
        } else {
            n1.pre = last;
            last.next = n1;
            last = n1;
        }
        l++;
    }


    public Item removeFirst() {                // remove and return the item from the front
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = first.it;
        if (l == 1) {
            first = null;
            last = null;
        }
        else { // l >= 1
            first = first.next;		// does it automatic remove reference (by .next) to oldfirst node?
            first.pre = null;		// remove reference (by .pre) to oldfirst node.
        }
        l--;
        assert check();
        return item;		
    }
    public Item removeLast() {                 // remove and return the item from the end
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = last.it;
        if (l == 1) {
            first = null;
            last = null;
        }
        else {
            last = last.pre;	// avoid loitering
            last.next = null;		
        }	
        l--;
        assert check();
        return item;
    }
    private boolean check() {
        if (l < 0) {
            return false;
        }
        else if (l == 0) {
            if (first != null) return false;
            if (last  != null) return false;
        }
        else if (l == 1) {
            if (first == null || last == null) return false;
            if (first != last)                 return false;
            if (first.next != null)            return false;
        }
        else {
            if (first == null || last == null) return false;
            if (first == last)      return false;
            if (first.next == null) return false;
            if (last.next  != null) return false;

            // check internal consistency of instance variable l
            int numberOfNodes = 0;
            for (Node x = first; x != null && numberOfNodes <= l; x = x.next) {
                numberOfNodes++;
            }
            if (numberOfNodes != l) return false;

            // check internal consistency of instance variable last
            Node lastNode = first;
            while (lastNode.next != null) {
                lastNode = lastNode.next;
            }
            if (last != lastNode) return false;
        }

        return true;
    } 
    public Iterator<Item> iterator() {         // return an iterator over items in order from front to end
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() { return current != null; }
        public Item next() { 
            if (!hasNext()) throw new NoSuchElementException("no");
            Item item = current.it; ////// ?? cast Item to Item???
            current = current.next;
            return item;
        }
        public void remove() { throw new UnsupportedOperationException(); }
    }


    public static void main(String[] args) {   // unit testing
        Deque<String> dq = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) dq.addFirst(item); 
            else if (!dq.isEmpty()) dq.removeLast();
            //			StdOut.println("current Deque --> " + Arrays.deepToString(dq));
            StdOut.println("(" + dq.size() + ") left in Deque!");
        }
        StdOut.println("(" + dq.size() + ") left in Deque!");

    }



}