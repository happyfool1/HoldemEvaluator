package holdemevaluator;

/* - This class creates hand histories */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*- Creates Hand Histories - in Poker Stars format. */
class HandHistory implements Constants {
	/*-  ******************************************************************************
	 * This class creates Hand History files in the same format as PokerStars with 
	 * minor modifications to make it not an exact copy.
	 * Copied from the game project and edited for this application but
	 * with lots of modifications.
	 * 
	 * Public methods:
	 * 		Initialize()
	 * 		createHH()
	 * 		closeHH()
	 * 
	 * All pf the data used to create aHand History file is in EvalData.
	 * The data shown below is collected specifically and only for Hand History.
	 * There is minimal impact on performance because it is only collected if we 
	 * are doing a full simulation.
	 * Data collecteed in normal operation:
	 * 
	 *  @author PEAK_
	 * ****************************************************************************** */

	/*- Complete hand history ready to write - each element is a line. */
	private static final String[] reportSt = new String[150];
	private static int lines;
	private static String hhDate;
	// Player names and positions - Index is seat number
	private static final String[] nameStringArray = { "", "", "", "", "", "" };
	private static String dateSt = "";
	private static PrintWriter pwFile1;
	/*- The HH to generate. */
	private static int files;
	static boolean writeEnabled = true; // Used to selectively write specific hand histories

	private static int street = 0;
	private static int seat = 0;
	private static int orbit = 0;
	private static boolean testAndDebug = true; // false to produce HH without additional data used for test and debug

	// Constructor
	HandHistory() {
	}

	/*-  *********************************************************************************** 
	 * For simulation modes
	 * Used by HandHistory
	  ************************************************************************************** */
	static int[][] foldedPreflop = new int[4][PLAYERS];
	static int[] lastOrbit = new int[4]; // Indexed by street
	static String[] cards = { "", "", "", "", "", "" }; // Hole cards
	static boolean[][][] isFold = new boolean[4][PLAYERS][4]; // Indexed by street,seat,orbit
	static boolean[][][] isLimp = new boolean[4][PLAYERS][4]; // Indexed by street,seat,orbit
	static boolean[][][] isCheck = new boolean[4][PLAYERS][4]; // Indexed by street,seat,orbit
	static boolean[][][] isCall = new boolean[4][PLAYERS][4]; // Indexed by street,seat,orbit
	static boolean[][][] isBet = new boolean[4][PLAYERS][4]; // Indexed by street,seat,orbit
	static boolean[][][] isRaise = new boolean[4][PLAYERS][4]; // Indexed by street,seat,orbit
	static boolean[][][] isAllin = new boolean[4][PLAYERS][4]; // Indexed by street,seat,orbit

	static BigDecimal[][][] raisedToBD = new BigDecimal[4][PLAYERS][4]; // Indexed by street,seat,orbit
	static BigDecimal[][][] returnedToBD = new BigDecimal[4][PLAYERS][4]; // Indexed by street,seat,orbit
	static BigDecimal[][][] betBD = new BigDecimal[4][PLAYERS][4]; // Indexed by street,seat,orbit
	static BigDecimal[][][] callBD = new BigDecimal[4][PLAYERS][4]; // Indexed by street,seat,orbit
	static BigDecimal[][][] winBD = new BigDecimal[4][PLAYERS][4]; // Indexed by street,seat,orbit
	static BigDecimal[][][] potThenBD = new BigDecimal[4][PLAYERS][4]; // Indexed by street,seat,orbit
	static BigDecimal[][][] moneyInThenBD = new BigDecimal[4][PLAYERS][4]; // Indexed by street,seat,orbit
	static BigDecimal[][][] stackThenBD = new BigDecimal[4][PLAYERS][4]; // Indexed by street,seat,orbit
	static BigDecimal[] winnerCollectedBD = new BigDecimal[PLAYERS]; // Indexed by street,seat,orbit
	static BigDecimal[] returnedToShowdownBD = new BigDecimal[PLAYERS]; // Indexed by street,seat,orbit
	static BigDecimal[] playerSidePotSplitTotalBD = new BigDecimal[PLAYERS]; // Indexed by street,seat,orbit
	static BigDecimal[] playerSidePotSplitBD = new BigDecimal[PLAYERS]; // Indexed by street,seat,orbit

