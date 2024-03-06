package holdemevaluator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*-  ******************************************************************************
*  An instance of this Class must be created for every array that is being analyzed. 
* 1. Draws  
* 2. Made Hands
* 3. Winning Showdown hands. 
* 
* The difference between instances is basically the board index such as HML.
* Examples:
* 			 allIndexFlop, 	hmlIndexFlop, hmlIndexTurn, hmlIndexRiver, 
* 			wetDryIndexFlop,  typeOf1755IndexFlop, flop1755IndexFlop,  SCBPIndexFlop,  
* 
* There are no public methods in this class.
* The constructor uses arguments that are arrays in IndexArrays and from these arrays 
* creates several other arrays in this class, all derived only from the original 3.
* These additional arrays are for convenience in analysis.
* Two classes are the users of this Class:
* 1. IndexArrayReport
* 2. IndexArrayAnalysis
*
* The first index int the arrays is some integer created from analyzing the board 
* or analyzing the hand ( board + hole cards )
* For example: HML is an index created by looking at the board cards and assigning 
* a value based on each cards value, high H is A - 10, Middle M is 9 -6, 
* and  low L is 5 - 2.
*
* This class is not unique to any index type or corresponding array dimensions.
* Instead it can be used to analyze any existing or yet to be imagined. 
*
* @author PEAK_
 ****************************************************************************** */

public class IndexArrayDrawMadeWin implements Constants {

	/*- ***************************************************************************************** 
	Ideas TODO
	If you missed the flop: In this case, the optimal play depends on a variety of factors such as
	 your position, the number of opponents, their playing styles, and the betting action. 
	 You may consider bluffing if you were the pre-flop aggressor, especially if the flop is 
	 likely to have missed your opponents' ranges.
	
	If you have a draw: On a neutral board, you may have a decent draw such as a gutshot straight draw
	 or a flush draw. The general strategy is to continue with the hand if the pot odds justify it,
	  possibly through a semi-bluff or a call.
	
	If you have a pair (top pair or otherwise): With a pair on a neutral board, you should consider 
	the strength of your pair and the potential draws available. If you have top pair with a good 
	kicker or an overpair, you should generally bet for value. If you have a weaker pair, you might 
	want to play more cautiously, possibly checking or calling rather than betting or raising.
	
	
	Sure, the following Java function takes as input a Monte Carlo simulation result, represented as a 
	probability of winning, and returns a simple recommended action (CHECK, CALL, RAISE, or FOLD).
	Note that this method oversimplifies the strategic nuances of poker and doesn't consider the current
	pot size, the bet to you, the number of players left to act, your stack size, or opponent tendencies, 
	but it can serve as a basic guideline.
	
	public class MonteCarloPokerStrategy {
	
	public enum Action {
	    CHECK, CALL, RAISE, FOLD
	}
	
	public static void main(String[] args) {
	    // testing
	    System.out.println(recommendAction(0.8)); // returns RAISE
	}
	
	public static Action recommendAction(double winProbability) {
	    if (winProbability > 0.75) {
	        return Action.RAISE;
	    } else if (winProbability > 0.5) {
	        return Action.CALL;
	    } else if (winProbability > 0.25) {
	        return Action.CHECK;
	    } else {
	        return Action.FOLD;
	    }
	}
	}
	This function interprets the winning probability in a straightforward way:
	
	If the winning probability is over 75%, it recommends raising, since your hand is strong.
	If the winning probability is between 50% and 75%, it recommends calling, as your hand is 
	likely competitive but not dominant.
	If the winning probability is between 25% and 50%, it recommends checking, since your hand
	might have potential but is currently weak.
	If the winning probability is below 25%, it recommends folding, as your hand is likely 
	not worth investing more chips.
	Again, this is a simplistic interpretation and should not be the sole determinant of your 
	poker strategy. Real game situations require a nuanced understanding of many additional factors.
	
	Calculating winning probability based on flop draws and made:
	If board is dry and hand or draw then probability good.
	If board is dry and no hand or draw then probability poor.
	If board is wet and hand or draw then probability good for draw, fair for hand.
	If board is wet and no hand or draw then probability poor.
	if board is neutral and hand or draw then probability poor for draw, good for hand.
	if board is neutral and no hand or draw then probability poor.
	
	Calculating winning probability based on flop and showdown winning hand:
	If made hand and that hand wins at showdown. TODO
	Need an array that relates hand on flop to win at showdown combined with index.
	So HML on flop, made and and showdown for that made hand
	
	public class IndexArrayStrategy implements Constants {
	boolean haveNothing = true;
	boolean haveMade = true;
	boolean haveDraw = true;
	boolean haveShowdown = true;
	boolean havePair = true;
	boolean haveTopPair = true;
	boolean haveWeakerPair = true;
	boolean haveGutshotDraw = true;
	boolean haveFlushDraw = true;
	boolean haveStraightDraw = true;
	
	Range Assessment: The results of your simulation can help you assess the range of hands 
	that your opponent could have. If a particular hand combination frequently wins in your 
	simulations, you can adjust your strategy to account for the possibility that your 
	opponent may have this hand.
	
	Bluffing Decisions: The results of your simulation can inform your bluffing decisions. 
	For example, if your simulations show that you have a low chance of winning the hand 
	with your current cards, you might choose to bluff, especially if your simulation 
	also suggests that your opponent is likely to have a weak hand.
	
	Betting Decisions: The results of your simulation can guide your betting decisions. 
	If your simulation shows a high probability of winning, you may choose to bet aggressively.
	Conversely, if your simulation shows a low probability of winning, you may choose to 
	bet conservatively or fold.
	
	Opponent Modeling: By combining the results of your simulations with observations about 
	your opponents' behavior, you can model how they play and make decisions that take advantage 
	of their weaknesses. If an opponent frequently folds in response to aggressive betting, 
	for example, you might choose to bet aggressively when your simulation shows that you
	have a moderately strong hand.
	
	Adaptation: Your simulation results can help you adapt your strategy as the game progresses.
	If your simulations suggest that certain hand combinations are particularly advantageous 
	or not advantageous in the current game context, you can adapt your strategy to prioritize 
	or avoid these combinations.
		
	Certainly! Building on the understanding that you'll be utilizing the results of the 
	simulation to make strategic decisions, another way to characterize the flop could 
	be by considering Strength Level and Potential Hand Range.
	
	Strength Level: This categorizes the flop based on the perceived strength of your 
	hand relative to potential hands your opponents might have.
	
	Strong: The flop indicates a strong hand, such as a made hand or a very strong draw.
	Moderate: The flop suggests a moderately strong hand or a decent draw.
	Weak: The flop indicates a weak hand or a draw with limited potential.
	Potential Hand Range: This evaluates the range of possible hands that the 
	flop can complete or improve.
	
	Narrow: The flop narrows down the potential hand range, limiting the possible 
	combinations that your opponents might hold.
	Moderate: The flop provides moderate information about the potential hand range.
	Wide: The flop doesn't significantly narrow down the potential hand range, leaving it wide open.
	 ***************************************************************************************** */

