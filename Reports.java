package holdemevaluator;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Reports implements Constants {
	/*- **************************************************************************** 
	* This Class is used by the GUI Class to display detailed data in a pop up 
	* window.
	* There are 2 display methods:
	* 		report(x,y)  Reports all ( almost ) data collected by Analyze in a scroll
	* 		panel. 
	* 		reportBoard(x,y)  Reports data associated with board. 
	* 
	* @author PEAK_
	*******************************************************************************/

	private static JFrame frame1 = null;
	private static JTable table1 = null;
	static DefaultTableModel tableModel1 = null;
	private static JFrame frame2 = null;
	private static JTable table2 = null;
	static DefaultTableModel tableModel2 = null;
	private static JFrame frame3 = null;
	private static JTable table3 = null;
	static DefaultTableModel tableModel3 = null;

	private static final Object[] columns3 = { "" };
	private static final Object[][] data3 = new Object[30000][1];

	private Reports() {
		throw new IllegalStateException("Utility class");
	}

	/*- **************************************************************************** 
	*  Displays a report of all data collected by Analyze.
	 * All data is in EvalData.both
	 * 
	 * Arg0 - Column for frame
	 * Arg1 - Row for frame
	*******************************************************************************/
	static void reportPreflop(int c, int r) {
		DefaultTableModel tableModel3 = null;
		JScrollPane pane3 = null;
		final var ff3 = new Font(Font.SERIF, Font.BOLD, 14);

		// Check if the display window already exists
		if (frame3 == null) {
			frame3 = new JFrame(String.valueOf(EvalData.seat));
			frame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame3.setLocation(c, r);
			frame3.setPreferredSize(new Dimension(1000, 900));
			frame3.setTitle("Hand History");
			tableModel3 = new DefaultTableModel(data3, columns3);
			table3 = new JTable(tableModel3);
			table3.setFont(ff3);
			table3.setRowHeight(25);
			pane3 = new JScrollPane(table3);
			table3.getColumnModel().getColumn(0).setPreferredWidth(140);
			table3.setBackground(IVORY);
			frame3.add(pane3);
			frame3.pack();
			frame3.setVisible(true);
		} else {
			// Update existing window content
			frame3.setTitle(String.valueOf(EvalData.seat));
			table3.setModel(new DefaultTableModel(data3, columns3));
			table3.setBackground(IVORY);
		}
		int row = 0;
		// read .txt file
		final var buffer = FileUtils
				.readTxtFileIntoString(EvalData.applicationDirectory + "\\HandHistory\\HHSimulation.txt");
		if (buffer == null) {
			Logger.log("Error \\HandHistory\\HHSimulation.txt  File empty or missing "
					+ "\\HandHistory\\HHSimulation.txt");
			return;
		}
		// break String into array
		final var lines = buffer.split("\n");
		for (int i = 0; i < lines.length; ++i) {
			table3.setValueAt(lines[i], row, 0);
			++row;
			if (i > data3.length - 50) {
				break;
			}
		}
	}

	// Report helper
	/*- **************************************************************************** 
	*  Helper method to convert a Card[] to a String
	*******************************************************************************/
	private static String cardArrayString(Card[] cards) {
		var st = "";
		for (var card : cards) {
			if (card == null) {
				return st;
			} else {
				st += card.toString();
			}
		}
		return st;
	}

}