	/*- ****************************************************************************
	* Initialize Hand History data for a new game
	**************************************************************************** */
	static void hhData() {
		for (int i = 0; i < 4; i++) {
			lastOrbit[i] = 0;
			for (int j = 0; j < PLAYERS; j++) {
				foldedPreflop[i][j] = 0;
				for (int k = 0; k < 4; k++) {
					isFold[i][j][k] = false;
					isLimp[i][j][k] = false;
					isCheck[i][j][k] = false;
					isCall[i][j][k] = false;
					isBet[i][j][k] = false;
					isRaise[i][j][k] = false;
					isAllin[i][j][k] = false;
					raisedToBD[i][j][k] = zeroBD;
					returnedToBD[i][j][k] = zeroBD;
					betBD[i][j][k] = zeroBD;
					callBD[i][j][k] = zeroBD;
					winBD[i][j][k] = zeroBD;
					potThenBD[i][j][k] = zeroBD;
					stackThenBD[i][j][k] = zeroBD;
					moneyInThenBD[i][j][k] = zeroBD;
				}
			}
		}
	}

	/*-  ******************************************************************************
	 *  Initialize. 
	 *  Called one time to create a new Hand History file.
	 *   ****************************************************************************** */
	static void initialize() {
		final var file = new File(EvalData.applicationDirectory + "\\HandHistory");
		if (!file.exists()) {
			file.mkdir();
		}
		eraseHHFiles();
		newHHFile();
		for (int i = 0; i < PLAYERS; ++i) {
			nameStringArray[i] = EvalData.playerNames[i];
		}
	}

	/*-  ******************************************************************************
	 *  Erase HH files
	 *   ****************************************************************************** */
	static void eraseHHFiles() {
		final var directoryPath = EvalData.applicationDirectory + "\\HandHistory";
		final var files = FileUtils.listFiles(directoryPath);

		if (files != null) {
			files.forEach(file -> {
				try {
					Files.delete(file);
				} catch (IOException e) {
					// System.err.println(new StringBuilder().append("Error while deleting file:
					// ").append(file)
					// .append(" - ").append(e.getMessage()).toString());
				}
			});
		} else {
			System.err.println("Error occurred while listing files in the directory.");
		}

	}

	/*-  ******************************************************************************
	 * Create a Hand History and add to file 
	 *   ****************************************************************************** */
	static void createHH() {
		for (int i = 0; i < reportSt.length; ++i) {
			reportSt[i] = "";
		}
		lines = 0;
		addHeader();
		street = PREFLOP;
		doStreetHH();
		if (EvalData.lastStreet >= FLOP) {
			flopHH();
			street = FLOP;
			doStreetHH();
		}
		for (int i = 0; i < PLAYERS; ++i) {
			// Simulation so non standard Hand History
			if (!GameControl.playerFolded[i] && EvalData.simulation && GameControl.foldCount < MAXFOLDED) {
				reportSt[lines] = new StringBuilder().append(nameStringArray[i]).append(" completed preflop ")
						.toString();
				reportSt[lines++] += "\r\n";
			}
		}
		reportSt[lines++] += "foldCount " + Integer.valueOf(GameControl.foldCount) + " limpCount "
				+ Integer.valueOf(GameControl.limpCount);
		reportSt[lines++] += "\r\n";

		if (EvalData.lastStreet >= TURN) {
			turnHH();
			street = TURN;
			doStreetHH();
		}
		if (EvalData.lastStreet >= RIVER) {
			riverHH();
			street = PREFLOP;
			doStreetHH();
		}
		if (GameControl.showdown) {
			showdownHH();
		}
		summaryHH();
		writeHH();
	}

	/*-  ******************************************************************************
	 * Close hand history file.
	 *   ****************************************************************************** */
	static void closeHH() {
		if (pwFile1 == null) {
			return;
		}
		pwFile1.flush();
		pwFile1.close();
	}

	/*-  ******************************************************************************
	 * New hand history file.
	 * ****************************************************************************** */
	private static void newHHFile() {
		var fileN = "";
		var num = "";
		if (EvalData.simulation) {
			fileN = new StringBuilder().append(EvalData.applicationDirectory).append("\\HandHistory\\HHSimulation")
					.append(num).append(".txt").toString();
		} else {
			final var dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			hhDate = dateFormat.format(new Date());
			dateSt = new StringBuilder().append(hhDate.substring(11, 13)).append(hhDate.substring(14, 16))
					.append(hhDate.substring(17, 19)).toString();

			num = files > 9 ? Integer.toString(files) : "0" + Integer.toString(files);

			fileN = new StringBuilder().append(EvalData.applicationDirectory).append("\\HandHistory\\HH")
					.append(hhDate.substring(5, 7)).append(hhDate.substring(8, 10)).append(hhDate.substring(11, 13))
					.append(hhDate.substring(14, 16)).append(num).append(".txt").toString();
			++files;
		}

		try {
			pwFile1 = new PrintWriter(new BufferedWriter(new FileWriter(fileN, true)));
		} catch (SecurityException | IOException i) {
			i.printStackTrace();
		}
	}

