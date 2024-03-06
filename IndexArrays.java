package holdemevaluator;

/*- ****************************************************************************
 * 
 * @author PEAK_
*******************************************************************************/
public class IndexArrays implements Constants {

	/*-  **************************************************************************
	* This is a data holding Class. 
	* It contains many arrays that are used to evaluate 3 types of hands or draws:
	* 1. Draws  
	* 2. Made Hands
	* 3. Winning Showdown hands. 
	* 
	* The first index int the arrays is some integer created from analyzing the board 
	* or analyzing the hand ( board + hole cards )
	* For example: HML is an index created by looking at the board cards and assigning 
	* a value based on each cards value, high H is A - 10, Middle M is 9 -6, 
	* and  low L is 5 - 2.
	* This is an experiment in determining is there is a simple way to classify a board
	* or hand without the ambiguity of Wet / Dry. ( Everyone has their own definition )
	* but almost all believe that their strategy should be adjusted based on wet / dry.
	* ( or coordinated, or static / dynamic, or .....)
	* HML for example is simple to derive, not subjective, has a precise definition, and
	* results in a small number of indexes.
	* Three arrays in EvalData are used, madeArray, drawArray, and showdownHand. 
	*
	* The arrays are updated:
	* 1.Initialized to 0
	* 2.Updated for each hand played
	* 3.Updated at Showdown.
	* 4.Updated after all hands have been played ( summary arrays )
	*  
	* The only method in this class is initialize.
	* EvaluateMany does the other updates.
	* Classification calculates indexes.
	****************************************************************************** */

	/*-  ******************************************************************************
	/*-
	* Used to calculate EV for hands on a street Values of hands by seat and type
	* of hand, and Win or Loose index from Evaluate for Flop Turn and River.
	* +1 because of Showdown
	 ****************************************************************************** */
	static int[][] evaluateIndex = new int[STREET + 1][PLAYERS];

	static int[][][] evaluateIndexPos = new int[STREET + 1][PLAYERS][POSITIONS];

	/*-  ******************************************************************************
	 * Evaluation arrays
	 * All of these arrays use some characteristic of the Flop, Turn, or River
	 * board to evaluate what happens with a draw, made hand, or Showdown.
	 ****************************************************************************** */
	static int[][] hmlDrawFlop = new int[HML_FLOP_SIZE][DRAW_SIZE];

	static int[][] hmlMadeFlop = new int[HML_FLOP_SIZE][MADE_SIZE];

	static int[][] hmlShowdownFlop = new int[HML_FLOP_SIZE][MADE_SIZE];

	static int[][] hmlBoardFlop = new int[HML_FLOP_SIZE][BOARD_SIZE];

	static int[][] hmlDrawTurn = new int[HML_TURN_SIZE][DRAW_SIZE];

	static int[][] hmlMadeTurn = new int[HML_TURN_SIZE][MADE_SIZE];

	static int[][] hmlShowdownTurn = new int[HML_TURN_SIZE][MADE_SIZE];

	static int[][] hmlBoardTurn = new int[HML_TURN_SIZE][BOARD_SIZE];

	static int[][] hmlMadeRiver = new int[HML_RIVER_SIZE][MADE_SIZE];

	static int[][] hmlShowdownRiver = new int[HML_RIVER_SIZE][MADE_SIZE];

	static int[][] hmlBoardRiver = new int[HML_RIVER_SIZE][BOARD_SIZE];

	// Only update if hmlMadeFlop is the hand that won
	static int[][] hmlShowdownMadeWinsFlop = new int[HML_FLOP_SIZE][MADE_SIZE];

	static int[][] hmlShowdownMadeWinsTurn = new int[HML_TURN_SIZE][MADE_SIZE];

	static int[][] hmlShowdownMadeWinsRiver = new int[HML_RIVER_SIZE][MADE_SIZE];

	static int[][] hmlBoardMadeWinsFlop = new int[HML_FLOP_SIZE][BOARD_SIZE];

	// Wet / Dry
	static int[][] wetDryDrawFlop = new int[WET_DRY_SIZE][DRAW_SIZE];

	static int[][] wetDryMadeFlop = new int[WET_DRY_SIZE][MADE_SIZE];

