package holdemevaluator;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

/*-  *****************************************************************************
 * Constant definitions used throughout multiple projects.
 * Hold'emEvaluator
 * Hold'emHandHistory
 * Hold'em......
 * Should be in every class in this project.
 * In every Class that uses this add to class 
 * 		class xxx implements Constants 
 * 
  * @author PEAK_
  ***************************************************************************** */

public interface Constants {

	/*-**********************************************************************************************
	* Global
	*************************************************************************************************/
	String APPLICATION_DIRECTORY = "C:\\ApplicationData\\"; // Directory application files
	String HH_DIRECTORY = "C:\\HH\\"; // Directory for Hand History files
	String APPLICATION_DATA_DIRECTORY = "C:\\Hold'emDatabase\\"; // Directory applications files
	long RANDOM_SEED = 2062142251;
	static final int POKER_STARS = 0;

	/*-**********************************************************************************************
	*HandRange
	*************************************************************************************************/
	int HANDS = 169;
	int NUM_ROWS = 13;
	int NUM_COLS = 13;
	int SUITED = 0;
	int OFFSUIT = 1;
	int PAIR = 2;
	int INVALID = 2;

	int COMBINATIONS = 1326;

	/*- *****************************************************************************
	 * Suits
	  ******************************************************************************/
	int SPADE = 0;

	int CLUB = 1;

	int DIAMOND = 2;

	int HEART = 3;

	String[] SUITS_ST = { "s", "c", "d", "h" };

	String[] SUITS2_ST = { "Spade", "Club", "Diamond", "Heart" };

	/*- *****************************************************************************
	 * Card
	  ******************************************************************************/
	int ACE = 12;

	int KING = 11;

	int QUEEN = 10;

	int JACK = 9;

	int TEN = 8;

	int NINE = 7;

	int EIGHT = 6;

	int SEVEN = 5;

	int SIX = 4;

	int FIVE = 3;

	int FOUR = 2;

	int THREE = 1;

	int TWO = 0;

	/*- *****************************************************************************
	 * Streets
	  ******************************************************************************/
	int PREFLOP = 0;

	int FLOP = 1;

	int TURN = 2;

	int RIVER = 3;

	int SHOWDOWN = 4;

	int SUMMARY = 5;

	int ALL_STREETS = 6;

	String[] STREET_ST = { "Preflop", "Flop", "Turn", "River", "Showdown", "Summary", "All Streets" };

	/*- *****************************************************************************
	 * Basic definitions
	  ******************************************************************************/
	int PLAYERS = 6;

	int MAXFOLDED = 5;

	int LAST_SEAT = 5;

	int ORBITS = 4;

	int STREET = 4;

	int NO = 0;

	/*- **************************************************************************** 
	*  Positions
	*******************************************************************************/

	int SB = 0;

	int BB = 1;

	int UTG = 2;

	int MP = 3;

	int CUTOFF = 4;

	int CO = 4;

	int BUTTON = 5;

	int BU = 5;

	int POSITIONS = 6;

	String[] POSITION_ST = { "Small Blind", "Big Blind", "Under The Gun", "Middle", "Cutoff", "Button" };

	String[] POSITION_ST2 = { "SB", "BB", "UTG", "MP", "CO", "BU" };

	/*- **************************************************************************** 
	*  Values for relative positions 
	*******************************************************************************/
	int RP_FIRST = 0;

	int RP_FIRSTHU = 1;

	int RP_MIDDLE = 2;

	int RP_LAST = 3;

	int RP_LASTHU = 4;

	String[] RP_ST = { "First", "First HU", "Middle", "Last", "Last HU" };

	/*- **************************************************************************** 
	*  Player types
	*******************************************************************************/
	int HERO = 0;

	int FISH = 1;

	int NIT = 2;

	int LAG = 3;

	int TAG = 4;

	int AVERAGE = 5;

	int SHARK = 6;

	int WINNER = 7;

	int LOOSER = 8;

	int NUM_TYPES = 9;

	String[] PLAYER_TYPE_ST = { "Hero", "Fish", "Nit", "Lag", "Tag", "Average", "Shark", "winner", "Looser" };

	int WIN = WINNER;

	int LOOSE = LOOSER;

	/*- **************************************************************************** 
	*  Player actions preflop and post flop
	*******************************************************************************/
	int FOLD = 0;

	int CHECK = 1;

	int BET1 = 2;

	int CALL_BET1 = 3;

	int LIMP = 4;

	int BET2 = 5;

	int OPEN = 6;

	int CALL_BET2 = 7;

	int CALL = 8;

	int BET3 = 9;

	int CALL_BET3 = 10;

	int BET4 = 11;

	int CALL_BET4 = 12;

	int ALLIN = 13;

	int CALL_ALLIN = 14;

