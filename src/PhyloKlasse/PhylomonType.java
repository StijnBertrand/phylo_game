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

	private Attack[] attacks;

	
	private String picLocatie;
	
	
	
	PhylomonType(String name,int HP,int experienceGroup,int baseXp,Attack[] attacks,String picLocatie){
		this.name = name;
		this.HP = HP;
		this.experienceGroup = experienceGroup;
		this.baseXp = baseXp;
		this.attacks = attacks;
		this.picLocatie = picLocatie;
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
	//voorlopig zo
	public Attack[] getAttacks(){
		return attacks;
	}
	
}
