import java.awt.Color;
import edu.princeton.cs.algs4.Picture;


public class SeamCarver {
	private Picture p;
	
	 public SeamCarver(Picture picture)                // create a seam carver object based on the given picture
	 {
		 if(picture==null) throw new IllegalArgumentException();
		 this.p = picture;
	 }
	   public Picture picture()                         
	   // current picture
	   {
		   return p;
	   }
	   public     int width()  {                          
	   // width of current picture
	   
		   return p.width();
	   }
	   public     int height()  {
		   // height of current picture
		   return p.height();
	   }
	   
	   
	   private double getEnergy(int x, int y) {
		   if(x < 0 || x > p.width()-1 || y < 0 || y > p.height()-1) throw new IllegalArgumentException();
		   
		   if(x == 0 || x == p.width()-1 || y == 0 || y == p.height()-1) return 1000;		   

		   double deltax = 0, deltay = 0;
		   
		   Color cl = p.get(x, y);
		   
		   Color cl_left = p.get(x-1, y);
		   Color cl_right = p.get(x+1, y);
		   deltax = deltax + Math.pow(cl_left.getRed() - cl_right.getRed(), 2) + Math.pow(cl_left.getGreen()- cl_right.getGreen(), 2) 
		    +  Math.pow(cl_left.getBlue() - cl_right.getBlue(), 2) ;
		   
		   Color cl_up = p.get(x, y-1);
		   Color cl_down = p.get(x, y+1); 
		   deltay = deltay + Math.pow(cl_up.getRed()- cl_down.getRed(), 2) + Math.pow(cl_up.getGreen()- cl_down.getGreen(), 2) 
		    +  Math.pow(cl_up.getBlue() - cl_down.getBlue(), 2);
		   
		   return Math.sqrt(deltay + deltax);
	   }
	   
	   
	   public  double energy(int x, int y) {
		   // energy of pixel at column x and row y
		   return getEnergy(x,y);
	   }
	   
	   public   int[] findHorizontalSeam() {
		   // sequence of indices for horizontal seam
		   
		   return null;
	   }
	   public   int[] findVerticalSeam() {
		   // sequence of indices for vertical seam
		   return null;
	   }
	   public    void removeHorizontalSeam(int[] seam) {
		   // remove horizontal seam from current picture
	   }
	   public    void removeVerticalSeam(int[] seam) {
		   // remove vertical seam from current picture
	   }
	
}