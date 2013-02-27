//this class gives some functions to calculate 
//		- the expierince a Phylomon has to get for leveling up
//		- the expierience to be divided after a battle
// in formation about how this is calculated can be found at:
// http://bulbapedia.bulbagarden.net/wiki/Experience



package PhyloKlasse;




public class Experience {
	
	
	public static int getXpForLevel(int XpClass,int level){
		int n = level;
		int n2 = n * n;
		int n3 = n2 * n; 
		
		int Xp = 1000000;
		switch(XpClass){
		//eratic case
		case 0 : 
			if(level < 50){
				Xp = n3 *(100 - n)/50;
			}else if(n < 68){
				Xp = n3 * (150 - n)/100;			
			}else if(n < 101){
				Xp = n3 * (1191 - 10*n)/1500;
			}
			break;
				
		}
		
		return Xp;
	}
	
	// nog niet af
	public static int getXpToGive(Phylomon victor,Phylomon fainter, boolean wild){
		double a;
		if(wild){a=3/2;}else{a=1;};
		int b = fainter.getBaseXp();
		int Lf = fainter.getLevel();
		int Lv = victor.getLevel();
		
		int Xp = 0;
		
		
		
		
		
		return 100;
	}
	
	
	
	
	

}
