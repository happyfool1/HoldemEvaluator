package holdemevaluator;

public class DrawMadeWonPercentages implements Constants {

	/*-  ******************************************************************************
	* This Class will contains data for the results of hand evaluation.
	* See the Class AnalysisOfMadeAndDrawHands.
	* The made and draw arrays are in instances of that class for one player.
	* This Class has static data that is the sum of all players that have not folded.
	* If there was preflop simulation then players will have folded.
	* 
	* @author PEAK_
	****************************************************************************** */

	/*-**********************************************************************************************
	* This method will initialize all arrays to zero.
	* Done prior to each new game.
	*************************************************************************************************/
	static void initializeAllArrays() {
		if (EvalData.street == FLOP || EvalData.street == ALL_STREETS) {
			typeCountDrawFlop = 0;
			typeCountSimDrawFlop = 0;
			typeCountFreezeBoardDrawFlop = 0;
			typeCountFreezeSeat1DrawFlop = 0;
			typeCountFreezeSeat2DrawFlop = 0;
			typeCountMadeFlop = 0;
			typeCountSimMadeFlop = 0;
			typeCountFreezeBoardMadeFlop = 0;
			typeCountFreezeSeat1MadeFlop = 0;
			typeCountFreezeSeat2MadeFlop = 0;
			for (int i = 0; i < MADE_SIZE; i++) {
				typeArrayMadeFlop[i] = 0;
				typeArraySimMadeFlop[i] = 0;
				typeArrayFreezeBoardMadeFlop[i] = 0;
				typeArrayFreezeSeat1MadeFlop[i] = 0;
				typeArrayFreezeSeat2MadeFlop[i] = 0;
			}
			for (int i = 0; i < DRAW_SIZE; i++) {
				typeArrayDrawFlop[i] = 0;
				typeArraySimDrawFlop[i] = 0;
				typeArrayFreezeBoardDrawFlop[i] = 0;
				typeArrayFreezeSeat1DrawFlop[i] = 0;
				typeArrayFreezeSeat2DrawFlop[i] = 0;
			}
		}
		if (EvalData.street == TURN || EvalData.street == ALL_STREETS) {
			typeCountDrawTurn = 0;
			typeCountSimDrawTurn = 0;
			typeCountFreezeBoardDrawTurn = 0;
			typeCountFreezeSeat1DrawTurn = 0;
			typeCountFreezeSeat2DrawTurn = 0;
			typeCountMadeTurn = 0;
			typeCountSimMadeTurn = 0;
			typeCountFreezeBoardMadeTurn = 0;
			typeCountFreezeSeat1MadeTurn = 0;
			typeCountFreezeSeat2MadeTurn = 0;
			for (int i = 0; i < MADE_SIZE; i++) {
				typeArrayMadeTurn[i] = 0;
				typeArraySimMadeTurn[i] = 0;
				typeArrayFreezeBoardMadeTurn[i] = 0;
				typeArrayFreezeSeat1MadeTurn[i] = 0;
				typeArrayFreezeSeat2MadeTurn[i] = 0;
			}
			for (int i = 0; i < DRAW_SIZE; i++) {
				typeArrayDrawTurn[i] = 0;
				typeArraySimDrawTurn[i] = 0;
				typeArrayFreezeBoardDrawTurn[i] = 0;
				typeArrayFreezeSeat1DrawTurn[i] = 0;
				typeArrayFreezeSeat2DrawTurn[i] = 0;
			}
		}
		if (EvalData.street == RIVER || EvalData.street == ALL_STREETS) {
			typeCountMadeRiver = 0;
			typeCountSimMadeRiver = 0;
			typeCountFreezeBoardMadeRiver = 0;
			typeCountFreezeSeat1MadeRiver = 0;
			typeCountFreezeSeat2MadeRiver = 0;
			for (int i = 0; i < MADE_SIZE; i++) {
				typeArrayMadeRiver[i] = 0;
				typeArraySimMadeRiver[i] = 0;
				typeArrayFreezeBoardMadeRiver[i] = 0;
				typeArrayFreezeSeat1MadeRiver[i] = 0;
				typeArrayFreezeSeat2MadeRiver[i] = 0;
			}
		}
	}

	/*-**********************************************************************************************
	* This method will add Showdown data for all players that have not folded at on the River
	*************************************************************************************************/
	static void addValueArray(int seat) {
		for (int i = 0; i < PLAYERS; i++) {
			for (int j = 0; j < MADE_SIZE; j++) {
				showdownValue[j] += Showdown.showdownValue[i][j];
				showdownWon[j] += Showdown.showdownWon[i][j];
			}
			++countShowdownValue;
			++countShowdownWon;
		}
	}

