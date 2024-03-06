package holdemevaluator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

/*- ****************************************************************************
* This is the primary GUI for this project. The other is GUIAnalyzeMany.
* This one is use to select hole cards and board cards that will be evaluated for
* draws and made hands.
*
* The Help option displays a fairly comprehensive description.
*
* We do not use drag and drop, we click on a card in the array of 52 cards then
* we click on the destination. If there is a card in the destination already that
* card is returned to the deck array.
*
* There are options to automatically deal random cards for hole cards or for
* the board cards. All hands are evaluated when Run is selected and the results
* are displayed for Draws, Made Hands, and an analysis of the board for the street
* selected.
*
* If the preflop simulation is selected a fairly realistic preflop is played using
* ranges in range arrays that were derived from Hand History files. A hand history
* that looks like a Pokerstars Hand History is displayed.
*
*
* @author PEAK_
*******************************************************************************/

/*- ****************************************************************************
* GUI definitions
*******************************************************************************/
public class GUI extends JFrame implements ActionListener, Constants {

	private static final long serialVersionUID = 1L;

	private static final String[] CARDS = { "As", "Ac", "Ad", "Ah", "Ks", "Kc", "Kd", "Kh", "Qs", "Qc", "Qd", "Qh",
			"Js", "Jc", "Jd", "Jh", "Ts", "Tc", "Td", "Th", "9s", "9c", "9d", "9h", "8s", "8c", "8d", "8h", "7s", "7c",
			"7d", "7h", "6s", "6c", "6d", "6h", "5s", "5c", "5d", "5h", "4s", "4c", "4d", "4h", "3s", "3c", "3d", "3h",
			"2s", "2c", "2d", "2h" };

	private static final Color SPADE = SKY_BLUE;
	private static final Color DIAMOND = YELLOW;
	private static final Color CLUB = PINK;
	private static final Color HEART = LIGHT_GRAY;
	private static final Color CONTROL = GREEN;
	private static final Color BOARD = CYAN;
	private static final Color HOLE = CYAN;
	private static final Color MADE = WHEAT;
	private static final Color DRAW = KHAKI;
	private static final Color PLAY = SKY_BLUE;
	private static final Color POSITION = ORANGE;
	private static final Color OUTS_FLOP = TOMATO;
	private static final Color OUTS_TURN = OLIVE_DRAB;
	private static final Color ODDS_FTT = TOMATO;
	private static final Color ODDS_FTR = TOMATO;
	private static final Color ODDS_TTR = CYAN;
	private static final Color STACK = MAGENTA;
	private static final Color GOOD = BLUE;
	private static final Color DIM = GRAY;
	private static final Color MONEY = GREEN;
	private static final Color SHOW = WHEAT;
	private static final Color VALUE = KHAKI;
	private static final Color HAND_STATUS = WHITE;

	private static final JButton[] cardArray = new JButton[52];

	private static final JButton[] hole1Array = new JButton[6];

	private static final JButton[] hole2Array = new JButton[6];

	private static final JButton[] boardArray = new JButton[5];

	private static final JTextArea[][] handsArray = new JTextArea[6][5];

	private static final JTextArea[][] drawsArray = new JTextArea[6][5];

	private static final JTextArea[] drawArrayType = new JTextArea[6];

	private static final JTextArea[] madeArrayType = new JTextArea[6];

	private static final JTextArea[] playArray = new JTextArea[6];

	private static final JTextArea[] positionArray = new JTextArea[6];

	private static final JTextArea[] outsFlopArray = new JTextArea[6];

	private static final JTextArea[] outsTurnArray = new JTextArea[6];

	private static final JTextArea[] percentageFTTArray = new JTextArea[6];

	private static final JTextArea[] percentageFTRArray = new JTextArea[6];

	private static final JTextArea[] percentageTTRArray = new JTextArea[6];

	private static final JTextArea pot = new JTextArea("$0");

	private static final JTextArea showdown = new JTextArea("");

	private static final JButton run = new JButton("Run ");

	private static final JButton holeCards = new JButton("Deal Hole Cards");

	private static final JButton boardCards = new JButton("Deal Board Cards");

	private static final JButton help = new JButton("Help");

	private static final JCheckBox multipleHands = new JCheckBox("Multiple Hands");

	private static final JCheckBox preflopSimulation = new JCheckBox("Preflop Simulation");

	private static final JCheckBox handHistoryReport = new JCheckBox("Hand History Report");

	private static final JCheckBox drawMadePercentagesReport = new JCheckBox("Draw Made % Report");

	private static final JCheckBox drawMadePercentagesCompareReport = new JCheckBox("Draw Made % Compare Reporet");

	private static final JCheckBox drawMadeWonAtShowdownReport = new JCheckBox("Draw Made Won Report");

	private static final JCheckBox drawMadeToWinnersReport = new JCheckBox("Draw Made To Winners Report");

	private static final JCheckBox boardWonAtShowdownReport = new JCheckBox("Board Won Report");

	private static final JCheckBox drawMadeFreezeSeat1Report = new JCheckBox("Freeze Seat 1 Report");

	private static final JCheckBox drawMadeFreezeSeat2Report = new JCheckBox("Freeze Seat 2 Report");

	private static final JCheckBox drawMadeFreezeBoardReport = new JCheckBox("Freeze Board Report");

	private static final JRadioButton freezeNone = new JRadioButton("Freeze None");

	private static final JRadioButton freezeSeat1and2HoleCards = new JRadioButton("Freeze Seat 1 - 2");

	private static final JRadioButton freezeBoardCards = new JRadioButton("Freeze Board Cards");

	private static final JTextArea status = new JTextArea();

	private static final JRadioButton ten = new JRadioButton("10");

	private static final JRadioButton hundred = new JRadioButton("100");

	private static final JRadioButton thousand = new JRadioButton("1,000");

	private static final JRadioButton tenThousand = new JRadioButton("10,000");

	private static final JRadioButton hundredThousand = new JRadioButton("100,000");

	private static final JRadioButton million = new JRadioButton("1,000,000");

	private static final JRadioButton tenMillion = new JRadioButton("10,000,000");

	private static final JRadioButton hundredMillion = new JRadioButton("100,000,000");

	private static final Font fStandard = new Font("Dialog", Font.BOLD, 16);

	private static final Font fOutsFlop = new Font("Dialog", Font.BOLD, 18);

	private static final Font fOutsTurn = new Font("Dialog", Font.BOLD, 18);

	private static final Font fOddsFTT = new Font("Dialog", Font.BOLD, 18);

	private static final Font fOddsFTR = new Font("Dialog", Font.BOLD, 18);

	private static final Font fOddsTTR = new Font("Dialog", Font.BOLD, 18);

	private static final Font fCard = new Font("Dialog", Font.BOLD, 18);

	private static final Font fHeader = new Font("Dialog", Font.BOLD, 16);

	private static final Font fPot = new Font("Dialog", Font.BOLD, 20);

	private static final Font fTiny = new Font("Dialog", Font.PLAIN, 4);

	private static JPanel panelRadioStreet = new JPanel();

	private static final JRadioButton[] radioStreet = new JRadioButton[4];

	private static JPanel panelRadioHands = new JPanel();

	private static JPanel panelRadioFrozen = new JPanel();

	private static JPanel mainPanel;

	private static JPanel panelHeader;

	private static JPanel panelControl;

	private static JPanel panelCards; // 52 cards 4 X 12

	private static JPanel panelHoleCards; // indexed by seat number

	private static JPanel panelHands; // Hand 5 cards

	private static JPanel panelDraws; // Draw 5 cards

	private static JPanel panelDrawType; // Draw Type

	private static JPanel panelMadeType; // Made Type

	private static JPanel panelBoard; // Board

	private static JPanel panelBoardStatus; // Board status

	private static JPanel panelHandStatus; // Board status

	private static JPanel panelStatus;

	private static JPanel panelPlay; // Playability index

	private static JPanel panelPosition; // Position SB, BB, UTG. MP, CO, BU

	private static JPanel panelOutsFlop; // Players outs Flop

	private static JPanel panelOutsTurn; // Players outs Turn

	private static JPanel panelOddsFTT; // Players Odds Flop to Turn

	private static JPanel panelOddsFTR; // Players Odds Flop to River

	private static JPanel panelOddsTTR; // Players Odds Turn to River

	private static JPanel panelPot; // Pot

	private static JPanel panelShowdown;

	private static JPanel panelShowdownValue;

	private static JPanel panelCheckBoxesReports;

	private static JPanel panelCheckBoxesFreezeReports;

	private static JPanel panelCheckBoxesOptions;

	private static final JCheckBox crash = new JCheckBox("Crash on severe error");
	private static final JPanel panelCheck = new JPanel();

	/*-  ******************************************************************************
	 * The first click, if it is on a button representing a card, the button text is saved
	 * in fromClicked.
	 * The next click, if it is also on a button representing a card, the button text
	 * is saved in toClicked.
	 * If there is a click on any other button or JTextArea both fromClicked and
	 * toClicked are cleared.
	 * Data set when a card in cardArray  or in hole1Array or hole2Array or
	 * boardArray or flopArray of turn or river is clicked
	 *  ****************************************************************************** */
	private static boolean click1 = false;

	private static String fromClicked = "";

	/*- ****************************************************************************
	* Variables
	*******************************************************************************/
	private static int selectedStreet = FLOP;

	private static boolean freezeNoneOption = false;

	private static boolean freezeSeat1and2HoleCardsOption = false;

	private static boolean freezeBoardCardsOption = false;

	private static boolean multipleHandsOption = false;

	private static boolean preflopSimulationOption = false;

	private static boolean handHistoryReportOption = false;

	private static boolean preflopBothSimNoSimOption = false;

	private static boolean drawMadePercentagesOption = false;

	private static boolean drawMadePercentagesCompareOption = false;

	private static boolean drawMadeWonAtShowdownOption = false;

	private static boolean drawMadeToWinnersReportOption = false;

	private static boolean boardWonAtShowdownOption = false;

	private static boolean drawMadeFreezeSeat1ReportOption = false;

	private static boolean drawMadeFreezeSeat2ReportOption = false;

	private static boolean drawMadeFreezeBoardReportOption = false;

	private static boolean userSetBoardCards = false;

	private static boolean userSetHoleCards = false;

	private static boolean boardDealt = false;

	private static boolean holeDealt = false;

	private static boolean hole2to6Dealt = false;

	// Card as string is key, location as string is data
	// Keys Ac, .... 52 cards
	// locations cardArray, hxa, hxb, bx x is array index
	private static HashMap<String, String> location;

	private static HandRange range = new HandRange();

	// @formatter:off  
	private static final String helpString = new StringBuilder().append("Welcome to the HoldemEvaluator project GUI.\r\n")
			.append("This GUI demonstrates how  hole cards are combined with a common board and many different\r\n")
			.append("types of analysis are performed.  This GUI just touches the surface of what this project can do.\r\n")
			.append("There are 2 modes of use, single hand and multiple hand. The Multiple Hands check  box.\r\n")
			.append("Single Hand layout:\r\n")
			.append("To the left of the screen is an array of 52 cards. Click on one of these cards and then click on a Hole Card or	B oard Card \r\n ")
			.append("The columns to the right contain evaluation results. Playability of the hole cards, players position, outs on the Flop and Turn.\r\n")	
			.append("Playability of the 2 hole cards is169 to 1 with 169 the best AA for the possible hole card combinations.\r\n")
			.append("Odds as percentages Flop to Turn, Flop to River, and Turn to River.\r\n")
			.append("The last 4 folumns are Made hands type and cards, Drawing hands type and cards.\r\n ")
			.append("Below that are status areas with detailed information about the board and each players hand.\r\n ")
				.append("Below that, radio buttons to select a street. Buttons to deal random hole cards and random board cards.\r\n ")  
			.append("Multiple Hand layout:\r\n")
			.append("This is the mode with detailed reports based on collecting simulation results from multiple hands.\r\n")
			.append("There are many reports with many more to follow in the next release.  This is where we are really cutting edge.\r\n ")
			.append("Freeeze none is the totally random mode. You can freeze hole cards for 1 seat or 2 seats with cards manually selected.\r\n")
			.append("Or freeze the board with cards manually selected.\r\n")
			.append("One unique option is Preflop simulation. Instead of all of the possible hole card combinations we play Preflop\r\n")
			.append("simulations so that the hole cards used in the calculations are representative of the real world. Makes a huge difference.\r\n")
			.append("Try it. Send me your suggestions and comments. Feel free to use any of the source code.\r\n")
			.append("\r\n ").toString();
	
