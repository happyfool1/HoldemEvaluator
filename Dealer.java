package holdemevaluator;

/*- ******************************************************************************
* This class simulates a dealer dealing cards in a normal game.
* A full table is dealt, six sets of hole cards, a Flop, Turn and River.
* Cards are random.
*
* The positions are rotated each hand, as in a normal game.
*
* The typical ranges for each position in a no-limit Texas hold'em 6-max $1/$2 game would
* vary depending on various factors such as the playing style of opponents, table dynamics,
* and stack sizes, but here are some general guidelines:
*
* Under the Gun (UTG) - This is the first position after the blinds, and players in this position
* should generally play tight and conservative ranges.
* Some typical hands that players may play in this position are strong pairs (e.g., AA, KK, QQ),
* broadway hands (e.g., AK, AQ), and some suited connectors (e.g., 89s, 78s).
 *
* Middle Position (MP) - Players in middle position have some information about the players
* in earlier positions and can play a wider range of hands than UTG.
* Some typical hands that players may play in this position are strong pairs, broadway hands,
* some suited connectors, and some suited aces (e.g., A9s, A8s).
*
* Cutoff (CO) - The player in this position is just one seat away from the dealer and can play
* a wider range of hands, as they have the advantage of acting last on all post-flop
* betting rounds.
* Some typical hands that players may play in this position are pairs, broadway hands,
* suited connectors, suited aces, and some suited one-gappers (e.g., J9s, T8s).
*
* Button (BTN) - The player on the button has the most advantageous position in the game
* and can play a very wide range of hands.
* Some typical hands that players may play in this position are pairs, broadway hands,
* suited connectors, suited aces, suited one-gappers, and any two cards that
* can make a straight or flush.
*
* Small Blind (SB) - This is the first of the two forced bets and players in this position are
* at a disadvantage, as they will be out of position for the rest of the hand.
* Some typical hands that players may play in this position are strong pairs, broadway hands,
* and some suited connectors.
*
* Big Blind (BB) - This is the second of the two forced bets and players in this position
* are also at a disadvantage, but they get to act last in the pre-flop betting round.
* Some typical hands that players may play in this position are strong pairs,
* broadway hands, suited connectors, and some suited aces.
*
* Again, these are just general guidelines and there can be a lot of variation depending
* on the specific table dynamics and player tendencies.
* It's always important to adjust your range based on the table and players you're up against.
*
* Sure, here are some rough percentages of the typical ranges for each position in a
* no-limit Texas hold'em 6-max $1/$2 game:
*
* Under the Gun (UTG) - 8% to 12% of hands
* Middle Position (MP) - 12% to 18% of hands
* Cutoff (CO) - 20% to 30% of hands
* Button (BTN) - 35% to 50% of hands
* Small Blind (SB) - 10% to 15% of hands
* Big Blind (BB) - 10% to 15% of hands
*
* Again, these are just rough estimates and the percentages may vary
* depending on the specific table and players you're up against.
* It's always important to adjust your range based on the situation and your
* opponents' tendencies.
*
* @author PEAK_
*******************************************************************************/

public class Dealer implements Constants {

	private Dealer() {
		throw new IllegalStateException("Utility class");
	}

	/*- ******************************************************************************
	 * This method will deal the Flop from the deck.
	 * The flop is sorted.
	  ********************************************************************************/
	static void dealFlop() {
		Board.boardUnsorted[0] = Deck.pop();
		Board.boardUnsorted[1] = Deck.pop();
		Board.boardUnsorted[2] = Deck.pop();
		Board.doFlop();
	}

	/*- ******************************************************************************
	 * This method sets the three flop cards
	 * The cards are removed from Deck so there will be no accidental duplicates. 
	 * The flop is sorted then copied into board.
	 * The cards are removed from Deck so there will be no accidental duplicates. 
	 * Arg0 - A card
	 * Arg1 - A card
	 * Arg2 - A card
	  ********************************************************************************/
	static void setFlopCards(Card card1, Card card2, Card card3) {
		Board.boardUnsorted[0] = card1;
		Board.boardUnsorted[1] = card2;
		Board.boardUnsorted[2] = card3;
		Deck.setDeadCard(card1);
		Deck.setDeadCard(card2);
		Deck.setDeadCard(card3);
		Board.doFlop();
	}

