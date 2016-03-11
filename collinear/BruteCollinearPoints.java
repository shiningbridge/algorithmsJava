package se.zcambridge.algs4.collinear;
//package se.zcambridge.algs4.elementary.collinear;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private int ns = 0;     // number of line segments.
    private final List<LineSegment> colls = new ArrayList<LineSegment>();
    private final Point[] pos;
//    private Point[] points; 
//    private Deque<Point[]> fourPQ = new Deque<Point[]>();
    
    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        if (points == null) throw new NullPointerException("points array cannot be null");
        pos = points.clone(); 
        Arrays.sort(pos);
        // loop all possible 4 points combinations, add to ArrayList or Deque.  
        for (int i = 0; i < pos.length; i++) {
            if (pos[i] == null) throw new NullPointerException("Point Object cannot be null");
            for (int j = i + 1; j < pos.length; j++) {
                if (pos[i].compareTo(pos[j]) == 0) throw new IllegalArgumentException("repeated input point is not acceptable");
                for (int k = j + 1; k < pos.length; k++) {
                    for (int l = k + 1; l < pos.length; l++) {
                        if (pos[i].slopeTo(pos[j]) == pos[i].slopeTo(pos[k]) & 
                                pos[i].slopeTo(pos[j]) == pos[i].slopeTo(pos[l]) ) { // colinear happened
                            ns++;
                            colls.add(new LineSegment(pos[i],pos[l]) ); 
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        // the number of line segments
        return ns;
    }

    public LineSegment[] segments() {
        return colls.toArray(new LineSegment[colls.size()]);
    }
    /**
     * Unit Test
     * @param args
     */
    public static void main(String[] args) {

        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.show(0);
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
    }

 }





