import java.util.Stack;

/**
 * Main class of the n-puzzle solver. Parses the command line arguments and if
 * there's a solution to the puzzle then it finds it using the A* algorithm and
 * the heuristic provided by the user.
 * 
 * @author Giorgos Argyrides
 *
 */
public class NPuzzleSolver {

	public static void main(String[] args) {
		String heuristic = null, filePath = "", heuristicChoice = "";
		if (args.length == 2) {
			heuristicChoice = args[0];
			filePath = args[1];
		} else {
			System.out.println("usage: NPuzzleSolver <heuristic> <filePath>\n" + "heuristic choices:\n"
					+ "-m\tManhattan distance\n" + "-mt\tNumber of misplaced tiles\n"
					+ "-rc\tNumber of tiles out of row or column\n" + "-lc\tLinear conflict\n");
			System.exit(0);
		}
		switch (heuristicChoice) {
		case "-m":
			heuristic = "Manhattan";
			break;
		case "-mt":
			heuristic = "misplacedTiles";
			break;
		case "-rc":
			heuristic = "tilesOutOfRowOrColumn";
			break;
		case "-lc":
			heuristic = "linearConflict";
			break;
		default:
			System.out.println("\nError! Invalid heuristic choice!");
			System.exit(0);
		}
		Board board = new Board(filePath);
		if (!board.isSolvable()) {
			System.out.println("\nThe puzzle is unsolvable!");
			System.exit(0);
		}
		State state = new State(board, 0, null, heuristic, Move.INITIAL);
		Fringe fringe = new Fringe();
		Closed closed = new Closed();
		fringe.add(state);
		double startTime = System.currentTimeMillis();
		// Iterate until the goal state is reached
		while (!state.isGoal()) {
			state = fringe.remove(); // remove the state with the lowest cost
			closed.add(state);
			state.expandState(fringe, closed, heuristic);// Expand the state and add its successors in the fringe
		}
		double endTime = (System.currentTimeMillis() - startTime) / 1000.0;
		Stack<State> stack = state.findSolutionSequence();
		int moveCount = 1;
		// print the solution sequence
		while (!stack.isEmpty()) {
			state = stack.pop();
			if (!state.getMove().equals(Move.INITIAL)) {
				System.out.println("\n" + moveCount + ". move blank: " + state.getMove());
				moveCount++;
			} else
				System.out.println("\nInitial board:");
			state.getBoard().printBoard();
		}
		System.out.println(
				"\nPuzzle solved successfully!\n\nNumber of states in the search space: " + fringe.getStatesCount());
		System.out.printf("\nTotal solution time: %.3f seconds\n", endTime);
	}
}