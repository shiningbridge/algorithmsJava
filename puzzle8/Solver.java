//package se.zcambridge.algs4.puzzle8;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private int moves;
    private final MinPQ<SearchNode> pq,pq2;
    private final Stack<Board> solutionBoards = new Stack<Board>(); 
    
    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        // put solution to Stack
        this.pq = new MinPQ<SearchNode>(new ManhattanComparator());
        this.pq2 = new MinPQ<SearchNode>(new ManhattanComparator());
        pq.insert(new SearchNode(initial));
        pq2.insert(new SearchNode(initial.twin())); // twin board 
        // Need to be approved.
        // solve one of the board means the twin board is unSolvable.
        while (!pq2.min().board.isGoal() && !pq.min().board.isGoal()) {
            // dequeue 
            SearchNode currentSearchNode  = pq.delMin(); 
            SearchNode currentSearchNode2 = pq2.delMin();
//            // update move
//            currentSearchNode.moves     = (currentSearchNode.parent != null)    ? currentSearchNode.parent.moves + 1    : 0;
//            currentSearchNode2.moves    = (currentSearchNode2.parent != null)   ? currentSearchNode2.parent.moves + 1   : 0;
            // insert neighbors
            for (SearchNode neighbNode : currentSearchNode.children())  pq.insert(neighbNode);
            for (SearchNode neighbNode : currentSearchNode2.children()) pq2.insert(neighbNode);
        }
        moves = (pq.min().board.isGoal()) ? pq.min().moves : -1;
     // trace back to first through the parent pointer
        SearchNode current = (pq.min().board.isGoal()) ? pq.min() : pq2.min(); 
        while (current.parent != null) {
            Board nextBoard = current.board;
            current = current.parent;
            solutionBoards.push(nextBoard);
        }
        solutionBoards.push(initial);
    }
    private class ManhattanComparator implements Comparator<SearchNode> {

        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            return ((Integer) o1.priority).compareTo((Integer) o2.priority);
        }
        
    }
    private class HammingComparator implements Comparator<SearchNode> {

        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            return ((Integer) o1.priority).compareTo((Integer) o2.priority);
        }
        
    }
    
    private class SearchNode {
        private int moves = 0; 
        private final int priority;
        private Board board;
        public  SearchNode parent;
        
        private SearchNode(Board bod) {
            this.moves = 0;
            this.parent = null;
            this.priority = bod.manhattan() + moves;
            this.board = bod;
        }
        private SearchNode(SearchNode parent, Board bod, int moves) {
            if (moves < 0) throw new IllegalArgumentException("can not move less than 0");
            this.moves = moves;
            this.parent = parent;
            this.priority = bod.manhattan() + moves;
            this.board = bod;
        }
        
        private List<SearchNode> children() {
            List<SearchNode> children = new ArrayList<SearchNode>();
            for (Board nextBoard : this.board.neighbors()) {
                if (this.parent != null && nextBoard.equals(this.parent.board)) continue; // avoid move back to previous step.
                children.add(new SearchNode(this, nextBoard, this.moves + 1));
            }
            return children;
        }
    }
    
    
    public boolean isSolvable() {
        // is the initial board solvable?
        return this.moves > -1;
    }
    public int moves() {    
        // min number of moves to solve initial board; -1 if unsolvable
        return this.moves;
    }
    public Iterable<Board> solution() {  
        // sequence of boards in a shortest solution; null if unsolvable
        return isSolvable() ? this.solutionBoards : null;
    }
    

    public static void main(String[] args) {
        // solve a slider puzzle (given below)
     // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
        
    }
}
