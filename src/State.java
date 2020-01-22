import java.util.Iterator;
import java.util.Stack;

/**
 * This class represents the state of the n-puzzle game. That is the board, the
 * moves made so far, the priority function value, the previous state and the
 * last move made to reach that state.
 * 
 * @author Giorgos Argyrides
 *
 */
public class State implements Comparable<State> {

	private Board board;
	private int moveCount;
	private int priorityFunctionValue;
	private int heuristicValue;
	private Move move;
	private State previous;

	public State(Board board, int moveCount, State previous, String heuristic, Move move) {
		this.board = board;
		this.moveCount = moveCount;
		this.previous = previous;
		this.move = move;
		calculatePriorityFunction(heuristic);
	}

	public Board getBoard() {
		return this.board;
	}

	public Move getMove() {
		return this.move;
	}

	// returns true if the state is a goal state
	public boolean isGoal() {
		int n = board.getN(), num = 1;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if ((i == n - 1) && (j == n - 1)) // 0
					return true;
				if (board.getTile(i, j) != num)
					return false;
				num++;
			}
		}
		return true;
	}

	// returns true if the two states have the same board;
	public boolean hasSameBoard(State sn) {
		for (int i = 0; i < board.getN(); i++) {
			for (int j = 0; j < board.getN(); j++) {
				if (board.getTile(i, j) != sn.board.getTile(i, j))
					return false;
			}
		}
		return true;
	}

	// Calculates the priority function value by using one of four available
	public void calculatePriorityFunction(String heuristic) {
		if (heuristic.equals("Manhattan")) {
			heuristicValue = Heuristics.Manhattan(this);
		} else if (heuristic.equals("misplacedTiles")) {
			heuristicValue = Heuristics.misplacedTiles(this);
		} else if (heuristic.equals("tilesOutOfRowAndColumn")) {
			heuristicValue = Heuristics.tilesOutOfRowOrColumn(this);
		} else if (heuristic.equals("linearConflict")) {
			heuristicValue = Heuristics.linearConflict(this);
		}
		priorityFunctionValue = moveCount + heuristicValue;
	}

	// Expands a state and adds its successors in the fringe.
	public void expandState(Fringe fringe, Closed closed, String heuristic) {
		Board b = this.board.moveBlank(Move.UP);
		if (b != null) {
			State state = new State(b, ++this.moveCount, this, heuristic, Move.UP);
			if (avoidRepetition(state, fringe, closed)) {
				fringe.add(state);
				fringe.increaseStatesCount();
			}
		}
		b = this.board.moveBlank(Move.DOWN);
		if (b != null) {
			State state = new State(b, ++this.moveCount, this, heuristic, Move.DOWN);
			if (avoidRepetition(state, fringe, closed)) {
				fringe.add(state);
				fringe.increaseStatesCount();
			}
		}
		b = this.board.moveBlank(Move.LEFT);
		if (b != null) {
			State state = new State(b, ++this.moveCount, this, heuristic, Move.LEFT);
			if (avoidRepetition(state, fringe, closed)) {
				fringe.add(state);
				fringe.increaseStatesCount();
			}
		}
		b = this.board.moveBlank(Move.RIGHT);
		if (b != null) {
			State state = new State(b, ++this.moveCount, this, heuristic, Move.RIGHT);
			if (avoidRepetition(state, fringe, closed)) {
				fringe.add(state);
				fringe.increaseStatesCount();
			}
		}
	}

	/**
	 * Checks if the state has already been expanded and if yes, it discards it. If
	 * the state is already in the fringe, then it keeps the state with the minimum
	 * priority function value. Returns true if there is no repetition.
	 */
	public boolean avoidRepetition(State s2, Fringe fringe, Closed closed) {
		State s1;
		// if the state is already in the closed list return false
		for (int i = 0; i < closed.size(); i++) {
			s1 = closed.get(i);
			if (s1.hasSameBoard(s2))
				return false;
		}
		Iterator<State> it = fringe.iterator();
		while (it.hasNext()) {
			s1 = it.next();
			if (s1.hasSameBoard(s2)) {
				// keep the state with the minimum priorityFunctionValue
				if (s2.priorityFunctionValue < s1.priorityFunctionValue) {
					s1.priorityFunctionValue = s2.priorityFunctionValue;
					s1.moveCount = s2.moveCount;
					s1.heuristicValue = s2.heuristicValue;
					s1.move = s2.move;
					s1.previous = s2.previous;
				}
				return false;
			}
		}
		return true;
	}

	/**
	 * Takes the final state of a solution and goes backwards adding every state in
	 * a stack in order to find the solution sequence.
	 */
	public Stack<State> findSolutionSequence() {
		Stack<State> stack = new Stack<State>();
		State s = this;
		while (s != null) {
			stack.add(s);
			s = s.previous;
		}
		return stack;
	}

	// Compares two nodes by their priority function value.
	public int compareTo(State sn) {
		if (priorityFunctionValue < sn.priorityFunctionValue)
			return -1;
		else if (priorityFunctionValue > sn.priorityFunctionValue)
			return 1;
		else
			return 0;
	}
}
