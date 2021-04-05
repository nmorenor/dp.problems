package com.nmorenor.dp.problems;

import java.util.*;
import java.io.*;

/**
 * Get the minimum number of operations required to sort an array of integers
 * with a reverse algorithm.
 * @author nacho
 *
 */
public class Reverse {

	void solve() throws IOException {
		int t = nextInt(), n;
		for (int nextCase = 0; nextCase < t; nextCase++) {
			n = nextInt();
			int [] c = new int[n];
			for (int j = 0; j < n; j++) {
				c[j] = nextInt();
			}
			
			int next = c[0];
			int totalCost = 0;
			for (int i = 1; i < c.length; i++) {
				int j = i;
				int min = next;
				for (int k = i; k < c.length; k++) {
					if (c[k] < min) {
						min = c[k];
						j = k + 1;
					}
				}
				int[] cc = Arrays.copyOfRange(c, i - 1, j);
				int ij = i - 1;
				for (int kj = cc.length - 1; kj >= 0; kj--) {
					c[ij++] = cc[kj];
				}
				int nextCost = (j - (i - 1));
				totalCost += nextCost;
				if (i < c.length) {
					next = c[i];
				}
			}
			out.println("Case #" + (nextCase + 1) + ": " + totalCost);
		}
	}

	BufferedReader reader;
	StringTokenizer tokenizer;
	PrintWriter writer;

	void run() throws IOException {
		// in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
		in = new BufferedReader(new FileReader(new File("Resolve")));
		solve();
		out.close();
	}

	public static void main(String[] args) throws IOException {
		new Reverse().run();
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
