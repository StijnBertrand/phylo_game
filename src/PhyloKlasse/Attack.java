package PhyloKlasse;

import org.w3c.dom.Element;

public class Attack {
	String name;
	int damage;
	
	Attack(Element element){
		this.name = XmlParser.getValue("name", element);
		this.damage = Integer.valueOf(XmlParser.getValue("damage", element));
	}
	
	
	public String getName(){
		return name;
	}
	
	public int getDamage(){
		return damage;
	}
	
	//returns the damage that has been done
	public int execute(Phylomon executer,Phylomon victem){
		victem.addHP(-damage);
		return damage;
	}
	
}