	/*- ******************************************************************************** 
	* Add data for testing
	************************************************************************************/
	private static void testAndDebug() {
		if (!testAndDebug)
			return;
		while (reportSt[lines].length() < 30) {
			reportSt[lines] += " ";
		}
		reportSt[lines] += " \tseat " + Integer.valueOf(seat + 1);
		reportSt[lines] += " \t" + POSITION_ST2[GameControl.seatPos[seat]];
		reportSt[lines] += " \torbit " + Integer.valueOf(orbit);
		if (street == SHOWDOWN && orbit < 4) {
			return;
		}
		reportSt[lines] += "  \t Pot " + Format.format(potThenBD[street][seat][orbit]);
		reportSt[lines] += " \tmoneyIn " + Format.format(moneyInThenBD[street][seat][orbit]);
		reportSt[lines] += " \tStack " + Format.format(stackThenBD[street][seat][orbit]);
		reportSt[lines] += " \t " + cards[seat];
		reportSt[lines] += " \t " + PLAYER_TYPE_ST[EvalData.playerType[seat]];
	}

	/*- ******************************************************************************** 
	* Add data for testing and debug
	************************************************************************************/
	private static void testSummary() {
		if (!testAndDebug)
			return;
		while (reportSt[lines].length() < 30) {
			reportSt[lines] += " ";
		}
		reportSt[lines] += " \tseat " + Integer.valueOf(seat + 1);
		reportSt[lines] += " \t" + POSITION_ST2[GameControl.seatPos[seat]];
		if (!(street != SHOWDOWN && orbit < 4)) {
			return;
		}
	}

	/*-  ******************************************************************************
	  * Add header information
	 * 		PokerStars
	 * 		Players
	 * 		Blinds
	 * 		Hole Cards
	 * - PokerStars Hand #178803089146: Hold'em No Limit ($1/$2 USD) - 2017/11/27
	 * 16:31:46 ET
	 *   ****************************************************************************** */
	private static void addHeader() {
		final var pattern = "########";
		var num = new DecimalFormat(pattern).format(GameControl.gamesPlayed);
		final var dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		hhDate = dateFormat.format(new Date());
		final int n = num.length();
		for (int i = n; i < 6; ++i) {
			num = "0" + num;
		}
		num = new StringBuilder().append("#").append(dateSt).append(num).toString();

		reportSt[lines++] = new StringBuilder().append("PokerStars Hand ").append(num)
				.append(":  Hold'em No Limit ($1/$2 USD) - ").append(hhDate)
				.append(" ET  - Generated by Hold'emEvaluation NOT by PokerStars\r\n").toString();

		reportSt[lines++] = new StringBuilder().append("Table 'Hold'em' 6-max Seat #")
				.append(Integer.toString(GameControl.seatBU + 1)).append(" is the button\r\n").toString();

		for (int i = 0; i < PLAYERS; ++i) {
			reportSt[lines++] = new StringBuilder().append("Seat ").append(i + 1).append(": ")
					.append(nameStringArray[i]).append(" ($").append(Format.format(GameControl.stackPreflopBD[i]))
					.append(" in chips)\r\n").toString();
		}

		reportSt[lines++] = new StringBuilder().append(nameStringArray[GameControl.seatSB])
				.append(": posts small blind $").append(Format.format(GameControl.SBBetBD)).append("\r\n").toString();
		reportSt[lines++] = new StringBuilder().append(nameStringArray[GameControl.seatBB])
				.append(": posts big blind $").append(Format.format(GameControl.BBBetBD)).append("\r\n").toString();

		reportSt[lines++] = "*** HOLE CARDS ***\r\n";
	}