	private static final JTextArea boardStatus = new JTextArea("");
	
	private static final JTextArea handStatus = new JTextArea("");
	
	private static final JTextArea header = new JTextArea("  ");
	private static String header1 = "   Cards                                                                        Hole Cards              Play   Pos    OutF  OutT     FTT          FTR          TTR               Made Type                   Made Hand                 Draw Type                  Draw Hand";
	private static String header2 = "   Cards                                                                        Hole Cards              ";
	
	// @formatter:on

	private static final JFrame frame = new JFrame("  ");

	private static int numberToPlay = 0;

	// Constructor
	GUI() {

	}

	/*- ****************************************************************************
	* Main
	*******************************************************************************/
	public static void main(String[] args) {
		if (!ValidInstallation.checkValid()) {
			return;
		}
		myFrame();
		Deck.shuffle();
	}

	/*- ****************************************************************************
	* Frame
	*******************************************************************************/
	public static void myFrame() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(10, 10);
		frame.setMinimumSize(new Dimension(1600, 980));
		frame.setLayout(new FlowLayout());

		frame.setTitle("Game Simulator   ");
		EvalData.initializeNewGame();
		selectedStreet = FLOP;
		/// Set up the main panel
		mainPanel = new JPanel(null);
		mainPanel.setPreferredSize(new Dimension(1700, 980));

		panelRadioHands = new JPanel(new GridLayout(1, 7));
		panelRadioHands.setBounds(10, 860, 1000, 30);
		panelRadioHands.setBackground(WHITE);
		final var handsBox = Box.createHorizontalBox();
		final var handsGroup = new ButtonGroup();
		handsBox.add(panelRadioHands);
		mainPanel.add(panelRadioHands);
		panelRadioHands.setVisible(false);

		panelHeader = new JPanel(new GridLayout(1, 1));
		panelHeader.setBounds(10, 5, 1700, 30);
		header.setFont(fHeader);
		header.setForeground(BLACK);
		header.setBackground(WHITE);
		header.setText(header1);
		panelHeader.add(header);
		mainPanel.add(panelHeader);

		panelCards = new JPanel(new GridLayout(13, 4));
		final var panelCSize = new Dimension(400, 450);
		panelCards.setMaximumSize(panelCSize);
		panelCards.setPreferredSize(panelCSize);
		panelCards.setBounds(20, 40, 300, 450);
		mainPanel.add(panelCards);

		panelBoard = new JPanel(new GridLayout(1, 5));
		panelBoard.setBounds(400, 260, 320, 50);
		mainPanel.add(panelBoard);

		panelBoardStatus = new JPanel(new GridLayout(1, 1));
		panelBoardStatus.setBounds(400, 320, 1250, 50);
		mainPanel.add(panelBoardStatus);
		panelBoardStatus.add(boardStatus);
		boardStatus.setFont(fStandard);
		boardStatus.setForeground(BLACK);
		boardStatus.setBackground(BOARD);
		boardStatus.setLineWrap(true);
		boardStatus.setWrapStyleWord(true);
		panelBoardStatus.setVisible(true);

		panelStatus = new JPanel(new GridLayout(1, 1));
		panelStatus.setBounds(400, 390, 1250, 50);
		mainPanel.add(panelStatus);

		status.setFont(fStandard);
		status.setPreferredSize(new Dimension(15, 15));
		status.setBackground(HAND_STATUS);
		status.setVisible(true);
		status.setLineWrap(true);
		status.setWrapStyleWord(true);
		panelStatus.setVisible(true);
		panelStatus.add(status);

		panelHandStatus = new JPanel(new GridLayout(1, 1));
		panelHandStatus.setBounds(400, 450, 1250, 130);
		mainPanel.add(panelHandStatus);
		panelHandStatus.add(handStatus);
		handStatus.setFont(fStandard);
		handStatus.setForeground(BLACK);
		handStatus.setBackground(HAND_STATUS);
		panelHandStatus.setVisible(true);

		panelPot = new JPanel(new GridLayout(1, 5));
		final var panelDSize = new Dimension(40, 40);
		panelPot.setMaximumSize(panelDSize);
		panelPot.setPreferredSize(panelDSize);
		panelPot.setBounds(760, 260, 90, 50);
		panelPot.add(pot);
		pot.setBackground(MONEY);
		pot.setFont(fPot);
		mainPanel.add(panelPot);

		panelCheckBoxesOptions = new JPanel(new GridLayout(1, 2));
		panelCheckBoxesOptions.setBounds(10, 810, 400, 40);
		mainPanel.add(panelCheckBoxesOptions);

		panelCheckBoxesReports = new JPanel(new GridLayout(1, 6));
		panelCheckBoxesReports.setBounds(10, 870, 1000, 40);
		mainPanel.add(panelCheckBoxesReports);

		panelCheckBoxesFreezeReports = new JPanel(new GridLayout(1, 3));
		panelCheckBoxesFreezeReports.setBounds(10, 930, 300, 40);
		mainPanel.add(panelCheckBoxesFreezeReports);

		panelRadioFrozen = new JPanel(new GridLayout(1, 3));
		panelRadioFrozen.setBounds(200, 810, 600, 40);
		final var frozenBox = Box.createHorizontalBox();
		final var frozenGroup = new ButtonGroup();
		frozenBox.add(panelRadioFrozen);
		mainPanel.add(panelRadioFrozen);

		panelRadioStreet = new JPanel(new GridLayout(1, 4));
		panelRadioStreet.setBounds(10, 730, 450, 40);
		final var streetBox = Box.createHorizontalBox();
		final var streetGroup = new ButtonGroup();
		streetBox.add(panelRadioStreet);
		mainPanel.add(panelRadioStreet);

		panelControl = new JPanel(new GridLayout(1, 6));
		panelControl.setBounds(10, 770, 1000, 40);
		mainPanel.add(panelControl);

		panelShowdownValue = new JPanel(new GridLayout(1, 1));
		panelShowdownValue.setBounds(20, 740, 1630, 150);
		mainPanel.add(panelShowdownValue);
		panelShowdownValue.setVisible(true);

		panelShowdown = new JPanel(new GridLayout(1, 1));
		panelShowdown.setBounds(20, 900, 1630, 50);
		mainPanel.add(panelShowdown);
		panelShowdown.setVisible(true);

		panelHoleCards = new JPanel(new GridLayout(6, 2));
		panelHoleCards.setBackground(LIGHT_GRAY);
		panelHoleCards.setForeground(BLACK);
		final var panelHSize = new Dimension(80, 200);
		panelHoleCards.setMaximumSize(panelHSize);
		panelHoleCards.setPreferredSize(panelHSize);
		panelHoleCards.setBounds(400, 40, 120, 200);
		mainPanel.add(panelHoleCards);

		panelPlay = new JPanel(new GridLayout(6, 1));
		panelPlay.setBounds(550, 40, 30, 200);
		mainPanel.add(panelPlay);

		panelPosition = new JPanel(new GridLayout(6, 1));
		panelPosition.setBounds(600, 40, 30, 200);
		mainPanel.add(panelPosition);

		panelOutsFlop = new JPanel(new GridLayout(6, 1));
		panelOutsFlop.setBounds(650, 40, 30, 200);
		mainPanel.add(panelOutsFlop);

		panelOutsTurn = new JPanel(new GridLayout(6, 1));
		panelOutsTurn.setBounds(700, 40, 30, 200);
		mainPanel.add(panelOutsTurn);

		panelOddsFTT = new JPanel(new GridLayout(6, 1));
		panelOddsFTT.setBounds(750, 40, 60, 200);
		mainPanel.add(panelOddsFTT);

		panelOddsFTR = new JPanel(new GridLayout(6, 1));
		panelOddsFTR.setBounds(830, 40, 60, 200);
		mainPanel.add(panelOddsFTR);

		panelOddsTTR = new JPanel(new GridLayout(6, 1));
		panelOddsTTR.setBounds(910, 40, 60, 200);
		mainPanel.add(panelOddsTTR);

		panelMadeType = new JPanel(new GridLayout(6, 1));
		panelMadeType.setBounds(1010, 40, 140, 200);
		mainPanel.add(panelMadeType);

		panelHands = new JPanel(new GridLayout(6, 5));
		panelHands.setBounds(1180, 40, 140, 200);
		mainPanel.add(panelHands);

		panelDrawType = new JPanel(new GridLayout(6, 1));
		panelDrawType.setBounds(1350, 40, 140, 200);
		mainPanel.add(panelDrawType);

		panelDraws = new JPanel(new GridLayout(6, 5));
		panelDraws.setBounds(1520, 40, 140, 200);
		mainPanel.add(panelDraws);

		panelHeader.add(header);
		mainPanel.add(panelHeader);

		location = new HashMap<>();
		for (var i = 0; i < 52; i++) {
			cardArray[i] = new JButton(CARDS[i]);
			final var c = new Card(CARDS[i]);
			cardArray[i].setFont(fCard);
			cardArray[i].setVisible(true);
			cardArray[i].setPreferredSize(new Dimension(10, 10));
			cardArray[i].addActionListener(new Listener());
			switch (c.suit) {
			case 0 -> cardArray[i].setBackground(SPADE);
			case 1 -> cardArray[i].setBackground(DIAMOND);
			case 2 -> cardArray[i].setBackground(CLUB);
			default -> cardArray[i].setBackground(HEART);
			}
			panelCards.add(cardArray[i]);
			location.put(cardArray[i].getText(), "cardArray");
		}

		radioStreet[0] = new JRadioButton("Flop");
		radioStreet[1] = new JRadioButton("Turn");
		radioStreet[2] = new JRadioButton("River");
		radioStreet[3] = new JRadioButton("All ");
		radioStreet[0].setFont(fStandard);
		radioStreet[1].setFont(fStandard);
		radioStreet[2].setFont(fStandard);
		radioStreet[3].setFont(fStandard);
		for (var i = 0; i < 4; i++) {
			radioStreet[i].addActionListener(new Listener());
			panelRadioStreet.add(radioStreet[i]);
			streetGroup.add(radioStreet[i]);
		}
		radioStreet[0].setSelected(true);
		selectedStreet = FLOP;
		panelRadioStreet.setFont(fStandard);
		radioStreet[3].setSelected(true);
		preflopSimulation.setSelected(true);

