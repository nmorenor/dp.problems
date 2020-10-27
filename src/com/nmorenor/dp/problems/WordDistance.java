package com.nmorenor.dp.problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * You have a large text file containing words. Given any two words, find the shortest
 * distance (in terms of words) between them in the file. If the operation will be repeated
 * many times for the same file (but different pairs of words), can you optimize your solution.
 * 
 * @author nacho
 *
 */
public class WordDistance {

	private static String text = "Long ago, there lived a lion in a dense forest. One morning his wife told him that his breath was bad and unpleasant. The lion became embarrassed and angry upon hearing it. He wanted to check this fact with others. So he called three others outside his cave.\n"
			+ "\n"
			+ "First came the sheep. The Lion opening his mouth wide said, “Sheep, tell me if my mouth smells bad?” The sheep thought that the lion wanted an honest answer, so the sheep said, “Yes, Friend. There seems to be something wrong with your breath”. This plain speak did not go well with the lion. He pounced on the sheep, killing it.\n"
			+ "\n"
			+ "Then the lion called the wolf and said, “What do you think? Do I have a bad breath?” The wolf saw what happened to the sheep. He wanted to be very cautious in answering a question. So, the wolf said, “Who says that Your breath is unpleasant. It’s as sweet as the smell of roses”. When the lion heard the reply, he roared in an anger and immediately attacked the wolf and killed it. “The flatterer!” growled the lion.\n"
			+ "\n"
			+ "Finally, came the turn of the fox. The lion asked him the same question. The fox was well aware of the fate of the sheep and the wolf. So he coughed and cleared his throat again and again and then said, “Oh Dear Friend, for the last few days, I have been having a very bad cold. Due to this, I can’t smell anything, pleasant or unpleasant”.\n"
			+ "\n"
			+ "The lion spared the fox’s life.";
	
	public static void main(String[] args) {
		Map<String, Word> words = extractWords();
		String[] input = new String[] {"lion", "sheep"};
		
		if (words.containsKey(input[0]) && words.containsKey(input[1])) {
			Word one = words.get(input[0]);
			Word other = words.get(input[1]);
			int distance = Integer.MAX_VALUE;
			WordLocation oLocation = null;
			WordLocation otLocation = null;
			
			for (WordLocation oneLocation : one.locations) {
				for (WordLocation otherLocation : other.locations) {
					int currentDistance = oneLocation.offset > otherLocation.offset ? oneLocation.offset - otherLocation.offset : otherLocation.offset - oneLocation.offset;
					if (currentDistance < distance) {
						distance = currentDistance;
						oLocation = oneLocation;
						otLocation = otherLocation;
					}
				}
			}
			if (oLocation.offset < otLocation.offset) {
				System.out.println(text.substring(oLocation.offset, otLocation.offset + other.word.length()));
				System.out.println(distance);
			} else {
				System.out.println(text.substring(otLocation.offset, oLocation.offset + one.word.length()));
				System.out.println(distance);
			}
		}
	}

	private static Map<String, Word> extractWords() {
		Map<String, Word> words = new HashMap<String, Word>();
		
		int current = 0;
		int line = 1;
		String currentText = text;
		while (!currentText.isEmpty()) {
			if (currentText.indexOf("\n") >= 0) {
				String lineText = currentText.substring(0, currentText.indexOf("\n"));
				if (lineText.equals("")) {
					current++;
					line++;
					currentText = currentText.substring(currentText.indexOf("\n") + 1);
					continue;
				}
				int lineOffset = 0;
				while (!lineText.isEmpty()) {
					String nextWord = lineText.indexOf(" ") < 0 ? lineText : lineText.substring(0, lineText.indexOf(" "));
					int nextWordLength = nextWord.length();
					if (nextWord.equals(" ")) {
						current++;
						lineOffset++;
						lineText = lineText.indexOf(" ") < 0 ? lineText : lineText.substring(lineText.indexOf(" ") + 1);
						continue;
					}
					if (nextWord.indexOf(",") >= 0 || nextWord.indexOf(".") >= 0 || nextWord.indexOf("“") >= 0 || nextWord.indexOf("”") >= 0) {
						nextWord = nextWord.replaceAll("\\.", "");
						nextWord = nextWord.replaceAll(",", "");
						nextWord = nextWord.replaceAll("“", "");
						nextWord = nextWord.replaceAll("”", "");
					}
					Word word = words.get(nextWord.toLowerCase());
					if (word == null) {
						word = new Word(nextWord.toLowerCase());
						words.put(nextWord, word);
					}
					WordLocation locaiton = new WordLocation(line, lineOffset, current);
					word.locations.add(locaiton);
					lineOffset += nextWordLength + 1;
					current += nextWordLength + 1;
					lineText = lineText.indexOf(" ") < 0 ? lineText.substring(nextWordLength) : lineText.substring(lineText.indexOf(" ") + 1);
				}
				line++;
				currentText = currentText.substring(currentText.indexOf("\n") + 1);
			} else {
				int lineOffset = 0;
				while (!currentText.isEmpty()) {
					String nextWord = currentText.indexOf(" ") < 0 ? currentText : currentText.substring(0, currentText.indexOf(" "));
					int nextWordLength = nextWord.length();
					if (nextWord.equals(" ")) {
						currentText = currentText.indexOf(" ") < 0 ? currentText.substring(currentText.length()) : currentText.substring(currentText.indexOf(" ") + 1);
						current++;
						continue;
					}
					if (nextWord.indexOf(",") >= 0 || nextWord.indexOf(".") >= 0 || nextWord.indexOf("“") >= 0 || nextWord.indexOf("”") >= 0) {
						nextWord = nextWord.replaceAll("\\.", "");
						nextWord = nextWord.replaceAll(",", "");
						nextWord = nextWord.replaceAll("“", "");
						nextWord = nextWord.replaceAll("”", "");
					}
					Word word = words.get(nextWord.toLowerCase());
					if (word == null) {
						word = new Word(nextWord.toLowerCase());
						words.put(nextWord, word);
					}
					WordLocation locaiton = new WordLocation(line, lineOffset, current);
					word.locations.add(locaiton);
					lineOffset += nextWordLength + 1;
					current += nextWordLength + 1;
					currentText = currentText.indexOf(" ") < 0 ? currentText.substring(currentText.length()) : currentText.substring(currentText.indexOf(" ") + 1);
				}
			}
		}
		return words;
	}
	
	public static class Word {
		private final String word;
		private ArrayList<WordLocation> locations = new ArrayList<WordLocation>();
		
		public Word(String word) {
			this.word = word;
		}
		
		@Override
		public String toString() {
			return word;
		}
	}
	
	@SuppressWarnings("unused")
	public static class WordLocation {
		private final int line;
		private final int lineOffset;
		private final int offset;
		
		public WordLocation(int line, int lineOffset, int offset) {
			this.line = line;
			this.lineOffset = lineOffset;
			this.offset = offset;
		}
	}
}
