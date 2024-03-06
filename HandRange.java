package holdemevaluator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

/*- ******************************************************************************
 * This class defines a range of hands The hands are in a Container class.
 * This array represents the commonly used 13 X 13 matrix to represent suited,
 * ofsuit, and pairs
 * An element is -1 if not in the array
 * Positive values have specific meaning.
 * This class may be used where there are different meanings to the value in the int array.
 *
 * The int values are < 0 for hand is not this range and >= 0 for hand is in this range.
 *
 * In a future revision I may combine ranges
 * For example:
 * 		1		hand is in this range
 * 		10	Hand is limp
 * 		20	Hand is open
 *		21	Hand is open if 1 opponent and we are IP
 *		22	Hand is open if 1 opponent and we are OOP
 * 		30	Hand is call
 * 		40	Hand is bet3
 *
 * @author PEAK_
****************************************************************************** */

public class HandRange implements Constants, java.io.Serializable {
	static final long serialVersionUID = 1234567L;

	private static final Color open = Color.CYAN;

	/*- ****************************************************************************
	 *  Array of all possible 2 card combinations - 169 hands.  From PioSolver
	*******************************************************************************/
	static final int[] handsArrayPioSolver = { UTG, UTG, UTG, UTG, UTG, UTG, UTG, UTG, UTG, UTG, UTG, UTG, UTG, UTG,
			UTG, UTG, UTG, UTG, MP, MP, CO, CO, CO, BU, BU, BU, UTG, UTG, UTG, UTG, UTG, MP, CO, BU, BU, BU, SB, SB, SB,
			UTG, MP, MP, UTG, UTG, MP, CO, BU, SB, SB, SB, SB, SB, UTG, CO, CO, CO, UTG, UTG, CO, BU, BU, SB, SB, SB,
			SB, BU, BU, BU, BU, BU, UTG, MP, CO, BU, SB, SB, NO, NO, BU, SB, SB, SB, SB, BU, UTG, MP, CO, BU, SB, NO,
			NO, BU, SB, SB, SB, SB, SB, SB, UTG, MP, CO, SB, SB, NO, BU, SB, SB, NO, NO, NO, SB, SB, UTG, UTG, BU, SB,
			NO, BU, SB, SB, NO, NO, NO, NO, SB, SB, UTG, MP, BU, SB, BU, SB, NO, NO, NO, NO, NO, NO, SB, SB, MP, BU, SB,
			SB, NO, NO, NO, NO, NO, NO, NO, NO, NO, NO, CO, SB, SB, NO, NO, NO, NO, NO, NO, NO, NO, NO, NO, NO, CO };
	/*-******************************************************************************
	* Array of all possible 2 card combinations - 169 hands.
	* Indexed by handIndex
	****************************************************************************** */
	//@formatter:off
	static final String[] handsArray = {
			"AA", "AKs", "AQs", "AJs", "ATs", "A9s", "A8s", "A7s", "A6s", "A5s", "A4s","A3s", "A2s",
			"AKo", "KK", "KQs", "KJs", "KTs", "K9s", "K8s", "K7s", "K6s", "K5s", "K4s", "K3s", "K2s",
			"AQo", "KQo", "QQ", "QJs", "QTs", "Q9s", "Q8s", "Q7s", "Q6s", "Q5s", "Q4s", "Q3s", "Q2s",
			"AJo", "KJo","QJo", "JJ", "JTs", "J9s", "J8s", "J7s", "J6s", "J5s", "J4s", "J3s", "J2s",
			"ATo", "KTo", "QTo", "JTo","TT", "T9s", "T8s", "T7s", "T6s", "T5s", "T4s", "T3s", "T2s",
			"A9o", "K9o", "Q9o", "J9o", "T9o", "99","98s", "97s", "96s", "95s", "94s", "93s", "92s",
			"A8o", "K8o", "Q8o", "J8o", "T8o", "98o", "88", "87s","86s", "85s", "84s", "83s", "82s",
			"A7o", "K7o", "Q7o", "J7o", "T7o", "97o", "87o", "77", "76s", "75s",	"74s", "73s", "72s",
			"A6o", "K6o", "Q6o", "J6o", "T6o", "96o", "86o", "76o", "66", "65s", "64s", "63s","62s",
			"A5o", "K5o", "Q5o", "J5o", "T5o", "95o", "85o", "75o", "65o", "55", "54s", "53s", "52s",
			"A4o","K4o", "Q4o", "J4o", "T4o", "94o", "84o", "74o", "64o", "54o", "44", "43s", "42s",
			"A3o", "K3o", "Q3o",	"J3o", "T3o", "93o", "83o", "73o", "63o", "53o", "43o", "33", "32s",
			"A2o", "K2o", "Q2o", "J2o", "T2o","92o", "82o", "72o", "62o", "52o", "42o", "32o", "22" };
	/*- ****************************************************************************
	* Equilab Equity vs. 5 random hands - Playability
	*******************************************************************************/
	static final String[] playabilityArray = {
			"AA", "KK", "QQ", "JJ", "AKs", "TT", "AQs", "KQs", "AKo", "AJs", "KJs",	"ATs", "99", "QJs", "AQo",
			"KTs", "QTs", "KQo", "JTs", "AJo", "A9s", "88", "KJo", "A8s", "K9s", "ATo",
			"QJo", "Q9s", "A7s", "T9s", "J9s", "A5s", "KTo", "77", "A4s", "A6s", "QTo", "JTo", "K8s", "A3s", "Q8s",
			"K7s", "A2s", "T8s", "J8s", "98s", "A9o", "66", "K6s", "K5s", "K9o", "A8o", "Q7s", "K4s", "87s", "T7s",
			"Q9o", "97s", "T9o", "J7s", "J9o", "K3s", "55", "Q6s", "A7o", "K2s", "A5o", "Q5s", "76s", "86s", "Q4s",
			"A4o", "A6o", "96s", "T6s", "K8o", "44", "J6s", "Q3s", "65s", "A3o", "J5s", "T8o", "Q8o", "Q2s", "75s",
			"J8o", "98o", "K7o", "54s", "J4s", "33", "A2o", "85s", "J3s", "T5s", "64s", "95s", "K6o", "J2s", "T4s",
			"22", "53s", "74s", "K5o", "T3s", "87o", "97o", "Q7o", "T7o", "84s", "J7o", "T2s", "43s", "K4o", "63s",
			"94s", "Q6o", "K3o", "93s", "76o", "52s", "73s", "92s", "Q5o", "86o", "K2o", "42s", "83s", "96o", "T6o",
			"82s", "65o", "Q4o", "62s", "J6o", "32s", "Q3o", "75o", "72s", "J5o", "64o", "Q2o", "85o", "J4o", "54o",
			"95o", "T5o", "J3o", "53o", "T4o", "J2o", "74o", "T3o", "43o", "84o", "63o", "T2o", "94o", "93o", "52o",
			"73o", "92o", "42o", "83o", "62o", "82o", "32o", "72o" };
	//@formatter:on

