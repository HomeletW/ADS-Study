package ADS.Basic;

import ADS.ArrayGenerator;
import ADS.Performance;

public class LinearSearch{
	
	/** Private Constructor to prevent direct instance */
	private LinearSearch(){}
	
	public static <E> int search(E[] data, E target){
		for(int i = 0; i < data.length; i++){
			if(data[i].equals(target)){
				return i;
			}
		}
		return -1;
	}
	
	public static void main(String[] args){
		int n = 1000000;
		Integer[] data = ArrayGenerator.orderedArray(n);
		
		Performance.test((testIndex, obj)->{
			LinearSearch.search(data, n);
			return true;
		}, "Linear Search n=" + n, 100, true);
	}
}
