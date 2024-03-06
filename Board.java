package holdemevaluator;

public class Board implements Constants {

	/*- ******************************************************************************************
	 * This Class is part of a group of related Classes that analyze the Eval.both array
	 * of combined hole cards and board cards. They also analyze the board.
	 * This analysis is the heart of this application. 
	 * 
	 * This Class contains several methods used to  gather information about 
	 * the board and board combined with hole cards. 
	 * 
	 * @author PEAK_ 
	 *********************************************************************************************/

	static StringBuilder boardStatusSB = new StringBuilder();
	static StringBuilder flopStatusSB = new StringBuilder();
	static String boardStatusSt = "";
	static String flopStatusSt = "";

	static boolean[] boardArray = new boolean[BOARD_SIZE];

	/*-  ************************************************************************************* 
	 * For the hand currently being evaluated
	* Sorted high to low, not in the order dealt  
	  ************************************************************************************** */
	static Card[] board = new Card[5];
	static Card[] boardUnsorted = new Card[5];
	static final Card[] boardCards = new Card[5];
	static int boardHighCardValue1 = -1; // Highest
	static int boardHighCardValue2 = -1; // Second highest
	static int boardLowCardValue = -1; // Lowest
	static int boardMiddleCardValue = -1; // Middle
	static int boardCount = -1;
	static int max = -1;
	static int min = 99;

	static boolean boardAceHigh = false;
	static boolean boardPair = false;
	static int boardPair1Value = -1;
	static int boardPair2Value = -1;
	static int boardPair3Value = -1;
	static boolean boardTwoPair = false;
	static int boardTwoPairValue1 = -1;
	static int boardTwoPairValue2 = -1;
	static boolean boardSet = false;
	static boolean boardQuad = false;
	static boolean boardFull = false;
	static int boardSetValue = -1;
	static int boardHighCardValue = -1;
	static boolean boardF0 = false; // Rainbow
	static boolean boardF2 = false; // Two suited
	static boolean boardF3 = false;
	static boolean boardF4 = false;
	static boolean boardFLUSH = false;
	static boolean boardGap1 = false;
	static boolean boardGap2 = false;
	static boolean boardGap0Gap0 = false;
	static boolean boardGap0Gap1 = false;
	static boolean boardGap0Gap2 = false;
	static boolean boardGap1Gap0 = false;
	static boolean boardGap1Gap1 = false;
	static boolean boardGap1Gap2 = false;
	static boolean boardGap2Gap0 = false;
	static boolean boardGap2Gap1 = false;
	static boolean boardGap2Gap2 = false;

	private static boolean boardAcePlusHigh = false;
	private static boolean boardAcePlus2High = false;
	private static boolean boardAcePlus3High = false;
	private static boolean board1High = false;
	private static boolean board2High = false;
	private static boolean board3High = false;

	static int gap0 = -1;
	static int gap1 = -1;
	static int gap2 = -1;

	private Board() {
		throw new IllegalStateException("Utility class");
	}

	/*- **************************************************************************** 
	* Initialize
	*******************************************************************************/
	private static void initialize() {
		boardAceHigh = false;
		boardPair = false;
		boardPair1Value = -1;
		boardPair2Value = -1;
		boardPair3Value = -1;
		boardTwoPair = false;
		boardTwoPairValue1 = -1;
		boardTwoPairValue2 = -1;
		boardSet = false;
		boardQuad = false;
		boardFull = false;
		boardSetValue = -1;
		boardHighCardValue = -1;

		boardF0 = false;
		boardF2 = false;
		boardF3 = false;
		boardF4 = false;
		boardFLUSH = false;
		boardGap1 = false;
		boardGap2 = false;
		boardGap0Gap0 = false;
		boardGap0Gap1 = false;
		boardGap0Gap2 = false;
		boardGap1Gap0 = false;
		boardGap1Gap1 = false;
		boardGap1Gap2 = false;
		boardGap2Gap0 = false;
		boardGap2Gap1 = false;
		boardGap2Gap2 = false;

		boardAcePlusHigh = false;
		boardAcePlus2High = false;
		boardAcePlus3High = false;
		board1High = false;
		board2High = false;
		board3High = false;
	}

	/*- **************************************************************************** 
	* For Flop
	*******************************************************************************/
	static void doFlop() {
		sortFlop();
		initialize();
		suits();
		pairedAndValues();
		boardGaps();
		flopBoardValues();
		updateBoardStatus();
		if (EvalData.handInfo) {
			updateBoardStatus();
		}
		buildBoardArray();
		IndexArrayClassification.doFlop();
	}

