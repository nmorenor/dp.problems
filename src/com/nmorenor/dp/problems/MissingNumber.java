package com.nmorenor.dp.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An array a contains all the integers from 0 to n, except for one number which
 * is missing. In this problem, we cannot access an entire integer in A with a
 * single operation. The elements of A are represented in binary, and the only
 * operation we can use to access them is "fetch the jth bit of A[i]" which
 * takes contant time. Write code to find the missing integer. Can you do it in
 * O(n) time?
 * 
 * @author nacho
 *
 */
public class MissingNumber {

	public static void main(String[] args) {
		int size = 10;
		int[] in = new int[size];
		int randomNumber = getRandomNumber(1, size);

		boolean beforeMissing = true;
		for (int i = 0; i < size + 1; i++) {
			if (i == randomNumber) {
				beforeMissing = false;
				continue;
			}
			in[beforeMissing ? i : i - 1] = i;
		}
		System.out.println(Arrays.toString(in));
		System.out.println("Missing: " + String.valueOf(randomNumber));
		
		List<int[]> bits = int2bit(in);
		int missingNumber = getMissingNumber(bits, 31);
		System.out.println(missingNumber);
	}
	
	private static int getMissingNumber(List<int[]> bits, int i) {
		if (i < 0) {
			return 0;
		}
		List<int[]> oneBits = new ArrayList<int[]>();
		List<int[]> zeroBits = new ArrayList<int[]>();
		
		for (int[] next : bits) {
			if (next[i] == 0) {
				zeroBits.add(next);
			} else {
				oneBits.add(next);
			}
		}
		
		if (zeroBits.size() <= oneBits.size()) {
			int v = getMissingNumber(zeroBits, i - 1);
			return (v << 1) | 0;
		} else {
			int v = getMissingNumber(oneBits, i - 1);
			return (v << 1) | 1;
		}
	}

	private static int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min)) + min);
	}

	private static String toBinaryString(int number) {

		StringBuilder res = new StringBuilder();
		int displayMask = 1 << 31;
		for (int i = 1; i <= 32; i++) {
			res.append((number & displayMask) == 0 ? '0' : '1');
			number = number << 1;
		}
		return res.toString();
	}
	
	public static int getIntValue(int[] input, int i) {
		int count = 0;
		int result = 0;
		
		for (int j = 31; j >= 0; j--) {
			if (input[i + j] == 1) {
				result += 1 << count;
			}
			count++;
		}
		return result;
	}

	private static List<int[]> int2bit(int[] src) {
		int srcLength = src.length;
		List<int[]> result = new ArrayList<int[]>();

		for (int i = 0; i < srcLength; i++) {
			String binaryString = toBinaryString(src[i]);
			result.add(binaryString.chars().map(x -> x - '0').toArray());
		}
		
		return result;
	}
}
