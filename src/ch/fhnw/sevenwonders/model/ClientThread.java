package ch.fhnw.sevenwonders.model;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.fhnw.sevenwonders.enums.*;
import ch.fhnw.sevenwonders.interfaces.*;
import ch.fhnw.sevenwonders.messages.*;
import ch.fhnw.sevenwonders.helper.DbHelper;
import ch.fhnw.sevenwonders.models.Player;

/**
 * 
 * @author Gabriel de Castilho, Joel Neutzner, Matteo Farneti, Ismael Liuzzi
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
		} catch(SocketException inEx) {
			game.removeClient(this);
			logger.log(Level.INFO, "Client disconnected [Client " + clientId + "]");
		}catch (EOFException inEOFEx) {
		} catch (Exception inEx) {
			logger.log(Level.SEVERE, "Error handling Message  [Client " + clientId + "]", inEx);
		}
	}

	/*
	 * In dieser Methode werden die eingehenden Client Messages verarbeitet. Anhand
	 * der StartupAction wird entschieden wie mit der Message umgegangen wird.
	 */
	private void handlingIncomingMessage(Message inMessage) throws IOException, InterruptedException {
		out.reset();
		if (inMessage instanceof ClientStartupMessage) {
			handleStartupMessages((ClientStartupMessage)inMessage);
		} else if (inMessage instanceof ClientLobbyMessage) {
			handleLobbyMessages((ClientLobbyMessage) inMessage);
		} else if (inMessage instanceof ClientGameMessage) {
			handleGameMessages((ClientGameMessage)inMessage);
		}
	}

	private void handleStartupMessages(ClientStartupMessage inMessage) throws IOException, InterruptedException {
		logger.log(Level.INFO, "Message received [Client " + clientId + "] - ClientStartupMessage");
		ServerStartupMessage tmpMessage = new ServerStartupMessage(inMessage.getActionType());
		IPlayer tmpPlayer =  inMessage.getPlayer();

		if (inMessage.getActionType() == StartupAction.Login) {

			if (DbHelper.doesPlayerExist(tmpPlayer)) {

				if (DbHelper.isPasswordValid(tmpPlayer)) {
					this.player = tmpPlayer;
					tmpMessage.setPlayer(this.player);
					tmpMessage.setLobbies(game.getLobbies());
					tmpMessage.setStatusCode(StatusCode.Success);
					logger.log(Level.INFO,
							"Player logged in successfully [Client " + clientId + "] - ClientStartupMessage");
					sendMessage(tmpMessage);
					return;
				} else {
					tmpMessage.setStatusCode(StatusCode.LoginFailed);
					logger.log(Level.WARNING,
							"Player typed wrong password [Client " + clientId + "] - ClientStartupMessage");
					sendMessage(tmpMessage);
					return;
				}

			} else {
				tmpMessage.setStatusCode(StatusCode.LoginFailed);
				logger.log(Level.WARNING,
						"Player doesn't exists in db [Client " + clientId + "] - ClientStartupMessage");
				sendMessage(tmpMessage);
				return;
			}
		}

		if (inMessage.getActionType() == StartupAction.Register) {
			if (DbHelper.doesPlayerExist(tmpPlayer)) {
				tmpMessage.setStatusCode(StatusCode.RegistrationFailed);
				logger.log(Level.WARNING,
						"Player already exists in db [Client " + clientId + "] - ClientStartupMessage");
				sendMessage(tmpMessage);
				return;
			} else {
				DbHelper.addPlayer(tmpPlayer);
				this.player = tmpPlayer;
				tmpMessage.setPlayer(this.player);
				tmpMessage.setLobbies(game.getLobbies());
				tmpMessage.setStatusCode(StatusCode.Success);
				logger.log(Level.INFO,
						"Player logged in successfully [Client " + clientId + "] - ClientStartupMessage");
				sendMessage(tmpMessage);
				return;
			}
		} else if (inMessage.getActionType() == StartupAction.LoginAsGuest) {
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
			sendMessage(tmpMessage);
			return;
		}
	}
	
	private void handleLobbyMessages(ClientLobbyMessage inMessage)  throws IOException, InterruptedException {
		switch (inMessage.getActionType()) {
			case CreateLobby: {
				ServerLobbyMessage tmpMessage = new ServerLobbyMessage(LobbyAction.CreateLobby);
				IPlayer tmpPlayer = inMessage.getPlayer();
				ILobby tmpLobby = inMessage.getLobby();
				tmpPlayer.setLobby(tmpLobby);
				tmpLobby.setLobbyMaster(tmpPlayer);
				tmpLobby.addPlayer(tmpPlayer);
				this.player.setLobby(tmpLobby);
				this.game.addLobby(tmpLobby);
				tmpMessage.setPlayer(tmpPlayer);
				tmpMessage.setStatusCode(StatusCode.Success);
				sendMessage(tmpMessage);
				// Zus�tzlich zur Antwort an den Ersteller einen Broadcast absetzen, damit die
				// anderen Spieler �ber die neue Lobby Bescheid wissen.
				ServerLobbyMessage tmpBroadcast = new ServerLobbyMessage(LobbyAction.LobbyCreated);
				tmpBroadcast.setLobby(tmpLobby);
				game.broadcastMessage(tmpBroadcast);
				break;
			}
			case DeleteLobby: {
				ServerLobbyMessage tmpMessage = new ServerLobbyMessage(LobbyAction.DeleteLobby);
				// Zus�tzlich zur Antwort an den Ersteller einen Broadcast absetzen, damit die
				// anderen Spieler �ber die L�schung der Lobby Bescheid wissen.
				ILobby tmpLobby = inMessage.getLobby();
				IPlayer tmpPlayer = inMessage.getPlayer();
				if (tmpLobby.getLobbyMaster().getName().equalsIgnoreCase(tmpPlayer.getName())) {
					this.game.removeLobby(tmpLobby);
					this.player.setLobby(null);
					tmpMessage.setStatusCode(StatusCode.Success);
					tmpMessage.setPlayer(this.player);
					sendMessage(tmpMessage);
					ServerLobbyMessage tmpBroadcast = new ServerLobbyMessage(LobbyAction.LobbyDeleted);
					tmpBroadcast.setLobby(tmpLobby);
					game.broadcastMessage(tmpBroadcast);
					break;
				}
				break;
			}
			case LeaveLobby: {
				ServerLobbyMessage tmpMessage = new ServerLobbyMessage(LobbyAction.LeaveLobby);
				// Zus�tzlich zur Antwort an den Ersteller einen Broadcast absetzen, damit die
				// anderen Spieler �ber die L�schung der Lobby Bescheid wissen.
				ILobby tmpLobby = inMessage.getLobby();
				IPlayer tmpPlayer = inMessage.getPlayer();
				//tmpLobby.removePlayer(tmpPlayer);
				this.player.setLobby(null);
				tmpMessage.setStatusCode(StatusCode.Success);
				tmpMessage.setPlayer(this.player);
				sendMessage(tmpMessage);
				ServerLobbyMessage tmpBroadcast = new ServerLobbyMessage(LobbyAction.PlayerLeft);
				tmpBroadcast.setLobby(tmpLobby);
				game.broadcastMessage(tmpBroadcast);
				break;
								
				}
			case StartLobby: {
				ServerLobbyMessage tmpMessage = new ServerLobbyMessage(LobbyAction.StartLobby);
				ILobby tmpLobby = inMessage.getLobby();
				IPlayer tmpPlayer = inMessage.getPlayer();
				//Zuf�llige Zuweisung von Board an Player
				Random random = new Random();
				for(int i=tmpLobby.getNumPlayers(); i==0; i--) {
					tmpLobby.getLobbyPlayers().get(i).setBoard(game.getListOfBoards().get(random.nextInt(game.getListOfBoards().size())));
				}
				//Zuf�llig Karten aus erstem Zeitalter f�r erste Runde den Spielern zuweisen und aus ListofCard entfernen
				ArrayList<ICard> tmpCardList = new ArrayList<ICard>();
				for(int i=game.getListOfCards().size(); i==0; i--) {
					//Karten in tmpCardList speicher welche ins erste Zeitalter geh�ren
					if(game.getListOfCards().get(i).getAge()==Age.AgeI) {
						tmpCardList.add(game.getListOfCards().get(i));			
					}
				}
				//tmpCardListForPlayer ist tempor�res Array f�r die Zuweisung der ersten 7 Karten f�r den Spieler
				ArrayList<ICard> tmpCardListForPlayer = new ArrayList<ICard>();
				for(int i=tmpLobby.getNumPlayers(); i==0; i--) {
					//CardListForPlayer ist eine Kopie von tmpCardListforPlayer und wird bei jedem neuen Spieler neu abgef�llt
					ArrayList<ICard> CardListForPlayer = new ArrayList<ICard>();
					for(int z=1; z==7; z++) {
						//Zuf�llige Zuweisung der 7 Karten an die tmpCardListForPlayer
						tmpCardListForPlayer.add(tmpCardList.get(random.nextInt(tmpCardList.size())));
					}
					//for-Schleife f�r die Kopie der 7 Karten von tmpCardListForPlayer zu CardListForPlayer
					for (int c = 0 ; c<tmpCardListForPlayer.size();c++){
						
						CardListForPlayer.add(tmpCardListForPlayer.get(c)) ;
					}
					//�bergabe des Arrays mit den 7 Karten an den Spieler
					tmpLobby.getLobbyPlayers().get(i).setCards(CardListForPlayer);
					//Alle Elemente auf tmpCardListForPlayer werden gel�scht, damit Sie auch aus der urspr�nglichen Liste aller Karten verschwinden. So sind
					//nur noch die Karten in der urspr�nglichen Liste welche wirklich noch einem Spieler zugewiesen werden k�nnen.
					tmpCardListForPlayer.clear();
										
				}
				game.addLobby(tmpLobby);
				tmpMessage.setStatusCode(StatusCode.Success);
				tmpMessage.setPlayer(this.player);
				sendMessage(tmpMessage);
				ServerLobbyMessage tmpBroadcast = new ServerLobbyMessage(LobbyAction.LobbyStarted);
				tmpBroadcast.setLobby(tmpLobby);
				game.broadcastMessage(tmpBroadcast);
				break;
								
				}
				
			
			case JoinLobby: {
				ServerLobbyMessage tmpMessage = new ServerLobbyMessage(LobbyAction.JoinLobby);				
				ILobby tmpReceivedLobby = inMessage.getLobby();
				ILobby tmpActualLobby = null;
				IPlayer tmpPlayer = inMessage.getPlayer();
				boolean lobbyFound = false;
				
				// Existiert die Lobby ueberhaupt?
				synchronized(game.getLobbies()) {
					Iterator<ILobby> iter = game.getLobbies().iterator();
					while (iter.hasNext()) {
						ILobby L = iter.next();
						if (L.getLobbyName().equals(tmpReceivedLobby.getLobbyName())) {
							lobbyFound = true; 
							tmpActualLobby = L;
							break;
						}					
					}
				}
				
				// Falls die Lobby nicht existiert, wirf einen Fehler zurueck
				if (!lobbyFound) {
					tmpMessage.setPlayer(tmpPlayer);
					tmpMessage.setStatusCode(StatusCode.LobbyNotAvailable);
					sendMessage(tmpMessage);
					return;					
				}
				
				// Wenn die Lobby bereits voll ist, kann nicht gejoint werden.
				if (tmpActualLobby.getNumPlayers() == this.game.countLobbyPlayers(tmpActualLobby)) {
					tmpMessage.setPlayer(tmpPlayer);
					tmpMessage.setStatusCode(StatusCode.LobbyMaxPlayerReached);
					sendMessage(tmpMessage);
					break;
				} else {					
					this.player.setLobby(tmpActualLobby);
					tmpActualLobby.addPlayer(this.player);
					
					tmpPlayer = inMessage.getPlayer();
					tmpPlayer.setLobby(tmpActualLobby);
					
					tmpMessage.setPlayer(tmpPlayer);
					tmpMessage.setLobby(tmpActualLobby);
					
					tmpMessage.setStatusCode(StatusCode.Success);
					sendMessage(tmpMessage);
					// Zus�tzlich zur Antwort an den Beitretende einen Broadcast absetzen, damit die
					// andere Spieler �ber den neuen Spieler Bescheid wissen.
					ServerLobbyMessage tmpBroadcast = new ServerLobbyMessage(LobbyAction.PlayerJoined);
					tmpBroadcast.setPlayer(tmpPlayer);
					tmpBroadcast.setLobby(tmpActualLobby);
					game.broadcastMessage(tmpBroadcast);					
				}
				break;
			}
			default:
				break;
		}
	}
	
	private void handleGameMessages(ClientGameMessage inMessage) throws IOException, InterruptedException {
		logger.log(Level.INFO, "Client [" + clientId + "] hat gespielt");
		// Is action valid?
		switch (inMessage.getAction()) {
		case PlayCard: {
			IPlayer tmpPlayer = ((ClientGameMessage) inMessage).getPlayer();
			ICard tmpCard = ((ClientGameMessage) inMessage).getCard();

			if (((ClientGameMessage) inMessage).getAction() == GameAction.PlayCard) {
				tmpPlayer.playCard(tmpCard);
			}
			inMessage.setPlayer(tmpPlayer);
			inMessage.setCard(tmpCard);
			sendMessage(inMessage);
			break;
		}

		case BuildCard:
			// What board does the player have? What stages have already been built? -> try
			// to build the next stage
			int tmpStageToBuild = inMessage.getPlayer().getBoard().getNextStageToBuild();
			if (inMessage.getPlayer().getBoard().canBuild(inMessage.getPlayer())) {
				IPlayer tmpPlayer = ((ClientGameMessage) inMessage).getPlayer();
				ICard tmpCard = ((ClientGameMessage) inMessage).getCard();
				IBoard tmpBoard = ((ClientGameMessage) inMessage).getBoard();

				if (((ClientGameMessage) inMessage).getAction() == GameAction.BuildCard) {
					tmpPlayer.useCardForBuilding(tmpCard);
				}
				inMessage.setPlayer(tmpPlayer);
				inMessage.setCard(tmpCard);
				inMessage.setBoard(tmpBoard);
				sendMessage(inMessage);
				break;

			}

			return;
		case MonetizeCard: {
			IPlayer tmpPlayer = inMessage.getPlayer();
			ICard tmpCard = inMessage.getCard();
			
			// Muenze die Karte um
			tmpPlayer.monetizeCard(tmpCard);

			inMessage.setPlayer(tmpPlayer);
			sendMessage(inMessage);
			break;
		}
		default:
			return;
		}
	}
	
	public void sendMessage(Message inMessage) throws IOException {
		logger.log(Level.INFO, "sending message" + inMessage.getClass().getName());
		out.reset();
		out.writeObject(inMessage);
		out.flush();
	}

	public IPlayer getPlayer() {
		return player;
	}

	public void setPlayer(IPlayer player) {
		this.player = player;
	}
}
