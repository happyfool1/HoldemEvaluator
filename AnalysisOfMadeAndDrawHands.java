package holdemevaluator;

public class AnalysisOfMadeAndDrawHands implements Constants {
	/*- ******************************************************************************************
	* This Class will analyze a hand for each player on each street.
	* It checks for made hands and draws.
	* 
	* There will be one instance of this Class for each of the 6 players.
	* There are only 2 public methods in this Class.
	* analyze 		- Analyzes one specific street or analyzes all streets in sequence.
	* initializeForNewGame	- Initializes variables to default values.
	* 
	* If one specific street, such as TURN, then the Flop and River are not analyzed.
	* Only the Turn.
	* if  ALL_STREETS  then the Flop, Turn, and River are analyzed in sequence.
	* 
	 * @author PEAK_ 
	 *********************************************************************************************/

	/*-  **************************************************************************************
	 *   Variables set by constructor
	  ************************************************************************************** */
	private int seat = -1;
	int selected = ALL_STREETS;

	/*-  ************************************************************************************* 
	 For the hand currently being evaluated, indexed by player number, 
	* A single int value is saved for each player not folded for each street.
	* The type is the larger if there is more than one.
	* See Constants MADE_ and DRAW_ for details.
	*************************************************************************************** */
	// Hole cards plus board cards sorted
	Card[] both = new Card[7];
	// Sorted by card ( 0 - 51 ) for Flush and Flush draws
	Card[] bothCards = new Card[7];
	Card[] pairedCards = new Card[5];
	Card[] suitedCards = new Card[5];
	Card[] connectedCards = new Card[5];
	int bothCount = -1;
	int cardCount = -1;

	Card holeCard1 = null;
	Card holeCard2 = null;
	int handIndex = -1;
	boolean holeCardsSuited = false;

	// Hole card paired with board card(s)
	private boolean holeCard1PairedWithBoard = false;
	private boolean holeCard2PairedWithBoard = false;

	private boolean[] takenP = { false, false, false, false, false, false, false };

	private boolean[] takenValueC = { false, false, false, false, false, false, false, false, false, false, false,
			false, false };

	private int gap0Count = 0;
	private int gap1Count = 0;
	private int gap2Count = 0;

	boolean flush2 = false;
	boolean flush3 = false;
	boolean flush4 = false;
	boolean flush5 = false;

	/*-**********************************************************************************************
	* Hands with pairs
	***********************************************************************************************/
	boolean onePair = false;
	boolean pocketPair = false;
	boolean twoPair = false;
	boolean pairPlusSmallerBoardPair = false;
	boolean pairPlusBiggerBoardPair = false;
	boolean threeOfKind = false;
	boolean fourOfKind = false;
	private boolean fullHouse = false;
	private boolean anyPaired = false;
	private boolean straight = false;
	private boolean flush = false;
	int pair1Value = -1; // Pair, set, first pair of two pair, four of, Full House
	int pair2Value = -1; // Second pair of 2 pair, second part of Full House,
	private int setValue = -1;
	private boolean pair1NoHole = false;
	private boolean pair2NoHole = false;
	private boolean pair3NoHole = false;
	private boolean set1NoHole = false;
	private boolean fourOfKindNoHole = false;
	private int pairedCount = -1;
	private int suitedCount = -1;
	private int connectedCount = -1;

	/*- ******************************************************************************************
	 * Data related to Draw and Made hands
	 *********************************************************************************************/
	int type = MADE_NONE;
	boolean drawWithFlush = false;

	int typeOfDrawFlop = DRAW_NONE;
	int typeOfDrawTurn = DRAW_NONE;
	int typeOfMadeFlop = MADE_NONE;
	int typeOfMadeTurn = MADE_NONE;
	int typeOfMadeRiver = MADE_NONE;
	Card[] drawCardsFlop = new Card[5];
	Card[] drawCardsTurn = new Card[5];
	Card[] madeCardsFlop = new Card[5];
	Card[] madeCardsTurn = new Card[5];
	Card[] madeCardsRiver = new Card[5];
	int street;
	int position;
	int rp;

	/*- ******************************************************************************************
	 * Outs and Odds
	 *********************************************************************************************/
	int outsFlop = 0;
	int outsTurn = 0;
	int outsMadeFlop = 0;
	int outsMadeTurn = 0;
	int outsDrawFlop = 0;
	int outsDrawTurn = 0;
	int outsWeakFlop = 0;
	int outsWeakTurn = 0;
	int outsBackdoorFlop = 0;
	int outsBackdoorTurn = 0;
	double percentageFTT = 0.;
	double percentageFTR = 0.;
	double percentageTTR = 0.;

	// Reporting data
	StringBuilder handStatusSB = new StringBuilder();

	/*-**********************************************************************************************
	* Constructor
	*************************************************************************************************/
	AnalysisOfMadeAndDrawHands(int seatNum) {
		this.seat = seatNum;
	}

	/*-**********************************************************************************************
	* Called prior to starting a new game. 
	* Must be called for every instance.
	* This method will copy data from EvalData into local variables in this class.
	*************************************************************************************************/
	void initializeForNewGame() {
		position = GameControl.seatPos[seat];
		rp = GameControl.relativePosition[seat];
	}

	/*-**********************************************************************************************
	* This method will:
	*  Set a lot of variables to their default state.
	*************************************************************************************************/
	private void initializeFlop() {
		if (EvalData.preflopSimulation) {
			++DrawMadeWonPercentages.typeCountSimMadeFlop;
			++DrawMadeWonPercentages.typeCountSimDrawFlop;
		} else {
			++DrawMadeWonPercentages.typeCountMadeFlop;
			++DrawMadeWonPercentages.typeCountDrawFlop;
		}
		type = MADE_NONE;
		typeOfMadeFlop = MADE_NONE;
		typeOfDrawFlop = DRAW_NONE;
		pocketPair = false;
		anyPaired = false;
		onePair = false;
		twoPair = false;
		pairPlusSmallerBoardPair = false;
		pairPlusBiggerBoardPair = false;
		threeOfKind = false;
		fourOfKind = false;
		fullHouse = false;
		pair1NoHole = false;
		pair2NoHole = false;
		pair3NoHole = false;
		set1NoHole = false;
		fourOfKindNoHole = false;
		holeCard1PairedWithBoard = false;
		holeCard2PairedWithBoard = false;
		flush2 = false;
		flush3 = false;
		flush4 = false;
		flush5 = false;
		drawWithFlush = false;
		outsFlop = 0;
		outsWeakFlop = 0;
		outsMadeFlop = 0;
		outsDrawFlop = 0;
		outsBackdoorFlop = 0;
		outsTurn = 0;
		outsWeakTurn = 0;
		outsMadeTurn = 0;
		outsDrawTurn = 0;
		percentageFTT = 0.;
		percentageFTR = 0.;
	}

	/*-**********************************************************************************************
	* This method will:
	*  Set a lot of variables to their default state.
	*************************************************************************************************/
	private void initializeTurn() {
		if (EvalData.preflopSimulation) {
			++DrawMadeWonPercentages.typeCountSimMadeTurn;
			++DrawMadeWonPercentages.typeCountSimDrawTurn;
		} else {
			++DrawMadeWonPercentages.typeCountMadeTurn;
			++DrawMadeWonPercentages.typeCountDrawTurn;
		}
		type = MADE_NONE;
		typeOfMadeTurn = MADE_NONE;
		typeOfDrawTurn = DRAW_NONE;
		pocketPair = false;
		anyPaired = false;
		onePair = false;
		twoPair = false;
		pairPlusSmallerBoardPair = false;
		pairPlusBiggerBoardPair = false;
		threeOfKind = false;
		fourOfKind = false;
		fullHouse = false;
		pair1NoHole = false;
		pair2NoHole = false;
		pair3NoHole = false;
		set1NoHole = false;
		fourOfKindNoHole = false;
		holeCard1PairedWithBoard = false;
		holeCard2PairedWithBoard = false;
		flush2 = false;
		flush3 = false;
		flush4 = false;
		flush5 = false;
		drawWithFlush = false;
		outsTurn = 0;
		outsWeakTurn = 0;
		outsMadeTurn = 0;
		outsDrawTurn = 0;
		outsFlop = 0;
		outsWeakFlop = 0;
		outsMadeFlop = 0;
		outsDrawFlop = 0;
		outsBackdoorFlop = 0;
		outsBackdoorTurn = 0;
		percentageTTR = 0.;
	}

	/*-**********************************************************************************************
	* This method will:
	*  Set a lot of variables to their default state.
	*************************************************************************************************/
	private void initializeRiver() {
		if (EvalData.preflopSimulation) {
			++DrawMadeWonPercentages.typeCountSimMadeRiver;
		} else {
			++DrawMadeWonPercentages.typeCountMadeRiver;
		}
		type = MADE_NONE;
		typeOfMadeRiver = MADE_NONE;
		pocketPair = false;
		anyPaired = false;
		onePair = false;
		twoPair = false;
		pairPlusSmallerBoardPair = false;
		pairPlusBiggerBoardPair = false;
		threeOfKind = false;
		fourOfKind = false;
		fullHouse = false;
		pair1NoHole = false;
		pair2NoHole = false;
		pair3NoHole = false;
		set1NoHole = false;
		fourOfKindNoHole = false;
		holeCard1PairedWithBoard = false;
		holeCard2PairedWithBoard = false;
		flush2 = false;
		flush3 = false;
		flush4 = false;
		flush5 = false;
	}

	/*-**********************************************************************************************
	* This method will call all of the other methods to analyze one seat.
	* If one specific street, such as TURN, then the Flop and River are not analyzed.
	* If  ALL_STREETS  then the Flop, Turn, and River are analyzed in sequence.
	* The calling program is responsible for dealing the hole cards and the board cards.
	* One instance of this class exist for each of the 6 players and the board is common to all,
	*************************************************************************************************/
	void analyzeFlop() {
		++AAA;
		EvalData.street = FLOP;
		EvalData.lastStreet = street;
		EvalData.seat = seat;
		EvalData.lastSeat = seat;
		initializeFlop();
		cardsAndBoardFlop();
		if (!analyzeStraightAndFlush()) {
			analyzePaired();
		}
		analyzeDraw();
		if (EvalData.manyHands) {
			IndexArrayUpdate.updateFlop();
		}
		if (EvalData.handInfo) {
			updateHandStatus();
		}
		outsAndOddsFlop();
	}

	/*-**********************************************************************************************
	* This method will call all of the other methods to analyze one seat.
	* If one specific street, such as TURN, then the Flop and River are not analyzed.
	* If  ALL_STREETS  then the Flop, Turn, and River are analyzed in sequence.
	* The calling program is responsible for dealing the hole cards and the board cards.
	* One instance of this class exist for each of the 6 players and the board is common to all,
	*************************************************************************************************/
	void analyzeTurn() {
		EvalData.street = TURN;
		EvalData.lastStreet = street;
		EvalData.seat = seat;
		EvalData.lastSeat = seat;
		initializeTurn();
		cardsAndBoardTurn();
		if (!analyzeStraightAndFlush()) {
			analyzePaired();
		}
		analyzeDraw();
		if (EvalData.manyHands) {
			IndexArrayUpdate.updateTurn();
		}
		if (EvalData.handInfo) {
			updateHandStatus();
		}
		outsAndOddsTurn();
	}

	/*-**********************************************************************************************
	* This method will call all of the other methods to analyze one seat.
	* If one specific street, such as TURN, then the Flop and River are not analyzed.
	* If  ALL_STREETS  then the Flop, Turn, and River are analyzed in sequence.
	* The calling program is responsible for dealing the hole cards and the board cards.
	* One instance of this class exist for each of the 6 players and the board is common to all,
	*************************************************************************************************/
	void analyzeRiver() {
		EvalData.street = RIVER;
		EvalData.lastStreet = street;
		EvalData.seat = seat;
		EvalData.lastSeat = seat;
		initializeRiver();
		cardsAndBoardRiver();
		if (!analyzeStraightAndFlush()) {
			analyzePaired();
		}
		if (EvalData.manyHands) {
			IndexArrayUpdate.updateRiver();
		}
		if (EvalData.handInfo) {
			updateHandStatus();
		}
	}