	/*- ******************************************************************************
	 * Test and debug
	 ****************************************************************************** */
	private static int x = 50;
	private static int y = 50;
	private static String title = "";

	/*- ******************************************************************************
	 * This array represents the commonly used 13 X 13 matrix to represent suited,
	 * ofsuit, and pairs
	 * -1 if not in range
	 * + in range
	 ****************************************************************************** */
	int[] rangeArray = new int[HANDS];

	/*- ******************************************************************************
	 * Summarized data for one specific range
	 ****************************************************************************** */
	String path = ""; // Path to file

	int playerType = 0; // HERO, FISH, NIT, LAG, TAG, AVERAGE, SHARK

	int position = 0; // SB, BB, UTG, MP, CO, BU

	int range = 0; // RANGE_LIMP, RANGE_CALL, RANGE_BET3, RANGE_CALL_BET3, RANGE_BET4,
					// RANGE_CALL_BET4, RANGE_ALLIN, RANGE_CALL_ALLIN
	int combos = 0; // Number of combinations in this range

	double percentage = 0.0; // Percentage of combinations in this range

	int gapsCount = 0; // Number of gaps

	int lowPlayability = 0;

	/*-  ****************************************************************************
	* Create playabilityArrayNumeric 
	* The array will contain values from 1 - 169
	* 
	* playabilityArrayNumeric
	* AA is 168, KK is 167, AK is 164,  is 272o is 0 (Easier to understand )
	* Create  playabilityArrayOrder 
	* 
	* playabilityArrayOrder
	* AA is 0, KK is 1, AKs is 4, TT is 5, 72o is 168.   the reverse of playabilityArrayNumeric 
	* The array index into playabilityArray a String array of hands in playability order.
	* 
	* These are static arrays created only once the first time constructor is called.
	 ****************************************************************************** */
	void createPlayabilityArrays() {
		if (staticRangesCreated)
			return;
		staticRangesCreated = true;
		int k = 0;
		for (int i = 0; i < HANDS; ++i) {
			k = getHandStringToIndex(playabilityArray[i]);
			playabilityArrayNumeric[k] = 168 - k;
			playabilityArrayOrder[i] = k;
		}
	}

	private static final int[] playabilityArrayNumeric = new int[HANDS];

	private static final int[] playabilityArrayOrder = new int[HANDS];

	private static boolean staticRangesCreated = false;

	/*- ******************************************************************************
	 * Constructor.
	  *******************************************************************************/
	HandRange() {
		if (!staticRangesCreated) {
			createPlayabilityArrays();
			staticRangesCreated = true;
		}
	}

	/*- ******************************************************************************
	* Initialize 
	*  *******************************************************************************/
	void initialize(int playerType, int posituion, int range) {
		this.playerType = playerType;
		this.position = posituion;
		this.range = range;
		if (!staticRangesCreated) {
			createPlayabilityArrays();
			staticRangesCreated = true;
		}
		buildRangePlayabilityCombos();
		this.combos = getCombos();
		this.percentage = getRangePercent(this.combos);
	}

	/*-  ****************************************************************************
	 * Build Range using Playability for the number of combinations specified.
	 * Count the number of combos in this current range.
	 * Clear the range.
	 * Add hands in playability order until the previous numbers of combos.
	 * This keeps the range at the same number of combos and the same percentage
	 * as previously BUT range is now entirely in playability order.
	 * Arg0 - Number of combos in the range
	  *****************************************************************************/
	void buildRangePlayabilityCombos() {
		var k = 0;
		var kk = 0;
		var c = 0;
		int t = 0;
		var target = getCombos();

		this.clearRange();
		for (var i = 0; i < HANDS; ++i) {
			k = playabilityArrayOrder[i];
			kk = playabilityArrayNumeric[i + 1];
			if (getHandType(kk) == SUITED) {
				t += 4;
			}
			if (getHandType(kk) == PAIR) {
				t += 6;
			}
			if (getHandType(kk) == OFFSUIT) {
				t += 12;
			}
			this.rangeArray[k] = IN_RANGE;
			this.combos = getCombos();
			if (this.combos >= target) {
				this.lowPlayability = HANDS - i;
				break;
			}
		}
	}