	/*-**********************************************************************************************
	* These arrays are used for reporting on the results of this analysis.
	* The arrays are counts of how frequently a type of drawing hand or the type of
	* a made hand is found.
	* A reporting Class, ReportMadeDrawPercengtages 
	* There are two groups of arrays, one without preflop simulation and one with simulation.
	*************************************************************************************************/
	static int[] typeArrayMadeFlop = new int[MADE_SIZE];
	static int typeCountMadeFlop = 0;
	static int[] typeArrayMadeTurn = new int[MADE_SIZE];
	static int typeCountMadeTurn = 0;
	static int[] typeArrayMadeRiver = new int[MADE_SIZE];
	static int typeCountMadeRiver = 0;

	static int[] typeArrayDrawFlop = new int[DRAW_SIZE];
	static int typeCountDrawFlop = 0;
	static int[] typeArrayDrawTurn = new int[DRAW_SIZE];
	static int typeCountDrawTurn = 0;

	static int[] typeArraySimMadeFlop = new int[MADE_SIZE];
	static int typeCountSimMadeFlop = 0;
	static int[] typeArraySimMadeTurn = new int[MADE_SIZE];
	static int typeCountSimMadeTurn = 0;
	static int[] typeArraySimMadeRiver = new int[MADE_SIZE];
	static int typeCountSimMadeRiver = 0;

	static int[] typeArraySimDrawFlop = new int[DRAW_SIZE];
	static int typeCountSimDrawFlop = 0;
	static int[] typeArraySimDrawTurn = new int[DRAW_SIZE];
	static int typeCountSimDrawTurn = 0;

	static int[] typeArrayFreezeBoardMadeFlop = new int[MADE_SIZE];
	static int typeCountFreezeBoardMadeFlop = 0;
	static int[] typeArrayFreezeBoardMadeTurn = new int[MADE_SIZE];
	static int typeCountFreezeBoardMadeTurn = 0;
	static int[] typeArrayFreezeBoardMadeRiver = new int[MADE_SIZE];
	static int typeCountFreezeBoardMadeRiver = 0;

	static int[] typeArrayFreezeBoardDrawFlop = new int[DRAW_SIZE];
	static int typeCountFreezeBoardDrawFlop = 0;
	static int[] typeArrayFreezeBoardDrawTurn = new int[DRAW_SIZE];
	static int typeCountFreezeBoardDrawTurn = 0;

	static int[] typeArrayFreezeSeat1MadeFlop = new int[MADE_SIZE];
	static int typeCountFreezeSeat1MadeFlop = 0;
	static int[] typeArrayFreezeSeat1MadeTurn = new int[MADE_SIZE];
	static int typeCountFreezeSeat1MadeTurn = 0;
	static int[] typeArrayFreezeSeat1MadeRiver = new int[MADE_SIZE];
	static int typeCountFreezeSeat1MadeRiver = 0;

	static int[] typeArrayFreezeSeat1DrawFlop = new int[DRAW_SIZE];
	static int typeCountFreezeSeat1DrawFlop = 0;
	static int[] typeArrayFreezeSeat1DrawTurn = new int[DRAW_SIZE];
	static int typeCountFreezeSeat1DrawTurn = 0;

	static int[] typeArrayFreezeSeat2MadeFlop = new int[MADE_SIZE];
	static int typeCountFreezeSeat2MadeFlop = 0;
	static int[] typeArrayFreezeSeat2MadeTurn = new int[MADE_SIZE];
	static int typeCountFreezeSeat2MadeTurn = 0;
	static int[] typeArrayFreezeSeat2MadeRiver = new int[MADE_SIZE];
	static int typeCountFreezeSeat2MadeRiver = 0;

	static int[] typeArrayFreezeSeat2DrawFlop = new int[DRAW_SIZE];
	static int typeCountFreezeSeat2DrawFlop = 0;
	static int[] typeArrayFreezeSeat2DrawTurn = new int[DRAW_SIZE];
	static int typeCountFreezeSeat2DrawTurn = 0;

// Results at Showdown
	static int[] showdownValue = new int[MADE_SIZE];
	static int[] showdownWon = new int[MADE_SIZE];
	static int countShowdownValue = 0;
	static int countShowdownWon = 0;

	static int printCount = 0;

	/*-**********************************************************************************************
	* This method is only used for testing
	*************************************************************************************************/
	void showDrawTypeArray() {
		System.out.println("//\r\n****Draw Flop " + typeCountDrawFlop);
		for (int i = 0; i < DRAW_SIZE; ++i) {
			System.out.println("// " + DRAW_ST[i] + " "
					+ Format.formatPer(((double) typeArrayDrawFlop[i] / (double) typeCountDrawFlop) * 100));
		}
		System.out.println("//\r\n****Draw Turn " + typeCountDrawTurn);
		for (int i = 0; i < DRAW_SIZE; ++i) {
			System.out.println("// " + DRAW_ST[i] + " "
					+ Format.formatPer(((double) typeArrayDrawTurn[i] / (double) typeCountDrawTurn) * 100));
		}
	}

