package ch.fhnw.sevenwonders.model;

import java.util.ArrayList;

import ch.fhnw.sevenwonders.enums.ResourceType;
import ch.fhnw.sevenwonders.interfaces.IBoard;

/**
 * 
 * @author Matte
 *
 */

public class Board implements IBoard {
	
	private ArrayList<ResourceType> stepOneValue;
	private ArrayList<ResourceType> stepOneCost;
	private ArrayList<ResourceType> stepTwoValue;
	private ArrayList<ResourceType> stepTwoCost;
	private String imageName;

	@Override
	public Boolean canBuild(int inStep, ArrayList<ResourceType> inResources) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getImageName() {
		return imageName;
	}

	@Override
	public ArrayList<ResourceType> getStepOneValue() {
		return stepOneValue;
	}

	@Override
	public ArrayList<ResourceType> getStepTwoValue() {
		return stepTwoValue;
	}

}
