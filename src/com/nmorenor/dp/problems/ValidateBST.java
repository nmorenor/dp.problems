package com.nmorenor.dp.problems;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Implement a function to check if a binary tree is a binary search tree
 * @author nacho
 *
 */
public class ValidateBST {

	private static class Tree {
		
		public static boolean RED = true;
		public static boolean BLACK = false;
		
		public static class Node {
			
			public final int val;			
			public Node parent;
			public Node left;
			public Node right;
			public boolean color;
			
			public Node(int val, boolean color) {
				this.val = val;
				this.color = color;
			}
			
			@Override
			public String toString() {
				return String.valueOf(val);
			}
			
		}
		
		public Node root;
		
		public Tree(int val) {
			this.root = new Node(val, BLACK);
		}
		
		public void add(int value) {
			root = add(root, value);
			root.color = BLACK;
		}
		
		private Node add(Node node, int val) {
			if (node == null) {
				return new Node(val, RED);
			}
			if (val < node.val) {
				node.left = add(node.left, val);
			} else if (val > node.val) {
				node.right = add(node.right, val);
			}
			if (isRed(node.right) && !isRed(node.left)) {
				node = rotateLeft(node);
			}
			if (isRed(node.left) && isRed(node.left.left)) {
				node = rotateRight(node);
			}
			if (isRed(node.left) && isRed(node.right)) {
				flipColors(node);
			}
			return node;
		}
		
		private boolean isRed(Node node) {
			if (node == null) {
				return false;
			}
			return node.color == RED;
		}
		
		private Node rotateLeft(Node h) {
			Node x = h.right;
			h.right = x.left;
			x.left = h;
			x.parent = h.parent;
			h.parent = x;
			x.color = h.color;
			h.color = RED;
			return x;
		}
		
		private Node rotateRight(Node h) {
			Node x = h.left;
			h.left = x.right;
			x.right = h;
			x.parent = h.parent;
			h.parent = x;
			x.color = h.color;
			h.color = RED;
			return x;
		}
		
		private void flipColors(Node h) {
			h.color = RED;
			h.left.color = BLACK;
			h.right.color = BLACK;
		}
		
		public boolean isBST() {
			if (root.left != null) {
				Queue<Node> bfs = new LinkedList<Node>();
				bfs.add(root.left);
				while (!bfs.isEmpty()) {
					Node next = bfs.poll();
					if (next.val > root.val) {
						return false;
					}
					if (next.left != null) {
						bfs.add(next.left);
					}
					if (next.right != null) {
						bfs.add(next.right);
					}
				}
			}
			if (root.right != null) {
				Queue<Node> bfs = new LinkedList<Node>();
				bfs.add(root.right);
				while (!bfs.isEmpty()) {
					Node next = bfs.poll();
					if (next.val < root.val) {
						return false;
					}
					if (next.left != null) {
						bfs.add(next.left);
					}
					if (next.right != null) {
						bfs.add(next.right);
					}
				}
			}
			return true;
		}
	}
	
	public static void main(String[] args) {
		int[] input = new int[] { 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 18, 15 };
		Tree tree = new Tree(input[0]);
		buildTree(input, 1, tree.root);
		System.out.println(tree.isBST());
		input = new int[] { 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15 };
		tree = new Tree(input[0]);
		for (int i = 1; i < input.length; i++) {
			tree.add(input[i]);
		}
		System.out.println(tree.isBST());
	}
	
	// build tree but not a BST
	private static void buildTree(int[] input, int index, Tree.Node node) {
		if (node == null) {
			return;
		}
		if (index >= input.length) {
			return;
		}
		if (index <= input.length - 1) {
			node.left = new Tree.Node(input[index], false);
		}
		if ((index + 1) <= input.length - 1) {
			node.right = new Tree.Node(input[index + 1], false);
		}
		buildTree(input, (index * 2) + 1, node.left);
		buildTree(input, ((index + 1) * 2) + 1, node.right);
	}
}