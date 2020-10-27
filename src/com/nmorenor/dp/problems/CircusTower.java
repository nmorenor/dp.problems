package com.nmorenor.dp.problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A circus is designing a tower routine consisting of people standing atop one
 * another's shoulders. For practical and aesthetic reasons, each person must be
 * both shorter and lighter than the person below him or her. Given the heights
 * and weights of each person in the circus, write a method to compute the
 * largest possible number of people in such a tower.
 * 
 * EXAMPLE Input (ht, wt): (65, 100) (70, 150) (56, 90) (75, 190) (60, 95) (68,
 * 110) Output: The longest tower is lenght 6 and includes from top to bottom:
 * (56, 90) (60, 95) (65, 100) (68, 110) (70, 150) (75, 190)
 * 
 * @author nacho
 *
 */
public class CircusTower {

	public static class Person implements Comparable<Person> {
		private final int height;
		private final int weight;

		public Person(int height, int weight) {
			this.height = height;
			this.weight = weight;
		}

		public int getWeight() {
			return weight;
		}

		public int getHeight() {
			return height;
		}

		@Override
		public String toString() {
			return "(" + String.valueOf(height) + ", " + String.valueOf(weight) + ")";
		}
		
		public boolean isBefore(Person other) {
			return height < other.height;
		}

		@Override
		public int compareTo(Person o) {
			return Integer.valueOf(height).compareTo(o.height);
		}
	}

	public static void main(String[] args) {
		String input = "(65, 100) (70, 150) (56, 90) (75, 190) (60, 95) (68, 110)";
		ArrayList<Person> persons = new ArrayList<Person>();

		parseInput(input, persons);
		Collections.sort(persons);
		
		System.out.println(getMaxTower(persons));
	}
	
	private static ArrayList<Person> getMaxTower(ArrayList<Person> persons) {
		ArrayList<ArrayList<Person>> solutions = new ArrayList<ArrayList<Person>>();
		ArrayList<Person> longest = null;
		
		for (int i = 0; i < persons.size(); i++) {
			ArrayList<Person> longestAtIndex = getLongestAtIndex(persons, solutions, i);
			solutions.add(i, longestAtIndex);
			longest = max(longestAtIndex, longest);
		}
		return longest;
	}
	
	@SuppressWarnings("unchecked")
	private static ArrayList<Person> getLongestAtIndex(ArrayList<Person> persons, ArrayList<ArrayList<Person>> solutions, int index) {
		Person value = persons.get(index);
		
		ArrayList<Person> bestSequence = new ArrayList<Person>();
		for (int i = 0; i < index; i++) {
			ArrayList<Person> solution = solutions.get(i);
			if (canAppend(value, solution)) {
				bestSequence = max(solution, bestSequence);
			}
		}
		
		ArrayList<Person> result = (ArrayList<Person>)bestSequence.clone();
		result.add(value);
		return result;
	}
	
	private static boolean canAppend(Person person, ArrayList<Person> sequence) {
		if (sequence == null) {
			return true;
		}
		if (sequence.size() == 0) {
			return true;
		}
		Person last = sequence.get(sequence.size() - 1);
		return last.isBefore(person);
	}
	
	private static ArrayList<Person> max(ArrayList<Person> one, ArrayList<Person> other) {
		if (other == null && one != null) {
			return one;
		}
		if (one == null && other != null) {
			return other;
		}
		return one.size() > other.size() ? one : other;
	}

	private static void parseInput(String input, List<Person> persons) {
		if (input == null || input.trim().length() == 0) {
			return;
		}
		if (input.indexOf("(") < 0) {
			return;
		}
		input = input.substring(input.indexOf("(") + 1);
		String nextPerson = input.substring(0, input.indexOf(")"));

		int height = Integer.valueOf(nextPerson.substring(0, nextPerson.indexOf(",")));
		int weight = Integer.valueOf(nextPerson.substring(nextPerson.indexOf(",") + 1).trim());
		persons.add(new Person(height, weight));

		parseInput(input.substring(input.indexOf(")") + 1), persons);
	}
}
