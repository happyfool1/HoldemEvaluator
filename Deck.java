package holdemevaluator;

import java.security.SecureRandom;
import java.util.Random;

/*- *************************************************************************************
 * Represents a standard 52-card deck of cards.
 * Methods allow shuffling, dealing, dead cards
 *
 * @author PEAK_
 **************************************************************************************/
class Deck implements Constants {
	private static final int DECK_SIZE = 52;

	/*- Backing array containing cards held within this deck. */
	private static Card[] cards = new Card[DECK_SIZE];

	private static final Card[] cardsInit = new Card[DECK_SIZE];

	/*- Current cursor offset in the backing array. */
	private static int currentIndex;

	private static final Random random = new SecureRandom();

	/*- Dead cards. */
	private static int deadCardCount = 0;
	private static final Card[] deadCards = new Card[DECK_SIZE];
	private static final int[] deadIndexes = new int[DECK_SIZE];

	private static boolean newDeck = false;

	/*-  Change this number for an all new pseudo-random series of cards set by setSeed method */
	private static long seed = 2062142251;

	private Deck() {
		throw new IllegalStateException("Utility class");
	}

	/*- *************************************************************************************
	* Constructs a new deck with the cards placed in a basic uniform order.
	* Call one time only!
	* The sequence will repeat exactly the same forever if the seed is not changed.
	* So it is really pseudo random, not random.
	* Starts all over again if called again with the same seed.
	* Truly random is if the seed in Options is 0 ( no seed ) so Random will choose a random seed.
	**************************************************************************************/
	static void newDeck() {
		if (seed != 0L) {
			random.setSeed(seed);
		}
		for (var i = 0; i < 52; ++i) {
			cardsInit[i] = new Card(i);
			cards[i] = new Card(i);
		}
		newDeck = true;
	}

	/*- *************************************************************************************
	 * Look at  next card to be dealt
	 * without disturbing the deck
	**************************************************************************************/
	static Card peekAtNextCard() {
		var card = cards[currentIndex];
		if (card == null && deadCardCount != 0) {
			for (var i = 0; i < deadCardCount; ++i) {
				card = cards[++currentIndex];
				if (card != null) {
					return card;
				}
			}
		}
		return card;
	}

	/*- *************************************************************************************
	 * Look at the value of the next card to be dealt
	 * without disturbing the deck
	************************************************************************************* */
	static int peekAtNextCardValue() {
		var card = cards[currentIndex];
		if (card == null && deadCardCount != 0) {
			for (var i = 0; i < deadCardCount; ++i) {
				card = cards[++currentIndex];
				if (card != null) {
					return card.value;
				}
			}
		}
		return card.value;
	}

	/*- *************************************************************************************
	 *Pops the next available from the deck.
	 *ReturnThe next available from the deck.
	 *The sequence will repeat exactly the same forever if the seed is not  changed.
	 *So it is really pseudo random, not random.
	 * Dead cards are set to null in the card array. 
	 * If we find a dead card we just continue looking at the next card in the deck until
	 * a card is not null or the dead count is exceeded.
	 **************************************************************************************/
	static Card pop() {
		var card = cards[currentIndex++];
		// Dead card if null
		if (card == null) {
			for (int i = 0; i < 51 - currentIndex; ++i) {
				card = cards[currentIndex++];
				if (card != null) {
					return card;
				}
			}
		}
		return card;
	}

	/*- *************************************************************************************
	 *  Set dead card
	 *  Set a card in the cardArray to null and increment the dead card count.
	 *  If card has already been dealt set it as a dead card anyway.
	 *  Arg0 - Card to set to null as a dead card
	 *************************************************************************************  */
	static void setDeadCard(Card c0) {
		for (var j = 0; j < DECK_SIZE; j++) {
			final boolean condition = cards[j] != null && cards[j].card == c0.card;
			if (condition) {
				cards[j] = null;
				deadIndexes[deadCardCount] = j;
				deadCards[deadCardCount++] = c0;
				break;
			}
		}
	}

	/*- *************************************************************************************
	 *  Clear dead cards 
	 *  If cards have been removed from the deck then dead cards are placed before the current
	 *  index and will be the next to be dealt.
	 *  If the dead card has already been used then returning it to the deck would
	 *  result in it being used a second time no changes are made.
	 *  If the current index is 0, no cards have been dealt, the dead card is put back in 
	 *  the slot that it was removed from.
	 *************************************************************************************  */
	static void clearDeadCards() {
		final int k = deadCardCount;
		for (var j = 0; j < k; j++) {
			if (currentIndex > 0) {
				// Cards have been dealt from this deck
				if (deadIndexes[j] >= currentIndex) {
					// This dead card was never dealt so put it back
					cards[deadIndexes[j]] = deadCards[j];
				} else {
					// This dead card was dealt so ignore so we do not use it again
				}
			} else {
				// Cards have not been dealt from this deck so put it back in the deck
				cards[deadIndexes[j]] = deadCards[j];
			}
		}
		deadCardCount = 0;
	}

	/*- *************************************************************************************
	 * New random seed
	 **************************************************************************************/
	static void setSeed(long s) {
		seed = s;
	}

	/*- *************************************************************************************
	 * The Knuth (or Fisher-Yates) shuffling algorithm.
	 **************************************************************************************/
	static void shuffle() {
		if (!newDeck) {
			newDeck();
		}
		deadCardCount = 0;
		cards = cardsInit;
		currentIndex = 0;
		// Loop over array.
		for (var i = 0; i < DECK_SIZE; ++i) {
			// Get a random index of the array past the current index.
			// ... The argument is an exclusive bound.
			// It will not go past the array's end.
			final var randomValue = i + random.nextInt(DECK_SIZE - i);
			// Swap the random element with the present element.
			final var randomElement = cards[randomValue];
			cards[randomValue] = cards[i];
			cards[i] = randomElement;
		}
	}

}
