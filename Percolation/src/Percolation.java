public class Percolation {
    int gridSize;
    int[][] grid; //grid with the open status (0 = not open; 1 = open) of each object of the system
    int[][] parentGrid; //structure to connect UnionFind with the openStatusGrid
    WeightedQuickUnionPathCompressionUF UnionFindStructure;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Constructor cannot take a negative value like: " + n);
        }

        this.gridSize = n;
        this.grid = new int[n+1][n+1];
        this.parentGrid = new int[n+1][n+1];
        this.UnionFindStructure = new WeightedQuickUnionPathCompressionUF(n*n + 2);
//        for (int i = 0; i <= gridSize; i++) {
//            UnionFindStructure.union(0, i);
//            int bottomVirtualSite = n+1;
//            UnionFindStructure.union(bottomVirtualSite, bottomVirtualSite-1);
//        }

        //int bottomVirtualSite = n+1;
        int parentIndex = 1;
        int[] parent = UnionFindStructure.getParent();
        //initialize all structures
        for (int i = 1; i <= n; i++) {
            //connect virtual sites to the top and bottom of the grid
            //UnionFindStructure.union(0, i);
            //bottomVirtualSite = n;
            //UnionFindStructure.union(bottomVirtualSite, bottomVirtualSite-1);
            for (int j = 1; j <= n; j++) {
                //initialize openStatusGrid element
                //grid[i][j] = 0;
                //initialize connectionGrid elements
                parentGrid[i][j] = parent[parentIndex];
                parentIndex++;
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

    private int getParentByIndex() {
        return 0;
    }

    private void displayGrid(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.printf("%4d", grid[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private void displayVector(int[] vector) {
        for (int i = 0; i < vector.length; i++) {
            System.out.print(vector[i] + " ");
        }
        System.out.println();
    }

    //getter and setters if needed
    protected int getGridSize() {
        return gridSize;
    }

    protected void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    protected int[][] getGrid() {
        return grid;
    }

    protected void setGrid(int[][] grid) {
        this.grid = grid;
    }

    protected int[][] getParentGrid() {
        return parentGrid;
    }

    protected void setParentGrid(int[][] parentGrid) {
        this.parentGrid = parentGrid;
    }

    protected WeightedQuickUnionPathCompressionUF getUnionFindStructure() {
        return UnionFindStructure;
    }

    protected void setUnionFindStructure(WeightedQuickUnionPathCompressionUF unionFindStructure) {
        UnionFindStructure = unionFindStructure;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        System.out.println("Grid openStatus:");
        percolation.displayGrid(percolation.grid);

        System.out.println("Grid parentGrid:");
        percolation.displayGrid(percolation.parentGrid);

        System.out.println("Parent grid");
        percolation.displayVector(percolation.getUnionFindStructure().getParent());
    }
}
