/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {
    private int numberOfSegments;
    private LineSegment[] lineSegments; // LineSegment(Point p, Point q)
    private Comparator<Point> pointComparator;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("constructor argument is null");
        }
        int pointsLength = points.length;
        for (int i = 0; i < pointsLength; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("point at index " + points[i] + "is null");
            }
            for (int j = i + 1; j < pointsLength; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException(
                            "Two points with coordiantes: " + points[i].toString()
                                    + " have the same coordinates");
                }
            }
        }

        this.numberOfSegments = 0;
        this.lineSegments = new LineSegment[10];

        // check each combination of 4 points from the input
        // if 4 points have equal slopes:
        // 1. check if the line segment made by them already exists
        // 2. if non-1., check if array needs to be resized and add it to it

        for (int i = 0; i < pointsLength; i++) {
            for (int j = i + 1; j < pointsLength; j++) {
                for (int k = j + 1; k < pointsLength &&
                        areThreePointsCollinear(points[i], points[j], points[k]); k++) {
                    for (int l = k + 1; l < pointsLength; l++) {
                        if (areThreePointsCollinear(points[j], points[k], points[l])) {
                            if (numberOfSegments == lineSegments.length) {
                                resizeLineSegmentsArray();
                            }
                            // slope order points
                            Point[] orderedPoints = orderCollinearPoints(
                                    points[i], points[j], points[k], points[l]);
                            lineSegments[numberOfSegments++] =
                                    new LineSegment(orderedPoints[0], points[3]);
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
        return lineSegments;
    }

    private void resizeLineSegmentsArray() {
        int arrayLength = this.lineSegments.length;
        LineSegment[] newArray = new LineSegment[2 * arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            newArray[i] = lineSegments[i];
        }
        lineSegments = newArray;
    }

    private boolean isSameLineSegment() {
        return false;
    }

    // private boolean areFourPointsOnTheSameLine(Point p1, Point p2, Point p3, Point p4) {
    //     if (p1.slopeTo(p2) == p2.slopeTo(p3)) {
    //         if (p2.slopeTo(p3) == p3.slopeTo(p4)) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    private boolean areThreePointsCollinear(Point p1, Point p2, Point p3) {
        if (p1.slopeTo(p1) == p2.slopeTo(p3)) {
            return true;
        }
        return false;
    }

    // private boolean isLineSegmentAlreadyAdded (Point p1, Point p2) {
    //     for (int i = 0; i < numberOfSegments; i++) {
    //         if (lineSegments[i].x = )
    //     }
    //     return false;
    // }

    // method to order the 4 points to find its endpoints
    private Point[] orderCollinearPoints(Point p1, Point p2, Point p3, Point p4) {
        Point[] pointsList = new Point[4];
        pointsList[0] = p1;
        pointsList[1] = p2;
        pointsList[2] = p3;
        pointsList[3] = p4;
        pointComparator = p1.slopeOrder();
        Arrays.sort(pointsList, pointComparator);
        return pointsList;
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
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        // Point origin = new Point(0, 0);
        // Point p1 = new Point(12, 34);
        // Point p2 = new Point(32, 53);
        // Point p3 = new Point(342, 324);
        // Point p4 = new Point(53, 21);
        // // List<Point> pointList = new ArrayList<>();
        // // pointList.add(p1);
        // // pointList.add(p2);
        // // pointList.add(p3);
        // // pointList.add(p4);
        // Point[] pointsArray = new Point[4];
        // pointsArray[0] = p1;
        // pointsArray[1] = p2;
        // pointsArray[2] = p3;
        // pointsArray[3] = p4;
        // StdOut.println("Before ordering: ");
        // for (Point point : pointsArray) {
        //     StdOut.print(point + " ");
        // }
        //
        // BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(pointsArray);
        // pointsArray = bruteCollinearPoints.orderCollinearPoints(p1, p2, p3, p4);
        // StdOut.println("\nAfter ordering");
        // for (Point point : pointsArray) {
        //     StdOut.print(point + " ");
        // }

        // Comparator<Point> comparator = p1.slopeOrder();
        // pointList.sort(comparator);
        // StdOut.println(pointList);
        // for (Point point : pointList) {
        //     StdOut.print(point.slopeTo(origin) + " ");
        // }
        // StdOut.println();
    }
}
