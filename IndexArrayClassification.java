package holdemevaluator;

public class IndexArrayClassification implements Constants {

	/*- ***********************************************************************************
	* Variables used for HML
	 *************************************************************************************/
	private static boolean l1 = false;
	private static boolean m1 = false;
	private static boolean h1 = false;
	private static boolean l2 = false;
	private static boolean m2 = false;
	private static boolean h2 = false;
	private static boolean l3 = false;
	private static boolean m3 = false;
	private static boolean h3 = false;
	private static boolean l4 = false;
	private static boolean m4 = false;
	private static boolean h4 = false;
	private static boolean l5 = false;
	private static boolean m5 = false;
	private static boolean h5 = false;

	static int gap0Score = -1;
	static int gap1Score = -1;
	static int gap2Score = -1;

	/*-***************************************************************************
	* This method will classify Flop using:
	* 	Suitedness
	*   Connectivity	
	*   Big card
	* 	Paired
	*
	* Some compromises were used in order to keep the number of different Intrger
	* index values small. There are 16
	*****************************************************************************/

	private IndexArrayClassification() {
		throw new IllegalStateException("Utility class");
	}

	/*- **********************************************************************************
	* Do Flop characterization
	* Call only one time for each Flop
	*************************************************************************************/
	static void doFlop() {
		if (!EvalData.manyHands) {
			SortCards.sortCardsValue(Board.board, 3);
		}
		hmlBoardSetBoolean();
		hmlBoardFlop();
		boardGapScore(); // MUST be after HMLBoardFlop and before any other
		allBoardFlop();
		if (EvalData.hmlIndexFlop == -1 || EvalData.hmlIndexFlop >= HML_FLOP_SIZE) {
// TODO			Logger.logError("ERROR HML index invalid " + EvalData.hmlIndexFlop);
		}

		wetDry();
		if (EvalData.wetDryArray[WET]) {
			EvalData.wetDryIndexFlop = WET;
		} else if (EvalData.wetDryArray[DRY]) {
			EvalData.wetDryIndexFlop = DRY;
		} else {
			EvalData.wetDryIndexFlop = NOT_WET_DRY;
		}

		EvalData.flop1755IndexFlop = Flop1755Methods.getFlopIndex(Board.board[0], Board.board[1], Board.board[2]);
		EvalData.typeOf1755IndexFlop = Flop1755Methods.lookupFlopType(Board.board[0], Board.board[1], Board.board[2]);

		flopTextureSCBP();
		edMillerFlopType();
		flopTextureSuitednessConnectedness();
		flopTexturePairBroadway();
		flopTextureStrengthSuitedness();
		flopTextureDistributionFlushPotential();
		flopTextureValueSpread();
		flopTextureValueSpreadX();
		flopTextureA();
		flopTexturB();
	}

	/*- ************************************************************************************
	* Do Turn characterization
	 *************************************************************************************/
	static void doTurn() {
		hmlBoardTurn();
		if (EvalData.hmlIndexTurn == -1 || EvalData.hmlIndexTurn >= HML_TURN_SIZE) {
			Logger.logError("ERROR HML Turn index invalid " + EvalData.hmlIndexTurn);
		}
	}

	/*- ************************************************************************************
	* Do River characterization
	 *************************************************************************************/
	static void doRiver() {
		hmlBoardRiver();
	}

	/*- *****************************************************************************
	 *Set HML boolean values
	  ******************************************************************************/
	private static void hmlBoardSetBoolean() {
		h1 = false;
		m1 = false;
		l1 = false;
		h2 = false;
		m2 = false;
		l2 = false;
		h3 = false;
		m3 = false;
		l3 = false;
		h4 = false;
		m4 = false;
		l4 = false;
		h5 = false;
		m5 = false;
		l5 = false;
		if (Board.board[0].value >= TEN) {
			h1 = true;
		} else if (Board.board[0].value >= SIX) {
			m1 = true;
		} else {
			l1 = true;
		}
		if (Board.board[1].value >= TEN) {
			h2 = true;
		} else if (Board.board[1].value >= SIX) {
			m2 = true;
		} else {
			l2 = true;
		}
		if (Board.board[2].value >= TEN) {
			h3 = true;
			m3 = false;
			l3 = false;
		} else if (Board.board[2].value >= SIX) {
			m3 = true;
		} else {
			l3 = true;

		}

		if (EvalData.street >= TURN) {
			if (Board.board[3].value >= TEN) {
				h4 = true;
			} else if (Board.board[3].value >= SIX) {
				m4 = true;
			} else {
				l4 = true;
			}
		}

		if (EvalData.street == RIVER) {
			if (Board.board[4].value >= TEN) {
				h5 = true;
			} else if (Board.board[4].value >= SIX) {
				m5 = true;
			} else {
				l5 = true;
			}
		}
	}

	/*- *****************************************************************************
	 * This method will
	 * Gap score is very subjective but is an attempt to determine drawing possibilities
	 * with a combination of gaps. Gap score is used by other methods evaluatng board
	 * possibilities such as wet/dry or static /dynamic. 
	 * 
	 * Gap data in EvalData is updated:
	 *	gap0Score  
	 *	gap1Score   
	 * 	gap2Score 
	 * 
	 * This method used data from HML and creates data for Wet / Dry so the 
	 * order in which methods are called is critical.
	  ******************************************************************************/
	static void boardGapScore() {
		gap2Score = 0;
		gap1Score = 0;
		gap0Score = 0;

		if (Board.gap0 > 0) {
			if (Board.gap0 == 2) {
				gap0Score = 8;
			}
			if (Board.gap0 == 1 && Board.gap1 == 1) {
				gap0Score = 6;
			}
			if (Board.gap0 == 1 && Board.gap2 == 1) {
				gap0Score = 5;
			}
			if (Board.gap0 == 1) {
				gap0Score = 4;
			}
		} else if (Board.gap1 <= 0) {
			if (Board.gap2 > 0) {
				if (Board.gap2 == 1) {
					gap2Score = 1;
				}
				if (Board.gap2 == 2) {
					gap2Score = 2;
				}
			}
		} else {
			if (Board.gap1 == 1) {
				gap1Score = 4;
			}
			if (Board.gap1 == 1 && Board.gap2 == 1) {
				gap1Score = 6;
			}
			if (Board.gap1 == 2) {
				gap1Score = 8;
			}
		}

		if (Board.gap0 > 0) {
			gap0Score += 2;
			if (EvalData.hmlIndexFlop == HHH || EvalData.hmlIndexFlop == HHM) {
				gap0Score += 3;
			}
			if (EvalData.hmlIndexFlop == MMM || EvalData.hmlIndexFlop == HMM) {
				gap0Score += 2;
			}
		}
		if (Board.gap1 <= 0) {
			return;
		}
		if (EvalData.hmlIndexFlop == HHH || (EvalData.hmlIndexFlop == HHM && Board.board[0].value != ACE)) {
			gap1Score += 2;
		}
		if (EvalData.hmlIndexFlop == MMM || EvalData.hmlIndexFlop == HMM) {
			gap1Score += 2;
		}
		if (EvalData.hmlIndexFlop == MLL || (EvalData.hmlIndexFlop == LLL && Board.board[0].value != ACE)) {
			gap1Score += 1;
		}
	}

