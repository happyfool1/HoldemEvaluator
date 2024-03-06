package holdemevaluator;

/*-  **************************************************************************************
* This Class contains the methods used for Showdown.
* When the River evaluation is complete it will call showdownValue() to calculate the 
* relative value of the made hand on the River.
*
* After all players have completed the River evaluation showdown() will be called to
* determine the winner using values saved by showdownValue().
* A tie is possible but unusual.
* 
* @author PEAK_
***************************************************************************************/
public class Showdown implements Constants {

	/*-  **************************************************************************************
	* This Class contains the methods used for Showdown.
	* The public methods are:
	*   showdownValue()
	*  	showdown()
	* There are 2 methods that create Strings for display of the results:
	* 	showdownValueSt()
	*   showdownSt()	
	***************************************************************************************/

	private Showdown() {
		throw new IllegalStateException("Utility class");
	}

	/*- ****************************************************************************
	* Results of showdownValue() and showdown() that can be displayed
	* *****************************************************************************/
	static StringBuilder[] showdownPlayersSB = new StringBuilder[PLAYERS];
	static StringBuilder showdownResultSB = new StringBuilder();

	// Results at Showdown
	static int[][] showdownValue = new int[PLAYERS][MADE_SIZE];
	static int[] countShowdownValue = new int[PLAYERS];

	static int[][] showdownWon = new int[PLAYERS][MADE_SIZE];
	static int[] countShowdownWon = new int[PLAYERS];

	static int[] playerMax = new int[PLAYERS];

	/*- *****************************************************************************
	* This method ranks one players for showdown value.
	* The larger the value the better the hand.
	* There is no other significance to the value. 
	* 
	* Note that the numbers used like 804000 are arbitrary and were chosen only to
	* be sure that the resulting value is not duplicated.
	*
	*  For a Set the number is  setHighCard + 804000
	*  80400 is just a relative number with the value of the sets high card added to it 
	*  because all sets are not equal.  For most hand types there is some equivalent
	*  math for things like kickers.
	*  Methods that evaluate the board and hands were already required for evaluations
	*  other than showdown so I wrote this code. Turned out to be faster, a bonus.
	*  
	*  Arg0 - Seat
	*  Sets values in EvalData
	******************************************************************************/
	static void showdownValue(int seat) {
		int max = EvalData.players[seat].typeOfMadeRiver;
		if (max == -1) {
			Crash.log("Showdown");
		}
		GameControl.highCard1 = EvalData.players[seat].both[0].value;
		GameControl.highCard2 = EvalData.players[seat].both[1].value;

		GameControl.showdownHand[seat] = max;
		++showdownValue[seat][max];
		++countShowdownValue[seat];
		playerMax[seat] = max;
		if (max <= MADE_SET) {
			findKickers(seat);
		}

		if (max == MADE_ROYAL_FLUSH) {
			GameControl.maxValue = 816000;
		} else if (max == MADE_STRAIGHT_FLUSH) {
			GameControl.maxValue = GameControl.highCard1 + 814000;
		} else if (max == MADE_FOUR_OF_A_KIND) {
			GameControl.maxValue = GameControl.highCard1 + 812000;
		} else if (max == MADE_FULL_HOUSE) {
			GameControl.maxValue = GameControl.highCard1 + 100 * GameControl.highCard2 + 810000;
		} else if (max == MADE_FLUSH) {
			GameControl.maxValue = GameControl.highCard1 + 808000;
		} else if (max == MADE_STRAIGHT) {
			GameControl.maxValue = GameControl.highCard1 + 806000;
		} else if (max == MADE_SET) {
			GameControl.maxValue = GameControl.highCard1 + 804000 + GameControl.kicker1 * 100
					+ GameControl.kicker2 * 10;
		} else if (max == MADE_BOTTOM_TWO_PAIR || max == MADE_TOP_TWO_PAIR || max == MADE_BOARD_PAIR
				|| max == MADE_TOP_AND_BOTTOM_TWO_PAIR || max == MADE_BOTTOM_PAIR
				|| max == MADE_PAIR_BELOW_BOARD_PAIR) {
			GameControl.maxValue = 800000 + GameControl.highCard1 * 100 + GameControl.highCard2 * 10
					+ GameControl.kicker1;
		} else if (max == MADE_BOARD_PAIR || max == MADE_BOTTOM_PAIR || max == MADE_MIDDLE_PAIR || max == MADE_TOP_PAIR
				|| max == MADE_OVER_PAIR || max == MADE_POCKET_PAIR_BELOW_TP || max == MADE_WEAK_PAIR
				|| max == MADE_PAIR_ABOVE_BOARD_PAIR) {
			GameControl.maxValue = 500000 + GameControl.highCard1 * 4000 + GameControl.kicker1 * 200
					+ GameControl.kicker2 * 10 + GameControl.kicker3;
		} else if (max == MADE_NONE || max == MADE_ACE_HIGH || max == MADE_OVERCARDS) {
			GameControl.maxValue = GameControl.kicker1 * 10000 + GameControl.kicker2 * 800 + GameControl.kicker3 * 80;
		} else {
			// ERROR showdownValue() No hand found Pair > board pair 11
			Crash.log("ERROR showdownValue() No hand found " + MADE_ST[max] + " " + max);
			GameControl.maxValue = -1;
		}
		GameControl.showdownValue[seat] = max;
		GameControl.showdownRank[seat] = GameControl.maxValue;
		if (EvalData.handInfo) {
			showdownValueSB(seat);
		}
	}

