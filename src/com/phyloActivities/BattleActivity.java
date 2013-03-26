package com.phyloActivities;



import com.phylogame.R;

import PhyloKlasse.Attack;
import PhyloKlasse.Battle;
import PhyloKlasse.Phylomon;
import PhyloKlasse.PhylomonType;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;


public class BattleActivity extends Activity implements OnTouchListener{
	PhyloApplication app;	
	Battle battle;
	
	Button attack, change, bag, run,attack1,attack2,attack3,attack4;
	TextView message,hph,hpv;
	TableLayout menu, attacks;
	
	int activityResult;
	// variable used in the on touch listener
	boolean touch = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.battle);
		app = ((PhyloApplication)getApplicationContext());
		
		message = (TextView) findViewById(R.id.message);
		message.setOnTouchListener(this);
				
		hph = (TextView) findViewById(R.id.hphome);
		hpv = (TextView) findViewById(R.id.hpvisitor);
		
			
		battle = new Battle(app.getMyPhylomon(),getIntent().getExtras().getInt("myfirst"),
							new Phylomon[]{new Phylomon(app.getDatabase()[0],5)},0
							);
		
		menu = (TableLayout) findViewById(R.id.menu);
		initializeMenu();
		attacks = (TableLayout) findViewById(R.id.attacks4);
		initializeMenu();
		
		draw();		
		next();		
	}	
	
	

	public boolean onTouch(View v, MotionEvent event) {
		if(touch){
			touch = false;
			next();
		}
		return false;
	}
	
	
	
	private void next(){
		int n = battle.next();

		switch (n){
		case 0 : {
			showMenu();
			break;
		}
		case 1 : {
			LetVisitorPlay();
			break;
		}
		case 2 : {
			getNextPhylomon();
			break;
		}
		case 3 : {
			break;
		}
		case 4 :{
			endBattle();
			break;
		}
		case 5 :{
			message.setText(battle.getInfo() + " did " + battle.getInfo() + " and it dit " + battle.getInfo() + " damage");
			touch = true;
			break;
		}
		case 6 :{
			message.setText(battle.getInfo() + " I choose you!!!");
			touch = true;
			break;
		}
		case 7 :{
			message.setText("The opponent has chosen " + battle.getInfo());
			touch = true;
			break;
		}
		case 8 :{
			message.setText(battle.getInfo() + " has fainted.");
			touch = true;
			break;
		}
		case 9 :{
			message.setText(battle.getInfo() + " has gained " + battle.getInfo() + "experience ");
			touch = true;
			break;
		}
		
		
		
		
		
		
		}
	}
	

	
	
	
	
	
	
	
	
	
	
	private void LetVisitorPlay(){
		battle.attack(0);
		draw();
		next();
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
				hideMenu();
				showAttacks();
				}
			}
		);
		
		//this puts a listener on the Phylomon button
		change.setOnClickListener(
		new OnClickListener(){
			@Override
			public void onClick(View v) {
				hideMenu();
				getNextPhylomon();	
			}
		});
		
		
		
	}

	
	//this procedure calls the activity to let the player choose the next Phylomon	
	private void getNextPhylomon(){
		Intent i = new Intent(this,MyPhylomon.class);  
		i.putExtra("activityId", 1);
		startActivityForResult(i, 0);
	}
	
	//this procedure gets Phylomon the players chose 
	//and calls the Battle.choose() method.
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    if(requestCode == 0){
	    	if(resultCode == RESULT_OK){
	    		int next = data.getIntExtra("next", -1);
	    		if(next!= -1){
	    			battle.change(next);
	    			draw();
	    			next();
	    		}
	    	}
	    }
	}
	
	
	

	private void draw(){	
		hph.setText(battle.getHome().getHP() + "/" + battle.getHome().getMaxHp());
		hpv.setText(battle.getVisitor().getHP() + "/" + battle.getVisitor().getMaxHp());
	}
	private void showMenu(){
		menu.setVisibility(TableLayout.VISIBLE);
	}
	private void hideMenu(){
		menu.setVisibility(TableLayout.GONE);
	}
	
	
	private void showAttacks(){
		Attack curr;
		curr = battle.getHome().getAttack(0);
		attack1 = (Button) findViewById(R.id.attack1);
		attack1.setText(curr.getName());
		attack1.setOnClickListener(
				new OnClickListener(){
					@Override
					public void onClick(View v) {
						hideAttacks();
						battle.attack(0);
						draw();
						next();
						}
					}
				);
		
		curr = battle.getHome().getAttack(1);
		if(curr != null){
			attack2 = (Button) findViewById(R.id.attack2);
			attack2.setText(curr.getName());
			attack2.setOnClickListener(
					new OnClickListener(){
						@Override
						public void onClick(View v) {
							hideAttacks();
							battle.attack(1);
							draw();
							next();
							}
						}
					);
		}
		
		curr = battle.getHome().getAttack(2);
		if(curr != null){
			attack3 = (Button) findViewById(R.id.attack3);
			attack3.setText(curr.getName());
			attack3.setOnClickListener(
					new OnClickListener(){
						@Override
						public void onClick(View v) {
							hideAttacks();
							battle.attack(2);
							draw();
							next();
							}
						}
					);
		}
		
		curr = battle.getHome().getAttack(3);
		if(curr != null){
			attack4 = (Button) findViewById(R.id.attack4);
			attack4.setText(curr.getName());
			attack4.setOnClickListener(
					new OnClickListener(){
						@Override
						public void onClick(View v) {
							hideAttacks();
							battle.attack(3);
							draw();
							next();
							}
						}
					);
		}		
		attacks.setVisibility(TableLayout.VISIBLE);
	}
	
	private void hideAttacks(){
		attacks.setVisibility(TableLayout.GONE);
	}
	
	private void endBattle(){
		//app.save();
		finish();
	}
	
	
	

}