	// Constants
	public static final int HML_SIZE = 10;
	public static final int DRAW_SIZE = 3;
	public static final int MADE_SIZE = 3;
	// Index values for draw and made types.
	public static final int MADE_NONE = 0;
	public static final int MADE_BOARD_PAIR = 1;
	public static final int MADE_BOTTOM_PAIR = 2;
	public static final int DRAW_GUTSHOT = 0;
	public static final int DRAW_STRAIGHT = 1;
	public static final int DRAW_OESD = 2;
	// HML arrays
	public static double[][] hmlDraw = new double[HML_SIZE][DRAW_SIZE];
	public static double[][] hmlMade = new double[HML_SIZE][MADE_SIZE];
	// Simple copy of arrays
	int[][] drawArray;
	int[][] madeArray;
	int[][] showdownArray;
	// Converted to percentage of all values in array
	double[][] drawArrayPer;
	double[][] madeArrayPer;
	double[][] showdownArrayPer;
	// Array row and column names
	String[] allArrayRowNames;
	String[] drawArrayColNames;
	String[] madeArrayColNames;
	// Sorted list for each array and reversed
	List<PercentageEntry> drawEntries = new ArrayList<>();
	List<PercentageEntry> madeEntries = new ArrayList<>();
	List<PercentageEntry> showdownEntries = new ArrayList<>();
	List<PercentageEntry> drawEntriesReversed = new ArrayList<>();
	List<PercentageEntry> madeEntriesReversed = new ArrayList<>();
	List<PercentageEntry> showdownEntriesReversed = new ArrayList<>();
	// Sorted list for each array and reversed
	List<PairEntry> drawTotalRowPerEntries = new ArrayList<>();
	List<PairEntry> madeTotalRowPerEntries = new ArrayList<>();
	List<PairEntry> showdownTotalRowPerEntries = new ArrayList<>();
	List<PairEntry> drawTotalColPerEntries = new ArrayList<>();
	List<PairEntry> madeTotalColPerEntries = new ArrayList<>();
	List<PairEntry> showdownTotalColPerEntries = new ArrayList<>();
	// And reversed
	// Sorted list for each array and reversed
	List<PairEntry> drawTotalRowPerEntriesReversed = new ArrayList<>();
	List<PairEntry> madeTotalRowPerEntriesReversed = new ArrayList<>();
	List<PairEntry> showdownTotalRowPerEntriesReversed = new ArrayList<>();
	List<PairEntry> drawTotalColPerEntriesReversed = new ArrayList<>();
	List<PairEntry> madeTotalColPerEntriesReversed = new ArrayList<>();
	List<PairEntry> showdownTotalColPerEntriesReversed = new ArrayList<>();
	// Totals of rows and/or columns for the 3 index arrays
	double[] drawRowTotalPer;
	double[] drawColTotalPer;
	double[] madeRowTotalPer;
	double[] madeColTotalPer;
	double[] showdownRowTotalPer;
	double[] showdownColTotalPer;
	double[] drawRowTotalNormalized;
	double[] drawColTotalNormalized;
	double[] madeRowTotalNormalized;
	double[] madeColTotalNormalized;
	double[] showdownRowTotalNormalized;
	double[] showdownColTotalNormalized;
	int drawSumOfAllValues;
	int madeSumOfAllValues;
	int showdownSumOfAllValues;
	// Totals of rows and/or columns for the 4 index arrays
	double[] drawToMadeRiverRowTotalPer;
	double[] drawToMadeRiverColTotalPer;
	double[] madeToMadeRiverRowTotalPer;
	double[] madeToMadeRiverColTotalPer;
	double[] drawToMadeWonRowTotalPer;
	double[] drawToMadeWonColTotalPer;
	double[] madeToMadeWonRowTotalPer;
	double[] madeToMadeWonColTotalPer;
	// top 3 high and low row percentages
	int[] bestDrawRows = new int[3];
	int[] worstDrawRows = new int[3];
	int[] bestMadeRows = new int[3];
	int[] worstMadeRows = new int[3];
	int[] bestShowdownRows = new int[3];
	int[] worstShowdownRows = new int[3];
	// top 3 high and low col percentages
	int[] bestDrawCols = new int[3];
	int[] worstDrawCols = new int[3];
	int[] bestMadeCols = new int[3];
	int[] worstMadeCols = new int[3];
	int[] bestShowdownCols = new int[3];
	int[] worstShowdownCols = new int[3];
	// Percentage value
	double[] bestDrawRowsPer = new double[3];
	double[] worstDrawRowsPer = new double[3];
	double[] bestMadeRowsPer = new double[3];
	double[] worstMadeRowsPer = new double[3];
	double[] bestShowdownRowsPer = new double[3];
	double[] worstShowdownRowsPer = new double[3];
	// Percentage Value
	double[] bestDrawColsPer = new double[3];
	double[] worstDrawColsPer = new double[3];
	double[] bestMadeColsPer = new double[3];
	double[] worstMadeColsPer = new double[3];
	double[] bestShowdownwColsPer = new double[3];
	double[] worstShowdownColsPer = new double[3];
	// 5 best in entire array row and column
	int[] bestDraw5Row = new int[5];
	int[] bestDraw5Col = new int[5];
	double[] bestDraw5Per = new double[5];
	int[] bestMade5Row = new int[5];
	int[] bestMade5Col = new int[5];
	double[] bestMade5Per = new double[5];
	int[] bestShowdown5Row = new int[5];
	int[] bestShowdown5Col = new int[5];
	double[] bestShowdown5Per = new double[5];
	// 5 worst in entire array row and column
	int[] worstDraw5Row = new int[5];
	int[] worstDraw5Col = new int[5];
	double[] worstDraw5Per = new double[5];
	int[] worstMade5Row = new int[5];
	int[] worstMade5Col = new int[5];
	double[] worstMade5Per = new double[5];
	int[] worstShowdown5Row = new int[5];
	int[] worstShowdown5Col = new int[5];
	double[] worstShowdown5Per = new double[5];
	Map<FlopType, Map<HandType, StrategyPercentages>> strategyMatrixA = new EnumMap<>(FlopType.class);
	Map<FlopType, Map<HandType, StrategyPercentages>> strategyMatrix = new EnumMap<>(FlopType.class);

	/*- **************************************************************************** 
	* Constructor.
	* The arguments are index arrays for draws, made hands, and showdown winning made hands.
	*   index arrays for made hands or draws of flop or turn to made hands on river.
	*   index arrays for made hands or draws of flop or turn to made hands on showdown that won.
	*   index arrays for row names, draw column names, and made column names.
	
	* Make a local copy of index arrays and create new arrays from these.
	* Create 3 sorted arrays, same data just sorted
	* Create 3 arrays with totals from rows.
	* Create 3 arrays with totals from columns.
	* 
	* Create 4 TODO
	*   
	* Relative hand value is like wet / dry. 
	*   A high relative value corresponds approximately  to a wet board. 
	*   It means that there are a lot of possible draws and possible made hands.
	
	*   A low relative value corresponds approximately  to a dry board. 
	*   It means that there are very few of possible draws and very few possible made hands.
	*
	*   A medium value corresponds approximately to board that is neutral, not wet or dry
	*
	*   Strategy suggestions are about the same as with wet / dry.
	****************************************************************************** */

// Sample from Class that creates an instance of this Class
//	final var hmlFlop = new IndexArrayDrawMadeWin(IndexArrays.hmlDrawFlop, IndexArrays.hmlMadeFlop,
//			IndexArrays.hmlShowdownFlop, HML_FLOP_ST, DRAW_ST, MADE_ST);

