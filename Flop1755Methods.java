package holdemevaluator;

/*-
 * Reducing the number of combinations is one key to increasing performance and 

 * reducing complexity.
 * By reducing the number of flop combinations to 1755 does just that.
 * Order is not important.  AKQ and AQK are exactly the same.
 * It also assumes that the actual suit in not important. 
* As Ks Qs are exactly the same as Ad Kd Qd or Ad Qd Kd. No difference at all.
* They are all equivalent flops, logically duplicate.
* 
* By eliminating card order and by eliminating one suit we have a valid representation of all
* possible flops. Any possible flop can be converted to the reduced set of flops. 
* Of course, the hole cards, turn card, and river card suits must be adjusted. 
* 
* If an array of 1755 flops is created then any single flop can be representsd by the index
* into that array. A single integer then represents any flop.
* A flop, with card orden not sorted and with all 4 possible suits can be converted into an 
* index into the representative array of 1755.
* 
* Other arrays of 1755 elements can be created that have information about the flop.
* For example there might be a boolean array of  wet/dry, rainbow, paired, whatever. 
* Or arrays of objects with more complex analysis such as relative flop strength.
* Or data obtained from analyzing Hand History files.
* All of these calculations done offline, when performance is not an issue and saved 
* in a file. 
* 
* There is a similar condition for hole cards. The number of combinations can be reduced 
* to 169, a 13 X 13 array. Very commonly used for ranges.
* 
* We end up with 2 integers that represent all possible combinations of hole cards and
* flop cards. A huge simplification and a huge performance improvement.
* Arrays are created offline, saved to files, then read back into arrays when needed.
* 
* 3 of a kind, AAA, 222,... 13  
* A pair with 3rd card suited with eiter of the pair cards, AdAs2d, AdAs3d... 13 x 12 = 156  
* A pair + 3rd card, all offsuit, same as above, 156  
* All suited, 13 x 12 x 11/(3 x 2) = 286  
* All offsuit, items 1-3 excluded = 286 
* 2 cards suited, 3rd offsuit, 2 lowest suited = 286
* 2 cards suited, 3rd offsuit, 2 highest suited = 286
* 2 cards suited, 3rd offsuit, low and high suited = 286
* Total 13+156+156+286+286+286 _ 286 + 286 =1755 
* 
* 
* The array of hands created by this program have a very fixed structure. 
* 		Number			Type					Starting index
* 		13 					sets								  0
* 	 	156					pairs suited  				 13
* 		156 				pairs offsuit			   169
* 		286				 	suited  						325
* 		286 				offsuit  						611
* 		286  				2 suited low 				897
* 		286 				2  suited high 			1183
* 		286  				2 suited high and low 1469
* 		1755																	 
* Because of the structure, we know that if a flop has a pair and one of the pair is the same suit as 
* the non pair card the hands start at index 169. Very fast.
* 
* That is what this project is all about.
* 
* Public methods
* 		void makeFlop()	
*  		int lookupFlopType(Card card1, Card card2, Card card3) 
*  		copyFlopArrayToFlops1755()
* 		 int getFlopIndex(Card card1, Card card2, Card card3)
* 		 int lookupFlop(Card card1, Card card2, Card card3)	
* 
* Unuse private 
* 		void createFlopForIndex(int ndx) 

* 
* There are several sources on the internet that talk about isomorph flops.
* google isomorph flops 1755
* 
* There are only 3 suits, spade, club, diamond. Sorry, no hears here.
 * 
 * @author PEAK_
 */
public class Flop1755Methods implements Constants {

	private static final int[][] flop = new int[1755][3];
	private static final int[] flopy = new int[1755];
	private static int flopIndex;
	private static int c1 = -1;
	private static int c2 = -1;
	private static int c3 = -1;

	private static int setCount;
	private static int pairsSuitedCount;
	private static int pairsOffsuitCount;
	private static int suitedCount;
	private static int offsuitCount;
	private static int suited2LowCount;
	private static int suited2HighCount;
	private static int suited2LowHighCount;
	private static final int[] suitedPairsIndexes = new int[13];
	private static final int[] offsuitPairsIndexes = new int[13];
	private static final int[][] suitedIndexs = new int[13][13];
	private static final int[][] offsuitIndexs = new int[13][13];
	private static final int[][] suited2LowIndexes = new int[13][13];
	private static final int[][] suited2HighIndexes = new int[13][13];
	private static final int[][] suited2LowHighIndexes = new int[13][13];
	private static int p;
	private static final boolean createFlop = true;
	private static boolean savedFlops = false;

