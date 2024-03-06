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
/*-  ******************************************************************************
 * This Class 
*
*
* @author PEAK_ 
 ****************************************************************************** */

public class IndexArrayReport implements Constants {

	private static final int DRAW_COL = DRAW_SIZE;
	private static final int MADE_COL = MADE_FLUSH + 1;
	static Font ff1 = new Font(Font.SERIF, Font.BOLD, 14);
	private JFrame frame1 = null;
	private JFrame frame2 = null;
	private JFrame frame3 = null;
	private JTable table1 = null;
	private JTable table2 = null;
	private JTable table3 = null;
	private DefaultTableModel tableModel1 = null;
	private DefaultTableModel tableModel2 = null;
	private DefaultTableModel tableModel3 = null;
	private JScrollPane pane1 = null;
	private JScrollPane pane2 = null;
	private JScrollPane pane3 = null;
	private final Object[] columnsDraw = { "Index", "No draw", "Gutshot", "Gutshot+Pair", "Double Gutshot",
			"Gutshot+OESD", "Gutshot+Straight", "Gutshot+Flush", "Straight", "Straight+Pair", "Straight+Flush", "OESD",
			"OESD+Pair", "OESD+Flush", "Flush", "Flush+Pair", "Total", "Normalized" };
	private final Object[][] dataDraw = new Object[50][18];

	private final Object[] columnsMade = { "Index", "No hand", "Board Pair", "Pair below board pair", "Ace High",
			"Overcards", "PP BelowTP", "Weak Pair", "Bottom Pair", "Middle Pair", "Top Pair", "Over Pair",
			"Pair above board pair", "Bottom 2 Pair", "Top-Bot 2 Pair", "Top 2 Pair", "Set", "Straight", "Flush",
			"Full house", "Total", "Normalized" };
	private final Object[][] dataMade = new Object[50][25];

	private final Object[] columnsShowdown = { "Index", "No hand", "Board Pair", "Pair below board pair", "Ace High",
			"Overcards", "PP BelowTP", "Weak Pair", "Bottom Pair", "Middle Pair", "Top Pair", "Over Pair",
			"Pair above board pair", "Bottom 2 Pair", "Top-Bot 2 Pair", "Top 2 Pair", "Set", "Straight", "Flush",
			"Full house", "Total", "Normalized" };
	private final Object[][] dataShowdown = new Object[50][25];

