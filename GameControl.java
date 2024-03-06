package holdemevaluator;

import java.math.BigDecimal;

public class GameControl implements Constants {
	/*- ****************************************************************************
	* This Class will remain only partially complete.
	* In this project we will not do a Holdem simulator, only as much as
	* is required to get good integrity in our results.
	* For now that is limited to Preflop play and only by using HandRange values
	* to determine if a player should fold, open, call, raise, raise all-in, or call all-in.
	*
	* If anyone is interested this can easily be extended to cover a full game.
	* The full version is currently being used in the game project which does
	* include a full and very sophisticated simulator and much much more.
	* Contact me if you are interested.
	*
	* What this Class does do if to play a very  limited preflop strategy.
	*		Money is taken from the stacks of the blinds and put in the pot.
	*		UTG starts off and decides using a HandRange for the UTG whether or
	*		not to fold or limp or open.
	*		Play decisions are handled in an instance of the Player Class.
	*		This process continues around the table until either the pot is right
	*		or all but one player has folded.
	*		Control is then returned to the Class that called us. In this project
	*		that is the Evaluate Class.
	*		After each hand the positions are rotated.
	* 
	* @author PEAK_
	*******************************************************************************/
	static Timer timer1 = new Timer();
	private static int seat = 0;
	private static final boolean rangesDone = false;
	private static final PlayerPreflop[] players = new PlayerPreflop[PLAYERS];

	private static int nextCheck = 0;
	private static int numToPlay = 0;

	private static int foldCountTotal = 0;
	private static int foldCountReset = 0;
	private static int retry = 0;
	private static int retryMax = 0;

	/*-  *********************************************************************************** 
	 * 
	************************************************************************************** */
	static int gamesPlayed = 0; // 2 to 6 players
	static int handsPlayed = 0; // players ( gamesPlayed * number of players )
	static boolean streetComplete = false;
	static boolean boardComplete = false;
	static int betType = 0;
	static int orbit = 0;
	static boolean betCalled = false;
	static int foldCount = 0;
	static int allinCount = 0;
	static int limpCount = 0;

	/*-  *********************************************************************************** 
	 * For simulation modes
	 * The seat number is the index into positions and relativePositions 
	 * positions is an array of SB BB UTG MP CO BU
	  ************************************************************************************** */
	static BigDecimal potBD = zeroBD;
	static BigDecimal mainPotBD = zeroBD;
	static BigDecimal betToMeBD = zeroBD;
	static BigDecimal betNowBD = zeroBD;

	/*-  *********************************************************************************** 
	 * For simulation  
	 * The seat number is the index into positions and relativePositions 
	 * positions is an array of SB BB UTG MP CO BU
	  ************************************************************************************** */
	static BigDecimal[] stackBD = { new BigDecimal(200), new BigDecimal(200), new BigDecimal(200), new BigDecimal(200),
			new BigDecimal(200), new BigDecimal(200) };

	static BigDecimal[] playerMainPotBD = { new BigDecimal(0), new BigDecimal(0), new BigDecimal(0), new BigDecimal(0),
			new BigDecimal(0), new BigDecimal(0) };

	static BigDecimal[] moneyInBD = { zeroBD, zeroBD, zeroBD, zeroBD, zeroBD, zeroBD };

	static BigDecimal[] stackPreflopBD = { new BigDecimal(200), new BigDecimal(200), new BigDecimal(200),
			new BigDecimal(200), new BigDecimal(200), new BigDecimal(200) };

	static boolean[] playerFolded = { false, false, false, false, false, false };
	static boolean[] playerAllin = { false, false, false, false, false, false };
	static boolean[] playerWon = { false, false, false, false, false, false };
	static boolean[] playerFoldedPreflop = { false, false, false, false, false, false };
	static boolean[] playerLimpedPreflop = { false, false, false, false, false, false };
	static boolean[] playerFoldedFlop = { false, false, false, false, false, false };
	static boolean[] playerFoldedTurn = { false, false, false, false, false, false };
	static boolean[] playerFoldedRiver = { false, false, false, false, false, false };
	static boolean[] playerWonShowdown = { false, false, false, false, false, false };

	/*-  *********************************************************************************** 
	 * Positions - rotate each hand - value is seat number
	************************************************************************************** */
	static int[] relativePosition = new int[PLAYERS];
	static int seatSB = LAST_SEAT; // Rotating Small Blind seat
	static int seatBB = 0;
	static int seatUTG = 1;
	static int seatMP = 2;
	static int seatCO = 3;
	static int seatBU = 4;
	static int[] seatPos = { seatSB, seatBB, seatUTG, seatMP, seatCO, seatBU }; // Seat positions

	/*-  **************************************************************************
	* Data used for Showdown 
	* This data is in arrays, one for each player.
	***************************************************************************** */
	static int winnerShowdown = -1; // Winner seat number
	static int[] showdownValue = new int[PLAYERS]; // Relative value of hand
	static int[] showdownRank = { -1, -1, -1, -1, -1, -1 };
	static int[] showdownHand = { -1, -1, -1, -1, -1, -1 };

	/*-  **************************************************************************
	* Data used for Showdown 
	* This data is in arrays, one for each player.
	***************************************************************************** */
	static int maxValue = -1; // Showdown
	static int winnerSeat = -1;
	static boolean showdown = false;
	static boolean winner = false;
	static int kicker1 = -1;
	static int kicker2 = -1;
	static int kicker3 = -1;
	static int kicker4 = -1;
	static int kicker5 = -1;

	/*-  **************************************************************************
	 * Data used during  Showdown 
	 ***************************************************************************** */
	static int highCard1 = -1;
	static int highCard2 = -1;
	static int suit = -1;

	/*- ******************************************************************************
	 * Constructor.
	  *******************************************************************************/
	GameControl() {
		createPlayabilityArrays();
	}

	/*-  ******************************************************************************
	*  Equilab Equity vs. 5 random hands - Playability  
	*  Constructed from playabilityArray.
	*  
	*  Array matches drawAndMadeArray but values are playability value, index 0 is AA 169, index 1 AKs  4
	*  playabilityArrayNumeric
	*  Values are 1 - 169, 169 being AA the most playable
	* playabilityArrayNumeric is indexed by handIndex. 
	* 
	* playabilityArrayNumeric
	* AA is 169, KK is 168, AK is 165,  is 272o is 1 (Easier to understand )
	* Create  playabilityArrayOrder 
	* 
	* playabilityArrayOrder 
	* AA is 0, KK is 1, AKs is 4, TT is 5, 72o is 168.   the reverse of playabilityArrayNumeric 
	* The array index into playabilityArray a String array of hands in playability order. 
	* 
		*  Array matches drawAndMadeArray but values are index into 
	*  Values are index into playabilityArray, an array of String
	 ****************************************************************************** */
	static final int[] playabilityArrayNumeric = new int[HANDS];

	static final int[] playabilityArrayOrder = new int[HANDS];

	/*-  ****************************************************************************
	* Create playabilityArrayNumeric 
	* The array will contain values from 1 - 169
	* 
	* playabilityArrayNumeric
	* AA is 169, KK is 168, AK is 165,  is 272o is 1 (Easier to understand )
	* Create  playabilityArrayOrder 
	* 
	* playabilityArrayOrder
	* AA is 0, KK is 1, AKs is 4, TT is 5, 72o is 168.   the reverse of playabilityArrayNumeric 
	* The array index into playabilityArray a String array of hands in playability order.
	 ****************************************************************************** */
	void createPlayabilityArrays() {
		int k = 0;
		for (int i = 0; i < HANDS; ++i) {
			k = getHandStringToIndex(HandRange.playabilityArray[i]);
			playabilityArrayNumeric[k] = 169 - k;
			playabilityArrayOrder[i] = k;
		}
	}

	/*-  ****************************************************************************
	 Find hand in handsArray and return index Arg0 - hand in string format.
	  *****************************************************************************/
	static int getHandStringToIndex(String hand) {
		for (var I = 0; I < HANDS; ++I) {
			if (HandRange.handsArray[I].equals(hand)) {
				return I;
			}
		}
		Logger.log("ERROR HandRange handStringToIndex() Invalid hand " + hand);
		return -1;
	}

	/*- ************************************************************************
	Get playabilityNumeric value 
	****************************************************************************/
	int getPlayabilityNumeric(int index) {
		return playabilityArrayNumeric[index];
	}

