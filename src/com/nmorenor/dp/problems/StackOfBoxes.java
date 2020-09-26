package com.nmorenor.dp.problems;

import java.util.Arrays;

/**
 * You have a stack of n boxes, with widths w heights h and depths d. The boxes cannot be 
 * rotated and can only be stacked on top of on another if each box in the stack is striclty 
 * larger than the box above it in width, height, and depth. Implement a method to compute the
 * height of the tallest possible stack. The height of a stack is the sum of the heights of each box.
 * @author nacho
 *
 */
public class StackOfBoxes {
	
	private static class Box implements Comparable<Box> {
		final int w;
		final int h;
		final int d;
		
		public Box(int w, int h, int d) {
			this.w = w;
			this.h = h;
			this.d = d;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * getVol();
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
			Box other = (Box) obj;
			if (getVol() != other.getVol())
				return false;
			return true;
		}
		
		private int getVol() {
			return w * h * d;
		}

		@Override
		public int compareTo(Box o) {
			if (this.equals(o)) {
				return 0;
			}
			return Integer.compare(o.getVol(), getVol());
		}
		
		
	}
	
	public static void main(String[] args) {
		int [][] input = new int[][] {
			new int[] {5, 8, 4},
			new int[] {3, 2, 4},
			new int[] {3, 2, 16},
		};
		Box[] boxes = Arrays.stream(input).map(n -> new Box(n[0], n[1], n[2])).toArray(Box[]::new);
		Arrays.sort(boxes);
		int height = 0;
		for (int i = 0; i > boxes.length; i++) {
			if (i < boxes.length - 1) {
				height += boxes[i].h;
			} else {
				if (boxes[i].equals(boxes[i+1]) && boxes[i+1].h > boxes[i].h) {
					continue;
				}
				height += boxes[i].h;
			}
		}
		System.out.print(height);
	}
}

