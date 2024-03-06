package holdemevaluator;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ReportMadeDrawPercentages implements Constants {
	/*-  ******************************************************************************
	 * This Class will display a report of the percentages of different hands by type.
	 * Drawing hands and Made Hands.
	 * Preflop simulation makes a very significant difference in the results.
	* The hole cards that we start with will obviously make a big difference in the results.
	* For example, players in real life will fold small cards and non suited cards and 
	* non connected cards much more frequently.
	* Their position also makes a big difference. UTG has a much smaller range 
	* than the Button.
	* The hand ranges used in Preflop simulation are pretty accurate for the various
	* positions and player types.
	*
	* @author PEAK_
	 ****************************************************************************** */

	private static final int DRAW_COL = DRAW_SIZE;
	private static final int MADE_COL = MADE_FLUSH + 1;
	static Font ff1 = new Font(Font.SERIF, Font.BOLD, 14);
	private JFrame frame0 = null;
	private JFrame frame1 = null;
	private JFrame frame2 = null;
	private JFrame frame3 = null;
	private JFrame frame4 = null;
	private JFrame frame5 = null;
	private JFrame frame6 = null;
	private JFrame frame7 = null;
	private JFrame frame8 = null;
	private JFrame frame9 = null;

	private JTable table0 = null;
	private JTable table1 = null;
	private JTable table2 = null;
	private JTable table3 = null;
	private JTable table4 = null;
	private JTable table5 = null;
	private JTable table6 = null;
	private JTable table7 = null;
	private JTable table8 = null;
	private JTable table9 = null;

	private DefaultTableModel tableModel0 = null;
	private DefaultTableModel tableModel1 = null;
	private DefaultTableModel tableModel2 = null;
	private DefaultTableModel tableModel3 = null;
	private DefaultTableModel tableModel4 = null;
	private DefaultTableModel tableModel5 = null;
	private DefaultTableModel tableModel6 = null;
	private DefaultTableModel tableModel7 = null;
	private DefaultTableModel tableModel8 = null;
	private DefaultTableModel tableModel9 = null;

	private JScrollPane pane0 = null;
	private JScrollPane pane1 = null;
	private JScrollPane pane2 = null;
	private JScrollPane pane3 = null;
	private JScrollPane pane4 = null;
	private JScrollPane pane5 = null;
	private JScrollPane pane6 = null;
	private JScrollPane pane7 = null;
	private JScrollPane pane8 = null;
	private JScrollPane pane9 = null;

	private final Object[] columnsDraw = { "Index", "Flop", "Turn " };

	private final Object[][] dataDraw = new Object[DRAW_SIZE][4];

	private final Object[] columnsMade = { "Index", "Flop", "Turn ", "River" };

	private final Object[][] dataMade = new Object[MADE_SIZE][5];

	private final Object[] columnsDrawCompare = { "Index", "Flop ", "Flop  Simulation", "Difference", "Turn ",
			"Turn Simulation", "Difference" };

	private final Object[][] dataDrawCompare = new Object[DRAW_SIZE][8];

	private final Object[] columnsMadeCompare = { "Index", "Flop ", "Flop Simulation", "Difference", "Turn",
			"Turn Simulation", "Difference", "River ", "River Simulation", "Difference" };
	private final Object[][] dataMadeCompare = new Object[MADE_SIZE][12];

	private final Object[] columnsDrawFreezeSeat1 = { "Index", "Random", "AcKc", "Difference", "Random ", "AcKc",
			"Difference" };

	private final Object[][] dataDrawFreezeSeat1 = new Object[DRAW_SIZE][8];

	private final Object[] columnsMadeFreezeSeat1 = { "Index", "Random", "AcKc", "Difference", "Random", "AcKc",
			"Difference", "Random", "AcKc", "Difference" };
	private final Object[][] dataMadeFreezeSeat1 = new Object[MADE_SIZE][12];

	private final Object[] columnsDrawFreezeSeat1and2 = { "Index", "Random", "AcKc", "Difference", "QhQs", "Difference",
			"Random ", "AcKc", "Difference", "QhQs", "Difference" };
	private final Object[][] dataDrawFreezeSeat1and2 = new Object[DRAW_SIZE][8];

	private final Object[] columnsMadeFreezeSeat1and2 = { "Index", "Random", "AcKc", "Difference", "QhQs", "Difference",
			"Random ", "AcKc", "Difference", "QhQs", "Difference", "Random", "AcKc", "Difference", "QhQs",
			"Difference" };
	private final Object[][] dataMadeFreezeSeat1and2 = new Object[MADE_SIZE][12];

	private final Object[] columnsDrawFreezeBoard = { "Index", "Random", "ThTd3s", "Difference", "Random", "ThTd3sAs",
			"Difference" };

	private final Object[][] dataDrawFreezeBoard = new Object[DRAW_SIZE][8];

	private final Object[] columnsMadeFreezeBoard = { "Index", "Random", "ThTd3s", "Difference", "Random", "ThTd3sAs",
			"Difference", "Random	", "ThTd3sAsKs", "Difference" };
	private final Object[][] dataMadeFreezeBoard = new Object[MADE_SIZE][12];

	// Constructor
	ReportMadeDrawPercentages() {

	}

	/*-*************************************************************************************************************
	 * This method does the following:
	 *
	 * 1. It checks if the JFrame is null. If so, it creates a new JFrame, sets its properties,
	 *    and creates a JScrollPane.
	 * 2. It initializes a PriorityQueue to hold the cells with the five highest integer values.
	 *    These cells are determined by parsing the string value of each cell (excluding column 0) to an integer.
	 * 3. It iterates over the data array (excluding column 0), checking each cell. If the PriorityQueue is not full,
	 *    it adds the cell to the queue. If the PriorityQueue is full, but the current cell has
	 *    a higher value than the smallest value in the queue, it removes the smallest cell and
	 *    adds the current cell.
	 * 4. It creates a JTable, passing in the data and column arrays. It overrides the prepareRenderer method
	 *    of the JTable to highlight cells (excluding cells in column 0) that are one of the five highest values.
	 * 5. Finally, it adds the JTable to the JScrollPane, adds the JScrollPane to the JFrame, and displays the JFrame.
	 *
	 * Arg0 - Column for frame
	 * Arg1 - Row for frame
	 * arg2 - Title of the frame
	****************************************************************************************************************/
	void reportDraw(int c, int r, String title) {
		if (this.frame0 == null) {
			this.frame0 = new JFrame(title);
			this.frame0.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.frame0.setLocation(c, r);
			this.frame0.setPreferredSize(new Dimension(650, 460));
		}

		this.tableModel0 = new DefaultTableModel(this.dataDraw, this.columnsDraw);
		this.table0 = new JTable(this.tableModel0);
		this.table0.setFont(ff1);
		this.table0.setRowHeight(25);

		this.table0.getColumnModel().getColumn(0).setPreferredWidth(90);
		for (var i = 0; i < DRAW_SIZE; ++i) {
			this.table0.setValueAt(DRAW_ST[i], i, 0);
		}
		for (var i = 0; i < DRAW_SIZE; ++i) {
			String st = "";
			double ds = 0.;
			if (EvalData.preflopSimulation) {
				ds = DrawMadeWonPercentages.typeArraySimDrawFlop[i]
						/ (double) DrawMadeWonPercentages.typeCountSimDrawFlop * 100;
				st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimDrawFlop[i]
						/ (double) DrawMadeWonPercentages.typeCountSimDrawFlop) * 100);
				st = Format.formatPer(ds);
				this.table0.setValueAt(st, i, 1);

				ds = DrawMadeWonPercentages.typeArraySimDrawTurn[i]
						/ (double) DrawMadeWonPercentages.typeCountSimDrawTurn * 100;
				st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimDrawTurn[i]
						/ (double) DrawMadeWonPercentages.typeCountSimDrawTurn) * 100);
				st = Format.formatPer(ds);
				this.table0.setValueAt(st, i, 2);
			} else {
				ds = DrawMadeWonPercentages.typeArrayDrawFlop[i] / (double) DrawMadeWonPercentages.typeCountDrawFlop
						* 100;
				st = Format.formatPer(((double) DrawMadeWonPercentages.typeArrayDrawFlop[i]
						/ (double) DrawMadeWonPercentages.typeCountDrawFlop) * 100);
				st = Format.formatPer(ds);
				this.table0.setValueAt(st, i, 1);

				ds = DrawMadeWonPercentages.typeArrayDrawTurn[i] / (double) DrawMadeWonPercentages.typeCountDrawTurn
						* 100;
				st = Format.formatPer(((double) DrawMadeWonPercentages.typeArrayDrawTurn[i]
						/ (double) DrawMadeWonPercentages.typeCountDrawTurn) * 100);
				st = Format.formatPer(ds);
				this.table0.setValueAt(st, i, 2);
			}
		}

		this.pane0 = new JScrollPane(this.table0);
		this.frame0.add(this.pane0);
		this.frame0.pack();
		this.frame0.setVisible(true);
	}

	/*-**********************************************************************************
	 * Same as above but for Made hands
	 ********************************************************************************** */
	void reportMade(int c, int r, String title) {
		if (this.frame1 == null) {
			this.frame1 = new JFrame(title);
			this.frame1.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.frame1.setLocation(c, r);
			this.frame1.setPreferredSize(new Dimension(900, 610));
		}
		this.tableModel1 = new DefaultTableModel(this.dataMade, this.columnsMade);
		this.table1 = new JTable(this.tableModel1);
		this.table1.setFont(ff1);
		this.table1.setRowHeight(25);
		this.table1.getColumnModel().getColumn(0).setPreferredWidth(90);

		for (var i = 0; i < MADE_SIZE; ++i) {
			this.table1.setValueAt(MADE_ST[i], i, 0);
		}
		double dsF = 0.;
		double dsT = 0.;
		double dsR = 0.;

		for (var i = 0; i < MADE_SIZE; ++i) {
			String st = "";
			double ds = 0.;
			if (EvalData.preflopSimulation) {
				ds = DrawMadeWonPercentages.typeArraySimMadeFlop[i]
						/ (double) DrawMadeWonPercentages.typeCountSimMadeFlop * 100;
				dsF += ds;
				st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimMadeFlop[i]
						/ (double) DrawMadeWonPercentages.typeCountSimMadeFlop) * 100);
				this.table1.setValueAt(st, i, 1);
				ds = DrawMadeWonPercentages.typeArraySimMadeTurn[i]
						/ (double) DrawMadeWonPercentages.typeCountSimMadeTurn * 100;
				dsT += ds;
				st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimMadeTurn[i]
						/ (double) DrawMadeWonPercentages.typeCountSimMadeTurn) * 100);
				this.table1.setValueAt(st, i, 2);
				ds = DrawMadeWonPercentages.typeArraySimMadeRiver[i]
						/ (double) DrawMadeWonPercentages.typeCountSimMadeRiver * 100;
				dsR += ds;
				st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimMadeRiver[i]
						/ (double) DrawMadeWonPercentages.typeCountSimMadeRiver) * 100);
				this.table1.setValueAt(st, i, 3);
			} else {
				ds = DrawMadeWonPercentages.typeArrayMadeFlop[i] / (double) DrawMadeWonPercentages.typeCountMadeFlop
						* 100;
				dsF += ds;
				st = Format.formatPer(((double) DrawMadeWonPercentages.typeArrayMadeFlop[i]
						/ (double) DrawMadeWonPercentages.typeCountMadeFlop) * 100);
				this.table1.setValueAt(st, i, 1);
				ds = DrawMadeWonPercentages.typeArrayMadeTurn[i] / (double) DrawMadeWonPercentages.typeCountMadeTurn
						* 100;
				dsT += ds;
				st = Format.formatPer(((double) DrawMadeWonPercentages.typeArrayMadeTurn[i]
						/ (double) DrawMadeWonPercentages.typeCountMadeTurn) * 100);
				this.table1.setValueAt(st, i, 2);
				ds = DrawMadeWonPercentages.typeArrayMadeRiver[i] / (double) DrawMadeWonPercentages.typeCountMadeRiver
						* 100;
				dsR += ds;
				st = Format.formatPer(((double) DrawMadeWonPercentages.typeArrayMadeRiver[i]
						/ (double) DrawMadeWonPercentages.typeCountMadeRiver) * 100);
				this.table1.setValueAt(st, i, 3);
			}
		}
		System.out.println("//SSS " + Format.formatPer(dsF) + " " + Format.formatPer(dsT) + " " + Format.formatPer(dsR)
				+ " " + DrawMadeWonPercentages.typeCountSimMadeFlop + " " + DrawMadeWonPercentages.typeCountSimMadeTurn
				+ " " + DrawMadeWonPercentages.typeCountSimMadeRiver);
		this.pane1 = new JScrollPane(this.table1);
		this.frame1.add(this.pane1);
		this.frame1.pack();
		this.frame1.setVisible(true);
	}

	/*-*************************************************************************************************************
	 * This method does the following:
	 *
	 * 1. It checks if the JFrame is null. If so, it creates a new JFrame, sets its properties,
	 *    and creates a JScrollPane.
	 * 2. It initializes a PriorityQueue to hold the cells with the five highest integer values.
	 *    These cells are determined by parsing the string value of each cell (excluding column 0) to an integer.
	 * 3. It iterates over the data array (excluding column 0), checking each cell. If the PriorityQueue is not full,
	 *    it adds the cell to the queue. If the PriorityQueue is full, but the current cell has
	 *    a higher value than the smallest value in the queue, it removes the smallest cell and
	 *    adds the current cell.
	 * 4. It creates a JTable, passing in the data and column arrays. It overrides the prepareRenderer method
	 *    of the JTable to highlight cells (excluding cells in column 0) that are one of the five highest values.
	 * 5. Finally, it adds the JTable to the JScrollPane, adds the JScrollPane to the JFrame, and displays the JFrame.
	 *
	 * Arg0 - Column for frame
	 * Arg1 - Row for frame
	 * arg2 - Title of the frame
	****************************************************************************************************************/
	void reportDrawCompare(int c, int r, String title) {
		if (this.frame2 == null) {
			this.frame2 = new JFrame(title);
			this.frame2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.frame2.setLocation(c, r);
			this.frame2.setPreferredSize(new Dimension(650, 460));
		}

		this.tableModel2 = new DefaultTableModel(this.dataDrawCompare, this.columnsDrawCompare);
		this.table2 = new JTable(this.tableModel2);
		this.table2.setFont(ff1);
		this.table2.setRowHeight(25);

		this.table2.getColumnModel().getColumn(0).setPreferredWidth(90);
		for (var i = 0; i < DRAW_SIZE; ++i) {
			this.table2.setValueAt(DRAW_ST[i], i, 0);
		}
		for (var i = 0; i < DRAW_SIZE; ++i) {
			String st = "";
			double d = 0.;
			double ds = 0.;
			double diff = 0.;
			d = DrawMadeWonPercentages.typeArrayDrawFlop[i] / (double) DrawMadeWonPercentages.typeCountDrawFlop * 100;
			st = Format.formatPer(d);
			this.table2.setValueAt(st, i, 1);

			ds = DrawMadeWonPercentages.typeArraySimDrawFlop[i] / (double) DrawMadeWonPercentages.typeCountSimDrawFlop
					* 100;
			st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimDrawFlop[i]
					/ (double) DrawMadeWonPercentages.typeCountSimDrawFlop) * 100);
			this.table2.setValueAt(st, i, 2);
			st = Format.formatPer(d);
			diff = d - ds;
			st = Format.formatPer(diff);
			this.table2.setValueAt(st, i, 3);

			d = DrawMadeWonPercentages.typeArrayDrawTurn[i] / (double) DrawMadeWonPercentages.typeCountDrawTurn * 100;
			st = Format.formatPer(d);
			this.table2.setValueAt(st, i, 4);
			ds = DrawMadeWonPercentages.typeArraySimDrawTurn[i] / (double) DrawMadeWonPercentages.typeCountSimDrawTurn
					* 100;
			st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimDrawTurn[i]
					/ (double) DrawMadeWonPercentages.typeCountSimDrawTurn) * 100);
			this.table2.setValueAt(st, i, 5);
			st = Format.formatPer(d);
			diff = d - ds;
			st = Format.formatPer(diff);
			this.table2.setValueAt(st, i, 6);
		}

		this.pane2 = new JScrollPane(this.table2);
		this.frame2.add(this.pane2);
		this.frame2.pack();
		this.frame2.setVisible(true);
	}

	/*-**********************************************************************************
	 * Same as above but for Made hands
	 ********************************************************************************** */
	void reportMadeCompare(int c, int r, String title) {
		if (this.frame3 == null) {
			this.frame3 = new JFrame(title);
			this.frame3.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.frame3.setLocation(c, r);
			this.frame3.setPreferredSize(new Dimension(900, 610));
		}
		this.tableModel3 = new DefaultTableModel(this.dataMadeCompare, this.columnsMadeCompare);
		this.table3 = new JTable(this.tableModel3);
		this.table3.setFont(ff1);
		this.table3.setRowHeight(25);
		this.table3.getColumnModel().getColumn(0).setPreferredWidth(90);

		for (var i = 0; i < MADE_SIZE; ++i) {
			this.table3.setValueAt(MADE_ST[i], i, 0);
		}
		for (var i = 0; i < MADE_SIZE; ++i) {
			String st = "";
			double d = 0.;
			double ds = 0.;
			double diff = 0.;
			d = DrawMadeWonPercentages.typeArrayMadeFlop[i] / (double) DrawMadeWonPercentages.typeCountMadeFlop * 100;
			st = Format.formatPer(d);
			this.table3.setValueAt(st, i, 1);
			ds = DrawMadeWonPercentages.typeArraySimMadeFlop[i] / (double) DrawMadeWonPercentages.typeCountSimMadeFlop
					* 100;
			st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimMadeFlop[i]
					/ (double) DrawMadeWonPercentages.typeCountSimMadeFlop) * 100);
			this.table3.setValueAt(st, i, 2);
			st = Format.formatPer(d);
			diff = d - ds;
			st = Format.formatPer(diff);
			this.table3.setValueAt(st, i, 3);

			d = DrawMadeWonPercentages.typeArrayMadeTurn[i] / (double) DrawMadeWonPercentages.typeCountMadeTurn * 100;
			st = Format.formatPer(d);
			this.table3.setValueAt(st, i, 4);
			ds = DrawMadeWonPercentages.typeArraySimMadeTurn[i] / (double) DrawMadeWonPercentages.typeCountSimMadeTurn
					* 100;
			st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimMadeTurn[i]
					/ (double) DrawMadeWonPercentages.typeCountSimMadeTurn) * 100);
			this.table3.setValueAt(st, i, 5);
			st = Format.formatPer(d);
			diff = d - ds;
			st = Format.formatPer(diff);
			this.table3.setValueAt(st, i, 6);

			d = DrawMadeWonPercentages.typeArrayMadeRiver[i] / (double) DrawMadeWonPercentages.typeCountMadeRiver * 100;
			st = Format.formatPer(d);
			this.table3.setValueAt(st, i, 7);
			ds = DrawMadeWonPercentages.typeArraySimMadeRiver[i] / (double) DrawMadeWonPercentages.typeCountSimMadeRiver
					* 100;
			st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimMadeRiver[i]
					/ (double) DrawMadeWonPercentages.typeCountSimMadeRiver) * 100);
			this.table3.setValueAt(st, i, 8);
			st = Format.formatPer(d);
			diff = d - ds;
			st = Format.formatPer(diff);
			this.table3.setValueAt(st, i, 9);
		}
		this.pane3 = new JScrollPane(this.table3);
		this.frame3.add(this.pane3);
		this.frame3.pack();
		this.frame3.setVisible(true);
	}

	/*-*************************************************************************************************************
	*
	****************************************************************************************************************/
	void reportDrawCompareFreezeSeat1(int c, int r, String title) {
		if (this.frame4 == null) {
			this.frame4 = new JFrame(title);
			this.frame4.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.frame4.setLocation(c, r);
			this.frame4.setPreferredSize(new Dimension(650, 460));
		}

		this.tableModel4 = new DefaultTableModel(this.dataDrawFreezeSeat1, this.columnsDrawFreezeSeat1);
		this.table4 = new JTable(this.tableModel4);
		this.table4.setFont(ff1);
		this.table4.setRowHeight(25);

		this.table4.getColumnModel().getColumn(0).setPreferredWidth(90);
		for (var i = 0; i < DRAW_SIZE; ++i) {
			this.table4.setValueAt(DRAW_ST[i], i, 0);
		}
		for (var i = 0; i < DRAW_SIZE; ++i) {
			String st = "";
			double d = 0.;
			double ds = 0.;
			double diff = 0.;
			d = DrawMadeWonPercentages.typeArrayDrawFlop[i] / (double) DrawMadeWonPercentages.typeCountDrawFlop * 100;
			st = Format.formatPer(d);
			this.table4.setValueAt(st, i, 1);
			ds = DrawMadeWonPercentages.typeArraySimDrawFlop[i] / (double) DrawMadeWonPercentages.typeCountSimDrawFlop
					* 100;
			st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimDrawFlop[i]
					/ (double) DrawMadeWonPercentages.typeCountSimDrawFlop) * 100);
			this.table4.setValueAt(st, i, 2);
			st = Format.formatPer(d);
			diff = d - ds;
			st = Format.formatPer(diff);
			this.table4.setValueAt(st, i, 4);
			d = DrawMadeWonPercentages.typeArrayDrawTurn[i] / (double) DrawMadeWonPercentages.typeCountDrawTurn * 100;
			st = Format.formatPer(d);
			this.table4.setValueAt(st, i, 4);
			ds = DrawMadeWonPercentages.typeArraySimDrawTurn[i] / (double) DrawMadeWonPercentages.typeCountSimDrawTurn
					* 100;
			st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimDrawTurn[i]
					/ (double) DrawMadeWonPercentages.typeCountSimDrawTurn) * 100);
			this.table4.setValueAt(st, i, 5);
			st = Format.formatPer(d);
			diff = d - ds;
			st = Format.formatPer(diff);
			this.table4.setValueAt(st, i, 6);
		}

		this.pane4 = new JScrollPane(this.table4);
		this.frame4.add(this.pane4);
		this.frame4.pack();
		this.frame4.setVisible(true);
	}

	/*-*************************************************************************************************************
	* Same as above
	****************************************************************************************************************/
	void reportDrawCompareFreezeSeat1and2(int c, int r, String title) {
		if (this.frame5 == null) {
			this.frame5 = new JFrame(title);
			this.frame5.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.frame5.setLocation(c, r);
			this.frame5.setPreferredSize(new Dimension(650, 460));
		}

		this.tableModel5 = new DefaultTableModel(this.dataDrawFreezeSeat1and2, this.columnsDrawFreezeSeat1and2);
		this.table5 = new JTable(this.tableModel5);
		this.table5.setFont(ff1);
		this.table5.setRowHeight(25);

		this.table5.getColumnModel().getColumn(0).setPreferredWidth(90);
		for (var i = 0; i < DRAW_SIZE; ++i) {
			this.table5.setValueAt(DRAW_ST[i], i, 0);
		}
		for (var i = 0; i < DRAW_SIZE; ++i) {
			String st = "";
			double d = 0.;
			double ds = 0.;
			double diff = 0.;
			d = DrawMadeWonPercentages.typeArrayDrawFlop[i] / (double) DrawMadeWonPercentages.typeCountDrawFlop * 100;
			st = Format.formatPer(d);
			this.table5.setValueAt(st, i, 1);
			ds = DrawMadeWonPercentages.typeArraySimDrawFlop[i] / (double) DrawMadeWonPercentages.typeCountSimDrawFlop
					* 100;
			st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimDrawFlop[i]
					/ (double) DrawMadeWonPercentages.typeCountSimDrawFlop) * 100);
			this.table5.setValueAt(st, i, 2);
			st = Format.formatPer(d);
			diff = d - ds;
			st = Format.formatPer(diff);
			this.table5.setValueAt(st, i, 5);
			d = DrawMadeWonPercentages.typeArrayDrawTurn[i] / (double) DrawMadeWonPercentages.typeCountDrawTurn * 100;
			st = Format.formatPer(d);
			this.table5.setValueAt(st, i, 4);
			ds = DrawMadeWonPercentages.typeArraySimDrawTurn[i] / (double) DrawMadeWonPercentages.typeCountSimDrawTurn
					* 100;
			st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimDrawTurn[i]
					/ (double) DrawMadeWonPercentages.typeCountSimDrawTurn) * 100);
			this.table5.setValueAt(st, i, 5);
			st = Format.formatPer(d);
			diff = d - ds;
			st = Format.formatPer(diff);
			this.table5.setValueAt(st, i, 6);
		}

		this.pane5 = new JScrollPane(this.table5);
		this.frame5.add(this.pane5);
		this.frame5.pack();
		this.frame5.setVisible(true);
	}

	/*-**********************************************************************************
	 * Same as above but for Made hands
	 ********************************************************************************** */
	void reportDrawCompareFreezeBoard(int c, int r, String title) {
		if (this.frame6 == null) {
			this.frame6 = new JFrame(title);
			this.frame6.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.frame6.setLocation(c, r);
			this.frame6.setPreferredSize(new Dimension(900, 610));
		}
		this.tableModel6 = new DefaultTableModel(this.dataDrawFreezeBoard, this.columnsDrawFreezeBoard);
		this.table6 = new JTable(this.tableModel6);
		this.table6.setFont(ff1);
		this.table6.setRowHeight(25);
		this.table6.getColumnModel().getColumn(0).setPreferredWidth(90);

		for (var i = 0; i < MADE_SIZE; ++i) {
			this.table6.setValueAt(MADE_ST[i], i, 0);
		}
		for (var i = 0; i < MADE_SIZE; ++i) {
			String st = "";
			double d = 0.;
			double ds = 0.;
			double diff = 0.;
			d = DrawMadeWonPercentages.typeArrayMadeFlop[i] / (double) DrawMadeWonPercentages.typeCountMadeFlop * 100;
			st = Format.formatPer(d);
			this.table6.setValueAt(st, i, 1);
			ds = DrawMadeWonPercentages.typeArraySimMadeFlop[i] / (double) DrawMadeWonPercentages.typeCountSimMadeFlop
					* 100;
			st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimMadeFlop[i]
					/ (double) DrawMadeWonPercentages.typeCountSimMadeFlop) * 100);
			this.table6.setValueAt(st, i, 2);
			st = Format.formatPer(d);
			diff = d - ds;
			st = Format.formatPer(diff);
			this.table6.setValueAt(st, i, 3);

			d = DrawMadeWonPercentages.typeArrayMadeTurn[i] / (double) DrawMadeWonPercentages.typeCountMadeTurn * 100;
			st = Format.formatPer(d);
			this.table6.setValueAt(st, i, 6);
			ds = DrawMadeWonPercentages.typeArraySimMadeTurn[i] / (double) DrawMadeWonPercentages.typeCountSimMadeTurn
					* 100;
			st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimMadeTurn[i]
					/ (double) DrawMadeWonPercentages.typeCountSimMadeTurn) * 100);
			this.table6.setValueAt(st, i, 5);
			st = Format.formatPer(d);
			diff = d - ds;
			st = Format.formatPer(diff);
			this.table6.setValueAt(st, i, 6);

			d = DrawMadeWonPercentages.typeArrayMadeRiver[i] / (double) DrawMadeWonPercentages.typeCountMadeRiver * 100;
			st = Format.formatPer(d);
			this.table6.setValueAt(st, i, 7);
			ds = DrawMadeWonPercentages.typeArraySimMadeRiver[i] / (double) DrawMadeWonPercentages.typeCountSimMadeRiver
					* 100;
			st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimMadeRiver[i]
					/ (double) DrawMadeWonPercentages.typeCountSimMadeRiver) * 100);
			this.table6.setValueAt(st, i, 8);
			st = Format.formatPer(d);
			diff = d - ds;
			st = Format.formatPer(diff);
			this.table6.setValueAt(st, i, 9);
		}
		this.pane6 = new JScrollPane(this.table6);
		this.frame6.add(this.pane6);
		this.frame6.pack();
		this.frame6.setVisible(true);
	}

	/*-**********************************************************************************
	 * Same as above but for Made hands
	 ********************************************************************************** */
	void reportMadeCompareFreezeSeat1(int c, int r, String title) {
		if (this.frame7 == null) {
			this.frame7 = new JFrame(title);
			this.frame7.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.frame7.setLocation(c, r);
			this.frame7.setPreferredSize(new Dimension(900, 610));
		}
		this.tableModel7 = new DefaultTableModel(this.dataMadeFreezeSeat1, this.columnsMadeFreezeSeat1);
		this.table7 = new JTable(this.tableModel7);
		this.table7.setFont(ff1);
		this.table7.setRowHeight(25);
		this.table7.getColumnModel().getColumn(0).setPreferredWidth(90);

		for (var i = 0; i < MADE_SIZE; ++i) {
			this.table7.setValueAt(MADE_ST[i], i, 0);
		}
		for (var i = 0; i < MADE_SIZE; ++i) {
			String st = "";
			double d = 0.;
			double ds = 0.;
			double diff = 0.;
			d = DrawMadeWonPercentages.typeArrayMadeFlop[i] / (double) DrawMadeWonPercentages.typeCountMadeFlop * 100;
			st = Format.formatPer(d);
			this.table7.setValueAt(st, i, 1);
			ds = DrawMadeWonPercentages.typeArraySimMadeFlop[i] / (double) DrawMadeWonPercentages.typeCountSimMadeFlop
					* 100;
			st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimMadeFlop[i]
					/ (double) DrawMadeWonPercentages.typeCountSimMadeFlop) * 100);
			this.table7.setValueAt(st, i, 2);
			st = Format.formatPer(d);
			diff = d - ds;
			st = Format.formatPer(diff);
			this.table7.setValueAt(st, i, 3);

			d = DrawMadeWonPercentages.typeArrayMadeTurn[i] / (double) DrawMadeWonPercentages.typeCountMadeTurn * 100;
			st = Format.formatPer(d);
			this.table7.setValueAt(st, i, 7);
			ds = DrawMadeWonPercentages.typeArraySimMadeTurn[i] / (double) DrawMadeWonPercentages.typeCountSimMadeTurn
					* 100;
			st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimMadeTurn[i]
					/ (double) DrawMadeWonPercentages.typeCountSimMadeTurn) * 100);
			this.table7.setValueAt(st, i, 5);
			st = Format.formatPer(d);
			diff = d - ds;
			st = Format.formatPer(diff);
			this.table7.setValueAt(st, i, 6);

			d = DrawMadeWonPercentages.typeArrayMadeRiver[i] / (double) DrawMadeWonPercentages.typeCountMadeRiver * 100;
			st = Format.formatPer(d);
			this.table7.setValueAt(st, i, 7);
			ds = DrawMadeWonPercentages.typeArraySimMadeRiver[i] / (double) DrawMadeWonPercentages.typeCountSimMadeRiver
					* 100;
			st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimMadeRiver[i]
					/ (double) DrawMadeWonPercentages.typeCountSimMadeRiver) * 100);
			this.table7.setValueAt(st, i, 8);
			st = Format.formatPer(d);
			diff = d - ds;
			st = Format.formatPer(diff);
			this.table7.setValueAt(st, i, 9);
		}
		this.pane7 = new JScrollPane(this.table7);
		this.frame7.add(this.pane7);
		this.frame7.pack();
		this.frame7.setVisible(true);
	}

	/*-**********************************************************************************
	 * Same as above but for Made hands
	 ********************************************************************************** */
	void reportMadeCompareFreezeSeat1and2(int c, int r, String title) {
		if (this.frame8 == null) {
			this.frame8 = new JFrame(title);
			this.frame8.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.frame8.setLocation(c, r);
			this.frame8.setPreferredSize(new Dimension(900, 610));
		}
		this.tableModel8 = new DefaultTableModel(this.dataMadeFreezeSeat1and2, this.columnsMadeFreezeSeat1and2);
		this.table8 = new JTable(this.tableModel8);
		this.table8.setFont(ff1);
		this.table8.setRowHeight(25);
		this.table8.getColumnModel().getColumn(0).setPreferredWidth(90);

		for (var i = 0; i < MADE_SIZE; ++i) {
			this.table8.setValueAt(MADE_ST[i], i, 0);
		}
		for (var i = 0; i < MADE_SIZE; ++i) {
			String st = "";
			double d = 0.;
			double ds = 0.;
			double diff = 0.;
			d = DrawMadeWonPercentages.typeArrayMadeFlop[i] / (double) DrawMadeWonPercentages.typeCountMadeFlop * 100;
			st = Format.formatPer(d);
			this.table8.setValueAt(st, i, 1);
			ds = DrawMadeWonPercentages.typeArraySimMadeFlop[i] / (double) DrawMadeWonPercentages.typeCountSimMadeFlop
					* 100;
			st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimMadeFlop[i]
					/ (double) DrawMadeWonPercentages.typeCountSimMadeFlop) * 100);
			this.table8.setValueAt(st, i, 2);
			st = Format.formatPer(d);
			diff = d - ds;
			st = Format.formatPer(diff);
			this.table8.setValueAt(st, i, 3);

			d = DrawMadeWonPercentages.typeArrayMadeTurn[i] / (double) DrawMadeWonPercentages.typeCountMadeTurn * 100;
			st = Format.formatPer(d);
			this.table8.setValueAt(st, i, 8);
			ds = DrawMadeWonPercentages.typeArraySimMadeTurn[i] / (double) DrawMadeWonPercentages.typeCountSimMadeTurn
					* 100;
			st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimMadeTurn[i]
					/ (double) DrawMadeWonPercentages.typeCountSimMadeTurn) * 100);
			this.table8.setValueAt(st, i, 5);
			st = Format.formatPer(d);
			diff = d - ds;
			st = Format.formatPer(diff);
			this.table8.setValueAt(st, i, 6);

			d = DrawMadeWonPercentages.typeArrayMadeRiver[i] / (double) DrawMadeWonPercentages.typeCountMadeRiver * 100;
			st = Format.formatPer(d);
			this.table8.setValueAt(st, i, 7);
			ds = DrawMadeWonPercentages.typeArraySimMadeRiver[i] / (double) DrawMadeWonPercentages.typeCountSimMadeRiver
					* 100;
			st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimMadeRiver[i]
					/ (double) DrawMadeWonPercentages.typeCountSimMadeRiver) * 100);
			this.table8.setValueAt(st, i, 8);
			st = Format.formatPer(d);
			diff = d - ds;
			st = Format.formatPer(diff);
			this.table8.setValueAt(st, i, 9);
		}
		this.pane8 = new JScrollPane(this.table8);
		this.frame8.add(this.pane8);
		this.frame8.pack();
		this.frame8.setVisible(true);
	}

	/*-**********************************************************************************
	 * Same as above but for Made hands
	 ********************************************************************************** */
	void reportMadeCompareFreezeBoard(int c, int r, String title) {
		if (this.frame9 == null) {
			this.frame9 = new JFrame(title);
			this.frame9.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.frame9.setLocation(c, r);
			this.frame9.setPreferredSize(new Dimension(900, 610));
		}
		this.tableModel9 = new DefaultTableModel(this.dataMadeFreezeBoard, this.columnsMadeFreezeBoard);
		this.table9 = new JTable(this.tableModel9);
		this.table9.setFont(ff1);
		this.table9.setRowHeight(25);
		this.table9.getColumnModel().getColumn(0).setPreferredWidth(90);

		for (var i = 0; i < MADE_SIZE; ++i) {
			this.table9.setValueAt(MADE_ST[i], i, 0);
		}
		for (var i = 0; i < MADE_SIZE; ++i) {
			String st = "";
			double d = 0.;
			double ds = 0.;
			double diff = 0.;
			d = DrawMadeWonPercentages.typeArrayMadeFlop[i] / (double) DrawMadeWonPercentages.typeCountMadeFlop * 100;
			st = Format.formatPer(d);
			this.table9.setValueAt(st, i, 1);
			ds = DrawMadeWonPercentages.typeArraySimMadeFlop[i] / (double) DrawMadeWonPercentages.typeCountSimMadeFlop
					* 100;
			st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimMadeFlop[i]
					/ (double) DrawMadeWonPercentages.typeCountSimMadeFlop) * 100);
			this.table9.setValueAt(st, i, 2);
			st = Format.formatPer(d);
			diff = d - ds;
			st = Format.formatPer(diff);
			this.table9.setValueAt(st, i, 3);

			d = DrawMadeWonPercentages.typeArrayMadeTurn[i] / (double) DrawMadeWonPercentages.typeCountMadeTurn * 100;
			st = Format.formatPer(d);
			this.table9.setValueAt(st, i, 9);
			ds = DrawMadeWonPercentages.typeArraySimMadeTurn[i] / (double) DrawMadeWonPercentages.typeCountSimMadeTurn
					* 100;
			st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimMadeTurn[i]
					/ (double) DrawMadeWonPercentages.typeCountSimMadeTurn) * 100);
			this.table9.setValueAt(st, i, 5);
			st = Format.formatPer(d);
			diff = d - ds;
			st = Format.formatPer(diff);
			this.table9.setValueAt(st, i, 6);

			d = DrawMadeWonPercentages.typeArrayMadeRiver[i] / (double) DrawMadeWonPercentages.typeCountMadeRiver * 100;
			st = Format.formatPer(d);
			this.table9.setValueAt(st, i, 7);
			ds = DrawMadeWonPercentages.typeArraySimMadeRiver[i] / (double) DrawMadeWonPercentages.typeCountSimMadeRiver
					* 100;
			st = Format.formatPer(((double) DrawMadeWonPercentages.typeArraySimMadeRiver[i]
					/ (double) DrawMadeWonPercentages.typeCountSimMadeRiver) * 100);
			this.table9.setValueAt(st, i, 8);
			st = Format.formatPer(d);
			diff = d - ds;
			st = Format.formatPer(diff);
			this.table9.setValueAt(st, i, 9);
		}
		this.pane9 = new JScrollPane(this.table9);
		this.frame9.add(this.pane9);
		this.frame9.pack();
		this.frame9.setVisible(true);
	}

	/*- *****************************************************************************
	 * Custom renderer
	*********************************************************************************/
	public class CustomRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;
		private final Map<Point, Color> cellColors;

		public CustomRenderer() {
			this.cellColors = new HashMap<>();
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			final var cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
					column);
			final var cellColor = this.cellColors.get(new Point(row, column));
			if (cellColor != null) {
				cellComponent.setBackground(cellColor);
			} else {
				cellComponent.setBackground(table.getBackground());
			}
			return cellComponent;
		}

		public void setColorAt(int row, int col, Color color) {
			this.cellColors.put(new Point(row, col), color);
		}
	}

}