	static int[][] wetDryShowdownFlop = new int[WET_DRY_SIZE][MADE_SIZE];

	static int[][] wetDryBoardFlop = new int[WET_DRY_SIZE][BOARD_SIZE];

	static int[][] wetDryDrawTurn = new int[WET_DRY_SIZE][DRAW_SIZE];

	static int[][] wetDryMadeTurn = new int[WET_DRY_SIZE][MADE_SIZE];

	static int[][] wetDryShowdownTurn = new int[WET_DRY_SIZE][MADE_SIZE];

	static int[][] wetDryBoardTurn = new int[WET_DRY_SIZE][BOARD_SIZE];

	static int[][] wetDryMadeRiver = new int[WET_DRY_SIZE][MADE_SIZE];

	static int[][] wetDryShowdownRiver = new int[WET_DRY_SIZE][MADE_SIZE];

	static int[][] wetDryBoardRiver = new int[WET_DRY_SIZE][BOARD_SIZE];

	// typeOf1755
	static int[][] typeOf1755DrawFlop = new int[TYPE_OF_1755_SIZE][DRAW_SIZE];

	static int[][] typeOf1755MadeFlop = new int[TYPE_OF_1755_SIZE][MADE_SIZE];

	static int[][] typeOf1755ShowdownFlop = new int[TYPE_OF_1755_SIZE][MADE_SIZE];

	static int[][] typeOf1755BoardFlop = new int[TYPE_OF_1755_SIZE][BOARD_SIZE];

	static int[][] typeOf1755DrawTurn = new int[TYPE_OF_1755_SIZE][DRAW_SIZE];

	static int[][] typeOf1755MadeTurn = new int[TYPE_OF_1755_SIZE][MADE_SIZE];

	static int[][] typeOf1755ShowdownTurn = new int[TYPE_OF_1755_SIZE][MADE_SIZE];

	static int[][] typeOf1755BoardTurn = new int[TYPE_OF_1755_SIZE][BOARD_SIZE];

	static int[][] typeOf1755MadeRiver = new int[TYPE_OF_1755_SIZE][MADE_SIZE];

	static int[][] typeOf1755ShowdownRiver = new int[TYPE_OF_1755_SIZE][MADE_SIZE];

	static int[][] typeOf1755BoardRiver = new int[TYPE_OF_1755_SIZE][BOARD_SIZE];

	// SCBP
	static int[][] SCBPDrawFlop = new int[SCBP_SIZE][DRAW_SIZE];

	static int[][] SCBPMadeFlop = new int[SCBP_SIZE][MADE_SIZE];

	static int[][] SCBPShowdownFlop = new int[SCBP_SIZE][MADE_SIZE];

	static int[][] SCBPBoardFlop = new int[SCBP_SIZE][BOARD_SIZE];

	static int[][] SCBPDrawTurn = new int[SCBP_SIZE][DRAW_SIZE];

	static int[][] SCBPMadeTurn = new int[SCBP_SIZE][MADE_SIZE];

	static int[][] SCBPShowdownTurn = new int[SCBP_SIZE][MADE_SIZE];

	static int[][] SCBPBoardTurn = new int[SCBP_SIZE][BOARD_SIZE];

	static int[][] SCBPMadeRiver = new int[SCBP_SIZE][MADE_SIZE];

	static int[][] SCBPShowdownRiver = new int[SCBP_SIZE][MADE_SIZE];

	static int[][] SCBPBoardRiver = new int[SCBP_SIZE][BOARD_SIZE];

	/*-  ************************************************************************************* 
	 * Best made hand or draw for player on this street
	  ************************************************************************************** */
	static int[] drawFlop = new int[PLAYERS];

	static int[] drawTurn = new int[PLAYERS];

	static int[] madeFlop = new int[PLAYERS];

	static int[] madeTurn = new int[PLAYERS];

	static int[] madeRiver = new int[PLAYERS];

