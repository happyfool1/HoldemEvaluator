package holdemevaluator;

/*-  ******************************************************************************

 *  @author PEAK_ 
 * ****************************************************************************** */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;

class EditRange implements Constants {

	private static final int HANDS = 169;
	private static final int NUM_ROWS = 13;
	private static final int NUM_COLS = 13;

	private static final Color open = Color.CYAN;
	private static final Color call = Color.YELLOW;
	private static final Color none = Color.LIGHT_GRAY;
	private static final Color control = Color.GREEN;

	private static int offset = 100;

	/*- - Number of ranges combined in matrix. */
	JFrame frame = new JFrame();
	HandRangeCumulative rangeMultiple = new HandRangeCumulative();
	HandRange range1 = new HandRange();
	HandRange range2 = new HandRange();

	Color range1Color = Color.CYAN;
	Color range2Color = Color.YELLOW;
	String file1 = "";

	/*- The HANDS buttons objects in matrix. */
	JButton[] buttonArr = new JButton[HANDS];

	JButton buttonSave = new JButton("Save");

	/*- Report. */
	JTextField combos1 = new JTextField("Combos1");
	JTextField comboPer1 = new JTextField("Percent1");
	JTextField play = new JTextField("Play");

	JTextField gaps = new JTextField("Gaps");

	/*- Report. */
	JTextField combos2 = new JTextField("Combos2");
	JTextField comboPer2 = new JTextField("Percen2");
	JTextField play2 = new JTextField("Play");
	JTextField gaps2 = new JTextField("Gaps");

	JTextField duplicates = new JTextField("");

	JTextField msg = new JTextField("");
	String caution = " Caution - Editing this range can change ";

	/*- Panel title. */
	String title = "";

	/*- File name. */
	String fileName = "";

	boolean edit;
	int hand = -1;
	int x;
	int y;

	/*-  ******************************************************************************
	 * This Class is used to edit the range of a hand.
	 * The HandRange to be edited is contained in the HandRangeMultiple Class that 
	 * contains 60 HandRanges for a single player type such as Hero.
	 * The class is two 2 dimensional arrays.
	 * One array for Raise and one for Call. ( Fold if neither)
	 * The array indexes are:
	 * 		position SB, BB, UTG, MP, CO, BU
	 * 		bet type PREFLOP_BET1, PREFLOP_BET2, PREFLOP_BET3, PREFLOP_BET4, 
	 * 		PREFLOP_ALLIN, PREFLOP_CALL_ALLIN
	 * 
	 * WE always edit 2 ranges at the same time, a Raise range and it's coreresponding
	 * Call range. This is because of the shadow effect, if a hand is in the Raise range
	 * and the Raise range is checked first ( whitch it always will ) then the hands 
	 * in the Raise Range will never be checked in the Call ranbge.
	 * 
	 * Both ranges are in playebility order ( Expected Values ) and hands are added 
	 * to or removed from using a slider.
	 * This may be controversial but it is whay we believe works best.
	 * 
	 * ****************************************************************************** */

	/*-*******************************************************************************
	Constructor
	*********************************************************************************/
	EditRange(String typ, int pos, int betTYpe) {
		hand = -1;
		edit = true;
		fileName = new StringBuilder().append(EvalData.applicationDirectory).append(typ)
				.append("-HandRangeMultiple.ser").toString();
		rangesToEdit(fileName);
		doFrame();
		rangeArrayToButtonColor();

		report1();
		report2();
	}

	/*- Close frame */
	void closeFrame() {
		frame.dispose();
	}

	/*- - Determine range(s) to edit. */
	/*-*******************************************************************************
	* RFead HandRangeMultiple instance from file
	*********************************************************************************/
	private void rangesToEdit(String fileNam) {
		rangeMultiple = rangeMultiple.readFromFile(fileNam);
		range1 = rangeMultiple.rangeArrayRaise[0][0];
		range2 = rangeMultiple.rangeArrayCall[0][0];
		return;
	}

	/*- - Convert range array to button color Sets color in buttonArray. */
	private void rangeArrayToButtonColor() {
		for (int i = 0; i < HANDS; ++i) {
			if (range1.isRangeArray(i) > 0) {
				buttonArr[i].setBackground(range1Color);
			}
		}
		// Add shadow range
		for (int k = 0; k < HANDS; ++k) {
			if (range2.isRangeArray(k) > 0) {
				buttonArr[k].setBackground(range2Color);
			}

		}
	}

