//package se.zcambridge.algs4.puzzle8;

import java.util.Arrays;

import edu.princeton.cs.algs4.Stack;
//import java.util.Stack;

public class Board {
    private final int n;
    private final int[] tiles;
    private final int hammDist;
    private final int manhDist;
    private final int izero;
    
    /**
     * // construct a board from an N-by-N array of blocks
     * // (where blocks[i][j] = block in row i, column j)
     * 
     * @param blocks
     */
    public Board(int[][] blocks) {
        if (blocks.length < 2) throw new IllegalArgumentException("input illegal smaller than 2x2");
        this.n = blocks.length;
        int itmp = -1;
        this.tiles      = new int[n * n];
        
        // use 1D array to store the blocks.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i * n + j] = blocks[i][j]; 
            }
        }
        
        // goalTile is from 1 to end + 0
        for (int i = 0; i < n * n; i++) {
            if (this.tiles[i] == 0) {
                itmp = i;  
            }
        }
        izero = itmp; 
        
        hammDist = this.calcHamming();
        manhDist = this.calcManhattan();
    }
    
    private Board(int[] block1D, int N) {
        this.n = N;
        int itmp = -1;
        this.tiles = Arrays.copyOf(block1D, block1D.length);
        
        // goalTile is from 1 to end + 0; in the meantime find empty tile.
        for (int i = 0; i < n * n; i++) {
            if (this.tiles[i] == 0) {
                itmp = i;  
            }
        }
        izero = itmp; 
        
        hammDist = this.calcHamming();
        manhDist = this.calcManhattan();
    }

    private int calcHamming() {
        // mistaken tile count
        int h = 0;
        for (int i = 0; i < this.tiles.length; i++) {
            if (this.tiles[i] == 0 ) continue;
            h = this.tiles[i] == i+1 ? h : h + 1;   // even the last is n*n instead of 0 this works.
        }
        return h;
    }
    private int calcManhattan() {
        // Manhattan distance  = summation distance from row and col direction.
        int h = 0;
        for (int i = 0; i < this.tiles.length; i++) {
            if (this.tiles[i] == 0 ) continue;
            int targetPosition = this.tiles[i]-1; // since goalTile position is 1,2,3,...n*n-1,0
            h += Math.abs(i/n - targetPosition/n) + Math.abs(i%n - targetPosition%n);
        }
        return h;
    }


    public int dimension() {
        // board dimension N
        return this.n;
    }
    public int hamming() {
        // number of blocks out of place
        return this.hammDist;
    }
    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        return this.manhDist;
    }
    public boolean isGoal() {
        // is this board the goal board?
        return this.hammDist == 0;
    }
    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        int[] twinTiles = Arrays.copyOf(this.tiles, this.tiles.length);
        int tmp = twinTiles[0];
        if ( tmp == 0 ) { // position 1, n exchange 
            tmp = twinTiles[1];
            twinTiles[1] = twinTiles[n];
            twinTiles[n] = tmp;
        }
        else if (twinTiles[1] == 0) { // position 0, n exchange 
            twinTiles[0] = twinTiles[n];
            twinTiles[n] = tmp;
        } else { // position 0, 1 exchange 
            twinTiles[0] = twinTiles[1];
            twinTiles[1] = tmp;
        }
        return new Board(twinTiles, n);
    }
    public boolean equals(Object y) {
        // does this board equal y?
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return (Arrays.equals(this.tiles, that.tiles)); // int[][] equals to each other. 
        
    }
    public Iterable<Board> neighbors() {
        // all neighboring boards
        Stack<Board> nbs = new Stack<Board>();
        int[] nbTiles; //  = Arrays.CopyOf(this.tiles, this.tiles.length);
        if (izero > n-1) { // not 1st row
            nbTiles = Arrays.copyOf(this.tiles, this.tiles.length);
            int tmp = this.tiles[izero - n];
            nbTiles[izero - n]  = 0;
            nbTiles[izero]      = tmp;
            nbs.push(new Board(nbTiles,n));
        }
        if (izero < n*(n-1)) { // not last row
            nbTiles = Arrays.copyOf(this.tiles, this.tiles.length);
            int tmp = this.tiles[izero + n];
            nbTiles[izero + n]  = 0;
            nbTiles[izero]      = tmp;
            nbs.push(new Board(nbTiles,n));
        }
        if (izero%n != 0) { // not 1st col
            nbTiles = Arrays.copyOf(this.tiles, this.tiles.length);
            int tmp = this.tiles[izero - 1];
            nbTiles[izero - 1]  = 0;
            nbTiles[izero]      = tmp;
            nbs.push(new Board(nbTiles,n));
        }
        if (izero%n != n-1) { // not last col
            nbTiles = Arrays.copyOf(this.tiles, this.tiles.length);
            int tmp = this.tiles[izero + 1];
            nbTiles[izero + 1]  = 0;
            nbTiles[izero]      = tmp;
            nbs.push(new Board(nbTiles,n));
        }
        return nbs;
    }
    
    public String toString() {
        // string representation of this board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i * n + j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
//    public String toString(int r) {
//        String result = "";
//        for (int i = 0; i < 3; ++i) {
//          String a = String.valueOf(tiles[3*i + 0]);
//          String b = String.valueOf(tiles[3*i + 1]);
//          String c = String.valueOf(tiles[3*i + 2]);
//          result  += " " + a + " | " + b + " | " + c + " \n";
//          if (i < 2) result += "---+---+---\n";
//        } return result;
//      }

    public static void main(String[] args) {
        // unit tests (not graded)
    }
}
