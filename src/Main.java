
import java.util.Scanner;
import java.util.Stack;

public class Main {

	public static void main(String[] args) {

		String heuristic = null, filePath;
		double startTime = 0, endTime = 0;
		int count = 1, choice;
		FringeClosed f = new FringeClosed();
		Stack<State> stack = new Stack<State>();
		Scanner scan = new Scanner(System.in);

		System.out.println("*** Hello!! Welcome to 8-puzzle / 15-puzzle program! ***\n\n");
		System.out.println("Give the name of the input file:");
		filePath = scan.next();
		System.out.println("\nChoose one of the 4 following heuristics:\n"
				+ "1.type 1 for Manhattan distance heuristic\n" + "2.type 2 for number of misplaced tiles heuristic\n"
				+ "3.type 3 for number of tiles out of row and column heuristic\n"
				+ "4.type 4 for linearConflict heuristic\n");

		choice = scan.nextInt();
		scan.close();

		switch (choice) {

		case 1:
			heuristic = "Manhattan";
			break;
		case 2:
			heuristic = "misplacedTiles";
			break;
		case 3:
			heuristic = "tilesOutOfRowAndColumn";
			break;
		case 4:
			heuristic = "linearConflict";
			break;
		default:
			System.out.println("\nError! Invalid heuristic choice! You have to type an integer 1-4");
			System.exit(0);
		}

		Board board = new Board(filePath);
		State state = new State(board, 0, null, heuristic, "initial");
		f.fringe.add(state);

		System.out.println();
		if (!board.isSolvable()) {
			System.out.println("\nThe puzzle is unsolvable!!!!");
			System.exit(0);
		}

		startTime = System.currentTimeMillis();
		// search loop
		while (!state.isGoal()) {
			state = f.fringe.remove(); // remove the state with the lowest cost
			f.closed.add(state);
			state.successors(f, heuristic);// add the successors of the state in the fringe
		}
		endTime = (System.currentTimeMillis() - startTime) / 1000.0;
		State s = state;

		while (s != null) {
			stack.add(s);
			s = s.previous;
		}

		while (!stack.isEmpty()) {
			s = stack.pop();
			if (!s.move.equals("initial")) {
				System.out.println("\n" + count + ". move blank: " + s.move);
				count++;
			} else
				System.out.println("\ninitial board:");
			s.board.printBoard();
		}

		System.out.println(
				"\nPuzzle solved successfully!!!\n\nNumber of problem states in the search space: " + f.states);
		System.out.printf("\nTotal solution time: %.3f seconds", endTime);
	}
}