	/*- *****************************************************************************
	 * Experimental index
	 * Too many to be practical at the table BUT we can find out which are most useful.
	 * Uses combinations of all HML, Flop rainbow, 2 suited, 3 suited, and gap 
	 * combinations of Gap 0 (pair) gap1 ( KQ ) and gap2 ( KJ ).
	  ******************************************************************************/
	private static void allBoardFlop() {
		int gapIndex = 0;
		int suitedIndex = 0;
		int allIndex = 0;
		if (Board.boardGap0Gap0) {
		} else if (Board.boardGap0Gap1) {
			gapIndex = 1;
		} else if (Board.boardGap0Gap2) {
			gapIndex = 2;
		} else if (Board.boardGap1Gap0) {
			gapIndex = 3;
		} else if (Board.boardGap1Gap1) {
			gapIndex = 4;
		} else if (Board.boardGap1Gap2) {
			gapIndex = 5;
		} else if (Board.boardGap2Gap0) {
			gapIndex = 6;
		} else if (Board.boardGap2Gap1) {
			gapIndex = 7;
		} else if (Board.boardGap2Gap2) {
			gapIndex = 8;
		}
		if (Board.boardF0) {
			suitedIndex = 0;
		} else if (Board.boardF2) {
			suitedIndex = 1;
		} else if (Board.boardF3) {
			suitedIndex = 2;
		}
		allIndex = gapIndex + 1 + (suitedIndex * 3) + (EvalData.hmlIndexFlop * 27);
	}

	/*- *****************************************************************************
	 * Calculate HML value for any Flop
	 * Defined as: High is A-T, Middle is 9-6, Low as 5-2
	*  I believe that the update of a Flop as some combination of HML is an
	* 	opportunity to find a simple way to evaluate betting opportunities.
	* 	What this class does is conduct an experiment to see what value HML has. We
	*	will collect data and analyze to see Which of the 10 HML combinations has:
	*	Draws - any draws EV - How often will we have the best hand with each of the
	* 	10 HML combinations.
	  ******************************************************************************/
	private static void hmlBoardFlop() {
		if (h1 && h2 && h3) {
			EvalData.hmlIndexFlop = 9;
			return;
		}
		if (h1 && h2 && m3) {
			EvalData.hmlIndexFlop = 8;
			return;
		}
		if (h1 && h2 && l3) {
			EvalData.hmlIndexFlop = 7;
			return;
		}
		if (h1 && m2 && m3) {
			EvalData.hmlIndexFlop = 6;
			return;
		}
		if (h1 && m2 && l3) {
			EvalData.hmlIndexFlop = 5;
			return;
		}
		if (h1 && l2 && l3) {
			EvalData.hmlIndexFlop = 4;
			return;
		}
		if (m1 && m2 && m3) {
			EvalData.hmlIndexFlop = 3;
			return;
		}
		if (m1 && m2 && l3) {
			EvalData.hmlIndexFlop = 2;
			return;
		}
		if (m1 && l2 && l3) {
			EvalData.hmlIndexFlop = 1;
			return;
		}
		if (l1 && l2 && l3) {
			EvalData.hmlIndexFlop = 0;
			return;
		}
		Crash.log("ERROR HML Flop index invalid " + EvalData.hmlIndexFlop);
	}

	/*- *****************************************************************************
	* Calculate HML value for any Turn
	* 
	* Defined as: High is A-T, Middle is 9-6, Low as 5-2
	* 
	 ******************************************************************************/
	private static void hmlBoardTurn() {
		hmlBoardSetBoolean();
		if (h1 && h2 && h3 && h4) {
			EvalData.hmlIndexTurn = 14;
			return;
		}
		if (h1 && h2 && h3 && m4) {
			EvalData.hmlIndexTurn = 13;
			return;
		}
		if (h1 && h2 && h3 && l4) {
			EvalData.hmlIndexTurn = 12;
			return;
		}
		if (h1 && h2 && m3 && m4) {
			EvalData.hmlIndexTurn = 11;
			return;
		}
		if (h1 && h2 && m3 && l4) {
			EvalData.hmlIndexTurn = 10;
			return;
		}
		if (h1 && h2 && l3 && l4) {
			EvalData.hmlIndexTurn = 9;
			return;
		}
		if (h1 && m2 && m3 && m4) {
			EvalData.hmlIndexTurn = 8;
			return;
		}
		if (h1 && m2 && m3 && l4) {
			EvalData.hmlIndexTurn = 7;
			return;
		}
		if (h1 && m2 && l3 && l4) {
			EvalData.hmlIndexTurn = 6;
			return;
		}
		if (h1 && l2 && l3 && l4) {
			EvalData.hmlIndexTurn = 5;
			return;
		}
		if (m1 && m2 && m3 && m4) {
			EvalData.hmlIndexTurn = 4;
			return;
		}
		if (m1 && m2 && m3 && l4) {
			EvalData.hmlIndexTurn = 3;
			return;
		}
		if (m1 && m2 && l3 && l4) {
			EvalData.hmlIndexTurn = 2;
			return;
		}
		if (m1 && l2 && l3 && l4) {
			EvalData.hmlIndexTurn = 1;
			return;
		}
		if (l1 && l2 && l3 && l4) {
			EvalData.hmlIndexTurn = 0;
			return;
		}
		// Crash.log("ERROR HML Turn index invalid " + EvalData.hmlIndexTurn);
	}

