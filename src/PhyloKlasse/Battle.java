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
		messageQueue.add(0);
	}
	
	public void attack(){
		Attack attack;
		messageQueue.add(5);
		if (aanHome){
			attack = attacks[homeTeam[home].getAttackId()];
			//Attack and put the attackers name, the attack name and the damage done on the InfoQueue
			infoQueue.add(homeTeam[home].getName());
			infoQueue.add(attack.getName());
			infoQueue.add(Integer.toString(attack.execute(homeTeam[home],visitorTeam[visitor])));
			
			//see if the Phylomon has fainted
			if(visitorTeam[visitor].dead()){
				//Message to tell that the Phylomon has fainted
				messageQueue.add(8);
				infoQueue.add(visitorTeam[visitor].getName());
				//divide the experience gained
				divideXp(homeTeam,homeTeam[home],visitorTeam[visitor]);
				if(battleFinished(visitorTeam)){
					messageQueue.add(4);
				}else{
					messageQueue.add(3);
				}
			}else{
				messageQueue.add(1);
			}
		}else{
			attack = attacks[visitorTeam[visitor].getAttackId()];
			//Attack and put the attackers name, the attack name and the damage done on the InfoQueue
			infoQueue.add(visitorTeam[visitor].getName());
			infoQueue.add(attack.getName());
			infoQueue.add(Integer.toString(attack.execute(visitorTeam[visitor],homeTeam[home])));
			
			//see if the Phylomon has fainted
			if(homeTeam[home].dead()){
				//Message to tell that the Phylomon has fainted
				messageQueue.add(8);
				infoQueue.add(homeTeam[home].getName());
				//divide the experience gained
				divideXp(visitorTeam,visitorTeam[visitor],homeTeam[home]);
				if(battleFinished(homeTeam)){
					messageQueue.add(4);
				}else{
					messageQueue.add(2);
				}
			}else{
				messageQueue.add(0);
			}
		}	
		aanHome = !aanHome;
	}
	
	public void change(int next){
		if(aanHome){
			// when a Phylomon has fainted is does not take a turn to change
			if(!homeTeam[home].dead())aanHome = !aanHome;
			this.home = next;
			//the message
			infoQueue.add(homeTeam[next].getName());
			messageQueue.add(6);	
		}else{
			// when a Phylomon has fainted is does not take a turn to change
			if(!visitorTeam[visitor].dead())aanHome = !aanHome;
			this.visitor = next;
			//the message
			infoQueue.add(visitorTeam[next].getName());
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
		return homeTeam[home];
	}
	public Phylomon getVisitor(){
		return visitorTeam[visitor];
	}
		

	private void divideXp(Phylomon[] team,Phylomon victor,Phylomon fainter){
		int Xp = Experience.getXpToGive(victor, fainter, true);
		victor.addXp(Xp);
		messageQueue.add(9);
		infoQueue.add(victor.getName());
		infoQueue.add(Integer.toString(Xp));
		
	}
	//returns true if there are no more Phylomon in the team that can fight
 	private boolean battleFinished(Phylomon[] team){
		for(Phylomon phylomon : team){
			if(phylomon == null)return true;
			if(!phylomon.dead())return false;
		}
		return true;
	}

}
