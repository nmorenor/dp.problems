package com.nmorenor.dp.problems;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.io.*;
/**
 * Cody-Jamal is working on his latest piece of abstract art: a mural consisting of a 
 * row of waning moons and closed umbrellas. Unfortunately, greedy copyright trolls are 
 * claiming that waning moons look like an uppercase C and closed umbrellas look like a J, 
 * and they have a copyright on CJ and JC. Therefore, for each time CJ appears in the mural, 
 * Cody-Jamal must pay X, and for each time JC appears in the mural, he must pay Y.
 *  
 * @author nacho
 *
 */
public class MoonAndUmbrellas {

	void solve() throws IOException {
		int t = nextInt(), x, y;
		String mural;
		for (int nextCase = 0; nextCase < t; nextCase++) {
			x = nextInt();
			y = nextInt();
			mural = next();
			
			HashMap<String, Integer> memo = new HashMap<String, Integer>();
			memo.put("C", 0);
			memo.put("J", 0);
			memo.put("CJ", x);
			memo.put("JC", y);
			AtomicReference<String> result = new AtomicReference<>();
			minCost(0, mural.length(), mural, memo, result);
			Integer cost = result.get() == null ? memo.get(mural) : memo.get(result.get());
			out.println("Case #" + (nextCase + 1) + ": " + (cost == null ? 0 : cost));
		}
	}
	
	void minCost(int i, int j, String mural, Map<String, Integer> memo, AtomicReference<String> result) {
		if (i >= j) {
			return;
		}
		if (mural.indexOf("?") < 0 && memo.containsKey(mural)) {
			return;
		}
		minCost(i + 1, j, mural, memo, result);
		String next = mural.substring(i, j);
		
		if (next.startsWith("?")) {
			String one = mural.substring(0, i) + "J" + next.substring(1);
			String other = mural.substring(0, i) + "C" + next.substring(1);
			minCost(0, one.length(), one, memo, result);
			minCost(0, other.length(), other, memo, result);
			if (other.indexOf("?") < 0 && other.length() == mural.length()) {
				Integer oneCost = memo.get(one);
				Integer otherCost = memo.get(other);
				Integer currentMin = Math.min(oneCost, otherCost);
				if (result.get() != null && memo.containsKey(result.get())) {
					if (currentMin.compareTo(memo.get(result.get())) <= 0) {
						if (oneCost.compareTo(otherCost) <= 0) {
							result.set(one);
						} else {
							result.set(other);
						}
					}
				} else {
					if (oneCost.compareTo(otherCost) <= 0) {
						result.set(one);
					} else {
						result.set(other);
					}
				}
			}
			return;
		}
		if (memo.containsKey(next)) {
			return;
		}
		
		int nextCost = 0;
		for (int k = 0; k < next.length() - 1; k++) {
			String n = next.substring(k, k + 2);
			if (memo.containsKey(n)) {
				nextCost += memo.get(n);
			}
			String rest = next.substring(k + 2);
			if (!rest.trim().isEmpty() && memo.containsKey(rest)) {
				char last = n.charAt(n.length() - 1);
				char first = rest.charAt(0);
				String prefix = new String(new char[] {last, first});
				if (memo.containsKey(prefix)) {
					nextCost += memo.get(prefix);
				}
				nextCost += memo.get(rest);
				break;
			}
			
		}
		if (next.length() == mural.length() && result.get() == null) {
			result.set(next);
		} else if (result.get() != null && Integer.valueOf(nextCost).compareTo(memo.get(result.get())) < 0) {
			result.set(next);
		}
		memo.put(next, nextCost);
	}

	BufferedReader reader;
	StringTokenizer tokenizer;
	PrintWriter writer;

	void run() throws IOException {
		// in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
		in = new BufferedReader(new FileReader(new File("Resolve"))); // change path to resolve
		solve();
		out.close();
	}

	public static void main(String[] args) throws IOException {
		new MoonAndUmbrellas().run();
	}

	BufferedReader in;
	StringTokenizer str;
	PrintWriter out;

	String next() throws IOException {
		while ((str == null) || (!str.hasMoreTokens())) {
			str = new StringTokenizer(in.readLine());
		}
		return str.nextToken();
	}

	int nextInt() throws IOException {
		return Integer.parseInt(next());
	}

}
