package PhyloKlasse;

public class Phylomon {
	PhylomonType type;
	int hp;
	Attack attack;
	
	public Phylomon(PhylomonType type){
		this.type = type;
		this.hp = type.getMaxHp();
		this.attack= type.getAttack();
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
	
	public void doAttack(Phylomon victem){
		victem.addHP(-this.getAttack().getDamage());
	}
	
	public boolean dead(){
		return this.hp == 0;
	}
	
	//geters
	public int getHP(){
		return hp;
	}
	
	public Attack  getAttack(){
		return attack;
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
}
