package ADS.Basic;

public class ArrayQueue<E> implements Queue<E>{
	
	private final Array<E> array;
	
	public ArrayQueue(int capacity){
		array = new Array<>(capacity);
	}
	
	public ArrayQueue(){
		this(10);
	}
	
	@Override
	public void enqueue(E e){
		array.addLast(e);
	}
	
	@Override
	public E dequeue(){
		return array.removeFirst();
	}
	
	@Override
	public E getFront(){
		return array.getFirst();
	}
	
	@Override
	public int getSize(){
		return array.getSize();
	}
	
	@Override
	public boolean isEmpty(){
		return array.isEmpty();
	}
	
	public int getCapacity(){
		return array.getCapacity();
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("ArrayQueue - Size=%d - Capacity=%d\nFront < [", array.getSize(), array
				.getCapacity()));
		for(int i = 0; i < array.getSize(); i++){
			builder.append(array.get(i)).append(i != array.getSize() - 1 ? ", " : "]");
		}
		if(array.getSize() == 0)
			builder.append("]");
		builder.append(" < Tail");
		return builder.toString();
	}
}
