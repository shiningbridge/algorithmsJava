package se.zcambridge.algs4.unionfind;
import java.util.Arrays;

public class WeightedQuickUnion {
	private int[] id;
	private int[] s; // size of the tree
	
	public WeightedQuickUnion(int N) {
		// constructor stub
		id = new int[N];
		s = new int[N];
		for (int i=0; i < N; i++){
			id[i] = i;
			s[i] = 1;
		}
	}
	
	public boolean connected(int p, int q) {
		return root(p) == root(q);
	}
	
	public void union(int p, int q) {
		// 
		int rp = root(p);
		int rq = root(q);
		if (rp!=rq) {
			// union 
			if (s[rq]>s[rp]) { // if size of p tree is larger p-->q
				id[rp] = id[rq];
				s[rq] = s[rp] + s[rq];
			}else { // otherwise q-->p
				id[rq] = id[rp];
				s[rp] = s[rp] + s[rq];
			}
			
			
		}
		
	}
	
	private int root(int x) {
		// find root		
		if (id[x] != x)
			return root(id[x]);
		else 
			return x;
	}
	
	public String toString() {
		return Arrays.toString(id); // .toString();
	}
}
