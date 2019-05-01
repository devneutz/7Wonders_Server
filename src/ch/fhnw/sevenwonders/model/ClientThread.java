package ch.fhnw.sevenwonders.model;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.fhnw.sevenwonders.enums.*;
import ch.fhnw.sevenwonders.interfaces.ILobby;
import ch.fhnw.sevenwonders.interfaces.IPlayer;
import ch.fhnw.sevenwonders.messages.*;
import ch.fhnw.sevenwonders.models.Player;

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
		try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());) {
			while (true) {
				Object inObject = in.readObject(); 
				if(inObject != null) {
					handlingIncomingMessage((Message)inObject, out);
				}
			}
		}
		catch(EOFException inEOFEx) {
					
		}catch(Exception inEx) {
			logger.log(Level.SEVERE, "Error handling Message  [Client " + clientId + "]", inEx);
		}
	}
	
	private void handlingIncomingMessage(Message inMessage, ObjectOutputStream outputStream) throws IOException, InterruptedException{
		if (inMessage instanceof ClientStartupMessage) {
			logger.log(Level.INFO, "Message received [Client " + clientId + "] - ClientStartupMessage");
			// TODO Spieler anhand der einkommenden Nachricht authentifizieren etc.
			ServerStartupMessage tmpMessage = new ServerStartupMessage(((ClientStartupMessage)inMessage).getActionType());
			tmpMessage.setPlayer(new Player());
			tmpMessage.setLobbies(new ArrayList<ILobby>());
			tmpMessage.setStatusCode(StatusCode.Success);
			outputStream.writeObject(tmpMessage);
			outputStream.flush();
			return;
		}
		
		if(inMessage instanceof ClientGameMessage)
		{
			logger.log(Level.INFO, "Client [" + clientId + "] hat gespielt");
			// Is action valid?
			ClientGameMessage tmpMessage = (ClientGameMessage)inMessage;
			switch(tmpMessage.getAction()) {
			case PlayCard:
				return;
			case BuildCard:
				// What board does the player have? What stages have already been built? -> try to build the next stage
				int tmpStageToBuild = tmpMessage.getPlayer().getBoard().getNextStageToBuild();
				if(tmpMessage.getPlayer().getBoard().canBuild(tmpStageToBuild, tmpMessage.getPlayer().getResource(null))){
					
				}
				
				return;
			case MonetizeCard:
				return;
				default:
					return;
			}
		}
	}
}