	/*- *****************************************************************************
	* Calculate HML value for any River
	 ******************************************************************************/
	private static void hmlBoardRiver() {
		hmlBoardSetBoolean();
		// HHHHH
		if (h1 && h2 && h3 && h4 && h5) {
			EvalData.hmlIndexRiver = 20;
			return;
		}
		// HHHHM
		if (h1 && h2 && h3 && h4 && m5) {
			EvalData.hmlIndexRiver = 19;
			return;
		}
		// HHHHL
		if (h1 && h2 && h3 && h4 && l5) {
			EvalData.hmlIndexRiver = 18;
			return;
		}
		// HHHMM
		if (h1 && h2 && h3 && m4 && m5) {
			EvalData.hmlIndexRiver = 17;
			return;
		}
		// HHHML
		if (h1 && h2 && h3 && m4 && l5) {
			EvalData.hmlIndexRiver = 16;
			return;
		}
		// HHHLL
		if (h1 && h2 && h3 && l4 && l5) {
			EvalData.hmlIndexRiver = 15;
			return;
		}
		// HHMMM
		if (h1 && h2 && m3 && m4 && m5) {
			EvalData.hmlIndexRiver = 14;
			return;
		}
		// HHMML
		if (h1 && h2 && m3 && m4 && l5) {
			EvalData.hmlIndexRiver = 13;
			return;
		}
		// HHMLL
		if (h1 && h2 && m3 && l4 && l5) {
			EvalData.hmlIndexRiver = 12;
			return;
		}

		if (h1 && h2 && l3 && l4 && l5) {
			EvalData.hmlIndexRiver = 11;
			return;
		}
		// HMMMM
		if (h1 && m2 && m3 && m4 && m5) {
			EvalData.hmlIndexRiver = 10;
			return;
		}
		// HMMML
		if (h1 && m2 && m3 && m4 && l5) {
			EvalData.hmlIndexRiver = 9;
			return;
		}
		// HMMLL
		if (h1 && m2 && m3 && l4 && l5) {
			EvalData.hmlIndexRiver = 8;
			return;
		}
		// HMLLL
		if (h1 && m2 && l3 && l4 && l5) {
			EvalData.hmlIndexRiver = 7;
			return;
		}
		// HLLLL
		if (h1 && l2 && l3 && l4 && l5) {
			EvalData.hmlIndexRiver = 6;
			return;
		}
		// MMMMM
		if (m1 && m2 && m3 && m4 && m5) {
			EvalData.hmlIndexRiver = 5;
			return;
		}
		// MMMML
		if (m1 && m2 && m3 && m4 && l5) {
			EvalData.hmlIndexRiver = 4;
			return;
		}
		// MMMLL
		if (m1 && m2 && m3 && l4 && l5) {
			EvalData.hmlIndexRiver = 3;
			return;
		}
		// MMLLL
		if (m1 && m2 && l3 && l4 && l5) {
			EvalData.hmlIndexRiver = 2;
			return;
		}
		// MLLLL
		if (m1 && l2 && l3 && l4 && l5) {
			EvalData.hmlIndexRiver = 1;
			return;
		}
		// LLLLL
		if (l1 && l2 && l3 && l4 && l5) {
			EvalData.hmlIndexRiver = 0;
			return;
		}

		// ERROR HML River index invalid -1
		System.out.println("//boardUnsorted  " + Board.boardUnsorted[0] + " " + Board.boardUnsorted[1] + " "
				+ Board.boardUnsorted[2] + " " + Board.boardUnsorted[3] + " " + Board.boardUnsorted[4] + "    " + "    "
				+ GameControl.handsPlayed);
		System.out.println("//board  " + Board.board[0] + " " + Board.board[1] + " " + Board.board[2] + " "
				+ Board.board[3] + " " + Board.board[4] + " " + GameControl.handsPlayed);
		System.out.println(
				new StringBuilder().append("//XXX ").append(h1).append(h2).append(h3).append(h4).append(h5).toString());
		System.out.println(
				new StringBuilder().append("//XXX ").append(m1).append(m2).append(m3).append(m4).append(m5).toString());
		System.out.println(
				new StringBuilder().append("//XXX ").append(l1).append(l2).append(l3).append(l4).append(l5).toString());

		Crash.log("ERROR HML River index invalid " + EvalData.hmlIndexRiver);
	}

	private static void flopTextureSCBP() {
		// Determine suit type
		int suitedType = 0;
		if (Board.boardF2) {
			suitedType = 8;
		}
		// Determine connectivity type
		int connectivityType = 0;
		if ((Board.board[0].value - Board.board[1].value == 1 || Board.board[1].value - Board.board[2].value == 1)
				|| (Board.board[0].value - Board.board[1].value == 2
						&& Board.board[1].value - Board.board[2].value == 2)) {
			connectivityType = 4;
		}
		// Determine pair type
		int pairType = 0;
		if (Board.boardPair) {
			pairType = 2;
		}
		// Determine big card type
		int bigCardType = 0;
		if (Board.board[0].value >= TEN && Board.board[1].value >= TEN) {
			bigCardType = 1;
		}

		// Calculate the index
		EvalData.SCBPIndexFlop = suitedType + connectivityType + pairType * bigCardType;
	}

	/*- ************************************************************************************************
	 * Type 1 flops have the characteristic that people either hit these flops hard, or not at all.
	 * Ed Miller
	 * On type 2 flops, there are lots of weak hits. When you c-bet and get called, there is not 
	 * an obvious answer to the question �what did they call me with?� 
	 * These boards are often going to elicit calls from gutshot straight draws, a weak pair 
	 * with backdoor draws, or combo draws with some other feature like a pair or backdoor draw. 
	 * J83 with a flush draw is a typical Type 2 flop.
	 * 
	 * Type 3 flops are highly coordinated, KQT with a suit or 987r. If someone hits these flops, 
	 * they tend to hit them very hard and legitimately will go to showdown with them. 
	 * These are flops that lead to boards that tend to be won by straights, flushes, 
	 * full houses and big two pair hands.
	***************************************************************************************************/
	private static void edMillerFlopType() {
		// Type1 people either hit these flops hard, or not at all.
		// K32 rainbow
		if (Board.boardF0 && EvalData.hmlIndexFlop == LLL || EvalData.hmlIndexFlop == HLL
				|| EvalData.hmlIndexFlop == HML) {
			EvalData.type = TYPE1;
			return;
		}
		// Type2 there are lots of weak hits.
		if (Board.boardGap2) {
			EvalData.type = TYPE2;
			return;
		}
		// highly coordinated, KQT with a suit or 987r. If someone hits these flops,
		// they tend to hit them very hard and legitimately will go to showdown with
		// them.
		if (EvalData.hmlIndexFlop == HHH || Board.boardSet || Board.boardPair || Board.boardF3) {
			EvalData.type = TYPE3;
			return;
		}
		if (!(Board.boardF3 || Board.boardPair || Board.boardSet || Board.boardF2)) {
			EvalData.type = TYPE3;
			return;
		}
		if (Board.boardTwoPair && (Board.boardPair && Board.boardGap1)) {
			EvalData.type = TYPE3;
			return;
		}
		EvalData.type = TYPE_NONE;
		Logger.log("ERROR ED MILLER FLOP TYPE invalid " + EvalData.type);
	}

