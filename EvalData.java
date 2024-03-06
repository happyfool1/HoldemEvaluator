package holdemevaluator;

public class EvalData implements Constants {

	/*-  **************************************************************************
	 * This switch is set true if we are evaluating multiple hands and the 
	 * Class EvaluateMany is being called to update the Class ManyHands.
	 *  Saving data for ManyHands adds a lot of additional overhead.
	 ***************************************************************************** */
	static boolean manyHands = true;
	static boolean preflopSimulation = false;
	static boolean preflopBothSimNoSim = false;
	static boolean freezeSeat1 = false;
	static boolean freezeSeat2 = false;
	static boolean freezeBoard = false;
	static boolean handInfo = false; // Collect text and other data
	static boolean do1755 = false;
	static boolean rotatePlayers = false;
	static boolean hh = false; // Hand History generate if simulation is true
	static boolean simulation = false; // if simulation is true
	static String[] playerNames = { "Lover", "Happy", "Foolish", "JustMe", "Susan", "Chucky" };
	static int[] playerType = { HERO, HERO, HERO, HERO, HERO, HERO }; // Set by GameControl
	/*- *****************************************************************************
	 * Customization
	  ******************************************************************************/
	static String applicationDirectory = APPLICATION_DIRECTORY; // Directory for this application files
	/*- Seed for Random. Change this number for an all new pseudo-random series of cards */
	static long seed = RANDOM_SEED;
	static Timer timer1 = new Timer();
	static Timer timer2 = new Timer();
	static Timer timer3 = new Timer();
	static Timer timer4 = new Timer();

	/*-  *********************************************************************************** 
	 * AnalysisOfMadeAndDrawHands is a class that has one instance for each player.
	 * What the class does is to analyze the combined hole cards and board cards for what
	 * type of made hand and/or drawing hand the player has.
	 * Access to a lot of data is made through the class.
	************************************************************************************** */
	static AnalysisOfMadeAndDrawHands[] players = { new AnalysisOfMadeAndDrawHands(0),
			new AnalysisOfMadeAndDrawHands(1), new AnalysisOfMadeAndDrawHands(2), new AnalysisOfMadeAndDrawHands(3),
			new AnalysisOfMadeAndDrawHands(4), new AnalysisOfMadeAndDrawHands(5) };

	/*-  ************************************************************************************* 
	 * Data associated with current hand being evaluated
	  ************************************************************************************** */
	static int handValue = -1; // Relative value of hand 0 - 9, 0 fold, 9 nuts
	// Current seat and street
	static int seat = -1;
	static int street = -1;
	// Last seat and street evaluated
	static int lastSeat = -1;
	static int lastStreet = -1;

	/*-  ************************************************************************************* 
	 * Net results of analyzing this hand.
	 * These are the most important to be able to decide on how to play the hand.
	 * When evaluating Texas hold'em hands and draws on the flop without any information 
	 * on player actions, there are several important things to consider:
	 *
	 * Your own hand strength: This includes your current hand and the potential to improve
	 *  with future community cards.
	 *
	 * Possible opponent hand ranges: Consider what hands your opponents could have
	 *  based on their position, preflop betting, and any visible community cards. 
	 *  This will give you an idea of the strength of your hand relative to your opponents' 
	 *  possible holdings.
	 *
	 * Pot odds: Determine the size of the pot and compare it to the cost of calling a bet 
	 * or making a bet yourself. 
	 * This will help you decide if it's worth continuing in the hand or folding.
	 * 
	 * Board texture: Analyze the texture of the flop to determine how it affects the range 
	 * of possible hands that your opponents could have. 
	 *  A dry flop with no draws will limit the range of possible hands, while a 
	 *  wet flop with many draws will expand it.
	 *
	 * Position: Your position at the table is crucial in determining your actions. 
	 * Being in late position will give you more information on your opponents' actions, 
	 * which can help you make better decisions.
	 *
	 * By considering these factors, you can make informed decisions on whether to 
	 * continue in the hand, fold, or make a bet. 
	 * Keep in mind that evaluating hands and draws in Texas hold'em is an ongoing process, 
	 * and new information will continue to be revealed with each subsequent community card.
	  ************************************************************************************** */
	static int handStrength = -1;
	static double rangeAdvantage = -1;
	static double bestPossible = -1;
	static int outs = -1;
	static double makeHandPer = -1;
	static int flopTexture = -1;
	static int turnTexture = -1;