	/*-  ************************************************************************************
	 * These arrays are  used to find out how frequently a draw or made hand on the Flop 
	 * or Turn makes what made hand on the River or wins at Showdown.
	 * Indexes are:
	 * 		hmlIndex or hhmlIndex
	 * 		Made hand or Draw on ( Flop, Turn)
	 * 		Made hand on River or made hand that won showdown.
	 * The value in the third index is the number of times that the hand in the second index was
	 * made on the River or won at Showdown
	 ************************************************************************************** */
	static int[][][] hmlDrawFlopToMadeRiver = new int[HML_FLOP_SIZE][DRAW_SIZE][MADE_SIZE];

	static int[][][] hmlDrawTurnToMadeRiver = new int[HML_TURN_SIZE][DRAW_SIZE][MADE_SIZE];

	static int[][][] hmlMadeFlopToMadeRiver = new int[HML_FLOP_SIZE][MADE_SIZE][MADE_SIZE];

	static int[][][] hmlMadeTurnToMadeRiver = new int[HML_TURN_SIZE][MADE_SIZE][MADE_SIZE];

	static int[][][] hmlBoardFlopToMadeRiver = new int[HML_FLOP_SIZE][MADE_SIZE][BOARD_SIZE];

	static int[][][] hmlBoardTurnToMadeRiver = new int[HML_TURN_SIZE][MADE_SIZE][BOARD_SIZE];

	static int[][][] hmlDrawFlopToMadeWon = new int[HML_FLOP_SIZE][DRAW_SIZE][MADE_SIZE];

	static int[][][] hmlDrawTurnToMadeWon = new int[HML_TURN_SIZE][DRAW_SIZE][MADE_SIZE];

	static int[][][] hmlMadeFlopToMadeWon = new int[HML_FLOP_SIZE][MADE_SIZE][MADE_SIZE];

	static int[][][] hmlMadeTurnToMadeWon = new int[HML_TURN_SIZE][MADE_SIZE][MADE_SIZE];

	static int[][][] hmlBoardFlopToMadeWon = new int[HML_FLOP_SIZE][MADE_SIZE][BOARD_SIZE];

	static int[][][] hmlBoardTurnToMadeWon = new int[HML_TURN_SIZE][MADE_SIZE][BOARD_SIZE];

	static int[][][] wetDryDrawFlopToMadeRiver = new int[WET_DRY_SIZE][DRAW_SIZE][MADE_SIZE];

	static int[][][] wetDryDrawTurnToMadeRiver = new int[WET_DRY_SIZE][DRAW_SIZE][MADE_SIZE];

	static int[][][] wetDryMadeFlopToMadeRiver = new int[WET_DRY_SIZE][MADE_SIZE][MADE_SIZE];

	static int[][][] wetDryMadeTurnToMadeRiver = new int[WET_DRY_SIZE][MADE_SIZE][MADE_SIZE];

	static int[][][] wetDryBoardFlopToMadeRiver = new int[WET_DRY_SIZE][MADE_SIZE][BOARD_SIZE];

	static int[][][] wetDryBoardTurnToMadeRiver = new int[WET_DRY_SIZE][MADE_SIZE][BOARD_SIZE];

	static int[][][] wetDryDrawFlopToMadeWon = new int[WET_DRY_SIZE][DRAW_SIZE][MADE_SIZE];

	static int[][][] wetDryDrawTurnToMadeWon = new int[WET_DRY_SIZE][DRAW_SIZE][MADE_SIZE];

	static int[][][] wetDryMadeFlopToMadeWon = new int[WET_DRY_SIZE][MADE_SIZE][MADE_SIZE];

	static int[][][] wetDryMadeTurnToMadeWon = new int[WET_DRY_SIZE][MADE_SIZE][MADE_SIZE];

	static int[][][] wetDryBoardFlopToMadeWon = new int[WET_DRY_SIZE][MADE_SIZE][BOARD_SIZE];

	static int[][][] wetDryBoardTurnToMadeWon = new int[WET_DRY_SIZE][MADE_SIZE][BOARD_SIZE];

	static int[][][] typeOf1755DrawFlopToMadeRiver = new int[TYPE_OF_1755_SIZE][DRAW_SIZE][MADE_SIZE];

	static int[][][] typeOf1755DrawTurnToMadeRiver = new int[TYPE_OF_1755_SIZE][DRAW_SIZE][MADE_SIZE];

	static int[][][] typeOf1755MadeFlopToMadeRiver = new int[TYPE_OF_1755_SIZE][MADE_SIZE][MADE_SIZE];