	/*-  *************************************************************************
	 Get playabilityOrder value 
	 *************************************************************************** */
	int getPlayabilityOrder(int index) {
		return playabilityArrayOrder[index];
	}

	/*-  *********************************************************************************** 
	 * In early position or when facing tighter opponents, you may opt for a larger raise size 
	 * (3.5-4 big blinds) to discourage callers and build a bigger pot with your strong hands.
	 * In later position or when playing against looser opponents, you can use a smaller 
	 * sizing (2.5-3 big blinds) to give yourself better pot odds and allow for more flexibility postflop.
	 * For example, if the initial raiser opens to $6 (3 big blinds), a standard 3-bet size 
	 * would be between $18 to $24. If you're in position, you can lean toward the smaller 
	 * end of that range, while out of position, you might opt for a larger sizing to 
	 * discourage callers and take control of the pot.
	 * 
	 * In a 6-max No-Limit $1/$2 game, a typical 4-bet sizing is between 2 to 2.5 times the size 
	 * of the 3-bet. However, the exact sizing can vary depending on factors such as your position,
	 * your opponents' tendencies, and the size of the 3-bet.
	 *
	 * For example, let's say the initial raiser (2-bet) opens to $6 (3 big blinds), and another player 
	 * 3-bets to $18. A standard 4-bet sizing would be between $36 to $45.
	 *
	 * When you're in position, you might opt for a smaller 4-bet size to give yourself better pot odds 
	 * and the ability to exert more postflop pressure on your opponent. When you're out of position, 
	 * a larger 4-bet size can help to discourage your opponent from calling and take down the pot 
	 * preflop or give you more control of the pot if they do call.
	 * 
	 * In No-Limit Texas Hold'em, the size of your bets on the flop is an important factor in dictating 
	 * the pace of the hand and exerting pressure on your opponents. Here are some general guidelines for 
	 * bet sizes on the flop:
	 *
	 * Standard sizing: A common bet size on the flop is between 50% and 75% of the pot. 
	 * This sizing allows you to build the pot with your strong hands, while also putting pressure on 
	 * your opponents with weaker holdings. For example, if the pot is $20, a standard bet size would be 
	 * between $10 and $15.
	 *
	 * Position: Your position at the table influences your bet sizing. When you're in position 
	* (acting after your opponent), you can use smaller bet sizes to take advantage of your informational edge.
	 * When out of position, you might choose larger bet sizes to discourage your opponent from floating 
	 * with weaker hands or to protect your strong hands.
	 *
	 * Board texture: The texture of the flop is an important consideration when determining your bet size.
	 * On dry, unconnected flops (e.g., K-7-2 rainbow), you can use smaller bet sizes 
	 * (around 1/3 to 1/2 of the pot) because it's less likely that your opponent has connected with the board.
	 * On wet, coordinated flops (e.g., Q-J-10 with two suited cards), larger bet sizes 
	 * (around 2/3 to full pot) are more appropriate to charge your opponents for drawing to better hands.
	 *
	 * Opponent tendencies: Adjust your bet sizing based on your opponents' playing styles.
	 * Against tighter opponents, smaller bet sizes may be enough to take down the pot, 
	 * as they are likely to fold weaker hands. Against looser or more aggressive opponents,
	 * larger bet sizes can help protect your strong hands and put pressure on their weaker holdings.
	 *
	 * Stack sizes: Consider the effective stack sizes when determining your bet size. 
	 * With deeper stacks, you may want to use larger bet sizes to build a bigger pot for 
	 * when you have a strong hand. When you or your opponent have smaller stacks, 
	 * smaller bet sizes can help you control the pot and prevent committing too many 
	 * chips with a marginal hand.
	 *
	 * These guidelines provide a starting point for determining your bet sizes on the flop,
	 *  but keep in mind that poker is a dynamic game that requires constant adjustments
	 * based on various factors, including your opponents' tendencies and your overall game plan.
	 ************************************************************************************** */
	static final BigDecimal SBBetBD = new BigDecimal(1);
	static final BigDecimal BBBetBD = new BigDecimal(2);
	static final BigDecimal preflopBetMultiplyerOpenBD = new BigDecimal(3); // Times BB + 1 each limp
	static final BigDecimal preflopBetMultiplyer3BetBD = new BigDecimal(3); // Times Open 2 Bet
	static final BigDecimal preflopBetMultiplyer4BetBD = new BigDecimal(2.5); // Times 3 Bet
	static final BigDecimal postFlopBbetMultiplierBD = new BigDecimal(.5); // Times pot size
	static final BigDecimal buyinBD = new BigDecimal(200);
	static final BigDecimal rebuyBD = new BigDecimal(100);
	static final BigDecimal maxStackBD = new BigDecimal(500);

	/*- ****************************************************************************
	 *Hand ranges contain data from external files.
	 *Originally created for Hold'em, a full function game simyulator.
	 *Ranges are pretty accurate for all of the player types.
	 *Not all ranges are in current use, just here for future expansion possibility.
	 **************************************************************************** */
	private static HandRangePlayability hero = new HandRangePlayability();
	private static HandRangePlayability average = new HandRangePlayability();
	private static HandRangePlayability fish = new HandRangePlayability();
	private static HandRangePlayability nit = new HandRangePlayability();
	private static HandRangePlayability lag = new HandRangePlayability();
	private static HandRangePlayability tag = new HandRangePlayability();
	private static HandRangePlayability shark = new HandRangePlayability();

	/*- ****************************************************************************
	 * Creates instances of Player for each seat.
	 * One time initialization
	 * Instances are customized for current seat :
	 **************************************************************************** */
	private static void createPlayersPreflop() {
		players[0] = new PlayerPreflop(0, hero);
		EvalData.playerType[0] = HERO;
		players[1] = new PlayerPreflop(1, hero);
		EvalData.playerType[1] = HERO;
		players[2] = new PlayerPreflop(2, nit);
		EvalData.playerType[2] = NIT;
		players[3] = new PlayerPreflop(3, fish);
		EvalData.playerType[3] = FISH;
		players[4] = new PlayerPreflop(4, average);
		EvalData.playerType[4] = AVERAGE;
		players[5] = new PlayerPreflop(5, average);
		EvalData.playerType[5] = AVERAGE;
	}

	/*- ****************************************************************************
	 * Initialize ranges
	 * Range file is read from disk for a player type
	 * One time initialization
	 **************************************************************************** */
	private static void createRanges() {
		hero = hero.readFromFile(EvalData.applicationDirectory + "hero-HandRangePlayability.ser");
		average = average.readFromFile(EvalData.applicationDirectory + "average-HandRangePlayability.ser");
		fish = fish.readFromFile(EvalData.applicationDirectory + "fish-HandRangePlayability.ser");
		nit = nit.readFromFile(EvalData.applicationDirectory + "nit-HandRangePlayability.ser");
		lag = lag.readFromFile(EvalData.applicationDirectory + "lag-HandRangePlayability.ser");
		tag = tag.readFromFile(EvalData.applicationDirectory + "tag-HandRangePlayability.ser");
		shark = tag.readFromFile(EvalData.applicationDirectory + "shark-HandRangePlayability.ser");

	}

	/*- ****************************************************************************
	*  Return uncalled bet
	 **************************************************************************** */
	static void returnUncalledBet() {
		var s = -1;
		for (var i = 0; i < PLAYERS; i++) {
			if (!playerFolded[i]) {
				s = i;
				break;
			}
		}
		moneyInBD[s] = moneyInBD[s].subtract(betNowBD);
		stackBD[s] = stackBD[s].add(betNowBD);
		potBD = potBD.subtract(betNowBD);
		HandHistory.returnedToBD[0][s][orbit] = betNowBD;
		betCalled = true;
		betNowBD = zeroBD;
	}

