package ch.fhnw.sevenwonders.model;

import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientThread extends Thread {
	private Socket socket;
	private final Logger logger = Logger.getLogger("");
	
	public ClientThread(Socket inSocket) {
		this.socket = inSocket;

		logger.log(Level.INFO, "Neuer Client verbunden");
	}
}
