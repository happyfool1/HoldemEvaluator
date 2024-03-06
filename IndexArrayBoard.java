package holdemevaluator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*-  ******************************************************************************
* This Class is a data holding Class.
*  An instance of this Class must be created for every array that is being analyzed. 
* 1. Boards  
* 2. BoardWins Hands
* 3. Winning Showdown hands. 
* 
* There aee no public methods in this class.
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

public class IndexArrayBoard implements Constants {

	// Simple copy of arrays
	int[][] boardArray;
	int[][] boardWinsArray;
	// Converted to percentage of all values in array
	double[][] boardArrayPer;
	double[][] boardWinsArrayPer;
	// Array row and column names
	String[] rowNames;
	String[] colNames;
	List<PercentageEntry> boardEntries = new ArrayList<>();
	List<PercentageEntry> boardWinsEntries = new ArrayList<>();
	List<PercentageEntry> boardEntriesReversed = new ArrayList<>();
	List<PercentageEntry> boardWinsEntriesReversed = new ArrayList<>();
	// Sorted list for each array and reversed
	List<PairEntry> boardTotalRowPerEntries = new ArrayList<>();
	List<PairEntry> boardWinsTotalRowPerEntries = new ArrayList<>();
	List<PairEntry> boardTotalColPerEntries = new ArrayList<>();
	List<PairEntry> boardWinsTotalColPerEntries = new ArrayList<>();
	// And reversed
	// Sorted list for each array and reversed
	List<PairEntry> boardTotalRowPerEntriesReversed = new ArrayList<>();
	List<PairEntry> boardWinsTotalRowPerEntriesReversed = new ArrayList<>();
	List<PairEntry> showdownTotalRowPerEntriesReversed = new ArrayList<>();
	List<PairEntry> boardTotalColPerEntriesReversed = new ArrayList<>();
	List<PairEntry> boardWinsTotalColPerEntriesReversed = new ArrayList<>();
	List<PairEntry> showdownTotalColPerEntriesReversed = new ArrayList<>();
	double[] boardRowTotalPer;
	double[] boardColTotalPer;
	double[] boardWinsRowTotalPer;
	double[] boardWinsColTotalPer;
	double[] showdownRowTotalPer;
	double[] showdownColTotalPer;
	int boardSumOfAllValues;
	int boardWinsSumOfAllValues;
	// Totals of rows and/or columns for the 4 index arrays
	double[] boardToBoardWinsRiverRowTotalPer;
	double[] boardToBoardWinsRiverColTotalPer;
	double[] boardWinsToBoardWinsRiverRowTotalPer;
	double[] boardWinsToBoardWinsRiverColTotalPer;
	double[] boardToBoardWinsWonRowTotalPer;
	double[] boardToBoardWinsWonColTotalPer;
	double[] boardWinsToBoardWinsWonRowTotalPer;
	double[] boardWinsToBoardWinsWonColTotalPer;
	// top 3 high and low row percentages
	int[] bestBoardRows = new int[3];
	int[] worstBoardRows = new int[3];
	int[] bestBoardWinsRows = new int[3];
	int[] worstBoardWinsRows = new int[3];
	// top 3 high and low col percentages
	int[] bestBoardCols = new int[3];
	int[] worstBoardCols = new int[3];
	int[] bestBoardWinsCols = new int[3];
	int[] worstBoardWinsCols = new int[3];
	// Percentage value
	double[] bestBoardRowsPer = new double[3];
	double[] worstBoardRowsPer = new double[3];
	double[] bestBoardWinsRowsPer = new double[3];
	double[] worstBoardWinsRowsPer = new double[3];
	// Percentage Value
	double[] bestBoardColsPer = new double[3];
	double[] worstBoardColsPer = new double[3];
	double[] bestBoardWinsColsPer = new double[3];
	double[] worstBoardWinsColsPer = new double[3];
	// 5 best in entire array row and column
	int[] bestBoard5Row = new int[5];
	int[] bestBoard5Col = new int[5];
	double[] bestBoard5Per = new double[5];
	int[] bestBoardWins5Row = new int[5];
	int[] bestBoardWins5Col = new int[5];
	double[] bestBoardWins5Per = new double[5];
	// 5 worst in entire array row and column
	int[] worstBoard5Row = new int[5];
	int[] worstBoard5Col = new int[5];
	double[] worstBoard5Per = new double[5];
	int[] worstBoardWins5Row = new int[5];
	int[] worstBoardWins5Col = new int[5];
	double[] worstBoardWins5Per = new double[5];

