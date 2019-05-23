package ch.fhnw.sevenwonders.helper;

import java.util.ArrayList;
import java.util.Arrays;

import ch.fhnw.sevenwonders.enums.Age;
import ch.fhnw.sevenwonders.enums.CardType;
import ch.fhnw.sevenwonders.enums.ResourceType;
import ch.fhnw.sevenwonders.enums.ValueCalculationType;
import ch.fhnw.sevenwonders.interfaces.IBoard;
import ch.fhnw.sevenwonders.interfaces.ICard;
import ch.fhnw.sevenwonders.models.Board;
import ch.fhnw.sevenwonders.models.Card;

/**
 * 
 * @author Matteo Farneti
 *
 */
public class InitHelper {
	
	public static ArrayList<IBoard> initAllBoards(){
		ArrayList<IBoard> tmpBoardList = new ArrayList<IBoard>();
		
		ArrayList<ResourceType> tmpResource = new ArrayList<ResourceType>();
		ArrayList<ResourceType> tmpValueOne = new ArrayList<ResourceType>();
		ArrayList<ResourceType> tmpCostOne = new ArrayList<ResourceType>();
		ArrayList<ResourceType> tmpValueTwo = new ArrayList<ResourceType>();
		ArrayList<ResourceType> tmpCostTwo = new ArrayList<ResourceType>();
		ArrayList<ResourceType> tmpValueThree = new ArrayList<ResourceType>();
		ArrayList<ResourceType> tmpCostThree = new ArrayList<ResourceType>();
		
		tmpResource.add(ResourceType.Papyrus);
		tmpValueOne.add(ResourceType.VictoryPoint);
		tmpValueOne.add(ResourceType.VictoryPoint);
		tmpValueOne.add(ResourceType.VictoryPoint);
		tmpCostOne.add(ResourceType.Stone);
		tmpCostOne.add(ResourceType.Stone);
		tmpValueTwo.add(ResourceType.Coin);
		tmpValueTwo.add(ResourceType.Coin);
		tmpValueTwo.add(ResourceType.Coin);
		tmpValueTwo.add(ResourceType.Coin);
		tmpValueTwo.add(ResourceType.Coin);
		tmpValueTwo.add(ResourceType.Coin);
		tmpValueTwo.add(ResourceType.Coin);
		tmpValueTwo.add(ResourceType.Coin);
		tmpValueTwo.add(ResourceType.Coin);
		tmpCostTwo.add(ResourceType.Wood);
		tmpCostTwo.add(ResourceType.Wood);
		tmpValueThree.add(ResourceType.VictoryPoint);
		tmpValueThree.add(ResourceType.VictoryPoint);
		tmpValueThree.add(ResourceType.VictoryPoint);
		tmpValueThree.add(ResourceType.VictoryPoint);
		tmpValueThree.add(ResourceType.VictoryPoint);
		tmpValueThree.add(ResourceType.VictoryPoint);
		tmpValueThree.add(ResourceType.VictoryPoint);
		tmpCostThree.add(ResourceType.Papyrus);
		tmpCostThree.add(ResourceType.Papyrus);
		
		
		tmpBoardList.add(new Board("Ephesos A", tmpResource, tmpValueOne, tmpCostOne, tmpValueTwo, tmpCostTwo, tmpValueThree, tmpCostThree, "Board_Ephesos_A.jpg"));
				
		tmpResource.clear();
		tmpValueOne.clear();
		tmpCostOne.clear();
		tmpValueTwo.clear();
		tmpCostTwo.clear();
		tmpValueThree.clear();
		tmpCostThree.clear();
		
		//----------------------------------------------------------
		
		tmpResource.add(ResourceType.Papyrus);
		
		tmpValueOne.addAll(Arrays.asList(ResourceType.VictoryPoint, ResourceType.VictoryPoint, ResourceType.Coin, 
				ResourceType.Coin, ResourceType.Coin, ResourceType.Coin, ResourceType.Coin));
		
		tmpCostOne.addAll(Arrays.asList(ResourceType.Stone, ResourceType.Stone));
		
		tmpValueTwo.addAll(Arrays.asList(ResourceType.VictoryPoint, ResourceType.VictoryPoint, ResourceType.VictoryPoint,
				ResourceType.Coin, ResourceType.Coin, ResourceType.Coin, ResourceType.Coin));
		
		tmpCostTwo.addAll(Arrays.asList(ResourceType.Wood, ResourceType.Wood));
		
		tmpValueThree.addAll(Arrays.asList(ResourceType.VictoryPoint, ResourceType.VictoryPoint, ResourceType.VictoryPoint, ResourceType.VictoryPoint,
				ResourceType.VictoryPoint, ResourceType.Coin, ResourceType.Coin, ResourceType.Coin, ResourceType.Coin));
		
		tmpCostThree.addAll(Arrays.asList(ResourceType.Papyrus, ResourceType.Cloth, ResourceType.Glas));
				
		tmpBoardList.add(new Board("Ephesos B", tmpResource, tmpValueOne, tmpCostOne, tmpValueTwo, tmpCostTwo, tmpValueThree, tmpCostThree, "Board_Ephesos_B.jpg"));
				
		tmpResource.clear();
		tmpValueOne.clear();
		tmpCostOne.clear();
		tmpValueTwo.clear();
		tmpCostTwo.clear();
		tmpValueThree.clear();
		tmpCostThree.clear();
		
		//----------------------------------------------------------
		
		tmpResource.add(ResourceType.Stone);
		
		tmpValueOne.addAll(Arrays.asList(ResourceType.VictoryPoint, ResourceType.VictoryPoint, ResourceType.VictoryPoint));
		
		tmpCostOne.addAll(Arrays.asList(ResourceType.Stone, ResourceType.Stone));
		
		tmpValueTwo.addAll(Arrays.asList(ResourceType.VictoryPoint, ResourceType.VictoryPoint, ResourceType.VictoryPoint,
				ResourceType.VictoryPoint, ResourceType.VictoryPoint));
		
		tmpCostTwo.addAll(Arrays.asList(ResourceType.Wood, ResourceType.Wood, ResourceType.Wood));
		
		tmpValueThree.addAll(Arrays.asList(ResourceType.VictoryPoint, ResourceType.VictoryPoint, ResourceType.VictoryPoint, ResourceType.VictoryPoint,
				ResourceType.VictoryPoint, ResourceType.VictoryPoint, ResourceType.VictoryPoint));
		
		tmpCostThree.addAll(Arrays.asList(ResourceType.Stone, ResourceType.Stone, ResourceType.Stone, ResourceType.Stone));
				
		tmpBoardList.add(new Board("Gizah A", tmpResource, tmpValueOne, tmpCostOne, tmpValueTwo, tmpCostTwo, tmpValueThree, tmpCostThree, "Board_Gizah_A.jpg"));
				
		tmpResource.clear();
		tmpValueOne.clear();
		tmpCostOne.clear();
		tmpValueTwo.clear();
		tmpCostTwo.clear();
		tmpValueThree.clear();
		tmpCostThree.clear();
		
		//----------------------------------------------------------
		
		tmpResource.add(ResourceType.Ore);
		
		tmpValueOne.addAll(Arrays.asList(ResourceType.VictoryPoint, ResourceType.VictoryPoint, ResourceType.VictoryPoint));
		
		tmpCostOne.addAll(Arrays.asList(ResourceType.Wood, ResourceType.Wood));
		
		tmpValueTwo.addAll(Arrays.asList(ResourceType.MilitaryMight, ResourceType.MilitaryMight));
		
		tmpCostTwo.addAll(Arrays.asList(ResourceType.Clay, ResourceType.Clay, ResourceType.Clay));
		
		tmpValueThree.addAll(Arrays.asList(ResourceType.VictoryPoint, ResourceType.VictoryPoint, ResourceType.VictoryPoint, ResourceType.VictoryPoint,
				ResourceType.VictoryPoint, ResourceType.VictoryPoint, ResourceType.VictoryPoint));
		
		tmpCostThree.addAll(Arrays.asList(ResourceType.Ore, ResourceType.Ore, ResourceType.Ore, ResourceType.Ore));
				
		tmpBoardList.add(new Board("Rhodos A", tmpResource, tmpValueOne, tmpCostOne, tmpValueTwo, tmpCostTwo, tmpValueThree, tmpCostThree, "Board_Rhodos_A.jpg"));
				
		tmpResource.clear();
		tmpValueOne.clear();
		tmpCostOne.clear();
		tmpValueTwo.clear();
		tmpCostTwo.clear();
		tmpValueThree.clear();
		tmpCostThree.clear();
		
		//----------------------------------------------------------
		
		tmpResource.add(ResourceType.Ore);
		
		tmpValueTwo.addAll(Arrays.asList(ResourceType.MilitaryMight, ResourceType.VictoryPoint, ResourceType.VictoryPoint, ResourceType.VictoryPoint,
				ResourceType.Coin, ResourceType.Coin, ResourceType.Coin));
		
		tmpCostTwo.addAll(Arrays.asList(ResourceType.Stone, ResourceType.Stone, ResourceType.Stone));
		
		tmpValueThree.addAll(Arrays.asList(ResourceType.MilitaryMight, ResourceType.VictoryPoint, ResourceType.VictoryPoint, ResourceType.VictoryPoint,
				ResourceType.VictoryPoint, ResourceType.Coin, ResourceType.Coin, ResourceType.Coin, ResourceType.Coin));
		
		tmpCostThree.addAll(Arrays.asList(ResourceType.Ore, ResourceType.Ore, ResourceType.Ore, ResourceType.Ore));
				
		tmpBoardList.add(new Board("Rhodos B", tmpResource, tmpValueOne, tmpCostOne, tmpValueTwo, tmpCostTwo, tmpValueThree, tmpCostThree, "Board_Rhodos_B.jpg"));
		
		for( int x = 0; x < tmpBoardList.size(); x++) { //@JOE fragen
			if(tmpBoardList.get(x).getImageName().equals("Board_Rhodos_B.jpg")) {
				tmpBoardList.get(x).setStepOneBuilt(true);
			}
		}
		
		tmpResource.clear();
		tmpValueOne.clear();
		tmpCostOne.clear();
		tmpValueTwo.clear();
		tmpCostTwo.clear();
		tmpValueThree.clear();
		tmpCostThree.clear();
		
		//----------------------------------------------------------
		
		return tmpBoardList;
	}
	
