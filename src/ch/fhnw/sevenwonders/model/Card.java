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
	
	public Card (String name,
				 Age age,
				 CardType cardType,
				 int usedStartingFrom,
				 ValueCalculationType valueCalculationType,
				 ArrayList<ResourceType> value,
				 ArrayList<ResourceType> cost,
				 boolean usedForPyramid,
				 String imageName) {
		this.name = name;
		this.age = age;
		this.cardType = cardType;
		this.usedStartingFrom = usedStartingFrom;
		this.valueCalculationType = valueCalculationType;
		this.value = value;
		this.cost = cost;
		this.usedForPyramid = usedForPyramid;
		this.imageName = imageName;
	}
	
	public String getName() {
		return name;
	}
	
	public Age getAge() {
		return age;
	}
	
	public CardType getCardType() {
		return cardType;
	}
	
	public int getUsedStartingFrom() {
		return usedStartingFrom;
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
	// Überprüft, ob die Kosten der Karte (ArrayList cost) mit den zur Verfügung stehenden Resourcen gedeckt werden können.
	public boolean isPlayable(ArrayList<ResourceType> availableResources) {
		ArrayList<ResourceType> tempResources = new ArrayList<ResourceType>(availableResources.size());
		for (ResourceType rt : availableResources) tempResources.add(rt);
		boolean result = true;
		for (int i = 0; i < cost.size(); i++) {
			for (int j = 0; j < tempResources.size(); j++) {
				if (tempResources.get(j).equals(cost.get(i))) {
					tempResources.remove(j);
					result = true;
					break;
				}
				else {
					result = false;
				}
				
			}
			
		}
			
		return result;			
	}
	
	public ValueCalculationType getValueCalculationType() {
		return valueCalculationType;		
	}
	
	public String getImageName() {
		return imageName;
	}
}
