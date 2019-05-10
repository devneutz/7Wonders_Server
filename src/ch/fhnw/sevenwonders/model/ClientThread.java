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
import ch.fhnw.sevenwonders.interfaces.*;
import ch.fhnw.sevenwonders.messages.*;
import ch.fhnw.sevenwonders.helper.DbHelper;
import ch.fhnw.sevenwonders.models.Player;

/**
 * 
 * @author Gabriel de Castilho, Joel Neutzner, Matteo Farneti
 * 
 *         Diese Klasse stellt den ClientThread f�r den Server dar. Hier wird
 *         jede Client Anfrage separat in einem eigenen Thread verarbeitet.
 *
 */
public class ClientThread extends Thread {
	private IPlayer player;
	private ArrayList<IPlayer> opponents;
	private Socket socket;
	private final Logger logger = Logger.getLogger("");

	private final int clientId;
	private static volatile int guestCounter = 0;

	private final Game game;

	private ObjectOutputStream out;
	private ObjectInputStream in;

	/*
	 * Konstruktur f�r den Clientthread
	 */
	public ClientThread(Socket inSocket, int inClientId, Game inGame) {
		this.socket = inSocket;
		this.clientId = inClientId;
		this.game = inGame;
		try {
			this.opponents = new ArrayList<IPlayer>();
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.in = new ObjectInputStream(socket.getInputStream());

			logger.log(Level.INFO, "New client joined server [Client " + clientId + "]");
		} catch (Exception inEx) {
			logger.log(Level.SEVERE, "Error connecting client [" + clientId + "]");
		}
	}

