package ch.fhnw.sevenwonders.model;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.fhnw.sevenwonders.interfaces.ILobby;

public class Game extends Thread{

private final Logger logger = Logger.getLogger("");
	private ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	private ArrayList<ILobby> lobbies = new ArrayList<ILobby>();
	
	public Game() {
		super("ServerSocket");
	}

	@Override
	public void run() {
		try (ServerSocket listener = new ServerSocket(50000, 10, null)) {
			logger.log(Level.INFO, "Server gestartet [Port 50000] - Warte auf eingehende Verbindungen");
			
			while (true) {
				Socket socket = listener.accept();
				ClientThread clientHandlingThread = new ClientThread(socket);
				clientHandlingThread.setDaemon(true);
				clients.add(clientHandlingThread);
				clientHandlingThread.start();
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	};

}