	/*- **************************************************************************************
	* Constructor
	* Three arrays, result of analyzing many hands,  Draw, Made, and Showdown.
	* Three String arrays, names of columns
	* Arrays are in the Class IndexArrays.
	* Indexes are calculated by the Class IndexArrayClassification
	***************************************************************************************** */
	IndexArrayDrawMadeWin(int[][] drawArray, int[][] madeArray, int[][] showdownArray, String[] rowNames,
			String[] drawColNames, String[] madeColNames) {

		this.drawArray = new int[drawArray.length][drawArray[0].length];
		this.madeArray = new int[madeArray.length][madeArray[0].length];
		this.showdownArray = new int[showdownArray.length][showdownArray[0].length];
		this.allArrayRowNames = new String[rowNames.length];
		this.drawArrayColNames = new String[drawColNames.length];
		this.madeArrayColNames = new String[madeColNames.length];

		this.drawArrayPer = new double[this.drawArray.length][this.drawArray[0].length];
		this.madeArrayPer = new double[this.madeArray.length][this.madeArray[0].length];
		this.showdownArrayPer = new double[this.showdownArray.length][this.showdownArray[0].length];

		this.drawRowTotalPer = new double[this.drawArray.length];
		this.madeRowTotalPer = new double[this.madeArray.length];
		this.showdownRowTotalPer = new double[this.showdownArray.length];

		this.drawColTotalPer = new double[this.drawArray[0].length];
		this.madeColTotalPer = new double[this.madeArray[0].length];
		this.showdownColTotalPer = new double[this.showdownArray[0].length];

		this.drawRowTotalNormalized = new double[this.drawArray.length];
		this.madeRowTotalNormalized = new double[this.madeArray.length];
		this.showdownRowTotalNormalized = new double[this.showdownArray.length];

		this.drawColTotalNormalized = new double[this.drawArray[0].length];
		this.madeColTotalNormalized = new double[this.madeArray[0].length];
		this.showdownColTotalNormalized = new double[this.showdownArray[0].length];

		CopyArrays.copyArray(madeArray, this.madeArray);
		CopyArrays.copyArray(drawArray, this.drawArray);
		CopyArrays.copyArray(showdownArray, this.showdownArray);

		CopyArrays.copyArray(rowNames, this.allArrayRowNames);
		CopyArrays.copyArray(drawColNames, this.drawArrayColNames);
		CopyArrays.copyArray(madeColNames, this.madeArrayColNames);

		this.madeSumOfAllValues = 0;
		this.drawSumOfAllValues = 0;
		this.showdownSumOfAllValues = 0;

		calculateArrays();
		createIndexArrayList();
		createIndexArrayRowAndColumnList();
		findBestAndWorst();
		findBestAndWorstOverall();
	}

	/*- **************************************************************************************
	* This method will:
	* calculate sum of all values for 3 indexArrays
	* calculate percentages for 3 indexArray percentages 
	* calculate sum of rows ans sum of columns for 3 indexArrays
	* Helper to constructor  
	***************************************************************************************** */
	private void calculateArrays() {
		for (int i = 0; i < this.drawArray.length; i++) {
			for (int j = 0; j < this.drawArray[0].length; j++) {
				this.drawSumOfAllValues += this.drawArray[i][j];
			}
		}
		for (int i = 0; i < this.madeArray.length; i++) {
			for (int j = 0; j < this.madeArray[0].length; j++) {
				this.madeSumOfAllValues += this.madeArray[i][j];
			}
		}
		for (int i = 0; i < this.showdownArray.length; i++) {
			for (int j = 0; j < this.showdownArray[0].length; j++) {
				this.showdownSumOfAllValues += this.showdownArray[i][j];
			}
		}

		for (int i = 0; i < this.drawArray.length; i++) {
			for (int j = 0; j < this.drawArray[0].length; j++) {
				this.drawArrayPer[i][j] = ((double) this.drawArray[i][j] / (double) this.drawSumOfAllValues) * 100.0;
			}
		}
		for (int i = 0; i < this.madeArray.length; i++) {
			for (int j = 0; j < this.madeArray[0].length; j++) {
				this.madeArrayPer[i][j] = ((double) this.madeArray[i][j] / (double) this.madeSumOfAllValues * 100.0);
			}
		}
		for (int i = 0; i < this.showdownArray.length; i++) {
			for (int j = 0; j < this.showdownArray[0].length; j++) {
				this.showdownArrayPer[i][j] = ((double) this.showdownArray[i][j] / (double) this.showdownSumOfAllValues)
						* 100.0;
			}
		}

		// Column totals
		for (int i = 0; i < this.drawArrayPer.length; i++) {
			for (int j = 0; j < this.drawArrayPer[0].length; j++) {
				this.drawColTotalPer[j] += this.drawArrayPer[i][j];
			}
		}
		for (int i = 0; i < this.madeArrayPer.length; i++) {
			for (int j = 0; j < this.madeArrayPer[0].length; j++) {
				this.madeColTotalPer[j] += this.madeArrayPer[i][j];
			}
		}
		for (int i = 0; i < this.showdownArrayPer.length; i++) {
			for (int j = 0; j < this.showdownArrayPer[0].length; j++) {
				this.showdownColTotalPer[j] += this.showdownArrayPer[i][j];
			}
		}
		// row totals
		for (int i = 0; i < this.drawArrayPer.length; i++) {
			for (int j = 0; j < this.drawArrayPer[0].length; j++) {
				this.drawRowTotalPer[i] += this.drawArrayPer[i][j];
			}
		}
		for (int i = 0; i < this.madeArrayPer.length; i++) {
			for (int j = 0; j < this.madeArrayPer[0].length; j++) {
				this.madeRowTotalPer[i] += this.madeArrayPer[i][j];
			}
		}
		for (int i = 0; i < this.showdownArrayPer.length; i++) {
			for (int j = 0; j < this.showdownArrayPer[0].length; j++) {
				showdownRowTotalPer[i] += showdownArrayPer[i][j];
			}
		}
		// Now create normalized arrays
		this.drawColTotalNormalized = normalizePercentages(this.drawColTotalPer);
		this.madeColTotalNormalized = normalizePercentages(this.madeColTotalPer);
		this.showdownColTotalNormalized = normalizePercentages(this.showdownColTotalPer);
		this.drawRowTotalNormalized = normalizePercentages(this.drawRowTotalPer);
		this.madeRowTotalNormalized = normalizePercentages(this.madeRowTotalPer);
		this.showdownRowTotalNormalized = normalizePercentages(this.showdownRowTotalPer);
	}

	/*- **************************************************************************************
	* This method create list for the 3 indexArray types.
	* Helper to constructor  
	***************************************************************************************** */
	private void createIndexArrayList() {
		for (int row = 0; row < drawArrayPer.length; row++) {
			for (int column = 0; column < drawArrayPer[0].length; column++) {
				drawEntries.add(new PercentageEntry(drawArrayPer[row][column], row, column));
			}
		}
		drawEntries.sort(Comparator.comparing(PercentageEntry::getPercentage));

		for (int row = 0; row < madeArrayPer.length; row++) {
			for (int column = 0; column < madeArrayPer[row].length; column++) {
				madeEntries.add(new PercentageEntry(madeArrayPer[row][column], row, column));
			}
		}
		madeEntries.sort(Comparator.comparing(PercentageEntry::getPercentage));

		for (int row = 0; row < showdownArrayPer.length; row++) {
			for (int column = 0; column < showdownArrayPer[0].length; column++) {
				showdownEntries.add(new PercentageEntry(showdownArrayPer[row][column], row, column));
			}
		}
		showdownEntries.sort(Comparator.comparing(PercentageEntry::getPercentage));

		// Now reversed
		for (int row = 0; row < drawArrayPer.length; row++) {
			for (int column = 0; column < drawArrayPer[0].length; column++) {
				drawEntriesReversed.add(new PercentageEntry(drawArrayPer[row][column], row, column));
			}
		}
		drawEntriesReversed.sort(Comparator.comparing(PercentageEntry::getPercentage).reversed());

		for (int row = 0; row < madeArrayPer.length; row++) {
			for (int column = 0; column < madeArrayPer[row].length; column++) {
				madeEntriesReversed.add(new PercentageEntry(madeArrayPer[row][column], row, column));
			}
		}
		madeEntriesReversed.sort(Comparator.comparing(PercentageEntry::getPercentage).reversed());

		for (int row = 0; row < showdownArrayPer.length; row++) {
			for (int column = 0; column < showdownArrayPer[0].length; column++) {
				showdownEntriesReversed.add(new PercentageEntry(showdownArrayPer[row][column], row, column));
			}
		}
		showdownEntriesReversed.sort(Comparator.comparing(PercentageEntry::getPercentage).reversed());
	}

