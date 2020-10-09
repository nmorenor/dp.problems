package com.nmorenor.dp.problems;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Design an algorithm and write code to find the fist common ancestor of two nodes in a binary tree.
 * Avoid sorting additional nodes in a data structure. NOTE: This is not necessarily
 * a binary search tree.
 * 
 * @author nacho
 *
 */
public class FirstCommonAncestor {
	
	private static class Tree {

		public static class Node {
			
			public final int val;
			public Node left;
			public Node right;
			
			@Override
			public int hashCode() {
				final int prime = 31;
				int result = 1;
				result = prime * result + val;
				return result;
			}

			@Override
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				if (obj == null)
					return false;
				if (getClass() != obj.getClass())
					return false;
				Node other = (Node) obj;
				if (val != other.val)
					return false;
				return true;
			}

			public Node(int val) {
				this.val = val;
			}
			
			@Override
			public String toString() {
				return String.valueOf(val);
			}
			
		}
		
		public Node root;
		
		public Tree(int val) {
			this.root = new Node(val);
		}
		
	}

	public static void main(String[] args) {
		int[] input = new int[] { 16, 15, 17, 14, 18, 13, 19, 12, 20, 11, 21, 10, 22, 9, 23, 8, 24, 7, 25, 6, 26, 5, 27, 4, 28, 3, 29, 2, 30, 1, 31 };
		Tree tree = new Tree(input[0]);
		buildTree(input, 1, tree.root);
		ArrayList<Tree.Node> result = new ArrayList<Tree.Node>();
		firstCommonAncestor(null, tree.root, 13, 12, new HashSet<Tree.Node>(), result);
		System.out.println(result.get(2));
	}
	
	private static void firstCommonAncestor(Tree.Node pev, Tree.Node node, int p, int q, Set<Tree.Node> onStack, List<Tree.Node> collected) {
		if (collected.size() >= 2) {
			return;
		}
		if (node.val == p || node.val == q) {
			onStack.add(pev);
			collected.add(node);
			return;
		}
		if (node.left != null) {
			firstCommonAncestor(node, node.left, p, q, onStack, collected);
		}
		if (node.right != null) {
			 firstCommonAncestor(node, node.right, p, q, onStack, collected);
		}
		
		if (collected.size() == 2 && onStack.isEmpty()) {
			collected.add(node);
		}
		onStack.remove(node);
		
	}
	
	private static void buildTree(int[] input, int index, Tree.Node node) {
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
			node.left = new Tree.Node(input[index]);
		}
		if ((index + 1) <= input.length - 1) {
			node.right = new Tree.Node(input[index + 1]);
		}
		buildTree(input, (index * 2) + 1, node.left);
		buildTree(input, ((index + 1) * 2) + 1, node.right);
	}
}
