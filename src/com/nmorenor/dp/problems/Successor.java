package com.nmorenor.dp.problems;

/**
 * Write an algorithm to find the "next" node (i.e., in-order successor) of a given node in a
 * binary search tree. You may assume that each node has a link to its parent
 * @author nacho
 *
 */
public class Successor {

	
	
	private static class Tree {
		
		private static final boolean RED = true;
		private static final boolean BLACK = false;
		
		static class Node {
			
			private final int val;
			private Node left;
			private Node right;
			private Node parent;
			private boolean color;
			
			public Node(int val) {
				this.val = val;
				this.color = RED;
			}
			
			public String toString() {
				return String.valueOf(val);
			}
		}
		
		private Node root;
		
		public Tree(int value) {
			this.root = new Node(value);
			this.root.color = BLACK;
		}
		
		public void add(int value) {
			root = add(root, value);
			root.color = BLACK;
		}
		
		private Node add(Node node, int val) {
			if (node == null) {
				Node result = new Node(val);
				return result;
			}
			int cmp = Integer.compare(node.val, val);
			if (cmp > 0) {
				node.left = add(node.left, val);
			} else if (cmp < 0) {
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
			if (node.left != null) {
				node.left.parent = node;
			}
			if (node.right != null) {
				node.right.parent = node;
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
			x.color = h.color;
			h.color = RED;
			return x;	
		}
		
		private Node rotateRight(Node h) {
			Node x = h.left;
			h.left = x.right;
			x.right = h;
			x.color = h.color;
			h.color = RED;
			return x;
		}
		
		private void flipColors(Node h) {
			h.color = RED;
			h.left.color = BLACK;
			h.right.color = BLACK;
		}
		
		public int ceil(int value) {
			Node found = find(root, value);
			if (found == null) {
				return Integer.MIN_VALUE;
			}
			if (found.right != null) {
				if (found.right.left != null) {
					Node next = found.right.left;
					while (next.left != null && next.left.val > value) {
						next = next.left;
					}
					if (next.val > value) {
						return next.val;
					}
					
					return next.right.val;
				}
				return found.right.val;
			} else if (found.right == null && found.parent.val < value && found.parent.right != null && found.parent.right.val != value) { 
				return found.parent.right.val;
			} else if (found.parent == null) {
				return found.val;
			} else if (found.right == null && found.parent != null) {
				if (found.parent.val > value) {
					return found.parent.val;
				}
				while (found.parent.parent != null && found.parent.parent.val < value) {
					found = found.parent;
				}
				if (found.parent.parent != null) {
					return found.parent.parent.val;
				}
			}
			
			
			return Integer.MIN_VALUE;
		}
		
		private Node find(Node source, int value) {
			if (source == null) {
				return null;
			}
			if (source.val == value) {
				return source;
			}
			int cmp = Integer.compare(source.val, value);
			if (cmp > 0) {
				return find(source.left, value);
			} else {
				return find(source.right, value);
			}
		}
	}
	
	public static void main(String[] args) {
		int[] input = new int[] { 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15 };
		Tree tree = new Tree(input[0]);
		for (int i = 0; i < input.length; i++) {
			tree.add(input[i]);
		}
		int nextSucessor = tree.ceil(3);
		while (nextSucessor > Integer.MIN_VALUE) {
			System.out.println(nextSucessor);
			nextSucessor = tree.ceil(nextSucessor);
		}
	}
}
