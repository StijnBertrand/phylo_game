package com.phyloActivities;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.UUID;


import com.google.gson.*;

import PhyloKlasse.Attack;
import PhyloKlasse.Phylomon;
import PhyloKlasse.PhylomonType;
import PhyloKlasse.XmlParser;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.util.Log;

public class PhyloApplication extends Application{ 
	private SharedPreferences pref;
	private  Editor editor;
	//writable private(meaning that only this application can read and write it) file on the android device
	private static String FILE_NAME = "phylomon.json";
	private static int TEAM_MAXIMUM = 6;
	
	//database of types
	private PhylomonType[] types;
	//the player his team (only relevant when NFC is disabled
	private Phylomon[] team;
	
	//options
	private boolean NFCenabled = true;
	//application identifier
	UUID appID;
	int teamCount;
	
	@Override
    public void onCreate() {
        super.onCreate();
        initializeDB(); 
        initializePhylomon();
        initializeAppData();
	}
	
	public PhylomonType[] getDatabase(){
		return types;
	}
	
	public Phylomon[] getTeam(){
		return team;
	}
	
	public int getTeamMaximum(){
		return TEAM_MAXIMUM;
	}
	
	public void removeFromTeam(int i){	
		for(int n = i; n != TEAM_MAXIMUM ; n++){
			team[n] = team [n+1];
		}
		team[TEAM_MAXIMUM-1]= null;
	}
	
	public boolean getNFCenabled(){
		return NFCenabled;
	}
	public void setNFCenabled(boolean NFCenabled){
		this.NFCenabled = NFCenabled;
		editor.putBoolean("NFC", NFCenabled);
		editor.commit();
	}
	
	public UUID getAppId(){
		return appID;
	}
	
	//this method can for example be called after a battle is done to save a win or a lose. 
	public void save(){
		savePhylomon();
	}
	
	
	private void initializePhylomon(){
		Gson gson = new Gson();		
		try{
			FileInputStream fis = openFileInput(FILE_NAME);	
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			
			String curr = null;
			String out = "";
			while((curr = br.readLine())!=null)out += curr;
			
			team = gson.fromJson(out, Phylomon[].class);

		}catch(FileNotFoundException e){
			//this is executed only the first time the application is executed
			team = new Phylomon[TEAM_MAXIMUM];
			//dit is tijdelijk
			Phylomon first = new Phylomon(types[0],5);
			Phylomon second = new Phylomon(types[1],7);
			Phylomon third = new Phylomon(types[2],9);
			team[0] = first;
			team[1] = second;
			team[2] = third;
			savePhylomon();
			
		} catch (IOException e) {

		}   	
	}
	

	
	//this stores the players personal phylomon in the applications private file
	private void savePhylomon(){
		Gson gson = new Gson();
		String json = gson.toJson(team);
		FileOutputStream fos;
		try {
			fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
			fos.write(json.getBytes());
			fos.close();
			
		} catch (FileNotFoundException e) {

		} catch (IOException e) {

		}
	}
		
	
	//method that initializes the database (the attacks array and the phylomontypes array)
	private void initializeDB(){		
		initPhylomonTypes();

	}
	
	//method that initializes the phylomontypes array
	private void initPhylomonTypes(){
		InputStream phylomonStream,attackStream;
		AssetManager assetManager = getAssets();   
        try {
        	phylomonStream = assetManager.open("creatures.xml");
        	attackStream = assetManager.open("attacks.xml");
            this.types = XmlParser.getDatabase(phylomonStream, attackStream);  
        } catch (IOException e) {
        	this.types = new PhylomonType[0];
            Log.e("tag", e.getMessage());
        }  
	}
	
	private void initializeAppData(){
		pref =  getSharedPreferences ("preferences",MODE_PRIVATE);
    	editor  = pref.edit();
		
    	NFCenabled = pref.getBoolean("NFC", true);
    	teamCount = pref.getInt("teamCount",1);
    	
    	//initializes the applications id
    	long mostSigBits = pref.getLong("most",0);
    	long leastSigBits = pref.getLong("least",0);
    	if(mostSigBits == 0 && leastSigBits == 0){
    		//in this case the application doesen't have a id yet so it gets one
    		appID = UUID.randomUUID();
    		editor.putLong("most", appID.getMostSignificantBits());
    		editor.putLong("least", appID.getLeastSignificantBits());
    		editor.commit();
    	}else{
        	appID = new UUID(mostSigBits,leastSigBits);

    	}
    	
	}
	public void saveAppData(){
		editor.putBoolean("NFC", NFCenabled);
		editor.putInt("teamCount", teamCount);
		editor.putLong("most", appID.getMostSignificantBits());
		editor.putLong("least", appID.getLeastSignificantBits());
		editor.commit();
		
	}
	
}
