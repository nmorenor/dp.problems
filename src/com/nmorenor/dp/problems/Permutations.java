package com.nmorenor.dp.problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Permutations {

	public static void main(String[] args) {
		PermutationsImpl permutations = new PermutationsImpl();
		String input = "abc";
		List<String> perms = permutations.doPermutations(input.length() - 1, input.toCharArray());
		for (String next : perms) {
			System.out.println(next);
		}
	}
	
	private static class PermutationsImpl {
		
		public List<String> doPermutations(int n, char[] word) {
			if (n == 0) {
				return new ArrayList<String>(Collections.singletonList(new String(new char[] {word[0]})));
			}
			List<String> prev = doPermutations(n - 1, word);
			
			List<String> result = new ArrayList<String>();
			
			
			for (int i = n; i <= n; i++) {
				for (String next : prev) {
					result.add(next + word[i]);
				}
				for (String next : prev) {
					result.add(word[i] + next);
				}
			}
			return result;
			
		}
	}
}
