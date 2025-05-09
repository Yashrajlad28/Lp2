package tools;

import java.util.ArrayList;
import java.util.Scanner;

public class NQueens {
	ArrayList<String> board = new ArrayList<>();
	ArrayList<ArrayList<String>> ans = new ArrayList<>();
	
	public void getN(int n) {
		for(int i=0; i<n; i++) {
			// StringBuilder is used for having mutable strings
			StringBuilder sb = new StringBuilder();
			for(int j=0; j<n; j++) {
				sb.append(".");
			}
			String str = sb.toString();
			board.add(str);
		}
	}
	
	public boolean isSafe(int row, int col, int n) {
		int rowCopy = row;
		int colCopy = col;
		
		/*
		
		  You need to look only in these 3 directions
		  				  \
		              ---- Q
		                  /
		
		*/
		
		// dir \
		//      \
		//       Q
		while(row>=0 && col>=0) {
			if(board.get(row).charAt(col)=='Q') return false;
			row--;
			col--;
		}
		
		// dir 
		// ------Q
		row = rowCopy;
		col = colCopy;
		while(col>=0) {
			if(board.get(row).charAt(col)=='Q') return false;
			col--;
		}
		
		// dir
		//      Q
		//     /
		//    /
		row = rowCopy;
		col = colCopy;
		while(row<n && col>=0) {
			if(board.get(row).charAt(col)=='Q') return false;
			col--;
			row++;
		}
		
		return true;
	}
	
	public void solve(int col, int n) {
		if(col == n) {
			// Create a deep copy of the board before adding to ans
			ans.add(new ArrayList<>(board));
			return;
		}

		for(int row = 0; row < n; row++) {
			if(isSafe(row, col, n)) {
				StringBuilder sb = new StringBuilder(board.get(row));
				sb.setCharAt(col, 'Q');
				board.set(row, sb.toString()); // Update board
				solve(col + 1, n);
				sb.setCharAt(col, '.');
				board.set(row, sb.toString()); // Backtrack
			}
		}
	}
	
	public void solveBB(int col, int[] leftRow, int[] upperDiagonal, 
			int[] lowerDiagonal, int n) {
		if(col == n) {
			ans.add(new ArrayList<>(board));
			return;
		}
		for(int row=0; row<n; row++) {
			if(leftRow[row] == 0 && lowerDiagonal[row+col] == 0
					&& upperDiagonal[n-1 + col - row] == 0) {
				
				StringBuilder sb = new StringBuilder(board.get(row));
				sb.setCharAt(col, 'Q');
				board.set(row, sb.toString());
				leftRow[row] = 1;
				lowerDiagonal[row+col] = 1;
				upperDiagonal[n-1 + col - row] = 1;
				solveBB(col + 1, leftRow, upperDiagonal, lowerDiagonal, n);
				sb.setCharAt(col, '.');
				board.set(row, sb.toString());
				leftRow[row] = 0;
				lowerDiagonal[row+col] = 0;
				upperDiagonal[n-1 + col - row] = 0;
			}
		}
	}
	
	public void showAns() {
		if(ans.isEmpty()) {
			System.out.println("No solution");
			return;
		}
		System.out.println("Number of solutions: "+ans.size());
		for(ArrayList<String>board : ans) {
			for(String s: board) {
				System.out.println(s);
			}
			System.out.println();
		}
		board.clear();
		ans.clear();
	}
	
	public static void main(String args[]) {
		NQueens nq = new NQueens();
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter number of queens: ");
		int n = sc.nextInt();
		nq.getN(n);
		nq.solve(0,n); // start from 0th column
		nq.showAns();
		nq.getN(n);
		int[] leftRow = new int[n];
		int[] upperDiagonal = new int[2*n - 1];
		int[] lowerDiagonal = new int[2*n - 1];
		nq.solveBB(0, leftRow, upperDiagonal, lowerDiagonal, n); 
		nq.showAns();
	}
}