	// Constructor
	Flop1755Methods() {
	}

	/*- **************************************************************************************************
	 * Returns an index into the array of all possible flops.
	 * The number returned can represent the flop.
	 * The three cards in a flop
	 * Arg0 - Card
	 * Arg1 - Card
	 * Arg3 - Card
	 * Returns index into array 0 - 1754.  
	  ***************************************************************************************************/
	static int getFlopIndex(Card card1, Card card2, Card card3) {
		final int $ = lookupFlop(card1, card2, card3);
		if ($ < 0) {
			Crash.log(
					new StringBuilder().append("//getFlopIndex ").append(card1).append(card2).append(card3).toString());
		}
		return $;
	}

	/*- **************************************************************************************************
	 * The local array is copied into the Class Flops1755 
	 * This method only needs to be called one time ever
	 * First call makeFlop() to creat an array then this method to save it in a disk file.
	 * 
	 * That Class only holds the array but it  implements java.io.Serializable.
	 * The array is written to a disk file and can be read back. 
	 * It has methods to write the array to a file, read the array back from a file, 
	 * and to return a flop from an index number using the Class FlopAsNumbers.
	 * FlopAsNumbers  is a flop representation using 3 integers 0-51 to represent a a flop.
	 * A flop can also be returned as a String
	  ***************************************************************************************************/
	static void copyFlopArrayToFlops1755() {
		if (!savedFlops) {
			for (int i = 0; i < 1755; ++i) {
				Flops1755.flops[i][0] = flop[i][0];
				Flops1755.flops[i][1] = flop[i][1];
				Flops1755.flops[i][2] = flop[i][2];
			}
		}
		savedFlops = true;
	}

	/*- **************************************************************************************************
	 * This method will create an array of 1755 flops that represent all possible flops.
	 *  isomorph flops.
	 *  The proof and a  lot of discussion on the internet.  
	 *  google  isomorph flops 1755
	 *  Too complex to describe here.
	 *  This implementation is simple and very fast.
	 *  Divides a flop into 8 segments:
	 *  	makeSets();
	 *		makePairsSuited() 
	 *		makePairsOffsuit() 
	 *		makeSuited() 
	 *		makeOffsuit() 
	 *		make2SuitedLow() 
	 *		make2SuitedHigh() 
	 *		make2SuitedLowHigh() 
	 * 
	  ***************************************************************************************************/
	static void makeFlop() {
		flopIndex = 0;
		for (int i = 0; i < 1755; ++i) {
			flopy[i] = flop[i][2] = -1;
		}
		for (int i = 0; i < 13; ++i) {
			for (int j = 0; j < 13; ++j) {
				offsuitPairsIndexes[i] = suitedPairsIndexes[i] = -1;
			}
		}
		for (int i = 0; i < 13; ++i) {
			for (int j = 0; j < 13; ++j) {
				suited2LowHighIndexes[i][j] = suited2HighIndexes[i][j] = -1;
			}
		}
		makeSets();
		makePairsSuited();
		makePairsOffsuit();
		makeSuited();
		makeOffsuit();
		make2SuitedLow();
		make2SuitedHigh();
		make2SuitedLowHigh();
		for (int i = 0; i < flopIndex; ++i) {
			if (flop[i][0] == flop[i][1] || flop[i][0] == flop[i][2] || flop[i][1] == flop[i][2]) {
				++p;
				final var st = new StringBuilder().append("//ERROR duplicate Cards ")
						.append(Card.cardToString(flop[i][0])).append(Card.cardToString(flop[i][1]))
						.append(Card.cardToString(flop[i][2])).append(" ").append(i).toString();
				if (p > 20) {
					Crash.log(st);
				}
			}
		}
		for (int k = 0; k < flopIndex; ++k) {
			for (int i = k + 1; i < flopIndex; ++i) {
				if (flop[i][0] == flop[k][0] && flop[i][1] == flop[k][1] && flop[i][2] == flop[k][2]) {
					++p;
					if (p > 20) {
						final var st = new StringBuilder().append("//ERROR duplicate Flops ")
								.append(Card.cardToString(flop[i][0])).append(Card.cardToString(flop[i][1]))
								.append(Card.cardToString(flop[i][2])).append(" ").append(k).append(" ").append(i)
								.toString();
						Crash.log(st);
					}
				}
			}
		}
		Logger.log(new StringBuilder().append("//Complete OK ").append(setCount).append(" ").append(pairsSuitedCount)
				.append(" ").append(pairsOffsuitCount).append(" ").append(suitedCount).append(" ").append(offsuitCount)
				.append(" ").append(suited2LowCount).append(" ").append(suited2HighCount).append(" ")
				.append(suited2LowHighCount).toString());
		Logger.log(new StringBuilder().append("//flop$$ ").append(flop[0][0]).append(" ").append(+flop[0][1])
				.append(" ").append(flop[0][2]).toString());
		Logger.log(new StringBuilder().append("//cards ").append(Card.cardToString(flop[0][0]))
				.append(Card.cardToString(flop[0][1])).append(Card.cardToString(flop[0][2])).toString());
		final var a = new Card(flop[0][0]);
		Logger.log(new StringBuilder().append("//cards ").append(a).append(" ").append(a.toString()).toString());
	}