	/*- **************************************************************************************
	 * This method create list for the 3 indexArray types fot the rows and columns that
	 * have thr row or column totals.
	 * Helper to constructor  
	 ***************************************************************************************** */
	private void createIndexArrayRowAndColumnList() {
		for (int row = 0; row < drawRowTotalPer.length; row++) {
			drawTotalRowPerEntries.add(new PairEntry(drawRowTotalPer[row], row));
		}
		drawTotalRowPerEntries.sort(Comparator.comparing(PairEntry::getPercentage));

		for (int row = 0; row < madeRowTotalPer.length; row++) {
			madeTotalRowPerEntries.add(new PairEntry(madeRowTotalPer[row], row));
		}
		madeTotalRowPerEntries.sort(Comparator.comparing(PairEntry::getPercentage));

		for (int row = 0; row < showdownRowTotalPer.length; row++) {
			showdownTotalRowPerEntries.add(new PairEntry(showdownRowTotalPer[row], row));
		}
		showdownTotalRowPerEntries.sort(Comparator.comparing(PairEntry::getPercentage));

		for (int col = 0; col < drawColTotalPer.length; col++) {
			drawTotalColPerEntries.add(new PairEntry(drawColTotalPer[col], col));
		}
		drawTotalColPerEntries.sort(Comparator.comparing(PairEntry::getPercentage));

		for (int col = 0; col < madeColTotalPer.length; col++) {
			madeTotalColPerEntries.add(new PairEntry(madeColTotalPer[col], col));
		}
		madeTotalColPerEntries.sort(Comparator.comparing(PairEntry::getPercentage));

		for (int col = 0; col < showdownColTotalPer.length; col++) {
			showdownTotalColPerEntries.add(new PairEntry(showdownColTotalPer[col], col));
		}
		showdownTotalColPerEntries.sort(Comparator.comparing(PairEntry::getPercentage));

		// Now reversed
		for (int row = 0; row < drawRowTotalPer.length; row++) {
			drawTotalRowPerEntriesReversed.add(new PairEntry(drawRowTotalPer[row], row));
		}
		drawTotalRowPerEntriesReversed.sort(Comparator.comparing(PairEntry::getPercentage).reversed());
		for (int row = 0; row < madeRowTotalPer.length; row++) {
			madeTotalRowPerEntriesReversed.add(new PairEntry(madeRowTotalPer[row], row));
		}
		madeTotalRowPerEntriesReversed.sort(Comparator.comparing(PairEntry::getPercentage).reversed());

		for (int row = 0; row < showdownRowTotalPer.length; row++) {
			showdownTotalRowPerEntriesReversed.add(new PairEntry(showdownRowTotalPer[row], row));
		}
		showdownTotalRowPerEntriesReversed.sort(Comparator.comparing(PairEntry::getPercentage).reversed());

		for (int col = 0; col < drawColTotalPer.length; col++) {
			drawTotalColPerEntriesReversed.add(new PairEntry(drawColTotalPer[col], col));
		}
		drawTotalColPerEntriesReversed.sort(Comparator.comparing(PairEntry::getPercentage).reversed());

		for (int col = 0; col < madeColTotalPer.length; col++) {
			madeTotalColPerEntriesReversed.add(new PairEntry(madeColTotalPer[col], col));
		}
		madeTotalColPerEntriesReversed.sort(Comparator.comparing(PairEntry::getPercentage).reversed());

		for (int col = 0; col < showdownColTotalPer.length; col++) {
			showdownTotalColPerEntriesReversed.add(new PairEntry(showdownColTotalPer[col], col));
		}
		showdownTotalColPerEntriesReversed.sort(Comparator.comparing(PairEntry::getPercentage).reversed());
	}

	/*- **************************************************************************************
	* This method will find the best and worst percentages
	* The 3 best and the 3 worst are found
	* Helper to constructor 
	*  Find best draw columns
	* Look through the sorted array list for the 3 columns with the best percentage
	***************************************************************************************** */
	private void findBestAndWorst() {
		for (int i = 0; i < 3; i++) {
			bestDrawRowsPer[i] = drawTotalRowPerEntriesReversed.get(i + 1).getPercentage();
			bestDrawRows[i] = drawTotalRowPerEntriesReversed.get(i + 1).getRowOrCol();

			worstDrawRowsPer[i] = drawTotalRowPerEntries.get(i + 1).getPercentage();
			worstDrawRows[i] = drawTotalRowPerEntries.get(i + 1).getRowOrCol();

			bestMadeRowsPer[i] = madeTotalRowPerEntriesReversed.get(i + 1).getPercentage();
			bestMadeRows[i] = madeTotalRowPerEntriesReversed.get(i + 1).getRowOrCol();

			worstMadeRowsPer[i] = madeTotalRowPerEntries.get(i + 1).getPercentage();
			worstMadeRows[i] = madeTotalRowPerEntries.get(i + 1).getRowOrCol();

			bestDrawColsPer[i] = drawTotalColPerEntriesReversed.get(i + 1).getPercentage();
			bestDrawCols[i] = drawTotalColPerEntriesReversed.get(i + 1).getRowOrCol();

			worstDrawColsPer[i] = drawTotalColPerEntries.get(i + 1).getPercentage();
			worstDrawCols[i] = drawTotalColPerEntries.get(i + 1).getRowOrCol();

			bestMadeColsPer[i] = madeTotalColPerEntriesReversed.get(i + 1).getPercentage();
			bestMadeCols[i] = madeTotalColPerEntriesReversed.get(i + 1).getRowOrCol();

			worstMadeColsPer[i] = madeTotalColPerEntries.get(i + 1).getPercentage();
			worstMadeCols[i] = madeTotalColPerEntries.get(i + 1).getRowOrCol();

			bestShowdownRowsPer[i] = showdownTotalRowPerEntriesReversed.get(i + 1).getPercentage();
			bestShowdownRows[i] = drawTotalRowPerEntriesReversed.get(i + 1).getRowOrCol();

			worstShowdownRowsPer[i] = drawTotalRowPerEntries.get(i + 1).getPercentage();
			worstShowdownRows[i] = drawTotalRowPerEntries.get(i + 1).getRowOrCol();

			bestShowdownwColsPer[i] = showdownTotalColPerEntriesReversed.get(i + 1).getPercentage();
			bestShowdownCols[i] = showdownTotalColPerEntriesReversed.get(i + 1).getRowOrCol();

			worstShowdownColsPer[i] = showdownTotalColPerEntries.get(i + 1).getPercentage();
			worstShowdownCols[i] = showdownTotalColPerEntries.get(i + 1).getRowOrCol();
		}

	}

	/*- **************************************************************************************
	* This method will find the best and worst percentages
	* The 5 best and the 5 worst are found.
	* Looks at complete array, not single rows or columns.
	* Helper to constructor 
	* Skip column 0 for best because it in MADE_NONE or DRAW_NONE
	***************************************************************************************** */
	private void findBestAndWorstOverall() {
		for (int i = 0; i < 5; i++) {
			bestDraw5Per[i] = drawEntriesReversed.get(i + 1).getPercentage();
			bestDraw5Row[i] = drawEntriesReversed.get(i + 1).getRow();
			bestDraw5Col[i] = drawEntriesReversed.get(i + 1).getCol();

			worstDraw5Per[i] = drawEntries.get(i).getPercentage();
			worstDraw5Row[i] = drawEntries.get(i).getRow();
			worstDraw5Col[i] = drawEntries.get(i).getCol();

			bestMade5Per[i] = madeEntriesReversed.get(i + 1).getPercentage();
			bestMade5Row[i] = madeEntriesReversed.get(i + 1).getRow();
			bestMade5Col[i] = madeEntriesReversed.get(i + 1).getCol();

			worstMade5Per[i] = madeEntries.get(i).getPercentage();
			worstMade5Row[i] = madeEntries.get(i).getRow();
			worstMade5Col[i] = madeEntries.get(i).getCol();

			bestShowdown5Per[i] = showdownEntriesReversed.get(i + 1).getPercentage();
			bestShowdown5Row[i] = showdownEntriesReversed.get(i + 1).getRow();
			bestShowdown5Col[i] = showdownEntriesReversed.get(i + 1).getCol();

			worstShowdown5Per[i] = showdownEntries.get(i).getPercentage();
			worstShowdown5Row[i] = showdownEntries.get(i).getRow();
			worstShowdown5Col[i] = showdownEntries.get(i).getCol();
		}
	}

