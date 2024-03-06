package holdemevaluator;

/*-  ******************************************************************************
 * This Class simply updates the arrays in the Class IndexArrays.
 * It is called after each player completes a street, at showdown, and after all hands 
 * have been played. 
* 
* The public methods are:
* 1. updateFlop()
* 2. updateTurn()
* 3. updateRiver()
* 4. updateShowdowns()
* 5. updateSummary()
*
* The first 4 update arrays using several indexes and and values from:
* EvalData.drawArray, EvalData,madeArray, EvalData.showdownHand.
*
* summary performs some simple calculations such as adding all of the rows in an array.

Sure, the following Java function takes as input a Monte Carlo simulation result, represented as a 
probability of winning, and returns a simple recommended action (CHECK, CALL, RAISE, or FOLD).
Note that this method oversimplifies the strategic nuances of poker and doesn't consider the 
current pot size, the bet to you, the number of players left to act, your stack size, or opponent 
tendencies, but it can serve as a basic guideline.

public class MonteCarloPokerStrategy {
    
    public enum Action {
        CHECK, CALL, RAISE, FOLD
    }

    public static void main(String[] args) {
        // testing
     
    }

    public static Action recommendAction(double winProbability) {
        if (winProbability > 0.75) {
            return Action.RAISE;
        } else if (winProbability > 0.5) {
            return Action.CALL;
        } else if (winProbability > 0.25) {
            return Action.CHECK;
        } else {
            return Action.FOLD;
        }
    }
}
This function interprets the winning probability in a straightforward way:

If the winning probability is over 75%, it recommends raising, since your hand is strong.
If the winning probability is between 50% and 75%, it recommends calling, as your hand is 
likely competitive but not dominant.
If the winning probability is between 25% and 50%, it recommends checking, since your hand
 might have potential but is currently weak.
If the winning probability is below 25%, it recommends folding, as your hand is likely 
not worth investing more chips.
Again, this is a simplistic interpretation and should not be the sole determinant of your 
poker strategy. Real game situations require a nuanced understanding of many additional factors.

Calculating winning probability based on flop draws and made:
	If board is dry and hand or draw then probability good.
	If board is dry and no hand or draw then probability poor.
	If board is wet and hand or draw then probability good for draw, fair for hand.
	If board is wet and no hand or draw then probability poor.
	if board is neutral and hand or draw then probability poor for draw, good for hand.
	if board is neutral and no hand or draw then probability poor.

Calculating winning probability based on flop and showdown winning hand:
    If made hand and that hand wins at showdown. TODO
	Need an array that relates hand on flop to win at showdown combined with index.
	So HML on flop, made and and showdown for that made hand
	int [][][] wtf = new int [][][] wtf = new int [DRAW_SIZE][MADE_SHORT_SIZE][winLoose];
* 
* @author PEAK_
 ****************************************************************************** */

public class IndexArrayUpdate implements Constants {

	private IndexArrayUpdate() {
		throw new IllegalStateException("Utility class");
	}

	/*-  ****************************************************************************
	 * Update Flop data in IndexArrays every hand is processed
	 ****************************************************************************** */
	static void updateFlop() {
		if (GameControl.playerFolded[EvalData.seat]) {
			return;
		}
		++IndexArrays.hmlCountFlop;
		++IndexArrays.typeCount;
		++IndexArrays.wetDryCount;
		++IndexArrays.type1755Count;
		++IndexArrays.flopIndexCount;
		++IndexArrays.handValueCount;
		++IndexArrays.sumOfDrawCount;
		++IndexArrays.sumOfMadeCount;
		++IndexArrays.sumOfShowdownCount;
		++IndexArrays.boardArrayFlopCount;
		++IndexArrays.boardArrayTurnCount;
		++IndexArrays.boardArrayRiverCount;
		++IndexArrays.flopArraysCount;

		final int index = EvalData.hmlIndexFlop;
		if (index < 0) {
			Crash.log("WTF");
		}
		++IndexArrays.hmlDrawFlop[index][EvalData.players[EvalData.seat].typeOfDrawFlop];
		++IndexArrays.drawFlop[EvalData.seat];
		++IndexArrays.hmlMadeFlop[index][EvalData.players[EvalData.seat].typeOfMadeFlop];
		++IndexArrays.madeFlop[EvalData.seat];
		++IndexArrays.flopArraysCount;
		++IndexArrays.boardArrayFlopCount;

		++IndexArrays.wetDryDrawFlop[EvalData.wetDryIndexFlop][EvalData.players[EvalData.seat].typeOfDrawFlop];
		++IndexArrays.wetDryMadeFlop[EvalData.wetDryIndexFlop][EvalData.players[EvalData.seat].typeOfMadeFlop];

		++IndexArrays.typeOf1755DrawFlop[EvalData.typeOf1755IndexFlop][EvalData.players[EvalData.seat].typeOfDrawFlop];
		++IndexArrays.typeOf1755MadeFlop[EvalData.typeOf1755IndexFlop][EvalData.players[EvalData.seat].typeOfMadeFlop];

		++IndexArrays.SCBPDrawFlop[EvalData.SCBPIndexFlop][EvalData.players[EvalData.seat].typeOfDrawFlop];
		++IndexArrays.SCBPMadeFlop[EvalData.SCBPIndexFlop][EvalData.players[EvalData.seat].typeOfMadeFlop];

		for (int i = 0; i < BOARD_SIZE; ++i) {
			if (Board.boardArray[i]) {
				++IndexArrays.hmlBoardFlop[EvalData.hmlIndexFlop][i];
				++IndexArrays.wetDryBoardFlop[EvalData.wetDryIndexFlop][i];
				++IndexArrays.typeOf1755BoardFlop[EvalData.typeOf1755IndexFlop][i];
				++IndexArrays.SCBPBoardFlop[EvalData.SCBPIndexFlop][i];
			}
		}
	}