	/*- ****************************************************************************
	* This method will do Preflop simulation with data collection. Multiple hands.
	* Arg0 is number of hands to play.
	* We handle shuffling deck and dealing cards, and player rotation.
	**************************************************************************** */
	static void simulationDataCollection(int num) {
		numToPlay = num;
		nextCheck = 10000;
		initializeForNewGame();
		HandHistory.initialize();
		if (!rangesDone) {
			createRanges();
			createPlayersPreflop();
		}
		if (numToPlay < 1000) {
			HandHistory.createHH();
		}
		gamesPlayed = 0;
		handsPlayed = 0;
		timer1.resetTimer();
		timer1.startTimer();
		while (++gamesPlayed <= numToPlay) {
			checkin();
			if (!preflopSimulation()) {
				return;
			}

			// TODO

			if (numToPlay < 10000) {
				HandHistory.closeHH();
			}
		}
		IndexArrayUpdate.updateSummary();
		String st = "";
		st += "Final games played  " + gamesPlayed + " hands Played " + gamesPlayed + " \t\t\t "
				+ timer1.getElapsedTimeSecondsString() + " Seconds " + timer1.getElapsedTimeMIliString()
				+ " Miliseconds  ";
		System.out.println(st);
	}

	/*- ****************************************************************************
	* This method will 
	* Called by GUI when the MultipleHands option is selected.
	* Arg0 - Number of hands to play
	* Arg1 - Streets to play. FLOP, TURN, RIVER, ALL_STREETS
	**************************************************************************** */
	static void playMultipleHandsAllRandom(int num, int street) {
		numToPlay = num;
		setup();
		while (++gamesPlayed <= numToPlay) {
			Deck.shuffle();
			Dealer.dealAllHoleCards();
			if (EvalData.preflopBothSimNoSim) {
				playBothSimNoSim(street);
			} else {
				if (EvalData.preflopSimulation) {
					if (!preflopSimulation()) {
						return;
					}
				}
				evaluate(street);
			}
			checkin();
		}
		finish(street);
	}

	/*- ****************************************************************************
	* This method will 
	* Called by GUI when the MultipleHands option is selected.
	* Preflop simulation is always done.
	* Arg0 - Number of games to play
	* Arg2 - Streets to play. FLOP, TURN, RIVER, ALL_STREETS
	**************************************************************************** */
	static void playMultipleHandsFreezeBoard(int num, int street) {
		numToPlay = num;
		setup();
		while (++gamesPlayed <= numToPlay) {
			Deck.shuffle();
			Dealer.dealAllHoleCards();
			if (EvalData.preflopBothSimNoSim) {
				setFrozenBoard();
				playBothSimNoSim(street);
			} else {
				if (EvalData.preflopSimulation) {
					if (!preflopSimulation()) {
						return;
					}
				}
				setFrozenBoard();
				evaluate(street);
			}
			checkin();
		}
		finish(street);
		System.out.println("//both " + EvalData.players[seat].both[0] + " " + EvalData.players[seat].both[1] + " "
				+ EvalData.players[seat].both[2] + " " + EvalData.players[seat].both[3] + " "
				+ EvalData.players[seat].both[4] + " " + EvalData.players[seat].both[5] + " "
				+ EvalData.players[seat].both[6] + "        " + EvalData.players[seat].holeCard1 + " "
				+ EvalData.players[seat].holeCard2);
	}

	/*- ****************************************************************************
	* This method will 
	* Called by GUI when the MultipleHands option is selected.
	* There is no preflop simulation.
	* Arg0 - Number of hands toplay
	* Arg1 - Streets to play. FLOP, TURN, RIVER, ALL_STREETS
	**************************************************************************** */
	static void playMultipleHandsFreezeSeat1and2(int num, int street) {
		numToPlay = num;
		setup();
		while (++gamesPlayed <= numToPlay) {
			frozen();
			if (EvalData.preflopBothSimNoSim) {
				playBothSimNoSim(street);
			} else {
				if (EvalData.preflopSimulation) {
					if (!preflopSimulation()) {
						return;
					}
				}
				if (EvalData.freezeSeat1 || EvalData.freezeSeat2) {
					frozen();
				}
				evaluate(street);
			}
			checkin();
		}
		finish(street);
		// both Ah Kh Th 8h 7c 6h 5h Ah Kh
		System.out.println("//both " + EvalData.players[seat].both[0] + " " + EvalData.players[seat].both[1] + " "
				+ EvalData.players[seat].both[2] + " " + EvalData.players[seat].both[3] + " "
				+ EvalData.players[seat].both[4] + " " + EvalData.players[seat].both[5] + " "
				+ EvalData.players[seat].both[6] + "        " + EvalData.players[seat].holeCard1 + " "
				+ EvalData.players[seat].holeCard2);
	}

	/*-**********************************************************************************************
	* This method is a helper with setup code common to multiple methods
	*************************************************************************************************/
	private static void setup() {
		initializeForNewGame();
		retry = 0;
		retryMax = 0;
		foldCountTotal = 0;
		nextCheck = 20000;
		gamesPlayed = 0;
		handsPlayed = 0;
		timer1.resetTimer();
		timer1.startTimer();

		if (!rangesDone) {
			EvalData.street = PREFLOP;
			createRanges();
			createPlayersPreflop();
		}
		if (EvalData.hh) {
			HandHistory.initialize();
		}
		if (EvalData.manyHands) {
			DrawMadeWonPercentages.initializeAllArrays();
		}
	}

	/*-**********************************************************************************************
	* This method is a helper with setup code common to multiple methods
	*************************************************************************************************/
	private static void checkin() {
		if (gamesPlayed > nextCheck) {
			String st = "";
			st += "Games played  " + gamesPlayed + " hands Played " + gamesPlayed + " \t\t\t "
					+ timer1.getElapsedTimeSecondsString() + " Seconds " + timer1.getElapsedTimeMIliString()
					+ " Miliseconds  ";
			System.out.println(st);
			if (GUIAnalyzeIndexArrays.checkExit(gamesPlayed)) {
				return;
			}
			nextCheck += 50000;
		}
	}

	/*-**********************************************************************************************
	* This method is a helper with setup code common to multiple methods
	*************************************************************************************************/
	private static void finish(int street) {
		String st = "";
		st += "Final games played  " + gamesPlayed + " hands Played " + gamesPlayed + " \t\t\t "
				+ timer1.getElapsedTimeSecondsString() + " Seconds " + timer1.getElapsedTimeMIliString()
				+ " Miliseconds  ";
		System.out.println(st);
		GUI.showElapsedTime(st);
		double d = (double) retry / (double) numToPlay;
		double d2 = (double) foldCountTotal / (double) numToPlay;
		double d3 = (double) foldCountReset / (double) numToPlay;
		// Retry 1361 average retry = 1.361 max retry 12 average foldCount 3.895
		// foldCount reset 0 reset average 0.0 3895 0
		System.out.println("//Retry " + retry + " " + "  average retry = " + d + " max retry " + retryMax
				+ " average foldCount " + d2 + " foldCount reset " + foldCountReset + " reset average " + d3);

		IndexArrayUpdate.updateSummary();
		if (street == RIVER || street == ALL_STREETS) {
			Showdown.showdown();
		}

		for (int i = 0; i < PLAYERS; i++) {
			if (EvalData.manyHands) {
				if (!playerFolded[i])
					DrawMadeWonPercentages.addValueArray(i);
			}
		}
	}

	/*-**********************************************************************************************
	* This method is a helper with setup code common to multiple methods
	*************************************************************************************************/
	private static void evaluate(int street) {
		if (street == FLOP || street == ALL_STREETS) {
			if (!EvalData.freezeBoard) {
				Dealer.dealFlop();
			}
			for (int i = 0; i < 6; i++) {
				if (!playerFolded[i]) {
					++handsPlayed;
					EvalData.players[i].analyzeFlop();
				}
			}
		}
		// Turn
		if (street == TURN || street == ALL_STREETS) {
			if (!EvalData.freezeBoard) {
				Dealer.dealTurnCard();
			}
			for (int i = 0; i < 6; i++) {
				if (!playerFolded[i]) {
					EvalData.players[i].analyzeTurn();
				}
			}
		}
		// River
		if (street == RIVER || street == ALL_STREETS) {
			if (!EvalData.freezeBoard) {
				Dealer.dealRiverCard();
			}
			for (int i = 0; i < 6; i++) {
				if (!playerFolded[i]) {
					EvalData.players[i].analyzeRiver();
					Showdown.showdownValue(i);
				}
			}
			Showdown.showdown();
		}
	}

