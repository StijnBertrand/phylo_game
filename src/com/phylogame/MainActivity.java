package com.phylogame;
 

import android.graphics.Color;
import android.os.Bundle;



import java.util.*; 

import com.phylogame.R;

import android.app.Activity;
import android.view.View;
import android.view.View.*;
import android.widget.Button;
import android.content.Intent;

 
public class MainActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.start);
        
        Button pDBut = (Button) this.findViewById(R.id.phyloDatabase);
        pDBut.setOnClickListener(this);
        

    }

	@Override
	public void onClick(View v) {
		Intent pDIntent = new Intent(this,ShowPhylomonDatabase.class);
		
		this.startActivity(pDIntent);
		
	}

}

