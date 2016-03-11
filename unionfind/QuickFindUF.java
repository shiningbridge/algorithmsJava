package se.zcambridge.algs4.unionfind;
import java.util.Arrays;

public class QuickFindUF {
	private int[] id;
	public QuickFindUF(int N) {
		id = new int[N];
		for (int i=0; i < N; i++){
			id[i] = i;
		}
	}
	
	public void union(int p, int q){
//		turn id[p] to id[q] if not connected. 
		if (connected(p,q)) return;
		int pid = id[p];
		int tar_id = id[q];
		for (int i=0; i < id.length	; i++) {
			if (id[i] == pid) id[i] = tar_id; 
		}
	}
	
	public boolean connected(int p, int q) {
		return (id[p] == id[q]);
	}

	public String toString() {
		
//		System.out.println(Arrays.toString(id));
		return Arrays.toString(id); // .toString();
	}
}