	// Constructor - not used but required by compiler
	IndexArrayBoard() {

	}

	/*- **************************************************************************** 
	* Constructor.
	* The arguments are index arrays for boards, boardWins hands, and showdown winning boardWins hands.
	*   index arrays for boardWins hands or boards of flop or turn to boardWins hands on river.
	*   index arrays for boardWins hands or boards of flop or turn to boardWins hands on showdown that won.
	*   index arrays for row names, board column names, and boardWins column names.
	
	* Make a local copy of index arrays and create new arrays from these.
	* Create 3 sorted arrays, same data just sorted
	* Create 3 arrays with totals from rows.
	* Create 3 arrays with totals from columns.
	* 
	* Create 4 TODO
	*   
	* Relative hand value is like wet / dry. 
	*   A high relative value corresponds approximately  to a wet board. 
	*   It means that there are a lot of possible boards and possible boardWins hands.
	
	*   A low relative value corresponds approximately  to a dry board. 
	*   It means that there are very few of possible boards and very few possible boardWins hands.
	*
	*   A medium value corresponds approximately to board that is neutral, not wet or dry
	*
	*   Strategy suggestions are about the same as with wet / dry.
	****************************************************************************** */
	IndexArrayBoard(int[][] boardArray, int[][] boardWinsArray, String[] rowNames, String[] colNames) {

		this.boardArray = new int[boardArray.length][boardArray[0].length];
		this.boardWinsArray = new int[boardWinsArray.length][boardWinsArray[0].length];
		this.rowNames = new String[rowNames.length];
		this.colNames = new String[colNames.length];

		this.boardArrayPer = new double[this.boardArray.length][this.boardArray[0].length];
		this.boardWinsArrayPer = new double[this.boardWinsArray.length][this.boardWinsArray[0].length];

		// TODO this.boardRowTotalPer = new double[this.boardArray.length];

		CopyArrays.copyArray(boardArray, this.boardArray);
		CopyArrays.copyArray(boardWinsArray, this.boardWinsArray);
		CopyArrays.copyArray(rowNames, this.rowNames);
		CopyArrays.copyArray(colNames, this.colNames);

		// TODO this.boardWinsSumOfAllValues = 0;

		calculateArrays();
		createIndexArrayList();
		createIndexArrayRowAndColumnList();
		findBestAndWorst();
		findBestAndWorstOverall();
		// System.out.println(stringStrategy());
	}

	/*- **************************************************************************************
	* This method will:
	* calculate sum of all values for 3 indexArrays
	* calculate percentages for 3 indexArray percentages 
	* calculate sum of rows ans sum of columns for 3 indexArrays
	* Helper to constructor  
	***************************************************************************************** */
	private void calculateArrays() {
		for (int i = 0; i < this.boardArray.length; i++) {
			for (int j = 0; j < this.boardArray[0].length; j++) {
				this.boardSumOfAllValues += this.boardArray[i][j];
			}
		}
		for (int i = 0; i < this.boardWinsArray.length; i++) {
			for (int j = 0; j < this.boardWinsArray[0].length; j++) {
				this.boardWinsSumOfAllValues += this.boardWinsArray[i][j];
			}
		}

		for (int i = 0; i < this.boardArray.length; i++) {
			for (int j = 0; j < this.boardArray[0].length; j++) {
				this.boardArrayPer[i][j] = ((double) this.boardArray[i][j] / (double) this.boardSumOfAllValues) * 100.0;
			}
		}
		for (int i = 0; i < this.boardWinsArray.length; i++) {
			for (int j = 0; j < this.boardWinsArray[0].length; j++) {
				this.boardWinsArrayPer[i][j] = ((double) this.boardWinsArray[i][j]
						/ (double) this.boardWinsSumOfAllValues * 100.0);
			}
		}

		// Column totals
		for (int i = 0; i < this.boardArrayPer.length; i++) {
			for (int j = 0; j < this.boardArrayPer[0].length; j++) {
				this.boardColTotalPer[j] += this.boardArrayPer[i][j];
			}
		}
		for (int i = 0; i < this.boardWinsArrayPer.length; i++) {
			for (int j = 0; j < this.boardWinsArrayPer[0].length; j++) {
				this.boardWinsColTotalPer[j] += this.boardWinsArrayPer[i][j];
			}
		}

		// row totals
		for (int i = 0; i < this.boardArrayPer.length; i++) {
			for (int j = 0; j < this.boardArrayPer[0].length; j++) {
				this.boardRowTotalPer[i] += this.boardArrayPer[i][j];
			}
		}
		for (int i = 0; i < this.boardWinsArrayPer.length; i++) {
			for (int j = 0; j < this.boardWinsArrayPer[0].length; j++) {
				this.boardWinsRowTotalPer[i] += this.boardWinsArrayPer[i][j];
			}
		}

	}