	/*-**********************************************************************************************
	* This method is a helper with setup code common to multiple methods.
	* Used for the case of EvalData.preflopBothSimNoSim where we will do a comparison
	* of playing a hand twice, once without preflop simulation and once again with preflop 
	* simulation using all of the same cards.
	*************************************************************************************************/
	private static void playBothSimNoSim(int street) {
		boolean save = EvalData.preflopSimulation;
		// preflop simulation - Result will be some hands skipped because of retry
		EvalData.preflopSimulation = true;
		if (!preflopSimulation()) {
			return;
		}
		// Play with simulation
		if (street == FLOP || street == ALL_STREETS) {
			if (!EvalData.freezeBoard) {
				Dealer.dealFlop();
			}
			for (int i = 0; i < 6; i++) {
				if (!playerFolded[i]) {
					++handsPlayed;
					EvalData.players[i].analyzeFlop();
				}
			}
		}
		// Turn
		if (street == TURN || street == ALL_STREETS) {
			if (!EvalData.freezeBoard) {
				Dealer.dealTurnCard();
			}
			for (int i = 0; i < 6; i++) {
				if (!playerFolded[i]) {
					EvalData.players[i].analyzeTurn();
				}
			}
		}
		// River
		if (street == RIVER || street == ALL_STREETS) {
			if (!EvalData.freezeBoard) {
				Dealer.dealRiverCard();
			}
			for (int i = 0; i < 6; i++) {
				if (!playerFolded[i]) {
					EvalData.players[i].analyzeRiver();
					Showdown.showdownValue(i);
				}
			}
			Showdown.showdown();
		}

		for (int i = 0; i < PLAYERS; i++) {
			playerFolded[i] = false;
			playerFoldedPreflop[i] = false;
			playerAllin[i] = false;
		}

		EvalData.preflopSimulation = false;
		if (EvalData.freezeSeat1 || EvalData.freezeSeat2) {
			frozen();
		}

		Board.doFlop();
		if (street == FLOP || street == ALL_STREETS) {
			for (int i = 0; i < 6; i++) {
				if (!playerFolded[i]) {
					++handsPlayed;
					EvalData.players[i].analyzeFlop();
				}
			}
		}
		// Turn
		Board.doTurn();
		if (street == TURN || street == ALL_STREETS) {
			for (int i = 0; i < 6; i++) {
				if (!playerFolded[i]) {
					EvalData.players[i].analyzeTurn();
				}
			}
		}
		// River
		Board.doRiver();
		if (street == RIVER || street == ALL_STREETS) {
			for (int i = 0; i < 6; i++) {
				if (!playerFolded[i]) {
					EvalData.players[i].analyzeRiver();
					Showdown.showdownValue(i);
				}
			}
			Showdown.showdown();
		}
		EvalData.preflopSimulation = save;
	}

	/*- ****************************************************************************
	* This method handles Preflop simulation.
	* It will continue trying until there are n players that have not folded at 
	* the end of preflop play.
	* Returns false if error
	**************************************************************************** */
	private static boolean preflopSimulation() {
		int count = 0;
		boolean done = false;
		int foldLimit = 5;
		int retryLimit = 100;
		if (EvalData.hh) {
			hhData();
		}
		newGameSimulation();
		if (EvalData.freezeSeat1 || EvalData.freezeSeat2) {
			retryLimit = 100;
		}
		while (!done) {
			if (!playPreflop()) {
				Crash.log("WTF " + 0 + " " + 0 + " " + EvalData.street + " " + gamesPlayed + " " + handsPlayed);
				return false; // ERROR
			}
			uncalledBet();
			// If every one but one last player is winner
			if (foldCount == foldLimit) {
				for (var i = 0; i < PLAYERS; i++) {
					if (!playerFolded[i]) {
						HandHistory.winnerCollectedBD[i] = potBD;
						HandHistory.winBD[0][i][orbit] = potBD;
						playerWon[i] = true;
						break;
					}
				}
			}
			if (EvalData.hh) {
				HandHistory.createHH();
			}

			if (foldCount < foldLimit) {
				done = true;
			} else {
				if (EvalData.hh) {
					hhData();
				}
				frozen();
				++count;
				++retry;
				if (retryMax < count) {
					retryMax = count;
				}
				if (EvalData.freezeSeat1 || EvalData.freezeSeat2) {
					if (count > 5) {
						foldLimit = 6;
						++foldCountReset;
					}
				} else {
					if (count > 30) {
						foldLimit = 6;
						++foldCountReset;
					}
				}

				if (count > retryLimit) {
					Crash.log(new StringBuilder().append("//Unable to get 2 players for Flop ").append(count)
							.append(" ").append(foldCount).toString());
					return false;
				}
			}
		}
		foldCountTotal += foldCount;
		return true;
	}

	/*- ****************************************************************************
	* Just do the Flop for one seat
	**************************************************************************** */
	static void evaluateFlopOneSeat(int seat) {
		EvalData.players[seat].analyzeFlop();
		if (EvalData.manyHands) {
			IndexArrayUpdate.updateFlop();
		}
	}

	/*- ****************************************************************************
	* Just do the Turn for one seat
	**************************************************************************** */
	static void evaluateTurnOneSeat(int seat) {
		EvalData.players[seat].analyzeTurn();
		if (EvalData.manyHands) {
			IndexArrayUpdate.updateFlop();
		}
	}

	/*- ****************************************************************************
	* Just do the River for one seat
	**************************************************************************** */
	static void evaluateRiverOneSeat(int seat) {
		EvalData.players[seat].analyzeRiver();
		if (EvalData.manyHands) {
			IndexArrayUpdate.updateFlop();
		}
		Showdown.showdownValue(seat);
	}

	/*-**********************************************************************************************
	*Set frozen board
	*************************************************************************************************/
	private static void setFrozenBoard() {
		Card card1 = Board.boardUnsorted[0];
		Card card2 = Board.boardUnsorted[1];
		Card card3 = Board.boardUnsorted[2];
		Card card4 = Board.boardUnsorted[3];
		Card card5 = Board.boardUnsorted[4];

		Dealer.setFlopCards(card1, card2, card3);
		if (card4 != null) {
			Dealer.setTurnCard(card4);
		}
		if (card5 != null) {
			Dealer.setRiverCard(card5);
		}
		Dealer.dealAllHoleCards();

	}

	/*-**********************************************************************************************
	* This method is a helper with setup code common to multiple methods.
	* It is called only if there are 1 or 2 seats that are frozen.
	*************************************************************************************************/
	private static void frozen() {
		int start = 0;
		Card card1 = null;
		Card card2 = null;
		Card card3 = null;
		Card card4 = null;
		if (EvalData.freezeSeat1) {
			start = 1;
			card1 = EvalData.players[0].holeCard1;
			card2 = EvalData.players[0].holeCard2;
		}
		if (EvalData.freezeSeat2) {
			start = 2;
			card3 = EvalData.players[1].holeCard1;
			card4 = EvalData.players[1].holeCard2;
		}
		Deck.shuffle();
		if (card1 != null) {
			Dealer.setHoleCards(0, card1, card2);
		}
		if (card3 != null) {
			Dealer.setHoleCards(1, card3, card4);
		}
		for (int i = start; i < PLAYERS; i++) {
			Dealer.dealHoleCards(i);
		}
	}

	/*- ****************************************************************************
	* Initialize for a new Game
	* Note: Game is 6 players, Hand is one player
	**************************************************************************** */
	private static void newGameSimulation() {
		betCalled = false;
		streetComplete = false;
		boardComplete = false;
		betType = -1;
		orbit = -1;
		potBD = zeroBD;
		mainPotBD = zeroBD;
		betToMeBD = zeroBD;
		betNowBD = zeroBD;
		foldCount = 0;
		limpCount = 0;
		allinCount = 0;
		for (int i = 0; i < PLAYERS; i++) {
			moneyInBD[i] = zeroBD;
			stackBD[i] = new BigDecimal(200);
			playerMainPotBD[i] = zeroBD;
			stackPreflopBD[i] = new BigDecimal(200);
			playerAllin[i] = false;
			playerWon[i] = false;
			playerWonShowdown[i] = false;
		}
		betType = LIMP;
		orbit = 0;
		HandHistory.lastOrbit[0] = 0;
	}

	/*- ****************************************************************************
	* Initialize for new game
	**************************************************************************** */
	private static void initializeForNewGame() {
		EvalData.initializeGameControl();
		EvalData.initializeNewGame();
		for (int i = 0; i < PLAYERS; i++) {
			HandHistory.returnedToShowdownBD[i] = zeroBD;
			HandHistory.playerSidePotSplitTotalBD[i] = zeroBD;
			HandHistory.playerSidePotSplitBD[i] = zeroBD;
		}
		gamesPlayed = 0;
		handsPlayed = 0;
		seatSB = LAST_SEAT;
		seatBB = 0;
		seatUTG = 1;
		seatMP = 2;
		seatCO = 3;
		seatBU = 4;
		for (int i = 0; i < 6; i++) {
			EvalData.players[i].initializeForNewGame();
		}
		rotatePositions();
	}