	/*- **************************************************************************************************
	 * Helper method to makeFlop()
	 * Creates sets
	  ***************************************************************************************************/
	static void makeSets() {
		for (int i = 0; i < 13; ++i) {
			c1 = Card.indexAndSuitToCard(i, SPADE);
			c2 = Card.indexAndSuitToCard(i, CLUB);
			c3 = Card.indexAndSuitToCard(i, DIAMOND);
			if (createFlop) {
				flop[flopIndex][0] = c1;
				flop[flopIndex][1] = c2;
				flop[flopIndex][2] = c3;
			}
			++flopIndex;
			++setCount;
		}
	}

	/*- **************************************************************************************************
	 * Helper method to makeFlop()
	 * Creates pairs suited
	  ***************************************************************************************************/
	private static void makePairsSuited() {
		for (int i = 0; i < 13; ++i) {
			suitedPairsIndexes[i] = flopIndex;
			c1 = Card.indexAndSuitToCard(i, SPADE);
			c2 = Card.indexAndSuitToCard(i, CLUB);
			for (int j = 0; j < 13; ++j) {
				if (j != i) {
					c3 = Card.indexAndSuitToCard(j, SPADE);
					if (createFlop) {
						flop[flopIndex][0] = c1;
						flop[flopIndex][1] = c2;
						flop[flopIndex][2] = c3;
					}
					++pairsSuitedCount;
					++flopIndex;
				}
			}
		}
	}

	/*- **************************************************************************************************
	 * Helper method to makeFlop()
	 * Creates pairs offsuit
	  ***************************************************************************************************/
	private static void makePairsOffsuit() {
		for (int i = 0; i < 13; ++i) {
			offsuitPairsIndexes[i] = flopIndex;
			c1 = Card.indexAndSuitToCard(i, SPADE);
			c2 = Card.indexAndSuitToCard(i, CLUB);
			for (int j = 0; j < 13; ++j) {
				if (j != i) {
					c3 = Card.indexAndSuitToCard(j, DIAMOND);
					if (createFlop) {
						flop[flopIndex][0] = c1;
						flop[flopIndex][1] = c2;
						flop[flopIndex][2] = c3;
					}
					++pairsOffsuitCount;
					++flopIndex;
				}
			}
		}
	}

	/*- **************************************************************************************************
	 * Helper method to makeFlop()
	 * Creates suited
	  ***************************************************************************************************/
	private static void makeSuited() {
		for (int i = 0; i < 13; ++i) {
			c1 = Card.indexAndSuitToCard(i, SPADE);
			for (int j = i + 1; j < 13; ++j) {
				suitedIndexs[i][j] = flopIndex;
				c2 = Card.indexAndSuitToCard(j, SPADE);
				for (int k = j + 1; k < 13; ++k) {
					c3 = Card.indexAndSuitToCard(k, SPADE);
					if (createFlop) {
						flop[flopIndex][0] = c1;
						flop[flopIndex][1] = c2;
						flop[flopIndex][2] = c3;
					}
					++flopIndex;
					++suitedCount;
				}
			}
		}
	}

