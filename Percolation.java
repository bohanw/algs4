import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.lang.*;
import java.util.*;

public class Percolation {
	private boolean[] open;
	private int openCount;
	private WeightedQuickUnionUF uf;
	private WeightedQuickUnionUF uf_nobottom;
	private int size;
	private int virtualTop;
	private int virtualBottom;
	
   public Percolation (int n)   {
	   if(n <=0)
		   throw new IllegalArgumentException();
	   size = n;
	   open = new boolean[n*n];
	   openCount = 0;
	   uf = new WeightedQuickUnionUF(size * size + 2); // virtualTop = idx[n*n],virtualBottom=idx[n
	   uf_nobottom = new WeightedQuickUnionUF(size * size + 2);
	   virtualTop = n*n;
	   virtualBottom = n*n+1;
	   for(int i=1;i<=size;i++){
		   uf.union(getUFidx(1,i),virtualTop);
		   uf_nobottom.union(getUFidx(1,i),virtualTop);
		   uf.union(getUFidx(size,i),virtualBottom);
	   }	   
   }
   
   ///Open site if not opened and connect to adjacent neighbor open sites
   public   void open(int row, int col)  {
	   if((row < 1 || row > size) || (col<1 || col > size) )
		   throw new IndexOutOfBoundsException();
	   else if (size == 1) {
		   int idx = getUFidx(row,col);
		   if(open[idx] == false){
			   open[idx] = true;
			   openCount++;
		   }
	   }
	   else {
		   int idx = getUFidx(row,col);
		   if(open[idx] == false){
			   open[idx] = true;
			   openCount++;
			   /*
			   if(row == 1){
				   if(col == 1) {
					   //only right and below
					   if(isOpen(row+1,col) && !uf.connected(getUFidx(row,col),getUFidx(row+1,col)))
						   uf.union(getUFidx(row,col),getUFidx(row+1,col));
					   if(isOpen(row,col+1) && !uf.connected(getUFidx(row,col), getUFidx(row,col+1)))
						   uf.union(getUFidx(row,col), getUFidx(row,col+1));
				   }
				   else if(col == size) {
					   //only left and below
					   if(isOpen(row,col-1) && !uf.connected(getUFidx(row,col), getUFidx(row,col-1)))
						   uf.union(getUFidx(row,col), getUFidx(row,col-1));
					   if(isOpen(row+1,col) && !uf.connected(getUFidx(row,col),getUFidx(row+1,col)))
						   uf.union(getUFidx(row,col),getUFidx(row+1,col));
				   }
				   else {
					   //left,right,below
					   if(isOpen(row+1,col) && !uf.connected(getUFidx(row,col),getUFidx(row+1,col)))
						   uf.union(getUFidx(row,col),getUFidx(row+1,col));
					   if(isOpen(row,col+1) && !uf.connected(getUFidx(row,col), getUFidx(row,col+1)))
						   uf.union(getUFidx(row,col), getUFidx(row,col+1));
					   if(isOpen(row,col-1) && !uf.connected(getUFidx(row,col), getUFidx(row,col-1)))
						   uf.union(getUFidx(row,col), getUFidx(row,col-1));
				   }
			   }
			   else if(row == size){
				   if(col == 1){
					   if(isOpen(row-1,col) && !uf.connected(getUFidx(row,col), getUFidx(row-1,col)))
						   uf.union(getUFidx(row,col), getUFidx(row-1,col));
					   if(isOpen(row,col+1) && !uf.connected(getUFidx(row,col), getUFidx(row,col+1)))
						   uf.union(getUFidx(row,col), getUFidx(row,col+1));
				   }
				   else if(col == size) {
					   if(isOpen(row-1,col) && !uf.connected(getUFidx(row,col), getUFidx(row-1,col)))
						   uf.union(getUFidx(row,col), getUFidx(row-1,col));
					   if(isOpen(row,col-1) && !uf.connected(getUFidx(row,col), getUFidx(row,col-1)))
						   uf.union(getUFidx(row,col), getUFidx(row,col-1));
				   }
				   else {
					   //left,right,above
					   if(isOpen(row,col+1) && !uf.connected(getUFidx(row,col), getUFidx(row,col+1)))
						   uf.union(getUFidx(row,col), getUFidx(row,col+1));
					   if(isOpen(row,col-1) && !uf.connected(getUFidx(row,col), getUFidx(row,col-1)))
						   uf.union(getUFidx(row,col), getUFidx(row,col-1));
					   if(isOpen(row-1,col) && !uf.connected(getUFidx(row,col), getUFidx(row-1,col)))
						   uf.union(getUFidx(row,col), getUFidx(row-1,col));				   
				   }
			   }
			   else if(col == 1 ) {
				   //above,below,right
				   if(isOpen(row,col+1) && !uf.connected(getUFidx(row,col), getUFidx(row,col+1)))
					   uf.union(getUFidx(row,col), getUFidx(row,col+1));
				   if(isOpen(row+1,col) && !uf.connected(getUFidx(row,col), getUFidx(row+1,col)))
					   uf.union(getUFidx(row,col), getUFidx(row+1,col));
				   if(isOpen(row-1,col) && !uf.connected(getUFidx(row,col), getUFidx(row-1,col)))
					   uf.union(getUFidx(row,col), getUFidx(row-1,col)); 
			   }
			   else if(col == size){
				   //above,below,left
				   if(isOpen(row,col-1) && !uf.connected(getUFidx(row,col), getUFidx(row,col-1)))
					   uf.union(getUFidx(row,col), getUFidx(row,col-1));
				   if(isOpen(row+1,col) && !uf.connected(getUFidx(row,col), getUFidx(row+1,col)))
					   uf.union(getUFidx(row,col), getUFidx(row+1,col));
				   if(isOpen(row-1,col) && !uf.connected(getUFidx(row,col), getUFidx(row-1,col)))
					   uf.union(getUFidx(row,col), getUFidx(row-1,col)); 
			   }
			   //all directions
			   else {
			   if(isOpen(row,col+1) && !uf.connected(getUFidx(row,col), getUFidx(row,col+1)))
				   uf.union(getUFidx(row,col), getUFidx(row,col+1));
			   if(isOpen(row,col-1) && !uf.connected(getUFidx(row,col), getUFidx(row,col-1)))
				   uf.union(getUFidx(row,col), getUFidx(row,col-1));
			   if(isOpen(row+1,col) && !uf.connected(getUFidx(row,col), getUFidx(row+1,col)))
				   uf.union(getUFidx(row,col), getUFidx(row+1,col));
			   if(isOpen(row-1,col) && !uf.connected(getUFidx(row,col), getUFidx(row-1,col)))
				   uf.union(getUFidx(row,col), getUFidx(row-1,col)); 
			   }
			   */
			   if(row == 1){
				   uf.union(getUFidx(row,col), virtualTop);
			       uf_nobottom.union(getUFidx(row,col), virtualTop);
			   }
			   if(row == size){
				   uf.union(getUFidx(row,col), virtualBottom);
			   }
			   if(row > 1 && isOpen(row-1,col)){
				   uf.union(getUFidx(row,col), getUFidx(row-1,col));
			       uf_nobottom.union(getUFidx(row,col), getUFidx(row-1,col));
			   }
			   if(row < size && isOpen(row+1,col)) {
				   uf.union(getUFidx(row,col), getUFidx(row+1,col));
			       uf_nobottom.union(getUFidx(row,col), getUFidx(row+1,col));
			   }
			   if(col > 1 && isOpen(row,col-1)) {
				   uf.union(getUFidx(row,col), getUFidx(row,col-1));
			       uf_nobottom.union(getUFidx(row,col), getUFidx(row,col-1));
			   }
			   if(col < size && isOpen(row,col+1)) {
				   uf.union(getUFidx(row,col), getUFidx(row,col+1));
			   	   uf_nobottom.union(getUFidx(row,col), getUFidx(row,col+1));
			   }
		   }
	   	}
   }
   //
   public boolean isOpen(int row, int col)  {
	   if((row < 1 || row > size) || (col<1 || col > size))
		   throw new IndexOutOfBoundsException(); 
	   else {
		   //int idx = (row-1)*size+(col-1);
		   int idx = getUFidx(row,col);
		   return (open[idx]);
	   }
   }
   //Convert from 2D (row,col) to 1D index of weightUF
   private int getUFidx(int row, int col){
	   return size * (row-1) + (col-1);
   }
   
