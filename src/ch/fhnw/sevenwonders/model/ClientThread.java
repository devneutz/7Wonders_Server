package ch.fhnw.sevenwonders.model;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import ch.fhnw.sevenwonders.enums.StartupAction;
import ch.fhnw.sevenwonders.enums.StatusCode;
import ch.fhnw.sevenwonders.helper.DbHelper;
import ch.fhnw.sevenwonders.interfaces.ILobby;
import ch.fhnw.sevenwonders.interfaces.IPlayer;
import ch.fhnw.sevenwonders.messages.ClientLobbyMessage;
import ch.fhnw.sevenwonders.messages.ClientStartupMessage;
import ch.fhnw.sevenwonders.messages.Message;
import ch.fhnw.sevenwonders.messages.ServerLobbyMessage;
import ch.fhnw.sevenwonders.messages.ServerStartupMessage;
import ch.fhnw.sevenwonders.models.Player;

/**
 * 
 * @author Gabriel de Castilho, Joel Neutzner
 * 
 *         Diese Klasse stellt den ClientThread für den Server dar. Hier wird
 *         jede Client Anfrage separat in einem eigenen Thread verarbeitet.
 *
 */
public class ClientThread extends Thread {
	private IPlayer player;
	private ArrayList<IPlayer> opponents;
	private Socket socket;
	private final Logger logger = Logger.getLogger("");

	private final int clientId;

	/*
	 * Konstruktur für den Clientthread
	 */
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
				if (inObject != null) {
					handlingIncomingMessage((Message) inObject, out);
				}
			}
		} catch (EOFException inEOFEx) {

		} catch (Exception inEx) {
			logger.log(Level.SEVERE, "Error handling Message  [Client " + clientId + "]", inEx);
		}
	}

	/*
	 * In dieser Methode werden die eingehenden Client Messages verarbeitet. Anhand
	 * der StartupAction wird entschieden wie mit der Message umgegangen wird.
	 */
	private void handlingIncomingMessage(Message inMessage, ObjectOutputStream outputStream)
			throws IOException, InterruptedException {
		if (inMessage instanceof ClientStartupMessage) {
			logger.log(Level.INFO, "Message received [Client " + clientId + "] - ClientStartupMessage");
			ServerStartupMessage tmpMessage = new ServerStartupMessage(
					((ClientStartupMessage) inMessage).getActionType());
			IPlayer tmpPlayer = ((ClientStartupMessage) inMessage).getPlayer();

			if (tmpMessage.getActionType() == StartupAction.Login) {

				if (DbHelper.doesPlayerExist(tmpPlayer)) {

					if (DbHelper.isPasswordValid(tmpPlayer)) {
						tmpMessage.setPlayer(this.player);
						tmpMessage.setLobbies(new ArrayList<ILobby>());
						tmpMessage.setStatusCode(StatusCode.Success);
						logger.log(Level.INFO,
								"Player logged in successfully [Client " + clientId + "] - ClientStartupMessage");
						outputStream.writeObject(tmpMessage);
						outputStream.flush();
						return;
					} else {
						tmpMessage.setStatusCode(StatusCode.LoginFailed);
						logger.log(Level.WARNING,
								"Player typed wrong password [Client " + clientId + "] - ClientStartupMessage");
						outputStream.writeObject(tmpMessage);
						outputStream.flush();
						return;
					}

				} else {
					tmpMessage.setStatusCode(StatusCode.LoginFailed);
					logger.log(Level.WARNING,
							"Player doesn't exists in db [Client " + clientId + "] - ClientStartupMessage");
					outputStream.writeObject(tmpMessage);
					outputStream.flush();
					return;

				}

			}

			if (tmpMessage.getActionType() == StartupAction.Register) {

				if (DbHelper.doesPlayerExist(tmpPlayer)) {
					tmpMessage.setStatusCode(StatusCode.RegistrationFailed);
					logger.log(Level.WARNING,
							"Player already exists in db [Client " + clientId + "] - ClientStartupMessage");
					outputStream.writeObject(tmpMessage);
					outputStream.flush();
					return;

				} else {
					DbHelper.addPlayer(tmpPlayer);
					tmpMessage.setPlayer(this.player);
					tmpMessage.setLobbies(new ArrayList<ILobby>());
					tmpMessage.setStatusCode(StatusCode.Success);
					logger.log(Level.INFO,
							"Player logged in successfully [Client " + clientId + "] - ClientStartupMessage");
					outputStream.writeObject(tmpMessage);
					outputStream.flush();
					return;

				}

			}

			if (tmpMessage.getActionType() == StartupAction.LoginAsGuest) {
				Player guestPlayer = new Player();
				guestPlayer.setName("RANDOMNAME");

				tmpMessage.setPlayer(guestPlayer);

				this.player = guestPlayer;

				tmpMessage.setLobbies(new ArrayList<ILobby>());
				tmpMessage.setStatusCode(StatusCode.Success);
				logger.log(Level.INFO, "Guest logged in successfully [Client " + clientId + "] - ClientStartupMessage");
				outputStream.writeObject(tmpMessage);
				outputStream.flush();
				return;

			}

		}
		
		else if (inMessage instanceof ClientLobbyMessage) {
			ServerLobbyMessage tmpMessage = new ServerLobbyMessage(((ClientLobbyMessage)inMessage).getActionType());
			tmpMessage.setStatusCode(StatusCode.Success);
			outputStream.writeObject(tmpMessage);
			outputStream.flush();
		}

	}
}
