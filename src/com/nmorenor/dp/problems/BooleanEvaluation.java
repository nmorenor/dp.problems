package com.nmorenor.dp.problems;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 * Given a boolean expression consisting of symbols 0 (false), 1 (true), & (AND),
 * | (OR), and ^ (XOR), and a desired boolean result value result, implement a function to
 * count the number of ways of parenthesizing the expression such that it evaluates to result.
 * The expression should be fully parenthesized (e.g., (0)^(1) but not extraneously 
 * (e.g., (((0))) ^ (((1)))
 * 
 * EXAMPLE
 * countEval("1^0|0|1", false)
 * countEval("0&0&0&1^1|0", true)
 * 
 * [DP III: Parenthesization, Edit Distance, Knapsack]
 * https://www.youtube.com/watch?v=ocZMDMZwhCY
 * @author nacho
 *
 */
public class BooleanEvaluation {

	public static void main(String[] args) {
		String input = "1^0|0|1";
		Set<String> evals = countEval(input.length() - 1, input.length(), input);
		evals = evaluateResult(evals, false);
		for (String next : evals) {
			System.out.println(next);
		}
	}
	
	private static enum Operation {
		AND('&'), OR('|'), XOR('^');
		
		private final char val;

		Operation(char val) {
			this.val = val;
		}
		
		boolean evaluate(boolean one, boolean other) throws Exception {
			switch (this) {
				case AND:
					return one & other;
				case OR:
					return one | other;
				case XOR:
					return one ^ other;
			}
			throw new Exception("bad input");
		}
		
		static Operation getFromVal(char val) {
			switch (val) {
				case '&':
					return Operation.AND;
				case '|':
					return Operation.OR;
				case '^':
					return Operation.XOR;
			}
			return null;
		}

		@SuppressWarnings("unused")
		public char getVal() {
			return val;
		}
	}
	
	private static Set<String> countEval(int i, int j, String input) {
		HashSet<String> result = new HashSet<String>();
		if (input.length() == 1) {
			result.add("(" + input + ")");
			return result;
		}
		
		if (i < 0) {
			return result;
		}
		char current = input.charAt(i);
		Set<String> before = countEval(i - 1, j, input);
		if (isOperator(current) || i >= j || (i > 1 && j == input.length())) {
			return before;
		}
		
		
		if (isBool(current) && (i + 1) < input.length() - 1) {
			String val = "(" + String.valueOf(input.charAt(i)) + ")";
			char operator = input.charAt(i + 1);
			String suffix = input.substring(i + 2);
			Set<String> rightSide = countEval(suffix.length() - 1, i + 2, suffix);
			for (String nextRight : rightSide) {
				if (isValid(val + String.valueOf(operator) + nextRight)) {
					result.add(val + String.valueOf(operator) + nextRight);
				}
				if (isValid("(" + val + String.valueOf(operator) + nextRight + ")")) {
					result.add("(" + val + String.valueOf(operator) + nextRight + ")");
				}
			}
			
		}
		
		return result;
	}
	
	private static Set<String> evaluateResult(Set<String> evals, boolean targetValue) {
		Set<String> result = new HashSet<String>();
		Map<String, Boolean> memo = new HashMap<String, Boolean>();
		for (String nextTarget : evals) {
			if (evaluate(nextTarget, memo) == targetValue) {
				result.add(nextTarget);
			}
		}
		return result;
	}

	private static boolean evaluate(String nextTarget, Map<String, Boolean> memo) {
		LinkedList<Boolean> toEval = new LinkedList<Boolean>();
		evaluate(nextTarget, 0, nextTarget.length() - 1, toEval, memo);
		return toEval.isEmpty() || toEval.size() != 1 ? false : toEval.getFirst();
	}
	
	private static void evaluate(String nextTarget, int i, int j, LinkedList<Boolean> toEval, Map<String, Boolean> memo) {
		if (nextTarget.isEmpty()) {
			return;
		}
		if (memo.containsKey(nextTarget)) {
			toEval.addFirst(memo.get(nextTarget));
			return;
		}
		if (nextTarget.equals("(1)")) {
			memo.put(nextTarget, true);
			toEval.addFirst(memo.get(nextTarget));
			return;
		}
		if (nextTarget.equals("(0)")) {
			memo.put(nextTarget, false);
			toEval.addFirst(memo.get(nextTarget));
			return;
		}
		if (i >= j) {
			return;
		}
		
		int openParens = 0;
		Operation operation = null;
		for (int k = j; k >= 0; k--) {
			char next = nextTarget.charAt(k);
			if (next == ')') {
				openParens++;
			}
			if (next == '(') {
				openParens--;
			}
			if (openParens == 0) {
				if (next ==  '(') {
					if ((k - 1) < 0 && nextTarget.charAt(k + 1) == '(') {
						// we are at the beginning of expression but next is a closing parenthesis
						String inner = nextTarget.substring(k + 1, nextTarget.length() - 1);
						evaluate(inner, 0, inner.length() - 1, toEval, memo);
					} else if (nextTarget.charAt(k + 1) == '(') {
						// next char is an opening parenthesis evaluate the middle
						operation = Operation.getFromVal(nextTarget.charAt(k - 1));
						String right = nextTarget.substring(k + 1, nextTarget.length() - 1);
						evaluate(right, 0, right.length() - 1, toEval, memo);
						String left = nextTarget.substring(i, k - 1);
						evaluate(left, 0, left.length() - 1, toEval, memo);
						evaluateOperation(nextTarget, toEval, memo, operation);
						return;
					} else if (k > i) {
						operation = Operation.getFromVal(nextTarget.charAt(k - 1));
						String right = nextTarget.substring(k, nextTarget.length());
						evaluate(right, 0, right.length() - 1, toEval, memo);
						String left = nextTarget.substring(i, k - 1);
						evaluate(left, 0, left.length() - 1, toEval, memo);
						evaluateOperation(nextTarget, toEval, memo, operation);
						return;
					}
				}
				
			}
		}
	}

	private static void evaluateOperation(String nextTarget, LinkedList<Boolean> toEval, Map<String, Boolean> memo,
			Operation operation) {
		if (operation != null) {
			try {
				boolean evaluated = operation.evaluate(toEval.pollFirst(), toEval.pollFirst());
				memo.put(nextTarget, evaluated);
				toEval.addFirst(evaluated);
			} catch (Exception e) {
				toEval.clear();
				e.printStackTrace();
				return;
			}
		}
	}

	// probably is not required, but it was fun :|
	private static boolean isValid(String target) {
		int closingParens = 0;
		for (int i = target.length() - 1; i >= 0; i--) {
			char next = target.charAt(i);
			if (i == target.length() - 1) {
				if ('(' == next || isBool(next)){ // can't start from right side ( or bool val
					return false;
				}
				if (!isOperator(next)) {
					closingParens++;
				}
				continue;
			}
			if (')' == next) {
				closingParens++;
				
			} else if (isOperator(next)) { // can't be (| or |)
				if ((i > 0 && target.charAt(i - 1) != ')') || target.charAt(i + 1) != '(') {
					return false;
				}
			} else if (isBool(next)) { // can't be ((0))
				if ((i + 1) <= target.length() - 1 && (i + 2) <= target.length() - 1 && (i - 1) >= 0 && (i - 2) >= 0) {
					if (target.charAt(i- 1) == '(' && target.charAt(i - 2) == '(' && target.charAt(i + 1) == ')' && target.charAt(i + 2) == ')') {
						return false;
					}
				}
			} else if ('(' == next) {
				closingParens--;
			}
		}
		return closingParens == 0;
	}
	
	private static boolean isBool(char val) {
		return val == '1' || val == '0';
	}
	
	private static boolean isOperator(char val) {
		return val == '|' || val == '^' || val == '&';
	}
	
}