	/*-  ******************************************************************************
	 * Flop.
	 *   ****************************************************************************** */
	static void flopHH() {
		if (EvalData.lastStreet < 1) {
			return;
		}
		reportSt[lines] = new StringBuilder().append("*** FLOP *** [").append(Board.board[0].toString()).append(" ")
				.append(Board.board[1].toString()).append(" ").append(Board.board[2].toString()).append("]").toString();
		reportSt[lines++] += "\r\n";
	}

	/*-  ******************************************************************************
	 * - Add turn to Hand History
	 *   ****************************************************************************** */
	static void turnHH() {
		if (EvalData.lastStreet < 2) {
			return;
		}
		reportSt[lines] = new StringBuilder().append("*** TURN *** [").append(Board.board[0].toString()).append(" ")
				.append(Board.board[1].toString()).append(" ").append(Board.board[2].toString()).append("] [")
				.append(Board.board[3]).append("]").toString();

		reportSt[lines++] += "\r\n";
	}

	/*-  ******************************************************************************
	 * Add River to HH
	 * ****************************************************************************** */
	static void riverHH() {
		if (EvalData.lastStreet != 3) {
			return;
		}
		reportSt[lines] = new StringBuilder().append("*** RIVER *** [").append(Board.board[0].toString())
				.append(Board.board[1].toString()).append(" ").append(Board.board[2].toString()).append(" ")
				.append(Board.board[3].toString()).append("] [").append(Board.board[4].toString()).append("]")
				.toString();
		reportSt[lines++] += "\r\n";
	}

	/*-  ******************************************************************************
	 * Preflop Hand History Format
	 * Fills in player actions
	 * If the call was from Logger then we add a few things not in normal Hand History
	  *   ****************************************************************************** */
	private static void doStreetHH() {
		seat = 0;
		int seatCount = PLAYERS;
		var st = "";

		for (orbit = 0; orbit <= lastOrbit[street]; ++orbit) {
			if (street == PREFLOP && orbit == 0) {
				seat = GameControl.seatUTG;
				seatCount = PLAYERS - 2;
			} else {
				seat = GameControl.seatSB;
				seatCount = PLAYERS;
			}

			for (int j = 0; j < seatCount; ++j) {
				if (seat >= PLAYERS) {
					seat = 0;
				}
				if (isFold[street][seat][orbit]) {
					reportSt[lines] = nameStringArray[seat] + ": folds";
					testAndDebug();
					reportSt[lines++] += "\r\n";
					++seat;
					continue;
				}

				if (isRaise[street][seat][orbit] || isBet[street][seat][orbit]) {
					st = "";
					if (isAllin[street][seat][orbit]) {
						st = " and is all-in";
					}
					final var bettBD = betBD[street][seat][orbit];
					if (isBet[street][seat][orbit]) {
						reportSt[lines] = new StringBuilder().append(nameStringArray[seat]).append(": bets $")
								.append(Format.format(bettBD)).append(st).toString();
					} else {
						reportSt[lines] = new StringBuilder().append(nameStringArray[seat]).append(": raises $")
								.append(Format.format(bettBD)).append(" to $")
								.append(Format.format(raisedToBD[street][seat][orbit])).append(st).toString();
					}
					testAndDebug();
					reportSt[lines++] += "\r\n";
					++seat;
					continue;
				}

				if (isCall[street][seat][orbit]) {
					st = "";
					if (isAllin[street][seat][orbit]) {
						st = " and is all-in";
					}
					reportSt[lines] = new StringBuilder().append(nameStringArray[seat]).append(": calls $")
							.append(Format.format(callBD[street][seat][orbit])).append(st).toString();
					testAndDebug();
					reportSt[lines++] += "\r\n";
					++seat;
					continue;
				}

				if (isCheck[street][seat][orbit]) {
					reportSt[lines] = nameStringArray[seat] + ": checks ";
					testAndDebug();
					reportSt[lines++] += "\r\n";
					++seat;
					continue;
				}
				++seat;
			}
		}

		// Check for bet returned or money collected
		for (orbit = 0; orbit <= lastOrbit[street]; ++orbit) {
			seat = GameControl.seatSB;
			seatCount = PLAYERS;
			if (street == PREFLOP && orbit == 0) {
				seat = GameControl.seatUTG;
				seatCount = PLAYERS - 2;
			}

			for (int j = 0; j < seatCount; ++j) {
				if (seat >= PLAYERS) {
					seat = 0;
				}

				if (returnedToBD[street][seat][orbit].compareTo(zeroBD) != 0) {
					reportSt[lines] = new StringBuilder().append("Uncalled bet ($")
							.append(Format.format(returnedToBD[street][seat][orbit])).append(") returned to ")
							.append(nameStringArray[seat]).toString();
					testAndDebug();
					reportSt[lines++] += "\r\n";
				}

				if (winBD[street][seat][orbit].compareTo(zeroBD) != 0) {
					reportSt[lines] = new StringBuilder().append(nameStringArray[seat]).append(" collected $")
							.append(Format.format(winnerCollectedBD[seat])).append(" from pot").toString();
					reportSt[lines++] += "\r\n";
					reportSt[lines] = new StringBuilder().append(nameStringArray[seat]).append(": shows [")
							.append(EvalData.players[seat].holeCard1.toString()).append("] [")
							.append(EvalData.players[seat].holeCard2.toString()).append("]").toString();
					testAndDebug();
					reportSt[lines++] += "\r\n";
				}
				++seat;
			}
		}
	}

