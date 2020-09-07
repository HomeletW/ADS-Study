package ADS;

import java.util.Random;

public class ArrayGenerator{
	
	/** Private Constructor to prevent direct instance */
	private ArrayGenerator(){}
	
	public static Integer[] orderedArray(int n){
		Integer[] arr = new Integer[n];
		for(int i = 0; i < n; i++)
			arr[i] = i;
		return arr;
	}
	
	public static Integer[] randomArray(int n){
		Integer[] arr = new Integer[n];
		Random r = new Random();
		for(int i = 0; i < n; i++){
			arr[i] = r.nextInt(n);
		}
		return arr;
	}
	
	public static Integer[] fillArray(int n, int fill){
		Integer[] arr = new Integer[n];
		for(int i = 0; i < n; i++){
			arr[i] = fill;
		}
		return arr;
	}
	
	public static <E extends Comparable<E>> boolean isSorted(E[] data){
		for(int i = 1; i < data.length; i++){
			if(data[i].compareTo(data[i - 1]) < 0)
				return false;
		}
		return true;
	}
}