	/*- ****************************************************************************
	* Initialize Hand History data for a new game
	**************************************************************************** */
	private static void hhData() {
		for (int i = 0; i < PLAYERS; i++) {
			stackPreflopBD[i] = new BigDecimal(200);
			moneyInBD[i] = zeroBD;
			stackBD[i] = new BigDecimal(200);
			playerMainPotBD[i] = new BigDecimal(0);
			playerAllin[i] = false;
			playerWon[i] = false;
			playerWonShowdown[i] = false;
		}
		HandHistory.hhData();
	}

	/*- ****************************************************************************
	 * Play hand Preflop
	 * An instance of the Player class will make a decision using 
	 * HandRangePlayability.
	 **************************************************************************** */
	private static void playHandPreflop() {
		if (playerFolded[seat] || playerAllin[seat]) {
			return;
		}
		players[seat].initialize(seat);
		if (foldCount == 5) {
			Crash.log("ERROR foldCount  " + foldCount);
		}
		betType = players[seat].getDecision();
		HandHistory.cards[seat] = EvalData.players[seat].holeCard1.toString()
				+ EvalData.players[seat].holeCard2.toString();
	}

	/*- ****************************************************************************
	* This method plays one Preflop hand
	* There is an instance of Player for each seat
	*******************************************************************************/
	private static boolean playPreflop() {
		if (EvalData.hh) {
			hhData();
		}
		EvalData.street = PREFLOP;
		foldCount = 0;
		betType = PREFLOP_LIMP;
		postBlinds();
		orbit = 0;
		preflopOrbit0();

		if (foldCount >= 5) {
			Crash.log("ERROR foldCount  " + foldCount);
		}
		orbit = 1;
		HandHistory.lastOrbit[0] = 1;
		preflopOrbit1();
		if (streetComplete || streetComplete()) {
			return true;
		}

		orbit = 2;
		HandHistory.lastOrbit[0] = 2;
		preflopOrbit23();

		if (streetComplete()) {
			return true;
		}

		orbit = 3;
		HandHistory.lastOrbit[0] = 3;
		preflopOrbit23();

		if (streetComplete()) {
			return true;
		}

		if (EvalData.hh) {
			HandHistory.createHH();
			HandHistory.closeHH();
		}

		Logger.logError("ERROR playPreflop()   street not complete after 4 orbits ");
		// WTF 0 4 1079 37086 0
		Crash.log("WTF " + 0 + " " + seat + " " + gamesPlayed + " " + handsPlayed + " " + EvalData.street);
		Crash.log("");
		return false;
	}

	/*- ****************************************************************************
	*  Start first Orbit
	 * Assumes 6-Max.
	 * A winner is not possible yet in the first EvalData.orbit SB and BB can not fold yet
	*******************************************************************************/
	private static void preflopOrbit0() {
		// Play hand UTG
		seat = seatUTG;
		betType = PREFLOP_LIMP;
		playHandPreflop();
		// Play hand MP
		seat = seatMP;
		playHandPreflop();

		// Play hand CO
		seat = seatCO;
		playHandPreflop();

		// Play hand Button
		seat = seatBU;
		playHandPreflop();
	}

	/*- ****************************************************************************
	* Orbit 1
	* Blinds get to play
	*******************************************************************************/
	private static void preflopOrbit1() {
		seat = seatSB;
		playHandPreflop();
		if (foldCount == MAXFOLDED) {
			return;
		}
		seat = seatBB;
		playHandPreflop();
		if (foldCount == MAXFOLDED || streetComplete()) {
			return;
		}
		seat = seatUTG;
		if (!playerFolded[seatUTG] && !playerAllin[seatUTG]) {
			playHandPreflop();
			if (foldCount == MAXFOLDED || streetComplete()) {
				return;
			}
		}
		seat = seatMP;
		if (!playerFolded[seatMP] && !playerAllin[seatMP]) {
			playHandPreflop();
			if (foldCount == MAXFOLDED || streetComplete()) {
				return;
			}
		}
		seat = seatCO;
		if (!playerFolded[seatCO] && !playerAllin[seatCO]) {
			playHandPreflop();
			if (foldCount == MAXFOLDED || streetComplete()) {
				return;
			}
		}
		seat = seatBU;
		if (!playerFolded[seatBU] && !playerAllin[seatBU]) {
			playHandPreflop();
		}
	}

	/*- ****************************************************************************
	* Play Orbit 2 and 3 Assumes 6 Max.
	*******************************************************************************/
	private static void preflopOrbit23() {
		if (!playerFolded[seatSB] && !playerAllin[seatSB]) {
			seat = seatSB;
			playHandPreflop();
			if (foldCount == MAXFOLDED || streetComplete()) {
				return;
			}
		}
		if (!playerFolded[seatBB] && !playerAllin[seatBB]) {
			seat = seatBB;
			playHandPreflop();
			if (foldCount == MAXFOLDED || streetComplete()) {
				return;
			}
		}
		if (!playerFolded[seatUTG] && !playerAllin[seatUTG]) {
			seat = seatUTG;
			playHandPreflop();
			if (foldCount == MAXFOLDED || streetComplete()) {
				return;
			}
		}
		if (!playerFolded[seatMP] && !playerAllin[seatMP]) {
			seat = seatMP;
			playHandPreflop();
			if (foldCount == MAXFOLDED || streetComplete()) {
				return;
			}
		}
		seat = seatCO;
		if (!playerFolded[seatCO] && !playerAllin[seatCO]) {
			playHandPreflop();
			if (foldCount == MAXFOLDED || streetComplete()) {
				return;
			}
		}
		seat = seatBU;
		if (!playerFolded[seatBU] && !playerAllin[seatBU]) {
			playHandPreflop();
			streetComplete();
			return;
		}
		playHandPreflop();
	}

	/*-**********************************************************************************************
	* This method will post the blinds, SB and BB
	*************************************************************************************************/
	private static void postBlinds() {
		// Small Blind
		stackBD[seatSB] = stackBD[seatSB].subtract(SBBetBD);
		potBD = potBD.add(SBBetBD);
		moneyInBD[seatSB] = moneyInBD[seatSB].add(SBBetBD);
		HandHistory.potThenBD[0][seatSB][orbit] = potBD;
		HandHistory.stackThenBD[0][seatSB][orbit] = stackBD[seatSB];
		HandHistory.moneyInThenBD[0][seatSB][orbit] = moneyInBD[seatSB];
		// Big Blind
		stackBD[seatBB] = stackBD[seatBB].subtract(BBBetBD);
		potBD = potBD.add(BBBetBD);
		moneyInBD[seatBB] = moneyInBD[seatBB].add(BBBetBD);
		HandHistory.potThenBD[0][seatBB][orbit] = potBD;
		HandHistory.moneyInThenBD[0][seatBB][orbit] = moneyInBD[seatBB];
		GameControl.betNowBD = BBBetBD;
	}

	/*- ****************************************************************************
	 *  Check for a street complete.
	 *  A street is complete if:
	 *  	All players have the same money in the pot ( unless folded or all-in ).
	 *  	All but 1 player have folded.
	 **************************************************************************** */
	private static boolean streetComplete() {
		if (foldCount == MAXFOLDED) {
			streetComplete = true;
			return true;
		}
		for (var i = 0; i < PLAYERS; ++i) {
			if (!playerFolded[i] && betNowBD.compareTo(moneyInBD[i]) != 0 && !playerAllin[i]) {
				return false;
			}
		}
		streetComplete = true;
		return true;
	}

	/*- ****************************************************************************
	* This method handles uncalled bets.
	* If a player has more money in that was not called, return that money.
	*
	* In order for an uncalled bet to be returned:
	* 		There must be only 1 player still active.
	* 		If 1 player is active then all others have folded. If the active player made a
	* 		bet or raise that was not called it is returned to him.
	*
	**************************************************************************** */
	private static void uncalledBet() {
		final var active = PLAYERS - foldCount;
		if (!betCalled && betType == CHECK) {
			return;
		}
		if (active == 1 && betNowBD.compareTo(zeroBD) != 0 && !betCalled) {
			returnUncalledBet();
		}
	}

