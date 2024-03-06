package holdemevaluator;

/*-  ******************************************************************************
* This is a one time only application used to move hand ranges from Hold'emDatabase
* to the directory for this project Hold'emEvaluator.
* 60 ranges from Hold'emDatabase for one player type are combined into one 
* class .
*  @author PEAK_ 
* ****************************************************************************** */

public class HandRangeConverterPlayability implements Constants {

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

	private static final HandRangePlayability action = new HandRangePlayability();
	private static String type = "?";
	private static int typeNum = -1;

	private static double percentLimpSB = 44.;
	private static double percentLimpBB = 44.;
	private static double percentLimpUTG = 24.;
	private static double percentLimpMP = 34.;
	private static double percentLimpCO = 44.;
	private static double percentLimpBU = 59.;

	private static double percentOpenSB = 10.;
	private static double percentOpenBB = 10.;
	private static double percentOpenUTG = 11.;
	private static double percentOpenMP = 20.;
	private static double percentOpenCO = 30.;
	private static double percentOpenBU = 40.;

	private static double percent3BetSB = 5.;
	private static double percent3BetBB = 5.;
	private static double percent3BetUTG = 6.;
	private static double percent3BetMP = 8.;
	private static double percent3BetCO = 10.;
	private static double percent3BetBU = 15.;

	private static double percentCallSB = 10.;
	private static double percentCallBB = 10.;
	private static double percentCallUTG = 10.;
	private static double percentCallMP = 20.;
	private static double percentCallCO = 30.;
	private static double percentCallBU = 40.;

	private static double percent4BetSB = 4.;
	private static double percent4BetBB = 4.;
	private static double percent4BetUTG = 6.;
	private static double percent4BetMP = 7.;
	private static double percent4BetCO = 8.;
	private static double percent4BetBU = 9.;

	private static double percent4BetCallSB = 5.;
	private static double percent4BetCallBB = 5.;
	private static double percent4BetCallUTG = 7.;
	private static double percent4BetCallMP = 8.;
	private static double percent4BetCallCO = 9.;
	private static double percent4BetCallBU = 12.;

	private static double percentAllinSB = 2.;
	private static double percentAllinBB = 2.;
	private static double percentAllinUTG = 2.;
	private static double percentAllinMP = 3.;
	private static double percentAllinCO = 3.;
	private static double percentAllinBU = 4.;

	private static double percentAllinCallSB = 3.;
	private static double percentAllinCallBB = 3.;
	private static double percentAllinCallUTG = 3.;
	private static double percentAllinCallMP = 4.;
	private static double percentAllinCallCO = 4.;
	private static double percentAllinCallBU = 5.;

