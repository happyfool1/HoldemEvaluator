package holdemevaluator;

import java.math.BigDecimal;

/*- ****************************************************************************
* This Class simulates a players decision during a game.
* getDecision is called whenever the player becomes active ( it is his turn ).
* Player has access to the current conditions facing him through GameControl.
* 		Street - preflop, flop, turn,river, showdown
* 		Bet to him ( check, bet, raise, all-in )
* 		Position ( SB, BB, UTG,MP, CO, BU )
* 		Relative position ( FIRST, FIRST_HU, MIDDLE, LAST, LAST_HU )
* 		Range for preflop play.
*
* The shipped version of this project has only a primitive Player.
* You may wish to make this a real simulation.
* See the Game project ( if it is available yet ).
* It is my project that plays a very sophisticated simulation using external control
* files that can all be edited for experimentation.
* Uses GTO concepts such as MDF.
* Has many  reports of results like EV analysis.
* Creates Hand History files that you can analyze with Holdem Manager or other
* analysis products.
* Has an interactive mode where you can play against simulator.
* Uses data developed by my Hand History analysis project hand_history_analysis.
* May not be available yet.
*
* @author PEAK_
*******************************************************************************/
public class PlayerPreflop implements Constants {

	/*- ****************************************************************************
	 * Data
	 **************************************************************************** */
	private int seat = -1;
	private int handIndex = -1;
	private int position = -1;
	private final HandRangePlayability range;

	// Can raise or can call
	private boolean canRaise = false;
	private boolean canCall = false;

	/*- ****************************************************************************
	 * Constructor
	 * Arg0 - seat
	 * Arg1 - Hand ranges 
	 **************************************************************************** */
	PlayerPreflop(int seat, HandRangePlayability range) {
		this.seat = seat;
		this.range = range;
	}

	/*- ****************************************************************************
	 * Make a decision based on action to me:
	 *  	PREFLOP_LIMP,   PREFLOP_OPEN,    PREFLOP_BET3,
	 *  	 PREFLOP_ALLIN,  PREFLOP_CALL_ALLIN.
	 *
	 *  The decision is obtained from an instance of HandRangePlayability
	 *  Range tables were read from files. 
	 *  The files were from tables used in a full simulation program and are very
	 *  close to how a good player would play preflop.
	 * **************************************************************************** */
	int getDecision() {
		if (GameControl.playerFolded[this.seat]) {
			Crash.log("WTF " + this.seat);
		}
		this.handIndex = EvalData.players[this.seat].handIndex;
		this.canRaise = false;
		this.canCall = false;
		switch (GameControl.betType) {
		case PREFLOP_LIMP -> {
			if (this.range.decisionRaise(this.handIndex, this.position, GameControl.betType, 0)) {
				doOpen();
				return PREFLOP_OPEN;
			}
			if (this.range.decisionCall(this.handIndex, this.position, GameControl.betType, 0)) {
				doLimp();
				if (this.position == BB) {
					return PREFLOP_OPEN;
				}
				return PREFLOP_LIMP;
			}
			doFold();
			return PREFLOP_LIMP;
		}
		case PREFLOP_OPEN -> {
			if (this.range.decisionRaise(this.handIndex, this.position, GameControl.betType, 0)) {
				doBet3();
				return PREFLOP_BET3;
			}
			if (this.range.decisionCall(this.handIndex, this.position, GameControl.betType, 0)) {
				doCall();
				return PREFLOP_OPEN;
			}
			doFold();
			return PREFLOP_OPEN;
		}
		case PREFLOP_BET3 -> {
			if (this.range.decisionRaise(this.handIndex, this.position, GameControl.betType, 0)) {
				doBet4();
				return PREFLOP_BET4;
			}
			if (this.range.decisionCall(this.handIndex, this.position, GameControl.betType, 0)) {
				doCall();
				return PREFLOP_BET3;
			}
			doFold();
			return PREFLOP_BET3;
		}
		case PREFLOP_BET4 -> {
			if (this.range.decisionRaise(this.handIndex, this.position, GameControl.betType, 0)) {
				doAllin();
				return PREFLOP_ALLIN;
			}
			if (this.range.decisionCall(this.handIndex, this.position, GameControl.betType, 0)) {
				doCall();
				return PREFLOP_BET4;
			}
			doFold();
			return PREFLOP_BET4;
		}
		case PREFLOP_ALLIN -> {
			if (this.range.decisionRaise(this.handIndex, this.position, GameControl.betType, 0)) {
				doAllin();
				return PREFLOP_ALLIN;
			}
			if (this.range.decisionCall(this.handIndex, this.position, GameControl.betType, 0)) {
				doAllinCall();
				return PREFLOP_ALLIN;
			}
			doFold();
			return PREFLOP_ALLIN;
		}
		case PREFLOP_CALL_ALLIN -> {
			if (this.range.decisionRaise(this.handIndex, this.position, GameControl.betType, 0)) {
				doAllin();
				return PREFLOP_ALLIN;
			}
			if (this.range.decisionCall(this.handIndex, this.position, GameControl.betType, 0)) {
				doAllinCall();
				return PREFLOP_ALLIN;
			}
			doFold();
			return PREFLOP_ALLIN;
		}
		default -> {
		}
		}
		Logger.log("ERROR Action getDecision(int GameControl.betType ) GameControl.betType  invalid "
				+ PLAYER_ACTIONS_ST[GameControl.betType]);
		Crash.log("Program bug ");
		return -1;
	}

