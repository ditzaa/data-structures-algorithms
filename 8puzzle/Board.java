/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

public class Board {
    private int boardSize;
    private int[][] tiles;
    // for solver
    // protected int priority;
    // protected int moves;
    // protected Board initialBoard;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.boardSize = tiles.length;
        this.tiles = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(boardSize + "\n");
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return boardSize;
    }

    // number of tiles out of place
    public int hamming() {
        int distance = 0;
        int goalNumber = 1;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (tiles[i][j] != goalNumber && tiles[i][j] != 0) {
                    distance++;
                }
                goalNumber++;
            }

        }
        return distance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        // for each site, get its goal number, calculate the distance
        // and add it to the distance
        int distance = 0;
        int goalNumber = 1;
        int row = -1;
        int col = -1;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                // don't count the last goal element, it should be the blank tile
                if (i == boardSize - 1) {
                    if (j == boardSize - 1) {
                        return distance;
                    }
                }
                // find the coordiantes of the goal number in our tiles array
                row = findRowTile(goalNumber);
                col = findColTile(goalNumber);
                // calculate nb of moves to replace the current tile with the goal one
                int newDistance = Math.abs(i - row) + Math.abs(j - col);
                // StdOut.println(goalNumber + " " + newDistance + " (" + i + "," + row + "; " +
                //                        j + "," + col + ")");
                distance += newDistance;
                goalNumber++;
            }
        }

        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int distance = 0;
        int goalNumber = 1;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (tiles[i][j] != goalNumber && tiles[i][j] != 0) {
                    return false;
                }
                goalNumber++;
            }
        }

        // last tile is blank tile (==0)
        if (tiles[boardSize - 1][boardSize - 1] != 0) {
            return false;
        }

        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.boardSize != that.boardSize) return false;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> boardQueue = new Queue<>();
        // coordinates of blank tile
        int row = findRowTile(0);
        int col = findColTile(0);

        Board newBoard;
        // upper neighbour
        if (row - 1 >= 0) {
            newBoard = this.copy();
            exchangeTiles(newBoard.tiles, row, col, row - 1, col);
            boardQueue.enqueue(newBoard);
        }
        // righter neighbour
        if (col + 1 < boardSize) {
            newBoard = this.copy();
            exchangeTiles(newBoard.tiles, row, col, row, col + 1);
            boardQueue.enqueue(newBoard);
        }
        // below neighbour
        if (row + 1 < boardSize) {
            newBoard = this.copy();
            exchangeTiles(newBoard.tiles, row, col, row + 1, col);
            boardQueue.enqueue(newBoard);
        }
        // below neighbour
        if (col - 1 >= 0) {
            newBoard = this.copy();
            exchangeTiles(newBoard.tiles, row, col, row, col - 1);
            boardQueue.enqueue(newBoard);
        }
        return boardQueue;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        // coordinates for pair of tiles
        int row1 = 0;
        int row2 = 1;
        int col1 = 0;
        int col2 = 0;

        // check if any of the tiles is blank they have the same coordinates
        while (this.tiles[row1][col1] == 0) {
            col1++;
        }
        while (this.tiles[row2][col2] == 0) {
            col2++;
        }

        Board twin = this.copy();

        // exchange tiles for creating a twin
        // exchangeTiles(int[][] tiles, int row0, int col0, int row1, int col1)
        exchangeTiles(twin.tiles, row1, col1, row2, col2);
        // StdOut.print("In functie \n" + twin.toString());
        return twin;
    }

    private int findRowTile(int number) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (tiles[i][j] == number) {
                    return i;
                }
            }
        }
        return -1;
    }

    private int findColTile(int number) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (tiles[i][j] == number) {
                    return j;
                }
            }
        }
        return -1;
    }

    private Board copy() {
        int boardSize = this.dimension();
        int[][] copiedTiles = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                copiedTiles[i][j] = this.tiles[i][j];
            }
        }
        Board newBoard = new Board(copiedTiles);
        return newBoard;
    }

    // exchange values between tile0 and tile1
    private void exchangeTiles(int[][] tiles, int row0, int col0, int row1, int col1) {
        int aux = tiles[row0][col0];
        tiles[row0][col0] = tiles[row1][col1];
        tiles[row1][col1] = aux;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        // StdOut.println("Initial:");
        // StdOut.println(initial.toString());
        // StdOut.println("Hamming: " + initial.hamming());
        // StdOut.println("Manhattan: " + initial.manhattan());
        // StdOut.println("Is goal: " + initial.isGoal());
        // Iterable<Board> iterable = initial.neighbors();
        // StdOut.println("Vecini");
        // for (Board board : iterable) {
        //     StdOut.print(board.toString());
        // }

        // create twin
        // StdOut.println("Twin:");
        // Board twin = initial.twin();
        // StdOut.println(twin.toString());

        // // declare a goal 3x3 array
        // int[][] goal3Tiles = new int[3][3];
        // int nb = 1;
        // for (int i = 0; i < n; i++)
        //     for (int j = 0; j < n; j++) {
        //         goal3Tiles[i][j] = nb;
        //         nb++;
        //     }
        // goal3Tiles[2][2] = 0;
        // Board goal3Board = new Board(goal3Tiles);
        // StdOut.println(goal3Board.toString());
        // StdOut.println("Is equal: " + initial.equals(goal3Board));

        // // solve the puzzle
        // Solver solver = new Solver(initial);
        //
        // // print solution to standard output
        // if (!solver.isSolvable())
        //     StdOut.println("No solution possible");
        // else {
        //     StdOut.println("Minimum number of moves = " + solver.moves());
        //     for (Board board : solver.solution())
        //         StdOut.println(board);
        // }
    }

}
