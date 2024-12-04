/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
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
            Comparator<Point> slopeComparator = points[i].slopeOrder();
            Arrays.sort(aux, slopeComparator);

            // debugging
            for (Point point : aux) {
                StdOut.println(point + " - " + points[0].slopeTo(point));
            }
            StdOut.println();
            break;
        }
        //     List<Point> collinearPoints = new ArrayList<>();
        //     nbOfCollinearPoints = 1;
        //     int iFirstSegment = -1;
        //     for (int j = 0; j < pointsLength - 1; j++) {
        //         if (i != j && points[i].slopeTo(points[j]) == points[i].slopeTo(points[j + 1])) {
        //             iFirstSegment = j;
        //             while (points[i].slopeTo(points[j]) == points[i].slopeTo(points[j + 1]) &&
        //                     j < pointsLength - 2) {
        //                 nbOfCollinearPoints++;
        //                 j++;
        //             }
        //         }
        //
        //         // 3 (or more) adjacent points are collinear if they have the same slope
        //         if (nbOfCollinearPoints >= 3) {
        //             int iLastSegment = j;
        //             for (int k = iFirstSegment; k <= iLastSegment; k++) {
        //                 collinearPoints.add(points[k]);
        //             }
        //             collinearPoints.sort(Point::compareTo);
        //             if (collinearPoints.get(0) == points[i]) {
        //                 Point firstPoint = collinearPoints.get(0);
        //                 Point lastPoint = collinearPoints.get(collinearPoints.size() - 1);
        //                 numberOfSegments++;
        //                 lineSegments.add(new LineSegment(firstPoint, lastPoint));
        //             }
        //         }
        //         j--;
        //     }
        // }
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
        // StdDraw.enableDoubleBuffering();
        // StdDraw.setXscale(0, 32768);
        // StdDraw.setYscale(0, 32768);
        // for (Point p : points) {
        //     p.draw();
        // }
        // StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        // for (LineSegment segment : collinear.segments()) {
        //     StdOut.println(segment);
        //     segment.draw();
        // }
        // StdDraw.show();
    }
}
