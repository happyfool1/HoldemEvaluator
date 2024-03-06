package holdemevaluator;

/*-  ******************************************************************************
* This is a one time only application used to move hand ranges from Hold'emDatabase
* to the directory for this project Hold'emEvaluator.
* 60 ranges from Hold'emDatabase for one player type are combined into one 
* class HandRangeCumulative.
*  @author PEAK_  
* ****************************************************************************** */

public class HandRangeConverter implements Constants {

	private static final HandRange rangeSBLimp = new HandRange();
	private static final HandRange rangeSBOpen = new HandRange();
	private static final HandRange rangeSBCall = new HandRange();
	private static final HandRange rangeSBBet3 = new HandRange();
	private static final HandRange rangeSBBet3Call = new HandRange();
	private static final HandRange rangeSBBet4 = new HandRange();
	private static final HandRange rangeSBBet4Call = new HandRange();
	private static final HandRange rangeSBAllin = new HandRange();
	private static final HandRange rangeSBAllinCall = new HandRange();
	private static final HandRange rangeBBLimp = new HandRange();
	private static final HandRange rangeBBOpen = new HandRange();
	private static final HandRange rangeBBCall = new HandRange();
	private static final HandRange rangeBBBet3 = new HandRange();
	private static final HandRange rangeBBBet3Call = new HandRange();
	private static final HandRange rangeBBBet4 = new HandRange();
	private static final HandRange rangeBBBet4Call = new HandRange();
	private static final HandRange rangeBBAllin = new HandRange();
	private static final HandRange rangeBBAllinCall = new HandRange();
	private static final HandRange rangeUTGLimp = new HandRange();
	private static final HandRange rangeUTGOpen = new HandRange();
	private static final HandRange rangeUTGCall = new HandRange();
	private static final HandRange rangeUTGBet3 = new HandRange();
	private static final HandRange rangeUTGBet3Call = new HandRange();
	private static final HandRange rangeUTGBet4 = new HandRange();
	private static final HandRange rangeUTGBet4Call = new HandRange();
	private static final HandRange rangeUTGAllin = new HandRange();
	private static final HandRange rangeUTGAllinCall = new HandRange();
	private static final HandRange rangeMPLimp = new HandRange();
	private static final HandRange rangeMPOpen = new HandRange();
	private static final HandRange rangeMPCall = new HandRange();
	private static final HandRange rangeMPBet3 = new HandRange();
	private static final HandRange rangeMPBet3Call = new HandRange();
	private static final HandRange rangeMPBet4 = new HandRange();
	private static final HandRange rangeMPBet4Call = new HandRange();
	private static final HandRange rangeMPAllin = new HandRange();
	private static final HandRange rangeMPAllinCall = new HandRange();
	private static final HandRange rangeCOLimp = new HandRange();
	private static final HandRange rangeCOOpen = new HandRange();
	private static final HandRange rangeCOCall = new HandRange();
	private static final HandRange rangeCOBet3 = new HandRange();
	private static final HandRange rangeCOBet3Call = new HandRange();
	private static final HandRange rangeCOBet4 = new HandRange();
	private static final HandRange rangeCOBet4Call = new HandRange();
	private static final HandRange rangeCOAllin = new HandRange();
	private static final HandRange rangeCOAllinCall = new HandRange();
	private static final HandRange rangeBULimp = new HandRange();
	private static final HandRange rangeBUOpen = new HandRange();
	private static final HandRange rangeBUCall = new HandRange();
	private static final HandRange rangeBUBet3 = new HandRange();
	private static final HandRange rangeBUBet3Call = new HandRange();
	private static final HandRange rangeBUBet4 = new HandRange();
	private static final HandRange rangeBUBet4Call = new HandRange();
	private static final HandRange rangeBUAllin = new HandRange();
	private static final HandRange rangeBUAllinCall = new HandRange();

	private static final HandRangeCumulative action = new HandRangeCumulative();
	private static String type = "?";

