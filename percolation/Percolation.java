package se.zcambridge.algs4.percolation; // when upload zip file to server remove this line.

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private boolean st[][]; // site open indicator
	private int n; 			// size of row (or column)
	private WeightedQuickUnionUF wuf; // Union Find obj.
	private boolean full[]; 	// flag for Full.
	private boolean empty[]; 	// flag for Empty.
	private boolean isPercolate; // flag for percolation
	/**
	 * // create N-by-N grid, with all sites blocked
	 * 
	 * @param N
	 * @throws IllegalArgumentException if <tt>N &lt; 0</tt>
	 */
	public Percolation(int N) {  
		if (N <= 0) throw new IllegalArgumentException("Matrix size " + N + " should be larger than zero");  
		this.n = N;
		st = new boolean [N][N];
		full = new boolean [N*N];
		empty = new boolean [N*N];
		isPercolate = false;
		wuf = new WeightedQuickUnionUF(N*N); // init an Weighted union find obj.
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				st[i][j] = false;
			}
		}
		
		// init the full/empty status for first and last rows respectively. 
		// isFull() won't return true until site open.
		for (int k = 0; k < N; k++) {
			full[k] = true;
			empty[N*(N-1)+k] = true;
		}
	}

	// validate that p is a valid index
    private void validate(int p) {
        if (p < 1 || p > n) {
            throw new IndexOutOfBoundsException("index " + p + " is not between 1 and " + n);  
        }
    }
    // row, column index to 1D array index.
    private int xyTo1D(int i, int j){
    	return (i-1)*n + (j-1); // p is from 0 to N^2-1.
    }
	
    /**
     * // open site (row i, column j) if it is not open already
     * 
     * @param i row
     * @param j column
     * @throws 
     */
	public void open(int i, int j) {         
		if (isOpen(i,j)) return;
		st[i-1][j-1] = true;
		// 1. convert 1D index
		int p = xyTo1D(i, j);
		// 2. if direct neighbors are opened, union them. note the corner case. i,j = 1 or n.
		if (i!=1) { if (isOpen(i-1, j))		myUnion(p, p-n);}
		if (i!=n) { if (isOpen(i+1, j))		myUnion(p, p+n);}
		if (j!=1) { if (isOpen(i, j-1))		myUnion(p, p-1);}
		if (j!=n) { if (isOpen(i, j+1))		myUnion(p, p+1);}
		if (n == 1) {
			isPercolate = true;
		}
//		System.out.println("index p = " + p);
//		System.out.println("row = " + i + "column = " + j);
	}

	private void myUnion(int p, int pp) {
		//1. store status of root of p and pp
		int rP 	= wuf.find(p);
		int rPP = wuf.find(pp);
		//2. union
		wuf.union(p, pp);
		//3. update the status of unioned root
		int rU = wuf.find(p);	// new root of p is the unioned root
		full[rU] = full[rP] | full[rPP];
		empty[rU] = empty[rP] | empty[rPP];
		if (!isPercolate) isPercolate = full[rU] & empty[rU];
	}

	/** 
	 * // is site (row i, column j) open?
	 * 
	 * @param i row
	 * @param j column
	 * @return IndexOutOfBoundsException if i or j is outside 1 and N 
	 */
	public boolean isOpen(int i, int j) {    
		validate(i);
		validate(j);
		return st[i-1][j-1] == true;
	}	   
	
	/**
	 * // is site (row i, column j) full?
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean isFull(int i, int j) {    
		// if an open site is connected to the top row, the site is Full
		if (!isOpen(i,j)) return false;
		// 1. convert 1D index
		int p = xyTo1D(i, j);		
		// 2. see whether the ROOT of site in question is connected to top. 
		return full[wuf.find(p)];
	}	   
	
	/**
	 * // does the system percolate?
	 * 
	 * @return
	 */
	public boolean percolates() { 
		// when any of the bottom sites is 'full site' then percolates.
		if (n==1) return isFull(1,1);
		return isPercolate;
	}
	public static void main(String[] args) { // test client (optional)
		int debug;
		debug = 0;
		if (debug == 1) {
			System.out.println("input size N = ");
			int N = StdIn.readInt();
			System.out.println("map constructed with " + N +" x " + N + "sites");
			Percolation pc = new Percolation(N);
			System.out.println("input integer for row and column to be open:");
			while (!StdIn.isEmpty()) {
				int i = StdIn.readInt();
				int j = StdIn.readInt();
				pc.open(i, j);
				//            StdOut.println("row: " + i + " " + "column: " + j);

			}	
		}
	}

}