	/*- *****************************************************************************
		 * This method will determine if a Flop is wet or dry or neutral.
		 * This is somewhat subjective as there is no precise definition.
		 * I have looked at other applications, like Huds and get consistent results. 
		 * Looking for something more definitive but this is good for now.
		 * boardArray and the HML value is used to determine wet or dry.
		 * There are 3 slots in boardArray, one for each condition.
		  ******************************************************************************/
	private static void wetDry() {
		if (Board.boardF3) {
			EvalData.wetDryArray[WET] = true;
			return;
		}
		if (Board.boardPair && EvalData.hmlIndexFlop != HHH) {
			EvalData.wetDryArray[DRY] = true;
			return;
		}
		if (EvalData.hmlIndexFlop == HHH) {
			if (Board.board[0].value == ACE || gap0Score >= 10 || gap1Score > 5 || Board.boardSet) {
				EvalData.wetDryArray[WET] = true;
			}
			return;
		}
		if (EvalData.hmlIndexFlop == HHM) {
			if (Board.board[0].value == ACE || gap0Score >= 6 || (gap1Score >= 6 && Board.board[1].value >= QUEEN)) {
				EvalData.wetDryArray[WET] = true;
			}
			return;
		}
		if (EvalData.hmlIndexFlop == HHL) {
			if ((Board.board[0].value == QUEEN || Board.gap0 == 1) || Board.gap1 >= 1 || gap1Score >= 5
					|| (Board.gap0 == 0 && Board.gap1 == 0)) {
				EvalData.wetDryArray[WET] = true;
			}
			return;
		}
		if (EvalData.hmlIndexFlop == HMM) {
			if (Board.board[0].value != ACE
					&& (Board.board[0].value == QUEEN || !Board.boardF0 || gap0Score >= 10 || Board.gap0 == 2
							|| (Board.board[0].value > TEN && Board.gap0 != 0) || gap1Score > 6 || Board.gap2 == 2)) {
				EvalData.wetDryArray[WET] = true;
			}
			return;
		}
		if (EvalData.hmlIndexFlop == HML) {
			if (Board.board[0].value == ACE && Board.gap0 == 1) {
				EvalData.wetDryArray[DRY] = true;
			} else {
				if (gap0Score >= 10 || gap1Score > 6) {
					EvalData.wetDryArray[WET] = true;
					return;
				}
				if (Board.board[0].value == QUEEN) {
					if (Board.board[1].value < NINE) {
						EvalData.wetDryArray[DRY] = true;
					}
					return;
				}
				if (Board.board[0].value == NINE) {
					if (Board.boardF0) {
						EvalData.wetDryArray[DRY] = true;
					}
					return;
				}
				if (Board.board[1].value < NINE && Board.boardF0) {
					EvalData.wetDryArray[DRY] = true;
				}
			}
			if (gap0Score >= 10 || gap1Score > 6) {
				EvalData.wetDryArray[WET] = true;
			} else {
				if (Board.board[0].value == QUEEN) {
					if (Board.board[1].value < NINE) {
						EvalData.wetDryArray[DRY] = true;
					}
					return;
				}
				if (Board.board[0].value == NINE) {
					if (Board.boardF0) {
						EvalData.wetDryArray[DRY] = true;
					}
					return;
				}
				if (Board.board[1].value < NINE && Board.boardF0) {
					EvalData.wetDryArray[DRY] = true;
				}
			}
			if (Board.board[0].value == QUEEN) {
				if (Board.board[1].value < NINE) {
					EvalData.wetDryArray[DRY] = true;
				}
			} else {
				if (Board.board[0].value == NINE) {
					if (Board.boardF0) {
						EvalData.wetDryArray[DRY] = true;
					}
					return;
				}
				if (Board.board[1].value < EIGHT && Board.boardF0) {
					EvalData.wetDryArray[DRY] = true;
				}
			}
			if (Board.board[0].value == NINE) {
				if (Board.boardF0) {
					EvalData.wetDryArray[DRY] = true;
				}
			} else if (Board.board[1].value < NINE && Board.boardF0) {
				EvalData.wetDryArray[DRY] = true;
			}
			return;
		}
		if (EvalData.hmlIndexFlop == HLL) {
			if (Board.board[0].value == ACE) {
				if (Board.gap0 >= 1) {
					return;
				}
				EvalData.wetDryArray[DRY] = true;
			} else {
				if (gap0Score >= 10) {
					EvalData.wetDryArray[WET] = true;
					return;
				}
				if (gap0Score < 7 || gap1Score < 6) {
					EvalData.wetDryArray[DRY] = true;
					return;
				}
				if ((Board.gap0 == 2 && !Board.boardAceHigh) || gap1Score > 5) {
					EvalData.wetDryArray[WET] = true;
					return;
				}
				if (Board.gap0 == 0 && Board.gap1 == 0) {
					EvalData.wetDryArray[DRY] = true;
					return;
				}
				if (Board.boardF0 && Board.gap0 < 2) {
					EvalData.wetDryArray[DRY] = true;
				}
			}
			if (gap0Score >= 10) {
				EvalData.wetDryArray[WET] = true;
			} else {
				if (gap0Score < 7 || gap1Score < 6) {
					EvalData.wetDryArray[DRY] = true;
					return;
				}
				if ((Board.gap0 == 2 && !Board.boardAceHigh) || gap1Score > 5) {
					EvalData.wetDryArray[WET] = true;
					return;
				}
				if (Board.gap0 == 0 && Board.gap1 == 0) {
					EvalData.wetDryArray[DRY] = true;
					return;
				}
				if (Board.boardF0 && Board.gap0 < 2) {
					EvalData.wetDryArray[DRY] = true;
				}
			}
			if (gap0Score < 7 || gap1Score < 6) {
				EvalData.wetDryArray[DRY] = true;
			} else {
				if ((Board.gap0 == 2 && !Board.boardAceHigh) || gap1Score > 5) {
					EvalData.wetDryArray[WET] = true;
					return;
				}
				if (Board.gap0 == 0 && Board.gap1 == 0) {
					EvalData.wetDryArray[DRY] = true;
					return;
				}
				if (Board.boardF0 && Board.gap0 < 2) {
					EvalData.wetDryArray[DRY] = true;
				}
			}
			if ((Board.gap0 == 2 && !Board.boardAceHigh) || gap1Score > 5) {
				EvalData.wetDryArray[WET] = true;
			} else {
				if (Board.gap0 == 0 && Board.gap1 == 0) {
					EvalData.wetDryArray[DRY] = true;
					return;
				}
				if (Board.boardF0 && Board.gap0 < 2) {
					EvalData.wetDryArray[DRY] = true;
				}
			}
			if ((Board.gap0 == 0) || (Board.boardF0 && Board.gap0 < 2)) {
				EvalData.wetDryArray[DRY] = true;
			}
			return;
		}
		if (EvalData.hmlIndexFlop == MMM) {
			if (gap0Score >= 10 || Board.gap0 == 2 || gap1Score > 5) {
				EvalData.wetDryArray[WET] = true;
			} else if (Board.boardF0) {
				EvalData.wetDryArray[DRY] = true;
			} else {
				EvalData.wetDryArray[NOT_WET_DRY] = true;
			}
		} else if (EvalData.hmlIndexFlop == MML) {
			if (gap0Score > 10) {
				EvalData.wetDryArray[WET] = true;
			} else {
				if (Board.gap0 == 1) {
					return;
				}
				if (Board.gap1 >= 1) {
					EvalData.wetDryArray[DRY] = true;
				}
			}
			if (Board.gap0 != 1 && Board.gap1 >= 1) {
				EvalData.wetDryArray[DRY] = true;
			}

		} else if (EvalData.hmlIndexFlop == MLL) {
			if (gap0Score >= 10) {
				EvalData.wetDryArray[WET] = true;
			} else {
				if (Board.gap0 == 2) {
					EvalData.wetDryArray[DRY] = true;
					return;
				}
				if (Board.gap0 == 1) {
					if (!Board.boardF0) {
						EvalData.wetDryArray[WET] = true;
					} else {
						EvalData.wetDryArray[DRY] = true;
					}
					return;
				}
				if (Board.gap1 == 1) {
					EvalData.wetDryArray[DRY] = true;
					return;
				}
				if (Board.gap1 == 2) {
					if (!Board.boardF0) {
						EvalData.wetDryArray[WET] = true;
						return;
					}
					EvalData.wetDryArray[DRY] = true;
				}
			}
			if (Board.gap0 == 2) {
				EvalData.wetDryArray[DRY] = true;
			} else {
				if (Board.gap0 == 1) {
					if (!Board.boardF0) {
						EvalData.wetDryArray[WET] = true;
					} else {
						EvalData.wetDryArray[DRY] = true;
					}
					return;
				}
				if (Board.gap1 == 1) {
					EvalData.wetDryArray[DRY] = true;
					return;
				}
				if (Board.gap1 == 2) {
					if (!Board.boardF0) {
						EvalData.wetDryArray[WET] = true;
						return;
					}
					EvalData.wetDryArray[DRY] = true;
				}
			}
			if (Board.gap0 == 1) {
				if (!Board.boardF0) {
					EvalData.wetDryArray[WET] = true;
				} else {
					EvalData.wetDryArray[DRY] = true;
				}
			} else {
				if (Board.gap1 == 1) {
					EvalData.wetDryArray[DRY] = true;
					return;
				}
				if (Board.gap1 == 2) {
					if (!Board.boardF0) {
						EvalData.wetDryArray[WET] = true;
						return;
					}
					EvalData.wetDryArray[DRY] = true;
				}
			}
			if (Board.gap1 == 1) {
				EvalData.wetDryArray[DRY] = true;
			} else {
				if (Board.gap1 == 2) {
					if (!Board.boardF0) {
						EvalData.wetDryArray[WET] = true;
						return;
					}
					EvalData.wetDryArray[DRY] = true;
				}
			}
			if (Board.gap1 != 2) {
				return;
			}
			if (!Board.boardF0) {
				EvalData.wetDryArray[WET] = true;
				return;
			}
		}
		if (EvalData.hmlIndexFlop != LLL) {
			EvalData.wetDryArray[DRY] = true;
			return;
		}
		// Logger.log("ERROR Wet Dry unable to classify" + EvalData.hmlIndexFlop);
		EvalData.wetDryArray[NOT_WET_DRY] = true;
	}