	public static void main(String[] s0) {
		type = "Hero";
		createRanges();
		doConversion();
		type = "Average";
		createRanges();
		doConversion();

		// type = "Fish";
		// createRanges();
		// doConversion();
		// type = "Nit";
		// createRanges();
		// doConversion();
		// type = "Lag";
		// createRanges();
		// doConversion();
		// type = "Tag";
		// createRanges();
		// doConversion();

	}

	/*- **************************************************************************** 
		 * Initialize instances of Player for each seat.
		 * One time initialization
		 * Instances are customized for current position :
		 * 		SB, BB, UTG, MP, CO, BU
		 **************************************************************************** */
	private static void createRanges() {
		rangeSBLimp.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\SB\\Limp.ser").toString());
		rangeSBOpen.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\SB\\Open.ser").toString());
		rangeSBCall.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\SB\\Call.ser").toString());
		rangeSBBet3.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\SB\\Bet3.ser").toString());
		rangeSBBet3Call.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\SB\\Bet3Call.ser").toString());
		rangeSBBet4.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\SB\\Bet4.ser").toString());
		rangeSBBet4Call.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\SB\\Bet4Call.ser").toString());
		rangeSBAllin.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\SB\\Allin.ser").toString());
		rangeSBAllinCall.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\SB\\AllinCall.ser").toString());

		rangeBBLimp.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\BB\\Limp.ser").toString());
		rangeBBOpen.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\BB\\Open.ser").toString());
		rangeBBCall.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\BB\\Call.ser").toString());
		rangeBBBet3.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\BB\\Bet3.ser").toString());
		rangeBBBet3Call.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\BB\\Bet3Call.ser").toString());
		rangeBBBet4.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\BB\\Bet4.ser").toString());
		rangeBBBet4Call.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\BB\\Bet4Call.ser").toString());
		rangeBBAllin.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\BB\\Allin.ser").toString());
		rangeBBAllinCall.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\BB\\AllinCall.ser").toString());

		rangeUTGLimp.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\UTG\\Limp.ser").toString());
		rangeUTGOpen.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\UTG\\Open.ser").toString());
		rangeUTGCall.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\UTG\\Call.ser").toString());
		rangeUTGBet3.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\UTG\\Bet3.ser").toString());
		rangeUTGBet3Call.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\UTG\\Bet3Call.ser").toString());
		rangeUTGBet4.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\UTG\\Bet4.ser").toString());
		rangeUTGBet4Call.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\UTG\\Bet4Call.ser").toString());
		rangeUTGAllin.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\UTG\\Allin.ser").toString());
		rangeUTGAllinCall.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\UTG\\AllinCall.ser").toString());

		rangeMPLimp.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\MP\\Limp.ser").toString());
		rangeMPOpen.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\MP\\Open.ser").toString());
		rangeMPCall.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\MP\\Call.ser").toString());
		rangeMPBet3.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\MP\\Bet3.ser").toString());
		rangeMPBet3Call.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\MP\\Bet3Call.ser").toString());
		rangeMPBet4.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\MP\\Bet4.ser").toString());
		rangeMPBet4Call.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\MP\\Bet4Call.ser").toString());
		rangeMPAllin.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\MP\\Allin.ser").toString());
		rangeMPAllinCall.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\MP\\AllinCall.ser").toString());

		rangeCOLimp.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Cutoff\\Limp.ser").toString());
		rangeCOOpen.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Cutoff\\Open.ser").toString());
		rangeCOCall.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Cutoff\\Call.ser").toString());
		rangeCOBet3.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Cutoff\\Bet3.ser").toString());
		rangeCOBet3Call.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Cutoff\\Bet3Call.ser").toString());
		rangeCOBet4.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Cutoff\\Bet4.ser").toString());
		rangeCOBet4Call.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Cutoff\\Bet4Call.ser").toString());
		rangeCOAllin.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Cutoff\\Allin.ser").toString());
		rangeCOAllinCall.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Cutoff\\AllinCall.ser").toString());

		rangeBULimp.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Button\\Limp.ser").toString());
		rangeBUOpen.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Button\\Open.ser").toString());
		rangeBUCall.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Button\\Call.ser").toString());
		rangeBUBet3.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Button\\Bet3.ser").toString());
		rangeBUBet3Call.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Button\\Bet3Call.ser").toString());
		rangeBUBet4.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Button\\Bet4.ser").toString());
		rangeBUBet4Call.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Button\\Bet4Call.ser").toString());
		rangeBUAllin.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Button\\Allin.ser").toString());
		rangeBUAllinCall.readRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Button\\AllinCall.ser").toString());
	}

