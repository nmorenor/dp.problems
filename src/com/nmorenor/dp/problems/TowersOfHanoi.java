package com.nmorenor.dp.problems;

import java.util.Arrays;
import java.util.LinkedList;

/***
 * In the classic problem of the Towers of Hanoi, you have 3 towers and N disks
 * of different sizes which can slide onto any otower. The puzzle starts with
 * disks sorted in ascending order of size from top to bottom (i.e., each disk
 * sits on top of an even larger one) You have the following constraints:
 * 
 * (1) Only one disk can be moved at a time (2) A disk is slid off top of no one
 * tower onto another tower. (3) A disk cannot be placed on top of a smaller
 * disk.
 * 
 * Write a program to move the disks from the first tower to the last using
 * stacks.
 * 
 * @author nacho
 *
 */
public class TowersOfHanoi {

	public static void main(String[] args) {
		TowersOfHanoiImpl towers = new TowersOfHanoiImpl(new int[] { 2, 4, 6, 8, 10 });
		for (Tower next : towers.towers) {
			System.out.println(next);
		}
	}

	private static class Tower {
		private LinkedList<Integer> stack = new LinkedList<Integer>();
		
		public Tower() {
			
		}
		
		public Tower(int[] disks) {
			Arrays.stream(disks).forEach(next -> stack.add(next));
		}

		public void add(int a) {
			if (!this.stack.isEmpty() && this.stack.peek() <= a) {
				System.out.println("Can't insert " + a);
			}
			this.stack.addFirst(a);
		}
		
		public void moveToTop(Tower t) {
			t.add(stack.pop());
		}

		@Override
		public String toString() {
			return stack.toString();
		}
		
		public void moveDisks(int quantity, Tower destination, Tower buffer) {
			if (quantity <= 0) {
				return;
			}
			moveDisks(quantity - 1, buffer, destination);
			moveToTop(destination);
			buffer.moveDisks(quantity - 1, destination, this);
		}
	}

	private static class TowersOfHanoiImpl {

		private Tower[] towers;

		public TowersOfHanoiImpl(int[] disks) {
			Tower source = new Tower(disks);
			Tower destination = new Tower();
			Tower buffer = new Tower();
			
			towers = new Tower[] { source, buffer, destination };
			source.moveDisks(disks.length, destination, buffer);
		}

		
	}

}
