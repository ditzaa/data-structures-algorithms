/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF unionFind;
    private int gridSize;
    private int[][] grid;
    // grid with the open status (0 = not open; 1 = open) of each node of the system
    private int[][] parentGrid; // structure to connect unionFind with the grid
    private int numberOfOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(
                    "Constructor cannot take a negative value, such as: " + n);
        }

        this.gridSize = n;
        this.grid = new int[n + 1][n + 1];
        this.parentGrid = new int[n + 2][n + 1];
        this.unionFind = new WeightedQuickUnionUF(n * n + 2);
        this.numberOfOpenSites = 0;

        int parentIndex = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                // initialize connectionGrid elements
                parentGrid[i][j] = parentIndex;
                parentIndex++;
            }
        }
        parentGrid[gridSize + 1][1] = parentIndex;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (validateIndex(row) && validateIndex(col) && !isOpen(row, col)) {
            grid[row][col] = 1;
            numberOfOpenSites++;
            int currentOpenedNode = parentGrid[row][col];
            if (row == 1) {
                parentGrid[row][col] = 0;
                unionFind.union(0, currentOpenedNode); // connect with top virtual site
            }
            if (row == gridSize) {
                unionFind.union(currentOpenedNode,
                                parentGrid[gridSize + 1][1]); // connect with bottom virtual site
            }

            if (validateIndexToConnectSites(row - 1, col) && isOpen(row - 1, col)) { // upper node
                int nodeToConnect = parentGrid[row - 1][col];
                unionFind.union(nodeToConnect, currentOpenedNode);
            }
            if (validateIndexToConnectSites(row, col + 1) && isOpen(row, col + 1)) { // right node
                int nodeToConnect = parentGrid[row][col + 1];
                unionFind.union(nodeToConnect, currentOpenedNode);
            }
            if (validateIndexToConnectSites(row + 1, col) && isOpen(row + 1, col)) { // lower node
                int nodeToConnect = parentGrid[row + 1][col];
                unionFind.union(nodeToConnect, currentOpenedNode);
            }
            if (validateIndexToConnectSites(row, col - 1) && isOpen(row, col - 1)) { // left node
                int nodeToConnect = parentGrid[row][col - 1];
                unionFind.union(nodeToConnect, currentOpenedNode);
            }
        }
        else {
            throw new IllegalArgumentException("Indices (" + row + ", " + col + ")" +
                                                       " are out of bounds (1, " + gridSize + ")");
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (validateIndex(row) && validateIndex(col)) {
            if (grid[row][col] == 1) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            throw new IllegalArgumentException("Indices (" + row + ", " + col + ")" + " are out "
                                                       + "of bounds (1, " + gridSize + ")");
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (validateIndex(row) && validateIndex(col)) {
            if (unionFind.find(0) == unionFind.find(parentGrid[row][col])) {
                return true;
            }

            return false;
        }
        else {
            throw new IllegalArgumentException("Indices (" + row + ", " + col + ")" +
                                                       " are out of bounds (1, " + gridSize + ")");
        }
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (unionFind.find(0) == unionFind.find(gridSize * gridSize + 1)) {
            return true;
        }
        return false;
    }

    // validate index to access grid elements
    private boolean validateIndex(int index) {
        if (index <= 0 || index > gridSize) {
            return false;
        }
        return true;
    }

    private boolean validateIndexToConnectSites(int row, int col) {
        if (row <= 0 || row > gridSize) {
            return false;
        }
        if (col <= 0 || col > gridSize)
            return false;

        return true;
    }

    // private void displayGrid(int[][] gridToDisplay) {
    //     for (int i = 1; i < gridToDisplay.length; i++) {
    //         for (int j = 1; j < gridToDisplay[i].length; j++) {
    //             System.out.printf("%4d", gridToDisplay[i][j]);
    //         }
    //         System.out.println();
    //     }
    //     System.out.println();
    // }

    // private static void displayStructures(Percolation percolation) {
    //     System.out.println("Grid openStatus:");
    //     percolation.displayGrid(percolation.grid);
    //
    //     System.out.println("Grid parentGrid:");
    //     percolation.displayGrid(percolation.parentGrid);
    //
    //     System.out.println("Percolates: " + percolation.percolates());
    //     System.out.println("--------------------------\n");
    // }
}