	/*-  ******************************************************************************
	 * Update Turn data in IndexArrays every hand is processed
	 ****************************************************************************** */
	static void updateTurn() {
		if (GameControl.playerFolded[EvalData.seat]) {
			return;
		}
		++IndexArrays.hmlCountTurn;
		++IndexArrays.turnArraysCount;
		final int index = EvalData.hmlIndexTurn;
		++IndexArrays.hmlDrawTurn[index][EvalData.players[EvalData.seat].typeOfDrawTurn];
		++IndexArrays.drawTurn[EvalData.seat];
		++IndexArrays.hmlMadeTurn[index][EvalData.players[EvalData.seat].typeOfMadeTurn];
		++IndexArrays.madeTurn[EvalData.seat];

		for (int i = 0; i < BOARD_SIZE; ++i) {
			if (Board.boardArray[i]) {
				++IndexArrays.hmlBoardFlop[EvalData.hmlIndexFlop][i];
				++IndexArrays.hmlBoardTurn[EvalData.hmlIndexTurn][i];
				++IndexArrays.wetDryBoardTurn[EvalData.wetDryIndexFlop][i];
				++IndexArrays.typeOf1755BoardTurn[EvalData.typeOf1755IndexFlop][i];
				++IndexArrays.SCBPBoardTurn[EvalData.SCBPIndexFlop][i];
			}
		}
	}

	/*-  ******************************************************************************
	 * Update River data in IndexArrays every hand is processed
	 ****************************************************************************** */
	static void updateRiver() {
		if (GameControl.playerFolded[EvalData.seat]) {
			return;
		}
		++IndexArrays.hmlCountRiver;
		++IndexArrays.riverArraysCount;
		final int index = EvalData.hmlIndexRiver;
		++IndexArrays.hmlMadeRiver[index][EvalData.players[EvalData.seat].typeOfMadeRiver];
		++IndexArrays.madeRiver[EvalData.seat];
		++IndexArrays.hmlMadeFlopToMadeRiver[EvalData.hmlIndexFlop][EvalData.players[EvalData.seat].typeOfMadeFlop][EvalData.players[EvalData.seat].typeOfMadeRiver];
		++IndexArrays.hmlDrawFlopToMadeRiver[EvalData.hmlIndexFlop][EvalData.players[EvalData.seat].typeOfDrawFlop][EvalData.players[EvalData.seat].typeOfMadeRiver];
		++IndexArrays.hmlMadeTurnToMadeRiver[EvalData.hmlIndexTurn][EvalData.players[EvalData.seat].typeOfMadeTurn][EvalData.players[EvalData.seat].typeOfMadeRiver];
		++IndexArrays.hmlDrawTurnToMadeRiver[EvalData.hmlIndexTurn][EvalData.players[EvalData.seat].typeOfDrawTurn][EvalData.players[EvalData.seat].typeOfMadeRiver];

		for (int i = 0; i < BOARD_SIZE; ++i) {
			if (Board.boardArray[i]) {
				++IndexArrays.hmlBoardRiver[EvalData.hmlIndexRiver][i];
				++IndexArrays.wetDryBoardRiver[EvalData.wetDryIndexFlop][i];
				++IndexArrays.typeOf1755BoardRiver[EvalData.typeOf1755IndexFlop][i];
				++IndexArrays.SCBPBoardRiver[EvalData.SCBPIndexFlop][i];

			}
		}
	}

