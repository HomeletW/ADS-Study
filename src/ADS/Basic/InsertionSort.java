package ADS.Basic;

import ADS.ArrayGenerator;
import ADS.Performance;

public class InsertionSort{
	
	private InsertionSort(){}
	
	public static <E extends Comparable<E>> void sort1(E[] data){
		for(int i = 0; i < data.length; i++){
			// loop invariant: data[0, i) is sorted
			// the job of the loop is to place data[i] to it's desired position
			int j = i;
			for(; j - 1 >= 0; j--){
				if(data[i].compareTo(data[j - 1]) >= 0){
					// now we find the position where data[i] should be,
					break;
				}
			}
			// j must be smaller or equals to i, if j == i, we don't need the swap
			if(j != i){
				// we shift everything from data[j] ~ data[i - 1] to right by one
				// and fill data[i] in
				E temp = data[i];
				for(int k = i - 1; k >= j; k--){
					data[k + 1] = data[k];
				}
				data[j] = temp;
			}
		}
	}
	
	public static <E extends Comparable<E>> void sort2(E[] data){
		for(int i = 0; i < data.length; i++){
			// loop invariant: data[0, i) is sorted
			// the job of the loop is to place data[i] to it's desired position
			for(int j = i - 1; j >= 0; j--){
				if(data[j].compareTo(data[j + 1]) > 0){
					// we should swap
					E temp = data[j];
					data[j] = data[j + 1];
					data[j + 1] = temp;
				}else{
					// the first time we encountered someone that is smaller or equals to us we break
					break;
				}
			}
		}
	}
	
	public static <E extends Comparable<E>> void sort(E[] data){
		for(int i = 0; i < data.length; i++){
			// loop invariant: data[0, i) is sorted
			// the job of the loop is to place data[i] to it's desired position
			E temp = data[i];
			int j;
			for(j = i; j - 1 >= 0 && temp.compareTo(data[j - 1]) < 0; j--){
				data[j] = data[j - 1];
			}
			data[j] = temp;
		}
	}
	
	public static <E extends Comparable<E>> void sortInverted(E[] data){
		for(int i = data.length - 1; i >= 0; i--){
			E temp = data[i];
			int j;
			for(j = i; j + 1 < data.length && temp.compareTo(data[j + 1]) > 0; j++){
				data[j] = data[j + 1];
			}
			data[j] = temp;
		}
	}
	
	public static void test1(){
		int repeat = 100;
		int n = 10000;
		Performance.test(new Performance.TestFunction(){
			@Override
			public Object preRun(){
				return ArrayGenerator.randomArray(n);
			}
			
			@Override
			public Object runTest(int testIndex, Object preRunObject){
				InsertionSort.sort((Integer[]) preRunObject);
				return preRunObject;
			}
			
			@Override
			public boolean postRun(Object runObject){
				return ArrayGenerator.isSorted((Integer[]) runObject);
			}
		}, "Insertion Sort Best n=" + n, repeat, true);
		
		Performance.test(new Performance.TestFunction(){
			@Override
			public Object preRun(){
				return ArrayGenerator.randomArray(n);
			}
			
			@Override
			public Object runTest(int testIndex, Object preRunObject){
				InsertionSort.sort1((Integer[]) preRunObject);
				return preRunObject;
			}
			
			@Override
			public boolean postRun(Object runObject){
				return ArrayGenerator.isSorted((Integer[]) runObject);
			}
		}, "Insertion Sort1 n=" + n, repeat, true);
		
		Performance.test(new Performance.TestFunction(){
			@Override
			public Object preRun(){
				return ArrayGenerator.randomArray(n);
			}
			
			@Override
			public Object runTest(int testIndex, Object preRunObject){
				InsertionSort.sort2((Integer[]) preRunObject);
				return preRunObject;
			}
			
			@Override
			public boolean postRun(Object runObject){
				return ArrayGenerator.isSorted((Integer[]) runObject);
			}
		}, "Insertion Sort2 n=" + n, repeat, true);
		
		Performance.test(new Performance.TestFunction(){
			@Override
			public Object preRun(){
				return ArrayGenerator.randomArray(n);
			}
			
			@Override
			public Object runTest(int testIndex, Object preRunObject){
				InsertionSort.sortInverted((Integer[]) preRunObject);
				return preRunObject;
			}
			
			@Override
			public boolean postRun(Object runObject){
				return ArrayGenerator.isSorted((Integer[]) runObject);
			}
		}, "Insertion Sort Inverted n=" + n, repeat, true);
	}
	
	public static void test2(){
		Integer[] orderedArr = ArrayGenerator.orderedArray(100000);
		Performance.test(new Performance.TestFunction(){
			@Override
			public Object preRun(){
				return orderedArr;
			}
			
			@Override
			public Object runTest(int testIndex, Object preRunObject){
				InsertionSort.sort((Integer[]) preRunObject);
				return preRunObject;
			}
			
			@Override
			public boolean postRun(Object runObject){
				return ArrayGenerator.isSorted((Integer[]) runObject);
			}
		}, "Insertion Sort", 1, true);
		Performance.test(new Performance.TestFunction(){
			@Override
			public Object preRun(){
				return orderedArr;
			}
			
			@Override
			public Object runTest(int testIndex, Object preRunObject){
				SelectionSort.sortReverted((Integer[]) preRunObject);
				return preRunObject;
			}
			
			@Override
			public boolean postRun(Object runObject){
				return ArrayGenerator.isSorted((Integer[]) runObject);
			}
		}, "Selection Sort", 1, true);
	}
	
	public static void main(String[] args){
		InsertionSort.test1();
	}
	
}
