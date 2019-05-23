package ch.fhnw.sevenwonders.model;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import ch.fhnw.sevenwonders.enums.*;
import ch.fhnw.sevenwonders.interfaces.*;
import ch.fhnw.sevenwonders.messages.*;
import ch.fhnw.sevenwonders.helper.DbHelper;
import ch.fhnw.sevenwonders.models.Player;

/**
 * 
 * @author Gabriel de Castilho, Joel Neutzner, Matteo Farneti, Ismael Liuzzi
 * 
 *         Diese Klasse stellt den ClientThread fuer den Server dar. Hier wird
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

	/**
	 * Konstruktor fuer den Clientthread
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
		} catch (SocketException inEx) {
			game.removeClient(this);
			logger.log(Level.INFO, "Client disconnected [Client " + clientId + "]");
		} catch (EOFException inEOFEx) {
		} catch (Exception inEx) {
			logger.log(Level.SEVERE, "Error handling Message  [Client " + clientId + "]", inEx);
		}
	}

	/**
	 * In dieser Methode werden die eingehenden Client Messages verarbeitet. Anhand
	 * der StartupAction wird entschieden, wie mit der Message umgegangen wird.
	 */
	private void handlingIncomingMessage(Message inMessage) throws IOException, InterruptedException {
		out.reset();
		if (inMessage instanceof ClientStartupMessage) {
			handleStartupMessages((ClientStartupMessage) inMessage);
		} else if (inMessage instanceof ClientLobbyMessage) {
			handleLobbyMessages((ClientLobbyMessage) inMessage);
		} else if (inMessage instanceof ClientGameMessage) {
			handleGameMessages((ClientGameMessage) inMessage);
		}
	}

	private void handleStartupMessages(ClientStartupMessage inMessage) throws IOException, InterruptedException {
		logger.log(Level.INFO, "Message received [Client " + clientId + "] - ClientStartupMessage");
		ServerStartupMessage tmpMessage = new ServerStartupMessage(inMessage.getActionType());
		IPlayer tmpPlayer = inMessage.getPlayer();

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

	private void handleLobbyMessages(ClientLobbyMessage inMessage) throws IOException, InterruptedException {
		switch (inMessage.getActionType()) {
		case CreateLobby: {
			ServerLobbyMessage tmpMessage = new ServerLobbyMessage(LobbyAction.CreateLobby);
			IPlayer tmpPlayer = inMessage.getPlayer();
			ILobby tmpLobby = inMessage.getLobby();
			boolean lobbyFound = false;
			
			// Existiert die Lobby tmpLobby bereits ?
			synchronized (game.getLobbies()) {
				Iterator<ILobby> iter = game.getLobbies().iterator();
				while (iter.hasNext()) {
					ILobby L = iter.next();
					if (L.getLobbyName().equals(tmpLobby.getLobbyName())) {
						lobbyFound = true;
						break;
					}
				}
			}
			// Falls die Lobby bereits existiert, wirf einen Fehler zurueck
			if (lobbyFound) {
				tmpMessage.setPlayer(tmpPlayer);
				tmpMessage.setStatusCode(StatusCode.LobbyAlreadyExists);
				sendMessage(tmpMessage);
				return;
			}
			tmpPlayer.setLobby(tmpLobby);
			tmpLobby.setLobbyMaster(tmpPlayer);
			tmpLobby.addPlayer(tmpPlayer);
			this.player.setLobby(tmpLobby);
			this.game.addLobby(tmpLobby);
			tmpMessage.setPlayer(tmpPlayer);
			tmpMessage.setStatusCode(StatusCode.Success);
			sendMessage(tmpMessage);
			// Zusï¿½tzlich zur Antwort an den Ersteller einen Broadcast absetzen, damit die
			// anderen Spieler ï¿½ber die neue Lobby Bescheid wissen.
			ServerLobbyMessage tmpBroadcast = new ServerLobbyMessage(LobbyAction.LobbyCreated);
			tmpBroadcast.setLobby(tmpLobby);
			game.broadcastMessage(tmpBroadcast);
			break;
		}
		case DeleteLobby: {
			ServerLobbyMessage tmpMessage = new ServerLobbyMessage(LobbyAction.DeleteLobby);
			// Zusï¿½tzlich zur Antwort an den Ersteller einen Broadcast absetzen, damit die
			// anderen Spieler ï¿½ber die Lï¿½schung der Lobby Bescheid wissen.
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
			// Zusaezlich zur Antwort an den Ersteller einen Broadcast absetzen, damit die
			// anderen Spieler ueber die Loeschung der Lobby Bescheid wissen.
			ILobby tmpLobby = inMessage.getLobby();
			IPlayer tmpPlayer = inMessage.getPlayer();
			ILobby tmpActualLobby = null;
			// Existiert die Lobby ueberhaupt?
			synchronized (game.getLobbies()) {
				Iterator<ILobby> iter = game.getLobbies().iterator();
				while (iter.hasNext()) {
					ILobby L = iter.next();
					if (L.getLobbyName().equals(tmpLobby.getLobbyName())) {
						tmpActualLobby = L;
						break;
					}
				}
			}
						
			tmpActualLobby.removePlayer(tmpPlayer);
			
			this.player.setLobby(null);
			tmpMessage.setStatusCode(StatusCode.Success);
			tmpMessage.setPlayer(this.player);
			sendMessage(tmpMessage);
			
			ServerLobbyMessage tmpBroadcast = new ServerLobbyMessage(LobbyAction.PlayerLeft);
			tmpBroadcast.setLobby(tmpActualLobby);
			tmpBroadcast.setPlayer(this.player);
			game.broadcastMessage(tmpBroadcast);
			break;

		}
		case StartLobby: {
			ILobby tmpLobby = inMessage.getLobby();
			ArrayList<ClientThread> cPlayersinLobby = game.getPlayersForLobby(tmpLobby);
			
			// Karten des entsprechenden Zeitalters holen
			ArrayList<ICard> tmpCardList = 
					new ArrayList<ICard>(game.getListOfCards().stream().filter(inc -> inc.getUsedStartingFrom() <= cPlayersinLobby.size() && inc.getAge() == Age.AgeI).collect(Collectors.toList()));
			
			logger.log(Level.INFO, "Thread [" + this.player.getName() + "]: Zusammenstellen der Karten fürs erste Zeitalter vollständig! Anzahl Karten: ["+tmpCardList.size()+"]");

			Random random = new Random();
			for (ClientThread c : cPlayersinLobby) {
				this.addOpponent(c.getPlayer());
			}

			for (ClientThread c : cPlayersinLobby) {
				//c.getPlayer().setBoard(game.getListOfBoards().get(random.nextInt(game.getListOfBoards().size())));
				c.getPlayer().setBoard(game.getListOfBoards().stream().filter(inBoard -> inBoard.getName().equals("Gizah A")).findAny().orElse(null));
				
				ArrayList<ICard> tmpCardStack = new ArrayList<ICard>();
				for (int z = 0; z < 7; z++) {
					// Zufällige Zuweisung der 7 Karten an die tmpCardListForPlayer
					int tmpBound = tmpCardList.size() - 1;
					if(tmpBound == 0) {
						tmpBound = 1;
					}
					ICard tmpCardToRemove = tmpCardList.get(random.nextInt(tmpBound));
					tmpCardStack.add(tmpCardToRemove);
					tmpCardList.remove(tmpCardToRemove);
				}
				// Übergabe des Arrays mit den 7 Karten an den Spieler
				c.getPlayer().setCardStack(tmpCardStack);
				if (!c.getPlayer().getName().equals(this.getPlayer().getName())) {
					for (ClientThread x : cPlayersinLobby) {
						c.addOpponent(x.getPlayer());
					}
				}

				ServerLobbyMessage tmpStartMessage = new ServerLobbyMessage(LobbyAction.LobbyStarted);
				tmpStartMessage.setStatusCode(StatusCode.Success);
				tmpStartMessage.setPlayer(c.getPlayer());

				tmpStartMessage.setOpponents(c.getOpponents());
				c.sendMessage(tmpStartMessage);
			}
			
			game.removeLobby(tmpLobby);
			break;
		}

		case JoinLobby: {
			ServerLobbyMessage tmpMessage = new ServerLobbyMessage(LobbyAction.JoinLobby);
			ILobby tmpReceivedLobby = inMessage.getLobby();
			ILobby tmpActualLobby = null;
			IPlayer tmpPlayer = inMessage.getPlayer();
			boolean lobbyFound = false;

			// Existiert die Lobby ueberhaupt?
			synchronized (game.getLobbies()) {
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
				// Zusï¿½tzlich zur Antwort an den Beitretende einen Broadcast absetzen, damit
				// die
				// andere Spieler über den neuen Spieler Bescheid wissen.
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
		// Is action valid?
		switch (inMessage.getAction()) {
		case PlayCard: {
			logger.log(Level.INFO, "Thread [" + this.player.getName() + "]: PlayCard ausgelöst");
			ServerGameMessage tmpMessage = new ServerGameMessage(GameAction.PlayCard);
			ICard tmpCard = ((ClientGameMessage) inMessage).getCard();
			this.player.setHasPlayedCard(true);

			this.player.playCard(tmpCard);

			tmpMessage.setPlayer(this.player);

			tmpMessage.setCard(tmpCard);
			tmpMessage.setStatusCode(StatusCode.Success);
			sendMessage(tmpMessage);

			tryFinishTurn();
			break;
		}

		case BuildCard:
			if (inMessage.getPlayer().getBoard().canBuild(inMessage.getPlayer())) {
				ServerGameMessage tmpMessage = new ServerGameMessage(GameAction.BuildCard);
				
				IPlayer tmpPlayer = ((ClientGameMessage) inMessage).getPlayer();
				ICard tmpCard = ((ClientGameMessage) inMessage).getCard();
				
				this.player.setHasPlayedCard(true);
				
				tmpPlayer.useCardForBuilding(tmpCard);
				
				tmpMessage.setPlayer(this.player);
				tmpMessage.setCard(tmpCard);
				sendMessage(tmpMessage);

				tryFinishTurn();
				break;
			}

			return;
		
		case MonetizeCard: {
			ServerGameMessage tmpMessage = new ServerGameMessage(GameAction.PlayCard);
			ICard tmpCard = inMessage.getCard();
			this.player.setHasPlayedCard(true);

			// Muenze die Karte um
			this.player.monetizeCard(tmpCard);

			tmpMessage.setCard(tmpCard);
			tmpMessage.setPlayer(this.player);
			tmpMessage.setStatusCode(StatusCode.Success);
			sendMessage(tmpMessage);

			tryFinishTurn();
			break;
		}
		default:
			return;
		}
	}

	public void sendMessage(Message inMessage) throws IOException {
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

	public void addOpponent(IPlayer inOpponent) {
		if (!inOpponent.getName().equals(this.player.getName())) {
			logger.log(Level.INFO,
					"Thread [" + getPlayer().getName() + "]: Setzen der Gegenspieler - '" + inOpponent.getName() + "'");
			this.opponents.add(inOpponent);
		}
	}
	
	public ArrayList<IPlayer> getOpponents(){
		return this.opponents;
	}
	public void tryFinishTurn() throws IOException {
		boolean roundFinished = true;
		for (IPlayer opp : opponents) {
			if (!opp.getHasPlayedCard()) {
				logger.log(Level.INFO, "Thread [" + this.player.getName() + "]: Gegenspieler '" + opp.getName()
						+ "' hat noch nicht gespielt");
				roundFinished = false;
				return;
			}
		}

		boolean ageFinished = this.player.getCardStack().size() == 1;

		if (ageFinished) {
			ArrayList<IPlayer> tmpAllPlayers = new ArrayList<IPlayer>();
			tmpAllPlayers.add(this.player);
			tmpAllPlayers.addAll(opponents);
			tmpAllPlayers.sort((IPlayer o1, IPlayer o2) -> o1.getName().compareTo(o2.getName()));

			// Ist bereits das zweite Zeitalter abgeschlossen?
			if(this.player.getCardStack().get(0).getAge() == Age.AgeII) {
				logger.log(Level.INFO, "Thread [" + this.player.getName() + "]: Zweites Zeitalter abgeschlossen");
				
				logger.log(Level.INFO, "Thread [" + this.player.getName() + "]: Evaluation der Kriegspunkte!");
				
				for (int i = 0; i < tmpAllPlayers.size(); i++) {
					int tmpEvaluationIndexLeft = i-1;
					int tmpEvaluationIndexRight = i + 1;
					
					// Linker Spieler nicht vorhanden -> gehe im Kreis zum letzten
					if(tmpEvaluationIndexLeft < 0) {
						tmpEvaluationIndexLeft = tmpAllPlayers.size() -1;
					}else if(tmpEvaluationIndexRight > tmpAllPlayers.size() -1) {
						tmpEvaluationIndexRight = 0;
					}					

					logger.log(Level.INFO, "Thread [" + tmpAllPlayers.get(i).getName() + "]: "
							+ "Vergleiche Kriegspunkte mit Spieler links [" + tmpAllPlayers.get(tmpEvaluationIndexLeft).getName() +"("+tmpAllPlayers.get(tmpEvaluationIndexLeft).getMilitaryPoints()+")] "
									+ "und Spieler rechts ["+tmpAllPlayers.get(tmpEvaluationIndexRight).getName() +"("+tmpAllPlayers.get(tmpEvaluationIndexRight).getMilitaryPoints()+")]");
					
					tmpAllPlayers.get(i).militaryConflict(tmpAllPlayers.get(tmpEvaluationIndexLeft), tmpAllPlayers.get(tmpEvaluationIndexRight), Age.AgeII);					
				}
				
				for(IPlayer p : tmpAllPlayers) {
					ServerEvaluationMessage tmpEvaluationMessage = new ServerEvaluationMessage(GameAction.Evaluation);
					tmpEvaluationMessage.setStatusCode(StatusCode.Success);					

					tmpEvaluationMessage.setPlayer(p);
					ArrayList<IPlayer> tmpOpponents = (ArrayList<IPlayer>)tmpAllPlayers.clone();
					tmpOpponents.remove(p);
					tmpEvaluationMessage.setOpponents(tmpOpponents);
					game.sendMessageToPlayer(p, tmpEvaluationMessage);
				}

				logger.log(Level.INFO, "Thread [" + this.player.getName() + "]: Auswertung des Spiels abgeschlossen.");
				return;
			}
			
			logger.log(Level.INFO, "Thread [" + this.player.getName() + "]: Erstes Zeitalter abgeschlossen");

			logger.log(Level.INFO, "Thread [" + this.player.getName() + "]: Verteilen von neuen Karten-Stacks");
			// Zufällig Karten aus erstem Zeitalter für erste Runde den Spielern zuweisen
			// und aus ListofCard entfernen
			ArrayList<ICard> tmpAgeIICards = new ArrayList<ICard>(game.getListOfCards()
					.stream()
					.filter(inc -> inc.getUsedStartingFrom() <= tmpAllPlayers.size() && inc.getAge() == Age.AgeI)
					.collect(Collectors.toList()));
			
			Random random = new Random();

			for (IPlayer p : tmpAllPlayers) {
				ArrayList<ICard> tmpCardStack = new ArrayList<ICard>();
				for (int z = 0; z < 7; z++) {
					// Zufällige Zuweisung der 7 Karten an die tmpCardListForPlayer
					int tmpBound = tmpAgeIICards.size() - 1;
					if(tmpBound == 0) {
						tmpBound = 1;
					}
					ICard tmpCardToRemove = tmpAgeIICards.get(random.nextInt(tmpBound));
					tmpCardStack.add(tmpCardToRemove);
					tmpAgeIICards.remove(tmpCardToRemove);
				}
				// Übergabe des Arrays mit den 7 Karten an den Spieler
				p.setCardStack(tmpCardStack);
				p.setHasPlayedCard(false);
				logger.log(Level.INFO,
						"Thread [" + p.getName() + "]: Zuweisen des neuen Stacks an '" + p.getName() + "'");

				ServerGameMessage tmpNewRoundMessage = new ServerGameMessage(GameAction.PlayCard);
				tmpNewRoundMessage.setStatusCode(StatusCode.NewRound);

				tmpNewRoundMessage.setPlayer(p);
				game.sendMessageToPlayer(p, tmpNewRoundMessage);
			}

			return;
		}

		if (roundFinished) {
			logger.log(Level.INFO, "Thread [" + this.player.getName() + "]: Runde abgeschlossen");

			ServerGameMessage tmpNewRoundMessage = new ServerGameMessage(GameAction.PlayCard);
			tmpNewRoundMessage.setStatusCode(StatusCode.NewRound);

			ArrayList<ArrayList<ICard>> tmpCardStacks = new ArrayList<ArrayList<ICard>>();

			ArrayList<IPlayer> tmpAllPlayers = new ArrayList<IPlayer>();
			tmpAllPlayers.add(this.player);
			tmpAllPlayers.addAll(opponents);
			tmpAllPlayers.sort((IPlayer o1, IPlayer o2) -> o1.getName().compareTo(o2.getName()));

			for (IPlayer p : tmpAllPlayers) {
				tmpCardStacks.add((ArrayList<ICard>) p.getCardStack().clone());
			}

			for (int i = 0; i < tmpAllPlayers.size(); i++) {
				int tmpIndexToTakeCardsFrom = i + 1;
				if (tmpIndexToTakeCardsFrom > tmpAllPlayers.size() - 1) {
					tmpIndexToTakeCardsFrom = 0;
				}

				logger.log(Level.INFO,
						"Thread [" + tmpAllPlayers.get(i).getName() + "]: Spieler '" + tmpAllPlayers.get(i).getName()
								+ "' nimmt Karten von Spieler '" + tmpAllPlayers.get(tmpIndexToTakeCardsFrom).getName()
								+ "'");
				tmpAllPlayers.get(i).setCardStack(tmpCardStacks.get(tmpIndexToTakeCardsFrom));
				tmpAllPlayers.get(i).setHasPlayedCard(false);
				tmpNewRoundMessage.setPlayer(tmpAllPlayers.get(i));
				ArrayList<IPlayer> opp =(ArrayList<IPlayer>) tmpAllPlayers.clone();
				opp.remove(tmpAllPlayers.get(i));
				tmpNewRoundMessage.setOpponents(opp);
				game.sendMessageToPlayer(tmpAllPlayers.get(i), tmpNewRoundMessage);
			}
		}
	}
}