	   public boolean isFull(int row, int col) {
		   // is site (row, col) full?
		   if((row < 1 || row > size) || (col<1 || col > size) )
			   throw new IndexOutOfBoundsException(); 
		   else {
			   int idx = getUFidx(row,col);
			   if(open[idx] == false)
				   return false;
			   else {
				   return (uf_nobottom.connected(virtualTop, getUFidx(row,col)) && uf.connected(virtualTop, getUFidx(row,col)));
			   }
		   }			   
	   }
	   
	   public  int numberOfOpenSites()     {
		   // number of open sites
		   return openCount ;
	   }
	   public boolean percolates()        {
		   // does the system percolate?
		   if (size == 1){
			  int idx = getUFidx(1,1);  
			  return (open[idx]);  
		   }
		   else 
			   return uf.connected(virtualTop, virtualBottom);   
	   }

	   public static void main(String[] args) {
		   // test client (optional)
		   int n2 = 1;
		   Percolation t0 = new Percolation(n2);	
		   StdOut.println(t0.isOpen(1, 1));
		   StdOut.println(t0.percolates());	
		   t0.isOpen(1, 1);
		   t0.open(1,1);
		   StdOut.println(t0.isOpen(1, 1));
		   StdOut.println(t0.percolates());	
		   
		   
		   int n1 = 4;
		   Percolation t1 = new Percolation(n1);
		   t1.open(1,1);
		   t1.isOpen(1, 1);
		   
		   t1.open(4,1);
		   t1.open(1,4);
		   t1.open(4,4);
		   
		   t1.isOpen(4,1);
		   
		   t1.open(1,2);
		   t1.open(2,3);
		   StdOut.println(t1.isOpen(4, 1));
		   StdOut.println(t1.isOpen(4, 4));
		   StdOut.println(t1.isOpen(2, 3)); 
		   StdOut.println(t1.isOpen(3, 2));
		   
		   //TEST 2
		   StdOut.println("TEST2");
		   Percolation t2 = new Percolation(3);
		   t2.open(1,1);
		   t2.open(2,1);
		   t2.open(3,1);
		   StdOut.println(t2.percolates());	
		   System.out.println(Arrays.toString(t2.open));
		   System.out.println(t2.uf.connected(t2.getUFidx(1,1), t2.getUFidx(2, 1)));
		   System.out.println(t2.uf.connected(t2.getUFidx(3,1), t2.getUFidx(2, 1)));
		   System.out.println(t2.uf.connected(t2.virtualBottom, t2.getUFidx(3, 2)));
		   System.out.println(t2.uf.connected(t2.virtualTop, t2.getUFidx(1, 2)));
		   System.out.println(t2.uf.connected(t2.virtualTop, t2.virtualBottom));
	   
	   }
	   

}