package com.nmorenor.dp.problems;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Imagine a robot sitting on the upper left corner of grid with r rows and c columns. 
 * The robot can only move in two directions right and down, but certain cells are "off limits"
 * such that the robot cannot step on them. Design an alorithm to find a path for the robot from 
 * the top left to the bottom right.
 * @author nacho
 *
 */
public class RobotGrid {

	public static void main(String[] args) {
		
		int [][] blocking = new int [][] {
			new int [] {2, 3},
			new int [] {4, 3},
			new int [] {3, 3},
		};
		Blocked[] blocked = Arrays.stream(blocking).map(next -> new Blocked(next[0], next[1])).toArray(n -> new Blocked[n]);
		RoboGridImpl roboGrid = new RoboGridImpl(10, 5, blocked);
		LinkedList<Blocked> solution = roboGrid.getPath();
		if (solution != null) {
			solution.stream().forEach(next -> System.out.println(next));
		}
	}
	
	private static class Blocked {
		public final int x;
		public final int y;
		
		public Blocked(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public String toString() {
			return "[ " + x + ", " + y + "]";
		}
	}
	
	private static class RoboGridImpl {
		
		private LinkedList<Blocked> solution;
		private int rows;
		private int cols;
		private boolean [][] index;
		
		public RoboGridImpl(int rows, int cols, Blocked[] c) {
			this.rows = rows;
			this.cols = cols;
			index = new boolean[rows][cols];
			solve(0, 0, c);
		}

		private void solve(int x, int y, Blocked[] c) {
			if (x >= rows || y >= cols || isBlocked(x, y, c) || hasPath()) {
				return;
			}
			if (index[x][y]) {
				return;
			}
			
			index[x][y] = true;
			for (int i = x; i < rows; i++) {
				this.solve(i + 1, y, c);
			}
			for (int i = y; i < cols; i++) {
				this.solve(x, i + 1, c);
			}
		}
		
		private boolean isBlocked(int x, int y, Blocked[] c) {
			return Arrays.stream(c).filter(next -> x == next.x && y == next.y).findAny().orElse(null) != null;
		}
		
		public boolean hasPath() {
			return index[rows - 1][cols - 1];
		}
		
		public LinkedList<Blocked> getPath() {
			if (solution != null) {
				return solution;
			}
			if (!hasPath()) {
				return null;
			}
			solution = new LinkedList<Blocked>();
			int x = rows - 1;
			int y = cols - 1;
			while (y >= 0 && x >=0) {
				if (x == 0 && y == 0) {
					break;
				}
				if (index[x][y]) {
					solution.push(new Blocked(x, y));
				}
				if (x > 0 && index[x -1][y]) {
					solution.push(new Blocked(x - 1, y));
					x -= 1;
					continue;
				}
				if (y > 0 && index[x][y - 1]) {
					solution.push(new Blocked(x, y - 1));
					y -= 1;
					continue;
				}
			}
			return solution;
		}
	}
}
