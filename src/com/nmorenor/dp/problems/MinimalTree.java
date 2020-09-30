package com.nmorenor.dp.problems;

/**
 * Given a sorted (increasing order) array with unique integer elements, write an 
 * algorithm to create a binary search tree with minimal height.
 * @author nacho
 *
 */
public class MinimalTree {

	public static void main(String[] args) {
		int [] input = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 ,10};
		int mid = (input.length - 1) / 2;
		Tree tree = new Tree(new Node(input[mid]));
		minimalTree(input, mid - 1, tree.root);
		minimalTree(input, mid + 1, tree.root);
		
		System.out.println("Root: " + tree.root.val);
		System.out.println("Left size: " + tree.sizeLeft());
		System.out.println("Right size: " + tree.sizeRight());
	}
	
	private static class Node {
		
		public final int val;
		public Node left;
		public Node right;
		
		public Node(int val) {
			this.val = val;
		}
		
		public int sizeLeft() {
			if (left == null) {
				return 0;
			}
			return this.left.sizeLeft() + 1;
		}
		
		public int sizeRight() {
			if (right == null) {
				return 0;
			}
			return this.right.sizeRight() + 1;
		}
	}
	
	private static class Tree {
		public final Node root;
		
		public Tree(Node root) {
			this.root = root;
		}
		
		public int sizeLeft() {
			if (root == null) {
				return 0;
			}
			return this.root.sizeLeft();
		}
		
		public int sizeRight() {
			if (root == null) {
				return 0;
			}
			return this.root.sizeRight();
		}
	}
	
	private static void minimalTree(int[] input, int i, Node node) {
		if (i >= 0 && i <= input.length - 1) {
			if (input[i] < node.val) {
				if (node.left == null) {
					node.left = new Node(input[i]);
				}
				minimalTree(input, i - 1, node.left);
			}
			if (input[i] > node.val) {
				if (node.right == null) {
					node.right = new Node(input[i]);
				}
				minimalTree(input, i + 1, node.right);
			}
		}
	}
}
