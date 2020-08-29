package ADS;

public class Performance{
	
	public static String default_start       = "%s | Test Started!\n";
	public static String default_end         = "%s | Test Finished!\n";
	public static String default_fail_end    = "%s | Test Failed!\n";
	public static String default_average     = "%s | Average Runtime after %d repeats : %f Second\n";
	public static String default_format      = "%s | Test %d Success, Time Elapsed: %f Second\n";
	public static String default_fail_format = "%s | Test %d, Test Failed\n";
	
	private Performance(){
	}
	
	public static double test(TestFunction test, String name){
		return Performance.test(test, name, 10, true);
	}
	
	public static double test(TestFunction test, String name, int repeat, boolean print){
		if(print)
			System.out.printf(Performance.default_start, name);
		double[] testStat = new double[repeat];
		for(int testIndex = 0; testIndex < repeat; testIndex++){
			try{
				double perf = Performance.singleTest(test, name, testIndex, print);
				if(perf < 0){
					if(print)
						System.out.printf(Performance.default_fail_end, name);
					return -1;
				}
				testStat[testIndex] = perf;
			}catch(Exception e){
				e.printStackTrace();
				if(print)
					System.out.printf(Performance.default_fail_end, name);
				return -1;
			}
		}
		double averagePerformance = 0;
		if(repeat != 0){
			for(double performance : testStat){
				averagePerformance += performance;
			}
			averagePerformance /= repeat;
			if(print)
				System.out.printf(Performance.default_average, name, repeat, averagePerformance);
		}
		if(print)
			System.out.printf(Performance.default_end, name);
		return averagePerformance;
	}
	
	public static double singleTest(TestFunction test, String name, int testIndex, boolean print){
		Object  preRunObject = test.preRun();
		long    startTime    = System.nanoTime();
		Object  runObject    = test.runTest(testIndex, preRunObject);
		long    endTime      = System.nanoTime();
		boolean success      = test.postRun(runObject);
		if(!success){
			if(print)
				System.out.printf(Performance.default_fail_format, name, testIndex);
			return -1;
		}
		double timeElapsed = (endTime - startTime) / 1000000000.0;
		if(print)
			System.out.printf(Performance.default_format, name, testIndex, timeElapsed);
		return timeElapsed;
	}
	
	public interface TestFunction{
		
		default Object preRun(){
			return null;
		}
		
		Object runTest(int testIndex, Object preRunObject);
		
		default boolean postRun(Object runObject){
			return true;
		}
	}
}
