package ch.fhnw.sevenwonders.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.fhnw.sevenwonders.enums.LobbyAction;
import ch.fhnw.sevenwonders.helper.DbHelper;
import ch.fhnw.sevenwonders.helper.InitHelper;
import ch.fhnw.sevenwonders.interfaces.IBoard;
import ch.fhnw.sevenwonders.interfaces.ICard;
import ch.fhnw.sevenwonders.interfaces.ILobby;
import ch.fhnw.sevenwonders.interfaces.IPlayer;
import ch.fhnw.sevenwonders.messages.Message;
import ch.fhnw.sevenwonders.messages.ServerLobbyMessage;

/**
 * Thread für die Verwaltung von Verbindungen - Server-Funktionalität
 * @author joeln
 *
 */
public class Game extends Thread{
	private final Logger logger = Logger.getLogger("");
	private ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	private List<ILobby> lobbies;
	
	// Statischer Client-Counter
	private static int clientId = 1;
	public static volatile int guestCounter = 0;
	
	private ArrayList<ICard> listOfAllCards = new ArrayList<ICard>();
	private ArrayList<IBoard> listOfAllBoards = new ArrayList<IBoard>();
	
	/**
	 * Konstruktor
	 */
	public Game() {
		super("ServerSocket");
		// Initialisieren des Kartenstapels
		listOfAllCards = InitHelper.initAllCards();
		
		// Initialisieren der Boards
		listOfAllBoards = InitHelper.initAllBoards();
		
		// Neue Thread-Safe list
		this.lobbies = Collections.synchronizedList(new ArrayList<ILobby>());
		
		// Anlegen der Datenbanktabellen etc.
		DbHelper.InitDatabase();
	}

