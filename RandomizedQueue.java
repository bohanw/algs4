import java.lang.NullPointerException ;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import java.util.Iterator;
import java.lang.UnsupportedOperationException;
import java.util.Arrays;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] queue;
	private int front;
	private int last;
	
	public RandomizedQueue(){
		this.queue = (Item[]) new Object[1];
		this.front = 0;
		this.last = 0;
	}
	
	public boolean isEmpty(){
		return front == last;
	}
	
	public int size(){
		return last - front;
	}
	
	//Add new item to the tail of queue
	//
	public void enqueue(Item item){
		/*
		if(size == queue.length)
			resize(2*queue.length);
		size++;
		int s = size;
		queue[s] = item;
		last = s; 
		*/
		if(item == null)
			throw new java.lang.NullPointerException();
		else {
			if(last-front == queue.length)
				resize(2*queue.length);
			queue[last] = item;
			last = last + 1;
		}
	}
	
	private void resize(int cap) {
		Item[] cp = (Item[]) new Object[cap];
		int size = last-front;
		for (int i =0;i<size;i++) {
			cp[i] = queue[i];
		}
		queue = cp;
	}
	
	//Remove an element from queue with random index
	public Item dequeue(){
		if(last-front==0)
			throw new java.util.NoSuchElementException();
		int idx = StdRandom.uniform(front,last);
		Item i = queue[idx];
		if(idx == front){
			queue[front] = null;
			if(last-front == 1)
				last = last-1;
			else 
				front = front + 1;
		}
		else if (idx == last){
			queue[last-1]=null;
			last = last - 1;
		}
		else {
			for(int j=idx;j < last - 1;j++ ){
				queue[j] = queue[j+1];
			}
			queue[last-1]=null;
			last = last - 1;
		}
		return i;
	}
	
	public Item sample(){
		if(last-front==0)
			throw new java.util.NoSuchElementException();
		int idx = StdRandom.uniform(front,last);
		Item i = queue[idx];
		return i;
	}
	
	public Iterator<Item> iterator() {
		return new ItemIterator();
	}
	
	private class ItemIterator implements Iterator<Item>{
		private int[] order = StdRandom.permutation(front,last);//ramdom permutation of queue index
		private int perm_i = 0;
		private int i = order[perm_i];
	
		public boolean hasNext(){
			return perm_i <= order.length-1;
		}
		public Item next(){
			if(!hasNext())
				throw new java.util.NoSuchElementException();
			Item it = queue[i];
			perm_i=perm_i+1;
			return it;		
		}
		
		public void remove(){
			throw new java.lang.UnsupportedOperationException();
		}
	}
	
	//test client main method
	public static void main(String[] args) {
		RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
		StdOut.println(rq.isEmpty());
		StdOut.println("front is " + rq.front + " last is " + rq.last);
		rq.enqueue(2);
		
		rq.dequeue();
		StdOut.println(Arrays.toString(rq.queue));
		StdOut.println(rq.size());
		StdOut.println("front is " + rq.front + " last is " + rq.last);
		//
		
		rq.enqueue(4);
		rq.enqueue(5);
		StdOut.println(Arrays.toString(rq.queue));
		StdOut.println(rq.size());
		System.out.println("front is " + rq.front + " last is " + rq.last);
		
		rq.dequeue();
		System.out.println(Arrays.toString(rq.queue));
		System.out.println("front is " + rq.front + " last is " + rq.last);
		
	}
}