	/*- ****************************************************************************
	 * Update after range edited.
	 * Call here from editor.
	 *******************************************************************************/
	void updateAfterRangeEdited() {
		updateRangeData();
	}

	/*- ****************************************************************************
	 * Get range lowest playability. This is the worst hand that we will play in this range.
	 ******************************************************************************/
	private void getRangeLow() {
		// Find lowest playabilityArray element with a hand
		this.lowPlayability = 999;
		for (int i = 0; i < HANDS; i++) {
			if (this.rangeArray[i] > 0 && playabilityArrayNumeric[i] < this.lowPlayability) {
				this.lowPlayability = playabilityArrayNumeric[i];
			}
		}
		System.out.println("//GGG " + this.lowPlayability);
	}

	/*- ****************************************************************************
	 * This method will count the number of playability gaps in this range.
	 *******************************************************************************/
	int countGaps() {
		getRangeLow();
		// Now checks for gaps in playability
		this.gapsCount = 0;
		for (var i = 0; i < HANDS; ++i) {
			if (this.rangeArray[i] < 0) {
				if (playabilityArrayNumeric[i] >= this.lowPlayability) {
					++this.gapsCount;
				}
			}
		}
		return this.gapsCount;
	}

	/*- ****************************************************************************
	 * This method is only used by the HandRangeConversion Class.
	 * It is for ranges that are included in the HandRangeCumulative Class.
	 * Used as a more efficient way to make preflop play decisions.
	 * Depreciated - 
	 *******************************************************************************/
	void updateRangeData() {
		this.getRangeLow();
		int combos = getCombos();
		for (var i = 0; i < HANDS; ++i) {
			if (this.rangeArray[i] < 0) {
				if (playabilityArrayNumeric[i] >= this.lowPlayability) {
					++this.gapsCount;
				}
			}
		}
	}

	/*-  ****************************************************************************
	*	Delete file.
	 *****************************************************************************/
	static boolean deleteFile(String fileN) {
		return FileUtils.deleteFile(fileN);
	}

	/*-  ****************************************************************************
	 * Returns the value index into an array of the 169 shorthand hands Arg0 is
	 * the first card Arg1 is the second card Returns an index 0 - 195.
	 * Index into  handsArray
	  *****************************************************************************/
	static int getArrayIndexCard(Card card1, Card card2) {
		final var i = (-(card1.value - 12));
		final var j = (-(card2.value - 12));
		return card1.suit != card2.suit ? i + 13 * j : j + 13 * i;
	}

	/*-  ****************************************************************************
	 *  Returns the value index into an array of the 169 shorthand hands Arg0 is
	 * the first card Arg1 is the second card Returns an index 0 - 195.
	  *****************************************************************************/
	static int getArrayIndexString(String card1, String card2) {
		return getArrayIndexCard(new Card(card1), new Card(card2));
	}

	/*-  ****************************************************************************
	  *****************************************************************************/
	static int getCard1ValueForIndex(int ndx) {
		for (var i = 0; i < 13; ++i) {
			if (handsArray[ndx].substring(0, 1).equals(Card.CARDS[i])) {
				return Card.CARD_VALUE[i];
			}
		}
		return -1;
	}

	/*-  ****************************************************************************
	 *  Get value at index for first card - class A = 0, 2 = 1, etc..
	  *****************************************************************************/
	static int getCard2ValueForIndex(int ndx) {
		for (var i = 0; i < 13; ++i) {
			if (handsArray[ndx].substring(1, 2).equals(Card.CARDS[i])) {
				return Card.CARD_VALUE[i];
			}
		}
		return -1;
	}

	/*-  ****************************************************************************
	 Find hand in handsArray and return index Arg0 - hand in string format.
	  *****************************************************************************/
	static int getHandStringToIndex(String hand) {
		for (var I = 0; I < HANDS; ++I) {
			if (handsArray[I].equals(hand)) {
				return I;
			}
		}
		Logger.log("ERROR HandRange handStringToIndex() Invalid hand " + hand);
		return -1;
	}

	/*-  ****************************************************************************
	 * - Returns the type of hand Arg0 is the first card Arg1 is the second card.
	  *****************************************************************************/
	static int getHandType(int ndx) {
		return handsArray[ndx].length() == 2 ? PAIR
				: handsArray[ndx].length() != 3 ? INVALID
						: "s".equals(handsArray[ndx].substring(2)) ? SUITED
								: "o".equals(handsArray[ndx].substring(2)) ? OFFSUIT : INVALID;
	}

	/*-  ****************************************************************************
	* Get order value
	 *******************************************************************************/
	static int getOrder(int index) {
		return playabilityArrayOrder[index];
	}

	/*-  ****************************************************************************
	* Get playabilityNumeric value
	 ********************************************************************************/
	static int getPlayabilityNumeric(int index) {
		return playabilityArrayNumeric[index];
	}

	/*-  ****************************************************************************
	 * Returns the String value of hand Arg0 is the first card Arg1 is the second
	 * card Returns hand as a string.
	  *****************************************************************************/
	static String getRangeArrayString(Card card1, Card card2) {
		return handsArray[getArrayIndexCard(card1, card2)];
	}

	/*-  ****************************************************************************
	 * Returns the String value of hand Arg0 is the index Returns hand as a
	 * string.
	   *****************************************************************************/
	static String getRangeArrayString(int ndx) {
		return handsArray[ndx];
	}

