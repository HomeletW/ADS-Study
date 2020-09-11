package ADS.Recursion;

import ADS.ArrayGenerator;
import ADS.Basic.InsertionSort;
import ADS.Basic.SelectionSort;
import ADS.Performance;

import java.util.Arrays;
import java.util.Random;

public class QuickSort{
	
	private static int HYPER_THRESHOLD_INSERTION = 19;
	
	private QuickSort(){}
	
	
	public static <E extends Comparable<E>> void sort(E[] arr){
		sortThreeWay(arr);
	}
	
	public static <E extends Comparable<E>> void sortV1(E[] arr){
		Random random = new Random();
		sortV1(arr, 0, arr.length - 1, random);
	}
	
	public static <E extends Comparable<E>> void sortTwoWay(E[] arr){
		Random random = new Random();
		sortTwoWay(arr, 0, arr.length - 1, random);
	}
	
	public static <E extends Comparable<E>> void sortThreeWay(E[] arr){
		Random random = new Random();
		sortThreeWay(arr, 0, arr.length - 1, random);
	}
	
	private static <E extends Comparable<E>> void sortV1(E[] arr, int left, int right, Random random){
		if(right - left <= HYPER_THRESHOLD_INSERTION){
			partialInsertionSort(arr, left, right);
			return;
		}
		int p = partitionV1(arr, left, right, random);
		
		sortV1(arr, left, p - 1, random);
		sortV1(arr, p + 1, right, random);
	}
	
	private static <E extends Comparable<E>> void sortTwoWay(E[] arr, int left, int right, Random random){
		if(right - left <= HYPER_THRESHOLD_INSERTION){
			partialInsertionSort(arr, left, right);
			return;
		}
		int p = partitionTwoWay(arr, left, right, random);
		
		sortTwoWay(arr, left, p - 1, random);
		sortTwoWay(arr, p + 1, right, random);
	}
	
	private static <E extends Comparable<E>> void sortThreeWay(E[] arr, int left, int right, Random r){
		if(right - left <= 15){
			partialInsertionSort(arr, left, right);
			return;
		}
		int[] divide = partitionThreeWay(arr, left, right, r);
		int lessThan = divide[0], greaterThan = divide[1];
		sortThreeWay(arr, left, lessThan - 1, r);
		sortThreeWay(arr, greaterThan, right, r);
	}
	
	private static <E extends Comparable<E>> int partitionV1(E[] arr, int left, int right, Random random){
		// int pivot = left; we don't always choose left as pivot, we use a random index
		int pivot = left + random.nextInt(right - left + 1);
		swap(arr, left, pivot);
		int j = left;
		for(int i = j + 1; i <= right; i++){
			// loop invariant: arr[left + 1, j] < arr[pivot], arr[j + 1, i - 1] >= arr[pivot]
			if(arr[i].compareTo(arr[left]) < 0){
				// we should move it to left part, otherwise we should maintain it's position,
				// because it naturally falls into the right part
				// we swap i with j
				j++;
				swap(arr, i, j);
			}
		}
		// now we swap pivot with j
		swap(arr, left, j);
		return j;
	}
	
	private static <E extends Comparable<E>> int partitionTwoWay(E[] arr, int left, int right, Random random){
		int pivot = left + random.nextInt(right - left + 1);
		swap(arr, left, pivot);
		int leftPointer = left + 1, rightPointer = right;
		while(true){
			// loop invariant : arr[left + 1, leftPointer - 1] <= pivot,
			//                  arr[rightPointer + 1, right] >= pivot
			// we move to an item that is greater or equals to pivot
			while(leftPointer <= rightPointer && arr[leftPointer].compareTo(arr[left]) < 0)
				leftPointer++;
			// we move to an item that is smaller or equals to pivot
			while(leftPointer <= rightPointer && arr[rightPointer].compareTo(arr[left]) > 0)
				rightPointer--;
			// if we have no more item, i.e. leftPointer >= rightPointer
			if(leftPointer >= rightPointer)
				break;
			// now leftPointer and rightPointer both points to and item
			// that belongs to the other side, we swap them
			swap(arr, leftPointer, rightPointer);
			leftPointer++;
			rightPointer--;
		}
		swap(arr, rightPointer, left);
		return rightPointer;
	}
	
