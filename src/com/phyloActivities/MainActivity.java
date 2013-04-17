package com.phyloActivities;
 

import PhyloKlasse.Phylomon;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;


import java.util.*; 

import com.phylogame.R;

import android.app.Activity;
import android.view.View;
import android.view.View.*;
import android.widget.Button;
import android.content.Intent;

 
public class MainActivity extends Activity implements OnClickListener{
    Button pDBut,battleBut,myPhyloBut,optionBut;
    PhyloApplication app;
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.start);
        app = ((PhyloApplication)getApplicationContext());
        
        pDBut = (Button) this.findViewById(R.id.phyloDatabase);
        pDBut.setOnClickListener(this);
        battleBut =(Button)findViewById(R.id.battle);
        battleBut.setOnClickListener(this);
        myPhyloBut = (Button)findViewById(R.id.My_Phylomon);
        myPhyloBut.setOnClickListener(this);
        optionBut = (Button)findViewById(R.id.options);
        optionBut.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		Intent intent; 
		if(v.getId() == R.id.phyloDatabase){
			intent = new Intent(this,ShowPhylomonDatabase.class);
			this.startActivity(intent);
		}else if(v.getId() == R.id.battle){
			//to start a battle there has to be at least one phylomon that is not dead
			int i = 0;
			for(Phylomon curr : app.getMyPhylomon()){
				if(curr == null){
					i = app.getMaxPhylomon();
					break;
				}
				if(curr.dead())	i++;
				else break;
			}
			//if we found one we start the battle else we notify the user
			if(i == app.getMaxPhylomon()){
				//alert dialog 
			}else{
				intent = new Intent(this,BattleActivity.class);
				//pass on the first not dead phylomon
				intent.putExtra("myfirst", (int)i);
				this.startActivity(intent);
			}
	
		}else if (v.getId() == R.id.My_Phylomon){
			intent = new Intent(this,MyPhylomon.class);
			this.startActivity(intent);
		}else if (v.getId() == R.id.options){
			intent = new Intent(this,Options.class);
			this.startActivity(intent);
		}
		
		
	}

}

