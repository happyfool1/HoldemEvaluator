package holdemevaluator;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Comparator;
import java.util.PriorityQueue;

/*- _**********************************************************************************************
 * 
 * This Class displays reports of HML related data.**All methods have 2 arguments,the column 
 * and the row where the frame is*to be displayed.*
 *@author PEAK
 * _**********************************************************************************************/

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class ReportHML implements Constants {

	private static JFrame frame1 = null;
	private static JFrame frame2 = null;
	private static JFrame frame3 = null;
	private static JFrame frame4 = null;
	private static JFrame frame5 = null;
	private static JFrame frame6 = null;
	private static JFrame frame7 = null;
	private static JFrame frame8 = null;
	private static JTable table1 = null;
	private static JTable table2 = null;
	private static JTable table3 = null;
	private static JTable table4 = null;
	private static JTable table5 = null;
	private static JTable table6 = null;
	private static JTable table7 = null;
	private static JTable table8 = null;
	private static DefaultTableModel tableModel1 = null;
	private static DefaultTableModel tableModel2 = null;
	private static DefaultTableModel tableModel3 = null;
	private static DefaultTableModel tableModel4 = null;
	private static DefaultTableModel tableModel5 = null;
	private static DefaultTableModel tableModel6 = null;
	private static DefaultTableModel tableModel7 = null;
	private static DefaultTableModel tableModel8 = null;
	static JScrollPane pane1 = null;
	static JScrollPane pane2 = null;
	static JScrollPane pane3 = null;
	static JScrollPane pane4 = null;
	static JScrollPane pane5 = null;
	static JScrollPane pane6 = null;
	static JScrollPane pane7 = null;
	static JScrollPane pane8 = null;
	private static final int DRAW_COL = DRAW_SIZE;
	private static final int DRAW_TOTAL_COL = DRAW_SIZE + 1;
	private static final int MADE_COL = MADE_FLUSH;
	private static final int MADE_TOTAL_COL = MADE_FLUSH + 2;
	static Font ff1 = new Font(Font.SERIF, Font.BOLD, 14);
	private static final Object[] columnsDrawFlop = { "Index", "No draw", "Gutshot", "Straight", "OESD", "Flush",
			"Flush OESD", "Total" };
	private static final Object[][] dataDrawFlop = { { "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "", "", "" } };
	private static final Object[] columnsMadeFlop = { "Index", "No hand", "Brd Pair", "Bottom Pair", "Middle Pair",
			"Top Pair", "Over Pair", "Brd 2Pair", "2Pair & Under", "2Pair & Over", "2Pair Bottom", "2Pair Top-Bot",
			"2Pair Top 2", "Brd Set", "Set", "Straight", "Flush", "Total" };
	private static final Object[][] dataMadeFlop = {
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" } };
	private static final Object[] columnsShowdownFlop = { "Index", "No hand", "Brd Pair", "Bottom Pair", "Middle Pair",
			"Top Pair", "Over Pair", "Brd 2Pair", "2Pair & Under", "2Pair & Over", "2Pair Bottom", "2Pair Top-Bot",
			"2Pair Top 2", "Brd Set", "Set", "Straight", "Flush", "Total" };
	private static final Object[][] dataShowdownFlop = {
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" } };
	private static final Object[] columnsDrawTurn = { "Index", "No draw", "Gutshot", "Straight", "OESD", "Flush",
			"Flush OESD", "Total" };
	private static final Object[][] dataDrawTurn = { { "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }, { "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" } };
	private static final Object[] columnsMadeTurn = { "Index", "No hand", "Brd Pair", "Bottom Pair", "Middle Pair",
			"Top Pair", "Over Pair", "Brd 2Pair", "2Pair & Under", "2Pair & Over", "2Pair Bottom", "2Pair Top-Bot",
			"2Pair Top 2", "Brd Set", "Set", "Straight", "Flush", "Total" };
	private static final Object[][] dataMadeTurn = {
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", } };
	private static final Object[] columnsShowdownTurn = { "Index", "No hand", "Brd Pair", "Bottom Pair", "Middle Pair",
			"Top Pair", "Over Pair", "Brd 2Pair", "2Pair & Under", "2Pair & Over", "2Pair Bottom", "2Pair Top-Bot",
			"2Pair Top 2", "Brd Set", "Set", "Straight", "Flush", "Total" };
	private static final Object[][] dataShowdownTurn = {
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", } };
	private static final Object[] columnsMadeRiver = { "Index", "No hand", "Brd Pair", "Bottom Pair", "Middle Pair",
			"Top Pair", "Over Pair", "Brd 2Pair", "2Pair & Under", "2Pair & Over", "2Pair Bottom", "2Pair Top-Bot",
			"2Pair Top 2", "Brd Set", "Set", "Straight", "Flush", "Total" };
	private static final Object[][] dataMadeRiver = {
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", } };
	private static final Object[] columnsShowdownRiver = { "Index", "No hand", "Brd Pair", "Bottom Pair", "Middle Pair",
			"Top Pair", "Over Pair", "Brd 2Pair", "2Pair & Under", "2Pair & Over", "2Pair Bottom", "2Pair Top-Bot",
			"2Pair Top 2", "Brd Set", "Set", "Straight", "Flush", "Total" };
	private static final Object[][] dataShowdownRiver = {
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", },
			{ "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", } };
	static boolean reportHandsDone = false;

	private ReportHML() {
		throw new IllegalStateException("Utility class");
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
	****************************************************************************************************************/
	static void reportDrawFlop(int c, int r) {
		if (frame1 == null) {
			frame1 = new JFrame("Flop HML Drawing hands");
			frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame1.setLocation(c, r);
			frame1.setPreferredSize(new Dimension(750, 330));
		}

		// Create a PriorityQueue to hold the cells with the top 5 values
		final PriorityQueue<Cell> largestCells = new PriorityQueue<>(5, Comparator.comparingInt(cell -> cell.value));
		final PriorityQueue<Cell> smallestCells = new PriorityQueue<>(5, Comparator.comparingInt(cell -> cell.value));

		tableModel1 = new DefaultTableModel(dataDrawFlop, columnsDrawFlop);
		table1 = new JTable(tableModel1);
		table1.setFont(ff1);
		table1.setRowHeight(25);

		double sum = 0.0;
		int row = 0;
		int col = 0;
		for (int i = 0; i < HML_FLOP_SIZE; ++i) {
			table1.setValueAt(HML_FLOP_ST[i], row, 0);
			col = 1;
			sum = 0.0;
			for (int j = 0; j < DRAW_COL; ++j) {
				if (IndexArrays.hmlDrawFlop[i][j] > 0) {
					final double per = ((double) IndexArrays.hmlDrawFlop[i][j] / (double) IndexArrays.hmlCountFlop)
							* 100.0;
					sum += per;

					table1.setValueAt(Format.formatPer(per), row, col);
					table1.setValueAt(Format.formatPer(sum), row, DRAW_TOTAL_COL);
					final var cellValueStr = "   " + String.valueOf(IndexArrays.hmlDrawFlop[i][j]);
					// Parse the cell value to an integer and add to the priority queue
					if (col > 1) {
						try {
							final int cellValue = Integer.parseInt(cellValueStr.trim());
							// For smallest cells
							if (smallestCells.size() < 5) {
								smallestCells.offer(new Cell(row, col, cellValue));
							} else if (cellValue < smallestCells.peek().value) {
								smallestCells.poll();
								smallestCells.offer(new Cell(row, col, cellValue));
							}
							// For largest cells
							if (largestCells.size() < 5) {
								largestCells.offer(new Cell(row, col, cellValue));
							} else if (cellValue > largestCells.peek().value) {
								largestCells.poll();
								largestCells.offer(new Cell(row, col, cellValue));
							}
						} catch (NumberFormatException e) {
							// Handle or ignore if cell doesn't contain a valid integer
						}
					}
				}
				col++;
			}
			col = 1;
			++row;
		}
		// Add custom cell renderer
		table1.setDefaultRenderer(Object.class, new TableCellRenderer() {
			private DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final var c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				if (column != 0 && containsCell(largestCells, row, column)) {
					c.setBackground(Color.GREEN);
				} else if (column != 0 && containsCell(smallestCells, row, column)) {
					c.setBackground(Color.RED);
				} else {
					c.setBackground(Color.CYAN);
				}
				return c;
			}
		});

		pane1 = new JScrollPane(table1);
		frame1.add(pane1);
		frame1.pack();
		frame1.setVisible(true);
	}

	/*-**********************************************************************************
	* Same as above but for Made hands
	********************************************************************************** */
	static void reportMadeFlop(int c, int r) {
		if (frame2 == null) {
			frame2 = new JFrame("Flop HML Made hands");
			frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame2.setLocation(c, r);
			frame2.setPreferredSize(new Dimension(1500, 330));
		}

		// Create a PriorityQueue to hold the cells with the top 5 values
		final PriorityQueue<Cell> largestCells = new PriorityQueue<>(5, Comparator.comparingInt(cell -> cell.value));
		final PriorityQueue<Cell> smallestCells = new PriorityQueue<>(5, Comparator.comparingInt(cell -> cell.value));
		tableModel2 = new DefaultTableModel(dataMadeFlop, columnsMadeFlop);
		table2 = new JTable(tableModel2);
		table2.setFont(ff1);
		table2.setRowHeight(25);
		double sumx = 0.0;
		double sum = 0.0;
		int row = 0;
		int col = 0;
		for (int i = 0; i < HML_FLOP_SIZE; ++i) {
			table2.setValueAt(HML_FLOP_ST[i], row, 0);
			col = 1;
			sum = 0.0;
			for (int j = 0; j < MADE_COL; ++j) {
				if (IndexArrays.hmlMadeFlop[i][j] > 0) {
					final double per = ((double) IndexArrays.hmlMadeFlop[i][j] / (double) IndexArrays.hmlCountFlop)
							* 100.0;
					sum += per;
					sumx += per;
					table2.setValueAt(Format.formatPer(per), row, col);
					table2.setValueAt(Format.formatPer(sum), row, MADE_TOTAL_COL);
					final var cellValueStr = "   " + String.valueOf(IndexArrays.hmlMadeFlop[i][j]);
					// Parse the cell value to an integer and add to the priority queue
					if (col > 1) {
						try {
							final int cellValue = Integer.parseInt(cellValueStr.trim());
							// For smallest cells
							if (smallestCells.size() < 5) {
								smallestCells.offer(new Cell(row, col, cellValue));
							} else if (cellValue < smallestCells.peek().value) {
								smallestCells.poll();
								smallestCells.offer(new Cell(row, col, cellValue));
							}
							// For largest cells
							if (largestCells.size() < 5) {
								largestCells.offer(new Cell(row, col, cellValue));
							} else if (cellValue > largestCells.peek().value) {
								largestCells.poll();
								largestCells.offer(new Cell(row, col, cellValue));
							}
						} catch (NumberFormatException e) {
							// Handle or ignore if cell doesn't contain a valid integer
						}
					}
				}
				col++;
			}
			col = 1;
			++row;
		}
		System.out.println("made sumx " + sumx);
		// Add custom cell renderer
		table2.setDefaultRenderer(Object.class, new TableCellRenderer() {
			private DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final var c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				if (column != 0 && containsCell(largestCells, row, column)) {
					c.setBackground(Color.GREEN);
				} else if (column != 0 && containsCell(smallestCells, row, column)) {
					c.setBackground(Color.RED);
				} else {
					c.setBackground(Color.CYAN);
				}
				return c;
			}
		});

		pane2 = new JScrollPane(table2);
		frame2.add(pane2);
		frame2.pack();
		frame2.setVisible(true);
	}

	/*-**********************************************************************************
	* Same as above but for Made hands
	********************************************************************************** */
	static void reportShowdownFlop(int c, int r) {
		if (frame3 == null) {
			frame3 = new JFrame("Showdown Flop HML Winner hands");
			frame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame3.setLocation(c, r);
			frame3.setPreferredSize(new Dimension(1300, 330));
		}

		// Create a PriorityQueue to hold the cells with the top 5 values
		final PriorityQueue<Cell> largestCells = new PriorityQueue<>(5, Comparator.comparingInt(cell -> cell.value));
		final PriorityQueue<Cell> smallestCells = new PriorityQueue<>(5, Comparator.comparingInt(cell -> cell.value));
		tableModel3 = new DefaultTableModel(dataShowdownFlop, columnsShowdownFlop);
		table3 = new JTable(tableModel3);
		table3.setFont(ff1);
		table3.setRowHeight(25);

		double sum = 0.0;
		int row = 0;
		int col = 0;
		for (int i = 0; i < HML_FLOP_SIZE; ++i) {
			table3.setValueAt(HML_FLOP_ST[i], row, 0);
			col = 1;
			sum = 0.0;

			for (int j = 0; j < MADE_COL; ++j) {
				if (IndexArrays.hmlShowdownFlop[i][j] > 0) {
					final double per = ((double) IndexArrays.hmlShowdownFlop[i][j] / (double) IndexArrays.showdownCount)
							* 100.0;
					sum += per;

					table3.setValueAt(Format.formatPer(per), row, col);
					table3.setValueAt(Format.formatPer(sum), row, MADE_TOTAL_COL);
					final var cellValueStr = "   " + String.valueOf(IndexArrays.hmlShowdownFlop[i][j]);
					// Parse the cell value to an integer and add to the priority queue

					if (col > 1) {
						try {
							final int cellValue = Integer.parseInt(cellValueStr.trim());
							// For smallest cells
							if (smallestCells.size() < 5) {
								smallestCells.offer(new Cell(row, col, cellValue));
							} else if (cellValue < smallestCells.peek().value) {
								smallestCells.poll();
								smallestCells.offer(new Cell(row, col, cellValue));
							}
							// For largest cells
							if (largestCells.size() < 5) {
								largestCells.offer(new Cell(row, col, cellValue));
							} else if (cellValue > largestCells.peek().value) {
								largestCells.poll();
								largestCells.offer(new Cell(row, col, cellValue));
							}
						} catch (NumberFormatException e) {
							// Handle or ignore if cell doesn't contain a valid integer
						}
					}
				}
				col++;
			}
			col = 1;
			++row;
		}

		// Add custom cell renderer
		table3.setDefaultRenderer(Object.class, new TableCellRenderer() {
			private DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final var c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				if (column != 0 && containsCell(largestCells, row, column)) {
					c.setBackground(Color.GREEN);
				} else if (column != 0 && containsCell(smallestCells, row, column)) {
					c.setBackground(Color.RED);
				} else {
					c.setBackground(Color.CYAN);
				}
				return c;
			}
		});

		pane3 = new JScrollPane(table3);
		frame3.add(pane3);
		frame3.pack();
		frame3.setVisible(true);
	}

	/*-**********************************************************************************
	* Same as above but for Made hands
	********************************************************************************** */
	static void reportDrawTurn(int c, int r) {
		if (frame4 == null) {
			frame4 = new JFrame("Turn HML Drawing hands");
			frame4.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame4.setLocation(c, r);
			frame4.setPreferredSize(new Dimension(750, 430));
		}

		// Create a PriorityQueue to hold the cells with the top 5 values
		final PriorityQueue<Cell> largestCells = new PriorityQueue<>(5, Comparator.comparingInt(cell -> cell.value));
		final PriorityQueue<Cell> smallestCells = new PriorityQueue<>(5, Comparator.comparingInt(cell -> cell.value));
		tableModel4 = new DefaultTableModel(dataDrawTurn, columnsDrawTurn);
		table4 = new JTable(tableModel4);
		table4.setFont(ff1);
		table4.setRowHeight(25);

		double sum = 0.0;
		int row = 0;
		int col = 0;
		for (int i = 0; i < HML_TURN_SIZE; ++i) {
			table4.setValueAt(HML_TURN_ST[i], row, 0);
			col = 1;
			sum = 0.0;
			for (int j = 0; j < DRAW_COL; ++j) {
				if (IndexArrays.hmlDrawTurn[i][j] > 0) {
					final double per = ((double) IndexArrays.hmlDrawTurn[i][j] / (double) IndexArrays.hmlCountTurn)
							* 100.0;
					sum += per;

					table4.setValueAt(Format.formatPer(per), row, col);
					table4.setValueAt(Format.formatPer(sum), row, DRAW_TOTAL_COL);
					final var cellValueStr = "   " + String.valueOf(IndexArrays.hmlDrawTurn[i][j]);
					// Parse the cell value to an integer and add to the priority queue
					if (col > 1) {
						try {
							final int cellValue = Integer.parseInt(cellValueStr.trim());
							// For smallest cells
							if (smallestCells.size() < 5) {
								smallestCells.offer(new Cell(row, col, cellValue));
							} else if (cellValue < smallestCells.peek().value) {
								smallestCells.poll();
								smallestCells.offer(new Cell(row, col, cellValue));
							}
							// For largest cells
							if (largestCells.size() < 5) {
								largestCells.offer(new Cell(row, col, cellValue));
							} else if (cellValue > largestCells.peek().value) {
								largestCells.poll();
								largestCells.offer(new Cell(row, col, cellValue));
							}
						} catch (NumberFormatException e) {
							// Handle or ignore if cell doesn't contain a valid integer
						}
					}
				}
				col++;
			}
			col = 1;
			++row;
		}

		// Add custom cell renderer
		table4.setDefaultRenderer(Object.class, new TableCellRenderer() {
			private DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final var c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				if (column != 0 && containsCell(largestCells, row, column)) {
					c.setBackground(Color.GREEN);
				} else if (column != 0 && containsCell(smallestCells, row, column)) {
					c.setBackground(Color.RED);
				} else {
					c.setBackground(Color.CYAN);
				}
				return c;
			}
		});

		pane4 = new JScrollPane(table4);
		frame4.add(pane4);
		frame4.pack();
		frame4.setVisible(true);
	}

	/*-**********************************************************************************
	* Same as above but for Made hands
	********************************************************************************** */
	static void reportMadeTurn(int c, int r) {
		if (frame5 == null) {
			frame5 = new JFrame("Turn HML Made hands");
			frame5.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame5.setLocation(c, r);
			frame5.setPreferredSize(new Dimension(1300, 440));
		}

		// Create a PriorityQueue to hold the cells with the top 5 values
		final PriorityQueue<Cell> largestCells = new PriorityQueue<>(5, Comparator.comparingInt(cell -> cell.value));
		final PriorityQueue<Cell> smallestCells = new PriorityQueue<>(5, Comparator.comparingInt(cell -> cell.value));
		tableModel5 = new DefaultTableModel(dataMadeTurn, columnsMadeTurn);
		table5 = new JTable(tableModel5);
		table5.setFont(ff1);
		table5.setRowHeight(25);

		double sum = 0.0;
		int row = 0;
		int col = 0;
		for (int i = 0; i < HML_TURN_SIZE; ++i) {
			table5.setValueAt(HML_TURN_ST[i], row, 0);
			col = 1;
			sum = 0.0;
			for (int j = 0; j < MADE_COL; ++j) {
				if (IndexArrays.hmlMadeTurn[i][j] > 0) {
					final double per = ((double) IndexArrays.hmlMadeTurn[i][j] / (double) IndexArrays.hmlCountTurn)
							* 100.0;
					sum += per;

					table5.setValueAt(Format.formatPer(per), row, col);
					table5.setValueAt(Format.formatPer(sum), row, MADE_TOTAL_COL);
					final var cellValueStr = "   " + String.valueOf(IndexArrays.hmlMadeTurn[i][j]);
					// Parse the cell value to an integer and add to the priority queue
					if (col > 1) {
						try {
							final int cellValue = Integer.parseInt(cellValueStr.trim());
							// For smallest cells
							if (smallestCells.size() < 5) {
								smallestCells.offer(new Cell(row, col, cellValue));
							} else if (cellValue < smallestCells.peek().value) {
								smallestCells.poll();
								smallestCells.offer(new Cell(row, col, cellValue));
							}
							// For largest cells
							if (largestCells.size() < 5) {
								largestCells.offer(new Cell(row, col, cellValue));
							} else if (cellValue > largestCells.peek().value) {
								largestCells.poll();
								largestCells.offer(new Cell(row, col, cellValue));
							}
						} catch (NumberFormatException e) {
							// Handle or ignore if cell doesn't contain a valid integer
						}
					}
				}
				col++;
			}
			col = 1;
			++row;
		}

		// Add custom cell renderer
		table5.setDefaultRenderer(Object.class, new TableCellRenderer() {
			private DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final var c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				if (column != 0 && containsCell(largestCells, row, column)) {
					c.setBackground(Color.GREEN);
				} else {
					c.setBackground(Color.CYAN);
				}
				return c;
			}
		});

		pane5 = new JScrollPane(table5);
		frame5.add(pane5);
		frame5.pack();
		frame5.setVisible(true);
	}

	/*-**********************************************************************************
	* Same as above but for Made hands
	********************************************************************************** */
	static void reportShowdownTurn(int c, int r) {
		if (frame6 == null) {
			frame6 = new JFrame("Showdown Turn HML Winner hands");
			frame6.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame6.setLocation(c, r);
			frame6.setPreferredSize(new Dimension(1300, 440));
		}

		// Create a PriorityQueue to hold the cells with the top 5 values
		final PriorityQueue<Cell> largestCells = new PriorityQueue<>(5, Comparator.comparingInt(cell -> cell.value));
		final PriorityQueue<Cell> smallestCells = new PriorityQueue<>(5, Comparator.comparingInt(cell -> cell.value));
		tableModel6 = new DefaultTableModel(dataShowdownTurn, columnsShowdownTurn);
		table6 = new JTable(tableModel6);
		table6.setFont(ff1);
		table6.setRowHeight(25);

		double sum = 0.0;
		int row = 0;
		int col = 0;
		for (int i = 0; i < HML_TURN_SIZE; ++i) {
			table6.setValueAt(HML_TURN_ST[i], row, 0);
			col = 1;
			for (int j = 0; j < MADE_COL; ++j) {
				if (IndexArrays.hmlShowdownTurn[i][j] > 0) {
					final double per = ((double) IndexArrays.hmlShowdownTurn[i][j] / (double) IndexArrays.showdownCount)
							* 100.0;
					sum += per;

					table6.setValueAt(Format.formatPer(per), row, col);
					table6.setValueAt(Format.formatPer(sum), row, MADE_TOTAL_COL);
					final var cellValueStr = "   " + String.valueOf(IndexArrays.hmlShowdownTurn[i][j]);
					// Parse the cell value to an integer and add to the priority queue
					if (col > 1) {
						try {
							final int cellValue = Integer.parseInt(cellValueStr.trim());
							// For smallest cells
							if (smallestCells.size() < 5) {
								smallestCells.offer(new Cell(row, col, cellValue));
							} else if (cellValue < smallestCells.peek().value) {
								smallestCells.poll();
								smallestCells.offer(new Cell(row, col, cellValue));
							}
							// For largest cells
							if (largestCells.size() < 5) {
								largestCells.offer(new Cell(row, col, cellValue));
							} else if (cellValue > largestCells.peek().value) {
								largestCells.poll();
								largestCells.offer(new Cell(row, col, cellValue));
							}
						} catch (NumberFormatException e) {
							// Handle or ignore if cell doesn't contain a valid integer
						}
					}
				}
				col++;
			}
			col = 1;
			++row;
		}

		// Add custom cell renderer
		table6.setDefaultRenderer(Object.class, new TableCellRenderer() {
			private DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final var c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				if (column != 0 && containsCell(largestCells, row, column)) {
					c.setBackground(Color.GREEN);
				} else if (column != 0 && containsCell(smallestCells, row, column)) {
					c.setBackground(Color.RED);
				} else {
					c.setBackground(Color.CYAN);
				}
				return c;
			}
		});
		pane6 = new JScrollPane(table6);
		frame6.add(pane6);
		frame6.pack();
		frame6.setVisible(true);
	}

	/*-**********************************************************************************
	* Same as above but for Made hands
	********************************************************************************** */
	static void reportMadeRiver(int c, int r) {
		if (frame7 == null) {
			frame7 = new JFrame("River HML Made hands");
			frame7.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame7.setLocation(c, r);
			frame7.setPreferredSize(new Dimension(1300, 600));
		}

		// Create a PriorityQueue to hold the cells with the top 5 values
		final PriorityQueue<Cell> largestCells = new PriorityQueue<>(5, Comparator.comparingInt(cell -> cell.value));
		final PriorityQueue<Cell> smallestCells = new PriorityQueue<>(5, Comparator.comparingInt(cell -> cell.value));
		tableModel7 = new DefaultTableModel(dataMadeRiver, columnsMadeRiver);
		table7 = new JTable(tableModel7);
		table7.setFont(ff1);
		table7.setRowHeight(25);

		double sum = 0.0;
		int row = 0;
		int col = 0;
		for (int i = 0; i < HML_RIVER_SIZE; ++i) {
			table7.setValueAt(HML_RIVER_ST[i], row, 0);
			col = 1;
			sum = 0.0;
			for (int j = 0; j < MADE_COL; ++j) {
				if (IndexArrays.hmlMadeRiver[i][j] > 0) {
					final double per = ((double) IndexArrays.hmlMadeRiver[i][j] / (double) IndexArrays.hmlCountRiver)
							* 100.0;
					sum += per;

					table7.setValueAt(Format.formatPer(per), row, col);
					table7.setValueAt(Format.formatPer(sum), row, MADE_TOTAL_COL);
					final var cellValueStr = "   " + String.valueOf(IndexArrays.hmlMadeRiver[i][j]);
					// Parse the cell value to an integer and add to the priority queue
					if (col > 1) {
						try {
							final int cellValue = Integer.parseInt(cellValueStr.trim());
							// For smallest cells
							if (smallestCells.size() < 5) {
								smallestCells.offer(new Cell(row, col, cellValue));
							} else if (cellValue < smallestCells.peek().value) {
								smallestCells.poll();
								smallestCells.offer(new Cell(row, col, cellValue));
							}
							// For largest cells
							if (largestCells.size() < 5) {
								largestCells.offer(new Cell(row, col, cellValue));
							} else if (cellValue > largestCells.peek().value) {
								largestCells.poll();
								largestCells.offer(new Cell(row, col, cellValue));
							}
						} catch (NumberFormatException e) {
							// Handle or ignore if cell doesn't contain a valid integer
						}
					}
				}
				col++;
			}
			col = 1;
			++row;
		}

		// Add custom cell renderer
		table7.setDefaultRenderer(Object.class, new TableCellRenderer() {
			private DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final var c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				if (column != 0 && containsCell(largestCells, row, column)) {
					c.setBackground(Color.GREEN);
				} else if (column != 0 && containsCell(smallestCells, row, column)) {
					c.setBackground(Color.RED);
				} else {
					c.setBackground(Color.CYAN);
				}
				return c;
			}
		});

		pane7 = new JScrollPane(table7);
		frame7.add(pane7);
		frame7.pack();
		frame7.setVisible(true);
	}

	/*-**********************************************************************************
	* Same as above but for Made hands
	********************************************************************************** */
	static void reportShowdownRiver(int c, int r) {
		if (frame8 == null) {
			frame8 = new JFrame("Showdown River HML Winner hands");
			frame8.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame8.setLocation(c, r);
			frame8.setPreferredSize(new Dimension(1300, 600));
		}

		// Create a PriorityQueue to hold the cells with the top 5 values
		final PriorityQueue<Cell> largestCells = new PriorityQueue<>(5, Comparator.comparingInt(cell -> cell.value));
		final PriorityQueue<Cell> smallestCells = new PriorityQueue<>(5, Comparator.comparingInt(cell -> cell.value));
		tableModel8 = new DefaultTableModel(dataShowdownRiver, columnsShowdownRiver);
		table8 = new JTable(tableModel8);
		table8.setFont(ff1);
		table8.setRowHeight(25);

		double sum = 0.0;
		int row = 0;
		int col = 0;
		for (int i = 0; i < HML_RIVER_SIZE; ++i) {
			table8.setValueAt(HML_RIVER_ST[i], row, 0);
			col = 1;
			sum = 0.0;
			for (int j = 0; j < MADE_COL; ++j) {
				if (IndexArrays.hmlShowdownRiver[i][j] > 0) {
					final double per = ((double) IndexArrays.hmlShowdownRiver[i][j]
							/ (double) IndexArrays.showdownCount) * 100.0;
					sum += per;

					table8.setValueAt(Format.formatPer(per), row, col);
					table8.setValueAt(Format.formatPer(sum), row, MADE_TOTAL_COL);
					final var cellValueStr = "   " + String.valueOf(IndexArrays.hmlShowdownRiver[i][j]);
					// Parse the cell value to an integer and add to the priority queue
					try {
						final int cellValue = Integer.parseInt(cellValueStr.trim());
						// For smallest cells
						if (smallestCells.size() < 5) {
							smallestCells.offer(new Cell(row, col, cellValue));
						} else if (cellValue < smallestCells.peek().value) {
							smallestCells.poll();
							smallestCells.offer(new Cell(row, col, cellValue));
						}
						// For largest cells
						if (largestCells.size() < 5) {
							largestCells.offer(new Cell(row, col, cellValue));
						} else if (cellValue > largestCells.peek().value) {
							largestCells.poll();
							largestCells.offer(new Cell(row, col, cellValue));
						}
					} catch (NumberFormatException e) {
						// Handle or ignore if cell doesn't contain a valid integer
					}
				}
				col++;
			}
			col = 1;
			++row;
		}

		// Add custom cell renderer
		table8.setDefaultRenderer(Object.class, new TableCellRenderer() {
			private DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final var c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				if (column != 0 && containsCell(largestCells, row, column)) {
					c.setBackground(Color.GREEN);
				} else if (column != 0 && containsCell(smallestCells, row, column)) {
					c.setBackground(Color.RED);
				} else {
					c.setBackground(Color.CYAN);
				}
				return c;
			}
		});

		pane8 = new JScrollPane(table8);
		frame8.add(pane8);
		frame8.pack();
		frame8.setVisible(true);
	}

	/*-**********************************************************************************
	* Helper method
	********************************************************************************** */
	private static boolean containsCell(PriorityQueue<Cell> cells, int row, int column) {
		for (var cell : cells) {
			if (cell.row == row && cell.column == column) {
				return true;
			}
		}
		return false;
	}

	/*-**********************************************************************************
	* Helper method
	********************************************************************************** */
	static class Cell {
		int row;
		int column;
		int value;

		public Cell(int row, int column, int value) {
			this.row = row;
			this.column = column;
			this.value = value;
		}
	}

}