	/*- *******************************************************************************
	 * In response to slider moving update range1
	 **********************************************************************************/
	private void updateRange1(int value) {
		range1.clearRange();
		range1.buildRangePlayabilityHands(value);
		range1.updateAfterRangeEdited();
		rangeArrayToButtonColor();
		report1();
	}

	/*- *******************************************************************************
	 * In response to slider moving update range2
	 **********************************************************************************/
	private void updateRange2(int value) {
		range2.clearRange();
		range2.buildRangePlayabilityHands(value);
		range2.subtractRange(range1);
		range2.updateAfterRangeEdited();
		rangeArrayToButtonColor();
		report2();
	}

	/*- - Create the primary frame. */
	private void doFrame() {
		// Primary frame

		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent j0) {
				j0.getWindow().dispose();
			}
		});

		final var panel0 = new JPanel();
		new Font(Font.SERIF, Font.BOLD, 10);
		final var f0 = new Font(Font.SERIF, Font.BOLD, 18);
		final var f2 = new Font(Font.SERIF, Font.BOLD, 16);
		final var f3 = new Font(Font.SERIF, Font.BOLD, 16);
		final var panel1 = new JPanel();
		final var panel2 = new JPanel();
		final var panel3 = new JPanel();
		final var frameDim = new Dimension(300, 350);
		final var frameDimN = new Dimension(200, 150);
		final var panel0Dim = new Dimension(250, 500);
		final var panel0DimX = new Dimension(200, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		if (!edit) {
			frame.setMaximumSize(frameDimN);
			frame.setMinimumSize(frameDimN);
			frame.setLocation(x, y);
			panel0.setMaximumSize(panel0DimX);
			panel0.setMinimumSize(panel0DimX);
			panel0.setSize(200, 200);
			frame.setMaximumSize(panel0DimX);
			frame.setMinimumSize(panel0DimX);
			frame.setSize(200, 200);
		} else {
			frame.setFont(f0);
			frame.setMaximumSize(frameDim);
			frame.setMinimumSize(frameDim);
			frame.setLocation(offset, 350);
			offset += 40;
			if (offset > 500) {
				offset = 100;
			}
			panel0.setMaximumSize(panel0Dim);
			panel0.setMinimumSize(panel0Dim);
			panel0.setSize(300, 400);
		}

		frame.setBackground(Color.WHITE);

		panel0.setLayout(new BoxLayout(panel0, BoxLayout.Y_AXIS));
		panel0.setFont(f0);

		// Hand matrix

		final var panel1Layout = new GridLayout(NUM_ROWS, NUM_COLS);
		panel1.setBackground(Color.WHITE);
		panel1.setLayout(panel1Layout);

		panel1.setFont(f0);
		final var f = new Font("Dialog", Font.BOLD, 10);
		final var fs = new Font("Dialog", Font.BOLD, 2);
		for (int i = 0; i < HANDS; ++i) {
			buttonArr[i] = new JButton(HandRange.getRangeArrayString(i));
			if (!edit) {
				buttonArr[i].setFont(fs);
			} else {
				buttonArr[i].addActionListener(new Listener());
				buttonArr[i].setFont(f);
			}
			buttonArr[i].setBackground(none);
			buttonArr[i].setForeground(Color.BLACK);
			panel1.add(buttonArr[i]);
		}
		panel0.add(panel1);

		if (edit) {
			panel3.setSize(300, 40);
			panel3.setBackground(Color.gray);
			panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
			final var dim3 = new Dimension(300, 40);
			panel3.setMaximumSize(dim3);
			panel3.setMinimumSize(dim3);
			combos1.setFont(f3);
			comboPer1.setFont(f3);
			gaps.setFont(f3);
			play.setFont(f3);
			combos1.setBackground(open);
			comboPer1.setBackground(open);
			gaps.setBackground(open);
			play.setBackground(open);

			panel3.add(combos1);
			panel3.add(comboPer1);
			panel3.add(play);
			panel3.add(gaps);
			panel0.add(panel3);

			final var panel4 = new JPanel();
			panel4.setBackground(Color.WHITE);
			panel4.setLayout(new BoxLayout(panel4, BoxLayout.X_AXIS));
			combos2.setFont(f3);
			comboPer2.setFont(f3);
			play2.setFont(f3);
			gaps2.setFont(f3);
			combos2.setBackground(call);
			comboPer2.setBackground(call);
			play2.setBackground(call);
			gaps2.setBackground(call);
			final var dim = new Dimension(300, 40);
			panel4.setMaximumSize(dim);
			panel4.setMinimumSize(dim);
			panel4.add(combos2);
			panel4.add(comboPer2);
			panel4.add(play2);
			panel4.add(gaps2);
			panel0.add(panel4);

			final var panel5 = new JPanel();
			panel5.setBackground(Color.gray);
			panel5.setLayout(new BoxLayout(panel5, BoxLayout.X_AXIS));

			final var panel6 = new JPanel();
			panel6.setBackground(Color.gray);
			panel6.setLayout(new BoxLayout(panel6, BoxLayout.X_AXIS));
			final var dim6 = new Dimension(300, 40);
			panel6.setMaximumSize(dim6);
			panel6.setMinimumSize(dim6);
		}

		// Control buttons
		if (edit) {
			panel2.setBackground(Color.gray);
			panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
			buttonSave.setBackground(control);
			buttonSave.addActionListener(new Listener());
			buttonSave.setFont(f2);
			panel2.add(buttonSave);
			panel0.add(panel2);
		}

		// Create a slider with initial value playability high
		final var slider1 = new JSlider(JSlider.HORIZONTAL, 0, 100, range1.lowPlayability);
		slider1.setMajorTickSpacing(10);
		slider1.setPaintTicks(true);
		slider1.setPaintLabels(true);
		slider1.setBackground(open);

		// Add a change listener to the slider1
		slider1.addChangeListener((ChangeEvent event) -> {
			// Get the source of the event (the JSlider)
			final var source = (JSlider) event.getSource();

			// Get the current value of the slider1
			final int value = source.getValue();
			updateRange1(value);

		});

		// Create a slider with initial value playability high
		final var slider2 = new JSlider(JSlider.HORIZONTAL, 0, 100, range1.lowPlayability);
		slider2.setMajorTickSpacing(10);
		slider2.setPaintTicks(true);
		slider2.setPaintLabels(true);
		slider2.setBackground(call);
		// Create a label to display the value
		// JLabel label = new JLabel("Current value: 50");

		// Add a change listener to the slider2
		slider2.addChangeListener((ChangeEvent event) -> {
			// Get the source of the event (the JSlider)
			final var source = (JSlider) event.getSource();

			// Get the current value of the slider2
			final int value = source.getValue();
			updateRange2(value);
		});

		// Add the sliders to the panel
		panel0.add(slider1);
		panel0.add(slider2);

		frame.add(panel0);
		frame.pack();
		frame.setVisible(true);
	}

	/*- - Save range files. */
	private void saveRangeFiles() {
		rangeMultiple.writeToFile(file1);
	}

	private void report1() {
		final var pattern = "##.0";
		final var df = new DecimalFormat(pattern);
		String st1;
		final int i;
		final double per1;
		final int j;

		i = range1.getCombos();
		st1 = new StringBuilder().append(" ").append(i).append(" ").toString();
		combos1.setText(st1);
		per1 = range1.getRangePercent();
		st1 = df.format(per1);
		comboPer1.setText(new StringBuilder().append(" ").append(st1).append("%    ").toString());
		combos1.setBackground(range1Color);
		comboPer1.setBackground(range1Color);
		play.setBackground(range1Color);
		j = range1.countHands();
		st1 = new StringBuilder().append(" ").append(j).append(" ").toString();
		play.setText(st1);

		st1 = df.format(per1 / range2.getRangePercent() * 100)
				+ " Percentage of Preflop Range is the actual percentage";
		msg.setText(st1);
	}

	private void report2() {
		final var pattern = "##.0";
		final var df = new DecimalFormat(pattern);
		String st1;
		final int i;
		final double per2;
		final int j;

		i = range2.getCombos();
		st1 = new StringBuilder().append(" ").append(i).append(" ").toString();
		combos2.setText(st1);
		per2 = range2.getRangePercent();
		st1 = df.format(per2);
		comboPer2.setText(new StringBuilder().append(" ").append(st1).append("%    ").toString());
		combos2.setBackground(range2Color);
		comboPer2.setBackground(range2Color);
		play2.setBackground(range2Color);
		j = range2.countHands();
		st1 = new StringBuilder().append(" ").append(j).append(" ").toString();
		play2.setText(st1);
	}

	/*- - Respond to mouse clicks. */
	private class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent a0) {
			final var action = a0.getActionCommand();
			if ("Save".equals(action)) {
				saveRangeFiles();
				return;
			}
			report1();
			report2();

		}
	}

}
