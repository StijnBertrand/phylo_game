package PhyloKlasse;

public class Battle {
	private Phylomon home;
	private Phylomon visitor;
	private boolean aanHome;
	private String message = "@string/battleStart";
	
	public Battle(Phylomon home,Phylomon visitor){
		this.home=home;
		this.visitor=visitor;	
	}
	
	public void attack(){
		if (aanHome){
			home.doAttack(visitor);
			message = home.getPicLocatie() + " does " + home.getAttack().getName() + "and it did " + Integer.toString(home.getAttack().getDamage());
		}else{
			visitor.doAttack(home);
			message = visitor.getPicLocatie() + " does " + visitor.getAttack().getName() + "and it did " + Integer.toString(visitor.getAttack().getDamage());
		}
		aanHome = !aanHome;
	}
	
	
	public String getMessage(){
		return message;
	}
	
	public Phylomon getHome(){
		return home;
	}
	public Phylomon getVisitor(){
		return visitor;
	}
	
	
	

}