	private static <E extends Comparable<E>> int[] partitionThreeWay(E[] arr, int left, int right, Random r){
		int pivot = left + r.nextInt(right - left + 1);
		swap(arr, left, pivot);
		int lessThan = left, greaterThan = right + 1;
		for(int i = left + 1; i < greaterThan; ){
			// loop invariant : arr[left + 1, lessThan] < pivot
			// 					arr[lessThan + 1, i - 1] == pivot
			// 					arr[greaterThan, right] > pivot
			int res = arr[i].compareTo(arr[left]);
			if(res < 0){
				// we move to first section
				lessThan++;
				swap(arr, i, lessThan);
				i++;
			}else if(res == 0){
				// we are already at second section
				i++;
			}else{
				// we move to third section, notice that we don't move i, since after swap, i still need to
				// compare
				greaterThan--;
				swap(arr, i, greaterThan);
			}
		}
		swap(arr, lessThan, left);
		// notice that since we swapped lessThan with left, now the invariant has changed to:
		// arr[left, lessThan - 1] < 0, arr[lessThan, greaterThan - 1] == 0, arr[greaterThan, right] > 0
		return new int[]{lessThan, greaterThan};
	}
	
	private static <E extends Comparable<E>> void swap(E[] arr, int i, int j){
		if(i == j)
			return;
		E e = arr[i];
		arr[i] = arr[j];
		arr[j] = e;
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
	
	public static void test_hyper_threshold_insertion(){
		double least_cost = 0;
		int best_c = 0;
		for(int c = 3; c <= 25; c++){
			HYPER_THRESHOLD_INSERTION = c;
			System.out.println("HYPER_THRESHOLD_INSERTION=" + c);
			double cost = test();
			if(least_cost == 0 || cost < least_cost){
				least_cost = cost;
				best_c = c;
			}
		}
		System.out.println("Best Hyper Threshold=" + best_c);
		HYPER_THRESHOLD_INSERTION = 15;
	}
	
	public static double test(){
		int repeat = 10;
		int n = 100000;
		return Performance.test(new Performance.TestFunction(){
			@Override
			public Object preRun(){
				return ArrayGenerator.randomArray(n);
			}
			
			@Override
			public Object runTest(int testIndex, Object preRunObject){
				QuickSort.sort((Integer[]) preRunObject);
				return preRunObject;
			}
			
			@Override
			public boolean postRun(Object runObject){
				return ArrayGenerator.isSorted((Integer[]) runObject);
			}
		}, "Quick Sort n=" + n, repeat, false);
	}
	
	public static void compare(){
		int repeat = 10;
		int n = 100000;
		final Integer[][] arr1 = new Integer[repeat][n];
		final Integer[][] arr2 = new Integer[repeat][n];
		final Integer[][] arr3 = new Integer[repeat][n];
		final Integer[][] arr4 = new Integer[repeat][n];
		for(int i = 0; i < repeat; i++){
			Integer[] randomArr = ArrayGenerator.randomArray(n);
			System.arraycopy(randomArr, 0, arr1[i], 0, n);
			System.arraycopy(randomArr, 0, arr2[i], 0, n);
			System.arraycopy(randomArr, 0, arr3[i], 0, n);
			System.arraycopy(randomArr, 0, arr4[i], 0, n);
		}
		System.out.println(String.format("Test Started repeat=%d n=%d!", repeat, n));
		Performance.test((i, e) -> {
			MergeSort.sort(arr1[i]);
			return ArrayGenerator.isSorted(arr1[i]);
		}, "Merge Sort          ", repeat, false);
		Performance.test((i, e) -> {
			QuickSort.sortV1(arr2[i]);
			return ArrayGenerator.isSorted(arr2[i]);
		}, "Quick Sort V1       ", repeat, false);
		Performance.test((i, e) -> {
			QuickSort.sortTwoWay(arr3[i]);
			return ArrayGenerator.isSorted(arr3[i]);
		}, "Quick Sort Two Way  ", repeat, false);
		Performance.test((i, e) -> {
			QuickSort.sortThreeWay(arr4[i]);
			return ArrayGenerator.isSorted(arr4[i]);
		}, "Quick Sort Three Way", repeat, false);
	}
	
	public static void testOnSorted(){
		int repeat = 10;
		int n = 100000;
		final Integer[][] arr1 = new Integer[repeat][n];
		final Integer[][] arr2 = new Integer[repeat][n];
		final Integer[][] arr3 = new Integer[repeat][n];
		final Integer[][] arr4 = new Integer[repeat][n];
		for(int i = 0; i < repeat; i++){
			Integer[] orderedArr = ArrayGenerator.orderedArray(n);
			Integer[] randomArr = ArrayGenerator.randomArray(n);
			System.arraycopy(orderedArr, 0, arr1[i], 0, n);
			System.arraycopy(orderedArr, 0, arr2[i], 0, n);
			System.arraycopy(randomArr, 0, arr3[i], 0, n);
			System.arraycopy(randomArr, 0, arr4[i], 0, n);
		}
		System.out.println(String.format("Test Started repeat=%d n=%d!", repeat, n));
		Performance.test((i, e) -> {
			MergeSort.sort(arr1[i]);
			return ArrayGenerator.isSorted(arr1[i]);
		}, "Merge Sort On Sorted Array", repeat, false);
		Performance.test((i, e) -> {
			QuickSort.sortV1(arr2[i]);
			return ArrayGenerator.isSorted(arr2[i]);
		}, "Quick Sort On Sorted Array", repeat, false);
		Performance.test((i, e) -> {
			MergeSort.sort(arr3[i]);
			return ArrayGenerator.isSorted(arr3[i]);
		}, "Merge Sort On Random Array", repeat, false);
		Performance.test((i, e) -> {
			QuickSort.sortV1(arr4[i]);
			return ArrayGenerator.isSorted(arr4[i]);
		}, "Quick Sort On Random Array", repeat, false);
	}
	
	public static void testOnFill(){
		int repeat = 10;
		int n = 1000000;
		final Integer[][] arr1 = new Integer[repeat][n];
		final Integer[][] arr2 = new Integer[repeat][n];
		final Integer[][] arr3 = new Integer[repeat][n];
		Integer[] fillArr = ArrayGenerator.fillArray(n, 0);
		for(int i = 0; i < repeat; i++){
			System.arraycopy(fillArr, 0, arr1[i], 0, n);
			System.arraycopy(fillArr, 0, arr2[i], 0, n);
			System.arraycopy(fillArr, 0, arr3[i], 0, n);
		}
//		Performance.test((i, e) -> {
//			QuickSort.sortV1(arr1[i]);
//			return ArrayGenerator.isSorted(arr1[i]);
//		}, "Quick Sort On Fill Array           n=" + n, repeat, false);
		Performance.test((i, e) -> {
			QuickSort.sortTwoWay(arr2[i]);
			return ArrayGenerator.isSorted(arr2[i]);
		}, "Quick Sort Two Way On Fill Array   n=" + n, repeat, false);
		Performance.test((i, e) -> {
			QuickSort.sortThreeWay(arr3[i]);
			return ArrayGenerator.isSorted(arr3[i]);
		}, "Quick Sort Three Way On Fill Array n=" + n, repeat, false);
	}
	
	public static void main(String[] args){
		compare();
	}
	
	public static Integer[] generateExtremeCase(int n, PivotPositionGenerator g){
		Integer[] arr = new Integer[n];
		putPivot(arr, 0, n - 1, 0, g);
		return arr;
	}
	
	private static void putPivot(Integer[] arr, int left, int right, int i, PivotPositionGenerator g){
		if(left > right)
			return;
		int pivot = g.get(left, right);
		arr[pivot] = i;
		// just like quick sort, we swap the pivot with left point
		swap(arr, left, pivot);
		// now we recurse on the right portion
		putPivot(arr, left + 1, right, i + 1, g);
		// we need to reverse the action we did, and put the pivot back
		swap(arr, pivot, left);
	}
	
	public interface PivotPositionGenerator{
		
		int get(int left, int right);
	}
	
	public static class VariablePivotQuickSort{
		
		private VariablePivotQuickSort(){}
		
		public static <E extends Comparable<E>> void sort(E[] arr, PivotPositionGenerator g){
			sort(arr, 0, arr.length - 1, g);
		}
		
		
		private static <E extends Comparable<E>> void sort(E[] arr, int left, int right, PivotPositionGenerator g){
			if(right - left <= HYPER_THRESHOLD_INSERTION){
				partialInsertionSort(arr, left, right);
				return;
			}
			int p = partition(arr, left, right, g);
			
			sort(arr, left, p - 1, g);
			sort(arr, p + 1, right, g);
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
		
		private static <E extends Comparable<E>> int partition(E[] arr, int left, int right, PivotPositionGenerator g){
			// int pivot = left; we don't always choose left as pivot, we use a random index
			int pivot = g.get(left, right);
			swap(arr, left, pivot);
			int j = left;
			for(int i = j + 1; i <= right; i++){
				// loop invariant: arr[left + 1, j] < arr[pivot], arr[j + 1, i - 1] >= arr[pivot]
				if(arr[i].compareTo(arr[left]) < 0){
					// we should move it to left part, otherwise we should maintain it's position,
					// because it naturally falls into the right part
					// we swap i with j
					j++;
					swap(arr, i, j);
				}
			}
			// now we swap pivot with j
			swap(arr, left, j);
			return j;
		}
		
		private static <E extends Comparable<E>> void swap(E[] arr, int i, int j){
			if(i == j)
				return;
			E e = arr[i];
			arr[i] = arr[j];
			arr[j] = e;
		}
		
		public static void test(){
			final Random random = new Random();
			PivotPositionGenerator leftPivot = ((left, right) -> (left + right) / 2);
			PivotPositionGenerator middlePivot = ((left, right) -> (left + right) / 2);
			PivotPositionGenerator rightPivot = ((left, right) -> (left + right) / 2);
			PivotPositionGenerator randomPivot = ((left, right) -> left + random.nextInt(right - left + 1));
			int repeat = 10;
			int n = 100000;
			final Integer[][] arr1 = new Integer[repeat][n];
			final Integer[][] arr2 = new Integer[repeat][n];
			final Integer[][] arr3 = new Integer[repeat][n];
			final Integer[][] arr4 = new Integer[repeat][n];
			
			System.out.println(String.format("Test Started repeat=%d n=%d!", repeat, n));
			for(int i = 0; i < repeat; i++){
				Integer[] randomArr = ArrayGenerator.randomArray(n);
				System.arraycopy(randomArr, 0, arr1[i], 0, n);
				System.arraycopy(randomArr, 0, arr2[i], 0, n);
				System.arraycopy(randomArr, 0, arr3[i], 0, n);
				System.arraycopy(randomArr, 0, arr4[i], 0, n);
			}
			System.out.println("Test : Random  Array");
			Performance.test((i, e) -> {
				VariablePivotQuickSort.sort(arr1[i], leftPivot);
				return ArrayGenerator.isSorted(arr1[i]);
			}, "Quick Sort Pivot Left    ", repeat, false);
			Performance.test((i, e) -> {
				VariablePivotQuickSort.sort(arr2[i], middlePivot);
				return ArrayGenerator.isSorted(arr2[i]);
			}, "Quick Sort Pivot Middle  ", repeat, false);
			Performance.test((i, e) -> {
				VariablePivotQuickSort.sort(arr3[i], rightPivot);
				return ArrayGenerator.isSorted(arr3[i]);
			}, "Quick Sort Pivot Right   ", repeat, false);
			Performance.test((i, e) -> {
				VariablePivotQuickSort.sort(arr4[i], randomPivot);
				return ArrayGenerator.isSorted(arr4[i]);
			}, "Quick Sort Pivot Random  ", repeat, false);
			
			for(int i = 0; i < repeat; i++){
				Integer[] orderedArr = ArrayGenerator.orderedArray(n);
				System.arraycopy(orderedArr, 0, arr1[i], 0, n);
				System.arraycopy(orderedArr, 0, arr2[i], 0, n);
				System.arraycopy(orderedArr, 0, arr3[i], 0, n);
				System.arraycopy(orderedArr, 0, arr4[i], 0, n);
			}
			System.out.println("Test : Sorted  Array");
			Performance.test((i, e) -> {
				VariablePivotQuickSort.sort(arr1[i], leftPivot);
				return ArrayGenerator.isSorted(arr1[i]);
			}, "Quick Sort Pivot Left    ", repeat, false);
			Performance.test((i, e) -> {
				VariablePivotQuickSort.sort(arr2[i], middlePivot);
				return ArrayGenerator.isSorted(arr2[i]);
			}, "Quick Sort Pivot Middle  ", repeat, false);
			Performance.test((i, e) -> {
				VariablePivotQuickSort.sort(arr3[i], rightPivot);
				return ArrayGenerator.isSorted(arr3[i]);
			}, "Quick Sort Pivot Right   ", repeat, false);
			Performance.test((i, e) -> {
				VariablePivotQuickSort.sort(arr4[i], randomPivot);
				return ArrayGenerator.isSorted(arr4[i]);
			}, "Quick Sort Pivot Random  ", repeat, false);
			
			for(int i = 0; i < repeat; i++){
				System.arraycopy(generateExtremeCase(n, leftPivot), 0, arr1[i], 0, n);
				System.arraycopy(generateExtremeCase(n, middlePivot), 0, arr2[i], 0, n);
				System.arraycopy(generateExtremeCase(n, rightPivot), 0, arr3[i], 0, n);
				System.arraycopy(generateExtremeCase(n, randomPivot), 0, arr4[i], 0, n);
			}
			System.out.println("Test : Extreme Array");
			Performance.test((i, e) -> {
				VariablePivotQuickSort.sort(arr1[i], leftPivot);
				return ArrayGenerator.isSorted(arr1[i]);
			}, "Quick Sort Pivot Left    ", repeat, false);
			Performance.test((i, e) -> {
				VariablePivotQuickSort.sort(arr2[i], middlePivot);
				return ArrayGenerator.isSorted(arr2[i]);
			}, "Quick Sort Pivot Middle  ", repeat, false);
			Performance.test((i, e) -> {
				VariablePivotQuickSort.sort(arr3[i], rightPivot);
				return ArrayGenerator.isSorted(arr3[i]);
			}, "Quick Sort Pivot Right   ", repeat, false);
			Performance.test((i, e) -> {
				VariablePivotQuickSort.sort(arr4[i], randomPivot);
				return ArrayGenerator.isSorted(arr4[i]);
			}, "Quick Sort Pivot Random  ", repeat, false);
		}
		
		public static void main(String[] args){
			VariablePivotQuickSort.test();
		}
	}
}