	/*- **************************************************************************** 
	* For Turn  
	*******************************************************************************/
	static void doTurn() {
		sortBoardTurn();
		initialize();
		pairedAndValues();
		boardGaps();
		flopBoardValues();
		turnBoardValues();
		if (EvalData.handInfo) {
			updateBoardStatus();
		}
		buildBoardArray();
		IndexArrayClassification.doTurn();
	}

	/*- **************************************************************************** 
	* For River - no draws
	*******************************************************************************/
	static void doRiver() {
		sortBoardRiver();
		initialize();
		pairedAndValues();
		boardGaps();
		flopBoardValues();
		turnBoardValues();
		riverBoardValues();
		if (EvalData.handInfo) {
			updateBoardStatus();
		}
		buildBoardArray();
		IndexArrayClassification.doRiver();
	}

	/*- ******************************************************************************
	 *  Sort the first 3 cards in the board, high cards first
	 *  Copy from Board.unsorted to Board.board then sort.
	*******************************************************************************/
	static void sortFlop() {
		Board.boardCount = 3;
		EvalData.street = FLOP;
		Board.board[0] = Board.boardUnsorted[0];
		Board.board[1] = Board.boardUnsorted[1];
		Board.board[2] = Board.boardUnsorted[2];
		Board.boardCards[0] = Board.board[0];
		Board.boardCards[1] = Board.board[1];
		Board.boardCards[2] = Board.board[2];
		SortCards.sortCardsValue(Board.board, 3);
		SortCards.sortCardsCard(Board.boardCards, 3);
	}

	/*- ******************************************************************************
	 *  Sort Board Turn
	 *  Copy from Board.unsorted to Board.board then sort.  
	*******************************************************************************/
	static void sortBoardTurn() {
		Board.boardCount = 4;
		EvalData.street = TURN;
		Board.board[0] = Board.boardUnsorted[0];
		Board.board[1] = Board.boardUnsorted[1];
		Board.board[2] = Board.boardUnsorted[2];
		Board.board[3] = Board.boardUnsorted[3];
		Board.boardCards[0] = Board.boardUnsorted[0];
		Board.boardCards[1] = Board.boardUnsorted[1];
		Board.boardCards[2] = Board.boardUnsorted[2];
		Board.boardCards[3] = Board.boardUnsorted[3];
		SortCards.sortCardsValue(Board.board, 4);
		SortCards.sortCardsCard(Board.boardCards, 4);
	}

	/*- ******************************************************************************
	 *  Sort Board River
	 *  Copy from Board.unsorted to Board.board then sort.  
	*******************************************************************************/
	static void sortBoardRiver() {
		Board.boardCount = 5;
		EvalData.street = RIVER;
		Board.board[0] = Board.boardUnsorted[0];
		Board.board[1] = Board.boardUnsorted[1];
		Board.board[2] = Board.boardUnsorted[2];
		Board.board[3] = Board.boardUnsorted[3];
		Board.board[4] = Board.boardUnsorted[4];
		Board.boardCards[0] = Board.boardUnsorted[0];
		Board.boardCards[1] = Board.boardUnsorted[1];
		Board.boardCards[2] = Board.boardUnsorted[2];
		Board.boardCards[3] = Board.boardUnsorted[3];
		Board.boardCards[4] = Board.boardUnsorted[4];
		SortCards.sortCardsValue(Board.board, 5);
		SortCards.sortCardsCard(Board.boardCards, 5);
	}

	/*-**********************************************************************************************
	* This method will examine the Flop board 
	*************************************************************************************************/
	private static void flopBoardValues() {
		boardHighCardValue1 = board[0].value;
		if (board[0].value != board[1].value) {
			boardHighCardValue2 = board[1].value;
		} else if (board[0].value != board[2].value) {
			boardHighCardValue2 = board[2].value;
		}
		boardLowCardValue = board[2].value;
		boardMiddleCardValue = board[1].value;
		if (board[0].value == ACE) {
			boardAceHigh = true;
			boardHighCardValue = board[0].value;
		}

		if (!boardF2 && !boardF3) {
			boardF0 = true;
		}

		if (board[0].value == ACE && board[1].value >= TEN && board[2].value < TEN) {
			boardAcePlusHigh = true;
		}
		if (board[0].value == ACE && board[1].value >= TEN && board[2].value >= TEN) {
			boardAcePlus2High = true;
		}
		if (board[0].value >= TEN && board[1].value < TEN) {
			board1High = true;
		}
		if (board[1].value >= TEN && board[2].value < TEN) {
			board2High = true;
		}
		if (board[2].value >= TEN) {
			board3High = true;
		}
	}

