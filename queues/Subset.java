package se.zcambridge.algs4.queues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {
//	private String ToString(RandomizedQueue<String> rq) {
//		
//		for (int i = 0; i < l; i++) {
//			s.add((String) rq[i]);
//		}
//		return s.toString();
//	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * takes a command-line integer k; reads in a sequence of N strings from standard input using StdIn.readString();
		 *  and prints out exactly k of them, uniformly at random. Each item from the sequence can be printed out at most 
		 *  once. You may assume that 0 <= k <= N, where N is the number of string on standard input.
		 * 
		 */
		RandomizedQueue<String> randQ = new RandomizedQueue<String>();
		int k = Integer.parseInt(args[0]);
//		List l = new List();
		while (!StdIn.isEmpty()) {
			String item = StdIn.readString();
			randQ.enqueue(item);
//			l.add(item);
		}
		int i = 0;
		for (String s : randQ) {
			if (++i > k) break;
			StdOut.println(s);
		}
	}

}