	/*-**********************************************************************************************
	* This method will initialize data - called one time at start of new game
	*************************************************************************************************/
	static void initialize() {
		for (int i = 0; i < PLAYERS; i++) {
			for (int j = 0; j < MADE_SIZE; j++) {
				showdownValue[i][j] = 0;
				showdownWon[i][j] = 0;
			}
			countShowdownValue[i] = 0;
			countShowdownWon[i] = 0;
		}
	}

	/*- *****************************************************************************
	 * This method will determine the winner or winners of a hand.
	 * showdownValue() has already been called for all players that made it to the River. 
	 * The winner is the one with the largest showdown value.
	 * A tie is possible.
	 *  
	 *  Normal use in this project is:
	 *  	Run the Flop, Turn, and River collecting data for one seat. The Hero.
	 *  	Run showdownValue ic called on the River.
	 *  	When all players have completed the River call showdown to determine the
	 *  	winner and details on the winning hand.
	
	  ******************************************************************************/
	static void showdown() {
		GameControl.showdown = true;
		int max = -1;
		// Find player with highest showdown value
		for (int i = 0; i < PLAYERS; i++) {
			if (!GameControl.playerFolded[i]) {
				if (GameControl.showdownRank[i] > max) {
					max = GameControl.showdownRank[i];
					if (max == -1) {
						Crash.log("Showdown");
					}
				}
			} else {
				GameControl.showdownRank[i] = -1;
			}
		}
		for (int i = 0; i < PLAYERS; i++) {
			if (!GameControl.playerFolded[i]) {
				if (GameControl.showdownRank[i] < max) {
					GameControl.playerWonShowdown[i] = false;
					GameControl.showdownHand[i] = GameControl.showdownValue[i];
				} else {
					GameControl.playerWonShowdown[i] = true;
					GameControl.winnerSeat = i;
					++showdownWon[i][playerMax[i]];
					++countShowdownWon[i];
				}
			}
		}

		GameControl.showdown = GameControl.winner = true;
		if (EvalData.manyHands) {
			IndexArrayUpdate.updateShowdown();
		}
		if (EvalData.handInfo) {
			showdownSB();
		}
	}

