package ADS.Basic;

public class LinkedStack<E> implements Stack<E>{
	
	private final LinkedList<E> linkedList;
	
	public LinkedStack(){
		linkedList = new LinkedList<>();
	}
	
	@Override
	public void push(E e){
		linkedList.addFirst(e);
	}
	
	@Override
	public E pop(){
		return linkedList.removeFirst();
	}
	
	@Override
	public E peek(){
		return linkedList.getFirst();
	}
	
	@Override
	public int getSize(){
		return linkedList.getSize();
	}
	
	@Override
	public boolean isEmpty(){
		return linkedList.isEmpty();
	}
	
	public static void main(String[] args){
	
	}
	
}
