package com.phylogame;
 

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
    Button pDBut,battleBut;
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.start);
        
        pDBut = (Button) this.findViewById(R.id.phyloDatabase);
        pDBut.setOnClickListener(this);
        battleBut =(Button)findViewById(R.id.battle);
        battleBut.setOnClickListener(this);
      
    }

	@Override
	public void onClick(View v) {
		Intent Intent; 
		if(v.getId() == R.id.phyloDatabase){
			Intent = new Intent(this,ShowPhylomonDatabase.class);
			this.startActivity(Intent);
		}else if(v.getId() == R.id.battle){
			Intent = new Intent(this,Battle.class);
			this.startActivity(Intent);
		}
		
		
	}

}