	/*- *****************************************************************************
	 * A method to gather information on the players hand that called showdownValue. 
	 * It's only use is to display the results of the showdownValue method.
	 ***************************************************************************** */
	static void showdownValueSB(int seat) {
		showdownPlayersSB[seat] = new StringBuilder("");
		if (seat != 0) {
			showdownPlayersSB[seat].append("\r\n)");
		}
		showdownPlayersSB[seat].append("Seat ").append(String.valueOf(EvalData.seat + 1)).append(": ");

		if (GameControl.playerFolded[seat]) {
			showdownPlayersSB[seat].append("Player folded");
			return;
		}

		if (!GameControl.showdown) {
			showdownPlayersSB[seat].append("No Showdown");
			return;
		}
		switch (GameControl.showdownHand[seat]) {
		case MADE_ROYAL_FLUSH -> {
			showdownPlayersSB[seat].append("Royal Flush Woohoo Waited a long time for that!");
		}
		case MADE_STRAIGHT_FLUSH -> {
			showdownPlayersSB[seat].append("Straight Flush high card ")
					.append(Card.CARDS_REVERSE[GameControl.highCard1]);
		}
		case MADE_FOUR_OF_A_KIND -> {
			showdownPlayersSB[seat].append("Four of a kind high card " + Card.CARDS[GameControl.highCard1]);
		}
		case MADE_FULL_HOUSE -> {
			showdownPlayersSB[seat].append("Full House high card ").append(Card.CARDS_REVERSE[GameControl.highCard1])
					.append(" pair high card ").append(Card.CARDS_REVERSE[GameControl.highCard2]);
		}
		case MADE_FLUSH -> {
			showdownPlayersSB[seat].append("Flush high card  ").append(Card.CARDS_REVERSE[GameControl.highCard1]);
		}
		case MADE_STRAIGHT -> {
			showdownPlayersSB[seat].append("Straight high card " + Card.CARDS_REVERSE[GameControl.highCard1]);
		}
		case MADE_SET -> {
			findKickers(seat);
			showdownPlayersSB[seat].append("Set high card  ").append(Card.CARDS_REVERSE[GameControl.highCard1]);
		}
		case MADE_BOTTOM_TWO_PAIR -> {
			findKickers(seat);
			showdownPlayersSB[seat].append("Two pair ").append(Card.CARDS_REVERSE[GameControl.highCard1]);
		}
		case MADE_TOP_AND_BOTTOM_TWO_PAIR -> {
			findKickers(seat);
			showdownPlayersSB[seat].append("Two pair ").append(Card.CARDS_REVERSE[GameControl.highCard1]);
		}

		case MADE_TOP_TWO_PAIR -> {
			findKickers(seat);
			showdownPlayersSB[seat].append("Two pair ").append(Card.CARDS_REVERSE[GameControl.highCard1]);
		}
		case MADE_POCKET_PAIR_BELOW_TP -> {
			findKickers(seat);
			showdownPlayersSB[seat].append("Any pair card ").append(Card.CARDS_REVERSE[GameControl.highCard1]);
		}
		case MADE_MIDDLE_PAIR -> {
			findKickers(seat);
			showdownPlayersSB[seat].append("Any pair card ").append(Card.CARDS_REVERSE[GameControl.highCard1]);
		}
		case MADE_TOP_PAIR -> {
			findKickers(seat);
			showdownPlayersSB[seat].append("Any pair card ").append(Card.CARDS_REVERSE[GameControl.highCard1]);
		}
		case MADE_OVER_PAIR -> {
			findKickers(seat);
			showdownPlayersSB[seat].append("Any pair card ").append(Card.CARDS_REVERSE[GameControl.highCard1]);
		}
		case MADE_ACE_HIGH -> {
			findKickers(seat);
			showdownPlayersSB[seat].append("Ace high ");
		}
		case MADE_BOARD_PAIR -> {
			findKickers(seat);
			showdownPlayersSB[seat].append("Board pair ");
		}

		case MADE_PAIR_BELOW_BOARD_PAIR -> {
			findKickers(seat);
			showdownPlayersSB[seat].append("Pair below board pair ");
		}
		case MADE_PAIR_ABOVE_BOARD_PAIR -> {
			findKickers(seat);
			showdownPlayersSB[seat].append("Pair above board pair ");
		}
		case MADE_BOTTOM_PAIR -> {
			findKickers(seat);
			showdownPlayersSB[seat].append("Bottom pair ");
		}
		case MADE_OVERCARDS -> {
			findKickers(seat);
			showdownPlayersSB[seat].append("Overcards ");
		}
		case MADE_WEAK_PAIR -> {
			findKickers(seat);
			showdownPlayersSB[seat].append("Weak pair ");
		}
		case MADE_NONE -> {
			findKickers(seat);
			showdownPlayersSB[seat].append("No hand ").append(String.valueOf(GameControl.kicker1)).append(" ")
					.append(String.valueOf(GameControl.kicker2)).append(" ").append(String.valueOf(GameControl.kicker3))
					.append(" ");
		}
		default -> {
			Logger.log("//showdownValueString() hand not found " + GameControl.showdownHand[seat]);
		}
		}

		if (GameControl.kicker1 != -1) {
			showdownPlayersSB[seat].append("kickers ").append(Card.CARD_STRING[GameControl.kicker1]);
		}
		if (GameControl.kicker2 != -1) {
			showdownPlayersSB[seat].append(" ").append(Card.CARD_STRING[GameControl.kicker2]);
		}
		if (GameControl.kicker3 != -1) {
			showdownPlayersSB[seat].append(" ").append(Card.CARD_STRING[GameControl.kicker3]);
		}
	}

