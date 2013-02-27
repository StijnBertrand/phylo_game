package PhyloKlasse;

public class Phylomon {
	PhylomonType type;
	int hp;
	int level;
	int Xp;
	int XpForNext;

	
	public Phylomon(PhylomonType type,int level){
		this.type = type;
		this.hp = type.getMaxHp();
		if(level>5 && level<100){
			this.level = level;	
		}else{
			this.level = 5;
		}
		Xp = Experience.getXpForLevel(type.getXpClass(), level);
		XpForNext =  Experience.getXpForLevel(type.getXpClass(), level + 1);
	}

	
	
	public void addHP(int hp){
		int newhp = this.hp + hp;
		if (newhp<0){
			this.hp=0;
		}
		else if(newhp > type.getMaxHp()){
			this.hp = type.getMaxHp();
		}
		else {
			this.hp = newhp;
		}
	}
	
	public void heal(){
		this.hp = type.getMaxHp();
	}
	
	public boolean dead(){
		return this.hp == 0;
	}
	
	//returns true if the Phylomon has to advance a level
	public boolean addXp(int amount){
		if(level != 100){
			Xp += amount;
			return Xp > XpForNext;
		}else{
			return false;
		}
		
	}
 	public void incLevel(){
		this.level += 1;
	}
	
	//geters
	public int getHP(){
		return hp;
	}
	
	public int getLevel(){
		return level;
	}

	//getters from the type
	public String getName(){
		return type.getName();
	}
	
	public int getMaxHp(){
		return type.getMaxHp();
	}

	public String getPicLocatie(){
		return type.getPicLocatie();
	}

	public int getAttackId() {	
		return type.getAttackId();
	}
	
	public int getBaseXp(){
		return type.getBaseXp();
	}
}
