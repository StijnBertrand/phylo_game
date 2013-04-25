package com.phyloActivities;

import java.util.UUID;

import com.phylogame.R;

import PhyloKlasse.NDEF;
import PhyloKlasse.Phylomon;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TextView;

public class PhyloCenter extends Activity {
	PhyloApplication app;
	NfcAdapter adapter;
	PendingIntent pendingIntent;
	Tag tag;
	TextView tekst;
	
	String TAG ="phylocenter";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.phylocenter);

    	Intent intent = new Intent(this, getClass());
	    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
	    pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
		adapter = NfcAdapter.getDefaultAdapter(this);
		app = ((PhyloApplication)getApplicationContext());
		 
		
		tekst = (TextView) findViewById(R.id.centertekst);
		tekst.setText("Welcome to the phylocenter ");

	}
	
	@Override
	public void onResume(){
		super.onResume();
		adapter.enableForegroundDispatch(this,pendingIntent,null,null);	
		
		if(tag != null){
			try{
				Ndef ndef = Ndef.get(tag);
				ndef.connect();
				NdefMessage message = ndef.getNdefMessage();
				ndef.close();	
				
				UUID id = NDEF.getUUIDOfNdef(message);
				//if this test succeeds the phylomon is bound to another application 
				if(id != null && !id.equals(app.getAppId())){
					tekst.setText("you cannot heal a phylomon that is in someone else his team.");
					return;
				}
				
				Phylomon phylomon = NDEF.ndefToPhylomon(message);
				phylomon.heal();
				NDEF.write(NDEF.phylomonToNdef(phylomon, id), tag);
				
				tekst.setText(phylomon.getName() + " his hitpoints have been restored");
			}catch(Exception e){}
		}
		
		
	}
	
	@Override
	protected void onNewIntent(Intent intent){	
		super.onNewIntent(intent);
		if(NDEF.BOUND_PHYLOMON_MIME.equals(intent.getType())||NDEF.UNBOUND_PHYLOMON_MIME.equals(intent.getType())){
			tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		}
		
	}
	
	@Override
	protected void onPause() { 
	    super.onPause();
	    adapter.disableForegroundDispatch(this);
	}

}
















/*

	*/
