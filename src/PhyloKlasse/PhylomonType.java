package PhyloKlasse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class PhylomonType {
	private String name;
	private int HP;	
	// this variable is important to calculate the experience needed for the next level
	private int experienceGroup;
	// this variable is used to calculate how much experience this Phylomon should yield when defeated
	private int baseXp;

	private Attack attack;

	
	private String picLocatie;
	
	//this constructor makes from a xml element a PhylomonType object
	PhylomonType(Element element){
		this.name = XmlParser.getValue("Name", element);
		this.HP = Integer.valueOf(XmlParser.getValue("HP", element));
		this.experienceGroup = Integer.valueOf(XmlParser.getValue("experienceGroup", element));
		this.baseXp = Integer.valueOf(XmlParser.getValue("baseXp", element));

		
		attack = XmlParser.getAttacks(element);
		
		
		
		
		this.picLocatie = XmlParser.getValue("picLocatie", element);
	}
	
	//getters:
	public String getName(){
		return name;
	}
	public int getMaxHp(){
		return HP;
	}
	public int getXpClass(){
		return 	experienceGroup;
	}

	public int getBaseXp(){
		return baseXp;
	}
	public String getPicLocatie(){
		return picLocatie;
	}
	public Attack getAttack(){
		return attack;
	}
	
}
