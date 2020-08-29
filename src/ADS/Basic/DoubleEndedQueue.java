package ADS.Basic;

public interface DoubleEndedQueue<E>{
	
	void addFront(E e);
	
	void addLast(E e);
	
	E removeFront();
	
	E removeLast();
	
	E getFront();
	
	E getLast();
	
	int getSize();
	
	boolean isEmpty();
}
