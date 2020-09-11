package ADS.Recursion;

import ADS.ArrayGenerator;
import ADS.Performance;

import java.util.Random;

public class BinarySearch{
	
	private BinarySearch(){}
	
	public static <E extends Comparable<E>> int search(E[] arr, E e){
		return search(arr, e, 0, arr.length - 1);
	}
	
	private static <E extends Comparable<E>> int search(E[] arr, E e, int left, int right){
		if(left > right)
			return - 1;
		else if(left == right)
			return arr[left] == e ? left : - 1;
		
		int mid = left + (right - left) / 2;
		if(e.compareTo(arr[mid]) <= 0) // the item is on right
			return search(arr, e, left, mid);
		else
			return search(arr, e, mid + 1, right);
	}
	
	public static <E extends Comparable<E>> int searchNR(E[] arr, E e){
		int left = 0, right = arr.length - 1;
		while(left <= right){
			if(left == right)
				return arr[left] == e ? left : - 1;
			
			int mid = left + (right - left) / 2;
			if(e.compareTo(arr[mid]) <= 0)
				right = mid;
			else
				left = mid + 1;
		}
		return - 1;
	}
	
	public static void test(){
		int n = 1000000;
		int repeat = 10;
		Integer[] arr = ArrayGenerator.orderedArray(n);
		Performance.test((i, obj) -> {
			return arr[BinarySearch.searchNR(arr, i)] == i;
		}, "Binary Search Test n=" + n, repeat, true);
	}
	
	public static void main(String[] args){
		test();
	}
}