	/*-**********************************************************************************************
		* This method will examine the Turn board 
	*************************************************************************************************/
	private static void turnBoardValues() {
		boardHighCardValue1 = board[0].value;
		if (board[0].value != board[1].value) {
			boardHighCardValue2 = board[1].value;
		} else if (board[0].value != board[2].value) {
			boardHighCardValue2 = board[2].value;
		} else if (board[0].value != board[3].value) {
			boardHighCardValue2 = board[3].value;
		}
		boardLowCardValue = board[3].value;
		if (board[1].value > board[2].value) {
			boardMiddleCardValue = board[1].value;
		} else {
			boardMiddleCardValue = board[2].value;
		}

		if (board[0].value == ACE) {
			boardAceHigh = true;
			boardHighCardValue = board[0].value;
		}
		boardLowCardValue = board[3].value;
	}

	/*-**********************************************************************************************
	* This method will
	*************************************************************************************************/
	private static void riverBoardValues() {
		boardHighCardValue1 = board[0].value;
		if (board[0].value != board[1].value) {
			boardHighCardValue2 = board[1].value;
		} else if (board[0].value != board[2].value) {
			boardHighCardValue2 = board[2].value;
		} else if (board[0].value != board[3].value) {
			boardHighCardValue2 = board[3].value;
		} else if (board[0].value != board[4].value) {
			boardHighCardValue2 = board[4].value;
		}

		if (board[1].value > board[2].value) {
			boardMiddleCardValue = board[1].value;
		} else {
			boardMiddleCardValue = board[2].value;
		}

		if (board[0].value == ACE) {
			boardAceHigh = true;
			boardHighCardValue = board[0].value;
		}
	}

	/*- *****************************************************************************
	 * This method will look at the board and  determine what gaps there are.
	 * 
	 * A gap of one would be Js9d ( no Tx ), card values are not consecutive. 
	 * A Ten is needed to complete the sequence, One gap.
	 * Two gaps Qs9c. 
	 * A2 is a special case as an Ace is both a 12 and a 1.
	 * There may be multiple gaps in a board, especially a Turn board with 6 cards.
	 * River boards are not analyzed because there will be no opportunity to draw.
	 * 
	 * Gap data in EvalData is updated. These are boolean
	 * 	gap0
	 * 	gap1
	 * 	gap2
	 ******************************************************************************/
	static void boardGaps() {
		boolean gap0A = false;
		boolean gap1A = false;
		boolean gap2A = false;
		boolean gap0B = false;
		boolean gap1B = false;
		boolean gap2B = false;

		if (board[0].value - board[1].value == 0)
			gap0A = true;
		if (board[0].value - board[1].value == 1)
			gap1A = true;
		if (board[0].value - board[1].value == 2)
			gap2A = true;
		if (board[1].value - board[2].value == 0)
			gap0B = true;
		if (board[1].value - board[2].value == 1)
			gap1B = true;
		if (board[1].value - board[2].value == 2)
			gap2B = true;

		if (gap0A && gap0B) {
			boardGap0Gap0 = true;
		}
		if (gap0A && gap1B) {
			boardGap0Gap1 = true;
		}
		if (gap0A && gap2B) {
			boardGap0Gap2 = true;
		}

		if (gap1A && gap0B) {
			boardGap1Gap0 = true;
		}
		if (gap1A && gap1B) {
			boardGap1Gap1 = true;
		}
		if (gap1A && gap2B) {
			boardGap1Gap2 = true;
		}

		if (gap2A && gap0B) {
			boardGap2Gap0 = true;
		}
		if (gap2A && gap1B) {
			boardGap2Gap1 = true;
		}
		if (gap2A && gap2B) {
			boardGap2Gap2 = true;
		}

		for (int i = 0; i < boardCount - 1; i++) {
			if (board[i].value - board[i + 1].value == 0) {
				++gap0;
			} else if (board[i].value - board[i + 1].value == 1) {
				++gap1;
			} else if (board[i].value - board[i + 1].value == 2) {
				++gap2;
			}
		}

		if (board[0].value == ACE && board[boardCount - 1].value == TWO) {
			++gap1;
		} else if (board[0].value == ACE && board[boardCount - 1].value == THREE) {
			++gap2;
		}

		if (gap1 != 0) {
			boardGap1 = true;
		}
		if (gap2 != 0) {
			boardGap2 = true;
		}
	}

