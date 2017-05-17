import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

import java.util.Arrays;
import java.util.Iterator;
import java.lang.*;


public class Permutation {
	public static void main(String[] args){
		int n = Integer.parseInt(args[0]);
		String[] in = StdIn.readAllStrings();
		RandomizedQueue<String> rq = new RandomizedQueue<String>();
		for(int i = 0; i < in.length;i++){
			rq.enqueue(in[i]);
		}
		
		Iterator<String> ite = rq.iterator();
		for(int j = 0;j < n;j++){
			String s = ite.next();
			StdOut.println(s);
		}
	}
}