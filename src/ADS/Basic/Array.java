package ADS.Basic;

import java.util.RandomAccess;

public class Array<E> implements RandomAccess{
	private E[] data;
	private int size;
	
	/**
	 * Construct a new Array that have initial length as capacity.
	 *
	 * @param capacity the expected capacity of the Array
	 */
	public Array(int capacity){
		data = (E[]) new Object[capacity];
		size = 0;
	}
	
	/**
	 * Construct a new Array that have capacity 10
	 */
	public Array(){
		this(10);
	}
	
	/**
	 * @return the size of the current array (how many item it's holding)
	 */
	public int getSize(){
		return size;
	}
	
	/**
	 * @return the capacity of the current array (how many item it could hold)
	 */
	public int getCapacity(){
		return data.length;
	}
	
	/**
	 * @return true if the array is empty, false otherwise
	 */
	public boolean isEmpty(){
		return size == 0;
	}
	
	/**
	 * Append e to the array
	 *
	 * @param e item to add
	 */
	public void addLast(E e){
		insert(size, e);
	}
	
	/**
	 * Prepend e to the array
	 *
	 * @param e item to add
	 */
	public void addFirst(E e){
		insert(0, e);
	}
	
	/**
	 * Insert e to position index
	 *
	 * @param index the position to add e
	 * @param e item to add
	 */
	public void insert(int index, E e){
		if(index == size || checkIndex(index)){
			expend();
			// first shift all item after index then add it
			for(int i = size - 1; i >= index; i--){
				data[i + 1] = data[i];
			}
			data[index] = e;
			size++;
		}
	}
	
	/**
	 * Return the first element in array
	 */
	public E getFirst(){
		return get(0);
	}
	
	/**
	 * Return the last element in array
	 */
	public E getLast(){
		return get(size - 1);
	}
	
	/**
	 * Get the item at position index
	 *
	 * @param index the position to get
	 * @return the item at index (or throws Index out of bound)
	 */
	public E get(int index){
		checkIndex(index);
		return data[index];
	}
	
	/**
	 * Set the item to position index
	 *
	 * @param index the position to set
	 * @param e the item
	 */
	public void set(int index, E e){
		checkIndex(index);
		data[index] = e;
	}
	
	/**
	 * Check does e contains in current array
	 *
	 * @param e item to find
	 * @return true if e is found, false otherwise
	 */
	public boolean contains(E e){
		return find(e) >= 0;
	}
	
	/**
	 * Return the index of e, if e not find, return -1
	 *
	 * @param e item to find
	 * @return the index of e, or -1 when not find
	 */
	public int find(E e){
		// linear search to find
		for(int i = 0; i < size; i++){
			if(data[i].equals(e))
				return i;
		}
		return -1;
	}
	
	/**
	 * Remove and return the last element of array, identical to pop
	 */
	public E removeLast(){
		return remove(size - 1);
	}
	
	/**
	 * Remove and return the first element of array
	 */
	public E removeFirst(){
		return remove(0);
	}
	
	
	public E remove(int index){
		checkIndex(index);
		E ret = data[index];
		for(int i = index; i < size - 1; i++){
			data[i] = data[i + 1];
		}
		size--;
		data[size] = null; // loitering objects != memory leak
		shrink();
		return ret;
	}
	
	public int removeElement(E e){
		int index = find(e);
		remove(index);
		return index;
	}
	
	// check is the index legal
	private boolean checkIndex(int index){
		if(index >= size || index < 0)
			throw new IndexOutOfBoundsException();
		return true;
	}
	
	// check the size, expend or shrink if necessary
	private void expend(){
		if(size >= data.length)
			resize(data.length * 2);
	}
	
	private void shrink(){
		if(size <= data.length / 4) // lazy shrink
			resize(data.length / 2);
	}
	
	private void resize(int size){
		if(size <= 0)
			return;
		E[] newData = (E[]) new Object[size];
		for(int i = 0; i < this.size; i++)
			newData[i] = data[i];
		data = newData;
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("Array - Size=%d - Capacity=%d\n[", size, data.length));
		for(int i = 0; i < size; i++){
			builder.append(data[i]).append(i != size - 1 ? ", " : "]");
		}
		if(size == 0)
			builder.append("]");
		return builder.toString();
	}
	
	public static void main(String[] args){
		Array<Integer> a = new Array<>(10);
		for(int i = 0; i < 20; i++){
			a.addFirst(i);
			System.out.println(a);
		}
		for(int i = 19; i >= 0; i--){
			a.remove(i);
			System.out.println(a);
		}
	}
	
}