	/*- **************************************************************************************************
	 * Helper method to makeFlop()
	 * Creates pairs offsuit
	  ***************************************************************************************************/
	private static void makeOffsuit() {
		for (int i = 0; i < 13; ++i) {
			c1 = Card.indexAndSuitToCard(i, SPADE);
			for (int j = i + 1; j < 13; ++j) {
				offsuitIndexs[i][j] = flopIndex;
				c2 = Card.indexAndSuitToCard(j, CLUB);
				for (int k = j + 1; k < 13; ++k) {
					c3 = Card.indexAndSuitToCard(k, DIAMOND);
					if (createFlop) {
						flop[flopIndex][0] = c1;
						flop[flopIndex][1] = c2;
						flop[flopIndex][2] = c3;
					}
					++flopIndex;
					++offsuitCount;
				}
			}
		}
	}

	/*- **************************************************************************************************
	 * Helper method to makeFlop()
	 * Creates 2 suited low
	  ***************************************************************************************************/
	private static void make2SuitedLow() {
		for (int i = 0; i < 13; ++i) {
			for (int j = i + 1; j < 13; ++j) {
				suited2LowIndexes[i][j] = flopIndex;
				for (int k = j + 1; k < 13; ++k) {
					c1 = Card.indexAndSuitToCard(i, CLUB);
					c2 = Card.indexAndSuitToCard(j, SPADE);
					c3 = Card.indexAndSuitToCard(k, SPADE);
					if (createFlop) {
						flop[flopIndex][0] = c1;
						flop[flopIndex][1] = c2;
						flop[flopIndex][2] = c3;
					}
					++flopIndex;
					++suited2LowCount;
				}
			}
		}
	}

	/*- **************************************************************************************************
	 * Helper method to makeFlop()
	 * Creates 2 suited high
	  ***************************************************************************************************/
	private static void make2SuitedHigh() {
		for (int i = 0; i < 13; ++i) {
			for (int j = i + 1; j < 13; ++j) {
				suited2HighIndexes[i][j] = flopIndex;
				for (int k = j + 1; k < 13; ++k) {
					c1 = Card.indexAndSuitToCard(i, SPADE);
					c2 = Card.indexAndSuitToCard(j, SPADE);
					c3 = Card.indexAndSuitToCard(k, CLUB);
					if (createFlop) {
						flop[flopIndex][0] = c1;
						flop[flopIndex][1] = c2;
						flop[flopIndex][2] = c3;
					}
					++flopIndex;
					++suited2HighCount;
				}
			}
		}
	}

	/*- **************************************************************************************************
	 * Helper method to makeFlop()
	 * Creates 2 suited high and low
	  ***************************************************************************************************/
	private static void make2SuitedLowHigh() {
		for (int i = 0; i < 13; ++i) {
			for (int j = i + 1; j < 13; ++j) {
				suited2LowHighIndexes[i][j] = flopIndex;
				for (int k = j + 1; k < 13; ++k) {
					c1 = Card.indexAndSuitToCard(i, SPADE);
					c2 = Card.indexAndSuitToCard(j, CLUB);
					c3 = Card.indexAndSuitToCard(k, SPADE);
					if (createFlop) {
						flop[flopIndex][0] = c1;
						flop[flopIndex][1] = c2;
						flop[flopIndex][2] = c3;
					}
					++suited2LowHighCount;
					++flopIndex;
				}
			}
		}
	}

	/*- **************************************************************************************************
	 * This method looks at a flop and determines which of the categories used in creating 
	 * a 1755 flop and returns which one it fits into.
	 * Experimental. Another way of determining flop texture. 
	  ***************************************************************************************************/
	static int lookupFlopType(Card card1, Card card2, Card card3) {
		if (card1.value == card2.value && card1.value == card3.value) {
			return SET;
		}
		if (card1.suit == card3.suit || card2.suit == card3.suit) {
			return SUITED_PAIR;
		}
		if (card1.suit == card2.suit && card2.suit == card3.suit) {
			return ALL_SUITED;
		}
		if (card1.suit != card2.suit && card1.suit != card3.suit && card2.suit != card3.suit) {
			return ALL_OFFSUIT;
		}
		if (card2.suit == card3.suit && card1.suit != card2.suit) {
			return TWO_SUITED_LOW;
		}
		if (card1.suit == card2.suit && card1.suit != card3.suit) {
			return TWO_SUITED_HIGH;
		}
		if (card1.suit == card3.suit && card1.suit != card2.suit) {
			return TWO_SUITED_HIGH_LOW;
		}
		Logger.log(new StringBuilder().append("//ERROR flop has no 1755 type ").append(card1).append(card2)
				.append(card3).append(" ").append(card1.suit).append(" ").append(card2.suit).append(" ")
				.append(card3.suit).toString());
		return -1;
	}