	/*-  ****************************************************************************
	 *This method will search the hand array for the last card, in playability
	 * order. The key assumption is that the range was initially constructed in
	 * playability order, The last playability hand in the array + 1, is added using
	 * playability ( changed to true )
	  *****************************************************************************/
	void addOneHandPlayability() {
		var k = 0;
		var last = 0;
		for (var i = 0; i < 169; ++i) {
			k = this.getOrderIndex(i);
			if (this.rangeArray[k] < 0) {
				last = i;
				break;
			}
		}
		k = this.getOrderIndex(last + 1);
		this.rangeArray[k] = IN_RANGE;
	}

	/*-  ****************************************************************************
	 * Subtract one range from another range.
	 * All hands that are in the second range are removed from this range.
	 * Arg0 - Second range.
	 * The other range is  this.
	  *****************************************************************************/
	void addRange(HandRange r) {
		for (var i = 0; i < HANDS; ++i) {
			if (r.isRangeArray(i) > 0) {
				this.setRangeArray(i, IN_RANGE);
			}
		}
	}

	/*-  ****************************************************************************
	 *  Build Maximum range Range The resulting range will be the hands that are
	 * possible after using other ranges.
	 * For example, preflop, after orbit 0 the  possible hands to start orbit 1
	 *  with are the combination of Open, Call, and  Limp.
	   *****************************************************************************/
	void buildRangeMaxRange(HandRange r) {
		for (var i = 0; i < HANDS; ++i) {
			if (r.isRangeArray(i) > 0) {
				this.setRangeArray(i, IN_RANGE);
			}
		}
	}

	/*-  ****************************************************************************
	 * Build Range using Playability and the number of 2 card hands
	 * Arg0 - Number  of card to start with
	 * Arg1 - Number of cards to add
	 *
	 * This Method constructs a Range array that does not start with the first card.
	 * This is generally because the Range being constructed is in the shadow of
	  *****************************************************************************/
	void buildRangeOrder(int hands) {
		var k = 0;
		this.clearRange();
		for (var i = 0; i < hands; ++i) {
			k = this.getOrderIndex(i);
			this.rangeArray[k] = IN_RANGE;
		}
	}

	/*-  ****************************************************************************
	 * Build Range using an OrderArray and the number of 2 card hands
	 * Arg0 -  Number of card to put in new Range
	 * Arg1 - Range with hands to exclude
	 * Arg2 - Order array to use in building new range
	 *
	 * This Method constructs a Range array that does not start with the first card.
	 * This is generally because the Range being constructed is in the shadow of
	 * another range, Only cards not in the first Range win be added
	  *****************************************************************************/
	void buildRangeOrder(int hands, HandRange range2) {
		var k = 0;
		final var r2 = range2.countHands();
		this.clearRange();
		for (var i = r2; i < hands; ++i) {
			k = this.getOrderIndex(i);
			this.rangeArray[k] = IN_RANGE;
		}
	}

	/*-  ****************************************************************************
	 * Build Range using Playability and percentage
	 * A Range for the percentage is constructed using the playability array that is part of this class.
	 * Arg0 - percentage
	  *****************************************************************************/
	void buildRangePercentage(double percentage) {
		var percentageNew = 0D;
		var k = 0;
		this.clearRange();
		if (percentage != 0) {
			for (var i = 0; i < HANDS; ++i) {
				k = this.getOrderIndex(i);
				this.setRangeArray(k, IN_RANGE);
				percentageNew = this.getRangePercent();
				final var kk = this.getOrderIndex(i + 1);
				if (percentageNew >= percentage || percentage
						- percentageNew < (getHandType(kk) == SUITED ? .302 : getHandType(kk) == PAIR ? .452 : .905)) {
					break;
				}
			}
		}
	}

	/*-  ****************************************************************************
	 * Build Range using a hand range
	 * Arg0 - Percentage of card to start with
	 * Arg1 - Range with hands to exclude
	 *
	 * This Method constructs a Range array that does not start with the first card.
	 * This is generally because the Range being constructed is in the shadow of
	 * another range, Only cards not in the first Range win be added
	  *****************************************************************************/
	void buildRangePercentage(double percentage, HandRange range2) {
		var percentageNew = 0D;
		var k = 0;
		this.clearRange();
		if (percentage != 0.) {
			for (var i = range2.countHands(); i < HANDS; ++i) {
				k = this.getOrderIndex(i);
				this.setRangeArray(k, IN_RANGE);
				percentageNew = this.getRangePercent();
				final var kk = this.getOrderIndex(i + 1);
				if (percentageNew >= percentage || percentage
						- percentageNew < (getHandType(kk) == SUITED ? .302 : getHandType(kk) == PAIR ? .452 : .905)) {
					break;
				}
			}
		}
	}

	/*-  ****************************************************************************
	 * Build Range using order Arg0 - Percentage of card to start with Arg1 -
	 * Range to start with Arg2 - Max number of hands in range
	 *
	 * This Method constructs a Range array that does not start with the first card.
	 * This is generally because the Range being constructed is in the shadow of
	 * another range, Only cards not in the first Range win be added. The max number
	 * is the most hands that can be in the range. For example, max may be the
	 * combination preflop of open, call, and limp.
	  *****************************************************************************/
	void buildRangePercentageMax(double percentage, int max) {
		var percentageNew = 0D;
		var k = 0;

		this.clearRange();
		if (percentage != 0) {
			for (var i = 0; i < max; ++i) {
				k = this.getOrderIndex(i);
				this.setRangeArray(k, IN_RANGE);
				percentageNew = this.getRangePercent();
				final var kk = this.getOrderIndex(i + 1);
				if (percentageNew >= percentage || percentage
						- percentageNew < (getHandType(kk) == SUITED ? .302 : getHandType(kk) == PAIR ? .452 : .905)) {
					break;
				}
			}
		}
	}

