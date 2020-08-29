package ADS.Basic;

import ADS.Performance;

public class CircularDeque<E> extends DynamicQueue<E> implements DoubleEndedQueue<E>{
	
	
	public CircularDeque(int capacity){
		super(0, 0, (E[]) new Object[capacity + 1]);
	}
	
	public CircularDeque(){
		this(10);
	}
	
	@Override
	public void addFront(E e){
		expend();
		front = (front + array.length - 1) % array.length;
		array[front] = e;
	}
	
	@Override
	public void addLast(E e){
		expend();
		array[tail] = e;
		tail = (tail + 1) % array.length;
	}
	
	@Override
	public E removeFront(){
		if(isEmpty())
			throw new IllegalArgumentException("Can't Dequeue From empty Queue");
		E temp = array[front];
		array[front] = null; // loitering object
		front = (front + 1) % array.length;
		shrink();
		return temp;
	}
	
	@Override
	public E removeLast(){
		if(isEmpty())
			throw new IllegalArgumentException("Can't Dequeue From empty Queue");
		tail = (tail + array.length - 1) % array.length;
		E temp = array[tail];
		array[tail] = null; // loitering object
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
	public E getLast(){
		if(isEmpty())
			throw new IllegalArgumentException("Can't getFront from empty Queue");
		return array[tail];
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
		builder.append(String.format("CircularDeque - Size=%d - Capacity=%d\nFront [", size, getCapacity()));
		for(int i = front; i != tail; i = (i + 1) % array.length){
			builder.append(array[i]).append((i + 1) % array.length == tail ? ", " : "]");
		}
		if(size == 0)
			builder.append("]");
		builder.append(" Last");
		return builder.toString();
	}
	
	public String debugToString(){
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("Front(%d) [", front));
		for(int i = 0; i < array.length; i++){
			builder.append(array[i] == null ? " " : ((Integer) array[i] % 10))
			       .append(i != array.length - 1 ? ", " : "]");
		}
		builder.append(String.format(" Tail(%d) - size:%d - capacity:%d", tail, getSize(), getCapacity()));
		return builder.toString();
	}
	
	public static void test(){
		int size = 10;
		CircularDeque<Integer> queue = new CircularDeque<>();
		Performance.test((index, obj) -> {
			for(int i = 0; i < size; i++){
				queue.addLast(i);
				System.out.println("Add Last  " + queue.debugToString());
			}
			for(int i = 0; i < size; i++){
				queue.addFront(i);
				System.out.println("Add Front " + queue.debugToString());
			}
			for(int i = size - 1; i >= 0; i--){
				assert queue.removeLast() == i;
				System.out.println(" Rm Front " + queue.debugToString());
			}
			for(int i = 0; i < size; i++){
				assert queue.removeLast() == i;
				System.out.println(" Rm Last  " + queue.debugToString());
			}
			assert queue.isEmpty();
			return null;
		}, "Circular Deque 1 size=" + size, 1, true);
	}
	
	public static void main(String[] args){
		test();
	}
}
