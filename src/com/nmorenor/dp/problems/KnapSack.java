package com.nmorenor.dp.problems;

import java.util.Arrays;

/**
 * Given weights and values of n items, put these items in a knapsack of
 * capacity W to get the maximum total value in the knapsack. In other words,
 * given two integer arrays val[0..n-1] and wt[0..n-1] which represent values
 * and weights associated with n items respectively. Also given an integer W
 * which represents knapsack capacity, find out the maximum value subset of
 * val[] such that sum of the weights of this subset is smaller than or equal to
 * W. You cannot break an item, either pick the complete item or don’t pick it
 * (0-1 property).
 * 
 * @author nacho
 *
 */
public class KnapSack {

	public static void main(String[] args) {
		int value[] = new int[] {60, 100, 120};
		int weight[] = new int[] { 10, 20, 30 };
		int W = 50;
		int [][] dp = new int[value.length][];
		for (int i = 0; i < dp.length; i++) {
			dp[i] = new int[W + 1];
			Arrays.fill(dp[i], -1);
		}
		int maxValue = getMaxValue(value.length - 1 , value, weight, W, dp);
		System.out.println(maxValue);
	}
	
	private static int getMaxValue(int index, int[] value, int weight[], int W, int [][] dp) {
		if (index < 0) {
			return 0;
		}
		if (W == 0) {
			return 0;
		}
		if (dp[index][W] != -1) {
			return dp[index][W];
		}
		if (weight[index] > W) {
			dp[index][W] = getMaxValue(index - 1, value, weight, W, dp);
			return dp[index][W];
		}
		dp[index][W] = Math.max(value[index] + getMaxValue(index - 1, value, weight, W - weight[index], dp), 
				getMaxValue(index - 1, value, weight, W - weight[index], dp));
		return dp[index][W];
	}
}