	/*-  ****************************************************************************
	 * Build Range using order
	 * Arg0 - Percentage of card to start with
	 * Arg1 - Range to start with
	 * Arg2 - Max number of hands in range
	 *
	 * This Method constructs a Range array that does not start with the first card.
	 * This is generally because the Range being constructed is in the shadow of
	 * another range, Only cards not in the first Range win be added. The max number
	 * is the most hands that can be in the range. For example, max may be the
	 * combination preflop of open, call, and limp.
	  *****************************************************************************/
	void buildRangePercentageMaxOrder(double percentage, HandRange range2, int max) {
		var percentageNew = 0D;
		var k = 0;

		this.clearRange();
		if (percentage != 0) {
			for (var i = range2.countHands(); i < max; ++i) {
				k = this.getOrderIndex(i);
				this.setRangeArray(k, IN_RANGE);
				percentageNew = this.getRangePercent();
				final var kk = this.getOrderIndex(i + 1);
				if (percentageNew >= percentage || percentage
						- percentageNew < (getHandType(kk) == SUITED ? .302 : getHandType(kk) == PAIR ? .452 : .905)) {
					break;
				}
			}
		}
	}

	/*-  ****************************************************************************
	 * Build Range using Playability and the number of 2 card hands Arg0 -
	 * Number of cards in new range.
	  *****************************************************************************/
	void buildRangePlayabilityHands(int hands) {
		var k = 0;
		this.clearRange();
		for (var i = 0; i < hands; ++i) {
			k = this.getOrderIndex(i);
			this.rangeArray[k] = IN_RANGE;
		}
	}

	/*-  ****************************************************************************
	 *  Build Range using Playability and the number of 2 card hands
	 *  Arg0 - Number of card to put in new Range
	 *  Arg1 - Range with hands to exclude
	 *
	 * This Method constructs a Range array that does not start with the first card.
	 * This is generally because the Range being constructed is in the shadow of
	 * another range, Only cards not in the first Range win be added
	  *****************************************************************************/
	void buildRangePlayabilityHands(int hands, HandRange range2) {
		var k = 0;
		final var r2 = range2.countHands();
		this.clearRange();
		for (var i = r2; i < hands; ++i) {
			k = this.getOrderIndex(i);
			this.rangeArray[k] = IN_RANGE;
		}
	}

	/*-  ****************************************************************************
	 * Build Range using Playability and the number of 2 card hands Arg0 - The
	 * Playability index to start with Arg1 - The number of hands to add
	  *****************************************************************************/
	void buildRangePlayabilityHandsStartEnd(int start, int count) {
		var k = 0;
		final var end = count + start;
		this.clearRange();
		// Find
		for (var i = start; i <= end; ++i) {
			k = this.getOrderIndex(i);
			this.rangeArray[k] = IN_RANGE;
		}
	}

	/*-  ****************************************************************************
	Clear hand range.
	******************************************************************************/
	void clearRange() {
		for (var i = 0; i < HANDS; ++i) {
			this.rangeArray[i] = NOT_RANGE;
		}
	}

	/*- ******************************************************************************
	 * Combine this range with another range.
	 * Used to eliminate shadow effect where a raise range is followed by a call range.
	 * Hands in the call range will not matter if they are also in the raise range
	 * because raise is always a narrower range and is always evaluated first, then call.
	 * Some say they dynamically change raise range based on conditions
	 * True but then the call range must also be changed dynamically.
	 * Always true if ranges are static.
	 * In future versions of this application we will use playability instead a range array.
	 * See HoleCardAction Class.
	 * This method is used by HoleCardAction Class.
	 *******************************************************************************/
	void combineRangeWithThisRange(HandRange range) {
		for (var i = 0; i < HANDS; ++i) {
			if (range.rangeArray[i] > 0) {
				this.rangeArray[i] = range.rangeArray[i];
			}
		}
	}

	/*-  ****************************************************************************
	 Count Hands.
	 *******************************************************************************/
	int countHands() {
		var I = 0;
		for (var i = 0; i < HANDS; ++i) {
			if (this.rangeArray[i] > 0) {
				++I;
			}
		}
		return I;
	}

	/*-  ****************************************************************************
	Count Hands from starting hand.
	*********************************************************************************/
	int countHandsFrom(int from) {
		var I = 0;
		for (var i = from; i < HANDS; ++i) {
			if (this.rangeArray[i] > 0) {
				++I;
			}
		}
		return I;
	}

	/*-  ****************************************************************************
		 * Called by constructor when the first instance is created to initialize
		 * static arrays. One time only.
		 * Construct orderArray
		****************************************************************************** */
	void createOrderArray() {
		var k = 0;
		for (var i = 0; i < HANDS; ++i) {
			k = getHandStringToIndex(playabilityArray[i]);
			playabilityArrayOrder[i] = k;
		}
	}

	/*-  **************************************************************************** \
	First hand Hands.
	*****************************************************************************/
	int firstHand() {
		for (var i = 0; i < HANDS; ++i) {
			if (this.rangeArray[i] > 0) {
				return this.getOrderIndex(i);
			}
		}
		return 0;
	}

	/*-  ****************************************************************************
	*	Count combinations.
	 *****************************************************************************/
	int getCombos() {
		var I = 0;
		for (var i = 0; i < HANDS; ++i) {
			if (this.rangeArray[i] > 0) {
				if (getHandType(i) == SUITED) {
					I += 4;
				}
				if (getHandType(i) == PAIR) {
					I += 6;
				}
				if (getHandType(i) == OFFSUIT) {
					I += 12;
				}
			}
		}
		this.combos = I;
		return I;
	}

