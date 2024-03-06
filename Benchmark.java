package holdemevaluator;

import java.util.concurrent.TimeUnit;

public class Benchmark implements Constants {
	/*- **************************************************************************** 
	* 
	* @author PEAK_
	*******************************************************************************/

	/*- *****************************************************************************
	 * This method is used in benchmarking performance. 
	 * Used for testing only, never used in the application. 
	 *This method now measures time in evaluateFlop.
	 * Arg0 - Seat number
	  ******************************************************************************/
	private static long elapsed1;

	private static long elapsed2;

	private Benchmark() {
		throw new IllegalStateException("Utility class");
	}

	static void benchmark() {
		long start;
		start = System.nanoTime();
		elapsed1 += TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - start);
		start = System.nanoTime();
		elapsed2 += TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - start);
	}

	/*- *****************************************************************************
	 * This method is used in benchmarking performance. 
	 * Used for testing only, never used in the application. 
	 * Displays results of benchmark
	  ******************************************************************************/
	static void benchmarkResults() {
		final double e1 = elapsed1;
		final double e2 = elapsed2;
		Logger.log("EvaluateAll() benchmark  miliseconds " + e1);
		Logger.log("foldem() benchmark miliseconds  " + e2);
		Logger.log("Ratio foldem vs Evaluate " + e1 / e2);
	}

}
