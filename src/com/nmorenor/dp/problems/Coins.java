package com.nmorenor.dp.problems;

/**
 * Given an infinite number of quarters (25 cents), dimes (10 cents), nickels (5 cents)
 * and pennies (1 cent), write code to calculate the number of ways of representing n cents
 * @author nacho
 *
 */
public class Coins {

	private static int QUARTER = 25;
	private static int DIMES = 10;
	private static int NICKELS = 5;
	private static int PENNIES = 1;
	
	private static int[] coinKinds = new int[] {QUARTER, DIMES, NICKELS, PENNIES};
	
	
	public static void main(String[] args) {
		int target = 98;
		int [][] numberOfCoins = new int[coinKinds.length][];
		for (int i = 0; i < coinKinds.length; i++) {
			int next[] = target > coinKinds[i] ? new int[target/coinKinds[i] + 1] : new int[0];
			numberOfCoins(target, coinKinds[i], next.length - 1, next);
			numberOfCoins[i] = next;
		}
		
		int numWays = 0;
		for (int i = 0; i < numberOfCoins.length; i++) {
			numWays += numberOfWays(target, i, numberOfCoins);
		}
		System.out.println(numWays);
	}
	
	public static int numberOfWays(int cents, int coinKind, int [][] numberOfCoins) {
		if (coinKind >= numberOfCoins.length) {
			return 0;
		}
		if (cents < numberOfCoins[coinKind][1]) {
			return 0;
		}
		// last coin kind max number of coins to get to cents is cents, there is no other combination
		if (cents == numberOfCoins[coinKind][numberOfCoins[coinKind].length - 1]) {
			return 1;
		}
		int foundIndex = indexOf(cents, 0, numberOfCoins[coinKind].length - 1, numberOfCoins[coinKind]);
		int ways = foundIndex != -1 ? 1 : 0;
		for (int i = 1; i < numberOfCoins[coinKind].length; i++) {
			int nextCents = cents - numberOfCoins[coinKind][i];
			if (nextCents == 0) {
				continue;
			}
			for (int j = coinKind + 1; j < numberOfCoins.length; j++) {
				if (indexOf(nextCents, 0, numberOfCoins[j].length - 1, numberOfCoins[j]) >= 0) {
					ways++;
				}
			}
		}
		return ways;
	}
	
	public static int indexOf(int cents, int i, int j, int[]numberOfCoins) {
		if ( j < i || i > j) {
			return -1;
		}
		if (cents > numberOfCoins[j]) {
			return -1;
		}
		if (cents == numberOfCoins[i]) {
			return i;
		}
		if (cents == numberOfCoins[j]) {
			return j;
		}
		int mid = (i + j) / 2;
		if (cents == numberOfCoins[mid]) {
			return mid;
		}
		if (cents < numberOfCoins[mid]) {
			int index = indexOf(cents, i + 1, mid - 1, numberOfCoins);
			if (index >= 0) {
				return index;
			}
		} else {
			int index = indexOf(cents, mid + 1, j - 1, numberOfCoins);
			if (index >= 0) {
				return index;
			}
		}
		return -1;
		
	}
	
	public static void numberOfCoins(int cents, int coinValue, int nCoins, int[] coins) {
		if (nCoins <= 0) {
			return;
		}
		if (cents <= 0) {
			return;
		}
		if (cents < coinValue) {
			return;
		}
		numberOfCoins(cents - coinValue, coinValue, nCoins - 1, coins);
		coins[nCoins] += coins[nCoins - 1] + coinValue;
	}
}
