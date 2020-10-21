package com.nmorenor.dp.problems;

/**
 * Write a function that adds two numbers. You should not use + or any arithmetic operators
 * 
 * @author nacho
 *
 */
public class AddWithoutPlus {

	public static void main(String[] args) {
		System.out.println(add(15, 25));
	}
	
	private static int add(int a, int b) {
		if (b == 0) {
			return a;
		}
		int sum = a ^ b; // add without carrying
		int carry = (a & b) << 1; // carry, but don't add
		return add(sum, carry);
	}
}