	/*- **************************************************************************************
	* This method create list for the 3 indexArray types.
	* Helper to constructor  
	***************************************************************************************** */
	private void createIndexArrayList() {
		for (int row = 0; row < boardArrayPer.length; row++) {
			for (int column = 0; column < boardArrayPer[0].length; column++) {
				boardEntries.add(new PercentageEntry(boardArrayPer[row][column], row, column));
			}
		}
		boardEntries.sort(Comparator.comparing(PercentageEntry::getPercentage));

		for (int row = 0; row < boardWinsArrayPer.length; row++) {
			for (int column = 0; column < boardWinsArrayPer[row].length; column++) {
				boardWinsEntries.add(new PercentageEntry(boardWinsArrayPer[row][column], row, column));
			}
		}
		boardWinsEntries.sort(Comparator.comparing(PercentageEntry::getPercentage));

		// Now reversed
		for (int row = 0; row < boardArrayPer.length; row++) {
			for (int column = 0; column < boardArrayPer[0].length; column++) {
				boardEntriesReversed.add(new PercentageEntry(boardArrayPer[row][column], row, column));
			}
		}
		boardEntriesReversed.sort(Comparator.comparing(PercentageEntry::getPercentage).reversed());

		for (int row = 0; row < boardWinsArrayPer.length; row++) {
			for (int column = 0; column < boardWinsArrayPer[row].length; column++) {
				boardWinsEntriesReversed.add(new PercentageEntry(boardWinsArrayPer[row][column], row, column));
			}
		}
		boardWinsEntriesReversed.sort(Comparator.comparing(PercentageEntry::getPercentage).reversed());

	}