	public static ArrayList<ICard> initAllCards(){
		ArrayList<ICard> tmpCardList = new ArrayList<ICard>();
		
		ArrayList<ResourceType> tmpCosts = new ArrayList<ResourceType>();
		ArrayList<ResourceType> tmpValue = new ArrayList<ResourceType>();
		
		tmpValue.add(ResourceType.Wood);
		
		tmpCardList.add(new Card("Holzplatz", Age.AgeI, CardType.RawMaterials, 3, null, tmpValue, tmpCosts, false, "RM_Holzplatz_3.jpg"));
		tmpCardList.add(new Card("Holzplatz", Age.AgeI, CardType.RawMaterials, 4, null, tmpValue, tmpCosts, false, "RM_Holzplatz_4.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpValue.add(ResourceType.Ore);
		
		tmpCardList.add(new Card("Erzbergwerk", Age.AgeI, CardType.RawMaterials, 3, null, tmpValue, tmpCosts, false, "RM_Erzbergwerk_3.jpg"));
		tmpCardList.add(new Card("Erzbergwerk", Age.AgeI, CardType.RawMaterials, 4, null, tmpValue, tmpCosts, false, "RM_Erzbergwerk_4.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpValue.add(ResourceType.Stone);
		
		tmpCardList.add(new Card("Steinbruch", Age.AgeI, CardType.RawMaterials, 3, null, tmpValue, tmpCosts, false, "RM_Steinbruch_3.jpg"));
		tmpCardList.add(new Card("Steinbruch", Age.AgeI, CardType.RawMaterials, 5, null, tmpValue, tmpCosts, false, "RM_Steinbruch_5.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpValue.add(ResourceType.Clay);
		
		tmpCardList.add(new Card("Ziegelei", Age.AgeI, CardType.RawMaterials, 3, null, tmpValue, tmpCosts, false, "RM_Ziegelei_3.jpg"));
		tmpCardList.add(new Card("Ziegelei", Age.AgeI, CardType.RawMaterials, 5, null, tmpValue, tmpCosts, false, "RM_Ziegelei_5.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Stone);
		tmpValue.add(ResourceType.Clay);
		
		tmpCardList.add(new Card("Ausgrabungsstaette", Age.AgeI, CardType.RawMaterials, 4, ValueCalculationType.Or, tmpValue, tmpCosts, false, "RM_Ausgrabungsstaette_4.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Wood);
		tmpValue.add(ResourceType.Clay);
		
		tmpCardList.add(new Card("Baumschule", Age.AgeI, CardType.RawMaterials, 6, ValueCalculationType.Or, tmpValue, tmpCosts, false, "RM_Baumschule_6.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------

		tmpCosts.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Wood);
		tmpValue.add(ResourceType.Stone);
		
		tmpCardList.add(new Card("Forstwirtschaft", Age.AgeI, CardType.RawMaterials, 3, ValueCalculationType.Or, tmpValue, tmpCosts, false, "RM_Forstwirtschaft_3.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------

		tmpCosts.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Ore);
		tmpValue.add(ResourceType.Stone);
				
		tmpCardList.add(new Card("Mine", Age.AgeI, CardType.RawMaterials, 6, ValueCalculationType.Or, tmpValue, tmpCosts, false, "RM_Mine_6.jpg"));
				
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Ore);
		tmpValue.add(ResourceType.Clay);
				
		tmpCardList.add(new Card("Tongrube", Age.AgeI, CardType.RawMaterials, 3, ValueCalculationType.Or, tmpValue, tmpCosts, false, "RM_Tongrube_3.jpg"));
				
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Ore);
		tmpValue.add(ResourceType.Wood);
				
		tmpCardList.add(new Card("Waldhoehle", Age.AgeI, CardType.RawMaterials, 5, ValueCalculationType.Or, tmpValue, tmpCosts, false, "RM_Waldhoehle_5.jpg"));
				
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpValue.add(ResourceType.Glas);
				
		tmpCardList.add(new Card("Glashuette", Age.AgeI, CardType.ManufacturedGoods, 3, null, tmpValue, tmpCosts, false, "MG_Glashuette_3.jpg"));
		tmpCardList.add(new Card("Glashuette", Age.AgeI, CardType.ManufacturedGoods, 6, null, tmpValue, tmpCosts, false, "MG_Glashuette_6.jpg"));
		tmpCardList.add(new Card("Glashuette", Age.AgeII, CardType.ManufacturedGoods, 3, null, tmpValue, tmpCosts, false, "MG_Glashuette_3.jpg"));
		tmpCardList.add(new Card("Glashuette", Age.AgeII, CardType.ManufacturedGoods, 5, null, tmpValue, tmpCosts, false, "MG_Glashuette_5.jpg"));
				
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpValue.add(ResourceType.Papyrus);
		
		tmpCardList.add(new Card("Presse", Age.AgeI, CardType.ManufacturedGoods, 3, null, tmpValue, tmpCosts, false, "MG_Presse_3.jpg"));
		tmpCardList.add(new Card("Presse", Age.AgeI, CardType.ManufacturedGoods, 6, null, tmpValue, tmpCosts, false, "MG_Presse_6.jpg"));
		tmpCardList.add(new Card("Presse", Age.AgeII, CardType.ManufacturedGoods, 3, null, tmpValue, tmpCosts, false, "MG_Presse_3.jpg"));
		tmpCardList.add(new Card("Presse", Age.AgeII, CardType.ManufacturedGoods, 5, null, tmpValue, tmpCosts, false, "MG_Presse_5.jpg"));
				
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpValue.add(ResourceType.Cloth);
		
		tmpCardList.add(new Card("Webstuhl", Age.AgeI, CardType.ManufacturedGoods, 3, null, tmpValue, tmpCosts, false, "MG_Webstuhl_3.jpg"));
		tmpCardList.add(new Card("Webstuhl", Age.AgeI, CardType.ManufacturedGoods, 6, null, tmpValue, tmpCosts, false, "MG_Webstuhl_6.jpg"));
		tmpCardList.add(new Card("Webstuhl", Age.AgeII, CardType.ManufacturedGoods, 3, null, tmpValue, tmpCosts, false, "MG_Webstuhl_3.jpg"));
		tmpCardList.add(new Card("Webstuhl", Age.AgeII, CardType.ManufacturedGoods, 5, null, tmpValue, tmpCosts, false, "MG_Webstuhl_5.jpg"));
				
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpValue.add(ResourceType.VictoryPoint);
		tmpValue.add(ResourceType.VictoryPoint);
		
		tmpCardList.add(new Card("Altar", Age.AgeI, CardType.CivilianStructures, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Altar_3.jpg"));
		tmpCardList.add(new Card("Altar", Age.AgeI, CardType.CivilianStructures, 5, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Altar_5.jpg"));
		tmpCardList.add(new Card("Theater", Age.AgeI, CardType.CivilianStructures, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Theater_3.jpg"));
		tmpCardList.add(new Card("Theater", Age.AgeI, CardType.CivilianStructures, 6, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Theater_6.jpg"));
						
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpValue.add(ResourceType.VictoryPoint);
		tmpValue.add(ResourceType.VictoryPoint);
		tmpValue.add(ResourceType.VictoryPoint);
		
		tmpCardList.add(new Card("Pfandhaus", Age.AgeI, CardType.CivilianStructures, 4, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Pfandhaus_4.jpg"));
		tmpCardList.add(new Card("Pfandhaus", Age.AgeI, CardType.CivilianStructures, 7, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Pfandhaus_7.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Stone);
		tmpValue.add(ResourceType.VictoryPoint);
		tmpValue.add(ResourceType.VictoryPoint);
		tmpValue.add(ResourceType.VictoryPoint);
		
		tmpCardList.add(new Card("Baeder", Age.AgeI, CardType.CivilianStructures, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Baeder_3.jpg"));
		tmpCardList.add(new Card("Baeder", Age.AgeI, CardType.CivilianStructures, 7, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Baeder_7.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Cloth);
		tmpValue.add(ResourceType.Compasses);
		
		tmpCardList.add(new Card("Apotheke", Age.AgeI, CardType.ScientificStructures, 3, null, tmpValue, tmpCosts, false, "SS_Apotheke_3.jpg"));
		tmpCardList.add(new Card("Apotheke", Age.AgeI, CardType.ScientificStructures, 5, null, tmpValue, tmpCosts, false, "SS_Apotheke_5.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Papyrus);
		tmpValue.add(ResourceType.StonePanel);
		
		tmpCardList.add(new Card("Skriptorium", Age.AgeI, CardType.ScientificStructures, 3, null, tmpValue, tmpCosts, false, "SS_Skriptorium_3.jpg"));
		tmpCardList.add(new Card("Skriptorium", Age.AgeI, CardType.ScientificStructures, 4, null, tmpValue, tmpCosts, false, "SS_Skriptorium_4.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Glas);
		tmpValue.add(ResourceType.GearWheel);
		
		tmpCardList.add(new Card("Werkstatt", Age.AgeI, CardType.ScientificStructures, 3, null, tmpValue, tmpCosts, false, "SS_Werkstatt_3.jpg"));
		tmpCardList.add(new Card("Werkstatt", Age.AgeI, CardType.ScientificStructures, 7, null, tmpValue, tmpCosts, false, "SS_Werkstatt_7.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Wood);
		tmpValue.add(ResourceType.MilitaryMight);
		
		tmpCardList.add(new Card("Befestigungsanlage", Age.AgeI, CardType.MilitaryStructures, 3, null, tmpValue, tmpCosts, false, "MS_Befestigungsanlage_3.jpg"));
		tmpCardList.add(new Card("Befestigungsanlage", Age.AgeI, CardType.MilitaryStructures, 7, null, tmpValue, tmpCosts, false, "MS_Befestigungsanlage_7.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Ore);
		tmpValue.add(ResourceType.MilitaryMight);
		
		tmpCardList.add(new Card("Kaserne", Age.AgeI, CardType.MilitaryStructures, 3, null, tmpValue, tmpCosts, false, "MS_Kaserne_3.jpg"));
		tmpCardList.add(new Card("Kaserne", Age.AgeI, CardType.MilitaryStructures, 5, null, tmpValue, tmpCosts, false, "MS_Kaserne_5.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Clay);
		tmpValue.add(ResourceType.MilitaryMight);
		
		tmpCardList.add(new Card("Wachturm", Age.AgeI, CardType.MilitaryStructures, 3, null, tmpValue, tmpCosts, false, "MS_Wachturm_3.jpg"));
		tmpCardList.add(new Card("Wachturm", Age.AgeI, CardType.MilitaryStructures, 4, null, tmpValue, tmpCosts, false, "MS_Wachturm_4.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpValue.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Coin);
		
		tmpCardList.add(new Card("Taverne", Age.AgeI, CardType.CommercialStructures, 4, ValueCalculationType.And, tmpValue, tmpCosts, false, "COM_Taverne_4.jpg"));
		tmpCardList.add(new Card("Taverne", Age.AgeI, CardType.CommercialStructures, 5, ValueCalculationType.And, tmpValue, tmpCosts, false, "COM_Taverne_5.jpg"));
		tmpCardList.add(new Card("Taverne", Age.AgeI, CardType.CommercialStructures, 7, ValueCalculationType.And, tmpValue, tmpCosts, false, "COM_Taverne_7.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Stone);
		tmpValue.add(ResourceType.Stone);
		
		tmpCardList.add(new Card("Bildhauerei", Age.AgeII, CardType.RawMaterials, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "RM_Bildhauerei_3.jpg"));
		tmpCardList.add(new Card("Bildhauerei", Age.AgeII, CardType.RawMaterials, 4, ValueCalculationType.And, tmpValue, tmpCosts, false, "RM_Bildhauerei_4.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Ore);
		tmpValue.add(ResourceType.Ore);
		
		tmpCardList.add(new Card("Giesserei", Age.AgeII, CardType.RawMaterials, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "RM_Giesserei_3.jpg"));
		tmpCardList.add(new Card("Giesserei", Age.AgeII, CardType.RawMaterials, 4, ValueCalculationType.And, tmpValue, tmpCosts, false, "RM_Giesserei_4.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Wood);
		tmpValue.add(ResourceType.Wood);
		
		tmpCardList.add(new Card("Saegewerk", Age.AgeII, CardType.RawMaterials, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "RM_Saegewerk_3.jpg"));
		tmpCardList.add(new Card("Saegewerk", Age.AgeII, CardType.RawMaterials, 4, ValueCalculationType.And, tmpValue, tmpCosts, false, "RM_Saegewerk_4.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Clay);
		tmpValue.add(ResourceType.Clay);
		
		tmpCardList.add(new Card("Ziegelbrennerei", Age.AgeII, CardType.RawMaterials, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "RM_Ziegelbrennerei_3.jpg"));
		tmpCardList.add(new Card("Ziegelbrennerei", Age.AgeII, CardType.RawMaterials, 4, ValueCalculationType.And, tmpValue, tmpCosts, false, "RM_Ziegelbrennerei_4.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Stone);
		tmpCosts.add(ResourceType.Stone);
		tmpCosts.add(ResourceType.Stone);
		tmpValue.add(ResourceType.VictoryPoint);
		tmpValue.add(ResourceType.VictoryPoint);
		tmpValue.add(ResourceType.VictoryPoint);
		tmpValue.add(ResourceType.VictoryPoint);
		tmpValue.add(ResourceType.VictoryPoint);
		
		tmpCardList.add(new Card("Aquaedukt", Age.AgeII, CardType.CivilianStructures, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Aquaedukt_3.jpg"));
		tmpCardList.add(new Card("Aquaedukt", Age.AgeII, CardType.CivilianStructures, 7, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Aquaedukt_7.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Clay);
		tmpCosts.add(ResourceType.Clay);
		tmpCosts.add(ResourceType.Cloth);
		tmpValue.add(ResourceType.VictoryPoint);
		tmpValue.add(ResourceType.VictoryPoint);
		tmpValue.add(ResourceType.VictoryPoint);
		tmpValue.add(ResourceType.VictoryPoint);
		
		tmpCardList.add(new Card("Gericht", Age.AgeII, CardType.CivilianStructures, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Gericht_3.jpg"));
		tmpCardList.add(new Card("Gericht", Age.AgeII, CardType.CivilianStructures, 5, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Gericht_5.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Ore);
		tmpCosts.add(ResourceType.Ore);
		tmpCosts.add(ResourceType.Wood);
		tmpValue.add(ResourceType.VictoryPoint);
		tmpValue.add(ResourceType.VictoryPoint);
		tmpValue.add(ResourceType.VictoryPoint);
		tmpValue.add(ResourceType.VictoryPoint);
		
		tmpCardList.add(new Card("Statue", Age.AgeII, CardType.CivilianStructures, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Statue_3.jpg"));
		tmpCardList.add(new Card("Statue", Age.AgeII, CardType.CivilianStructures, 7, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Statue_7.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Wood);
		tmpCosts.add(ResourceType.Clay);
		tmpCosts.add(ResourceType.Cloth);
		tmpValue.add(ResourceType.VictoryPoint);
		tmpValue.add(ResourceType.VictoryPoint);
		tmpValue.add(ResourceType.VictoryPoint);
		
		tmpCardList.add(new Card("Statue", Age.AgeII, CardType.CivilianStructures, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Tempel_3.jpg"));
		tmpCardList.add(new Card("Statue", Age.AgeII, CardType.CivilianStructures, 6, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Tempel_6.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Ore);
		tmpCosts.add(ResourceType.Ore);
		tmpCosts.add(ResourceType.Glas);
		tmpValue.add(ResourceType.Compasses);
		
		tmpCardList.add(new Card("Arzneiausgabe", Age.AgeII, CardType.ScientificStructures, 3, null, tmpValue, tmpCosts, false, "SS_Arzneiausgabe_3.jpg"));
		tmpCardList.add(new Card("Arzneiausgabe", Age.AgeII, CardType.ScientificStructures, 4, null, tmpValue, tmpCosts, false, "SS_Arzneiausgabe_4.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Stone);
		tmpCosts.add(ResourceType.Stone);
		tmpCosts.add(ResourceType.Cloth);
		tmpValue.add(ResourceType.StonePanel);
		
		tmpCardList.add(new Card("Bibliothek", Age.AgeII, CardType.ScientificStructures, 3, null, tmpValue, tmpCosts, false, "SS_Bibliothek_3.jpg"));
		tmpCardList.add(new Card("Bibliothek", Age.AgeII, CardType.ScientificStructures, 6, null, tmpValue, tmpCosts, false, "SS_Bibliothek_6.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Clay);
		tmpCosts.add(ResourceType.Clay);
		tmpCosts.add(ResourceType.Papyrus);
		tmpValue.add(ResourceType.GearWheel);
		
		tmpCardList.add(new Card("Laboratorium", Age.AgeII, CardType.ScientificStructures, 3, null, tmpValue, tmpCosts, false, "SS_Laboratorium_3.jpg"));
		tmpCardList.add(new Card("Laboratorium", Age.AgeII, CardType.ScientificStructures, 5, null, tmpValue, tmpCosts, false, "SS_Laboratorium_5.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Wood);
		tmpCosts.add(ResourceType.Papyrus);
		tmpValue.add(ResourceType.StonePanel);
		
		tmpCardList.add(new Card("Schule", Age.AgeII, CardType.ScientificStructures, 3, null, tmpValue, tmpCosts, false, "SS_Schule_3.jpg"));
		tmpCardList.add(new Card("Schule", Age.AgeII, CardType.ScientificStructures, 7, null, tmpValue, tmpCosts, false, "SS_Schule_7.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Stone);
		tmpCosts.add(ResourceType.Stone);
		tmpCosts.add(ResourceType.Stone);
		tmpValue.add(ResourceType.MilitaryMight);
		tmpValue.add(ResourceType.MilitaryMight);
		
		tmpCardList.add(new Card("Mauern", Age.AgeII, CardType.MilitaryStructures, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "MS_Mauern_3.jpg"));
		tmpCardList.add(new Card("Mauern", Age.AgeII, CardType.MilitaryStructures, 7, ValueCalculationType.And, tmpValue, tmpCosts, false, "MS_Mauern_7.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Wood);
		tmpCosts.add(ResourceType.Wood);
		tmpCosts.add(ResourceType.Ore);
		tmpValue.add(ResourceType.MilitaryMight);
		tmpValue.add(ResourceType.MilitaryMight);
		
		tmpCardList.add(new Card("Schiessplatz", Age.AgeII, CardType.MilitaryStructures, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "MS_Schiessplatz_3.jpg"));
		tmpCardList.add(new Card("Schiessplatz", Age.AgeII, CardType.MilitaryStructures, 6, ValueCalculationType.And, tmpValue, tmpCosts, false, "MS_Schiessplatz_6.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Clay);
		tmpCosts.add(ResourceType.Wood);
		tmpCosts.add(ResourceType.Ore);
		tmpValue.add(ResourceType.MilitaryMight);
		tmpValue.add(ResourceType.MilitaryMight);
		
		tmpCardList.add(new Card("Staelle", Age.AgeII, CardType.MilitaryStructures, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "MS_Staelle_3.jpg"));
		tmpCardList.add(new Card("Staelle", Age.AgeII, CardType.MilitaryStructures, 5, ValueCalculationType.And, tmpValue, tmpCosts, false, "MS_Staelle_5.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Ore);
		tmpCosts.add(ResourceType.Ore);
		tmpCosts.add(ResourceType.Wood);
		tmpValue.add(ResourceType.MilitaryMight);
		tmpValue.add(ResourceType.MilitaryMight);
		
		tmpCardList.add(new Card("Trainingsgelaende", Age.AgeII, CardType.MilitaryStructures, 4, ValueCalculationType.And, tmpValue, tmpCosts, false, "MS_Trainingsgelaende_4.jpg"));
		tmpCardList.add(new Card("Trainingsgelaende", Age.AgeII, CardType.MilitaryStructures, 6, ValueCalculationType.And, tmpValue, tmpCosts, false, "MS_Trainingsgelaende_6.jpg"));
		tmpCardList.add(new Card("Trainingsgelaende", Age.AgeII, CardType.MilitaryStructures, 7, ValueCalculationType.And, tmpValue, tmpCosts, false, "MS_Trainingsgelaende_7.jpg"));

		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Clay);
		tmpCosts.add(ResourceType.Clay);
		tmpValue.add(ResourceType.Glas);
		tmpValue.add(ResourceType.Cloth);
		tmpValue.add(ResourceType.Papyrus);
		
		tmpCardList.add(new Card("Forum", Age.AgeII, CardType.CommercialStructures, 3, ValueCalculationType.Or, tmpValue, tmpCosts, false, "COM_Forum_3.jpg"));
		tmpCardList.add(new Card("Forum", Age.AgeII, CardType.CommercialStructures, 6, ValueCalculationType.Or, tmpValue, tmpCosts, false, "COM_Forum_6.jpg"));
		tmpCardList.add(new Card("Forum", Age.AgeII, CardType.CommercialStructures, 7, ValueCalculationType.Or, tmpValue, tmpCosts, false, "COM_Forum_7.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Wood);
		tmpCosts.add(ResourceType.Wood);
		tmpValue.add(ResourceType.Wood);
		tmpValue.add(ResourceType.Stone);
		tmpValue.add(ResourceType.Ore);
		tmpValue.add(ResourceType.Clay);
		
		tmpCardList.add(new Card("Karawanserei", Age.AgeII, CardType.CommercialStructures, 3, ValueCalculationType.Or, tmpValue, tmpCosts, false, "COM_Karawanserei_3.jpg"));
		tmpCardList.add(new Card("Karawanserei", Age.AgeII, CardType.CommercialStructures, 5, ValueCalculationType.Or, tmpValue, tmpCosts, false, "COM_Karawanserei_5.jpg"));
		tmpCardList.add(new Card("Karawanserei", Age.AgeII, CardType.CommercialStructures, 6, ValueCalculationType.Or, tmpValue, tmpCosts, false, "COM_Karawanserei_6.jpg"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCardList.add(new Card("Kontor Ost", Age.AgeI, null, 3, null, tmpValue, tmpCosts, false, "COM_Kontor Ost_3.jpg"));
		tmpCardList.add(new Card("Kontor Ost", Age.AgeI, null, 7, null, tmpValue, tmpCosts, false, "COM_Kontor Ost_7.jpg"));
		tmpCardList.add(new Card("Kontor West", Age.AgeI, null, 3, null, tmpValue, tmpCosts, false, "COM_Kontor West_3.jpg"));
		tmpCardList.add(new Card("Kontor West", Age.AgeI, null, 7, null, tmpValue, tmpCosts, false, "COM_Kontor West_7.jpg"));
		tmpCardList.add(new Card("Markt", Age.AgeI, null, 3, null, tmpValue, tmpCosts, false, "COM_Markt_3.jpg"));
		tmpCardList.add(new Card("Markt", Age.AgeI, null, 6, null, tmpValue, tmpCosts, false, "COM_Markt_6.jpg"));
		
		tmpCardList.add(new Card("Basar", Age.AgeII, null, 4, null, tmpValue, tmpCosts, false, "COM_Basar_4.jpg"));
		tmpCardList.add(new Card("Basar", Age.AgeII, null, 7, null, tmpValue, tmpCosts, false, "COM_Basar_7.jpg"));
		tmpCardList.add(new Card("Weinberg", Age.AgeII, null, 3, null, tmpValue, tmpCosts, false, "COM_Weinberg_3.jpg"));
		tmpCardList.add(new Card("Weinberg", Age.AgeII, null, 6, null, tmpValue, tmpCosts, false, "COM_Weinberg_6.jpg"));
		
		//----------------------------------------------------------
		
		return tmpCardList;
	}
}