	@Override
	public void run() {
		try {
			while (!socket.isClosed()) {
				Object inObject = in.readObject();
				if (inObject != null) {
					handlingIncomingMessage((Message) inObject);
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
	private void handlingIncomingMessage(Message inMessage) throws IOException, InterruptedException {
		if (inMessage instanceof ClientStartupMessage) {
			logger.log(Level.INFO, "Message received [Client " + clientId + "] - ClientStartupMessage");
			ServerStartupMessage tmpMessage = new ServerStartupMessage(
					((ClientStartupMessage) inMessage).getActionType());
			IPlayer tmpPlayer = ((ClientStartupMessage) inMessage).getPlayer();

			if (tmpMessage.getActionType() == StartupAction.Login) {

				if (DbHelper.doesPlayerExist(tmpPlayer)) {

					if (DbHelper.isPasswordValid(tmpPlayer)) {
						this.player = tmpPlayer;
						tmpMessage.setPlayer(this.player);
						tmpMessage.setLobbies(game.getLobbies());
						tmpMessage.setStatusCode(StatusCode.Success);
						logger.log(Level.INFO,
								"Player logged in successfully [Client " + clientId + "] - ClientStartupMessage");
						out.writeObject(tmpMessage);
						out.flush();
						return;
					} else {
						tmpMessage.setStatusCode(StatusCode.LoginFailed);
						logger.log(Level.WARNING,
								"Player typed wrong password [Client " + clientId + "] - ClientStartupMessage");
						out.writeObject(tmpMessage);
						out.flush();
						return;
					}

				} else {
					tmpMessage.setStatusCode(StatusCode.LoginFailed);
					logger.log(Level.WARNING,
							"Player doesn't exists in db [Client " + clientId + "] - ClientStartupMessage");
					out.writeObject(tmpMessage);
					out.flush();
					return;
				}
			}

			if (tmpMessage.getActionType() == StartupAction.Register) {
				if (DbHelper.doesPlayerExist(tmpPlayer)) {
					tmpMessage.setStatusCode(StatusCode.RegistrationFailed);
					logger.log(Level.WARNING,
							"Player already exists in db [Client " + clientId + "] - ClientStartupMessage");
					out.writeObject(tmpMessage);
					out.flush();
					return;
				} else {
					DbHelper.addPlayer(tmpPlayer);
					this.player = tmpPlayer;
					tmpMessage.setPlayer(this.player);
					tmpMessage.setLobbies(game.getLobbies());
					tmpMessage.setStatusCode(StatusCode.Success);
					logger.log(Level.INFO,
							"Player logged in successfully [Client " + clientId + "] - ClientStartupMessage");
					out.writeObject(tmpMessage);
					out.flush();
					return;
				}
			} else if (tmpMessage.getActionType() == StartupAction.LoginAsGuest) {
				Player guestPlayer = new Player();
				synchronized (this) {
					guestCounter++;
				}
				guestPlayer.setName("Guest " + guestCounter);
				tmpMessage.setPlayer(guestPlayer);

				this.player = guestPlayer;

				tmpMessage.setLobbies(game.getLobbies());
				tmpMessage.setStatusCode(StatusCode.Success);
				logger.log(Level.INFO, "Guest logged in successfully [Client " + clientId + "] - ClientStartupMessage");
				out.writeObject(tmpMessage);
				out.flush();
				return;
			}
		} else if (inMessage instanceof ClientLobbyMessage) {
			ServerLobbyMessage tmpMessage = new ServerLobbyMessage(((ClientLobbyMessage) inMessage).getActionType());
			switch (tmpMessage.getAction()) {
			case CreateLobby: {
				IPlayer tmpPlayer = ((ClientLobbyMessage) inMessage).getPlayer();
				ILobby tmpLobby = ((ClientLobbyMessage) inMessage).getLobby();
				tmpPlayer.setLobby(tmpLobby);
				tmpLobby.setLobbyMaster(tmpPlayer);

				this.game.addLobby(tmpLobby);
				tmpMessage.setPlayer(tmpPlayer);
				tmpMessage.setStatusCode(StatusCode.Success);
				out.writeObject(tmpMessage);
				out.flush();
				// Zus�tzlich zur Antwort an den Ersteller einen Broadcast absetzen, damit die
				// anderen Spieler �ber die neue Lobby Bescheid wissen.
				ServerLobbyMessage tmpBroadcast = new ServerLobbyMessage(LobbyAction.LobbyCreated);
				tmpBroadcast.setLobby(tmpLobby);
				game.broadcastMessage(tmpBroadcast);
				break;
			}
			case DeleteLobby: {
				// Zus�tzlich zur Antwort an den Ersteller einen Broadcast absetzen, damit die
				// anderen Spieler �ber die L�schung der Lobby Bescheid wissen.
				ILobby tmpLobby = ((ClientLobbyMessage) inMessage).getLobby();
				ServerLobbyMessage tmpBroadcast = new ServerLobbyMessage(LobbyAction.LobbyDeleted);
				tmpBroadcast.setLobby(tmpLobby);
				game.broadcastMessage(tmpBroadcast);
				break;
			}
			default:
				break;
			}
		} else if (inMessage instanceof ClientGameMessage) {
			logger.log(Level.INFO, "Client [" + clientId + "] hat gespielt");
			// Is action valid?
			ClientGameMessage tmpMessage = (ClientGameMessage) inMessage;
			switch (tmpMessage.getAction()) {
			case PlayCard: {
				IPlayer tmpPlayer = ((ClientGameMessage) inMessage).getPlayer();
				ICard tmpCard = ((ClientGameMessage) inMessage).getCard();

				if (((ClientGameMessage) inMessage).getAction() == GameAction.PlayCard) {
					tmpPlayer.playCard(tmpCard);
				}
				tmpMessage.setPlayer(tmpPlayer);
				tmpMessage.setCard(tmpCard);
				out.writeObject(tmpMessage);
				out.flush();
				break;
			}

			case BuildCard:
				// What board does the player have? What stages have already been built? -> try
				// to build the next stage
				int tmpStageToBuild = tmpMessage.getPlayer().getBoard().getNextStageToBuild();
				if (tmpMessage.getPlayer().getBoard().canBuild(tmpStageToBuild,
						tmpMessage.getPlayer().getPlayerResources())) {
					IPlayer tmpPlayer = ((ClientGameMessage) inMessage).getPlayer();
					ICard tmpCard = ((ClientGameMessage) inMessage).getCard();
					IBoard tmpBoard = ((ClientGameMessage) inMessage).getBoard();

					if (((ClientGameMessage) inMessage).getAction() == GameAction.BuildCard) {
						tmpPlayer.useCardForBuilding(tmpCard);
					}
					tmpMessage.setPlayer(tmpPlayer);
					tmpMessage.setCard(tmpCard);
					tmpMessage.setBoard(tmpBoard);
					out.writeObject(tmpMessage);
					out.flush();
					break;

				}

				return;
			case MonetizeCard: {
				IPlayer tmpPlayer = ((ClientGameMessage) inMessage).getPlayer();
				ICard tmpCard = ((ClientGameMessage) inMessage).getCard();
				
				// M�nze die Karte um
				tmpPlayer.monetizeCard(tmpCard);

				tmpMessage.setPlayer(tmpPlayer);
				out.writeObject(tmpMessage);
				out.flush();
				break;
			}
			default:
				return;
			}
		}
	}

	public void sendMessage(Message inMessage) throws IOException {
		logger.log(Level.INFO, "sending message lobby created");
		out.writeObject(inMessage);
		out.flush();
	}

}