	/*- *****************************************************************************
	 * This method will classify a flop.
	 * Sure, to make this Java method, let's consider that each card has two properties:
	 *  a value and a suit. Let's represent values as integers from 2 (for 2) to 14 
	 * (for Ace), and suits as integers from 1 to 4.
	 * 
	 * We can have 9 combinations in total, given three types of suit distributions
	 * (Monotone, Two-tone, and Rainbow) and three types of connectivity 
	 * (Connected, Semi-connected, and Unconnected). 
	 * These combinations can be represented by integers from 1 to 9.
	 * This will return a unique integer from 1 to 9 for each possible combination. 
	 * The logic for the computation is that we subtract 1 from the suitType and 
	 * multiply by 3 (since we have three connectivity types), and then we add the 
	 * connectivityType. This ensures a unique value for each combination.
	 * 
	 *******************************************************************************/
	private static void flopTextureSuitednessConnectedness() {
		if (Board.board[0].suit == Board.board[1].suit && Board.board[1].suit == Board.board[2].suit) {
		} else if (Board.board[0].suit == Board.board[1].suit || Board.board[1].suit == Board.board[2].suit
				|| Board.board[0].suit == Board.board[2].suit) {
		} else {
		}

		if (Board.board[1].value - Board.board[0].value == 1 && Board.board[2].value - Board.board[1].value == 1) {
		} else if (Board.board[1].value - Board.board[0].value == 1
				|| Board.board[2].value - Board.board[1].value == 1) {
		} else {
		}

		// Return the combined characterization as an integer
		// EvalData.suitednessConnectednessIndex = (suitType - 1) * 3 +
		// connectivityType;
	}