	/**
	 * Verwaltet Verbindungen von Clients
	 */
	@Override
	public void run() {
		try (ServerSocket listener = new ServerSocket(50000, 10, null)) {
			logger.log(Level.INFO, "Server gestartet [Port 50000] - Warte auf eingehende Verbindungen");
			// Maximale Anzahl der Clients begrenzen um zu hohe Last zu vermeiden
			ExecutorService threadPool = Executors.newFixedThreadPool(20);
			while (true) {
				Socket socket = listener.accept();
				ClientThread clientHandlingThread = new ClientThread(socket, clientId, this);
				clientId++;
				clientHandlingThread.setDaemon(true);
				clients.add(clientHandlingThread);
				threadPool.execute(clientHandlingThread);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	};

	/**
	 * Getter für Kartenstapel
	 * @return
	 */
	public ArrayList<ICard> getListOfCards(){
		return this.listOfAllCards;
	}
	
	/**
	 * Getter für Boards
	 * @return
	 */
	public ArrayList<IBoard> getListOfBoards(){
		return this.listOfAllBoards;
	}
	
	/**
	 * Getter für Lobbies
	 * @return
	 */
	public ArrayList<ILobby> getLobbies(){
		return new ArrayList<ILobby>(this.lobbies);
	}
	
	/**
	 * Schickt eine bestimmte Message an alle Spieler - neue Lobby erstellt o.ä.
	 * @param inMessage
	 */
	public void broadcastMessage(Message inMessage) {
		logger.log(Level.INFO, "BROADCASTING MESSAGE");
		for(ClientThread ct : clients) {
			try {
				ct.sendMessage(inMessage);
			}
			catch(IOException inEx) {
				logger.log(Level.WARNING, "Broadcasting message failed", inEx);
			}
		}
	}
	
	/**
	 * Hinzufügen einer Lobby zum Spiel
	 * @param inLobby
	 */
	public void addLobby(ILobby inLobby) {
		synchronized(this.lobbies){
			this.lobbies.add(inLobby);
		}
	}
	
	/**
	 * Entfernen einer Lobby aus dem Spiel
	 * @param inLobby
	 */
	public void removeLobby(ILobby inLobby) {
		// Synchronized nötig? Ist eigentlich thread-safe list
		synchronized(this.lobbies) {
			// Erstellen eines iterators, damit das entfernen einer Lobby aus der liste funktioniert
			Iterator<ILobby> iter = this.lobbies.iterator();
			while(iter.hasNext()) {
				ILobby L = iter.next();
				if(L.getLobbyName().equals(inLobby.getLobbyName())) {
					iter.remove();
				}
				
			}
		}
	}
	
	/**
	 * Wie viele Spieler befinden sich bereits in der angegebenen Lobby?
	 * @param lobby
	 * @return
	 */
	public int countLobbyPlayers(ILobby lobby){
		return getPlayersForLobby(lobby).size();
	}
	
	/**
	 * Gibt eine Liste von ClientThreads für eine Lobby zurück. Wird benötigt, um ein Spiel zu starten und somit Kartenstapel korrekt weiterzugeben etc.
	 * @param inLobby
	 * @return
	 */
	public ArrayList<ClientThread> getPlayersForLobby(ILobby inLobby){
		ArrayList<ClientThread> tmpPlayers = new ArrayList<ClientThread>();
		synchronized(this.clients) {
			for(int x = 0; x < this.clients.size(); x++) {
				if(clients.get(x).getPlayer() == null) {continue;}
				if(clients.get(x).getPlayer().getLobby() != null) {
					if(clients.get(x).getPlayer().getLobby().getLobbyName().equals(inLobby.getLobbyName())) {
						tmpPlayers.add(clients.get(x));
					}
				
				}
			}	
		}
		return tmpPlayers;
	}
	
	/**
	 * Entfernen eines Clients - beim Schliessen eines Fensters zb.
	 * @param inClient
	 */
	public void removeClient(ClientThread inClient) {
		synchronized(this.clients) {
			this.clients.remove(inClient);
		}
		
		// Befindet er sich in einer Lobby, müssen die betroffenen Spieler auch informiert werden.
		if(inClient.getPlayer().getLobby() != null) {		
			// Handelt es sich sogar um den Lobby-Master wird die Lobby gelöscht
			if (inClient.getPlayer().getLobby().getLobbyMaster().getName().equals(inClient.getPlayer().getName())) {
				this.removeLobby(inClient.getPlayer().getLobby());
				ServerLobbyMessage tmpBroadcast = new ServerLobbyMessage(LobbyAction.LobbyDeleted);
				tmpBroadcast.setLobby(inClient.getPlayer().getLobby());
				broadcastMessage(tmpBroadcast);
				return;
			}
			IPlayer tmpPlayer = inClient.getPlayer();
			ILobby tmpActualLobby = null;
			// Existiert die Lobby ueberhaupt?
			synchronized (getLobbies()) {
				Iterator<ILobby> iter = getLobbies().iterator();
				while (iter.hasNext()) {
					ILobby L = iter.next();
					if (L.getLobbyName().equals(tmpPlayer.getLobby().getLobbyName())) {
						tmpActualLobby = L;
						break;
					}
				}
			}						
			tmpActualLobby.removePlayer(tmpPlayer);
			ServerLobbyMessage tmpBroadcast = new ServerLobbyMessage(LobbyAction.PlayerLeft);
			tmpBroadcast.setLobby(tmpActualLobby);
			tmpBroadcast.setPlayer(tmpPlayer);
			broadcastMessage(tmpBroadcast);
		}
	}
	
	/**
	 * Senden einer Message an einen bestimmten Spieler
	 * @param inPlayer
	 * @param inMessage
	 * @throws IOException
	 */
	public void sendMessageToPlayer(IPlayer inPlayer, Message inMessage) throws IOException {
		synchronized(this.clients) {
			for(int x = 0; x < this.clients.size(); x++) {
					if(clients.get(x).getPlayer().getName().equals(inPlayer.getName())) {
						clients.get(x).sendMessage(inMessage);
					}
			}	
		}
	}
}