	/*
	 * Write the completed HandRangeCumulative class to a disk file
	 */
	static void writeToFile() {
		action.writeToFile(new StringBuilder().append(EvalData.applicationDirectory).append(type)
				.append("-HandRangePlayability.ser").toString());
	}

	// Do conversion
	private static void doConversion() {
		// PREFLOP_LIMP BET1
		rangeSBOpen.updateRangeData();
		rangeSBLimp.combineRangeWithThisRange(rangeSBOpen);
		rangeSBLimp.updateRangeData();
		// PREFLOP_OPEN BET2
		rangeSBBet3.updateRangeData();
		rangeSBCall.combineRangeWithThisRange(rangeSBBet3);
		rangeSBCall.updateRangeData();
		// PREFLOP_BET3
		rangeSBBet4.updateRangeData();
		rangeSBBet3Call.combineRangeWithThisRange(rangeSBBet4);
		rangeSBBet3Call.updateRangeData();
		// PREFLOP_BET4
		rangeSBAllin.updateRangeData();
		rangeSBBet4Call.combineRangeWithThisRange(rangeSBAllin);
		rangeSBBet4Call.updateRangeData();
		// PREFLOP_ALLIN
		rangeSBAllinCall.updateRangeData();

		// PREFLOP_LIMP BET1
		rangeBBOpen.updateRangeData();
		rangeBBLimp.combineRangeWithThisRange(rangeBBOpen);
		rangeBBLimp.updateRangeData();
		// PREFLOP_OPEN BET2
		rangeBBBet3.updateRangeData();
		rangeBBCall.combineRangeWithThisRange(rangeBBBet3);
		rangeBBBet3Call.updateRangeData();
		// PREFLOP_BET3
		rangeBBBet4.updateRangeData();
		rangeBBBet3Call.combineRangeWithThisRange(rangeBBBet4);
		rangeBBBet3Call.updateRangeData();
		// PREFLOP_BET4
		rangeBBAllin.updateRangeData();
		rangeBBBet4Call.combineRangeWithThisRange(rangeBBAllin);
		rangeBBBet4Call.updateRangeData();
		// PREFLOP_ALLIN
		rangeBBAllinCall.updateRangeData();

		// PREFLOP_LIMP BET1
		rangeUTGOpen.updateRangeData();
		rangeUTGLimp.combineRangeWithThisRange(rangeUTGOpen);
		rangeUTGLimp.updateRangeData();
		// PREFLOP_OPEN BET2
		rangeUTGBet3.updateRangeData();
		rangeUTGCall.combineRangeWithThisRange(rangeUTGBet3);
		rangeUTGCall.updateRangeData();
		// PREFLOP_BET3
		rangeUTGBet4.updateRangeData();
		rangeUTGBet3Call.combineRangeWithThisRange(rangeUTGBet4);
		rangeUTGBet3Call.updateRangeData();
		// PREFLOP_BET4
		rangeUTGAllin.updateRangeData();
		rangeUTGBet4Call.combineRangeWithThisRange(rangeUTGAllin);
		rangeUTGBet4Call.updateRangeData();
		// PREFLOP_ALLIN
		rangeUTGAllinCall.updateRangeData();

		// PREFLOP_LIMP BET1
		rangeMPOpen.updateRangeData();
		rangeMPLimp.combineRangeWithThisRange(rangeMPOpen);
		rangeMPLimp.updateRangeData();
		// PREFLOP_OPEN BET2
		rangeMPBet3.updateRangeData();
		rangeMPCall.combineRangeWithThisRange(rangeMPBet3);
		rangeMPCall.updateRangeData();
		// PREFLOP_BET3
		rangeMPBet4.updateRangeData();
		rangeMPBet3Call.combineRangeWithThisRange(rangeMPBet4);
		rangeMPBet3Call.updateRangeData();
		// PREFLOP_BET4
		rangeMPAllin.updateRangeData();
		rangeMPBet4Call.combineRangeWithThisRange(rangeMPAllin);
		rangeMPBet4Call.updateRangeData();
		// PREFLOP_ALLIN
		rangeMPAllinCall.updateRangeData();

		// PREFLOP_LIMP BET1
		rangeCOOpen.updateRangeData();
		rangeCOLimp.combineRangeWithThisRange(rangeCOOpen);
		rangeCOLimp.updateRangeData();
		// PREFLOP_OPEN BET2
		rangeCOBet3.updateRangeData();
		rangeCOCall.combineRangeWithThisRange(rangeCOBet3);
		rangeCOCall.updateRangeData();
		// PREFLOP_BET3
		rangeCOBet4.updateRangeData();
		rangeCOBet3Call.combineRangeWithThisRange(rangeCOBet4);
		rangeCOBet3Call.updateRangeData();
		// PREFLOP_BET4
		rangeCOAllin.updateRangeData();
		rangeCOBet4Call.combineRangeWithThisRange(rangeCOAllin);
		rangeCOBet4Call.updateRangeData();
		// PREFLOP_ALLIN
		rangeCOAllinCall.updateRangeData();

		// PREFLOP_LIMP BET1
		rangeBUOpen.updateRangeData();
		rangeBULimp.combineRangeWithThisRange(rangeBUOpen);
		rangeBULimp.updateRangeData();
		// rangeBUOpen.show();
		// PREFLOP_OPEN BET2
		rangeBUBet3.updateRangeData();
		rangeBUCall.combineRangeWithThisRange(rangeBUBet3);
		rangeBUCall.updateRangeData();
		// PREFLOP_BET3
		rangeBUBet4.updateRangeData();
		rangeBUBet3Call.combineRangeWithThisRange(rangeBUBet4);
		rangeBUBet3Call.updateRangeData();
		// PREFLOP_BET4
		rangeBUAllin.updateRangeData();
		rangeBUBet4Call.combineRangeWithThisRange(rangeBUAllin);
		rangeBUBet4Call.updateRangeData();
		// PREFLOP_ALLIN
		rangeBUAllinCall.updateRangeData();
		copyRanges();
		writeToFile();
	}