	/*- *****************************************************************************
	 * This method will classify a flop.
	 * 
	Another way to characterize the flop in Texas Hold'em is by assessing its Pair Distribution 
	and Broadway Potential. This can be valuable in predicting possible hand strengths
	and formulating a strategy.
	
	Pair Distribution: Identify if there are any pairs on the flop, and if so, what they are.
	
	Unpaired Flop: This is when all three cards on the flop have different values (e.g., 5-7-Queen).
	
	Paired Flop: This is when two out of the three cards on the flop have the same value (e.g., 6-6-King).
	
	Trips Flop: This is when all three cards on the flop have the same value (e.g., 8-8-8).
	
	Broadway Potential: Broadway is a term for the highest possible straight, which is a 10-J-Q-K-A straight. 
	The flop could have various levels of potential for a Broadway straight.
	
	High Potential: This is when two or more cards from the flop are part of the Broadway sequence (10, J, Q, K, A).
	
	Medium Potential: This is when only one card from the flop is part of the Broadway sequence.
	
	Low Potential: This is when none of the cards from the flop are part of the Broadway sequence.
	*******************************************************************************/
	private static void flopTexturePairBroadway() {
		// Determine pair distribution type
		final int pairType;
		if (Board.board[0].value == Board.board[1].value && Board.board[1].value == Board.board[2].value) {
			pairType = 1; // Trips
		} else if (Board.board[0].value == Board.board[1].value || Board.board[1].value == Board.board[2].value
				|| Board.board[0].value == Board.board[2].value) {
			pairType = 2; // Pair
		} else {
			pairType = 3; // Unpaired
		}

		// Determine Broadway potential type
		final int broadwayPotentialType;
		int broadwayCount = 0;
		for (int i = 0; i < 3; i++) {
			if (Board.board[i].value >= TEN && Board.board[i].value <= ACE) { // 10, J, Q, K, A
				broadwayCount++;
			}
		}
		if (broadwayCount >= 2) {
			broadwayPotentialType = 1; // High
		} else if (broadwayCount == 1) {
			broadwayPotentialType = 2; // Medium
		} else {
			broadwayPotentialType = 3; // Low
		}

		// Return the combined characterization as an integer
		EvalData.pairBroadwayIndex = (pairType - 1) * 3 + broadwayPotentialType;
	}

	/*- *****************************************************************************
	 * This method will classify a flop.
	 * A useful way to characterize the flop could be by assessing its High Card Strength 
	 * and Suitedness. Both these factors can affect the potential hand strengths of 
	 * you and your opponents.
	
	High Card Strength: This categorizes the flop based on the highest card present.
	
	High: The highest card is an Ace, King, or Queen.
	Medium: The highest card is a Jack, 10, or 9.
	Low: The highest card is 8 or lower.
	Suitedness: This evaluates the potential for flushes based on the suits of the flop cards.
	
	Monotone: All three cards are of the same suit.
	Two-tone: Two cards share a suit, but the third is different.
	Rainbow: All three cards are of different suits.
	 ***************************************************************************** */
	private static void flopTextureStrengthSuitedness() {

		// Determine high card strength type
		final int highCardStrengthType;
		final int maxRank = Math.max(Board.board[0].value, Math.max(Board.board[1].value, Board.board[2].value));
		if (maxRank >= 13) { // Ace, King, Queen
			highCardStrengthType = 1; // High
		} else if (maxRank >= 9) { // Jack, 10, 9
			highCardStrengthType = 2; // Medium
		} else {
			highCardStrengthType = 3; // Low
		}

		// Determine suitedness type
		final int suitednessType;
		if (Board.board[0].suit == Board.board[1].suit && Board.board[1].suit == Board.board[2].suit) {
			suitednessType = 1; // Monotone
		} else if (Board.board[0].suit == Board.board[1].suit || Board.board[1].suit == Board.board[2].suit
				|| Board.board[0].suit == Board.board[2].suit) {
			suitednessType = 2; // Two-tone
		} else {
			suitednessType = 3; // Rainbow
		}

		// Return the combined characterization as an integer
		EvalData.strengthSuitednessIndex = (highCardStrengthType - 1) * 3 + suitednessType;
	}

	/*-*****************************************************************************
	* This method will classify a Flop.
	* Another approach to characterizing the flop can be by looking at the Number 
	* Distribution and Flush Potential.
	
	Number Distribution: This categorizes the flop based on how many of each value are present.
	
	Trips: All three cards have the same value.
	One Pair: Two cards have the same value, and the third card has a different value.
	No Pair: All three cards have different values.
	Flush Potential: This categorizes the flop based on the suits of the cards, 
	similar to Suitedness.
	
	High: All three cards have the same suit, implying high potential for a flush.
	Medium: Two cards have the same suit, but the third is of a different suit, 
	indicating medium potential for a flush.
	Low: All three cards have different suits, indicating low potential for a flush.
	 ******************************************************************************/
	private static void flopTextureDistributionFlushPotential() {
		// Determine number distribution type
		final int numberDistributionType;
		if (Board.board[0].value == Board.board[1].value && Board.board[1].value == Board.board[2].value) {
			numberDistributionType = 1; // Trips
		} else if (Board.board[0].value == Board.board[1].value || Board.board[1].value == Board.board[2].value
				|| Board.board[0].value == Board.board[2].value) {
			numberDistributionType = 2; // One Pair
		} else {
			numberDistributionType = 3; // No Pair
		}

		// Determine flush potential type
		final int flushPotentialType;
		if (Board.board[0].suit == Board.board[1].suit && Board.board[1].suit == Board.board[2].suit) {
			flushPotentialType = 1; // High
		} else if (Board.board[0].suit == Board.board[1].suit || Board.board[1].suit == Board.board[2].suit
				|| Board.board[0].suit == Board.board[2].suit) {
			flushPotentialType = 2; // Medium
		} else {
			flushPotentialType = 3; // Low
		}

		// Return the combined characterization as an integer
		EvalData.distributionFlushPotentialIndex = (numberDistributionType - 1) * 3 + flushPotentialType;
	}

	/*-*****************************************************************************
	* This method will classify a Flop.
	* Another useful way to characterize the flop could be by evaluating its Connectedness
	and High-Low Spread.
	
	Connectedness: This categorizes the flop based on how sequential the values of the cards are.
	
	High: All three cards are sequential (e.g., 5-6-7).
	Medium: Two cards are sequential, but the third is not (e.g., 5-6-9).
	Low: None of the cards are sequential (e.g., 3-6-9).
	High-Low Spread: This characterizes the flop based on the difference between the 
	highest and lowest cards.
	
	High: The highest card and the lowest card have a difference of 5 or more (e.g., 2 and 8).
	Medium: The difference is between 3 and 4 (e.g., 5 and 9).
	Low: The difference is 2 or less (e.g., 7 and 8).
	
	******************************************************************************/
	private static void flopTextureValueSpread() {
		// Determine connectedness type
		final int connectednessType;
		if (Board.board[1].value - Board.board[0].value == 1 && Board.board[2].value - Board.board[1].value == 1) {
			connectednessType = 1; // High
		} else if (Board.board[1].value - Board.board[0].value == 1
				|| Board.board[2].value - Board.board[1].value == 1) {
			connectednessType = 2; // Medium
		} else {
			connectednessType = 3; // Low
		}

		// Determine high-low spread type
		final int highLowSpreadType;
		final int spread = Board.board[2].value - Board.board[0].value;
		if (spread >= 5) {
			highLowSpreadType = 1; // High
		} else if (spread >= 3) {
			highLowSpreadType = 2; // Medium
		} else {
			highLowSpreadType = 3; // Low
		}

		// Return the combined characterization as an integer
		EvalData.connectednessValueIndex = (connectednessType - 1) * 3 + highLowSpreadType;
	}

