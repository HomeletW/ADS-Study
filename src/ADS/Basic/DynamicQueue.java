package ADS.Basic;

public abstract class DynamicQueue<E>{
	
	protected int front, tail;
	protected E[] array;
	
	public DynamicQueue(int front, int tail, E[] array){
		this.front = front;
		this.tail = tail;
		this.array = array;
	}
	
	protected void expend(){
		// if the array is full (there is one space left) we resize to capacity x 2 + 1
		if(getSize() >= getCapacity())
			resize(getCapacity() * 2 + 1);
	}
	
	
	protected void shrink(){
		// if the size is less or equals to capacity / 4, we shrink to capacity / 2
		if(getSize() <= getCapacity() / 4)
			resize(getCapacity() / 2 + 1);
	}
	
	private void resize(int size){
		if(size == 0)
			return;
		E[] newArray = (E[]) new Object[size];
		//		for(int i = front; i != tail; i = (i + 1) % getCapacity(), ri++){
		//			newArray[ri] = array[i];
		//		}
		int arraySize = getSize();
		for(int i = 0; i < arraySize; i++){
			newArray[i] = array[(front + i) % array.length];
		}
		array = newArray;
		front = 0;
		tail = arraySize;
	}
	
	public abstract int getSize();
	
	public abstract int getCapacity();
}
