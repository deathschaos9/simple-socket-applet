/**
 * 
 */
package com;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author John
 *
 */
public class Server extends Thread {
	
	/**
	 * Is the server running
	 */
	public static boolean running = true;
	
	/**
	 * Time taken to initialize the socket
	 */
	public static long loadTime = 0;

	public Server() {
		if (running) {
		try {
			testServer = new ServerSocket(12345);
			// Initial load time minus current time calculates load time in milliseconds
			loadTime = System.currentTimeMillis() - loadTime;
			System.out.println("....  and we're bound. (" + loadTime + "ms)");
			while(testServer.isBound()) {
				clientSocket = testServer.accept();
				String hostName = clientSocket.getLocalAddress().getHostAddress();
				System.out.println("Incoming connection from: " + hostName);
				if (clientSocket.isConnected()) {
					System.out.println("Connection success, notifying and terminating client.");
					clientSocket.getOutputStream().write(1); // 0 = nogo 1 = yay
					clientSocket.close();
				}
			}
		} catch (IOException e) {
		}
		}
	}
	
	public static void killServer() {
		running = false;
		try {
			testServer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		testServer = null;
		System.out.println("Server flatlined, you're free to exit.");
	}
	
	/*
	 * The server socket to be created 
	 */
	public static ServerSocket testServer = null;
	
	/*
	 * Incoming connection request
	 */
	public Socket clientSocket = null;
}
