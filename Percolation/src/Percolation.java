public class Percolation {
    private int gridSize;
    private int[][] grid; //grid with the open status (0 = not open; 1 = open) of each object of the system
    private int[][] parentGrid; //structure to connect UnionFind with the openStatusGrid
    private WeightedQuickUnionPathCompressionUF UnionFindStructure;
    private int[] parent;//parent from UF structure, as well as index of the virtual bottom site
    private int parentSize;
    private int numberOfOpenSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Constructor cannot take a negative value like: " + n);
        }

        this.gridSize = n;
        this.grid = new int[n+1][n+1];
        this.parentGrid = new int[n+1][n+1];
        this.parentSize = n*n + 2; //+2 for virtual sites
        this.UnionFindStructure = new WeightedQuickUnionPathCompressionUF(parentSize);
        this.parent = UnionFindStructure.getParent();
        this.numberOfOpenSites = 0;
        //this.parent[n*n + 1] = 0;

        //int bottomVirtualSite = n+1;
        int parentIndex = 1;
        //int[] parent = UnionFindStructure.getParent();
        //initialize all structures
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                //initialize connectionGrid elements
                parentGrid[i][j] = parent[parentIndex];
                parentIndex++;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (validateIndex(row) && validateIndex(col)) {
            grid[row][col] = 1;
            if (row == 1) {
                int previousRoot = parentGrid[row][col];
                parentGrid[row][col] = 0;
                numberOfOpenSites++;
                UnionFindStructure.union(0, previousRoot); //conect with top virtual site
            } else if (row == gridSize) {
                int previousRoot = parentGrid[row][col];
                parentGrid[row][col] = parentSize - 1;
                UnionFindStructure.union(parentSize - 1, previousRoot); //connect with bottom virtual site
            }

            int currentOpenedNode = parentGrid[row][col];
            if (validateIndexToConnectSites(row-1, col) && isOpen(row-1, col)) { //upper node
                int nodeToConnect = parentGrid[row-1][col];
                UnionFindStructure.union(nodeToConnect, currentOpenedNode);
                parentGrid[row][col] = parentGrid[row-1][col];
            }
            if (validateIndexToConnectSites(row, col+1) &&isOpen(row, col+1)) { //right node
                int nodeToConnect = parentGrid[row][col+1];
                UnionFindStructure.union(nodeToConnect, currentOpenedNode);
            }
            if (validateIndexToConnectSites(row+1, col) &&isOpen(row+1, col)) { //lower node
                int nodeToConnect = parentGrid[row+1][col];
                UnionFindStructure.union(nodeToConnect, currentOpenedNode);
            }
            if (validateIndexToConnectSites(row, col-1) && isOpen(row, col-1)) { //left node
                int nodeToConnect = parentGrid[row][col-1];
                UnionFindStructure.union(nodeToConnect, currentOpenedNode);
                System.out.println("Am intrat pe aici jaja");
            }
        } else {
            throw new IllegalArgumentException("Indices (" + row + ", " + col + ")" +
                    " are out of bounds (1, " + gridSize + ")");
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (validateIndex(row) && validateIndex(col)) {
            if (grid[row][col] == 1) {
                return true;
            } else {
                return false;
            }
        } else {
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
        } else {
            throw new IllegalArgumentException("Indices (" + row + ", " + col + ")" +
                    " are out of bounds (1, " + gridSize + ")");
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (UnionFindStructure.connected(0, parentSize - 1)){
            return true;
        }

        return false;
    }

    private boolean validateIndex(int index) {
        if ( index <= 0 || index > gridSize) {
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

    private int getParentByIndex() {
        return 0;
    }

    private void displayGrid(int[][] grid) {
        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[i].length; j++) {
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

    //getters and setters
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

    private static void displayStructures(Percolation percolation) {
        System.out.println("Grid openStatus:");
        percolation.displayGrid(percolation.grid);

        System.out.println("Grid parentGrid:");
        percolation.displayGrid(percolation.parentGrid);

        System.out.println("Parent grid");
        percolation.displayVector(percolation.getUnionFindStructure().getParent());
        System.out.println("--------------------------------");
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(5);
        percolation.open(1, 2);
        //displayStructures(percolation);
        percolation.open(2, 2);
        //displayStructures(percolation);
        percolation.open(3, 2);
        displayStructures(percolation);
//        percolation.open(4, 2);
//        displayStructures(percolation);
        percolation.open(4, 3);
        displayStructures(percolation);
        percolation.open(5, 3);
        //System.out.println();
        displayStructures(percolation);
        System.out.println(percolation.percolates());
    }
}