	/*- *****************************************************************************
	 * This method initializes arrays of cards for the Flop.
	 *
	 * The cards from the Hands class are first copied into arrays in the EvalData class.
	 * There are performance advantages to having the arrays in EvalData because in
	 * hands a couple of levels of pointers are used.
	 *
	 * The cards in hand.board are already sorted, done by Hands and Board and HoleCards.
	 * The hole cards and board are combined into an array and that array is sorted.
	 *
	 * They are then used to create two more arrays sorted by card value ( 0 - 51 ) which places
	 * them in both value order and suit order. Ac7cQh5h8s
	 * Sorting the arrays has several advantages:
	 * 		It greatly simplifies checking the board ot hole cards plus board for
	 * 		information such as straight draws, flush draws, made hands, etc.
	 * 		It is easier for a human to look at and understand.
	 * 		It is actually faster. While sorting takes time it eliminates additional
	 * 		programming in the methods that do evaluation.
	 * 		The sort method is very fast, uses the quick sort algorithm and is specific
	 * 		to the Card class.
	  ******************************************************************************/
	void cardsAndBoardFlop() {
		cardCount = 5;
		both[0] = holeCard1;
		bothCards[0] = holeCard1;
		both[1] = holeCard2;
		bothCards[1] = holeCard2;
		if (holeCard1.suit == holeCard2.suit) {
			holeCardsSuited = true;
		}
		both[2] = Board.boardUnsorted[0];
		bothCards[2] = both[2];
		both[3] = Board.boardUnsorted[1];
		bothCards[3] = both[3];
		both[4] = Board.boardUnsorted[2];
		bothCards[4] = both[4];
		bothCount = 5;
		both[5] = null;
		both[6] = null;
		bothCards[5] = null;
		bothCards[6] = null;
		SortCards.sortCardsValue(both, 5);
		SortCards.sortCardsCard(bothCards, 5);
		gap0Count = 0;
		for (int i = 0; i < bothCount - 1; i++) {
			if (both[i].value == both[i + 1].value) {
				++gap0Count;
			}
		}
		copyAll();
		suits();
		pairedAndValues();
	}

	/*-**********************************************************************************************
	* This method will all cards to:  
	* pairedCards
	* suitedCards
	* connectedCards
	* Flop only, 5 cards so nothing to select
	*************************************************************************************************/
	private void copyAll() {
		for (int i = 0; i < 5; i++) {
			connectedCards[i] = both[i];
			pairedCards[i] = both[i];
			suitedCards[i] = bothCards[i];
		}
	}

	/*- *****************************************************************************
	 * This method initializes arrays of cards for theTurn.
	  ******************************************************************************/
	void cardsAndBoardTurn() {
		both[0] = holeCard1;
		bothCards[0] = holeCard1;
		both[1] = holeCard2;
		bothCards[1] = holeCard2;
		both[2] = Board.boardUnsorted[0];
		both[3] = Board.boardUnsorted[1];
		both[4] = Board.boardUnsorted[2];
		both[5] = Board.boardUnsorted[3];
		bothCards[2] = Board.boardUnsorted[0];
		bothCards[3] = Board.boardUnsorted[1];
		bothCards[4] = Board.boardUnsorted[2];
		bothCards[5] = Board.boardUnsorted[3];
		bothCount = 6;
		both[6] = null;
		bothCards[6] = null;
		SortCards.sortCardsValue(both, 6);
		SortCards.sortCardsCard(bothCards, 6);
		// The sequence here is important.
		addHoleCards();
		pairedCards();
		suits();
		pairedAndValues();
		suitedCards();
		connectedCards();
	}

	/*- *****************************************************************************
	 * This method initializes arrays of cards for the River.
	  ******************************************************************************/
	void cardsAndBoardRiver() {
		both[0] = holeCard1;
		bothCards[0] = holeCard1;
		both[1] = holeCard2;
		bothCards[1] = holeCard2;
		both[2] = Board.boardUnsorted[0];
		both[3] = Board.boardUnsorted[1];
		both[4] = Board.boardUnsorted[2];
		both[5] = Board.boardUnsorted[3];
		both[6] = Board.boardUnsorted[4];
		bothCards[2] = Board.boardUnsorted[0];
		bothCards[3] = Board.boardUnsorted[1];
		bothCards[4] = Board.boardUnsorted[2];
		bothCards[5] = Board.boardUnsorted[3];
		bothCards[6] = Board.boardUnsorted[4];

		bothCount = 7;
		SortCards.sortCardsValue(both, 7);
		SortCards.sortCardsCard(bothCards, 7);
		// The sequence here is important.
		addHoleCards();
		pairedCards();
		suits();
		pairedAndValues();
		suitedCards();
		connectedCards();
	}

	/*-**********************************************************************************************
	* This method will construct the pairedCards array.
	* Hole cards have already been copied to the array.
	* Because any made hand must include both hole cards we begin by copying both hole cards to 
	* Then we copy any cards that are paired. 
	* Finally, if there are not 5 cards we copy the bigger cards as kickers.
	*************************************************************************************************/
	private void pairedCards() {
		addPairedCards();
		if (pairedCount < 5) {
			addKickerCardsToPaired();
		}
		SortCards.sortCardsValue(pairedCards, 5);
	}

	/*-****************************************************************************** 
	* This method will suits in the both array
	* It will then check for gaps between suits
	*********************************************************************************/
	private void suits() {
		int[] suitArray = { 0, 0, 0, 0 };
		for (int i = 0; i < bothCount; i++) {
			++suitArray[bothCards[i].suit];
		}
		for (int i = 0; i < 4; i++) {
			switch (suitArray[i]) {
			case 2 -> {
				flush2 = true;
			}
			case 3 -> {
				flush3 = true;
			}
			case 4 -> {
				flush4 = true;
			}
			case 5 -> {
				flush5 = true;
			}
			default -> {
			}
			}
		}
	}

	/*-**********************************************************************************************
	 * This method will look at the cards in pairedCards and determine if any of 
	 * the cards are paired. If they are, then determine what kind.
	 * onePair, two pair, three of a kind, full house, and four of a kind.
	*************************************************************************************************/
	private void pairedAndValues() {
		int[] valueArray = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		int p = 0;
		int s = 0;
		int q = 0;
		if (holeCard1.value == holeCard2.value) {
			pocketPair = true;
		}

		for (int i = 0; i < 5; i++) {
			if (pairedCards[i] != null) {
				if (pairedCards[i].value == holeCard1.value && pairedCards[i].card != holeCard1.card
						&& pairedCards[i].card != holeCard2.card) {
					holeCard1PairedWithBoard = true;
				} else if (pairedCards[i].value == holeCard2.value && pairedCards[i].card != holeCard1.card
						&& pairedCards[i].card != holeCard2.card) {
					holeCard2PairedWithBoard = true;
				}
			}
		}

		int max = -1;
		int min = 99;
		int k = 0;
		for (int i = 0; i < 5; i++) {
			k = pairedCards[i].value;
			if (k > max)
				max = k;
			if (k < min)
				min = k;
			++valueArray[k];
		}

		// Paired cards must include holeCard value, paired with a hole card
		for (int i = max; i >= min; i--) {
			if (i == holeCard1.value || i == holeCard2.value) {
				if (valueArray[i] == 2) {
					++p;
					anyPaired = true;
					pair1Value = i;
				} else if (valueArray[i] == 3) {
					if (holeCard1.value != i && holeCard2.value != i) {
						if (!set1NoHole) {
							set1NoHole = true;
						}
					}
					++s;
					anyPaired = true;
					if (s == 1) {
						setValue = i;
					}
					anyPaired = true;
				} else if (valueArray[i] == 4) {
					++q;
					anyPaired = true;
					if (holeCard1.value != i && holeCard2.value != i) {
						fourOfKindNoHole = true;
					}
				}
			}
		}

		if (q >= 1) {
			fourOfKind = true;
		} else if ((p >= 1 && s >= 1) || s > 1) {
			fullHouse = true;
		} else if (s == 1) {
			threeOfKind = true;
		} else if (p == 2) {
			twoPair = true;
		} else if (p == 1) {
			onePair = true;
		}

		// Now get the values for two pair ppqqx xppqq ppxqq
		if (twoPair) {
			if (pairedCards[0].value == pairedCards[1].value) {
				pair1Value = pairedCards[0].value;
				if (pairedCards[2].value == pairedCards[3].value) {
					pair2Value = pairedCards[2].value;
				} else {
					pair2Value = pairedCards[3].value;
				}
			} else
				pair1Value = pairedCards[0].value;
			pair2Value = pairedCards[2].value;
		}

		if (onePair && Board.boardPair) {
			if (pair1Value > Board.boardPair1Value) {
				pairPlusBiggerBoardPair = true;
				pairPlusSmallerBoardPair = false;
			} else {
				pairPlusSmallerBoardPair = true;
				pairPlusBiggerBoardPair = false;
			}
		}
	}

	/*-**********************************************************************************************
	* Add hole cards to cards
	* Add to null slots if possible
	*************************************************************************************************/
	private void addHoleCards() {
		pairedCards[0] = holeCard1;
		suitedCards[0] = holeCard1;
		connectedCards[0] = holeCard1;
		pairedCards[1] = holeCard2;
		suitedCards[1] = holeCard2;
		connectedCards[1] = holeCard2;
		for (int i = 2; i < 5; i++) {
			pairedCards[i] = null;
			suitedCards[i] = null;
			connectedCards[i] = null;
		}
	}

	/*-**********************************************************************************************
	* This method will construct the suitedCards array.
	* Hole cards have already been copied to the array.
	* Then we copy any cards that are the same suit as the hole cards.
	* Finally, if there are not 5 cards we copy the bigger cards as kickers.
	*************************************************************************************************/
	private void suitedCards() {
		suitedCount = 2;
		addSuitedCards();
		if (suitedCount < 5) {
			addKickerCardsToSuited();
		}
		SortCards.sortCardsCard(suitedCards, 5);
	}

	/*-**********************************************************************************************
	* This method will construct the connectedCards array.
	* Hole cards have already been copied to the array.
	* Then we copy any cards that have 
	* Finally, if there are not 5 cards we copy the bigger cards as kickers.
	*************************************************************************************************/
	private void connectedCards() {
		connectedCount = 2;
		addConnectedCards();
		if (connectedCount < 5) {
			addKickerCardsToConnected();
		}
	}

	/*-**********************************************************************************************
	* This method will add paired cards pairedCards
	*************************************************************************************************/
	private void addPairedCards() {
		pairedCount = 2;
		for (int i = 0; i < bothCount; i++) {
			if (both[i].card == holeCard1.card || both[i].card == holeCard2.card) {
				takenP[i] = true;
			} else {
				takenP[i] = false;
			}
		}
		// Paired with a hole card
		for (int i = 0; i < bothCount; i++) {
			if (both[i].value == holeCard1.value || both[i].value == holeCard2.value) {
				if (!takenP[i]) {
					pairedCards[pairedCount++] = both[i];
					takenP[i] = true;
					if (pairedCount >= 5)
						break;
				}
			}
		}
		// Board pair AB XXXYY
		if (pairedCount >= 5)
			return;
		for (int i = 0; i < bothCount - 1; i++) {
			if (!takenP[i] && both[i].value == both[i + 1].value) {
				pairedCards[pairedCount++] = both[i];
				takenP[i] = true;
				if (pairedCount >= 5)
					break;
				if (!takenP[i + 1]) {
					pairedCards[pairedCount++] = both[i + 1];
					takenP[i + 1] = true;
					if (pairedCount >= 5)
						break;
				}
			}
		}
	}

	/*-**********************************************************************************************
	* This method will add suited cards suitedCards
	*************************************************************************************************/
	private void addSuitedCards() {
		suitedCount = 2;
		for (int i = 0; i < bothCount; i++) {
			if ((bothCards[i].card != holeCard1.card && bothCards[i].card != holeCard2.card)
					&& (bothCards[i].suit == holeCard1.suit || bothCards[i].suit == holeCard2.suit)) {
				suitedCards[suitedCount++] = bothCards[i];
				if (suitedCount >= 5)
					break;
			}
		}
	}