	public static void main(String[] s0) {
		type = "Hero";
		createRanges();
		doConversion();
		type = "Average";
		createRanges();
		doConversion();
		type = "Fish";
		percentageFish();
		createRanges();
		doConversion();
		type = "Shark";
		percentageShark();
		createRanges();
		doConversion();
		type = "Nit";
		percentageNit();
		createRanges();
		doConversion();
		type = "Lag";
		percentageLag();
		createRanges();
		doConversion();
		type = "Tag";
		percentageTag();
		createRanges();
		doConversion();

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
	 * Write the completed class to a disk file
	 */
	static void writeToFile() {
		action.writeToFile(new StringBuilder().append(EvalData.applicationDirectory).append(type)
				.append("-HandRangePlayability.ser").toString());
	}

	// Do conversion
	private static void doConversion() {
		rangeSBOpen.buildRangePlayabilityCombos();
		rangeSBLimp.combineRangeWithThisRange(rangeSBOpen);
		rangeSBLimp.buildRangePlayabilityCombos();
		// PREFLOP_OPEN BET2
		rangeSBBet3.buildRangePlayabilityCombos();
		rangeSBCall.combineRangeWithThisRange(rangeSBBet3);
		rangeSBCall.buildRangePlayabilityCombos();
		// PREFLOP_BET3
		rangeSBBet4.buildRangePlayabilityCombos();
		rangeSBBet3Call.combineRangeWithThisRange(rangeSBBet4);
		rangeSBBet3Call.buildRangePlayabilityCombos();
		// PREFLOP_BET4
		rangeSBAllin.buildRangePlayabilityCombos();
		rangeSBBet4Call.combineRangeWithThisRange(rangeSBAllin);
		rangeSBBet4Call.buildRangePlayabilityCombos();
		// PREFLOP_ALLIN
		rangeSBAllinCall.buildRangePlayabilityCombos();

		// PREFLOP_LIMP BET1
		rangeBBOpen.buildRangePlayabilityCombos();
		rangeBBLimp.combineRangeWithThisRange(rangeBBOpen);
		rangeBBLimp.buildRangePlayabilityCombos();
		// PREFLOP_OPEN BET2
		rangeBBBet3.buildRangePlayabilityCombos();
		rangeBBCall.combineRangeWithThisRange(rangeBBBet3);
		rangeBBBet3Call.buildRangePlayabilityCombos();
		// PREFLOP_BET3
		rangeBBBet4.buildRangePlayabilityCombos();
		rangeBBBet3Call.combineRangeWithThisRange(rangeBBBet4);
		rangeBBBet3Call.buildRangePlayabilityCombos();
		// PREFLOP_BET4
		rangeBBAllin.buildRangePlayabilityCombos();
		rangeBBBet4Call.combineRangeWithThisRange(rangeBBAllin);
		rangeBBBet4Call.buildRangePlayabilityCombos();
		// PREFLOP_ALLIN
		rangeBBAllinCall.buildRangePlayabilityCombos();

		// PREFLOP_LIMP BET1
		rangeUTGOpen.buildRangePlayabilityCombos();
		rangeUTGLimp.combineRangeWithThisRange(rangeUTGOpen);
		rangeUTGLimp.buildRangePlayabilityCombos();
		// PREFLOP_OPEN BET2
		rangeUTGBet3.buildRangePlayabilityCombos();
		rangeUTGCall.combineRangeWithThisRange(rangeUTGBet3);
		rangeUTGCall.buildRangePlayabilityCombos();
		// PREFLOP_BET3
		rangeUTGBet4.buildRangePlayabilityCombos();
		rangeUTGBet3Call.combineRangeWithThisRange(rangeUTGBet4);
		rangeUTGBet3Call.buildRangePlayabilityCombos();
		// PREFLOP_BET4
		rangeUTGAllin.buildRangePlayabilityCombos();
		rangeUTGBet4Call.combineRangeWithThisRange(rangeUTGAllin);
		rangeUTGBet4Call.buildRangePlayabilityCombos();
		// PREFLOP_ALLIN
		rangeUTGAllinCall.buildRangePlayabilityCombos();

		// PREFLOP_LIMP BET1
		rangeMPOpen.buildRangePlayabilityCombos();
		rangeMPLimp.combineRangeWithThisRange(rangeMPOpen);
		rangeMPLimp.buildRangePlayabilityCombos();
		// PREFLOP_OPEN BET2
		rangeMPBet3.buildRangePlayabilityCombos();
		rangeMPCall.combineRangeWithThisRange(rangeMPBet3);
		rangeMPCall.buildRangePlayabilityCombos();
		// PREFLOP_BET3
		rangeMPBet4.buildRangePlayabilityCombos();
		rangeMPBet3Call.combineRangeWithThisRange(rangeMPBet4);
		rangeMPBet3Call.buildRangePlayabilityCombos();
		// PREFLOP_BET4
		rangeMPAllin.buildRangePlayabilityCombos();
		rangeMPBet4Call.combineRangeWithThisRange(rangeMPAllin);
		rangeMPBet4Call.buildRangePlayabilityCombos();
		// PREFLOP_ALLIN
		rangeMPAllinCall.buildRangePlayabilityCombos();

		// PREFLOP_LIMP BET1
		rangeCOOpen.buildRangePlayabilityCombos();
		rangeCOLimp.combineRangeWithThisRange(rangeCOOpen);
		rangeCOLimp.buildRangePlayabilityCombos();
		// PREFLOP_OPEN BET2
		rangeCOBet3.buildRangePlayabilityCombos();
		rangeCOCall.combineRangeWithThisRange(rangeCOBet3);
		rangeCOCall.buildRangePlayabilityCombos();
		// PREFLOP_BET3
		rangeCOBet4.buildRangePlayabilityCombos();
		rangeCOBet3Call.combineRangeWithThisRange(rangeCOBet4);
		rangeCOBet3Call.buildRangePlayabilityCombos();
		// PREFLOP_BET4
		rangeCOAllin.buildRangePlayabilityCombos();
		rangeCOBet4Call.combineRangeWithThisRange(rangeCOAllin);
		rangeCOBet4Call.buildRangePlayabilityCombos();
		// PREFLOP_ALLIN
		rangeCOAllinCall.buildRangePlayabilityCombos();

		// PREFLOP_LIMP BET1
		rangeBUOpen.buildRangePlayabilityCombos();
		rangeBULimp.combineRangeWithThisRange(rangeBUOpen);
		rangeBULimp.buildRangePlayabilityCombos();
		// PREFLOP_OPEN BET2
		rangeBUBet3.buildRangePlayabilityCombos();
		rangeBUCall.combineRangeWithThisRange(rangeBUBet3);
		rangeBUCall.buildRangePlayabilityCombos();
		// PREFLOP_BET3
		rangeBUBet4.buildRangePlayabilityCombos();
		rangeBUBet3Call.combineRangeWithThisRange(rangeBUBet4);
		rangeBUBet3Call.buildRangePlayabilityCombos();
		// PREFLOP_BET4
		rangeBUAllin.buildRangePlayabilityCombos();
		rangeBUBet4Call.combineRangeWithThisRange(rangeBUAllin);
		rangeBUBet4Call.buildRangePlayabilityCombos();
		// PREFLOP_ALLIN
		rangeBUAllinCall.buildRangePlayabilityCombos();

		copyRanges();
		writeToFile();
	}

	// Copy data from HandRange to HandRangePlayability
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

	/*- **************************************************************************** 
	 * Create HandRange ranges based on percentages for testing - FISH
	 * A HandRange is created in the old Hold'emDatabase directory.
	 * It replaces the old hand ranges, all of them, for the player type.
	 * The 6 percentage values persentXX are for preflop positions to open.
	 * The values deltaX are multipliers for the increasing bet sizes.
	 * For example:
	 * 		 percentBU is the Buttons percentage to open.
	 * 		 The limp percentage is percentBU * deltaLimp. So open is 55% to open , 
	 * 	     and limp is is 55 * 1.2 =  66 to limp.
	 *       deltaCall .7 so call bet2 is 55 * .7 = 38%
	 **************************************************************************** */
	static void percentageFish() {
		type = "Fish";
		typeNum = FISH;
		percentLimpSB = 44.;
		percentLimpBB = 44.;
		percentLimpUTG = 24.;
		percentLimpMP = 34.;
		percentLimpCO = 44.;
		percentLimpBU = 59.;

		percentOpenSB = 10.;
		percentOpenBB = 10.;
		percentOpenUTG = 11.;
		percentOpenMP = 20.;
		percentOpenCO = 30.;
		percentOpenBU = 40.;

		percent3BetSB = 5.;
		percent3BetBB = 5.;
		percent3BetUTG = 6.;
		percent3BetMP = 8.;
		percent3BetCO = 10.;
		percent3BetBU = 15.;

		percentCallSB = 10.;
		percentCallBB = 10.;
		percentCallUTG = 10.;
		percentCallMP = 20.;
		percentCallCO = 30.;
		percentCallBU = 40.;

		percent4BetSB = 4.;
		percent4BetBB = 4.;
		percent4BetUTG = 6.;
		percent4BetMP = 7.;
		percent4BetCO = 8.;
		percent4BetBU = 9.;

		percent4BetCallSB = 5.;
		percent4BetCallBB = 5.;
		percent4BetCallUTG = 7.;
		percent4BetCallMP = 8.;
		percent4BetCallCO = 9.;
		percent4BetCallBU = 12.;

		percentAllinSB = 2.;
		percentAllinBB = 2.;
		percentAllinUTG = 2.;
		percentAllinMP = 3.;
		percentAllinCO = 3.;
		percentAllinBU = 4.;

		percentAllinCallSB = 3.;
		percentAllinCallBB = 3.;
		percentAllinCallUTG = 3.;
		percentAllinCallMP = 4.;
		percentAllinCallCO = 4.;
		percentAllinCallBU = 5.;
		buildPercentageRanges();
	}

	/*- **************************************************************************** 
	 * Create HandRange ranges based on percentages for testing - FISH
	 * A HandRange is created in the old Hold'emDatabase directory.
	 * It replaces the old hand ranges, all of them, for the player type.
	 * The 6 percentage values persentXX are for preflop positions to open.
	 * The values deltaX are multipliers for the increasing bet sizes.
	 * For example:
	 * 		 percentBU is the Buttons percentage to open.
	 * 		 The limp percentage is percentBU * deltaLimp. So open is 55% to open , 
	 * 	     and limp is is 55 * 1.2 =  66 to limp.
	 *       deltaCall .7 so call bet2 is 55 * .7 = 38%
	 **************************************************************************** */
	static void percentageShark() {
		String type = "Shark";
		typeNum = SHARK;
		percentLimpSB = 20.;
		percentLimpBB = 0.;
		percentLimpUTG = 0.;
		percentLimpMP = 0.;
		percentLimpCO = 0.;
		percentLimpBU = 0.;

		percentOpenSB = 5.;
		percentOpenBB = 5.;
		percentOpenUTG = 11.;
		percentOpenMP = 20.;
		percentOpenCO = 30.;
		percentOpenBU = 50.;

		percent3BetSB = 5.;
		percent3BetBB = 5.;
		percent3BetUTG = 6.;
		percent3BetMP = 8.;
		percent3BetCO = 10.;
		percent3BetBU = 15.;

		percentCallSB = 8.;
		percentCallBB = 8.;
		percentCallUTG = 8.;
		percentCallMP = 15.;
		percentCallCO = 25.;
		percentCallBU = 40.;

		percent4BetSB = 4.;
		percent4BetBB = 4.;
		percent4BetUTG = 6.;
		percent4BetMP = 7.;
		percent4BetCO = 8.;
		percent4BetBU = 9.;

		percent4BetCallSB = 5.;
		percent4BetCallBB = 5.;
		percent4BetCallUTG = 7.;
		percent4BetCallMP = 8.;
		percent4BetCallCO = 9.;
		percent4BetCallBU = 12.;

		percentAllinSB = 2.;
		percentAllinBB = 2.;
		percentAllinUTG = 2.;
		percentAllinMP = 3.;
		percentAllinCO = 3.;
		percentAllinBU = 4.;

		percentAllinCallSB = 3.;
		percentAllinCallBB = 3.;
		percentAllinCallUTG = 3.;
		percentAllinCallMP = 4.;
		percentAllinCallCO = 4.;
		percentAllinCallBU = 5.;

		buildPercentageRanges();
	}

	/*- **************************************************************************** 
	 * Create HandRange ranges based on percentages for testing - FISH
	 * A HandRange is created in the old Hold'emDatabase directory.
	 * It replaces the old hand ranges, all of them, for the player type.
	 * The 6 percentage values persentXX are for preflop positions to open.
	 * The values deltaX are multipliers for the increasing bet sizes.
	 * For example:
	 * 		 percentBU is the Buttons percentage to open.
	 * 		 The limp percentage is percentBU * deltaLimp. So open is 55% to open , 
	 * 	     and limp is is 55 * 1.2 =  66 to limp.
	 *       deltaCall .7 so call bet2 is 55 * .7 = 38%
	 **************************************************************************** */
	static void percentageNit() {
		String type = "Nit";
		typeNum = NIT;
		percentLimpSB = 20.;
		percentLimpBB = 0.;
		percentLimpUTG = 0.;
		percentLimpMP = 0.;
		percentLimpCO = 0.;
		percentLimpBU = 0.;

		percentOpenSB = 10.;
		percentOpenBB = 10.;
		percentOpenUTG = 11.;
		percentOpenMP = 20.;
		percentOpenCO = 30.;
		percentOpenBU = 40.;

		percent3BetSB = 5.;
		percent3BetBB = 5.;
		percent3BetUTG = 6.;
		percent3BetMP = 8.;
		percent3BetCO = 10.;
		percent3BetBU = 15.;

		percentCallSB = 10.;
		percentCallBB = 10.;
		percentCallUTG = 10.;
		percentCallMP = 20.;
		percentCallCO = 30.;
		percentCallBU = 40.;

		percent4BetSB = 4.;
		percent4BetBB = 4.;
		percent4BetUTG = 6.;
		percent4BetMP = 7.;
		percent4BetCO = 8.;
		percent4BetBU = 9.;

		percent4BetCallSB = 5.;
		percent4BetCallBB = 5.;
		percent4BetCallUTG = 7.;
		percent4BetCallMP = 8.;
		percent4BetCallCO = 9.;
		percent4BetCallBU = 12.;

		percentAllinSB = 2.;
		percentAllinBB = 2.;
		percentAllinUTG = 2.;
		percentAllinMP = 3.;
		percentAllinCO = 3.;
		percentAllinBU = 4.;

		percentAllinCallSB = 3.;
		percentAllinCallBB = 3.;
		percentAllinCallUTG = 3.;
		percentAllinCallMP = 4.;
		percentAllinCallCO = 4.;
		percentAllinCallBU = 5.;

		buildPercentageRanges();
	}

	/*- **************************************************************************** 
	 * Create HandRange ranges based on percentages for testing - FISH
	 * A HandRange is created in the old Hold'emDatabase directory.
	 * It replaces the old hand ranges, all of them, for the player type.
	 * The 6 percentage values persentXX are for preflop positions to open.
	 * The values deltaX are multipliers for the increasing bet sizes.
	 * For example:
	 * 		 percentBU is the Buttons percentage to open.
	 * 		 The limp percentage is percentBU * deltaLimp. So open is 55% to open , 
	 * 	     and limp is is 55 * 1.2 =  66 to limp.
	 *       deltaCall .7 so call bet2 is 55 * .7 = 38%
	 **************************************************************************** */
	static void percentageLag() {
		String type = "Lag";
		typeNum = LAG;
		percentLimpSB = 20.;
		percentLimpBB = 0.;
		percentLimpUTG = 0.;
		percentLimpMP = 0.;
		percentLimpCO = 0.;
		percentLimpBU = 0.;

		percentOpenSB = 10.;
		percentOpenBB = 10.;
		percentOpenUTG = 11.;
		percentOpenMP = 20.;
		percentOpenCO = 30.;
		percentOpenBU = 40.;

		percent3BetSB = 5.;
		percent3BetBB = 5.;
		percent3BetUTG = 6.;
		percent3BetMP = 8.;
		percent3BetCO = 10.;
		percent3BetBU = 15.;

		percentCallSB = 10.;
		percentCallBB = 10.;
		percentCallUTG = 10.;
		percentCallMP = 20.;
		percentCallCO = 30.;
		percentCallBU = 40.;

		percent4BetSB = 4.;
		percent4BetBB = 4.;
		percent4BetUTG = 6.;
		percent4BetMP = 7.;
		percent4BetCO = 8.;
		percent4BetBU = 9.;

		percent4BetCallSB = 5.;
		percent4BetCallBB = 5.;
		percent4BetCallUTG = 7.;
		percent4BetCallMP = 8.;
		percent4BetCallCO = 9.;
		percent4BetCallBU = 12.;

		percentAllinSB = 2.;
		percentAllinBB = 2.;
		percentAllinUTG = 2.;
		percentAllinMP = 3.;
		percentAllinCO = 3.;
		percentAllinBU = 4.;

		percentAllinCallSB = 3.;
		percentAllinCallBB = 3.;
		percentAllinCallUTG = 3.;
		percentAllinCallMP = 4.;
		percentAllinCallCO = 4.;
		percentAllinCallBU = 5.;
		buildPercentageRanges();
	}

	/*- **************************************************************************** 
	 * Create HandRange ranges based on percentages for testing - FISH
	 * A HandRange is created in the old Hold'emDatabase directory.
	 * It replaces the old hand ranges, all of them, for the player type.
	 * The 6 percentage values persentXX are for preflop positions to open.
	 * The values deltaX are multipliers for the increasing bet sizes.
	 * For example:
	 * 		 percentBU is the Buttons percentage to open.
	 * 		 The limp percentage is percentBU * deltaLimp. So open is 55% to open , 
	 * 	     and limp is is 55 * 1.2 =  66 to limp.
	 *       deltaCall .7 so call bet2 is 55 * .7 = 38%
	 **************************************************************************** */
	static void percentageTag() {
		String type = "Tag";
		typeNum = TAG;
		percentLimpSB = 20.;
		percentLimpBB = 0.;
		percentLimpUTG = 0.;
		percentLimpMP = 0.;
		percentLimpCO = 0.;
		percentLimpBU = 0.;

		percentOpenSB = 5.;
		percentOpenBB = 5.;
		percentOpenUTG = 12.;
		percentOpenMP = 15.;
		percentOpenCO = 20.;
		percentOpenBU = 25.;

		percent3BetSB = 5.;
		percent3BetBB = 5.;
		percent3BetUTG = 9.;
		percent3BetMP = 12.;
		percent3BetCO = 20.;
		percent3BetBU = 30.;

		percentCallSB = 10.;
		percentCallBB = 10.;
		percentCallUTG = 10.;
		percentCallMP = 20.;
		percentCallCO = 30.;
		percentCallBU = 40.;

		percent4BetSB = 4.;
		percent4BetBB = 4.;
		percent4BetUTG = 6.;
		percent4BetMP = 10.;
		percent4BetCO = 11.;
		percent4BetBU = 12.;

		percent4BetCallSB = 5.;
		percent4BetCallBB = 5.;
		percent4BetCallUTG = 7.;
		percent4BetCallMP = 11.;
		percent4BetCallCO = 12.;
		percent4BetCallBU = 14.;

		percentAllinSB = 2.;
		percentAllinBB = 2.;
		percentAllinUTG = 2.;
		percentAllinMP = 3.;
		percentAllinCO = 3.;
		percentAllinBU = 4.;

		percentAllinCallSB = 3.;
		percentAllinCallBB = 3.;
		percentAllinCallUTG = 3.;
		percentAllinCallMP = 4.;
		percentAllinCallCO = 4.;
		percentAllinCallBU = 5.;

		buildPercentageRanges();
	}

	/*- **************************************************************************** 
	 * Build ranges for type and percentages
	 **************************************************************************** */
	static void buildPercentageRanges() {
		final var rangeSB = new HandRange();
		final var rangeBB = new HandRange();
		final var rangeUTG = new HandRange();
		final var rangeMP = new HandRange();
		final var rangeCO = new HandRange();
		final var rangeBU = new HandRange();

		rangeSB.buildRangePercentage(percentOpenSB);
		rangeBB.buildRangePercentage(percentOpenBB);
		rangeUTG.buildRangePercentage(percentOpenUTG);
		rangeMP.buildRangePercentage(percentOpenMP);
		rangeCO.buildRangePercentage(percentOpenCO);
		rangeBU.buildRangePercentage(percentOpenBU);
		rangeSB.initialize(typeNum, SB, RANGE_OPEN);
		rangeBB.initialize(typeNum, BB, RANGE_OPEN);
		rangeUTG.initialize(typeNum, UTG, RANGE_OPEN);
		rangeMP.initialize(typeNum, MP, RANGE_OPEN);
		rangeCO.initialize(typeNum, CO, RANGE_OPEN);
		rangeBU.initialize(typeNum, BU, RANGE_OPEN);

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

		rangeSB.buildRangePercentage(percentLimpSB);
		rangeBB.buildRangePercentage(percentLimpBB);
		rangeUTG.buildRangePercentage(percentLimpUTG);
		rangeMP.buildRangePercentage(percentLimpMP);
		rangeCO.buildRangePercentage(percentLimpCO);
		rangeBU.buildRangePercentage(percentLimpBU);
		rangeSB.initialize(typeNum, SB, RANGE_LIMP);
		rangeBB.initialize(typeNum, BB, RANGE_LIMP);
		rangeUTG.initialize(typeNum, UTG, RANGE_LIMP);
		rangeMP.initialize(typeNum, MP, RANGE_LIMP);
		rangeCO.initialize(typeNum, CO, RANGE_LIMP);
		rangeBU.initialize(typeNum, BU, RANGE_LIMP);

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

		rangeSB.buildRangePercentage(percent3BetSB);
		rangeBB.buildRangePercentage(percent3BetBB);
		rangeUTG.buildRangePercentage(percent3BetUTG);
		rangeMP.buildRangePercentage(percent3BetMP);
		rangeCO.buildRangePercentage(percent3BetCO);
		rangeBU.buildRangePercentage(percent3BetBU);
		rangeSB.initialize(typeNum, SB, RANGE_BET3);
		rangeBB.initialize(typeNum, BB, RANGE_BET3);
		rangeUTG.initialize(typeNum, UTG, RANGE_BET3);
		rangeMP.initialize(typeNum, MP, RANGE_BET3);
		rangeCO.initialize(typeNum, CO, RANGE_BET3);
		rangeBU.initialize(typeNum, BU, RANGE_BET3);

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

		rangeSB.buildRangePercentage(percentCallSB);
		rangeBB.buildRangePercentage(percentCallBB);
		rangeUTG.buildRangePercentage(percentCallUTG);
		rangeMP.buildRangePercentage(percentCallMP);
		rangeCO.buildRangePercentage(percentCallCO);
		rangeBU.buildRangePercentage(percentCallBU);
		rangeSB.initialize(typeNum, SB, RANGE_CALL);
		rangeBB.initialize(typeNum, BB, RANGE_CALL);
		rangeUTG.initialize(typeNum, UTG, RANGE_CALL);
		rangeMP.initialize(typeNum, MP, RANGE_CALL);
		rangeCO.initialize(typeNum, CO, RANGE_CALL);
		rangeBU.initialize(typeNum, BU, RANGE_CALL);

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

		rangeSB.buildRangePercentage(percent4BetSB);
		rangeBB.buildRangePercentage(percent4BetBB);
		rangeUTG.buildRangePercentage(percent4BetUTG);
		rangeMP.buildRangePercentage(percent4BetMP);
		rangeCO.buildRangePercentage(percent4BetCO);
		rangeBU.buildRangePercentage(percent4BetBU);
		rangeSB.initialize(typeNum, SB, RANGE_BET4);
		rangeBB.initialize(typeNum, BB, RANGE_BET4);
		rangeUTG.initialize(typeNum, UTG, RANGE_BET4);
		rangeMP.initialize(typeNum, MP, RANGE_BET4);
		rangeCO.initialize(typeNum, CO, RANGE_BET4);
		rangeBU.initialize(typeNum, BU, RANGE_BET4);

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

		rangeSB.buildRangePercentage(percent4BetCallSB);
		rangeBB.buildRangePercentage(percent4BetCallBB);
		rangeUTG.buildRangePercentage(percent4BetCallUTG);
		rangeMP.buildRangePercentage(percent4BetCallMP);
		rangeCO.buildRangePercentage(percent4BetCallCO);
		rangeBU.buildRangePercentage(percent4BetCallBU);
		rangeSB.initialize(typeNum, SB, RANGE_CALL_BET4);
		rangeBB.initialize(typeNum, BB, RANGE_CALL_BET4);
		rangeUTG.initialize(typeNum, UTG, RANGE_CALL_BET4);
		rangeMP.initialize(typeNum, MP, RANGE_CALL_BET4);
		rangeCO.initialize(typeNum, CO, RANGE_CALL_BET4);
		rangeBU.initialize(typeNum, BU, RANGE_CALL_BET4);

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

		rangeSB.buildRangePercentage(percentAllinSB);
		rangeBB.buildRangePercentage(percentAllinBB);
		rangeUTG.buildRangePercentage(percentAllinUTG);
		rangeMP.buildRangePercentage(percentAllinMP);
		rangeCO.buildRangePercentage(percentAllinCO);
		rangeBU.buildRangePercentage(percentAllinBU);
		rangeSB.initialize(typeNum, SB, RANGE_ALLIN);
		rangeBB.initialize(typeNum, BB, RANGE_ALLIN);
		rangeUTG.initialize(typeNum, UTG, RANGE_ALLIN);
		rangeMP.initialize(typeNum, MP, RANGE_ALLIN);
		rangeCO.initialize(typeNum, CO, RANGE_ALLIN);
		rangeBU.initialize(typeNum, BU, RANGE_ALLIN);

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

		rangeSB.buildRangePercentage(percentAllinCallSB);
		rangeBB.buildRangePercentage(percentAllinCallBB);
		rangeUTG.buildRangePercentage(percentAllinCallUTG);
		rangeMP.buildRangePercentage(percentAllinCallMP);
		rangeCO.buildRangePercentage(percentAllinCallCO);
		rangeBU.buildRangePercentage(percentAllinCallBU);
		rangeSB.initialize(typeNum, SB, RANGE_CALL_ALLIN);
		rangeBB.initialize(typeNum, BB, RANGE_CALL_ALLIN);
		rangeUTG.initialize(typeNum, UTG, RANGE_CALL_ALLIN);
		rangeMP.initialize(typeNum, MP, RANGE_CALL_ALLIN);
		rangeCO.initialize(typeNum, CO, RANGE_CALL_ALLIN);
		rangeBU.initialize(typeNum, BU, RANGE_CALL_ALLIN);

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