	/*-***************************************************************************
	* This method will classify a Flop.
	*****************************************************************************/
	private static void flopTextureValueSpreadX() {
		// Determine strength level type
		final int strengthLevelType;
		// Perform your own analysis or use the simulation results to determine the
		// strength level of your hand.
		// This could be based on the likelihood of your hand winning against potential
		// opponent hands.
		// For example, if your simulation suggests a high probability of winning,
		// consider it a strong hand.
		// Modify the logic below to suit your specific evaluation criteria.
		final double winProbability = 0.7; // Example win probability
		if (winProbability >= 0.7) {
			strengthLevelType = 1; // Strong
		} else if (winProbability >= 0.4) {
			strengthLevelType = 2; // Moderate
		} else {
			strengthLevelType = 3; // Weak
		}

		// Determine potential hand range type
		final int potentialHandRangeType;
		// Use your understanding of the game and the simulation results to estimate the
		// potential hand range.
		// If the simulation indicates that the flop significantly limits or expands the
		// possible opponent hands,
		// you can assign it to the respective category.
		// Modify the logic below based on your analysis.
		final boolean narrowsHandRange = true; // Example narrow hand range indicator
		if (narrowsHandRange) {
			potentialHandRangeType = 1; // Narrow
		} else if (narrowsHandRange) {
			potentialHandRangeType = 2; // Moderate
		} else {
			potentialHandRangeType = 3; // Wide
		}

		// Return the combined characterization as an integer
		final int x = (strengthLevelType - 1) * 3 + potentialHandRangeType; // TODO
	}

	/*-***************************************************************************
	* This method will classify a Flop.
	*  You're looking for combinations where only the gaps matter, not the actual 
	values or suits. In this case, there are a few possibilities for the patterns of gaps:
	
	Group 0: All cards of the same value (like 2-2-2). The gap is 0 between any two cards. 
	There is only 1 combination here (0-0).
	
	Group 1: Two cards are of the same value and the third card is of a different value (like 2-2-3).
	There are 2 combinations here (0-1, 1-0).
	
	Group 2: All cards are of different values and consecutive (like 2-3-4). 
	There is only 1 combination here (1-1).
	
	Group 3: Two cards are of the same value and the third card is two values apart (like 2-2-4). 
	There are 2 combinations here (0-2, 2-0).
	
	Group 4: Two cards are of different values, one gap of 1 and the other of 2 (like 2-3-5). 
	There are 2 combinations here (1-2, 2-1).
	
	Considering only gaps, these are all the possible combinations for a three-card flop.
	So in total, we have 1 + 2 + 1 + 2 + 2 = 8 combinations.
	
	
	
	Here is a Java method that would take in an array of three integers 
	(representing the sorted values of the three flop cards) and return a string identifying 
	which category of flop it is:
	
	java 
	This method assumes that the values of the cards are represented as integers from 2 to 14,
	where 2-10 correspond to the values 2-10, and 11, 12, 13, 14 correspond to Jack, 
	Queen, King, and Ace, respectively. Also, the values are sorted in ascending order 
	before being passed into the method.
	
	The method first checks if the input array length is 3 (representing the three flop cards).
	If not, it throws an IllegalArgumentException.
	
	It then calculates the gaps between the sorted values of the cards, and uses these gaps
	to determine which category the flop falls into. The category is returned as a string 
	"Group 0" through "Group 4", corresponding to the five categories of flops identified earlier.
	If the flop doesn't fit into any of these categories (which shouldn't happen 
	if the input is valid), it defaults to "Group 4".
	
	*****************************************************************************/
	private static String flopTextureA() {
		// Calculate the gaps
		final int gap1 = Board.board[1].value - Board.board[0].value;
		final int gap2 = Board.board[2].value - Board.board[1].value;

		// Compare the gaps to determine the category
		if (gap1 == 0 && gap2 == 0) {
			return "Group 0";
		} else if (gap1 == 0 || gap2 == 0) {
			return "Group 1";
		} else if (gap1 == 1 && gap2 == 1) {
			return "Group 2";
		} else if (gap1 == 0 || gap2 == 0) {
			return "Group 3";
		} else {
			return "Group 4";
		}
	}

	private static String flopTexturB() {
		// Calculate the gaps
		final int gap1 = Board.board[1].value - Board.board[0].value;
		final int gap2 = Board.board[2].value - Board.board[1].value;

		// Compare the gaps to determine the category
		if (gap1 == 0 && gap2 == 0) {
			return "Group 0";
		} else if ((gap1 == 0 && gap2 == 0)) {
			return "Group 1";
		} else if ((gap1 == 1 && gap2 == 1)) {
			return "Group 2";
		} else if ((gap1 == 0 && gap2 == 2)) {
			return "Group 3";
		} else if ((gap1 == 1 && gap2 == 2)) {
			return "Group 4";
		} else {
			return "Group 5";
		}
	}

