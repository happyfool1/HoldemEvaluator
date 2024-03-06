package holdemevaluator;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class GUIEditRanges extends JFrame implements Constants {

	private static JTextField typeField;
	private static JTextField positionField;
	private static JTextField betTypeField;
	private static JButton submitButton;

	private static String playerType = "";
	private static int playerPosition = -1;
	private static int betType = -1;

	private static boolean typeOK = false;
	private static boolean positionOK = false;
	private static boolean betTypeOK = false;

	private static void createView() {
		final var frame = new JFrame("Edit Ranges");
		frame.setPreferredSize(new Dimension(400, 300));
		frame.setLocation(400, 100);
		final var panel = new JPanel();

		panel.setLayout(new GridLayout(4, 2));

		final var typeLabel = new JLabel("PLayer Type Hero, Average, Nit, Lag, Tag, ....");
		panel.add(typeLabel);
		typeField = new JTextField("Hero");
		panel.add(typeField);

		final var positionLabel = new JLabel("Position SB, BB, UTG, MP, CO, BU");
		panel.add(positionLabel);
		positionField = new JTextField("BU");
		panel.add(positionField);

		final var betTypeLabel = new JLabel("0 - 5 , Bet Type Bet1 Limp, Bet2 OPen, Bet3, Bet4, Allin, Call_Allin");
		panel.add(betTypeLabel);
		betTypeField = new JTextField("Limp");
		panel.add(betTypeField);

		submitButton = new JButton("Submit");
		panel.add(submitButton);
		frame.add(panel);

		submitButton.addActionListener((ActionEvent e) -> {
			final var type = typeField.getText();
			final var position = positionField.getText();
			final var bet = betTypeField.getText();

			typeOK = true;
			positionOK = true;
			betTypeOK = true;

			switch (type) {
			case "Hero" -> playerType = type;
			case "Average" -> playerType = type;
			case "Nit" -> playerType = type;
			case "Lag" -> playerType = type;
			case "Tag" -> playerType = type;
			default -> typeOK = false;
			}
			switch (position) {
			case "SB" -> playerPosition = SB;
			case "BB" -> playerPosition = BB;
			case "UTG" -> playerPosition = UTG;
			case "MP" -> playerPosition = MP;
			case "CO" -> playerPosition = CO;
			case "BU" -> playerPosition = BU;
			default -> positionOK = false;
			}
			switch (bet) {
			case "Limp" -> betType = PREFLOP_LIMP;
			case "Open" -> betType = PREFLOP_OPEN;
			case "Bet3" -> betType = PREFLOP_BET3;
			case "Bet4" -> betType = PREFLOP_BET4;
			case "Allin" -> betType = PREFLOP_ALLIN;
			case "Call_Allin" -> betType = PREFLOP_CALL_ALLIN;
			default -> typeOK = false;
			}
			if (!typeOK) {
				JOptionPane.showMessageDialog(null, "Type invalid");
			}
			if (!positionOK) {
				JOptionPane.showMessageDialog(null, "Position invalid");
			}
			if (!betTypeOK) {
				JOptionPane.showMessageDialog(null, "Bet Type invalid");
			}
			if (typeOK && positionOK && betTypeOK) {
				new EditRange(playerType, playerPosition, betType);
			}

		});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(GUIEditRanges::createView);
	}

}
