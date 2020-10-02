package com.nmorenor.dp.problems;

import java.util.LinkedList;

/**
 * Given a binary Tree design an algorithm which creates a linked list of all the nodes
 * at each depth (e.g., if you have a tree with depth D, you'll have D linked Nodes or elments.
 * @author nacho
 *
 */
public class ListOfDepths {
	
	private static class Node {
		
		public final int val;
		public Node left;
		public Node right;
		
		public LinkedList<Node> childNodes = new LinkedList<Node>();
		
		public Node(int val) {
			this.val = val;
		}
		
		@Override
		public String toString() {
			return String.valueOf(val);
		}
	}
	
	private static class Tree {
		public final Node root;
		
		public Tree(Node root) {
			this.root = root;
		}
	}
	
	public static void main(String[] args) {
		int[] input = new int[] { 20, 19, 21, 18, 22, 17, 23, 16, 24, 15, 25 }; // kind of a heap
		Tree tree = new Tree(new Node(input[0]));
		buildTree(input, 0, 1, tree.root, false);
		listOfDepths(tree.root);
		
	}
	
	private static void listOfDepths(Node node) {
		if (node == null ) {
			return;
		}
		listOfDepths(node.left);
		if (node.left != null) {
			node.childNodes.addAll(node.left.childNodes);
		}
		node.childNodes.add(node);
		listOfDepths(node.right);
		if (node.right != null) {
			node.childNodes.addAll(node.right.childNodes);
		}
		System.out.println(node);
		System.out.println(node.childNodes);
	}
	
	private static void buildTree(int[] input, int i, int depth, Node node, boolean postOrder) {
		if (node == null || i >= input.length) {
			return;
		}
		if (i + 1 <= (input.length - 1)) {
			node.left = new Node(input[i + 1]);
		}
		if ((i + 2) <= (input.length - 1)) {
			node.right = new Node(input[i + 2]);
		}
		int nextDepth = depth + 1;
		buildTree(input, postOrder ? (1 << nextDepth) : (1 << nextDepth) - 2, nextDepth, node.left, postOrder);
		if (i > 0) {
			return;
		}
		buildTree(input, (1 << nextDepth), nextDepth, node.right, true);
	}
}
