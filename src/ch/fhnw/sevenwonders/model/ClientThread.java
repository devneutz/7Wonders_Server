package ch.fhnw.sevenwonders.model;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.fhnw.sevenwonders.interfaces.IPlayer;
import ch.fhnw.sevenwonders.messages.ClientStartupMessage;
import ch.fhnw.sevenwonders.messages.Message;

public class ClientThread extends Thread {
	private IPlayer player;
	private ArrayList<IPlayer> opponents;
	private Socket socket;
	private final Logger logger = Logger.getLogger("");
	
	private final int clientId;
	
	public ClientThread(Socket inSocket, int inClientId) {
		this.socket = inSocket;
		this.clientId = inClientId;
		this.opponents = new ArrayList<IPlayer>();
		logger.log(Level.INFO, "New client joined server [Client " + clientId + "]");
	}
	
	@Override
	public void run() {
		try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());) {
			while (true) {
				Object inObject = in.readObject(); 
				if(inObject != null) {
					handlingIncomingMessage((Message)inObject);
				}
			}
		}catch(Exception inEx) {
			logger.log(Level.SEVERE, "Error handling Message  [Client " + clientId + "]", inEx);
		}
	}
	
	private void handlingIncomingMessage(Message inMessage) {
		if (inMessage instanceof ClientStartupMessage) {
			logger.log(Level.INFO, "Message received [Client " + clientId + "] - ClientStartupMessage -> " + inMessage.message);
			// TODO Spieler anhand der einkommenden Nachricht authentifizieren etc.
			return;
		}
	}
}