	// Constructor
	IndexArrayReport() {

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
	 * Arg3 - IndexArray object
	****************************************************************************************************************/
	void reportDraw(int c, int r, String title, IndexArrayDrawMadeWin arrays) {
		var rr = 0;
		var cc = 0;
		var w = 0;
		if (this.frame1 == null) {
			this.frame1 = new JFrame(title);
			this.frame1.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.frame1.setLocation(c, r);
			final var x = (arrays.drawArray.length * 30) + 70;
			this.frame1.setPreferredSize(new Dimension(1700, x));
		}

		this.tableModel1 = new DefaultTableModel(this.dataDraw, this.columnsDraw);
		if (this.tableModel1.getRowCount() > 0) {
			for (var i = this.tableModel1.getRowCount() - 1; i > arrays.allArrayRowNames.length + 1; i--) {
				this.tableModel1.removeRow(i);
			}
		}
		this.table1 = new JTable(this.tableModel1);
		this.table1.setFont(ff1);
		this.table1.setRowHeight(25);
		for (final var arrayRowName : arrays.allArrayRowNames) {
			if (arrayRowName.length() > w) {
				w = arrayRowName.length();
			}
		}
		w = (w * 10);
		this.table1.getColumnModel().getColumn(0).setPreferredWidth(w + 50);
		var row = 0;
		var col = 0;
		for (var i = 0; i < arrays.allArrayRowNames.length; ++i) {
			this.table1.setValueAt(arrays.allArrayRowNames[i], row, 0);
			col = 1;
			for (var j = 0; j < DRAW_COL; ++j) {
				if (arrays.drawArray[i][j] >= 0) {
					this.table1.setValueAt(Format.formatPer(arrays.drawArrayPer[i][j]), row, col);
				}
				this.table1.setValueAt(Format.formatPer(arrays.drawColTotalPer[j]), arrays.drawArray.length, col);
				col++;
			}
			this.table1.setValueAt(Format.formatPer(arrays.drawRowTotalPer[row]), row, col);
			col = 1;
			++row;
		}

		row = 0;
		for (var i = 0; i < arrays.allArrayRowNames.length; ++i) {
			this.table1.setValueAt(arrays.allArrayRowNames[i], row, 0);
			col = 1;
			for (var j = 0; j < DRAW_COL; ++j) {
				if (arrays.drawArray[i][j] >= 0) {
					this.table1.setValueAt(Format.format(arrays.drawArray[i][j]), row, col);
				}
				col++;
			}
			col = 1;
			++row;
		}
		this.table1.setValueAt("%", row++, 0);
		this.table1.setValueAt("Normalized", row, 0);

		for (var i = 0; i < arrays.allArrayRowNames.length; ++i) {
			this.table1.setValueAt(Format.format(arrays.drawColTotalNormalized[i]), arrays.drawArray.length, col);
			row = arrays.allArrayRowNames.length + 1;
			this.table1.setValueAt(Format.format(arrays.drawRowTotalNormalized[i]), row, i + 1);
		}

		final var customRendererDraw = new CustomRenderer();
		for (var i = 0; i < arrays.bestDraw5Row.length; ++i) {
			rr = arrays.bestDraw5Row[i];
			cc = arrays.bestDraw5Col[i] + 1;
			customRendererDraw.setColorAt(rr, cc, Color.GREEN);
			this.table1.setDefaultRenderer(Object.class, customRendererDraw);
		}
		for (var i = 0; i < arrays.worstDraw5Row.length; ++i) {
			rr = arrays.worstDraw5Row[i];
			cc = arrays.worstDraw5Col[i] + 1;
			customRendererDraw.setColorAt(rr, cc, Color.RED);
			this.table1.setDefaultRenderer(Object.class, customRendererDraw);
		}
		for (final int bestDrawRow : arrays.bestDrawRows) {
			rr = bestDrawRow;
			cc = DRAW_COL + 1;
			customRendererDraw.setColorAt(rr, cc, Color.GREEN);
			this.table1.setDefaultRenderer(Object.class, customRendererDraw);
		}
		for (final int bestDrawCol : arrays.bestDrawCols) {
			rr = row;
			cc = bestDrawCol + 1;
			customRendererDraw.setColorAt(rr, cc, Color.GREEN);
			this.table1.setDefaultRenderer(Object.class, customRendererDraw);
		}

		this.pane1 = new JScrollPane(this.table1);
		this.frame1.add(this.pane1);
		this.frame1.pack();
		this.frame1.setVisible(true);
	}

	/*-**********************************************************************************
	 * Same as above but for Made hands
	 ********************************************************************************** */
	void reportMade(int c, int r, String title, IndexArrayDrawMadeWin arrays) {
		var rr = 0;
		var cc = 0;
		var w = 0;
		if (this.frame2 == null) {
			this.frame2 = new JFrame(title);
			this.frame2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.frame2.setLocation(c, r);
			final var x = (arrays.madeArray.length * 30) + 70;
			this.frame2.setPreferredSize(new Dimension(1700, x));
		}

		this.tableModel2 = new DefaultTableModel(this.dataMade, this.columnsMade);
		if (this.tableModel2.getRowCount() > 0) {
			for (var i = this.tableModel2.getRowCount() - 1; i > arrays.allArrayRowNames.length + 1; i--) {
				this.tableModel2.removeRow(i);
			}
		}
		this.tableModel2 = new DefaultTableModel(this.dataMade, this.columnsMade);
		for (final var arrayRowName : arrays.allArrayRowNames) {
			if (arrayRowName.length() > w) {
				w = arrayRowName.length();
			}
		}
		w = (w * 10);

		this.table2 = new JTable(this.tableModel2);
		this.table2.setFont(ff1);
		this.table2.setRowHeight(25);
		this.table2.getColumnModel().getColumn(0).setPreferredWidth(w + 50);
		var row = 0;
		var col = 0;
		for (var i = 0; i < arrays.allArrayRowNames.length; ++i) {
			this.table2.setValueAt(arrays.allArrayRowNames[i], row, 0);
			col = 1;
			for (var j = 0; j < MADE_COL; ++j) {
				if (arrays.madeArray[i][j] >= 0) {
					this.table2.setValueAt(Format.formatPer(arrays.madeArrayPer[i][j]), row, col);
				}
				this.table2.setValueAt(Format.formatPer(arrays.madeColTotalPer[j]), arrays.madeArray.length, col);
				++col;
			}
			this.table2.setValueAt(Format.formatPer(arrays.madeRowTotalPer[row]), row, col);
			col = 1;
			++row;
		}
		this.table2.setValueAt("%", row++, 0);
		this.table2.setValueAt("Normalized", row, 0);

		for (var i = 0; i < arrays.allArrayRowNames.length; ++i) {
			this.table2.setValueAt(Format.format(arrays.madeColTotalNormalized[i]), arrays.madeArray.length, col);
			row = arrays.allArrayRowNames.length + 1;
			this.table2.setValueAt(Format.format(arrays.madeRowTotalNormalized[i]), row, i + 1);
		}

		final var customRendererMade = new CustomRenderer();
		for (var i = 0; i < arrays.bestMade5Row.length; ++i) {
			rr = arrays.bestMade5Row[i];
			cc = arrays.bestMade5Col[i] + 1;
			customRendererMade.setColorAt(rr, cc, Color.GREEN);
			this.table2.setDefaultRenderer(Object.class, customRendererMade);
		}
		for (var i = 0; i < arrays.worstMade5Row.length; ++i) {
			rr = arrays.worstMade5Row[i];
			cc = arrays.worstMade5Col[i] + 1;
			customRendererMade.setColorAt(rr, cc, Color.RED);
			this.table2.setDefaultRenderer(Object.class, customRendererMade);
		}
		for (final int bestMadeRow : arrays.bestMadeRows) {
			rr = bestMadeRow;
			cc = DRAW_COL + 1;
			customRendererMade.setColorAt(rr, cc, Color.GREEN);
			this.table2.setDefaultRenderer(Object.class, customRendererMade);
		}
		for (final int bestMadeCol : arrays.bestMadeCols) {
			rr = row;
			cc = bestMadeCol + 1;
			customRendererMade.setColorAt(rr, cc, Color.GREEN);
			this.table2.setDefaultRenderer(Object.class, customRendererMade);
		}
		this.pane2 = new JScrollPane(this.table2);
		this.frame2.add(this.pane2);
		this.frame2.pack();
		this.frame2.setVisible(true);
	}

	/*-**********************************************************************************
	 * Same as above but for Showdown hands
	 ********************************************************************************** */
	void reportShowdown(int c, int r, String title, IndexArrayDrawMadeWin arrays) {
		var rr = 0;
		var cc = 0;
		var w = 0;
		if (this.frame3 == null) {
			this.frame3 = new JFrame(title);
			this.frame3.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.frame3.setLocation(c, r);
			final var x = (arrays.showdownArray.length * 30) + 70;
			this.frame3.setPreferredSize(new Dimension(1700, x));
		}

		this.tableModel3 = new DefaultTableModel(this.dataShowdown, this.columnsShowdown);
		if (this.tableModel3.getRowCount() > 0) {
			for (var i = this.tableModel3.getRowCount() - 1; i > arrays.allArrayRowNames.length + 1; i--) {
				this.tableModel3.removeRow(i);
			}
		}

		this.tableModel3 = new DefaultTableModel(this.dataShowdown, this.columnsShowdown);
		for (final var arrayRowName : arrays.allArrayRowNames) {
			if (arrayRowName.length() > w) {
				w = arrayRowName.length();
			}
		}
		w = (w * 10);

		this.table3 = new JTable(this.tableModel3);
		this.table3.setFont(ff1);
		this.table3.setRowHeight(25);
		this.table3.getColumnModel().getColumn(0).setPreferredWidth(w + 50);
		var row = 0;
		var col = 0;
		for (var i = 0; i < arrays.allArrayRowNames.length; ++i) {
			this.table3.setValueAt(arrays.allArrayRowNames[i], row, 0);
			col = 1;
			for (var j = 0; j < MADE_COL; ++j) {
				if (arrays.showdownArray[i][j] >= 0) {
					this.table3.setValueAt(Format.formatPer(arrays.showdownArrayPer[i][j]), row, col);
				}
				this.table3.setValueAt(Format.formatPer(arrays.showdownColTotalPer[j]), arrays.showdownArray.length,
						col);
				col++;
			}
			this.table3.setValueAt(Format.formatPer(arrays.showdownRowTotalPer[row]), row, col);
			col = 1;
			++row;
		}
		this.table3.setValueAt("%", row++, 0);
		this.table3.setValueAt("Normalized", row, 0);

		for (var i = 0; i < arrays.allArrayRowNames.length; ++i) {
			this.table2.setValueAt(Format.format(arrays.madeColTotalNormalized[i]), arrays.madeArray.length, col);
			row = arrays.allArrayRowNames.length + 1;
			this.table2.setValueAt(Format.format(arrays.madeRowTotalNormalized[i]), row, i + 1);
		}

		final var customRendererShowdown = new CustomRenderer();
		for (var i = 0; i < arrays.bestShowdown5Row.length; ++i) {
			rr = arrays.bestShowdown5Row[i];
			cc = arrays.bestShowdown5Col[i] + 1;
			customRendererShowdown.setColorAt(rr, cc, Color.GREEN);
			this.table3.setDefaultRenderer(Object.class, customRendererShowdown);
		}
		for (var i = 0; i < arrays.worstShowdown5Row.length; ++i) {
			rr = arrays.worstShowdown5Row[i];
			cc = arrays.worstShowdown5Col[i] + 1;
			customRendererShowdown.setColorAt(rr, cc, Color.RED);
			this.table3.setDefaultRenderer(Object.class, customRendererShowdown);
		}
		for (final int bestShowdownRow : arrays.bestShowdownRows) {
			rr = bestShowdownRow;
			cc = DRAW_COL + 1;
			customRendererShowdown.setColorAt(rr, cc, Color.GREEN);
			this.table3.setDefaultRenderer(Object.class, customRendererShowdown);
		}
		for (final int bestShowdownCol : arrays.bestShowdownCols) {
			rr = row;
			cc = bestShowdownCol + 1;
			customRendererShowdown.setColorAt(rr, cc, Color.GREEN);
			this.table3.setDefaultRenderer(Object.class, customRendererShowdown);
		}
		this.pane3 = new JScrollPane(this.table3);
		this.frame3.add(this.pane3);
		this.frame3.pack();
		this.frame3.setVisible(true);
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
