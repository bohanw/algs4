
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
import edu.princeton.cs.algs4.*;


public class Solver {
	private Board initial;
	private boolean solvable;
	private int moveCount;
	
	private class SearchNode implements Comparable<SearchNode>{
		private Board board;
		private int moves;
		private int priority;//manhattan priority
		private SearchNode prev;
		
			
		public SearchNode (Board init, int moves, SearchNode prev){
			this.solvable =false;
			this.board = init;
			this.prev = prev;
			
			if(prev != null){
				this.moves = prev.moves + moves;
			}
			else this.moves = 0;
			this.priority = this.board.manhattan() + moves;
		}
		
		public int compareTo(SearchNode other){
			return this.priority - other.priority;
		}
	}
	
	public Solver(Board initial) {
		this.moveCount = -1;
		if(initial == null) throw new IllegalArgumentException();
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
			//Either find a solution or determine unsolvable
			if(node.board.isGoal() || twinnode.board.isGoal()){
				if(node.board.isGoal()){
					this.solvable = true;
					this.moveCount = node.moves;
					
				}
				if(twinnode.board.isGoal()){
					this.solvable = false;
					this.moveCount = -1;
				}
				break;
			} else {
				for(Board neigh : node.board.neighbors()){
					SearchNode neighborNode = new SearchNode(nbs, node.moves+1,node);
					
				}
			}
		}
	}
	
	public int moves(){
		return this.;
	}
	
	public boolean isSolvable(){
		return this.solvable;
	}
	
	public Iterable<Board> solution(){
		
	}
	
	public static void main (String[] args){
		
	}
	
}