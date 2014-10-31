/**
 * 
 */
package com;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

import javax.swing.*;

import com.io.ConsoleOutput;

/**
 * @author John
 * 
 */
@SuppressWarnings("restriction")
public class GUI implements ActionListener {

	/**
	 * Randomly generated serialVersionUID
	 */
	private static final long serialVersionUID = -273301150237210109L;

	/**
	 * Create, size, position, and otherwise initialize components for the
	 * <b>Container</b>
	 * 
	 * @param pane
	 *            - the Container pane.
	 */
	public void addComponentsToPane(Container pane) {
		// Start button
		JButton startButton = new JButton("Start Server Listener");
		pane.add(startButton, BorderLayout.PAGE_START);
		startButton.addActionListener(this);

		// Console area
		JTextArea displayArea = new JTextArea();
		pane.setPreferredSize(new Dimension(250, 300));
		pane.add(displayArea, BorderLayout.CENTER);
		displayArea.setEditable(false);
		displayArea.setLineWrap(true);
		PrintStream printStream = new PrintStream(
				new ConsoleOutput(displayArea));
		System.setOut(printStream);
		System.setErr(printStream);

		// Kill button
		JButton stopButton = new JButton("Terminate Server");
		pane.add(stopButton, BorderLayout.PAGE_END);
		stopButton.addActionListener(this);
	}

	/**
	 * Create the GUI and show it.
	 */
	private GUI() {
		JFrame frame = new JFrame("Simple Server");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addComponentsToPane(frame.getContentPane());
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception e) {
		}
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GUI();
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Start Server Listener")) {
			if (Server.testServer == null) {
				Server.loadTime = System.currentTimeMillis();
				System.out.print("Starting server...");
				Server.running = true;
				new Thread() {
					public void run() {
						new Server();
					}
				}.start();
			} else {
				System.err.println("Server is already bound.");
			}
		} else if (e.getActionCommand().equals("Terminate Server")) {
			if (!Server.running) {
				System.err.println("Server isn't running");
				return;
			}
			System.out
					.println("Attempting to kill server, if it doesn't work try starting the server first ;)");
			Server.interrupted();
			Server.killServer();
		} else {
			System.err.println("Unrecognized action: " + e.getActionCommand());
		}
	}
}