	/*- ****************************************************************************
	 * Play Flop. 
	 * TODO  For a future version - maybe
		**************************************************************************** */
	private static void playFlop() {
		boardComplete = streetComplete = false;
		if (foldCount == MAXFOLDED) {
			streetComplete();
			streetComplete = true;
			return;
		}
		if (streetComplete()) {
			return;
		}

		if (foldCount == MAXFOLDED) {
			streetComplete();
			streetComplete = true;
			return;
		}
		if (streetComplete()) {
			return;
		}

		if (foldCount == MAXFOLDED) {
			streetComplete();
			streetComplete = true;
			return;
		}
		if (streetComplete()) {
			return;
		}

		if (foldCount == MAXFOLDED) {
			streetComplete();
			streetComplete = true;
			return;
		}
		if (streetComplete()) {
			return;
		}

		if (streetComplete()) {
			return;
		}
		Logger.logError("ERROR Play()   street not complete after 4 orbits ");
		Crash.log("WTF");
	}

	/*- ****************************************************************************
	*	Play Turn.
	****************************************************************************	 */
	private static void playTurn() {
		boardComplete = streetComplete = false;
		if (foldCount == MAXFOLDED) {
			streetComplete();
			streetComplete = true;
			return;
		}
		if (streetComplete()) {
			return;
		}

		if (foldCount == MAXFOLDED) {
			streetComplete();
			streetComplete = true;
			return;
		}
		if (streetComplete()) {
			return;
		}

		if (foldCount == MAXFOLDED) {
			streetComplete();
			streetComplete = true;
			return;
		}
		if (streetComplete()) {
			return;
		}

		if (foldCount == MAXFOLDED) {
			streetComplete();
			streetComplete = true;
			return;
		}
		if (streetComplete()) {
			return;
		}

		if (streetComplete) {
			return;
		}
		Logger.logError("ERROR Play()   street not complete after 4 orbits ");
		Crash.log("WTF");
	}

	/*- ****************************************************************************
	 *  - - Play River.
	**************************************************************************** */
	private static void playRiver() {
		boardComplete = streetComplete = false;
		if (foldCount == MAXFOLDED) {
			streetComplete();
			streetComplete = true;
			return;
		}

		if (streetComplete()) {
			return;
		}

		if (foldCount == MAXFOLDED) {
			streetComplete();
			streetComplete = true;
			return;
		}
		if (streetComplete()) {
			return;
		}

		if (foldCount == MAXFOLDED) {
			streetComplete();
			streetComplete = true;
			return;
		}
		if (streetComplete()) {
			return;
		}

		if (foldCount == MAXFOLDED) {
			streetComplete();
			streetComplete = true;
			return;
		}
		if (streetComplete()) {
			return;
		}

		if (streetComplete) {
			return;
		}
		// ERROR Play() street not complete after 4 orbits foldCount 4
		Logger.logError(new StringBuilder().append("ERROR Play()   street not complete after 4 orbits ")
				.append(" foldCount ").append(foldCount).append(" handsPlayed ").append(handsPlayed).toString());
		Crash.log("WTF");
	}

	/*- ****************************************************************************
	 *	Play one Flop Orbit.
	****************************************************************************	 */
	private static void flopOrbit() {
		if (!playerFolded[seatSB] && !playerAllin[seatSB]) {
			EvalData.seat = seatSB;
			flopPlay();
		}

		if (!playerFolded[seatBB] && !playerAllin[seatBB]) {
			EvalData.seat = seatBB;
			flopPlay();
		}

		if (!playerFolded[seatUTG] && !playerAllin[seatUTG]) {
			EvalData.seat = seatUTG;
			flopPlay();
		}

		if (!playerFolded[seatMP] && !playerAllin[seatMP]) {
			EvalData.seat = seatMP;
			flopPlay();
		}

		if (!playerFolded[seatCO] && !playerAllin[seatCO]) {
			EvalData.seat = seatCO;
			flopPlay();
		}

		if (!playerFolded[seatBU] && !playerAllin[seatBU]) {
			return;
		}
		EvalData.seat = seatBU;
		flopPlay();
	}

	/*- ****************************************************************************
	*	 Play one Turn Orbit 
	*	Assumes 6 Max. 
	****************************************************************************	*/
	private static void turnOrbit() {
		if (!playerFolded[seatSB] && !playerAllin[seatSB]) {
			EvalData.seat = seatSB;
			turnPlay();
		}

		if (!playerFolded[seatBB] && !playerAllin[seatBB]) {
			EvalData.seat = seatBB;
			turnPlay();
		}

		if (!playerFolded[seatUTG] && !playerAllin[seatUTG]) {
			EvalData.seat = seatUTG;
			turnPlay();
		}

		if (!playerFolded[seatMP] && !playerAllin[seatMP]) {
			EvalData.seat = seatMP;
			turnPlay();
		}

		if (!playerFolded[seatCO] && !playerAllin[seatCO]) {
			EvalData.seat = seatCO;
			turnPlay();
		}

		if (!playerFolded[seatBU] && !playerAllin[seatBU]) {
			return;
		}
		EvalData.seat = seatBU;
		turnPlay();
	}

	/*- ****************************************************************************
	 * Play one Orbit
	**************************************************************************** */
	private static void riverOrbit() {
		if (playerFolded[seatSB] && !playerAllin[seatSB]) {
			EvalData.seat = seatSB;
			riverPlay();
		}

		if (!playerFolded[seatBB] && !playerAllin[seatBB]) {
			EvalData.seat = seatBB;
			riverPlay();
		}

		if (!playerFolded[seatUTG] && !playerAllin[seatUTG]) {
			EvalData.seat = seatUTG;
			riverPlay();
		}

		if (!playerFolded[seatMP] && !playerAllin[seatMP]) {
			EvalData.seat = seatMP;
			riverPlay();
		}

		if (!playerFolded[seatCO] && !playerAllin[seatCO]) {
			EvalData.seat = seatCO;
			riverPlay();
		}

		if (!playerFolded[seatBU] && !playerAllin[seatBU]) {
			return;
		}
		EvalData.seat = seatBU;
		riverPlay();
	}

	/*- ******************************************************************************
	* Rotate positions
	* The positions are rotated each hand, as in a normal game.
	* EvalData.positions is indexed by seat returns position ( SB, BB, UTG, MO, CO, BU)
	*********************************************************************************/
	static void rotatePositions() {
		if (seatSB == LAST_SEAT) {
			seatSB = 0;
			seatBB = 1;
			seatUTG = 2;
			seatMP = 3;
			seatCO = 4;
			seatBU = 5;
			seatPos[0] = SB;
			seatPos[1] = BB;
			seatPos[2] = UTG;
			seatPos[3] = MP;
			seatPos[4] = CO;
			seatPos[5] = BU;
			return;
		}
		if (seatBB == LAST_SEAT) {
			seatSB = 5;
			seatBB = 0;
			seatUTG = 1;
			seatMP = 2;
			seatCO = 3;
			seatBU = 4;
			seatPos[0] = BB;
			seatPos[1] = UTG;
			seatPos[2] = MP;
			seatPos[3] = CO;
			seatPos[4] = BU;
			seatPos[5] = SB;
			return;
		}
		if (seatUTG == LAST_SEAT) {
			seatSB = 4;
			seatBB = 5;
			seatUTG = 0;
			seatMP = 1;
			seatCO = 2;
			seatBU = 3;
			seatPos[0] = UTG;
			seatPos[1] = MP;
			seatPos[2] = CO;
			seatPos[3] = BU;
			seatPos[4] = SB;
			seatPos[5] = BB;
			return;
		}
		if (seatMP == LAST_SEAT) {
			seatSB = 3;
			seatBB = 4;
			seatUTG = 5;
			seatMP = 0;
			seatCO = 1;
			seatBU = 2;
			seatPos[0] = MP;
			seatPos[1] = CO;
			seatPos[2] = BU;
			seatPos[3] = SB;
			seatPos[4] = BB;
			seatPos[5] = UTG;
			return;
		}
		if (seatCO == LAST_SEAT) {
			seatSB = 2;
			seatBB = 3;
			seatUTG = 4;
			seatMP = 5;
			seatCO = 0;
			seatBU = 1;
			seatPos[0] = CO;
			seatPos[1] = BU;
			seatPos[2] = SB;
			seatPos[3] = BB;
			seatPos[4] = UTG;
			seatPos[5] = MP;
			return;
		}
		if (seatBU != LAST_SEAT) {
			return;
		}
		seatSB = 1;
		seatBB = 2;
		seatUTG = 3;
		seatMP = 4;
		seatCO = 5;
		seatBU = 0;
		seatPos[0] = BU;
		seatPos[1] = SB;
		seatPos[2] = BB;
		seatPos[3] = UTG;
		seatPos[4] = MP;
		seatPos[5] = CO;
	}