	/*-**********************************************************************************************
	* This method will add connected cards  
	* KhQdJsTs9s   Kh9s
	* Ah5c4s3d2hAh  4h4h
	* 9cxxx4s    Ax5432
	*************************************************************************************************/
	private void addConnectedCards() {
		for (int i = 0; i < 13; i++) {
			takenValueC[i] = false;
		}
		connectedCount = 2;
		takenValueC[holeCard1.value] = true;
		takenValueC[holeCard2.value] = true;
		boolean wheelPossible = false;
		// Axyyy Axxyyy Axxxyyy
		if (both[0].value == ACE && (holeCard1.value <= FIVE || (holeCard1.value == ACE && holeCard2.value <= FIVE))) {
			wheelPossible = true;
		}
		if (wheelPossible) {
			if (!takenValueC[both[0].value]) {
				// Add the Ace
				connectedCards[connectedCount++] = both[0];
				takenValueC[both[0].value] = true;
			}
			for (int i = 0; i < bothCount; i++) {
				if (!takenValueC[both[i].value] && both[i].value <= FIVE) {
					connectedCards[connectedCount++] = both[i];
					takenValueC[both[i].value] = true;
					if (connectedCount >= 5)
						break;
				}
			}
			// AKJ432 Turn wheel is 4 cards so draw, AKQ is 3 cards so no draw
			// AKQJ43 Turn wheel is 3 cards so no draw, AKQJ is 4 cards so draw
			// No draws on the River so if not wheel go on to check Straight
			if ((EvalData.street == TURN && connectedCount >= 4) || (EvalData.street == RIVER && connectedCount >= 5)) {
				SortCards.sortCardsValue(connectedCards, connectedCount);
				return;
			}
			for (int i = 0; i < 13; i++) {
				takenValueC[i] = false;
			}
			connectedCount = 2;
		}

		int holeCardGap = holeCard1.value - holeCard2.value;
		// No possible straight if holeCardGap is more than 3 cards or pocket pair
		if (holeCardGap > 4 || holeCardGap == 0) {
			return;
		}

		// First try to fill in the gap between hole cards
		if (holeCardGap == 2) {
			// xx9d7cxx Jcx9xd7dx6c
			for (int i = 0; i < bothCount; i++) {
				if (!takenValueC[both[i].value] && holeCard1.value - 1 == both[i].value) {
					connectedCards[connectedCount++] = both[i];
					takenValueC[both[i].value] = true;
					if (connectedCount >= 5)
						break;
				}
			}
		} else if (holeCardGap == 3) {
			// x9d6cx Ts9xxd6c5d
			for (int i = 0; i < bothCount; i++) {
				if (!takenValueC[both[i].value]
						&& (holeCard1.value - 1 == both[i].value || holeCard1.value - 2 == both[i].value)) {
					connectedCards[connectedCount++] = both[i];
					takenValueC[both[i].value] = true;
					if (connectedCount >= 5)
						break;
				}
			}
		} else if (holeCardGap == 4) {
			// 9d5c 9dxxx5c 7dxxx3c
			// connectedCards 6c 2h 5d 4d 4s 6c 2h 3 <<<<<<<<<<< Pair
			for (int i = 0; i < bothCount; i++) {
				if (!takenValueC[both[i].value] && (holeCard1.value - 1 == both[i].value
						|| holeCard1.value - 2 == both[i].value || holeCard1.value - 3 == both[i].value)) {
					connectedCards[connectedCount++] = both[i];
					takenValueC[both[i].value] = true;
					if (connectedCount >= 5)
						break;
				}
			}
		}
		// Then try to fill in cards outside starting with cards closest to hole cards
		// x9yy6x 9 - 6 = 3
		if (connectedCount < 5 && holeCardGap <= 3) {
			for (int i = 0; i < bothCount; i++) {
				if (!takenValueC[both[i].value]
						&& (holeCard1.value + 1 == both[i].value || holeCard2.value - 1 == both[i].value)) {
					connectedCards[connectedCount++] = both[i];
					takenValueC[both[i].value] = true;
					if (connectedCount >= 5)
						break;
				}
			}
		}
		// xx9y7xx
		if (connectedCount < 5 && holeCardGap <= 2) {
			for (int i = 0; i < bothCount; i++) {
				if (!takenValueC[both[i].value]
						&& (holeCard1.value + 2 == both[i].value || holeCard2.value - 2 == both[i].value)) {
					connectedCards[connectedCount++] = both[i];
					takenValueC[both[i].value] = true;
					if (connectedCount >= 5)
						break;
				}
			}
		}
		// xxx98xxx
		if (connectedCount < 5 && holeCardGap <= 1) {
			for (int i = 0; i < bothCount; i++) {
				if (!takenValueC[both[i].value]
						&& (holeCard1.value + 3 == both[i].value || holeCard2.value - 3 == both[i].value)) {
					connectedCards[connectedCount++] = both[i];
					takenValueC[both[i].value] = true;
					if (connectedCount >= 5)
						break;
				}
			}
		}
		SortCards.sortCardsValue(connectedCards, connectedCount);
	}

	/*-**********************************************************************************************
	* This method will kicker cards to cards if needed Connected or paired
	*************************************************************************************************/
	private void addKickerCardsToPaired() {
		for (int i = 0; i < bothCount; i++) {
			if (!takenP[i]) {
				pairedCards[pairedCount++] = both[i];
				if (pairedCount >= 5)
					break;
			}
		}
	}

	/*-**********************************************************************************************
	* This method will kicker cards to cards if needed to connected
	* If there is a pair available add it
	* Give priority to pairs, first paired with hole card, then any pair at all 
	*************************************************************************************************/
	private void addKickerCardsToConnected() {
		boolean[] takenC = { false, false, false, false, false, false, false };
		for (int i = 0; i < bothCount; i++) {
			if (both[i].card == holeCard1.card || both[i].card == holeCard2.card) {
				takenC[i] = true;
			} else {
				for (int j = 0; j < connectedCount; j++) {
					if (both[i].card == connectedCards[j].card) {
						takenC[i] = true;
					}
				}
			}
		}
		// Give priority to pairs, first paired with hole card, then any pair at all
		if (anyPaired || Board.boardPair || Board.boardTwoPair || Board.boardSet) {
			for (int i = 0; i < bothCount - 1; i++) {
				if (both[i].value == holeCard1.value || both[i].value == holeCard2.value) {
					if (!takenC[i]) {
						connectedCards[connectedCount++] = both[i];
						takenC[i] = true;
					}
					if (connectedCount >= 5)
						return;
				}
			}
			if (connectedCount < 5) {
				for (int i = 0; i < bothCount - 1; i++) {
					if (connectedCount < 5 && both[i].value == both[i + 1].value) {
						if (!takenC[i]) {
							connectedCards[connectedCount++] = both[i];
							takenC[i] = true;
						} else if (!takenC[i + 1]) {
							connectedCards[connectedCount++] = both[i + 1];
							takenC[i + 1] = true;
						}
						if (connectedCount >= 5) {
							return;
						}
					}
				}
			}
		}
		for (int i = 0; i < bothCount; i++) {
			if (!takenC[i]) {
				connectedCards[connectedCount++] = both[i];
				takenC[i] = true;
				if (connectedCount >= 5)
					return;
			}
		}
	}

	/*-**********************************************************************************************
	* This method will kicker cards to cards if needed suited
	* If there is a pair available add it
	* Give priority to pairs, first paired with hole card, then any pair at all 
	*************************************************************************************************/
	private void addKickerCardsToSuited() {
		boolean[] takenS = { false, false, false, false, false, false, false };
		for (int i = 0; i < bothCount; i++) {
			for (int j = 0; j < suitedCount; j++) {
				if (bothCards[i].card == suitedCards[j].card) {
					takenS[i] = true;
				}
			}
		}
		// Give priority to pairs, first paired with hole card, then any pair at all
		if (anyPaired || Board.boardPair || Board.boardTwoPair || Board.boardSet) {
			for (int i = 0; i < bothCount - 1; i++) {
				if (bothCards[i].value == holeCard1.value || bothCards[i].value == holeCard2.value) {
					if (!takenS[i]) {
						suitedCards[suitedCount++] = bothCards[i];
						takenS[i] = true;
					}
					if (suitedCount >= 5)
						return;
				}
			}
			if (suitedCount < 5) {
				for (int i = 0; i < bothCount - 1; i++) {
					if (suitedCount < 5 && bothCards[i].value == bothCards[i + 1].value) {
						if (!takenS[i]) {
							suitedCards[suitedCount++] = bothCards[i];
							takenS[i] = true;
						} else if (!takenS[i + 1]) {
							suitedCards[suitedCount++] = bothCards[i + 1];
							takenS[i + 1] = true;
						}
						if (suitedCount >= 5)
							return;
					}
				}
			}
		}

		for (int i = 0; i < bothCount; i++) {
			if (!takenS[i]) {
				suitedCards[suitedCount++] = bothCards[i];
				if (suitedCount >= 5)
					return;
			}
		}
	}

	/*- **************************************************************************** 
	* Information obtained includes:
	* 		Board analysis on Flop, gaps, suholeCardGapdness, etc..
	* 		Draws available with combined board and hole cards. 
	* A data holding Classes Draw and Made is used to save the cards that make up
	* the hand and  other related information. 
	* There is one instance of Draw and Made in EvalData for each player as arrays
	* indexed by seat number. 
	*******************************************************************************/

	/*- ************************************************************************************** 
	* Flop made hands with pairs of cards.
	* Four of a kind, full house,set, two pair.
	* 
	* A  made hand must include both hole cards. 
	* It is not enough that the both card array contains a full house.
	* Not a problem on the Flop, there are only 5 cards.
	* On the Turn or River there can be full house or four of a kind, olt two pair with none
	* of the players hold cards.
	* With a set or pair there is room for hole cards.
	* 
	* If the hand does not include hole cards, then the hand is basically useless
	* unless hit by a EvalData.draw. 
	* Example: Ts 9s    5d 5h 5c	 	is useless unless the Turn or River comes with an T or 9.
	* Even then the opponent may already have a bigger pocket pair or a 5.
	* Dangerous and weak.
	* 
	* What this code does it to look at paired combinations, four of a kind, full house, 
	* sets, two pairs and pairs that include both hole cards or one hole card participating
	* and one hole card as a kicker.
	* Example: Ts 9s    9d 9h 2c 		A  set with T kicker. A T and 9 are both outs.
	* Example 4s 5s    Kc Kh 6c 6s 	is nothing, not even a pair. Opponent with K or 6 has FH. 
	* 		No EvalData.draws. Fold or bluff
	* 
	* If neither hole card value matches a pair value set as MADE_NONE
	*****************************************************************************************/
	void analyzePaired() {
		int type = findType();
		finishMade(type);
	}

	/*-**********************************************************************************************
	* This method will check the card array to find made hands with paired cards
	*************************************************************************************************/
	private int findType() {
		if (fourOfKind) {
			return MADE_FOUR_OF_A_KIND;
		}
		if (fullHouse) {
			return MADE_FULL_HOUSE;
		}
		if (threeOfKind) {
			return MADE_SET;
		}
		if (twoPair) {
			return whatKindOfTwoPair();
		}
		if (Board.boardPair && onePair) {
			return whatKindOfBoardPairPlusPair();
		}
		if (onePair) {
			return whatKindOfPair();
		}
		if (Board.boardPair) {
			return whatKindOfBoardPair();
		}
		type = whatKindOfBigCards();
		return type;
	}

	/*- ************************************************************************************
	 * What kind of big cards
	 * Over cards - hole cards that rank higher than any card appearing on the board.
	 * Ace High
	  ************************************************************************************/
	private int whatKindOfBigCards() {
		if (holeCard1.value == ACE) {
			return MADE_ACE_HIGH;
		}
		boolean over = false;
		for (int i = 0; i < 5; i++) {
			if (holeCard1.card != pairedCards[i].card && holeCard2.card != pairedCards[i].card) {
				if (holeCard1.value > pairedCards[i].value && holeCard2.value > pairedCards[i].value) {
					over = true;
				}
			}
		}
		if (over) {
			return MADE_OVERCARDS;
		}
		return MADE_NONE;
	}

	/*- *************************************************************************************** 
	What kind of boardPair
	 Not sure about this. Needs evaluation.
	When the board pairs in No Limit Texas Hold'em, especially in a 6-max setting,
	 there are several strategic considerations you should take into account:
	
	Pot Control: With a paired board, the likelihood of someone having trips or better increases. 
	If you don't have a strong hand that can beat trips, it's often wise to control the size of the pot, 
	especially if there are still betting rounds to come.
	
	Representing Trips: If you've been the aggressor or have a reason to believe that your opponent 
	may fold to continued aggression, you might be able to represent having trips yourself. 
	This can be especially effective if the paired card is one that would reasonably 
	fit into your range given your previous actions.
	
	Overpairs: If you have an overpair to the board and it pairs, your overpair is still strong, 
	but it's now vulnerable to any player holding a card of the rank that's paired. 
	Be cautious if you face heavy aggression.
	
	Drawing Hands: If you have a drawing hand (like a straight or flush draw), a paired board 
	can be problematic. 
	if you complete your draw but the board has paired, you could be drawing to a
	 hand that's second-best to a full house.
	
	Bluffing Opportunities: Some paired boards, especially ones that are less likely to have hit 
	your opponent's range, can offer good bluffing opportunities, especially if you can represent a
	 hand that would logically play the way you've been playing.
	
	Boat and Quads Possibilities: If the board pairs twice (e.g., J-J-7-7), remember that quads 
	(four of a kind) and full houses become possible. This can change the dynamics considerably, 
	as now a player can have a hand like 7-7 for quads or J-7 for a full house.
	
	Stack Sizes: Always be aware of the stack sizes. If you or your opponent have a short stack,
	 the implications of the paired board can become magnified. For example, with a short stack, 
	 you might be more inclined to shove with an overpair, while an opponent might be more 
	 inclined to call with just a pair.
	
	Opponent's Perceived Range: Always consider what kind of hands your opponent might be holding. 
	If the board pairs a high card and you believe your opponent would always 3-bet big pairs pre-flop, 
	then it might be less likely that they have trips.
	
	Implied Odds: The implied odds can change with a paired board. For example, if you think 
	your opponent has trips and you're drawing to a hand that can beat it (like a straight or flush), 
	the potential payout if you hit your hand could be substantial.
	
	Beware of Slow-Plays: Especially in 6-max games, where players might take more deceptive 
	lines with their strong hands, be cautious of opponents who suddenly show strong aggression 
	on later streets. They might have slow-played a monster on the flop.
	
	In summary, a paired board in No Limit Texas Hold'em adds complexity to the game and  
	requires careful thought about both your and your opponent's ranges. 
	Adjusting your strategy based on the texture of the board and the tendencies of your opponents  
	is crucial to maximizing your profitability.
	********************************************************************************************/
	private int whatKindOfBoardPairPlusPair() {
		if (pairPlusSmallerBoardPair) {
			return MADE_PAIR_BELOW_BOARD_PAIR;
		}
		if (pairPlusBiggerBoardPair) {
			return MADE_PAIR_ABOVE_BOARD_PAIR;
		}
		return MADE_BOARD_PAIR;
	}