	/*- ******************************************************************************
	  * This method will deal the turn card.
	 * It is inserted into the board in order to maintain sort order.
	  ********************************************************************************/
	static void dealTurnCard() {
		Board.boardUnsorted[3] = Deck.pop();
		Board.doTurn();
	}

	/*- ******************************************************************************
	 * This method will deal theRiver card from the deck.
	 * It is inserted into the board in order to maintain sort order
	  ********************************************************************************/
	static void dealRiverCard() {
		Board.boardUnsorted[4] = Deck.pop();
		Board.doRiver();
	}

	/*- ******************************************************************************
	* This method sets Turn card.
	* The card is removed from Deck so there will be no accidental duplicates. 
	* The card is inserted into the board in order to keep it sorted. 
	* Flop is lready sorted so just do an insert.
	* Arg0 - A  Card
	********************************************************************************/
	static void setTurnCard(Card card) {
		Board.boardUnsorted[3] = card;
		Deck.setDeadCard(card);
		Board.doTurn();
	}

	/*- ******************************************************************************
	* This method sets River card.
	* The card is removed from Deck so there will be no accidental duplicates. 
	* The card is inserted into the board in order to keep it sorted. 
	* Flop is already sorted so just do an insert.
	* Arg0 - A  Card
	  ********************************************************************************/
	static void setRiverCard(Card card) {
		Board.boardUnsorted[4] = card;
		Deck.setDeadCard(card);
		Board.doRiver();
	}

	/*- ******************************************************************************
	 * This method deals hole cards for all players
	  ********************************************************************************/
	static void dealAllHoleCards() {
		for (var i = 0; i < PLAYERS; i++) {
			dealHoleCards(i);
		}
	}

	/*- ******************************************************************************
	 * This method deals two cards from the deck and places then in the hole cards.
	 * The hole cards are sorted.
	 * Arg0 - Seat number 0-5
	  ********************************************************************************/
	static void dealHoleCards(int seat) {
		EvalData.players[seat].holeCard1 = Deck.pop();
		EvalData.players[seat].holeCard2 = Deck.pop();
		sortHoleCards(seat);
	}

	/*- ******************************************************************************
	 * This method will deal all hole cards then deal the flop.
	  ********************************************************************************/
	static void dealHoleCardsAndFlop() {
		dealAllHoleCards();
		dealFlop();
	}

	/*- ******************************************************************************
	 * This method sets the two hole cards.
	 * The cards are removed from Deck so there will be no accidental duplicates. 
	 * The hole cards are sorted.
	 * Arg0 - Seat number 0-5
	 * Arg1 - A card
	 * Arg2 - A card
	  ********************************************************************************/
	static void setHoleCards(int seat, Card card1, Card card2) {
		EvalData.players[seat].holeCard1 = card1;
		EvalData.players[seat].holeCard2 = card2;
		sortHoleCards(seat);
		Deck.setDeadCard(card1);
		Deck.setDeadCard(card2);
	}

	/*- ******************************************************************************
	 * Sort HoleCards
	*******************************************************************************/
	static void sortHoleCards(int seat) {
		if (EvalData.players[seat].holeCard1.value < EvalData.players[seat].holeCard2.value) {
			final Card cardSave = EvalData.players[seat].holeCard1;
			EvalData.players[seat].holeCard1 = EvalData.players[seat].holeCard2;
			EvalData.players[seat].holeCard2 = cardSave;
		}
		EvalData.players[seat].handIndex = HandRange.getArrayIndexCard(EvalData.players[seat].holeCard1,
				EvalData.players[seat].holeCard2);
		GameControl.playerFolded[seat] = false;
	}

	/*- ******************************************************************************
	 * This method will override the default which is to rotate after each hand.
	 * ********************************************************************************/
	static void setRotatePositions(boolean b) {
		EvalData.rotatePlayers = b;
	}

}
