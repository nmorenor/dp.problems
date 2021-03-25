package com.nmorenor.dp.problems;

/**
 * Write a recursive function to multiply two positive integers without using * operator. 
 * You can use addition, substraction, and bit shifting, but you should 
 * @author nacho
 *
 */
public class RecursiveMultiply {

	public static void main(String[] args) {
		RecursiveMultiplyImpl recursiveMultiply = new RecursiveMultiplyImpl(2, 7);
		System.out.println(recursiveMultiply.solution);
	}
	
	private static class RecursiveMultiplyImpl {
		
		private int solution;
		
		public RecursiveMultiplyImpl(int n, int m) {
			int[] sol = new int[] {0};
			solve(n, m,  0, sol);
			solution = sol[0];
		}

		private void solve(int n, int m, int count, int[] sol) {
			if (m <= 0) {
				return;
			}
			if (m % 2 == 1) {
				sol[0] += n << count;
			}
			
			solve(n, m / 2, count + 1, sol);
		}
	}
}