	/*-**********************************************************************************************
	* This method is only used for testing
	*************************************************************************************************/
	void showMadeTypeArray() {
		System.out.println("//\r\n****Made Flop " + typeCountMadeFlop);
		for (int i = 0; i < MADE_SIZE; ++i) {
			System.out.println("// " + MADE_ST[i] + " "
					+ Format.formatPer(((double) typeArrayMadeFlop[i] / (double) typeCountMadeFlop) * 100.));
		}

		System.out.println("//\r\n****Made Turn " + typeCountMadeTurn);
		for (int i = MADE_STRAIGHT; i <= MADE_FLUSH; ++i) {
			System.out.println("// " + MADE_ST[i] + " "
					+ Format.formatPer(((double) typeArrayMadeTurn[i] / (double) typeCountMadeTurn) * 100.));
		}
		System.out.println("//\r\n****Made PairedRiver " + typeCountMadeRiver);
		for (int i = 0; i < MADE_SIZE; ++i) {
			System.out.println("// " + MADE_ST[i] + " "
					+ Format.formatPer(((double) typeArrayMadeRiver[i] / (double) typeCountMadeRiver) * 100.));
		}

		double p = 0;
		p = ((typeArrayMadeFlop[MADE_PAIR_BELOW_BOARD_PAIR] + typeArrayMadeFlop[MADE_PAIR_ABOVE_BOARD_PAIR]
				+ typeArrayMadeFlop[MADE_WEAK_PAIR] + typeArrayMadeFlop[MADE_BOTTOM_PAIR]
				+ typeArrayMadeFlop[MADE_MIDDLE_PAIR] + typeArrayMadeFlop[MADE_TOP_PAIR]
				+ typeArrayMadeFlop[MADE_OVER_PAIR]) / (double) typeCountMadeFlop) * 100.;
		System.out.println("//\r\n****Made Flop Pairs " + Format.formatPer(p));

		p = ((typeArrayMadeFlop[MADE_BOTTOM_TWO_PAIR] + typeArrayMadeFlop[MADE_TOP_AND_BOTTOM_TWO_PAIR]
				+ typeArrayMadeFlop[MADE_TOP_TWO_PAIR]) / (double) typeCountMadeFlop) * 100.;
		System.out.println("//\r\n****Made Flop Two Pair " + Format.formatPer(p));

		p = ((typeArrayDrawFlop[DRAW_GUTSHOT] + typeArrayDrawFlop[DRAW_GUTSHOT_PAIR]
				+ typeArrayDrawFlop[DRAW_GUTSHOT_GUTSHOT] + typeArrayDrawFlop[DRAW_OESD_GUTSHOT]
				+ typeArrayDrawFlop[DRAW_FLUSH_GUTSHOT]) / (double) typeCountDrawFlop) * 100.;
		System.out.println("//\r\n****Draw Flop Gutshot " + Format.formatPer(p));

		p = ((typeArrayDrawFlop[DRAW_STRAIGHT] + typeArrayDrawFlop[DRAW_STRAIGHT_PAIR]
				+ typeArrayDrawFlop[DRAW_STRAIGHT_GUTSHOT] + typeArrayDrawFlop[DRAW_FLUSH_STRAIGHT])
				/ (double) typeCountDrawFlop) * 100.;
		System.out.println("//\r\n****Draw Flop Straight " + Format.formatPer(p));

		p = ((typeArrayDrawFlop[DRAW_OESD] + typeArrayDrawFlop[DRAW_OESD_PAIR] + typeArrayDrawFlop[DRAW_FLUSH_OESD])
				/ (double) typeCountDrawFlop) * 100.;
		System.out.println("//\r\n****Draw Flop OESD" + Format.formatPer(p));

		p = ((typeArrayDrawFlop[DRAW_FLUSH] + typeArrayDrawFlop[DRAW_FLUSH_PAIR]) / (double) typeCountDrawFlop) * 100.;
		System.out.println("//\r\n****Draw Flop Flush" + Format.formatPer(p));
	}

