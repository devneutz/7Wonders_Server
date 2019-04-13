package ch.fhnw.sevenwonders.helper;

import java.util.ArrayList;

import ch.fhnw.sevenwonders.interfaces.ICard;

/**
 * 
 * @author lucasruesch
 *
 */
public class InitHelper {

	public static ArrayList<ICard> initAllCards(){
		ArrayList<ICard> tmpCardList = new ArrayList<ICard>() {
			/*new Card() {
				Eigeschaft1 = xy
			}*/
		};
		return tmpCardList;
	}
}
