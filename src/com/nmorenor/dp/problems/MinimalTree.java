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
		Tree tree = new Tree(minimalTree(input, 0, input.length - 1));
		
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
	
	private static Node minimalTree(int[] input, int i, int j) {
		if (j < i) {
			return null;
		}
		int mid = (j + i) / 2;
		Node node = new Node(input[mid]);
		node.left = minimalTree(input, i, mid - 1);
		node.right = minimalTree(input, mid + 1, j);
		return node;
	}
}
