package ADS.Basic;

import ADS.Performance;

public class LinkedQueue<E> implements Queue<E>{
	
	private final DoublyLinkedList<E> doublyLinkedList;
	
	public LinkedQueue(){
		doublyLinkedList = new DoublyLinkedList<>();
	}
	
	@Override
	public void enqueue(E e){
		doublyLinkedList.addFirst(e);
	}
	
	@Override
	public E dequeue(){
		return doublyLinkedList.removeLast();
	}
	
	@Override
	public E getFront(){
		return doublyLinkedList.getLast();
	}
	
	@Override
	public int getSize(){
		return doublyLinkedList.getSize();
	}
	
	@Override
	public boolean isEmpty(){
		return doublyLinkedList.isEmpty();
	}
	
	@Override
	public String toString(){
		return "LinkedQueue - " + doublyLinkedList.toString();
	}
	
	public static void test(){
		int size = 10;
		LinkedQueue<Integer> queue = new LinkedQueue<>();
		Performance.test((index, obj) -> {
			for(int i = 0; i < size; i++){
				System.out.println(queue);
				queue.enqueue(i);
			}
			for(int i = 0; i < size; i++){
				System.out.println(queue);
				assert queue.dequeue() == i;
			}
			assert queue.isEmpty();
			return null;
		}, "Circular Queue 1 size=" + size, 1, true);
	}
	
	public static void test2(){
		int size = 100;
		LinkedQueue<Integer> queue = new LinkedQueue<>();
		Performance.test((index, obj) -> {
			int dequAccum = 0;
			for(int i = 0; i < size; i++){
				queue.enqueue(i);
				System.out.println("ENQUEUE " + queue);
				if(i % 3 != 0){
					assert queue.dequeue() == dequAccum++;
					System.out.println("DEQUEUE " + queue);
				}
			}
			return null;
		}, "Circular Queue 1 size=" + size, 1, true);
	}
	
	
	public static void main(String[] args){
		test();
		test2();
	}
}
