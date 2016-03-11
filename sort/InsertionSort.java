package se.zcambridge.algs4.sort;

import java.util.Arrays;

import edu.princeton.cs.algs4.StdOut;

public class InsertionSort {
	private int[] num;
	public InsertionSort(int[] nums) {
		// TODO Auto-generated constructor stub
		int i;
		int j;
		int exc;
		this.num = nums;
		exc = 0;
		for ( j = 1; j < num.length; j++) {
			for ( i = j; ( i > 0 && num[i] < num[i-1]); i--) {
				exchange(i, i-1);
				exc++;
				StdOut.println("==>" + exc + " exchange: " + Arrays.toString(num));
			}
			StdOut.println("---");
			StdOut.println(j + " th turn: " + Arrays.toString(num));
		}
		
	}
	
	private void exchange(int i, int j) {
		int tmp;
		tmp = num[i]; 
		num[i] = num[j];
		num[j] = tmp;		
	}

	public static void main(String[] args) {
		// parse " " space separated array to int[] array 
		String numbers = "45 46 60 79 98 80 42 28 58 47 ";
		String[] tokens = numbers.split(" ");
		int[] nums = new int[tokens.length];

		int i = 0;
		for (String token : tokens){
		    nums[i++] = Integer.parseInt(token); 
		}

		StdOut.println("Origin: " + Arrays.toString(nums));
		InsertionSort isort = new InsertionSort(nums);
		StdOut.println("Sorted: " + Arrays.toString(isort.num));
		
		// convert back int[] array to white space separated array
		for (int n = 0; n < nums.length; n++) {
			System.out.print(nums[n] + " ");
		}
	}

}
