package com.nmorenor.dp.problems;

import java.util.HashSet;
import java.util.Set;

/**
 * Oh, no! You have accidentally removed all spaces, punctuation, and
 * capitalization in a lengthy document. A sentence like "I reset the computer.
 * It still didn't boot!" became "iresetthecomputeritstilldidntboot". You'll
 * deal with the punctuation and capitalization later; right now you need to
 * re-isert the spaces. Most of the words are in a dictionary but a few are not.
 * Given a dictionary (a list of strings) and document (a string), design an
 * algorithm to unconcatenate the document in a way that minimizes the number of
 * unrecognized characters.
 * 
 * Example:
 * 
 * Input: jesslookedjustliketimherbrother Output: jess looked just like tim her
 * brother (7 unrecognized characters) (tim & jess)
 * 
 * @author nacho
 *
 */
public class ReSpace {

	public static class ParseResult {
		public int invalid = Integer.MAX_VALUE;
		public String parsed = " ";

		public ParseResult(int invalid, String parsed) {
			this.invalid = invalid;
			this.parsed = parsed;
		}

		@Override
		public String toString() {
			return parsed + " (" + String.valueOf(invalid) + " unrecognized characters)";
		}
	}

	public static void main(String[] args) {
		Set<String> dictionary = new HashSet<String>();
		dictionary.add("looked");
		dictionary.add("just");
		dictionary.add("like");
		dictionary.add("her");
		dictionary.add("brother");

		String input = "jesslookedjustliketimherbrother";
		ParseResult solution = solution(dictionary, input, 0, new ParseResult[input.length()]);
		System.out.println(solution);
	}

	private static ParseResult solution(Set<String> dictionary, String sentence, int start, ParseResult[] memo) {
		if (start >= sentence.length()) {
			return new ParseResult(0, "");
		}
		if (memo[start] != null) {
			return memo[start];
		}

		int bestInvalid = Integer.MAX_VALUE;
		String bestParsing = null;
		String partial = "";
		int index = start;
		while (index < sentence.length()) {
			char c = sentence.charAt(index);
			partial += c;
			int invalid = dictionary.contains(partial) ? 0 : partial.length();
			if (invalid < bestInvalid) {
				ParseResult result = solution(dictionary, sentence, index + 1, memo);
				if (invalid + result.invalid < bestInvalid) {
					bestInvalid = invalid + result.invalid;
					bestParsing = partial + " " + result.parsed;
					if (bestInvalid == 0) {
						break;
					}
				}
			}
			index++;
		}
		memo[start] = new ParseResult(bestInvalid, bestParsing);
		return memo[start];
	}

}
