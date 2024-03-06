package holdemevaluator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class GUIAnalyzeIndexArrays extends JFrame implements ActionListener, Constants {

	/*-  **************************************************************************
	* This class is used to analyze thousands or millions of simulated hands.
	* Using a monte-carlo methodology determine characteristics of a Hold'em 
	* 6-max no limit game. 
	* Specifically to find ways to characterize a Flop, such as HML ( High Medium Low )
	* which results in only 10 Flop types. A million or more hands are run, and for 
	* each hand run the draws and made hands and other data is analyzed and collected
	* in arrays which will be analyzed after a run is completed. 
	* We analyze each street and Showdown.
	* 
	* This is not a final implementation. We will be experimenting continuously 
	* trying to find ways to characterize a Flop that will result in  information on
	* how best to play a hand. 
	* For example for a Flop type:
	*   It may result in a high percentage of made hands and we do not have a made hand.
	*   It may result in a low percentage of made hands and we hve have a made hand.
	*   It may result in a high percentage of strong draws.
	*   It may result in a low percentage of strong draws.
	*   It may result in a a high percentage of times that the made hand is the winning
	*   hand at Showdown.
	*   And many more.
	*
	* @author PEAK_
	***************************************************************************** */

	private static final Color CONTROL = GREEN;

	private static JPanel panelRadioHands = new JPanel();

	private static JPanel mainPanel;

	private static JPanel panelCheckBoxesSimulation;

	private static JPanel panelCheckBoxesAll;

	private static JPanel panelCheckBoxesFlop;

	private static JPanel panelCheckBoxesTurn;

	private static JPanel panelControl;

	private static JPanel panelStatus;

	private static final JButton run = new JButton("Run");

	private static final JButton exit = new JButton("Exit");

	private static final JButton help = new JButton("Help");

	private static final JRadioButton hundred = new JRadioButton("100");

	private static final JRadioButton thousand = new JRadioButton("1,000");

	private static final JRadioButton tenThousand = new JRadioButton("10,000");

	private static final JRadioButton hundredThousand = new JRadioButton("100,000");

	private static final JRadioButton million = new JRadioButton("1,000,000");

	private static final JRadioButton tenMillion = new JRadioButton("10,000,000");

	private static final JRadioButton hundredMillion = new JRadioButton("100,000,000");

	private static final JTextField status = new JTextField(10);

	private static final JCheckBox crash = new JCheckBox("Crash on severe error");
	private static final JPanel panelCheck = new JPanel();

	private static boolean quit = false;

	private static final Font fs = new Font("Dialog", Font.PLAIN, 12);

	private static final Font fc = new Font("Dialog", Font.PLAIN, 12);

	static int statusUpdate = 0;

	// @formatter:off
   private static final String helpString = new StringBuilder().append("Welcome to the Hold'emEvaluator project GUI.\r\n")
            .append("This GUI runs thousands or millions of hands in order to obtain data used in \r\n")
            .append("evaluating how different indexes relate to the draws, made hands and best hand at Showdown.\r\n")
            .append("We analyze thousands or millions of simulated hands\r\n")
            .append("Using a monte-carlo methodology determine characteristics of a Hold'em \r\n")
            .append("6-max no limit game. \r\n")
            .append("Specifically to find ways to characterize a Flop, such as HML ( High Medium Low )\r\n ")
            .append("which results in only 10 Flop types. A million or more hands are run, and for \r\n")
            .append("each hand run the draws and made hands and other data is analyzed and collected\r\n ")
            .append("in arrays which will be analyzed after a run is completed. \r\n")
            .append(" We analyze each street and Showdown.\r\n")
            .append("This is not a final implementation. We will be experimenting continuously \r\n ")
            .append("trying to find ways to characterize a Flop that will result in  information on\r\n")
            .append(" how best to play a hand.  \r\n")
             .append("For example for a Flop type:\r\n")
            .append(" It may result in a high percentage of made hands and we do not have a made hand.\r\n")
            .append(" It may result in a low percentage of made hands and we hve have a made hand.\r\n")
            .append(" It may result in a high percentage of strong draws.\r\n ")
            .append(" It may result in a low percentage of strong draws.\r\n")
            .append(" It may result in a a high percentage of times that the made hand is the winning \r\n")
            .append(" hand at Showdown.\r\n")
            .append("  And many more.\r\n\r\n")
            .append("\r\n ").toString();
    // @formatter:on

	/*- ******************************************************************************************
	* Flop Draw Made Showdown
	********************************************************************************************/
	// Board
	private static JPanel panelCheckBoxesBoardFlop;

	private static final JCheckBox preflopSimulation = new JCheckBox("Preflop Simulator");

	private static final JCheckBox boardDrawFlop = new JCheckBox("Draw");

	private static final JCheckBox boardMadeFlop = new JCheckBox("Made Hand");

	private static final JCheckBox boardCombinedFlop = new JCheckBox("Combined");

	private static final JCheckBox boardShowdownFlop = new JCheckBox("Showdown");

	// Board Suited and Connected onlyF
	private static JPanel panelCheckBoxesBoardSGFlop;

	private static final JCheckBox boardSGDrawFlop = new JCheckBox("Draw");

	private static final JCheckBox boardSGMadeFlop = new JCheckBox("Made Hand");

	private static final JCheckBox boardSGCombinedFlop = new JCheckBox("Combined");

	private static final JCheckBox boardSGShowdownFlop = new JCheckBox("Showdown");

	// HML
	private static JPanel panelCheckBoxesHMLFlop;

	private static final JCheckBox hmlDrawFlop = new JCheckBox("Draw");

	private static final JCheckBox hmlMadeFlop = new JCheckBox("Made Hand");

	private static final JCheckBox hmlCombinedFlop = new JCheckBox("Combined");

	private static final JCheckBox hmlShowdownFlop = new JCheckBox("Showdown");
	// WetDry
	private static JPanel panelCheckBoxesWetDryFlop;

	private static final JCheckBox wetDryDrawFlop = new JCheckBox("Draw");

	private static final JCheckBox wetDryMadeFlop = new JCheckBox("Made Hand");

	private static final JCheckBox wetDryCombinedFlop = new JCheckBox("Combined");

	private static final JCheckBox wetDryShowdownFlop = new JCheckBox("Showdown");

	// TypeOf1755
	private static JPanel panelCheckBoxesTypeOf1755Flop;

	private static final JCheckBox typeOf1755DrawFlop = new JCheckBox("Draw");

	private static final JCheckBox typeOf1755MadeFlop = new JCheckBox("Made Hand");

	private static final JCheckBox typeOf1755CombinedFlop = new JCheckBox("Combined");

	private static final JCheckBox typeOf1755ShowdownFlop = new JCheckBox("Showdown");

	// SCPB
	private static JPanel panelCheckBoxesSCBPFlop;

	private static final JCheckBox SCBPDrawFlop = new JCheckBox("Draw");

	private static final JCheckBox SCBPMadeFlop = new JCheckBox("Made Hand");

	private static final JCheckBox SCBPCombinedFlop = new JCheckBox("Combined");

	private static final JCheckBox SCBPShowdownFlop = new JCheckBox("Showdown");

	/*- ******************************************************************************************
	* Flop Draw Made  Win
	********************************************************************************************/
	// HML Win
	private static JPanel panelCheckBoxesHMLFlopWin;

	private static final JCheckBox hmlDrawFlopWin = new JCheckBox("Draw");

	private static final JCheckBox hmlMadeFlopWin = new JCheckBox("Made Hand");

	private static final JCheckBox hmlCombinedFlopWin = new JCheckBox("Combined");

	// WetDry Win
	private static JPanel panelCheckBoxesWetDryFlopWin;

	private static final JCheckBox wetDryDrawFlopWin = new JCheckBox("Wet Dry Draw");

	private static final JCheckBox wetDryMadeFlopWin = new JCheckBox("Wet Dry Made Hand");

	private static final JCheckBox wetDryCombinedFlopWin = new JCheckBox("Wet Dry Combined");

	// TypeOf1755 Win
	private static JPanel panelCheckBoxesTypeOf1755FlopWin;

	private static final JCheckBox typeOf1755DrawFlopWin = new JCheckBox("Draw Win");

	private static final JCheckBox typeOf1755MadeFlopWin = new JCheckBox("Made Hand Win");

	private static final JCheckBox typeOf1755CombinedFlopWin = new JCheckBox("Combined Win");

	// SCPB Win
	private static JPanel panelCheckBoxesSCBPFlopWin;

	private static final JCheckBox SCBPDrawFlopWin = new JCheckBox("SCBP Flop Draw Win");

	private static final JCheckBox SCBPMadeFlopWin = new JCheckBox("SCBP Flop Made Hand Win");

	private static final JCheckBox SCBPCombinedFlopWin = new JCheckBox("SCBP Flop Combined Win");

	/*- ******************************************************************************************
	* Turn Draw Made Showdown
	********************************************************************************************/
	// Board
	private static JPanel panelCheckBoxesBoardTurn;

	private static final JCheckBox boardDrawTurn = new JCheckBox("Draw");

	private static final JCheckBox boardMadeTurn = new JCheckBox("Made Hand");

	private static final JCheckBox boardCombinedTurn = new JCheckBox("Combined");

	private static final JCheckBox boardShowdownTurn = new JCheckBox("Showdown");

	// HML
	private static JPanel panelCheckBoxesHMLTurn;

	private static final JCheckBox hmlDrawTurn = new JCheckBox("Draw");

	private static final JCheckBox hmlMadeTurn = new JCheckBox("Made Hand");

	private static final JCheckBox hmlCombinedTurn = new JCheckBox("Combined");

	private static final JCheckBox hmlShowdownTurn = new JCheckBox("Showdown");
	// WetDry
	private static JPanel panelCheckBoxesWetDryTurn;

	private static final JCheckBox wetDryDrawTurn = new JCheckBox("Draw");

	private static final JCheckBox wetDryMadeTurn = new JCheckBox("Made Hand");

	private static final JCheckBox wetDryCombinedTurn = new JCheckBox("Combined");

	private static final JCheckBox wetDryShowdownTurn = new JCheckBox("Showdown");

	// TypeOf1755
	private static JPanel panelCheckBoxesTypeOf1755Turn;

	private static final JCheckBox typeOf1755DrawTurn = new JCheckBox("Draw");

	private static final JCheckBox typeOf1755MadeTurn = new JCheckBox("Made Hand");

	private static final JCheckBox typeOf1755CombinedTurn = new JCheckBox("Combined");

	private static final JCheckBox typeOf1755ShowdownTurn = new JCheckBox("Showdown");

	// SCPB
	private static JPanel panelCheckBoxesSCBPTurn;

	private static final JCheckBox SCBPDrawTurn = new JCheckBox("Draw");

	private static final JCheckBox SCBPMadeTurn = new JCheckBox("Made Hand");

	private static final JCheckBox SCBPCombinedTurn = new JCheckBox("Combined");

	private static final JCheckBox SCBPShowdownTurn = new JCheckBox("Showdown");

	/*- ******************************************************************************************
	* Turn Draw Made  Win
	********************************************************************************************/
	// HML Win
	private static JPanel panelCheckBoxesHMLTurnWin;

	private static final JCheckBox hmlDrawTurnWin = new JCheckBox("Draw");

	private static final JCheckBox hmlMadeTurnWin = new JCheckBox("Made Hand");

	private static final JCheckBox hmlCombinedTurnWin = new JCheckBox("Combined");

	// WetDry Win
	private static JPanel panelCheckBoxesWetDryTurnWin;

	private static final JCheckBox wetDryDrawTurnWin = new JCheckBox("Wet Dry Draw");

	private static final JCheckBox wetDryMadeTurnWin = new JCheckBox("Wet Dry Made Hand");

	private static final JCheckBox wetDryCombinedTurnWin = new JCheckBox("Wet Dry Combined");

	// TypeOf1755 Win
	private static JPanel panelCheckBoxesTypeOf1755TurnWin;

	private static final JCheckBox typeOf1755DrawTurnWin = new JCheckBox("Draw Win");

	private static final JCheckBox typeOf1755MadeTurnWin = new JCheckBox("Made Hand Win");

	private static final JCheckBox typeOf1755CombinedTurnWin = new JCheckBox("Combined Win");

	// SCPB Win
	private static JPanel panelCheckBoxesSCBPTurnWin;

	private static final JCheckBox SCBPDrawTurnWin = new JCheckBox("SCBP Turn Draw Win");

	private static final JCheckBox SCBPMadeTurnWin = new JCheckBox("SCBP Turn Made Hand Win");

	private static final JCheckBox SCBPCombinedTurnWin = new JCheckBox("SCBP Turn Combined Win");

	private static JPanel panelTemp;
	private static final JTextField temp = new JTextField(
			"NOTE: Only 3 panels are active in this release - Will add in future releases");

	private static final JFrame frame = new JFrame("  ");

	/*- *****************************************************************************************
	* Colors
	*******************************************************************************************/
	private static final Color colorA = TAN;
	private static final Color colorB = TAN;
	private static final Color colorC = TAN;
	private static final Color colorD = TAN;
	private static final Color colorE = TAN;
	private static final Color colorF = TAN;
	private static final Color colorG = TAN;
	private static final Color colorH = WHEAT;
	private static final Color colorI = TAN;
	private static final Color colorJ = TAN;
	private static final Color colorK = TAN;

	/*- **************************************************************************** 
	* Main
	*******************************************************************************/
	public static void main(String[] args) {
		if (!ValidInstallation.checkValid()) {
			return;
		}
		myFrame();
	}

	/*- **************************************************************************** 
	* Frame
	*******************************************************************************/
	public static void myFrame() {
		final var run = new JButton("Run");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(10, 10);
		frame.setMinimumSize(new Dimension(1600, 940));
		frame.setLayout(new FlowLayout());

		frame.setTitle("Evaluator   ");
		/// Set up the main panel
		mainPanel = new JPanel(null);
		mainPanel.setPreferredSize(new Dimension(1100, 800));
		mainPanel.setBackground(CYAN);

		panelRadioHands = new JPanel(new GridLayout(1, 6));
		panelRadioHands.setBounds(100, 10, 900, 50);
		panelRadioHands.setBackground(colorH);
		final var handsBox = Box.createHorizontalBox();
		final var handsGroup = new ButtonGroup();
		final var radioHandsBorder = BorderFactory.createTitledBorder("Number of Hands to Play");
		panelRadioHands.setBorder(radioHandsBorder);
		handsBox.add(panelRadioHands);
		mainPanel.add(panelRadioHands);

		panelCheckBoxesSimulation = new JPanel(new GridLayout(1, 1));
		panelCheckBoxesSimulation.setBounds(100, 75, 900, 40);
		panelCheckBoxesSimulation.setFont(fs);
		panelCheckBoxesSimulation.setBackground(colorI);
		panelCheckBoxesSimulation.add(preflopSimulation);
		preflopSimulation.setSelected(true);
		preflopSimulation.setBackground(colorI);
		mainPanel.add(panelCheckBoxesSimulation);

		panelTemp = new JPanel(new GridLayout(1, 1));
		panelTemp.setBounds(100, 125, 900, 40);
		final var f = new Font("Dialog", Font.BOLD, 16);
		temp.setFont(f);
		temp.setBackground(RED);
		panelTemp.add(temp);
		mainPanel.add(panelTemp);

		panelCheckBoxesAll = new JPanel(new GridLayout(1, 2));
		panelCheckBoxesAll.setBounds(100, 150, 900, 400);

		panelCheckBoxesFlop = new JPanel(new GridLayout(10, 1));
		panelCheckBoxesFlop.setBounds(100, 100, 900, 400);
		panelCheckBoxesTurn = new JPanel(new GridLayout(10, 1));
		panelCheckBoxesTurn.setBounds(100, 100, 900, 400);

		panelCheckBoxesAll.add(panelCheckBoxesFlop);
		panelCheckBoxesAll.add(panelCheckBoxesTurn);
		mainPanel.add(panelCheckBoxesAll);

		panelControl = new JPanel(new GridLayout(1, 3));
		panelControl.setBounds(100, 600, 650, 50);
		mainPanel.add(panelControl);

		panelStatus = new JPanel(new GridLayout(1, 1));
		panelStatus.setBounds(100, 700, 750, 50);
		mainPanel.add(panelStatus);

		// Board Flop Draw Made Showdown
		panelCheckBoxesBoardFlop = new JPanel(new GridLayout(1, 3));
		panelCheckBoxesBoardFlop.setBounds(300, 380, 450, 20);
		final var boardFlopBorder = BorderFactory.createTitledBorder("Board Reports Flop for Draw, Made, Showdown");
		panelCheckBoxesBoardFlop.setBorder(boardFlopBorder);
		boardDrawFlop.setFont(fs);
		boardDrawFlop.setPreferredSize(new Dimension(20, 20));
		boardDrawFlop.addActionListener(new Listener());
		boardDrawFlop.setSelected(false);
		boardDrawFlop.setBackground(colorB);
		panelCheckBoxesBoardFlop.add(boardDrawFlop);

		boardMadeFlop.setFont(fs);
		boardMadeFlop.setPreferredSize(new Dimension(20, 20));
		boardMadeFlop.addActionListener(new Listener());
		boardMadeFlop.setSelected(false);
		boardMadeFlop.setBackground(colorB);
		panelCheckBoxesBoardFlop.add(boardMadeFlop);

		boardShowdownFlop.setFont(fs);
		boardShowdownFlop.setPreferredSize(new Dimension(20, 20));
		boardShowdownFlop.addActionListener(new Listener());
		boardShowdownFlop.setSelected(false);
		boardShowdownFlop.setBackground(colorB);
		panelCheckBoxesBoardFlop.add(boardShowdownFlop);
		panelCheckBoxesBoardFlop.setBackground(colorB);

		// Board Flop Draw Made Showdown Suited and Connected only
		panelCheckBoxesBoardSGFlop = new JPanel(new GridLayout(1, 3));
		panelCheckBoxesBoardSGFlop.setBounds(300, 380, 450, 20);
		final var boardSGFlopBorder = BorderFactory
				.createTitledBorder("Board Suited and Connected Only Reports Flop for Draw, Made, Showdown");
		panelCheckBoxesBoardSGFlop.setBorder(boardSGFlopBorder);
		boardSGDrawFlop.setFont(fs);
		boardSGDrawFlop.setPreferredSize(new Dimension(20, 20));
		boardSGDrawFlop.addActionListener(new Listener());
		boardSGDrawFlop.setSelected(false);
		boardSGDrawFlop.setBackground(colorC);
		panelCheckBoxesBoardSGFlop.add(boardSGDrawFlop);

		boardSGMadeFlop.setFont(fs);
		boardSGMadeFlop.setPreferredSize(new Dimension(20, 20));
		boardSGMadeFlop.addActionListener(new Listener());
		boardSGMadeFlop.setSelected(false);
		boardSGMadeFlop.setBackground(colorC);
		panelCheckBoxesBoardSGFlop.add(boardSGMadeFlop);

		boardSGShowdownFlop.setFont(fs);
		boardSGShowdownFlop.setPreferredSize(new Dimension(20, 20));
		boardSGShowdownFlop.addActionListener(new Listener());
		boardSGShowdownFlop.setSelected(false);
		boardSGShowdownFlop.setBackground(colorC);
		panelCheckBoxesBoardSGFlop.add(boardSGShowdownFlop);
		panelCheckBoxesBoardSGFlop.setBackground(colorC);

		// HML Flop Draw Made Showdown
		panelCheckBoxesHMLFlop = new JPanel(new GridLayout(1, 3));
		panelCheckBoxesHMLFlop.setBounds(300, 380, 450, 20);
		final var hmlFlopBorder = BorderFactory
				.createTitledBorder("HML Reports Flop for Draw, Made, Combined, Combined, Showdown");
		panelCheckBoxesHMLFlop.setBorder(hmlFlopBorder);
		hmlDrawFlop.setFont(fs);
		hmlDrawFlop.setPreferredSize(new Dimension(20, 20));
		hmlDrawFlop.addActionListener(new Listener());
		hmlDrawFlop.setSelected(false);
		hmlDrawFlop.setBackground(colorD);
		panelCheckBoxesHMLFlop.add(hmlDrawFlop);

		hmlMadeFlop.setFont(fs);
		hmlMadeFlop.setPreferredSize(new Dimension(20, 20));
		hmlMadeFlop.addActionListener(new Listener());
		hmlMadeFlop.setSelected(false);
		hmlMadeFlop.setBackground(colorD);
		panelCheckBoxesHMLFlop.add(hmlMadeFlop);

		hmlCombinedFlop.setFont(fs);
		hmlCombinedFlop.setPreferredSize(new Dimension(20, 20));
		hmlCombinedFlop.addActionListener(new Listener());
		hmlCombinedFlop.setSelected(false);
		hmlCombinedFlop.setBackground(colorD);
		panelCheckBoxesHMLFlop.add(hmlCombinedFlop);

		hmlShowdownFlop.setFont(fs);
		hmlShowdownFlop.setPreferredSize(new Dimension(20, 20));
		hmlShowdownFlop.addActionListener(new Listener());
		hmlShowdownFlop.setSelected(false);
		hmlShowdownFlop.setBackground(colorD);
		panelCheckBoxesHMLFlop.add(hmlShowdownFlop);
		panelCheckBoxesHMLFlop.setBackground(colorD);

		// WetDry Flop Draw Made Showdown
		panelCheckBoxesWetDryFlop = new JPanel(new GridLayout(1, 3));
		panelCheckBoxesWetDryFlop.setBounds(300, 380, 450, 20);
		final var wetDryBorderFlop = BorderFactory
				.createTitledBorder("Wet Dry Reports Flop for Draw, Made, Combined, Combined, Showdown");
		panelCheckBoxesWetDryFlop.setBorder(wetDryBorderFlop);
		wetDryDrawFlop.setFont(fs);
		wetDryDrawFlop.setPreferredSize(new Dimension(20, 20));
		wetDryDrawFlop.addActionListener(new Listener());
		wetDryDrawFlop.setSelected(false);
		wetDryDrawFlop.setBackground(colorE);
		panelCheckBoxesWetDryFlop.add(wetDryDrawFlop);

		wetDryMadeFlop.setFont(fs);
		wetDryMadeFlop.setPreferredSize(new Dimension(20, 20));
		wetDryMadeFlop.addActionListener(new Listener());
		wetDryMadeFlop.setSelected(false);
		wetDryMadeFlop.setBackground(colorE);
		panelCheckBoxesWetDryFlop.add(wetDryMadeFlop);

		wetDryCombinedFlop.setFont(fs);
		wetDryCombinedFlop.setPreferredSize(new Dimension(20, 20));
		wetDryCombinedFlop.addActionListener(new Listener());
		wetDryCombinedFlop.setSelected(false);
		wetDryCombinedFlop.setBackground(colorD);
		panelCheckBoxesWetDryFlop.add(wetDryCombinedFlop);

		wetDryShowdownFlop.setFont(fs);
		wetDryShowdownFlop.setPreferredSize(new Dimension(20, 20));
		wetDryShowdownFlop.addActionListener(new Listener());
		wetDryShowdownFlop.setSelected(false);
		wetDryShowdownFlop.setBackground(colorE);
		panelCheckBoxesWetDryFlop.add(wetDryShowdownFlop);
		panelCheckBoxesWetDryFlop.setBackground(colorE);

		// Type of 1755 Flop Draw Made Showdown
		panelCheckBoxesTypeOf1755Flop = new JPanel(new GridLayout(1, 3));
		panelCheckBoxesTypeOf1755Flop.setBounds(300, 380, 450, 20);
		final var typeOf17ffBorderFlop = BorderFactory
				.createTitledBorder("Type of 1755 Reports Flop for Draw, Made, Combined, Combined, Showdown");
		panelCheckBoxesTypeOf1755Flop.setBorder(typeOf17ffBorderFlop);
		typeOf1755DrawFlop.setFont(fs);
		typeOf1755DrawFlop.setPreferredSize(new Dimension(20, 20));
		typeOf1755DrawFlop.addActionListener(new Listener());
		typeOf1755DrawFlop.setSelected(false);
		typeOf1755DrawFlop.setBackground(colorF);
		panelCheckBoxesTypeOf1755Flop.add(typeOf1755DrawFlop);

		typeOf1755MadeFlop.setFont(fs);
		typeOf1755MadeFlop.setPreferredSize(new Dimension(20, 20));
		typeOf1755MadeFlop.addActionListener(new Listener());
		typeOf1755MadeFlop.setSelected(false);
		typeOf1755MadeFlop.setBackground(colorF);
		panelCheckBoxesTypeOf1755Flop.add(typeOf1755MadeFlop);

		typeOf1755CombinedFlop.setFont(fs);
		typeOf1755CombinedFlop.setPreferredSize(new Dimension(20, 20));
		typeOf1755CombinedFlop.addActionListener(new Listener());
		typeOf1755CombinedFlop.setSelected(false);
		typeOf1755CombinedFlop.setBackground(colorD);
		panelCheckBoxesTypeOf1755Flop.add(typeOf1755CombinedFlop);

		typeOf1755ShowdownFlop.setFont(fs);
		typeOf1755ShowdownFlop.setPreferredSize(new Dimension(20, 20));
		typeOf1755ShowdownFlop.addActionListener(new Listener());
		typeOf1755ShowdownFlop.setSelected(false);
		typeOf1755ShowdownFlop.setBackground(colorF);
		panelCheckBoxesTypeOf1755Flop.add(typeOf1755ShowdownFlop);
		panelCheckBoxesTypeOf1755Flop.setBackground(colorF);

		// SCBP Flop Draw Made Showdown
		panelCheckBoxesSCBPFlop = new JPanel(new GridLayout(1, 3));
		panelCheckBoxesSCBPFlop.setBounds(300, 380, 450, 20);
		final var SCBPBorderFlop = BorderFactory
				.createTitledBorder("Type of SCBP Reports Flop for Draw, Made, Combined, Combined, Showdown");
		panelCheckBoxesSCBPFlop.setBorder(SCBPBorderFlop);
		SCBPDrawFlop.setFont(fs);
		SCBPDrawFlop.setPreferredSize(new Dimension(20, 20));
		SCBPDrawFlop.addActionListener(new Listener());
		SCBPDrawFlop.setSelected(false);
		SCBPDrawFlop.setBackground(colorG);
		panelCheckBoxesSCBPFlop.add(SCBPDrawFlop);

		SCBPMadeFlop.setFont(fs);
		SCBPMadeFlop.setPreferredSize(new Dimension(20, 20));
		SCBPMadeFlop.addActionListener(new Listener());
		SCBPMadeFlop.setSelected(false);
		SCBPMadeFlop.setBackground(colorG);
		panelCheckBoxesSCBPFlop.add(SCBPMadeFlop);

		SCBPCombinedFlop.setFont(fs);
		SCBPCombinedFlop.setPreferredSize(new Dimension(20, 20));
		SCBPCombinedFlop.addActionListener(new Listener());
		SCBPCombinedFlop.setSelected(false);
		SCBPCombinedFlop.setBackground(colorD);
		panelCheckBoxesSCBPFlop.add(SCBPCombinedFlop);

		SCBPShowdownFlop.setFont(fs);
		SCBPShowdownFlop.setPreferredSize(new Dimension(20, 20));
		SCBPShowdownFlop.addActionListener(new Listener());
		SCBPShowdownFlop.setSelected(false);
		SCBPShowdownFlop.setBackground(colorG);
		panelCheckBoxesSCBPFlop.add(SCBPShowdownFlop);
		panelCheckBoxesSCBPFlop.setBackground(colorG);

		// HML Flop Draw Win, Made Win
		panelCheckBoxesHMLFlopWin = new JPanel(new GridLayout(1, 2));
		panelCheckBoxesHMLFlopWin.setBounds(300, 380, 450, 20);
		final var hmlBorderFlopWin = BorderFactory.createTitledBorder("HML Win Reports Flop for Draw, Made");
		panelCheckBoxesHMLFlopWin.setBorder(hmlBorderFlopWin);
		hmlDrawFlopWin.setFont(fs);
		hmlDrawFlopWin.setPreferredSize(new Dimension(20, 20));
		hmlDrawFlopWin.addActionListener(new Listener());
		hmlDrawFlopWin.setSelected(false);
		hmlDrawFlopWin.setBackground(colorD);
		panelCheckBoxesHMLFlopWin.add(hmlDrawFlopWin);

		hmlMadeFlopWin.setFont(fs);
		hmlMadeFlopWin.setPreferredSize(new Dimension(20, 20));
		hmlMadeFlopWin.addActionListener(new Listener());
		hmlMadeFlopWin.setSelected(false);
		hmlMadeFlopWin.setBackground(colorD);
		panelCheckBoxesHMLFlopWin.add(hmlMadeFlopWin);
		panelCheckBoxesHMLFlopWin.setBackground(colorD);

		// Wet Dry Flop Draw Win, Made Win
		panelCheckBoxesWetDryFlopWin = new JPanel(new GridLayout(1, 2));
		panelCheckBoxesWetDryFlopWin.setBounds(300, 380, 450, 20);
		final var wetDryBorderFlopWin = BorderFactory.createTitledBorder("Wet Dry Win Reports Flop for Draw, Made");
		panelCheckBoxesWetDryFlopWin.setBorder(wetDryBorderFlopWin);
		wetDryDrawFlopWin.setFont(fs);
		wetDryDrawFlopWin.setPreferredSize(new Dimension(20, 20));
		wetDryDrawFlopWin.addActionListener(new Listener());
		wetDryDrawFlopWin.setSelected(false);
		wetDryDrawFlopWin.setBackground(colorE);
		panelCheckBoxesWetDryFlopWin.add(wetDryDrawFlopWin);

		wetDryMadeFlopWin.setFont(fs);
		wetDryMadeFlopWin.setPreferredSize(new Dimension(20, 20));
		wetDryMadeFlopWin.addActionListener(new Listener());
		wetDryMadeFlopWin.setSelected(false);
		wetDryMadeFlopWin.setBackground(colorE);
		panelCheckBoxesWetDryFlopWin.add(wetDryMadeFlopWin);
		panelCheckBoxesWetDryFlopWin.setBackground(colorE);

		// Type of 1755 Flop Draw Win, Made Win
		panelCheckBoxesTypeOf1755FlopWin = new JPanel(new GridLayout(1, 2));
		panelCheckBoxesTypeOf1755FlopWin.setBounds(300, 380, 450, 20);
		final var typeOf17ffBorderFlopWin = BorderFactory
				.createTitledBorder("Type of 1755 Win Reports Flop for Draw, Made");
		panelCheckBoxesTypeOf1755FlopWin.setBorder(typeOf17ffBorderFlopWin);
		typeOf1755DrawFlopWin.setFont(fs);
		typeOf1755DrawFlopWin.setPreferredSize(new Dimension(20, 20));
		typeOf1755DrawFlopWin.addActionListener(new Listener());
		typeOf1755DrawFlopWin.setSelected(false);
		typeOf1755DrawFlopWin.setBackground(colorF);
		panelCheckBoxesTypeOf1755FlopWin.add(typeOf1755DrawFlopWin);

		typeOf1755MadeFlopWin.setFont(fs);
		typeOf1755MadeFlopWin.setPreferredSize(new Dimension(20, 20));
		typeOf1755MadeFlopWin.addActionListener(new Listener());
		typeOf1755MadeFlopWin.setSelected(false);
		typeOf1755MadeFlopWin.setBackground(colorF);
		panelCheckBoxesTypeOf1755FlopWin.add(typeOf1755MadeFlopWin);
		panelCheckBoxesTypeOf1755FlopWin.setBackground(colorF);

		// SCBP Flop Draw Win, Made Win
		panelCheckBoxesSCBPFlopWin = new JPanel(new GridLayout(1, 2));
		panelCheckBoxesSCBPFlopWin.setBounds(300, 380, 450, 20);
		final var SCBPBorderFlopWin = BorderFactory.createTitledBorder("Type of SCBP Win Reports Flop for Draw, Made");
		panelCheckBoxesSCBPFlopWin.setBorder(SCBPBorderFlopWin);
		SCBPDrawFlopWin.setFont(fs);
		SCBPDrawFlopWin.setPreferredSize(new Dimension(20, 20));
		SCBPDrawFlopWin.addActionListener(new Listener());
		SCBPDrawFlopWin.setSelected(false);
		SCBPDrawFlopWin.setBackground(colorG);
		panelCheckBoxesSCBPFlopWin.add(SCBPDrawFlopWin);

		SCBPMadeFlopWin.setFont(fs);
		SCBPMadeFlopWin.setPreferredSize(new Dimension(20, 20));
		SCBPMadeFlopWin.addActionListener(new Listener());
		SCBPMadeFlopWin.setSelected(false);
		SCBPMadeFlopWin.setBackground(colorG);
		panelCheckBoxesSCBPFlopWin.add(SCBPMadeFlopWin);
		panelCheckBoxesSCBPFlopWin.setBackground(colorG);

		// Enable or disable check box - test and debug only
		boardDrawFlop.setEnabled(false);
		boardMadeFlop.setEnabled(false);
		boardShowdownFlop.setEnabled(false);
		boardSGDrawFlop.setEnabled(false);
		boardSGMadeFlop.setEnabled(false);
		boardSGShowdownFlop.setEnabled(false);
		hmlDrawFlop.setEnabled(true);
		hmlMadeFlop.setEnabled(true);
		hmlCombinedFlop.setEnabled(false);
		hmlShowdownFlop.setEnabled(true);
		wetDryDrawFlop.setEnabled(true);
		wetDryMadeFlop.setEnabled(true);
		wetDryCombinedFlop.setEnabled(false);
		wetDryShowdownFlop.setEnabled(true);
		typeOf1755DrawFlop.setEnabled(true);
		typeOf1755MadeFlop.setEnabled(true);
		typeOf1755CombinedFlop.setEnabled(false);
		typeOf1755ShowdownFlop.setEnabled(true);
		SCBPDrawFlop.setEnabled(true);
		SCBPMadeFlop.setEnabled(true);
		SCBPCombinedFlop.setEnabled(false);
		SCBPShowdownFlop.setEnabled(true);

		hmlDrawFlopWin.setEnabled(false);
		hmlMadeFlopWin.setEnabled(false);
		wetDryDrawFlopWin.setEnabled(false);
		wetDryMadeFlopWin.setEnabled(false);
		typeOf1755DrawFlopWin.setEnabled(false);
		typeOf1755MadeFlopWin.setEnabled(false);
		SCBPDrawFlopWin.setEnabled(false);
		SCBPMadeFlopWin.setEnabled(false);

		// Add check boxes to panel
		panelCheckBoxesFlop.add(panelCheckBoxesBoardFlop);
		panelCheckBoxesFlop.add(panelCheckBoxesBoardSGFlop);
		panelCheckBoxesFlop.add(panelCheckBoxesHMLFlop);
		panelCheckBoxesFlop.add(panelCheckBoxesWetDryFlop);
		panelCheckBoxesFlop.add(panelCheckBoxesTypeOf1755Flop);
		panelCheckBoxesFlop.add(panelCheckBoxesSCBPFlop);
		panelCheckBoxesFlop.add(panelCheckBoxesHMLFlopWin);
		panelCheckBoxesFlop.add(panelCheckBoxesWetDryFlopWin);
		panelCheckBoxesFlop.add(panelCheckBoxesTypeOf1755FlopWin);
		panelCheckBoxesFlop.add(panelCheckBoxesSCBPFlopWin);

		// Board Flop Draw Made Showdown
		panelCheckBoxesBoardTurn = new JPanel(new GridLayout(1, 3));
		panelCheckBoxesBoardTurn.setBounds(300, 380, 450, 20);
		final var boardTurnBorder = BorderFactory
				.createTitledBorder("Board Reports Turn for Draw, Made, Combined, Combined, Showdown");
		panelCheckBoxesBoardTurn.setBorder(boardTurnBorder);
		boardDrawTurn.setFont(fs);
		boardDrawTurn.setPreferredSize(new Dimension(20, 20));
		boardDrawTurn.addActionListener(new Listener());
		boardDrawTurn.setSelected(false);
		boardDrawTurn.setBackground(colorB);
		panelCheckBoxesBoardTurn.add(boardDrawTurn);

		boardMadeTurn.setFont(fs);
		boardMadeTurn.setPreferredSize(new Dimension(20, 20));
		boardMadeTurn.addActionListener(new Listener());
		boardMadeTurn.setSelected(false);
		boardMadeTurn.setBackground(colorB);
		panelCheckBoxesBoardTurn.add(boardMadeTurn);

		boardShowdownTurn.setFont(fs);
		boardShowdownTurn.setPreferredSize(new Dimension(20, 20));
		boardShowdownTurn.addActionListener(new Listener());
		boardShowdownTurn.setSelected(false);
		boardShowdownTurn.setBackground(colorB);
		panelCheckBoxesBoardTurn.add(boardShowdownTurn);
		panelCheckBoxesBoardTurn.setBackground(colorB);

		// HML Turn Draw Made Showdown
		panelCheckBoxesHMLTurn = new JPanel(new GridLayout(1, 3));
		panelCheckBoxesHMLTurn.setBounds(300, 380, 450, 20);
		final var hmlTurnBorder = BorderFactory
				.createTitledBorder("HML Reports Turn for Draw, Made, Combined, Combined, Showdown");
		panelCheckBoxesHMLTurn.setBorder(hmlTurnBorder);
		hmlDrawTurn.setFont(fs);
		hmlDrawTurn.setPreferredSize(new Dimension(20, 20));
		hmlDrawTurn.addActionListener(new Listener());
		hmlDrawTurn.setSelected(false);
		hmlDrawTurn.setBackground(colorD);
		panelCheckBoxesHMLTurn.add(hmlDrawTurn);

		hmlMadeTurn.setFont(fs);
		hmlMadeTurn.setPreferredSize(new Dimension(20, 20));
		hmlMadeTurn.addActionListener(new Listener());
		hmlMadeTurn.setSelected(false);
		hmlMadeTurn.setBackground(colorD);
		panelCheckBoxesHMLTurn.add(hmlMadeTurn);

		hmlShowdownTurn.setFont(fs);
		hmlShowdownTurn.setPreferredSize(new Dimension(20, 20));
		hmlShowdownTurn.addActionListener(new Listener());
		hmlShowdownTurn.setSelected(false);
		hmlShowdownTurn.setBackground(colorD);
		panelCheckBoxesHMLTurn.add(hmlShowdownTurn);
		panelCheckBoxesHMLTurn.setBackground(colorD);

		// WetDry Turn Draw Made Showdown
		panelCheckBoxesWetDryTurn = new JPanel(new GridLayout(1, 3));
		panelCheckBoxesWetDryTurn.setBounds(300, 380, 450, 20);
		final var wetDryBorderTurn = BorderFactory
				.createTitledBorder("Wet Dry Reports Turn for Draw, Made, Combined, Combined, Showdown");
		panelCheckBoxesWetDryTurn.setBorder(wetDryBorderTurn);
		wetDryDrawTurn.setFont(fs);
		wetDryDrawTurn.setPreferredSize(new Dimension(20, 20));
		wetDryDrawTurn.addActionListener(new Listener());
		wetDryDrawTurn.setSelected(false);
		wetDryDrawTurn.setBackground(colorE);
		panelCheckBoxesWetDryTurn.add(wetDryDrawTurn);

		wetDryMadeTurn.setFont(fs);
		wetDryMadeTurn.setPreferredSize(new Dimension(20, 20));
		wetDryMadeTurn.addActionListener(new Listener());
		wetDryMadeTurn.setSelected(false);
		wetDryMadeTurn.setBackground(colorE);
		panelCheckBoxesWetDryTurn.add(wetDryMadeTurn);

		wetDryShowdownTurn.setFont(fs);
		wetDryShowdownTurn.setPreferredSize(new Dimension(20, 20));
		wetDryShowdownTurn.addActionListener(new Listener());
		wetDryShowdownTurn.setSelected(false);
		wetDryShowdownTurn.setBackground(colorE);
		panelCheckBoxesWetDryTurn.add(wetDryShowdownTurn);
		panelCheckBoxesWetDryTurn.setBackground(colorE);

		// Type of 1755 Turn Draw Made Showdown
		panelCheckBoxesTypeOf1755Turn = new JPanel(new GridLayout(1, 3));
		panelCheckBoxesTypeOf1755Turn.setBounds(300, 380, 450, 20);
		final var typeOf17ffBorderTurn = BorderFactory
				.createTitledBorder("Type of 1755 Reports Turn for Draw, Made, Combined, Combined, Showdown");
		panelCheckBoxesTypeOf1755Turn.setBorder(typeOf17ffBorderTurn);
		typeOf1755DrawTurn.setFont(fs);
		typeOf1755DrawTurn.setPreferredSize(new Dimension(20, 20));
		typeOf1755DrawTurn.addActionListener(new Listener());
		typeOf1755DrawTurn.setSelected(false);
		typeOf1755DrawTurn.setBackground(colorF);
		panelCheckBoxesTypeOf1755Turn.add(typeOf1755DrawTurn);

		typeOf1755MadeTurn.setFont(fs);
		typeOf1755MadeTurn.setPreferredSize(new Dimension(20, 20));
		typeOf1755MadeTurn.addActionListener(new Listener());
		typeOf1755MadeTurn.setSelected(false);
		typeOf1755MadeTurn.setBackground(colorF);
		panelCheckBoxesTypeOf1755Turn.add(typeOf1755MadeTurn);

		typeOf1755ShowdownTurn.setFont(fs);
		typeOf1755ShowdownTurn.setPreferredSize(new Dimension(20, 20));
		typeOf1755ShowdownTurn.addActionListener(new Listener());
		typeOf1755ShowdownTurn.setSelected(false);
		typeOf1755ShowdownTurn.setBackground(colorF);
		panelCheckBoxesTypeOf1755Turn.add(typeOf1755ShowdownTurn);
		panelCheckBoxesTypeOf1755Turn.setBackground(colorF);

		// SCBP Turn Draw Made Showdown
		panelCheckBoxesSCBPTurn = new JPanel(new GridLayout(1, 3));
		panelCheckBoxesSCBPTurn.setBounds(300, 380, 450, 20);
		final var SCBPBorderTurn = BorderFactory
				.createTitledBorder("Type of SCBP Reports Turn for Draw, Made, Combined, Combined, Showdown");
		panelCheckBoxesSCBPTurn.setBorder(SCBPBorderTurn);
		SCBPDrawTurn.setFont(fs);
		SCBPDrawTurn.setPreferredSize(new Dimension(20, 20));
		SCBPDrawTurn.addActionListener(new Listener());
		SCBPDrawTurn.setSelected(false);
		SCBPDrawTurn.setBackground(colorG);
		panelCheckBoxesSCBPTurn.add(SCBPDrawTurn);

		SCBPMadeTurn.setFont(fs);
		SCBPMadeTurn.setPreferredSize(new Dimension(20, 20));
		SCBPMadeTurn.addActionListener(new Listener());
		SCBPMadeTurn.setSelected(false);
		SCBPMadeTurn.setBackground(colorG);
		panelCheckBoxesSCBPTurn.add(SCBPMadeTurn);

		SCBPShowdownTurn.setFont(fs);
		SCBPShowdownTurn.setPreferredSize(new Dimension(20, 20));
		SCBPShowdownTurn.addActionListener(new Listener());
		SCBPShowdownTurn.setSelected(false);
		SCBPShowdownTurn.setBackground(colorG);
		panelCheckBoxesSCBPTurn.add(SCBPShowdownTurn);
		panelCheckBoxesSCBPTurn.setBackground(colorG);

		// HML Turn Draw Win, Made Win
		panelCheckBoxesHMLTurnWin = new JPanel(new GridLayout(1, 2));
		panelCheckBoxesHMLTurnWin.setBounds(300, 380, 450, 20);
		final var hmlBorderTurnWin = BorderFactory.createTitledBorder("HML Win Reports Turn for Draw, Made");
		panelCheckBoxesHMLTurnWin.setBorder(hmlBorderTurnWin);
		hmlDrawTurnWin.setFont(fs);
		hmlDrawTurnWin.setPreferredSize(new Dimension(20, 20));
		hmlDrawTurnWin.addActionListener(new Listener());
		hmlDrawTurnWin.setSelected(false);
		hmlDrawTurnWin.setBackground(colorD);
		panelCheckBoxesHMLTurnWin.add(hmlDrawTurnWin);

		hmlMadeTurnWin.setFont(fs);
		hmlMadeTurnWin.setPreferredSize(new Dimension(20, 20));
		hmlMadeTurnWin.addActionListener(new Listener());
		hmlMadeTurnWin.setSelected(false);
		hmlMadeTurnWin.setBackground(colorD);
		panelCheckBoxesHMLTurnWin.add(hmlMadeTurnWin);
		panelCheckBoxesHMLTurnWin.setBackground(colorD);

		// Wet Dry Turn Draw Win, Made Win
		panelCheckBoxesWetDryTurnWin = new JPanel(new GridLayout(1, 2));
		panelCheckBoxesWetDryTurnWin.setBounds(300, 380, 450, 20);
		final var wetDryBorderTurnWin = BorderFactory.createTitledBorder("Wet Dry Win Reports Turn for Draw, Made");
		panelCheckBoxesWetDryTurnWin.setBorder(wetDryBorderTurnWin);
		wetDryDrawTurnWin.setFont(fs);
		wetDryDrawTurnWin.setPreferredSize(new Dimension(20, 20));
		wetDryDrawTurnWin.addActionListener(new Listener());
		wetDryDrawTurnWin.setSelected(false);
		wetDryDrawTurnWin.setBackground(colorE);
		panelCheckBoxesWetDryTurnWin.add(wetDryDrawTurnWin);

		wetDryMadeTurnWin.setFont(fs);
		wetDryMadeTurnWin.setPreferredSize(new Dimension(20, 20));
		wetDryMadeTurnWin.addActionListener(new Listener());
		wetDryMadeTurnWin.setSelected(false);
		wetDryMadeTurnWin.setBackground(colorE);
		panelCheckBoxesWetDryTurnWin.add(wetDryMadeTurnWin);
		panelCheckBoxesWetDryTurnWin.setBackground(colorE);

		// Type of 1755 Turn Draw Win, Made Win
		panelCheckBoxesTypeOf1755TurnWin = new JPanel(new GridLayout(1, 2));
		panelCheckBoxesTypeOf1755TurnWin.setBounds(300, 380, 450, 20);
		final var typeOf17ffBorderTurnWin = BorderFactory
				.createTitledBorder("Type of 1755 Win Reports Turn for Draw, Made");
		panelCheckBoxesTypeOf1755TurnWin.setBorder(typeOf17ffBorderTurnWin);
		typeOf1755DrawTurnWin.setFont(fs);
		typeOf1755DrawTurnWin.setPreferredSize(new Dimension(20, 20));
		typeOf1755DrawTurnWin.addActionListener(new Listener());
		typeOf1755DrawTurnWin.setSelected(false);
		typeOf1755DrawTurnWin.setBackground(colorF);
		panelCheckBoxesTypeOf1755TurnWin.add(typeOf1755DrawTurnWin);

		typeOf1755MadeTurnWin.setFont(fs);
		typeOf1755MadeTurnWin.setPreferredSize(new Dimension(20, 20));
		typeOf1755MadeTurnWin.addActionListener(new Listener());
		typeOf1755MadeTurnWin.setSelected(false);
		typeOf1755MadeTurnWin.setBackground(colorF);
		panelCheckBoxesTypeOf1755TurnWin.add(typeOf1755MadeTurnWin);
		panelCheckBoxesTypeOf1755TurnWin.setBackground(colorF);

		// SCBP Turn Draw Win, Made Win
		panelCheckBoxesSCBPTurnWin = new JPanel(new GridLayout(1, 2));
		panelCheckBoxesSCBPTurnWin.setBounds(300, 380, 450, 20);
		final var SCBPBorderTurnWin = BorderFactory.createTitledBorder("Type of SCBP Win Reports Turn for Draw, Made");
		panelCheckBoxesSCBPTurnWin.setBorder(SCBPBorderTurnWin);
		SCBPDrawTurnWin.setFont(fs);
		SCBPDrawTurnWin.setPreferredSize(new Dimension(20, 20));
		SCBPDrawTurnWin.addActionListener(new Listener());
		SCBPDrawTurnWin.setSelected(false);
		SCBPDrawTurnWin.setBackground(colorG);
		panelCheckBoxesSCBPTurnWin.add(SCBPDrawTurnWin);

		SCBPMadeTurnWin.setFont(fs);
		SCBPMadeTurnWin.setPreferredSize(new Dimension(20, 20));
		SCBPMadeTurnWin.addActionListener(new Listener());
		SCBPMadeTurnWin.setSelected(false);
		SCBPMadeTurnWin.setBackground(colorG);
		panelCheckBoxesSCBPTurnWin.add(SCBPMadeTurnWin);
		panelCheckBoxesSCBPTurnWin.setBackground(colorG);

		// Enable or disable check box - test and debug only
		boardDrawTurn.setEnabled(false);
		boardMadeTurn.setEnabled(false);
		boardShowdownTurn.setEnabled(false);
		hmlDrawTurn.setEnabled(false);
		hmlMadeTurn.setEnabled(false);
		hmlShowdownTurn.setEnabled(false);
		wetDryDrawTurn.setEnabled(false);
		wetDryMadeTurn.setEnabled(false);
		wetDryShowdownTurn.setEnabled(false);
		typeOf1755DrawTurn.setEnabled(false);
		typeOf1755MadeTurn.setEnabled(false);
		typeOf1755ShowdownTurn.setEnabled(false);
		SCBPDrawTurn.setEnabled(false);
		SCBPMadeTurn.setEnabled(false);
		SCBPShowdownTurn.setEnabled(false);

		hmlDrawTurnWin.setEnabled(false);
		hmlMadeTurnWin.setEnabled(false);
		wetDryDrawTurnWin.setEnabled(false);
		wetDryMadeTurnWin.setEnabled(false);
		typeOf1755DrawTurnWin.setEnabled(false);
		typeOf1755MadeTurnWin.setEnabled(false);
		SCBPDrawTurnWin.setEnabled(false);
		SCBPMadeTurnWin.setEnabled(false);

		// Add check boxes to panel
		panelCheckBoxesTurn.add(panelCheckBoxesBoardTurn);
		panelCheckBoxesTurn.add(panelCheckBoxesHMLTurn);
		panelCheckBoxesTurn.add(panelCheckBoxesWetDryTurn);
		panelCheckBoxesTurn.add(panelCheckBoxesTypeOf1755Turn);
		panelCheckBoxesTurn.add(panelCheckBoxesSCBPTurn);
		panelCheckBoxesTurn.add(panelCheckBoxesHMLTurnWin);
		panelCheckBoxesTurn.add(panelCheckBoxesWetDryTurnWin);
		panelCheckBoxesTurn.add(panelCheckBoxesTypeOf1755TurnWin);
		panelCheckBoxesTurn.add(panelCheckBoxesSCBPTurnWin);

		// Number of hands to play
		hundred.setFont(fs);
		hundred.addActionListener(new Listener());
		hundred.setBackground(colorH);
		panelRadioHands.add(hundred);
		handsGroup.add(hundred);

		thousand.setFont(fs);
		thousand.addActionListener(new Listener());
		thousand.setBackground(colorH);
		panelRadioHands.add(thousand);
		thousand.setSelected(true);
		handsGroup.add(thousand);

		tenThousand.setFont(fs);
		tenThousand.addActionListener(new Listener());
		tenThousand.setBackground(colorH);
		panelRadioHands.add(tenThousand);
		handsGroup.add(tenThousand);

		hundredThousand.setFont(fs);
		hundredThousand.addActionListener(new Listener());
		hundredThousand.setBackground(colorH);
		panelRadioHands.add(hundredThousand);
		handsGroup.add(hundredThousand);

		million.setFont(fs);
		million.addActionListener(new Listener());
		million.setBackground(colorH);
		panelRadioHands.add(million);
		handsGroup.add(million);

		tenMillion.setFont(fs);
		tenMillion.addActionListener(new Listener());
		tenMillion.setBackground(colorH);
		panelRadioHands.add(tenMillion);
		handsGroup.add(tenMillion);

		hundredMillion.setFont(fs);
		hundredMillion.addActionListener(new Listener());
		hundredMillion.setBackground(colorH);
		panelRadioHands.add(hundredMillion);
		handsGroup.add(hundredMillion);

		run.setFont(fc);
		run.setPreferredSize(new Dimension(15, 15));
		run.addActionListener(new Listener());
		run.setBackground(CONTROL);
		panelControl.add(run);

		exit.setFont(fc);
		exit.setPreferredSize(new Dimension(15, 15));
		exit.addActionListener(new Listener());
		exit.setBackground(CONTROL);
		panelControl.add(exit);

		help.setFont(fc);
		help.setPreferredSize(new Dimension(15, 15));
		help.addActionListener(new Listener());
		help.setBackground(CONTROL);
		panelControl.add(help);

		status.setFont(fc);
		status.setPreferredSize(new Dimension(15, 15));
		status.setBackground(colorG);
		status.setVisible(true);
		panelStatus.setVisible(true);
		panelStatus.add(status);

		panelCheck.setLayout(new GridLayout(1, 1));
		final var optionsCheck = Box.createHorizontalBox();
		optionsCheck.setFont(f);
		panelCheck.setBorder(BorderFactory.createTitledBorder("Options"));
		panelCheck.add(crash);
		mainPanel.add(panelCheck);
		crash.setSelected(true);
		crash.addActionListener(new Listener());
		panelCheck.setFont(f);

		crash.setSelected(true);

		// Set some properties of the frame
		frame.setContentPane(mainPanel);
		frame.setTitle("Evaluate Hands");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	 * The Full Simulation  option has been selected.
	 * Prompt for the number of hands to play
	 *  ****************************************************************************** */
	private static void doSimulationDataCollection() {
		EvalData.initializeNewGame();
		int number = 0;
		if (hundred.isSelected()) {
			number = 100;
		} else if (thousand.isSelected()) {
			number = 1000;
		} else if (tenThousand.isSelected()) {
			number = 10000;
		} else if (hundredThousand.isSelected()) {
			number = 100000;
		} else if (million.isSelected()) {
			number = 1000000;
			status.setVisible(true);
			panelStatus.setVisible(true);
		} else if (tenMillion.isSelected()) {
			number = 10000000;
			status.setVisible(true);
			panelStatus.setVisible(true);
		} else if (hundredMillion.isSelected()) {
			number = 100000000;
			status.setVisible(true);
			panelStatus.setVisible(true);
			status.setBackground(RED);
		}

		if (preflopSimulation.isSelected()) {
			EvalData.preflopSimulation = true;
		}

		EvalData.manyHands = true;
		EvalData.rotatePlayers = true;
		EvalData.hh = false;

		quit = false;

		// Simulate the play of xxx hands, Preflop, Flop, Turn, and River
		GameControl.simulationDataCollection(number);

		final var hmlFlop = new IndexArrayDrawMadeWin(IndexArrays.hmlDrawFlop, IndexArrays.hmlMadeFlop,
				IndexArrays.hmlShowdownFlop, HML_FLOP_ST, DRAW_ST, MADE_ST);

		final var hmlTurn = new IndexArrayDrawMadeWin(IndexArrays.hmlDrawTurn, IndexArrays.hmlMadeTurn,
				IndexArrays.hmlShowdownTurn, HML_TURN_ST, DRAW_ST, MADE_ST);

		final var wetDryFlop = new IndexArrayDrawMadeWin(IndexArrays.wetDryDrawFlop, IndexArrays.wetDryMadeFlop,
				IndexArrays.wetDryShowdownFlop, WET_DRY_ST, DRAW_ST, MADE_ST);

		final var typeOf1755Flop = new IndexArrayDrawMadeWin(IndexArrays.typeOf1755DrawFlop,
				IndexArrays.typeOf1755MadeFlop, IndexArrays.typeOf1755ShowdownFlop, TYPE_OF_1755_ST, DRAW_ST, MADE_ST);

		final var SCBPFlop = new IndexArrayDrawMadeWin(IndexArrays.SCBPDrawFlop, IndexArrays.SCBPMadeFlop,
				IndexArrays.SCBPShowdownFlop, SCBP_ST, DRAW_ST, MADE_ST);

		final var reportFlop = new IndexArrayReport();
		if (hmlDrawFlop.isSelected()) {
			reportFlop.reportDraw(10, 100, "Flop Draws with HML Index", hmlFlop);
		}
		if (hmlMadeFlop.isSelected()) {
			reportFlop.reportMade(10, 400, "Flop  Made hands with HML Index", hmlFlop);
		}
		if (hmlShowdownFlop.isSelected()) {
			reportFlop.reportShowdown(10, 600, "Flop  Showdown hands with HML Index", hmlFlop);
		}

		final var reportFlopWetDry = new IndexArrayReport();
		if (wetDryDrawFlop.isSelected()) {
			reportFlopWetDry.reportDraw(10, 100, "Flop Draws Wet Dry Index", wetDryFlop);
		}
		if (wetDryMadeFlop.isSelected()) {
			reportFlopWetDry.reportMade(10, 400, "Flop  Made hands Wet Dry Index", wetDryFlop);
		}
		if (wetDryShowdownFlop.isSelected()) {
			reportFlopWetDry.reportShowdown(10, 600, "Flop  Showdown hands Wet Dry Index", wetDryFlop);
		}

		final var reportFlopTypeOf1755 = new IndexArrayReport();
		if (typeOf1755DrawFlop.isSelected()) {
			reportFlopTypeOf1755.reportDraw(10, 100, "Flop Draws type of 1755 Index", typeOf1755Flop);
		}
		if (typeOf1755MadeFlop.isSelected()) {
			reportFlopTypeOf1755.reportMade(10, 400, "Flop  Made type of 1755Dry Index", typeOf1755Flop);
		}
		if (typeOf1755ShowdownFlop.isSelected()) {
			reportFlopTypeOf1755.reportShowdown(10, 600, "Flop  Showdown hands type of 1755 Index", typeOf1755Flop);
		}

		final var reportFlopSCBP = new IndexArrayReport();
		if (SCBPDrawFlop.isSelected()) {
			reportFlopSCBP.reportDraw(10, 100, "Flop Draws SCPB Index", SCBPFlop);
		}
		if (SCBPMadeFlop.isSelected()) {
			reportFlopSCBP.reportMade(10, 400, "Flop  Made SCPB Index", SCBPFlop);
		}
		if (SCBPShowdownFlop.isSelected()) {
			reportFlopSCBP.reportShowdown(10, 600, "Flop  Showdown hands SCPBIndex", SCBPFlop);
		}

		final var hmlFlopWin = new IndexArrayDrawMadeWin(IndexArrays.hmlDrawFlop, IndexArrays.hmlMadeFlop,
				IndexArrays.hmlShowdownFlop, HML_FLOP_ST, DRAW_ST, MADE_ST);

		final var hmlTurnWin = new IndexArrayDrawMadeWin(IndexArrays.hmlDrawTurn, IndexArrays.hmlMadeTurn,
				IndexArrays.hmlShowdownTurn, HML_TURN_ST, DRAW_ST, MADE_ST);

		final var wetDryFlopWin = new IndexArrayDrawMadeWin(IndexArrays.wetDryDrawFlop, IndexArrays.wetDryMadeFlop,
				IndexArrays.wetDryShowdownFlop, WET_DRY_ST, DRAW_ST, MADE_ST);

		final var typeOf1755FlopWin = new IndexArrayDrawMadeWin(IndexArrays.typeOf1755DrawFlop,
				IndexArrays.typeOf1755MadeFlop, IndexArrays.typeOf1755ShowdownFlop, TYPE_OF_1755_ST, DRAW_ST, MADE_ST);

		final var SCBPFlopWin = new IndexArrayDrawMadeWin(IndexArrays.SCBPDrawFlop, IndexArrays.SCBPMadeFlop,
				IndexArrays.SCBPShowdownFlop, SCBP_ST, DRAW_ST, MADE_ST);

		final var reportFlopWin = new IndexArrayReportToWinner();
		if (hmlDrawFlopWin.isSelected()) {
			reportFlopWin.reportDraw(10, 100, "Flop Draws with HML Index", hmlFlop);
		}
		if (hmlMadeFlopWin.isSelected()) {
			reportFlopWin.reportMade(10, 400, "Flop  Made hands with HML Index", hmlFlop);
		}

		final var reportFlopWetDryWin = new IndexArrayReportToWinner();
		if (wetDryDrawFlopWin.isSelected()) {
			reportFlopWetDryWin.reportDraw(10, 100, "Flop Draws Wet Dry Index", wetDryFlop);
		}
		if (wetDryMadeFlopWin.isSelected()) {
			reportFlopWetDry.reportMade(10, 400, "Flop  Made hands Wet Dry Index", wetDryFlop);
		}

		final var reportFlopTypeOf1755Win = new IndexArrayReportToWinner();
		if (typeOf1755DrawFlop.isSelected()) {
			reportFlopTypeOf1755Win.reportDraw(10, 100, "Flop Draws type of 1755 Index", typeOf1755Flop);
		}
		if (typeOf1755MadeFlopWin.isSelected()) {
			reportFlopTypeOf1755Win.reportMade(10, 400, "Flop  Made type of 1755Dry Index", typeOf1755Flop);
		}

		final var reportFlopSCBPWin = new IndexArrayReportToWinner();
		if (SCBPDrawFlopWin.isSelected()) {
			reportFlopSCBPWin.reportDraw(10, 100, "Flop Draws SCPB Index", SCBPFlop);
		}
		if (SCBPMadeFlopWin.isSelected()) {
			reportFlopSCBPWin.reportMade(10, 400, "Flop  Made SCPB Index", SCBPFlop);
		}
		if (crash.isSelected()) {
			Crash.crash = true;
		} else {
			Crash.crash = false;
		}
	}

	/*-  ******************************************************************************
	* For very long runs update elapsed time and check for exit selected
	*  ****************************************************************************** */
	static boolean checkExit(int played) {
		if (statusUpdate++ > 10000) {
			statusUpdate = 0;
			status.setVisible(true);
			panelStatus.setVisible(true);
			status.repaint();
			panelStatus.repaint();
		}
		return !quit ? false : true;
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

			switch (action) {
			case "Run" -> doSimulationDataCollection();
			case "Help" -> JOptionPane.showMessageDialog(null, helpString);
			case "Exit" -> {
				quit = true;
			}
			default -> {
			}
			}
		}
	}

}