	/*- ************************************************************************************************
	 * Just a board pair 
	 ********************************************************************************************/
	private int whatKindOfBoardPair() {
		return MADE_BOARD_PAIR;
	}

	/*- ************************************************************************************************
	What kind of pair is this?
	Over Pair: When a player's pocket pair (the two hole cards dealt face-down to the player) 
	is higher than any of the community cards on the board. For example, if you hold a pair of Queens
	and the board reads 8-6-3-4-2, you have an over pair.
	
	Top Pair: When a player's hole card pairs with the highest card on the board. 
	If you have an Ace in your hand and the flop comes A-8-6, you've hit top pair with your Ace.
	
	Pocket Pair Below Top Pair: When a player's pocket pair is lower than the highest card on the board 
	but higher than other community cards. If you hold 8-8 and the board is A-8-4, you have a 
	pocket pair below top pair.
	
	Middle Pair: When a player's hole card pairs with the second highest card on the board. 
	If you have a Jack in your hand and the flop comes K-J-6, you've made middle pair.
	
	Weak Pair: A somewhat subjective term. It generally refers to a pair that's neither top, middle, 
	nor bottom pair and might be vulnerable to being outdrawn or is already beaten by many 
	other possible hands. For instance, if you have a 6 in your hand and the board is A-K-6-4-2, you 
	have a weak pair. However, context matters, and in some situations, this might be referred to 
	as bottom pair.
	
	Bottom Pair: When a player's hole card pairs with the lowest card on the board. 
	If you have a 4 in your hand and the flop is K-J-4, you've made bottom pair.
	************************************************************************************************/
	private int whatKindOfPair() {
		if (pocketPair) {
			if (holeCard1.value > Board.boardHighCardValue1) {
				return MADE_OVER_PAIR;
			}
			if (holeCard1.value <= Board.boardHighCardValue1) {
				return MADE_POCKET_PAIR_BELOW_TP;
			}
			Crash.log("WTF " + 0 + " " + 0 + " " + EvalData.street + " " + GameControl.gamesPlayed + " "
					+ GameControl.handsPlayed);
		} else if (onePair) {
			// pairs
			if (pair1Value == Board.boardHighCardValue1) {
				return MADE_TOP_PAIR;
			}
			if (pair1Value < Board.boardHighCardValue1 && pair1Value == Board.boardLowCardValue) {
				return MADE_BOTTOM_PAIR;
			}
			if (pair1Value == Board.boardMiddleCardValue) {
				return MADE_MIDDLE_PAIR;
			}
			return MADE_WEAK_PAIR;
		}
		Crash.log("Crash " + 0 + " " + seat + " " + GameControl.gamesPlayed + " " + GameControl.handsPlayed + " "
				+ EvalData.street);
		return -1;
	}

	/*- ************************************************************************************
	* What kind of two pair is this?
	/*- ************************************************************************************/
	private int whatKindOfTwoPair() {
		if (pair1Value >= Board.boardHighCardValue1 && pair2Value >= Board.boardHighCardValue2) {
			return MADE_TOP_TWO_PAIR;
		}
		if (pair1Value >= Board.boardHighCardValue1 && pair2Value < Board.boardHighCardValue2) {
			return MADE_TOP_AND_BOTTOM_TWO_PAIR;
		}
		return MADE_BOTTOM_TWO_PAIR;
	}

	/*- ************************************************************************************
	 * INFORMATIONAL ONLY
	
	 What kind of two pair is this?
	  Returns MADE_BOTTOM_TWO_PAIR, MADE_TOP_AND_BOTTOM_TWO_PAIR, or MADE_TOP_TWO_PAIR
	
	definitions two pair for flop, turn, and river
	ChatGPT
	When a player has two pair, it refers to their hand consisting of two different pairs of cards. 
	The classification of these pairs can change as the board develops from the flop to the turn and river. 
	Here's how it typically goes:
	
	Flop:
	
	Top Two Pair: When your hand makes a pair with the two highest cards on the flop. 
	Example: You hold A-Q, and the flop comes Q-A-5.
	Top and Middle Pair: When your hand makes a pair with the highest and middle cards on the flop. 
	Example: You hold A-8, and the flop comes A-8-3.
	Top and Bottom Pair: When your hand makes a pair with the highest and lowest cards on the flop. 
	Example: You hold A-5, and the flop comes A-9-5.
	Middle Two Pair: When your hand makes a pair with the two middle cards on the flop. 
	Example: You hold 9-8, and the flop comes 10-9-8.
	Middle and Bottom Pair: When your hand makes a pair with the middle and lowest cards on the flop. 
	Example: You hold 8-3, and the flop comes 10-8-3.
	Bottom Two Pair: When your hand makes a pair with the two lowest cards on the flop. 
	Example: You hold 5-3, and the flop comes 8-5-3.
	Turn: The addition of a fourth community card can change the classification of your two pairs. 
	For example, if you held A-5, and the flop was A-9-5, you had top and bottom pair. 
	But if a King comes on the turn, your pair of Aces and 5s becomes middle and bottom pair.
	
	River: A fifth and final community card is added. Just like with the turn, the river card 
	may affect the status of your two pairs.
	
	Remember, these classifications mainly serve to describe the strength of your hand, but they 
	don't tell the whole story. The strength of your hand also depends on the actions of your 
	opponents, the board texture, and many other factors in the game situation. 
	Always consider these factors when deciding the best course of action.
	
	
	User
	what if board paired
	ChatGPT
	When the board pairs, it means that there are two cards of the same rank among the community cards. 
	This changes the dynamics of the game significantly as it introduces the potential for full houses 
	and three-of-a-kinds (also known as 'trips' or 'sets').
	
	Here's how it might affect your situation:
	
	Board Pairs on the Flop: If the flop comes with a pair, for example 8-8-3, and you have a hand like A-8,
	you would have three-of-a-kind (trips). If you have a hand like A-3, you would have bottom pair, 
	and if you have something like A-K, you would have an over pair to the board.
	
	Board Pairs on the Turn or River: If the board pairs on the turn or river, 
	for example the flop comes A-9-4 and the turn is a 9, this introduces the possibility of full houses. 
	If you hold A-9, you now have a full house. If you hold A-K, you have two pairs (Aces and Nines) with a King kicker.
	
	When the board pairs, it's crucial to consider how the paired board interacts with your opponents' likely ranges. 
	For instance, if an opponent bets aggressively on a paired board, they could have a three-of-a-kind or full house, 
	or they could be bluffing with the intention of representing these hands.
	
	In general, when the board pairs, it often makes your relative hand strength weaker, 
	unless you also hold a card of the same rank as the pair on the board. 
	The more players in the pot, the more cautious you should be when the board pairs, 
	as the likelihood that someone has trips or a full house increases.
	
	
	User
	Top Two Pair:  
	Top and Middle Pair: does definition change if one of the pairs is a board pair
	ChatGPT
	Yes, the definition can change if one of the pairs is a pair on the board.
	
	When you have "Top Two Pair" or "Top and Middle Pair," it traditionally means you're using 
	both of your hole cards to make two different pairs with the community cards.
	
	Top Two Pair: This usually means you have a hand that forms a pair with the two highest 
	cards on the board. For example, if you hold A-Q, and the flop comes Q-A-5, you have Top Two Pair.
	
	Top and Middle Pair: This usually means you have a hand that forms a pair with the highest 
	and middle cards on the board. For example, if you hold A-8, and the flop comes A-8-3, you have Top and Middle Pair.
	
	However, if the board pairs, making one of the pairs on the board, the description of your hand will change.
	For example, if the board is Q-Q-5, and you hold an A-Q, you would not be described as having "Top Two Pair".
	Instead, you'd have "three-of-a-kind" or "trips". If you hold A-5, you wouldn't have "Top and Middle Pair". 
	Instead, you would have a "pair of fives" with an Ace kicker, or simply "a pair" if there's no chance of confusion.
	
	It's worth noting that when the board pairs, it greatly increases the strength of hands that can be made, 
	such as three-of-a-kind or full houses, so a simple pair or two pair hand is often much weaker in relative terms.
	Always consider the whole board texture, the possible hands your opponents could have, and the betting 
	action when determining the strength of your hand.
		
	User
	what if hole cards A T and board T J J
	ChatGPT
	In this scenario, you hold Ace-Ten (A-T) and the board shows Ten-Jack-Jack (T-J-J). You have two pair, 
	Jacks and Tens, with an Ace kicker.
	
	Your hole card, Ten, pairs with the Ten on the board, and the two Jacks on the board form the second pair.
	 In Texas Hold'em poker, a poker hand consists of the best five-card hand you can make using any combination 
	 of your two hole cards and the five community cards on the board. The Ace in your hand acts as the 
	 "kicker" or tiebreaker card, meaning that if another player also has two pair (Jacks and Tens), 
	 your hand would beat theirs if their fifth card (or kicker) is lower than an Ace.
	
	However, it's important to note that in this situation, any player holding a Jack has you beaten,
	as they would have three-of-a-kind (also known as "trips" or "a set"). Also, any player holding 
	a Queen or a King would have a higher two pair (Queens and Jacks, or Kings and Jacks) that beats your two pair.
		
	User
	what if hole cards A T and board A J J is that an over pair
	ChatGPT
	In this scenario, where you hold Ace-Ten (A-T) and the board shows Ace-Jack-Jack (A-J-J), you would have two pair, 
	Aces and Jacks, with a Ten kicker.
	
	Your hole card, Ace, pairs with the Ace on the board, and the two Jacks on the board form the second pair.
	 As mentioned before, in Texas Hold'em, a poker hand consists of the best five-card hand you can make using 
	 any combination of your two hole cards and the five community cards on the board. The Ten in your hand 
	 acts as the "kicker" or tiebreaker card.
	
	However, in this case, your hand is not considered an "over pair." An over pair refers to a situation 
	where you have a pair in your hole cards that is higher than any card on the board. For instance, 
	if you held A-A and the board was J-7-2, you'd have an over pair (Aces), as your pair is higher 
	than any card on the board.
	
	In your given scenario (hole cards A-T and board A-J-J), your best hand is two pair (Aces and Jacks). 
	It's also worth noting that any player holding a Jack in this situation would have a better hand 
	- a full house (Jacks full of Aces) - and anyone with an Ace and a better kicker than Ten would also beat your hand.
		
	Yes, those definitions are correct.
	
	Here is a table that summarizes the different types of two pair hands:
	
	Type of Two Pair	Description	Example
	Top Two Pair	Your hand makes a pair with the two highest cards on the flop.	You hold A-Q, and the flop comes Q-A-5.
	Top and Middle Pair	Your hand makes a pair with the highest and middle cards on the flop.	You hold A-8, and the flop comes A-8-3.
	Top and Bottom Pair	Your hand makes a pair with the highest and lowest cards on the flop.	You hold A-5, and the flop comes A-9-5.
	Middle Two Pair	Your hand makes a pair with the two middle cards on the flop.	You hold 9-8, and the flop comes 10-9-8.
	Middle and Bottom Pair	Your hand makes a pair with the middle and lowest cards on the flop.	You hold 8-3, and the flop comes 10-8-3.
	Bottom Two Pair	Your hand makes a pair with the two lowest cards on the flop.	You hold 5-3, and the flop comes 8-5-3.
	
	 * Flop
	 * Also used in both doTurnHands() and doRiverHands() because hands found 
	 * earlier will be better hands.
	 * Confusing but because the card arrays are sorted the higher cards will be first in the array.
	 * Example: 
	 * Hole cards Qh Qc
	 * Flop array was Qh Qc Tc 6c 5c but turn card Ah new sorted array 
	 * Ah Qd Qd Tc 6c 5c still a pair but better kicker. River card is Ac and array is
	 * Ac Ah Qh Qc Tc 6c 5c and best hand is actually two pair. 
	 * The Flush is not valid because a made hand must include both hole cards.
	 * His hand is actually Qh Qc Ah Ac Tc or sorted as Ah Ac Qh Qc Tc
	 * 
	* This method will determine if there is a made hand in the current combination of
	* hole cards and the shared board. The both array is sorted. 
	* There is a second array, bothCards that is sorted by card, first by suit then by value.
	* Example 3h 9c Kd Qd Td 9d ( first by suit then by value.
	* 
	* Sorted arrays make checking for made hands very much more efficient, but 
	* a little confusing.
	* 
	* Returns the hand type MADE_NONE, MADE_PAIR, MADE_TWO_PAIR etc..
	* Updates this instance data.
	* If a hand can be made it is here it the instance of this class.
	* 
	* Note: The calling method should have determined that this type of hand is
	* either a sure thing ( Flop ) or likely ( Turn or River )
	* 
	*	if (bothGap1_2 == 0 || bothGap2_3 == 0 || bothGap3_4 == 0
	*		hand[seat].doHandPairs() 
	*	}else{
	* if (bothGap1_2 == 1 && bothGap2_3 == 1 && bothGap3_4 == 1
	*		&& bothGap4_5 == 1) {
	*		hand[seat].doHandStraight();
	* }else{
	* if (bothCardsSuit1 == bothCardsSuit2
	*		&& bothCardsSuit1 == bothCardsSuit3
	*   	&& bothCardsSuit1 == bothCardsSuit4 && bothGap4_5 == 1) {
	*   hand[seat].doHandFlush();
	* }
	* 
	* Top Pair: This refers to having a pair with the highest card on the flop. 
	* For example, if you have AK and the flop comes A-7-2, you have the top pair (a pair of Aces).
	*		
	* Middle Pair: This refers to having a pair with the middle card on the flop. 
	* For example, if you have 7K and the flop comes A-7-2, you have the middle pair (a pair of Sevens).
	*
	* Bottom Pair: This refers to having a pair with the lowest card on the flop. 
	* For example, if you have 2K and the flop comes A-7-2, you have the bottom pair (a pair of Twos).
	*
	* Over Pair: This refers to having a pair in your hand that is higher than any card on the board. 
	* For example, if you have QQ and the flop comes J-7-2, you have an over pair (a pair of Queens, which is higher
	* The strength of your pair can greatly influence your decisions in poker. 
	* A top pair or over pair is generally a strong hand, while a middle or bottom pair can often be vulnerable. 
	* However, context is crucial: the strength of your hand should always be evaluated relative to what 
	* other hands your opponents might have.
	*
	* Yes, when a player has two pair, they're often described based on the cards that make up the pairs.
	* While not as commonly referred to as top pair, middle pair, bottom pair, or over pair, 
	* these classifications can still be helpful in analyzing the strength of the hand. Here are a
	* few possible scenarios:
	*
	* Top Two Pair: When a player's hand includes the two highest cards on the board. For example, 
	* if you hold A-7 and the flop is A-7-2, you have top two pair.
	*
	* Top and Bottom Pair: When a player's hand includes the highest and the lowest card on the board. 
	* For example, if you hold A-2 and the flop is A-7-2, you have top and bottom pair.
	*
	* Bottom Two Pair: When a player's hand includes the two lowest cards on the board. 
	* For example, if you hold 7-2 and the flop is A-7-2, you have bottom two pair.
	*
	* Just like with a single pair, the strength of your two pair hand can greatly influence your 
	* decisions in poker. 
	* In general, two pair is a strong hand, but the exact strength of it can vary significantly based 
	* on the board texture, possible hands of your opponents, and your position at the table. 
	* Always consider these factors when deciding how to proceed with your hand.
	* 
	* 
	texas holeem no limit 6-max what are considerations board pair
	*
	 ***************************************************************************************/