	static int[][][] typeOf1755MadeTurnToMadeRiver = new int[TYPE_OF_1755_SIZE][MADE_SIZE][MADE_SIZE];

	static int[][][] typeOf1755BoardFlopToMadeRiver = new int[TYPE_OF_1755_SIZE][MADE_SIZE][BOARD_SIZE];

	static int[][][] typeOf1755BoardTurnToMadeRiver = new int[TYPE_OF_1755_SIZE][MADE_SIZE][BOARD_SIZE];

	static int[][][] typeOf1755DrawFlopToMadeWon = new int[TYPE_OF_1755_SIZE][DRAW_SIZE][MADE_SIZE];

	static int[][][] typeOf1755DrawTurnToMadeWon = new int[TYPE_OF_1755_SIZE][DRAW_SIZE][MADE_SIZE];

	static int[][][] typeOf1755MadeFlopToMadeWon = new int[TYPE_OF_1755_SIZE][MADE_SIZE][MADE_SIZE];

	static int[][][] typeOf1755MadeTurnToMadeWon = new int[TYPE_OF_1755_SIZE][MADE_SIZE][MADE_SIZE];

	static int[][][] typeOf1755BoardFlopToMadeWon = new int[TYPE_OF_1755_SIZE][MADE_SIZE][BOARD_SIZE];

	static int[][][] typeOf1755BoardTurnToMadeWon = new int[TYPE_OF_1755_SIZE][MADE_SIZE][BOARD_SIZE];

	static int[][][] SCBPDrawFlopToMadeRiver = new int[SCBP_SIZE][DRAW_SIZE][MADE_SIZE];

	static int[][][] SCBPDrawTurnToMadeRiver = new int[SCBP_SIZE][DRAW_SIZE][MADE_SIZE];

	static int[][][] SCBPMadeFlopToMadeRiver = new int[SCBP_SIZE][MADE_SIZE][MADE_SIZE];

	static int[][][] SCBPMadeTurnToMadeRiver = new int[SCBP_SIZE][MADE_SIZE][MADE_SIZE];

	static int[][][] SCBPBoardFlopToMadeRiver = new int[SCBP_SIZE][MADE_SIZE][BOARD_SIZE];

	static int[][][] SCBPBoardTurnToMadeRiver = new int[SCBP_SIZE][MADE_SIZE][BOARD_SIZE];

	static int[][][] SCBPDrawFlopToMadeWon = new int[SCBP_SIZE][DRAW_SIZE][MADE_SIZE];

	static int[][][] SCBPDrawTurnToMadeWon = new int[SCBP_SIZE][DRAW_SIZE][MADE_SIZE];

	static int[][][] SCBPMadeFlopToMadeWon = new int[SCBP_SIZE][MADE_SIZE][MADE_SIZE];

	static int[][][] SCBPMadeTurnToMadeWon = new int[SCBP_SIZE][MADE_SIZE][MADE_SIZE];

	static int[][][] SCBPBoardFlopToMadeWon = new int[SCBP_SIZE][MADE_SIZE][BOARD_SIZE];

	static int[][][] SCBPBoardTurnToMadeWon = new int[SCBP_SIZE][MADE_SIZE][BOARD_SIZE];

	/*-  ***********************************************************************************
	* Number of hands counted, not games
	*************************************************************************************  */
	static int hmlCountFlop = 0;

	static int hmlCountTurn = 0;

	static int hmlCountRiver = 0;

	static int wetDryCount = 0;

	static int typeOf1755Count = 0;

	static int SCBPCount = 0;

	static int type1755Count = 0;

	static int flopIndexCount = 0;

	static int handValueCount = 0;

	static int sumOfDrawCount = 0;

	static int sumOfMadeCount = 0;

	static int typeCount = 0;

	static int sumOfShowdownCount = 0;

	static int boardArrayFlopCount = 0;

	static int boardArrayTurnCount = 0;

	static int boardArrayRiverCount = 0;

	static int flopArraysCount = 0;

	static int turnArraysCount = 0;

	static int riverArraysCount = 0;

	// Showdown
	static int showdownCount = 0;

