package PhyloKlasse;

import org.w3c.dom.Element;

public class Attack {
	String name;
	int damage;
	
	Attack(String name,int damage){
		this.name = name;
		this.damage = damage;
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
