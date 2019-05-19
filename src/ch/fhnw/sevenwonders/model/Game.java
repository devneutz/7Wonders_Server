package ch.fhnw.sevenwonders.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.fhnw.sevenwonders.enums.Age;
import ch.fhnw.sevenwonders.enums.LobbyAction;
import ch.fhnw.sevenwonders.enums.StatusCode;
import ch.fhnw.sevenwonders.helper.DbHelper;
import ch.fhnw.sevenwonders.helper.InitHelper;
import ch.fhnw.sevenwonders.interfaces.IBoard;
import ch.fhnw.sevenwonders.interfaces.ICard;
import ch.fhnw.sevenwonders.interfaces.ILobby;
import ch.fhnw.sevenwonders.interfaces.IPlayer;
import ch.fhnw.sevenwonders.messages.Message;
import ch.fhnw.sevenwonders.messages.ServerLobbyMessage;

public class Game extends Thread{
	private final Logger logger = Logger.getLogger("");
	private ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	private List<ILobby> lobbies;
	private static int clientId = 1;
	
	private ArrayList<ICard> listOfAllCards = new ArrayList<ICard>();
	private ArrayList<IBoard> listOfAllBoards = new ArrayList<IBoard>();
	
	public Game() {
		super("ServerSocket");
		listOfAllCards = InitHelper.initAllCards();
		listOfAllBoards = InitHelper.initAllBoards();
		this.lobbies = Collections.synchronizedList(new ArrayList<ILobby>());
		DbHelper.InitDatabase();
	}

	@Override
	public void run() {
		try (ServerSocket listener = new ServerSocket(50000, 10, null)) {
			logger.log(Level.INFO, "Server gestartet [Port 50000] - Warte auf eingehende Verbindungen");
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

	public ArrayList<ICard> getListOfCards(){
		return this.listOfAllCards;
	}
	
	public ArrayList<IBoard> getListOfBoards(){
		return this.listOfAllBoards;
	}
	
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
	
	public void addLobby(ILobby inLobby) {
		synchronized(this.lobbies){
			this.lobbies.add(inLobby);
		}
	}
	
	public void removeLobby(ILobby inLobby) {
		synchronized(this.lobbies) {
			Iterator<ILobby> iter = this.lobbies.iterator();
			while(iter.hasNext()) {
				ILobby L = iter.next();
				if(L.getLobbyName().equals(inLobby.getLobbyName())) {
					iter.remove();
				}
				
			}
		}
	}
	
	public ArrayList<ILobby> getLobbies(){
		return new ArrayList<ILobby>(this.lobbies);
	}
	
	
	public int countLobbyPlayers(ILobby lobby){
		return getPlayersForLobby(lobby).size();
	}
	
	public ArrayList<IPlayer> getPlayersForLobby(ILobby inLobby){
		ArrayList<IPlayer> tmpPlayers = new ArrayList<IPlayer>();
		synchronized(this.clients) {
			for(int x = 0; x < this.clients.size(); x++) {
				if(clients.get(x).getPlayer().getLobby() != null) {
					if(clients.get(x).getPlayer().getLobby().getLobbyName() == inLobby.getLobbyName()) {
						tmpPlayers.add(clients.get(x).getPlayer());
					}
				
				}
			}	
		}
		return tmpPlayers;
	}
	
	public void removeClient(ClientThread inClient) {
		synchronized(this.clients) {
			this.clients.remove(inClient);
		}
		
		if (inClient.getPlayer().getLobby().getLobbyMaster().getName().equalsIgnoreCase(inClient.getPlayer().getName())) {
			this.removeLobby(inClient.getPlayer().getLobby());
			ServerLobbyMessage tmpBroadcast = new ServerLobbyMessage(LobbyAction.LobbyDeleted);
			tmpBroadcast.setLobby(inClient.getPlayer().getLobby());
			broadcastMessage(tmpBroadcast);
		}
		
		if(inClient.getPlayer().getLobby() != null) {
			ServerLobbyMessage tmpBroadcast = new ServerLobbyMessage(LobbyAction.PlayerLeft);
			tmpBroadcast.setLobby(inClient.getPlayer().getLobby());
			tmpBroadcast.setPlayer(inClient.getPlayer());
			broadcastMessage(tmpBroadcast);
		}
	}
}