	/*-  ******************************************************************************
	 * Show Down
	 * ***************************************************************************** */
	private static void showdownHH() {
		reportSt[lines++] = "*** SHOW DOWN ***\r\n";

		for (int i = 0; i < PLAYERS; ++i) {
			seat = i;
			if (!GameControl.playerFolded[seat] && returnedToShowdownBD[i].compareTo(zeroBD) != 0
					&& playerSidePotSplitTotalBD[i].compareTo(zeroBD) != 0) {
				Crash.log("$$$");
			} else if (returnedToShowdownBD[i].compareTo(zeroBD) != 0) {
				reportSt[lines] += new StringBuilder().append("Uncalled bet ($")
						.append(Format.format(returnedToShowdownBD[i])).append(" returned to ")
						.append(EvalData.playerNames[i]).toString();
				testAndDebug();
				reportSt[lines++] += "\r\n";
			}
		}

		int winners = 0;
		for (int i = 0; i < PLAYERS; ++i) {
			if (GameControl.playerWon[i]) {
				winners++;
			}
		}

		// One winner, the simple case
		if (winners == 1) {
			boolean show = false;
			for (int i = 0; i < PLAYERS; ++i) {
				seat = i;
				if (!GameControl.playerFolded[i] && winnerCollectedBD[i].compareTo(zeroBD) != 0) {
					reportSt[lines] = new StringBuilder().append(nameStringArray[i]).append(" collected $")
							.append(Format.format(winnerCollectedBD[i])).append(" from pot ").toString();
					testAndDebug();
					reportSt[lines++] += "\r\n";
					show = true;
				}
				if (playerSidePotSplitTotalBD[i].compareTo(zeroBD) != 0) {
					reportSt[lines] = new StringBuilder().append(nameStringArray[i]).append(" collected $")
							.append(Format.format(playerSidePotSplitTotalBD[i])).append(" from Side pot ").toString();
					testAndDebug();
					reportSt[lines++] += "\r\n";
					show = true;
				}
				if (show) {
					reportSt[lines++] = new StringBuilder().append(nameStringArray[i]).append(": shows [")
							.append(EvalData.players[i].holeCard1).append(" ").append(EvalData.players[i].holeCard2)
							.append("]\r\n").toString();
					testAndDebug();
					show = false;
				}
			}
		}

		else {
			boolean show = false;
			for (int i = 0; i < PLAYERS; ++i) {
				seat = i;
				if (!GameControl.playerFolded[i] && winnerCollectedBD[i].compareTo(zeroBD) != 0) {
					reportSt[lines] = new StringBuilder().append(nameStringArray[i]).append(" collected $")
							.append(Format.format(winnerCollectedBD[i])).append(" from Main pot ").toString();
					testAndDebug();
					reportSt[lines++] += "\r\n";
					show = true;
				}
				if (playerSidePotSplitTotalBD[i].compareTo(zeroBD) != 0) {
					reportSt[lines] = new StringBuilder().append(nameStringArray[i]).append(" collected $")
							.append(Format.format(playerSidePotSplitTotalBD[i])).append(" from Side pot ").toString();
					testAndDebug();
					reportSt[lines++] += "\r\n";
					show = true;
				}
				if (show) {
					reportSt[lines++] = new StringBuilder().append(nameStringArray[i]).append(": shows [")
							.append(EvalData.players[i].holeCard1).append(" ").append(EvalData.players[i].holeCard2)
							.append("]\r\n").toString();
					testAndDebug();
					show = false;
				}
			}
		}
	}

