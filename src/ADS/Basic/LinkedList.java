package ADS.Basic;

import ADS.Performance;

public class LinkedList<E>{
	
	// dummy head, the true head is head.next
	private final Node head;
	private       int  size;
	
	public LinkedList(){
		head = new Node(null, null);
		size = 0;
	}
	
	public void insert(int index, E e){
		if(index == size || checkIndex(index)){
			Node n = head;
			for(int i = 0; i < index; i++)
				n = n.next;
			n.next = new Node(e, n.next);
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
		Node n = head;
		for(int i = 0; i < index; i++)
			n = n.next;
		Node delNode = n.next;
		n.next = delNode.next;
		delNode.next = null;
		size--;
		return delNode.e;
	}
	
	public E removeFirst(){
		return remove(0);
	}
	
	public E removeLast(){
		return remove(size - 1);
	}
	
	public E get(int index){
		checkIndex(index);
		Node n = head;
		for(int i = 0; i < index + 1; i++)
			n = n.next;
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
		builder.append(String.format("LinkedList - Size=%d\n[", size));
		int i = 0;
		for(Node n = head.next; n != null; n = n.next, i++)
			builder.append(n).append(i != size - 1 ? "->" : "]");
		if(size == 0)
			builder.append("]");
		return builder.toString();
	}
	
	private class Node{
		E    e;
		Node next;
		
		Node(E e, Node next){
			this.e = e;
			this.next = next;
		}
		
		@Override
		public String toString(){
			return e.toString();
		}
	}
	
	public static void test(){
		int size = 10;
		Performance.test(new Performance.TestFunction(){
			@Override
			public Object runTest(int testIndex, Object preRunObject){
				LinkedList<Integer> linkedList = new LinkedList<>();
				for(int i = 0; i < size; i++){
					linkedList.addFirst(i);
					linkedList.addLast(i);
				}
				System.out.println(linkedList);
				for(int i = 0; i < size; i++){
					linkedList.insert(size, i);
				}
				System.out.println(linkedList);
				for(int i = 0; i < size * 3; i++){
					linkedList.remove(linkedList.getSize() / 2);
					System.out.println(linkedList);
				}
				System.out.println(linkedList);
				return null;
			}
		}, "LinkedList size=" + size);
	}
	
	public static void main(String[] args){
		test();
	}
}
