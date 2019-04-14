package ch.fhnw.sevenwonders.helper;

import java.util.ArrayList;

import ch.fhnw.sevenwonders.enums.Age;
import ch.fhnw.sevenwonders.enums.CardType;
import ch.fhnw.sevenwonders.enums.ResourceType;
import ch.fhnw.sevenwonders.enums.ValueCalculationType;
import ch.fhnw.sevenwonders.interfaces.ICard;
import ch.fhnw.sevenwonders.model.Card;

/**
 * 
 * @author Matteo
 *
 */
public class InitHelper {

	public static ArrayList<ICard> initAllCards(){
		ArrayList<ICard> tmpCardList = new ArrayList<ICard>();
		
		ArrayList<ResourceType> tmpCosts = new ArrayList<ResourceType>();
		ArrayList<ResourceType> tmpValue = new ArrayList<ResourceType>();
		
		tmpValue.add(ResourceType.Wood);
		
		tmpCardList.add(new Card("Holzplatz", Age.AgeI, CardType.RawMaterials, 3, null, tmpValue, tmpCosts, false, "RM_Holzplatz_3"));
		tmpCardList.add(new Card("Holzplatz", Age.AgeI, CardType.RawMaterials, 4, null, tmpValue, tmpCosts, false, "RM_Holzplatz_4"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpValue.add(ResourceType.Ore);
		
		tmpCardList.add(new Card("Erzbergwerk", Age.AgeI, CardType.RawMaterials, 3, null, tmpValue, tmpCosts, false, "RM_Erzbergwerk_3"));
		tmpCardList.add(new Card("Erzbergwerk", Age.AgeI, CardType.RawMaterials, 4, null, tmpValue, tmpCosts, false, "RM_Erzbergwerk_4"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpValue.add(ResourceType.Stone);
		
		tmpCardList.add(new Card("Steinbruch", Age.AgeI, CardType.RawMaterials, 3, null, tmpValue, tmpCosts, false, "RM_Steinbruch_3"));
		tmpCardList.add(new Card("Steinbruch", Age.AgeI, CardType.RawMaterials, 5, null, tmpValue, tmpCosts, false, "RM_Steinbruch_5"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpValue.add(ResourceType.Clay);
		
		tmpCardList.add(new Card("Ziegelei", Age.AgeI, CardType.RawMaterials, 3, null, tmpValue, tmpCosts, false, "RM_Ziegelei_3"));
		tmpCardList.add(new Card("Ziegelei", Age.AgeI, CardType.RawMaterials, 5, null, tmpValue, tmpCosts, false, "RM_Ziegelei_5"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Stone);
		tmpValue.add(ResourceType.Clay);
		
		tmpCardList.add(new Card("Ausgrabungsstätte", Age.AgeI, CardType.RawMaterials, 4, ValueCalculationType.Or, tmpValue, tmpCosts, false, "RM_Ausgrabungsstaette_4"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Wood);
		tmpValue.add(ResourceType.Clay);
		
		tmpCardList.add(new Card("Baumschule", Age.AgeI, CardType.RawMaterials, 6, ValueCalculationType.Or, tmpValue, tmpCosts, false, "RM_Baumschule_6"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------

		tmpCosts.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Wood);
		tmpValue.add(ResourceType.Stone);
		
		tmpCardList.add(new Card("Forstwirtschaft", Age.AgeI, CardType.RawMaterials, 3, ValueCalculationType.Or, tmpValue, tmpCosts, false, "RM_Forstwirtschaft_3"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------

		tmpCosts.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Ore);
		tmpValue.add(ResourceType.Stone);
				
		tmpCardList.add(new Card("Mine", Age.AgeI, CardType.RawMaterials, 6, ValueCalculationType.Or, tmpValue, tmpCosts, false, "RM_Mine_6"));
				
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Ore);
		tmpValue.add(ResourceType.Clay);
				
		tmpCardList.add(new Card("Tongrube", Age.AgeI, CardType.RawMaterials, 3, ValueCalculationType.Or, tmpValue, tmpCosts, false, "RM_Tongrube_3"));
				
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Ore);
		tmpValue.add(ResourceType.Wood);
				
		tmpCardList.add(new Card("Waldhöhle", Age.AgeI, CardType.RawMaterials, 5, ValueCalculationType.Or, tmpValue, tmpCosts, false, "RM_Waldhoehle_5"));
				
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpValue.add(ResourceType.Glas);
				
		tmpCardList.add(new Card("Glashütte", Age.AgeI, CardType.ManufacturedGoods, 3, null, tmpValue, tmpCosts, false, "MG_Glashuette_3"));
		tmpCardList.add(new Card("Glashütte", Age.AgeI, CardType.ManufacturedGoods, 6, null, tmpValue, tmpCosts, false, "MG_Glashuette_6"));
		tmpCardList.add(new Card("Glashütte", Age.AgeII, CardType.ManufacturedGoods, 3, null, tmpValue, tmpCosts, false, "MG_Glashuette_3"));
		tmpCardList.add(new Card("Glashütte", Age.AgeII, CardType.ManufacturedGoods, 5, null, tmpValue, tmpCosts, false, "MG_Glashuette_5"));
				
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpValue.add(ResourceType.Papyrus);
		
		tmpCardList.add(new Card("Presse", Age.AgeI, CardType.ManufacturedGoods, 3, null, tmpValue, tmpCosts, false, "MG_Presse_3"));
		tmpCardList.add(new Card("Presse", Age.AgeI, CardType.ManufacturedGoods, 6, null, tmpValue, tmpCosts, false, "MG_Presse_6"));
		tmpCardList.add(new Card("Presse", Age.AgeII, CardType.ManufacturedGoods, 3, null, tmpValue, tmpCosts, false, "MG_Presse_3"));
		tmpCardList.add(new Card("Presse", Age.AgeII, CardType.ManufacturedGoods, 5, null, tmpValue, tmpCosts, false, "MG_Presse_5"));
				
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpValue.add(ResourceType.Cloth);
		
		tmpCardList.add(new Card("Webstuhl", Age.AgeI, CardType.ManufacturedGoods, 3, null, tmpValue, tmpCosts, false, "MG_Webstuhl_3"));
		tmpCardList.add(new Card("Webstuhl", Age.AgeI, CardType.ManufacturedGoods, 6, null, tmpValue, tmpCosts, false, "MG_Webstuhl_6"));
		tmpCardList.add(new Card("Webstuhl", Age.AgeII, CardType.ManufacturedGoods, 3, null, tmpValue, tmpCosts, false, "MG_Webstuhl_3"));
		tmpCardList.add(new Card("Webstuhl", Age.AgeII, CardType.ManufacturedGoods, 5, null, tmpValue, tmpCosts, false, "MG_Webstuhl_5"));
				
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpValue.add(ResourceType.VictoryPoint);
		tmpValue.add(ResourceType.VictoryPoint);
		
		tmpCardList.add(new Card("Altar", Age.AgeI, CardType.CivilianStructures, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Altar_3"));
		tmpCardList.add(new Card("Altar", Age.AgeI, CardType.CivilianStructures, 5, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Altar_5"));
		tmpCardList.add(new Card("Theater", Age.AgeI, CardType.CivilianStructures, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Theater_3"));
		tmpCardList.add(new Card("Theater", Age.AgeI, CardType.CivilianStructures, 6, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Theater_6"));
						
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpValue.add(ResourceType.VictoryPoint);
		tmpValue.add(ResourceType.VictoryPoint);
		tmpValue.add(ResourceType.VictoryPoint);
		
		tmpCardList.add(new Card("Pfandhaus", Age.AgeI, CardType.CivilianStructures, 4, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Pfandhaus_4"));
		tmpCardList.add(new Card("Pfandhaus", Age.AgeI, CardType.CivilianStructures, 7, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Pfandhaus_7"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Stone);
		tmpValue.add(ResourceType.VictoryPoint);
		tmpValue.add(ResourceType.VictoryPoint);
		tmpValue.add(ResourceType.VictoryPoint);
		
		tmpCardList.add(new Card("Bäder", Age.AgeI, CardType.CivilianStructures, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Baeder_3"));
		tmpCardList.add(new Card("Bäder", Age.AgeI, CardType.CivilianStructures, 7, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Baeder_7"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Cloth);
		tmpValue.add(ResourceType.Compasses);
		
		tmpCardList.add(new Card("Apotheke", Age.AgeI, CardType.ScientificStructures, 3, null, tmpValue, tmpCosts, false, "SS_Apotheke_3"));
		tmpCardList.add(new Card("Apotheke", Age.AgeI, CardType.ScientificStructures, 5, null, tmpValue, tmpCosts, false, "SS_Apotheke_5"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Papyrus);
		tmpValue.add(ResourceType.StonePanel);
		
		tmpCardList.add(new Card("Skriptorium", Age.AgeI, CardType.ScientificStructures, 3, null, tmpValue, tmpCosts, false, "SS_Skriptorium_3"));
		tmpCardList.add(new Card("Skriptorium", Age.AgeI, CardType.ScientificStructures, 4, null, tmpValue, tmpCosts, false, "SS_Skriptorium_4"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Glas);
		tmpValue.add(ResourceType.GearWheel);
		
		tmpCardList.add(new Card("Werkstatt", Age.AgeI, CardType.ScientificStructures, 3, null, tmpValue, tmpCosts, false, "SS_Werkstatt_3"));
		tmpCardList.add(new Card("Werkstatt", Age.AgeI, CardType.ScientificStructures, 7, null, tmpValue, tmpCosts, false, "SS_Werkstatt_7"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Wood);
		tmpValue.add(ResourceType.MilitaryMight);
		
		tmpCardList.add(new Card("Befestigungsanlage", Age.AgeI, CardType.MilitaryStructures, 3, null, tmpValue, tmpCosts, false, "MS_Befestigungsanlage_3"));
		tmpCardList.add(new Card("Befestigungsanlage", Age.AgeI, CardType.MilitaryStructures, 7, null, tmpValue, tmpCosts, false, "MS_Befestigungsanlage_7"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Ore);
		tmpValue.add(ResourceType.MilitaryMight);
		
		tmpCardList.add(new Card("Kaserne", Age.AgeI, CardType.MilitaryStructures, 3, null, tmpValue, tmpCosts, false, "MS_Kaserne_3"));
		tmpCardList.add(new Card("Kaserne", Age.AgeI, CardType.MilitaryStructures, 5, null, tmpValue, tmpCosts, false, "MS_Kaserne_5"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Clay);
		tmpValue.add(ResourceType.MilitaryMight);
		
		tmpCardList.add(new Card("Wachturm", Age.AgeI, CardType.MilitaryStructures, 3, null, tmpValue, tmpCosts, false, "MS_Wachturm_3"));
		tmpCardList.add(new Card("Wachturm", Age.AgeI, CardType.MilitaryStructures, 4, null, tmpValue, tmpCosts, false, "MS_Wachturm_4"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpValue.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Coin);
		
		tmpCardList.add(new Card("Taverne", Age.AgeI, CardType.CommercialStructures, 4, ValueCalculationType.And, tmpValue, tmpCosts, false, "COM_Taverne_4"));
		tmpCardList.add(new Card("Taverne", Age.AgeI, CardType.CommercialStructures, 5, ValueCalculationType.And, tmpValue, tmpCosts, false, "COM_Taverne_5"));
		tmpCardList.add(new Card("Taverne", Age.AgeI, CardType.CommercialStructures, 7, ValueCalculationType.And, tmpValue, tmpCosts, false, "COM_Taverne_7"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Stone);
		tmpValue.add(ResourceType.Stone);
		
		tmpCardList.add(new Card("Bildhauerei", Age.AgeII, CardType.RawMaterials, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "RM_Bildhauerei_3"));
		tmpCardList.add(new Card("Bildhauerei", Age.AgeII, CardType.RawMaterials, 4, ValueCalculationType.And, tmpValue, tmpCosts, false, "RM_Bildhauerei_4"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Ore);
		tmpValue.add(ResourceType.Ore);
		
		tmpCardList.add(new Card("Giesserei", Age.AgeII, CardType.RawMaterials, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "RM_Giesserei_3"));
		tmpCardList.add(new Card("Giesserei", Age.AgeII, CardType.RawMaterials, 4, ValueCalculationType.And, tmpValue, tmpCosts, false, "RM_Giesserei_4"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Wood);
		tmpValue.add(ResourceType.Wood);
		
		tmpCardList.add(new Card("Sägewerk", Age.AgeII, CardType.RawMaterials, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "RM_Saegewerk_3"));
		tmpCardList.add(new Card("Sägewerk", Age.AgeII, CardType.RawMaterials, 4, ValueCalculationType.And, tmpValue, tmpCosts, false, "RM_Saegewerk_4"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Coin);
		tmpValue.add(ResourceType.Clay);
		tmpValue.add(ResourceType.Clay);
		
		tmpCardList.add(new Card("Ziegelbrennerei", Age.AgeII, CardType.RawMaterials, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "RM_Ziegelbrennerei_3"));
		tmpCardList.add(new Card("Ziegelbrennerei", Age.AgeII, CardType.RawMaterials, 4, ValueCalculationType.And, tmpValue, tmpCosts, false, "RM_Ziegelbrennerei_4"));
		
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
		
		tmpCardList.add(new Card("Aquädukt", Age.AgeII, CardType.CivilianStructures, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Aquaedukt_3"));
		tmpCardList.add(new Card("Aquädukt", Age.AgeII, CardType.CivilianStructures, 7, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Aquaedukt_7"));
		
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
		
		tmpCardList.add(new Card("Gericht", Age.AgeII, CardType.CivilianStructures, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Gericht_3"));
		tmpCardList.add(new Card("Gericht", Age.AgeII, CardType.CivilianStructures, 5, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Gericht_5"));
		
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
		
		tmpCardList.add(new Card("Statue", Age.AgeII, CardType.CivilianStructures, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Statue_3"));
		tmpCardList.add(new Card("Statue", Age.AgeII, CardType.CivilianStructures, 7, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Statue_7"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Wood);
		tmpCosts.add(ResourceType.Clay);
		tmpCosts.add(ResourceType.Cloth);
		tmpValue.add(ResourceType.VictoryPoint);
		tmpValue.add(ResourceType.VictoryPoint);
		tmpValue.add(ResourceType.VictoryPoint);
		
		tmpCardList.add(new Card("Statue", Age.AgeII, CardType.CivilianStructures, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Tempel_3"));
		tmpCardList.add(new Card("Statue", Age.AgeII, CardType.CivilianStructures, 6, ValueCalculationType.And, tmpValue, tmpCosts, false, "CS_Tempel_6"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Ore);
		tmpCosts.add(ResourceType.Ore);
		tmpCosts.add(ResourceType.Glas);
		tmpValue.add(ResourceType.Compasses);
		
		tmpCardList.add(new Card("Arzneiausgabe", Age.AgeII, CardType.ScientificStructures, 3, null, tmpValue, tmpCosts, false, "SS_Arzneiausgabe_3"));
		tmpCardList.add(new Card("Arzneiausgabe", Age.AgeII, CardType.ScientificStructures, 4, null, tmpValue, tmpCosts, false, "SS_Arzneiausgabe_4"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Stone);
		tmpCosts.add(ResourceType.Stone);
		tmpCosts.add(ResourceType.Cloth);
		tmpValue.add(ResourceType.StonePanel);
		
		tmpCardList.add(new Card("Bibliothek", Age.AgeII, CardType.ScientificStructures, 3, null, tmpValue, tmpCosts, false, "SS_Bibliothek_3"));
		tmpCardList.add(new Card("Bibliothek", Age.AgeII, CardType.ScientificStructures, 6, null, tmpValue, tmpCosts, false, "SS_Bibliothek_6"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Clay);
		tmpCosts.add(ResourceType.Clay);
		tmpCosts.add(ResourceType.Papyrus);
		tmpValue.add(ResourceType.GearWheel);
		
		tmpCardList.add(new Card("Laboratorium", Age.AgeII, CardType.ScientificStructures, 3, null, tmpValue, tmpCosts, false, "SS_Laboratorium_3"));
		tmpCardList.add(new Card("Laboratorium", Age.AgeII, CardType.ScientificStructures, 5, null, tmpValue, tmpCosts, false, "SS_Laboratorium_6"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Wood);
		tmpCosts.add(ResourceType.Papyrus);
		tmpValue.add(ResourceType.StonePanel);
		
		tmpCardList.add(new Card("Schule", Age.AgeII, CardType.ScientificStructures, 3, null, tmpValue, tmpCosts, false, "SS_Schule_3"));
		tmpCardList.add(new Card("Schule", Age.AgeII, CardType.ScientificStructures, 7, null, tmpValue, tmpCosts, false, "SS_Schule_7"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Stone);
		tmpCosts.add(ResourceType.Stone);
		tmpCosts.add(ResourceType.Stone);
		tmpValue.add(ResourceType.MilitaryMight);
		tmpValue.add(ResourceType.MilitaryMight);
		
		tmpCardList.add(new Card("Mauern", Age.AgeII, CardType.MilitaryStructures, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "MS_Mauern_3"));
		tmpCardList.add(new Card("Mauern", Age.AgeII, CardType.MilitaryStructures, 7, ValueCalculationType.And, tmpValue, tmpCosts, false, "MS_Mauern_7"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Wood);
		tmpCosts.add(ResourceType.Wood);
		tmpCosts.add(ResourceType.Ore);
		tmpValue.add(ResourceType.MilitaryMight);
		tmpValue.add(ResourceType.MilitaryMight);
		
		tmpCardList.add(new Card("Schiessplatz", Age.AgeII, CardType.MilitaryStructures, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "MS_Mauern_3"));
		tmpCardList.add(new Card("Schiessplatz", Age.AgeII, CardType.MilitaryStructures, 6, ValueCalculationType.And, tmpValue, tmpCosts, false, "MS_Mauern_6"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Clay);
		tmpCosts.add(ResourceType.Wood);
		tmpCosts.add(ResourceType.Ore);
		tmpValue.add(ResourceType.MilitaryMight);
		tmpValue.add(ResourceType.MilitaryMight);
		
		tmpCardList.add(new Card("Ställe", Age.AgeII, CardType.MilitaryStructures, 3, ValueCalculationType.And, tmpValue, tmpCosts, false, "MS_Staelle_3"));
		tmpCardList.add(new Card("Ställe", Age.AgeII, CardType.MilitaryStructures, 5, ValueCalculationType.And, tmpValue, tmpCosts, false, "MS_Staelle_5"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Ore);
		tmpCosts.add(ResourceType.Ore);
		tmpCosts.add(ResourceType.Wood);
		tmpValue.add(ResourceType.MilitaryMight);
		tmpValue.add(ResourceType.MilitaryMight);
		
		tmpCardList.add(new Card("Trainingsgelände", Age.AgeII, CardType.MilitaryStructures, 4, ValueCalculationType.And, tmpValue, tmpCosts, false, "MS_Trainingsgelaende_4"));
		tmpCardList.add(new Card("Trainingsgelände", Age.AgeII, CardType.MilitaryStructures, 6, ValueCalculationType.And, tmpValue, tmpCosts, false, "MS_Trainingsgelaende_6"));
		tmpCardList.add(new Card("Trainingsgelände", Age.AgeII, CardType.MilitaryStructures, 7, ValueCalculationType.And, tmpValue, tmpCosts, false, "MS_Trainingsgelaende_7"));

		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Clay);
		tmpCosts.add(ResourceType.Clay);
		tmpValue.add(ResourceType.Glas);
		tmpValue.add(ResourceType.Cloth);
		tmpValue.add(ResourceType.Papyrus);
		
		tmpCardList.add(new Card("Forum", Age.AgeII, CardType.CommercialStructures, 3, ValueCalculationType.Or, tmpValue, tmpCosts, false, "COM_Forum_3"));
		tmpCardList.add(new Card("Forum", Age.AgeII, CardType.CommercialStructures, 6, ValueCalculationType.Or, tmpValue, tmpCosts, false, "COM_Forum_6"));
		tmpCardList.add(new Card("Forum", Age.AgeII, CardType.CommercialStructures, 7, ValueCalculationType.Or, tmpValue, tmpCosts, false, "COM_Forum_7"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		tmpCosts.add(ResourceType.Wood);
		tmpCosts.add(ResourceType.Wood);
		tmpValue.add(ResourceType.Wood);
		tmpValue.add(ResourceType.Stone);
		tmpValue.add(ResourceType.Ore);
		tmpValue.add(ResourceType.Clay);
		
		tmpCardList.add(new Card("Karawanserei", Age.AgeII, CardType.CommercialStructures, 3, ValueCalculationType.Or, tmpValue, tmpCosts, false, "COM_Karawanserei_3"));
		tmpCardList.add(new Card("Karawanserei", Age.AgeII, CardType.CommercialStructures, 5, ValueCalculationType.Or, tmpValue, tmpCosts, false, "COM_Karawanserei_5"));
		tmpCardList.add(new Card("Karawanserei", Age.AgeII, CardType.CommercialStructures, 6, ValueCalculationType.Or, tmpValue, tmpCosts, false, "COM_Karawanserei_6"));
		
		tmpCosts.clear();
		tmpValue.clear();
		
		//----------------------------------------------------------
		
		
		return tmpCardList;
	}
}