	/*- ****************************************************************************
	 * - Play hand. Flop
	**************************************************************************** */
	private static void flopPlay() {
		if (foldCount == MAXFOLDED) {
			return;
			// if (EvalData.moneyIn[EvalData.seat] == EvalData.betNow && EvalData.betType !=
			// CHECK
			// && EvalData.moneyIn[EvalData.seat] != 0) {
			// return;
		}

		EvalData.handIndex = EvalData.players[EvalData.seat].handIndex;
	}

	/*- ****************************************************************************
	 * - Play hand.
	****************************************************************************	 */
	private static void turnPlay() {
		if (foldCount == MAXFOLDED) {
			return;
		}

		// if (EvalData.moneyIn[EvalData.seat] == EvalData.betNow && EvalData.betType !=
		// EvalData.CHECK
		// && EvalData.moneyIn[EvalData.seat] != 0)
		// return;

		++EvalData.turnCount[EvalData.seat];
		EvalData.handIndex = EvalData.players[EvalData.seat].handIndex;
	}

	/*- ****************************************************************************
	 * - Play hand.
	**************************************************************************** */
	private static void riverPlay() {
		if (foldCount == MAXFOLDED) {
			return;
			// if (EvalData.moneyIn[EvalData.seat] == EvalData.betNow && EvalData.betType !=
			// EvalData.CHECK
			// && EvalData.moneyIn[EvalData.seat] != 0)
			// return;
		}

		++EvalData.riverCount[EvalData.seat];
		EvalData.handIndex = EvalData.players[EvalData.seat].handIndex;
	}

	/*- ****************************************************************************
	 * Play hand Flop
	**************************************************************************** */
	private static void playHandFlop() {
		// errorCheck();
		// getRelativePosition();
		// EvalData.decisionProcessOpponentAction[EvalData.streetNumber][EvalData.seat][EvalData.orbit]
		// = EvalData.betType;
		// EvalData.handIndex = EvalData.handIndeesx[EvalData.seat];

		if (orbit == 0) {
			// int hv = Evaluate.evaluateAll(EvalData.seat);
			// handValue[EvalData.seat] = hv;
			// EvalData.stgy_HandValue[EvalData.streetNumber][EvalData.seat] = hv;
			// EvalData.evaluateIndex[EvalData.streetNumber][EvalData.seat] = hv;
			// EvalData.evaluateIndexPos[EvalData.streetNumber][EvalData.seat][EvalData.positions[EvalData.seat]]
			// = hv;
			// ++EvalData.evalArrayCounts[EvalData.streetNumber][EvalData.seat][handValue[EvalData.seat]];
			// ++EvalData.evalArrayCountsPos[EvalData.streetNumber][EvalData.positions[EvalData.seat]][EvalData.seat][handValue[EvalData.seat]];

			// if (Evaluate.evalArray[Evaluate.BOARD_WET])
			// EvalData.stgy_WetDry = EvalData.WET;
			// else if (Evaluate.evalArray[Evaluate.BOARD_DRY])
			// EvalData.stgy_WetDry = EvalData.DRY;
			// else
			// EvalData.stgy_WetDry = EvalData.NOT_WET_DRY;
			// if (Evaluate.evalArray[Evaluate.BOARD_STATIC])
			// EvalData.stgy_StaticDynamic = EvalData.STATIC;
			// else if (Evaluate.evalArray[Evaluate.BOARD_DYNAMIC])
			// EvalData.stgy_StaticDynamic = EvalData.DYNAMIC;
			// else
			// EvalData.stgy_StaticDynamic = EvalData.NOT_STATIC_DYNAMIC;
			// EvalData.stgy_HMLIndex = Evaluate.HMLIndex;
			// EvalData.stgy_BoardDrawIndex = Evaluate.boardPossibleArrayFlopMax;
			// TODO EvalData.stgy_BoardDrawIndex1577 = 0;

		}

		// if (Options.flopABC[EvalData.seat]) {
		// EvalData.decisionProcess[EvalData.streetNumber][EvalData.seat][EvalData.orbit]
		// = EvalData.DEC_ABC;
		// if (PlayABC.flopOrbit0Preemptive()) {
		// EvalData.decisionProcessDecision[EvalData.streetNumber][EvalData.seat][EvalData.orbit]
		// = -1;
		// return;
		// }
		// }
		// if (Options.flopGTO[EvalData.seat]) {
		// EvalData.decisionProcess[EvalData.streetNumber][EvalData.seat][EvalData.orbit]
		// = EvalData.DEC_GTO;
		// if (PlayGTO.flopOrbit0Preemptive()) {
		// EvalData.decisionProcessDecision[EvalData.streetNumber][EvalData.seat][EvalData.orbit]
		// = -1;
		// return;
		// }
		// }

		// Check the Rules
		// getRulePLay();
		/// EvalData.decisionProcessRule[EvalData.streetNumber][EvalData.seat][EvalData.orbit]
		// = EvalData.DEC_RULES;
		// EvalData.decisionProcessRule[EvalData.streetNumber][EvalData.seat][EvalData.orbit]
		// = rulePlay;
		// if (playHandRuleFlop()) {
		// return;
		// }

		// if (!Options.flopAdvanced[EvalData.seat]) {
		// EvalData.decisionProcess[EvalData.streetNumber][EvalData.seat][EvalData.orbit]
		// = EvalData.DEC_BLUFF;
		// if (playHandBluffFlop()) {
		// return;
		// }
		// }

		// // Next we try C Bet
		// if (Options.CBet[EvalData.seat] && EvalData.seat == EvalData.preflopRaiser &&
		// EvalData.betType == EvalData.CHECK) {
		// EvalData.decisionProcess[EvalData.streetNumber][EvalData.seat][EvalData.orbit]
		// = EvalData.DEC_CBET;
		// if (playHandCBet()) {
		// return;
		// }
		// }

		// No decision by Rules or Bluff or C Bet, so we will use MDF
		// if (EvalData.betType == EvalData.CHECK || EvalData.betType == EvalData.BET1
		// || EvalData.betType == EvalData.BET2) {
		// if ((Options.mdfFlop[EvalData.seat] || Options.mdfBet2Flop[EvalData.seat])) {
		/// TODO this makes no sense to me
		// TODO Looks like we will play MDF only if we did not raise or call bet 3 and
		/// did call or raise bet 2
		// TODO ??? then why || bet3 ??????
		// Will need to check orbits in a loop
		// if ((!EvalData.isCalledBet3[EvalData.PREFLOP][EvalData.seat] [EvalData.orbit]
		/// &&
		// !EvalData.isRaisedToBet3[EvalData.PREFLOP][EvalData.seat] [EvalData.orbit])
		// && ((!EvalData.isCalledBet2[EvalData.PREFLOP][EvalData.seat] [EvalData.orbit]
		/// &&
		// !EvalData.isRaisedToBet2[EvalData.PREFLOP][EvalData.seat] [EvalData.orbit]))
		// || ((!EvalData.isCallAllin[EvalData.PREFLOP][EvalData.seat] [EvalData.orbit]
		/// &&
		// !EvalData.isAllin[EvalData.PREFLOP][EvalData.seat] [EvalData.orbit])))

		// copied from Turn
		// if (EvalData.betType == EvalData.CHECK || EvalData.betType == EvalData.BET1
		// || EvalData.betType == EvalData.BET2) {
		// if ((Options.mdfFlop[EvalData.seat] || Options.mdfBet2Flop[EvalData.seat])) {
		// if (playHandMDFFlop())
		// return;
		// }
		// }
		// }
		// }
		// EvalData.decisionProcess[EvalData.streetNumber][EvalData.seat][EvalData.orbit]
		// = EvalData.DEC_FINAL;
		// double call = EvalData.betNow - EvalData.moneyIn[EvalData.seat];
		// if (call == 0) {
		// if (EvalData.dataCollection)
		// setPlayerCheckedMsg(56, POSTFLOP_MDF);
		// // else
		// setPlayerChecked();
		// return;
		// }

		// If all else fails just fold

		// if (EvalData.dataCollection)
		// setPlayerFoldedMsg(12, POSTFLOP_MDF);
		// else
		// setPlayerFolded();
	}

