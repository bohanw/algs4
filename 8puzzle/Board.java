import java.util.Arrays;  
import java.util.Comparator; 
import java.util.Stack;
import edu.princeton.cs.algs4.*;


public class Board {
	private int[][] board;
	private int size;
	//private int[][] goal;
	
	public Board(int[][] blocks) {
		// construct a board from an n-by-n array of blocks
		size = blocks.length;
		this.board = new int[size][size];
		//this.goal = new int[size][size];

		for(int i = 0;i < size;i++){
			for (int j = 0;j<size;j++){
				board[i][j] = blocks[i][j];
				/*
				if(i == size-1 && j == size-1)
					this.goal[i][j] = 0;
				else {
					this.goal[i][j]  = i * size + (j + 1);
				}*/
			}
		}
		
	}
	
    // (where blocks[i][j] = block in row i, column j)
	public int dimension() {
		// board dimension n
		return size;
	}
	public int hamming() {
		// number of blocks out of place
		int hammDist = 0;
		for(int i = 0;i < size;i++){
			for(int j = 0;j < size;j++){
				/*if(board[i][j] != 0 && board[i][j] != ) {
				*	hammDist++;
				}*/
				if(i == size-1 && j == size-1){
					if(board[i][j] != 0) hammDist++;
				}else {
					if(board[i][j] !=i*size+j+1) hammDist++;
				}
			}
		}
		return hammDist;
	}
	
	public int manhattan() {
		// sum of Manhattan distances between blocks and goal
		int manDist = 0;
		int goal_x, goal_y;
		int x_diff, y_diff;
		for(int i = 0;i < size;i++){
			for(int j = 0;j < size; j++){
				if(board[i][j] != 0 ){
					if((i == size-1 && j == size - 1 && board[i][j] != 0) || 
						(!(i == size-1 && j == size - 1) && board[i][j] !=i*size+j+1 ) ){
						goal_y = (board[i][j] -1) % size;
						goal_x = (board[i][j] -1)/ size;
						x_diff = Math.abs(goal_x - i);
						y_diff = Math.abs(goal_y - j);
						manDist +=  x_diff + y_diff;
					}
				}
				else manDist =manDist+0;
			}
		}
		return manDist;
		
	}
	public boolean isGoal() {
		// is this board the goal board?
		return this.manhattan() == 0;
		//if this.board.equals(goal)
	}
	public Board twin(){
		// a board that is obtained by exchanging any pair of blocks
		// Swap the first two blocks by default, if empty swap the second row
		int[][] twin = new int[size][size];
		for(int i = 0;  i < size;i++){
			for(int j = 0; j < size;j++){
				twin[i][j] = this.board[i][j];
			}
		}
		if(board[0][0] != 0 && board[0][1] != 0){
			int temp = twin[0][0];
			twin[0][0] = twin[0][1];
			twin[0][1] = temp;		
		}
		else {
			int temp = twin[1][0];
			twin[1][0] = twin[1][1];
			twin[1][1] = temp;
		}

		return new Board(twin);
		
	}
	
	public boolean equals(Object y){
		// does this board equal y?
		if(y == null) return false;
		else if (this == y) return true;
		else if (this.getClass() != y.getClass()) return false;
		else {
			Board that = (Board) y;
			if(this.size != that.size) return false;
			for(int i = 0; i < size;i++){
				for(int j = 0;j < size;j++){
					if(this.board[i][j] != that.board[i][j]) {
						return false;
					}
				}
			}
			return true;
		}
	}
	
	private int[][] getCopy (int[][] board){	
		int [][] copy = new int[size][size];
		for(int i = 0; i < size;i++){
			for(int j = 0;j < size;j++){
				copy[i][j] = board[i][j];							
			}
		}
		return copy;
	}
	private void swap(int [][] blocks, int src_x, int src_y, int dest_x, int dest_y){
		int temp = blocks[dest_x][dest_y];
		blocks[dest_x][dest_y] = blocks[src_x][src_y];
		blocks[src_x][src_y] = temp;
	}
	
	//Returns an iterable objects of Boards which are all valid neighbors of the current board
	public Iterable<Board> neighbors() {
		// all neighboring boards
		Stack<Board> neighbors = new Stack<Board>();
		int blankRow=0;
		int blankCol=0;
		for(int i = 0; i < size;i++){
			for(int j = 0;j < size;j++){
				if(board[i][j] == 0){
					blankRow = i;
					blankCol = j;
				}			
			}
		}
		
		if( blankRow !=0){
			int [][] copy = getCopy(board);
			swap(copy,blankRow,blankCol,blankRow-1,blankCol);
			Board bd = new Board(copy);
			if (!neighbors.contains(bd)) neighbors.push(bd);
		}
		if(blankRow != size-1){
			int [][] copy = getCopy(board);
			swap(copy,blankRow,blankCol,blankRow+1,blankCol);
			Board bd = new Board(copy);
			if (!neighbors.contains(bd)) neighbors.push(bd);
		}
		if(blankCol != 0){
			int [][] copy = getCopy(board);
			swap(copy,blankRow,blankCol,blankRow,blankCol-1);
			Board bd = new Board(copy);
			if (!neighbors.contains(bd)) neighbors.push(bd);
		}
		if(blankCol != size-1){
			int [][] copy = getCopy(board);
		    swap(copy,blankRow,blankCol,blankRow,blankCol+1);
		    Board bd = new Board(copy);
			if (!neighbors.contains(bd)) neighbors.push(bd);
		}
		return neighbors;
		
	}
	public String toString()   {
	// string representation of this board (in the output format specified below)
		StringBuilder s = new StringBuilder();
		s.append(size + "\n");
	    for (int i = 0; i < size; i++) {
	        for (int j = 0; j < size; j++) {
	            s.append(String.format("%2d ", board[i][j]));
	        }
	        s.append("\n");
	    }
	    return s.toString();
	}
	
	public static void main(String[] args){
		int size = 3;
		int [][] puzzle = new int[3][3];
		puzzle[0][0] = 8;
		puzzle[0][1] = 1;
		puzzle[0][2] = 3;
		puzzle[1][0] = 4;
		puzzle[1][1] = 0;
		puzzle[1][2] = 2;
		puzzle[2][0] = 7;
		puzzle[2][1] = 6;
		puzzle[2][2] = 5;
		/*
		int[][] goal = new int[3][3];
		goal[0][0] = 1;
		goal[0][1] = 2;
		goal[0][2] = 3;
		goal[1][0] = 4;
		goal[1][1] = 5;
		goal[1][2] = 6;
		goal[2][0] = 7;
		goal[2][1] = 8;
		goal[2][2] = 0;
		Board gl = new Board(goal);
		System.out.println(gl);
		System.out.println(Arrays.deepToString(puzzle));*/
		Board bd = new Board(puzzle);

		for(Board b: bd.neighbors()){
			System.out.println(b.toString());
		}
		
	}

}