		for (var i = 0; i < 6; i++) {
			hole1Array[i] = new JButton("");
			hole1Array[i].setPreferredSize(new Dimension(10, 10));
			hole1Array[i].setFont(fTiny);
			hole1Array[i].setText(new StringBuilder().append("h").append(String.valueOf(i)).append("a").toString());
			hole1Array[i].addActionListener(new Listener());
			hole1Array[i].setForeground(BLACK);
			hole1Array[i].setBackground(HOLE);
			panelHoleCards.add(hole1Array[i]);

			hole2Array[i] = new JButton("");
			hole2Array[i].setPreferredSize(new Dimension(10, 10));
			hole2Array[i].setFont(fTiny);
			hole2Array[i].setText(new StringBuilder().append("h").append(String.valueOf(i)).append("b").toString());
			hole2Array[i].addActionListener(new Listener());
			hole2Array[i].setForeground(BLACK);
			hole2Array[i].setBackground(HOLE);
			panelHoleCards.add(hole2Array[i]);
		}

		for (var i = 0; i < 6; i++) {
			playArray[i] = new JTextArea("");
			playArray[i].setFont(fStandard);
			playArray[i].setPreferredSize(new Dimension(25, 25));
			playArray[i].setBackground(PLAY);
			panelPlay.add(playArray[i]);
		}

		for (var i = 0; i < 6; i++) {
			positionArray[i] = new JTextArea("");
			positionArray[i].setFont(fStandard);
			positionArray[i].setPreferredSize(new Dimension(25, 25));
			positionArray[i].setBackground(POSITION);
			panelPosition.add(positionArray[i]);
		}

		for (var i = 0; i < 6; i++) {
			outsFlopArray[i] = new JTextArea("");
			outsFlopArray[i].setFont(fOutsFlop);
			outsFlopArray[i].setPreferredSize(new Dimension(25, 25));
			outsFlopArray[i].setBackground(OUTS_FLOP);
			panelOutsFlop.add(outsFlopArray[i]);
		}

		for (var i = 0; i < 6; i++) {
			outsTurnArray[i] = new JTextArea("");
			outsTurnArray[i].setFont(fOutsTurn);
			outsTurnArray[i].setPreferredSize(new Dimension(25, 25));
			outsTurnArray[i].setBackground(OUTS_TURN);
			panelOutsTurn.add(outsTurnArray[i]);
		}

		for (var i = 0; i < 6; i++) {
			percentageFTTArray[i] = new JTextArea("");
			percentageFTTArray[i].setFont(fOddsFTT);
			percentageFTTArray[i].setPreferredSize(new Dimension(25, 25));
			percentageFTTArray[i].setBackground(ODDS_FTT);
			panelOddsFTT.add(percentageFTTArray[i]);
		}

		for (var i = 0; i < 6; i++) {
			percentageFTRArray[i] = new JTextArea("");
			percentageFTRArray[i].setFont(fOddsFTR);
			percentageFTRArray[i].setPreferredSize(new Dimension(25, 25));
			percentageFTRArray[i].setBackground(ODDS_FTR);
			panelOddsFTR.add(percentageFTRArray[i]);
		}

		for (var i = 0; i < 6; i++) {
			percentageTTRArray[i] = new JTextArea("");
			percentageTTRArray[i].setFont(fOddsTTR);
			percentageTTRArray[i].setPreferredSize(new Dimension(25, 25));
			percentageTTRArray[i].setBackground(ODDS_TTR);
			panelOddsTTR.add(percentageTTRArray[i]);
		}

		for (var i = 0; i < 6; i++) {
			for (var j = 0; j < 5; j++) {
				handsArray[i][j] = new JTextArea("");
				handsArray[i][j].setFont(fTiny);
				handsArray[i][j].setPreferredSize(new Dimension(25, 25));
				handsArray[i][j].setBackground(MADE);
				panelHands.add(handsArray[i][j]);
			}
		}

		for (var i = 0; i < 6; i++) {
			for (var j = 0; j < 5; j++) {
				drawsArray[i][j] = new JTextArea("");
				drawsArray[i][j].setFont(fTiny);
				drawsArray[i][j].setPreferredSize(new Dimension(25, 25));
				drawsArray[i][j].setBackground(DRAW);
				panelDraws.add(drawsArray[i][j]);
			}
		}

		for (var i = 0; i < 6; i++) {
			drawArrayType[i] = new JTextArea("");
			drawArrayType[i].setFont(fStandard);
			drawArrayType[i].setPreferredSize(new Dimension(25, 25));
			drawArrayType[i].setBackground(DRAW);
			panelDrawType.add(drawArrayType[i]);
		}

		for (var i = 0; i < 6; i++) {
			madeArrayType[i] = new JTextArea("");
			madeArrayType[i].setFont(fTiny);
			madeArrayType[i].setPreferredSize(new Dimension(25, 25));
			madeArrayType[i].setBackground(MADE);
			panelMadeType.add(madeArrayType[i]);
		}

		for (var i = 0; i < 5; i++) {
			boardArray[i] = new JButton("");
			boardArray[i].setPreferredSize(new Dimension(10, 10));
			boardArray[i].addActionListener(new Listener());
			boardArray[i].setFont(fTiny);
			boardArray[i].setText("bo" + String.valueOf(i));
			boardArray[i].setForeground(BLACK);
			boardArray[i].setBackground(BOARD);
			panelBoard.add(boardArray[i]);
		}

		multipleHands.setFont(fStandard);
		multipleHands.setPreferredSize(new Dimension(25, 25));
		multipleHands.addActionListener(new Listener());
		panelCheckBoxesOptions.add(multipleHands);
		multipleHands.setSelected(true);
		multipleHands.setBackground(YELLOW);
		multipleHandsOption = true;

		preflopSimulation.setFont(fStandard);
		preflopSimulation.setPreferredSize(new Dimension(25, 25));
		preflopSimulation.addActionListener(new Listener());
		panelCheckBoxesOptions.add(preflopSimulation);
		preflopSimulationOption = true;
		preflopSimulation.setSelected(true);
		checkBoxes();

		freezeNone.setFont(fStandard);
		freezeNone.addActionListener(new Listener());
		panelRadioHands.add(freezeNone);
		frozenGroup.add(freezeNone);
		panelRadioFrozen.add(freezeNone);
		freezeNone.setSelected(true);

		freezeSeat1and2HoleCards.setFont(fStandard);
		freezeSeat1and2HoleCards.addActionListener(new Listener());
		frozenGroup.add(freezeSeat1and2HoleCards);
		panelRadioFrozen.add(freezeSeat1and2HoleCards);

		freezeSeat1and2HoleCards.setFont(fStandard);
		freezeSeat1and2HoleCards.addActionListener(new Listener());
		frozenGroup.add(freezeSeat1and2HoleCards);
		panelRadioFrozen.add(freezeSeat1and2HoleCards);

		freezeBoardCards.setFont(fStandard);
		freezeBoardCards.addActionListener(new Listener());
		frozenGroup.add(freezeBoardCards);
		panelRadioFrozen.add(freezeBoardCards);

		handHistoryReport.setFont(fStandard);
		handHistoryReport.setPreferredSize(new Dimension(25, 25));
		handHistoryReport.addActionListener(new Listener());
		handHistoryReport.setSelected(false);
		panelCheckBoxesReports.add(handHistoryReport);
		handHistoryReport.setSelected(false);

		drawMadePercentagesReport.setFont(fStandard);
		drawMadePercentagesReport.setPreferredSize(new Dimension(25, 25));
		drawMadePercentagesReport.addActionListener(new Listener());
		drawMadePercentagesReport.setSelected(false);
		panelCheckBoxesReports.add(drawMadePercentagesReport);

		drawMadePercentagesCompareReport.setFont(fStandard);
		drawMadePercentagesCompareReport.setPreferredSize(new Dimension(25, 25));
		drawMadePercentagesCompareReport.addActionListener(new Listener());
		drawMadePercentagesCompareReport.setSelected(false);
		panelCheckBoxesReports.add(drawMadePercentagesCompareReport);

		drawMadeWonAtShowdownReport.setFont(fStandard);
		drawMadeWonAtShowdownReport.setPreferredSize(new Dimension(25, 25));
		drawMadeWonAtShowdownReport.addActionListener(new Listener());
		drawMadeWonAtShowdownReport.setSelected(false);
		panelCheckBoxesReports.add(drawMadeWonAtShowdownReport);
		drawMadeWonAtShowdownReport.setSelected(true);

		drawMadeToWinnersReport.setFont(fStandard);
		drawMadeToWinnersReport.setPreferredSize(new Dimension(25, 25));
		drawMadeToWinnersReport.addActionListener(new Listener());
		drawMadeToWinnersReport.setSelected(false);
		panelCheckBoxesReports.add(drawMadeToWinnersReport);

		boardWonAtShowdownReport.setFont(fStandard);
		boardWonAtShowdownReport.setPreferredSize(new Dimension(25, 25));
		boardWonAtShowdownReport.addActionListener(new Listener());
		boardWonAtShowdownReport.setSelected(false);
		panelCheckBoxesReports.add(boardWonAtShowdownReport);

		drawMadeFreezeSeat1Report.setFont(fStandard);
		drawMadeFreezeSeat1Report.setPreferredSize(new Dimension(25, 25));
		drawMadeFreezeSeat1Report.addActionListener(new Listener());
		drawMadeFreezeSeat1Report.setSelected(false);
		panelCheckBoxesFreezeReports.add(drawMadeFreezeSeat1Report);

		drawMadeFreezeSeat2Report.setFont(fStandard);
		drawMadeFreezeSeat2Report.setPreferredSize(new Dimension(25, 25));
		drawMadeFreezeSeat2Report.addActionListener(new Listener());
		drawMadeFreezeSeat2Report.setSelected(false);
		panelCheckBoxesFreezeReports.add(drawMadeFreezeSeat2Report);

		drawMadeFreezeBoardReport.setFont(fStandard);
		drawMadeFreezeBoardReport.setPreferredSize(new Dimension(25, 25));
		drawMadeFreezeBoardReport.addActionListener(new Listener());
		drawMadeFreezeBoardReport.setSelected(false);
		panelCheckBoxesFreezeReports.add(drawMadeFreezeBoardReport);

		run.setFont(fStandard);
		run.setPreferredSize(new Dimension(15, 15));
		run.addActionListener(new Listener());
		run.setBackground(CONTROL);
		panelControl.add(run);

		holeCards.setFont(fStandard);
		holeCards.setPreferredSize(new Dimension(15, 15));
		holeCards.addActionListener(new Listener());
		holeCards.setBackground(CONTROL);
		panelControl.add(holeCards);

		boardCards.setFont(fStandard);
		boardCards.setPreferredSize(new Dimension(15, 15));
		boardCards.addActionListener(new Listener());
		boardCards.setBackground(CONTROL);
		panelControl.add(boardCards);

		help.setFont(fStandard);
		help.setPreferredSize(new Dimension(15, 15));
		help.addActionListener(new Listener());
		help.setBackground(CONTROL);
		panelControl.add(help);

		// Number of hands to play
		ten.setFont(fStandard);
		ten.addActionListener(new Listener());
		ten.setBackground(WHITE);
		panelRadioHands.add(ten);
		handsGroup.add(ten);

		hundred.setFont(fStandard);
		hundred.addActionListener(new Listener());
		hundred.setBackground(WHITE);
		panelRadioHands.add(hundred);
		handsGroup.add(hundred);

		thousand.setFont(fStandard);
		thousand.addActionListener(new Listener());
		thousand.setBackground(WHITE);
		panelRadioHands.add(thousand);
		thousand.setSelected(true);
		handsGroup.add(thousand);

		tenThousand.setFont(fStandard);
		tenThousand.addActionListener(new Listener());
		tenThousand.setBackground(WHITE);
		panelRadioHands.add(tenThousand);
		handsGroup.add(tenThousand);

