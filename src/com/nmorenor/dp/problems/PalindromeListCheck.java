package com.nmorenor.dp.problems;

import java.util.LinkedList;

/**
 * Implement a function to check if a linked list is palindrome
 * @author nacho
 *
 */
public class PalindromeListCheck {

	public static void main(String[] args) {
		LinkedList<Integer> palindrome = new LinkedList<Integer>();
		palindrome.add(3);
		palindrome.add(2);
		palindrome.add(5);
		palindrome.add(2);
		palindrome.add(3);
		LinkedList<Integer> notpalindrome = new LinkedList<Integer>();
		notpalindrome.add(3);
		notpalindrome.add(2);
		notpalindrome.add(1);
		notpalindrome.add(3);
		
		System.out.println(isPalindromeList(palindrome, 0));
		System.out.println(isPalindromeList(notpalindrome, 0));
	}
	
	private static boolean isPalindromeList(LinkedList<Integer> list, int i) {
		int mid = (list.size()) / 2;
		
		while (mid >= i) {
			int last = getKthLast(list, list.size(), i);
			int first = list.get(i);
			if (last != first) {
				return false;
			}
			i++;
		}
		return true;
	}
	
	private static <T> T getKthLast(LinkedList<T> list, int i, int pos) {
		if ((list.size() - i) <= pos) {
			return getKthLast(list, i - 1, pos);
		}
		return list.get(i);
	}
}
