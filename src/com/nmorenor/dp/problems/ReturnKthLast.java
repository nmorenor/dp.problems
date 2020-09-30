package com.nmorenor.dp.problems;

import java.util.LinkedList;
/**
 * Implement an algorithm to find the kth to last element of a singly linked list.
 * @author nacho
 *
 */
public class ReturnKthLast {

	public static void main(String[] args) {
		LinkedList<Integer> input = new LinkedList<Integer>();
		for (int i = 0; i < 25; i++) {
			input.add(i);
		}
		System.out.println(getKthLast(input, input.size(), 3));
	}
	
	private static <T> T getKthLast(LinkedList<T> list, int i, int pos) {
		if ((list.size() - i) < pos) {
			return getKthLast(list, i - 1, pos);
		}
		return list.get(i);
	}
}
