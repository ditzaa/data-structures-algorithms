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
import java.util.List;

public class BruteCollinearPoints {
    private int numberOfSegments;
    private List<LineSegment> lineSegments; // LineSegment(Point p, Point q)

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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

        // check each combination of 4 points from the input
        // if 4 points have equal slopes:
        // 1. check if the line segment made by them already exists
        // 2. if non-1., check if array needs to be resized and add it to it
        Arrays.sort(points);

        for (int i = 0; i < pointsLength; i++) {
            for (int j = i + 1; j < pointsLength; j++) {
                for (int k = j + 1; k < pointsLength; k++) {
                    if (arePointsCollinear(points[i], points[j], points[k])) {
                        for (int l = k + 1; l < pointsLength; l++) {
                            if (arePointsCollinear(points[i], points[j], points[k], points[l])) {
                                Point[] orderedPoints = new Point[4];
                                orderedPoints[0] = points[i];
                                orderedPoints[1] = points[j];
                                orderedPoints[2] = points[k];
                                orderedPoints[3] = points[l];
                                Arrays.sort(orderedPoints, Point::compareTo);

                                if (orderedPoints[0].compareTo(points[i]) == 0 &&
                                        orderedPoints[3].compareTo(points[l]) == 0) {
                                    LineSegment newLineSegment =
                                            new LineSegment(points[i], points[l]);
                                    lineSegments.add(newLineSegment);
                                    numberOfSegments++;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] newLineSegments = new LineSegment[numberOfSegments];
        for (int i = 0; i < lineSegments.size(); i++) {
            if (lineSegments.get(i) != null) {
                newLineSegments[i] = lineSegments.get(i);
            }
        }
        return newLineSegments;
    }

    private boolean arePointsCollinear(Point p1, Point p2, Point p3) {
        if (p1.slopeTo(p2) == p1.slopeTo(p3)) {
            return true;
        }
        return false;
    }

    private boolean arePointsCollinear(Point p1, Point p2, Point p3, Point p4) {
        if (p1.slopeTo(p2) == p1.slopeTo(p3) && p1.slopeTo(p3) == p1.slopeTo(p4)) {
            return true;
        }
        return false;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        StdOut.println(collinear.numberOfSegments);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
