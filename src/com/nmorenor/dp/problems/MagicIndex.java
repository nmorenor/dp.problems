package com.nmorenor.dp.problems;

/**
 * A magic index in an array A[0...n-1] is defined to be an index such that A[i] = i/
 * Given a sorted array of distict integers, write a method to find a magic index, if one exists,
 * in array A
 * @author nacho
 *
 */
public class MagicIndex {
	
	public static void main(String[] args) {
		int [] input = new int[] { -1, -2, -3, 3, 5, 6, 7, 8};
		MagicIndexImpl mgc = new MagicIndexImpl(input);
		if (mgc.getMagicIndex() >= 0) {
			System.out.println("Magic Index is: " + mgc.getMagicIndex());
		} else {
			System.out.println("There is no magic number");
		}
	}
	
	private static class MagicIndexImpl {
		
		private int magicIndex = -1;
		
		public MagicIndexImpl(int[] input) {
			solve(0, input.length - 1, input);
		}
		
		public void solve(int i, int j, int[] input) {
			if (input.length == 0) {
				return;
			}
			if (magicIndex != -1 || i < 0 || j >= input.length) {
				return;
			}
			if (i >= j) {
				return;
			}
			if (input[i] == i) {
				magicIndex = i;
				return;
			}
			if (input[i] > i && input[(i + j) /2] >= i) {
				solve(i, (i + j) /2, input);
			}
			if (input[(i + j) /2] > i && input[j] >= i) {
				solve((i + j) /2, j, input);
			}
		}
		
		public int getMagicIndex() {
			return magicIndex;
		}
		
	}

}
