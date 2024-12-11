/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private MinPQ<SearchNode> priorityQueue;
    private int moves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Board argument is null");
        }
        this.priorityQueue = new MinPQ<SearchNode>();
        this.moves = 0;
        SearchNode initialNode = new SearchNode(initial, null, initial.manhattan(), 0);
        priorityQueue.insert(initialNode);

        // Repeat this procedure until the search node dequeued corresponds to the goal board.
        SearchNode deletedNode = initialNode;
        // debug
        int i = 0;
        while (!deletedNode.board.isGoal() && !priorityQueue.isEmpty()) {
            // debug
            StdOut.println("-------Step" + i + "--------");
            for (SearchNode node : priorityQueue) {
                StdOut.println(node.board.toString());
                StdOut.println("Manhattan: " + node.board.manhattan());
                StdOut.println("Moves: " + node.moves);
                StdOut.println("Priority: " + node.priority);
                StdOut.println("*********************");
                StdOut.println();
            }
            StdOut.println();


            // all neighboring search nodes of current node
            Iterable<Board> neighbors = deletedNode.board.neighbors();
            // delete from the priority queue the search node with the minimum priority
            deletedNode = priorityQueue.delMin();
            // get first neighbor
            // SearchNode nextCurrentNode = null;
            for (Board board : neighbors) {
                deletedNode = new SearchNode(board, deletedNode.board,
                                             board.manhattan() + deletedNode.moves + 1,
                                             deletedNode.moves + 1);
                break;
            }
            // insert onto the priority queue all neighboring search nodes
            for (Board board : neighbors) {
                // public SearchNode(Board board, Board previousBoard, int priority, int moves)
                SearchNode newSearchNode = new SearchNode(board, deletedNode.board,
                                                          board.manhattan() + deletedNode.moves,
                                                          deletedNode.moves);
                priorityQueue.insert(newSearchNode);
                if (deletedNode.priority > newSearchNode.priority) {
                    deletedNode = newSearchNode;
                }
            }
            i++;
        }
        StdOut.println(deletedNode.board.toString());
        StdOut.println("Manhattan: " + deletedNode.board.manhattan());
        StdOut.println("Moves: " + deletedNode.moves);
        StdOut.println("Priority: " + deletedNode.priority);

        if (priorityQueue.isEmpty()) {
            moves = -1;
        }
        else {
            moves = deletedNode.moves;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return true;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return null;
    }

    // private SearchNode findMinPriorityNode (MinPQ<SearchNode> queue) {
    //     SearchNode minNode = queue.delMin();
    //     return
    // }

    private class SearchNode implements Comparable<SearchNode> {
        private int priority;
        private int moves;
        private Board previousBoard;
        private Board board;

        public SearchNode(Board board, Board previousBoard, int priority, int moves) {
            this.board = board;
            this.previousBoard = previousBoard;
            this.priority = priority;
            this.moves = moves;
        }

        @Override
        public int compareTo(SearchNode otherNode) {
            // int hammingComparator = Integer.compare(this.board.hamming(),
            //                                         otherNode.board.hamming());
            // if (hammingComparator != 0) {
            //     return hammingComparator;
            // }
            // return Integer.compare(this.board.manhattan(), otherNode.board.manhattan());
            return Integer.compare(this.priority, otherNode.priority);
        }

        // public class PlayerRankingComparator implements Comparator<SearchNode> {
        //     public int compare(SearchNode o1, SearchNode o2) {
        //         int hammingComparator = Integer.compare(o1.board.hamming(), o2.board.hamming());
        //         if (hammingComparator != 0) {
        //             return hammingComparator;
        //         }
        //         return Integer.compare(o1.board.manhattan(), o2.board.manhattan());
        //     }
        // }
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);


        // print solution to standard output
        // if (!solver.isSolvable())
        //     StdOut.println("No solution possible");
        // else {
        //     StdOut.println("Minimum number of moves = " + solver.moves());
        //     for (Board board : solver.solution())
        //         StdOut.println(board);
        // }
    }
}
