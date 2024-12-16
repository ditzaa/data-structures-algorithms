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
    private MinPQ<SearchNode> priorityQueue;
    private int moves;
    private SearchNode solution;
    private Board initial;
    private boolean isSolvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Board argument is null");
        }
        this.initial = initial;
        this.moves = 0;
        // initialize priority queue
        SearchNodeComparator comparator = new SearchNodeComparator();
        this.priorityQueue = new MinPQ<SearchNode>(comparator);
        SearchNode initialNode = new SearchNode(initial, null, initial.manhattan(), 0);
        priorityQueue.insert(initialNode);

        // go with twin priority tree in parallel to check if it board is solvable
        MinPQ<SearchNode> priorityQueueTwin = new MinPQ<SearchNode>(comparator);
        Board twin = initial.twin();
        SearchNode initialNodeTwin = new SearchNode(twin, null, twin.manhattan(), 0);
        priorityQueueTwin.insert(initialNodeTwin);

        // Repeat this procedure until the search node dequeued corresponds to the goal board.
        SearchNode deletedNode = initialNode;
        SearchNode deletedNodeTwin = initialNodeTwin;
        // nodes for critical optimization
        SearchNode criticalNode = initialNode;
        SearchNode criticalNodeTwin = initialNodeTwin;

        // debug
        // StdOut.println("-------Step" + 0 + "--------");
        // StdOut.println(deletedNode.board.toString());
        // StdOut.println("Manhattan: " + deletedNode.board.manhattan());
        // StdOut.println("Moves: " + deletedNode.moves);
        // StdOut.println("Priority: " + deletedNode.priority);
        // StdOut.println("*********************");
        int i = 0;
        while (!deletedNode.board.isGoal() && !deletedNodeTwin.board.isGoal()) {
            // debug
            StdOut.println("-------Step" + i + "--------");
            // StdOut.println("To be deleted:\n" + deletedNode.board.toString());
            // StdOut.println("Priority: " + deletedNode.priority);
            for (SearchNode node : priorityQueue) {
                StdOut.println(node.board.toString());
                StdOut.println("Priority: " + node.priority);
                StdOut.println("Moves: " + node.moves);
                StdOut.println("Manhattan: " + node.board.manhattan());
                StdOut.println("*********************");
            }
            StdOut.println();

            // delete from the priority queue the search node with the minimum priority
            deletedNode = priorityQueue.delMin();
            deletedNodeTwin = priorityQueueTwin.delMin();

            // all neighboring search nodes of current node
            Iterable<Board> neighbors = deletedNode.board.neighbors();
            Iterable<Board> neighborsTwin = deletedNodeTwin.board.neighbors();

            // debug
            StdOut.println("Critical optimization:");
            StdOut.println(criticalNode.board.toString());
            StdOut.println("*********************");

            // insert onto the priority queue all neighboring search nodes

            for (Board board : neighbors) {
                // public SearchNode(Board board, Board previousBoard, int priority, int moves)
                SearchNode newSearchNode = new SearchNode(board, deletedNode,
                                                          board.manhattan() + deletedNode.moves + 1,
                                                          deletedNode.moves + 1);

                if (!board.equals(criticalNode.board)) {
                    priorityQueue.insert(newSearchNode);
                }

                // critical optimization
                // if (!board.equals(newSearchNode.previousNode.board)) {
                //     // check for previous nodes
                //     if (newSearchNode.previousNode.previousNode != null) {
                //         if (!board.equals(newSearchNode.previousNode.previousNode.board)) {
                //             priorityQueue.insert(newSearchNode);
                //         }
                //     }
                //     else {
                //         priorityQueue.insert(newSearchNode);
                //     }
                // }
            }

            for (Board board : neighborsTwin) {
                // public SearchNode(Board board, Board previousBoard, int priority, int moves)
                SearchNode newSearchNode = new SearchNode(board, deletedNodeTwin,
                                                          board.manhattan() + deletedNodeTwin.moves
                                                                  + 1,
                                                          deletedNodeTwin.moves + 1);

                if (!board.equals(criticalNode.board)) {
                    priorityQueueTwin.insert(newSearchNode);
                }
                // if (!board.equals(newSearchNode.previousNode.board)) {
                //     // check for previous nodes
                //     if (newSearchNode.previousNode.previousNode != null) {
                //         if (!board.equals(newSearchNode.previousNode.previousNode.board)) {
                //             priorityQueueTwin.insert(newSearchNode);
                //         }
                //     }
                //     else {
                //         priorityQueueTwin.insert(newSearchNode);
                //     }
                //
                // }

            }
            criticalNode = deletedNode;
            criticalNodeTwin = deletedNodeTwin;
            i++;
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

        // StdOut.println("Solution\n" + deletedNode.board.toString());
        // StdOut.println("Manhattan: " + deletedNode.board.manhattan());
        // StdOut.println("Moves: " + deletedNode.moves);
        // StdOut.println("Priority: " + deletedNode.priority);
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return this.isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        Stack<Board> solutionSequence = new Stack<>();
        solutionSequence.push(solution.board);
        if (this.isSolvable()) {
            SearchNode node;
            node = solution;
            while (node.previousNode != null) {
                node = node.previousNode;
                solutionSequence.push(node.board);
            }
        }
        else {
            return null;
        }
        return solutionSequence;
    }

    private class SearchNode {
        private int priority;
        private int moves;
        // private int manhattan;
        private SearchNode previousNode;
        private Board board;

        public SearchNode(Board board, SearchNode previousNode, int priority, int moves) {
            this.board = board;
            this.previousNode = previousNode;
            this.priority = priority;
            this.moves = moves;
            // this.manhattan = board.manhattan();
        }
    }

    private class SearchNodeComparator implements Comparator<SearchNode> {
        public SearchNodeComparator() {

        }

        @Override
        public int compare(SearchNode node1, SearchNode node2) {
            // StdOut.println("Prioritites" + node1.priority + " " + node2.priority);
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

        // StdOut.println("-----Main----");
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
