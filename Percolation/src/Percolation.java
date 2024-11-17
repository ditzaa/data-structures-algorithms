public class Percolation {
    int gridSize;
    int[][] grid;
    WeightedQuickUnionPathCompressionUF UnionFindStructure;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Constructor cannot take a negative value like: " + n);
        }

        this.gridSize = n;
        this.grid = new int[n][n];
        this.UnionFindStructure = new WeightedQuickUnionPathCompressionUF(n);

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                grid[i][j] = 0;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (validateIndex(row) && validateIndex(col)) {

        } else {
            throw new IllegalArgumentException("Indices (" + row + ", " + col + ")" +
                    " are out of bounds (1, " + gridSize + ")");
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (validateIndex(row) && validateIndex(col)) {

        } else {
            throw new IllegalArgumentException("Indices (" + row + ", " + col + ")" +
                    " are out of bounds (1, " + gridSize + ")");
        }
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (validateIndex(row) && validateIndex(col)) {

        } else {
            throw new IllegalArgumentException("Indices (" + row + ", " + col + ")" +
                    " are out of bounds (1, " + gridSize + ")");
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return 0;
    }

    // does the system percolate?
    public boolean percolates() {
        return false;
    }

    private boolean validateIndex(int index) {
        if ( index <= 0 || index < gridSize) {
            return false;
        }
        return true;
    }

    // test client (optional)
    public static void main(String[] args) {
        System.out.println("ceva");
    }
}