	/*- **************************************************************************** 
	* Flop made hands
	* If there is no Straight or Flush then do nothing. We use results from paired.
	* Returns true if Straight or Flush.
	*******************************************************************************/
	boolean analyzeStraightAndFlush() {
		straight = false;
		flush = false;
		findStraight();
		findFlush();
		if (!straight && !flush) {
			return false;
		}
		// Flush is better hand
		if (flush) {
			type = MADE_FLUSH;
			updateCounts();
		} else if (straight) {
			type = MADE_STRAIGHT;
			updateCounts();
		}
		if (straight && flush) {
			int sameCount = 0;
			boolean ace = false;
			// Check that all 5 cards in connectedCards and in suitedCards are the same
			// cards
			if (suitedCards[0].value == ACE) {
				ace = true;
			}
			// Are the same cards in connectedCards as are in suitedCards
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (connectedCards[i].card == suitedCards[j].card) {
						sameCount++;
					}
				}
			}
			if (sameCount == 5) {
				if (ace) {
					type = MADE_ROYAL_FLUSH;
				} else {
					type = MADE_STRAIGHT_FLUSH;
				}
			}
		}
		finishMadeStraightFlush(type);
		return true;
	}

	/*- **************************************************************************** 
	* Update counts for how many times this street was evaluated using simulation
	* and without simulation.
	* With simulation players will have folded, no folds without simulation.
	* If there is a made Straight ot a made Flush the count is incremented by 1
	* for accuracy. There will also be a paired value or Overcards.
	*******************************************************************************/
	private void updateCounts() {
		if (street == FLOP) {
			if (EvalData.preflopSimulation) {
				++DrawMadeWonPercentages.typeCountSimMadeFlop;
			} else {
				++DrawMadeWonPercentages.typeCountMadeFlop;
			}
		} else if (street == TURN) {
			if (EvalData.preflopSimulation) {
				++DrawMadeWonPercentages.typeCountSimMadeTurn;
			} else {
				++DrawMadeWonPercentages.typeCountMadeTurn;
			}
		} else if (street == RIVER) {
			if (EvalData.preflopSimulation) {
				++DrawMadeWonPercentages.typeCountSimMadeRiver;
			} else {
				++DrawMadeWonPercentages.typeCountMadeRiver;
			}
		}
	}

	/*- **************************************************************************** 
	* This method will check bothNoNpPair for a made hands straight.
	* We just use brute force comparing the gaps between cards by subtracting value.
	*******************************************************************************/
	private void findStraight() {
		if (connectedCards[0].value - connectedCards[1].value == 1
				&& connectedCards[1].value - connectedCards[2].value == 1
				&& connectedCards[2].value - connectedCards[3].value == 1
				&& connectedCards[3].value - connectedCards[4].value == 1) {
			straight = true;
			return;
		}

		// Check forAce Straight wheel
		if (connectedCards[0].value == ACE && connectedCards[1].value == FIVE && connectedCards[2].value == FOUR
				&& connectedCards[3].value == THREE && connectedCards[4].value == TWO) {
			straight = true;
		}
	}

	/*- **************************************************************************** 
	* This method will check suitedCardsfor a made hands flush.
	*******************************************************************************/
	private void findFlush() {
		if (suitedCards[0].suit == suitedCards[1].suit && suitedCards[0].suit == suitedCards[2].suit
				&& suitedCards[0].suit == suitedCards[3].suit && suitedCards[0].suit == suitedCards[4].suit) {
			flush = true;
		}
	}

	private final int[] drawType = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private int drawCount = 0;
	private boolean pairSuited = false;
	private boolean pairConnected = false;

	/*- **************************************************************************** 
	* Flop drawing hands
	* There can be more than one draw:
	*        OESD and Flush
	*        Gutshot and Flush
	*        Gutshot and OESD
	*        Not sure what else so need to be very careful not to miss a possibility so
	*        We use 2 sets of arrays in EvalData
	* 
	* Called by Analyze.evaluateTurn(seat) after calling 	BothValues.cardsAndBoardTurn(seat)       
	*******************************************************************************/
	void analyzeDraw() {
		flush = false;
		drawCount = 0;
		type = DRAW_NONE;
		for (int i = 0; i < 6; i++) {
			drawType[i] = 0;
		}
		// Check for any paired in connectedCards or suitedCards
		pairConnected = false;
		pairSuited = false;
		for (int i = 0; i < 4; i++) {
			for (int j = i + 1; j < 5; j++) {
				if (connectedCards[i].value == connectedCards[j].value) {
					pairConnected = true;
				}
				if (suitedCards[i].value == suitedCards[j].value) {
					pairSuited = true;
				}
			}
		}

		analyzeFlushDraw();
		// The combination of Flush and other draws is a problem because there are
		// 2 different card arrays. We choose the stronger hand with more
		// outs, Flush
		// TODO Think about this

		analyzeStraightDraw();
		analyzeOESDDraw();
		analyzeGutshotDraw();
		convertMultipleDrawsToSingleDraw();

		type = drawType[0];
		finishDraw(type);
	}

	/*-**********************************************************************************************
	* This method will
	*************************************************************************************************/
	private void analyzeFlushDraw() {
		if (suitedCards[0].suit == suitedCards[1].suit && suitedCards[0].suit == suitedCards[2].suit
				&& suitedCards[0].suit == suitedCards[3].suit && suitedCards[0].suit == suitedCards[4].suit) {
			return;
		}
		if (suitedCards[0].suit == suitedCards[1].suit && suitedCards[0].suit == suitedCards[2].suit
				&& suitedCards[0].suit == suitedCards[3].suit) {
			drawType[drawCount++] = DRAW_FLUSH;
			drawWithFlush = true;
		} else if (suitedCards[1].suit == suitedCards[2].suit && suitedCards[1].suit == suitedCards[3].suit
				&& suitedCards[1].suit == suitedCards[4].suit) {
			drawType[drawCount++] = DRAW_FLUSH;
			drawWithFlush = true;
		}
	}

	/*-**********************************************************************************************
	* This method will check for a Straight draw
	*  AKQJ6   
	*************************************************************************************************/
	private void analyzeStraightDraw() {
		if (connectedCards[0].value == ACE && connectedCards[1].value == KING && connectedCards[2].value == QUEEN
				&& connectedCards[3].value == JACK && connectedCards[4].value != TEN) {
			drawType[drawCount++] = DRAW_STRAIGHT;
		}
	}

	/*-**********************************************************************************************
	* This method will check for an OESD draw
	* T9873 or K9876 or A9432  
	*************************************************************************************************/
	private void analyzeOESDDraw() {
		// Ax432
		if (connectedCards[0].value == ACE && connectedCards[2].value == FOUR && connectedCards[3].value == THREE
				&& connectedCards[4].value == TWO) {
			drawType[drawCount++] = DRAW_OESD;
			return;
		}
		if (connectedCards[0].value == ACE) {
			return;
		}
		if (connectedCards[0].value - connectedCards[1].value == 1
				&& connectedCards[1].value - connectedCards[2].value == 1
				&& connectedCards[2].value - connectedCards[3].value == 1
				&& connectedCards[3].value - connectedCards[4].value != 1) {
			drawType[drawCount++] = DRAW_OESD;
			return;
		}
		if (connectedCards[0].value - connectedCards[1].value != 1
				&& connectedCards[1].value - connectedCards[2].value == 1
				&& connectedCards[2].value - connectedCards[3].value == 1
				&& connectedCards[3].value - connectedCards[4].value == 1) {
			drawType[drawCount++] = DRAW_OESD;
		}
	}

	/*-**********************************************************************************************
	* This method will check for a gutshot draw
	* T8762 or T9762 or T9862 or KT876  or KT976  or KT986  or or   AQJ42 or  AQJ32
	*************************************************************************************************/
	private void analyzeGutshotDraw() {
		if (connectedCards[0].value - connectedCards[1].value == 1
				&& connectedCards[1].value - connectedCards[2].value == 1
				&& connectedCards[2].value - connectedCards[3].value == 1
				&& connectedCards[3].value - connectedCards[4].value == 1) {
			// It is a Straight so exit
			return;
		}
		// AQ432 542_A
		if (connectedCards[0].value == ACE && connectedCards[2].value <= FIVE) {
			if (connectedCards[2].value - connectedCards[3].value == 1
					&& connectedCards[3].value - connectedCards[4].value == 1) {
				drawType[drawCount++] = DRAW_GUTSHOT;
				return;
			}
			// AQ542 542_A
			if (connectedCards[2].value - connectedCards[3].value == 1
					&& connectedCards[3].value - connectedCards[4].value == 2) {
				drawType[drawCount++] = DRAW_GUTSHOT;
				return;
			}
			// AQ532 532_A
			if (connectedCards[2].value - connectedCards[3].value == 2
					&& connectedCards[3].value - connectedCards[4].value == 1) {
				drawType[drawCount++] = DRAW_GUTSHOT;
				return;
			}
		}
		if (connectedCards[0].value - connectedCards[1].value == 1
				&& connectedCards[1].value - connectedCards[2].value == 1
				&& connectedCards[2].value - connectedCards[3].value == 2) {
			drawType[drawCount++] = DRAW_GUTSHOT;
		}
		if (connectedCards[1].value - connectedCards[2].value == 1
				&& connectedCards[2].value - connectedCards[3].value == 2
				&& connectedCards[3].value - connectedCards[4].value == 1) {
			drawType[drawCount++] = DRAW_GUTSHOT;
		}
		if (connectedCards[0].value - connectedCards[1].value == 2
				&& connectedCards[1].value - connectedCards[2].value == 1
				&& connectedCards[2].value - connectedCards[3].value == 1) {
			drawType[drawCount++] = DRAW_GUTSHOT;
		}
		if (connectedCards[0].value - connectedCards[1].value == 1
				&& connectedCards[1].value - connectedCards[2].value == 2
				&& connectedCards[2].value - connectedCards[3].value == 1) {
			drawType[drawCount++] = DRAW_GUTSHOT;
		}
		if (connectedCards[1].value - connectedCards[2].value == 1
				&& connectedCards[2].value - connectedCards[3].value == 1
				&& connectedCards[3].value - connectedCards[4].value == 2) {
			drawType[drawCount++] = DRAW_GUTSHOT;
		}
		if (connectedCards[1].value - connectedCards[2].value == 2
				&& connectedCards[2].value - connectedCards[3].value == 1
				&& connectedCards[3].value - connectedCards[4].value == 1) {
			drawType[drawCount++] = DRAW_GUTSHOT;
		}
	}

	/*- ******************************************************************************************
	* Check for more than one draw type
	********************************************************************************************/
	private void convertDrawsWithPair() {
		if (pairSuited) {
			if (drawType[0] == DRAW_FLUSH) {
				drawType[0] = DRAW_FLUSH_PAIR;
				drawCount = 1;
				return;
			}
		}

		if (pairConnected) {
			if (drawType[0] == DRAW_GUTSHOT) {
				drawType[0] = DRAW_GUTSHOT_PAIR;
				drawCount = 1;
				return;
			}
			if (drawType[0] == DRAW_STRAIGHT) {
				drawType[0] = DRAW_STRAIGHT_PAIR;
				drawCount = 1;
				return;
			}
			if (drawType[0] == DRAW_OESD) {
				drawType[0] = DRAW_OESD_PAIR;
				drawCount = 1;
				return;
			}
		}
	}

	/*- ******************************************************************************************
	* Check for more than one draw type
	********************************************************************************************/
	private void convertMultipleDrawsToSingleDraw() {
		if (drawCount == 0) {
			drawType[0] = DRAW_NONE;
			return;
		}

		if (drawCount > 1) {
			convertMultipleToSingle();
		}
		if (drawCount >= 1 && (pairConnected || pairSuited)) {
			convertDrawsWithPair();
		}

		// WTF did not catch double draw
		if (drawCount > 1) {
			System.out.println("//drawCount =  " + drawCount + " " + DRAW_ST[drawType[0]] + " " + DRAW_ST[drawType[1]]
					+ " " + DRAW_ST[drawType[2]] + " " + DRAW_ST[drawType[3]] + "    " + GameControl.handsPlayed);
			System.out.println("//both " + both[0] + " " + both[1] + " " + both[2] + " " + both[3] + " " + both[4] + " "
					+ both[5] + " " + both[6] + "    " + holeCard1 + " " + holeCard2);
			System.out.println("//suitedCards " + suitedCards[0] + " " + suitedCards[1] + " " + suitedCards[2] + " "
					+ suitedCards[3] + " " + suitedCards[4] + "    " + holeCard1 + " " + holeCard2);
			System.out
					.println("//connectedCards " + connectedCards[0] + " " + connectedCards[1] + " " + connectedCards[2]
							+ " " + connectedCards[3] + " " + connectedCards[4] + "    " + holeCard1 + " " + holeCard2);
			Crash.log("WTF " + drawType[0] + " " + drawType[1] + " " + drawType[2]);
		}
	}

	/*-**********************************************************************************************
	* This method will convert multiple types into a single type
	*************************************************************************************************/
	private void convertMultipleToSingle() {
		if (drawCount == 2) {

			if ((drawType[0] == DRAW_GUTSHOT && drawType[1] == DRAW_FLUSH)
					|| (drawType[1] == DRAW_GUTSHOT && drawType[0] == DRAW_FLUSH)) {
				drawType[0] = DRAW_FLUSH_GUTSHOT;
				drawCount = 1;
				return;
			}

			if ((drawType[0] == DRAW_OESD && drawType[1] == DRAW_FLUSH)
					|| (drawType[1] == DRAW_OESD && drawType[0] == DRAW_FLUSH)) {
				drawCount = 1;
				drawType[0] = DRAW_FLUSH_OESD;
				return;
			}

			if ((drawType[0] == DRAW_GUTSHOT && drawType[1] == DRAW_OESD)
					|| (drawType[1] == DRAW_GUTSHOT && drawType[0] == DRAW_OESD)) {
				drawType[0] = DRAW_OESD_GUTSHOT;
				drawCount = 1;
				return;
			}

			if ((drawType[0] == DRAW_GUTSHOT && drawType[1] == DRAW_STRAIGHT)
					|| (drawType[1] == DRAW_GUTSHOT && drawType[0] == DRAW_STRAIGHT)) {
				drawType[0] = DRAW_STRAIGHT_GUTSHOT;
				drawCount = 1;
				return;
			}

			// Check for double Gutshot like 98653 or 98652 or 98754
			if ((drawType[0] == DRAW_GUTSHOT && drawType[1] == DRAW_GUTSHOT)
					|| (drawType[1] == DRAW_GUTSHOT && drawType[0] == DRAW_GUTSHOT)) {
				drawType[0] = DRAW_GUTSHOT_GUTSHOT;
				drawCount = 1;
				return;
			}
			// AKQJ
			if ((drawType[0] == DRAW_STRAIGHT && drawType[1] == DRAW_FLUSH)
					|| (drawType[1] == DRAW_STRAIGHT && drawType[0] == DRAW_FLUSH)) {
				drawType[0] = DRAW_FLUSH_STRAIGHT;
				drawCount = 1;
				return;
			}
			Crash.log("WTF " + drawType[0] + " " + drawType[1]);
		}
		if (drawCount == 3) {
			if ((drawType[0] == DRAW_FLUSH && drawType[1] == DRAW_OESD && drawType[2] == DRAW_GUTSHOT)
					|| (drawType[0] == DRAW_FLUSH && drawType[2] == DRAW_OESD && drawType[1] == DRAW_GUTSHOT)
					|| (drawType[1] == DRAW_FLUSH && drawType[0] == DRAW_OESD && drawType[1] == DRAW_GUTSHOT)
					|| (drawType[1] == DRAW_FLUSH && drawType[1] == DRAW_OESD && drawType[0] == DRAW_GUTSHOT)
					|| (drawType[2] == DRAW_FLUSH && drawType[1] == DRAW_OESD && drawType[0] == DRAW_GUTSHOT)
					|| (drawType[2] == DRAW_FLUSH && drawType[0] == DRAW_OESD && drawType[1] == DRAW_GUTSHOT)) {
				drawType[0] = DRAW_FLUSH_OESD_GUTSHOT;// TODO
				drawCount = 1;
				return;
			}
			if ((drawType[0] == DRAW_FLUSH && drawType[1] == DRAW_STRAIGHT && drawType[2] == DRAW_GUTSHOT)
					|| (drawType[0] == DRAW_FLUSH && drawType[2] == DRAW_STRAIGHT && drawType[1] == DRAW_GUTSHOT)
					|| (drawType[1] == DRAW_FLUSH && drawType[0] == DRAW_STRAIGHT && drawType[1] == DRAW_GUTSHOT)
					|| (drawType[1] == DRAW_FLUSH && drawType[1] == DRAW_STRAIGHT && drawType[0] == DRAW_GUTSHOT)
					|| (drawType[2] == DRAW_FLUSH && drawType[1] == DRAW_STRAIGHT && drawType[0] == DRAW_GUTSHOT)
					|| (drawType[2] == DRAW_FLUSH && drawType[0] == DRAW_STRAIGHT && drawType[1] == DRAW_GUTSHOT)) {
				drawType[0] = DRAW_FLUSH_STRAIGHT_GUTSHOT; // TODO
				drawCount = 1;
				return;
			}
			if ((drawType[0] == DRAW_FLUSH && drawType[1] == DRAW_GUTSHOT && drawType[2] == DRAW_GUTSHOT)
					|| (drawType[0] == DRAW_FLUSH && drawType[2] == DRAW_GUTSHOT && drawType[1] == DRAW_GUTSHOT)
					|| (drawType[1] == DRAW_FLUSH && drawType[0] == DRAW_GUTSHOT && drawType[1] == DRAW_GUTSHOT)
					|| (drawType[1] == DRAW_FLUSH && drawType[1] == DRAW_GUTSHOT && drawType[0] == DRAW_GUTSHOT)
					|| (drawType[2] == DRAW_FLUSH && drawType[1] == DRAW_GUTSHOT && drawType[0] == DRAW_GUTSHOT)
					|| (drawType[2] == DRAW_FLUSH && drawType[0] == DRAW_GUTSHOT && drawType[1] == DRAW_GUTSHOT)) {
				drawType[0] = DRAW_FLUSH_GUTSHOT_GUTSHOT; // TODO
				drawCount = 1;
				return;
			}

			System.out.println("//both " + EvalData.players[seat].both[0] + " " + EvalData.players[seat].both[1] + " "
					+ EvalData.players[seat].both[2] + " " + EvalData.players[seat].both[3] + " "
					+ EvalData.players[seat].both[4] + " " + EvalData.players[seat].both[5] + " "
					+ EvalData.players[seat].both[6] + "        " + EvalData.players[seat].holeCard1 + " "
					+ EvalData.players[seat].holeCard2);
			Crash.log("WTF " + drawType[0] + " " + drawType[1] + " " + drawType[2]);
		}
	}

	/*- ************************************************************************************
	 * Copy both cards 1 through 5 into an instance of the Draw Class for one seat
	 * Straight or OESD  or gutshot draw 
	 * 	 *************************************************************************************/
	private void copyDrawConnected() {
		if (EvalData.street == FLOP) {
			drawCardsFlop[0] = connectedCards[0];
			drawCardsFlop[1] = connectedCards[1];
			drawCardsFlop[2] = connectedCards[2];
			drawCardsFlop[3] = connectedCards[3];
			drawCardsFlop[4] = connectedCards[4];
		} else if (EvalData.street == TURN) {
			drawCardsTurn[0] = connectedCards[0];
			drawCardsTurn[1] = connectedCards[1];
			drawCardsTurn[2] = connectedCards[2];
			drawCardsTurn[3] = connectedCards[3];
			drawCardsTurn[4] = connectedCards[4];
		}
	}

	/*- ************************************************************************************
	 * Copy both cards 1 through 5 into an instance of the Draw Class for one seat
	 * Straight or OESD  or gutshot draw 
	 * 	 *************************************************************************************/
	private void copyDrawSuited() {
		if (EvalData.street == FLOP) {
			drawCardsFlop[0] = connectedCards[0];
			drawCardsFlop[1] = connectedCards[1];
			drawCardsFlop[2] = connectedCards[2];
			drawCardsFlop[3] = connectedCards[3];
			drawCardsFlop[4] = connectedCards[4];
			SortCards.sortCardsCard(drawCardsFlop, 5);
		} else if (EvalData.street == TURN) {
			drawCardsTurn[0] = connectedCards[0];
			drawCardsTurn[1] = connectedCards[1];
			drawCardsTurn[2] = connectedCards[2];
			drawCardsTurn[3] = connectedCards[3];
			drawCardsTurn[4] = connectedCards[4];
			SortCards.sortCardsCard(drawCardsTurn, 5);
		}
	}

	/*- ************************************************************************************
	 * Copy both cards 1 through 5 into an instance of the Draw Class for one seat
	 * Straight or OESD  or gutshot draw 
	 * 	 *************************************************************************************/
	private void copyDrawPaired() {
		if (EvalData.street == FLOP) {
			drawCardsFlop[0] = pairedCards[0];
			drawCardsFlop[1] = pairedCards[1];
			drawCardsFlop[2] = pairedCards[2];
			drawCardsFlop[3] = pairedCards[3];
			drawCardsFlop[4] = pairedCards[4];
			SortCards.sortCardsCard(drawCardsFlop, 5);
		} else if (EvalData.street == TURN) {
			drawCardsTurn[0] = pairedCards[0];
			drawCardsTurn[1] = pairedCards[1];
			drawCardsTurn[2] = pairedCards[2];
			drawCardsTurn[3] = pairedCards[3];
			drawCardsTurn[4] = pairedCards[4];
			SortCards.sortCardsCard(drawCardsTurn, 5);
		}
	}

	/*- ************************************************************************************
	 * Copy both cards 1 through 5 into an instance of the Draw Class for one seat
	 * Called for made hands Straight 
	 * Arg0 - Array of cards 
	 *************************************************************************************/
	private void copyMade(Card[] card) {
		if (EvalData.street == FLOP) {
			madeCardsFlop[0] = card[0];
			madeCardsFlop[1] = card[1];
			madeCardsFlop[2] = card[2];
			madeCardsFlop[3] = card[3];
			madeCardsFlop[4] = card[4];
		} else if (EvalData.street == TURN) {
			madeCardsTurn[0] = card[0];
			madeCardsTurn[1] = card[1];
			madeCardsTurn[2] = card[2];
			madeCardsTurn[3] = card[3];
			madeCardsTurn[4] = card[4];
		} else if (EvalData.street == RIVER) {
			madeCardsRiver[0] = card[0];
			madeCardsRiver[1] = card[1];
			madeCardsRiver[2] = card[2];
			madeCardsRiver[3] = card[3];
			madeCardsRiver[4] = card[4];
		}
	}

	/*- ************************************************************************************
	 * This private helper method will complete filling in data for Made hands
	 * For all types of Made hands
	 * Set type of Made hand
	 * Copy cards to madeCardsFlop, madeCardsTurn or madeCardsRiver
	 ***************************************************************************************/
	void finishMade(int type) {
		if (EvalData.street == FLOP) {
			++BBB;
		}
		if (type == MADE_STRAIGHT) {
			copyMade(connectedCards);
		} else if (type == MADE_FLUSH || type == MADE_STRAIGHT_FLUSH || type == MADE_ROYAL_FLUSH) {
			copyMade(suitedCards);
		} else {
			copyMade(pairedCards);
		}
		if (EvalData.street == FLOP) {
			typeOfMadeFlop = type;
			if (EvalData.preflopSimulation) {
				++DrawMadeWonPercentages.typeArraySimMadeFlop[type];
			} else {
				++DrawMadeWonPercentages.typeArrayMadeFlop[type];
			}
			if (EvalData.freezeSeat1) {
				++DrawMadeWonPercentages.typeArrayFreezeSeat1MadeFlop[type];
			}
			if (EvalData.freezeSeat2) {
				++DrawMadeWonPercentages.typeArrayFreezeSeat2MadeFlop[type];
			}
			if (EvalData.freezeBoard) {
				++DrawMadeWonPercentages.typeArrayFreezeBoardMadeFlop[type];
			}
		} else if (EvalData.street == TURN) {
			typeOfMadeTurn = type;
			if (EvalData.preflopSimulation) {
				++DrawMadeWonPercentages.typeArraySimMadeTurn[type];
			} else {
				++DrawMadeWonPercentages.typeArrayMadeTurn[type];
			}
			if (EvalData.freezeSeat1) {
				++DrawMadeWonPercentages.typeArrayFreezeSeat1MadeTurn[type];
			}
			if (EvalData.freezeSeat2) {
				++DrawMadeWonPercentages.typeArrayFreezeSeat2MadeTurn[type];
			}
			if (EvalData.freezeBoard) {
				++DrawMadeWonPercentages.typeArrayFreezeBoardMadeTurn[type];
			}
		} else if (EvalData.street == RIVER) {
			typeOfMadeRiver = type;
			if (EvalData.preflopSimulation) {
				++DrawMadeWonPercentages.typeArraySimMadeRiver[type];
			} else {
				++DrawMadeWonPercentages.typeArrayMadeRiver[type];
			}
			if (EvalData.freezeSeat1) {
				++DrawMadeWonPercentages.typeArrayFreezeSeat1MadeRiver[type];
			}
			if (EvalData.freezeSeat2) {
				++DrawMadeWonPercentages.typeArrayFreezeSeat2MadeRiver[type];
			}
			if (EvalData.freezeBoard) {
				++DrawMadeWonPercentages.typeArrayFreezeBoardMadeRiver[type];
			}
		}
	}

	/*- ************************************************************************************
	 * This private helper method will complete filling in data for Made hands
	 * For all types of Made hands
	 * Set type of Made hand
	 * Copy cards to madeCardsFlop, madeCardsTurn or madeCardsRiver
	 ***************************************************************************************/
	void finishMadeStraightFlush(int type) {
		if (EvalData.street == FLOP) {
			++CCC;
		}
		if (EvalData.street == FLOP) {
			typeOfMadeFlop = type;
			if (EvalData.preflopSimulation) {
				++DrawMadeWonPercentages.typeArraySimMadeFlop[type];
			} else {
				++DrawMadeWonPercentages.typeArrayMadeFlop[type];
			}
			if (EvalData.freezeSeat1) {
				++DrawMadeWonPercentages.typeArrayFreezeSeat1MadeFlop[type];
			}
			if (EvalData.freezeSeat2) {
				++DrawMadeWonPercentages.typeArrayFreezeSeat2MadeFlop[type];
			}
			if (EvalData.freezeBoard) {
				++DrawMadeWonPercentages.typeArrayFreezeBoardMadeFlop[type];
			}
		} else if (EvalData.street == TURN) {
			typeOfMadeTurn = type;
			if (EvalData.preflopSimulation) {
				++DrawMadeWonPercentages.typeArraySimMadeTurn[type];
			} else {
				++DrawMadeWonPercentages.typeArrayMadeTurn[type];
			}
			if (EvalData.freezeSeat1) {
				++DrawMadeWonPercentages.typeArrayFreezeSeat1MadeTurn[type];
			}
			if (EvalData.freezeSeat2) {
				++DrawMadeWonPercentages.typeArrayFreezeSeat2MadeTurn[type];
			}
			if (EvalData.freezeBoard) {
				++DrawMadeWonPercentages.typeArrayFreezeBoardMadeTurn[type];
			}
		} else if (EvalData.street == RIVER) {
			typeOfMadeRiver = type;
			if (EvalData.preflopSimulation) {
				++DrawMadeWonPercentages.typeArraySimMadeRiver[type];
			} else {
				++DrawMadeWonPercentages.typeArrayMadeRiver[type];
			}
			if (EvalData.freezeSeat1) {
				++DrawMadeWonPercentages.typeArrayFreezeSeat1MadeRiver[type];
			}
			if (EvalData.freezeSeat2) {
				++DrawMadeWonPercentages.typeArrayFreezeSeat2MadeRiver[type];
			}
			if (EvalData.freezeBoard) {
				++DrawMadeWonPercentages.typeArrayFreezeBoardMadeRiver[type];
			}
		}
	}

	int AAA = 0;
	int BBB = 0;
	int CCC = 0;
	int DDD = 0;

	/*- ************************************************************************************
	 * This private helper method will complete filling in data for Draw hands
	 * For all types of Draw hands
	 * Set type of Made hand
	 * Copy cards to madeCardsFlop, madeCardsTurn or madeCardsRiver
	 ***************************************************************************************/
	private void finishDraw(int type) {
		if (EvalData.street == FLOP) {
			++DDD;
		}
		if (type == DRAW_NONE) {
			copyDrawPaired();
		} else if (drawWithFlush) {
			copyDrawSuited();
		} else {
			copyDrawConnected();
		}
		if (EvalData.street == FLOP) {
			typeOfDrawFlop = type;
			if (EvalData.preflopSimulation) {
				++DrawMadeWonPercentages.typeArraySimDrawFlop[type];
			} else {
				++DrawMadeWonPercentages.typeArrayDrawFlop[type];
			}
			if (EvalData.freezeSeat1) {
				++DrawMadeWonPercentages.typeArrayFreezeSeat1DrawFlop[type];
			}
			if (EvalData.freezeSeat2) {
				++DrawMadeWonPercentages.typeArrayFreezeSeat2DrawFlop[type];
			}
			if (EvalData.freezeBoard) {
				++DrawMadeWonPercentages.typeArrayFreezeBoardDrawFlop[type];
			}
		} else {
			typeOfMadeTurn = type;
			if (EvalData.preflopSimulation) {
				++DrawMadeWonPercentages.typeArraySimDrawTurn[type];
			} else {
				++DrawMadeWonPercentages.typeArrayDrawTurn[type];
			}
			if (EvalData.freezeSeat1) {
				++DrawMadeWonPercentages.typeArrayFreezeSeat1DrawTurn[type];
			}
			if (EvalData.freezeSeat2) {
				++DrawMadeWonPercentages.typeArrayFreezeSeat2DrawTurn[type];
			}
			if (EvalData.freezeBoard) {
				++DrawMadeWonPercentages.typeArrayFreezeBoardDrawTurn[type];
			}
		}
	}

	/*-**********************************************************************************************
	* Based on the MADE array. Outs are to make this hand or to make a draw.
	* MADE_ and DRAW_ values are used as indexes.
	*************************************************************************************************/
	int[] outsArrayFlop = new int[MADE_SIZE + DRAW_SIZE];
	int[] outsArrayTurn = new int[MADE_SIZE + DRAW_SIZE];
	int[] oddsArrayFlopToTurn = new int[MADE_SIZE + DRAW_SIZE];
	int[] oddsArrayFlopToRiver = new int[MADE_SIZE + DRAW_SIZE];
	int[] oddsArrayTurnToRiver = new int[MADE_SIZE + DRAW_SIZE];
	int[] oddsArrayTurn = new int[MADE_SIZE + DRAW_SIZE];

	private int outs = 0;
	private int outsWeak = 0;
	private int outsDraw = 0;
	private int outsMade = 0;

	/*-**********************************************************************************************
	* This method will calculate the number of out to improve each hand based on the analysis that
	* this Class did for the Flop.
	*************************************************************************************************/
	private void outsAndOddsFlop() {
		outsAndOddsFlopAndTurn(typeOfMadeFlop, typeOfDrawFlop);
		outsFlop = outs;
		outsWeakFlop = outsWeak;
		outsDrawFlop = outsDraw;
		outsMadeFlop = outsMade;
		percentageFTT = (double) outsFlop / (double) 47;
		percentageFTR = ((double) outsFlop / (double) 47) + ((double) outsFlop / (double) 46);
	}

	/*-**********************************************************************************************
	* This method will calculate the number of out to improve each hand based on the analysis that
	* this Class did for the Turn.
	*************************************************************************************************/
	private void outsAndOddsTurn() {
		outsAndOddsFlopAndTurn(typeOfMadeTurn, typeOfDrawTurn);
		outsFlop = outs;
		outsWeakFlop = outsWeak;
		outsDrawFlop = outsDraw;
		outsMadeFlop = outsMade;
		percentageTTR = (double) outsTurn / (double) 46;
	}

	/*-**********************************************************************************************
	* This method will calculate the number of out to improve each hand based on thre analysis that
	* this Class did for the Flop.
	*************************************************************************************************/
	private void outsAndOddsFlopAndTurn(int type1, int type2) {
		outs = 0;
		outsWeak = 0;
		outsDraw = 0;
		outsMade = 0;
		switch (type1) {
		case MADE_NONE -> {
			outs += 0;
			outsWeak += 0;
			outsMade += 0;
		}
		case MADE_BOARD_PAIR -> {
			outs += 0;
			outsWeak += 0;
			outsMade += 0;
		}
		// 78 KK8
		case MADE_PAIR_BELOW_BOARD_PAIR -> {
			outs += 2;
			outsWeak += 0;
			outsMade += 2;
		}
		case MADE_ACE_HIGH -> {
			outs += 3;
			outsWeak += 3;
			outsMade += 3;
		}
		case MADE_OVERCARDS -> {
			outs += 6;
			outsWeak += 0;
			outsMade += 6;
		}
		case MADE_POCKET_PAIR_BELOW_TP -> {
			outs += 2;
			outsWeak += 2;
			outsMade += 2;
		}
		case MADE_WEAK_PAIR -> {
			outs += 2;
			outsWeak += 2;
			outsMade += 2;
		}
		case MADE_BOTTOM_PAIR -> {
			outs += 2;
			outsWeak += 2;
			outsMade += 2;
		}
		case MADE_MIDDLE_PAIR -> {
			outs += 2;
			outsWeak += 2;
			outsMade += 2;
		}
		case MADE_TOP_PAIR -> {
			outs += 2;
		}
		case MADE_OVER_PAIR -> {
			outs += 2;
			outsMade += 2;
		}
		case MADE_PAIR_ABOVE_BOARD_PAIR -> {
			outs += 2 + 2;
			outsMade += 2 + 2;
		}
		case MADE_BOTTOM_TWO_PAIR -> {
			outs += 4;
			outsMade += 4;
		}
		case MADE_TOP_AND_BOTTOM_TWO_PAIR -> {
			outs += 4;
			outsMade += 4;
		}
		case MADE_TOP_TWO_PAIR -> {
			outs += 4;
			outsMade += 4;
		}
		case MADE_SET -> {
			outs += 1;
			outsMade += 4;
		}
		case MADE_FULL_HOUSE -> {
		}
		case MADE_FOUR_OF_A_KIND -> {
		}
		case MADE_STRAIGHT -> {
		}
		case MADE_FLUSH -> {
		}
		case MADE_STRAIGHT_FLUSH -> {
		}
		case MADE_ROYAL_FLUSH -> {
		}
		default -> {
			Crash.log("WTF " + type1 + " " + MADE_ST[type1] + " " + EvalData.street + " " + GameControl.gamesPlayed
					+ " " + GameControl.handsPlayed);
		}
		}

		switch (type2) {
		case DRAW_NONE -> {
			outs += 0;
			outsDraw += 0;
		}
		case DRAW_GUTSHOT -> {
			outs += 4;
			outsDraw += 4;
		}
		case DRAW_GUTSHOT_PAIR -> {
			outs += 4;
			outsDraw += 4;
		}
		case DRAW_GUTSHOT_GUTSHOT -> {
			outs += 8;
			outsDraw += 8;
		}
		// XXXX_X
		case DRAW_OESD_GUTSHOT -> {
			outs += 8;
			outsDraw += 8;
		}
		// AKQJ_X
		case DRAW_STRAIGHT_GUTSHOT -> {
			outs += 4;
			outsDraw += 4;
		}
		// FF_FF
		case DRAW_FLUSH_GUTSHOT -> {
			outs += 9 + 3;
			outsDraw += 9 + 3;
		}
		case DRAW_STRAIGHT -> {
			outs += 4;
			outsDraw += 4;
		}
		// AKQJJ
		case DRAW_STRAIGHT_PAIR -> {
			outs += 6;
			outsDraw += 6;
		}
		// AKQJJ
		case DRAW_FLUSH_STRAIGHT -> {
			outs += 4;
			outsDraw += 4;
		}
		case DRAW_OESD -> {
			outs += 8;
			outsDraw += 8;
		}
		// T9877
		case DRAW_OESD_PAIR -> {
			outs += 8 + 12;
			outsDraw += 8 + 12;
		}
		// Tc9c8c7c
		case DRAW_FLUSH_OESD -> {
			outs += 9 + 8 - 2;
			outsDraw += 9 + 8 - 2;
		}
		case DRAW_FLUSH -> {
			outs += 9;
			outsDraw += 9;
		}
		// 7c6c5c4c4d
		case DRAW_FLUSH_PAIR -> {
			outs += 9 + 2;
			outsDraw += 9 + 2;
		}
		case DRAW_FLUSH_OESD_GUTSHOT -> {
			outs += 9 + 4 + 4;
			outsDraw += 9 + 4 + 4;
		}
		case DRAW_FLUSH_STRAIGHT_GUTSHOT -> {
			outs += 9 + 4 + 4;
			outsDraw += 9 + 4 + 2;
		}
		case DRAW_FLUSH_GUTSHOT_GUTSHOT -> {
			outs += 9 + 4 + 4;
			outsDraw += 9 + 4 + 4;
		}
		default -> {
			Crash.log("WTF " + type2 + " " + DRAW_ST[type2] + " " + EvalData.street + " " + GameControl.gamesPlayed
					+ " " + GameControl.handsPlayed);
		}
		}
	}

	/*-  ******************************************************************************
	 * Update hand status
	 *  ****************************************************************************** */
	void updateHandStatus() {
		double per1 = 0.;
		double per2 = 0.;
		handStatusSB = new StringBuilder();
		handStatusSB.append(new StringBuilder().append("Seat  ").append(String.valueOf(seat + 1)).append(" , "));
		if (pair1NoHole && pair2NoHole) {
			handStatusSB.append(new StringBuilder().append("Both Pairs ").append(Card.CARD_STRING[pair1Value])
					.append(" and ").append(Card.CARD_STRING[pair2Value]).append(" NOT player pair, "));
		}
		if (pair1NoHole) {
			handStatusSB.append(new StringBuilder().append("Both Pair ").append(Card.CARD_STRING[pair1Value])
					.append(" NOT player pair, "));
		}

		if (outsFlop != 0) {
			per1 = (double) outsFlop / (double) 47;
			per2 = ((double) outsFlop / (double) 47) + ((double) outsFlop / (double) 46);
			handStatusSB.append("Flop outs, Draw = ").append(String.valueOf(outsFlop)).append(" Made = ")
					.append(String.valueOf(outsMadeFlop)).append(" Weak = ").append(String.valueOf(outsWeakFlop))
					.append(" Total = ").append(String.valueOf(outsFlop)).append(" Flop to Turn = ")
					.append(Format.formatPer(per1)).append(" Flop to River = ").append(Format.formatPer(per2))
					.append(", ");
		}
		if (outsTurn != 0) {
			per1 = (double) outsFlop / (double) 46;
			handStatusSB.append("Turn outs, Draw = ").append(String.valueOf(outsFlop)).append(" Made = ")
					.append(String.valueOf(outsMadeFlop)).append(" Weak = ").append(String.valueOf(outsWeakFlop))
					.append(" Turn to River = ").append(Format.formatPer(per1)).append(" Total = ")
					.append(String.valueOf(outsFlop)).append(", ");
		}
		if (fourOfKindNoHole) {
			handStatusSB.append("Four of a Kind not paired with  hole card, ");
		}
		if (set1NoHole) {
			handStatusSB.append("Set not paired with a hole card, ");
		}
		if (pair3NoHole) {
			handStatusSB.append("3 both pairs none paired with a hole card, ");
		} else if (pair2NoHole) {
			handStatusSB.append("2 both pairs none paired with a hole card, ");
		} else if (pair1NoHole) {
			handStatusSB.append("Pair not paired with a hole card, ");
		}
		if (pocketPair) {
			handStatusSB.append(
					new StringBuilder().append("Pocket pair ").append(Card.CARD_STRING[holeCard1.value]).append(", "));
		}
		if (pairPlusSmallerBoardPair) {
			handStatusSB.append(new StringBuilder().append("Pair plus Smaller board pair "));
		}
		if (pairPlusBiggerBoardPair) {
			handStatusSB.append(new StringBuilder().append("Pair plus Bigger board pair "));
		}
		if (pair2Value != -1) {
			handStatusSB.append(new StringBuilder().append("Second pair ").append(Card.CARDS_REVERSE[holeCard1.value])
					.append(" paired with board, "));
		}
		if (holeCard1PairedWithBoard) {
			handStatusSB.append(new StringBuilder().append("Hole card 1 ").append(Card.CARD_STRING[holeCard1.card])
					.append(" paired with board, "));
		}
		if (holeCard2PairedWithBoard) {
			handStatusSB.append(new StringBuilder().append("Hole card 2 ").append(Card.CARD_STRING[holeCard2.card])
					.append(" paired with board, "));
		}
		if (gap0Count != 0) {
			handStatusSB.append(
					new StringBuilder().append("Paired Count = ").append(String.valueOf(gap0Count)).append(", "));
		}
		if (gap1Count != 0) {
			handStatusSB.append(
					new StringBuilder().append("One gap count = ").append(String.valueOf(gap1Count)).append(", "));
		}
		if (gap2Count != 0) {
			handStatusSB.append(
					new StringBuilder().append("Two gap count = ").append(String.valueOf(gap2Count)).append(", "));
		}
		if (flush2) {
			handStatusSB.append(new StringBuilder().append("2 of Same Suit ").append(" , "));
		}
		if (flush3) {
			handStatusSB.append(new StringBuilder().append("3 of Same Suit ").append(" , "));
		}
		if (flush4) {
			handStatusSB.append(new StringBuilder().append("4 of Same Suit ").append(" , "));
		}
		if (flush5) {
			handStatusSB.append(new StringBuilder().append("Flush "));
		}
		if (seat != 5) {
			handStatusSB.append("\r\n");
		}
	}

	int printCount = 0;

	/*- ************************************************************************************************************
	  In Texas Hold'em poker, when you see the flop or the turn, you might have a made hand or a drawing hand.
	   Let's break down the approximate probabilities of completing your hand or having certain 
	   types of draws when you see the flop or the turn:
	
	On the Flop:
	
	a. No Draw or High Card Only: This varies depending on your hole cards and the flop. 
	If you have two unrelated hole cards and the flop doesn't pair any of your cards or give you a draw, 
	you essentially have "no draw" or just "high card".
	
	b. Straight Draw:
	
	Open-Ended Straight Draw (OESD): If you have four consecutive cards after the flop, the chances of 
	completing the straight by the river is about 31.5%.
	
	Gutshot (or Inside Straight Draw): If you need one specific card to complete a straight, your chance of 
	hitting it by the river is about 16.5%.
	
	c. Flush Draw: If you have a flush draw on the flop (i.e., you have 4 cards of the same suit), 
	the probability of completing the flush by the river is roughly 35%.
	
	On the Turn:
	
	a. No Draw: As with the flop, this will depend on your hole cards and the community cards.
	
	b. Straight Draw:
	
	Open-Ended Straight Draw (OESD): If you have an OESD after the turn, the chances of completing 
	the straight on the river is about 17% (or 1 in 6).
	
	Gutshot: If you have a gutshot after the turn, the chance of hitting your card on the river 
	is about 8.5% (or roughly 1 in 12).
	
	c. Flush Draw: If you have a flush draw after the turn, the probability of completing the flush 
	on the river is about 19% (or roughly 1 in 5).
	
	It's important to note that while these are general probabilities, poker strategy involves 
	considering other factors like pot odds, implied odds, and the tendencies of your opponents. 
	Being aware of these probabilities, however, is crucial for making informed decisions
	in various game situations.
	
	
	
	
	User
	I want the percentage of time that you will have a draw
	ChatGPT
	Ah, I see what you're asking now. You want to know the chances of being dealt a drawing hand 
	post-flop or post-turn. The exact probabilities can be complex to calculate on the spot, 
	but I can provide some rough estimates:
	
	1. On the Flop:
	
	a. Open-Ended Straight Draw (OESD): This occurs when you're one card away from 
	completing a straight from both ends. For example, if you have 7-8 and the flop 
	comes 5-6-10. The chance of flopping an OESD when holding two consecutive 
	cards (like 78 or T9) is roughly 10.5%.
	
	b. Gutshot Straight Draw: This is when you're one card away from completing a
	straight, but not from both ends. Like if you have 7-9 and the flop is 5-6-10. I
	f you're holding a two-gap hand (like 79, T8, etc.), the chance of flopping a 
	gutshot is roughly 17%.
	
	c. Flush Draw: If you're holding two suited cards, the chance of flopping a flush draw 
	(i.e., flopping 3 more cards of your suit) is approximately 11%.
	
	2. On the Turn:
	
	If you're heading to the turn without a made hand or a draw, then you're relying on the turn 
	card to give you some form of drawing hand. The chances here are more varied due to the 
	multitude of possible starting hands and flops. However:
	
	a. OESD: If you had something like a gutshot on the flop, the turn can sometimes upgrade
	your hand to an OESD. However, the probability is scenario-specific and would require 
	a more specific hand and board breakdown.
	
	b. Gutshot: Similarly, you might have two overcards on the flop, and the turn could give you
	a gutshot. Again, the probability is hand and board-specific.
	
	c. Flush Draw: If you had two suited cards and one of your suit on the flop, the turn could 
	bring a second card of your suit, giving you a flush draw. The chance of this specific scenario 
	happening is 19.15%.
	
	It's essential to consider these are rough estimates. Actual percentages can vary based on the
	specifics of your hole cards and the community cards. If you play often, it's good to 
	familiarize yourself with common scenarios and their probabilities to make better-informed 
	decisions during the game.
	
	
	
	
	User
	without considering what you are holding
	ChatGPT
	
	2 / 2
	
	Ah, if you're asking about the percentage chance of seeing a certain type of draw on the board 
	regardless of your hole cards, here's a basic breakdown:
	
	1. On the Flop:
	
	a. Open-Ended Straight Draw (OESD): This would mean the flop itself has consecutive cards, 
	like 5-6-7 or 9-T-J. There are various combinations to consider, but roughly the chance is about 3.45%.
	
	b. Gutshot Straight Draw: This is harder to determine because it's dependent on the rank of the cards. 
	For example, a flop like 5-7-9 offers a gutshot straight draw. The rough chance of a gutshot 
	appearing on the flop is around 7%.
	
	c. Flush Draw: The chance that the flop comes down all one suit (thus creating a potential flush 
	draw for anyone holding cards of that suit) is about 0.84%.
	
	2. On the Turn:
	
	a. Adding to OESD: If there wasn't an OESD on the flop, the turn can provide one. For instance, 
	a flop of 5-6-Q can turn into an OESD with a 7 or 4 on the turn. The specific probability here is 
	situational but is relatively low.
	
	b. Adding to Gutshot: Similarly, if there wasn’t a gutshot on the flop, the turn could make one. 
	Again, the exact probability is situational.
	
	c. Adding to Flush Draw: If two cards of the same suit were on the flop, the turn has a 19.15% chance 
	of being the same suit, offering a flush draw.
	
	These are general percentages, and they can be affected by various factors such as deck 
	composition, burn cards, and more.
		
	************************************************************************************************************ */

}
