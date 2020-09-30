package com.nmorenor.dp.problems;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;

/**
 * You have two numbers represented by a linked list, where each node contains a single digit,
 * The digits are stored in reverse order such that the 1's digit is at the head of the list.
 * Write a function that adds the two numbers and returns the sum as a linked list
 * (You are not allowed to "cheat" and just convert the linked list to an integer.)
 * @author nacho
 *
 */
public class SumLists {

	public static void main(String[] args) {
		LinkedList<Integer> a = new LinkedList<Integer>();
		a.addFirst(5);
		a.addFirst(3);
		a.addFirst(1);
		LinkedList<Integer> b = new LinkedList<Integer>();
		b.addFirst(5);
		b.addFirst(3);
		b.addFirst(1);
		b.addFirst(2);
		LinkedList<Integer> c = new LinkedList<Integer>();
		// 135 + 2135 = 2270
		sumList(a, b, c, Math.max(a.size(), b.size() - 1));
		System.out.println(c);
	}
	
	public static int sumList(LinkedList<Integer> a, LinkedList<Integer> b, LinkedList<Integer> c, int i) {
		if (i < 0) {
			return 0;
		}
		int reminder = sumList(a, b, c, i - 1);
		int one = i < a.size() ? a.get((a.size() - 1) - i) : 0;
		int other = i < b.size() ? b.get((b.size() - 1) - i) : 0;
		int[] sum = String.valueOf(one + other + reminder).chars().map(Character::getNumericValue).toArray();
		c.addFirst(sum[sum.length - 1]);
		Optional<String> rem = Arrays.stream(Arrays.copyOfRange(sum, 0, sum.length - 1)).mapToObj(String::valueOf).reduce((next, prev) -> next + prev);
		return Integer.valueOf(rem.isEmpty() ? "0" :rem.get());
	}
}
