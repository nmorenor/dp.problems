package com.nmorenor.dp.problems;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * You are given a binary tree in which each node contains an integer value
 * (which might be positive or negative). Design an algorithm to count the
 * number of paths that sum to a given value. The path does not need to start or
 * end at the root or a leaf, but it must go downwards (traveling only from
 * parent nodes to child nodes)
 * 
 * @author nacho
 *
 */
public class PathsWithSum {

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

	public static void main(String[] args) {
		int[] input = new int[] { 16, 15, 17, 14, 18, 13, 19, 12, 20, 11, 21, 10, 22, 9, 23, 8, 24, 7, 25, 6, 26, 5, 27, 4, 28, 3, 29, 2, 30, 1, 31 };
		Tree tree = new Tree(input[0]);
		buildTree(input, 1, tree.root);
		ArrayList<List<Tree.Node>> memo = new ArrayList<List<Tree.Node>>();
		pathsWithSum(tree.root, 29, memo);
		
		System.out.println("Paths with sum: " + memo.size());
		System.out.println();
		System.out.println();
		System.out.println("Paths:");
		for (List<Tree.Node> next : memo) {
			System.out.println(next);
		}
	}
	
	private static List<Tree.Node> pathsWithSum(Tree.Node node, int target, List<List<Tree.Node>> memo) {
		LinkedList<Tree.Node> levelNodes = new LinkedList<Tree.Node>();
		
		List<Tree.Node> leftNodes = new LinkedList<Tree.Node>();
		List<Tree.Node> rightNodes = new LinkedList<Tree.Node>();
		if (node.left != null) {
			leftNodes = pathsWithSum(node.left, target, memo);
			if ((node.val + node.left.val) == target) {
				LinkedList<Tree.Node> vals = new LinkedList<>();
				vals.add(node.left);
				vals.addFirst(node);
				memo.add(vals);
			}
		}
		if (node.right != null) {
			rightNodes = pathsWithSum(node.right, target, memo);
			if ((node.val + node.right.val) == target) {
				LinkedList<Tree.Node> vals = new LinkedList<>();
				vals.add(node.left);
				vals.addFirst(node);
				memo.add(vals);
			}
		}
		int leftSum = Integer.MIN_VALUE;
		int rightSum = Integer.MIN_VALUE;
		if (!leftNodes.isEmpty()) {
			leftSum = leftNodes.stream().map(next -> next.val).reduce((prev, next) -> next + prev).get();
		}
		
		if (!rightNodes.isEmpty()) {
			rightSum = rightNodes.stream().map(next -> next.val).reduce((prev, next) -> next + prev).get();
		}
		
		if (leftNodes.size() > 1 && leftSum == target) {
			memo.add(leftNodes);
		}
		if (rightNodes.size() > 1 && rightSum == target) {
			memo.add(rightNodes);
		}
		if (leftSum + node.val == target) {
			LinkedList<Tree.Node> vals = new LinkedList<>();
			vals.addAll(leftNodes);
			vals.addFirst(node);
			memo.add(vals);
		}
		if (rightSum + node.val == target) {
			LinkedList<Tree.Node> vals = new LinkedList<>();
			vals.addAll(rightNodes);
			vals.addFirst(node);
			memo.add(vals);
		}
		
		if ((leftSum + rightSum + node.val) == target) {
			LinkedList<Tree.Node> vals = new LinkedList<>();
			vals.addAll(leftNodes);
			vals.addFirst(node);
			for (Tree.Node nextNode : rightNodes) {
				vals.addFirst(nextNode);
			}
			memo.add(vals);
		}
		levelNodes.addAll(leftNodes);
		levelNodes.addFirst(node);
		for (Tree.Node nextNode : rightNodes) {
			levelNodes.addFirst(nextNode);
		}
		
		return levelNodes;
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
		if ((index + 1) <= input.length - 1) {
			node.right = new Tree.Node(input[index + 1]);
		}
		buildTree(input, (index * 2) + 1, node.left);
		buildTree(input, ((index + 1) * 2) + 1, node.right);
	}
}
