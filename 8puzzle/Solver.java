
/*
public class Solver {
	public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    public boolean isSolvable()            // is the initial board solvable?
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    public static void main(String[] args) // solve a slider puzzle (given below)
}
*/
import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;
import edu.princeton.cs.algs4.*;


public class Solver {
	private Board initial;
	private boolean solvable;
	private int moveCount;
	private Stack<Board> solution;
	
	private class SearchNode implements Comparable<SearchNode>{
		private Board board;
		private int moves;
		private int priority;//manhattan priority
		private SearchNode prev;
		
			
		public SearchNode (Board init, int moves, SearchNode prev){
			this.board = init;
			this.prev = prev;		
			this.moves = moves;
			this.priority = this.board.manhattan() + moves;
		}
		
		public int compareTo(SearchNode other){
			return this.priority - other.priority;
		}
	}
	
	public Solver(Board initial) {

		if(initial == null) throw new IllegalArgumentException();
		this.moveCount = -1;
		this.solution = new Stack<Board>();	
		this.initial = initial;
		MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
		pq.insert(new SearchNode(initial,0,null));
		
		MinPQ<SearchNode> twin = new MinPQ<SearchNode>();
		twin.insert(new SearchNode(initial.twin(),0,null));
		
		solve(pq,twin);
	}
	
	/*
	private int[][] getGoal(){
		int[][] goal = new int[initial.dimension()][initial.dimension()];
		for(int i=0;i < initial.dimension();i++){
			for(int j=0;j < initial.dimension();j++){
				if(i == initial.dimension()-1 && j == initial.dimension()-1) 
					goal[i][j] = 0;
				else goal[i][j] = i * initial.dimension() + j + 1;
			}
		}
		return goal;
	}
	*/
	
	//run a* algorithm
	private void solve(MinPQ<SearchNode> pq, MinPQ<SearchNode> twin){
		while(true){
			SearchNode node = pq.delMin();
			SearchNode twinnode = twin.delMin();
			System.out.println("move is "+ node.moves);
			System.out.println("man dist is "+node.board.manhattan());
			System.out.println("priorioty is "+node.priority);
			System.out.println("total moves" + this.moveCount);
			//Either find a solution or determine unsolvable
			if(node.board.isGoal() || twinnode.board.isGoal()){
				if(node.board.isGoal()){
					this.solvable = true;
					this.moveCount = node.moves;
					this.solution.push(node.board);
					//push all parent nodes until finding the null(root) game node
					while(node.prev != null){
						node = node.prev;
						this.solution.push(node.board);
					}
				}
				if(twinnode.board.isGoal()){
					this.solvable = false;
					this.moveCount = -1;
				}
				break;
			} else {
				int currMove = node.moves + 1;
				for(Board neigh : node.board.neighbors()){
					SearchNode neighborNode = new SearchNode(neigh, currMove,node);
					if(node.prev == null){
						pq.insert(neighborNode);
					}else if(!node.prev.board.equals(neighborNode.board)){
						pq.insert(neighborNode);
					}
				}
				for(Board neigh_twin:twinnode.board.neighbors()){
					SearchNode neighborNode = new SearchNode(neigh_twin, currMove,twinnode);
					if(twinnode.prev == null){
						twin.insert(neighborNode);
					} else if(!twinnode.prev.board.equals(neighborNode.board)){
						twin.insert(neighborNode);
					}
				}
			}
		}
	}
	
	public int moves(){
		return this.moveCount;
	}
	
	public boolean isSolvable(){
		return this.solvable;
	}
	
	public Iterable<Board> solution(){
		if(!isSolvable()){
			return null;
		} else {
			return this.solution;
		}
	}
	
	//Test Client
	
	public static void main (String[] args){
		// create initial board from file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    int[][] blocks = new int[n][n];
	    for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }	
	}
	
	/*
	public static void main(String[] args){
		int size = 3;
		int [][] puzzle = new int[3][3];
		puzzle[0][0] = 0;
		puzzle[0][1] = 1;
		puzzle[0][2] = 3;
		puzzle[1][0] = 4;
		puzzle[1][1] = 2;
		puzzle[1][2] = 5;
		puzzle[2][0] = 7;
		puzzle[2][1] = 8;
		puzzle[2][2] = 6;
		Board initial = new Board(puzzle);
		Solver solver = new Solver(initial);
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
	}*/
	
}


