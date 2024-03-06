package holdemevaluator;

public class CopyArrays implements Constants {
	private CopyArrays() {
		throw new IllegalStateException("Utility class");
	}

	/*- **************************************************************************************
	* Copy array passed ar Argument to local copy of array.
	* Overloaded
	* Used by IndexArrayBoard, IndexArrayDrawMadeWin, and IndexArrayToWinner
	* Just a simple utility 
	 * @author PEAK_
	***************************************************************************************** */
	static void copyArray(int[][][] fromArray, int[][][] toArray) {
		for (int i = 0; i < fromArray.length; i++) {
			for (int j = 0; j < fromArray[0].length; j++) {
				for (int k = 0; k < fromArray[0][0].length; k++) {
					toArray[i][j][k] = fromArray[i][j][k];
				}
			}
		}
	}

	static void copyArray(int[][] fromArray, int[][] toArray) {
		for (int i = 0; i < fromArray.length; i++) {
			for (int j = 0; j < fromArray[0].length; j++) {
				toArray[i][j] = fromArray[i][j];
			}
		}
	}

	static void copyArray(String[][] fromArray, String[][] toArray) {
		for (int i = 0; i < fromArray.length; i++) {
			for (int j = 0; j < fromArray[0].length; j++) {
				toArray[i][j] = fromArray[i][j];
			}
		}
	}

	static void copyArray(String[] fromArray, String[] toArray) {
		for (int i = 0; i < fromArray.length; i++) {
			toArray[i] = fromArray[i];
		}
	}

}
