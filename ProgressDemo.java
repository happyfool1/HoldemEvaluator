package holdemevaluator;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class ProgressDemo {
	/*-**********************************************************************************************
	
	In this example:
	
	Initialization: A basic GUI with a JTextArea and a JButton is set up.
	startLongRunningTask(): Initiates the long-running task.
	SwingWorker: This is where the magic happens. 
	It allows you to execute time-consuming tasks on a background thread. 
	There are two main methods:
	doInBackground(): This is where the time-consuming task is executed. Use publish() to send 
	interim results (like progress updates) to be processed on the EDT.
	process(): Receives the interim results published from doInBackground() and updates the GUI. 
	This runs on the EDT, so you can safely update GUI components here.
	In the given example, the long-running task simply sleeps for a while, but you can replace this 
	with your own logic. As the task progresses, the JTextArea is updated with the progress percentage.
	
	Remember to always update Swing components on the EDT, which is what the process()
	method does in the SwingWorker.
	
	*************************************************************************************************/

	private JFrame frame;
	private JTextArea textArea;
	private JButton startButton;

	public ProgressDemo() {
		frame = new JFrame("Progress Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 200);
		frame.setLayout(new BorderLayout());

		textArea = new JTextArea();
		frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

		startButton = new JButton("Start");
		frame.add(startButton, BorderLayout.SOUTH);
		startButton.addActionListener(e -> startLongRunningTask());

		frame.setVisible(true);
	}

	private void startLongRunningTask() {
		SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {

			@Override
			protected Void doInBackground() {
				for (int i = 0; i < 10; i++) {
					try {
						Thread.sleep(500); // Simulate long-running task
						publish("Progress: " + (i + 1) * 10 + "%\n");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				return null;
			}

			@Override
			protected void process(List<String> chunks) {
				for (String chunk : chunks) {
					textArea.append(chunk);
				}
			}

			@Override
			protected void done() {
				textArea.append("Task completed!\n");
			}
		};

		worker.execute();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(ProgressDemo::new);
	}
}