	/*-  **************************************************************************
	* There are only 3 public methods, doFlop(), doTurn(), and doRiver()
	*
	* This class contains methods that characterize the boards for Flop, Turn, River
	* and hands ( board + hole Cards ) with made hands and draws analyzed.
	* In general, a single integer value is used to identify the type of the board 
	* or hand.   For example:
	* The Flop is evaluated as a combination of High, Middle, and Low EvalData.board.
	* So a flop with A 7 5 is HML ( High Medium Low ). 
	* There are exactly 10 combinations so a value of 0 to 9 is assigned. 
	* At the same time the hand ( hole cards + Flop cards ) is evaluated for
	* draws and made hands. Then at showdown, the hand is evaluated for best hand
	* or not. After many thousands of games simulated the percentage of the time that
	* one of the 10 HML combinations combined with draws, made hands, and winning hands
	* is calculated as a percentage. That percentage is a clue as how to play this
	* specific Flop, hand, or draw. If you don not have a made hand, or good draw,
	* then it tells you what to expect from your opponent.
	* 
	* There are many different ways to convert a Flop into a single integer such as
	* simply adding the values of the 13 cards ( 0 to 12 ) and array at a number 
	* between 0 and 36. So, same analysis as the exampled above.
	*
	* We will be experimenting with many different ways to characterize a Flop, Turn,
	* or River board and with hands. Where exactly we will end up is unknown but  
	* after all this is an experiment.
	*
	* There is a parallel project, Hold'emHandHistory that evaluates millions of 
	* real Hand History files from PokerStars and is a similar experimenting but with real 
	* data.
	*
	*
	*
	* This class is used to analyze thousands or millions of simulated hands.
	* Using a monte-carlo methodology determine characteristics of a Hold'em 
	* 6-max no limit game. 
	* Specifically to find ways to characterize a Flop, such as HML ( High Medium Low )
	* which results in only 10 Flop types. A million or more hands are run, and for 
	* each hand run the draws and made hands and other data is analyzed and collected
	* in arrays which will be analyzed after a run is completed. 
	* We analyze each street and Showdown.
	* 
	* This is not a final version. May never be. We will be experimenting constantly 
	* trying to find ways to characterize a Flop that will result in  information on
	* how best to play a hand. 
	* For example for a Flop type:
	*   It may result in a high percentage of made hands and we do not have a made hand.
	*   It may result in a low percentage of made hands and we hve have a made hand.
	*   It may result in a high percentage of strong draws.
	*   It may result in a low percentage of strong draws.
	*   It may result in a a high percentage of times that the made hand is the winning
	*   hand at Showdown.
	*   And many more.
	
	
	The results of your Monte Carlo simulation provide important probabilities that can directly 
	inform your poker strategy. However, to convert these numerical results into a concrete 
	strategy requires understanding the context of the game (your opponents, your position, 
	he stage of the tournament, etc.). Here are several ways to relate simulation results to strategy:
	
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
	Conversely, if your simulation shows a low probability of winning, you may choose to bet 
	conservatively or fold.
	
	Opponent Modeling: By combining the results of your simulations with observations 
	about your opponents' behavior, you can model how they play and make decisions that 
	ake advantage of their weaknesses. If an opponent frequently folds in response to 
	aggressive betting, for example, you might choose to bet aggressively when your 
	simulation shows that you have a moderately strong hand.
	
	Adaptation: Your simulation results can help you adapt your strategy as the game progresses.
	If your simulations suggest that certain hand combinations are particularly advantageous 
	or disadvantageous in the current game context, you can adapt your strategy to prioritize 
	or avoid these combinations.
	
	Remember, the key to a successful poker strategy is flexibility. Use the results of your 
	simulations as a guide, but always be ready to adap
	
	
		Flop Classification thoughts
		 Characterizing a flop can be done in many ways depending on what is important to the player.
		Here are a few easy ways to classify a flop that are simple enough to do quickly in your head:
		
		Suit distribution: The three cards on the flop could all be the same suit (3 suited),
		two could share a suit while the third is a different suit (2 suited), or all three could be
		different suits (rainbow). This is easy to count quickly and gives a lot of information 
		about the possibility of flush draws.
		
		Paired or not: This is also very straightforward: either the flop contains a pair of cards (paired) 
		or it does not (unpaired). This is very important to consider for the potential of a full house or four of a kind.
		
		Connectedness: You could classify the flop as either connected (three consecutive cards),
		semi-connected (two consecutive cards and one card of a different value), or unconnected 
		(all three cards of different values). This tells you about the likelihood of straights.
		
		High, Medium, or Low: You can also classify the flop as high (one or more face cards), 
		medium (no face cards, but one or more cards 8 through 10), or low (all cards 7 or lower). 
		This could be important if you have hole cards that match these categories.
		
		Rank distribution: You can categorize the flop based on the number of different values present, 
		which could be three (e.g., K-8-3), two (e.g., J-J-4), or one (e.g., 5-5-5).
		
		These are just some possibilities. The key is to pick a method of characterization that 
		provides useful information for the particular style of play or strategy that you are using.
		
		Rank distribution is a way to categorize the flop in Texas Hold'em poker based on the 
		number of distinct values that appear among the three community cards. Here's how you 
		could classify the flop:
		
		Three values: This means that all three cards on the flop have different values. 
		For example, a flop of 7-9-Jack would be classified as "three values". 
		This type of flop doesn't complete any sets but may create possibilities for 
		straights and flushes.
		
		Two values: This means that two of the three cards on the flop have the same value, 
		and the third card has a different value. For example, a flop of 4-4-8 would be 
		classified as "two values". This type of flop is said to be paired, which increases 
		the likelihood of hands like three-of-a-kind or full houses.
		
		One value: This means that all three cards on the flop have the same value. 
		For example, a flop of 2-2-2 would be classified as "one value". 
		This is a highly unusual flop and immediately completes four-of-a-kind for anyone 
		holding a card of the same value. It also makes full houses possible for all players still in the hand.
		
		Rank distribution provides insight into the likelihood of certain hands and can 
		inform a player's decision-making about whether to bet, check, or fold.
		
		
		One way to characterize a flop could be by assessing its Texture: 
		The texture of a flop can help in predicting the possible hands that an opponent 
		might have, and hence can assist in formulating a strategy. Here's a way to do that:
		
		Monotone: All three flop cards are of the same suit. This increases the chances
		of flushes. If you have a suited hand, this is good for you; otherwise, 
		it can be risky if an opponent is likely to have a flush.
		
		Two-tone: Two cards on the flop are of the same suit. This creates the 
		possibility of flush draws.
		
		Rainbow: Each card is a different suit. This reduces the likelihood of flushes significantly.
		
		And you can combine this with:
		
		Connected: All three cards are numerically sequential, such as 4-5-6. 
		This increases the likelihood of straights.
		
		Semi-connected: Two of the three cards are numerically sequential, such as 
		4-5-7. This leaves some potential for straights.
		
		Unconnected: The cards do not have any numeric connection, like 3-7-Jack.
		
		So, you can classify a flop as being "Monotone Connected" or "Two-tone Semi-connected" 
		or "Rainbow Unconnected" and so on.
		
		Knowing this, you can assess whether the flop is more likely to have helped your 
		opponents based on their pre-flop actions. For instance, players who raise pre-flop
		are more likely to have high cards or suited cards, and thus may be more likely to 
		benefit from a high, monotone, or connected flop.
		
		Please note that this way of categorizing flop texture simplifies the complexities
		of the game. You'd need to combine this with other reads and strategies to improve 
		your overall gameplay.
		
		In this method, we sort the three cards by rank. We then determine the suit type by checking
		if all suits are the same (Monotone), two are the same (Two-tone), or all are different (Rainbow).
		 We then determine the connectivity type by checking if all ranks are sequential 
		 (Connected), two are sequential (Semi-connected), or none are sequential (Unconnected). 
		 Finally, we return the combined characterization of the flop.
	
	* @author PEAK_
	***************************************************************************** */

}