	/*- **************************************************************************************
	 * This method create list for the 3 indexArray types fot the rows and columns that
	 * have thr row or column totals.
	 * Helper to constructor  
	 ***************************************************************************************** */
	private void createIndexArrayRowAndColumnList() {
		for (int row = 0; row < boardRowTotalPer.length; row++) {
			boardTotalRowPerEntries.add(new PairEntry(boardRowTotalPer[row], row));
		}
		boardTotalRowPerEntries.sort(Comparator.comparing(PairEntry::getPercentage));

		for (int row = 0; row < boardWinsRowTotalPer.length; row++) {
			boardWinsTotalRowPerEntries.add(new PairEntry(boardWinsRowTotalPer[row], row));
		}
		boardWinsTotalRowPerEntries.sort(Comparator.comparing(PairEntry::getPercentage));

		for (int col = 0; col < boardColTotalPer.length; col++) {
			boardTotalColPerEntries.add(new PairEntry(boardColTotalPer[col], col));
		}
		boardTotalColPerEntries.sort(Comparator.comparing(PairEntry::getPercentage));

		for (int col = 0; col < boardWinsColTotalPer.length; col++) {
			boardWinsTotalColPerEntries.add(new PairEntry(boardWinsColTotalPer[col], col));
		}
		boardWinsTotalColPerEntries.sort(Comparator.comparing(PairEntry::getPercentage));

		// Now reversed
		for (int row = 0; row < boardRowTotalPer.length; row++) {
			boardTotalRowPerEntriesReversed.add(new PairEntry(boardRowTotalPer[row], row));
		}
		boardTotalRowPerEntriesReversed.sort(Comparator.comparing(PairEntry::getPercentage).reversed());
		for (int row = 0; row < boardWinsRowTotalPer.length; row++) {
			boardWinsTotalRowPerEntriesReversed.add(new PairEntry(boardWinsRowTotalPer[row], row));
		}
		boardWinsTotalRowPerEntriesReversed.sort(Comparator.comparing(PairEntry::getPercentage).reversed());

		for (int row = 0; row < showdownRowTotalPer.length; row++) {
			showdownTotalRowPerEntriesReversed.add(new PairEntry(showdownRowTotalPer[row], row));
		}
		showdownTotalRowPerEntriesReversed.sort(Comparator.comparing(PairEntry::getPercentage).reversed());

		for (int col = 0; col < boardColTotalPer.length; col++) {
			boardTotalColPerEntriesReversed.add(new PairEntry(boardColTotalPer[col], col));
		}
		boardTotalColPerEntriesReversed.sort(Comparator.comparing(PairEntry::getPercentage).reversed());

		for (int col = 0; col < boardWinsColTotalPer.length; col++) {
			boardWinsTotalColPerEntriesReversed.add(new PairEntry(boardWinsColTotalPer[col], col));
		}
		boardWinsTotalColPerEntriesReversed.sort(Comparator.comparing(PairEntry::getPercentage).reversed());

		for (int col = 0; col < showdownColTotalPer.length; col++) {
			showdownTotalColPerEntriesReversed.add(new PairEntry(showdownColTotalPer[col], col));
		}
		showdownTotalColPerEntriesReversed.sort(Comparator.comparing(PairEntry::getPercentage).reversed());
	}

	/*- **************************************************************************************
	* This method will find the best and worst percentages
	* The 3 best and the 3 worst are found
	* Helper to constructor 
	***************************************************************************************** */
	private void findBestAndWorst() {
		double p;
		int count = 0;
		int ndx = 0;
		for (int i = 0; i < boardTotalRowPerEntriesReversed.size(); i++) {
			p = boardTotalRowPerEntriesReversed.get(ndx).getPercentage();
			if (p != 0.) {
				bestBoardRowsPer[count] = p;
				bestBoardRows[count] = boardTotalRowPerEntriesReversed.get(ndx).getRowOrCol();
				if (++count >= 3) {
					break;
				}
			}
			ndx++;
		}
		count = 0;
		ndx = 0;
		for (int i = 0; i < boardTotalRowPerEntries.size(); i++) {
			p = boardTotalRowPerEntries.get(ndx).getPercentage();
			if (p != 0.) {
				worstBoardRowsPer[count] = p;
				worstBoardRows[count] = boardTotalRowPerEntries.get(ndx).getRowOrCol();
				if (++count >= 3) {
					break;
				}
			}
			ndx++;
		}

		count = 0;
		ndx = 0;
		for (int i = 0; i < boardWinsTotalRowPerEntriesReversed.size(); i++) {
			p = boardWinsTotalRowPerEntriesReversed.get(ndx).getPercentage();
			if (p != 0.) {
				bestBoardWinsRowsPer[count] = p;
				bestBoardWinsRows[count] = boardWinsTotalRowPerEntriesReversed.get(ndx).getRowOrCol();
				if (++count >= 3) {
					break;
				}
			}
			ndx++;
		}
		count = 0;
		ndx = 0;
		for (int i = 0; i < boardWinsTotalRowPerEntries.size(); i++) {
			p = boardWinsTotalRowPerEntries.get(ndx).getPercentage();
			if (p != 0.) {
				worstBoardWinsRowsPer[count] = p;
				worstBoardWinsRows[count] = boardWinsTotalRowPerEntries.get(ndx).getRowOrCol();
				if (++count >= 3) {
					break;
				}
			}
			++ndx;
		}

		count = 0;
		ndx = 1;
		for (int i = 0; i < boardTotalColPerEntriesReversed.size(); i++) {
			p = boardTotalColPerEntriesReversed.get(ndx).getPercentage();
			if (p != 0.) {
				bestBoardColsPer[count] = p;
				bestBoardCols[count] = boardTotalColPerEntriesReversed.get(ndx).getRowOrCol();
				if (++count >= 3) {
					break;
				}
			}
			ndx++;
		}
		count = 0;
		ndx = 0;
		for (int i = 0; i < boardTotalColPerEntries.size(); i++) {
			p = boardTotalColPerEntries.get(ndx).getPercentage();
			if (p != 0.) {
				worstBoardColsPer[count] = p;
				worstBoardCols[count] = boardTotalColPerEntries.get(ndx).getRowOrCol();
				if (++count >= 3) {
					break;
				}
			}
			++ndx;
		}

		count = 0;
		ndx = 1;
		for (int i = 0; i < boardWinsTotalColPerEntriesReversed.size(); i++) {
			p = boardWinsTotalColPerEntriesReversed.get(ndx).getPercentage();
			if (p != 0.) {
				bestBoardWinsColsPer[count] = p;
				bestBoardWinsCols[count] = boardWinsTotalColPerEntriesReversed.get(ndx).getRowOrCol();
				if (++count >= 3) {
					break;
				}
			}
			++ndx;
		}
		count = 0;
		ndx = 0;
		for (int i = 0; i < boardWinsTotalColPerEntries.size(); i++) {
			p = boardWinsTotalColPerEntries.get(ndx).getPercentage();
			if (p != 0.) {
				worstBoardWinsColsPer[count] = p;
				worstBoardWinsCols[count] = boardWinsTotalColPerEntries.get(ndx).getRowOrCol();
				++ndx;
				if (++count >= 3) {
					break;
				}
			}
			++ndx;
		}

	}