	// Copy data from HandRange
	private static void copyRanges() {
		// BET1
		action.rangeArrayRaise[SB][0] = rangeSBOpen;
		action.rangeArrayCall[SB][0] = rangeSBLimp;
		// BET2
		action.rangeArrayRaise[SB][1] = rangeSBBet3;
		action.rangeArrayCall[SB][1] = rangeSBCall;
		// BET3
		action.rangeArrayRaise[SB][2] = rangeSBBet4;
		action.rangeArrayCall[SB][2] = rangeSBBet3Call;
		// BET4
		action.rangeArrayRaise[SB][3] = rangeSBAllin;
		action.rangeArrayCall[SB][3] = rangeSBBet4Call;
		// ALLIN
		action.rangeArrayRaise[SB][4] = rangeSBAllin;
		action.rangeArrayCall[SB][4] = rangeSBAllinCall;

		// BET1
		action.rangeArrayRaise[SB][0] = rangeSBOpen;
		action.rangeArrayCall[SB][0] = rangeSBLimp;
		// BET2
		action.rangeArrayRaise[SB][1] = rangeSBBet3;
		action.rangeArrayCall[SB][1] = rangeSBCall;
		// BET3
		action.rangeArrayRaise[SB][2] = rangeSBBet4;
		action.rangeArrayCall[SB][2] = rangeSBBet3Call;
		// BET4
		action.rangeArrayRaise[SB][3] = rangeSBAllin;
		action.rangeArrayCall[SB][3] = rangeSBBet4Call;
		// ALLIN
		action.rangeArrayRaise[SB][4] = rangeSBAllin;
		action.rangeArrayCall[SB][4] = rangeSBAllinCall;

		// BET1
		action.rangeArrayRaise[BB][0] = rangeBBOpen;
		action.rangeArrayCall[BB][0] = rangeBBLimp;
		// BET2
		action.rangeArrayRaise[BB][1] = rangeBBBet3;
		action.rangeArrayCall[BB][1] = rangeBBCall;
		// BET3
		action.rangeArrayRaise[BB][2] = rangeBBBet4;
		action.rangeArrayCall[BB][2] = rangeBBBet3Call;
		// BET4
		action.rangeArrayRaise[BB][3] = rangeBBAllin;
		action.rangeArrayCall[BB][3] = rangeBBBet4Call;
		// ALLIN
		action.rangeArrayRaise[BB][4] = rangeBBAllin;
		action.rangeArrayCall[BB][4] = rangeBBAllinCall;

		// BET1
		action.rangeArrayRaise[UTG][0] = rangeUTGOpen;
		action.rangeArrayCall[UTG][0] = rangeUTGLimp;
		// BET2
		action.rangeArrayRaise[UTG][1] = rangeUTGBet3;
		action.rangeArrayCall[UTG][1] = rangeUTGCall;
		// BET3
		action.rangeArrayRaise[UTG][2] = rangeUTGBet4;
		action.rangeArrayCall[UTG][2] = rangeUTGBet3Call;
		// BET4
		action.rangeArrayRaise[UTG][3] = rangeUTGAllin;
		action.rangeArrayCall[UTG][3] = rangeUTGBet4Call;
		// ALLIN
		action.rangeArrayRaise[UTG][4] = rangeUTGAllin;
		action.rangeArrayCall[UTG][4] = rangeUTGAllinCall;
		// BET1
		action.rangeArrayRaise[MP][0] = rangeMPOpen;
		action.rangeArrayCall[MP][0] = rangeMPLimp;
		// BET2
		action.rangeArrayRaise[MP][1] = rangeMPBet3;
		action.rangeArrayCall[MP][1] = rangeMPCall;
		// BET3
		action.rangeArrayRaise[MP][2] = rangeMPBet4;
		action.rangeArrayCall[MP][2] = rangeMPBet3Call;
		// BET4
		action.rangeArrayRaise[MP][3] = rangeMPAllin;
		action.rangeArrayCall[MP][3] = rangeMPBet4Call;
		// ALLIN
		action.rangeArrayRaise[MP][4] = rangeMPAllin;
		action.rangeArrayCall[MP][4] = rangeMPAllinCall;
		// BET1
		action.rangeArrayRaise[CO][0] = rangeCOOpen;
		action.rangeArrayCall[CO][0] = rangeCOLimp;
		// BET2
		action.rangeArrayRaise[CO][1] = rangeCOBet3;
		action.rangeArrayCall[CO][1] = rangeCOCall;
		// BET3
		action.rangeArrayRaise[CO][2] = rangeCOBet4;
		action.rangeArrayCall[CO][2] = rangeCOBet3Call;
		// BET4
		action.rangeArrayRaise[CO][3] = rangeCOAllin;
		action.rangeArrayCall[CO][3] = rangeCOBet4Call;
		// ALLIN
		action.rangeArrayRaise[CO][4] = rangeCOAllin;
		action.rangeArrayCall[CO][4] = rangeCOAllinCall;

		// BET1
		action.rangeArrayRaise[BU][0] = rangeBUOpen;
		action.rangeArrayCall[BU][0] = rangeBULimp;
		// BET2
		action.rangeArrayRaise[BU][1] = rangeBUBet3;
		action.rangeArrayCall[BU][1] = rangeBUCall;
		// BET3
		action.rangeArrayRaise[BU][2] = rangeBUBet4;
		action.rangeArrayCall[BU][2] = rangeBUBet3Call;
		// BET4
		action.rangeArrayRaise[BU][3] = rangeBUAllin;
		action.rangeArrayCall[BU][3] = rangeBUBet4Call;
		// ALLIN
		action.rangeArrayRaise[BU][4] = rangeBUAllin;
		action.rangeArrayCall[BU][4] = rangeBUAllinCall;

	}

