import java.util.Iterator;
import java.lang.UnsupportedOperationException;
import java.lang.NullPointerException ;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
	private int size;
	private Node first;
	private Node last;
	
	private class Node {
		Item item;
		Node next;
		Node prev;
	}
	
	public Deque(){
		this.size = 0;
		this.first = null;
		this.last = null;
	}
	
	//Check whether empty
	public boolean isEmpty(){
		return first == null;
	}
	
	//returns size of deque
	public int size(){
		return size;
	}
	
	public void addFirst(Item item){
		if(item == null)
			throw new NullPointerException();
		else {
			size++;
			if(first==null){
				Node n = new Node();
				first = n;
				last = n;
				first.item = item;
				first.next = null;
			}
			else {
				//at least one element
				Node oldfirst = first;
				first = new Node();
				first.item = item;
				first.next = oldfirst;
				oldfirst.prev = first;
			}
		}
	}
	
	public void addLast(Item item){
		if(item == null)
			throw new NullPointerException();
		else {
			size++;
			if(first == null){
				Node n = new Node();
				first = n;
				last = n;
				last.item = item;
				last.next = null;
			}
			else {
				Node oldLast = last;
				last.next = new Node();
				last.next.item = item;
				last.next.next = null;
				last = last.next;
				last.prev = oldLast;
			}
		}
	}
	
	public Item removeFirst()  {
		// remove and return the item from the front
		if(first == null)
			throw new NoSuchElementException();
		else if (first == last){
			size--;
			Item i = first.item;
			first = null;
			last = null;
			return i;
		}
		else {
			size--;
			Node oldFirst = first;
			first = oldFirst.next;
			first.prev = null;
			return oldFirst.item;
		}
	}
	public Item removeLast()     {
		// remove and return the item from the end
		if(first == null)
			throw new NoSuchElementException();
		else if (first == last){
			size--;
			Item i = first.item;
			first = null;
			last = null;
			return i;
		}
		else {
			size--;
			Node newLast = last.prev;
			Item i = last.item;
			last.prev = null;
			newLast.next = null;
			last = newLast;
			return i;
		}
	}
	
	
	public Iterator<Item> iterator()  {
		// return an iterator over items in order from front to end
		return new ItemIterator();
	}
	
	private class ItemIterator implements Iterator<Item>{
		private Node curr = first;
		
		public boolean hasNext() {
			return curr != null;
		}
		public void remove(){
			throw new java.lang.UnsupportedOperationException();
			
		}		
		public Item next(){
			if(!hasNext())
				throw new java.util.NoSuchElementException();
			
			Item i = curr.item;
			curr = curr.next;
			return i;
		}
	}
	public static void main(String[] args) {
		// unit testing (optional)
		Deque<String> q = new Deque<String>();
		q.isEmpty();
		q.addFirst("s");
		q.addFirst("a");
		q.addLast("k");
		q.addLast("c");
		System.out.println("last item is " + q.last.item);
		q.removeLast();
		System.out.println("last item is " + q.last.item);
		System.out.println(q.first.item);	
		System.out.println(q.first.next.item);	
		System.out.println(q.last.item);
		System.out.println(q.size);	
		
		Iterator<String> i = q.iterator();
		
	}

}
