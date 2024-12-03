/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class FastCollinearPoints {
    private int numberOfSegments;
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
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
                            "Two points at coordiantes: " + points[i].toString()
                                    + " are the same");
                }
            }
        }

        this.numberOfSegments = 0;
        this.lineSegments = new LineSegment[10];
        Point[] comparedPoints = new Point[pointsLength - 1]; // points compared to origin
        double[] slopesOfComparedPoints = new double[pointsLength - 1];
        int indexOfComparedPoints;

        // Think of p = points[i] as the origin. For each other q point,
        // determine the slope it makes with p
        for (int i = 0; i < pointsLength; i++) {
            indexOfComparedPoints = 0;
            for (int j = 0; j < pointsLength; j++) {
                if (i != j) {
                    slopesOfComparedPoints[indexOfComparedPoints]
                            = points[i].slopeTo(points[j]);
                    comparedPoints[indexOfComparedPoints++] = points[j];
                }
            }
            // Sort the points according to the slopes they makes with p

            Comparator<Point> slopeComparator = points[i].slopeOrder();
            // private void sort(Point[] points, int[] slopeOfPoints, int lo, int hi) {
            sort(comparedPoints, slopesOfComparedPoints, 0, pointsLength - 1);
            

            // 3 (or more) adjacent points are collinear if they have the same slope
            int nbOfPointsWithSameSlope = 0;
            int indexFirstPointOfLine = -1;
            int indexLastPointOfLine = -1;
            boolean areCollinearPoints = false;
            for (int k = 0; k < pointsLength - 2; k++) {
                if (slopesOfComparedPoints[k] == slopesOfComparedPoints[k + 1]) {
                    nbOfPointsWithSameSlope++;
                    if (nbOfPointsWithSameSlope >= 3) {
                        areCollinearPoints = true;
                        indexFirstPointOfLine = k - nbOfPointsWithSameSlope + 1;
                        indexLastPointOfLine = k;
                    }
                }
                else {
                    // indexLastPointOfLine = k;
                    nbOfPointsWithSameSlope = 0;
                }
            }

            if (areCollinearPoints) {
                LineSegment newLineSegment = new LineSegment(comparedPoints[indexFirstPointOfLine],
                                                             comparedPoints[indexLastPointOfLine]);
                if (numberOfSegments <= lineSegments.length) {
                    resizeLineSegmentsArray();
                }
                lineSegments[numberOfSegments++] = newLineSegment;
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        // reallocate memory in order to avoid null elements
        LineSegment[] newLineSegments = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            newLineSegments[i] = lineSegments[i];
        }
        return newLineSegments;
    }

    private void resizeLineSegmentsArray() {
        int arrayLength = this.lineSegments.length;
        LineSegment[] newArray = new LineSegment[2 * arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            newArray[i] = lineSegments[i];
        }
        lineSegments = newArray;
    }

    private void merge(Point[] points, double[] slopeOfPoints, int lo, int mid, int hi) {
        // length of temp arrays
        int n1 = mid - hi + 1;
        int n2 = hi - 1;

        // create temp arrays for slope sizes
        Point[] subPointsArray1 = new Point[n1];
        Point[] subPointsArray2 = new Point[n2];
        // create temp arrays for slope sizes
        int subSlopeArray1[] = new int[n1];
        int subSlopeArray2[] = new int[n2];

        // initial indices of first and second subarrays
        int i = 0, j = 0;

        int k = lo;
        while (i < n1 && j < n2) {
            if (slopeOfPoints[i] <= slopeOfPoints[j]) {
                points[k] = subPointsArray1[i];
                slopeOfPoints[k] = subSlopeArray1[i];
                i++;
            }
            else {
                points[k] = subPointsArray1[j];
                slopeOfPoints[k] = subSlopeArray1[j];
                j++;
            }
            k++;

            // copy remaining elements of left subbarrays if any
            while (i < n1) {
                points[k] = subPointsArray1[i];
                slopeOfPoints[k] = subSlopeArray1[i];
                i++;
                k++;
            }

            // copy remaining elements of right subbarrays if any
            while (j < n2) {
                points[k] = subPointsArray1[j];
                slopeOfPoints[k] = subSlopeArray1[j];
                j++;
                k++;
            }
        }
    }

    private void sort(Point[] points, double[] slopeOfPoints, int lo, int hi) {
        if (lo < hi) {
            int mid = lo + (hi - 1) / 2;

            sort(points, slopeOfPoints, lo, mid);
            sort(points, slopeOfPoints, mid, hi);

            merge(points, slopeOfPoints, lo, mid, hi);

        }

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
