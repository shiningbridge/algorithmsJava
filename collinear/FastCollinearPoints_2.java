package se.zcambridge.algs4.collinear;
//package se.zcambridge.algs4.elementary.collinear;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    private int ns = 0;     // number of line segments.
    private final int l;          // length of the point array
    private final List<LineSegment> colls = new ArrayList<LineSegment>();
    private final List<Point> endPs = new ArrayList<Point>();
    private final List<Double> slopes = new ArrayList<Double>();
//    private final Point[] pos;
    /**
     * 
     * @param points
     * @throws NullPointerException("points array cannot be null");
     * @throws IllegalArgumentException("too few points.");
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new NullPointerException("points array cannot be null");
        // finds all line segments containing 4 or more points
        l = points.length;
        Point[] pos1 = points.clone(); 
        Arrays.sort(pos1);
//        if (l < 4) throw new IllegalArgumentException("too few points.");
        // loop all possible 4 points combinations, add to ArrayList or Deque.  
        for (int i = 0; i < l; i++) {
            if (points[i] == null) throw new NullPointerException("Point Object cannot be null");
            for (int j = i + 1; j < l; j++) {
                if (points[i].compareTo(points[j]) == 0) 
                    throw new IllegalArgumentException("repeated input point is not acceptable");
            }
        }
        this.segments(pos1);
    }
    private void segments(Point[] pos) {
        ns = 0;
        Point[] otherP = new Point[l-1];

        for (int i = 0; i < l; i++) {
            for (int j = 0; j < l; j++) {
                if (j < i) otherP[j] = pos[j];
                else if (j > i) otherP[j-1] = pos[j];
                else ;
            }
            // sort use comparator.
            // create a comparator
            Comparator<Point> comp = pos[i].slopeOrder();

            // sorting array using comparator
            Arrays.sort(otherP, comp);
//            StdOut.print("slope in orders: ");
//            for (int j = 0; j < otherP.length; j++) {
//                StdOut.print(pos[i].slopeTo(otherP[j]) + ", ");
//            }
//            StdOut.println("");

            // find if exist more than 3 same slopes (includsive) then collinear appears.
            int cc = 0;
            for (int k = 1; k < otherP.length; k++) { // compare otherP.length - 1 times
                //                if ( pos[i].slopeTo(otherP[k]) == pos[i].slopeTo(otherP[k+1]) ) cc++;
                if (pos[i].slopeOrder().compare(otherP[k-1], otherP[k]) == 0) cc++;
                if (k == otherP.length-1 || pos[i].slopeOrder().compare(otherP[k], otherP[k+1]) != 0) {  
                    // judge collinear status when slope NOT equal or hit the end 
                    if (cc >= 2) {  // more than 3 compare are equal until k-th slope.
//                        double currentSlope     =  pos[i].slopeTo(otherP[k]); 
//                        Point currentEndPoint   = otherP[k];
                        // check if there exist same end points and slope
                        boolean existFlag = false;
                        for (int j = 0; j < colls.size(); j++) {
                            if (currentSlope == slopes.get(j) && currentEndPoint == endPs.get(j)) 
                                existFlag = true; 
                        }
                        if (existFlag) {
                            /* do nothing */
                        }
                        else {
                            ns++;   // counter for linesegment
//                            // assign points to an new array to find LineSegment Ending points.
//                            Point[] colP = new Point[cc+2];
//                            colP[0] = pos[i];   // first point is the outer loop point
//                            for (int j = 0; j <= cc; j++) { // following points are inner loop points found.
//                                colP[j+1] = otherP[k-cc+j];
//                            //                            }
//                            Arrays.sort(colP);
//                            colls.add(new LineSegment(colP[0],colP[cc+1]) ); 
                            colls.add(new LineSegment(pos[i],currentEndPoint) ); 
//                            endPs.add(currentEndPoint);
//                            slopes.add(currentSlope);
                        }
                    } 
                    cc = 0; // reset local counter for collinear pieces.
                }

            }
            otherP = null;  // release memory
        }
        
    }
    public           int numberOfSegments() {
        // the number of line segments
        return ns;
    }
    /**
     * the line segments 
     * 
     * @return LineSegment object array with all longest collinear line segments.
     */
    public LineSegment[] segments() {
        
        LineSegment[] lsArray = new LineSegment[colls.size()];
        return colls.toArray(lsArray);
    }

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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        collinear.segments();
        collinear.segments();
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }


    }

}
