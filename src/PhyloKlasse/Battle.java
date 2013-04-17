package PhyloKlasse;

import java.util.LinkedList;
import java.util.Queue;

public class Battle {
	// 0: waiting for input from the home trainer
	// 1: waiting for input from the visitor trainer
	// 2: home trainer has to change Phylomon
	// 3: visitor trainer has to change Phylomon
	// 4: game is over
	
	// 5: attack message
	// infoQueue : [PhylomonName ,AttackNam, damage ,...]
	// 6: the home trainer has just chosen a new Phylomon
	// infoQueue : [PhylomonName ,...]
	// 7: the visitor trainer has just chosen a new Phylomon
	// infoQueue : [PhylomonName ,...]
	// 8: a Phylomon has just fainted
	// infoQueue : [PhylomonName ,...]

	private Queue<Integer> messageQueue = new LinkedList<Integer>();
	private Queue<String> infoQueue = new LinkedList<String>();
	
	//variables for the battle
	private Phylomon home;
	private Phylomon visitor;

	
	private boolean aanHome;
	
	public Battle(Phylomon home,Phylomon visitor){
		this.home = home;
		this.visitor = visitor;

		aanHome = true;
		messageQueue.add(0);
	}
	
	public void attack(int i){
		Attack attack;
		messageQueue.add(5);
		if (aanHome){
			attack = home.getAttack(i);
			//Attack and put the attackers name, the attack name and the damage done on the InfoQueue
			infoQueue.add(home.getName());
			infoQueue.add(attack.getName());
			infoQueue.add(Integer.toString(attack.execute(home,visitor)));
			
			//see if the Phylomon has fainted
			if(visitor.dead()){
				//Message to tell that the Phylomon has fainted
				messageQueue.add(8);
				infoQueue.add(visitor.getName());
				//divide the experience gained
				
				//divideXp(homeTeam,home,visitor);	
			}
			messageQueue.add(1);
		}else{
			attack = visitor.getAttack(i);
			//Attack and put the attackers name, the attack name and the damage done on the InfoQueue
			infoQueue.add(visitor.getName());
			infoQueue.add(attack.getName());
			infoQueue.add(Integer.toString(attack.execute(visitor,home)));
			
			//see if the Phylomon has fainted
			if(home.dead()){
				//Message to tell that the Phylomon has fainted
				messageQueue.add(8);
				infoQueue.add(home.getName());
				//divide the experience gained
				//divideXp(visitorTeam,visitor,home);
			}
			messageQueue.add(0);
		}	
		aanHome = !aanHome;
	}
	
	
	public void changeHome(Phylomon next){
		if(home != null)if(!home.dead())aanHome = !aanHome;
		
		if(next == null){
			infoQueue.add(home.getName());
			messageQueue.add(10);
			messageQueue.add(2);
			
		}else if(aanHome){
			messageQueue.add(6);
			infoQueue.add(next.getName());
			messageQueue.add(0);
			
		}else{

			messageQueue.add(6);
			infoQueue.add(next.getName());
			messageQueue.add(1);
		}
		
		
		this.home = next;	
	
		
		
	}
	
	public void change(Phylomon next){
		if(aanHome){
			// when a Phylomon has fainted is does not take a turn to change
			if(!home.dead())aanHome = !aanHome;
			this.home = next;
			//the message
			infoQueue.add(home.getName());
			messageQueue.add(6);	
		}else{
			// when a Phylomon has fainted is does not take a turn to change
			if(!visitor.dead())aanHome = !aanHome;
			this.visitor = next;
			//the message
			infoQueue.add(visitor.getName());
			messageQueue.add(7);
		}
		
		if(aanHome){
			messageQueue.add(0);
		}else{
			messageQueue.add(1);
		}
	}
	

	public int next(){
		return messageQueue.poll();
	}
	public String getInfo(){
		return infoQueue.poll();
	}
	
	
	
 	public Phylomon getHome(){
		return home;
	}
	public Phylomon getVisitor(){
		return visitor;
	}
		

	private void divideXp(Phylomon[] team,Phylomon victor,Phylomon fainter){
		int Xp = Experience.getXpToGive(victor, fainter, true);
		victor.addXp(Xp);
		messageQueue.add(9);
		infoQueue.add(victor.getName());
		infoQueue.add(Integer.toString(Xp));
		
	}

}
