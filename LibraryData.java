package holdemevaluator;

public class LibraryData {
	/*-  *****************************************************************************
	 * This Class contains global values used in the library.
	 * Data previously defined in EvalData has been moved here because EvalData 
	 * is unique to the Hold'emEvaluator project. 
	 * Data here is not unique to a specific Hold'em.... project.
	 * 
	 *
	 * @author PEAK_
	 ***************************************************************************** */
	static String applicationDirectory = "C:\\ApplicationData\\"; // Directory for this application // files
	/*- Seed for Random. Change this number for an all new pseudorandom series of cards */
	static long seed = 223112234;

	private LibraryData() {
		throw new IllegalStateException("Utility class");
	}
}
