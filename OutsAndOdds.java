package holdemevaluator;

/*- ******************************************************************************
* This Class contains methods that deal with outs and odds.
* Public methods
* 		int outsFromHandIndex(int index)
* 
 * 
 * @author PEAK_
****************************************************************************** */
public class OutsAndOdds implements Constants {

	/*-  *****************************************************************************
	 * For calculating outs and odds
	 * Index into all of these arrays is the same as OneHand.drawAndMadeArray
	 ***************************************************************************** */
	static final int[] outsArray = { 6, 3, 2, 2, 2, 2, 2, 4, 5, 6, 8, 9, 10, 11, 13, 17, 15, 4, 7, 0, 0, 0, 0, 0, 0 };
	static final double[] outsToOddsArrayFlopToTurn = { 0, 2.1, 4.3, 6.4, 8.5, 10.6, 12.8, 14.9, 17.0, 19.1, 21.3, 23.4,
			25.5, 27.7, 29.8, 31.9, 34.0, 36.2, 38.3, 40.4, 42.6 };
	static final double[] outsToOddsArrayTurnToRiver = { 0, 2.2, 4.3, 6.5, 8.7, 10.9, 13.0, 15.2, 17.4, 19.6, 21.7,
			23.9, 26.1, 28.3, 30.4, 32.6, 34.8, 37.0, 39.1, 41.3, 43.5 };
	static final double[] outsToOddsArrayFlopToRiver = { 0, 4.3, 8.4, 12.5, 16.5, 20.3, 24.1, 27.8, 31.5, 35.0, 38.4,
			41.7, 45.0, 48.1, 51.2, 54.1, 57.0, 59.8, 62.4, 65.0, 67.5 };

	private OutsAndOdds() {
		throw new IllegalStateException("Utility class");
	}

	/*-
	 * Return the number of outs for a hand in OneHanddrawAndMadeArray
	 * 
	* outsArray is the number of outs for each hand in OneHand.drawAndMadeArray
	* We use that, indexed by Arg0, to find the number of outs for a specific
	* hand. Almost all hands have some kind of draw.
	*  
	 * Arg0 - Index into drawAndMadeArray
	 * Returns the number of outs
	 */
	static int outsFromHandIndex(int index) {
		return outsArray[index];
	}

	/*-
	* Return the odds for a hand in OneHanddrawAndMadeArray Flop to Turn
	* 
	* outsToOddsArrayFlopToTurn is an array containing the odds each 
	* hand in OneHand.drawAndMadeArray
	* Faster to look up then to calculate.
	* 
	* Arg0 - Index into drawAndMadeArray
	* Returns the odds
	*/
	static double oddsForFlopToTurn(int index) {
		return outsToOddsArrayFlopToTurn[outsArray[index]];
	}

	/*-
	* Return the odds for a hand in OneHanddrawAndMadeArray Flop to River
	* 
	* outsToOddsArrayFlopToRiver is an array containing the odds each 
	* hand in OneHand.drawAndMadeArray
	* Faster to look up then to calculate.
	* 
	* Arg0 - Index into drawAndMadeArray
	* Returns the odds
	*/
	static double oddsForFlopToRiver(int index) {
		return outsToOddsArrayFlopToRiver[outsArray[index]];
	}

	/*-
	* Return the odds for a hand in OneHanddrawAndMadeArray Turn to River
	* 
	* outsToOddsArrayTurnToRiver is an array containing the odds each 
	* hand in OneHand.drawAndMadeArray
	* Faster to look up then to calculate. 
	* 
	* Arg0 - Index into drawAndMadeArray
	* Returns the odds
	*/
	static double oddsForTurnToRiver(int index) {
		return outsToOddsArrayTurnToRiver[outsArray[index]];
	}

	/*-**********************************************************************************************
	* Based on the MADE array. Outs are to make this hand or to make a draw.
	* MADE_ and DRAW_ values are used as indexes.
	*************************************************************************************************/
	static int[] outsArrayFlop = new int[MADE_SIZE + DRAW_SIZE];
	static int[] outsArrayTurn = new int[MADE_SIZE + DRAW_SIZE];
	static int[] oddsArrayFlopToTurn = new int[MADE_SIZE + DRAW_SIZE];
	static int[] oddsArrayFlopToRiver = new int[MADE_SIZE + DRAW_SIZE];
	static int[] oddsArrayTurnToRiver = new int[MADE_SIZE + DRAW_SIZE];
	static int[] oddsArrayTurn = new int[MADE_SIZE + DRAW_SIZE];

	/*-**********************************************************************************************
	* This method will calculate the number of outs and odds after all evaluations 
	* are completed.
	* It uses the arrays in the AnalysisOfMadeAndDraw hands  such as typeArraySimMadeFlop.
	* Only data developed using preflop simulation.
	* An array. outsArray is constructed. The number of outs are used to calculate 
	* odds, Flop to Turn, Flop to River, and Turn to River.
	*************************************************************************************************/
	static void outsAndOdds() {

	}

}