	/*- ****************************************************************************
	 * Do Fold
	 **************************************************************************** */
	private void doFold() {
		if (GameControl.playerFolded[this.seat]) {
			Crash.log("$$$");
		}
		if (GameControl.playerFolded[this.seat] || GameControl.playerAllin[this.seat] || GameControl.foldCount == 5) {
			Crash.log(new StringBuilder().append("doFold() ").append(this.seat).append(" ")
					.append(GameControl.playerFolded[this.seat]).append(" ").append(GameControl.playerAllin[this.seat])
					.append(" ").append(GameControl.foldCount).toString());
		}
		++GameControl.foldCount;
		HandHistory.isFold[0][this.seat][GameControl.orbit] = true;
		++HandHistory.foldedPreflop[GameControl.orbit][this.seat];
		GameControl.playerFoldedPreflop[seat] = true;
		GameControl.playerFolded[this.seat] = true;
		HandHistory.potThenBD[0][this.seat][GameControl.orbit] = GameControl.potBD;
		HandHistory.stackThenBD[0][this.seat][GameControl.orbit] = GameControl.stackBD[this.seat];
		HandHistory.moneyInThenBD[0][this.seat][GameControl.orbit] = GameControl.moneyInBD[this.seat];
	}

	/*- ****************************************************************************
	 * Do Limp
	 **************************************************************************** */
	private void doLimp() {
		if (GameControl.playerFolded[this.seat]) {
			Crash.log("$$$");
		}
		// Money
		if (this.position == BB) {
			HandHistory.isCheck[0][this.seat][1] = true;
			HandHistory.callBD[0][this.seat][1] = zeroBD;
			GameControl.moneyInBD[this.seat] = zeroBD;
			HandHistory.potThenBD[0][this.seat][1] = GameControl.potBD;
			HandHistory.stackThenBD[0][this.seat][1] = GameControl.stackBD[this.seat];
			HandHistory.moneyInThenBD[0][this.seat][0] = zeroBD;
			return;
		}
		// Flags and counts
		++GameControl.limpCount;
		HandHistory.isCall[0][this.seat][GameControl.orbit] = true;
		HandHistory.isLimp[0][this.seat][GameControl.orbit] = true;
		GameControl.playerLimpedPreflop[this.seat] = true;
		if (this.position == SB) {
			final var diff = GameControl.BBBetBD.subtract(GameControl.SBBetBD);
			GameControl.stackBD[this.seat] = GameControl.stackBD[this.seat].subtract(diff);
			HandHistory.callBD[0][this.seat][1] = diff;
			GameControl.potBD = GameControl.potBD.add(diff);
			GameControl.moneyInBD[this.seat] = GameControl.moneyInBD[this.seat].add(diff);
			HandHistory.potThenBD[0][this.seat][1] = GameControl.potBD;
			HandHistory.stackThenBD[0][this.seat][1] = GameControl.stackBD[this.seat];
			HandHistory.moneyInThenBD[0][this.seat][1] = GameControl.moneyInBD[this.seat];
			return;
		}
		GameControl.stackBD[this.seat] = GameControl.stackBD[this.seat].subtract(GameControl.BBBetBD);
		HandHistory.callBD[0][this.seat][0] = GameControl.BBBetBD;
		GameControl.potBD = GameControl.potBD.add(GameControl.BBBetBD);
		GameControl.moneyInBD[this.seat] = GameControl.moneyInBD[this.seat].add(GameControl.BBBetBD);
		HandHistory.potThenBD[0][this.seat][0] = GameControl.potBD;
		HandHistory.stackThenBD[0][this.seat][0] = GameControl.stackBD[this.seat];
		HandHistory.moneyInThenBD[0][this.seat][0] = GameControl.moneyInBD[this.seat];
	}

