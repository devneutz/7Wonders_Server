package ch.fhnw.sevenwonders.helper;

import java.util.ArrayList;

import ch.fhnw.sevenwonders.enums.Age;
import ch.fhnw.sevenwonders.enums.CardType;
import ch.fhnw.sevenwonders.interfaces.ICard;
import ch.fhnw.sevenwonders.model.Card;

/**
 * 
 * @author Matteo
 *
 */
public class InitHelper {

	public static ArrayList<ICard> initAllCards(){
		ArrayList<ICard> tmpCardList = new ArrayList<ICard>() {
			/*Card card =	new Card("Ziegelei", Age.AgeI, CardType.RawMaterials, 1);*/

			
			
		};
		return tmpCardList;
	}
}