	/*- *************************************************************************************************
	 * This method calculates and returns the index for a flop. 
	 * makeFlop() must have already been called because it saves indexes into
	 * the array as it constructs the 1755 array. 
	 * Those indexes are used here for a very fast lookup.
	 *
	 *Arg0 - Arg2 are Card
	 *Returns index into array
	 **************************************************************************************************/
	static int lookupFlop(Card card1, Card card2, Card card3) {
		if (card1.value < card2.value || card1.value < card3.value || card2.value < card3.value) {
			final var st = new StringBuilder().append("//lookupFlop ").append(card1).append(card2).append(card3)
					.append(" ").append(card1.value).append(" ").append(card2.value).append(" ").append(card3.value)
					.toString();
			Crash.log(st);
			sortFlop();
		}
		final int i1 = 12 - card1.value;
		final int i2 = 12 - card2.value;
		final int i3 = 12 - card3.value;
		if (card1.value == card2.value && card1.value == card3.value) {
			return i1;
		}
		if (card1.value == card2.value || card2.value == card3.value) {
			if (card1.suit == card3.suit || card2.suit == card3.suit) {
				if (card2.value < card3.value) {
					return suitedPairsIndexes[i1] + i3;
				} else {
					return suitedPairsIndexes[i1] + i3 - 1;
				}
			} else {
				if (card1.value < card3.value) {
					return offsuitPairsIndexes[i1] + i3;
				} else {
					return offsuitPairsIndexes[i1] + i3 - 1;
				}
			}
		}
		if (card1.suit == card2.suit && card2.suit == card3.suit) {
			return suitedIndexs[i1][i2] + i3 - i2 - 1;
		}
		if (card1.suit != card2.suit && card1.suit != card3.suit && card2.suit != card3.suit) {
			return offsuitIndexs[i1][i2] + i3 - i2 - 1;
		}
		if (card2.suit == card3.suit && card1.suit != card2.suit) {
			return suited2LowIndexes[i1][i2] + i3 - i2 - 1;
		}
		if (card1.suit == card2.suit && card1.suit != card3.suit) {
			return suited2HighIndexes[i1][i2] + i3 - i2 - 1;
		}
		if (card1.suit == card3.suit && card1.suit != card2.suit) {
			return suited2LowHighIndexes[i1][i2] + i3 - i2 - 1;
		}
		Crash.log(new StringBuilder().append("lookupFlop ").append(card1).append(card2).append(card3).toString());
		return -1;
	}

	/*- **************************************************************************************************
	 * Helper method to lookupFlop
	 * Uses shared variables
	 * Sorts a flop into a high low sequence.
	 * Many advantages to having all flops sorted, reduces complexity and improves performance.
	  ***************************************************************************************************/
	private static void sortFlop() {
		var card1 = new Card(c1);
		var card2 = new Card(c2);
		var card3 = new Card(c3);
		var cardSave = new Card(0);
		final var st = new StringBuilder().append("//sortFlop() ERROR never get here ").append(card1).append(card2)
				.append(card3).toString()
				+ new StringBuilder().append("//Before sort  ").append(card1).append(" ").append(card2).append(" ")
						.append(card3).toString();
		Crash.log(st);
		if (!(card1.value >= card2.value && card2.value >= card3.value)) {
			if (card2.value > card1.value && card2.value >= card3.value) {
				cardSave = card1;
				card1 = card2;
				card2 = cardSave;
			} else if (card3.value > card1.value && card3.value > card2.value) {
				cardSave = card1;
				card1 = card3;
				card3 = cardSave;
			}
			if (card3.value > card2.value) {
				cardSave = card2;
				card2 = card3;
				card3 = cardSave;
			}
		}
		if (!(card1.value < card2.value || card1.value < card3.value || card2.value < card3.value || c1 == c2
				|| c1 == c3 || c2 == c3)) {
			return;
		}
		Crash.log("//After sort  ");
	}

}
