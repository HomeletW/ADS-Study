package ADS.Recursion;

import ADS.ArrayGenerator;
import ADS.Basic.InsertionSort;
import ADS.Basic.SelectionSort;
import ADS.Performance;

import java.util.Arrays;

public class MergeSort{
	
	private MergeSort(){
	}
	
	public static <E extends Comparable<E>> void sort(E[] arr){
		// create a public temp space that has size arr.length
		E[] temp = Arrays.copyOf(arr, arr.length);
		split(arr, 0, arr.length - 1, temp);
	}
	
	private static <E extends Comparable<E>> void split(E[] arr, int left, int right, E[] temp){
		// array with length 1 is already sorted!
		// if(left >= right)
		//     return;
		
		// if the remaining array is smaller than a certain threshold, we use insertion sort.
		if(right - left <= 15){
			// we perform insertion sort
			partialInsertionSort(arr, left, right);
			return;
		}
		
		// to prevent 32-bit integer overflow. Because left + right could could be a very big number
		// we change from (left + right) to left + (right - left) / 2, since right is valid, this
		// expression is also valid.
		int mid = left + (right - left) / 2;
		split(arr, left, mid, temp);
		split(arr, mid + 1, right, temp);
		
		if(arr[mid].compareTo(arr[mid + 1]) > 0)
			merge(arr, left, mid, right, temp);
	}
	
	private static <E extends Comparable<E>> void partialInsertionSort(E[] arr, int left, int right){
		for(int i = left; i <= right; i++){
			// loop invariant: data[left, i) is sorted
			// the job of the loop is to place data[i] to it's desired position
			E temp = arr[i];
			int j;
			for(j = i; j - 1 >= left && temp.compareTo(arr[j - 1]) < 0; j--){
				arr[j] = arr[j - 1];
			}
			arr[j] = temp;
		}
	}
	
	private static <E extends Comparable<E>> void merge(E[] arr, int left, int mid, int right, E[] temp){
		int leftPointer = left, rightPointer = mid + 1;
		// notice that we still need to make sure the item in temp is correct
		System.arraycopy(arr, left, temp, left, right - left + 1);
		for(int i = left; i <= right; i++){
			if(rightPointer > right){
				// add to left
				arr[i] = temp[leftPointer];
				leftPointer++;
			}else if(leftPointer > mid){
				// add to right
				arr[i] = temp[rightPointer];
				rightPointer++;
			}else if(temp[leftPointer].compareTo(temp[rightPointer]) <= 0){
				// add to left
				arr[i] = temp[leftPointer];
				leftPointer++;
			}else{
				// add to right
				arr[i] = temp[rightPointer];
				rightPointer++;
			}
		}
	}
	
	public static void test(){
		int repeat = 100;
		int n = 10000000;
		Performance.test(new Performance.TestFunction(){
			@Override
			public Object preRun(){
				return ArrayGenerator.randomArray(n);
			}
			
			@Override
			public Object runTest(int testIndex, Object preRunObject){
				MergeSort.sort((Integer[]) preRunObject);
				return preRunObject;
			}
			
			@Override
			public boolean postRun(Object runObject){
				return ArrayGenerator.isSorted((Integer[]) runObject);
			}
		}, "Merge Sort n=" + n, repeat, true);
	}
	
	public static void compare(){
		int repeat = 10;
		int n = 100000;
		final Integer[][] arr1 = new Integer[repeat][n];
		final Integer[][] arr2 = new Integer[repeat][n];
		final Integer[][] arr3 = new Integer[repeat][n];
		for(int i = 0; i < repeat; i++){
			Integer[] randomArr = ArrayGenerator.randomArray(n);
			System.arraycopy(randomArr, 0, arr1[i], 0, n);
			System.arraycopy(randomArr, 0, arr2[i], 0, n);
			System.arraycopy(randomArr, 0, arr3[i], 0, n);
		}
		System.out.println(String.format("Test Started repeat=%d n=%d!", repeat, n));
		Performance.test((i, e) -> {
			MergeSort.sort(arr3[i]);
			return ArrayGenerator.isSorted(arr3[i]);
		}, "Merge Sort", repeat, false);
		Performance.test((i, e) -> {
			SelectionSort.sort(arr1[i]);
			return ArrayGenerator.isSorted(arr1[i]);
		}, "Selection Sort", repeat, false);
		Performance.test((i, e) -> {
			InsertionSort.sort(arr2[i]);
			return ArrayGenerator.isSorted(arr2[i]);
		}, "Insertion Sort", repeat, false);
	}
	
	public static void main(String[] args){
		compare();
	}
}
