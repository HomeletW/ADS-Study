package ADS.Basic;

import ADS.Performance;

/**
 * Stands for LinkedDoubleEndedList
 */
public class LinkedDQList<E>{
	
	// head always have prev == null and tail always have next == null
	private final Node head;
	private final Node tail;
	private       int  size;
	
	public LinkedDQList(){
		head = new Node(null, null, null);
		tail = new Node(null, null, null);
		head.next = tail;
		tail.prev = head;
		size = 0;
	}
	
	public void insert(int index, E e){
		if(index == size || checkIndex(index)){
			// check which the index is closer to? head or tail
			Node n;
			if(index > size / 2){
				// we go from tail
				n = tail;
				for(int i = 0; i < size - index + 1; i++)
					n = n.prev;
			}else{
				// we go from head
				n = head;
				for(int i = 0; i < index; i++)
					n = n.next;
			}
			Node newNode = new Node(e, n, n.next);
			n.next.prev = newNode;
			n.next = newNode;
			size++;
		}
	}
	
	public void addFirst(E e){
		insert(0, e);
	}
	
	public void addLast(E e){
		insert(size, e);
	}
	
	public E remove(int index){
		checkIndex(index);
		// check the index is closer to? head or tail
		Node n;
		if(index > size / 2){
			// we go from tail
			n = tail;
			for(int i = 0; i < size - index; i++)
				n = n.prev;
		}else{
			// we go from head
			n = head;
			for(int i = 0; i < index + 1; i++)
				n = n.next;
		}
		n.prev.next = n.next;
		n.next.prev = n.prev;
		n.prev = null;
		n.next = null;
		size--;
		return n.e;
	}
	
	public E removeFirst(){
		return remove(0);
	}
	
	public E removeLast(){
		return remove(size - 1);
	}
	
	public E get(int index){
		checkIndex(index);
		// check the index is closer to? head or tail
		Node n;
		if(index > size / 2){
			// we go from tail
			n = tail;
			for(int i = 0; i < size - index + 1; i++)
				n = n.prev;
		}else{
			// we go from head
			n = head;
			for(int i = 0; i < index; i++)
				n = n.next;
		}
		return n.e;
	}
	
	public E getFirst(){
		return get(0);
	}
	
	public E getLast(){
		return get(size - 1);
	}
	
	public int contains(E e){
		int index = 0;
		for(Node n = head.next; n != null; n = n.next, index++){
			if(n.e.equals(e))
				return index;
		}
		return - 1;
	}
	
	public void set(int index, E e){
		checkIndex(index);
		Node n = head;
		for(int i = 0; i < index + 1; i++)
			n = n.next;
		n.e = e;
	}
	
	private boolean checkIndex(int index){
		if(index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		return true;
	}
	
	public int getSize(){
		return size;
	}
	
	public boolean isEmpty(){
		return size == 0;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("LinkedDQList - Size=%d\n[", size));
		int i = 0;
		for(Node n = head.next; n != tail; n = n.next, i++)
			builder.append(n).append(i != size - 1 ? "-" : "]");
		if(size == 0)
			builder.append("]");
		return builder.toString();
	}
	
	
	private class Node{
		E    e;
		Node prev, next;
		
		Node(E e, Node prev, Node next){
			this.e = e;
			this.prev = prev;
			this.next = next;
		}
		
		@Override
		public String toString(){
			return e.toString();
		}
	}
	
	public static void test(){
		int size = 5;
		Performance.test(new Performance.TestFunction(){
			@Override
			public Object runTest(int testIndex, Object preRunObject){
				LinkedDQList<Integer> linkedList = new LinkedDQList<>();
				for(int i = 0; i < size; i++){
					linkedList.addFirst(i);
					linkedList.addLast(i);
					System.out.println(linkedList);
				}
				for(int i = 0; i < size; i++){
					linkedList.insert(size, i);
					System.out.println(linkedList);
				}
				System.out.println(linkedList.contains(size - 1));
				for(int i = 0; i < size * 3; i++){
					linkedList.remove(linkedList.getSize() / 3 * (i % 3 == 0 ? 2 : 1));
					System.out.println(linkedList);
				}
				return null;
			}
		}, "LinkedDQList size=" + size);
	}
	
	public static void main(String[] args){
		test();
	}
}
