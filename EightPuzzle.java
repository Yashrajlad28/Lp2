import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.HashSet;
import java.util.Comparator;
import java.util.Scanner;

class State{
    int[][] board;
    int g;
    int h;
    String path;

    public State(int[][] board, int g, int h, String path){
        this.board = board;
        this.g = g;
        this.h = h;
        this.path = path;
    }

    public int f(){
        return g+h;
    }
}

public class EightPuzzle{
    static final int n = 3;
    int[][] start = new int[n][n];
    int[][] goal = new int[n][n];

    public void getStates(){
        System.out.println("Enter Start State: ");
        Scanner sc = new Scanner(System.in);
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                start[i][j] = sc.nextInt();
            }
        }
        System.out.println("Enter Goal State: ");
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                goal[i][j] = sc.nextInt();
            }
        }
    }

    public boolean isGoal(int[][] board){
        return Arrays.deepEquals(board, goal);
    }

    public int calcH(int[][] board){
        int mismatch = 0;
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(board[i][j]!=0 && board[i][j] != goal[i][j]){
                    mismatch++;
                }
            }
        }
        return mismatch;
    }

    public int[] getZeroCoordinates(int[][] board){
        int[] coor = new int[2];
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(board[i][j] == 0){ // CHECK I J 
                    coor[0] = i;
                    coor[1] = j;
                }
            }       
        }
        return coor;
    }

    public int[][] copyBoard(int[][] board){
        int[][] newBoard = new int[n][n];
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                newBoard[i][j] = board[i][j];
            }
        }
        return newBoard;
    }

    public String boardToString(int[][] board){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                sb.append(board[i][j]);
            }
        }
        return sb.toString();
    }

    // start writing this method first
    // then as helper methods arrive then write them
    public void solve(){
        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.f()));
        Set<String> visited = new HashSet<>();
        int[] dx = {0,-1,0,1};
        int[] dy = {-1, 0, 1,0};
        char[] dir = {'L', 'U', 'R', 'D'};

        pq.add(new State(start, 0, calcH(start), ""));
        visited.add(boardToString(start));

        while(!pq.isEmpty()){
            State curr = pq.poll();
            if(isGoal(curr.board)){
                System.out.println("Puzzle solved in "+curr.g+" steps.");
                System.out.println("Path of 0: "+curr.path);
                return;
            }
            int[] coor = getZeroCoordinates(curr.board);
            int x = coor[0];
            int y = coor[1];
            for(int i=0; i<4; i++){
                int nx = x+dx[i];
                int ny = y+dy[i];
                int[][] newBoard = copyBoard(curr.board); // NEW BOARD DECLARE OUTSIDE OF IF

                if(nx>=0 && ny>=0 && nx<n && ny<n){
                    int temp = newBoard[x][y];
                    newBoard[x][y] = newBoard[nx][ny];
                    newBoard[nx][ny] = temp;
                    String strState = boardToString(newBoard);
                    if(!visited.contains(strState)){
                        visited.add(strState);
                        pq.add(new State(newBoard, curr.g+1, calcH(newBoard), curr.path+dir[i]));
                    }
                }
            }
        }
    }

    public static void main(String args[]){
        EightPuzzle ep = new EightPuzzle();
        boolean flag = true;
        Scanner sc = new Scanner(System.in);
        while(flag){
            menu();
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();
            switch(ch){
                case 1:
                    ep.getStates();
                    break;
                case 2:
                    ep.solve();
                    break;
                case 3:
                    System.out.println("Exited...");
                    flag = false;
                    break;
                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }

    public static void menu(){
        System.out.println("=====Menu======");
        System.out.println("1. Enter start and goal states");
        System.out.println("2. solve puzzle");
        System.out.println("3. Exit");
    }
}
/*
=====Menu======
1. Enter start and goal states
2. solve puzzle
3. Exit
Enter choice: 1
Enter Start State: 
1 2 3
0 4 6
7 5 8
Enter Goal State: 
1 2 3
4 5 6
7 8 0
=====Menu======
1. Enter start and goal states
2. solve puzzle
3. Exit
Enter choice: 2
Puzzle solved in 3 steps.
Path of 0: RDR
=====Menu======
1. Enter start and goal states
2. solve puzzle
3. Exit
Enter choice: 1
Enter Start State: 
1 2 3
8 0 4
7 6 5
Enter Goal State: 
2 8 1
0 4 3
7 6 5
=====Menu======
1. Enter start and goal states
2. solve puzzle
3. Exit
Enter choice: 2
Puzzle solved in 9 steps.
Path of 0: ULDRRULLD
=====Menu======
1. Enter start and goal states
2. solve puzzle
3. Exit
Enter choice: 3
Exited...
*/