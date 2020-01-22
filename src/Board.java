import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

/**
 * This class represents the board of the n-puzzle game. The board is a n x n
 * 2-dimensional array of integers.
 * 
 * @author Giorgos Argyrides
 *
 */
public class Board {

	private int n;
	private int[][] array;

	/**
	 * Reads the given file and initializes the board.
	 * @param n
	 * @param filePath
	 */
	public Board(String filePath) {		
		Scanner scan;
		try {
			scan = new Scanner(new FileInputStream(filePath));
			this.n = scan.nextInt();
			this.array = new int[n][n];
			for (int i = 0; i < this.n; i++) {
				for (int j = 0; j < this.n; j++) {
					this.array[i][j] = scan.nextInt();
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Board(int n) {
		this.n = n;
		this.array = new int[n][n];
	}

	public int getN() {
		return this.n;
	}

	public int getTile(int i, int j) {
		return this.array[i][j];
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Board)) {
			return false;
		}
		Board board = (Board) o;
		return n == board.n && Objects.equals(array, board.array);
	}

	/**
	 * Checks if the board is solvable.
	 * 
	 * @return returns true if the board is solvable.
	 */
	public boolean isSolvable() {
		int[] temp = new int[n * n];
		int k = 0, inversions = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				temp[k++] = array[i][j];
			}
		}
		for (int i = 0; i < n * n; i++) {
			for (int j = i + 1; j < n * n; j++) {
				if ((temp[i] != 0) && (temp[j] != 0) && (temp[i] > temp[j]))
					inversions++;
			}
		}
		if (this.n % 2 == 0) {

			int[] pos = this.findCoordinates(0);
			int blankRow = pos[0];
			if (((inversions + blankRow) % 2) == 0)
				return false;
		} else {

			if (inversions % 2 != 0)
				return false;
		}
		return true;
	}

	/**
	 * Prints the board.
	 */
	public void printBoard() {
		for (int i = 0; i < n; i++) {
			System.out.print("+");
			for (int j = 0; j < n; j++) {
				System.out.print("-----+");
			}
			System.out.print("\n|");
			for (int j = 0; j < n; j++) {
				if (array[i][j] < 10)
					System.out.print("  " + array[i][j] + "  |");
				else
					System.out.print("  " + array[i][j] + " |");
			}
			System.out.println();
		}
		System.out.print("+");
		for (int j = 0; j < n; j++) {
			System.out.print("-----+");
		}
		System.out.println();
	}

	/**
	 * Duplicates a board.
	 * 
	 * @return the duplicate of the board.
	 */
	public Board duplicate() {
		Board b = new Board(n);
		b.n = n;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				b.array[i][j] = array[i][j];
			}
		}
		return b;
	}

	/**
	 * Finds the coordinates of the the given number in the board and stores them in
	 * an array pos[]. pos[0] is X and pos[1] is Y.
	 * 
	 * @return returns coordinates of the given number in an array.
	 */
	public int[] findCoordinates(int tile) {
		int[] pos = new int[2];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (array[i][j] == tile) {
					pos[0] = i;
					pos[1] = j;
				}
			}
		}
		return pos;
	}

	/**
	 * Moves the blank (0) block up, down, left or right.
	 * 
	 * @return the board with the moved blank tile.
	 */
	public Board moveBlank(String position) {
		Board b = this.duplicate();
		int[] pos = this.findCoordinates(0); // find blank tile coordinates
		int x = pos[0], y = pos[1];

		switch (position) {
		case "up":
			if (x == 0)
				return null;
			b.array[x][y] = array[x - 1][y];
			b.array[x - 1][y] = 0;
			break;
		case "down":
			if (x == n - 1)
				return null;
			b.array[x][y] = array[x + 1][y];
			b.array[x + 1][y] = 0;
			break;
		case "left":
			if (y == 0)
				return null;
			b.array[x][y] = array[x][y - 1];
			b.array[x][y - 1] = 0;
			break;
		case "right":
			if (y == n - 1)
				return null;
			b.array[x][y] = array[x][y + 1];
			b.array[x][y + 1] = 0;
			break;
		}
		return b;
	}
}