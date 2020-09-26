package com.nmorenor.dp.problems;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implement the "paint fill" function that one might see on many image editing programs.
 * That is, given a screen (represented by two-dimensional array of colors), a point,
 * and a new color, fill in the surrounding area until the color changes from the original color 
 * @author nacho
 *
 */
public class PaintFill {
	
	public static class Point {
		public final int x;
		public final int y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public String toString() {
			return "[" + String.valueOf(x) + ", " + String.valueOf(y) + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Point other = (Point) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}
	}

	public static void main(String[] args) {
		String [][] input = new String[8][8];
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 2; j++) {
				input[i][j] = "black";
			}
		}
		for (int i = 0; i < 2; i++) {
			for (int j = 2; j < 8; j++) {
				input[i][j] = "black";
			}
		}
		for (int i = 6; i < 8; i++) {
			for (int j = 2; j < 8; j++) {
				input[i][j] = "black";
			}
		}
		for (int i = 0; i < 8; i++) {
			for (int j = 6; j < 8; j++) {
				input[i][j] = "black";
			}
		}
		for (int i = 2; i < 6; i++) {
			for (int j = 2; j < 6; j++) {
				input[i][j] = "white";
			}
		}
		PaintFillImpl paint = new PaintFillImpl(8, 8, input);
		paint.printScreen();
		paint.fill(new Point(3, 3), "black");
		System.out.println("=========");
		System.out.println("==AFTER==");
		System.out.println("=========");
		paint.printScreen();
	}
	
	private static class PaintFillImpl {
		
		private class Pixel extends Point {
			
			public String color;
			
			public Pixel(int x, int y, String color) {
				super(x, y);
				this.color = color;
			}
			
			public boolean hasPath(Pixel other) {
				return color.equals(other.color);
			}

			private List<Pixel> adj = new LinkedList<Pixel>();
		}
		
		Map<String, Pixel> pixels = new HashMap<String, Pixel>();
		private final int width;
		private final int height;
		
		
		public PaintFillImpl(int width, int height, String[][] screenState) {
			this.width = width;
			this.height = height;
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					Pixel next = new Pixel(i, j, screenState[i][j]);
					pixels.put(next.toString(), next);
					if (i > 0 && next.hasPath(new Pixel(i - 1, j, screenState[i-1][j]))) {
						next.adj.add(new Pixel(i - 1, j, screenState[i-1][j]));
					}
					if (i < width - 1 && next.hasPath(new Pixel(i + 1, j, screenState[i + 1][j]))) {
						next.adj.add(new Pixel(i + 1, j, screenState[i+1][j]));
					}
					if (j > 0 && next.hasPath(new Pixel(i, j - 1, screenState[i][j-1]))) {
						next.adj.add(new Pixel(i, j - 1, screenState[i][j-1]));
					}
					if (j < height - 1 && next.hasPath(new Pixel(i, j + 1, screenState[i][j+1]))) {
						next.adj.add(new Pixel(i, j + 1, screenState[i][j+1]));
					}
				}
			}
		}
		
		public void fill(Point point, String color) {
			Pixel startingPoint = pixels.get(point.toString());
			if (startingPoint == null) {
				System.out.println("Point off screen");
				return;
			}
			Set<String> visited = new HashSet<String>();
			for (Pixel next : startingPoint.adj) {
				if (visited.contains(next.toString())) {
					continue;
				}
				dfs(next, color, visited);
			}
			startingPoint.color = color;
		}

		private void dfs(Pixel next, String color, Set<String> visited) {
			if (visited.contains(next.toString())) {
				return;
			}
			next = pixels.get(next.toString());
			next.color = color;
			visited.add(next.toString());
			for (Pixel nextAdj : next.adj) {
				dfs(nextAdj, color, visited);
			}
		}
		
		public void printScreen() {
			for (int j = 0; j < height; j++) {
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < width; i++) {
					Pixel next = pixels.get(new Point(i, j).toString());
					builder.append(next.color);
					if (i < width - 1) {
						builder.append(",");
					}
				}
				System.out.println(builder);
			}
			
		}
	}
}