	// Create HandRange ranges based on percentages for testing
	static void testRanges() {
		final double a = 1;
		final double b = 1.1;

		final double c = .7;
		final double d = .8;

		final double e = .3;
		final double f = .4;

		final double g = .2;
		final double h = .3;

		final double percentSB = 10;
		final double percentBB = 10;
		final double percentUTG = 8;
		final double percentMP = 12;
		final double percentCO = 20;
		final double percentBU = 35;

		final var rangeSB = new HandRange();
		final var rangeBB = new HandRange();
		final var rangeUTG = new HandRange();
		final var rangeMP = new HandRange();
		final var rangeCO = new HandRange();
		final var rangeBU = new HandRange();

		rangeSB.buildRangePercentage(percentSB * a);
		rangeBB.buildRangePercentage(percentBB * a);
		rangeUTG.buildRangePercentage(percentUTG * a);
		rangeMP.buildRangePercentage(percentMP * a);
		rangeCO.buildRangePercentage(percentCO * a);
		rangeBU.buildRangePercentage(percentBU * a);
		rangeSB.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\SB\\Open.ser").toString());
		rangeBB.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\BB\\Open.ser").toString());
		rangeUTG.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\UTG\\Open.ser").toString());
		rangeMP.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\MP\\Open.ser").toString());
		rangeCO.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Cutoff\\Open.ser").toString());
		rangeBU.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Button\\Open.ser").toString());

		rangeSB.buildRangePercentage(percentSB * b);
		rangeBB.buildRangePercentage(percentBB * b);
		rangeUTG.buildRangePercentage(percentUTG * b);
		rangeMP.buildRangePercentage(percentMP * b);
		rangeCO.buildRangePercentage(percentCO * b);
		rangeBU.buildRangePercentage(percentBU * b);
		rangeSB.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\SB\\Limp.ser").toString());
		rangeBB.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\BB\\Limp.ser").toString());
		rangeUTG.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\UTG\\Limp.ser").toString());
		rangeMP.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\MP\\Limp.ser").toString());
		rangeCO.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Cutoff\\Limp.ser").toString());
		rangeBU.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Button\\Limp.ser").toString());

		rangeSB.buildRangePercentage(percentSB * c);
		rangeBB.buildRangePercentage(percentBB * c);
		rangeUTG.buildRangePercentage(percentUTG * c);
		rangeMP.buildRangePercentage(percentMP * c);
		rangeCO.buildRangePercentage(percentCO * c);
		rangeBU.buildRangePercentage(percentBU * c);
		rangeSB.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\SB\\Bet3.ser").toString());
		rangeBB.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\BB\\Bet3.ser").toString());
		rangeUTG.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\UTG\\Bet3.ser").toString());
		rangeMP.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\MP\\Bet3.ser").toString());
		rangeCO.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Cutoff\\Bet3.ser").toString());
		rangeBU.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Button\\Bet3.ser").toString());

		rangeSB.buildRangePercentage(percentSB * d);
		rangeBB.buildRangePercentage(percentBB * d);
		rangeUTG.buildRangePercentage(percentUTG * d);
		rangeMP.buildRangePercentage(percentMP * d);
		rangeCO.buildRangePercentage(percentCO * d);
		rangeBU.buildRangePercentage(percentBU * d);
		rangeSB.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\SB\\Bet3Call.ser").toString());
		rangeBB.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\BB\\Bet3Call.ser").toString());
		rangeUTG.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\UTG\\Bet3Call.ser").toString());
		rangeMP.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\MP\\Bet3Call.ser").toString());
		rangeCO.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Cutoff\\Bet3Call.ser").toString());
		rangeBU.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Button\\Bet3Call.ser").toString());

		rangeSB.buildRangePercentage(percentSB * e);
		rangeBB.buildRangePercentage(percentBB * e);
		rangeUTG.buildRangePercentage(percentUTG * e);
		rangeMP.buildRangePercentage(percentMP * e);
		rangeCO.buildRangePercentage(percentCO * e);
		rangeBU.buildRangePercentage(percentBU * e);
		rangeSB.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\SB\\Bet4.ser").toString());
		rangeBB.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\BB\\Bet4.ser").toString());
		rangeUTG.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\UTG\\Bet4.ser").toString());
		rangeMP.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\MP\\Bet4.ser").toString());
		rangeCO.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Cutoff\\Bet4.ser").toString());
		rangeBU.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Button\\Bet4.ser").toString());

		rangeSB.buildRangePercentage(percentSB * f);
		rangeBB.buildRangePercentage(percentBB * f);
		rangeUTG.buildRangePercentage(percentUTG * f);
		rangeMP.buildRangePercentage(percentMP * f);
		rangeCO.buildRangePercentage(percentCO * f);
		rangeBU.buildRangePercentage(percentBU * f);
		rangeSB.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\SB\\Bet4Call.ser").toString());
		rangeBB.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\BB\\Bet4Call.ser").toString());
		rangeUTG.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\UTG\\Bet4Call.ser").toString());
		rangeMP.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\MP\\Bet4Call.ser").toString());
		rangeCO.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Cutoff\\Bet4Call.ser").toString());
		rangeBU.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Button\\Bet4Call.ser").toString());

		rangeSB.buildRangePercentage(percentSB * g);
		rangeBB.buildRangePercentage(percentBB * g);
		rangeUTG.buildRangePercentage(percentUTG * g);
		rangeMP.buildRangePercentage(percentMP * g);
		rangeCO.buildRangePercentage(percentCO * g);
		rangeBU.buildRangePercentage(percentBU * g);
		rangeSB.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\SB\\Allin.ser").toString());
		rangeBB.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\BB\\Allin.ser").toString());
		rangeUTG.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\UTG\\Allin.ser").toString());
		rangeMP.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\MP\\Allin.ser").toString());
		rangeCO.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Cutoff\\Allin.ser").toString());
		rangeBU.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Button\\Allin.ser").toString());

		rangeSB.buildRangePercentage(percentSB * h);
		rangeBB.buildRangePercentage(percentBB * h);
		rangeUTG.buildRangePercentage(percentUTG * h);
		rangeMP.buildRangePercentage(percentMP * h);
		rangeCO.buildRangePercentage(percentCO * h);
		rangeBU.buildRangePercentage(percentBU * h);
		rangeSB.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\SB\\AllinCall.ser").toString());
		rangeBB.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\BB\\AllinCall.ser").toString());
		rangeUTG.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\UTG\\AllinCall.ser").toString());
		rangeMP.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\MP\\AllinCall.ser").toString());
		rangeCO.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Cutoff\\AllinCall.ser").toString());
		rangeBU.writeRange(new StringBuilder().append("C:\\Hold'emDatabase\\").append(type)
				.append("\\Preflop\\Button\\AllinCall.ser").toString());

	}

}