	/*- ****************************************************************************
	 * Do Open
	 **************************************************************************** */
	private void doOpen() {
		if (GameControl.playerFolded[this.seat]) {
			Crash.log("$$$");
		}
		canRaiseOrCall();
		if (this.canRaise) {
			doRaises(GameControl.BBBetBD.multiply(GameControl.preflopBetMultiplyerOpenBD));
		} else {
			doFold();
		}
	}

	/*- ****************************************************************************
	 * Do Call
	 **************************************************************************** */
	private void doCall() {
		if (GameControl.playerFolded[this.seat]) {
			Crash.log("$$$");
		}
		canRaiseOrCall();
		if (this.canCall) {
			doCalls();
		} else {
			doFold();
		}
	}

	/*- ****************************************************************************
	 *  Do raises
	 * Arg0-  bet size
	 **************************************************************************** */
	private void doRaises(BigDecimal bet) {
		if (GameControl.playerFolded[this.seat]) {
			Crash.log("$$$");
		}
		final var call = GameControl.betNowBD.subtract(GameControl.moneyInBD[this.seat]);
		final var temp = bet.add(call);
		if (call.compareTo(zeroBD) < 0 || temp.compareTo(zeroBD) <= 0
				|| GameControl.moneyInBD[this.seat].compareTo(zeroBD) < 0
				|| GameControl.betNowBD.compareTo(zeroBD) <= 0) {
			Crash.log(new StringBuilder().append("Player preflop ").append(GameControl.moneyInBD[this.seat]).append(" ")
					.append(GameControl.betNowBD).append(" ").append(temp).append(" ").append(call).append(" ")
					.append(this.seat).append(" ").append(bet).toString());
		}
		// Flags and counts
		HandHistory.isRaise[0][this.seat][GameControl.orbit] = true;
		GameControl.moneyInBD[this.seat] = temp;
		GameControl.stackBD[this.seat] = GameControl.stackBD[this.seat].subtract(temp);
		GameControl.potBD = GameControl.potBD.add(temp);

		GameControl.betNowBD = temp;

		HandHistory.potThenBD[0][this.seat][GameControl.orbit] = GameControl.potBD;
		HandHistory.stackThenBD[0][this.seat][GameControl.orbit] = GameControl.stackBD[this.seat];
		HandHistory.moneyInThenBD[0][this.seat][GameControl.orbit] = GameControl.moneyInBD[this.seat];

		HandHistory.betBD[0][this.seat][GameControl.orbit] = bet;
		HandHistory.raisedToBD[0][this.seat][GameControl.orbit] = temp;
		GameControl.betCalled = false;
	}

	/*- ****************************************************************************
	 *  Do calls
	 **************************************************************************** */
	private void doCalls() {
		if (GameControl.playerFolded[this.seat]) {
			Crash.log("$$$");
		}
		final var call = GameControl.betNowBD.subtract(GameControl.moneyInBD[this.seat]);
		// Flags and counts
		HandHistory.isCall[0][this.seat][GameControl.orbit] = true;
		// Money
		// 8 + 22= 30
		GameControl.moneyInBD[this.seat] = GameControl.moneyInBD[this.seat].add(call);
		GameControl.stackBD[this.seat] = GameControl.stackBD[this.seat].subtract(call);
		HandHistory.betBD[0][this.seat][GameControl.orbit] = call;
		GameControl.potBD = GameControl.potBD.add(call);
		HandHistory.potThenBD[0][this.seat][GameControl.orbit] = GameControl.potBD;
		HandHistory.stackThenBD[0][this.seat][GameControl.orbit] = GameControl.stackBD[this.seat];
		HandHistory.moneyInThenBD[0][this.seat][GameControl.orbit] = GameControl.moneyInBD[this.seat];
		HandHistory.callBD[0][this.seat][GameControl.orbit] = call;
		GameControl.betCalled = true;
	}

	/*- ****************************************************************************
	 * Can raise or call?
	 * Sets boolean canRaise and canCall
	 **************************************************************************** */
	void canRaiseOrCall() {
		this.canCall = false;
		this.canRaise = false;
		var temp1 = GameControl.betNowBD.subtract(GameControl.moneyInBD[this.seat]);
		var temp2 = GameControl.stackBD[this.seat].subtract(temp1);

		if (temp2.compareTo(zeroBD) > 0 && temp1.compareTo(zeroBD) > 0) {
			this.canRaise = true;
			this.canCall = true;
		}
	}

