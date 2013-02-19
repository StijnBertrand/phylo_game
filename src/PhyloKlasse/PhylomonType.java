package PhyloKlasse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class PhylomonType {
	private String name;
	private int HP;
	private int attackId;
	
	private String picLocatie;
	
	//de constructor die de DBM gebruikt om van xml phylomon te maken
	PhylomonType(Element element){
		this.name = XmlParser.getValue("Name", element);
		this.HP = Integer.valueOf(XmlParser.getValue("HP", element));
		this.attackId = Integer.valueOf(XmlParser.getValue("attackId", element));
		this.picLocatie = XmlParser.getValue("picLocatie", element);
		

	}
	
	//getters:
	public String getName(){
		return name;
	}
	public int getMaxHp(){
		return HP;
	}
	public int getAttackId() {
		return attackId;
	}
	public String getPicLocatie(){
		return picLocatie;
	}
	
}
