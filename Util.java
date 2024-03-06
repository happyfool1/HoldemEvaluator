package holdemevaluator;

import java.util.ArrayList;
import java.util.Arrays;

public class Util implements Constants {
	/*- *****************************************************************************
	*
	* @author PEAK_
	*******************************************************************************/
	// Constants for number of cards and suits in a deck
	private static final int NUM_CARDS = 52;
	private static final int NUM_SUITS = 4;
	// Array to represent the ranks of cards
	private static final String[] CARD_RANKS = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A" };
	// Array to represent the suits of cards
	private static final String[] CARD_SUITS = { "C", "D", "H", "S" };

	/**
	 * Returns the number of possible combinations of k elements from n elements
	 * 
	 * @param n the number of elements to choose from
	 * @param k the number of elements to choose
	 * @return the number of possible combinations of k elements from n elements
	 */
	public static int numCombinations(int n, int k) {
		int numerator = 1;
		for (int i = n; i > n - k; i--) {
			numerator *= i;
		}
		int denominator = 1;
		for (int i = 1; i <= k; i++) {
			denominator *= i;
		}
		return numerator / denominator;
	}

	/**
	 * Returns the number of possible ways to choose 2 cards from a deck
	 * 
	 * @return the number of possible ways to choose 2 cards from a deck
	 */
	public static int numTwoCardCombinations() {
		return numCombinations(NUM_CARDS, 2);
	}

	/**
	 * Returns the number of possible ways to choose k cards from a deck
	 * 
	 * @param k the number of cards to choose
	 * @return the number of possible ways to choose k cards from a deck
	 */
	public static int numKCardCombinations(int k) {
		return numCombinations(NUM_CARDS, k);
	}

	/**
	 * Returns the probability of being dealt a pocket pair (two cards of the same
	 * rank)
	 * 
	 * @return the probability of being dealt a pocket pair
	 */
	public static double pocketPairProbability() {
		return 13 * (4.0 / NUM_CARDS) * ((3.0 / (NUM_CARDS - 1)));
	}

	/**
	 * Returns the probability of being dealt two cards of the same suit
	 * 
	 * 
	 * 
	 * peak_charles@yahoo.com show rest of this scss Copy code
	 * 
	 * @return the probability of being dealt two cards of the same suit
	 */
	public static double sameSuitProbability() {
		return NUM_SUITS * ((NUM_SUITS - 1.0) / NUM_CARDS) * ((NUM_SUITS - 2.0) / (NUM_CARDS - 1));
	}

	/**
	 * Returns the probability of being dealt two cards that are connected (in
	 * sequence)
	 * 
	 * @return the probability of being dealt two cards that are connected
	 */
	public static double connectedProbability() {
		return 12 * (4.0 / NUM_CARDS) * (4.0 / (NUM_CARDS - 1));
	}

	/**
	 * Returns the probability of being dealt a specific pocket pair (e.g. AA, KK,
	 * etc.)
	 * 
	 * @param rank the rank of the pocket pair
	 * @return the probability of being dealt the specified pocket pair
	 */
	public static double specificPocketPairProbability(String rank) {
		int numCardsOfRank = 4;
		if ("J".equals(rank) || "Q".equals(rank) || "K".equals(rank) || "A".equals(rank)) {
			numCardsOfRank = 1;
		}
		return (numCardsOfRank * (numCardsOfRank - 1) / 2.0) / numTwoCardCombinations();
	}

	/**
	 * Returns the probability of being dealt a specific two-card hand (e.g. AK, 72,
	 * etc.)
	 * 
	 * @param hand the two-card hand
	 * @return the probability of being dealt the specified hand
	 */
	public static double specificTwoCardHandProbability(String hand) {
		int rank1 = Arrays.asList(CARD_RANKS).indexOf(hand.substring(0, 1));
		int rank2 = Arrays.asList(CARD_RANKS).indexOf(hand.substring(1));
		if (rank1 > rank2) {
			final int temp = rank1;
			rank1 = rank2;
			rank2 = temp;
		}
		if (rank1 == rank2) {
			return specificPocketPairProbability(hand.substring(0, 1));
		}
		final double suitedProbability = (2.0 / NUM_CARDS) * (1.0 / (NUM_CARDS - 1));
		final double offsuitProbability = (4.0 / NUM_CARDS) * (3.0 / (NUM_CARDS - 1));
		if (CARD_SUITS[rank1 / 13].equals(CARD_SUITS[rank2 / 13])) {
			return suitedProbability;
		} else {
			return offsuitProbability;
		}
	}

