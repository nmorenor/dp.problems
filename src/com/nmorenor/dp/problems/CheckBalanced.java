package com.nmorenor.dp.problems;

public class CheckBalanced {
	
	private static class Node {
		
		public final int val;
		public Node left;
		public Node right;
		
		public Node(int val) {
			this.val = val;
		}
		
		@Override
		public String toString() {
			return String.valueOf(val);
		}
		
		public int countLeft() {
			if (left == null) {
				return 0;
			}
			return 1 + left.countLeft();
		}
		
		public int countRight() {
			if (right == null) {
				return 0;
			}
			return 1 + right.countRight();
		}
	}
	
	private static class Tree {
		public final Node root;
		
		public Tree(Node root) {
			this.root = root;
		}
		
		public boolean isBalanced() {
			if (root.left == null && root.right == null) {
				return true;
			}
			if (root.left == null || root.right == null) {
				return false;
			}
			return root.left.countLeft() == root.right.countRight();
		}
	}

	public static void main(String[] args) {
		int[] input = new int[] { 5, 4, 6, 3, 7, 2, 8, 1, 9 };
		Tree tree = new Tree(new Node(input[0]));
		buildTree(input, 1, tree.root);
		System.out.println(tree.isBalanced());
		input = new int[] { 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15 };
		tree = new Tree(new Node(input[0]));
		buildTree(input, 1, tree.root);
		System.out.println(tree.isBalanced());
		input = new int[] { 16, 15, 17, 14, 18, 13, 19, 12, 20, 11, 21, 10, 22, 9, 23, 8, 24, 7, 25, 6, 26, 5, 27, 4, 28, 3, 29, 2, 30, 1, 31 };
		tree = new Tree(new Node(input[0]));
		buildTree(input, 1, tree.root);
		System.out.println(tree.isBalanced());
	}
	
	private static void buildTree(int[] input, int index, Node node) {
		if (node == null) {
			return;
		}
		if (index >= input.length) {
			return;
		}
		if (index < 0) {
			return;
		}
		if (index <= input.length - 1) {
			node.left = new Node(input[index]);
		}
		if ((index + 1) <= input.length - 1) {
			node.right = new Node(input[index + 1]);
		}
		buildTree(input, (index * 2) + 1, node.left);
		buildTree(input, ((index + 1) * 2) + 1, node.right);
	}
}
