import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class nQueenHillClimb {

	private static class Board {
		Random random = new Random();
		int[] rows;

		/*
		 * Creates a new n x n board and randomly fills it with one queen in each
		 * column.
		 */
		Board(int n) {
			rows = new int[n];
			scramble();
		}

		/* Randomly fills the board with one queen in each column. */
		void scramble() {
			for (int i = 0, n = rows.length; i < n; i++) {
				rows[i] = i;
			}
			for (int i = 0, n = rows.length; i < n; i++) {
				int j = random.nextInt(n);
				int rowToSwap = rows[i];
				rows[i] = rows[j];
				rows[j] = rowToSwap;
			}
		}

		/*
		 * Returns the number of queens that conflict with (row,col), excluding the
		 * queen in column col.
		 */
		int findConflicts(int row, int col) {
			int count = 0;
			for (int c = 0; c < rows.length; c++) {
				if (c == col)
					continue;
				int r = rows[c];
				if (r == row || Math.abs(r - row) == Math.abs(c - col))
					count++;
			}
			return count;
		}

		/* Fills board with legal arrangement of queens. */
		void solve() {

			int moves = 0;

			ArrayList<Integer> candidates = new ArrayList<Integer>();

			while (true) {

				// Find queen with maximum conflicts
				int maxConflicts = 0;
				candidates.clear();
				for (int c = 0; c < rows.length; c++) {
					int conflicts = findConflicts(rows[c], c);
					if (conflicts == maxConflicts) {
						candidates.add(c);
					} else if (conflicts > maxConflicts) {
						maxConflicts = conflicts;
						candidates.clear();
						candidates.add(c);
					}
				}

				if (maxConflicts == 0) {
					// Checked EVERY queen and found no conflicts
					return;
				}

				// Pick a random queen from those that had the most conflicts
				int worstQueenColumn = candidates.get(random.nextInt(candidates.size()));

				// Move her to the place with the least conflicts.
				int minConflicts = rows.length;
				candidates.clear();
				for (int r = 0; r < rows.length; r++) {
					int conflicts = findConflicts(r, worstQueenColumn);
					if (conflicts == minConflicts) {
						candidates.add(r);
					} else if (conflicts < minConflicts) {
						minConflicts = conflicts;
						candidates.clear();
						candidates.add(r);
					}
				}

				if (!candidates.isEmpty()) {
					rows[worstQueenColumn] = candidates.get(random.nextInt(candidates.size()));
				}

				moves++;
				if (moves == rows.length * 2) {
					// Start over if trying too long
					scramble();
					moves = 0;
				}
			}
		}

		/* Print the board */
		void print() {
			System.out.println("Solution : ");
			char axis1 = 'A';
			char axis2 = 'a';
			for (int i = 0; i < rows.length; i++) {
				System.out.print("  ");
				System.out.print(axis2);
				axis2++;
			}
			System.out.println();
			for (int r = 0; r < rows.length; r++) {
				System.out.print(axis1);
				for (int c = 0; c < rows.length; c++) {
					System.out.print(rows[c] == r ? " Q " : " - ");
				}
				System.out.println("");
				axis1++;
			}

		}
	}

	/* Take user Input for Number of Queens */
	@SuppressWarnings("resource")
	public static int userInput() {
		Scanner input = new Scanner(System.in);
		System.out.println("How many Queens do you wish to place? (Pick a Number between 4 and 13)");
		int num = input.nextInt();
//		input.close();
		return num;
	}

	/* Driver Method. */
	public static void main(String[] args) {
		try {
			while (true) {
				int size = userInput();
				if (size >= 4 && size <= 13) {
					Board board = new Board(size);
					board.solve();
					board.print();
					break;
				} else {
					System.out.println("Please enter Number of Queens between 4 and 13");
				}
			}
		} catch (Exception e) {
			System.out.println("Oops! Something went wrong, Please try again.");
//			e.printStackTrace();
		}
	}

}
