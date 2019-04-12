package ch.fhnw.sevenwonders.model;

import java.util.ArrayList;

import ch.fhnw.sevenwonders.enums.*;
import ch.fhnw.sevenwonders.interfaces.*;

public class Player implements IPlayer {

	@Override
	public void setPasswordHash(String pwHash) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ResourceType> getResource(ResourceType resource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IBoard getBoard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isPasswordValid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean getCardPlayed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCardPlayed(Boolean cardPlayed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void joinLobby(ILobby lobby) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createLobby(String lobbyName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteLobby(ILobby lobby) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leaveLobby(String lobbyName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int compareTo(IPlayer player) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getIsAuthenticated() {
		// TODO Auto-generated method stub
		return false;
	}

}