	BigDecimal zeroBD = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);

	/*- **************************************************************************** 
	* Actions as defined for HandRange.  
	*******************************************************************************/
	int RANGE_LIMP = 0;

	int RANGE_OPEN = 1;

	int RANGE_CALL = 2;

	int RANGE_BET3 = 3;

	int RANGE_CALL_BET3 = 4;

	int RANGE_BET4 = 5;

	int RANGE_CALL_BET4 = 6;

	int RANGE_ALLIN = 7;

	int RANGE_CALL_ALLIN = 8;

	int RANGE_COUNT = 9;

	// Can not use values as index, must delete 0
	String[] RANGE_ST = { "Limp", "Open", "Call", "3 Bet", "Call 3 bet", "4 Bet", "Call 4 Bet", "All-In",
			"Call All-In" };

	int NOT_RANGE = -1;

	int IN_RANGE = 1;

	/*- **************************************************************************** 
	* Actions to a player - Preflop
	*******************************************************************************/
	int PREFLOP_LIMP = 0;

	int PREFLOP_BET1 = 0;

	int PREFLOP_OPEN = 1;

	int PREFLOP_BET2 = 1;

	int PREFLOP_BET3 = 2;

	int PREFLOP_BET4 = 3;

	int PREFLOP_ALLIN = 4;

	int PREFLOP_CALL_ALLIN = 5;

	int PREFLOP_ACTIONS = 6;

	String[] PREFLOP_ST = { "Limp 1 Bet", "Open 2 Bet", "3 Bet", "4 Bet", "All-In", "Call All-In" };

	/*- *****************************************************************************
	 * Draws
	  ******************************************************************************/
	int DRAW_NONE = 0;

	int DRAW_GUTSHOT = 1;

	int DRAW_FLUSH = 2;

	int DRAW_OESD = 3;

	int DRAW_FLUSH_PAIR = 4;

	int DRAW_OESD_GUTSHOT = 5;

	int DRAW_GUTSHOT_PAIR = 6;

	int DRAW_GUTSHOT_GUTSHOT = 7;

	int DRAW_FLUSH_GUTSHOT = 8;

	int DRAW_STRAIGHT = 9;

	int DRAW_OESD_PAIR = 10;

	int DRAW_STRAIGHT_GUTSHOT = 11;

	int DRAW_FLUSH_OESD = 12;

	int DRAW_STRAIGHT_PAIR = 13;

	int DRAW_FLUSH_GUTSHOT_GUTSHOT = 14;

	int DRAW_FLUSH_OESD_GUTSHOT = 15;

	int DRAW_FLUSH_STRAIGHT_GUTSHOT = 16;

	int DRAW_FLUSH_STRAIGHT = 17;

	int DRAW_SIZE = 18;

	String[] DRAW_ST = { "No draw", "Gutshot", "Flush", "OESD", "Flush+Pair", "OESD+Gutshot", "Gutshot+Pair",
			"Gutshot+Gutshot", "Flush+Gutshot", "Straight", "OESD+Pair", "Straight+Gutshot", "Flush+OESD",
			"Straight+Pair", "Flush+Gut+Gut", "Flush+OESD+Gut", "Flush+Straight+Gut", "Flush+Straight" };

	/*- *****************************************************************************
	 * Made hand typesGut+Gut
	  ******************************************************************************/
	int MADE_NONE = 0;

	int MADE_BOARD_PAIR = 1;

	int MADE_PAIR_BELOW_BOARD_PAIR = 2;

	int MADE_ACE_HIGH = 3;

	int MADE_OVERCARDS = 4;

	int MADE_POCKET_PAIR_BELOW_TP = 5;

	int MADE_WEAK_PAIR = 6;

	int MADE_BOTTOM_PAIR = 7;

	int MADE_MIDDLE_PAIR = 8;

	int MADE_TOP_PAIR = 9;

	int MADE_OVER_PAIR = 10;

	int MADE_PAIR_ABOVE_BOARD_PAIR = 11;

	int MADE_BOTTOM_TWO_PAIR = 12;

	int MADE_TOP_AND_BOTTOM_TWO_PAIR = 13;

	int MADE_TOP_TWO_PAIR = 14;

	int MADE_SET = 15;

	int MADE_STRAIGHT = 16;

	int MADE_FLUSH = 17;

	int MADE_FULL_HOUSE = 18;

	int MADE_FOUR_OF_A_KIND = 19;

	int MADE_STRAIGHT_FLUSH = 20;

	int MADE_ROYAL_FLUSH = 21;

	int MADE_SIZE = 22;

	int MADE_SHORT_SIZE = 17;

	String[] MADE_ST = { "No hand", "Board Pair", "Pair < board pair", "Ace High", "Overcards", "PP BelowTP",
			"Weak Pair", "Bottom Pair", "Middle Pair", "Top Pair", "Over Pair", "Pair > board pair", "Bottom 2 Pair",
			"Top-Bot 2 Pair", "Top 2 Pair", "Set", "Straight", "Flush", "Full house", "Four of a Kind",
			"Straight flush", "Royal flush" };

	/*- *****************************************************************************
	 * Board analysis
	  ******************************************************************************/
	int BOARD_NONE = 0;

	int BOARD_GAP1 = 1;

	int BOARD_GAP2 = 2;

	int BOARD_GAP0_GAP0 = 3;

	int BOARD_GAP0_GAP1 = 4;

	int BOARD_GAP0_GAP2 = 5;

	int BOARD_GAP1_GAP0 = 6;

	int BOARD_GAP1_GAP1 = 7;

	int BOARD_GAP1_GAP2 = 8;

	int BOARD_GAP2_GAP0 = 9;

	int BOARD_GAP2_GAP1 = 10;

	int BOARD_GAP2_GAP2 = 11;

	int BOARD_F0 = 12;

	int BOARD_F2 = 13;

	int BOARD_1HIGH = 14;

	int BOARD_2HIGH = 15;

	int BOARD_3HIGH = 16;

	int BOARD_F3 = 17;

	int BOARD_ACE_HIGH = 18;

	int BOARD_ACE_PLUS_1HIGH = 19;

	int BOARD_ACE_PLUS_2HIGH = 20;

	int BOARD_ACE_PLUS_3HIGH = 21;

	int BOARD_ACE_3HIGH = 22;

	int BOARD_F4 = 23;

	int BOARD_PAIR = 24;

	int BOARD_TWO_PAIR = 25;

	int BOARD_SET = 26;

	int BOARD_STRAIGHT = 27;

	int BOARD_FLUSH = 28;

	int BOARD_FULL = 29;

	int BOARD_QUAD = 30;

	int BOARD_SIZE = 31;

	String[] BOARD_ST = { "None", "Gap1", "Gap2", "Gap0_Gap0", "Gap0_Gap1", "Gap0_Gap2", "Gap1_Gap0", "Gap1_Gap1",
			"Gap01_Gap2", "Gap2_Gap0", "Gap2_Gap1", "Gap2_Gap2", "F0", "F2", "1High", "2High", "3High",
			"Ace_PLUS_1High ", "Ace_PLUS_2High ", "Ace_PLUS_3High ", "F4", "Pair", "Two Pair", "Set", "Straight",
			"Flush", "Full House", "Quad" };

	/*- ****************************************************************************
	 * New Flop combinations Suited and Gaps
	 * Ace and high cards in BB_ and HML, not here
	 ******************************************************************************/
	int FLOP_NONE_ = 0;
	int FLOP_2_FLUSH = 1;
	int FLOP_3_FLUSH = 2;
	int FLOP_PAIR = 3;
	int FLOP_SET = 4;
	int FLOP_GAP1 = 5;
	int FLOP_GAP2 = 6;
	int FLOP_GAP0_GAP1_ = 7;
	int FLOP_GAP0_GAP2_ = 8;
	int FLOP_GAP1_GAP1_ = 9;
	int FLOP_GAP1_GAP2_ = 10;
	int FLOP_GAP2_GAP2_ = 11;
	int FLOP_SUITED_PAIR_ = 12;
	int FLOP_SUITED_GAP1_ = 13;
	int FLOP_SUITED_GAP2_ = 14;
	int FLOP_SUITED_GAP0_GAP1_ = 15;
	int FLOP_SUITED_GAP0_GAP2_ = 16;
	int FLOP_SUITED_GAP1_GAP1_ = 17;
	int FLOP_SUITED_GAP1_GAP2_ = 18;
	int FLOP_SUITED_GAP2_GAP2_ = 19;
	int FLOP_3FLUSH_ = 20;
	int FLOP_SIZE = 21;

	String FLOP_ST[] = { "None", "2 Suited", "3 Suited", "Pair", "Set", "Gap1", "Gap2", "Gap0+Gap1", "Gap0+Gap2",
			"Gap1+Gap1", "Gap1+Gap2", "Gap2+Gap2", "2 Suited+Pair", "2 Suited+Gap1", "2 Suited+Gap2",
			"2 Suited+Gap0+Gap1", "2 Suited+Gap0+Gap2", "2 Suited+Gap1+Gap1", "2 Suited+Gap2+Gap2", "3 Flush" };

	/*- *****************************************************************************
	 * HLM analysis of Flop - High Medium Low
	 ******************************************************************************/
	int LLL = 0;
	int MLL = 1;
	int MML = 2;
	int MMM = 3;
	int HLL = 4;
	int HML = 5;
	int HMM = 6;
	int HHL = 7;
	int HHM = 8;
	int HHH = 9;
	int HML_FLOP_SIZE = 10;
	String[] HML_FLOP_ST = { "LLL", "MLL", "MML", "MMM", "HLL", "HML", "HMM", "HHL", "HHM", "HHH" };

	int LLLL = 0;
	int MLLL = 1;
	int MMLL = 2;
	int MMML = 3;
	int MMMM = 4;
	int HLLL = 5;
	int HMLL = 6;
	int HMML = 7;
	int HMMM = 8;
	int HHLL = 9;
	int HHML = 10;
	int HHMM = 11;
	int HHHL = 12;
	int HHHM = 13;
	int HHHH = 14;
	int HML_TURN_SIZE = 15;
	String[] HML_TURN_ST = { "LLLL", "MLLL", "MMLL", "MMML", "MMMM", "HLLL", "HMLL", "HMML", "HMMM", "HHLL", "HHML",
			"HHLMM", "HHHL", "HHHM", "HHHH" };

	int LLLLL = 0;
	int MLLLL = 1;
	int MMLLL = 2;
	int MMMLL = 3;
	int MMMML = 4;
	int MMMMM = 5;

	int HLLLL = 6;
	int HMLLL = 7;
	int HMMLL = 8;
	int HMMML = 9;
	int HMMMM = 10;
	int HHLLL = 11;
	int HHMLL = 12;
	int HHMML = 13;
	int HHMMM = 14;
	int HHHLL = 15;
	int HHHML = 16;
	int HHHMM = 17;
	int HHHHL = 18;
	int HHHHM = 19;
	int HHHHH = 20;
	int HML_RIVER_SIZE = 21;

	String[] HML_RIVER_ST = { "LLLLL", "MLLLL", "MMLLL", "MMMLL", "MMMML", "MMMMM", "HLLLL", "HMLLL", "HMMLL", "HMMML",
			"HMMMM", "HHLLL", "HHMLL", "HHMML", "HHMMM", "HHHLL", "HHHML", "HHHMM", "HHHHL", "HHHHM", "HHHHH" };

	/*- *****************************************************************************
	 * Suited, Connected, Big Card, Paired
	 ******************************************************************************/
	int SCBP_SIZE = 16;
	String[] SCBP_ST = { "Rbw,Not Con,No Pair,Low Cards", "Rbw,Not Con,No Pair,High Card", "Rbw,Not Con,Pair,Low Cards",
			"Rbw,Not Con,Pair,High Card", "Rbw,Con,No Pair,Low Cards", "Rbw,Con,No Pair,High Card",
			"Rbw,Con,Pair,Low Cards", "Rbw,Con,Pair,High Card", "2 St,Not Con,No Pair,Low Cards",
			"2 St,Not Con,No Pair,High Card", "2 St,Not Con,Pair,Low Cards", "2 St,Not Con,Pair,High Card",
			"2 St,Con,No Pair,Low Cards", "2 St,Con,No Pair,High Card", "2 St,Con,Pair,Low Cards",
			"2 St,Con,Pair,High Card" };

	/*- ***************************************************************************
	* Flop texture Suitedness and connectedness
	******************************************************************************/
	int SUITED_CONNECTED_MONOTONE_CONNECTED = 0;
	int SUITED_CONNECTED_MONOTONE_SEMI = 1;
	int SUITED_CONNECTED_MONOTONE_UNCONNECTED = 2;
	int SUITED_CONNECTED_TWO_TONE_CONNECTED = 3;
	int SUITED_CONNECTED_TWO_TONE_SEMI = 4;
	int SUITED_CONNECTED_TWO_TONE_UNCONNECTED = 5;
	int SUITED_CONNECTED_RAINBOW_CONNECTED = 6;
	int SUITED_CONNECTED_RAINBOW_SEMI = 7;
	int SUITED_CONNECTED_RAINBOW_UNCONNECTED = 8;
	int SUITED_CONNECTED_SIZE = 9;
	String SUITED_CONNECTED_ST[] = { "Monotone Connected", "Monotone Semi", "Monotone Unconnected",
			"Two-tone Connected", "Two-tone Semi", "Two-tone Unconnected", "Rainbow Connected", "Rainbow Semi",
			"Rainbow Unconnected" };

	/*- **************************************************************************
	* Flop texture Pair Booadway
	******************************************************************************/
	int PAIR_BROADWAY_TRIPS_HIGH = 0;
	int PAIR_BROADWAY_TRIPS_MEDIUM = 1;
	int PAIR_BROADWAY_TRIPS_LOW = 2;
	int PAIR_BROADWAY_PAIR_HIGH = 3;
	int PAIR_BROADWAY_PAIR_MEDIUM = 4;
	int PAIR_BROADWAY_PAIR_LOW = 5;
	int PAIR_BROADWAY_UNPAIRED_HIGH = 6;
	int PAIR_BROADWAY_UNPAIRED_MEDIUM = 7;
	int PAIR_BROADWAY_UNPAIRED_LOW = 8;
	int PAIR_BROADWAY_SIZE = 9;
	String[] PAIR_BROADWAY_ST = { "Trips High", "Trips Medium", "Trips Low", "Pair High", "Pair Medium", "Pair Low",
			"Unpaired High", "Unpaired Medium", "Unpaired Low" };

	/*- **************************************************************************
	* Flop texture Distribution Flush potential
	******************************************************************************/
	int DISTRIBUTION_FLUSH_POTENTIAL_TRIPS_HIGH = 1;
	int DISTRIBUTION_FLUSH_POTENTIAL_TRIPS_MEDIUM = 2;
	int DISTRIBUTION_FLUSH_POTENTIAL_TRIPS_LOW = 3;
	int DISTRIBUTION_FLUSH_POTENTIAL_ONE_PAIR_HIGH = 4;
	int DISTRIBUTION_FLUSH_POTENTIAL_ONE_PAIR_MEDIUM = 5;
	int DISTRIBUTION_FLUSH_POTENTIAL_ONE_PAIR_LOW = 6;
	int DISTRIBUTION_FLUSH_POTENTIAL_NO_PAIR_HIGH = 7;
	int DISTRIBUTION_FLUSH_POTENTIAL_NO_PAIR_MEDIUM = 8;
	int DISTRIBUTION_FLUSH_POTENTIAL_NO_PAIR_LOW = 9;
	int DISTRIBUTION_FLUSH_POTENTIAL_SIZE = 10;
	String[] DISTRIBUTION_FLUSH_POTENTIAL_ST = { "Trips High", "Trips Medium", "Trips Low", "One Pair High",
			"One Pair Medium", "One Pair Low", "No Pair High", "No Pair Medium", "No Pair Low" };

	/*- **************************************************************************
	* Flop texture  Value Spread 
	******************************************************************************/
	int DISTRIBUTION_VALUE_SPREAD_HIGH_HIGH = 0;
	int DISTRIBUTION_VALUE_SPREAD_HIGH_MEDIUM = 1;
	int DISTRIBUTION_VALUE_SPREAD_HIGH_LOW = 2;
	int DISTRIBUTION_VALUE_SPREAD_MEDIUM_HIGH = 3;
	int DISTRIBUTION_VALUE_SPREAD_MEDIUM_LOW = 4;
	int DISTRIBUTION_VALUE_SPREAD_LOW_HIGH = 5;
	int DISTRIBUTION_VALUE_SPREAD_LOW_MEDIUM = 6;
	int DISTRIBUTION_VALUE_SPREAD_LOW_LOW = 7;
	int DISTRIBUTION_VALUE_SPREAD_SIZE = 8;
	String[] DISTRIBUTION_VALUE_ST = { "High High", "High bMedium", "High Low", "Medium High", "Medium Low", "Low High",
			"Low Medium", "Low Low" };

	/*- ****************************************************************************
	 * Experimental index
	 * Too many to be practical at the table BUT we can find out which are most useful
	 ******************************************************************************/
	int EX_NONE = 0;
	int EX_HHHS1GAP0GAP0 = 1;
	int EX_HHHS1GAP0GAP1 = 2;
	int EX_HHHHS1GAP0GAP2 = 3;
	int EX_HHHHS1GAP1GAP0 = 4;
	int EX_HHHHS1GAP1GAP1 = 5;
	int EX_HHHHS1GAP1GAP2 = 6;
	int EX_HHHHS1GAP2GAP0 = 7;
	int EX_HHHHS1GAP2GAP1 = 8;
	int EX_HHHHS1GAP2GAP2 = 9;

	int EX_HHHHS2GAP0GAP0 = 10;
	int EX_HHHHS2GAP0GAP1 = 11;
	int EX_HHHHS2GAP0GAP2 = 12;
	int EX_HHHHS2GAP1GAP0 = 13;
	int EX_HHHHS2GAP1GAP1 = 14;
	int EX_HHHHS2GAP1GAP2 = 15;
	int EX_HHHHS2GAP2GAP0 = 16;
	int EX_HHHHS2GAP2GAP1 = 17;
	int EX_HHHHS2GAP2GAP2 = 18;

	int EX_HHHHS3GAP0GAP0 = 19;
	int EX_HHHHS3GAP0GAP1 = 20;
	int EX_HHHHS3GAP0GAP2 = 21;
	int EX_HHHHS3GAP1GAP0 = 22;
	int EX_HHHHS3GAP1GAP1 = 23;
	int EX_HHHHS3GAP1GAP2 = 24;
	int EX_HHHHS3GAP2GAP0 = 25;
	int EX_HHHHS3GAP2GAP1 = 26;
	int EX_HHHS3GAP2GAP2 = 27;

	int EX_HHMS1GAP0GAP0 = 1;
	int EX_HHMS1GAP0GAP1 = 2;
	int EX_HHMHS1GAP0GAP2 = 3;
	int EX_HHMHS1GAP1GAP0 = 4;
	int EX_HHMHS1GAP1GAP1 = 5;
	int EX_HHMHS1GAP1GAP2 = 6;
	int EX_HHMHS1GAP2GAP0 = 7;
	int EX_HHMHS1GAP2GAP1 = 8;
	int EX_HHMHS1GAP2GAP2 = 9;

	int EX_HHMHS2GAP0GAP0 = 28;
	int EX_HHMHS2GAP0GAP1 = 29;
	int EX_HHMHS2GAP0GAP2 = 30;
	int EX_HHMHS2GAP1GAP0 = 31;
	int EX_HHMHS2GAP1GAP1 = 32;
	int EX_HHMHS2GAP1GAP2 = 33;
	int EX_HHMHS2GAP2GAP0 = 34;
	int EX_HHMHS2GAP2GAP1 = 35;
	int EX_HHMHS2GAP2GAP2 = 36;

	int EX_HHMHS3GAP0GAP0 = 37;
	int EX_HHMHS3GAP0GAP1 = 38;
	int EX_HHMHS3GAP0GAP2 = 39;
	int EX_HHMHS3GAP1GAP0 = 40;
	int EX_HHMHS3GAP1GAP1 = 41;
	int EX_HHMHS3GAP1GAP2 = 42;
	int EX_HHMHS3GAP2GAP0 = 43;
	int EX_HHMHS3GAP2GAP1 = 44;
	int EX_HHMS3GAP2GAP2 = 45;

	int EX_HHLS1GAP0GAP0 = 46;
	int EX_HHLS1GAP0GAP1 = 47;
	int EX_HHLHS1GAP0GAP2 = 48;
	int EX_HHLHS1GAP1GAP0 = 49;
	int EX_HHLHS1GAP1GAP1 = 50;
	int EX_HHLHS1GAP1GAP2 = 51;
	int EX_HHLHS1GAP2GAP0 = 52;
	int EX_HHLHS1GAP2GAP1 = 53;
	int EX_HHLHS1GAP2GAP2 = 54;

	int EX_HHLHS2GAP0GAP0 = 55;
	int EX_HHLHS2GAP0GAP1 = 56;
	int EX_HHLHS2GAP0GAP2 = 57;
	int EX_HHLHS2GAP1GAP0 = 58;
	int EX_HHLHS2GAP1GAP1 = 59;
	int EX_HHLHS2GAP1GAP2 = 60;
	int EX_HHLHS2GAP2GAP0 = 61;
	int EX_HHLHS2GAP2GAP1 = 62;
	int EX_HHLHS2GAP2GAP2 = 63;

	int EX_HHLHS3GAP0GAP0 = 64;
	int EX_HHLHS3GAP0GAP1 = 65;
	int EX_HHLHS3GAP0GAP2 = 66;
	int EX_HHLHS3GAP1GAP0 = 67;
	int EX_HHLHS3GAP1GAP1 = 68;
	int EX_HHLHS3GAP1GAP2 = 69;
	int EX_HHLHS3GAP2GAP0 = 70;
	int EX_HHLHS3GAP2GAP1 = 71;
	int EX_HHLS3GAP2GAP2 = 72;

	int EX_HMMS1GAP0GAP0 = 73;
	int EX_HMMS1GAP0GAP1 = 74;
	int EX_HMMS1GAP0GAP2 = 75;
	int EX_HMMS1GAP1GAP0 = 76;
	int EX_HMMS1GAP1GAP1 = 77;
	int EX_HMMS1GAP1GAP2 = 78;
	int EX_HMMS1GAP2GAP0 = 79;
	int EX_HMMS1GAP2GAP1 = 80;
	int EX_HMMS1GAP2GAP2 = 81;

	int EX_HMMS2GAP0GAP0 = 82;
	int EX_HMMS2GAP0GAP1 = 83;
	int EX_HMMS2GAP0GAP2 = 84;
	int EX_HMMS2GAP1GAP0 = 85;
	int EX_HMMS2GAP1GAP1 = 86;
	int EX_HMMS2GAP1GAP2 = 87;
	int EX_HMMS2GAP2GAP0 = 88;
	int EX_HMMS2GAP2GAP1 = 89;
	int EX_HMMS2GAP2GAP2 = 90;

	int EX_HMMS3GAP0GAP0 = 91;
	int EX_HMMS3GAP0GAP1 = 92;
	int EX_HMMS3GAP0GAP2 = 93;
	int EX_HMMS3GAP1GAP0 = 94;
	int EX_HMMS3GAP1GAP1 = 95;
	int EX_HMMS3GAP1GAP2 = 96;
	int EX_HMMS3GAP2GAP0 = 97;
	int EX_HMMS3GAP2GAP1 = 98;
	int EX_HMMS3GAP2GAP2 = 99;

	int EX_HMLS1GAP0GAP0 = 100;
	int EX_HMLS1GAP0GAP1 = 101;
	int EX_HMLS1GAP0GAP2 = 102;
	int EX_HMLS1GAP1GAP0 = 103;
	int EX_HMLS1GAP1GAP1 = 104;
	int EX_HMLS1GAP1GAP2 = 105;
	int EX_HMLS1GAP2GAP0 = 106;
	int EX_HMLS1GAP2GAP1 = 107;
	int EX_HMLS1GAP2GAP2 = 108;

	int EX_HMLS2GAP0GAP0 = 109;
	int EX_HMLS2GAP0GAP1 = 110;
	int EX_HMLS2GAP0GAP2 = 111;
	int EX_HMLS2GAP1GAP0 = 112;
	int EX_HMLS2GAP1GAP1 = 113;
	int EX_HMLS2GAP1GAP2 = 114;
	int EX_HMLS2GAP2GAP0 = 115;
	int EX_HMLS2GAP2GAP1 = 116;
	int EX_HMLS2GAP2GAP2 = 117;

	int EX_HMLS3GAP0GAP0 = 118;
	int EX_HMLS3GAP0GAP1 = 119;
	int EX_HMLS3GAP0GAP2 = 120;
	int EX_HMLS3GAP1GAP0 = 121;
	int EX_HMLS3GAP1GAP1 = 122;
	int EX_HMLS3GAP1GAP2 = 123;
	int EX_HMLS3GAP2GAP0 = 124;
	int EX_HMLS3GAP2GAP1 = 125;
	int EX_HMLS3GAP2GAP2 = 126;

	int EX_HLLS1GAP0GAP0 = 127;
	int EX_HLLS1GAP0GAP1 = 128;
	int EX_HLLS1GAP0GAP2 = 129;
	int EX_HLLS1GAP1GAP0 = 130;
	int EX_HLLS1GAP1GAP1 = 131;
	int EX_HLLS1GAP1GAP2 = 132;
	int EX_HLLS1GAP2GAP0 = 133;
	int EX_HLLS1GAP2GAP1 = 134;
	int EX_HLLS1GAP2GAP2 = 135;

	int EX_HLLS2GAP0GAP0 = 136;
	int EX_HLLS2GAP0GAP1 = 137;
	int EX_HLLS2GAP0GAP2 = 138;
	int EX_HLLS2GAP1GAP0 = 139;
	int EX_HLLS2GAP1GAP1 = 140;
	int EX_HLLS2GAP1GAP2 = 141;
	int EX_HLLS2GAP2GAP0 = 142;
	int EX_HLLS2GAP2GAP1 = 143;
	int EX_HLLS2GAP2GAP2 = 144;

	int EX_HLLS3GAP0GAP0 = 145;
	int EX_HLLS3GAP0GAP1 = 146;
	int EX_HLLS3GAP0GAP2 = 147;
	int EX_HLLS3GAP1GAP0 = 148;
	int EX_HLLS3GAP1GAP1 = 149;
	int EX_HLLS3GAP1GAP2 = 150;
	int EX_HLLS3GAP2GAP0 = 151;
	int EX_HLLS3GAP2GAP1 = 152;
	int EX_HLLS3GAP2GAP2 = 153;

	int EX_MMMS1GAP0GAP0 = 154;
	int EX_MMMS1GAP0GAP1 = 155;
	int EX_MMMS1GAP0GAP2 = 156;
	int EX_MMMS1GAP1GAP0 = 157;
	int EX_MMMS1GAP1GAP1 = 158;
	int EX_MMMS1GAP1GAP2 = 159;
	int EX_MMMS1GAP2GAP0 = 160;
	int EX_MMMS1GAP2GAP1 = 161;
	int EX_MMMS1GAP2GAP2 = 162;

	int EX_MMMS2GAP0GAP0 = 163;
	int EX_MMMS2GAP0GAP1 = 164;
	int EX_MMMS2GAP0GAP2 = 165;
	int EX_MMMS2GAP1GAP0 = 166;
	int EX_MMMS2GAP1GAP1 = 167;
	int EX_MMMS2GAP1GAP2 = 168;
	int EX_MMMS2GAP2GAP0 = 169;
	int EX_MMMS2GAP2GAP1 = 170;
	int EX_MMMS2GAP2GAP2 = 171;

	int EX_MMMS3GAP0GAP0 = 172;
	int EX_MMMS3GAP0GAP1 = 173;
	int EX_MMMS3GAP0GAP2 = 174;
	int EX_MMMS3GAP1GAP0 = 175;
	int EX_MMMS3GAP1GAP1 = 176;
	int EX_MMMS3GAP1GAP2 = 177;
	int EX_MMMS3GAP2GAP0 = 178;
	int EX_MMMS3GAP2GAP1 = 178;
	int EX_MMMS3GAP2GAP2 = 180;

	int EX_MMLS1GAP0GAP0 = 181;
	int EX_MMLS1GAP0GAP1 = 182;
	int EX_MMLS1GAP0GAP2 = 183;
	int EX_MMLS1GAP1GAP0 = 184;
	int EX_MMLS1GAP1GAP1 = 185;
	int EX_MMLS1GAP1GAP2 = 186;
	int EX_MMLS1GAP2GAP0 = 187;
	int EX_MMLS1GAP2GAP1 = 188;
	int EX_MMLS1GAP2GAP2 = 189;

	int EX_MMLS2GAP0GAP0 = 190;
	int EX_MMLS2GAP0GAP1 = 191;
	int EX_MMLS2GAP0GAP2 = 192;
	int EX_MMLS2GAP1GAP0 = 193;
	int EX_MMLS2GAP1GAP1 = 194;
	int EX_MMLS2GAP1GAP2 = 195;
	int EX_MMLS2GAP2GAP0 = 196;
	int EX_MMLS2GAP2GAP1 = 197;
	int EX_MMLS2GAP2GAP2 = 198;

	int EX_MMLS3GAP0GAP0 = 199;
	int EX_MMLS3GAP0GAP1 = 200;
	int EX_MMLS3GAP0GAP2 = 201;
	int EX_MMLS3GAP1GAP0 = 202;
	int EX_MMLS3GAP1GAP1 = 203;
	int EX_MMLS3GAP1GAP2 = 204;
	int EX_MMLS3GAP2GAP0 = 205;
	int EX_MMLS3GAP2GAP1 = 206;
	int EX_MMLS3GAP2GAP2 = 207;

	int EX_MLLS1GAP0GAP0 = 208;
	int EX_MLLS1GAP0GAP1 = 209;
	int EX_MLLS1GAP0GAP2 = 210;
	int EX_MLLS1GAP1GAP0 = 211;
	int EX_MLLS1GAP1GAP1 = 212;
	int EX_MLLS1GAP1GAP2 = 213;
	int EX_MLLS1GAP2GAP0 = 214;
	int EX_MLLS1GAP2GAP1 = 215;
	int EX_MLLS1GAP2GAP2 = 216;

	int EX_MLLS2GAP0GAP0 = 217;
	int EX_MLLS2GAP0GAP1 = 218;
	int EX_MLLS2GAP0GAP2 = 219;
	int EX_MLLS2GAP1GAP0 = 220;
	int EX_MLLS2GAP1GAP1 = 221;
	int EX_MLLS2GAP1GAP2 = 222;
	int EX_MLLS2GAP2GAP0 = 223;
	int EX_MLLS2GAP2GAP1 = 224;
	int EX_MLLS2GAP2GAP2 = 225;

	int EX_MLLS3GAP0GAP0 = 226;
	int EX_MLLS3GAP0GAP1 = 227;
	int EX_MLLS3GAP0GAP2 = 228;
	int EX_MLLS3GAP1GAP0 = 229;
	int EX_MLLS3GAP1GAP1 = 230;
	int EX_MLLS3GAP1GAP2 = 231;
	int EX_MLLS3GAP2GAP0 = 232;
	int EX_MLLS3GAP2GAP1 = 233;
	int EX_MLLS3GAP2GAP2 = 234;

	int EX_LLLS1GAP0GAP1 = 235;
	int EX_LLLS1GAP0GAP2 = 236;
	int EX_LLLS1GAP1GAP0 = 237;
	int EX_LLLS1GAP1GAP1 = 238;
	int EX_LLLS1GAP1GAP2 = 239;
	int EX_LLLS1GAP2GAP0 = 240;
	int EX_LLLS1GAP2GAP1 = 241;
	int EX_LLLS1GAP2GAP2 = 242;

	int EX_LLLS2GAP0GAP0 = 243;
	int EX_LLLS2GAP0GAP1 = 244;
	int EX_LLLS2GAP0GAP2 = 245;
	int EX_LLLS2GAP1GAP0 = 246;
	int EX_LLLS2GAP1GAP1 = 247;
	int EX_LLLS2GAP1GAP2 = 248;
	int EX_LLLS2GAP2GAP0 = 249;
	int EX_LLLS2GAP2GAP1 = 250;
	int EX_LLLS2GAP2GAP2 = 251;

	int EX_LLLS3GAP0GAP0 = 252;
	int EX_LLLS3GAP0GAP1 = 253;
	int EX_LLLS3GAP0GAP2 = 254;
	int EX_LLLS3GAP1GAP0 = 255;
	int EX_LLLS3GAP1GAP1 = 256;
	int EX_LLLS3GAP1GAP2 = 257;
	int EX_LLLS3GAP2GAP0 = 258;
	int EX_LLLS3GAP2GAP1 = 259;
	int EX_LLLS3GAP2GAP2 = 260;

	int EX_SIZE = 261;

	String[] EX_ST = { "None", "HHHS1GAP0GAP0", "HHHS1GAP0GAP1", "HHHS1GAP0GAP2", "HHHS1GAP1GAP0", "HHHS1HHHP1GAP0",
			"HHHS1GAP1GAP1", "HHHS1GAP1GAP2", "HHHS1GAP2GAP0", "HHHS1GAP2GAP1", "HHHS1GAP2GAP2", "HHHS2GAP0GAP",

			"HHHS2GAP0GAP0", "HHHS2GAP0GAP1", "HHHS2GAP0GAP2", "HHHS2GAP1GAP0", "HHHS2GAP1GAP0", "HHHS2GAP1GAP1",
			"HHHS2GAP1GAP2", "HHHS2GAP2GAP0", "HHHS2GAP2GAP1", "HHHS2GAP2GAP2", "HHHS2GAP0GAP",

			"HHHS3GAP0GAP0", "HHHS3GAP0GAP1", "HHHS3GAP0GAP2", "HHHS3GAP1GAP0", "HHHS3GAP1GAP0", "HHHS3GAP1GAP1",
			"HHHS3GAP1GAP2", "HHHS3GAP2GAP0", "HHHS3GAP2GAP1", "HHHS1GAP2GAP2", "HHHS2GAP0GAP",

			"HHMS1GAP0GAP0", "HHMS1GAP0GAP1", "HHMS1GAP0GAP2", "HHMS1GAP1GAP0", "HHMS1HHMP1GAP0", "HHMS1GAP1GAP1",
			"HHMS1GAP1GAP2", "HHMS1GAP2GAP0", "HHMS1GAP2GAP1", "HHMS1GAP2GAP2", "HHMS2GAP0GAP",

			"HHMS2GAP0GAP0", "HHMS2GAP0GAP1", "HHMS2GAP0GAP2", "HHMS2GAP1GAP0", "HHMS2GAP1GAP0", "HHMS2GAP1GAP1",
			"HHMS2GAP1GAP2", "HHMS2GAP2GAP0", "HHMS2GAP2GAP1", "HHMS2GAP2GAP2", "HHMS2GAP0GAP",

			"HHMS3GAP0GAP0", "HHMS3GAP0GAP1", "HHMS3GAP0GAP2", "HHMS3GAP1GAP0", "HHMS3GAP1GAP0", "HHMS3GAP1GAP1",
			"HHMS3GAP1GAP2", "HHMS3GAP2GAP0", "HHMS3GAP2GAP1", "HHMS1GAP2GAP2", "HHMS2GAP0GAP",

			"HHLS1GAP0GAP0", "HHLS1GAP0GAP1", "HHLS1GAP0GAP2", "HHLS1GAP1GAP0", "HHLS1HHLP1GAP0", "HHLS1GAP1GAP1",
			"HHLS1GAP1GAP2", "HHLS1GAP2GAP0", "HHLS1GAP2GAP1", "HHLS1GAP2GAP2", "HHLS2GAP0GAP",

			"HHLS2GAP0GAP0", "HHLS2GAP0GAP1", "HHLS2GAP0GAP2", "HHLS2GAP1GAP0", "HHLS2GAP1GAP0", "HHLS2GAP1GAP1",
			"HHLS2GAP1GAP2", "HHLS2GAP2GAP0", "HHLS2GAP2GAP1", "HHLS2GAP2GAP2", "HHLS2GAP0GAP",

			"HHLS3GAP0GAP0", "HHLS3GAP0GAP1", "HHLS3GAP0GAP2", "HHLS3GAP1GAP0", "HHLS3GAP1GAP0", "HHLS3GAP1GAP1",
			"HHLS3GAP1GAP2", "HHLS3GAP2GAP0", "HHLS3GAP2GAP1", "HHLS1GAP2GAP2", "HHLS2GAP0GAP",

			"HMMS1GAP0GAP0", "HMMMS1GAP0GAP1", "HMMS1GAP0GAP2", "HMMS1GAP1GAP0", "HMMS1GAP1GAP0", "HMMS1GAP1GAP1",
			"HMMS1GAP1GAP2", "HMMS1GAP2GAP0", "HMMS1GAP2GAP1", "HMMS1GAP2GAP2", "HMMS2GAP0GAP",

			"HMMS1GAP0GAP0", "HMMS1GAP0GAP1", "HMMS1GAP0GAP2", "HMMS1GAP1GAP0", "HMMS1GAP1GAP0", "HMMS1GAP1GAP1",
			"HMMS1GAP1GAP2", "HMMS1GAP2GAP0", "HMMS1GAP2GAP1", "HMMS1GAP2GAP2", "HMMS2GAP0GAP",

			"HMMS1GAP0GAP0", "HMMS1GAP0GAP1", "HMMS1GAP0GAP2", "HMMS1GAP1GAP0", "HMMS1GAP1GAP0", "HMMS1GAP1GAP1",
			"HMMS1GAP1GAP2", "HMMS1GAP2GAP0", "HMMS1GAP2GAP1", "HMMS1GAP2GAP2", "HMMS2GAP0GAP",

			"HMLS1GAP0GAP0", "HMLS1GAP0GAP1", "HMLS1GAP0GAP2", "HMLS1GAP1GAP0", "HMLS1GAP1GAP0", "HMLS1GAP1GAP1",
			"HMLS1GAP1GAP2", "HMLS1GAP2GAP0", "HMLS1GAP2GAP1", "HMLS1GAP2GAP2", "HMLS2GAP0GAP",

			"HMLS1GAP0GAP0", "HMLS1GAP0GAP1", "HMLS1GAP0GAP2", "HMLS1GAP1GAP0", "HMLS1GAP1GAP0", "HMLS1GAP1GAP1",
			"HMLS1GAP1GAP2", "HMLS1GAP2GAP0", "HMLS1GAP2GAP1", "HMLS1GAP2GAP2", "HMLS2GAP0GAP",

			"HMLS1GAP0GAP0", "HMLS1GAP0GAP1", "HMLS1GAP0GAP2", "HMLS1GAP1GAP0", "HMLS1GAP1GAP0", "HMLS1GAP1GAP1",
			"HMLS1GAP1GAP2", "HMLS1GAP2GAP0", "HMLS1GAP2GAP1", "HMLS1GAP2GAP2", "HMLS2GAP0GAP",

			"HLLS1GAP0GAP0", "HLLS1GAP0GAP1", "HLLS1GAP0GAP2", "HLLS1GAP1GAP0", "HLLS1GAP1GAP0", "HLLS1GAP1GAP1",
			"HLLS1GAP1GAP2", "HLLS1GAP2GAP0", "HLLS1GAP2GAP1", "HLLS1GAP2GAP2", "HLLS2GAP0GAP",

			"HLLS2GAP0GAP0", "HLLS2GAP0GAP1", "HLLS2GAP0GAP2", "HLLS2GAP1GAP0", "HLLS2GAP1GAP0", "HLLS2GAP1GAP1",
			"HLLS2GAP1GAP2", "HLLS2GAP2GAP0", "HLLS2GAP2GAP1", "HLLS2GAP2GAP2", "HLLS2GAP0GAP",

			"HLLS3GAP0GAP0", "HLLS3GAP0GAP1", "HLLS3GAP0GAP2", "HLLS3GAP1GAP0", "HLLS3GAP1GAP0", "HLLS3GAP1GAP1",
			"HLLS3GAP1GAP2", "HLLS3GAP2GAP0", "HLLS3GAP2GAP1", "HLLS1GAP2GAP2", "HLLS2GAP0GAP",

			"MMMS1GAP0GAP0", "MMMS1GAP0GAP1", "MMMS1GAP0GAP2", "MMMS1GAP1GAP0", "MMMS1GAP1GAP0", "MMMS1GAP1GAP1",
			"MMMS1GAP1GAP2", "MMMS1GAP2GAP0", "MMMS1GAP2GAP1", "MMMS1GAP2GAP2", "MMMS2GAP0GAP",

			"MMMS1GAP0GAP0", "MMMS1GAP0GAP1", "MMMS1GAP0GAP2", "MMMS1GAP1GAP0", "MMMS1GAP1GAP0", "MMMS1GAP1GAP1",
			"MMMS1GAP1GAP2", "MMMS1GAP2GAP0", "MMMS1GAP2GAP1", "MMMS1GAP2GAP2", "MMMS2GAP0GAP",

			"MMMS1GAP0GAP0", "MMMS1GAP0GAP1", "MMMS1GAP0GAP2", "MMMS1GAP1GAP0", "MMMS1GAP1GAP0", "MMMS1GAP1GAP1",
			"MMMS1GAP1GAP2", "MMMS1GAP2GAP0", "MMMS1GAP2GAP1", "MMMS1GAP2GAP2", "MMMS2GAP0GAP",

			"MMLS1GAP0GAP0", "MMLS1GAP0GAP1", "MMLS1GAP0GAP2", "MMLS1GAP1GAP0", "MMLS1GAP1GAP0", "MMLS1GAP1GAP1",
			"MMLS1GAP1GAP2", "MMLS1GAP2GAP0", "MMLS1GAP2GAP1", "MMLS1GAP2GAP2", "MMLS2GAP0GAP",

			"MMLS1GAP0GAP0", "MMLS1GAP0GAP1", "MMLS1GAP0GAP2", "MMLS1GAP1GAP0", "MMLS1GAP1GAP0", "MMLS1GAP1GAP1",
			"MMLS1GAP1GAP2", "MMLS1GAP2GAP0", "MMLS1GAP2GAP1", "MMLS1GAP2GAP2", "MMLS2GAP0GAP",

			"MMLS1GAP0GAP0", "MMLS1GAP0GAP1", "MMLS1GAP0GAP2", "MMLS1GAP1GAP0", "MMLS1GAP1GAP0", "MMLS1GAP1GAP1",
			"MMLS1GAP1GAP2", "MMLS1GAP2GAP0", "MMLS1GAP2GAP1", "MMLS1GAP2GAP2", "MMLS2GAP0GAP",

			"MLLS1GAP0GAP0", "MLLS1GAP0GAP1", "MLLS1GAP0GAP2", "MLLS1GAP1GAP0", "MLLS1GAP1GAP0", "MLLS1GAP1GAP1",
			"MLLS1GAP1GAP2", "MLLS1GAP2GAP0", "MLLS1GAP2GAP1", "MLLS1GAP2GAP2", "MLLS2GAP0GAP",

			"MLLS1GAP0GAP0", "MLLS1GAP0GAP1", "MLLS1GAP0GAP2", "MLLS1GAP1GAP0", "MLLS1GAP1GAP0", "MLLS1GAP1GAP1",
			"MLLS1GAP1GAP2", "MLLS1GAP2GAP0", "MLLS1GAP2GAP1", "MLLS1GAP2GAP2", "MLLS2GAP0GAP",

			"MLLS1GAP0GAP0", "MLLS1GAP0GAP1", "MLLS1GAP0GAP2", "MLLS1GAP1GAP0", "MLLS1GAP1GAP0", "MLLS1GAP1GAP1",
			"MLLS1GAP1GAP2", "MLLS1GAP2GAP0", "MLLS1GAP2GAP1", "MLLS1GAP2GAP2", "MLLS2GAP0GAP",

			"LLLS1GAP0GAP0", "LLLS1GAP0GAP1", "LLLS1GAP0GAP2", "LLLS1GAP1GAP0", "LLLS1GAP1GAP0", "LLLS1GAP1GAP1",
			"LLLS1GAP1GAP2", "LLLS1GAP2GAP0", "LLLS1GAP2GAP1", "LLLS1GAP2GAP2", "LLLS2GAP0GAP",

			"LLLS1GAP0GAP0", "LLLS1GAP0GAP1", "LLLS1GAP0GAP2", "LLLS1GAP1GAP0", "LLLS1GAP1GAP0", "LLLS1GAP1GAP1",
			"LLLS1GAP1GAP2", "LLLS1GAP2GAP0", "LLLS1GAP2GAP1", "LLLS1GAP2GAP2", "LLLS2GAP0GAP",

			"LLLS1GAP0GAP0", "LLLS1GAP0GAP1", "LLLS1GAP0GAP2", "LLLS1GAP1GAP0", "LLLS1GAP1GAP0", "LLLS1GAP1GAP1",
			"LLLS1GAP1GAP2", "LLLS1GAP2GAP0", "LLLS1GAP2GAP1", "LLLS1GAP2GAP2", "LLLS2GAP0GAP" };

	/*- **************************************************************************** 
	* Actions to a player - Post Flop
	*******************************************************************************/
	int POSTFLOP_CHECK = 0;

	int POSTFLOP_BET1 = 1;

	int POSTFLOP_BET2 = 2;

	int POSTFLOP_BET3 = 3;

	int POSTFLOP_BET4 = 4;

	int POSTFLOP_ALLIN = 5;

	int POSTFLOP_CALL_ALLIN = 6;

	String[] POSTFLOP_ST = { "Check", "1 Bet", "2 Bet", "3 Bet", "4 Bet", "All-In", "Call All-In" };

	/*- *****************************************************************************
	* Best Draws
	 ******************************************************************************/
	int BEST_NONE = 0;

	int BEST_OVERCARDS = 1;

	int BEST_ACE_HIGH = 2;

	int BEST_WEAK_PAIR = 3;

	int BEST_MIDDLE_PAIR = 4;

	int BEST_POCKET_PAIR_BELOW_TP = 5;

	int BEST_OVER_PAIR = 6;

	int BEST_TOP_PAIR = 7;

	int BEST_TWO_PAIR = 8;

	int BEST_SET = 9;

	int BEST_GUTSHOT = 10;

	int BEST_GUTSHOT_PAIR = 11;

	int BEST_GUTSHOT_HIGH = 12;

	int BEST_STRAIGHT_DRAW = 13;

	int BEST_OESD = 14;

	int BEST_OESD_PAIR = 15;

	int BEST_FLUSH_DRAW = 16;

	int BEST_FLUSH_DRAW_PAIR = 17;

	int BEST_FLUSH_DRAW_GUTSHOT = 18;

	int BEST_FLUSH_DRAW_OESD = 19;

	int BEST_SIZE = 20;

	String[] BEST_ST = { "None", "Over cards", "Ace high", "Pair", "Weak pair", "Middle pair",
			"Pocket pair below top pair", "Over pair", "Top pair", "Two pair", "Set", "Gutshot", "Gutshot + pair",
			"Gutshot + high", "Straight draw", "OESD", "OESD + pair", "Flush draw", "Flush draw + pair",
			"Flush draw + gutshot", "Flush draw + OESD" };

	/*- ****************************************************************************
	 * Wet Dry
	 ******************************************************************************/
	int DRY = 0;

	int WET = 1;

	int NOT_WET_DRY = 2;

	int WET_DRY_SIZE = 3;

	String[] WET_DRY_ST = { "Dry", "Wet", "Not Wet or Dry" };

	/*- *****************************************************************************
	 * Strategy what I have
	  ******************************************************************************/
	int HAVE_NONE = 0;
	int HAVE_GUTSHOT_DRAW = 1;
	int HAVE_STRAIGHT_DRAW = 2;
	int HAVE_OESD = 3;
	int HAVE_FLUSH_DRAW = 4;
	int HAVE_FLUSH_OESD_DRAW = 5;
	int HAVE_BOTTOM_PAIR = 6;
	int HAVE_MIDDLE_PAIR = 7;
	int HAVE_TOP_PAIR = 8;
	int HAVE_OVER_PAIR = 9;
	int HAVE_BOARD_PAIR_PLUS_UNDER_PAIR = 10;
	int HAVE_BOARD_PAIR_PLUS_OVER_PAIR = 11;
	int HAVE_BOTTOM_TWO_PAIR = 12;
	int HAVE_TOP_AND_BOTTOM_TWO_PAIR = 13;
	int HAVE_TOP_TWO_PAIR = 14;
	int HAVE_BOARD_SET = 15;
	int HAVE_SET = 16;
	int HAVE_STRAIGHT = 17;
	int HAVE_FLUSH = 18;
	String[] HAVE_ST = { "None", "Gutshot draw", "Straight draw", "OESD", "Flush draw", "Flush OESD draw",
			"Bottom pair", "Middle pair", "Top pair", "Over pair", "Board pair + under pair", "Board pair + over pair",
			"Bottom 2 pair", "Top +bottom 2 pair", "Top two pair", "Board set", "Set", "Straight", "Flush" };

	/*- *****************************************************************************
	 *Rules
	 ******************************************************************************/
	int RULE_OP_CHECK = 0;
	int RULE_OP_BET1 = 1;
	int RULE_OP_BET2 = 2;
	int RULE_OP_BET3 = 3;
	int RULE_OP_BET4 = 4;
	int RULE_OP_ALLIN = 5;
	int RULE_OP_ACTIONS = 6;
	/*- Returned Values. */

	int RULE_BET = 0;
	int RULE_CALL = 1;
	int RULE_ALLIN = 2;
	int RULE_NO_ACTION = 3;

	/*- **************************************************************************
	* Flop texture Strength Suitedness
	******************************************************************************/
	int STRENGTH_SUITEDNESS_HSUITEDH_MONOTONE = 0;

	int STRENGTH_SUITEDNESS_HIGH_TWO_TONE = 1;

	int STRENGTH_SUITEDNESS_HIGH_RAINBOW = 2;

	int STRENGTH_SUITEDNESS_MEDIUM_MONOTONE = 3;

	int STRENGTH_SUITEDNESS_MEDIUM_TWO_TONE = 4;

	int STRENGTH_SUITEDNESS_MEDIUM_RAINBOW = 5;

	int STRENGTH_SUITEDNESS_LOW_MONOTONE = 6;

	int STRENGTH_SUITEDNESS_LOW_TWO_TONE = 7;

	int STRENGTH_SUITEDNESS_LOW_RAINBOW = 8;

	int STRENGTH_SUITEDNESS_SIZE = 9;
	String[] STRENGTH_SUITEDNESS_ST = { "High Monotone", "High Two-Tone", "High Rainbow", "Medium Monotne",
			"Medium Two-Tone", "Medium Rainbow", "Low Monotone", "Low Two-Tone", "Low Rainbow" };

	/*- *****************************************************************************
	 * Types of 1755 possible Flops  
	 ******************************************************************************/
	int SET = 0;

	int SUITED_PAIR = 1;

	int ALL_SUITED = 2;

	int ALL_OFFSUIT = 3;

	int TWO_SUITED_LOW = 4;

	int TWO_SUITED_HIGH = 5;

	int TWO_SUITED_HIGH_LOW = 6;

	int TYPE_OF_1755_SIZE = 7;

	String[] TYPE_OF_1755_ST = { "Set", "Suited pair", "All suited", "All offsuit", "Two suited low", "Two suited high",
			"Two suited high low" };

	/*- *****************************************************************************
	 * Ed Miller Flop types 
	  ******************************************************************************/
	int TYPE_NONE = 0;

	int TYPE1 = 1;

	int TYPE2 = 2;

	int TYPE3 = 3;

	String[] TYPE_ST = { "None", "Type 1", "Type 2", "Type 3" };

	/*- *****************************************************************************
	 * Hand value
	  ******************************************************************************/
	int HAND_VALUE_NOTHING = 0;

	int HAND_VALUE_POOR = 1;

	int HAND_VALUE_FAIR = 2;

	int HAND_VALUE_OK = 3;

	int HAND_VALUE_ABOVE_AVERAGE = 4;

	int HAND_VALUE_VERY_GOOD = 5;

	int HAND_VALUE_GREAT = 6;

	int HAND_VALUE_EXCELENT = 7;

	int HAND_VALUE_THE_NUTS = 8;

	int HAND_VALUE_SIZE = 8;

	String[] HAND_VALUE_ST = { "Nothing", "Poor", "Poor", "Fair", "OK", "Above average", "Very good", "Great",
			"Excelent", "The NUTS" };

	String[] TYPE_OF_FLOP_ST = { "Coordinated", "Uncoordinated", "Paired", "Rainbow", "Monotone" };

	String[] PLAYER_ACTIONS_ST = { "Fold", "Check", "1 Bet", "Call 1 Bet", "Limp", "2 Bet", "Open", "Call 2 Bet",
			"Call", "3 Bet", "Call 3 Bet", "4 Bet", "Call 4 Bet", "All-In", "Call All-in" };

	/*- *****************************************************************************
	 * HHHand unique definitions for Actions
	 ******************************************************************************/
	static final int ACTION_ERROR = 0;
	static final int ACTION_FOLD = 1;
	static final int ACTION_CHECK = 2;
	static final int ACTION_CALL = 3;
	static final int ACTION_CALL_ALLIN = 4;
	static final int ACTION_BET = 5;
	static final int ACTION_BET_ALLIN = 6;
	static final int ACTION_RAISE = 7;
	static final int ACTION_RAISE_ALLIN = 8;
	static final int ACTION_POST = 9;
	static final int ACTION_SHOWS_STREET = 10;
	static final int ACTION_UNCALLED_RETURNED_TO = 11;
	static final int ACTION_COLLECTED = 12;
	static final int ACTION_WON = 13;
	// Applies to Showdown only
	static final int ACTION_WON_SIDE_POT = 14;
	static final int ACTION_COLLECTED_SIDE_POT = 15;
	static final int ACTION_COLLECTED_MAIN_POT = 16;
	static final int ACTION_COLLECTED_POT = 17;
	static final int ACTION_LAST_MONEY = 18;
	static final int ACTION_SHOWS_SHOWDOWN = 19;
	// Ignored
	static final int ACTION_DOES_NOT_SHOW = 20;
	static final int ACTION_SAID = 21;
	static final int ACTION_NO_ACTION = 22;
	static final int ACTION_MAX_ACTIONS = 23;

	static final String[] ACTIONS_ST = { "*ERROR*", "Fold", "Check", "Call", "Call All-in short", "Bet",
			"Does not show", "Raise", "Raise All-in", "Post", "Shows on Street", "Uncalled Returned To", "Collected",
			"Won", "Won Side Pot", "Collected Side Pot", "Collected Main Pot", "Collected Pot", "Shows on Showdown",
			"Does not show", "Said", "No Action - like check" };

	static final int ERR_OK = 0;

	static final int ERR_IN_VS_TOTAL_POT = 1;

	static final int ERR_IN_VS_SHOWDOWN = 2;

	static final int ERR_IN_VS_SUMMARY = 3;

	static final int ERR_START_STACKS_FINAL_STACKS = 4;

	static final int ERR_SUMMARY_WON_TOTAL_POT = 5;

	static final int ERR_TOTAL_POT_MONEY_OUT_SREETS = 6;

	static final int ERR_MONEY_IN_STREETS_MONEY_OUT_STREETS = 7;

	static final int COLLECTED_STREET_COLLECTED_SUMMARY = 8;

	static final int ERR_MAX = 9;

	static final String[] ERR_ST = { "OK", "Money in streets vs totalPot", "Money in streets vs Showdown",
			"Money in streets vs Summary", "Start stacks vs Final stacks", "Won summary vs totalPot",
			"totalPot vs money out streets", "moneyInStreets != moneyOutStreets", "CollectedStreet vs collectedSummary",
			"?" };

	/*- *****************************************************************************
	 *Colors for GUI
	  ******************************************************************************/
	Color RED = Color.red;

	Color BLUE = Color.blue;

	Color GREEN = Color.green;

	Color YELLOW = Color.yellow;

	Color ORANGE = Color.orange;

	Color PINK = Color.pink;

	Color MAGENTA = Color.magenta;

	Color CYAN = Color.cyan;

	Color GRAY = Color.gray;

	Color DARK_GRAY = Color.darkGray;

	Color LIGHT_GRAY = Color.lightGray;

	Color WHITE = Color.white;

	Color BLACK = Color.black;

	Color IVORY = new Color(255, 255, 240);

	Color BEIGE = new Color(245, 245, 220);

	Color WHEAT = new Color(245, 222, 179);

	Color TAN = new Color(210, 180, 140);

	Color KHAKI = new Color(195, 176, 145);

	Color SILVER = new Color(192, 192, 192);

	Color DARK_SLATE_GRAY = new Color(47, 79, 79);

	Color SLATE_GRAY = new Color(112, 128, 144);

	Color LIGHT_SLATE_GRAY = new Color(119, 136, 153);

	Color NAVY = new Color(0, 0, 128);

	Color MIDNIGHT_BLUE = new Color(25, 25, 112);

	Color CORNFLOWER_BLUE = new Color(100, 149, 237);

	Color STEEL_BLUE = new Color(70, 130, 180);

	Color ROYAL_BLUE = new Color(65, 105, 225);

	Color DODGER_BLUE = new Color(30, 144, 255);

	Color DEEP_SKY_BLUE = new Color(0, 191, 255);

	Color SKY_BLUE = new Color(135, 206, 235);

	Color LIGHT_SKY_BLUE = new Color(135, 206, 250);

	Color TEAL = new Color(0, 128, 128);

	Color DARK_CYAN = new Color(0, 139, 139);

	Color CADET_BLUE = new Color(95, 158, 160);

	Color AQUAMARINE = new Color(127, 255, 212);

	Color TURQUOISE = new Color(64, 224, 208);

	Color MEDIUM_TURQUOISE = new Color(72, 209, 204);

	Color PALE_TURQUOISE = new Color(175, 238, 238);

	Color GREEN_YELLOW = new Color(173, 255, 47);

	Color CHARTREUSE = new Color(127, 255, 0);

	Color LAWN_GREEN = new Color(124, 252, 0);

	Color LIME_GREEN = new Color(50, 205, 50);

	Color FOREST_GREEN = new Color(34, 139, 34);

	Color OLIVE_DRAB = new Color(107, 142, 35);

	Color DARK_KHAKI = new Color(189, 183, 107);

	Color GOLDEN_ROD = new Color(218, 165, 32);

	Color DARK_GOLDEN_ROD = new Color(184, 134, 11);

	Color SADDLE_BROWN = new Color(139, 69, 19);

	Color SIENNA = new Color(160, 82, 45);

	Color CHOCOLATE = new Color(210, 105, 30);

	Color PERU = new Color(205, 133, 63);

	Color ROSY_BROWN = new Color(188, 143, 143);

	Color SANDY_BROWN = new Color(244, 164, 96);

	Color LIGHT_SALMON = new Color(255, 160, 122);

	Color SALMON = new Color(250, 128, 114);

	Color DARK_SALMON = new Color(233, 150, 122);

	Color ORANGE_RED = new Color(255, 69, 0);

	Color TOMATO = new Color(255, 99, 71);

	Color CORAL = new Color(255, 127, 80);

	Color DARK_ORANGE = new Color(255, 140, 0);

	Color LIGHT_CORAL = new Color(240, 128, 128);

	Color HOT_PINK = new Color(255, 105, 180);

	Color DEEP_PINK = new Color(255, 20, 147);

	Color LIGHT_PINK = new Color(255, 182, 193);

	Color PLUM = new Color(221, 160, 221);

	Color VIOLET = new Color(238, 130, 238);

	Color ORCHID = new Color(218, 112, 214);

	Color MEDIUM_ORCHID = new Color(186, 85, 211);

	Color DARK_ORCHID = new Color(153, 50, 204);

	Color PURPLE = new Color(128, 0, 128);

	Color MEDIUM_PURPLE = new Color(147, 112, 219);

	Color THISTLE = new Color(216, 191, 216);

	Color GHOST_WHITE = new Color(248, 248, 255);

	Color LAVENDER = new Color(230, 230, 250);

	Color MISTY_ROSE = new Color(255, 228, 225);

	Color ANTIQUE_WHITE = new Color(250, 235, 215);

}