	/*-  ****************************************************************************
	 * Update Showdown data in IndexArrays  
	 ****************************************************************************** */
	static void updateShowdown() {
		for (int i = 0; i < PLAYERS; i++) {
			if (!GameControl.playerFolded[i] && GameControl.playerWonShowdown[i]) {
				++IndexArrays.hmlShowdownFlop[EvalData.hmlIndexFlop][GameControl.showdownHand[i]];
				++IndexArrays.hmlShowdownMadeWinsFlop[EvalData.hmlIndexFlop][GameControl.showdownHand[i]];
				++IndexArrays.hmlShowdownTurn[EvalData.hmlIndexTurn][GameControl.showdownHand[i]];
				++IndexArrays.hmlShowdownMadeWinsTurn[EvalData.hmlIndexTurn][GameControl.showdownHand[i]];
				++IndexArrays.hmlShowdownMadeWinsTurn[EvalData.hmlIndexFlop][GameControl.showdownHand[i]];
				++IndexArrays.hmlShowdownRiver[EvalData.hmlIndexRiver][GameControl.showdownHand[i]];
				++IndexArrays.hmlShowdownMadeWinsRiver[EvalData.hmlIndexRiver][GameControl.showdownHand[i]];
				++IndexArrays.hmlShowdownMadeWinsRiver[EvalData.hmlIndexFlop][GameControl.showdownHand[i]];

				++IndexArrays.wetDryShowdownFlop[EvalData.wetDryIndexFlop][GameControl.showdownHand[i]];
				++IndexArrays.typeOf1755ShowdownFlop[EvalData.typeOf1755IndexFlop][GameControl.showdownHand[i]];
				++IndexArrays.SCBPShowdownFlop[EvalData.SCBPIndexFlop][GameControl.showdownHand[i]];

				++IndexArrays.showdownCount;
			}
		}
		for (int i = 0; i < PLAYERS; i++) {
			if (!GameControl.playerFolded[i] && GameControl.playerWonShowdown[i]) {
				++IndexArrays.hmlMadeFlopToMadeWon[EvalData.hmlIndexFlop][EvalData.players[i].typeOfMadeFlop][GameControl.showdownHand[i]];
				++IndexArrays.hmlDrawFlopToMadeWon[EvalData.hmlIndexFlop][EvalData.players[i].typeOfDrawFlop][GameControl.showdownHand[i]];
				++IndexArrays.hmlMadeTurnToMadeWon[EvalData.hmlIndexTurn][EvalData.players[i].typeOfMadeTurn][GameControl.showdownHand[i]];
				++IndexArrays.hmlDrawTurnToMadeWon[EvalData.hmlIndexTurn][EvalData.players[i].typeOfDrawTurn][GameControl.showdownHand[i]];

				++IndexArrays.wetDryMadeFlopToMadeWon[EvalData.wetDryIndexFlop][EvalData.players[i].typeOfMadeFlop][GameControl.showdownHand[i]];
				++IndexArrays.wetDryDrawFlopToMadeWon[EvalData.wetDryIndexFlop][EvalData.players[i].typeOfDrawFlop][GameControl.showdownHand[i]];
				++IndexArrays.wetDryMadeTurnToMadeWon[EvalData.wetDryIndexFlop][EvalData.players[i].typeOfMadeTurn][GameControl.showdownHand[i]];
				++IndexArrays.wetDryDrawTurnToMadeWon[EvalData.wetDryIndexFlop][EvalData.players[i].typeOfDrawTurn][GameControl.showdownHand[i]];

				++IndexArrays.typeOf1755MadeFlopToMadeWon[EvalData.typeOf1755IndexFlop][EvalData.players[i].typeOfMadeFlop][GameControl.showdownHand[i]];
				++IndexArrays.typeOf1755DrawFlopToMadeWon[EvalData.typeOf1755IndexFlop][EvalData.players[i].typeOfDrawFlop][GameControl.showdownHand[i]];
				++IndexArrays.typeOf1755MadeTurnToMadeWon[EvalData.typeOf1755IndexFlop][EvalData.players[i].typeOfMadeTurn][GameControl.showdownHand[i]];
				++IndexArrays.typeOf1755DrawTurnToMadeWon[EvalData.typeOf1755IndexFlop][EvalData.players[i].typeOfDrawTurn][GameControl.showdownHand[i]];

				++IndexArrays.SCBPMadeFlopToMadeWon[EvalData.SCBPIndexFlop][EvalData.players[i].typeOfMadeFlop][GameControl.showdownHand[i]];
				++IndexArrays.SCBPDrawFlopToMadeWon[EvalData.SCBPIndexFlop][EvalData.players[i].typeOfDrawFlop][GameControl.showdownHand[i]];
				++IndexArrays.SCBPMadeTurnToMadeWon[EvalData.SCBPIndexFlop][EvalData.players[i].typeOfDrawTurn][GameControl.showdownHand[i]];
				++IndexArrays.SCBPDrawTurnToMadeWon[EvalData.SCBPIndexFlop][EvalData.players[i].typeOfDrawTurn][GameControl.showdownHand[i]];
			}
		}
	}

	/*-  ****************************************************************************
	 * Update Summary data in IndexArrays  
	****************************************************************************** */
	static void updateSummary() {

	}

	/*-  ******************************************************************************
	 * Initialize data in IndexArrays prior to calculating 
	 ****************************************************************************** */
	static void initializeIndexArrays() {
		IndexArrays.flopArraysCount = 0;
		IndexArrays.turnArraysCount = 0;
		IndexArrays.riverArraysCount = 0;
	}

}