	/*-  ******************************************************************************
	 * Summary 
	 *   ****************************************************************************** */
	static void summaryHH() {
		final String st;
		var sidePotBD = zeroBD;
		var mainPotBD = zeroBD;

		++lines;
		reportSt[lines++] = "*** SUMMARY *** \r\n";
		for (int i = 0; i < PLAYERS; ++i) {
			final boolean condition = winnerCollectedBD[i] != null && winnerCollectedBD[i].compareTo(zeroBD) != 0;
			if (condition) {
				sidePotBD = sidePotBD.add(playerSidePotSplitTotalBD[i]);
			}
		}

		st = " | Rake $0.0\r\n";

		if (sidePotBD != null) {
			if (sidePotBD.compareTo(zeroBD) == 0) {
				reportSt[lines++] = new StringBuilder().append("Total pot $").append(Format.format(GameControl.potBD))
						.append(st).toString();
			} else {
				mainPotBD = GameControl.potBD.subtract(sidePotBD);
				reportSt[lines++] = new StringBuilder().append("Total pot $").append(Format.format(GameControl.potBD))
						.append(" Main pot $").append(Format.format(mainPotBD)).append(". Side pot $")
						.append(Format.format(sidePotBD)).append(st).toString();
			}
		}
		if (EvalData.street == FLOP) {
			reportSt[lines++] = new StringBuilder().append("Board [").append(Board.board[0]).append(" ")
					.append(Board.board[1]).append(" ").append(Board.board[2]).append("]\r\n").toString();
		}
		if (EvalData.street == TURN) {
			reportSt[lines++] = new StringBuilder().append("Board [").append(Board.board[0]).append(" ")
					.append(Board.board[1]).append(" ").append(Board.board[2]).append(" ").append(Board.board[3])
					.append("]\r\n").toString();
		}
		if (EvalData.street == RIVER) {
			reportSt[lines++] = new StringBuilder().append("Board [").append(Board.board[0]).append(" ")
					.append(Board.board[1]).append(" ").append(Board.board[2]).append(" ").append(Board.board[3])
					.append(" ").append(Board.board[4]).append("]\r\n").toString();
		}

		// Step through each position
		for (seat = 0; seat < PLAYERS; ++seat) {
			reportSt[lines] = new StringBuilder().append("Seat ").append(seat + 1).append(": ")
					.append(nameStringArray[seat]).toString();
			if (GameControl.seatSB == seat) {
				reportSt[lines] += " (small blind)";
			}
			if (GameControl.seatBB == seat) {
				reportSt[lines] += " (big blind)";
			}
			if (GameControl.seatBU == seat) {
				reportSt[lines] += " (button)";
			}

			if (!GameControl.playerWonShowdown[seat]) {
				reportSt[lines] += new StringBuilder().append(" showed [").append(EvalData.players[seat].holeCard1)
						.append(" ").append(EvalData.players[seat].holeCard2).append("] and lost with a smile ")
						.toString();
				testSummary();
				reportSt[lines++] += "\r\n";
				continue;
			}

			if (GameControl.playerWon[seat]) {
				reportSt[lines] += new StringBuilder().append(" showed [").append(EvalData.players[seat].holeCard1)
						.append(" ").append(EvalData.players[seat].holeCard2).append("] and won ($")
						.append(Format.format(winnerCollectedBD[seat].add(playerSidePotSplitTotalBD[seat]))).append(")")
						.toString();
				testSummary();
				reportSt[lines++] += "\r\n";
				continue;
			}

			if (GameControl.playerFoldedPreflop[seat]) {
				reportSt[lines] += " folded before the flop";
				if (GameControl.moneyInBD[seat].compareTo(zeroBD) == 0) {
					reportSt[lines] += " (didn't bet)";
				}
				testSummary();
				reportSt[lines++] += "\r\n";
				continue;
			}
			if (GameControl.playerFoldedFlop[seat]) {
				reportSt[lines] += " folded on the flop";
				testSummary();
				reportSt[lines++] += "\r\n";
				continue;
			}
			if (GameControl.playerFoldedTurn[seat]) {
				reportSt[lines] += " folded on the turn";
				testSummary();
				reportSt[lines++] += "\r\n";
				continue;
			}
			if (GameControl.playerFoldedRiver[seat]) {
				reportSt[lines] += " folded on the river";
				testAndDebug();
				reportSt[lines++] += "\r\n";
			}
			// Simulation mode so non standaqrd Hand History
			if (EvalData.simulation && GameControl.foldCount < MAXFOLDED && !GameControl.playerWon[seat]) {
				reportSt[lines] += " completed hand without Showdown or Win";
				testSummary();
				reportSt[lines++] += "\r\n";
			}
		}
	}

