package ADS.Basic;

import ADS.Performance;

public class CircularQueue<E> extends DynamicQueue<E> implements Queue<E>{
	
	public CircularQueue(int capacity){
		super(0, 0, (E[]) new Object[capacity + 1]);
	}
	
	public CircularQueue(){
		this(10);
	}
	
	@Override
	public void enqueue(E e){
		expend();
		array[tail] = e;
		tail = (tail + 1) % array.length;
	}
	
	@Override
	public E dequeue(){
		if(isEmpty())
			throw new IllegalArgumentException("Can't Dequeue From empty Queue");
		E temp = array[front];
		array[front] = null; // loitering object
		front = (front + 1) % array.length;
		shrink();
		return temp;
	}
	
	@Override
	public E getFront(){
		if(isEmpty())
			throw new IllegalArgumentException("Can't getFront from empty Queue");
		return array[front];
	}
	
	@Override
	public int getSize(){
		if(tail > front){
			return tail - front;
		}else if(tail < front){
			return tail + (array.length - front);
		}else{
			return 0;
		}
	}
	
	@Override
	public boolean isEmpty(){
		return getSize() == 0;
	}
	
	/**
	 * Return the capacity of this CircularQueue, Please note that the true capacity of Circular Queue is getCapacity() + 1
	 */
	public int getCapacity(){
		return array.length - 1;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		int size = getSize();
		builder.append(String.format("CircularQueue - Size=%d - Capacity=%d\nFront < [", size, getCapacity()));
		for(int i = front; i != tail; i = (i + 1) % array.length){
			builder.append(array[i]).append((i + 1) % array.length == tail ? ", " : "]");
		}
		if(size == 0)
			builder.append("]");
		builder.append(" < Tail");
		return builder.toString();
	}
	
	public String debugToString(){
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("Front(%d) < [", front));
		for(int i = 0; i < array.length; i++){
			builder.append(array[i] == null ? " " : ((Integer) array[i] % 10))
			       .append(i != array.length - 1 ? ", " : "]");
		}
		builder.append(String.format(" < Tail(%d) - size:%d - capacity:%d", tail, getSize(), getCapacity()));
		return builder.toString();
	}
	
	public static void test(){
		int size = 10;
		CircularQueue<Integer> queue = new CircularQueue<>();
		Performance.test((index, obj) -> {
			for(int i = 0; i < size; i++){
				System.out.println(queue.debugToString());
				queue.enqueue(i);
			}
			for(int i = 0; i < size; i++){
				System.out.println(queue.debugToString());
				assert queue.dequeue() == i;
			}
			assert queue.isEmpty();
			return null;
		}, "Circular Queue 1 size=" + size, 1, true);
	}
	
	public static void test2(){
		int size = 100;
		CircularQueue<Integer> queue = new CircularQueue<>();
		Performance.test((index, obj) -> {
			int dequAccum = 0;
			for(int i = 0; i < size; i++){
				queue.enqueue(i);
				System.out.println("ENQUEUE " + queue.debugToString());
				if(i % 3 != 0){
					assert queue.dequeue() == dequAccum++;
					System.out.println("DEQUEUE " + queue.debugToString());
				}
			}
			return null;
		}, "Circular Queue 1 size=" + size, 1, true);
	}
	
	
	public static void main(String[] args){
		//		test();
		test2();
	}
}
