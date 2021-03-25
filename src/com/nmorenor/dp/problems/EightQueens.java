package com.nmorenor.dp.problems;

/**
 * Write an algorithm to print all ways of arranging eight queens on an 8x8 chess board
 * so that none of them share the same row column, or diagonal. In this case "diagonal" means all
 * diagonals, not just the two that bisect the board
 * @author nacho
 *
 */
public class EightQueens {

	public static void main(String[] args) {
		nQueens(8);
	}
	
	public static void nQueens(int n){
        int[] board = new int[n];
        if(!helper(board,0)){
            System.out.println("No solution");
        }
    }
	
	public static boolean helper(int[] board,int i){
        if(i == board.length){
            for(int row : board){
                for(int c=0;c<board.length;c++){
                    if(c == row){
                        System.out.print(" O ");
                    }else{
                        System.out.print(" X ");
                    }
                }
                System.out.println("");
            }
            return true;
        }
        for(int c=0;c<board.length;c++){
            boolean flag = false;
            for(int r=0;r<i;r++){
                if(board[r] == c || Math.abs(board[r]-c) == (i-r)){
                    flag = true;
                    break;
                }
            }
            if(flag){
                continue;
            }
            board[i]=c;
            if(helper(board,i+1)){
                return true;
            }
        }
        return false;
    }
}
