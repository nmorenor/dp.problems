package com.nmorenor.dp.problems;

/**
 * Consider a simple data structure called BiNode, which has pointers to two
 * other nodes.
 * 
 * public class BiNode { public BiNode node1, node2; public int data; }
 * 
 * The data structure BiNode could be used to represent both binary tree (where
 * node1 is is the left node and node2 is the right node) or a doubly linked
 * list (where node1 is the previous node and node2 is the next node). Implement
 * a method to convert a binary search tree (implemented with BiNode) into a
 * doubly linked list. The values should be kept in order ad the operation
 * should be performed in place (this is, on the original data structure)
 * 
 * @author nacho
 *
 */
public class BiNodes {

	public static void main(String[] args) {
		int input[] = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
		BiNode biNode = buildBiNode(input);
		asLinkedList(biNode);
	}
	
	private static void asLinkedList(BiNode node) {
		BiNode linkedList = toLinkedList(node);
		while (linkedList.node1 != null) {
			linkedList = linkedList.node1;
		}
		while (linkedList.node2 != null) {
			System.out.println(linkedList);
			linkedList = linkedList.node2;
		}
		System.out.println(linkedList);
	}
	
	private static BiNode toLinkedList(BiNode node) {
		if (node == null) {
			return null;
		}
		BiNode nextNode = new BiNode(node.data);
		if (node.node1 != null) {
			BiNode nextNode1 = toLinkedList(node.node1);
			if (nextNode1.node2 == null) {
				nextNode.node1 = nextNode1;
			} else {
				BiNode right = nextNode1.node2;
				while (right.node2 != null) {
					right = right.node2;
				}
				nextNode.node1 = right;
			}
			if (nextNode.node1.node2 == null) {
				nextNode.node1.node2 = nextNode;
			}
		}
		if (node.node2 != null) {
			BiNode nextNode2 = toLinkedList(node.node2);
			if (nextNode2.node1 == null) {
				nextNode.node2 = nextNode2;
			} else {
				BiNode left = nextNode2.node1;
				while (left.node1 != null) {
					left = left.node1;
				}
				nextNode.node2 = left;
			}
			
			if (nextNode.node2.node1 == null) {
				nextNode.node2.node1 = nextNode;
			}
		}
		return nextNode;
		
	}
	
	private static BiNode buildBiNode(int[] input) {
		BST bst = new BST(input[0]);
		for (int i = 1; i < input.length; i++) {
			bst.add(input[i]);
		}
		return bst.root;
	}

	public static class BiNode {

		public static boolean RED = true;
		public static boolean BLACK = false;

		public BiNode node1;
		public BiNode node2;
		public final int data;

		private boolean color;

		public BiNode(int data) {
			this.data = data;
			this.color = BiNode.RED;
		}

		@Override
		public String toString() {
			return String.valueOf(data);
		}
	}

	public static class BST {
		private BiNode root;

		public BST(int val) {
			this.root = new BiNode(val);
			this.root.color = BiNode.BLACK;
		}
		
		public void add(int value) {
			root = add(root, value);
			root.color = BiNode.BLACK;
		}
		
		private BiNode add(BiNode node, int val) {
			if (node == null) {
				return new BiNode(val);
			}
			int cmp = Integer.compare(node.data, val);
			if (cmp > 0) {
				node.node1 = add(node.node1, val);
			} else {
				node.node2 = add(node.node2, val);
			}
			
			if (isRed(node.node2) && !isRed(node.node1)) {
				node = rotateLeft(node);
			}
			if (isRed(node.node1) && isRed(node.node1.node1)) {
				node = rotateRight(node);
			}
			if (isRed(node.node1) && isRed(node.node2)) {
				flipColors(node);
			}
			return node;
		}
		
		private boolean isRed(BiNode h) {
			if (h == null) {
				return false;
			}
			return h.color == BiNode.RED;
		}
		
		private BiNode rotateLeft(BiNode h) {
			BiNode x = h.node2;
			h.node2 = x.node1;
			x.node1 = h;
			x.color = h.color;
			h.color = BiNode.RED;
			return x;
		}
		
		private BiNode rotateRight(BiNode h) {
			BiNode x = h.node1;
			h.node1 = x.node2;
			x.node2 = h;
			x.color = x.color;
			h.color = BiNode.RED;
			return x;
		}
		
		private void flipColors(BiNode h) {
			h.color = BiNode.RED;
			h.node1.color = BiNode.BLACK;
			h.node2.color = BiNode.BLACK;
		}

	}
}