	/*-
	 * Returns the probability of making a specific hand (e.g. two pair, straight,
	 * flush, etc.) given the community cards and the player's hole cards
	 * 
	 * @param communityCards an array containing the community cards
	 * @param holeCards      an array containing the player's hole cards
	 * @param hand           the hand to make (e.g. "two pair", "straight", etc.)
	 * @return the probability of making the specified hand
	 */
	public static double specificHandProbability(int[] communityCards, int[] holeCards, String hand) {
		final int[] allCards = Arrays.copyOf(communityCards, communityCards.length + holeCards.length);
		for (int[] combination : combinations(allCards.length, 5)) {
			final int[] handRankCounts = new int[13];
			final int[] handSuitCounts = new int[4];
			for (int index : combination) {
				handRankCounts[allCards[index] % 13]++;
				handSuitCounts[allCards[index] / 13]++;
			}
			final int[] holeRankCounts = new int[13];
			final int[] holeSuitCounts = new int[4];
			for (int holeCard : holeCards) {
				holeRankCounts[holeCard % 13]++;
				holeSuitCounts[holeCard / 13]++;
			}
			boolean handPossible = false;
			switch (hand) {
			case "royal flush" -> {
				if (handRankCounts[0] >= 1 && handRankCounts[9] >= 1 && handRankCounts[10] >= 1
						&& handRankCounts[11] >= 1 && handRankCounts[12] >= 1) {
					for (int suitCount : handSuitCounts) {
						if (suitCount >= 5) {
							handPossible = true;
							break;
						}
					}
				}
			}
			case "straight flush" -> {
				for (int i = 0; i <= 8; i++) {
					if (handRankCounts[i] >= 1 && handRankCounts[i + 1] >= 1 && handRankCounts[i + 2] >= 1
							&& handRankCounts[i + 3] >= 1 && handRankCounts[i + 4] >= 1) {
						for (int suitCount : handSuitCounts) {
							if (suitCount >= 5) {
								handPossible = true;
								break;
							}
						}
					}
				}
			}
			case "four of a kind" -> {
				for (int rankCount : handRankCounts) {
					if (rankCount >= 4) {
						handPossible = true;
						break;
					}
				}
			}
			case "full house" -> {
				boolean threeOfAKind = false;
				boolean twoOfAKind = false;
				for (int rankCount : handRankCounts) {
					if (rankCount >= 3) {
						threeOfAKind = true;
					} else if (rankCount >= 2) {
						twoOfAKind = true;
					}
				}
				if (threeOfAKind && twoOfAKind) {
					handPossible = true;
				}
			}
			case "flush" -> {
				for (int suitCount : handSuitCounts) {
					if (suitCount >= 5) {
						handPossible = true;
						break;
					}
				}
			}
			case "straight" -> {
				for (int i = 0; i <= 8; i++) {
					if (handRankCounts[i] >= 1 && handRankCounts[i + 1] >= 1 && handRankCounts[i + 2] >= 1
							&& handRankCounts[i + 3] >= 1 && handRankCounts[i + 4] >= 1) {
						handPossible = true;
					}
				}
			}
			case "three of a kind" -> {
				for (int rankCount : handRankCounts) {
					if (rankCount >= 3) {
						handPossible = true;
						break;
					}
				}
			}
			case "two pair" -> {
				int numPairs = 0;
				for (int rankCount : handRankCounts) {
					if (rankCount >= 2) {
						numPairs++;
					}
				}
				if (numPairs >= 2) {
					handPossible = true;
				}
			}
			case "pair" -> {
				for (int rankCount : handRankCounts) {
					if (rankCount >= 2) {
						handPossible = true;
						break;
					}
				}
			}
			default -> {
			}
			}
			final int numCards = 0; // TODO missing actual definition
			final int handRank = 0; // TODO did not copy correctly
			if (handPossible &= holeRankCounts[handRank] <= 2) {
				// A player can only have two hole cards that match the hand rank
			}
			if (handPossible) {
				int numOuts = 0;
				switch (hand) {
				case "royal flush" -> {
				}
				case "straight flush" -> {
					for (int i = 0; i <= 8; i++) {
						final boolean condition = handRankCounts[i] >= 1 && handRankCounts[i + 1] >= 1
								&& handRankCounts[i + 2] >= 1 && handRankCounts[i + 3] >= 1
								&& handRankCounts[i + 4] == 0;
						if (condition) { // Check for inside straight
							numOuts += numCards - 8; // Four possible cards to complete the straight
						}
					}
				}
				case "four of a kind" -> {
					for (int rankCount : handRankCounts) {
						if (rankCount == 0) {
							numOuts += numCards - 2; // Two possible cards to complete the four of a kind
						}
					}
					numOuts += (numCards - 4) * (numCards - 5); // Possible kicker cards
				}
				case "full house" -> {
					int threeRank = -1;
					int twoRank = -1;
					for (int i = 0; i < 13; i++) {
						if (handRankCounts[i] >= 3) {
							threeRank = i;
						} else if (handRankCounts[i] >= 2) {
							twoRank = i;
						}
					}
					for (int i = 0; i < 13; i++) {
						if (i != threeRank && handRankCounts[i] >= 1) { // Check for possible trips
							numOuts += 4; // Four cards of the same rank as the trips
						}
						if (i != twoRank && handRankCounts[i] >= 2) { // Check for possible pairs
							numOuts += 6; // Six cards of the same rank as the pairs
						}
					}
				}
				case "flush" -> {
					for (int suit = 0; suit < 4; suit++) {
						if (handSuitCounts[suit] >= 4) { // Check for possible flush
							numOuts += numCards - 4; // Possible cards to complete the flush
						}
					}
				}
				case "straight" -> {
					for (int i = 0; i <= 8; i++) {
						final boolean condition1 = handRankCounts[i] >= 1 && handRankCounts[i + 1] >= 1
								&& handRankCounts[i + 2] >= 1 && handRankCounts[i + 3] == 0;
						if (condition1) { // Check for inside straight
							numOuts += 4; // Four possible cards to complete the straight
						}
					}
					final boolean condition2 = handRankCounts[0] >= 1 && handRankCounts[1] >= 1
							&& handRankCounts[2] >= 1 && handRankCounts[3] >= 1 && handRankCounts[4] == 0
							&& handRankCounts[12] == 0;
					// Check for possible A-5 straight
					if (condition2) { // Make sure no other straights are
										// possible
						numOuts += 1; // One possible card (the 5 of a different suit)
					}
				}
				case "three of a kind" -> {
					for (int rankCount : handRankCounts) {
						if (rankCount == 0) {
							numOuts += numCards - 2; // Two possible cards to complete the three of a kind
						}
					}
					numOuts += (numCards - 3) * (numCards - 4); // Possible kicker cards
				}
				case "two pair" -> {
					int pair1Rank = -1;
					int pair2Rank = -1;
					for (int i = 0; i < 13; i++) {
						if (handRankCounts[i] >= 2) {
							if (pair1Rank == -1) {
								pair1Rank = i;
							} else {
								pair2Rank = i;
							}
						}
					}
					for (int i = 0; i < 13; i++) {
						if (i != pair1Rank && i != pair2Rank && handRankCounts[i] >= 1) { // Check for possible pairs
							numOuts += 2; // Two cards of the same rank as the pairs
						}
					}
					numOuts += (numCards - 4) * (numCards - 5); // Possible kicker cards
				}
				case "pair" -> {
					int pairRank = -1;
					for (int i = 0; i < 13; i++) {
						if (handRankCounts[i] >= 2) {
							pairRank = i;
							break;
						}
					}
					for (int i = 0; i < 13; i++) {
						if (i != pairRank && handRankCounts[i] >= 1) { // Check for possible pairs
							numOuts += 2; // Two cards of the same rank as the pair
						}
					}
					numOuts += (numCards - 2) * (numCards - 3) * (numCards - 4); // Possible kicker cards
				}
				case "high card" ->
					numOuts += (numCards - 1) * (numCards - 2) * (numCards - 3) * (numCards - 4) * (numCards - 5); // Possible
				}
			}
		}
		return 0.;
	}