	static int[] winningHand = new int[99]; // ?? TODO

	private IndexArrays() {
		throw new IllegalStateException("Utility class");
	}

	/*-  ****************************************************************************
	 * Initialize for a new run
	 ****************************************************************************** */
	static void initialize() {
		for (int i = 0; i < HML_FLOP_SIZE; i++) {
			for (int j = 0; j < DRAW_SIZE; j++) {
				hmlDrawFlop[i][j] = 0;
			}
			for (int j = 0; j < MADE_SIZE; j++) {
				hmlMadeFlop[i][j] = 0;
				hmlShowdownFlop[i][j] = 0;
				hmlShowdownMadeWinsFlop[i][j] = 0;
			}
		}
		for (int i = 0; i < HML_TURN_SIZE; i++) {
			for (int j = 0; j < DRAW_SIZE; j++) {
				hmlDrawTurn[i][j] = 0;
			}
			for (int j = 0; j < MADE_SIZE; j++) {
				hmlMadeTurn[i][j] = 0;
				hmlShowdownTurn[i][j] = 0;
				hmlShowdownMadeWinsTurn[i][j] = 0;
			}
		}
		for (int i = 0; i < HML_RIVER_SIZE; i++) {
			for (int j = 0; j < MADE_SIZE; j++) {
				hmlMadeRiver[i][j] = 0;
				hmlShowdownRiver[i][j] = 0;
				hmlShowdownMadeWinsRiver[i][j] = 0;
			}
		}

		// Wet / Dry
		for (int i = 0; i < WET_DRY_SIZE; i++) {
			for (int j = 0; j < DRAW_SIZE; j++) {
				wetDryDrawFlop[i][j] = 0;
			}
			for (int j = 0; j < MADE_SIZE; j++) {
				wetDryMadeFlop[i][j] = 0;
				wetDryShowdownFlop[i][j] = 0;
			}
		}
		for (int i = 0; i < WET_DRY_SIZE; i++) {
			for (int j = 0; j < DRAW_SIZE; j++) {
				wetDryDrawTurn[i][j] = 0;
			}
			for (int j = 0; j < MADE_SIZE; j++) {
				wetDryMadeTurn[i][j] = 0;
				wetDryShowdownTurn[i][j] = 0;
			}
		}
		for (int i = 0; i < WET_DRY_SIZE; i++) {
			for (int j = 0; j < MADE_SIZE; j++) {
				wetDryMadeRiver[i][j] = 0;
				wetDryShowdownRiver[i][j] = 0;
			}
		}

		// typeOf1755
		for (int i = 0; i < TYPE_OF_1755_SIZE; i++) {
			for (int j = 0; j < DRAW_SIZE; j++) {
				typeOf1755DrawFlop[i][j] = 0;
			}
			for (int j = 0; j < MADE_SIZE; j++) {
				typeOf1755MadeFlop[i][j] = 0;
				typeOf1755ShowdownFlop[i][j] = 0;
			}
		}
		for (int i = 0; i < TYPE_OF_1755_SIZE; i++) {
			for (int j = 0; j < DRAW_SIZE; j++) {
				typeOf1755DrawTurn[i][j] = 0;
			}
			for (int j = 0; j < MADE_SIZE; j++) {
				typeOf1755MadeTurn[i][j] = 0;
				typeOf1755ShowdownTurn[i][j] = 0;
			}
		}
		for (int i = 0; i < TYPE_OF_1755_SIZE; i++) {
			for (int j = 0; j < MADE_SIZE; j++) {
				typeOf1755MadeRiver[i][j] = 0;
				typeOf1755ShowdownRiver[i][j] = 0;
			}
		}

		// SCBP
		for (int i = 0; i < SCBP_SIZE; i++) {
			for (int j = 0; j < DRAW_SIZE; j++) {
				SCBPDrawFlop[i][j] = 0;
			}
			for (int j = 0; j < MADE_SIZE; j++) {
				SCBPMadeFlop[i][j] = 0;
				SCBPShowdownFlop[i][j] = 0;
			}
		}
		for (int i = 0; i < SCBP_SIZE; i++) {
			for (int j = 0; j < DRAW_SIZE; j++) {
				SCBPDrawTurn[i][j] = 0;
			}
			for (int j = 0; j < MADE_SIZE; j++) {
				SCBPMadeTurn[i][j] = 0;
				SCBPShowdownTurn[i][j] = 0;
			}
		}
		for (int i = 0; i < SCBP_SIZE; i++) {
			for (int j = 0; j < MADE_SIZE; j++) {
				SCBPMadeRiver[i][j] = 0;
				SCBPShowdownRiver[i][j] = 0;
			}
		}

		for (int i = 0; i < HML_FLOP_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				hmlBoardFlop[i][j] = 0;
			}
		}
		for (int i = 0; i < HML_TURN_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				hmlBoardTurn[i][j] = 0;
			}
		}
		for (int i = 0; i < HML_RIVER_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				hmlBoardRiver[i][j] = 0;
			}
		}
		for (int i = 0; i < HML_RIVER_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				hmlBoardRiver[i][j] = 0;
			}
		}

		for (int i = 0; i < WET_DRY_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				wetDryBoardFlop[i][j] = 0;
			}
		}
		for (int i = 0; i < WET_DRY_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				wetDryBoardTurn[i][j] = 0;
			}
		}
		for (int i = 0; i < WET_DRY_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				wetDryBoardRiver[i][j] = 0;
			}
		}

		for (int i = 0; i < TYPE_OF_1755_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				typeOf1755BoardFlop[i][j] = 0;
			}
		}
		for (int i = 0; i < TYPE_OF_1755_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				typeOf1755BoardTurn[i][j] = 0;
			}
		}
		for (int i = 0; i < TYPE_OF_1755_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				typeOf1755BoardRiver[i][j] = 0;
			}
		}
		for (int i = 0; i < SCBP_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				SCBPBoardFlop[i][j] = 0;
			}
		}
		for (int i = 0; i < SCBP_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				SCBPBoardTurn[i][j] = 0;
			}
		}
		for (int i = 0; i < SCBP_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				SCBPBoardRiver[i][j] = 0;
			}
		}

		for (int i = 0; i < WET_DRY_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				wetDryBoardRiver[i][j] = 0;
			}
		}

		for (int i = 0; i < 99; i++) {// TODO
			winningHand[i] = 0;
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < PLAYERS; j++) {
				evaluateIndex[i][j] = 0;
			}
		}

		for (int i = 0; i < STREET + 1; i++) {
			for (int j = 0; j < PLAYERS; j++) {
				for (int k = 0; k < POSITIONS; k++) {
					evaluateIndexPos[i][j][k] = 0;
				}
			}
		}

		for (int i = 0; i < MADE_SIZE; i++) {
			winningHand[i] = 0;
		}

		for (int i = 0; i < PLAYERS; i++) {
			drawFlop[i] = 0;
			drawTurn[i] = 0;
			madeFlop[i] = 0;
			madeTurn[i] = 0;
			madeRiver[i] = 0;
		}

		for (int i = 0; i < HML_FLOP_SIZE; i++) {
			for (int j = 0; j < DRAW_SIZE; j++) {
				for (int k = 0; k < MADE_SIZE; k++) {
					hmlDrawFlopToMadeRiver[i][j][k] = 0;
					hmlDrawFlopToMadeWon[i][j][k] = 0;
				}
			}
		}
		for (int i = 0; i < HML_FLOP_SIZE; i++) {
			for (int j = 0; j < MADE_SIZE; j++) {
				for (int k = 0; k < MADE_SIZE; k++) {
					hmlMadeFlopToMadeRiver[i][j][k] = 0;
					hmlMadeFlopToMadeWon[i][j][k] = 0;
				}
			}
		}
		for (int i = 0; i < HML_TURN_SIZE; i++) {
			for (int j = 0; j < DRAW_SIZE; j++) {
				for (int k = 0; k < MADE_SIZE; k++) {
					hmlDrawTurnToMadeRiver[i][j][k] = 0;
					hmlDrawTurnToMadeWon[i][j][k] = 0;
				}
			}
		}
		for (int i = 0; i < HML_TURN_SIZE; i++) {
			for (int j = 0; j < MADE_SIZE; j++) {
				for (int k = 0; k < MADE_SIZE; k++) {
					hmlMadeTurnToMadeRiver[i][j][k] = 0;
					hmlMadeTurnToMadeWon[i][j][k] = 0;
				}
			}
		}

		// Wet DRy
		for (int i = 0; i < WET_DRY_SIZE; i++) {
			for (int j = 0; j < DRAW_SIZE; j++) {
				for (int k = 0; k < MADE_SIZE; k++) {
					wetDryDrawFlopToMadeRiver[i][j][k] = 0;
					wetDryDrawFlopToMadeWon[i][j][k] = 0;
				}
			}
		}
		for (int i = 0; i < WET_DRY_SIZE; i++) {
			for (int j = 0; j < MADE_SIZE; j++) {
				for (int k = 0; k < MADE_SIZE; k++) {
					wetDryMadeFlopToMadeRiver[i][j][k] = 0;
					wetDryMadeFlopToMadeWon[i][j][k] = 0;
				}
			}
		}
		for (int i = 0; i < WET_DRY_SIZE; i++) {
			for (int j = 0; j < DRAW_SIZE; j++) {
				for (int k = 0; k < MADE_SIZE; k++) {
					wetDryDrawTurnToMadeRiver[i][j][k] = 0;
					wetDryDrawTurnToMadeWon[i][j][k] = 0;
				}
			}
		}
		for (int i = 0; i < WET_DRY_SIZE; i++) {
			for (int j = 0; j < MADE_SIZE; j++) {
				for (int k = 0; k < MADE_SIZE; k++) {
					wetDryMadeTurnToMadeRiver[i][j][k] = 0;
					wetDryMadeTurnToMadeWon[i][j][k] = 0;
				}
			}
		}

		// Type of 1755
		for (int i = 0; i < TYPE_OF_1755_SIZE; i++) {
			for (int j = 0; j < DRAW_SIZE; j++) {
				for (int k = 0; k < MADE_SIZE; k++) {
					typeOf1755DrawFlopToMadeRiver[i][j][k] = 0;
					typeOf1755DrawFlopToMadeWon[i][j][k] = 0;
				}
			}
		}
		for (int i = 0; i < TYPE_OF_1755_SIZE; i++) {
			for (int j = 0; j < MADE_SIZE; j++) {
				for (int k = 0; k < MADE_SIZE; k++) {
					typeOf1755MadeFlopToMadeRiver[i][j][k] = 0;
					typeOf1755MadeFlopToMadeWon[i][j][k] = 0;
				}
			}
		}
		for (int i = 0; i < TYPE_OF_1755_SIZE; i++) {
			for (int j = 0; j < DRAW_SIZE; j++) {
				for (int k = 0; k < MADE_SIZE; k++) {
					typeOf1755DrawTurnToMadeRiver[i][j][k] = 0;
					typeOf1755DrawTurnToMadeWon[i][j][k] = 0;
				}
			}
		}
		for (int i = 0; i < TYPE_OF_1755_SIZE; i++) {
			for (int j = 0; j < MADE_SIZE; j++) {
				for (int k = 0; k < MADE_SIZE; k++) {
					typeOf1755MadeTurnToMadeRiver[i][j][k] = 0;
					typeOf1755MadeTurnToMadeWon[i][j][k] = 0;
				}
			}
		}

		// SCBP
		for (int i = 0; i < SCBP_SIZE; i++) {
			for (int j = 0; j < DRAW_SIZE; j++) {
				for (int k = 0; k < MADE_SIZE; k++) {
					SCBPDrawFlopToMadeRiver[i][j][k] = 0;
					SCBPDrawFlopToMadeWon[i][j][k] = 0;
				}
			}
		}
		for (int i = 0; i < SCBP_SIZE; i++) {
			for (int j = 0; j < MADE_SIZE; j++) {
				for (int k = 0; k < MADE_SIZE; k++) {
					SCBPMadeFlopToMadeRiver[i][j][k] = 0;
					SCBPMadeFlopToMadeWon[i][j][k] = 0;
				}
			}
		}
		for (int i = 0; i < SCBP_SIZE; i++) {
			for (int j = 0; j < DRAW_SIZE; j++) {
				for (int k = 0; k < MADE_SIZE; k++) {
					SCBPDrawTurnToMadeRiver[i][j][k] = 0;
					SCBPDrawTurnToMadeWon[i][j][k] = 0;
				}
			}
		}
		for (int i = 0; i < SCBP_SIZE; i++) {
			for (int j = 0; j < MADE_SIZE; j++) {
				for (int k = 0; k < MADE_SIZE; k++) {
					SCBPMadeTurnToMadeRiver[i][j][k] = 0;
					SCBPMadeTurnToMadeWon[i][j][k] = 0;
				}
			}
		}

		for (int i = 0; i < HML_FLOP_SIZE; i++) {
			for (int j = 0; j < DRAW_SIZE; j++) {
				for (int k = 0; k < BOARD_SIZE; k++) {
					hmlBoardFlopToMadeRiver[i][j][k] = 0;
					hmlBoardFlopToMadeWon[i][j][k] = 0;
				}
			}
		}

		for (int i = 0; i < HML_TURN_SIZE; i++) {
			for (int j = 0; j < MADE_SIZE; j++) {
				for (int k = 0; k < BOARD_SIZE; k++) {
					hmlBoardTurnToMadeRiver[i][j][k] = 0;
					hmlBoardTurnToMadeWon[i][j][k] = 0;
				}
			}
		}

		for (int i = 0; i < WET_DRY_SIZE; i++) {
			for (int j = 0; j < DRAW_SIZE; j++) {
				for (int k = 0; k < BOARD_SIZE; k++) {
					wetDryBoardFlopToMadeRiver[i][j][k] = 0;
					wetDryBoardFlopToMadeWon[i][j][k] = 0;
				}
			}
		}

		for (int i = 0; i < WET_DRY_SIZE; i++) {
			for (int j = 0; j < MADE_SIZE; j++) {
				for (int k = 0; k < BOARD_SIZE; k++) {
					wetDryBoardTurnToMadeRiver[i][j][k] = 0;
					wetDryBoardTurnToMadeWon[i][j][k] = 0;
				}
			}
		}

		for (int i = 0; i < TYPE_OF_1755_SIZE; i++) {
			for (int j = 0; j < DRAW_SIZE; j++) {
				for (int k = 0; k < BOARD_SIZE; k++) {
					typeOf1755BoardFlopToMadeRiver[i][j][k] = 0;
					typeOf1755BoardFlopToMadeWon[i][j][k] = 0;
				}
			}
		}
		for (int i = 0; i < TYPE_OF_1755_SIZE; i++) {
			for (int j = 0; j < MADE_SIZE; j++) {
				for (int k = 0; k < BOARD_SIZE; k++) {
					typeOf1755BoardTurnToMadeRiver[i][j][k] = 0;
					typeOf1755BoardTurnToMadeWon[i][j][k] = 0;
				}
			}
		}
		for (int i = 0; i < SCBP_SIZE; i++) {
			for (int j = 0; j < DRAW_SIZE; j++) {
				for (int k = 0; k < BOARD_SIZE; k++) {
					SCBPBoardFlopToMadeRiver[i][j][k] = 0;
					SCBPBoardFlopToMadeWon[i][j][k] = 0;
				}
			}
		}
		for (int i = 0; i < SCBP_SIZE; i++) {
			for (int j = 0; j < MADE_SIZE; j++) {
				for (int k = 0; k < BOARD_SIZE; k++) {
					SCBPBoardTurnToMadeRiver[i][j][k] = 0;
					SCBPBoardTurnToMadeWon[i][j][k] = 0;
				}
			}
		}

		hmlCountFlop = 0;
		hmlCountTurn = 0;
		hmlCountRiver = 0;
		typeCount = 0;
		wetDryCount = 0;
		typeOf1755Count = 0;
		SCBPCount = 0;

		type1755Count = 0;
		flopIndexCount = 0;
		handValueCount = 0;
		sumOfDrawCount = 0;
		sumOfMadeCount = 0;
		sumOfShowdownCount = 0;
		boardArrayFlopCount = 0;
		boardArrayTurnCount = 0;
		boardArrayRiverCount = 0;
		flopArraysCount = 0;
		turnArraysCount = 0;
		riverArraysCount = 0;
		showdownCount = 0;
	}

}