	/*-  ****************************************************************************
	 * Build Range using Playability and the number of 2 card hands Returns the
	 * Playability index of the first card in the array
	  *****************************************************************************/
	int getFirstHandInRangePlayability() {
		for (var i = 0; i < HANDS; ++i) {
			if (this.rangeArray[i] > 0) {
				return this.getOrderIndex(i);
			}
		}
		return -0;
	}

	/*-  ****************************************************************************
	* Get hand string from handsArray using index.
	*****************************************************************************/
	String getHandString(int index) {
		return handsArray[index];
	}

	/*-  ****************************************************************************
	 * This method will search the hand array for the last card, in playability
	 * order. The key assumption is that the range was initially constructed in
	 * playability order, The index is into the rangeArray
	  *****************************************************************************/
	int getLastHandPlayability() {
		var I = 0;
		for (var i = 168; i > 0; ++i) {
			I = this.getOrderIndex(i);
			if (this.rangeArray[I] > 0) {
				break;
			}
		}
		return I;
	}

	/*-  Get location in RangeArray. */
	public int getOrderIndex(int ndx) {
		return HandRange.playabilityArrayOrder[ndx];
	}

	/*-  ****************************************************************************
	* Get hand string from rankingArray using index.  \
	*****************************************************************************/
	String getPlayabilityArrayString(int index) {
		return handsArray[this.getOrderIndex(index)];
	}

	/*-  ****************************************************************************
	 *Returns the String value of hand Arg0 is the first card as a String Arg1 is
	 * the second card as a String Returns hand as a string.
	  *****************************************************************************/
	String getRangeArrayStringx(String c1, String c2) {
		return handsArray[getArrayIndexCard(new Card(c1), new Card(c2))];
	}

	/*-  ****************************************************************************
	 * Range percent. What percentage of all two card combinations (1326) does
	 * this range use.
	  *****************************************************************************/
	double getRangePercent() {
		final var I = getCombos();
		return I == 0 ? 0 : 100.0 * I / COMBINATIONS;
	}

	/*-  ****************************************************************************
	 * Range percent. What percentage of all two card combinations (1326) does
	 * this range use.
	 * Arg0 - Combinations
	  *****************************************************************************/
	double getRangePercent(int comb) {
		final var I = comb;
		return I == 0 ? 0 : 100.0 * I / COMBINATIONS;
	}

	/*-  ****************************************************************************
	 * Returns the value of of an element in rangeArray Arg0 is the first card
	 * Arg1 is the second card Returns to true or false.
	  *****************************************************************************/
	int isRangeArray(Card card1, Card card2) {
		return this.rangeArray[getArrayIndexCard(card1, card2)];
	}

	/*-  ****************************************************************************
	 *Returns the value of of an element in rangeArray Arg0 is the array index
	 * Returns to true or false.
	  *****************************************************************************/
	int isRangeArray(int ndx) {
		if (ndx <= HANDS && ndx >= 0) {
			return this.rangeArray[ndx];
		}
		Logger.log(new StringBuilder().append("ERROR  HandRange isRangeArray() index out of range ").append(ndx)
				.append(" ").append(this.rangeArray.length).toString());
		return NOT_RANGE;
	}

	/*- ****************************************************************************
	 * Read a Range from a disk file.
	 * Arg0 - The full path name.
	  *****************************************************************************/
	HandRange readFromFile(String path) {
		HandRange r = null;
		try (var fileIn = new FileInputStream(path); var in = new ObjectInputStream(fileIn)) {
			r = (HandRange) in.readObject();
		} catch (final ClassNotFoundException | IOException i) {
			i.printStackTrace();
		}
		return r;
	}

	/*-  ****************************************************************************
	*	Read a rangeArray from a disk file Arg0 - The full path name.
	 *****************************************************************************/
	boolean readRange(String path) {
		this.path = path;
		if (!new File(path).exists()) {
			Logger.log("ERROR HandRange readRange() File does not exist " + path);
			return false;
		}
		this.rangeArray = FileUtils.readFile(path, this.rangeArray);
		return this.rangeArray != null;
	}

	/*-  ****************************************************************************
	 * This method will search the hand array for the last card, in playability
	 * order. The key assumption is that the range was initially constructed in
	 * playability order, The last hand in the array is removed ( changed to false )
	  *****************************************************************************/
	boolean removeOneHandPlayability() {
		this.rangeArray[this.getLastHandPlayability()] = NOT_RANGE;
		return true;
	}

	// Undo combine
	void removeRangeWithThisRange(HandRange range) {
		for (var i = 0; i < HANDS; ++i) {
			if (range.rangeArray[i] > 0) {
				this.rangeArray[i] = -1;
			}
		}
	}

	/*-  ****************************************************************************
	 *  Set the value of an element in an array 0f 169 possible hands Arg0 is the
	 * first card Arg1 is the second card Arg2 is the boolean condition to set this
	 * element An element in rangeArray is set to true or false.
	  *****************************************************************************/
	void setRangeArray(Card card1, Card card2, int b) {
		this.rangeArray[getArrayIndexCard(card1, card2)] = b;
	}

	/*-  ****************************************************************************
	 * Set the value of an element in an array 0f 169 possible hands Arg0 is the
	 * index into array Arg1 is the boolean condition to set this element An element
	 *****************************************************************************/
	void setRangeArray(int ndx, int b) {
		if (ndx >= 0 && ndx <= HANDS) {
			this.rangeArray[ndx] = b;
		} else {
			Logger.log("ERROR HandRange setRangeArray() invalid index " + ndx);
		}
	}

