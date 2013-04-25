package com.phyloActivities;

import java.io.IOException;

import PhyloKlasse.NDEF;
import PhyloKlasse.Phylomon;
import PhyloKlasse.PhylomonType;
import android.app.Activity;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.phylogame.R;

public class ShowPhylomon extends Activity {
	PhyloApplication myapp;
	ImageView image;
	TextView nameView;
	Button toTeamBut;
	Phylomon current = null;
	Tag tag;
	NfcAdapter adapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.phylomon);
        adapter = NfcAdapter.getDefaultAdapter(this);
        myapp = ((PhyloApplication)getApplicationContext());
        
        // gets the database from the application 
        nameView = (TextView)findViewById(R.id.name2);
        toTeamBut = (Button)findViewById(R.id.toteam);
        
        Intent intent = getIntent(); 
        int activityId = getIntent().getIntExtra("activityId",-1);
        if (activityId == 2){
        	int i = getIntent().getIntExtra("position",-1);
            if(i!= -1){
            	current =  myapp.getTeam()[i];
            	display();
            } 
        }else if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())){
        	try{
        		tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        		Ndef ndef = Ndef.get(tag);
        		ndef.connect();
        		NdefMessage message = ndef.getNdefMessage();
        		ndef.close();
        		//get the Phylomon object and display it
        		current = NDEF.ndefToPhylomon(message);    
	        	if(current != null)display();
        	}catch (Exception e) {
        		finish();
        	}
        }
        
	}
	
	
	private void setToTeamBut(){
		toTeamBut.setOnClickListener(
		new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}
	
	private void display(){
		//show a picture
        image = (ImageView)findViewById(R.id.imageView2);
        String picloc = current.getPicLocatie();
        int imageResource = getResources().getIdentifier(picloc , "drawable", getPackageName());
        image.setImageResource(imageResource);
        
        //showing the name
        nameView = (TextView)findViewById(R.id.name2);
        nameView.setText(current.getName());
	}
}
