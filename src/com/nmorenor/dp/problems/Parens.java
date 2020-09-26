package com.nmorenor.dp.problems;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Implement an algorithm to print all valid(e.g., properly opened and closed)
 * combinations of parentheses.
 * input: 3
 * Output: ()()(), (()()), ()(()), ((())), (())()
 * @author nacho
 *
 */
public class Parens {

	public static void main(String[] args) {
		ParensImpl parens = new ParensImpl();
		List<String> solution = parens.solve(3);
		System.out.println(solution);
	}
	
	private static class ParensImpl {
		
		public List<String> solve(int n) {
			LinkedList<String> result = new LinkedList<String>();
			solve(n, result);
			return result;
		}
		
		private void solve(int n, LinkedList<String> result) {
			if (n <= 0) {
				result.add("");
				return;
			}
			if (n == 1) {
				result.add("()");
				return;
			}
			solve(n - 1, result);
			Set<String> holder = new HashSet<String>();
			for (int i = 1; i < n; i++) {
				String next = result.poll();
				holder.add("()" + next);
				holder.add("(" + next + ")");
				holder.add(next + "()");
			}
			result.addAll(holder);
		}
	}
}
