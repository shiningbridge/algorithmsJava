package se.zcambridge.algs4.sort;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class MergeSort {
	private int[] aux;
	private int mc = 0; // merge count
	public int[] getAux() {
		return aux;
	}

	public void setAux(int[] aux) {
		this.aux = aux;
	}

	private boolean alreadyExecuted; 

	public void sort(int[] list) {
		sort(list, 0, list.length-1);
	}
	/**
	 * sort items from index p to r in a list, list. 
	 * 
	 * @param list is a list
	 * @param p is the start index
	 * @param r is the end index
	 */
	public void sort(int[] list, int p, int r) {
		if (!alreadyExecuted) {
			aux = new int[list.length];
			alreadyExecuted = true;
		}
		// change the list to be ordered version between p and r.
		if (p>=r) return;
		int q = (r-p)/2 + p;
		sort(list, p, q);
		sort(list, q+1,r);
		merge(list, p, q, r); mc++;
//        System.out.println("aux = " + Arrays.toString(aux));
        System.out.println("mc = " + mc + ", list = " + Arrays.toString(list));

	}
	
	private void merge(int[] list, int p, int q, int r) {
		// p to q is sorted q+1 to r is sorted and merge function is to merge to a larger sorted range, p to r, in list.
//		// case of p==q r==p+1; only two item merge
//		if (p==q) list[p] = list[p]<list[q+1]?list[p]:list[q+1];
//		// case of more than 2 item merge.
//		else if (list[p]<list[q+1]) {
		int j = 0; int k = 0; 
		for (int i = p; i <= r; i++) {
			if (k >= (r-q)) {				// RIGHT part item exosted. 
				aux[i] = list[p+j];			// assign the LEFT part items to the aux list.
				j++; 						// --> pointer increase
			} else if (j > (q-p)) {			// LEFT part item exosted. 
				aux[i] = list[q+1+k];		// assign the RIGHT part items to the aux list.
				k++; 
			} else 
				if (list[p+j] <= list[q+1+k]) {	
				aux[i] = list[p+j];			// assign the LEFT part items to the aux list. since it is smaller
				j++; 						// --> pointer increase
			} else if (list[p+j] > list[q+1+k])  {	
				aux[i] = list[q+1+k];		// assign the RIGHT part items to the aux list. since it is smaller
				k++; 						// --> pointer increase
			}
		}
		// assign aux to original list 
		for (int i = p; i <= r; i++) {
			list[i] = aux[i];
		} 
	}

	public static void main(String[] args) {
		MergeSort ms = new MergeSort();
		// parse " " space separated array to int[] array 
        String numbers = "73 34 56 17 78 12 52 63 42 55 32 23";
        String[] tokens = numbers.split(" ");
        int[] nums = new int[tokens.length];
        int i = 0;
        for (String token : tokens){
            nums[i++] = Integer.parseInt(token); 
        }
        StdOut.println("Origin: " + Arrays.toString(nums));        
		ms.sort(nums);
		System.out.println("sorted: " + Arrays.toString(ms.getAux()));
	}

}
