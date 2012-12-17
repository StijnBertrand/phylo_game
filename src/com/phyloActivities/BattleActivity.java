package com.phyloActivities;



import com.phylogame.R;

import PhyloKlasse.Battle;
import PhyloKlasse.Phylomon;
import PhyloKlasse.PhylomonType;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class BattleActivity extends Activity implements OnClickListener{
	PhyloApplication app;	
	Battle battle;
	
	Button attack;
	TextView message,hph,hpv;
	AlertDialog.Builder dialog;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.battle);
		app = ((PhyloApplication)getApplicationContext());
		
		message = (TextView) findViewById(R.id.message);
		attack = (Button) findViewById(R.id.attackBut);
		hph = (TextView) findViewById(R.id.hphome);
		hpv = (TextView) findViewById(R.id.hpvisitor);
		attack.setOnClickListener(this);
		dialog = new AlertDialog.Builder(this);
		
		
		battle = new Battle(new Phylomon (app.getDatabase()[0]),
							new Phylomon (app.getDatabase()[0]));

	}

	@Override
	public void onClick(View v) {

		battle.attack();	
		draw();
		
		battle.attack();
		draw();
		
		if(battle.getHome().dead()){
			message.setText("the battel is over, you have lost");
			this.onBackPressed();
		}
		if(battle.getVisitor().dead()){
			message.setText("the batte is over, you have won");
			this.onBackPressed();
		}
		
	}
	
	public void draw(){
		
		message.setText(battle.getMessage());
		hph.setText(battle.getHome().getHP() + "/" + battle.getHome().getMaxHp());
		hpv.setText(battle.getVisitor().getHP() + "/" + battle.getVisitor().getMaxHp());

		
		
		
	}
	

	
		
	
	
	

}