	/*-  ******************************************************************************
	 * Update board status
	 *  ****************************************************************************** */
	private static void updateBoardStatus() {
		boardStatusSB = new StringBuilder();
		if (boardAcePlusHigh) {
			boardStatusSB.append(board[0].toString()).append(board[1].toString()).append("Ace + High, ");
		}
		if (boardAcePlus2High) {
			boardStatusSB.append("Ace +High+High, ");
		}
		if (boardAcePlus3High) {
			boardStatusSB.append("Ace +High+High+High, ");
		}
		if (board1High) {
			boardStatusSB.append("One High cards, ");
		}
		if (board2High) {
			boardStatusSB.append("Two High Cards, ");
		}
		if (board3High) {
			boardStatusSB.append("Three High Cards, ");
		}

		if (gap0 == 1) {
			boardStatusSB.append("One gap 0, ");
		} else if (gap0 == 2) {
			boardStatusSB.append("Two gap 0, ");
		} else if (gap0 == 3) {
			boardStatusSB.append("Three gap 0, ");
		}
		if (gap1 == 1) {
			boardStatusSB.append("One gap 1, ");
		} else if (gap1 == 2) {
			boardStatusSB.append("Two gap 1, ");
		} else if (gap1 == 3) {
			boardStatusSB.append("Three gap 1, ");
		}
		if (gap0 == 2) {
			boardStatusSB.append("One gap 2, ");
		} else if (gap0 == 2) {
			boardStatusSB.append("Two gap 2, ");
		} else if (gap0 == 2) {
			boardStatusSB.append("Three gap 2, ");
		}

		if (boardGap1) {
			boardStatusSB.append("One 1 Gap, ");
		}
		if (boardGap2) {
			boardStatusSB.append("Two one Gaps, ");
		}
		if (boardGap0Gap0) {
			boardStatusSB.append("Gap0 and Gap0 (set) , ");
		}
		if (boardGap0Gap1) {
			boardStatusSB.append("Gap0 and Gap1, ");
		}
		if (boardGap0Gap2) {
			boardStatusSB.append("Gap0 and Gap2, ");
		}
		if (boardGap1Gap0) {
			boardStatusSB.append("Gap0 and Gap0, ");
		}
		if (boardGap1Gap1) {
			boardStatusSB.append("Gap1 and Gap1, ");
		}
		if (boardGap1Gap2) {
			boardStatusSB.append("Gap1 and Gap2, ");
		}
		if (boardGap2Gap0) {
			boardStatusSB.append("Gap2 and Gap0, ");
		}
		if (boardGap2Gap1) {
			boardStatusSB.append("Gap2 and Gap1, ");
		}
		if (boardGap2Gap2) {
			boardStatusSB.append("Gap2 and Gap2, ");
		}
		if (boardAceHigh) {
			boardStatusSB.append("Ace High, ");
		}
		if (boardPair) {
			boardStatusSB.append("Board Pair,  ");
			boardStatusSB.append("Board pair value ").append(String.valueOf(boardPair1Value)).append(" , ");
			if (boardPair2Value != -1) {
				boardStatusSB.append("Board pair 2 value ").append(String.valueOf(boardPair2Value)).append(" , ");
			} else if (boardPair2Value != -1) {
				boardStatusSB.append("Board pair 3 value ").append(String.valueOf(boardPair3Value)).append(" , ");
			}
		}
		if (boardTwoPair) {
			boardStatusSB.append("Two Pair, ");
		}
		if (boardSet) {
			boardStatusSB.append("Set, ");
		}
		if (boardF0) {
			boardStatusSB.append("Rainbow, ");
		}
		if (boardF2) {
			boardStatusSB.append("2 Flush, ");
		}
		if (boardF3) {
			boardStatusSB.append("3 Flush, ");
		}
		if (boardF4) {
			boardStatusSB.append("4 Flush, ");
		}
		if (boardFLUSH) {
			boardStatusSB.append("Made Flush, ");
		}

	}

	/*-****************************************************************************** 
	* This method will suits in the both array
	* It will then check for gaps between suits
	*********************************************************************************/
	private static void suits() {
		int[] suitArray = { 0, 0, 0, 0 };

		for (int i = 0; i < boardCount; i++) {
			++suitArray[boardCards[i].suit];
		}
		for (int i = 0; i < 4; i++) {
			switch (suitArray[i]) {
			case 2 -> {
				boardF2 = true;
			}
			case 3 -> {
				boardF3 = true;
			}
			case 4 -> {
				boardF4 = true;
			}
			case 5 -> {
				boardFLUSH = true;
			}
			default -> {
			}
			}
		}
	}

