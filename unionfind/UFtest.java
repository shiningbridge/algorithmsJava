package se.zcambridge.algs4.unionfind;

public class UFtest {

	public static void main(String[] args) {
		
		int t = 2;
		if (t == 1) {
//			1-6 2-6 9-2 3-8 2-5 6-0
		QuickFindUF uf = new QuickFindUF(10);
		uf.union(5,2);
		uf.union(0,2);
		uf.union(5,9);
		uf.union(2,1);
		uf.union(3,9);
		uf.union(4,6);

		System.out.println("jiuzhe?" + uf.toString());
		}
		else if (t == 2) {
			WeightedQuickUnion uf2 = new WeightedQuickUnion(10);
			uf2.union(4,0);
			uf2.union(9,2);
			uf2.union(7,8);
			uf2.union(5,1);
			uf2.union(3,0);
			uf2.union(2,8);
			uf2.union(0,1);
			uf2.union(2,4);
			uf2.union(2,6);

			System.out.println("jiuzhe?" + uf2.toString());
		}else if (t == 3) {
//			find all tree size
//			if exist 
		}
		
		
		
	}

}
