
public class Board {
	private int[][] board;
	private int size;
	private int[][] goal;
	
	public Board(int[][] blocks) {
		// construct a board from an n-by-n array of blocks
		size = blocks.length;
		for(int i = 0;i < size;i++){
			for (int j = 0;j<size;j++){
				this.board[i][j] = blocks[i][j];
				if(i == size-1 && j == size-1)
					this.goal[i][j] = 0;
				else {
					this.goal[i][j]  = j * size + (i + 1);
				}
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
				if(board[i][j] != goal[i][j])
					hammDist++;
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
				if(board[i][j] != 0){
					goal_x = board[i][j] % size - 1;
					goal_y = board[i][j] / size;
					x_diff = Math.abs(goal_x - i);
					y_diff = Math.abs(goal_y - j);
					manDist += manDist + x_diff + y_diff;
				}
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
		int[][] twin = new int[size][size];
		for(int i = 0; int i < size;i++){
			for(int j = 0;int j < size;j++){
				twin[i][j] = this.board[i][j];
			}
		}
		if(board[0][0] != 0 && board[0][1] != 0){
			int temp = board[0][0];
			board[0][0] = board[0][1];
			board[0][1] = temp;			
		}
		else {
			int temp = board[1][0];
			board[1][0] = board[1][1];
			board[1][1] = temp;
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
	public Iterable<Board> neighbors() {
		// all neighboring boards
		
		return 
		
	}
public String toString()   {
	// string representation of this board (in the output format specified below)
}

}
