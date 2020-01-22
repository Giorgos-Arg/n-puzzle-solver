import static java.util.Arrays.binarySearch;

/**
 * The n-puzzle can be solved using four different heuristics according to the
 * user's choice.
 * 
 * @author Giorgos Argyrides
 *
 */
public class Heuristics {

	public static enum Axis {
		ROW, COL
	};

	// Calculates the manhattan distance of all the tiles.
	public static int Manhattan(State state) {
		int sum = 0, tile = 1;
		for (int i = 0; i < state.getBoard().getN(); i++) {
			for (int j = 0; j < state.getBoard().getN(); j++) {
				if ((state.getBoard().getTile(i, j) != 0) && (state.getBoard().getTile(i, j) != tile)) {// Misplaced
																										// tile
					int[] pos = state.getBoard().findCoordinates(tile);
					sum += Math.abs(pos[0] - i) + Math.abs(pos[1] - j);
				}
				tile++;
			}
		}
		return sum;
	}

	// Counts the number of tiles that are not in their correct position.
	public static int misplacedTiles(State state) {
		int tile = 1, misplaced = 0, n = state.getBoard().getN();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if ((state.getBoard().getTile(i, j) != 0) && (state.getBoard().getTile(i, j) != tile))
					misplaced++;
				tile++;
			}
		}
		return misplaced;
	}

	// Counts the number of tiles that are out of their correct row or column.
	public static int tilesOutOfRowOrColumn(State state) {
		int outOfRow = 0, outOfColumn = 0, tile = 1;
		for (int i = 0; i < state.getBoard().getN(); i++) {
			for (int j = 0; j < state.getBoard().getN(); j++) {
				if ((state.getBoard().getTile(i, j) != 0) && (state.getBoard().getTile(i, j) != tile)) {// Misplaced
																										// tile
					int[] pos = state.getBoard().findCoordinates(tile);
					if (pos[0] - i != 0)
						outOfRow++;
					if (pos[1] - j != 0)
						outOfColumn++;
				}
				tile++;
			}
		}
		return outOfRow + outOfColumn;
	}

	/**
	 * Auxiliary method for the linear conflict heuristic. Returns the tiles of the
	 * puzzle for a specified row or column.
	 */
	private static int[] tuple(Axis axis, int index, State state) {
		int n = state.getBoard().getN();
		int[] result = new int[state.getBoard().getN()];
		if (axis.equals(Axis.ROW)) {
			for (int i = 0; i < n; i++) {
				result[i] = state.getBoard().getTile(index, i);
			}
		} else {
			for (int i = 0; i < n; i++)
				for (int j = index; j < n * n; j += n)
					result[i] = state.getBoard().getTile(i, index);
		}
		return result;
	}

	/**
	 * Auxiliary method for the linear conflict heuristic. Returns the squares of
	 * the puzzle of this size as if it were in its solved state for a specified row
	 * or column.
	 */
	private static int[] idealTuple(Axis axis, int index, State state) {
		int boardN = state.getBoard().getN();
		int[] result = new int[boardN];
		int[][] ideal = new int[boardN][boardN];
		int num = 1;
		for (int i = 0; i < boardN; i++) {
			for (int j = 0; j < boardN; j++) {
				ideal[i][j] = num++;
			}
		}
		ideal[boardN - 1][boardN - 1] = 0;
		if (axis.equals(Axis.ROW)) {
			for (int i = 0; i < boardN; i++) {
				result[i] = ideal[index][i];
			}
		} else {
			for (int i = 0; i < boardN; i++)
				for (int j = index; j < boardN * boardN; j += boardN)
					result[i] = ideal[i][index];
		}
		return result;
	}

	/**
	 * Auxiliary method for the linear conflict heuristic. Counts inversions (linear
	 * conflicts) for a row or column.
	 */
	public static int inversions(Axis axis, int index, State state) {
		int[] current = tuple(axis, index, state);
		int[] ideal = idealTuple(axis, index, state);
		int inversions = 0;
		for (int i = 1; i < state.getBoard().getN(); i++) {
			if ((current[i] != 0) && (0 <= binarySearch(ideal, current[i]))) {
				for (int j = 0; j < i; j++) {
					if ((current[j] != 0) && (0 <= binarySearch(ideal, current[j]))) {
						if ((current[i] < current[j]) != (i < j)) {
							inversions++;
						}
					}
				}
			}
		}
		return inversions;
	}

	/**
	 * Returns the number of linear conflicts in the state. For each pair of tiles,
	 * if both numbers are supposed to be in this row or column , and neither is
	 * blank and they are inverted there is a linear conflict.
	 */
	public static int linearConflict(State state) {
		int linearConflict = 0;
		for (int i = 0; i < state.getBoard().getN(); i++) {
			linearConflict += inversions(Axis.ROW, i, state);
			linearConflict += inversions(Axis.COL, i, state);
		}
		return linearConflict;
	}
}
