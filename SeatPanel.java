package holdemevaluator;

/*-  ******************************************************************************
 *  @author PEAK_ ****************************************************************************** */

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SeatPanel implements Constants {
	/*-
	 * This class is a helper to the Interactive application.
	 * There is one instance of this class for each seat at the table.
	 * The constructor arg is the seat number 
	 * 
	 * The seat panel includes
	 * 	Position ( SB, BB, etc.. )
	 * Status ( Active, folded. etc. )
	 * Hand ( AsKs  )
	 * Player name
	 * Bet
	 * Stack
	 * 
	 *  The background color for the user name indicates status
	 *  	
	 * Public Methods
	 * 	showSeat
	 * 		Updates fields in seat panel
	 * 		Returns panel
	 * 	getPanel
	 * 		Updates fields in seat panel
	 * 		Returns panel
	 * 		?
	 * 	fillSeat
	 * 		Updates all fields in seat pane
	 */

	/*- - This is the seat JPanel. */
	private final JPanel topPanel = new JPanel();
	private final JPanel seatPanel = new JPanel();
	private final JLabel name = new JLabel("");
	private final JLabel stack = new JLabel("        ");
	private final JLabel pos = new JLabel("SB");
	String nameSt;
	String position;
	private final int seat;

	/*- - Constructor Arg0 is seat number. */
	public SeatPanel(int s) {
		seat = s;

		constructPanel();

	}

	/*- - Show seat. */
	public JPanel showSeat() {
		fillSeat();
		return topPanel;
	}

	/*- - Get Panel. */
	public JPanel getPanel() {
		fillSeat();
		seatPanel.repaint();
		return topPanel;
	}

	/*- - Construct panel. */
	private void constructPanel() {
		final var f = new Font(Font.SERIF, Font.BOLD, 16);
		stack.setForeground(Color.GREEN);
		name.setForeground(Color.WHITE);
		pos.setForeground(Color.WHITE);
		seatPanel.setForeground(Color.BLACK);
		stack.setFont(f);

		seatPanel.add(name);
		seatPanel.add(stack);
		seatPanel.add(pos);
		topPanel.add(seatPanel);
		fillSeat();
		topPanel.setVisible(true);
	}

	/*- - Show seat panel. */
	public void fillSeat() {
		name.setText(EvalData.playerNames[seat]);
		nameSt = EvalData.playerNames[seat];
		if (GameControl.seatPos[seat] == SB) {
			pos.setText("SB");
			position = "SB";
		} else if (GameControl.seatPos[seat] == BB) {
			pos.setText("BB");
			position = "BB";
		} else if (GameControl.seatPos[seat] == UTG) {
			pos.setText("UTG");
			position = "UTG";
		} else if (GameControl.seatPos[seat] == MP) {
			pos.setText("MP");
			position = "MP";
		} else if (GameControl.seatPos[seat] == CO) {
			pos.setText("Cutoff");
			position = "Cutoff";
		} else if (GameControl.seatPos[seat] == BU) {
			pos.setText("Button");
			position = "Button";
		}
		pos.setForeground(EvalData.seat == seat ? Color.RED : Color.WHITE);
		seatPanel.setBackground(!GameControl.playerFolded[seat] ? Color.BLACK : Color.LIGHT_GRAY);
		stack.setText(Format.format$(GameControl.stackBD[seat]));
	}

}