	/*-  **************************************************************************************
	 * Experimental 
	 * Indexes are used to identify Flop and board types
	  ************************************************************************************** */
	static int type = -1; // Ed miller
	static int allIndexFlop = -1;
	static int hmlIndexFlop = -1;
	static int hmlIndexTurn = -1;
	static int hmlIndexRiver = -1;
	static int wetDryIndexFlop = -1;
	static int typeOf1755IndexFlop = -1; // Type of 1755 flop
	static int flop1755IndexFlop = -1; // Index into array of 1755 Flops, all possible
	static int SCBPIndexFlop = -1; // Suited, Connected, Big card, Paired

	static int pairBroadwayIndex = -1;
	static int strengthSuitednessIndex = -1;
	static int distributionFlushPotentialIndex = -1;
	static int connectednessValueIndex = -1;

	static int bestFlopIndex = -1; // Index into drawAndMadeArray for Flop
	static int bestTurnIndex = -1; // Index into boardArray for Turn
	static int bestRiverIndex = -1; // Index into boardArray for River
	static int handIndex = -1; // Hole cards

	/*-  **************************************************************************
	 * Data used for index array calculation
	 ***************************************************************************** */
	static int flopCount = -1;
	static int[] count = new int[HML_FLOP_SIZE]; // How many times we had this combination
	static int hmlCount = -1;
	static int[] winnerArray = new int[HML_FLOP_SIZE]; // How many we won
	static int[] showdownCount = new int[HML_FLOP_SIZE]; // How many we went to at showdown
	static int[] showdownWin = new int[HML_FLOP_SIZE]; // How many we went to at showdown
	static int[] showdownLoose = new int[HML_FLOP_SIZE]; // How many we went to at showdown
	static int[] showdownArray = new int[HML_FLOP_SIZE]; // How many we won at showdown
	static int[] flopoCount = new int[HML_FLOP_SIZE]; // How many we won at Flop
	static int[] turnCount = new int[HML_FLOP_SIZE]; // How many we won at Turn
	static int[] riverCount = new int[HML_FLOP_SIZE]; // How many we won at River

	/*-  **************************************************************************************
	 *  Wet / Dry
	  ************************************************************************************** */
	static boolean[] wetDryArray = { false, false, false };

	static boolean interactive = false;
	static String[] decSt = { "Rules", "MDFCheck", "MDFBet1", "MDFBet2", "Bluff", "ABC", "C-Bet", "GTO", "Barrel",
			"Final", "None" };
	static String[] rpSt = { "First", "First HU", "Middle", "Last", "Last HU" };
	static String[] actionSt = { "Check", "Bet", "Raise", "Call", "All-in", "Call All-in", "Fold", "None" };
	static String[] ruleSt = { "Bet", "Call", "All-in", "None" };
	static String[] opActionSt = { "Check", "Bet", "Raise", "Call", "All-in", "Call All-in", "Fold", "None" };
	static String[] streetSt = { "Preflop", "Flop", "Turn", "River", "None" };
	static String releaseDate = "202100511";
	static String installDate = "202100511";
	static String expirationDateFree = "202100511";
	static String expirationDateTrial = "202100511";
	static String machineID = "chucky";
	static String UUID = "11324";
	static String version = "02.00.00";
	static boolean freeVersion = false;
	static boolean trialVersion = false;
	static boolean paidVersion = true;
	static boolean developerVersion = true;

	private EvalData() {
		throw new IllegalStateException("Utility class");
	}

	/*- ******************************************************************************************
	*  Initialize for start of new game, a series of hands
	********************************************************************************************/
	static void initializeNewGame() {
		Deck.setSeed(RANDOM_SEED);
		Deck.newDeck();
		street = FLOP;
		IndexArrays.initialize();

	}

	/*- ******************************************************************************************
	*  Initialize Hand History
	********************************************************************************************/
	static void initializeGameControl() {
		do1755 = false;
		rotatePlayers = true;
		simulation = true;
	}

}
