import java.util.Iterator;

/**
 * A search nodes contains a state of the n-puzzle game. That is the board, the
 * moves made so far, the priority function value, the previous state and the
 * move made to reach that state.
 * 
 * @author Giorgos Argyrides
 *
 */
public class State implements Comparable<State> {
	Board board;
	int moveCount; // moves made so far
	int priorityFunctionValue; // priority function value
	int heuristicValue; // heuristic
	String move;
	State previous;

	public State(Board board, int moveCount, State previous, String heuristic, String move) {
		this.board = board;
		this.moveCount = moveCount;
		this.previous = previous;
		calculatePriorityFunction(heuristic);
		this.move = move;
	}

	/**
	 * @return true if the state is a goal state
	 */
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

	/**
	 * @return true if the two nodes are in the same state.
	 */
	public boolean equals(State sn) {
		for (int i = 0; i < board.getN(); i++) {
			for (int j = 0; j < board.getN(); j++) {
				if (board.getTile(i, j) != sn.board.getTile(i, j))
					return false;
			}
		}
		return true;
	}

	/**
	 * Calculates the priority function value by using one of four available
	 * heuristics.
	 * 
	 * @param heuristic the heuristic used to calculate the priority function value.
	 */
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

	/**
	 * Compares two nodes by their priority function value.
	 */
	public int compareTo(State sn) {
		if (priorityFunctionValue < sn.priorityFunctionValue)
			return -1;
		else if (priorityFunctionValue > sn.priorityFunctionValue)
			return 1;
		else
			return 0;
	}

	/**
	 * Expands a state and finds it's successors
	 */
	public void successors(FringeClosed fc, String heuristic) {
		Board b1 = this.board.moveBlank("up");
		if (b1 != null) {
			State state = new State(b1, ++this.moveCount, this, heuristic, "up");
			if (avoidRepetition(state, fc)) {
				fc.fringe.add(state);
				fc.states++;
			}
		}
		Board b2 = this.board.moveBlank("down");
		if (b2 != null) {
			State state = new State(b2, ++this.moveCount, this, heuristic, "down");
			if (avoidRepetition(state, fc)) {
				fc.fringe.add(state);
				fc.states++;
			}
		}
		Board b3 = this.board.moveBlank("left");
		if (b3 != null) {
			State state = new State(b3, ++this.moveCount, this, heuristic, "left");
			if (avoidRepetition(state, fc)) {
				fc.fringe.add(state);
				fc.states++;
			}
		}
		Board b4 = this.board.moveBlank("right");
		if (b4 != null) {
			State state = new State(b4, ++this.moveCount, this, heuristic, "right");
			if (avoidRepetition(state, fc)) {
				fc.fringe.add(state);
				fc.states++;
			}
		}
	}

	/**
	 * Checks if the state has already been expanded and discards it. If the state
	 * is in the fringe, then it keeps the state with the minimum priority function
	 * value.
	 * 
	 * @return returns true if there is no repetition
	 */
	public boolean avoidRepetition(State sn, FringeClosed fc) {
		State temp;
		// if the state is already in the closed list return false
		for (int i = 0; i < fc.closed.size(); i++) {
			temp = fc.closed.get(i);
			if (temp.equals(sn))
				return false;
		}
		Iterator<State> it = fc.fringe.iterator();
		while (it.hasNext()) {
			temp = it.next();
			if (temp.equals(sn)) {
				// keep the state with the minimum priorityFunctionValue
				if (sn.priorityFunctionValue < temp.priorityFunctionValue) {
					temp.priorityFunctionValue = sn.priorityFunctionValue;
					temp.moveCount = sn.moveCount;
					temp.heuristicValue = sn.heuristicValue;
					temp.move = sn.move;
					temp.previous = sn.previous;
				}
				return false;
			}
		}
		return true;
	}
}
