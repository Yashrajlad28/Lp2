package tools;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class EightPuzzle {
	
	int[][] start = new int[n][n];
	int[][] goal = new int[n][n];
	static final int n = 3;
	
	
	// for taking input of start and final state
	private void getStartAndGoal() {
		
		System.out.println("Enter Start State: ");
		Scanner sc = new Scanner(System.in);
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				start[i][j] = sc.nextInt();
			}
		}
		
		System.out.println("Enter Goal State: ");
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				goal[i][j] = sc.nextInt();
			}
		}
	}
	
	// calculating h
	private int mismatchTiles(int[][] board) {
		int mismatch = 0;
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(board[i][j]!=0 && board[i][j] != goal[i][j]) {
					mismatch++;
				}
			}
		}
		return mismatch;
	}
	
	// check if current state is goal state
	private boolean isGoal(int[][] board) {
		return Arrays.deepEquals(board, goal);
	}
	
	// to get blank position and then return its index as array
	private int[] getBlankPosition(int[][] board) {
		int[] position = new int[2];
		position[0] = -1;
		position[1] = -1;
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				if(board[i][j] == 0) {
					position[0] = i;
					position[1] = j;
				}
			}
		}
		return position;
	}
	
	public String boardToString(int[][] board) {
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				sb.append(board[i][j]);
			}
		}
		return sb.toString();
	}
	
	public int[][] copyBoard(int[][] board) {
		int[][] newBoard = new int[n][n];
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				newBoard[i][j] = board[i][j];
			}
		}
		return newBoard;
	}
	
	// path is of the blank space
	public void solve() {
		getStartAndGoal();
		PriorityQueue<State> pq = 
				new PriorityQueue<>(Comparator.comparingInt(i->i.f()));
		Set<String> visited = new HashSet<>();

        pq.add(new State(start, 0, mismatchTiles(start), ""));
        visited.add(boardToString(start));

        int[] dx = {0, -1, 0, 1};
        int[] dy = {-1, 0, 1, 0};
        char[] dir = {'L', 'U', 'R', 'D'};

        while (!pq.isEmpty()) {
            State curr = pq.poll();
            if (isGoal(curr.board)) {
                System.out.println("Solved in " + curr.g + " steps.");
                System.out.println("Path: " + curr.path);
                return;
            }

            int[] blank = getBlankPosition(curr.board);
            int x = blank[0], y = blank[1];

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i], ny = y + dy[i];
                if (nx >= 0 && ny >= 0 && nx < n && ny < n) {
                    int[][] newBoard = copyBoard(curr.board);
                    int temp = newBoard[x][y];
                    newBoard[x][y] = newBoard[nx][ny];
                    newBoard[nx][ny] = temp;

                    String stateStr = boardToString(newBoard);
                    if (!visited.contains(stateStr)) {
                        visited.add(stateStr);
                        pq.add(new State(newBoard, curr.g + 1, mismatchTiles(newBoard), curr.path + dir[i]));
                    }
                }
            }
        }
        System.out.println("No solution found.");
	}
	
	public int[][] getStartState(){
		return this.start;
	}
	
	public static void main(String args[]) {
		EightPuzzle ep = new EightPuzzle();
		ep.solve();
	}
	
}

