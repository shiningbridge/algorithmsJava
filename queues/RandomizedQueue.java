package se.zcambridge.algs4.queues;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Your randomized queue implementation must support each randomized queue operation (besides creating an iterator) 
 * in constant amortized time. That is, any sequence of M randomized queue operations (starting from an empty queue) 
 * should take at most cM steps in the worst case, for some constant c. A randomized queue containing N items must 
 * use at most 48N + 192 bytes of memory. 
 * 
 * 
 * @author kangqiao
 *
 * @param <Item>
 */
// item removed is chosen uniformly at random from items in the data structure
public class RandomizedQueue<Item> implements Iterable<Item> {
	private int l = 0;
	private Item[] it; 
	private int capacity = 1;
	private List<String> s;
	
	public RandomizedQueue() {                 // construct an empty randomized queue
		it = (Item[]) new Object[capacity];			// ugly cast !
	}
	
	public boolean isEmpty() {                 // is the queue empty?
		return l == 0;
	}
	public int size() {                        // return the number of items on the queue
		return l;
	}
	public void enqueue(Item item) {           // add the item
		if (item == null) throw new NullPointerException("null item is not accepted");
		if (l == capacity) enlargeArray();
		it[l] = item;
		l++;
	}
	private void resize(int cap)
	 {
	 Item[] copy = (Item[]) new Object[cap];
	 for (int i = 0; i < l; i++)
	 copy[i] = it[i];
	 it = copy;
	 }
	private void enlargeArray() {		
		capacity *= 2;
		resize(capacity);
	}
	private void shrinkArray() 	{
		capacity /= 2;	
		resize(capacity);
	}

	public Item dequeue() {                    // remove and return a random item
		if (isEmpty()) throw new NoSuchElementException("RandomizedQueue underflow");
		if (l == capacity/4) shrinkArray();
		int key = StdRandom.uniform(l);
		Item item = it[key];
		// exchange the last item to the removed position if it is not the last item
		if (key != l-1) exchange(key, l-1);
		it[l-1] = null; // loitering protection
		l--;
		return item;
	}
	private void exchange(int key, int i) {
		Item tmp 	= it[key];
		it[key] 	= it[i];
		it[i] 		= tmp;
	}

	public Item sample() {                     // return (but do not remove) a random item
		if (isEmpty()) throw new NoSuchElementException("RandomizedQueue underflow");
		int key = StdRandom.uniform(l);
		return it[key];
	}
	public Iterator<Item> iterator() {         // return an independent iterator over items in random order
		return new RandArrayIterator();
	}
	private class RandArrayIterator implements Iterator<Item> {
		private int current = 0;
		private int[] randArray;
//		private Random r = new Random();
		public RandArrayIterator() {
			randArray = new int[l];
			for (int i = 0; i < l; i++) {
				randArray[i] = i;
			}
			StdRandom.shuffle(randArray);
		}
		@Override
		public boolean hasNext() { return current < l; }
		@Override
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException("no");
			
			Item item = it[randArray[current++]];
			return item;
		}
		@Override
		public void remove() {		throw new UnsupportedOperationException("not implemented"); }		
	}
	
	
	public static void main(String[] args) {   // unit testing
		RandomizedQueue<String> randQ = new RandomizedQueue<String>();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			if (!item.equals("-")) randQ.enqueue(item); 
			else if (!randQ.isEmpty()) randQ.dequeue();
//			StdOut.println("current RandomizedQueue --> " + randQ.ToString());
			StdOut.println("(" + randQ.size() + ") left in RandomizedQueue!");
		}
		StdOut.println("(" + randQ.size() + ") left in RandomizedQueue!");
	}


}