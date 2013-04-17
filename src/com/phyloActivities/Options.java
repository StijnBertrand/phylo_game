package com.phyloActivities;


import com.phylogame.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

public class Options extends Activity{
	PhyloApplication app;
	CheckBox NFC;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.options);
        app = ((PhyloApplication)getApplicationContext());
        
        NFC = (CheckBox) findViewById(R.id.NFC);
        NFC.setChecked(app.getNFCenabled());
	}
	
	@Override
	public void onStop(){
		super.onStop();
		app.setNFCenabled(NFC.isChecked());
	
	}
	
	
}
