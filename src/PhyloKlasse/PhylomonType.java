package PhyloKlasse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class PhylomonType {
	private String name;
	private int HP;
	private Attack attack;
	private String picLocatie;
	
	
	PhylomonType(String name,int Hp,String attackName,int attackDamage, String picLocatie){
		this.name = name;
		this.HP = Hp;
		
		if(picLocatie.equals("")){
			this.picLocatie = "no pic locatie";
		}else{
			this.picLocatie = picLocatie;
		}
	}
	
	//de constructor die de DBM gebruikt om van xml phylomon te maken
	PhylomonType(Element element){
		this.name = XmlParser.getValue("Name", element);
		this.HP = Integer.valueOf(XmlParser.getValue("HP", element));
		this.attack = new Attack(XmlParser.getValue("attackName", element),
								 Integer.valueOf(XmlParser.getValue("attackDamage", element)));		
		this.picLocatie = XmlParser.getValue("picLocatie", element);
		
		

	}
	
	public Element toElement(Document doc,Element rootElement){
		Element phylomon = doc.createElement("phylomon");
		//de attributen van de phylomon maken
		Element name = doc.createElement("Name");
		name.appendChild(doc.createTextNode(this.getName()));
		Element hp = doc.createElement("HP");
		hp.appendChild(doc.createTextNode(Integer.toString(this.getMaxHp())));
		Element attackName = doc.createElement("attackName");
		attackName.appendChild(doc.createTextNode(this.attack.getName()));
		Element attackDamage = doc.createElement("attackDamage");
		attackDamage.appendChild(doc.createTextNode(Integer.toString(this.attack.getDamage())));
		Element picLocatie = doc.createElement("picLocatie");
		picLocatie.appendChild(doc.createTextNode(this.getPicLocatie()));
		//attributen toevoegen
		phylomon.appendChild(name);
		phylomon.appendChild(hp);
		phylomon.appendChild(attackName);
		phylomon.appendChild(attackDamage);
		phylomon.appendChild(picLocatie);
		
		
		return phylomon;		
	}
	
	
	
	
	
	
	
	//getters:
	public String getName(){
		return name;
	}
	public int getMaxHp(){
		return HP;
	}
	public Attack getAttack(){
		return attack;
	}

	public String getPicLocatie(){
		return picLocatie;
	}
	//setters:
	public void setName(String name){
		this.name = name;
	}
	public void setHp(int Hp){
		this.HP = Hp;
	}
	public void setAttackName(Attack attack ){
		this.attack = attack;
	}

	public void setPicLocatie(String picLocatie){
		this.picLocatie = picLocatie;
	}
	
}