	/*- *****************************************************************************
	 * A method to gather information on the players hand that called showdown.
	 * It's only use is to display the results of the showdownValue method.
	 ***************************************************************************** */
	static void showdownSB() {
		int winners = 0;
		showdownResultSB = new StringBuilder("");
		for (int i = 0; i < PLAYERS; i++) {
			if (!GameControl.playerFolded[i] && GameControl.playerWonShowdown[i]) {
				++winners;
			}
		}
		if (winners == 1) {
			showdownResultSB.append("One winner ");
		} else {
			showdownResultSB.append("There was a tie ");
		}

		for (int i = 0; i < PLAYERS; i++) {
			final boolean condition = !GameControl.playerFolded[i] && GameControl.playerWonShowdown[i];
			if (condition) {
				showdownResultSB.append("Seat ").append(String.valueOf(i)).append(" had ")
						.append(MADE_ST[GameControl.showdownHand[i]]).toString();
				showdownResultSB.append(" won with ");
			}
		}
		showdownResultSB.append("The board was ").append(Board.board[0].toString()).append(Board.board[1].toString())
				.append(Board.board[2].toString()).append(Board.board[3].toString()).append(Board.board[4].toString())
				.append("\r\n");
	}

	/*- *****************************************************************************
	 *Find best three kickers. 
	 * The player can use 2 hole cards, 1 hole card, or  none at all.  
	 * It is simply the best 5 card hand. 
	 * There is only one kicker for two pair. 
	 * A pocket pair has no kickers. 
	 * A hole card paired with the board has 3 kickers 
	 * A board pair has 3 kickers. 
	 * No made hand is the best 5
	 ********************************************************************************/
	private static void findKickers(int seat) {
		final int[] best = { 0, 0, 0, 0, 0 };
		GameControl.kicker1 = -1;
		GameControl.kicker2 = -1;
		GameControl.kicker3 = -1;
		GameControl.kicker4 = -1;
		GameControl.kicker5 = -1;

		// Two pair - 1 kicker
		if (EvalData.players[seat].twoPair) {
			for (int i = 0; i < 5; ++i) {
				if (EvalData.players[seat].madeCardsRiver[i].value != EvalData.players[seat].pair1Value
						&& EvalData.players[seat].madeCardsRiver[i].value != EvalData.players[seat].pair2Value
						&& EvalData.players[seat].madeCardsRiver[i].value > GameControl.kicker1) {
					GameControl.kicker1 = EvalData.players[seat].madeCardsRiver[i].value;
				}
				return;
			}
		}
		// Set 2 kickers
		if (EvalData.players[seat].threeOfKind) {
			GameControl.kicker1 = best[0];
			GameControl.kicker2 = best[1];
		}
		// Four of kind one kickers
		if (EvalData.players[seat].fourOfKind) {
			GameControl.kicker1 = best[0];
		}

		// Pocket pair - no kickers ?? TODO 3 kickers?
		if (EvalData.players[seat].pocketPair) {
			GameControl.kicker1 = best[0];
			GameControl.kicker2 = best[1];
			GameControl.kicker3 = best[2];
			return;
		}

		// One pair = 3 kickers
		int highSoFar = 99;
		int cvj = 0;
		if (EvalData.players[seat].onePair) {
			for (int i = 0; i < 5; ++i) {
				if (EvalData.players[seat].madeCardsRiver[i] == null) {
					return;
				}
				cvj = EvalData.players[seat].madeCardsRiver[i].value;
				if (EvalData.players[seat].pair1Value != cvj && cvj < highSoFar && best[i] < cvj) {
					best[i] = cvj;
				}
				highSoFar = best[i];
			}
			GameControl.kicker1 = best[0];
			GameControl.kicker2 = best[1];
			GameControl.kicker3 = best[2];
			return;
		}

		// No made hand - best any 5 cards
		if (EvalData.players[seat].madeCardsRiver[0] == null || EvalData.players[seat].madeCardsRiver[1] == null
				|| EvalData.players[seat].madeCardsRiver[2] == null || EvalData.players[seat].madeCardsRiver[3] == null
				|| EvalData.players[seat].madeCardsRiver[4] == null) {
			GameControl.kicker1 = EvalData.players[seat].madeCardsRiver[0].value;
			GameControl.kicker2 = EvalData.players[seat].madeCardsRiver[1].value;
			GameControl.kicker3 = EvalData.players[seat].madeCardsRiver[2].value;
			GameControl.kicker4 = EvalData.players[seat].madeCardsRiver[3].value;
			GameControl.kicker5 = EvalData.players[seat].madeCardsRiver[4].value;
			return;
		}
		GameControl.kicker1 = EvalData.players[seat].madeCardsRiver[0].value;
		GameControl.kicker2 = EvalData.players[seat].madeCardsRiver[1].value;
		GameControl.kicker3 = EvalData.players[seat].madeCardsRiver[2].value;
		GameControl.kicker4 = EvalData.players[seat].madeCardsRiver[3].value;
		GameControl.kicker5 = EvalData.players[seat].madeCardsRiver[4].value;
	}
}
