package com.nmorenor.dp.problems;

/**
 * 
 * A popular messeuse receives a sequence of back-to-back appointment requests
 * and is debating which ones to accept. She needs a 15 minute break between
 * appointments a and therefore she cannot accept any adjacent requests. Given a
 * sequence of back-to-back appointments requests (all of 15 minutes, none
 * overlap, and none can be moved), find the optimal (highest total booked
 * minutes) set the messeuse can honor. Return the number of minutes
 * 
 * Example:
 * input: 30, 15, 60, 75, 45, 15, 15, 45
 * Output: 60 minutes 30. 60, 45, 45
 * 
 * @author nacho
 *
 */
public class TheMesseuse {

	public static void main(String[] args) {
		int[] input = new int[] {30, 15, 60, 75, 45, 15, 15, 45};
		System.out.println(maxMinutes(input, 0, new int[input.length]));
	}
	
	private static int maxMinutes(int[] massages, int index, int[] memo) {
		if (index >= massages.length) {
			return 0;
		}
		if (memo[index] == 0) {
			int bestWith = massages[index] + maxMinutes(massages, index + 2, memo);
			int bestWithout = maxMinutes(massages, index + 1, memo);
			memo[index] = Math.max(bestWith, bestWithout);
		}
		
		return memo[index];
	}
}
