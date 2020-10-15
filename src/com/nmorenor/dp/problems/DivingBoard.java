package com.nmorenor.dp.problems;

import java.util.Arrays;

/**
 * You are building a diving board by placing a bunch of planks of wood end-to-end. There are 
 * two types of planks, one of length shorter and one of length longer. You must use exactly 
 * K planks of wood. Write a method to generate all possible lengths for the diving board
 * 
 * @author nacho
 *
 */
public class DivingBoard {

	public static void main(String[] args) {
		int shorter = 1;
		int larger = 2;
		int [] planks = new int[] { shorter, larger };
		int k = 5;
		int [][] memoleft = new int[k + 1][];
		int [][] memoright = new int[k + 1][];
		buildDivingBoard(planks, k, k, memoleft, memoright);
		
		StringBuilder builder = new StringBuilder();
		for (int[] next : memoleft) {
			boolean first = true;
			for (int nextLeft : next) {
				if (!first) {
					builder.append(" + ");
				}
				builder.append(nextLeft);
				first = false;
			}
			builder.append(" = ");
			builder.append(String.valueOf(Arrays.stream(next).reduce((prev, nextval) -> prev + nextval).getAsInt()));
			builder.append("\n");
		}
		
		for (int[] next : memoright) {
			boolean first = true;
			for (int nextRight : next) {
				if (!first) {
					builder.append(" + ");
				}
				builder.append(nextRight);
				first = false;
			}
			builder.append(" = ");
			builder.append(String.valueOf(Arrays.stream(next).reduce((prev, nextval) -> prev + nextval).getAsInt()));
			builder.append("\n");
		}
		System.out.println(builder.toString());
	}
	
	private static void buildDivingBoard(int[] planks, int k, int i, int[][] shorts, int[][] larges) {
		if (i < 0 || i > k ) {
			return;
		}
		if (i == 0) {
			shorts[0] = new int[k];
			Arrays.fill(shorts[0], planks[1]);
			larges[0] = new int[k];
			Arrays.fill(larges[0], planks[0]);
			return;
		}
		buildDivingBoard(planks, k, i - 1, shorts, larges);
		shorts[i] = Arrays.copyOf(shorts[i - 1], k);
		larges[i] = Arrays.copyOf(larges[i - 1], k);
		
		shorts[i][i - 1] = planks[0];
		larges[i][i - 1] = planks[1];
	}
}
