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

import ch.fhnw.sevenwonders.helper.DbHelper;
import ch.fhnw.sevenwonders.helper.InitHelper;
import ch.fhnw.sevenwonders.interfaces.IBoard;
import ch.fhnw.sevenwonders.interfaces.ICard;
import ch.fhnw.sevenwonders.interfaces.ILobby;
import ch.fhnw.sevenwonders.messages.Message;

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
}
