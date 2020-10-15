package com.nmorenor.dp.problems;

import java.util.LinkedList;

/**
 * A binary search tree was created by traversing through an array from left to right
 * and inserting each element. Given a binary search tree with distinct elements, print all 
 * possible arrays that could have led to this tree.
 * 
 * @author nacho
 *
 */
public class BSTSequences {
	
	private static class Tree {
		private static class Node {
			private int val;
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

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		int[] input = new int[] { 3, 2, 1 };
		Tree tree = new Tree(input[0]);
		buildTree(input, 1, tree.root);
		LinkedList<Tree.Node>[] sequences = new LinkedList[] {new LinkedList<Tree.Node>(), new LinkedList<Tree.Node>()};
		buildSequences(tree.root, sequences);
		for (LinkedList<Tree.Node> next :sequences) {
			System.out.println(next);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void buildSequences(Tree.Node node, LinkedList<Tree.Node>[] sequences) {
		if (node == null) {
			return;
		}
		LinkedList<Tree.Node>[] left = new LinkedList[] {new LinkedList<Tree.Node>(), new LinkedList<Tree.Node>()};
		LinkedList<Tree.Node>[] right = new LinkedList[] {new LinkedList<Tree.Node>(), new LinkedList<Tree.Node>()};
		if (node.left != null) {
			buildSequences(node.left, left);
		}
		if (node.right != null) {
			buildSequences(node.right, right);
		}
		left[0].addAll(right[0]);
		right[0].addAll(left[1]);
		
		sequences[0].addFirst(node);
		sequences[0].addAll(left[0]);
		sequences[1].addFirst(node);
		sequences[1].addAll(right[0]);
	}
	
	private static void buildTree(int[] input, int index, Tree.Node node) {
		if (node == null) {
			return;
		}
		if (index >= input.length) {
			return;
		}
		if (index <= input.length) {
			node.left = new Tree.Node(input[index]);
		}
		if (index + 1 <= input.length) {
			node.right = new Tree.Node(input[index + 1]);
		}
		buildTree(input, (index * 2) + 1, node.left);
		buildTree(input, ((index + 1) * 2) + 1, node.right);
	}
}
