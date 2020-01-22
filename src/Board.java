import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class represents the board of the n-puzzle game. The board is a n x n
 * array of integers.
 * 
 * @author Giorgos Argyrides
 */
public class Board {
	private int n;
	private int[][] array;

	// Reads the given file and initializes the board.
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
			System.out.println("\nFile \"" + filePath + "\" not found!\nProgram will terminate...");
			System.exit(0);
		}
	}

	// creates an n x n board
	public Board(int n) {
		this.n = n;
		this.array = new int[n][n];
	}

	public int getN() {
		return this.n;
	}

	// returns the tile at the coordinates i, j
	public int getTile(int i, int j) {
		return this.array[i][j];
	}

	// returns true if the board is solvable.
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
		} else if (inversions % 2 != 0) {
			return false;
		}
		return true;
	}

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
	 * Finds the coordinates of the the given tile in the board and stores them in
	 * an array pos[]. pos[0] is x and pos[1] is y
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

	// Moves the blank tile (0) up, down, left or right and returns the new board
	public Board moveBlank(Move move) {
		Board b = this.duplicate();
		int[] pos = this.findCoordinates(0); // find blank tile coordinates
		int x = pos[0], y = pos[1];
		switch (move) {
		case UP:
			if (x == 0)
				return null;
			b.array[x][y] = array[x - 1][y];
			b.array[x - 1][y] = 0;
			break;
		case DOWN:
			if (x == n - 1)
				return null;
			b.array[x][y] = array[x + 1][y];
			b.array[x + 1][y] = 0;
			break;
		case LEFT:
			if (y == 0)
				return null;
			b.array[x][y] = array[x][y - 1];
			b.array[x][y - 1] = 0;
			break;
		case RIGHT:
			if (y == n - 1)
				return null;
			b.array[x][y] = array[x][y + 1];
			b.array[x][y + 1] = 0;
			break;
		default:
		}
		return b;
	}
}