	/*-**********************************************************************************************
	* This method is only used for testing
	*************************************************************************************************/
	void showDrawSimTypeArray() {
		System.out.println("//\r\n****Draw Flop " + typeCountDrawFlop);
		for (int i = 0; i < DRAW_SIZE; ++i) {
			System.out.println("// " + DRAW_ST[i] + " "
					+ Format.formatPer(((double) typeArrayDrawFlop[i] / (double) typeCountDrawFlop) * 100));
		}
		System.out.println("//\r\n****Draw Turn " + typeCountDrawTurn);
		for (int i = 0; i < DRAW_SIZE; ++i) {
			System.out.println("// " + DRAW_ST[i] + " "
					+ Format.formatPer(((double) typeArrayDrawTurn[i] / (double) typeCountDrawTurn) * 100));
		}
	}

	/*-**********************************************************************************************
	* This method is only used for testing
	*************************************************************************************************/
	void showMadeSimTypeArray() {
		System.out.println("//\r\n****Made Flop Sim " + typeCountSimMadeFlop);
		for (int i = 0; i < MADE_SIZE; ++i) {
			System.out.println("// " + MADE_ST[i] + " "
					+ Format.formatPer(((double) typeArraySimMadeFlop[i] / (double) typeCountSimMadeFlop) * 100.));
		}

		System.out.println("//\r\n****Made Turn Sim " + typeCountSimMadeTurn);
		for (int i = 0; i < MADE_SIZE; ++i) {
			System.out.println("// " + MADE_ST[i] + " "
					+ Format.formatPer(((double) typeArraySimMadeTurn[i] / (double) typeCountSimMadeTurn) * 100.));
		}

		System.out.println("//\r\n****Made River Sim " + typeCountSimMadeRiver);
		for (int i = 0; i < MADE_SIZE; ++i) {
			System.out.println("// " + MADE_ST[i] + " "
					+ Format.formatPer(((double) typeArraySimMadeRiver[i] / (double) typeCountSimMadeRiver) * 100.));
		}

		double p = 0;
		p = ((typeArraySimMadeFlop[MADE_PAIR_BELOW_BOARD_PAIR] + typeArraySimMadeFlop[MADE_PAIR_ABOVE_BOARD_PAIR]
				+ typeArraySimMadeFlop[MADE_WEAK_PAIR] + typeArraySimMadeFlop[MADE_BOTTOM_PAIR]
				+ typeArraySimMadeFlop[MADE_MIDDLE_PAIR] + typeArraySimMadeFlop[MADE_TOP_PAIR]
				+ typeArraySimMadeFlop[MADE_OVER_PAIR]) / (double) typeCountSimMadeFlop) * 100.;
		System.out.println("//\r\n****Made Flop Pairs " + Format.formatPer(p));

		p = ((typeArraySimMadeFlop[MADE_BOTTOM_TWO_PAIR] + typeArraySimMadeFlop[MADE_TOP_AND_BOTTOM_TWO_PAIR]
				+ typeArraySimMadeFlop[MADE_TOP_TWO_PAIR]) / (double) typeCountSimMadeFlop) * 100.;
		System.out.println("//\r\n****Made Flop Two Pair " + Format.formatPer(p));

		p = ((typeArraySimDrawFlop[DRAW_GUTSHOT] + typeArraySimDrawFlop[DRAW_GUTSHOT_PAIR]
				+ typeArraySimDrawFlop[DRAW_GUTSHOT_GUTSHOT] + typeArraySimDrawFlop[DRAW_OESD_GUTSHOT]
				+ typeArraySimDrawFlop[DRAW_FLUSH_GUTSHOT]) / (double) typeCountSimDrawFlop) * 100.;
		System.out.println("//\r\n****Draw Flop Gutshot " + Format.formatPer(p));

		p = ((typeArraySimDrawFlop[DRAW_STRAIGHT] + typeArraySimDrawFlop[DRAW_STRAIGHT_PAIR]
				+ typeArraySimDrawFlop[DRAW_STRAIGHT_GUTSHOT] + typeArraySimDrawFlop[DRAW_FLUSH_STRAIGHT])
				/ (double) typeCountSimDrawFlop) * 100.;
		System.out.println("//\r\n****Draw Flop Straight " + Format.formatPer(p));

		p = ((typeArraySimDrawFlop[DRAW_OESD] + typeArraySimDrawFlop[DRAW_OESD_PAIR]
				+ typeArraySimDrawFlop[DRAW_FLUSH_OESD]) / (double) typeCountSimDrawFlop) * 100.;
		System.out.println("//\r\n****Draw Flop OESD" + Format.formatPer(p));

		p = ((typeArraySimDrawFlop[DRAW_FLUSH] + typeArraySimDrawFlop[DRAW_FLUSH_PAIR]) / (double) typeCountSimDrawFlop)
				* 100.;
		System.out.println("//\r\n****Draw Flop Flush" + Format.formatPer(p));
	}

}