	/*- Add to text file. */
	private static void writeHH() {
		if (pwFile1 == null) {
			Crash.log("WTF " + 0 + " " + seat + " " + GameControl.gamesPlayed + " " + GameControl.handsPlayed + " "
					+ EvalData.street);
		}
		for (int i = 0; i < lines; ++i) {
			pwFile1.append(reportSt[i]);
		}

		pwFile1.append("\r\n\r\n\r\n\r\n");
		pwFile1.flush();
	}

	/*-
	PokerStars Hand #198162255235:  Hold'em No Limit ($1/$2 USD) - 2019/03/15 13:30:27 ET
	Table 'Aaltje III' 6-max Seat #5 is the button
	Seat 1: psek1 ($202.38 in chips) 
	Seat 2: Rifa92 ($217.90 in chips) 
	Seat 3: solbi86 ($200 in chips) 
	Seat 4: Slavikkkk7 ($200 in chips) 
	Seat 5: typ6oky3mu4 ($200 in chips) 
	Seat 6: AlexEliseev7 ($211.01 in chips) 
	AlexEliseev7: posts small blind $1
	psek1: posts big blind $2
	*** HOLE CARDS ***
	Rifa92: calls $2
	solbi86: folds 
	Slavikkkk7: folds 
	typ6oky3mu4: folds 
	AlexEliseev7: folds 
	psek1: checks 
	*** FLOP *** [3c 5d Ks]
	psek1: bets $2
	Rifa92: calls $2
	*** TURN *** [3c 5d Ks] [3s]
	psek1: bets $7.01
	Rifa92: calls $7.01
	*** RIVER *** [3c 5d Ks 3s] [Ah]
	psek1: bets $17.93
	Rifa92: calls $17.93
	*** SHOW DOWN ***
	psek1: shows [4d 2d] (a straight, Ace to Five)
	Rifa92: mucks hand 
	psek1 collected $56.13 from pot
	*** SUMMARY ***
	Total pot $58.88 | Rake $2.75 
	Board [3c 5d Ks 3s Ah]
	Seat 1: psek1 (big blind) showed [4d 2d] and won ($56.13) with a straight, Ace to Five
	Seat 2: Rifa92 mucked
	Seat 3: solbi86 folded before Flop (didn't bet)
	Seat 4: Slavikkkk7 folded before Flop (didn't bet)
	Seat 5: typ6oky3mu4 (button) folded before Flop (didn't bet)
	Seat 6: AlexEliseev7 (small blind) folded before Flop
	
	
	PokerStars Hand #198162187333:  Hold'em No Limit ($1/$2 USD) - 2019/03/15 13:28:37 ET
	Table 'Aaltje III' 6-max Seat #3 is the button
	Seat 1: psek1 ($207.22 in chips) 
	Seat 2: Rifa92 ($158.24 in chips) 
	Seat 3: solbi86 ($200 in chips) 
	Seat 4: Slavikkkk7 ($224.65 in chips) 
	Seat 5: typ6oky3mu4 ($200 in chips) 
	Seat 6: AlexEliseev7 ($221.85 in chips) 
	Slavikkkk7: posts small blind $1
	typ6oky3mu4: posts big blind $2
	*** HOLE CARDS ***
	AlexEliseev7: raises $4 to $6
	psek1: folds 
	Rifa92: folds 
	solbi86: folds 
	Slavikkkk7: raises $19 to $25
	typ6oky3mu4: folds 
	AlexEliseev7: folds 
	Uncalled bet ($19) returned to Slavikkkk7
	Slavikkkk7 collected $14 from pot
	Slavikkkk7: doesn't show hand 
	*** SUMMARY ***
	Total pot $14 | Rake $0 
	Seat 1: psek1 folded before Flop (didn't bet)
	Seat 2: Rifa92 folded before Flop (didn't bet)
	Seat 3: solbi86 (button) folded before Flop (didn't bet)
	Seat 4: Slavikkkk7 (small blind) collected ($14)
	Seat 5: typ6oky3mu4 (big blind) folded before Flop
	Seat 6: AlexEliseev7 folded before Flop
	 */

}
