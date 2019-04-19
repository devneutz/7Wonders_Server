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
		ArrayList<ResourceType> tempResources = new ArrayList<ResourceType>(inResources.size());
		for (ResourceType rt : inResources) tempResources.add(rt);
		boolean result = true;
		
		// �berpr�ft, ob die Kosten (StepOneCost) im Step 1 mit den verf�gbaren Resourcen (inResources) gedeckt werden k�nnen.
		if (inStep == 1) {
			for (int i = 0; i < stepOneCost.size(); i++) {
				for (int j = 0; j < tempResources.size(); j++) {
					if (tempResources.get(j).equals(stepOneCost.get(i))) {
						tempResources.remove(j);
						result = true;
						break;
					}
					else {
						result = false;
					}
				}
			}
		}
		// �berpr�ft, ob die Kosten (StepTwoCost) im Step 2 mit den verf�gbaren Resourcen (inResources) gedeckt werden k�nnen.
		if (inStep == 2) {
			for (int i = 0; i < stepTwoCost.size(); i++) {
				for (int j = 0; j < tempResources.size(); j++) {
					if (tempResources.get(j).equals(stepTwoCost.get(i))) {
						tempResources.remove(j);
						result = true;
						break;
					}
					else {
						result = false;
					}
				}
			}			
		}
		return result;
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