	/*-**********************************************************************************
	 * To normalize percentages across different row counts, you can calculate the
	 *  proportionate values instead of absolute percentages. 
	 *  This approach ensures that the relative values remain consistent, regardless 
	 *  of the number of rows. 
	 * The normalizePercentages method takes an array of percentages
	 * and the row count as input. It calculates the sum of percentages and then divides
	 * each percentage by the sum. Finally, it multiplies by 100 and divides by the row
	 * count to obtain the normalized percentages. The result is an array of normalized
	 * percentages that can be compared regardless of the row count.
	 * Arg0 - Array of percentages
	 * Returns a double array
	 ********************************************************************************** */
	private static double[] normalizePercentages(double[] percentages) {
		final double sum = Arrays.stream(percentages).sum();
		final var normalized = new double[percentages.length];
		for (var i = 0; i < percentages.length; i++) {
			normalized[i] = (percentages[i] / sum) * 100.0 / percentages.length;
		}
		return normalized;
	}

	/*- **************************************************************************************
	* This method will return smallest percentage entry
	***************************************************************************************** */
	private PercentageEntry getSmallest(List<PercentageEntry> entries) {
		if (entries.isEmpty()) {
			return null; // or throw an exception, depending on your preference
		}
		entries.sort(Comparator.comparing(PercentageEntry::getPercentage));
		return entries.get(0);
	}

	/*- **************************************************************************************
	* This method will return largest percentage entry
	***************************************************************************************** */
	private PercentageEntry getLargest(List<PercentageEntry> entries) {
		if (entries.isEmpty()) {
			return null; // or throw an exception, depending on your preference
		}
		entries.sort(Comparator.comparing(PercentageEntry::getPercentage));
		return entries.get(entries.size() - 1);
	}

	/*- **************************************************************************************
	* This method searches the 3 indexArray types.
	* 
	***************************************************************************************** */
	private void searchIndexArrayList() {
		final double targetPercentage = 0.5; // Replace with the percentage you're searching for
		final List<PercentageEntry> matchingEntries = drawEntries.stream()
				.filter(entry -> Math.abs(entry.getPercentage() - targetPercentage) < 0.0001)
				.collect(Collectors.toList());

		final List<PairEntry> matchingEntriesX = drawTotalRowPerEntries.stream()
				.filter(entry -> Math.abs(entry.getPercentage() - targetPercentage) < 0.0001)
				.collect(Collectors.toList());
	}

	/*- **************************************************************************************
	* This method will get entries in sort order
	*  
	***************************************************************************************** */
	private void indexArrayListInSortedOrder() {
		drawTotalRowPerEntries.stream().mapToInt(PairEntry::getRowOrCol).forEach(r -> {
		});
	}

	/*- **************************************************************************************
	* Get percentage for row and column
	*  
	***************************************************************************************** */
	private double getPercentageByRowAndColumn(int targetRow, int targetColumn) {
		for (var entry : drawEntries) {
			if (entry.getRow() == targetRow && entry.getCol() == targetColumn) {
				return entry.getPercentage();
			}
		}
		throw new IllegalArgumentException("Invalid row and/or column.");
	}

	/*- **************************************************************************************
	* Evaluate row totals and column totals 
	***************************************************************************************** */
	private void evaluateTotals() {
		// TODO
	}