	/*- ****************************************************************************
	 *  Re-buy if low on chips.
	 *  Called before the start of every game.
	 **************************************************************************** */
	private void checkRebuy() {
		if (GameControl.stackBD[this.seat].compareTo(GameControl.maxStackBD) > 0) {
			GameControl.stackBD[this.seat] = GameControl.buyinBD;
		}
		if (GameControl.stackBD[this.seat].compareTo(GameControl.rebuyBD) < 0) {
			GameControl.stackBD[this.seat] = GameControl.buyinBD.subtract(GameControl.stackBD[this.seat]);
		}
	}

	/*- ****************************************************************************
	 * DoAll-In
	 **************************************************************************** */
	private void doAllin() {
		if (GameControl.playerFolded[this.seat] || GameControl.playerAllin[this.seat]) {
			Crash.log("$$$");
		}
		final var bet = GameControl.stackBD[this.seat].add(GameControl.betToMeBD);
		// Flags and counts
		HandHistory.isRaise[0][this.seat][GameControl.orbit] = true;
		++GameControl.allinCount;
		GameControl.playerAllin[this.seat] = true;
		HandHistory.isAllin[0][this.seat][GameControl.orbit] = true;
		// Money
		HandHistory.betBD[0][this.seat][GameControl.orbit] = bet;
		HandHistory.raisedToBD[0][this.seat][GameControl.orbit] = GameControl.betNowBD.add(bet);
		GameControl.betNowBD = GameControl.betNowBD.add(bet);
		GameControl.moneyInBD[this.seat] = GameControl.moneyInBD[this.seat].add(bet);
		GameControl.potBD = GameControl.potBD.add(bet);
		HandHistory.potThenBD[0][this.seat][GameControl.orbit] = GameControl.potBD;
		HandHistory.stackThenBD[0][this.seat][GameControl.orbit] = GameControl.stackBD[this.seat];
		HandHistory.moneyInThenBD[0][this.seat][GameControl.orbit] = GameControl.moneyInBD[this.seat];
		GameControl.stackBD[this.seat] = zeroBD;
		GameControl.betCalled = false;
	}

	/*- ****************************************************************************
	 * DoAll-In Call
	 **************************************************************************** */
	private void doAllinCall() {
		if (GameControl.playerFolded[this.seat]) {
			Crash.log("$$$");
		}
		if (GameControl.playerFolded[this.seat] || GameControl.playerAllin[this.seat]) {
			Crash.log("$$$");
		}
		final var call = GameControl.betNowBD.subtract(GameControl.moneyInBD[this.seat]);
		// Flags and counts
		++GameControl.allinCount;
		GameControl.playerAllin[this.seat] = true;
		HandHistory.isAllin[0][this.seat][GameControl.orbit] = true;
		HandHistory.isCall[0][this.seat][GameControl.orbit] = true;
		// Money
		GameControl.stackBD[this.seat] = GameControl.stackBD[this.seat].subtract(call);
		HandHistory.callBD[0][this.seat][GameControl.orbit] = call;
		GameControl.moneyInBD[this.seat] = GameControl.moneyInBD[this.seat].add(call);
		GameControl.potBD = GameControl.potBD.add(call);
		HandHistory.potThenBD[0][this.seat][GameControl.orbit] = GameControl.potBD;
		HandHistory.stackThenBD[0][this.seat][GameControl.orbit] = GameControl.stackBD[this.seat];
		HandHistory.moneyInThenBD[0][this.seat][GameControl.orbit] = GameControl.moneyInBD[this.seat];
		GameControl.betCalled = true;
	}

	/*- ****************************************************************************
	 * Do Bet 4
	 **************************************************************************** */
	private void doBet4() {
		if (GameControl.playerFolded[this.seat]) {
			Crash.log("$$$");
		}
		canRaiseOrCall();
		if (canRaise) {
			doRaises(GameControl.betNowBD.multiply(GameControl.preflopBetMultiplyer4BetBD));
		} else {
			doFold();
		}
	}

	/*- ****************************************************************************
	 * Do Bet 3
	 **************************************************************************** */
	private void doBet3() {
		if (GameControl.playerFolded[this.seat]) {
			Crash.log("$$$");
		}
		canRaiseOrCall();
		if (canRaise) {
			doRaises(GameControl.betNowBD.multiply(GameControl.preflopBetMultiplyer3BetBD));
		} else {
			doFold();
		}
	}

	/*- **************************************************************************
	* Initialize
	* Uses seat number to initialize variables for a new game
	* Arg0 - seat
	**************************************************************************** */
	void initialize(int seat) {
		this.seat = seat;
		this.handIndex = EvalData.players[this.seat].handIndex;
		this.position = GameControl.seatPos[this.seat];
		checkRebuy();
	}

}
