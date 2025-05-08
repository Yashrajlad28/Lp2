package tools;

public class State {
	
	int[][] board;
	int g;
	int h;
	String path;
	
	State(int[][] board, int g, int h, String path) {
        this.board = board;
        this.g = g;
        this.h = h;
        this.path = path;
    }

    int f() {
        return g + h;
    }
}
