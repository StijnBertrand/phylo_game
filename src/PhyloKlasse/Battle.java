package PhyloKlasse;

public class Battle {
	private Phylomon[] homeTeam;
	private Phylomon[] visitorTeam;
	private int home;
	private int visitor;
	private Attack[] attacks;
	
	private boolean aanHome;
	
	public Battle(Phylomon[] homeTeam,int homefirst,Phylomon[] visitorTeam,int visitorfirst,Attack[] attacks){
		this.homeTeam = homeTeam;
		this.visitorTeam = visitorTeam;
		this.home = homefirst;
		this.visitor = visitorfirst;
		this.attacks = attacks;
		aanHome = true;
	}
	
	public int attack(){
		Attack attack;
		int damage;
		if (aanHome){
			attack = attacks[homeTeam[home].getAttackId()];
			damage = attack.execute(homeTeam[home],visitorTeam[visitor]);
		}else{
			attack = attacks[visitorTeam[visitor].getAttackId()];
			damage = attack.execute(visitorTeam[visitor],homeTeam[home]);
		}
		aanHome = !aanHome;
		return damage;
	}
	
	
	public Phylomon getHome(){
		return homeTeam[home];
	}
	public Phylomon getVisitor(){
		return visitorTeam[visitor];
	}
	
	// 1 Everything was OK
	// 2 the chosen one was already dead
	public int change(int next){
		Phylomon[] team;
		if(aanHome){
			team = homeTeam;
		}else{
			team = visitorTeam;
		}
		aanHome = !aanHome;
		if (team[next].dead()){
			return 2;
		}else{
			this.home = next;
			return 1;
		}
		
	}
	
	public boolean dead(){
		if(aanHome){
			return homeTeam[home].dead();
		}else{
			return visitorTeam[visitor].dead();
		}
	}
}