	/*- **************************************************************************************
	* This method will find the best and worst percentages
	* The 5 best and the 5 worst are found.
	* Looks at complete array, not single roows or columns.
	* Helper to constructor 
	* Skip column 0 for best because it in MADE_NONE or DRAW_NONE
	***************************************************************************************** */
	private void findBestAndWorstOverall() {
		int count = 0;
		int c = 0;
		int ndx = 0;
		double p;
		for (int i = 0; i < boardEntriesReversed.size(); i++) {
			p = boardEntriesReversed.get(ndx).getPercentage();
			c = boardEntriesReversed.get(ndx).getCol();
			if (p != 0. && c != 0) {
				bestBoard5Per[count] = p;
				bestBoard5Row[count] = boardEntriesReversed.get(ndx).getRow();
				bestBoard5Col[count] = boardEntriesReversed.get(ndx).getCol();
				if (++count >= 5) {
					break;
				}
			}
			ndx++;
		}
		count = 0;
		ndx = 0;
		for (int i = 0; i < boardEntries.size(); i++) {
			p = boardEntries.get(ndx).getPercentage();
			if (p != 0.) {
				worstBoard5Per[count] = p;
				worstBoard5Row[count] = boardEntries.get(ndx).getRow();
				worstBoard5Col[count] = boardEntries.get(ndx).getCol();
				if (++count >= 5) {
					break;
				}
			}
			ndx++;
		}
		count = 0;
		ndx = 0;
		for (int i = 0; i < boardWinsEntriesReversed.size(); i++) {
			p = boardWinsEntriesReversed.get(ndx).getPercentage();
			c = boardWinsEntriesReversed.get(ndx).getCol();
			if (p != 0. && c != 0) {
				bestBoardWins5Per[count] = p;
				bestBoardWins5Row[count] = boardWinsEntriesReversed.get(ndx).getRow();
				bestBoardWins5Col[count] = boardWinsEntriesReversed.get(ndx).getCol();
				if (++count >= 5) {
					break;
				}
			}
			++ndx;
		}
		count = 0;
		ndx = 0;
		for (int i = 0; i < boardWinsEntries.size(); i++) {
			p = boardWinsEntries.get(ndx).getPercentage();
			if (p != 0.) {
				worstBoardWins5Per[count] = p;
				worstBoardWins5Row[count] = boardWinsEntries.get(ndx).getRow();
				worstBoardWins5Col[count] = boardWinsEntries.get(ndx).getCol();
				if (++count >= 5) {
					break;
				}
			}
			ndx++;
		}

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
		final List<PercentageEntry> matchingEntries = boardEntries.stream()
				.filter(entry -> Math.abs(entry.getPercentage() - targetPercentage) < 0.0001)
				.collect(Collectors.toList());

		final List<PairEntry> matchingEntriesX = boardTotalRowPerEntries.stream()
				.filter(entry -> Math.abs(entry.getPercentage() - targetPercentage) < 0.0001)
				.collect(Collectors.toList());
	}