	/*- ***************************************************************************************** 
	 Create a String describing strategy
	***************************************************************************************** */
	private String stringStrategy() {
		var st = new StringBuilder().append("The best Draw rows were ").append(Format.formatPer(bestDrawRowsPer[0]))
				.append(" ").append(allArrayRowNames[bestDrawRows[0]]).append(", ")
				.append(Format.formatPer(bestDrawRowsPer[1])).append(" ").append(allArrayRowNames[bestDrawRows[1]])
				.append(", ").append(Format.formatPer(bestDrawRowsPer[2])).append(" ")
				.append(allArrayRowNames[bestDrawRows[2]]).append("\r\n").toString();
		st += new StringBuilder().append("The worst Draw rows were ").append(Format.formatPer(worstDrawRowsPer[0]))
				.append(" ").append(allArrayRowNames[worstDrawRows[0]]).append(", ")
				.append(Format.formatPer(worstDrawRowsPer[1])).append(" ").append(allArrayRowNames[worstDrawRows[1]])
				.append(", ").append(Format.formatPer(worstDrawRowsPer[2])).append(" ")
				.append(allArrayRowNames[worstDrawRows[2]]).append("\r\n").toString();
		st += new StringBuilder().append("The best Made rows were ").append(Format.formatPer(bestMadeRowsPer[0]))
				.append(" ").append(allArrayRowNames[bestMadeRows[0]]).append(", ")
				.append(Format.formatPer(bestMadeRowsPer[1])).append(" ").append(allArrayRowNames[bestMadeRows[1]])
				.append(", ").append(Format.formatPer(worstMadeRowsPer[2])).append(" ")
				.append(allArrayRowNames[bestMadeRows[2]]).append("\r\n").toString();
		st += new StringBuilder().append("The worst Made rows were ").append(Format.formatPer(bestMadeRowsPer[0]))
				.append(" ").append(allArrayRowNames[worstMadeRows[0]]).append(", ")
				.append(Format.formatPer(bestMadeRowsPer[1])).append(" ").append(allArrayRowNames[worstMadeRows[1]])
				.append(Format.formatPer(bestMadeRowsPer[2])).append(" ").append(", ")
				.append(allArrayRowNames[worstMadeRows[2]]).append("\r\n").toString();

		st += new StringBuilder().append("The best Showdownw rows were ")
				.append(Format.formatPer(bestShowdownRowsPer[0])).append(" ")
				.append(allArrayRowNames[bestShowdownRows[0]]).append(", ")
				.append(Format.formatPer(bestShowdownRowsPer[1])).append(allArrayRowNames[bestShowdownRows[1]])
				.append(Format.formatPer(bestShowdownRowsPer[2])).append(", ")
				.append(allArrayRowNames[bestShowdownRows[2]]).append("\r\n").toString();
		st += new StringBuilder().append("The worst Showdown rows were ")
				.append(Format.formatPer(worstShowdownRowsPer[0])).append(allArrayRowNames[worstShowdownRows[0]])
				.append(", ").append(Format.formatPer(worstShowdownRowsPer[1]))
				.append(allArrayRowNames[worstShowdownRows[1]]).append(Format.formatPer(worstShowdownRowsPer[2]))
				.append(", ").append(allArrayRowNames[worstShowdownRows[2]]).append("\r\n\r\n").toString();

		st += new StringBuilder().append("The best Draw cols were ").append(Format.formatPer(bestDrawColsPer[0]))
				.append(" ").append(drawArrayColNames[bestDrawCols[0]]).append(", ")
				.append(Format.formatPer(bestDrawColsPer[0])).append(drawArrayColNames[bestDrawCols[1]]).append(", ")
				.append(Format.formatPer(bestDrawColsPer[1])).append(" ").append(drawArrayColNames[bestDrawCols[2]])
				.append("\r\n").toString();
		st += new StringBuilder().append("The worst Draw cols were ").append(Format.formatPer(worstDrawColsPer[0]))
				.append(" ").append(drawArrayColNames[worstDrawCols[0]]).append(", ")
				.append(Format.formatPer(worstDrawColsPer[1])).append(" ").append(drawArrayColNames[worstDrawCols[1]])
				.append(", ").append(Format.formatPer(worstDrawColsPer[2])).append(" ")
				.append(drawArrayColNames[worstDrawCols[2]]).append("\r\n").toString();

		st += new StringBuilder().append("The bes Made cols were ").append(Format.formatPer(bestMadeColsPer[0]))
				.append(" ").append(madeArrayColNames[bestMadeCols[0]]).append(", ")
				.append(Format.formatPer(bestMadeColsPer[1])).append(" ").append(madeArrayColNames[bestMadeCols[1]])
				.append(", ").append(Format.formatPer(bestMadeColsPer[2])).append(" ")
				.append(madeArrayColNames[bestMadeCols[2]]).append("\r\n").toString();
		st += new StringBuilder().append("The worst Made cols were ").append(Format.formatPer(worstMadeColsPer[0]))
				.append(" ").append(madeArrayColNames[worstMadeCols[0]]).append(", ")
				.append(Format.formatPer(worstMadeColsPer[1])).append(" ").append(madeArrayColNames[worstMadeCols[1]])
				.append(Format.formatPer(worstMadeColsPer[2])).append(" ").append(", ")
				.append(madeArrayColNames[worstMadeCols[2]]).append("\r\n").toString();
		st += new StringBuilder().append("The best Showdownw cols were ")
				.append(Format.formatPer(bestShowdownwColsPer[0])).append(" ")
				.append(madeArrayColNames[bestShowdownCols[0]]).append(", ")
				.append(Format.formatPer(bestShowdownwColsPer[1])).append(" ")
				.append(madeArrayColNames[bestShowdownCols[1]]).append(Format.formatPer(bestShowdownwColsPer[2]))
				.append(" ").append(", ").append(madeArrayColNames[bestShowdownCols[2]]).append("\r\n").toString();
		st += new StringBuilder().append("The worst Showdown cols were ")
				.append(Format.formatPer(worstShowdownColsPer[0])).append(" ")
				.append(madeArrayColNames[worstShowdownCols[0]]).append(", ")
				.append(Format.formatPer(worstShowdownColsPer[1])).append(" ")
				.append(madeArrayColNames[worstShowdownCols[1]]).append(", ")
				.append(Format.formatPer(worstShowdownColsPer[2])).append(" ")
				.append(madeArrayColNames[worstShowdownCols[2]]).append("\r\n\r\n").toString();
		st += new StringBuilder().append("The drawSumOfAllValues was ").append(drawSumOfAllValues).append("\r\n")
				.toString();
		st += new StringBuilder().append("The madeSumOfAllValues was ").append(madeSumOfAllValues).append("\r\n")
				.toString();
		st += new StringBuilder().append("The showdownSumOfAllValues was ").append(showdownSumOfAllValues)
				.append("\r\n\r\n").toString();
		st += new StringBuilder().append("The best 5 draws were \r\n").append(Format.formatPer(bestDraw5Per[0]))
				.append(" ").append(allArrayRowNames[bestDraw5Row[0]]).append(", ")
				.append(drawArrayColNames[bestDraw5Col[1]]).append("\r\n").append(Format.formatPer(bestDraw5Per[1]))
				.append(" ").append(allArrayRowNames[bestDraw5Row[1]]).append(", ")
				.append(drawArrayColNames[bestDraw5Col[0]]).append("\r\n").append(Format.formatPer(bestDraw5Per[2]))
				.append(" ").append(allArrayRowNames[bestDraw5Row[2]]).append(", ")
				.append(drawArrayColNames[bestDraw5Col[2]]).append("\r\n").append(Format.formatPer(bestDraw5Per[3]))
				.append(" ").append(allArrayRowNames[bestDraw5Row[3]]).append(", ")
				.append(drawArrayColNames[bestDraw5Col[3]]).append("\r\n").append(Format.formatPer(bestDraw5Per[4]))
				.append(" ").append(allArrayRowNames[bestDraw5Row[4]]).append(", ")
				.append(drawArrayColNames[bestDraw5Col[4]]).append("\r\n").toString();
		st += new StringBuilder().append("The worstt 5 draws were \r\n").append(Format.formatPer(worstDraw5Per[0]))
				.append(" ").append(allArrayRowNames[worstDraw5Row[0]]).append(", ")
				.append(drawArrayColNames[worstDraw5Col[1]]).append("\r\n").append(Format.formatPer(worstDraw5Per[1]))
				.append(" ").append(allArrayRowNames[worstDraw5Row[1]]).append(", ")
				.append(drawArrayColNames[worstDraw5Col[0]]).append("\r\n").append(Format.formatPer(worstDraw5Per[2]))
				.append(" ").append(allArrayRowNames[worstDraw5Row[2]]).append(", ")
				.append(drawArrayColNames[worstDraw5Col[2]]).append("\r\n").append(Format.formatPer(worstDraw5Per[3]))
				.append(" ").append(allArrayRowNames[worstDraw5Row[3]]).append(", ")
				.append(drawArrayColNames[worstDraw5Col[3]]).append("\r\n").append(Format.formatPer(worstDraw5Per[4]))
				.append(" ").append(allArrayRowNames[worstDraw5Row[4]]).append(", ")
				.append(drawArrayColNames[worstDraw5Col[4]]).append("\r\n").toString();

		return st;
	}

	// The method to calculate the HML score.
	public static double calculateHMLScore(int hmlIndex) {
		double score = 0;
		// Iterate over all types of made hands.
		for (int i = 0; i < MADE_SIZE; i++) {
			// Add the proportion of this type of hand that are made to the score.
			score += hmlMade[hmlIndex][i];
		}

		// Iterate over all types of draws.
		for (int i = 0; i < DRAW_SIZE; i++) {
			// Add the proportion of this type of draw to the score.
			score += hmlDraw[hmlIndex][i];
		}

		// Convert the score to a percentage (0 - 100 scale).
		score = score / (MADE_SIZE + DRAW_SIZE) * 100;

		return score;
	}

	public void PokerStrategy() {
		for (var flopType : FlopType.values()) {
			final Map<HandType, StrategyPercentages> handTypeMap = new EnumMap<>(HandType.class);
			strategyMatrix.put(flopType, handTypeMap);
			for (var handType : HandType.values()) {
				handTypeMap.put(handType, new StrategyPercentages(0, 0, 0, 0)); // default values
			}
		}

		// Fill in the actual strategy percentages
		strategyMatrix.get(FlopType.WET).get(HandType.NONE).fold = 70;
		strategyMatrix.get(FlopType.WET).get(HandType.NONE).bluff = 20;
		// ... and so on for all the other strategy percentages
	}

	public StrategyPercentages getStrategy(FlopType flopType, HandType handType) {
		return strategyMatrix.get(flopType).get(handType);
	}

	public void PokerStrategyA() {
		// ... Initialize the strategyMatrix as in the previous code
	}

	public StrategyPercentages getStrategy(FlopType flopType, HandType handType, PositionType position) {
		final var strategyPercentages = strategyMatrix.get(flopType).get(handType);

		switch (position) {
		case EARLY -> {
			// Reduce bluff and raise percentages, increase fold and call percentages
			strategyPercentages.bluff -= 10;
			strategyPercentages.raise -= 10;
			strategyPercentages.fold += 10;
			strategyPercentages.call += 10;
		}
		case MIDDLE -> {
			// Slightly adjust percentages
			strategyPercentages.bluff -= 5;
			strategyPercentages.raise += 5;
		}
		case LATE, HEADSUP_LAST -> {
			// Increase bluff and raise percentages, decrease fold and call percentages
			strategyPercentages.bluff += 10;
			strategyPercentages.raise += 10;
			strategyPercentages.fold -= 10;
			strategyPercentages.call -= 10;
		}
		case HEADSUP_FIRST -> {
			// Similar to EARLY position, decrease aggression
			strategyPercentages.bluff -= 10;
			strategyPercentages.raise -= 10;
			strategyPercentages.fold += 10;
			strategyPercentages.call += 10;
		}
		}

		// Make sure percentages are in valid range [0, 100]
		strategyPercentages.fold = Math.min(100, Math.max(0, strategyPercentages.fold));
		strategyPercentages.bluff = Math.min(100, Math.max(0, strategyPercentages.bluff));
		strategyPercentages.call = Math.min(100, Math.max(0, strategyPercentages.call));
		strategyPercentages.raise = Math.min(100, Math.max(0, strategyPercentages.raise));

		return strategyPercentages;
	}

