/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int trials;
    private double[] percolationThresholdsArray;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException(
                    "Constructor cannot take a negative value like: " + n);
        }

        this.trials = trials;
        int numberOfSites = n * n;
        this.percolationThresholdsArray = new double[trials];

        // run the experiment for the number of trials
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            // while it does not percolate, open a random site
            while (!percolation.percolates()) {
                int randomRow = StdRandom.uniformInt(1, n + 1);
                int randomCol = StdRandom.uniformInt(1, n + 1);
                while (percolation.isOpen(randomRow, randomCol)) {
                    // StdOut.println(randomRow + " " + randomCol);
                    randomRow = StdRandom.uniformInt(1, n + 1);
                    randomCol = StdRandom.uniformInt(1, n + 1);
                }
                percolation.open(randomRow, randomCol);
            }
            int numberOfOpenSites = percolation.numberOfOpenSites();
            percolationThresholdsArray[i] = (double) numberOfOpenSites / numberOfSites;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationThresholdsArray);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolationThresholdsArray);
    }

    // Formula confidence: [mean ± 1.96 * (standard deviation / trials)]
    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return StdStats.mean(percolationThresholdsArray)
                - 1.96 * (StdStats.stddev(percolationThresholdsArray) / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return StdStats.mean(percolationThresholdsArray)
                + 1.96 * (StdStats.stddev(percolationThresholdsArray) / trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, t);
        String confidence = percolationStats.confidenceLo() + ", "
                + percolationStats.confidenceHi();
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}