	/*- **************************************************************************************
	* This method will get entries in sort order
	*  
	***************************************************************************************** */
	private void indexArrayListInSortedOrder() {
		boardTotalRowPerEntries.stream().mapToInt(PairEntry::getRowOrCol).forEach(r -> {
		});
	}

	/*- **************************************************************************************
	* Get percentage for row and column
	*  
	***************************************************************************************** */
	private double getPercentageByRowAndColumn(int targetRow, int targetColumn) {
		for (var entry : boardEntries) {
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
		var st = new StringBuilder().append("The best Board rows were ").append(Format.formatPer(bestBoardRowsPer[0]))
				.append(" ").append(rowNames[bestBoardRows[0]]).append(", ")
				.append(Format.formatPer(bestBoardRowsPer[1])).append(" ").append(rowNames[bestBoardRows[1]])
				.append(", ").append(Format.formatPer(bestBoardRowsPer[2])).append(" ")
				.append(rowNames[bestBoardRows[2]]).append("\r\n").toString();
		st += new StringBuilder().append("The worst Board rows were ").append(Format.formatPer(worstBoardRowsPer[0]))
				.append(" ").append(rowNames[worstBoardRows[0]]).append(", ")
				.append(Format.formatPer(worstBoardRowsPer[1])).append(" ").append(rowNames[worstBoardRows[1]])
				.append(", ").append(Format.formatPer(worstBoardRowsPer[2])).append(" ")
				.append(rowNames[worstBoardRows[2]]).append("\r\n").toString();
		st += new StringBuilder().append("The best BoardWins rows were ")
				.append(Format.formatPer(bestBoardWinsRowsPer[0])).append(" ").append(rowNames[bestBoardWinsRows[0]])
				.append(", ").append(Format.formatPer(bestBoardWinsRowsPer[1])).append(" ")
				.append(rowNames[bestBoardWinsRows[1]]).append(", ").append(Format.formatPer(worstBoardWinsRowsPer[2]))
				.append(" ").append(rowNames[bestBoardWinsRows[2]]).append("\r\n").toString();
		st += new StringBuilder().append("The worst BoardWins rows were ")
				.append(Format.formatPer(bestBoardWinsRowsPer[0])).append(" ").append(rowNames[worstBoardWinsRows[0]])
				.append(", ").append(Format.formatPer(bestBoardWinsRowsPer[1])).append(" ")
				.append(rowNames[worstBoardWinsRows[1]]).append(Format.formatPer(bestBoardWinsRowsPer[2])).append(" ")
				.append(", ").append(rowNames[worstBoardWinsRows[2]]).append("\r\n").toString();

		st += new StringBuilder().append("The best Board cols were ").append(Format.formatPer(bestBoardColsPer[0]))
				.append(" ").append(colNames[bestBoardCols[0]]).append(", ")
				.append(Format.formatPer(bestBoardColsPer[0])).append(colNames[bestBoardCols[1]]).append(", ")
				.append(Format.formatPer(bestBoardColsPer[1])).append(" ").append(colNames[bestBoardCols[2]])
				.append("\r\n").toString();
		st += new StringBuilder().append("The worst Board cols were ").append(Format.formatPer(worstBoardColsPer[0]))
				.append(" ").append(colNames[worstBoardCols[0]]).append(", ")
				.append(Format.formatPer(worstBoardColsPer[1])).append(" ").append(colNames[worstBoardCols[1]])
				.append(", ").append(Format.formatPer(worstBoardColsPer[2])).append(" ")
				.append(colNames[worstBoardCols[2]]).append("\r\n").toString();

		st += new StringBuilder().append("The bes BoardWins cols were ")
				.append(Format.formatPer(bestBoardWinsColsPer[0])).append(" ").append(colNames[bestBoardWinsCols[0]])
				.append(", ").append(Format.formatPer(bestBoardWinsColsPer[1])).append(" ")
				.append(colNames[bestBoardWinsCols[1]]).append(", ").append(Format.formatPer(bestBoardWinsColsPer[2]))
				.append(" ").append(colNames[bestBoardWinsCols[2]]).append("\r\n").toString();
		st += new StringBuilder().append("The worst BoardWins cols were ")
				.append(Format.formatPer(worstBoardWinsColsPer[0])).append(" ").append(colNames[worstBoardWinsCols[0]])
				.append(", ").append(Format.formatPer(worstBoardWinsColsPer[1])).append(" ")
				.append(colNames[worstBoardWinsCols[1]]).append(Format.formatPer(worstBoardWinsColsPer[2])).append(" ")
				.append(", ").append(colNames[worstBoardWinsCols[2]]).append("\r\n").toString();

		st += new StringBuilder().append("The boardSumOfAllValues was ").append(boardSumOfAllValues).append("\r\n")
				.toString();
		st += new StringBuilder().append("The boardWinsSumOfAllValues was ").append(boardWinsSumOfAllValues)
				.append("\r\n").toString();
		st += new StringBuilder().append("The best 5 boards were \r\n").append(Format.formatPer(bestBoard5Per[0]))
				.append(" ").append(rowNames[bestBoard5Row[0]]).append(", ").append(colNames[bestBoard5Col[1]])
				.append("\r\n").append(Format.formatPer(bestBoard5Per[1])).append(" ")
				.append(rowNames[bestBoard5Row[1]]).append(", ").append(colNames[bestBoard5Col[0]]).append("\r\n")
				.append(Format.formatPer(bestBoard5Per[2])).append(" ").append(rowNames[bestBoard5Row[2]]).append(", ")
				.append(colNames[bestBoard5Col[2]]).append("\r\n").append(Format.formatPer(bestBoard5Per[3]))
				.append(" ").append(rowNames[bestBoard5Row[3]]).append(", ").append(colNames[bestBoard5Col[3]])
				.append("\r\n").append(Format.formatPer(bestBoard5Per[4])).append(" ")
				.append(rowNames[bestBoard5Row[4]]).append(", ").append(colNames[bestBoard5Col[4]]).append("\r\n")
				.toString();
		st += new StringBuilder().append("The worstt 5 boards were \r\n").append(Format.formatPer(worstBoard5Per[0]))
				.append(" ").append(rowNames[worstBoard5Row[0]]).append(", ").append(colNames[worstBoard5Col[1]])
				.append("\r\n").append(Format.formatPer(worstBoard5Per[1])).append(" ")
				.append(rowNames[worstBoard5Row[1]]).append(", ").append(colNames[worstBoard5Col[0]]).append("\r\n")
				.append(Format.formatPer(worstBoard5Per[2])).append(" ").append(rowNames[worstBoard5Row[2]])
				.append(", ").append(colNames[worstBoard5Col[2]]).append("\r\n")
				.append(Format.formatPer(worstBoard5Per[3])).append(" ").append(rowNames[worstBoard5Row[3]])
				.append(", ").append(colNames[worstBoard5Col[3]]).append("\r\n")
				.append(Format.formatPer(worstBoard5Per[4])).append(" ").append(rowNames[worstBoard5Row[4]])
				.append(", ").append(colNames[worstBoard5Col[4]]).append("\r\n").toString();

		return st;
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

	// Totals of rows and/or columns for the 3 index arrays

}
