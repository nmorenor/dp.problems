package com.nmorenor.dp.problems;

/**
 * Write a method to count the number of 2s between 0 and n
 * @author nacho
 *
 */
public class CountOf2s {

	public static void main(String[] args) {
		System.out.println(bruteForce(100));
		System.out.println(count2sInRange(100));
	}
	
	private static int count2sInRange(int number) {
		int count = 0;
		int len = String.valueOf(number).length();
		for (int digit = 0; digit < len; digit++) {
			count += count2sInRangeAtDigit(number, digit);
		}
		return count;
	}
	
	private static int count2sInRangeAtDigit(int number, int d) {
		int powerOf10 = (int) Math.pow(10, d);
		int nextPowerOf10 = powerOf10 * 10;
		int right = number % powerOf10;
		
		int roundDown = number - (number % nextPowerOf10);
		int roundUp = roundDown + nextPowerOf10;
		
		int digit = (number / powerOf10) % 10;
		if (digit < 2) {
			return roundDown / 10;
		} else if (digit == 2) {
			return roundDown / 10 + right + 1;
		} else {
			return roundUp / 10;
		}
	}
	
	private static int bruteForce(int n) {
		int count = 0;
		for (int i = 2; i <= n; i++) {
			count += numberOf2s(i);
		}
		return count;
	}
	
	private static int numberOf2s(int n) {
		int count = 0;
		while (n > 0) {
			if (n % 10 == 2) {
				count++;
			}
			n = n / 10;
		}
		return count;
	}
}
