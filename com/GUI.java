/**
 * 
 */
package com;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;

import javax.swing.*;

import com.io.OutStream;
import com.io.OutStreamErr;

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
		pane.setPreferredSize(new Dimension(375, 200));
		pane.add(displayArea, BorderLayout.CENTER);
		displayArea.setEditable(false);
		displayArea.setLineWrap(true);
		PrintStream printStream = new PrintStream(
				new OutStream(displayArea));
		System.setOut(printStream);
		PrintStream printStreamErr = new PrintStream(
				new OutStreamErr(displayArea));
		System.setErr(printStreamErr);

		// Kill button
		JButton stopButton = new JButton("Terminate Server");
		pane.add(stopButton, BorderLayout.PAGE_END);
		stopButton.addActionListener(this);
	}

	/**
	 * Create the GUI and show it.
	 */
	private GUI() {
		JFrame frame = new JFrame("Simple Server by dc9");
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