package com.phyloActivities;



import com.phylogame.R;

import PhyloKlasse.Attack;
import PhyloKlasse.Battle;
import PhyloKlasse.NDEF;
import PhyloKlasse.Phylomon;
import PhyloKlasse.PhylomonType;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
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
	NfcAdapter adapter;
	PendingIntent pendingIntent;
	Tag tag = null;
	
	PhyloApplication app;	
	Battle battle;
	
	Button attack, change, bag, run,attack1,attack2,attack3,attack4;
	TextView message,hph,hpv;
	TableLayout menu, attacks;
	
	int activityResult;
	// variable used in the on touch listener
	boolean touch = false;
	boolean emptyBallScanAllouwd = false;
	boolean fullBallScanAllouwd = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.battle);
		
		
	    pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		adapter = NfcAdapter.getDefaultAdapter(this);
		
		
		app = ((PhyloApplication)getApplicationContext());
		
		message = (TextView) findViewById(R.id.message);
		message.setOnTouchListener(this);
				
		hph = (TextView) findViewById(R.id.hphome);
		hpv = (TextView) findViewById(R.id.hpvisitor);
		
		if(app.getNFCenabled()){
			battle = new Battle(null,new Phylomon(app.getDatabase()[0],5));
			fullBallScanAllouwd = true;
		}else{
			battle = new Battle(app.getMyPhylomon()[0],new Phylomon(app.getDatabase()[0],5));
		}
		
		
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
			emptyBallScanAllouwd = true;
			showMenu();
			break;
		}
		case 1 : {
			LetVisitorPlay();
			break;
		}
		
		case 2 : {
			message.setText("choose your next phylomon");
			showMenu();
			break;
		}
		
		case 3 : {
			break;
		}
		/*
		case 4 :{
			endBattle();
			break;
		}
		*/
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
		case 10:{
			message.setText(battle.getInfo() + " come back");
			touch = true;
			break;
		}
		case 11:{
			message.setText(battle.getInfo() + " returns");
			touch = true;
			break;
		}
		default : {
			break;
		}
		
		
		
		
		
		}
	}
	
	
	
	private void LetVisitorPlay(){
		if(!battle.getVisitor().dead()){
			battle.attack(0);
			draw();
			next();
		}else{
			finish();
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
				if(battle.getHome() ==null)return;
				if(!battle.getHome().dead()){
					hideMenu();
					showAttacks();
				}	
			}
		});
				
		if(!app.getNFCenabled()){
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
		
		
		//this puts a listener on the run away button
		run.setOnClickListener(
		new OnClickListener(){
			@Override
			public void onClick(View v){
				if(battle.getHome() != null && app.getNFCenabled()){
					message.setText("call " + battle.getHome().getName() + " back before running away.");
				}else{
					finish();
				}
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
	//and calls the Battle.change() method.
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    if(requestCode == 0){
	    	if(resultCode == RESULT_OK){
	    		int next = data.getIntExtra("next", -1);
	    		if(next!= -1){
	    			battle.change(app.getMyPhylomon()[next]);
	    			draw();
	    			next();
	    		}	
	    	}else{
	    		showMenu();
	    	}
	    }
	}
	
	
	

	private void draw(){
		if(battle.getHome() != null){
			hph.setText(battle.getHome().getHP() + "/" + battle.getHome().getMaxHp());
		}else{
			hph.setText("");
		}
		hpv.setText(battle.getVisitor().getHP() + "/" + battle.getVisitor().getMaxHp());
	}
	private void showMenu(){
		menu.setVisibility(TableLayout.VISIBLE);
	}
	private void hideMenu(){
		menu.setVisibility(TableLayout.GONE);
	}
	
	//initializes the attack buttons and shows the buttons zo the player
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
	
	@Override
	public void onBackPressed(){
		if(attack.getVisibility() == TableLayout.VISIBLE){
			hideAttacks();
			showMenu();
		}
	}
	
	public void onResume(){
		super.onResume();
		if(tag != null){
			try{
				Ndef ndef = Ndef.get(tag);
	    		ndef.connect();
	    		NdefMessage message = ndef.getNdefMessage();
	    		ndef.close();	    		
				if(fullBallScanAllouwd){
					try{
						Phylomon next = NDEF.ndefToPhylomon(message);
						//you can't choose a phylomon that has alredy fainted
						if(!next.dead()){
							NDEF.write(NDEF.getEmptyPhyloNdef(), tag);
							battle.changeHome(next);
							fullBallScanAllouwd = false;
							hideMenu();
							draw();
							next();
						}else{
							this.message.setText("choose a phylomon that has not yet fainted");
						}
					}catch(Exception e){}
				}else if(emptyBallScanAllouwd){
					try{
						NDEF.write(NDEF.phylomonToNdef(battle.getHome()), tag);
						battle.changeHome(null);
						emptyBallScanAllouwd = false;
						fullBallScanAllouwd = true;
						hideMenu();
						draw();
						next();
						
					}catch(Exception e){}
				}
				
			}catch(Exception e){}
		}
		if(fullBallScanAllouwd){
			enablePhylomonScan();
		}else{
			enableEmptyBallScan();
		}
	}
	
	
	private void enableEmptyBallScan(){
		//enable the handeling of a empty ball
		IntentFilter[] filters = new IntentFilter[1]; 
		filters[0] = NDEF.getEmptyBallFilter();
		adapter.enableForegroundDispatch(this,PendingIntent.getActivity(this, 0, getIntent(), 0),filters,null);
	}
	
	private void enablePhylomonScan(){
		//enable the handeling of a Phylomon 
		IntentFilter[] filters = new IntentFilter[1]; 
		filters[0] = NDEF.getPhylomonFilter();
		adapter.enableForegroundDispatch(this,PendingIntent.getActivity(this, 0, getIntent(), 0),filters,null);
	}
	
	
	@Override
	protected void onNewIntent(Intent intent){	
		super.onNewIntent(intent);
		if(fullBallScanAllouwd && intent.getType().equals(NDEF.PHYLOMON_MIME)){
			tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		}
		if(emptyBallScanAllouwd && intent.getType().equals(NDEF.EMPTY_BALL_MIME)){
			tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		}
	}
	
	@Override
	protected void onPause() {
	    super.onPause();
	    adapter.disableForegroundDispatch(this);
	}
	

}