	public StrategyPercentages getStrategy(FlopType flopType, HandType handType, PlayerContext context) {
		final var strategyPercentages = strategyMatrix.get(flopType).get(handType);

		// Adjust based on position, like before...
		// ...

		// Adjust based on stack size
		if (context.stackSize < 10) {
			strategyPercentages.bluff -= 10;
			strategyPercentages.raise -= 10;
			strategyPercentages.fold += 10;
			strategyPercentages.call += 10;
		} else if (context.stackSize > 100) {
			strategyPercentages.bluff += 10;
			strategyPercentages.raise += 10;
			strategyPercentages.fold -= 10;
			strategyPercentages.call -= 10;
		}

		// Make sure percentages are in valid range [0, 100]
		strategyPercentages.fold = Math.min(100, Math.max(0, strategyPercentages.fold));
		strategyPercentages.bluff = Math.min(100, Math.max(0, strategyPercentages.bluff));
		strategyPercentages.call = Math.min(100, Math.max(0, strategyPercentages.call));
		strategyPercentages.raise = Math.min(100, Math.max(0, strategyPercentages.raise));

		return strategyPercentages;
	}

	/*-
	 Texas holdem no limit 6-max java $1/$2  java
	For a flop rated wet, dry, or neutral
	In this instance a wet flop. In the matrix below fill in the percentages of the time that you would fold, bluff, call, or raise in a typical game. 
	
	Wet     	hand          		fold      	bluff     	call     	raise  
	Yes		none			70%		30%		0%		0%
	Yes		pair			70%		30%		0%		0%
	Yes		2 pair			70%		30%		0%		0%
	Yes		set		  	70%		30%		0%		0%
	Yes		gutshot draw	70%		30%		0%		0%
	Yes		straight draw	70%		30%		0%		0%
	Yes		OESD draw		70%		30%		0%		0%
	Yes		Flush draw		70%		30%		0%		0%
	
	ChatGPT
	When playing poker, one's actions should generally depend on various factors, including the strength of one's hand, the texture of the board (wet or dry), the position, the actions of other players, and the specific tendencies of one's opponents. However, I understand that you're asking for some generalized guidelines.
	
	Remember, the following matrix is a simplification and should not be strictly adhered to. It's always important to consider the specific context of each hand:
	
	Wet hand fold bluff call raise
	Yes none 70% 20% 5% 5%
	Yes pair 30% 10% 40% 20%
	Yes 2 pair 10% 5% 30% 55%
	Yes set 5% 5% 20% 70%
	Yes gutshot draw 40% 30% 20% 10%
	Yes straight draw 30% 25% 35% 10%
	Yes OESD draw 20% 25% 35% 20%
	Yes flush draw 25% 20% 35% 20%
	
	This table reflects that players will generally be more aggressive (more calling, raising)
	with stronger hands (2 pair, set) and more likely to fold with weaker hands or bluffs. 
	Remember, percentages can vary significantly depending on specific situations and players involved.
	
	
	
	
	
	User
	show for dry flop and for neutral flop
	ChatGPT
	Sure, let's fill in a similar matrix for a dry and neutral flop, keeping in mind that the 
	exact percentages can vary based on a multitude of factors including the specific opponents, 
	stack sizes, position, and more.
	
	For a dry board:
	
	Dry hand fold bluff call raise
	Yes none 80% 15% 5% 0%
	Yes pair 25% 5% 60% 10%
	Yes 2 pair 5% 5% 30% 60%
	Yes set 5% 5% 20% 70%
	Yes gutshot draw 60% 30% 10% 0%
	Yes straight draw 50% 25% 20% 5%
	Yes OESD draw 40% 25% 30% 5%
	Yes flush draw 40% 20% 35% 5%
	
	On dry boards, bluffing frequencies can be lower as opponents are less likely to have hit a 
	part of the board, meaning our bluffs will often be successful.
	
	And for a neutral board:
	
	Neutral hand fold bluff call raise
	Yes none 75% 20% 5% 0%
	Yes pair 30% 10% 50% 10%
	Yes 2 pair 10% 5% 30% 55%
	Yes set 5% 5% 25% 65%
	Yes gutshot draw 50% 30% 15% 5%
	Yes straight draw 40% 25% 30% 5%
	Yes OESD draw 30% 20% 40% 10%
	Yes flush draw 35% 15% 40% 10%
	
	Again, these matrices are just generalized guides and your actions should always take 
	into account a wide variety of factors. Poker is a complex game and decisions should 
	not be made solely based on hand strength and flop texture.
	 */
	enum FlopType {
		WET, DRY, NEUTRAL
	}

	enum HandType {
		NONE, PAIR, TWO_PAIR, SET, GUTSHOT_DRAW, STRAIGHT_DRAW, OESD_DRAW, FLUSH_DRAW
	}

	enum PositionType {
		EARLY, MIDDLE, LATE, HEADSUP_FIRST, HEADSUP_LAST
	}

	/*- **************************************************************************************
	* Class that holds all of an IndexArray's data.
	* percentage is the value where a row and column intersect.
	* row is the row number.
	***************************************************************************************** */
	private class PercentageEntry {
		double percentage;
		int row;
		int column;

		private PercentageEntry(double percentage, int row, int column) {
			this.percentage = percentage;
			this.row = row;
			this.column = column;
		}

		// Getters and setters
		private double getPercentage() {
			return this.percentage;
		}

		private int getRow() {
			return this.row;
		}

		private int getCol() {
			return this.column;
		}
	}

	/*- **************************************************************************************
	* Class that holds one column or one row
	* percentage is the value where a row and column intersect.
	* rowOrCol is the row number.
	***************************************************************************************** */
	private class PairEntry {
		double percentage;
		int rowOrCol;

		private PairEntry(double percentage, int rowOrCol) {
			this.percentage = percentage;
			this.rowOrCol = rowOrCol;
		}

		// Getters and setters
		private double getPercentage() {
			return percentage;
		}

		private int getRowOrCol() {
			return rowOrCol;
		}

	}

	/*- ************************************************************************************** 
	 Please note that this method is just a simple example, which may not reflect the exact score 
	 you want to calculate. In real poker game analysis, you might want to weight different types 
	 of hands differently, consider more factors (like the number of players, the position, etc.), 
	 or use more sophisticated statistical methods. You would need to adjust the method accordingly 
	 based on your specific requirements.
	
	The method works by simply adding up the proportions of each type of made hand and draw for the 
	given HML index, then converting this sum to a percentage. 
	This gives a simple measure of how "good" the hand is, with a higher score indicating a better hand. 
	However, this measure is quite simplistic, and may not accurately reflect the actual quality of the
	hand in a real poker game.
	*/

	class StrategyPercentages {
		int fold;
		int bluff;
		int call;
		int raise;

		public StrategyPercentages(int fold, int bluff, int call, int raise) {
			this.fold = fold;
			this.bluff = bluff;
			this.call = call;
			this.raise = raise;
		}
	}

	/*-
	In this case, getStrategy is applying a simple strategy of being less aggressive in early positions 
	and more aggressive in later positions. These are not real poker strategies, just an example 
	of how you can adjust the percentages based on the position. You can define the adjustments 
	based on your poker strategy.
	   
	 */

	class StrategyPercentageX {
		int fold;
		int bluff;
		int call;
		int raise;