	/*-  ****************************************************************************
	*	Return shorthand for a range
	 *****************************************************************************/
	String shorthand() {
		var st = "";
		var singleSt = "";
		var sequentialSt = "";
		var first = 0;
		var last = 0;
		var len = 0;
		var plus = false;
		var cont = false;
		// Pairs in a range
		first = -1;
		if (this.rangeArray[0] > 0) {
			plus = true;
		}
		sequentialSt = singleSt = "";
		for (var i = 0; i < HANDS; i += 14) {
			first = -1;
			last = i;
			cont = false;
			if (this.rangeArray[i] > 0) {
				plus = true;
				cont = false;
			} else {
				plus = false;
				cont = true;
			}
			if (this.rangeArray[i] > 0) {
				if (first == -1 && this.rangeArray[i] > 0) {
					first = i;
				}
				last = i;
				if ((i >= HANDS || this.rangeArray[i + 14] < 0) && !cont && first != -1) {
					if (!plus || first == last) {
						singleSt += getRangeArrayString(first) + ", ";
					} else {
						sequentialSt += getRangeArrayString(last) + "+, ";
					}
					plus = false;
					first = -1;
				} else if (first != -1) {
					if (first == last) {
						singleSt += getRangeArrayString(first) + ", ";
					} else if (first == 0) {
						sequentialSt += getRangeArrayString(last) + "+, ";
					} else {
						last = i;
						sequentialSt += new StringBuilder().append(getRangeArrayString(first)).append("-")
								.append(getRangeArrayString(last)).append(",  ").toString();
					}
				}
			}
		}
		st += sequentialSt + singleSt;

		// Suited
		len = 12;
		for (var i = 1; i < HANDS; i += 14) {
			sequentialSt = singleSt = "";
			first = -1;
			last = i;
			cont = false;
			if (this.rangeArray[0] > 0) {
				plus = true;
				cont = false;
			} else {
				plus = false;
				cont = true;
			}
			for (var j = i; j < i + len; ++j) {
				if (first == -1 && this.rangeArray[j] > 0) {
					first = j;
				}
				if (j != i + len - 1 && this.rangeArray[j + 1] >= 0 || cont || first == -1) {
					if ((j == i + len - 1 || this.rangeArray[j + 1] < 0) && cont && first != -1) {
						last = j;
						if (first == last) {
							singleSt += getRangeArrayString(first) + ", ";
						} else {
							sequentialSt += new StringBuilder().append(getRangeArrayString(first)).append("-")
									.append(getRangeArrayString(last)).append(",  ").toString();
						}
						first = -1;
					}
				} else {
					last = j;
					if (!plus || first == last) {
						singleSt += getRangeArrayString(first) + ", ";
					} else {
						sequentialSt += getRangeArrayString(last) + "+, ";
					}
					cont = true;
					plus = false;
					first = -1;
				}
			}
			--len;
			first = -1;
			plus = false;
			st += sequentialSt + singleSt;
		}

		// Offsuit
		len = 0;
		for (var i = 13; i < HANDS - 13; i += 13) {
			sequentialSt = singleSt = "";
			first = -1;
			last = i;
			cont = false;
			if (this.rangeArray[i] > 0) {
				plus = true;
				cont = false;
			} else {
				plus = false;
				cont = true;
			}
			for (var j = i; j <= i + len; ++j) {
				if (first == -1 && this.rangeArray[j] > 0) {
					first = j;
				}
				if (j != i + len && this.rangeArray[j + 1] >= 0 || cont || first == -1) {
					if ((j == i + len || this.rangeArray[j + 1] < 0) && cont && first != -1) {
						last = j;
						if (first == last) {
							singleSt += getRangeArrayString(first) + ", ";
						} else {
							sequentialSt += new StringBuilder().append(getRangeArrayString(first)).append("-")
									.append(getRangeArrayString(last)).append(",  ").toString();
						}
						first = -1;
					}
				} else {
					last = j;
					if (!plus || first == last) {
						singleSt += getRangeArrayString(first) + ", ";
					} else {
						sequentialSt += getRangeArrayString(last) + "+, ";
					}
					cont = true;
					plus = false;
					first = -1;
				}
			}
			++len;
			first = -1;
			plus = false;
			st += sequentialSt + singleSt;
		}

		final var l = st.length();
		if (l != 0 && ", ".equals(st.substring(l - 3, l - 1))) {
			st = st.substring(0, l - 3);
		}

		return st;
	}

	/*-  ****************************************************************************
	 * Show panel for this range
	  *****************************************************************************/
	void show() {
		constructPanel();
	}

	/*-  ****************************************************************************
	 * Show panel for this range
	 * Arg0 - frame title
	  * Arg1 - frame x position
	  * Arg2 - frame y position
	  *****************************************************************************/
	void show(String titl, int xx, int yy) {
		x = xx;
		y = yy;
		title = titl;
		constructPanel();
	}

	/*-  ****************************************************************************
	 * Show panel for this range
	  *****************************************************************************/
	void showRange() {
		constructPanel();
	}

	/*-  ****************************************************************************
	 * Show panel for this range
	 * Arg0 - frame title
	  * Arg1 - frame x position
	  * Arg2 - frame y position
	  *****************************************************************************/
	void showRange(String titl, int xx, int yy) {
		x = xx;
		y = yy;
		title = titl;
		constructPanel();
	}

