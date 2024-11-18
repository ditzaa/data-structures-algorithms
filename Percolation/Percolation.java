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
                    "Constructor cannot take a negative value like: " + n);
        }

        this.gridSize = n;
        this.grid = new int[n + 1][n + 1];
        this.parentGrid = new int[n + 1][n + 1];
        this.unionFind = new WeightedQuickUnionUF(n * n);
        this.numberOfOpenSites = 0;
        // this.parentSize = n * n + 2; //+2 for virtual sites

        int parentIndex = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                // initialize connectionGrid elements
                parentGrid[i][j] = parentIndex;
                parentIndex++;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (validateIndex(row) && validateIndex(col)) {
            grid[row][col] = 1;
            numberOfOpenSites++;
            if (row == 1) {
                int previousRoot = parentGrid[row][col];
                parentGrid[row][col] = 0;
                unionFind.union(0, previousRoot); // conect with top virtual site
            }
            // is it neccesary?
            // else if (row == gridSize) {
            //     int previousRoot = parentGrid[row][col];
            //     parentGrid[row][col] = parentSize - 1;
            //     UnionFindStructure.union(parentSize - 1,
            //                              previousRoot); // connect with bottom virtual site
            // }

            int currentOpenedNode = parentGrid[row][col];
            if (validateIndexToConnectSites(row - 1, col) && isOpen(row - 1, col)) { // upper node
                int nodeToConnect = parentGrid[row - 1][col];
                unionFind.union(nodeToConnect, currentOpenedNode);
                // parentGrid[row][col] = parentGrid[row - 1][col];
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
            throw new IllegalArgumentException("Indices (" + row + ", " + col + ")" +
                                                       " are out of bounds (1, " + gridSize + ")");
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (validateIndex(row) && validateIndex(col)) {
            if (parentGrid[row][col] == 0) {
                return true;
            }

            return false;
        }
        else {
            throw new IllegalArgumentException("Indices (" + row + ", " + col + ")" +
                                                       " are out of bounds (1, " + gridSize + ")");
        }
    }

    // validate index to acces grid elements
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

    public static void main(String[] args) {

    }
}