	/*- ****************************************************************************
	 * Play hand Turn
	****************************************************************************	 */
	private static void playHandTurn() {
		/*-
		getRelativePosition();
		EvalData.decisionProcessOpponentAction[EvalData.streetNumber][EvalData.seat][EvalData.orbit] = EvalData.betType;
		errorCheck();
		handIndex = EvalData.handIndex[EvalData.seat];
		
		if (EvalData.orbit == 0) {
			timerEvaluate.startTimer();
			int hv = Evaluate.evaluateAll(EvalData.seat);
			handValue[EvalData.seat] = hv;
			EvalData.stgy_HandValue[EvalData.streetNumber][EvalData.seat] = hv;
			EvalData.evaluateIndex[EvalData.streetNumber][EvalData.seat] = hv;
			EvalData.evaluateIndexPos[EvalData.streetNumber][EvalData.seat][EvalData.positions[EvalData.seat]] = hv;
			++EvalData.evalArrayCounts[EvalData.streetNumber][EvalData.seat][handValue[EvalData.seat]];
			++EvalData.evalArrayCountsPos[EvalData.streetNumber][EvalData.positions[EvalData.seat]][EvalData.seat][handValue[EvalData.seat]];
			timerEvaluate.sumTimer();
		}
		
		if (Options.turnABC[EvalData.seat]) {
			EvalData.decisionProcess[EvalData.streetNumber][EvalData.seat][EvalData.orbit] = EvalData.DEC_ABC;
			if (PlayABC.turnOrbit0Preemptive()) {
				EvalData.decisionProcessDecision[EvalData.streetNumber][EvalData.seat][EvalData.orbit] = -1;
				return;
			}
		}
		if (Options.turnGTO[EvalData.seat]) {
			EvalData.decisionProcess[EvalData.streetNumber][EvalData.seat][EvalData.orbit] = EvalData.DEC_GTO;
			if (PlayGTO.turnOrbit0Preemptive()) {
				return;
			}
		}
		
		// Check the Rules
		
		getRulePLay();
		EvalData.decisionProcessRule[EvalData.streetNumber][EvalData.seat][EvalData.orbit] = EvalData.DEC_RULES;
		EvalData.decisionProcessRule[EvalData.streetNumber][EvalData.seat][EvalData.orbit] = rulePlay;
		if (playHandRule()) {
			return;
		}
		
		if (!Options.turnAdvanced[EvalData.seat]) {
			EvalData.decisionProcess[EvalData.streetNumber][EvalData.seat][EvalData.orbit] = EvalData.DEC_BLUFF;
			if (playHandBluffTurn()) {
				EvalData.decisionProcessDecision[EvalData.streetNumber][EvalData.seat][EvalData.orbit] = -1;
				return;
			}
		}
		
		// Next we try barreling
		if (Options.barrelTurn[EvalData.seat]) {
			if (barrelTurn()) {
				EvalData.decisionProcess[EvalData.streetNumber][EvalData.seat][EvalData.orbit] = EvalData.DEC_BARREL;
				return;
			}
		}
		
		// No decision by Rules or Bluff or C Bet or Barreling so we will use MDF
		if (EvalData.betType == EvalData.CHECK || EvalData.betType == EvalData.BET1 || EvalData.betType == EvalData.BET2) {
			if ((Options.mdfTurn[EvalData.seat] || Options.mdfBet2Turn[EvalData.seat]))
				if (playHandMDFTurn())
					return;
		}
		
		EvalData.decisionProcess[EvalData.streetNumber][EvalData.seat][EvalData.orbit] = EvalData.DEC_FINAL;
		double call = EvalData.betNow - EvalData.moneyIn[EvalData.seat];
		if (call == 0) {
			if (EvalData.dataCollection)
				setPlayerCheckedMsg(56, POSTFLOP_MDF);
			else
				setPlayerChecked();
			return;
		}
		// If all else fails just fold
		if (EvalData.dataCollection) {
			setPlayerFoldedMsg(12, POSTFLOP_MDF);
		} else {
			setPlayerFolded();
		}
		*/
	}

	/*- ****************************************************************************
	 * Play hand River 
	**************************************************************************** */
	private static void playHandRiver() {
		/*-		errorCheck();
				getRelativePosition();
				EvalData.decisionProcessOpponentAction[EvalData.streetNumber][EvalData.seat][EvalData.orbit] = EvalData.betType;
				handIndex = EvalData.handIndex[EvalData.seat];
		
				if (EvalData.orbit == 0) {
					timerEvaluate.startTimer();
					int hv = Evaluate.evaluateAll(EvalData.seat);
					handValue[EvalData.seat] = hv;
					EvalData.stgy_HandValue[EvalData.streetNumber][EvalData.seat] = hv;
					EvalData.evaluateIndex[EvalData.streetNumber][EvalData.seat] = hv;
					EvalData.evaluateIndexPos[EvalData.streetNumber][EvalData.seat][EvalData.positions[EvalData.seat]] = hv;
					++EvalData.evalArrayCounts[EvalData.streetNumber][EvalData.seat][handValue[EvalData.seat]];
					++EvalData.evalArrayCountsPos[EvalData.streetNumber][EvalData.positions[EvalData.seat]][EvalData.seat][handValue[EvalData.seat]];
					timerEvaluate.sumTimer();
				}
		
				if (Options.riverABC[EvalData.seat]) {
					EvalData.decisionProcess[EvalData.streetNumber][EvalData.seat][EvalData.orbit] = EvalData.DEC_ABC;
					if (PlayABC.riverOrbit0Preemptive()) {
						EvalData.decisionProcessDecision[EvalData.streetNumber][EvalData.seat][EvalData.orbit] = -1;
						return;
					}
				}
				if (Options.riverGTO[EvalData.seat]) {
					EvalData.decisionProcess[EvalData.streetNumber][EvalData.seat][EvalData.orbit] = EvalData.DEC_GTO;
					if (PlayGTO.riverOrbit0Preemptive()) {
						return;
					}
				}
		
				// Check the Rules
				getRulePLay();
				EvalData.decisionProcessRule[EvalData.streetNumber][EvalData.seat][EvalData.orbit] = EvalData.DEC_RULES;
				EvalData.decisionProcessRule[EvalData.streetNumber][EvalData.seat][EvalData.orbit] = rulePlay;
				if (playHandRule()) {
					return;
				}
		
				if (!Options.riverAdvanced[EvalData.seat]) {
					EvalData.decisionProcess[EvalData.streetNumber][EvalData.seat][EvalData.orbit] = EvalData.DEC_BLUFF;
					if (playHandBluffRiver()) {
						return;
					}
				}
		
				// Next we try barreling
				if (Options.barrelRiver[EvalData.seat]) {
					if (barrelRiver()) {
						EvalData.decisionProcess[EvalData.streetNumber][EvalData.seat][EvalData.orbit] = EvalData.DEC_BARREL;
						return;
					}
				}
		
		//No decision by Rules or Bluff or Barreling so we will use MDF 
				if (EvalData.betType == EvalData.CHECK || EvalData.betType == EvalData.BET1 || EvalData.betType == EvalData.BET2) {
					if ((Options.mdfRiver[EvalData.seat] || Options.mdfBet2River[EvalData.seat]))
						if (playHandMDFRiver())
							return;
				}
		
				EvalData.decisionProcess[EvalData.streetNumber][EvalData.seat][EvalData.orbit] = EvalData.DEC_FINAL;
				double call = EvalData.betNow - EvalData.moneyIn[EvalData.seat];
				if (call == 0) {
					if (EvalData.dataCollection)
						setPlayerCheckedMsg(56, POSTFLOP_MDF);
					else
						setPlayerChecked();
					return;
				}
				// If all else fails just fold
				if (EvalData.dataCollection)
					setPlayerFoldedMsg(12, POSTFLOP_MDF);
				else
					setPlayerFolded();
			}
		*/
	}
}