	/*-  ****************************************************************************
	*  Construct panel.
	 *****************************************************************************/
	void constructPanel() {
		title = this.path;
		final var panel = new JPanel();
		final var panel0 = new JPanel();

		panel0.setLayout(new BoxLayout(panel0, BoxLayout.Y_AXIS));
		final var f0 = new Font(Font.SERIF, Font.BOLD, 18);
		panel0.setFont(f0);
		panel0.setSize(200, 200);

		final var panel1Layout = new GridLayout(NUM_ROWS, NUM_COLS);
		final var panel1 = new JPanel();
		panel1.setBackground(Color.WHITE);
		panel1.setLayout(panel1Layout);
		panel1.setFont(f0);

		final var panel2 = new JPanel();
		final var panel3 = new JPanel();
		final TitledBorder rangeTitle;
		final var textArray = new JTextField[HANDS];

		final var combosNum = new JTextField("");
		final var comboPerNum = new JTextField("");
		final var gapsNum = new JTextField("");
		final var playNum = new JTextField("");
		final var type = new JTextField("");
		final var pos = new JTextField("");
		final var range = new JTextField("");

		final var frame = new JFrame();

		new Dimension(300, 350);
		final var frameDimN = new Dimension(200, 150);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setLocation(x, y);
		frame.setPreferredSize(new Dimension(500, 600));
		frame.setTitle(title);
		frame.setMaximumSize(frameDimN);
		frame.setMinimumSize(frameDimN);

		final var ff = new Font(Font.SERIF, Font.BOLD, 10);
		panel.setLayout(new GridLayout(13, 13));
		panel.setMaximumSize(new Dimension(30, 30));

		// Hand matrix
		final var buttonDim = new Dimension(1, 1);

		rangeTitle = BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(""));
		panel1.setBorder(rangeTitle);
		rangeTitle.setTitle("");
		for (var i = 0; i < HANDS; ++i) {
			textArray[i] = new JTextField(this.getHandString(i));
			textArray[i].setFont(ff);
			textArray[i].setMaximumSize(buttonDim);
			textArray[i].setMinimumSize(buttonDim);
			textArray[i].setBackground(Color.LIGHT_GRAY);
			textArray[i].setForeground(Color.BLACK);
			panel1.add(textArray[i]);
		}
		panel0.add(panel1);

		for (var i = 0; i < HANDS; ++i) {
			textArray[i].setBackground(Color.LIGHT_GRAY);
		}
		for (var i = 0; i < HANDS; ++i) {
			if (this.isRangeArray(i) > 0) {
				textArray[i].setBackground(Color.GREEN);
			}
		}

		panel2.setSize(400, 40);
		panel2.setBackground(Color.gray);
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		final var dim3 = new Dimension(400, 40);
		panel2.setMaximumSize(dim3);
		panel2.setMinimumSize(dim3);
		final var f3 = new Font(Font.SERIF, Font.BOLD, 16);
		combosNum.setBackground(open);
		comboPerNum.setBackground(open);
		gapsNum.setBackground(open);
		playNum.setBackground(open);
		type.setBackground(open);
		pos.setBackground(open);
		range.setBackground(open);
		combosNum.setFont(f3);
		comboPerNum.setFont(f3);
		gapsNum.setFont(f3);
		playNum.setFont(f3);
		type.setFont(f3);
		pos.setFont(f3);
		range.setFont(f3);

		combosNum.setText("Combos   " + String.valueOf(this.combos));
		comboPerNum.setText(String.valueOf(Format.formatPer(this.percentage)));
		playNum.setText("Play   " + String.valueOf(this.lowPlayability));
		gapsNum.setText("Gaps   " + String.valueOf(this.gapsCount));

		type.setText(PLAYER_TYPE_ST[this.playerType]);
		pos.setText(POSITION_ST2[this.position]);
		range.setText(RANGE_ST[this.range]);

		panel2.add(combosNum);
		panel2.add(comboPerNum);
		panel2.add(playNum);
		panel2.add(gapsNum);

		panel0.add(panel2);
		panel3.setSize(300, 40);
		panel3.setBackground(Color.gray);
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
		panel3.setMaximumSize(dim3);
		panel3.setMinimumSize(dim3);

		panel3.add(type);
		panel3.add(pos);
		panel3.add(range);
		panel0.add(panel3);

		frame.add(panel0);
		panel.repaint();
		frame.pack();
		frame.setVisible(true);
	}

	/*-  ****************************************************************************
	 * Subtract one range from another range.
	 * All hands that are in the second  range are removed from this range.
	 * Arg0 - Second range.
	 * The other range is this.
	  *****************************************************************************/
	void subtractRange(HandRange r) {
		for (var i = 0; i < HANDS; ++i) {
			if (r.isRangeArray(i) > 0) {
				this.setRangeArray(i, NOT_RANGE);
			}
		}
	}

	/*- ****************************************************************************
	 * Show range data - for test and debug only
	 *****************************************************************************/
	void showRangeData() {
		System.out.println("// this.lowPlayability " + this.lowPlayability + " this.gapsCount " + this.gapsCount);
	}

	/*-  ****************************************************************************
	*	Write a HandRange object to a disk file Arg0 - The full path name.
	 *****************************************************************************/
	void writeRange(String path) {
		FileUtils.writeFile(path, this.rangeArray);
	}

	/*- ****************************************************************************
	 * - Write Object to file - this
	 **************************************************************************** */
	void writeToFile(String path) {
		final var filename = path;
		// Saving of object in a file
		try (var file = new FileOutputStream(filename); var out = new ObjectOutputStream(file)) {
			// Method for serialization of object
			out.writeObject(this);
		} catch (final IOException i) {
			i.printStackTrace();
		}
	}

}
