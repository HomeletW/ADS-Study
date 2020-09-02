package ADS.Basic;

import ADS.Performance;

public class RecursiveLinkedList<E>{
	
	private Node head;
	private int  size;
	
	public RecursiveLinkedList(){
		head = new Node(null, null);
		size = 0;
	}
	
	public void insert(int index, E e){
		if(index == size || checkIndex(index)){
			if(index == 0){
				head.next = new Node(e, head.next);
				size++;
			}else{
				Node realHead = head;
				head = head.next;
				insert(index - 1, e);
				head = realHead;
			}
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
		if(index == 0){
			Node delNode = head.next;
			head.next = delNode.next;
			delNode.next = null;
			size--;
			return delNode.e;
		}else{
			Node realHead = head;
			head = head.next;
			E e = remove(index - 1);
			head = realHead;
			return e;
		}
	}
	
	public E removeFirst(){
		return remove(0);
	}
	
	public E removeLast(){
		return remove(size - 1);
	}
	
	public E get(int index){
		checkIndex(index);
		if(index == 0){
			return head.next.e;
		}else{
			Node realHead = head;
			head = head.next;
			E e = get(index - 1);
			head = realHead;
			return e;
		}
	}
	
	
	public E getFirst(){
		return get(0);
	}
	
	public E getLast(){
		return get(size - 1);
	}
	
	public int contains(E e){
		if(head.next == null){
			return - 1;
		}else if(head.next.e == e){
			return 0;
		}else{
			Node realHead = head;
			head = head.next;
			int i = contains(e);
			head = realHead;
			if(i < 0){
				return i;
			}else{
				return i + 1;
			}
		}
	}
	
	public void set(int index, E e){
		checkIndex(index);
		if(index == 0){
			head.next.e = e;
		}else{
			Node realHead = head;
			head = head.next;
			set(index - 1, e);
			head = realHead;
		}
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
		builder.append(String.format("RecursiveLinkedList - Size=%d\n[", size));
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
				RecursiveLinkedList<Integer> linkedList = new RecursiveLinkedList<>();
				for(int i = 0; i < size; i++){
					linkedList.addFirst(i);
					linkedList.addLast(i);
					System.out.println(linkedList);
				}
				for(int i = 0; i < size; i++){
					linkedList.insert(size, i);
					System.out.println(linkedList);
				}
				for(int i = 0; i < size * 2; i++){
					if(i < size)
						assert linkedList.contains(i) >= 0;
					else
						assert linkedList.contains(i) < 0;
					System.out.println(linkedList);
				}
				for(int i = 0; i < linkedList.getSize(); i++){
					linkedList.set(i, linkedList.get(i) * 3);
					System.out.println(linkedList);
				}
				System.out.println(linkedList.contains(size - 1));
				for(int i = 0; i < size * 3; i++){
					linkedList.remove(linkedList.getSize() / 3 * (i % 3 == 0 ? 2 : 1));
					System.out.println(linkedList);
				}
				try{
					linkedList.remove(size / 2);
				}catch(IndexOutOfBoundsException e){
					System.out.println("Can't remove from empty linkedlist");
				}
				try{
					linkedList.removeLast();
				}catch(IndexOutOfBoundsException e){
					System.out.println("Can't remove from empty linkedlist");
				}
				try{
					linkedList.removeFirst();
				}catch(IndexOutOfBoundsException e){
					System.out.println("Can't remove from empty linkedlist");
				}
				return null;
			}
		}, "RecursiveLinkedList size=" + size);
	}
	
	public static void main(String[] args){
		test();
	}
}

