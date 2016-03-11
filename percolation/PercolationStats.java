package se.zcambridge.algs4.percolation;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
//	private Percolation pc;
	private double[] threshold;
	private int ct; // store experiment count.
	
	/**
	 * // perform T independent experiments on an N-by-N grid
	 * 
	 * @param N
	 * @param T
	 * @throws java.lang.IllegalArgumentException if either N <= 0 or T <= 0.
	 */
	public PercolationStats(int N, int T)     {
		if (N <= 0 || T <= 0) throw new java.lang.IllegalArgumentException("N" + N + " <= 0 or T" + T + "<= 0");
		ct = T;
		threshold = new double[T];
		for (int k = 0; k < T; k++) {
			Percolation pc = new Percolation(N);
			int count = 0;
			while(!pc.percolates()){
				int i1 = StdRandom.uniform(N)+1;		// random integer from 1 to N, uniform distributed.
				int j1 = StdRandom.uniform(N)+1;
				if (!pc.isOpen(i1, j1)) {
					pc.open(i1, j1);
					count++;
				}
//				StdOut.println("Percolate after " + count + " open sites" );
			}
			threshold[k] = (double) count/N/N;				
		}
		
	}
	
	/**
	 * // sample mean of percolation threshold
	 * 
	 * @return
	 */
	public double mean() {
		return StdStats.mean(threshold); 
	}
	
	/** 
	 * // sample standard deviation of percolation threshold
	 * 
	 * @return
	 */
	public double stddev() {
		return StdStats.stddev(threshold);
	}
	
	/** 
	 * // low  endpoint of 95% confidence interval
	 * 
	 * @return
	 */
	public double confidenceLo() {
		return this.mean()-1.96*this.stddev()/(Math.sqrt(ct));
	}
	
	/**
	 * // high endpoint of 95% confidence interval
	 * 
	 * @return
	 */
	public double confidenceHi() {
		return this.mean()+1.96*this.stddev()/(Math.sqrt(ct));
	}

	public static void main(String[] args) {   // test client (described below)
		int NRun = 4;
		long[] t1 = new long[NRun];
		int T = 1000;
		for (int i = 0; i < NRun; i++) {
			int N = (int) Math.pow(2,i);
			System.out.println("% java PercolationStats N: " + N + " T: " + T);
			long startTime = System.currentTimeMillis();			
			PercolationStats ps = new PercolationStats(N, T);
			long endTime   = System.currentTimeMillis();
			System.out.println("mean			= " + ps.mean());
			System.out.println("stddev			= " + ps.stddev());
			System.out.println("95% confidence interval	= " + ps.confidenceLo() + "," + ps.confidenceHi());
			t1[i] = endTime - startTime;
			
		}
		System.out.println("total running time "); //for PercolationStats N: " + N + " T: " + T );
		System.out.println(" is:" + Arrays.toString(t1) + " ms");
	}
}