		hundredThousand.setFont(fStandard);
		hundredThousand.addActionListener(new Listener());
		hundredThousand.setBackground(WHITE);
		panelRadioHands.add(hundredThousand);
		handsGroup.add(hundredThousand);

		million.setFont(fStandard);
		million.addActionListener(new Listener());
		million.setBackground(WHITE);
		panelRadioHands.add(million);
		handsGroup.add(million);

		tenMillion.setFont(fStandard);
		tenMillion.addActionListener(new Listener());
		tenMillion.setBackground(WHITE);
		panelRadioHands.add(tenMillion);
		handsGroup.add(tenMillion);

		hundredMillion.setFont(fStandard);
		hundredMillion.addActionListener(new Listener());
		hundredMillion.setBackground(WHITE);
		panelRadioHands.add(hundredMillion);
		handsGroup.add(hundredMillion);

		showdown.setFont(fStandard);
		showdown.setPreferredSize(new Dimension(4000, 100));
		showdown.setBackground(VALUE);
		panelShowdownValue.add(showdown);

		panelCheck.setLayout(new GridLayout(1, 1));
		final var optionsCheck = Box.createHorizontalBox();
		optionsCheck.setFont(fHeader);
		panelCheck.setBorder(BorderFactory.createTitledBorder("Options"));
		panelCheck.add(crash);
		mainPanel.add(panelCheck);
		crash.setSelected(true);
		crash.addActionListener(new Listener());
		panelCheck.setFont(fHeader);
		crash.setSelected(true);

