package com.nmorenor.dp.problems;

/**
 * T1 and T2 are two very large binary trees, with T1 much bigger than T2. Create an algorithm 
 * to determine if T2 is a subtree of T1
 * @author nacho
 *
 */
public class CheckSubtree {
	
	private static class Tree {
		private static class Node {
			private final int val;
			private Node left;
			private Node right;
			
			public Node(int val) {
				this.val = val;
			}
			
			@Override
			public String toString() {
				return String.valueOf(val);
			}
		}
		
		private Node root;
		
		public Tree(int val) {
			root = new Node(val);
		}
	}

	public static void main(String[] args) {
		int [] input = new int[] { 16, 15, 17, 14, 18, 13, 19, 12, 20, 11, 21, 10, 22, 9, 23, 8, 24, 7, 25, 6, 26, 5, 27, 4, 28, 3, 29, 2, 30, 1, 31 };
		Tree one = new Tree(input[0]);
		buildTree(input, 1, one.root);
		
		input = new int[] { 13, 10, 22, 4, 28, 3, 29 };
		Tree other = new Tree(input[0]);
		
		buildTree(input, 1, other.root);
		
		System.out.println(isSubtree(one, one.root, other.root, null));
	}
	
	private static boolean isSubtree(Tree sourceTree, Tree.Node one, Tree.Node other, Tree.Node found) {
		if (found == null && one == null) {
			return false;
		}
		if (found == null && one != null && other != null && one.val == other.val) {
			found = one;
		}
		if (found == null) {
			return isSubtree(sourceTree, one.left, other, found) || isSubtree(sourceTree, one.right, other, found);
		}
		if (found != null && one == null && other == null) {
			return true;
		}
		if (found != null && one == null && other != null) {
			return false;
		}
		if (found != null && other == null && one == null) {
			return false;
		}
		if (found != null && one.val != other.val) {
			return false;
		}
		
		if (found != null && one.left == null && other.left != null) {
			return false;
		}
		if (found != null && other.left == null && one.left != null) {
			return false;
		}
		if (found != null && one.right == null && other.right != null) {
			return false;
		}
		if (found != null && other.right == null && one.right != null) {
			return false;
		}
		return isSubtree(sourceTree, one.left, other.left, found) && isSubtree(sourceTree, one.right, other.right, found);
		
	}
	
	private static void buildTree(int[] input, int index, Tree.Node node) {
		if (node == null) {
			return;
		}
		if (index >= input.length) {
			return;
		}
		if (index <= input.length - 1) {
			node.left = new Tree.Node(input[index]);
		}
		if (index + 1 <= input.length - 1) {
			node.right = new Tree.Node(input[index + 1]);
		}
		buildTree(input, (index * 2) + 1, node.left);
		buildTree(input, ((index + 1) * 2) + 1, node.right);
	}
}
