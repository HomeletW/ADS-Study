package ADS.Basic;

import ADS.Performance;

import java.util.ArrayDeque;
import java.util.Deque;

public class ArrayStack<E> implements Stack<E>{
	
	private final Array<E> array;
	
	public ArrayStack(int capacity){
		array = new Array<>(capacity);
	}
	
	public ArrayStack(){
		this(10);
	}
	
	@Override
	public void push(E e){
		array.addLast(e);
	}
	
	@Override
	public E pop(){
		return array.removeLast();
	}
	
	@Override
	public E peek(){
		return array.getLast();
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
		builder.append(String.format("ArrayStack - Size=%d - Capacity=%d\n[", array.getSize(), array.getCapacity()));
		for(int i = 0; i < array.getSize(); i++){
			builder.append(array.get(i)).append(i != array.getSize() - 1 ? ", " : "]");
		}
		if(array.getSize() == 0)
			builder.append("]");
		builder.append("<- TOP");
		return builder.toString();
	}
	
	public static void test(){
		int                  size  = 50;
		final Stack<Integer> stack = new ArrayStack<>();
		Performance.test((testIndex, preRunObject)->{
			for(int i = 0; i < size; i++)
				stack.push(i);
			System.out.println(stack);
			for(int i = size - 1; i >= 0; i--)
				assert stack.pop() == i;
			System.out.println(stack);
			return true;
		}, "Array Stack size=" + size);
	}
	
	public static void main(String[] args){
		//		test();
		System.out.println(parenthesesMatching("[([{[]}])]([]{}(([]))){}"));
		System.out.println(parenthesesMatching("[([{[]])]([]{}(([]))){"));
	}
	
	public static boolean parenthesesMatching(String s){
		Stack<Character> stack = new ArrayStack<>();
		for(int i = 0; i < s.length(); i++){
			char c = s.charAt(i);
			if(c == '[')
				stack.push(']');
			else if(c == '{')
				stack.push('}');
			else if(c == '(')
				stack.push(')');
			else if(stack.isEmpty() || stack.pop() != c)
				return false;
		}
		return stack.isEmpty();
	}
}