	/*-**********************************************************************************************
	 * This method will look at the cards in board cards and determine if any of 
	 * the cards are paired. If they are, then determine what kind.
	 * onePair, two pair, three of a kind, full house, and four of a kind.
	*************************************************************************************************/
	private static void pairedAndValues() {
		int[] valueArray = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		int p = 0;
		int s = 0;
		int q = 0;

		max = -1;
		min = 99;
		int k = 0;
		for (int i = 0; i < boardCount; i++) {
			k = boardCards[i].value;
			if (k > max)
				max = k;
			if (k < min)
				min = k;
			++valueArray[k];
		}

		for (int i = max; i >= min; i--) {
			if (valueArray[i] == 2) {
				++p;
				if (p == 1) {
					boardPair1Value = i;
				} else if (p == 2) {
					boardPair3Value = i;
				} else {
					boardPair3Value = i;
				}
			} else if (valueArray[i] == 3) {
				++s;
				boardSetValue = i;
			} else if (valueArray[i] == 4) {
				++q;
			}
		}
		if (q >= 1) {
			boardQuad = true;
		} else if ((p >= 1 && s >= 1) || s > 1) {
			boardFull = true;
		} else if (s == 1) {
			boardSet = true;
		} else if (p == 2) {
			boardTwoPair = true;
		} else if (p == 1) {
			boardPair = true;
		}

		// Two pair values
		if (EvalData.street == TURN) {
			// PPQQ
			boardTwoPairValue1 = board[0].value;
			boardTwoPairValue2 = board[2].value;
		} else if (EvalData.street == RIVER) {
			// PPQQx PPxQQ xPPQQ
			if (board[0].value == board[1].value) {
				boardTwoPairValue1 = board[0].value;
				if (board[2].value == board[3].value) {
					boardTwoPairValue2 = board[2].value;
				} else {
					boardTwoPairValue2 = board[3].value;
				}
			} else
				boardTwoPairValue1 = board[0].value;
			boardTwoPairValue2 = board[2].value;
		}
	}

	/*-**********************************************************************************************
	* This method will built the boardArray
	*************************************************************************************************/
	private static void buildBoardArray() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			boardArray[i] = false;
		}

		if (boardGap1) {
			boardArray[BOARD_GAP1] = true;
		}
		if (boardGap2) {
			boardArray[BOARD_GAP2] = true;
		}
		if (boardGap0Gap0) {
			boardArray[BOARD_GAP0_GAP0] = true;
		}
		if (boardGap0Gap1) {
			boardArray[BOARD_GAP0_GAP1] = true;
		}
		if (boardGap0Gap2) {
			boardArray[BOARD_GAP0_GAP2] = true;
		}
		if (boardGap1Gap0) {
			boardArray[BOARD_GAP1_GAP0] = true;
		}
		if (boardGap1Gap1) {
			boardArray[BOARD_GAP1_GAP1] = true;
		}
		if (boardGap1Gap2) {
			boardArray[BOARD_GAP1_GAP2] = true;
		}
		if (boardGap2Gap0) {
			boardArray[BOARD_GAP2_GAP0] = true;
		}
		if (boardGap2Gap1) {
			boardArray[BOARD_GAP2_GAP1] = true;
		}
		if (boardGap2Gap2) {
			boardArray[BOARD_GAP2_GAP2] = true;
		}
		if (boardF0) {
			boardArray[BOARD_F0] = true;
		}
		if (boardF2) {
			boardArray[BOARD_F2] = true;
		}
		if (boardF3) {
			boardArray[BOARD_F3] = true;
		}
		if (boardAceHigh) {
			boardArray[BOARD_ACE_HIGH] = true;
		}
		if (boardAcePlusHigh) {
			boardArray[BOARD_ACE_PLUS_1HIGH] = true;
		}
		if (boardAcePlus2High) {
			boardArray[BOARD_ACE_PLUS_2HIGH] = true;
		}
		if (boardAcePlus3High) {
			boardArray[BOARD_ACE_3HIGH] = true;
		}
		if (board1High) {
			boardArray[BOARD_1HIGH] = true;
		}
		if (board2High) {
			boardArray[BOARD_2HIGH] = true;
		}
		if (board3High) {
			boardArray[BOARD_3HIGH] = true;
		}
		if (boardPair) {
			boardArray[BOARD_PAIR] = true;
		}
		if (boardTwoPair) {
			boardArray[BOARD_TWO_PAIR] = true;
		}
		if (boardSet) {
			boardArray[BOARD_SET] = true;
		}
		if (boardQuad) {
			boardArray[BOARD_QUAD] = true;
		}
		if (boardFull) {
			boardArray[BOARD_FULL] = true;
		}
		if (boardF4) {
			boardArray[BOARD_F4] = true;
		}
		if (boardFLUSH) {
			boardArray[BOARD_FLUSH] = true;
		}

	}

}
