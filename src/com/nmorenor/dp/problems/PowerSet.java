package com.nmorenor.dp.problems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Write a method that returns all subsets of a set
 * @author nacho
 *
 */
public class PowerSet {
	
	public static void main(String[] args) {
		// for simplicity we assume that is a set of integers, 
		// an array of integers with distinct integers
		int [] input = new int[] { 5, 8, 10, 20, 1, 3, 26, 18, 21, 44 };
		PowerSetImpl powerSetImpl = new PowerSetImpl(input); // this will just print the results
		Set<List<Integer>> result = powerSetImpl.getResult();
		result.stream().forEach(next -> System.out.println(next.stream().map(val -> String.valueOf(val)).reduce((str1, str2) -> str1 + ", " + str2).get()));
		
	}
	
	private static class PowerSetImpl {
		
		private Set<List<Integer>> result;

		@SuppressWarnings("unchecked")
		public PowerSetImpl(int[] input) {
			result = new HashSet<List<Integer>>();
			solve(input.length - 1, input.length -1, input, new List[input.length][input.length], new ArrayList<Integer>(), result);
		}

		private List<Integer> solve(int i, int j, int[] input, List<Integer>[][] memo, List<Integer> next, Set<List<Integer>> result) {
			if (next == null) {
				return new ArrayList<>();
			}
			if (i < 0 || j < 0) {
				return new ArrayList<>();
			}
			if (memo[i][j] != null) {
				return memo[i][j];
			}
			if (next.size() >= input.length - 1 ) {
				return new ArrayList<>();
			}
			boolean modified = false;
			if (!next.contains(input[i])) {
				next.add(input[i]);
				modified = true;
			}
			if (!next.contains(input[j])) {
				next.add(input[j]);
				modified = true;
			}
			
			memo[i][j] = new ArrayList<>(next);
			if (modified) {
				result.add(memo[i][j]);
			}
			
			if (i > 0) {
				next = solve(i - 1, j, input, memo, next, result);
				memo[i][j] = new ArrayList<>(next);
			}
			
			if (j > 0) {
				next = solve(i, j - 1, input, memo, next, result);
				memo[i][j] = new ArrayList<>(next);
			}
			
			return next;
		}
		
		public Set<List<Integer>> getResult() {
			return result;
		}
	}
}