		public void StrategyPercentagesX(int fold, int bluff, int call, int raise) {
			this.fold = fold;
			this.bluff = bluff;
			this.call = call;
			this.raise = raise;
		}
	}

	class PlayerContext {
		PositionType position;
		int stackSize;
		// ... You could add more fields here to represent other factors.

		public PlayerContext(PositionType position, int stackSize /* , ... */) {
			this.position = position;
			this.stackSize = stackSize;
			// ...
		}
	}

	/*-
	In this code:
	
	calculatePotOdds method returns the pot odds as a decimal, which is the call amount divided 
	by the total size of the pot after making the call.
	getPotOddsRatio method returns the pot odds as a ratio, comparing the pot size to the call amount.
	isCallProfitable method checks whether a call would be profitable given a certain probability 
	of winning the hand. If the chance of winning is higher than the calculated pot odds, 
	then the call would be expected to be profitable in the long run.
	 */
	class PotOddsCalculator {
		double potSize;
		double callAmount;

		public PotOddsCalculator(double potSize, double callAmount) {
			this.potSize = potSize;
			this.callAmount = callAmount;
		}

		public double calculatePotOdds() {
			return callAmount / (potSize + callAmount);
		}

		public String getPotOddsRatio() {
			return "1:" + (potSize / callAmount);
		}

		public boolean isCallProfitable(double winningProbability) {
			// If the probability of winning is higher than the pot odds, then the call is
			// profitable.
			return calculatePotOdds() < winningProbability;
		}
	}

	/*-
	 
	 */
	class PokerStrategy {
		// ... The rest of your code here...

		public StrategyPercentages getStrategy(FlopType flopType, HandType handType, PlayerContext context) {
			final var strategyPercentages = strategyMatrix.get(flopType).get(handType);

			// Adjust based on position, like before...
			// ...

			// Adjust based on stack size, like before...
			// ...

			// Adjust based on pot odds
			if (context.potOdds < 0.2) {
				// If pot odds are low, we're more likely to fold or bluff
				strategyPercentages.fold += 10;
				strategyPercentages.bluff += 10;
				strategyPercentages.call -= 10;
				strategyPercentages.raise -= 10;
			} else if (context.potOdds > 0.5) {
				// If pot odds are high, we're more likely to call or raise
				strategyPercentages.fold -= 10;
				strategyPercentages.bluff -= 10;
				strategyPercentages.call += 10;
				strategyPercentages.raise += 10;
			}

			// Make sure percentages are in valid range [0, 100]
			strategyPercentages.fold = Math.min(100, Math.max(0, strategyPercentages.fold));
			strategyPercentages.bluff = Math.min(100, Math.max(0, strategyPercentages.bluff));
			strategyPercentages.call = Math.min(100, Math.max(0, strategyPercentages.call));
			strategyPercentages.raise = Math.min(100, Math.max(0, strategyPercentages.raise));

			return strategyPercentages;
		}

		class PlayerContext {
			PositionType position;
			int stackSize;
			double potOdds; // Added a new field for pot odds
			// ... You could add more fields here to represent other factors.

			public PlayerContext(PositionType position, int stackSize, double potOdds /* , ... */) {
				this.position = position;
				this.stackSize = stackSize;
				this.potOdds = potOdds;
				// ...
			}
		}

		/*-
		
		Sure, let's adjust the strategy based on the pot odds, similar to the way we did for stack size.
		Here's how you can modify the getStrategy method to take into account the pot odds:
		*/

		class PokerStrategyX {
			// ... The rest of your code here...

			public StrategyPercentages getStrategy(FlopType flopType, HandType handType, PlayerContext context) {
				final var strategyPercentages = strategyMatrix.get(flopType).get(handType);

				// Adjust based on position, like before...
				// ...

				// Adjust based on stack size, like before...
				// ...

				// Adjust based on pot odds
				if (context.potOdds < 0.2) {
					// If pot odds are low, we're more likely to fold or bluff
					strategyPercentages.fold += 10;
					strategyPercentages.bluff += 10;
					strategyPercentages.call -= 10;
					strategyPercentages.raise -= 10;
				} else if (context.potOdds > 0.5) {
					// If pot odds are high, we're more likely to call or raise
					strategyPercentages.fold -= 10;
					strategyPercentages.bluff -= 10;
					strategyPercentages.call += 10;
					strategyPercentages.raise += 10;
				}

				// Make sure percentages are in valid range [0, 100]
				strategyPercentages.fold = Math.min(100, Math.max(0, strategyPercentages.fold));
				strategyPercentages.bluff = Math.min(100, Math.max(0, strategyPercentages.bluff));
				strategyPercentages.call = Math.min(100, Math.max(0, strategyPercentages.call));
				strategyPercentages.raise = Math.min(100, Math.max(0, strategyPercentages.raise));

				return strategyPercentages;
			}

			class PlayerContext {
				PositionType position;
				int stackSize;
				double potOdds; // Added a new field for pot odds
				// ... You could add more fields here to represent other factors.

				public PlayerContext(PositionType position, int stackSize, double potOdds /* , ... */) {
					this.position = position;
					this.stackSize = stackSize;
					this.potOdds = potOdds;
					// ...
				}
			}
		}
		/*-
		This example  adds a new  potOdds field  to the PlayerContext class and  adjusts the   strategy percentages
		    in the  getStrategy method   based on  the value  of potOdds.
		    If the  pot odds are low (meaning the player has to risk a lot to win a little), the code increases 
		    the likelihood of folding or bluffing and decreases the likelihood of calling or raising. 
		    Conversely, if the pot odds are
		
		    high (meaning the player can win a lot for a small risk), the code decreases the likelihood of 
		    folding or bluffing and increases the likelihood of calling or raising.
		     */

		class PokerStrategyZ {
			// ... The rest of your code here...

			// TODO caused error public StrategyPercentages getStrategy(FlopType flopType,
			// HandType handType, PlayerContext context) {
			public void getStrategy(FlopType flopType, HandType handType, PlayerContext context) {
				final var strategyPercentages = strategyMatrix.get(flopType).get(handType);

				// Adjust based on position, stack size, and pot odds, like before...
				// ...

				// Adjust based on player type
				final int type = 0;
				switch (type) {
				case FISH -> {
					strategyPercentages.bluff -= 10;
					strategyPercentages.raise += 10;
				}
				case NIT -> {
					strategyPercentages.bluff += 10;
					strategyPercentages.raise -= 10;
				}
				case LAG -> {
					strategyPercentages.fold -= 10;
					strategyPercentages.bluff -= 10;
					strategyPercentages.call += 10;
					strategyPercentages.raise += 10;
				}
				case TAG -> {
					strategyPercentages.fold += 10;
					strategyPercentages.bluff -= 10;
					strategyPercentages.call += 10;
					strategyPercentages.raise -= 10;
				}
				case AVERAGE -> {
				}
				}

				// Suggested action if we have nothing
				// Suggested action if we have a made hand
				// Suggested action if we have a draw

				// Suggested action if we have a pair
				// Suggested action if we have a bottom pair
				// Suggested action if we have a middle pair
				// Suggested action if we have a top pair
				// Suggested action if we have a over pair
				// Suggested action if we have a board pair + under pair
				// Suggested action if we have a board pair + over pair
				// Suggested action if we have a bottom 2 pair
				// Suggested action if we have top and bottom 2 pair
				// Suggested action if we have top 2 pair
				// Suggested action if we have a set

				// Suggested action if we have a draw
				// Suggested action if we have gutshot draw
				// Suggested action if we have Straight draw
				// Suggested action if we have OESD draw
				// Suggested action if we have Flush draw

				// Sum of rows and columns

			}

			class PlayerContext {
				PositionType position;
				int stackSize;
				double potOdds;

				public PlayerContext() {
					this.position = position;
					this.stackSize = stackSize;
					this.potOdds = potOdds;

					// ...
				}
			}

		}
	}

}
