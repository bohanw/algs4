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
	private int size;
	
	public RandomizedQueue(){
		this.queue = (Item[]) new Object[1];
		this.size = 0;
	}
	
	public boolean isEmpty(){
		return size == 0;
	}
	
	public int size(){
		return size;
	}
	
	public void enqueue(Item item){
		if(item == null)
			throw new java.lang.NullPointerException();
		else {
			if(size == queue.length){
				resize(2 * queue.length);
			}
			queue[size++] = item;
		}
	}
	
	private void resize(int cap){
		Item[] cp = (Item[]) new Object[cap];
		for (int i = 0;i < size;i++){
			cp[i] = queue[i];
		}
		queue = cp;
	}
	
	public Item dequeue(){
		if(isEmpty())
			throw new java.util.NoSuchElementException();
		int idx = StdRandom.uniform(size);
		Item it = queue[idx];
		if(idx == size-1){
			queue[size-1] = null;
			size = size - 1;
		}
		else {
			//not the last element
			for(int j = idx;j < size-1;j++){
				queue[j] = queue[j+1];
			}
			queue[size-1] = null;
			size = size - 1;
		}
		
		if(size > 0 & size < queue.length/4)
			resize(queue.length/2);
		return it;
			
	}
	
	public Item sample(){
		if(isEmpty())
			throw new java.util.NoSuchElementException();
		int idx = StdRandom.uniform(size);
		return queue[idx];
	}
	
	public Iterator<Item> iterator(){
		return new ItemIterator();
	}
	
	private class ItemIterator implements Iterator<Item>{
		private Item[] queue_cp;
		private int idx;
		
		public ItemIterator(){
			this.queue_cp = (Item[]) new Object[size];
			for(int i = 0; i < size; i++){
				queue_cp[i] = queue[i];
				
			}
			StdRandom.shuffle(queue_cp);
			this.idx = 0;
		}
		
		public boolean hasNext(){
			return idx < queue_cp.length;
		}
		
		public Item next(){
			if(!hasNext())
				throw new java.util.NoSuchElementException();
			Item it = queue_cp[idx];
			idx++;
			return it;
		}
	}
}
