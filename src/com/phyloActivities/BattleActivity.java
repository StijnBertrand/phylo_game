package com.phyloActivities;



import com.phylogame.R;

import PhyloKlasse.Battle;
import PhyloKlasse.Phylomon;
import PhyloKlasse.PhylomonType;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;


public class BattleActivity extends Activity{
	PhyloApplication app;	
	Battle battle;
	
	Button attack, change, bag, run;
	TextView message,hph,hpv;
	TableLayout menu;
	
	String stringmessage;
	
	int activityResult;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.battle);
		app = ((PhyloApplication)getApplicationContext());
		
		message = (TextView) findViewById(R.id.message);
		hph = (TextView) findViewById(R.id.hphome);
		hpv = (TextView) findViewById(R.id.hpvisitor);
		
			
		battle = new Battle(app.getMyPhylomon(),getIntent().getExtras().getInt("myfirst"),
							new Phylomon[]{new Phylomon(app.getDatabase()[0],5)},0,
							app.getAttacks());
		
		menu = (TableLayout) findViewById(R.id.menu);
		initializeMenu();
		
		draw();
		showMenu();
	}	
	
	private void LetVisitorPlay(){
		battle.attack();
		draw();
		if(battle.getHome().dead()){
			message.setText("the battle is over, you have lost");
			//app.save();
			//zelfde reden als hierboven
			draw();
			endBattle();
		}
	}
	
	
	
	private void initializeMenu(){
		attack = (Button) findViewById(R.id.attackBut);
		change = (Button) findViewById(R.id.chousePhylomon);
		bag = (Button) findViewById(R.id.bag);
		run = (Button) findViewById(R.id.RunAway);
		
		//this puts a Listener on the attack button
		attack.setOnClickListener(
		new OnClickListener(){
			@Override
			public void onClick(View v) {
				stringmessage = "ge hebt aangevallen";
				battle.attack();
				draw();
				if(battle.dead()){
					stringmessage = "the battle is over,you have won";
					draw();
					endBattle();
				}else{
					LetVisitorPlay();
					showMenu();
				}
			}
		});
		
		change.setOnClickListener(
		new OnClickListener(){
			@Override
			public void onClick(View v) {
				getNextPhylomon();	
			}
		});
		
		
		
	}
	
	
	
	
	private void getNextPhylomon(){
		Intent i = new Intent(this,MyPhylomon.class);  
		i.putExtra("activityId", 1);
		startActivityForResult(i, 0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    if(requestCode == 0){
	    	if(resultCode == RESULT_OK){
	    		int next = data.getIntExtra("next", -1);
	    		if(next!= -1){
	    			battle.change(next);
	    			draw();
					LetVisitorPlay();
					showMenu();
	    		}
	    	}
	    }
	}
	
	
	
	
	
	
	private void draw(){	
		
		message.setText(stringmessage);
		hph.setText(battle.getHome().getHP() + "/" + battle.getHome().getMaxHp());
		hpv.setText(battle.getVisitor().getHP() + "/" + battle.getVisitor().getMaxHp());
	}
	private void showMenu(){
		menu.setVisibility(0);
	}
	private void hideMenu(){
		menu.setVisibility(2);
	}
	private void endBattle(){
		this.onBackPressed();
	}
	
	
	

}
