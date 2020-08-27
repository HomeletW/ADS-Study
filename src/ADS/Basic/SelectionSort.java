package ADS.Basic;

import ADS.ArrayGenerator;
import ADS.Performance;

public class SelectionSort{
	
	private SelectionSort(){}
	
	public static <E extends Comparable<E>> void sort(E[] data){
		for(int i = 0; i < data.length; i++){
			// loop invariant: data[0]~data[i] is sorted
			// iterate the leftover to find the smallest element's index
			int smallIndex = i;
			for(int j = smallIndex + 1; j < data.length; j++){
				if(data[j].compareTo(data[smallIndex]) < 0){
					smallIndex = j;
				}
			}
			// now we have find the smallest one, we swap the current index(i) with the smallest element
			// so the loop invariant is maintained.
			E temp = data[i];
			data[i] = data[smallIndex];
			data[smallIndex] = temp;
		}
	}
	
	public static <E extends Comparable<E>> void sortReverted(E[] data){
		for(int i = data.length - 1; i >= 0; i--){
			// loop invariant: data[i]~data[data.length - 1] is sorted
			// iterate the leftover to find the largest element's index
			int largeIndex = i;
			for(int j = largeIndex - 1; j >= 0; j--){
				if(data[j].compareTo(data[largeIndex]) > 0){
					largeIndex = j;
				}
			}
			// now we have find the largest one, we swap the current index(i) with the largest element
			// so the loop invariant is maintained.
			E temp = data[i];
			data[i] = data[largeIndex];
			data[largeIndex] = temp;
		}
	}
	
	public static void main(String[] args){
		int repeat = 100;
		int n = 10000;
		Performance.test(new Performance.TestFunction(){
			@Override
			public Object preRun(){
				return ArrayGenerator.randomArray(n);
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
		}, "Selection Sort n=" + n, repeat, true);
	}
}
