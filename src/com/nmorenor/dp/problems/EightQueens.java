package com.nmorenor.dp.problems;

/**
 * Write an algorithm to print all ways of arranging eight queens on an 8x8 chess board
 * so that none of them share the same row column, or diagonal. In this case "diagonal" means all
 * diagonals, not just the two that bisect the board
 * @author nacho
 *
 */
public class EightQueens {

	public static void main(String[] args) {
		for (int i = 7; i > 0; i--) {
			int[][] sol = new int[8][8];
			int[] cols  = new int[8];
			solve(7, i, sol, cols);
			
			print(sol, cols);
			if (i > 1) {
				System.out.println();
				System.out.println("================");
				System.out.println();
			}
		}
	}

	private static void print(int[][] sol, int[] cols) {
		for (int i = 0; i < 8; i++) {
			StringBuffer row = new StringBuffer("[");
			for (int j = 0; j < 8; j++) {
				row.append(String.valueOf(sol[i][j]));
				if (j < 7) {
					row.append(", ");
				}
			}
			row.append("]");
			System.out.println(row);
		}
	}
	
	public static void solve(int i, int j, int[][]queens, int[] cols) {
		if (i < 0 | j < 0) {
			return;
		}
		queens[i][j] = 1;
		cols[j] = j;
		if ((i - 1) >= 0 && (j - 2) >= 0 && cols[j - 2] == 0 && isValid(i - 1, j - 2, queens)) {
			solve(i - 1, j - 2, queens, cols);
		} else if (i == 1) { 
			int target = 0;
			for (int c = cols.length - 1; c >= 0; c--) {
				if (cols[c] == 0) {
					target = c;
					break;
				}
			}
			solve(i - 1, target, queens, cols);
		} else {
			int target = 0;
			for (int c = cols.length - 1; c >= 0; c--) {
				if (cols[c] == 0 && isValid(i - 1, c, queens)) {
					target = c;
					break;
				}
			}
			solve(i - 1, target, queens, cols);
		}
	}

	private static boolean isValid(int row, int column, int[][] queens) {
		if (row < 0) {
			return false;
		}
		int right = row;
		for (int i = column; i < 7; i++) {
			if (right < 7 && queens[right + 1][i + 1] != 0) {
				return false;
			}
			right += 1;
		}
		if (row > 0) {
			int left = row;
			for (int i = column; i > 0; i--) {
				if (left < 7 && queens[left + 1][i - 1] != 0) {
					return false;
				}
				left += 1;
			}
		}
		return true;
	}
}
