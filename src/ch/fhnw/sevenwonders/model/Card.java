package ch.fhnw.sevenwonders.model;

import java.util.ArrayList;

import ch.fhnw.sevenwonders.enums.Age;
import ch.fhnw.sevenwonders.enums.CardType;
import ch.fhnw.sevenwonders.enums.ResourceType;
import ch.fhnw.sevenwonders.enums.ValueCalculationType;
import ch.fhnw.sevenwonders.interfaces.ICard;

/**
 * 
 * @author Matteo
 *
 */

public class Card implements ICard {
	
	private String name;
	private Age age;
	private CardType cardType;
	private int usedStartingFrom;
	private ValueCalculationType valueCalculationType;
	private ArrayList<ResourceType> value;
	private ArrayList<ResourceType> cost;
	private boolean usedForPyramid;
	private String imageName;
	
	public Age getAge() {
		return age;
	}
	
	public CardType getCardType() {
		return cardType;
	}
	
	public boolean getUsedForPyramid() {
		return usedForPyramid;
	}
	
	public ArrayList<ResourceType> getCost() {
		return cost;
	}
	
	public ArrayList<ResourceType> getValue() {
		return value;
	}
	
	public boolean isPlayable(ArrayList<ResourceType> cost) {
		ArrayList<ResourceType> actResources;
		
		return true;			
	}
	
	public ValueCalculationType getValueCalculationType() {
		return valueCalculationType;		
	}
	
	public String getImageName() {
		return imageName;
	}
}
