/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FastCollinearPoints {
    private int numberOfSegments;
    private List<LineSegment> lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        // check points array not null
        if (points == null) {
            throw new IllegalArgumentException("constructor argument is null");
        }

        // check if points array has null elements
        int pointsLength = points.length;
        for (int i = 0; i < pointsLength; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("Point at index " + i + " is null");
            }
        }

        // create a copy of the points array and sort it
        Point[] sortedPoints = Arrays.copyOf(points, pointsLength);
        Arrays.sort(sortedPoints);

        // check if array has duplicates
        for (int i = 0; i < pointsLength - 1; i++) {
            if (sortedPoints[i].compareTo(sortedPoints[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicate points: " + sortedPoints[i]);
            }
        }

        this.numberOfSegments = 0;
        this.lineSegments = new ArrayList<>();
        int nbOfCollinearPoints;
        Point[] aux = Arrays.copyOf(sortedPoints, pointsLength);
        for (int i = 0; i < pointsLength; i++) {
            // Think of p = points[i] as the origin. For each other q point,
            //  determine the slope it makes with p
            Point p = sortedPoints[i];
            // Sort the points according to the slopes they make with p
            Comparator<Point> slopeComparator = p.slopeOrder();
            Arrays.sort(aux, slopeComparator);

            // debugging
            // StdOut.println("Refference point: " + p.toString());
            // for (Point q : aux) {
            //     StdOut.println(q + " - " + p.slopeTo(q));
            // }

            // 3 (or more) adjacent points are collinear (togheter with p),
            // if they have equal slopes with respect to p.
            nbOfCollinearPoints = 0;
            List<Point> collinearPoints = new ArrayList<>();
            for (int j = 1; j < aux.length - 1; j++) {
                nbOfCollinearPoints = 0;
                if (Double.compare(p.slopeTo(aux[j]), p.slopeTo(aux[j + 1])) == 0) {
                    collinearPoints = new ArrayList<>();
                    collinearPoints.add(p);
                    // nbOfCollinearPoints = 1;
                    while (j < aux.length - 1 &&
                            Double.compare(p.slopeTo(aux[j]), p.slopeTo(aux[j + 1])) == 0) {
                        nbOfCollinearPoints++;
                        collinearPoints.add(aux[j]);
                        j++;
                    }
                    j--;
                    nbOfCollinearPoints++;
                    if (j + 1 < aux.length) {
                        collinearPoints.add(aux[j + 1]);
                    }
                    if (nbOfCollinearPoints >= 3) {
                        collinearPoints.sort(Point::compareTo);
                        StdOut.println("Collinear points");
                        for (Point q : collinearPoints) {
                            StdOut.println(q + " - " + p.slopeTo(q));
                        }
                        StdOut.println();

                        // StdOut.println(p.toString() + " - " + collinearPoints.get(0));
                        if (p.compareTo(collinearPoints.get(0)) == 0) {
                            StdOut.println("Exista linie");
                            Point lastCollinearPoint = collinearPoints.get(
                                    collinearPoints.size() - 1);
                            LineSegment newLineSegment = new LineSegment(p, lastCollinearPoint);
                            lineSegments.add(newLineSegment);
                            numberOfSegments++;
                        }
                    }
                }
            }
            // add line segment
            // StdOut.println("----------------------------------");
            // StdOut.println("Vertical colliniar points?");
            // for (Point q : collinearPoints) {
            //     StdOut.println(q + " - " + p.slopeTo(q));
            // }
            // StdOut.println("----------------------------------");
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        // convert List<Point> to Point[]
        LineSegment[] newLineSegments = new LineSegment[numberOfSegments];
        for (int i = 0; i < lineSegments.size(); i++) {
            if (lineSegments.get(i) != null) {
                newLineSegments[i] = lineSegments.get(i);
            }
        }
        return newLineSegments;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