	/**
	 * Returns an ArrayList containing all possible combinations of k elements from
	 * n elements
	 * 
	 * @param n the number of elements to choose from
	 * @param k the number of elements to choose
	 * @return an ArrayList containing all possible combinations of k elements from
	 *         n elements
	 */
	public static ArrayList<int[]> combinations(int n, int k) {
		final ArrayList<int[]> result = new ArrayList<>();
		final int[] combination = new int[k];
		for (int i = 0; i < k; i++) {
			combination[i] = i;
		}
		while (combination[k - 1] < n) {
			result.add(Arrays.copyOf(combination, k));
			int t = k - 1;
			while (t != 0 && combination[t] == n - k + t) {
				t--;
			}
			combination[t]++;
			for (int i = t + 1; i < k; i++) {
				combination[i] = combination[i - 1] + 1;
			}
		}
		return result;
	}

	/*- **************************************************************************** 
	* 
	* @author PEAK_
	*******************************************************************************/

	/*-
	*	Here's an example Java class with methods to do some o
	* the calculations commonly used in Texas Hold'em No Limit:
	*/

	/*-
	*			Texas Hold'em No Limit. Some of the methods included are:
	*
	*		CalculatePotOdds: This method takes in the current size of the pot, the size of the bet a player
	*		is facing, and the number of outs they have, and returns the pot odds as a percentage.
	*
	*		CalculateHandOuts: This method takes in a player's two hole cards and the community cards, 
	*		and returns a HashMap containing the number of outs for each possible hand the player could 
	*		make (such as a flush, straight, or pair).
	*
	*		CalculateEquity: This method takes in a player's two hole cards and the community cards, 
	*		as well as the number of opponents and their likely hand ranges, and returns the player's 
	*		equity as a percentage.
	*
	*		These methods can be used to help players make informed decisions in Texas Hold'em No Limit,
	*		 such as whether to call a bet or fold their hand. 
	*		 By calculating pot odds, hand outs, and equity, players can better understand the potential 
	*		 value of their hand and make more strategic plays. 
	*		 Overall, a thorough understanding of these calculations is essential for success in 
	*		 Texas Hold'em No Limit.
	*
	*/

}
