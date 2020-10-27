package com.nmorenor.dp.problems;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * Given a list of words, write a program to find the longest word made of other words
 * in the list.
 * 
 * Example
 * Input: cat, banana, dog, nana, walk, walker, dogwalker
 * Output: dogwalker
 * 
 * @author nacho
 *
 */
public class LongestWord {
	
	public static class Word implements Comparable<Word> {
		private final String word;
		private final Set<String> composed = new HashSet<String>();
		
		public Word(String word) {
			this.word = word;
		}
		
		public int compareTo(Word other) {
			return word.compareTo(other.word);
		}
		
		@Override
			public String toString() {
				return word;
			}
	}

	public static void main(String[] args) {
		String[] input = new String[] {"dog", "cat", "banana", "nana", "walk", "walker", "dogwalker"};
		final Set<String> dictionary = new HashSet<String>();
		Arrays.stream(input).forEach(next -> dictionary.add(next));
		
		Word[] words = Arrays.stream(input).map(next -> new Word(next)).toArray(Word[]::new);
		Arrays.sort(words, new Comparator<Word>() {

			@Override
			public int compare(Word o1, Word o2) {
				return Integer.compare(o2.word.length(), o1.word.length());
			}
		});
		getLongestWord(words, 0, words[0].word.length() - 1, "", true, dictionary);
		getLongestWord(words, 0, 0, "", false, dictionary);
		Arrays.sort(words, new Comparator<Word>() {

			@Override
			public int compare(Word o1, Word o2) {
				return Integer.compare(o2.composed.size(), o1.composed.size());
			}
		});
		System.out.println(words[0]);
	}
	
	private static void getLongestWord(Word[] input, int index, int i, String current, boolean suffix, Set<String> dictionary) {
		if (index >= input.length) {
			return;
		}
		if (suffix && i < 0) {
			if (index + 1 >= input.length) {
				return;
			}
			getLongestWord(input, index + 1, input[index+1].word.length() - 1, "", suffix, dictionary);
			return;
		}
		if (!suffix && i >= input[index].word.length()) {
			getLongestWord(input, index + 1, 0, "", suffix, dictionary);
			return;
		}
		
		if (suffix) {
			current = input[index].word.charAt(i) + current;
		} else {
			current += input[index].word.charAt(i);				
		}
		if (input[index].composed.contains(current)) {
			getLongestWord(input, index, suffix ? i - 1 : i + 1, current, suffix, dictionary);
			return;
		}
		if (dictionary.contains(current)) {
			input[index].composed.add(current);
		}
		getLongestWord(input, index, suffix ? i - 1 : i + 1, current, suffix, dictionary);
	}
}
