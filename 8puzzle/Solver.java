/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private int moves;
    private SearchNode solution;
    private boolean isSolvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Solver argument is null");
        }
        this.moves = 0;

        // initialize priority queue
        SearchNodeComparator comparator = new SearchNodeComparator();
        MinPQ<SearchNode> priorityQueue = new MinPQ<SearchNode>(comparator);

        // insert intial node
        SearchNode initialNode = new SearchNode(initial, null, initial.manhattan(), 0);
        priorityQueue.insert(initialNode);

        // twin priority tree in parallel to check if the board is solvable
        MinPQ<SearchNode> priorityQueueTwin = new MinPQ<SearchNode>(comparator);
        Board twin = initial.twin();
        SearchNode initialNodeTwin = new SearchNode(twin, null, twin.manhattan(), 0);
        priorityQueueTwin.insert(initialNodeTwin);

        SearchNode deletedNode = initialNode;
        SearchNode deletedNodeTwin = initialNodeTwin;

        // Repeat this procedure until the search node dequeued corresponds to the goal board.
        //  or have proof that board is unsolvable
        while (!deletedNode.board.isGoal() && !deletedNodeTwin.board.isGoal()) {
            // delete from the priority queues the search nodes with the minimum priority
            deletedNode = priorityQueue.delMin();
            deletedNodeTwin = priorityQueueTwin.delMin();

            // all neighboring search nodes of current nodes
            Iterable<Board> neighbors = deletedNode.board.neighbors();
            Iterable<Board> neighborsTwin = deletedNodeTwin.board.neighbors();

            // insert onto the priority queues all neighboring search nodes
            for (Board board : neighbors) {
                if (deletedNode.previousNode == null ||
                        !board.equals(deletedNode.previousNode.board)) {
                    SearchNode newSearchNode = new SearchNode(board, deletedNode,
                                                              board.manhattan() + deletedNode.moves
                                                                      + 1,
                                                              deletedNode.moves + 1);
                    priorityQueue.insert(newSearchNode);
                }
            }

            for (Board board : neighborsTwin) {
                if (deletedNodeTwin.previousNode == null ||
                        !board.equals(deletedNodeTwin.previousNode.board)) {
                    SearchNode newSearchNode = new SearchNode(board, deletedNodeTwin,
                                                              board.manhattan()
                                                                      + deletedNodeTwin.moves + 1,
                                                              deletedNodeTwin.moves + 1);
                    priorityQueueTwin.insert(newSearchNode);
                }

            }
        }

        if (deletedNode.board.isGoal()) {
            this.solution = deletedNode;
            this.isSolvable = true;
            moves = deletedNode.moves;
        }
        else if (deletedNodeTwin.board.isGoal()) {
            this.isSolvable = false;
            moves = -1;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return this.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!this.isSolvable()) {
            return null;
        }
        Stack<Board> solutionSequence = new Stack<>();
        solutionSequence.push(solution.board);
        SearchNode node;
        node = solution;
        while (node.previousNode != null) {
            node = node.previousNode;
            solutionSequence.push(node.board);
        }

        return solutionSequence;
    }

    private class SearchNode {
        private int priority;
        private int moves;
        private SearchNode previousNode;
        private Board board;

        public SearchNode(Board board, SearchNode previousNode, int priority, int moves) {
            this.board = board;
            this.previousNode = previousNode;
            this.priority = priority;
            this.moves = moves;
        }
    }

    private class SearchNodeComparator implements Comparator<SearchNode> {
        public SearchNodeComparator() {

        }

        @Override
        public int compare(SearchNode node1, SearchNode node2) {
            return Integer.compare(node1.priority, node2.priority);
        }
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
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            Iterable<Board> solutionSteps = solver.solution();
            for (Board board : solutionSteps)
                StdOut.println(board);
        }
    }
}