		// Set some properties of the frame
		frame.add(mainPanel);
		frame.setContentPane(mainPanel);
		frame.setTitle("Evaluate Hands");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	/*-  ******************************************************************************
	 * 
	 *  ****************************************************************************** */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Dummy to avoid compiler error message
	}

	/*-  ******************************************************************************
	 * The Run button has been clicked.
	 * If a random check box is checked and we have not done a random setup yet
	 * then update hole cards and or board cards.
	 *
	 * 	Check holeArray and call Evaluate methods to set hole cards.
	 * 	Check board array and call Evaluate methods to set board cards.
	 *  ****************************************************************************** */
	private static void run() {
		checkBoxes();
		clearDisplay();
		if (crash.isSelected()) {
			Crash.crash = true;
		} else {
			Crash.crash = false;
		}
		if (multipleHandsOption) {
			if (freezeBoardCardsOption) {
				if (!checkAndSortBoardCards()) {
					return;
				}
			}
			if (freezeSeat1and2HoleCardsOption) {
				if (!checkSeat1()) {
					return;
				}
				if (!checkAndSortHoleCards()) {
					return;
				}
			}
			if (!readyForMultiple()) {
				return;
			}
			EvalData.handInfo = true; // TODO
			EvalData.manyHands = true;
			doSimulationDataCollection();
		} else {
			if (checkHoleCardsAndBoardCards()) {
				return;
			}
			if (!checkAndSortHoleCards()) {
				return;
			}
			if (!checkAndSortBoardCards()) {
				return;
			}

			EvalData.handInfo = true;
			EvalData.manyHands = false;
			runOneHand();
			updateData();
			updateDrawsAndMade();
			updateBoardStatus();
		}

		if (drawMadePercentagesOption)

		{

		}
	}

	/*-**********************************************************************************************
	* This method will run one hand.
	**************************************************************************************************/
	private static void runOneHand() {
		// Run for selected street
		EvalData.initializeNewGame();
		if (userSetHoleCards) {
			setHoleCards();
		}
		if (userSetBoardCards) {
			setBoardCards();
		}

		EvalData.handInfo = true;
		for (var i = 0; i < PLAYERS; i++) {
			if (!GameControl.playerFolded[i]) {
				switch (selectedStreet) {
				case FLOP -> {
					GameControl.evaluateFlopOneSeat(i);
				}
				case TURN -> GameControl.evaluateTurnOneSeat(i);
				case RIVER -> {
					GameControl.evaluateRiverOneSeat(i);
					Showdown.showdown();
				}
				}
			}
		}
	}

	/*-  ******************************************************************************
	 * The Full Simulation  option has been selected.
	 * Prompt for the number of hands to play
	 *  ****************************************************************************** */
	private static void doSimulationDataCollection() {
		EvalData.initializeNewGame();
		EvalData.manyHands = true;
		EvalData.rotatePlayers = true;

		// Simulate the play of xxx hands, Preflop, Flop, Turn, and River

		if (preflopBothSimNoSimOption) {
			EvalData.preflopBothSimNoSim = true;
		}

		if (freezeNoneOption) {
			GameControl.playMultipleHandsAllRandom(numberToPlay, selectedStreet);
		} else if (freezeSeat1and2HoleCardsOption) {
			GameControl.playMultipleHandsFreezeSeat1and2(numberToPlay, selectedStreet);
		} else if (freezeBoardCardsOption) {
			GameControl.playMultipleHandsFreezeBoard(numberToPlay, selectedStreet);
		}

		if (EvalData.freezeSeat1 || EvalData.freezeSeat2) {

		}

		final var hmlFlop = new IndexArrayDrawMadeWin(IndexArrays.hmlDrawFlop, IndexArrays.hmlMadeFlop,
				IndexArrays.hmlShowdownFlop, HML_FLOP_ST, DRAW_ST, MADE_ST);

		final var hmlTurn = new IndexArrayDrawMadeWin(IndexArrays.hmlDrawTurn, IndexArrays.hmlMadeTurn,
				IndexArrays.hmlShowdownTurn, HML_TURN_ST, DRAW_ST, MADE_ST);

		ReportMadeDrawPercentages r = new ReportMadeDrawPercentages();
		final var reportFlop = new IndexArrayReport();

		if (handHistoryReportOption) {
			Reports.reportPreflop(100, 100);
		}

		if (drawMadeWonAtShowdownOption) {
//			reportFlop.reportDraw(0, 0, "Flop Draws with HML Index", hmlFlop);
			// reportFlop.reportMade(0, 370, "Flop Made hands with HML Index", hmlFlop);
			// reportFlop.reportShowdown(0, 740, "Flop Showdown hands with HML Index",
			// hmlFlop);
		}
		if (drawMadeToWinnersReportOption) {

		}
		if (boardWonAtShowdownOption) {

		}

		if (drawMadePercentagesOption) {
			r.reportDraw(10, 50, "Draw Percentages");
			r.reportMade(700, 50, "Made Percentages");
		}

		if (drawMadePercentagesCompareOption) {
			r.reportDrawCompare(10, 50, "Draw comparison");
			r.reportMadeCompare(700, 50, "Made comparison");
		}

		if (drawMadeFreezeSeat1ReportOption) {
			r.reportDrawCompareFreezeSeat1(10, 50, "Draw comparison Freeze Seat 1");
			r.reportMadeCompareFreezeSeat1(700, 50, "Made comparison Freeze Seat 1");
		}
		if (drawMadeFreezeSeat2ReportOption) {
			r.reportDrawCompareFreezeSeat1and2(10, 50, "Draw comparison Freeze Seat 1 and 2");
			r.reportMadeCompareFreezeSeat1and2(700, 50, "Made comparison Freeze Seat 1 and 2");
		}
		if (drawMadeFreezeBoardReportOption) {
			r.reportDrawCompareFreezeBoard(10, 50, "Draw comparison Freeze Board");
			r.reportMadeCompareFreezeBoard(700, 50, "Made comparison Freeze Board");
		}

//			final var wetDryFlop = new IndexArrayDrawMadeWin(IndexArrays.wetDryDrawFlop, IndexArrays.wetDryMadeFlop,
//					IndexArrays.wetDryShowdownFlop, WET_DRY_ST, DRAW_ST, MADE_ST);

//			final var typeOf1755Flop = new IndexArrayDrawMadeWin(IndexArrays.typeOf1755DrawFlop,
//					IndexArrays.typeOf1755MadeFlop, IndexArrays.typeOf1755ShowdownFlop, TYPE_OF_1755_ST, DRAW_ST, MADE_ST);

//			final var SCBPFlop = new IndexArrayDrawMadeWin(IndexArrays.SCBPDrawFlop, IndexArrays.SCBPMadeFlop,
//					IndexArrays.SCBPShowdownFlop, SCBP_ST, DRAW_ST, MADE_ST);	  

		// final var hmlFlopWin = new IndexArrayDrawMadeWin(IndexArrays.hmlDrawFlop,
		// IndexArrays.hmlMadeFlop,
		// IndexArrays.hmlShowdownFlop, HML_FLOP_ST, DRAW_ST, MADE_ST);

//		final var hmlTurnWin = new IndexArrayDrawMadeWin(IndexArrays.hmlDrawTurn, IndexArrays.hmlMadeTurn,
//				IndexArrays.hmlShowdownTurn, HML_TURN_ST, DRAW_ST, MADE_ST);

//		final var wetDryFlopWin = new IndexArrayDrawMadeWin(IndexArrays.wetDryDrawFlop, IndexArrays.wetDryMadeFlop,
		// IndexArrays.wetDryShowdownFlop, WET_DRY_ST, DRAW_ST, MADE_ST);

//		final var typeOf1755FlopWin = new IndexArrayDrawMadeWin(IndexArrays.typeOf1755DrawFlop,
//				IndexArrays.typeOf1755MadeFlop, IndexArrays.typeOf1755ShowdownFlop, TYPE_OF_1755_ST, DRAW_ST, MADE_ST);

//		final var SCBPFlopWin = new IndexArrayDrawMadeWin(IndexArrays.SCBPDrawFlop, IndexArrays.SCBPMadeFlop,
//				IndexArrays.SCBPShowdownFlop, SCBP_ST, DRAW_ST, MADE_ST);
	}

	/*-  **************************************************************************
	 * Before we can deal any hole cards we must make sure that there will be no
	 * duplicate cards. We do this by setting the board cards. 
	 * This will make them dead cards in the deck so they will not be dealt.
	****************************************************************************** */
	private static void setBoardCards() {
		Card card1 = null;
		Card card2 = null;
		Card card3 = null;
		Card card4 = null;
		Card card5 = null;
		if (boardArray[0].getText().length() == 2 && boardArray[1].getText().length() == 2
				&& boardArray[2].getText().length() == 2) {
			card1 = new Card(boardArray[0].getText());
			card2 = new Card(boardArray[1].getText());
			card3 = new Card(boardArray[2].getText());
			Dealer.setFlopCards(card1, card2, card3);
		} else {
			return;
		}
		if (boardArray[3].getText().length() == 2) {
			card4 = new Card(boardArray[3].getText());
			Dealer.setTurnCard(card4);
		}
		if (boardArray[4].getText().length() == 2) {
			card5 = new Card(boardArray[4].getText());
			Dealer.setRiverCard(card5);
		}
	}

	/*-  **************************************************************************
	 * Before we can deal any board cards we must make sure that there will be no
	 * duplicate cards. We do this by setting the hole cards. 
	 * This will make them dead cards in the deck so they will not be dealt.
	****************************************************************************** */
	private static void setHoleCards() {
		Card card1 = null;
		Card card2 = null;
		for (var i = 0; i < 6; i++) {
			if (hole1Array[i].getText().length() == 2 && hole2Array[i].getText().length() == 2) {
				card1 = new Card(hole1Array[i].getText());
				card2 = new Card(hole2Array[i].getText());
				Dealer.setHoleCards(i, card1, card2);
				GameControl.playerFolded[i] = false;
			} else {
				GameControl.playerFolded[i] = true;
			}
		}
	}

	/*-  ****************************************************************************
	 * Board cards are all returned to cardArray
	****************************************************************************** */
	private static void returnBoardCards() {
		Card card1;
		for (var i = 0; i < 5; i++) {
			if (boardArray[i].getText().length() == 2) {
				card1 = new Card(boardArray[i].getText());
				final var ndx1 = ((12 - card1.value) * 4) + card1.suit;
				cardArray[ndx1].setText(card1.toString());
				location.put(card1.toString(), "cardArray");
				boardArray[i].setText("bo" + String.valueOf(i));
				boardArray[i].setFont(fTiny);
			}
		}
	}

	/*-  ****************************************************************************
	 * Hole cards are all returned to cardArray
	****************************************************************************** */
	private static void returnHoleCards() {
		Card card1;
		Card card2;
		for (var i = 0; i < 6; i++) {
			if (hole1Array[i].getText().length() == 2 && hole2Array[i].getText().length() == 2) {
				card1 = new Card(hole1Array[i].getText());
				card2 = new Card(hole2Array[i].getText());
				final var ndx1 = ((12 - card1.value) * 4) + card1.suit;
				final var ndx2 = ((12 - card2.value) * 4) + card2.suit;
				cardArray[ndx1].setText(card1.toString());
				cardArray[ndx2].setText(card2.toString());
				location.put(card1.toString(), "cardArray");
				location.put(card2.toString(), "cardArray");
				hole1Array[i].setText(new StringBuilder().append("h").append(String.valueOf(i)).append("a").toString());
				hole2Array[i].setText(new StringBuilder().append("h").append(String.valueOf(i)).append("b").toString());
			}
		}
	}

	/*-**********************************************************************************************
	* This method will check that at least one hole card is there and that board cards are all 
	* there for the selected street.
	* Called just prior to execution 
	* Returns false if not OK,
	**************************************************************************************************/
	private static boolean checkHoleCardsAndBoardCards() {
		if (selectedStreet == FLOP && checkFlopCards()) {
			return false;
		}
		if (selectedStreet == TURN && checkTurnCard()) {
			return false;
		}
		if (selectedStreet == RIVER && checkRiverCard()) {
			return false;
		}
		return checkHoleCards();
	}

	/*-**********************************************************************************************
	* This method will check hole cards for valid
	* There must be at least one pair of hole cards.
	* All hole cards must be a valid pair ( 2 cards )
	* Returns false if invalid
	**************************************************************************************************/
	private static boolean checkHoleCards() {
		EvalData.street = PREFLOP;
		EvalData.lastStreet = PREFLOP;
		EvalData.simulation = true;
		for (var i = 0; i < 6; i++) {
			if (!"h".equals(hole1Array[i].getText().substring(0, 1))
					&& !"h".equals(hole2Array[i].getText().substring(0, 1))) {
				return true;
			}
		}
		if (!multipleHandsOption) {
			JOptionPane.showMessageDialog(null,
					"Please fill in at least one Hole Cards unless MultipleHands is selected ");
		}
		return false;
	}

	/*-  ******************************************************************************
	 * Clear the display fields
	 *  ****************************************************************************** */
	private static void clearDisplay() {
		for (var i = 0; i < 6; i++) {
			playArray[i].setText("");
			for (var j = 0; j < 5; j++) {
				handsArray[i][j].setText("");
			}
			for (var j = 0; j < 5; j++) {
				drawsArray[i][j].setText("");
			}
			drawArrayType[i].setText("");
			madeArrayType[i].setText("");
		}
	}

	/*-  ******************************************************************************
	 * Check to see if there are cards in Flop board
	 *  ****************************************************************************** */
	private static boolean checkFlopCards() {
		if (!("b".equals(boardArray[0].getText().substring(0, 1)) || "b".equals(boardArray[1].getText().substring(0, 1))
				|| "b".equals(boardArray[2].getText().substring(0, 1)))) {
			return true;
		}
		JOptionPane.showMessageDialog(null,
				new StringBuilder().append("Error, Please fill in Flop cards ").append(boardArray[0].getText())
						.append(" ").append(boardArray[1].getText()).append(" ").append(boardArray[2].getText())
						.toString());
		return false;
	}

	/*-  ******************************************************************************
	 * If a player has folded dim the hole cards
	 *  ****************************************************************************** */
	private static void holeCardsFolded() {
		for (var i = 0; i < PLAYERS; i++) {
			if (GameControl.playerFolded[i]) {
				hole1Array[i].setBackground(DIM);
				hole2Array[i].setBackground(DIM);
			}
		}
		panelHoleCards.repaint();
	}

	/*- ******************************************************************************************
	* This method will check seat 1 if the freezeSeat1Option is used,
	* Returns false if 
	********************************************************************************************/
	private static boolean checkSeat1() {
		final var sta = hole1Array[0].getText();
		final var stb = hole2Array[0].getText();
		var ca = new Card(sta);
		var cb = new Card(stb);
		if (!"h".equals(sta.substring(0, 1)) && !"h".equals(stb.substring(0, 1))) {
			if (ca.value < cb.value) {
				final var temp = ca;
				cb = ca;
				ca = temp;
			}
			hole1Array[0].setText(stb);
			hole2Array[0].setText(sta);
			location.put(stb, "h0a");
			location.put(sta, "h0b");
			EvalData.players[0].holeCard1 = ca;
			EvalData.players[0].holeCard2 = cb;
			Deck.setDeadCard(ca);
			Deck.setDeadCard(cb);
		} else {
			JOptionPane.showMessageDialog(null,
					"Freeze seat 1 option is selected but seat 1 cards are missing or invalid");
			return false;
		}
		return true;
	}

	/*-  ******************************************************************************
	 * Check to see if there is a river card in board
	 * Return true if OK
	 *  ****************************************************************************** */
	private static boolean checkRiverCard() {
		if (!"b".equals(boardArray[4].getText().substring(0, 1))) {
			return true;
		}
		JOptionPane.showMessageDialog(null, new StringBuilder().append("Error, Please fill in River card ").append(" ")
				.append(boardArray[4].getText()).toString());
		return false;
	}

	/*-**********************************************************************************************
	 * Sort board cards
	 * Required for correct results during evaluation - the methods that evaluate draws and made hands
	 * require that the hole cards and board cards be sorted.
	 * If random board sorting is not required.
	 *************************************************************************************************/
	private static boolean checkAndSortBoardCards() {
		final var sta = new String[6];
		final var ca = new Card[6];
		var cards = 0;
		// Get the cards and sort them
		if (selectedStreet == FLOP) {
			cards = 3;
			EvalData.street = FLOP;
		} else if (selectedStreet == TURN) {
			cards = 4;
			EvalData.street = TURN;
		} else if (selectedStreet == RIVER || selectedStreet == ALL_STREETS) {
			cards = 5;
			EvalData.street = RIVER;
		}
		Board.boardCount = cards;
		for (var i = 0; i < cards; i++) {
			sta[i] = boardArray[i].getText();
			if ("bo".equals(sta[i].substring(0, 2)) || sta[i].length() != 2) {
				JOptionPane.showMessageDialog(null, new StringBuilder().append("Error card ").append(String.valueOf(i))
						.append(" in the board is missing or invalid").toString());
				return false;
			}
			ca[i] = new Card(sta[i]);
			Board.board[i] = ca[i];
			Board.boardUnsorted[i] = ca[i];
		}
		SortCards.sortCardsValue(Board.board, 3);
		if (!multipleHandsOption) {
			for (var i = 0; i < cards; i++) {
				boardArray[i].setText(Board.board[i].toString());
				location.put(Board.board[i].toString(),
						new StringBuilder().append("ba").append(String.valueOf(i)).toString());
			}

			panelBoard.repaint();
		}
		return true;
	}

	/*-**********************************************************************************************
	 *  Set and sort hole cards
	 * Required for correct results during evaluation - the methods that evaluate draws and made hands
	 * require that the hole cards and board cards be sorted.
	 * Random hole cards required because some may have been manually selected
	 *************************************************************************************************/
	private static boolean checkAndSortHoleCards() {
		final var sta = new String[6];
		final var stb = new String[6];
		final var ca = new Card[6];
		final var cb = new Card[6];
		for (var i = 0; i < 6; i++) {
			GameControl.playerFolded[i] = true;
			sta[i] = hole1Array[i].getText();
			stb[i] = hole2Array[i].getText();
			if (!"h".equals(sta[i].substring(0, 1)) && !"h".equals(stb[i].substring(0, 1))) {
				ca[i] = new Card(sta[i]);
				cb[i] = new Card(stb[i]);
				if (ca[i].value < cb[i].value) {
					final var temp = cb[i];
					cb[i] = ca[i];
					ca[i] = temp;
					hole1Array[i].setText(stb[i]);
					hole2Array[i].setText(sta[i]);
					location.put(stb[i],
							new StringBuilder().append("h").append(String.valueOf(i)).append("a").toString());
					location.put(sta[i],
							new StringBuilder().append("h").append(String.valueOf(i)).append("b").toString());
				}
				GameControl.playerFolded[i] = false;
				panelCheckBoxesOptions.repaint();
			} else if (("h".equals(sta[i].substring(0, 1)) && !"h".equals(stb[i].substring(0, 1)))
					|| (!"h".equals(sta[i].substring(0, 1)) && "h".equals(stb[i].substring(0, 1)))) {
				JOptionPane.showMessageDialog(null, new StringBuilder().append("Error hole card for seat ")
						.append(String.valueOf(i)).append(" is not valid").toString());
				return false;
			}
		}
		panelCards.repaint();
		panelHoleCards.repaint();
		return true;
	}

	/*-  ******************************************************************************
	 * Check to see if there is a Turn card in board
	 *  ****************************************************************************** */
	private static boolean checkTurnCard() {
		if (!"b".equals(boardArray[3].getText().substring(0, 1))) {
			return true;
		}
		JOptionPane.showMessageDialog(null, new StringBuilder().append("Error, Please fill in Turn card ").append(" ")
				.append(boardArray[3].getText()).toString());
		return false;
	}

	/*-  ******************************************************************************
	 * Update display various things, position, stack, pot odds, odds,
	 *  ****************************************************************************** */
	private static void updateData() {
		for (var i = 0; i < 6; i++) {
			positionArray[i].setText(POSITION_ST2[GameControl.seatPos[i]]);
			if (EvalData.street == FLOP) {
				if (EvalData.players[i].outsFlop != 0) {
					outsFlopArray[i].setText(Format.format(EvalData.players[i].outsFlop));
					percentageFTTArray[i].setText(Format.formatPer(EvalData.players[i].percentageFTT));
					percentageFTRArray[i].setText(Format.formatPer(EvalData.players[i].percentageFTR));
				} else {
					outsFlopArray[i].setText("");
					percentageFTTArray[i].setText("");
					percentageFTRArray[i].setText("");
				}
			}
			if (EvalData.street == TURN) {
				if (EvalData.players[i].outsTurn != 0) {
					outsTurnArray[i].setText(Format.format(EvalData.players[i].outsTurn));
					percentageTTRArray[i].setText(Format.formatPer(EvalData.players[i].percentageTTR));
				} else {
					outsTurnArray[i].setText("");
					percentageTTRArray[i].setText("");
				}
			}
		}
		if (EvalData.street == RIVER || EvalData.street == ALL_STREETS) {
			showdown.setText("");
			showdown.setText("");
			StringBuilder sb = new StringBuilder("");
			for (int i = 0; i < 6; i++) {
				sb.append(Showdown.showdownPlayersSB[i]);
			}
			sb.append("\r\n)").append(Showdown.showdownResultSB);
			showdown.setText(sb.toString());
		}
		pot.setText(Format.format$(GameControl.potBD));
	}

	/*-  ******************************************************************************
	 * Update display for made hands and draws and Playability
	 *  ****************************************************************************** */
	private static void updateDrawsAndMade() {
		if (EvalData.street == FLOP) {
			updateMadeHandFlop();
		} else if (EvalData.street == TURN) {
			updateMadeHandTurn();
		} else {
			updateMadeHandRiver();
		}
		updateDrawHand();
		updatePlayability();
	}

	/*-  ******************************************************************************
	 * Update Draw hand
	 * Check the cards in every evalData.hand for each street.
	 * If not null then move cards from instance to hands JTextArea
	 *  ****************************************************************************** */
	private static void updateDrawHand() {
		if (selectedStreet == RIVER || selectedStreet == SHOWDOWN) {
			panelDraws.setVisible(false);
			panelDrawType.setVisible(false);
			return;
		}
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				if (selectedStreet == FLOP) {
					if (EvalData.players[i].typeOfDrawFlop == DRAW_NONE) {
						drawsArray[i][j].setForeground(BLACK);
					} else {
						drawsArray[i][j].setForeground(GOOD);
					}
					if (!GameControl.playerFolded[i]) {
						drawArrayType[i].setText(DRAW_ST[EvalData.players[i].typeOfDrawFlop]);
						if (EvalData.players[i].drawCardsFlop[j] != null) {
							drawsArray[i][j].setText(EvalData.players[i].drawCardsFlop[j].toString());
						}
					} else {
						drawsArray[i][j].setText("");
						drawArrayType[i].setText("");
					}
					drawArrayType[i].setFont(fStandard);
				} else {
					if (EvalData.players[i].typeOfDrawTurn == DRAW_NONE) {
						drawsArray[i][j].setForeground(BLACK);
					} else {
						drawsArray[i][j].setForeground(GOOD);
					}
					if (!GameControl.playerFolded[i]) {
						drawArrayType[i].setText(DRAW_ST[EvalData.players[i].typeOfMadeTurn]);
						if (EvalData.players[i].drawCardsTurn[j] != null) {
							drawsArray[i][j].setText(EvalData.players[i].drawCardsTurn[j].toString());
						}
					} else {
						drawsArray[i][j].setText("");
						drawArrayType[i].setText("");
					}
				}
				drawsArray[i][j].setFont(fCard);
			}
		}
		panelDraws.setVisible(true);
		panelDrawType.setVisible(true);
		panelDrawType.repaint();
		panelDraws.repaint();
	}

	/*-  ******************************************************************************
	 * Update Made hand Flop
	 * Check the cards in every evalData.hand for each street.
	 * If not null then move cards from instance to hands JTextArea
	 *  ****************************************************************************** */
	private static void updateMadeHandFlop() {
		for (var i = 0; i < 6; i++) {
			for (var j = 0; j < 5; j++) {
				handsArray[i][j].setFont(fCard);
				if (EvalData.players[i].typeOfMadeFlop == MADE_NONE) {
					handsArray[i][j].setForeground(BLACK);
				} else {
					handsArray[i][j].setForeground(GOOD);
				}
				if (!GameControl.playerFolded[i]) {
					madeArrayType[i].setText(MADE_ST[EvalData.players[i].typeOfMadeFlop]);
					if (EvalData.players[i].madeCardsFlop[j] != null) {
						handsArray[i][j].setText(EvalData.players[i].madeCardsFlop[j].toString());
					}
				} else {
					handsArray[i][j].setText("");
					madeArrayType[i].setText("");
				}
				handsArray[i][j].setFont(fCard);
			}
			madeArrayType[i].setFont(fStandard);
		}
		panelHands.repaint();
		panelMadeType.repaint();
		panelHands.setVisible(true);
		panelMadeType.setVisible(true);
	}

	/*-  ******************************************************************************
	 * Update Made hand Flop
	 * Check the cards in every evalData.hand for each street.
	 * If not null then move cards from instance to hands JTextArea
	 *  ****************************************************************************** */
	private static void updateMadeHandTurn() {
		for (var i = 0; i < 6; i++) {
			for (var j = 0; j < 5; j++) {
				handsArray[i][j].setFont(fCard);
				if (EvalData.players[i].typeOfMadeTurn == MADE_NONE) {
					handsArray[i][j].setForeground(BLACK);
				} else {
					handsArray[i][j].setForeground(GOOD);
				}
				if (!GameControl.playerFolded[i]) {
					madeArrayType[i].setText(MADE_ST[EvalData.players[i].typeOfMadeTurn]);
					if (EvalData.players[i].madeCardsTurn[j] != null) {
						handsArray[i][j].setText(EvalData.players[i].madeCardsTurn[j].toString());
					}
				} else {
					handsArray[i][j].setText("");
					madeArrayType[i].setText("");
				}
				handsArray[i][j].setFont(fCard);
			}
			madeArrayType[i].setFont(fStandard);
		}
		panelHands.repaint();
		panelMadeType.repaint();
		panelHands.setVisible(true);
		panelMadeType.setVisible(true);
	}

	private static void updateMadeHandRiver() {
		for (var i = 0; i < 6; i++) {
			for (var j = 0; j < 5; j++) {
				handsArray[i][j].setFont(fCard);
				if (EvalData.players[i].typeOfMadeRiver == MADE_NONE) {
					handsArray[i][j].setForeground(BLACK);
				} else {
					handsArray[i][j].setForeground(GOOD);
				}
				if (!GameControl.playerFolded[i]) {
					if (EvalData.players[i].madeCardsRiver[j] != null) {
						handsArray[i][j].setText(EvalData.players[i].madeCardsRiver[j].toString());
					}
				} else {
					handsArray[i][j].setText("");
				}
				handsArray[i][j].setFont(fCard);
			}
			if (EvalData.players[i].typeOfMadeRiver == MADE_NONE) {
				madeArrayType[i].setForeground(BLACK);
			} else {
				madeArrayType[i].setForeground(GOOD);
			}
			if (!GameControl.playerFolded[i]) {
				madeArrayType[i].setText(MADE_ST[EvalData.players[i].typeOfMadeRiver]);
			} else {
				madeArrayType[i].setText("");
			}
			madeArrayType[i].setFont(fStandard);
		}
		panelHands.repaint();
		panelMadeType.repaint();
		panelHands.setVisible(true);
		panelMadeType.setVisible(true);
	}

	/*-  ******************************************************************************
	 * Update Playability
	 *  ****************************************************************************** */
	private static void updatePlayability() {
		Card card1;
		Card card2;
		for (var i = 0; i < 6; i++) {
			if (hole1Array[i].getText().length() == 2 && hole2Array[i].getText().length() == 2) {
				card1 = new Card(hole1Array[i].getText());
				card2 = new Card(hole2Array[i].getText());
				final var ndx = HandRange.getArrayIndexCard(card1, card2);
				int pn = HandRange.getPlayabilityNumeric(ndx);
				pn = 169 - pn;
				playArray[i].setText(String.valueOf(pn));
				if (pn > 148) {
					playArray[i].setForeground(GOOD);
				} else {
					playArray[i].setForeground(BLACK);
				}
			} else {
				playArray[i].setText("");
				playArray[i].setForeground(BLACK);
			}
		}
		panelPlay.repaint();
	}

	private static int multiple = -1;

	/*-  ******************************************************************************
	 * Handle check boxes
	 * Check the status of boxes and set boolean variables 
	 *  ****************************************************************************** */
	private static void checkBoxes() {
		if (handHistoryReport.isSelected()) {
			handHistoryReportOption = true;
			EvalData.hh = true;
		} else {
			handHistoryReportOption = false;
			EvalData.hh = false;
		}
		if (drawMadePercentagesReport.isSelected()) {
			drawMadePercentagesOption = true;
		} else {
			drawMadePercentagesOption = false;
		}
		if (drawMadePercentagesCompareReport.isSelected()) {
			drawMadePercentagesCompareOption = true;
			preflopBothSimNoSimOption = true;
		} else {
			drawMadePercentagesCompareOption = false;
			preflopBothSimNoSimOption = false;
		}
		if (drawMadeWonAtShowdownReport.isSelected()) {
			drawMadeWonAtShowdownOption = true;
		} else {
			drawMadeWonAtShowdownOption = false;
		}
		if (drawMadeToWinnersReport.isSelected()) {
			drawMadeToWinnersReportOption = true;
		} else {
			drawMadeToWinnersReportOption = false;
		}
		if (boardWonAtShowdownReport.isSelected()) {
			boardWonAtShowdownOption = true;
		} else {
			boardWonAtShowdownOption = false;
		}
		if (drawMadeFreezeSeat1Report.isSelected()) {
			drawMadeFreezeSeat1ReportOption = true;
		} else {
			drawMadeFreezeSeat1ReportOption = false;
		}
		if (drawMadeFreezeSeat2Report.isSelected()) {
			drawMadeFreezeSeat2ReportOption = true;
		} else {
			drawMadeFreezeSeat2ReportOption = false;
		}
		if (drawMadeFreezeBoardReport.isSelected()) {
			drawMadeFreezeBoardReportOption = true;
		} else {
			drawMadeFreezeBoardReportOption = false;
		}

		if (multipleHands.isSelected()) {
			if (multiple == -1 || multiple == 2) {
				boardDealt = false;
				holeDealt = false;
				multipleOnOff(false);
				multipleHandsOption = true;
				preflopSimulation.setSelected(true);
				preflopSimulation.setVisible(true);
				panelRadioHands.setVisible(true);
				panelRadioFrozen.setVisible(true);
				panelCheckBoxesReports.setVisible(true);
				holeCards.setEnabled(false);
				boardCards.setEnabled(false);
				panelCheckBoxesFreezeReports.setVisible(true);
				panelCheckBoxesReports.setBounds(10, 500, 1700, 40);
				panelCheckBoxesFreezeReports.setBounds(10, 530, 1100, 40);
				panelRadioFrozen.setBounds(10, 570, 700, 40);
				panelRadioStreet.setBounds(10, 600, 400, 40);
				panelRadioHands.setBounds(10, 650, 1000, 30);
				panelCheckBoxesOptions.setBounds(10, 690, 400, 40);
				panelControl.setBounds(10, 740, 1000, 40);
				panelShowdownValue.setBounds(20, 790, 1630, 150);
				panelShowdown.setBounds(20, 950, 1630, 50);
				panelShowdownValue.setVisible(true);
				panelShowdown.setVisible(true);
				radioStreet[3].setVisible(true);
				radioStreet[3].setSelected(true);
				selectedStreet = ALL_STREETS;
				header.setText(header2);
				preflopSimulation.setSelected(true);
			}
			multiple = 1;
		} else {
			if (multiple == -1 || multiple == 1) {
				multipleOnOff(true);
				multipleHandsOption = false;
				preflopSimulationOption = false;
				preflopSimulation.setSelected(false);
				preflopSimulation.setVisible(false);
				panelRadioHands.setVisible(false);
				panelCheckBoxesReports.setVisible(false);
				panelCheckBoxesFreezeReports.setVisible(false);
				panelRadioFrozen.setVisible(false);
				holeCards.setEnabled(true);
				boardCards.setEnabled(true);
				panelRadioStreet.setBounds(10, 590, 400, 40);
				panelControl.setBounds(10, 640, 1000, 40);
				panelRadioHands.setBounds(10, 860, 1000, 30);
				panelCheckBoxesOptions.setBounds(10, 690, 400, 40);
				panelShowdownValue.setBounds(20, 740, 1630, 150);
				panelShowdown.setBounds(20, 900, 1630, 50);
				panelShowdownValue.setVisible(true);
				panelShowdown.setVisible(true);
				radioStreet[3].setVisible(false);
				radioStreet[0].setSelected(true);
				selectedStreet = FLOP;
				header.setText(header1);
			}
			multiple = 2;
		}
		if (preflopSimulation.isSelected()) {
			preflopSimulationOption = true;
			EvalData.preflopSimulation = true;
		} else {
			preflopSimulationOption = false;
			EvalData.preflopSimulation = false;
		}

		if (boardWonAtShowdownReport.isSelected()) {
			boardWonAtShowdownOption = true;
		} else {
			boardWonAtShowdownOption = false;
		}
		if (boardWonAtShowdownReport.isSelected()) {
			boardWonAtShowdownOption = true;
		} else {
			boardWonAtShowdownOption = false;
		}
		if (boardWonAtShowdownReport.isSelected()) {
			boardWonAtShowdownOption = true;
		} else {
			boardWonAtShowdownOption = false;
		}
		if (boardWonAtShowdownReport.isSelected()) {
			boardWonAtShowdownOption = true;
		} else {
			boardWonAtShowdownOption = false;
		}
	}

	/*-**********************************************************************************************
	* This method will setup the display for multiple hands on or off
	*************************************************************************************************/
	private static void multipleOnOff(boolean b) {
		multipleHandsOption = b;
		preflopSimulationOption = b;
		panelRadioHands.setVisible(b);
		panelCheckBoxesReports.setVisible(b);
		panelHands.setVisible(b);
		panelDraws.setVisible(b);
		panelDrawType.setVisible(b);
		panelMadeType.setVisible(b);
		panelBoardStatus.setVisible(b);
		panelHandStatus.setVisible(b);
		panelStatus.setVisible(b);
		panelPlay.setVisible(b);
		panelPosition.setVisible(b);
		panelOutsFlop.setVisible(b);
		panelOutsTurn.setVisible(b);
		panelOddsFTT.setVisible(b);
		panelOddsFTR.setVisible(b);
		panelOddsTTR.setVisible(b);
		panelPot.setVisible(b);
		panelShowdown.setVisible(b);
		panelShowdownValue.setVisible(b);
	}

	/*-**********************************************************************************************
	* This method will check that we are ready to run multiple hands.
	* If the freezeBoardCardsOption is selected then the board must have all 5 cards.
	* If the freezeSeat1Option is selected then Seat 1 must have cards. 
	* If the freezeHoleCardsOption is selected then at least one seat must have cards.
	*************************************************************************************************/
	private static boolean readyForMultiple() {
		if (freezeBoardCardsOption) {
			for (var i = 0; i < 5; i++) {
				if (boardArray[i].getText().length() != 2) {
					JOptionPane.showMessageDialog(null,
							"Please fill in all five board cards because the freeze board option is selected ");
					return false;
				}
			}
		}
		if (freezeSeat1and2HoleCardsOption
				&& (hole1Array[0].getText().length() != 2 || hole2Array[0].getText().length() != 2)) {
			JOptionPane.showMessageDialog(null,
					"Please fill Seat 1 hole cards cards because the freeze Seat 1 option is selected ");
			return false;
		}
		return true;
	}

	/*-  ******************************************************************************
	 * Update board status
	 *  ****************************************************************************** */
	private static void updateBoardStatus() {
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		if (EvalData.street == FLOP) {
			sb.append("Flop Board Indexes: ");
			sb.append(HML_FLOP_ST[EvalData.hmlIndexFlop] + " , ");
			sb.append(" SCBP ").append(SCBP_ST[EvalData.SCBPIndexFlop]).append(" , ").toString();
			sb.append(" Wet/Dry ").append(WET_DRY_ST[EvalData.wetDryIndexFlop]).append(" , ").toString();
			sb.append("Type of 1755 ").append(TYPE_OF_1755_ST[EvalData.typeOf1755IndexFlop]).append(" \r\n ");

		} else if (EvalData.street == TURN) {
			sb.append("Turn Board Indexes: ");
			sb.append(HML_TURN_ST[EvalData.hmlIndexTurn] + " \r\n");
		} else if (EvalData.street == RIVER) {
			sb.append("River Board Indexes: ");
			sb.append(HML_RIVER_ST[EvalData.hmlIndexRiver] + " \r\n ");
		}

		String st = sb.toString();
		st += Board.boardStatusSB.toString();
		boardStatus.setText(st);
		boardStatus.repaint();
		panelBoardStatus.repaint();
		for (int i = 0; i < 6; i++) {
			sb2.append(EvalData.players[i].handStatusSB);
		}
		String st2 = sb2.toString();
		handStatus.setText(st2);
		handStatus.repaint();
		panelHandStatus.repaint();
	}

	/*-  ******************************************************************************
	* Implement the actionPerformed method of the ActionListener interface
	* Responds to any button click.
	* First we determine if this is a click on cardArray.
	* If it is, then it is  first click and all that we do is to save he card in firstClick
	* and exit waiting for second click.
	*
	* If there is a click on a hole card or board card that is the first click it is ignored.
	*
	* If it is not a click on cardArray then it may be a second click, or a click from
	* a radio button or control button.
	 *  ****************************************************************************** */
	private static class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			final var action = e.getActionCommand();
			if (action.isEmpty()) {
				return;
			}
			if (action.length() == 2 && !action.equals("10")) {
				clickOnCard(action);
				return;
			}
			if (action.length() == 3 && !action.equals("Run") && !action.equals("All")) {
				clickEmptyLocation(action);
				return;
			}
			clearClicks();
			checkBoxes();
			checkRadio();
			buttonStreet(action);
			buttonCommand(action);
		}

		/*-  ******************************************************************************
		 * Handle button click actions from radio buttons streets
		 * Set selectedStreet.
		 *  ****************************************************************************** */
		private static void buttonStreet(String action) {
			final var st = action;
			if (radioStreet[0].isSelected()) {
				selectedStreet = FLOP;
			}
			if (radioStreet[1].isSelected()) {
				selectedStreet = TURN;
			}
			if (radioStreet[2].isSelected()) {
				selectedStreet = RIVER;
			}
			if (radioStreet[3].isSelected()) {
				selectedStreet = ALL_STREETS;
			}

			if (selectedStreet == RIVER || selectedStreet == ALL_STREETS) {
				panelShowdown.setVisible(true);
				panelShowdownValue.setVisible(true);
			} else {
				panelShowdown.setVisible(false);
				panelShowdownValue.setVisible(false);
			}
		}

		/*-  ******************************************************************************
		 * Handle Command buttons
		 *  ****************************************************************************** */
		private static void buttonCommand(String action) {
			final var st = action;
			switch (st) {
			case "Run " -> run();
			case "Deal Hole Cards" -> dealHoleOrDealBoard(1);
			case "Deal Board Cards" -> dealHoleOrDealBoard(2);
			case "Help" -> JOptionPane.showMessageDialog(null, helpString);
			}

		}

		/*-  ******************************************************************************
		 * Random Deal Hole Cards or Deal Board Cards.
		 * Called to determine what to do about shuffling the deck and, returning cards
		 * from the board, returning hole cards, save and restore board cards, or 
		 * save and restore hole cards.
		 * 
		 * deal hole or deal board
		 * neither dealt or set - save nothing just shuffle and deal
		 * 
		 * deal hole
		 * board dealt or set, hole not dealt or set - no shuffle deal hole cards, return hole cards, 
		 * both dealt or set  - no shuffle, deal board cards,  shuffle, return 
		 * 
		 * deal board
		 * hole dealt or set, board not dealt or set - no shuffle deal board cards, return board cards 
		 * both dealt or set  - no shuffle, deal board cards,  shuffle, return  
		 * 
		 * Arg0 - 1 Deal random Hole Cards, 2 Deal Random Board Cards
		 *  ****************************************************************************** */
		private static void dealHoleOrDealBoard(int which) {
			// Deal Hole cards
			if (which == 1) {
				if (!boardDealt && !userSetBoardCards && !holeDealt && !userSetHoleCards) {
					Deck.shuffle();
					dealRandomHoleCards();
				} else if (boardDealt || userSetBoardCards) {
					if (holeDealt || userSetHoleCards) {
						// Both dealt or set
						Deck.shuffle();
						returnHoleCards(); // Return to cardArray
						restoreBoardCardsAfterShuffle(); // Set to remove from deck and avoid duplicates
						dealRandomHoleCards();
					} else {
						dealRandomHoleCards();
					}
				}
			} else {
				// Deal board cards
				if (!boardDealt && !userSetBoardCards && !holeDealt && !userSetHoleCards) {
					Deck.shuffle();
					dealRandomBoardCards();
				} else if (holeDealt || userSetHoleCards) {
					if (boardDealt || userSetBoardCards) {
						// Both dealt or set
						Deck.shuffle();
						returnBoardCards(); // Return to card array
						restoreHoleCardsAfterShuffle(); // Set to remove from deck and avoid duplicates
						dealRandomBoardCards();
					}
				} else {
					dealRandomBoardCards();
				}
			}
			if (holeDealt && boardDealt) {
				run();
			}
		}

		/*-  ******************************************************************************
		 * Do random hole cards
		 * Called when random hole cards button is clicked.
		 * If there are already cards in boardArray they are removed and returned to
		 * cardArray and if also in board ??
		 *
		 * If holexArray is empty then Evaluate is called to deal cards.
		 * The dealt cards in holeCards are copied to holexArray and removed
		 * from cardArray.
		 * Returns false if invalid cards in seat i if seat 1 is frozen
		 *  ****************************************************************************** */
		private static void dealRandomHoleCards() {
			holeDealt = true;
			userSetHoleCards = false;
			for (var i = 0; i < PLAYERS; i++) {
				Dealer.dealHoleCards(i);
				var card1 = EvalData.players[i].holeCard1;
				var card2 = EvalData.players[i].holeCard2;
				final var cardSt1 = card1.toString();
				final var cardSt2 = card2.toString();
				final var ndx1 = ((12 - card1.value) * 4) + card1.suit;
				final var ndx2 = ((12 - card2.value) * 4) + card2.suit;
				cardArray[ndx1].setText("");
				cardArray[ndx2].setText("");
				hole1Array[i].setText(cardSt1);
				hole2Array[i].setText(cardSt2);
				hole1Array[i].setFont(fCard);
				hole2Array[i].setFont(fCard);
				updatePlayability();
				location.put(cardSt1, new StringBuilder().append("h").append(String.valueOf(i)).append("a").toString());
				location.put(cardSt2, new StringBuilder().append("h").append(String.valueOf(i)).append("b").toString());
			}
			panelCards.repaint();
			panelHoleCards.repaint();
		}

		/*-  ******************************************************************************
		* Deal a random board for street selected
		 *  ****************************************************************************** */
		private static void dealRandomBoardCards() {
			boardDealt = true;
			userSetBoardCards = false;
			if (selectedStreet == FLOP) {
				Dealer.dealFlop();
				EvalData.street = FLOP;
				Board.boardCount = 3;
			} else if (selectedStreet == TURN) {
				Dealer.dealFlop();
				Dealer.dealTurnCard();
				EvalData.street = TURN;
				Board.boardCount = 4;
			} else if (selectedStreet == RIVER) {
				Dealer.dealFlop();
				Dealer.dealTurnCard();
				Dealer.dealRiverCard();
				EvalData.street = RIVER;
				Board.boardCount = 5;
			}
			// remove these cards from card array
			for (var i = 0; i < Board.boardCount; i++) {
				final var card1 = Board.board[i];
				final var cardSt = card1.toString();
				final var ndx = ((12 - card1.value) * 4) + card1.suit;
				cardArray[ndx].setText("");
				boardArray[i].setText(cardSt);
				boardArray[i].setFont(fCard);
				location.put(cardSt, "bo" + String.valueOf(i));
			}
			panelCards.repaint();
			panelBoard.repaint();
			updateBoardStatus();
		}

		/*-  ******************************************************************************
		 * The holeCardArray cards we will keep after the deck is shuffled
		 * but we need to restore dead cards in Deck and restore  
		 * Restore the hole1Array and hole2Array after shuffle
		 *  ****************************************************************************** */
		private static void restoreHoleCardsAfterShuffle() {
			Card card1 = null;
			Card card2 = null;
			for (var i = 0; i < 6; i++) {
				card1 = null;
				card2 = null;
				if (hole1Array[0].getText().length() == 2 && hole2Array[0].getText().length() == 2) {
					card1 = new Card(hole1Array[0].getText());
					card2 = new Card(hole2Array[0].getText());
					Dealer.setHoleCards(i, card1, card2);
				}
			}
		}

		/*-  **************************************************************************
		 * The boardArray cards we will keep after the deck is shuffled
		 * but we need to restore dead cards in Deck and restore 
		****************************************************************************** */
		private static void restoreBoardCardsAfterShuffle() {
			Card card1 = null;
			Card card2 = null;
			Card card3 = null;
			Card card4 = null;
			Card card5 = null;
			if (boardArray[0].getText().length() == 2 && boardArray[1].getText().length() == 2
					&& boardArray[2].getText().length() == 2) {
				card1 = new Card(boardArray[0].getText());
				card2 = new Card(boardArray[1].getText());
				card3 = new Card(boardArray[2].getText());
				Dealer.setFlopCards(card1, card2, card3);
			}
			if (boardArray[3].getText().length() == 2) {
				card4 = new Card(boardArray[3].getText());
				Dealer.setTurnCard(card4);
			}
			if (boardArray[4].getText().length() == 2) {
				card5 = new Card(boardArray[4].getText());
				Dealer.setRiverCard(card5);
			}
		}
	}

	/*-  ******************************************************************************
	 * Handle radio buttons
	 * Check the status of boxes and set boolean variables 
	 *  ****************************************************************************** */
	private static void checkRadio() {
		if (freezeSeat1and2HoleCards.isSelected()) {
			freezeSeat1and2HoleCardsOption = true;
			EvalData.freezeSeat1 = true;
		} else {
			freezeSeat1and2HoleCardsOption = false;
			EvalData.freezeSeat1 = false;
			EvalData.freezeSeat2 = false;
		}
		if (freezeBoardCards.isSelected()) {
			freezeBoardCardsOption = true;
			EvalData.freezeBoard = true;
		} else {
			freezeBoardCardsOption = false;
			EvalData.freezeBoard = false;
		}
		if (freezeNone.isSelected()) {
			freezeNoneOption = true;
		} else {
			freezeNoneOption = false;
		}

		int oldNum = numberToPlay;
		if (ten.isSelected()) {
			numberToPlay = 10;
		} else if (hundred.isSelected()) {
			numberToPlay = 100;
		} else if (thousand.isSelected()) {
			numberToPlay = 1000;
		} else if (tenThousand.isSelected()) {
			numberToPlay = 10000;
		} else if (hundredThousand.isSelected()) {
			numberToPlay = 100000;
		} else if (million.isSelected()) {
			numberToPlay = 1000000;
		} else if (tenMillion.isSelected()) {
			numberToPlay = 10000000;
		} else if (hundredMillion.isSelected()) {
			numberToPlay = 100000000;
		}
		if (numberToPlay != oldNum && numberToPlay >= 1000000) {
			Popup.textDisplayPopup("Notification", "This may take several minutes", 2, 50, 800,
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/*-  ******************************************************************************
	 * Click on a Card not an empty location
	 * If previous click was also a card then we may be moving a card to a location
	 * that already has a card.
	 *  ****************************************************************************** */
	private static void clickOnCard(String action) {
		var st = "";
		final var loc = location.get(action);
		if (loc == null) {
			Crash.log("WTF Program bug location returned is null");
			return;
		}
		if ("".equals(loc)) {
			Logger.log("//ERROR location HashMap returned null " + action);
			Crash.log("//Program bug ");
		}
		if (!"cardArray".equals(loc)) {
			if (!"".equals(fromClicked)) {
				st = loc.substring(0, 1);
				switch (st) {
				case "h" -> {
					buttonHoleClickHasCard(action, loc);
					clearClicks();
					return;
				}
				case "b" -> {
					buttonBoardClickHasCard(action, loc);
					clearClicks();
					return;
				}
				default -> {
					Logger.log("//ERROR location HashMap returned impossible location " + loc);
					Crash.log("//Program bug ");
				}
				}
			}
		} else {
			click1 = true;
			fromClicked = action;
			return;
		}
	}

	/*-  ******************************************************************************
	 * Click empty location
	 *  ****************************************************************************** */
	private static void clickEmptyLocation(String action) {
		if ("bo".equals(action.substring(0, 2))) {
			if (click1) {
				buttonBoardClickEmpty(action);
			} else {
				clearClicks();
			}
			return;
		}
		if ("h".equals(action.substring(0, 1))) {
			if (click1) {
				buttonHoleClickEmpty(action);
			} else {
				clearClicks();
			}
		}
	}

	/*-  ******************************************************************************
	* Handle button click actions
	 * There have been 2 clicks, a from click followed by a to click.
	`* The location is empty because the action length was 2 and started witb "b".
	 * We just add the from card and update the location HashMap.
	 *
	 * Arg0 -  Action
	 *  ****************************************************************************** */
	private static void buttonBoardClickEmpty(String action) {
		userSetBoardCards = true;
		boardDealt = false;
		final var card = new Card(fromClicked);
		final var ndx = ((12 - card.value) * 4) + card.suit;
		final var stX = action.substring(2);
		final var x = Integer.parseInt(stX);
		cardArray[ndx].setText("");
		boardArray[x].setText(fromClicked);
		boardArray[x].setFont(fCard);
		location.put(fromClicked, action);
		fromClicked = "";
		panelCards.repaint();
		panelBoard.repaint();
	}

	/*-  ******************************************************************************
	* Handle button click actions
	* There have been 2 clicks, a from click followed by a to click.
	* The second click had a length of 3 so it must be in the form hxa or hxb.
	* On the first click the card was saved in from clicked.
	* We place the saved card in that location and update the location HashMap.
	*
	* Arg0 -  action
	*  ****************************************************************************** */
	private static void buttonHoleClickEmpty(String action) {
		userSetHoleCards = true;
		holeDealt = false;
		hole2to6Dealt = false;
		final var loc = location.get(fromClicked);
		if (loc == null || "".equals(loc)) {
			Logger.log(new StringBuilder().append("//ERROR buttonHoleClick() card not found in HashMap ")
					.append(fromClicked).append(" ").append(action).append(" ").append(loc).toString());
			Crash.log("program bug ");
		}
		final var stX = action.substring(1, 2);
		final var stAB = action.substring(2);
		final var x = Integer.parseInt(stX);
		// Location is empty
		final var card = new Card(fromClicked);
		final var ndx = ((12 - card.value) * 4) + card.suit;
		cardArray[ndx].setText("");
		if ("a".equals(stAB)) {
			hole1Array[x].setText(fromClicked);
			hole1Array[x].setFont(fCard);
		} else {
			hole2Array[x].setText(fromClicked);
			hole2Array[x].setFont(fCard);
		}
		location.put(fromClicked, action);
		fromClicked = "";
		click1 = false;
		Deck.clearDeadCards();
		panelCards.repaint();
		panelHoleCards.repaint();
	}

	/*-  ******************************************************************************
	* Handle button click actions
	* There have been 2 clicks, a from click followed by a to click.
	* The second click was a card and the location HashMap placed it here.
	* On the first click the card was saved in from clicked.
	* Things are a little more complicated.
	* First we must return the card in hole1Array or hole2Array to cardArray and
	* update the location HashMap.
	* Then we place the card in fromClicked into hole1Array or hole2 array and
	* update location HashMap fot this new location.
	*
	* Arg0 -  card clicked on ( in holexArray )
	* Arg1 - location from HashMap
	*  ****************************************************************************** */
	private static void buttonHoleClickHasCard(String card, String loc) {
		var stX = "";
		int x = -1;
		Card c = null;
		// Move old card back to cardArray
		c = new Card(card);
		int ndx = ((12 - c.value) * 4) + c.suit;
		cardArray[ndx].setText(card);
		location.put(card, "cardArray");
		// Remove new card from carArray
		c = new Card(fromClicked);
		ndx = ((12 - c.value) * 4) + c.suit;
		cardArray[ndx].setText("");

		// Calculate new location in Hole1Array or Hole2Array
		stX = loc.substring(1, 2);
		x = Integer.parseInt(stX);
		if (x == 0) {
			if (!"".equals(fromClicked)) {
				hole1Array[x].setText(fromClicked);
				hole1Array[x].setFont(fCard);
				location.put(fromClicked, loc);
			} else {
				hole1Array[x].setText(loc);
				hole1Array[x].setFont(fTiny);
			}
		} else {
			if (!"".equals(fromClicked)) {
				hole2Array[x].setText(fromClicked);
				hole2Array[x].setFont(fCard);
				location.put(fromClicked, loc);
			} else {
				hole2Array[x].setText(loc);
				hole2Array[x].setFont(fTiny);
			}
		}
		fromClicked = "";
		Deck.clearDeadCards();
		panelCards.repaint();
		panelHoleCards.repaint();
	}

	/*-  ******************************************************************************
	* Handle button click actions
	* There have been 2 clicks, a from click followed by a to click.
	* The second click was a card and the location HashMap placed it here.
	* On the first click the card was saved in from clicked.
	* Things are a little more complicated.
	* First we must return the card in boardArray to cardArray and
	* update the location HashMap.
	* Then we place the card in fromClicked into boardArray and
	* update location HashMap fot this new location.
	*
	* Arg0 -  card clicked on ( in holexArray )
	* Arg1 - location from HashMap
	*  ****************************************************************************** */
	private static void buttonBoardClickHasCard(String card, String loc) {
		Card c = null;
		// Move old card back to cardArray
		c = new Card(card);
		int ndx = ((12 - c.value) * 4) + c.suit;
		cardArray[ndx].setText(card);
		location.put(card, "cardArray");
		// Remove new card from boardArray
		if (fromClicked.equals("")) {
			// No new card, just moving it back
			final var stX = loc.substring(2);
			final var x = Integer.parseInt(stX);
			boardArray[x].setText(loc);
			boardArray[x].setFont(fTiny);
		} else {
			c = new Card(fromClicked);
			ndx = ((12 - c.value) * 4) + c.suit;
			cardArray[ndx].setText("");
			final var stX = loc.substring(2);
			final var x = Integer.parseInt(stX);
			boardArray[x].setText(fromClicked);
			boardArray[x].setFont(fCard);
			location.put(fromClicked, loc);
		}
		fromClicked = "";
		Deck.clearDeadCards();
		panelCards.repaint();
		panelBoard.repaint();
	}

	/*-  ******************************************************************************
	 * Clear all of the click data
	 * Happens when a control button, radio button, or anything that is not a card location.
	 *  ****************************************************************************** */
	private static void clearClicks() {
		click1 = false;
		fromClicked = "";
	}

	/*-**********************************************************************************************
	* This method will
	*************************************************************************************************/
	static void showElapsedTime(String st) {
		showdown.setText(st);
		panelShowdown.setVisible(true);
		panelShowdown.repaint();
	}
}
