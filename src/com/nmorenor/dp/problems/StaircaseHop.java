package com.nmorenor.dp.problems;

/**
 * A child is running up a staircase with n steps and can hop either 1 step, 2 steps or 3 steps
 * at a time. Implement a method to count how many possible ways the child can run up the stairs.
 * @author nacho
 *
 */
public class StaircaseHop {

	public static void main(String [] args) {
		StairHops stairHops = new StairHops(3);
		System.out.println(stairHops.getWays());
	}
	
	private static class StairHops {
		
		private int steps;
		private int ways;

		public StairHops(int steps) {
			this.steps = steps;
			this.ways = doHops(0, steps, new int[steps + 1]);
		}
		
		private int doHops(int level, int steps, int memo[]) {
			if (level > steps) {
				return 0;
			}
			if (memo[level] != 0) {
				return memo[level];
			}
			int hops = doHops(level + 1, steps, memo) + 1;
			hops += doHops(level + 2, steps, memo) + 1;
			hops += doHops(level + 3, steps, memo) + 1;
			memo[level] = hops;
			return  hops;  
		}
		
		public int getWays() {
			return ways;
		}
		
		@SuppressWarnings("unused")
		public int getSteps() {
			return steps;
		}
	}
}
